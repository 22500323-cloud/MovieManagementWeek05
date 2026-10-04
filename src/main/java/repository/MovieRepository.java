package repository;

import domain.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieRepository {

    Movie save(Movie movie);

    List<Movie> findAll();

    Optional<Movie> findById(Long id);

    void deleteById(Long id);
}