package com.alkemy.HFC.disney.repository.specification;

import com.alkemy.HFC.disney.dto.CharacterDTOFilter;
import com.alkemy.HFC.disney.entity.CharacterEntity;
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

public class CharacterSpecification {

    // RECEIVE THE LIST OF DTO FILTERS
    public Specification<CharacterEntity> getByFilters(CharacterDTOFilter filterDTO) {

        // LAMBDA FUNCTION
        return (root, query, criteriaBuilder) -> {

            // THE LIST OF PREDICATES IS CREATED
            List<Predicate> predicates = new ArrayList<>();

            // CREATING A DYNAMIC QUERY, hasLength() CHECKS IF IT EXIST
            // FILTER BY NAME
            if (StringUtils.hasLength(filterDTO.getName())) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("name")),
                                "%" + filterDTO.getName().toLowerCase() + "%"));
            }

            // FILTER BY AGE
            if (filterDTO.getAge() != null) {
                predicates.add(
                        criteriaBuilder.equal(root.get("age"), filterDTO.getAge())
                );
            }

            // FILTER BY MOVIE
            if (!CollectionUtils.isEmpty(filterDTO.getMovies())) {
                Join<CharacterEntity, MovieEntity> join = root.join("characterMovies", JoinType.INNER);
                Expression<String> moviesId = join.get("id");
                predicates.add(moviesId.in(filterDTO.getMovies()));
            }

            // REMOVE DUPLICATES
            query.distinct(true);

            // ORDER RESOLVER
            String orderByField = "name";
            query.orderBy(
                    filterDTO.isASC()
                    ? criteriaBuilder.asc(root.get(orderByField))
                    : criteriaBuilder.desc(root.get(orderByField))
            );

            // RETURN THE GENERATED PREDICATE AS A LIST
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
