package com.webservice.moviemanagementweek05.dto;

import com.webservice.moviemanagementweek05.domain.Movie;

public class MovieResponse {

    private Long id;
    private String title;
    private String director;
    private String genre;
    private int year;
    private double rating;
    private int runningTime;

    public MovieResponse(Long id, String title, String director, String genre,
                         int year, double rating, int runningTime) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.year = year;
        this.rating = rating;
        this.runningTime = runningTime;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }

    public String getGenre() {
        return genre;
    }

    public int getYear() {
        return year;
    }

    public double getRating() {
        return rating;
    }

    public int getRunningTime() {
        return runningTime;
    }

    public static MovieResponse from(Movie movie) {
        return new MovieResponse(
                movie.getId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getGenre(),
                movie.getYear(),
                movie.getRating(),
                movie.getRunningTime()
        );
    }
}