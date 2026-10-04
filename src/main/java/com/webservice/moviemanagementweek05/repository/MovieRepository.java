package com.webservice.moviemanagementweek05.repository;

import com.webservice.moviemanagementweek05.domain.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieRepository {

    Movie save(Movie movie);

    List<Movie> findAll();

    Optional<Movie> findById(Long id);

    void deleteById(Long id);

    List<Movie> findByGenre(String genre);
}