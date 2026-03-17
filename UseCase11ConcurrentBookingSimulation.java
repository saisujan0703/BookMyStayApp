/**
 * Book My Stay Application - Use Case 11
 * 
 * This class demonstrates concurrent booking using threads and synchronization.
 * It ensures thread-safe access to shared resources like queue and inventory.
 * 
 * @author SAI SUJAN
 * @version 11.0
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

// -------------------- THREAD-SAFE QUEUE --------------------
class BookingQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    public synchronized void addRequest(Reservation r) {
        queue.offer(r);
        System.out.println(Thread.currentThread().getName() +
                " added request for " + r.getGuestName());
    }

    public synchronized Reservation getRequest() {
        return queue.poll();
    }
}

// -------------------- THREAD-SAFE INVENTORY --------------------
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
    }

    // Critical section (synchronized)
    public synchronized boolean allocateRoom(String roomType) {

        int available = inventory.getOrDefault(roomType, 0);

        if (available > 0) {
            inventory.put(roomType, available - 1);
            return true;
        }
        return false;
    }

    public synchronized void display() {
        System.out.println("\nFinal Inventory:");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }
}

// -------------------- BOOKING PROCESSOR (THREAD) --------------------
class BookingProcessor extends Thread {

    private BookingQueue queue;
    private RoomInventory inventory;

    public BookingProcessor(String name, BookingQueue queue, RoomInventory inventory) {
        super(name);
        this.queue = queue;
        this.inventory = inventory;
    }

    @Override
    public void run() {

        while (true) {
            Reservation r;

            // synchronized retrieval
            synchronized (queue) {
                r = queue.getRequest();
            }

            if (r == null) break;

            System.out.println(getName() + " processing " + r.getGuestName());

            // Critical section: allocation
            boolean success = inventory.allocateRoom(r.getRoomType());

            if (success) {
                System.out.println(getName() + " SUCCESS: " + r.getGuestName()
                        + " got " + r.getRoomType());
            } else {
                System.out.println(getName() + " FAILED: No room for "
                        + r.getGuestName());
            }
        }
    }
}

// -------------------- MAIN CLASS --------------------
public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {

        System.out.println("===================================");
        System.out.println(" Book My Stay - Concurrent Booking ");
        System.out.println(" Version 11.0 ");
        System.out.println("===================================");

        BookingQueue queue = new BookingQueue();
        RoomInventory inventory = new RoomInventory();

        // Simulate multiple booking requests
        queue.addRequest(new Reservation("Sai", "Single Room"));
        queue.addRequest(new Reservation("Rahul", "Single Room"));
        queue.addRequest(new Reservation("Anita", "Single Room"));
        queue.addRequest(new Reservation("Kiran", "Double Room"));

        // Create multiple threads
        Thread t1 = new BookingProcessor("Thread-1", queue, inventory);
        Thread t2 = new BookingProcessor("Thread-2", queue, inventory);

        // Start threads
        t1.start();
        t2.start();

        // Wait for completion
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Final inventory
        inventory.display();

        System.out.println("\nConcurrent booking completed safely!");
    }
}
