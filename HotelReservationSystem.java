import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/*
 * ============================================================
 *             GRAND HORIZON HOTEL RESERVATION SYSTEM
 * ============================================================
 *
 * Features:
 * 1. Search available rooms
 * 2. Room categories: Standard, Deluxe, Suite
 * 3. Make reservations
 * 4. Cancel reservations
 * 5. Payment simulation
 * 6. View booking details
 * 7. View all reservations
 * 8. Hotel statistics
 * 9. File I/O for persistent storage
 * 10. Object-Oriented Programming
 *
 * Data files:
 *      rooms.dat
 *      bookings.dat
 *
 * ============================================================
 */


// ============================================================
//                         COLORS
// ============================================================

class Colors {

    public static final String RESET = "\u001B[0m";

    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    public static final String BOLD = "\u001B[1m";
}


// ============================================================
//                         ROOM CLASS
// ============================================================

class Room implements Serializable {

    private static final long serialVersionUID = 1L;

    private int roomNumber;
    private String category;
    private double pricePerNight;
    private int capacity;
    private boolean available;

    public Room(int roomNumber,
                String category,
                double pricePerNight,
                int capacity) {

        this.roomNumber = roomNumber;
        this.category = category;
        this.pricePerNight = pricePerNight;
        this.capacity = capacity;
        this.available = true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getCategory() {
        return category;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {

        return String.format(
                "%-8d %-12s ₹%-13.2f %-10d",
                roomNumber,
                category,
                pricePerNight,
                capacity
        );
    }
}


// ============================================================
//                    RESERVATION CLASS
// ============================================================

class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    private String bookingId;
    private String customerName;
    private String phone;
    private String email;

    private int roomNumber;
    private String category;

    private String checkIn;
    private String checkOut;

    private int guests;
    private int nights;

    private double totalAmount;

    private String paymentMethod;
    private String paymentStatus;
    private String bookingStatus;

    public Reservation(
            String bookingId,
            String customerName,
            String phone,
            String email,
            int roomNumber,
            String category,
            String checkIn,
            String checkOut,
            int guests,
            int nights,
            double totalAmount,
            String paymentMethod,
            String paymentStatus,
            String bookingStatus
    ) {

        this.bookingId = bookingId;
        this.customerName = customerName;
        this.phone = phone;
        this.email = email;
        this.roomNumber = roomNumber;
        this.category = category;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.guests = guests;
        this.nights = nights;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.bookingStatus = bookingStatus;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getCategory() {
        return category;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void displayDetails() {

        System.out.println(
                Colors.CYAN +
                "╔════════════════════════════════════════════════════════════╗" +
                Colors.RESET
        );

        System.out.printf(
                "║ %-18s : %-38s ║%n",
                "Booking ID",
                bookingId
        );

        System.out.printf(
                "║ %-18s : %-38s ║%n",
                "Guest Name",
                customerName
        );

        System.out.printf(
                "║ %-18s : %-38s ║%n",
                "Phone",
                phone
        );

        System.out.printf(
                "║ %-18s : %-38s ║%n",
                "Email",
                email
        );

        System.out.printf(
                "║ %-18s : %-38d ║%n",
                "Room Number",
                roomNumber
        );

        System.out.printf(
                "║ %-18s : %-38s ║%n",
                "Room Category",
                category
        );

        System.out.printf(
                "║ %-18s : %-38d ║%n",
                "Guests",
                guests
        );

        System.out.printf(
                "║ %-18s : %-38s ║%n",
                "Check-in",
                checkIn
        );

        System.out.printf(
                "║ %-18s : %-38s ║%n",
                "Check-out",
                checkOut
        );

        System.out.printf(
                "║ %-18s : %-38d ║%n",
                "Number of Nights",
                nights
        );

        System.out.printf(
                "║ %-18s : ₹%-37.2f ║%n",
                "Total Amount",
                totalAmount
        );

        System.out.printf(
                "║ %-18s : %-38s ║%n",
                "Payment Method",
                paymentMethod
        );

        System.out.printf(
                "║ %-18s : %-38s ║%n",
                "Payment Status",
                paymentStatus
        );

        String statusColor =
                bookingStatus.equals("Confirmed")
                        ? Colors.GREEN
                        : Colors.RED;

        System.out.printf(
                "║ %-18s : %s%-38s%s ║%n",
                "Booking Status",
                statusColor,
                bookingStatus,
                Colors.RESET
        );

        System.out.println(
                Colors.CYAN +
                "╚════════════════════════════════════════════════════════════╝" +
                Colors.RESET
        );
    }
}


// ============================================================
//                      FILE MANAGER
// ============================================================

class FileManager {

    private static final String ROOMS_FILE = "rooms.dat";
    private static final String BOOKINGS_FILE = "bookings.dat";


    // --------------------------------------------------------
    // Save Rooms
    // --------------------------------------------------------

    public static void saveRooms(ArrayList<Room> rooms) {

        try (ObjectOutputStream output =
                     new ObjectOutputStream(
                             new FileOutputStream(ROOMS_FILE))) {

            output.writeObject(rooms);

        } catch (IOException e) {

            System.out.println(
                    Colors.RED +
                    "Error saving room data: " +
                    e.getMessage() +
                    Colors.RESET
            );
        }
    }


    // --------------------------------------------------------
    // Load Rooms
    // --------------------------------------------------------

    @SuppressWarnings("unchecked")
    public static ArrayList<Room> loadRooms() {

        File file = new File(ROOMS_FILE);

        if (!file.exists()) {
            return createDefaultRooms();
        }

        try (ObjectInputStream input =
                     new ObjectInputStream(
                             new FileInputStream(file))) {

            return (ArrayList<Room>) input.readObject();

        } catch (IOException | ClassNotFoundException e) {

            System.out.println(
                    Colors.YELLOW +
                    "Creating fresh room database..." +
                    Colors.RESET
            );

            return createDefaultRooms();
        }
    }


    // --------------------------------------------------------
    // Save Bookings
    // --------------------------------------------------------

    public static void saveBookings(
            ArrayList<Reservation> bookings) {

        try (ObjectOutputStream output =
                     new ObjectOutputStream(
                             new FileOutputStream(BOOKINGS_FILE))) {

            output.writeObject(bookings);

        } catch (IOException e) {

            System.out.println(
                    Colors.RED +
                    "Error saving booking data: " +
                    e.getMessage() +
                    Colors.RESET
            );
        }
    }


    // --------------------------------------------------------
    // Load Bookings
    // --------------------------------------------------------

    @SuppressWarnings("unchecked")
    public static ArrayList<Reservation> loadBookings() {

        File file = new File(BOOKINGS_FILE);

        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream input =
                     new ObjectInputStream(
                             new FileInputStream(file))) {

            return (ArrayList<Reservation>) input.readObject();

        } catch (IOException | ClassNotFoundException e) {

            return new ArrayList<>();
        }
    }


    // --------------------------------------------------------
    // Create Default Rooms
    // --------------------------------------------------------

    private static ArrayList<Room> createDefaultRooms() {

        ArrayList<Room> rooms = new ArrayList<>();

        // Standard Rooms
        for (int i = 101; i <= 106; i++) {

            rooms.add(
                    new Room(
                            i,
                            "Standard",
                            2500,
                            2
                    )
            );
        }

        // Deluxe Rooms
        for (int i = 201; i <= 205; i++) {

            rooms.add(
                    new Room(
                            i,
                            "Deluxe",
                            4000,
                            3
                    )
            );
        }

        // Suite Rooms
        for (int i = 301; i <= 304; i++) {

            rooms.add(
                    new Room(
                            i,
                            "Suite",
                            6500,
                            5
                    )
            );
        }

        return rooms;
    }
}


// ============================================================
//                       PAYMENT CLASS
// ============================================================

class Payment {

    public static String processPayment(
            Scanner scanner,
            double amount) {

        System.out.println();

        System.out.println(
                Colors.PURPLE +
                "╔════════════════════════════════════════════════════════════╗"
        );

        System.out.println(
                "║                    PAYMENT CENTER                         ║"
        );

        System.out.println(
                "╚════════════════════════════════════════════════════════════╝"
                + Colors.RESET
        );

        System.out.printf(
                "\nAmount Payable: %s₹%.2f%s%n",
                Colors.GREEN,
                amount,
                Colors.RESET
        );

        System.out.println();
        System.out.println("  1. 💳 Credit / Debit Card");
        System.out.println("  2. 📱 UPI");
        System.out.println("  3. 💵 Cash at Hotel");

        System.out.print(
                Colors.YELLOW +
                "\nChoose payment method: " +
                Colors.RESET
        );

        String choice = scanner.nextLine();

        switch (choice) {

            case "1":

                System.out.print(
                        "Enter card number (simulation): "
                );

                String cardNumber = scanner.nextLine();

                if (cardNumber.length() < 4) {

                    System.out.println(
                            Colors.RED +
                            "Invalid card details." +
                            Colors.RESET
                    );

                    return null;
                }

                System.out.println(
                        Colors.CYAN +
                        "\nProcessing card payment..." +
                        Colors.RESET
                );

                pause(1000);

                System.out.println(
                        Colors.GREEN +
                        "✔ Card payment approved!" +
                        Colors.RESET
                );

                return "Credit/Debit Card";

            case "2":

                System.out.print(
                        "Enter UPI ID (simulation): "
                );

                String upi = scanner.nextLine();

                if (!upi.contains("@")) {

                    System.out.println(
                            Colors.RED +
                            "Invalid UPI ID." +
                            Colors.RESET
                    );

                    return null;
                }

                System.out.println(
                        Colors.CYAN +
                        "\nProcessing UPI payment..." +
                        Colors.RESET
                );

                pause(1000);

                System.out.println(
                        Colors.GREEN +
                        "✔ UPI payment successful!" +
                        Colors.RESET
                );

                return "UPI";

            case "3":

                System.out.println(
                        Colors.YELLOW +
                        "\nPayment will be collected at the hotel." +
                        Colors.RESET
                );

                return "Cash at Hotel";

            default:

                System.out.println(
                        Colors.RED +
                        "Invalid payment option." +
                        Colors.RESET
                );

                return null;
        }
    }

    private static void pause(long milliseconds) {

        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException ignored) {
        }
    }
}


// ============================================================
//                       HOTEL SYSTEM
// ============================================================

public class HotelReservationSystem {

    private Scanner scanner;

    private ArrayList<Room> rooms;
    private ArrayList<Reservation> bookings;


    // ========================================================
    //                     CONSTRUCTOR
    // ========================================================

    public HotelReservationSystem() {

        scanner = new Scanner(System.in);

        rooms = FileManager.loadRooms();

        bookings = FileManager.loadBookings();

        updateRoomAvailability();
    }


    // ========================================================
    //                     UTILITY METHODS
    // ========================================================

    private void clearScreen() {

        for (int i = 0; i < 35; i++) {
            System.out.println();
        }
    }


    private void pause() {

        System.out.println();

        System.out.print(
                Colors.YELLOW +
                "Press ENTER to continue..." +
                Colors.RESET
        );

        scanner.nextLine();
    }


    private void header(String title) {

        System.out.println();

        System.out.println(
                Colors.CYAN +
                "╔════════════════════════════════════════════════════════════╗"
                + Colors.RESET
        );

        System.out.printf(
                Colors.CYAN +
                "║" +
                Colors.BOLD +
                Colors.WHITE +
                " %-58s " +
                Colors.RESET +
                Colors.CYAN +
                "║%n" +
                Colors.RESET,
                title
        );

        System.out.println(
                Colors.CYAN +
                "╚════════════════════════════════════════════════════════════╝"
                + Colors.RESET
        );

        System.out.println();
    }


    private void success(String message) {

        System.out.println(
                Colors.GREEN +
                "\n✔ " +
                message +
                Colors.RESET
        );
    }


    private void error(String message) {

        System.out.println(
                Colors.RED +
                "\n✖ " +
                message +
                Colors.RESET
        );
    }


    private void info(String message) {

        System.out.println(
                Colors.CYAN +
                "\nℹ " +
                message +
                Colors.RESET
        );
    }


    // ========================================================
    //               UPDATE ROOM AVAILABILITY
    // ========================================================

    private void updateRoomAvailability() {

        for (Room room : rooms) {

            room.setAvailable(true);
        }

        for (Reservation booking : bookings) {

            if (booking.getBookingStatus()
                    .equals("Confirmed")) {

                for (Room room : rooms) {

                    if (room.getRoomNumber()
                            == booking.getRoomNumber()) {

                        room.setAvailable(false);
                    }
                }
            }
        }

        FileManager.saveRooms(rooms);
    }


    // ========================================================
    //                    SEARCH ROOMS
    // ========================================================

    private void searchRooms() {

        clearScreen();

        header("🔎 SEARCH AVAILABLE ROOMS");

        System.out.println(
                "Room Categories\n"
        );

        System.out.println(
                "  1. Standard   | ₹2,500/night | 2 Guests"
        );

        System.out.println(
                "  2. Deluxe     | ₹4,000/night | 3 Guests"
        );

        System.out.println(
                "  3. Suite      | ₹6,500/night | 5 Guests"
        );

        System.out.println(
                "  4. All Rooms"
        );

        System.out.print(
                Colors.YELLOW +
                "\nSelect category: " +
                Colors.RESET
        );

        String choice = scanner.nextLine();

        String category = null;

        if (choice.equals("1")) {
            category = "Standard";
        } else if (choice.equals("2")) {
            category = "Deluxe";
        } else if (choice.equals("3")) {
            category = "Suite";
        } else if (!choice.equals("4")) {

            error("Invalid category.");

            pause();

            return;
        }

        System.out.println();

        System.out.println(
                Colors.BLUE +
                "┌────────┬────────────┬───────────────┬──────────┐"
                + Colors.RESET
        );

        System.out.println(
                Colors.BLUE +
                "│ Room   │ Category   │ Price/Night   │ Capacity │"
                + Colors.RESET
        );

        System.out.println(
                Colors.BLUE +
                "├────────┼────────────┼───────────────┼──────────┤"
                + Colors.RESET
        );

        int count = 0;

        for (Room room : rooms) {

            if (room.isAvailable()
                    && (category == null
                    || room.getCategory().equals(category))) {

                System.out.printf(
                        Colors.GREEN +
                        "│ %-6d │ %-10s │ ₹%-12.2f │ %-8d │%n" +
                        Colors.RESET,
                        room.getRoomNumber(),
                        room.getCategory(),
                        room.getPricePerNight(),
                        room.getCapacity()
                );

                count++;
            }
        }

        System.out.println(
                Colors.BLUE +
                "└────────┴────────────┴───────────────┴──────────┘"
                + Colors.RESET
        );

        if (count == 0) {

            error("No rooms are currently available.");

        } else {

            success(
                    count +
                    " room(s) available for reservation."
            );
        }

        pause();
    }


    // ========================================================
    //                     BOOK ROOM
    // ========================================================

    private void bookRoom() {

        clearScreen();

        header("🏨 MAKE A HOTEL RESERVATION");

        System.out.println(
                Colors.CYAN +
                "AVAILABLE ROOMS" +
                Colors.RESET
        );

        for (Room room : rooms) {

            if (room.isAvailable()) {

                System.out.printf(
                        "  %d  | %-10s | ₹%.2f/night | Up to %d guests%n",
                        room.getRoomNumber(),
                        room.getCategory(),
                        room.getPricePerNight(),
                        room.getCapacity()
                );
            }
        }

        System.out.print(
                Colors.YELLOW +
                "\nEnter room number: " +
                Colors.RESET
        );

        int roomNumber;

        try {

            roomNumber =
                    Integer.parseInt(scanner.nextLine());

        } catch (NumberFormatException e) {

            error("Invalid room number.");

            pause();

            return;
        }

        Room selectedRoom = null;

        for (Room room : rooms) {

            if (room.getRoomNumber() == roomNumber
                    && room.isAvailable()) {

                selectedRoom = room;

                break;
            }
        }

        if (selectedRoom == null) {

            error("That room is not available.");

            pause();

            return;
        }


        // ----------------------------------------------------
        // Customer Details
        // ----------------------------------------------------

        System.out.println();

        System.out.println(
                Colors.CYAN +
                "CUSTOMER DETAILS" +
                Colors.RESET
        );

        System.out.print("Full Name       : ");
        String name = scanner.nextLine();

        System.out.print("Phone Number    : ");
        String phone = scanner.nextLine();

        System.out.print("Email Address   : ");
        String email = scanner.nextLine();

        if (name.isBlank()
                || phone.isBlank()
                || email.isBlank()) {

            error("All customer details are required.");

            pause();

            return;
        }


        // ----------------------------------------------------
        // Number of Guests
        // ----------------------------------------------------

        System.out.print("Number of Guests: ");

        int guests;

        try {

            guests =
                    Integer.parseInt(scanner.nextLine());

        } catch (NumberFormatException e) {

            error("Invalid number of guests.");

            pause();

            return;
        }

        if (guests <= 0
                || guests > selectedRoom.getCapacity()) {

            error(
                    "This room can accommodate maximum " +
                    selectedRoom.getCapacity() +
                    " guests."
            );

            pause();

            return;
        }


        // ----------------------------------------------------
        // Dates
        // ----------------------------------------------------

        System.out.print(
                "Check-in Date  (DD-MM-YYYY): "
        );

        String checkIn = scanner.nextLine();

        System.out.print(
                "Check-out Date (DD-MM-YYYY): "
        );

        String checkOut = scanner.nextLine();

        int nights = calculateNights(
                checkIn,
                checkOut
        );

        if (nights <= 0) {

            error(
                    "Invalid dates. Check-out must be after check-in."
            );

            pause();

            return;
        }


        // ----------------------------------------------------
        // Calculate Price
        // ----------------------------------------------------

        double total =
                selectedRoom.getPricePerNight()
                        * nights;


        // ----------------------------------------------------
        // Booking Summary
        // ----------------------------------------------------

        System.out.println();

        System.out.println(
                Colors.PURPLE +
                "╔════════════════════════════════════════════════════════════╗"
        );

        System.out.println(
                "║                    BOOKING SUMMARY                         ║"
        );

        System.out.println(
                "╚════════════════════════════════════════════════════════════╝"
                + Colors.RESET
        );

        System.out.printf(
                "Room          : %d%n",
                selectedRoom.getRoomNumber()
        );

        System.out.printf(
                "Category      : %s%n",
                selectedRoom.getCategory()
        );

        System.out.printf(
                "Price/Night   : ₹%.2f%n",
                selectedRoom.getPricePerNight()
        );

        System.out.printf(
                "Number Nights : %d%n",
                nights
        );

        System.out.printf(
                "Guests        : %d%n",
                guests
        );

        System.out.printf(
                "TOTAL         : %s₹%.2f%s%n",
                Colors.GREEN,
                total,
                Colors.RESET
        );

        System.out.print(
                "\nProceed to payment? (Y/N): "
        );

        String confirm =
                scanner.nextLine();

        if (!confirm.equalsIgnoreCase("Y")) {

            info("Reservation cancelled.");

            pause();

            return;
        }


        // ----------------------------------------------------
        // Payment
        // ----------------------------------------------------

        String paymentMethod =
                Payment.processPayment(
                        scanner,
                        total
                );

        if (paymentMethod == null) {

            error(
                    "Payment failed. Reservation not created."
            );

            pause();

            return;
        }


        // ----------------------------------------------------
        // Create Reservation
        // ----------------------------------------------------

        String bookingId =
                generateBookingId();

        Reservation reservation =
                new Reservation(
                        bookingId,
                        name,
                        phone,
                        email,
                        selectedRoom.getRoomNumber(),
                        selectedRoom.getCategory(),
                        checkIn,
                        checkOut,
                        guests,
                        nights,
                        total,
                        paymentMethod,
                        "Paid",
                        "Confirmed"
                );

        bookings.add(reservation);

        selectedRoom.setAvailable(false);

        FileManager.saveBookings(bookings);

        FileManager.saveRooms(rooms);


        // ----------------------------------------------------
        // Confirmation
        // ----------------------------------------------------

        System.out.println();

        System.out.println(
                Colors.GREEN +
                "╔════════════════════════════════════════════════════════════╗"
        );

        System.out.println(
                "║                  🎉 BOOKING CONFIRMED! 🎉                ║"
        );

        System.out.println(
                "╚════════════════════════════════════════════════════════════╝"
                + Colors.RESET
        );

        System.out.println();

        System.out.println(
                "Booking ID : " +
                Colors.YELLOW +
                bookingId +
                Colors.RESET
        );

        System.out.println(
                "Guest      : " + name
        );

        System.out.println(
                "Room       : " +
                selectedRoom.getRoomNumber()
        );

        System.out.println(
                "Category   : " +
                selectedRoom.getCategory()
        );

        System.out.println(
                "Check-in   : " + checkIn
        );

        System.out.println(
                "Check-out  : " + checkOut
        );

        System.out.println(
                "Total Paid : ₹" +
                String.format("%.2f", total)
        );

        success(
                "Your reservation has been successfully created!"
        );

        pause();
    }


    // ========================================================
    //                   CALCULATE NIGHTS
    // ========================================================

    private int calculateNights(
            String checkIn,
            String checkOut) {

        try {

            SimpleDateFormat format =
                    new SimpleDateFormat("dd-MM-yyyy");

            format.setLenient(false);

            Date start =
                    format.parse(checkIn);

            Date end =
                    format.parse(checkOut);

            long difference =
                    end.getTime() - start.getTime();

            return (int)
                    (difference /
                            (1000L * 60 * 60 * 24));

        } catch (Exception e) {

            return -1;
        }
    }


    // ========================================================
    //                 GENERATE BOOKING ID
    // ========================================================

    private String generateBookingId() {

        String id;

        do {

            int number =
                    ThreadLocalRandom.current()
                            .nextInt(1000, 10000);

            id = "GH" + number;

        } while (bookingExists(id));

        return id;
    }


    private boolean bookingExists(String id) {

        for (Reservation booking : bookings) {

            if (booking.getBookingId().equals(id)) {

                return true;
            }
        }

        return false;
    }


    // ========================================================
    //                 VIEW BOOKING DETAILS
    // ========================================================

    private void viewBooking() {

        clearScreen();

        header("🧾 VIEW BOOKING DETAILS");

        System.out.print(
                "Enter Booking ID: "
        );

        String id =
                scanner.nextLine().trim();

        Reservation reservation =
                findBooking(id);

        if (reservation == null) {

            error("Booking not found.");

        } else {

            reservation.displayDetails();
        }

        pause();
    }


    // ========================================================
    //                  FIND BOOKING
    // ========================================================

    private Reservation findBooking(String id) {

        for (Reservation booking : bookings) {

            if (booking.getBookingId()
                    .equalsIgnoreCase(id)) {

                return booking;
            }
        }

        return null;
    }


    // ========================================================
    //                CANCEL RESERVATION
    // ========================================================

    private void cancelBooking() {

        clearScreen();

        header("❌ CANCEL RESERVATION");

        System.out.print(
                "Enter Booking ID: "
        );

        String id =
                scanner.nextLine().trim();

        Reservation reservation =
                findBooking(id);

        if (reservation == null) {

            error("Booking not found.");

            pause();

            return;
        }

        if (reservation.getBookingStatus()
                .equals("Cancelled")) {

            error("This reservation is already cancelled.");

            pause();

            return;
        }

        System.out.println();

        System.out.println(
                "Guest : " +
                reservation.getCustomerName()
        );

        System.out.println(
                "Room  : " +
                reservation.getRoomNumber()
        );

        System.out.printf(
                "Amount: ₹%.2f%n",
                reservation.getTotalAmount()
        );

        System.out.print(
                Colors.YELLOW +
                "\nAre you sure you want to cancel? (Y/N): " +
                Colors.RESET
        );

        String confirm =
                scanner.nextLine();

        if (!confirm.equalsIgnoreCase("Y")) {

            info("Cancellation aborted.");

            pause();

            return;
        }

        reservation.setBookingStatus(
                "Cancelled"
        );

        // Make room available
        for (Room room : rooms) {

            if (room.getRoomNumber()
                    == reservation.getRoomNumber()) {

                room.setAvailable(true);

                break;
            }
        }

        FileManager.saveBookings(bookings);

        FileManager.saveRooms(rooms);

        success(
                "Booking " +
                reservation.getBookingId() +
                " has been cancelled."
        );

        info(
                "The room is now available for new reservations."
        );

        pause();
    }


    // ========================================================
    //                VIEW ALL BOOKINGS
    // ========================================================

    private void viewAllBookings() {

        clearScreen();

        header("📋 ALL RESERVATIONS");

        if (bookings.isEmpty()) {

            info("There are no reservations.");

            pause();

            return;
        }

        for (Reservation booking : bookings) {

            String statusColor;

            if (booking.getBookingStatus()
                    .equals("Confirmed")) {

                statusColor =
                        Colors.GREEN;

            } else {

                statusColor =
                        Colors.RED;
            }

            System.out.println(
                    Colors.BLUE +
                    "┌────────────────────────────────────────────────────────────┐"
                    + Colors.RESET
            );

            System.out.printf(
                    "│ Booking ID : %-44s │%n",
                    booking.getBookingId()
            );

            System.out.printf(
                    "│ Guest      : %-44s │%n",
                    booking.getCustomerName()
            );

            System.out.printf(
                    "│ Room       : %-44s │%n",
                    booking.getRoomNumber()
                            + " (" +
                            booking.getCategory() +
                            ")"
            );

            System.out.printf(
                    "│ Amount     : ₹%-43.2f │%n",
                    booking.getTotalAmount()
            );

            System.out.printf(
                    "│ Status     : %s%-44s%s │%n",
                    statusColor,
                    booking.getBookingStatus(),
                    Colors.RESET
            );

            System.out.println(
                    Colors.BLUE +
                    "└────────────────────────────────────────────────────────────┘"
                    + Colors.RESET
            );

            System.out.println();
        }

        pause();
    }


    // ========================================================
    //                   HOTEL STATISTICS
    // ========================================================

    private void statistics() {

        clearScreen();

        header("📊 HOTEL STATISTICS");

        int totalRooms =
                rooms.size();

        int availableRooms = 0;

        int occupiedRooms = 0;

        int confirmedBookings = 0;

        int cancelledBookings = 0;

        double revenue = 0;


        for (Room room : rooms) {

            if (room.isAvailable()) {

                availableRooms++;

            } else {

                occupiedRooms++;
            }
        }


        for (Reservation booking : bookings) {

            if (booking.getBookingStatus()
                    .equals("Confirmed")) {

                confirmedBookings++;

                if (booking.getPaymentStatus()
                        .equals("Paid")) {

                    revenue +=
                            booking.getTotalAmount();
                }

            } else {

                cancelledBookings++;
            }
        }


        System.out.println(
                Colors.CYAN +
                "╔════════════════════════════════════════════════════════════╗"
                + Colors.RESET
        );

        System.out.printf(
                "║ %-25s : %-29d ║%n",
                "Total Rooms",
                totalRooms
        );

        System.out.printf(
                "║ %-25s : %s%-29d%s ║%n",
                "Available Rooms",
                Colors.GREEN,
                availableRooms,
                Colors.RESET
        );

        System.out.printf(
                "║ %-25s : %s%-29d%s ║%n",
                "Occupied Rooms",
                Colors.RED,
                occupiedRooms,
                Colors.RESET
        );

        System.out.printf(
                "║ %-25s : %-29d ║%n",
                "Confirmed Bookings",
                confirmedBookings
        );

        System.out.printf(
                "║ %-25s : %-29d ║%n",
                "Cancelled Bookings",
                cancelledBookings
        );

        System.out.printf(
                "║ %-25s : %s₹%-28.2f%s ║%n",
                "Total Revenue",
                Colors.GREEN,
                revenue,
                Colors.RESET
        );

        System.out.println(
                Colors.CYAN +
                "╚════════════════════════════════════════════════════════════╝"
                + Colors.RESET
        );

        pause();
    }


    // ========================================================
    //                       MAIN MENU
    // ========================================================

    public void run() {

        while (true) {

            clearScreen();

            System.out.println(
                    Colors.CYAN +
                    """
                    
╔════════════════════════════════════════════════════════════════════╗
║                                                                    ║
║                 🏨 GRAND HORIZON HOTEL 🏨                         ║
║                                                                    ║
║                    RESERVATION SYSTEM                              ║
║                                                                    ║
║                 "Your Comfort, Our Priority"                       ║
║                                                                    ║
╚════════════════════════════════════════════════════════════════════╝
                    
                    """ +
                    Colors.RESET
            );


            System.out.println(
                    Colors.BLUE +
                    "┌────────────────────────────────────────────────────────────┐"
                    + Colors.RESET
            );

            System.out.println(
                    "│                                                            │"
            );

            System.out.println(
                    "│   1. 🔎  Search Available Rooms                            │"
            );

            System.out.println(
                    "│   2. 🏨  Make a Reservation                                │"
            );

            System.out.println(
                    "│   3. 🧾  View Booking Details                             │"
            );

            System.out.println(
                    "│   4. ❌  Cancel Reservation                                │"
            );

            System.out.println(
                    "│   5. 📋  View All Reservations                             │"
            );

            System.out.println(
                    "│   6. 📊  Hotel Statistics                                 │"
            );

            System.out.println(
                    "│   7. 🚪  Exit                                              │"
            );

            System.out.println(
                    "│                                                            │"
            );

            System.out.println(
                    Colors.BLUE +
                    "└────────────────────────────────────────────────────────────┘"
                    + Colors.RESET
            );


            System.out.print(
                    Colors.YELLOW +
                    "\nEnter your choice: " +
                    Colors.RESET
            );

            String choice =
                    scanner.nextLine();


            switch (choice) {

                case "1":
                    searchRooms();
                    break;

                case "2":
                    bookRoom();
                    break;

                case "3":
                    viewBooking();
                    break;

                case "4":
                    cancelBooking();
                    break;

                case "5":
                    viewAllBookings();
                    break;

                case "6":
                    statistics();
                    break;

                case "7":

                    clearScreen();

                    System.out.println(
                            Colors.GREEN +
                            """
                            
╔══════════════════════════════════════════════════════════════╗
║                                                              ║
║        Thank you for choosing Grand Horizon Hotel!          ║
║                                                              ║
║                 Have a wonderful stay! 🏨                   ║
║                                                              ║
╚══════════════════════════════════════════════════════════════╝
                            
                            """ +
                            Colors.RESET
                    );

                    scanner.close();

                    return;

                default:

                    error(
                            "Invalid option. Please choose 1-7."
                    );

                    pause();
            }
        }
    }


    // ========================================================
    //                         MAIN
    // ========================================================

    public static void main(String[] args) {

        HotelReservationSystem system =
                new HotelReservationSystem();

        system.run();
    }
}
