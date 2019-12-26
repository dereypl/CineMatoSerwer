package database;

import models.Movie;
import models.Screening;
import models.Seat;

import static database.DatabaseController.executeQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QueriesCntroller {

    //TODO: refactor as singleton ??

    public static ArrayList<String> getAllMovies(Statement st) {
        ArrayList<String> movies = new ArrayList<>();
        ResultSet rs = executeQuery(st, "Select * from Movie;");
        while (true) {
            try {
                if (!rs.next()) break;

                Movie movie = new Movie();
                movie.setId(rs.getInt("id"));
                movie.setTitle(rs.getString("title"));
                movie.setDirector(rs.getString("director"));
                movie.setGenre(rs.getString("genre"));
                movie.setDescription(rs.getString("description"));
                movie.setDuration_min(rs.getInt("duration_min"));
                movie.setYear(rs.getString("year"));
                movie.setRating(rs.getString("rating"));

                movies.add(movie.toString());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return movies;
    }

    public static ArrayList<String> getMovieScreenings(Statement st,String movieId) {
        ArrayList<String> screenings = new ArrayList<>();
        ResultSet rs = executeQuery(st, "select * from screening where movie_id ="+movieId+";");
        while (true) {
            try {
                if (!rs.next()) break;

                Screening screening = new Screening();
                screening.setId(rs.getInt("id"));
                screening.setMovieId(rs.getInt("movie_id"));
                screening.setDate(rs.getDate("date"));
                screening.setStartTime(rs.getTime("start_time"));

                screenings.add(screening.toString());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return screenings;
    }

    public static ArrayList<String> getAllSeats(Statement st) {
        ArrayList<String> seats = new ArrayList<>();
        ResultSet rs = executeQuery(st, "Select * from seat;");
        while (true) {
            try {
                if (!rs.next()) break;

                Seat seat = new Seat();
                seat.setId(rs.getInt("id"));
                seat.setRow(rs.getString("row"));
                seat.setNumber(rs.getInt("number"));
                seats.add(seat.toString());

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return seats;
    }
}
