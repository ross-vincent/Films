package com.rossbv;

public class Film {
    private long id;
    private String title;
    private String year;
    
    public Film() {}
    
    public Film(long id, String title, String year) {
        this.id = id;
        this.title = title;
        setYear(year);
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
    	this.id = id;
    }

    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
    	this.title = title;
    }
    
    public String getYear() {
        return year;
    }
    
    public void setYear(String year) {
    	if(year.isEmpty()) {
        	this.year = "Unknown";
        }
        else {
        	this.year = year;
        }
    }
}
