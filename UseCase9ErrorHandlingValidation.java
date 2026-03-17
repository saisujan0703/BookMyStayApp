/**
 * Book My Stay Application - Use Case 9
 * 
 * This class demonstrates validation and error handling using
 * custom exceptions and fail-fast design.
 * 
 * It ensures invalid inputs are caught early and system state remains safe.
 * 
 * @author SAI SUJAN
 * @version 9.0
 */

import java.util.*;

// -------------------- CUSTOM EXCEPTION --------------------
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

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
}

// -------------------- INVENTORY --------------------
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0); // no availability
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, -1);
    }

    public void decrement(String roomType) throws InvalidBookingException {
        int count = inventory.getOrDefault(roomType, -1);

        if (count <= 0) {
            throw new InvalidBookingException("No rooms available for " + roomType);
        }

        inventory.put(roomType, count - 1);
    }

    public boolean isValidRoomType(String roomType) {
        return inventory.containsKey(roomType);
    }
}

// -------------------- VALIDATOR --------------------
class BookingValidator {

    public static void validate(Reservation r, RoomInventory inventory)
            throws InvalidBookingException {

        // Validate null/empty input
        if (r.getGuestName() == null || r.getGuestName().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        if (r.getRoomType() == null || r.getRoomType().isEmpty()) {
            throw new InvalidBookingException("Room type cannot be empty.");
        }

        // Validate room type
        if (!inventory.isValidRoomType(r.getRoomType())) {
            throw new InvalidBookingException("Invalid room type: " + r.getRoomType());
        }

        // Validate availability
        if (inventory.getAvailability(r.getRoomType()) <= 0) {
            throw new InvalidBookingException("Room not available: " + r.getRoomType());
        }
    }
}

// -------------------- BOOKING SERVICE --------------------
class BookingService {

    public void confirmBooking(Reservation r, RoomInventory inventory) {
        try {
            // Validate first (Fail-Fast)
            BookingValidator.validate(r, inventory);

            // Safe allocation
            inventory.decrement(r.getRoomType());

            System.out.println("Booking Confirmed for " + r.getGuestName()
                    + " | Room: " + r.getRoomType());

        } catch (InvalidBookingException e) {
            // Graceful failure handling
            System.out.println("Booking Failed: " + e.getMessage());
        }
    }
}

// -------------------- MAIN CLASS --------------------
public class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        System.out.println("===================================");
        System.out.println(" Book My Stay - Validation System ");
        System.out.println(" Version 9.0 ");
        System.out.println("===================================");

        RoomInventory inventory = new RoomInventory();
        BookingService service = new BookingService();

        // Test cases
        Reservation r1 = new Reservation("Sai", "Single Room");   // valid
        Reservation r2 = new Reservation("", "Double Room");      // invalid name
        Reservation r3 = new Reservation("Rahul", "Deluxe Room"); // invalid type
        Reservation r4 = new Reservation("Anita", "Suite Room");  // no availability

        service.confirmBooking(r1, inventory);
        service.confirmBooking(r2, inventory);
        service.confirmBooking(r3, inventory);
        service.confirmBooking(r4, inventory);

        System.out.println("\nSystem continues running safely!");
    }
}
