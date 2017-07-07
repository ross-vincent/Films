package com.rossbv;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rossbv.exception.BadRequestException;
import com.rossbv.exception.NotFoundException;

@RestController
public class FilmsController {

    // Create a logger so we can write debug messages to the console
    private Logger log = LoggerFactory.getLogger(FilmsController.class);

    // Create a global film list (temporary, until we start using a proper database)
    public static List<Film> films = new ArrayList<Film>();

    public FilmsController() {
        // Add some films at startup
        films.add(new Film(1, "Star Wars: Episode I - The Phantom Menace", "1999"));
        films.add(new Film(2, "Star Wars: Episode II - Attack of the Clones", "2002"));
        films.add(new Film(3, "Star Wars: Episode III - Revenge of the Sith", "2005"));
        films.add(new Film(4, "Star Wars: Episode IV - A New Hope", "1977"));
        films.add(new Film(5, "Star Wars: Episode V - The Empire Strikes Back", "1980"));
        films.add(new Film(6, "Star Wars: Episode VI - Return of the Jedi", "1983"));
    }


    /**
     * GET /films
     * 
     * Returns: The list of films
     */
    @RequestMapping(method = RequestMethod.GET, value = "/films")
    public List<Film> getFilms()
    {
        log.info("GET /films");

        log.info("Film count: " + films.size());
        return films;
    }

    
    /**
     * GET /films/{id}
     * 
     * Returns: The details of a specific film
     */
    @RequestMapping(method = RequestMethod.GET, value = "/films/{id}")
    public Film getFilm(@PathVariable String id)
    {
        final String func = "getFilm";
        
        log.info("GET /films/" + id);

        // Convert id to a long and catch any error
        long filmId;
        try {
            filmId = Long.parseLong(id);
        }
        catch (NumberFormatException e) {
            String errMsg = "Film id must be numeric";
            log.error("{} failed: {}", func, errMsg + ": " + id);
            throw new BadRequestException(errMsg);
        }
    
        log.info("Film count: " + films.size());
        
        for (Film film : films) {
            if (film.getId() == filmId) {
                return film;
            }
        }
        
        // Film with specified id not found
        String errMsg = "Could not find film with id: " + id;
        log.error("{} failed: {}", func, errMsg);
        throw new NotFoundException(errMsg);
    }
    
    /**
     * POST /films
     * 
     * @param film
     * Returns the added film with the next free id
     */
    @RequestMapping(method = RequestMethod.POST, value = "/films")
    public Film addFilm(@RequestBody Film film) {
    	log.info("POST /films");
    	
    	long filmId = 0;
    	for (Film listFilm : films) {
            if (listFilm.getId() > filmId) {
                filmId = listFilm.getId();
            }
        }
    	filmId++;

    	film.setId(filmId);
    	films.add(film);
    	return film;
    }
    
    /**
     * DELETE /films/{id}
     * 
     * @param id
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/films/{id}")
    public void deleteFilm(@PathVariable String id) {
    	final String func = "deleteFilm";
    	log.info("DELETE /films/" + id);
    	
    	// Convert id to a long and catch any error
    	long filmId;
        try {
            filmId = Long.parseLong(id);
        }
        catch (NumberFormatException e) {
            String errMsg = "Film id must be numeric";
            log.error("{} failed: {}", func, errMsg + ": " + id);
            throw new BadRequestException(errMsg);
        }

    	for (Film film : films) {
            if (film.getId() == filmId) {
            	films.remove(film);
                return;
            }
        }
    	
    	// Film with specified id not found
        String errMsg = "Could not find film with id: " + id;
        log.error("{} failed: {}", func, errMsg);
        throw new NotFoundException(errMsg);
    }
}
