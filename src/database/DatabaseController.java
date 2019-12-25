package database;


import java.sql.*;
import java.util.Properties;

public class DatabaseController {

    String driver = "com.mysql.jdbc.Driver";
    String url = "jdbc:mysql://hostname:3306/dbname";

    public static boolean checkDriver(String driver) {
        System.out.print("Sprawdzanie sterownika:");
        try {
            Class.forName(driver).newInstance();
            return true;
        } catch (Exception e) {
            System.out.println("Blad przy ladowaniu sterownika bazy!");
            return false;
        }
    }

    public static Connection getConnection(String kindOfDatabase, String adres, int port, String userName, String password) {

        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", userName);
        connectionProps.put("password", password);
        try {
            conn = DriverManager.getConnection(kindOfDatabase + adres + ":" + port + "/",
                    connectionProps);
        } catch (SQLException e) {
            System.out.println("Blad polaczenia z baza danych! " + e.getMessage() + ": " + e.getErrorCode());
            System.exit(2);
        }
        System.out.println("Polaczenie z baza danych: ... OK");
        return conn;
    }


    public static Statement createStatement(Connection connection) {
        try {
            return connection.createStatement();
        } catch (SQLException e) {
            System.out.println("Blad createStatement! " + e.getMessage() + ": " + e.getErrorCode());
            System.exit(3);
        }
        return null;
    }

    public static void closeConnection(Connection connection, Statement s) {
        System.out.print("\nZamykanie polaczenia z baza:");
        try {
            s.close();
            connection.close();
        } catch (SQLException e) {
            System.out
                    .println("Blad przy zamykaniu polaczenia z baza! " + e.getMessage() + ": " + e.getErrorCode());;
            System.exit(4);
        }
        System.out.print("Status zamkniecia:  OK");
    }

    public static ResultSet executeQuery(Statement s, String sql) {
        try {
            return s.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Zapytanie nie wykonane! " + e.getMessage() + ": " + e.getErrorCode());
        }
        return null;
    }
    public static int executeUpdate(Statement s, String sql) {
        try {
            return s.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Zapytanie nie wykonane! " + e.getMessage() + ": " + e.getErrorCode());
        }
        return -1;
    }

    public static void printDataFromQuery(ResultSet r) {
        ResultSetMetaData rsmd;
        try {
            rsmd = r.getMetaData();
            int numcols = rsmd.getColumnCount(); // pobieranie liczby kolumn
            // wyswietlanie nazw kolumn:
            for (int i = 1; i <= numcols; i++) {
                System.out.print("\t" + rsmd.getColumnLabel(i) + "\t|");
            }
            System.out
                    .print("\n____________________________________________________________________________\n");
            /**
             * r.next() - przej�cie do kolejnego rekordu (wiersza) otrzymanych wynik�w
             */
            // wyswietlanie kolejnych rekordow:
            while (r.next()) {
                for (int i = 1; i <= numcols; i++) {
                    Object obj = r.getObject(i);
                    if (obj != null)
                        System.out.print("\t" + obj.toString() + "\t|");
                    else
                        System.out.print("\t");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("Bl�d odczytu z bazy! " + e.getMessage() + ": " + e.getErrorCode());
        }
    }



}
