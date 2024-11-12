package com.example.trabalho_spring.Controller;

import com.example.trabalho_spring.Model.Pagamento;
import com.example.trabalho_spring.Repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pagamento")
public class PagamentoController {

    @Autowired
    private PagamentoRepository pagamentoRep;

    @GetMapping("/listar")
    public ResponseEntity<List<Pagamento>> listAll(){

        try{
            List<Pagamento> listaPagamentos = new ArrayList<Pagamento>();
            pagamentoRep.findAll().forEach(listaPagamentos::add);

            if(listaPagamentos.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(listaPagamentos, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listarAno/{ano}")
    public ResponseEntity<?> listByAno (@PathVariable("ano") int ano){
        List<Pagamento> data = pagamentoRep.findByAno(ano);

        if(!data.isEmpty()){
            return new ResponseEntity<>(data, HttpStatus.OK);
        }
        return new ResponseEntity<>("Nenhum pagamento neste ano." , HttpStatus.NOT_FOUND);
    }

    @GetMapping("/listarMes/{mes}")
    public ResponseEntity<?> listByMes (@PathVariable("mes") int mes){
        List<Pagamento> data = pagamentoRep.findByMes(mes);

        if(!data.isEmpty()){
            return new ResponseEntity<>(data, HttpStatus.OK);
        }
        return new ResponseEntity<>("Nenhum pagamento neste mes." , HttpStatus.NOT_FOUND);
    }

    @GetMapping("/listarJogador/{codJogador}")
    public ResponseEntity<?> listByJogador(@PathVariable("codJogador") long codJogador){
        List<Pagamento> data = pagamentoRep.findByCodJogador(codJogador);

        if(!data.isEmpty()){
            return new ResponseEntity<>(data, HttpStatus.OK);
        }
        return new ResponseEntity<>("Jogador não realizou nenhum pagamento.", HttpStatus.NOT_FOUND);
    }
    @GetMapping("/listarId/{cod_pagamento}")
    public ResponseEntity<?> getPagamentoById (@PathVariable ("cod_pagamento") long cod_pagamento){
        Optional<Pagamento> data = pagamentoRep.findById(cod_pagamento);

        if(data.isPresent()){
            return new ResponseEntity<>(data.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Nenhum pagamento com esse ID.", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<Pagamento> createPagamento (@RequestBody Pagamento pagamento){
        try{
            Pagamento newPagamento = pagamentoRep.save(new Pagamento(pagamento.getAno(), pagamento.getMes(), pagamento.getValor(), pagamento.getCodJogador()));
            return new ResponseEntity<>(newPagamento, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("update/{cod_pagamento}")
    public ResponseEntity<?> updatePagamento(@PathVariable("cod_pagamento") long cod_pagamento, @RequestBody Pagamento pagamento){
        Optional<Pagamento> data = pagamentoRep.findById(cod_pagamento);

        if(data.isPresent()){
            Pagamento pgto = data.get();
            if(pagamento.getAno() != null){
                pgto.setAno(pagamento.getAno());
            }
            if(pagamento.getMes() != null){
                pgto.setMes(pagamento.getMes());
            }
            if(pagamento.getValor() != null){
                pgto.setValor(pagamento.getValor());
            }
            if(pagamento.getCodJogador() != null){
                pgto.setCodJogador(pagamento.getCodJogador());
            }

            return new ResponseEntity<>(pagamentoRep.save(pgto), HttpStatus.OK);
        }
        return new ResponseEntity<>("Nenhum pagamento com esse ID", HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("delete/{cod_pagamento}")
    public ResponseEntity<?> deletePagamento(@PathVariable("cod_pagamento") long cod_pagamento){
        try{
            Optional<Pagamento> data = pagamentoRep.findById(cod_pagamento);

            if(data.isPresent()){
                pagamentoRep.deleteById(cod_pagamento);
                return new ResponseEntity<>("Deletado com sucesso", HttpStatus.OK);
            }
            return new ResponseEntity<>("Nenhum pagamento com esse ID", HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>("Erro ao deletar", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("deleteAll")
    public ResponseEntity<?> deleteAll(){
        try{
            pagamentoRep.deleteAll();
            return new ResponseEntity<>("Tabela limpa com sucesso!", HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("Erro ao deletar", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
