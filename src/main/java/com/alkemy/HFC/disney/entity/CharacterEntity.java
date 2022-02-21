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
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "characters")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
//
// THIS QUERY PROVIDES THE SOFT DELETION, SUCH AS AN UPDATE ON THE CHARACTER
@SQLDelete(sql = "UPDATE characters SET deleted = true WHERE id=?")
//
// WHEN SEARCHING FOR CHARACTERS I HAVE TO DIFFERENTIATE BETWEEN THOSE THAT ARE 
// DELETED AND THOSE THAT ARE NOT, WITH THIS CLAUSE I DIFFERENTIATE BETWEEN THEM
@Where(clause = "deleted = false")
//
public class CharacterEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(unique = true)
    private String id;

    private String image;

    private String name;

    private Integer age;

    private Double weight;

    private String biography;

    @ManyToMany(mappedBy = "characters", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<MovieEntity> movies = new ArrayList<>();

    // ATTRIBUTE TO SOFT DELETE
    private boolean deleted = Boolean.FALSE;

    //ADD MOVIE
    public void addMovie(MovieEntity movie) {
        this.movies.add(movie);
    }

    //REMOVE MOVIE
    public void removeMovie(MovieEntity movie) {
        this.movies.remove(movie);
    }
}
