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


        // --- CHOOSE DATABASE ---
        if (executeUpdate(st, "USE cinemato;") == 0)
            System.out.println("Baza wybrana");
        else {
            System.out.println("Baza nie istnieje! Tworze baze: ");
            if (executeUpdate(st, "create Database cinemato;") == 1)
                System.out.println("Baza utworzona");
            else
                System.out.println("Baza nieutworzona!");
            if (executeUpdate(st, "USE cinemato;") == 0)
                System.out.println("Baza wybrana");
            else
                System.out.println("Baza niewybrana!");
        }

        // --- CREATE TABLE ... ---
        //TODO: make init data and drop database if exists
//        if (executeUpdate(st,
//                "CREATE TABLE movie ( id INT NOT NULL, tytul VARCHAR(50) NOT NULL, autor INT NOT NULL, PRIMARY KEY (id) );") == 0)
//            System.out.println("Tabela utworzona");
//        else
//            System.out.println("Tabela nie utworzona!");
//        String sql = "INSERT INTO ksiazki_ VALUES(2, 'Pan Tadeusz', 1);";

//        executeUpdate(st, sql);
//        String sql = "Select * from Movie;";
//        printDataFromQuery(executeQuery(st, sql));


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
