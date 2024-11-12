package com.example.trabalho_spring.Controller;


import com.example.trabalho_spring.Model.Posicao;
import com.example.trabalho_spring.Repository.PosicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/posicoes")
public class PosicaoController {
    
    @Autowired
    private PosicaoRepository posicaoRep;

    @GetMapping("/listar")
    public ResponseEntity<List<Posicao>> listAll(){
        try{
            List<Posicao> listaPosicoes = new ArrayList<Posicao>();
            posicaoRep.findAll().forEach(listaPosicoes::add);
            return new ResponseEntity<>(listaPosicoes, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
