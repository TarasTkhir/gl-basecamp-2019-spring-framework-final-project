package com.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Arrays;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CharacterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private String name;

    @Column
    private String height;

    @Column
    private String mass;

    @Column
    private String hair_color;

    @Column
    private String skin_color;

    @Column
    private String eye_color;

    @Column
    private String birth_year;

    @Column
    private String gender;

    @Column
    private String homeworld;

    @Column(length = 500)
    private String[] films;

    @Column(length = 500)
    private String[] species;

    @Column(length = 500)
    private String[] vehicles;

    @Column(length = 500)
    private String[] starships;

    @Column
    private String created;

    @Column
    private String edited;

    @Column
    private String url;

    @Column
    private String detail;

    @Override
    public String toString() {
        return "CharacterEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", height='" + height + '\'' +
                ", mass='" + mass + '\'' +
                ", hair_color='" + hair_color + '\'' +
                ", skin_color='" + skin_color + '\'' +
                ", eye_color='" + eye_color + '\'' +
                ", birth_year='" + birth_year + '\'' +
                ", gender='" + gender + '\'' +
                ", homeworld='" + homeworld + '\'' +
                ", films=" + Arrays.toString(films) +
                ", species=" + Arrays.toString(species) +
                ", vehicles=" + Arrays.toString(vehicles) +
                ", starships=" + Arrays.toString(starships) +
                ", created='" + created + '\'' +
                ", edited='" + edited + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
