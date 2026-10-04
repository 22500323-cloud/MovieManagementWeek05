package com.webservice.moviemanagementweek05.controller;

import com.webservice.moviemanagementweek05.dto.MovieRequest;
import com.webservice.moviemanagementweek05.dto.MovieResponse;
import com.webservice.moviemanagementweek05.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<MovieResponse> create(@RequestBody MovieRequest request) {
        MovieResponse response = movieService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // READ - 전체 조회
    @GetMapping
    public ResponseEntity<List<MovieResponse>> findAll() {
        return ResponseEntity.ok(movieService.findAll());
    }

    // READ - 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.findById(id));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<MovieResponse> update(
            @PathVariable Long id,
            @RequestBody MovieRequest request) {

        return ResponseEntity.ok(movieService.update(id, request));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        movieService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // 장르로 영화 검색
    @GetMapping(params = "genre")
    public ResponseEntity<List<MovieResponse>> findByGenre(
            @RequestParam String genre) {

        return ResponseEntity.ok(movieService.findByGenre(genre));
    }
}