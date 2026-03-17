/**
 * Book My Stay Application - Use Case 7
 * 
 * This class demonstrates add-on service selection for reservations.
 * It extends functionality without modifying booking or inventory logic.
 * 
 * @author SAI SUJAN
 * @version 7.0
 */

import java.util.*;

// -------------------- ADD-ON SERVICE --------------------
class AddOnService {
    private String name;
    private double cost;

    public AddOnService(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }
}

// -------------------- SERVICE MANAGER --------------------
class AddOnServiceManager {

    // Map: Reservation ID -> List of Services
    private Map<String, List<AddOnService>> serviceMap = new HashMap<>();

    // Add service to reservation
    public void addService(String reservationId, AddOnService service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);

        System.out.println(service.getName() + " added to Reservation " + reservationId);
    }

    // Display services for a reservation
    public void displayServices(String reservationId) {
        System.out.println("\nServices for Reservation " + reservationId + ":");

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected.");
            return;
        }

        for (AddOnService s : services) {
            System.out.println("- " + s.getName() + " (₹" + s.getCost() + ")");
        }
    }

    // Calculate total cost
    public double calculateTotalCost(String reservationId) {
        double total = 0;

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services != null) {
            for (AddOnService s : services) {
                total += s.getCost();
            }
        }

        return total;
    }
}

// -------------------- MAIN CLASS --------------------
public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        System.out.println("===================================");
        System.out.println(" Book My Stay - Add-On Services ");
        System.out.println(" Version 7.0 ");
        System.out.println("===================================");

        // Assume reservation already exists (from Use Case 6)
        String reservationId = "SI101";

        // Initialize service manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Create services
        AddOnService wifi = new AddOnService("WiFi", 200);
        AddOnService breakfast = new AddOnService("Breakfast", 300);
        AddOnService parking = new AddOnService("Parking", 150);

        // Guest selects services
        manager.addService(reservationId, wifi);
        manager.addService(reservationId, breakfast);
        manager.addService(reservationId, parking);

        // Display selected services
        manager.displayServices(reservationId);

        // Calculate total add-on cost
        double total = manager.calculateTotalCost(reservationId);
        System.out.println("\nTotal Add-On Cost: ₹" + total);

        System.out.println("\nAdd-on services processed successfully!");
    }
}
