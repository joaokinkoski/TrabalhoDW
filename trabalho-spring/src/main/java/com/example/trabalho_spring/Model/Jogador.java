package com.example.trabalho_spring.Model;


import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Table (name = "jogador")
public class Jogador {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long cod_jogador;   

    @Column(nullable = false, length = 60)
    private String nome;

    @Column(nullable = false, length = 60)
    private String email;

    @Column(nullable = false)
    private LocalDate datanasc;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "posicao_id", referencedColumnName = "posicao_id")
    private Posicao posicao;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "time_id", referencedColumnName = "time_id")
    private Times time;

    @Column(name="caminho_imagem")
    private String caminhoImg;

    public Jogador(){

    }

    public Jogador(String nome, String email, LocalDate datanasc, Posicao posicao, Times time){
        this.nome = nome;
        this.email = email;
        this.datanasc = datanasc;
        this.posicao = posicao;
        this.time = time;
    }


    public long getCod_jogador(){
        return this.cod_jogador;
    }
    public void setCod_jogador(long cod_jogador){
        this.cod_jogador = cod_jogador;
    }
    public String getNome(){
        return this.nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public String getEmail(){
        return this.email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public LocalDate getDatanasc(){
        return this.datanasc;
    }
    public void setDatanasc(LocalDate datanasc){
        this.datanasc = datanasc;
    }
    public Posicao getPosicao() {
        return this.posicao;
    }
    public Times getTimes(){
        return this.time;
    }
    public void setTimes(Times times){
        this.time = times;
    }

    public void setPosicao(Posicao posicao) {
        this.posicao = posicao;
    }
    public String getCaminhoImg(){
        return this.caminhoImg;
    }
    public void setCaminhoImg(String caminhoImg){
        this.caminhoImg = caminhoImg;
    }
    public String toString(){
        return "Jogador{" +  "cod_jogador= " + cod_jogador + ", nome='" + nome + '\'' + ", email='" + email + '\'' + ", datanasc=" + datanasc + '\'' + '}';
    }
}
