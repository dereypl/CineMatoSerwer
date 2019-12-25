package database;

import models.Movie;

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
}
