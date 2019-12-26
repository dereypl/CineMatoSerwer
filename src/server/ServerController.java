package server;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.sql.Statement;

import database.QueriesCntroller;
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
                            ArrayList<String> movies = QueriesCntroller.getAllMovies(st);
                            objectOutputStream.writeObject(new Message("moviesList", movies));
                            break;
                        }
                        case "getSeatsList": {
                            ArrayList<String> seats = QueriesCntroller.getAllSeats(st);
                            objectOutputStream.writeObject(new Message("seatsList", seats));
                            break;
                        }
                        case "getMovieScreenings": {
                            System.out.println("getMovieScreenings");
                            System.out.println(request.getBody().get(0));
                            ArrayList<String> screenings = QueriesCntroller.getMovieScreenings(st,request.getBody().get(0));
                            objectOutputStream.writeObject(new Message("MovieScreeningsList", screenings));
                            break;
                        }
                        case "getSeatsReserved": {
                            System.out.println("getSeatsReserved");
                            System.out.println(request.getBody().get(0));
                            ArrayList<String> seatsReserved = QueriesCntroller.getSeatsReserved(st,request.getBody().get(0));
                            objectOutputStream.writeObject(new Message("seatsReserved", seatsReserved));
                            break;
                        }
                    }
                }

                System.out.println("Closing sockets.");
                objectInputStream.close();
                objectOutputStream.close();
                clientSocket.close();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
