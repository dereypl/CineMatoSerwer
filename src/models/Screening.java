package models;

import java.sql.Time;
import java.sql.Date;

public class Screening {

    private int Id;
    private int MovieId;
    private Date Date;
    private Time StartTime;

//    public Screening(int id, int movieId, java.sql.Date date, Time startTime) {
//        Id = id;
//        MovieId = movieId;
//        Date = date;
//        StartTime = startTime;
//    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getMovieId() {
        return MovieId;
    }

    public void setMovieId(int movieId) {
        MovieId = movieId;
    }

    public java.sql.Date getDate() {
        return Date;
    }

    public void setDate(java.sql.Date date) {
        Date = date;
    }

    public Time getStartTime() {
        return StartTime;
    }

    public void setStartTime(Time startTime) {
        StartTime = startTime;
    }

    public String toString() {
        return Id + "&" + MovieId + "&" + Date + "&" + StartTime;
    }
}
