package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@ApiModel("All information about movie")
public class Movie {
    @ApiModelProperty("Auto-generated primary key")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty("Movie title")
    private String title;

    @ApiModelProperty("Movie director")
    @ManyToOne(fetch = FetchType.EAGER)
    private Person director;

    @ApiModelProperty("Acting staff of movie")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Person> actors = new ArrayList<>();
}
