package service;

import domain.Movie;
import dto.MovieRequest;
import dto.MovieResponse;
import org.springframework.stereotype.Service;
import repository.MemoryMovieRepository;
import repository.MovieRepository;

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
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        return MovieResponse.from(movie);
    }

    // 영화 수정
    public MovieResponse update(Long id, MovieRequest request) {

        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

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
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        movieRepository.deleteById(id);
    }
}