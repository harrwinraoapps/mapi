package com.maybank.mapi.service;

import com.maybank.mapi.controller.FavouriteMoviesController;
import com.maybank.mapi.exception.MovieNotFoundException;
import com.maybank.mapi.model.Movie;
import com.maybank.mapi.model.MovieList;
import com.maybank.mapi.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MovieServiceImpl implements MovieService{

    @Autowired
    private MovieRepository movieRepository;

    Logger log = LoggerFactory.getLogger(MovieServiceImpl.class);

    @Override
    public Movie createFavouriteMovie(Movie movie) {
        log.info("RESPONSE[" + HttpStatus.OK + "]: " + movie.toString());
        return movieRepository.save(movie);
    }

    @Override
    public Page<Movie> getAllFavouriteMovies(Pageable pageable) {
        Page<Movie> result = this.movieRepository.findAll(pageable);
        if (!result.hasContent()) {
            log.error("No favourite movie records found");
            throw new MovieNotFoundException("No favourite movie records found");
        }
        List<Movie> movieList = new ArrayList<>();
        for (Movie movie : result) {
            movieList.add(movie);
        }
        log.info("RESPONSE[" + HttpStatus.OK + "]: " + movieList.toString());
        return result;
    }

    @Override
    public Movie getFavouriteMovie(Long id) {
        Optional<Movie> result = this.movieRepository.findById(id);

        if (result.isPresent()) {
            log.info("RESPONSE[" + HttpStatus.OK + "]: " + result.toString());
            return result.get();
        }
        else {
            log.error("Movie not found with id : " + id);
            throw new MovieNotFoundException("Movie not found with id : " + id);
        }
    }

    @Override
    public Movie updateFavouriteMovies(Movie movie) {
        Optional<Movie> result = this.movieRepository.findById(movie.getId());

        if(result.isPresent()) {
            Movie movieUpdate = result.get();
            movieUpdate.setId(movie.getId());
            movieUpdate.setTitle(movie.getTitle());
            movieUpdate.setYear(movie.getYear());
            movieUpdate.setRated(movie.getRated());
            movieUpdate.setReleased(movie.getReleased());
            movieUpdate.setRuntime(movie.getRuntime());
            movieUpdate.setGenre(movie.getGenre());
            movieUpdate.setDirector(movie.getDirector());
            movieUpdate.setWriter(movie.getWriter());
            movieUpdate.setActors(movie.getActors());
            movieUpdate.setPlot(movie.getPlot());
            movieUpdate.setLanguage(movie.getLanguage());
            movieUpdate.setCountry(movie.getCountry());
            movieUpdate.setAwards(movie.getAwards());
            movieUpdate.setRatings(movie.getRatings());
            movieUpdate.setType(movie.getType());
            movieUpdate.setPosterUrl(movie.getPosterUrl());
            movieRepository.save(movieUpdate);

            log.info("RESPONSE[" + HttpStatus.OK + "]: " + movieUpdate.toString());

            return movieUpdate;
        }
        else {
            log.error("Movie not found with id : " + movie.getId());
            throw new MovieNotFoundException("Movie not found with id : " + movie.getId());
        }
    }

    @Override
    public void deleteFavouriteMovies(Long id) {
        Optional<Movie> result = this.movieRepository.findById(id);

        if (result.isPresent()) {
            this.movieRepository.delete(result.get());
            log.info("RESPONSE[" + HttpStatus.OK + "]: " + result.toString());
        }
        else {
            throw new MovieNotFoundException("Movie not found with id : " + id);
        }
    }
}
