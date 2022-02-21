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
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

@Entity
@Table(name = "movies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
//
// THIS QUERY PROVIDES THE SOFT DELETION, SUCH AS AN UPDATE ON THE MOVIE
@SQLDelete(sql = "UPDATE movies SET deleted = true WHERE id=?")
//
// WHEN SEARCHING FOR MOVIES I HAVE TO DIFFERENTIATE BETWEEN THOSE THAT ARE 
// DELETED AND THOSE THAT ARE NOT, WITH THIS CLAUSE I DIFFERENTIATE BETWEEN THEM
@Where(clause = "deleted = false")
//
public class MovieEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(unique = true)
    private String id;

    private String image;

    @Column(unique = true)
    private String title;

    @Column(name = "date_of_creation")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate creationDate;

    @Nullable
    // FROM 1 UP TO 5
    private Integer rating;

    //NTERMEDIATE TABLE BETWEEN CHARACTERS AND MOVIES
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    // fetch -> MAKES THE INITIALIZATION AN EARLY TYPE, EACH TIME IT ASKS FOR AN
    // MOVIE, IT'S GOING TO COME WITH ALL THE CHARACTERS
    @JoinTable(
            name = "movie_characters",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "character_id"))
    private List<CharacterEntity> characters = new ArrayList<>();
//
//    //INTERMEDIATE TABLE BETWEEN MOVIES AND GENRES
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    // fetch -> MAKES THE INITIALIZATION AN EARLY TYPE, EACH TIME IT ASKS FOR AN
    // MOVIE, IT'S GOING TO COME WITH ALL GENRES
    @JoinTable(
            name = "movie_genres",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<GenreEntity> genres = new ArrayList<>();

    // ATTRIBUTE TO SOFT DELETE
    private boolean deleted = Boolean.FALSE;

    //ADD CHARACTER
    public void addCharacter(CharacterEntity character) {
        this.characters.add(character);
    }

    //REMOVE CHARACTER
    public void removeCharacter(CharacterEntity character) {
        this.characters.remove(character);
    }

    // ADD GENRE
    public void addGenre(GenreEntity genre) {
        this.genres.add(genre);
    }

    //REMOVE GENRE
    public void removeGenre(GenreEntity genre) {
        this.genres.remove(genre);
    }

    @Override
    public boolean equals(Object object) {

        if (object == null) {
            return false;
        }
        
        if (getClass() != object.getClass()) {
            return false;
        }

        final MovieEntity other = (MovieEntity) object;

        return other.id.equals(id);
    }
}
