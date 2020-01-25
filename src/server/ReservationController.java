package server;

import database.QueriesController;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static database.DatabaseController.executeQuery;

public class ReservationController {

    public static String seatReservation(Statement st, Integer screeningId, Integer seatId, String firstName, String surname, String email,String cardNumber ,String cardCvv, String cardExpMonth, String cardExpYear) throws SQLException {

        synchronized (seatId) {

            System.out.println(seatId + " - try to reserve seat!");

            try {
                if (isSeatAvailable(st, screeningId, seatId)) {
                    int affectedRows = QueriesController.reserveSeat(st, screeningId, seatId,firstName, surname, email,cardNumber,cardCvv, cardExpMonth, cardExpYear);
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
