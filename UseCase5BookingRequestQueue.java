 /**
 * Book My Stay Application - Use Case 5
 * 
 * This class demonstrates booking request handling using Queue (FIFO).
 * It ensures fair processing of requests without modifying inventory.
 * 
 * @author SAI SUJAN
 * @version 5.0
 */

import java.util.*;

// -------------------- RESERVATION --------------------
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println("Guest: " + guestName + " | Room Type: " + roomType);
    }
}

// -------------------- BOOKING QUEUE --------------------
class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    // Add request (enqueue)
    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Request added for " + reservation.getGuestName());
    }

    // View all requests (without removing)
    public void displayQueue() {
        System.out.println("\n--- Booking Request Queue (FIFO) ---");

        if (queue.isEmpty()) {
            System.out.println("No pending requests.");
            return;
        }

        for (Reservation r : queue) {
            r.display();
        }
    }

    // Get next request (for future processing)
    public Reservation getNextRequest() {
        return queue.peek(); // does not remove
    }
}

// -------------------- MAIN CLASS --------------------
public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        System.out.println("===================================");
        System.out.println(" Book My Stay - Booking Queue ");
        System.out.println(" Version 5.0 ");
        System.out.println("===================================");

        // Initialize queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Simulate guest booking requests
        bookingQueue.addRequest(new Reservation("Sai", "Single Room"));
        bookingQueue.addRequest(new Reservation("Rahul", "Suite Room"));
        bookingQueue.addRequest(new Reservation("Anita", "Double Room"));

        // Display queue (FIFO order)
        bookingQueue.displayQueue();

        // Peek next request (without removing)
        System.out.println("\nNext request to process:");
        Reservation next = bookingQueue.getNextRequest();
        if (next != null) {
            next.display();
        }

        System.out.println("\nRequests queued successfully (no allocation yet).");
    }
}
