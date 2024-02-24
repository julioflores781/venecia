package com.test.venecia.persistence.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getCrewCapacity() {
        return crewCapacity;
    }

    public void setCrewCapacity(int crewCapacity) {
        this.crewCapacity = crewCapacity;
    }
}