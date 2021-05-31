package com.maybank.mapi.controller;

import com.maybank.mapi.model.Movie;
import com.maybank.mapi.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/favourite")
@RestController
public class FavouriteMoviesController {

    @Autowired
    private MovieService movieService;

    Logger log = LoggerFactory.getLogger(FavouriteMoviesController.class);

    @GetMapping("/movies")
    public ResponseEntity<Page<Movie>> getAllFavouriteMovies(Pageable pageable) {
        log.info("Find all favourite movies");
        log.info("REQUEST: " + "http://localhost:8080/api/favourite/movies/");
        return ResponseEntity.ok().body(movieService.getAllFavouriteMovies(pageable));
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<Movie> getFavouriteMovie(@PathVariable Long id) {
        log.info("Find favourite movie by id");
        log.info("REQUEST: " + "http://localhost:8080/api/favourite/movies/" + id);
        return ResponseEntity.ok().body(this.movieService.getFavouriteMovie(id));
    }

    @PostMapping("/movies")
    public ResponseEntity<Movie> createFavouriteMovie(@RequestBody Movie movie) {
        log.info("Add new favourite movie");
        log.info("REQUEST: " + "http://localhost:8080/api/favourite/movies/");
        return ResponseEntity.ok().body(this.movieService.createFavouriteMovie(movie));
    }

    @PutMapping("/movies/{id}")
    public ResponseEntity<Movie> updateFavouriteMovie(@PathVariable Long id, @RequestBody Movie movie) {
        log.info("Edit existing favourite movie");
        log.info("REQUEST: " + "http://localhost:8080/api/favourite/movies/" + id);
        movie.setId(id);
        return ResponseEntity.ok().body(this.movieService.updateFavouriteMovies(movie));
    }

    @DeleteMapping("/movies/{id}")
    public String deleteFavouriteMovie(@PathVariable Long id) {
        log.info("Delete existing favourite movie");
        log.info("REQUEST: " + "http://localhost:8080/api/favourite/movies/" + id);
        this.movieService.deleteFavouriteMovies(id);
        return "Successfully deleted favourite movie with id = " + id;
    }
}
