package com.maybank.mapi.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public class MovieList {
    @JsonAlias({"Search"})
    private List<Movie> movieList;

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }
}
