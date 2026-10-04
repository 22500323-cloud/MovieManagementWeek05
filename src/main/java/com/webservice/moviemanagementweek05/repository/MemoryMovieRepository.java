package com.webservice.moviemanagementweek05.repository;

import com.webservice.moviemanagementweek05.domain.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemoryMovieRepository implements MovieRepository {

    private final List<Movie> movies = new ArrayList<>();
    private Long nextId = 1L;

    @Override
    public Movie save(Movie movie) {
        Movie savedMovie = new Movie(
                nextId,
                movie.getTitle(),
                movie.getDirector(),
                movie.getGenre(),
                movie.getYear(),
                movie.getRating(),
                movie.getRunningTime()
        );

        movies.add(savedMovie);
        nextId++;

        return savedMovie;
    }

    @Override
    public List<Movie> findAll() {
        return new ArrayList<>(movies);
    }

    @Override
    public Optional<Movie> findById(Long id) {
        return movies.stream()
                .filter(movie -> movie.getId().equals(id))
                .findFirst();
    }

    @Override
    public void deleteById(Long id) {
        movies.removeIf(movie -> movie.getId().equals(id));
    }

    // 장르로 영화 검색
    @Override
    public List<Movie> findByGenre(String genre) {
        return movies.stream()
                .filter(movie -> movie.getGenre().equalsIgnoreCase(genre))
                .toList();
    }
}