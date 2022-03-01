package com.alkemy.HFC.disney.repository;

import com.alkemy.HFC.disney.entity.GenreEntity;
import com.alkemy.HFC.disney.entity.MovieEntity;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, String> {

     List<MovieEntity> findAll(Specification<MovieEntity> entitySpecification);
}
