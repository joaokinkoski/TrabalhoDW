package com.example.trabalho_spring.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table (name = "pagamento")
public class Pagamento {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long cod_pagamento;

    @Column(nullable = false)
    private Integer ano;

    @Column(nullable = false)
    private Integer mes;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(name= "cod_jogador", nullable = false)
    private Long codJogador;

    
    public Pagamento(){

    }
    
    public Pagamento(int ano, int mes, BigDecimal valor, long codJogador){
        this.ano = ano;
        this.mes = mes;
        this.valor = valor;
        this.codJogador = codJogador;
    }

    public long getCod_pagamento(){
        return this.cod_pagamento;
    }
    public void setCod_pagamento(long cod_pagamento){
        this.cod_pagamento = cod_pagamento;
    }
    public Integer getAno(){
        return this.ano;
    }
    public void setAno(Integer ano){
        if(ano < 1 || ano > 9999 ){
            throw new IllegalArgumentException("O ano deve estar entre 1 e 9999!");
        }
        this.ano = ano;
    }
    public Integer getMes(){
        return this.mes;
    }
    public void setMes(Integer mes){
        if(mes < 1 || mes > 12){
            throw new IllegalArgumentException("Digite um mes valido");
        }
        this.mes = mes;
    }
    public BigDecimal getValor(){
        return this.valor;
    }
    public void setValor(BigDecimal valor){
        if(valor != null){
            this.valor = valor.setScale(2, RoundingMode.HALF_UP);
        }else{
        this.valor = null;
        }
    }
    public Long getCodJogador(){
        return this.codJogador;
    }
    public void setCodJogador(long codJogador){
        this.codJogador = codJogador;
    }
    @Override
    public String toString() {
        return "Pagamento{" + "cod_pagamento=" + cod_pagamento + ", ano=" + ano + ", mes=" + mes + ", valor=" + valor + ", codJogador=" + codJogador + '}';
    }
}
