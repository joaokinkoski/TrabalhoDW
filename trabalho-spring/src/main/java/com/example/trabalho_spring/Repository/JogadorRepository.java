package com.example.trabalho_spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.trabalho_spring.Model.Jogador;

public interface JogadorRepository extends JpaRepository<Jogador, Long> {
    
}
