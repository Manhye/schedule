package com.example.schedule.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "schedule")
public class Schedule extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "longtext")
    private String contents;

    @Column(nullable = false)
    private LocalDate scheduledDate;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    public Schedule(){
    }

    public Schedule(String title, String contents, LocalDate scheduledDate){
        this.title = title;
        this.contents=contents;
        this.scheduledDate=scheduledDate;
    }

}
