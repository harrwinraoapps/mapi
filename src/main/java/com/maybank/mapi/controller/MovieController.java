package com.maybank.mapi.controller;

import com.maybank.mapi.exception.MovieNotFoundException;
import com.maybank.mapi.model.Movie;
import com.maybank.mapi.model.MovieList;
import com.maybank.mapi.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/omdb")
@RestController
public class MovieController {

    @Value("${omdb.api.key}")
    private String apiKey;

    @Value("${omdb.api.url}")
    private String apiUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MovieService movieService;

    Logger log = LoggerFactory.getLogger(MovieController.class);

    @GetMapping("/movies/search/{searchString}")
    public ResponseEntity<List<Movie>> getMoviesBySearch(@PathVariable String searchString) {
        log.info("Find movies by search string");
        log.info("REQUEST: " + "http://localhost:8080/api/omdb/movies/search/" + searchString);
        String url = apiUrl + "?s=" + searchString + "&apikey=" + apiKey;
        log.info("EXTERNAL REQUEST: " + url);
        MovieList response = restTemplate.getForObject(url, MovieList.class);
        List<Movie> result = new ArrayList<>();
        if (response != null ) {
            result = response.getMovieList();
        }
        else {
            log.error("Movie not found for search string : " + searchString);
            throw new MovieNotFoundException("Movie not found for search string : " + searchString);
        }
        log.info("RESPONSE[" + HttpStatus.OK + "]: " + result.toString());
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable String id) {
        log.info("REQUEST: " + "http://localhost:8080/api/omdb/movies/" + id);
        String url = apiUrl + "?i=" + id + "&apikey=" + apiKey;
        log.info("EXTERNAL API REQUEST: " + url);
        Movie result = restTemplate.getForObject(url, Movie.class);
        if (result != null) {
            log.info("RESPONSE[" + HttpStatus.OK + "]: " + result.toString());
        }
        else {
            log.error("Movie not found for imdbId : " + id);
            throw new MovieNotFoundException("Movie not found for imdbId : " + id);
        }
        return ResponseEntity.ok().body(result);
    }
}
