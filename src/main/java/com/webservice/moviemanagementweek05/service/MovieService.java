package com.webservice.moviemanagementweek05.service;

import com.webservice.moviemanagementweek05.domain.Movie;
import com.webservice.moviemanagementweek05.dto.MovieRequest;
import com.webservice.moviemanagementweek05.dto.MovieResponse;
import com.webservice.moviemanagementweek05.exception.MovieNotFoundException;
import com.webservice.moviemanagementweek05.repository.MemoryMovieRepository;
import com.webservice.moviemanagementweek05.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService() {
        this.movieRepository = new MemoryMovieRepository();
    }

    // 영화 등록
    public MovieResponse create(MovieRequest request) {
        validate(request);

        Movie movie = new Movie(
                null,
                request.getTitle(),
                request.getDirector(),
                request.getGenre(),
                request.getYear(),
                request.getRating(),
                request.getRunningTime()
        );

        Movie savedMovie = movieRepository.save(movie);

        return MovieResponse.from(savedMovie);
    }

    // 전체 영화 조회
    public List<MovieResponse> findAll() {
        return movieRepository.findAll()
                .stream()
                .map(MovieResponse::from)
                .collect(Collectors.toList());
    }

    // 영화 한 개 조회
    public MovieResponse findById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() ->
                        new MovieNotFoundException("Movie not found"));

        return MovieResponse.from(movie);
    }

    // 영화 수정
    public MovieResponse update(Long id, MovieRequest request) {
        validate(request);

        Movie movie = movieRepository.findById(id)
                .orElseThrow(() ->
                        new MovieNotFoundException("Movie not found"));

        movie.update(
                request.getTitle(),
                request.getDirector(),
                request.getGenre(),
                request.getYear(),
                request.getRating(),
                request.getRunningTime()
        );

        return MovieResponse.from(movie);
    }

    // 영화 삭제
    public void delete(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() ->
                        new MovieNotFoundException("Movie not found"));

        movieRepository.deleteById(id);
    }

    // 장르로 영화 검색
    public List<MovieResponse> findByGenre(String genre) {
        return movieRepository.findByGenre(genre)
                .stream()
                .map(MovieResponse::from)
                .collect(Collectors.toList());
    }

    // 입력값 검증
    private void validate(MovieRequest request) {

        if (request.getTitle() == null || request.getTitle().isBlank()) {
            throw new IllegalArgumentException("Title is required");
        }

        if (request.getDirector() == null || request.getDirector().isBlank()) {
            throw new IllegalArgumentException("Director is required");
        }

        if (request.getGenre() == null || request.getGenre().isBlank()) {
            throw new IllegalArgumentException("Genre is required");
        }

        if (request.getYear() <= 0) {
            throw new IllegalArgumentException("Year must be positive");
        }

        if (request.getRating() < 0 || request.getRating() > 10) {
            throw new IllegalArgumentException(
                    "Rating must be between 0 and 10"
            );
        }

        if (request.getRunningTime() <= 0) {
            throw new IllegalArgumentException(
                    "Running time must be positive"
            );
        }
    }
}