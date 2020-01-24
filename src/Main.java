import server.ServerController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;

import static database.DatabaseController.*;

public class Main {

    public static void main(String[] args) {

        // --- CHECK DRIVER ---
        if (checkDriver("com.mysql.jdbc.Driver"))
            System.out.println(" ... OK");
        else
            System.exit(1);

        // --- CREATE STATEMENT AND CONNECTION ---
        Connection con = getConnection("jdbc:mysql://", "localhost", 3306, "cinemato_admin", "cinematopass");
        Statement st = createStatement(con);

        // --- INITIALIZE DATABASE ---
        dbInit(st);

        // --- RUN SERVER ---
        ServerController server = new ServerController();
        try {
            server.start(8808, st);
            server.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // --- CLOSE CONNECTION ---
        closeConnection(con, st);
    }
}
