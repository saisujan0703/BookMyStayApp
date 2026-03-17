/**
 * Book My Stay Application - Use Case 6
 * 
 * This class demonstrates booking confirmation and safe room allocation.
 * It ensures no double-booking using Set and maintains inventory consistency.
 * 
 * @author SAI SUJAN
 * @version 6.0
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
}

// -------------------- BOOKING QUEUE --------------------
class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNextRequest() {
        return queue.poll(); // remove in FIFO
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

// -------------------- INVENTORY --------------------
class RoomInventory {
    private HashMap<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrement(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("\n--- Updated Inventory ---");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// -------------------- BOOKING SERVICE --------------------
class BookingService {

    // Map roomType -> allocated room IDs
    private HashMap<String, Set<String>> allocatedRooms = new HashMap<>();

    // Global set to ensure uniqueness
    private Set<String> allRoomIds = new HashSet<>();

    private int idCounter = 1;

    public void processBookings(BookingRequestQueue queue, RoomInventory inventory) {

        while (!queue.isEmpty()) {

            Reservation r = queue.getNextRequest();
            String roomType = r.getRoomType();

            System.out.println("\nProcessing request for " + r.getGuestName());

            // Check availability
            if (inventory.getAvailability(roomType) > 0) {

                // Generate unique room ID
                String roomId = generateRoomId(roomType);

                // Ensure uniqueness using Set
                while (allRoomIds.contains(roomId)) {
                    roomId = generateRoomId(roomType);
                }

                allRoomIds.add(roomId);

                // Map room type to IDs
                allocatedRooms.putIfAbsent(roomType, new HashSet<>());
                allocatedRooms.get(roomType).add(roomId);

                // Decrement inventory (atomic step)
                inventory.decrement(roomType);

                // Confirm booking
                System.out.println("Booking Confirmed!");
                System.out.println("Guest: " + r.getGuestName());
                System.out.println("Room Type: " + roomType);
                System.out.println("Assigned Room ID: " + roomId);

            } else {
                System.out.println("Booking Failed! No rooms available for " + roomType);
            }
        }
    }

    // Room ID generator
    private String generateRoomId(String roomType) {
        return roomType.substring(0, 2).toUpperCase() + idCounter++;
    }
}

// -------------------- MAIN CLASS --------------------
public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        System.out.println("===================================");
        System.out.println(" Book My Stay - Room Allocation ");
        System.out.println(" Version 6.0 ");
        System.out.println("===================================");

        // Initialize queue
        BookingRequestQueue queue = new BookingRequestQueue();

        queue.addRequest(new Reservation("Sai", "Single Room"));
        queue.addRequest(new Reservation("Rahul", "Single Room"));
        queue.addRequest(new Reservation("Anita", "Single Room")); // should fail
        queue.addRequest(new Reservation("Kiran", "Suite Room"));

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Process bookings
        BookingService service = new BookingService();
        service.processBookings(queue, inventory);

        // Show final inventory
        inventory.displayInventory();

        System.out.println("\nAll bookings processed safely!");
    }
}
