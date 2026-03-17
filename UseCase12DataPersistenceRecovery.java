/**
 * Book My Stay Application - Use Case 12
 * 
 * This class demonstrates persistence using serialization and recovery
 * using deserialization to restore system state after restart.
 * 
 * @author SAI SUJAN
 * @version 12.0
 */

import java.io.*;
import java.util.*;

// -------------------- RESERVATION --------------------
class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public void display() {
        System.out.println(reservationId + " | " + guestName + " | " + roomType);
    }
}

// -------------------- SYSTEM STATE --------------------
class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;

    Map<String, Integer> inventory;
    List<Reservation> bookings;

    public SystemState(Map<String, Integer> inventory, List<Reservation> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

// -------------------- PERSISTENCE SERVICE --------------------
class PersistenceService {

    private static final String FILE_NAME = "system_state.dat";

    // Save state to file
    public void save(SystemState state) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("System state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    // Load state from file
    public SystemState load() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            SystemState state = (SystemState) ois.readObject();
            System.out.println("System state loaded successfully.");
            return state;

        } catch (FileNotFoundException e) {
            System.out.println("No previous state found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading state. Starting with safe defaults.");
        }

        // Return default state if failure occurs
        return new SystemState(new HashMap<>(), new ArrayList<>());
    }
}

// -------------------- MAIN CLASS --------------------
public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        System.out.println("===================================");
        System.out.println(" Book My Stay - Persistence ");
        System.out.println(" Version 12.0 ");
        System.out.println("===================================");

        PersistenceService service = new PersistenceService();

        // Step 1: Load previous state
        SystemState state = service.load();

        // If empty, initialize default state
        if (state.inventory.isEmpty()) {
            state.inventory.put("Single Room", 2);
            state.inventory.put("Double Room", 1);
        }

        if (state.bookings.isEmpty()) {
            state.bookings.add(new Reservation("R101", "Sai", "Single Room"));
            state.bookings.add(new Reservation("R102", "Rahul", "Double Room"));
        }

        // Display current state
        System.out.println("\n--- Current Inventory ---");
        for (Map.Entry<String, Integer> e : state.inventory.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }

        System.out.println("\n--- Booking History ---");
        for (Reservation r : state.bookings) {
            r.display();
        }

        // Step 2: Simulate update
        System.out.println("\nSimulating new booking...");
        state.bookings.add(new Reservation("R103", "Anita", "Single Room"));
        state.inventory.put("Single Room",
                state.inventory.get("Single Room") - 1);

        // Step 3: Save updated state
        service.save(state);

        System.out.println("\nSystem ready for restart with saved state!");
    }
}
