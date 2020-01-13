package server;

import java.net.*;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;

import database.QueriesController;
import main.java.Cinemato.connection.Message;

public class ServerController {

    private ServerSocket serverSocket;

    public void start(int port, Statement st) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server is running on port: " + port);

        while (true)
            new ClientHandler(serverSocket.accept(), st).start();
    }

    public void stop() throws IOException {
        serverSocket.close();
    }

    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
        private Statement st;

        public ClientHandler(Socket socket, Statement st) {
            this.clientSocket = socket;
            this.st = st;
        }

        public void run() {
            try {

                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());

                boolean should = false;
                Message request;

                while (!should) {
                    request = (Message) objectInputStream.readObject();

                    switch (request.getType()) {
                        case "terminate": {
                            objectOutputStream.writeObject(new Message("Terminated", new ArrayList<>()));
                            should = true;
                            break;
                        }
                        case "getMovieList": {
                            ArrayList<String> movies = QueriesController.getAllMovies(st);
                            objectOutputStream.writeObject(new Message("moviesList", movies));
                            break;
                        }
                        case "getSeatsList": {
                            System.out.println("getSeatsList");
                            System.out.println(request.getBody().get(0));
                            ArrayList<String> seats = QueriesController.getAllSeats(st, request.getBody().get(0));
                            objectOutputStream.writeObject(new Message("seatsList", seats));
                            break;
                        }
                        case "getMovieScreenings": {
                            System.out.println("getMovieScreenings");
                            System.out.println(request.getBody().get(0));
                            ArrayList<String> screenings = QueriesController.getMovieScreenings(st, request.getBody().get(0));
                            objectOutputStream.writeObject(new Message("MovieScreeningsList", screenings));
                            break;
                        }
                        case "getSeatsReserved": {
                            System.out.println("getSeatsReserved");
                            System.out.println(request.getBody().get(0));
                            ArrayList<String> seatsReserved = QueriesController.getSeatsReserved(st, request.getBody().get(0));
                            objectOutputStream.writeObject(new Message("seatsReserved", seatsReserved));
                            break;
                        }
                        case "reservationRequest": {
                            System.out.println("reservationRequest Handled: movie: "+ request.getBody().get(0) + " screening: " + request.getBody().get(1) + "seat: "+ request.getBody().get(2));
                            Integer screeningId = Integer.parseInt(request.getBody().get(1));
                            Integer seatId = Integer.parseInt(request.getBody().get(2));
                            String firstName = request.getBody().get(3);
                            String LastName = request.getBody().get(4);
                            String Email = request.getBody().get(5);
                            String CardNumber = request.getBody().get(6);
                            String cvv = request.getBody().get(7);
                            String month = request.getBody().get(8);
                            String year = request.getBody().get(9);

                            String response = ReservationController.seatReservation(st, screeningId, seatId, firstName, LastName, Email, CardNumber, cvv, month, year);
                            ArrayList<String> responseForEverySeat = new ArrayList<>();
                            responseForEverySeat.add(response);
                            objectOutputStream.writeObject(new Message(response, responseForEverySeat));
                            break;
                        }
                    }
                }

                System.out.println("Closing sockets.");
                objectInputStream.close();
                objectOutputStream.close();
                clientSocket.close();

            } catch (IOException | ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
