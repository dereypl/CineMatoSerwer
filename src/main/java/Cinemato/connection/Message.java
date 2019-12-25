package main.java.Cinemato.connection;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable{

    private final String type;
    private ArrayList<String> body;

    public Message(String type, ArrayList<String> body) {
        this.type = type;
        this.body = body;
    }

    public String getType() {
        return type;
    }

    public ArrayList<String> getBody() {
        return body;
    }
}