package com.iot.mobiledevelopment;


public class Movie {
    private String title;
    private Integer year;
    private String description;
    private String poster;

    public Movie(String title, Integer year, String description, String poster) {
        this.title = title;
        this.year = year;
        this.description = description;
        this.poster = poster;
    }


    public String getPoster() {
        return poster;
    }


    public String getDescription() {
        return description;
    }


    public Integer getYear() {
        return year;
    }


    public String getTitle() {
        return title;
    }
}
