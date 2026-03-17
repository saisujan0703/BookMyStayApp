/**
 * Book My Stay Application - Use Case 2
 * 
 * This class demonstrates object-oriented concepts such as abstraction,
 * inheritance, polymorphism, and encapsulation using different room types.
 * 
 * @author SAI SUJAN
 * @version 2.0
 */

// Abstract class
abstract class Room {
    private int beds;
    private double price;
    private String type;

    // Constructor
    public Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    // Method to display room details
    public void displayDetails() {
        System.out.println("Room Type: " + type);
        System.out.println("Beds: " + beds);
        System.out.println("Price per night: ₹" + price);
    }
}

// Single Room
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 1000);
    }
}

// Double Room
class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 1800);
    }
}

// Suite Room
class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 3000);
    }
}

// Main Class
public class UseCase2RoomInitialization {

    /**
     * Main method - Entry point
     */
    public static void main(String[] args) {

        System.out.println("===================================");
        System.out.println(" Book My Stay - Room Availability ");
        System.out.println(" Version 2.0 ");
        System.out.println("===================================");

        // Creating room objects (Polymorphism)
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability variables
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        // Display details
        System.out.println("\n--- Single Room ---");
        single.displayDetails();
        System.out.println("Available: " + singleAvailable);

        System.out.println("\n--- Double Room ---");
        doubleRoom.displayDetails();
        System.out.println("Available: " + doubleAvailable);

        System.out.println("\n--- Suite Room ---");
        suite.displayDetails();
        System.out.println("Available: " + suiteAvailable);

        System.out.println("\nApplication executed successfully!");
    }
}