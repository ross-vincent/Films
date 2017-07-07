package com.scottlv;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rossbv.Film;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FilmsControllerTests {

    @Autowired
    private MockMvc mockMvc;
    
    private int getFilmCount() throws Exception {
    	Type listType = new TypeToken<ArrayList<Film>>(){}.getType();
    	
    	MvcResult result = this.mockMvc.perform(get("/films"))
    			.andReturn();
    	String content = result.getResponse().getContentAsString();
    	List<Film> films = new Gson().fromJson(content, listType);
    	
    	return films.size();
    }

    @Test
    public void getAllFilms() throws Exception {

        this.mockMvc.perform(get("/films"))
                .andExpect(status().isOk());
    }

    @Test
    public void getFilmUsingBadId() throws Exception {

        this.mockMvc.perform(get("/films/myBadId"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getFilmNotFound() throws Exception {

        this.mockMvc.perform(get("/films/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getFilmFound() throws Exception {

        this.mockMvc.perform(get("/films/1")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Star Wars: Episode I - The Phantom Menace"))
                .andExpect(jsonPath("$.year").value("1999"));
    }
    
    @Test
    public void deleteFilmNotFound() throws Exception {

        this.mockMvc.perform(delete("/films/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteFilmFound() throws Exception {

    	
    	int originalSize = getFilmCount();
    	
        this.mockMvc.perform(delete("/films/1")).andDo(print())
                .andExpect(status().isOk());

    	int newSize = getFilmCount();

    	assertEquals(originalSize, newSize + 1);
                
    }

    @Test
    public void addFilm() throws Exception {
    	
    	int originalSize = getFilmCount();
    	Film film = new Film(99, "myFilm", "2017");
    	String jsonContent = new Gson().toJson(film);
    	
    	this.mockMvc.perform(post("/films")
    			.contentType(MediaType.APPLICATION_JSON).content(jsonContent))
        		.andExpect(status().isOk());
    	
    	int newSize = getFilmCount();
    	assertEquals(originalSize + 1, newSize);
    	
    }
}
