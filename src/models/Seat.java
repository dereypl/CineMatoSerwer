package models;

public class Seat {

    private int Id;
    private String Row;
    private int Number;
    private int Screening_id;

    public int getScreening_id() {
        return Screening_id;
    }

    public void setScreening_id(int screening_id) {
        Screening_id = screening_id;
    }

    public int getReservation_id() {
        return Reservation_id;
    }

    public void setReservation_id(int reservation_id) {
        Reservation_id = reservation_id;
    }

    private int Reservation_id;

//    public Seat(int id, String row, int number) {
//        Id = id;
//        Row = row;
//        Number = number;
//    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getRow() {
        return Row;
    }

    public void setRow(String row) {
        Row = row;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int number) {
        Number = number;
    }

    public String toString() {
        return Id + "&" + Row + "&" + Number;
    }
}
