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
@Table(name = "genres")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
//
// THIS QUERY PROVIDES THE SOFT DELETION, SUCH AS AN UPDATE ON THE GENRE
@SQLDelete(sql = "UPDATE genres SET deleted = true WHERE id=?")
//
// WHEN SEARCHING FOR GENRES I HAVE TO DIFFERENTIATE BETWEEN THOSE THAT ARE 
// DELETED AND THOSE THAT ARE NOT, WITH THIS CLAUSE I DIFFERENTIATE BETWEEN THEM
@Where(clause = "deleted = false")
//
public class GenreEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(unique = true)
    private String id;

    private String name;

    private String image;

    // ATTRIBUTE TO SOFT DELETE
    private boolean deleted = Boolean.FALSE;

    // RELATION BETWEEN ENTITIES
    // GENRE -> MOVIE
    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY, cascade = {
        CascadeType.DETACH,
        CascadeType.MERGE,
        CascadeType.PERSIST,
        CascadeType.REFRESH})
    private List<MovieEntity> movies = new ArrayList<>();

//    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<MovieEntity> movies = new ArrayList<>();
}
