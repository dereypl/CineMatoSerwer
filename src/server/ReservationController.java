package server;

import database.QueriesController;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static database.DatabaseController.executeQuery;

public class ReservationController {

    public static String seatReservation(Statement st, Integer screeningId, Integer seatId) throws SQLException {

        synchronized (seatId) { //TODO: why synchronized on id.. should be on reservation_id
            try {
                if (isSeatAvailable(st, screeningId, seatId)) {
                    int affectedRows = QueriesController.reserveSeat(st, screeningId, seatId);
                    System.out.println(affectedRows);
                    Thread.sleep(10000);
                    if (affectedRows != 0) return "reserved";
                    else return "failed";
                }
                return "seat not available";

            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(exception);
            }
        }
    }

    private static boolean isSeatAvailable(Statement st, Integer screeningId, Integer seatId) throws SQLException {

        String sql = String.format("Select id from reservation where screening_id = '%s' and seat_id = '%s';", screeningId, seatId);
        ResultSet rs = executeQuery(st, sql);
        return !rs.isBeforeFirst();
    }
}
