package com.example.trabalho_spring.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "times")
public class Times {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long time_id;

    @Column(nullable = false)
    private String time_nome;

    public Times(){

    }

    public Long getTimeId(){
        return time_id;
    }
    public void setTimeId(Long time_id){
        this.time_id = time_id;
    }
    public String getTimeNome(){
        return time_nome;
    }
    public void setTimeNome(String time_nome){
        this.time_nome = time_nome;
    }
}
