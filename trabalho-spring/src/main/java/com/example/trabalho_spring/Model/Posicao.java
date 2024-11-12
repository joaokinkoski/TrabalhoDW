package com.example.trabalho_spring.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "posicao")
public class Posicao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long posicao_id;

    @Column(nullable = false, length = 3)
    private String nome;

    public Posicao(){}

    public Long getPosicaoId(){
        return posicao_id;
    }
    public void setPosicaoId(Long posicao_id){
        this.posicao_id = posicao_id;
    }
    public String getNome(){
        return nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }

    @Override
    public String toString(){
        return "Posicao{" + "posicao_id=" + posicao_id + ", nome='" + nome + "'\'" + "}";
    }

}
