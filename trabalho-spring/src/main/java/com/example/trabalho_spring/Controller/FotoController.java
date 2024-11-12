package com.example.trabalho_spring.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.trabalho_spring.Repository.JogadorRepository;



import com.example.trabalho_spring.Model.Jogador;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/fotos")
public class FotoController {
    
    @Autowired
    private JogadorRepository jogadorRep;

    @Value("${frontend.upload.dir}")
    private String uploadDir;

    @PostMapping("/salvar")
public ResponseEntity<Map<String, String>> salvarFoto(
        @RequestParam("image") MultipartFile image,
        @RequestParam("cod_jogador") Long codJogador) {

    Map<String, String> response = new HashMap<>();

    // Verifica se o arquivo é vazio
    if (image.isEmpty()) {
        response.put("message", "Arquivo vazio. Por favor, envie uma imagem.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    try {
        // Cria o diretório de upload, caso não exista
        Path dirPath = Paths.get(uploadDir);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
            System.out.println("Diretório criado: " + dirPath.toString());
        }

        // Define o nome do arquivo e a extensão
        String fileExtension = getFileExtension(image.getOriginalFilename());
        String fileName = codJogador + fileExtension;

        // Verifica e exclui imagem existente
        excluirImagemExistente(codJogador);

        // Define o caminho e salva a imagem
        Path filePath = dirPath.resolve(fileName);
        Files.write(filePath, image.getBytes());
        System.out.println("Arquivo salvo em: " + filePath.toString());

        // Salva o nome do arquivo no banco
        salvarNoBanco(codJogador, fileName);

        response.put("message", "Imagem salva com sucesso!");
        response.put("filePath", filePath.toString());
        return ResponseEntity.ok(response);

    } catch (IOException e) {
        e.printStackTrace();
        response.put("message", "Erro ao salvar a imagem.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

private String getFileExtension(String fileName) {
    return fileName.substring(fileName.lastIndexOf("."));
}

private void excluirImagemExistente(Long codJogador) throws IOException {
    // Verifica se já existe um arquivo com o mesmo nome base
    Path dirPath = Paths.get(uploadDir);
    File dir = dirPath.toFile();
    String fileBaseName = codJogador.toString();  // Nome base do arquivo (sem a extensão)

    // Percorre todos os arquivos no diretório
    for (File file : dir.listFiles()) {
        // Verifica se o nome do arquivo começa com o mesmo nome base
        if (file.getName().startsWith(fileBaseName)) {
            // Exclui o arquivo, caso encontre
            Files.delete(file.toPath());
            System.out.println("Arquivo excluído: " + file.getName());
            break;  // Exclui apenas o primeiro arquivo encontrado e interrompe o loop
        }
    }
}

private void salvarNoBanco(Long codJogador, String fileName) {
    // Encontra o jogador pelo código
    Jogador jogador = jogadorRep.findById(codJogador)
            .orElseThrow(() -> new RuntimeException("Jogador não encontrado"));

    // Atualiza o caminho da imagem
    jogador.setCaminhoImg(fileName);
    jogadorRep.save(jogador);  // Salva o jogador atualizado no banco de dados
}
@DeleteMapping("/deletar")
public ResponseEntity<Map<String, String>> deletarFoto(
        @RequestParam("cod_jogador") Long codJogador) {

    Map<String, String> response = new HashMap<>();

    try {
        // Exclui o arquivo da imagem
        excluirImagemExistente(codJogador);

        // Atualiza o caminho da imagem no banco de dados para null
        atualizarCaminhoImagemNoBanco(codJogador);

        response.put("message", "Imagem deletada com sucesso!");
        return ResponseEntity.ok(response);

    } catch (IOException e) {
        e.printStackTrace();
        response.put("message", "Erro ao deletar a imagem.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
private void atualizarCaminhoImagemNoBanco(Long codJogador) {
    // Encontra o jogador pelo código
    Jogador jogador = jogadorRep.findById(codJogador)
            .orElseThrow(() -> new RuntimeException("Jogador não encontrado"));

    // Atualiza o caminho da imagem para null
    jogador.setCaminhoImg(null);
    jogadorRep.save(jogador);  // Salva o jogador atualizado no banco de dados
}
}
