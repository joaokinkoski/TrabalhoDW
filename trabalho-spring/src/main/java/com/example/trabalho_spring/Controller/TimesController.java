package com.example.trabalho_spring.Controller;


import com.example.trabalho_spring.Model.Times;
import com.example.trabalho_spring.Repository.TimesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/times")
public class TimesController {
    
    @Autowired
    private TimesRepository timesRep;

    @GetMapping("/listar")
    public ResponseEntity<List<Times>> listAll(){
        try{
            List<Times> listaTimes = new ArrayList<Times>();
            timesRep.findAll().forEach(listaTimes::add);
            return new ResponseEntity<>(listaTimes, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
