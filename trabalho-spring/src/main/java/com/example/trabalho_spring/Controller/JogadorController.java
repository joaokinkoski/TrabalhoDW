package com.example.trabalho_spring.Controller;

import com.example.trabalho_spring.Model.Jogador;
import com.example.trabalho_spring.Model.Posicao;
import com.example.trabalho_spring.Model.Times;
import com.example.trabalho_spring.Repository.JogadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/jogador")
public class JogadorController {
    
    @Autowired
    private JogadorRepository jogadorRep;

    @GetMapping("/listar")
    public ResponseEntity<List<Jogador>> listAll(){
        try{
            List<Jogador> listaJogadores = new ArrayList<Jogador>();
            jogadorRep.findAll().forEach(listaJogadores::add);

            if(listaJogadores.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(listaJogadores, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/create")
    public ResponseEntity<Jogador> createJogador(@RequestBody Jogador jogador){
        try{
            LocalDate datanasc = LocalDate.of(2002, 5, 13);      
            Jogador newJogador = jogadorRep.save(new Jogador(jogador.getNome(), jogador.getEmail(), datanasc, jogador.getPosicao(), jogador.getTimes()));
            return new ResponseEntity<>(newJogador, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/listarId/{cod_jogador}")
        public ResponseEntity<?> getJogadorById(@PathVariable("cod_jogador") long cod_jogador){
            Optional<Jogador> data = jogadorRep.findById(cod_jogador);

            if(data.isPresent()){
                return new ResponseEntity<>(data.get(), HttpStatus.OK);
            }
            return new ResponseEntity<>("Nenhum jogador com esse ID." ,HttpStatus.NOT_FOUND);
        }
    @PutMapping("update/{cod_jogador}")
    public ResponseEntity<?> updateJogador(@PathVariable("cod_jogador") long cod_jogador, @RequestBody Jogador jogador){

        Optional<Jogador> data = jogadorRep.findById(cod_jogador);

        if(data.isPresent()){
            Jogador jgdr = data.get();
            if(jogador.getNome() != null){
                jgdr.setNome(jogador.getNome());
            }
            if(jogador.getEmail() != null){
                jgdr.setEmail(jogador.getEmail());
            }
            if(jogador.getDatanasc() != null){
                jgdr.setDatanasc(jogador.getDatanasc());
            }
            if (jogador.getPosicao() != null) {
            // Aqui você vai usar o ID da posição
            Posicao posicao = new Posicao();
            posicao.setPosicaoId(jogador.getPosicao().getPosicaoId());
            jgdr.setPosicao(posicao);
        }
        if (jogador.getTimes() != null) {
            // Aqui você vai usar o ID do time
            Times time = new Times();
            time.setTimeId(jogador.getTimes().getTimeId());
            jgdr.setTimes(time);
        }

            return new ResponseEntity<>(jogadorRep.save(jgdr), HttpStatus.OK);
        }
            return new ResponseEntity<>("Nenhum jogador com esse ID", HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("delete/{cod_jogador}")
    public ResponseEntity<?> deleteJogador(@PathVariable("cod_jogador")long cod_jogador){
        try{
        Optional<Jogador> data = jogadorRep.findById(cod_jogador);

        if(data.isPresent()){
            jogadorRep.deleteById(cod_jogador);
            return new ResponseEntity<>("Deletado com sucesso", HttpStatus.OK);
        }
        return new ResponseEntity<>("Nenhum jogador com esse ID", HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>("Erro ao deletar.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("deleteAll")
    public ResponseEntity<?> deleteAll(){
        try{
            jogadorRep.deleteAll();
            return new ResponseEntity<>("Tabela limpa com sucesso!", HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
