package com.alkemy.HFC.disney.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

@Entity
@Table(name = "movies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MovieEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(unique = true, name = "id")
    private Long id;

    @Column(name = "image")
    private String image;

    @Column(unique = true, name = "title")
    private String title;

    @Column(name = "date_of_creation")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate creationDate;

    @Nullable
    @Column(name = "rating")
    // FROM 1 UP TO 5
    private Integer rating;

    //NTERMEDIATE TABLE BETWEEN CHARACTERS AND MOVIES
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    // fetch -> HACE QUE LA INICIALIZACIÓN SEA DE TIPO TEMPRANA, CADA VEZ QUE PIDA UNA
    // PELÍCULA, VA A VENIR CON TODOS LOS PERSONAJES
    @JoinTable(
            name = "movie_characters", // NAME OF THE INTERMEDIATE TABLE
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "character_id"))
    private List<CharacterEntity> characters = new ArrayList<>();

    //INTERMEDIATE TABLE BETWEEN MOVIES AND GENRES
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    // fetch -> HACE QUE LA INICIALIZACIÓN SEA DE TIPO TEMPRANA, CADA VEZ QUE PIDA UNA
    // PELÍCULA, VA A VENIR CON TODOS LOS GÉNEROS
    @JoinTable(
            name = "movie_genres", // NAME OF THE INTERMEDIATE TABLE
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<GenreEntity> genres = new ArrayList<>();

//    //SOFT DELETE
//    private boolean deleted = Boolean.FALSE;
//
//    //ADD CHARACTER
//    public void addCharacter(CharacterEntity character) {
//        this.characters.add(character);
//    }
//
//    //REMOVE CHARACTER
//    public void removeCharacter(CharacterEntity character) {
//        this.characters.remove(character);
//    }
//
//    // ADD GENRE
//    public void addGenre(GenreEntity genre) {
//        this.genres.add(genre);
//    }
//
//    //REMOVE GENRE
//    public void removeGenre(GenreEntity genre) {
//        this.genres.remove(genre);
//    }
}
