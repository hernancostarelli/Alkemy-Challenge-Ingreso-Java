package com.alkemy.HFC.disney.repository.specification;

import com.alkemy.HFC.disney.dto.MovieDTOFilter;
import com.alkemy.HFC.disney.entity.CharacterEntity;
import com.alkemy.HFC.disney.entity.GenreEntity;
import com.alkemy.HFC.disney.entity.MovieEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class MovieSpecification {

    // RECEIVE THE LIST OF DTO FILTERS
    public Specification<MovieEntity> getFiltered(MovieDTOFilter movieFilters) {

        // LAMBDA FUNCTION
        return (root, query, criteriaBuilder) -> {

            // THE LIST OF PREDICATES IS CREATED
            List<Predicate> predicates = new ArrayList<>();

            // CREATING A DYNAMIC QUERY, hasLength() CHECKS IF IT EXIST
             // FILTER BY ID
            if (movieFilters.getId() != null) {
                predicates.add(
                        criteriaBuilder.equal(root.get("id"), movieFilters.getId())
                );
            }
            // FILTER BY TITLE
            if (StringUtils.hasLength(movieFilters.getTitle())) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("title")),
                                "%" + movieFilters.getTitle().toLowerCase() + "%"
                        )
                );
            }

            // FILTER BY CHARECTER
            if (!CollectionUtils.isEmpty(movieFilters.getCharacters())) {
                Join<MovieEntity, CharacterEntity> join = root.join("movieCharacters", JoinType.INNER);
                Expression<String> charactersId = join.get("id");
                predicates.add(charactersId.in(movieFilters.getCharacters()));
            }
            
            // FILTER BY GENRE
            if (!CollectionUtils.isEmpty(movieFilters.getGenres())) {
                Join<MovieEntity, GenreEntity> join = root.join("movieGenres", JoinType.INNER);
                Expression<String> genresId = join.get("id");
                predicates.add(genresId.in(movieFilters.getGenres()));
            }

            // REMOVE DUPLICATES
            query.distinct(true);

            // ORDER RESOLVER
            String orderByField = "title";
            query.orderBy(
                    movieFilters.isASC()
                    ? criteriaBuilder.asc(root.get(orderByField))
                    : criteriaBuilder.desc(root.get(orderByField))
            );

            // RETURN THE GENERATED PREDICATE AS A LIST
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
