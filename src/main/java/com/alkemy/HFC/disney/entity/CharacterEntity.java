package com.alkemy.HFC.disney.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "characters")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CharacterEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(unique = true, name = "id")
    private Long id;

    @Column(name = "image")
    private String image;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "biography")
    private String biography;

    @Column(name = "movies")
    @ManyToMany(mappedBy = "characters", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<MovieEntity> movies = new ArrayList<>();

//    //SOFT DELETE
//    private boolean deleted = Boolean.FALSE;
//
//    //ADD MOVIE
//    public void addMovie(MovieEntity movie) {
//        this.movies.add(movie);
//    }
//
//    //REMOVE MOVIE
//    public void removeMovie(MovieEntity movie) {
//        this.movies.remove(movie);
//    }
}
