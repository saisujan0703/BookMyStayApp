/**
 * Book My Stay Application - Use Case 8
 * 
 * This class demonstrates booking history tracking and reporting.
 * It maintains an ordered list of confirmed reservations and allows
 * reporting without modifying stored data.
 * 
 * @author SAI SUJAN
 * @version 8.0
 */

import java.util.*;

// -------------------- RESERVATION --------------------
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println("Reservation ID: " + reservationId +
                " | Guest: " + guestName +
                " | Room: " + roomType);
    }
}

// -------------------- BOOKING HISTORY --------------------
class BookingHistory {

    // List to store confirmed bookings (ordered)
    private List<Reservation> history = new ArrayList<>();

    // Add confirmed reservation
    public void addReservation(Reservation r) {
        history.add(r);
    }

    // Retrieve all reservations
    public List<Reservation> getAllReservations() {
        return history;
    }
}

// -------------------- REPORT SERVICE --------------------
class BookingReportService {

    // Display all bookings
    public void displayAllBookings(List<Reservation> history) {
        System.out.println("\n--- Booking History ---");

        if (history.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Reservation r : history) {
            r.display();
        }
    }

    // Generate summary report
    public void generateSummary(List<Reservation> history) {

        System.out.println("\n--- Booking Summary Report ---");

        Map<String, Integer> countByRoom = new HashMap<>();

        for (Reservation r : history) {
            String type = r.getRoomType();
            countByRoom.put(type, countByRoom.getOrDefault(type, 0) + 1);
        }

        for (Map.Entry<String, Integer> entry : countByRoom.entrySet()) {
            System.out.println(entry.getKey() + " bookings: " + entry.getValue());
        }

        System.out.println("Total Bookings: " + history.size());
    }
}

// -------------------- MAIN CLASS --------------------
public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        System.out.println("===================================");
        System.out.println(" Book My Stay - Booking History ");
        System.out.println(" Version 8.0 ");
        System.out.println("===================================");

        // Initialize history
        BookingHistory history = new BookingHistory();

        // Simulate confirmed bookings
        history.addReservation(new Reservation("SI101", "Sai", "Single Room"));
        history.addReservation(new Reservation("SU102", "Rahul", "Suite Room"));
        history.addReservation(new Reservation("DO103", "Anita", "Double Room"));
        history.addReservation(new Reservation("SI104", "Kiran", "Single Room"));

        // Reporting service
        BookingReportService reportService = new BookingReportService();

        // Display all bookings
        reportService.displayAllBookings(history.getAllReservations());

        // Generate summary
        reportService.generateSummary(history.getAllReservations());

        System.out.println("\nReporting completed successfully!");
    }
}
