 /**
 * Book My Stay Application - Use Case 4
 * 
 * This class demonstrates read-only room search functionality.
 * It ensures safe access to inventory without modifying system state.
 * 
 * @author SAI SUJAN
 * @version 4.0
 */

import java.util.*;

// -------------------- DOMAIN MODEL --------------------
abstract class Room {
    private String type;
    private int beds;
    private double price;

    public Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + type);
        System.out.println("Beds: " + beds);
        System.out.println("Price: ₹" + price);
    }
}

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 1000);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 1800);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 3000);
    }
}

// -------------------- INVENTORY (READ ONLY ACCESS) --------------------
class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 0); // unavailable
        inventory.put("Suite Room", 2);
    }

    // Read-only method
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public Set<String> getRoomTypes() {
        return inventory.keySet();
    }
}

// -------------------- SEARCH SERVICE --------------------
class RoomSearchService {

    public void searchAvailableRooms(RoomInventory inventory, List<Room> rooms) {

        System.out.println("\n--- Available Rooms ---");

        for (Room room : rooms) {
            int available = inventory.getAvailability(room.getType());

            // Defensive check (filter unavailable)
            if (available > 0) {
                room.displayDetails();
                System.out.println("Available: " + available);
                System.out.println("----------------------");
            }
        }
    }
}

// -------------------- MAIN CLASS --------------------
public class UseCase4RoomSearch {

    public static void main(String[] args) {

        System.out.println("===================================");
        System.out.println(" Book My Stay - Room Search ");
        System.out.println(" Version 4.0 ");
        System.out.println("===================================");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Create room objects (Domain Model)
        List<Room> rooms = new ArrayList<>();
        rooms.add(new SingleRoom());
        rooms.add(new DoubleRoom());
        rooms.add(new SuiteRoom());

        // Search Service (Read-only)
        RoomSearchService searchService = new RoomSearchService();

        // Perform search
        searchService.searchAvailableRooms(inventory, rooms);

        System.out.println("\nSearch completed successfully!");
    }
}
