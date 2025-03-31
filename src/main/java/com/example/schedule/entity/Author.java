package com.example.schedule.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "author")
public class Author extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable=false)
    private String username;

    @Column(nullable=false, unique = true)
    private String email;

    @Column(nullable =false)
    private String password;

    @Column(nullable = false)
    private Integer age;


    public Author(){
    }

    public Author(String username, String email, String password, Integer age){
        this.username=username;
        this.email=email;
        this.password=password;
        this.age = age;
    }

}
