/**
 * Book My Stay Application - Use Case 10
 * 
 * This class demonstrates booking cancellation and rollback using Stack.
 * It ensures safe reversal of state changes and maintains inventory consistency.
 * 
 * @author SAI SUJAN
 * @version 10.0
 */

import java.util.*;

// -------------------- RESERVATION --------------------
class Reservation {
    private String reservationId;
    private String roomType;
    private String roomId;

    public Reservation(String reservationId, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }
}

// -------------------- INVENTORY --------------------
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 0);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public void increment(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }

    public void displayInventory() {
        System.out.println("\n--- Current Inventory ---");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// -------------------- BOOKING HISTORY --------------------
class BookingHistory {

    private Map<String, Reservation> confirmedBookings = new HashMap<>();

    public void addReservation(Reservation r) {
        confirmedBookings.put(r.getReservationId(), r);
    }

    public Reservation getReservation(String id) {
        return confirmedBookings.get(id);
    }

    public void removeReservation(String id) {
        confirmedBookings.remove(id);
    }

    public boolean exists(String id) {
        return confirmedBookings.containsKey(id);
    }
}

// -------------------- CANCELLATION SERVICE --------------------
class CancellationService {

    // Stack to track released room IDs (LIFO rollback)
    private Stack<String> rollbackStack = new Stack<>();

    public void cancelBooking(String reservationId,
                              BookingHistory history,
                              RoomInventory inventory) {

        System.out.println("\nProcessing cancellation for: " + reservationId);

        // Validation
        if (!history.exists(reservationId)) {
            System.out.println("Cancellation Failed: Reservation not found.");
            return;
        }

        // Fetch reservation
        Reservation r = history.getReservation(reservationId);

        // Push to rollback stack
        rollbackStack.push(r.getRoomId());

        // Restore inventory
        inventory.increment(r.getRoomType());

        // Remove from history
        history.removeReservation(reservationId);

        // Confirmation
        System.out.println("Cancellation Successful!");
        System.out.println("Released Room ID: " + r.getRoomId());
    }

    public void displayRollbackStack() {
        System.out.println("\n--- Rollback Stack (Recent Releases) ---");
        for (String id : rollbackStack) {
            System.out.println(id);
        }
    }
}

// -------------------- MAIN CLASS --------------------
public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        System.out.println("===================================");
        System.out.println(" Book My Stay - Cancellation ");
        System.out.println(" Version 10.0 ");
        System.out.println("===================================");

        // Setup inventory
        RoomInventory inventory = new RoomInventory();

        // Setup booking history
        BookingHistory history = new BookingHistory();

        // Simulate confirmed bookings
        history.addReservation(new Reservation("R101", "Single Room", "SI1"));
        history.addReservation(new Reservation("R102", "Suite Room", "SU2"));

        // Cancellation service
        CancellationService service = new CancellationService();

        // Perform cancellations
        service.cancelBooking("R101", history, inventory); // valid
        service.cancelBooking("R999", history, inventory); // invalid

        // Display rollback stack
        service.displayRollbackStack();

        // Show updated inventory
        inventory.displayInventory();

        System.out.println("\nSystem state restored successfully!");
    }
}
