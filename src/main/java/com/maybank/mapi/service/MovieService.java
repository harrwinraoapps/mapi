package com.maybank.mapi.service;

import com.maybank.mapi.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MovieService {
    Movie createFavouriteMovie(Movie movie);
    Page<Movie> getAllFavouriteMovies(Pageable pageable);
    Movie getFavouriteMovie(Long id);
    Movie updateFavouriteMovies(Movie movie);
    void deleteFavouriteMovies(Long id);
}
