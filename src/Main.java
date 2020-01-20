import server.ServerController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

        // --- DROP DATABASE---
        try {
            st.executeUpdate("DROP TABLE IF EXISTS movie");
            st.executeUpdate("DROP TABLE IF EXISTS reservation");
            st.executeUpdate("DROP TABLE IF EXISTS screening");
            st.executeUpdate("DROP TABLE IF EXISTS seat");
        } catch (SQLException e) {
            e.printStackTrace();
        }


        // --- CREATE DATABASE---

        String createMovieTable = "CREATE TABLE movie (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "title VARCHAR(255) NOT NULL, " +
                "director VARCHAR(128) NOT NULL, " +
                "genre VARCHAR(1024) DEFAULT NULL, " +
                "description VARCHAR(1024) DEFAULT NULL, " +
                "duration_min int(11) DEFAULT NULL, " +
                "year VARCHAR(10) DEFAULT NULL, " +
                "rating VARCHAR(10) DEFAULT NULL," +
                "poster_link VARCHAR(255) DEFAULT NULL);";


        String fillMovieTable = "INSERT INTO `movie` (`id`, `title`, `director`, `genre`, `description`, `duration_min`, `year`, `rating`, `poster_link`) VALUES" +
                "(1, 'Dunkierka', 'Christopher Nolan', 'Dramat, Wojenny', 'Wojska alianckie zostają przyparte do morza pod Dunkierką. Bitwa staje się sprawdzianem dla młodych żołnierzy, pilota RAF-u oraz załogi cywilnej łodzi płynącej przez Kanał La Manche.', 106, '2017', '7.3', 'src/main/java/Cinemato/resources/assets/posters/dunkierka.png')," +
                "(2, 'Skazani na Shawshank', 'Frank Darabont', 'Dramat', 'Adaptacja opowiadania Stephena Kinga. Niesłusznie skazany na dożywocie bankier, stara się przetrwać w brutalnym, więziennym świecie.', 142, '1994', '8,8', 'src/main/java/Cinemato/resources/assets/posters/shawshank.png')," +
                "(3, 'Ojciec chrzestny', 'Francis Ford Coppola', 'Dramat,Gangsterski', 'Opowieść o nowojorskiej rodzinie mafijnej. Starzejący się Don Corleone pragnie przekazać władzę swojemu synowi.', 175, '1972', '8,6', 'src/main/java/Cinemato/resources/assets/posters/godfather.png')," +
                "(4, 'Kac Vegas', 'Todd Phillips', 'Komedia', 'Czterech przyjaciół spędza wieczór kawalerski w Las Vegas. Następnego dnia okazuje się, że zgubili pana młodego i nic nie pamiętają.', 100, '2009', '7,3', 'src/main/java/Cinemato/resources/assets/posters/kacvegas.png')," +
                "(5, 'Incepcja', 'Christopher Nolan', 'Surrealistyczny,Thriller,Sci-Fi', 'Czasy, gdy technologia pozwala na wchodzenie w świat snów. Złodziej Cobb ma za zadanie wszczepić myśl do śpiącego umysłu.', 148, '2010', '8,3', 'src/main/java/Cinemato/resources/assets/posters/incepcja.png');";


        String createReservationTable = "CREATE TABLE `reservation` (" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                "  `screening_id` int(11) NOT NULL," +
                "  `seat_id` int(11) NOT NULL," +
                "  `customer_name` varchar(100) COLLATE utf16_polish_ci DEFAULT NULL," +
                "  `customer_surname` varchar(100) COLLATE utf16_polish_ci DEFAULT NULL," +
                "  `customer_email` varchar(100) COLLATE utf16_polish_ci DEFAULT NULL," +
                "  `customer_card_cvv` varchar(100) COLLATE utf16_polish_ci DEFAULT NULL," +
                "  `customer_card_number` varchar(100) COLLATE utf16_polish_ci DEFAULT NULL," +
                "  `customer_card_exp_month` varchar(100) COLLATE utf16_polish_ci DEFAULT NULL," +
                "  `customer_card_exp_year` varchar(100) COLLATE utf16_polish_ci DEFAULT NULL," +
                "  `status` varchar(100) COLLATE utf16_polish_ci NOT NULL) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_polish_ci;";


        String fillReservationTable = "INSERT INTO `reservation` (`id`, `screening_id`, `seat_id`, `customer_name`, `customer_surname`, `customer_email`, `customer_card_cvv`, `customer_card_number`, `customer_card_exp_month`, `customer_card_exp_year`, `status`) VALUES" +
                "(1, 4, 20, 'Andrzej', 'Piaseczny', 'piasek@betorniara.ll', '098', '4929327295477365', '03', '2006', 'confirmed')," +
                "(2, 7, 38, 'Dariusz', 'Tyszkiewicz', 'dareczektyszeczek@tyszeczek.rr', '0943', '4532200725166024', '05', '2023', 'confirmed')," +
                "(3, 6, 31, 'Monika', 'Konika', 'monikakonika@ihha.oo', '841', '4416908690999549', '12', '2030', 'confirmed')," +
                "(4, 6, 32, 'Weronika', 'Grzyb', 'grzyb302@test.qq', '998', '4296438162130279', '08', '2021', 'confirmed')," +
                "(5, 6, 33, 'Justyna', 'Kubica', 'kubi@just.op', '991', '4485545147706071', '01', '2024', 'confirmed')," +
                "(6, 6, 34, 'Weronika', 'Grzyb', 'grzyb302@test.qq', '998', '4296438162130279', '08', '2021', 'confirmed')," +
                "(7, 4, 24, 'Andrzej', 'Piaseczny', 'piasek@betorniara.ll', '098', '4929327295477365', '03', '2006', 'confirmed')," +
                "(8, 1, 2, 'Andrzej', 'Piaseczny', 'piasek@betorniara.ll', '098', '4929327295477365', '03', '2006', 'confirmed');";

        String createScreeningTable = "CREATE TABLE `screening` (" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                "  `movie_id` int(11) NOT NULL," +
                "  `date` date NOT NULL," +
                "  `start_time` time NOT NULL" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_polish_ci;";

        String fillScreeningTable = "INSERT INTO `screening` (`id`, `movie_id`, `date`, `start_time`) VALUES" +
                "(1, 1, '2020-02-04', '18:00:00')," +
                "(2, 3, '2020-02-04', '20:00:00')," +
                "(3, 4, '2020-02-05', '16:00:00')," +
                "(4, 2, '2020-02-05', '18:00:00')," +
                "(5, 5, '2020-02-05', '21:00:00')," +
                "(6, 1, '2020-02-06', '19:00:00')," +
                "(7, 5, '2020-02-07', '22:00:00')," +
                "(8, 4, '2020-02-08', '17:00:00')," +
                "(9, 4, '2020-02-08', '22:00:00')," +
                "(10, 3, '2019-12-08', '19:00:00')," +
                "(11, 2, '2020-02-14', '22:00:00');";

        String createSeatTable = "CREATE TABLE `seat` (" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                "  `row` varchar(2) COLLATE utf16_polish_ci NOT NULL," +
                "  `number` int(11) NOT NULL," +
                "  `screening_id` int(11) NOT NULL" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_polish_ci;";

        String fillSeatTable = "INSERT INTO `seat` (`id`, `row`, `number`, `screening_id`) VALUES\n" +
                "(1, 'A', 1, 1)," +
                "(2, 'A', 2, 1)," +
                "(3, 'A', 3, 1)," +
                "(4, 'A', 4, 1)," +
                "(5, 'A', 5, 1)," +
                "(6, 'A', 6, 1)," +
                "(7, 'A', 1, 2)," +
                "(8, 'A', 2, 2)," +
                "(9, 'A', 3, 2)," +
                "(10, 'A', 4, 2)," +
                "(11, 'A', 5, 2)," +
                "(12, 'A', 6, 2)," +
                "(13, 'A', 1, 3)," +
                "(14, 'A', 2, 3)," +
                "(15, 'A', 3, 3)," +
                "(16, 'A', 4, 3)," +
                "(17, 'A', 5, 3)," +
                "(18, 'A', 6, 3)," +
                "(19, 'A', 1, 4)," +
                "(20, 'A', 2, 4)," +
                "(21, 'A', 3, 4)," +
                "(22, 'A', 4, 4)," +
                "(23, 'A', 5, 4)," +
                "(24, 'A', 6, 4)," +
                "(25, 'A', 1, 5)," +
                "(26, 'A', 2, 5)," +
                "(27, 'A', 3, 5)," +
                "(28, 'A', 4, 5)," +
                "(29, 'A', 5, 5)," +
                "(30, 'A', 6, 5)," +
                "(31, 'A', 1, 6)," +
                "(32, 'A', 2, 6)," +
                "(33, 'A', 3, 6)," +
                "(34, 'A', 4, 6)," +
                "(35, 'A', 5, 6)," +
                "(36, 'A', 6, 6)," +
                "(37, 'A', 1, 7)," +
                "(38, 'A', 2, 7)," +
                "(39, 'A', 3, 7)," +
                "(40, 'A', 4, 7)," +
                "(41, 'A', 5, 7)," +
                "(42, 'A', 6, 7)," +
                "(43, 'A', 1, 8)," +
                "(44, 'A', 2, 8)," +
                "(45, 'A', 3, 8)," +
                "(46, 'A', 4, 8)," +
                "(47, 'A', 5, 8)," +
                "(48, 'A', 6, 8)," +
                "(49, 'A', 1, 9)," +
                "(50, 'A', 2, 9)," +
                "(51, 'A', 3, 9)," +
                "(52, 'A', 4, 9)," +
                "(53, 'A', 5, 9)," +
                "(54, 'A', 6, 9)," +
                "(55, 'A', 1, 10)," +
                "(56, 'A', 2, 10)," +
                "(57, 'A', 3, 10)," +
                "(58, 'A', 4, 10)," +
                "(59, 'A', 5, 10)," +
                "(60, 'A', 6, 10)," +
                "(61, 'A', 1, 11)," +
                "(62, 'A', 2, 11)," +
                "(63, 'A', 3, 11)," +
                "(64, 'A', 4, 11)," +
                "(65, 'A', 5, 11)," +
                "(66, 'A', 6, 11);";


        // --- ADD CONSTRAINTS --

        String reservationConstraint = "ALTER TABLE `reservation`" +
                "  ADD CONSTRAINT `reservation_ibfk_1` FOREIGN KEY (`screening_id`) REFERENCES `screening` (`id`)," +
                "  ADD CONSTRAINT `reservation_ibfk_2` FOREIGN KEY (`seat_id`) REFERENCES `seat` (`id`);";

        String reservationConstraintKey = "ALTER TABLE `reservation`" +
                "  ADD KEY `screening_id` (`screening_id`)," +
                "  ADD KEY `seat_id` (`seat_id`);";

        String screeningConstraint = "ALTER TABLE `screening`" +
                "  ADD CONSTRAINT `screening_ibfk_1` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`id`);";

        String screeningConstraintKey = "ALTER TABLE `screening`" +
                "  ADD KEY `movie_id` (`movie_id`);";

        String seatConstraint = "ALTER TABLE `seat`" +
                "  ADD CONSTRAINT `seat_ibfk_1` FOREIGN KEY (`screening_id`) REFERENCES `screening` (`id`);";

        String seatConstraintKey = "ALTER TABLE `seat`" +
                "  ADD KEY `screening_id` (`screening_id`);";

        try {
            st.executeUpdate(createMovieTable);
            st.executeUpdate(fillMovieTable);
            st.executeUpdate(createReservationTable);
            st.executeUpdate(fillReservationTable);
            st.executeUpdate(createScreeningTable);
            st.executeUpdate(screeningConstraintKey);
            st.executeUpdate(fillScreeningTable);
            st.executeUpdate(createSeatTable);
            st.executeUpdate(seatConstraintKey);
            st.executeUpdate(fillSeatTable);
            //
            st.executeUpdate(reservationConstraint);
            st.executeUpdate(reservationConstraintKey);
            st.executeUpdate(screeningConstraint);
            st.executeUpdate(seatConstraint);

        } catch (SQLException e) {
            e.printStackTrace();
        }

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

    private static void dropDatabase() {
    }
}
