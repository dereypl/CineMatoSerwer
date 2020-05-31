package database;

import models.Movie;
import models.Screening;
import models.Seat;

import static database.DatabaseController.executeQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class QueriesController {

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
                movie.setPosterLink(rs.getString("poster_link"));

                movies.add(movie.toString());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return movies;
    }

    public static ArrayList<String> getMovieScreenings(Statement st, String movieId) {
        ArrayList<String> screenings = new ArrayList<>();
        ResultSet rs = executeQuery(st, "select * from screening where movie_id =" + movieId + ";");
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

    public static ArrayList<String> getSeatsReserved(Statement st, String screeningId) {
        ArrayList<String> seatsReserved = new ArrayList<>();
        ResultSet rs = executeQuery(st, "select seat_id from reservation where screening_id = " + screeningId + ";");
        while (true) {
            try {
                if (!rs.next()) break;

                Seat seat = new Seat();
                seat.setId(rs.getInt("seat_id"));

                seatsReserved.add(Integer.toString(seat.getId()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return seatsReserved;
    }

    public static ArrayList<String> getAllSeats(Statement st, String screeningId) {
        ArrayList<String> seats = new ArrayList<>();
        ResultSet rs = executeQuery(st, "Select * from seat where screening_id = " + screeningId + ";");
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

    public static int reserveSeat(Statement st, Integer screeningId, Integer seatId, String firstName, String surname, String email,String cardNumber,String cardCvv, String cardExpMonth, String cardExpYear) {

        String sql = String.format("INSERT INTO reservation " +
                "(screening_id, seat_id, customer_name, customer_surname, customer_email, customer_card_cvv, customer_card_number, customer_card_exp_month, customer_card_exp_year, status)" +
                " VALUES" + "('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', 'confirmed');",
                screeningId, seatId, firstName, surname, email,cardNumber,cardCvv, cardExpMonth, cardExpYear);
        try {
            return st.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
