package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@ApiModel(value = "All details about person")
public class Person {
    @ApiModelProperty("Auto-generated primary key")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty("Name of person")
    private String name;

    @ApiModelProperty("Directed movies of person")
    @JsonIgnore
    @OneToMany(mappedBy = "director")
    private List<Movie> directedMovies = new ArrayList<>();

    @ApiModelProperty("Acted movies of person")
    @JsonIgnore
    @ManyToMany(mappedBy = "actors")
    private List<Movie> movies = new ArrayList<>();
}
