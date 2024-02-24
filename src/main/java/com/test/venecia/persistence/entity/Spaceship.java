package com.test.venecia.persistence.entity;


import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Spaceship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SERIES")
    private String series;

    @Column(name = "MOVIE")
    private String movie;

    @Column(name = "MODEL")
    private String model;

    @Column(name = "CREW_CAPACITY")
    private int crewCapacity;

    public Spaceship() {
    }

    public Spaceship(String name, String series, String movie, String model, int crewCapacity) {
        this.name = name;
        this.series = series;
        this.movie = movie;
        this.model = model;
        this.crewCapacity = crewCapacity;
    }

    @Override
    public String toString() {
        return "Spaceship{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", series='" + series + '\'' +
                ", movie='" + movie + '\'' +
                ", model='" + model + '\'' +
                ", crewCapacity=" + crewCapacity +
                '}';
    }
}