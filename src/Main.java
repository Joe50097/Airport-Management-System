import java.util.*;
import java.io.*;

public class Main
{
    // csv code starts here
    private static final String CSV_FILE = "data.csv";  // heda l csv file

    // heda to write data to CSV
    public static void writeDataToCSV(List<CSVConvertible> data)
    {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CSV_FILE)))
        {
            for (CSVConvertible item : data)
            {
                writer.println(item.toCSV());  // heda to write each object's CSV data
            }
            System.out.println("Data saved to CSV.");
        }
        catch (IOException e)
        {
            System.out.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    // heda to read data from CSV
    public static List<CSVConvertible> readDataFromCSV()
    {
        List<CSVConvertible> data = new ArrayList<>();
        File file = new File(CSV_FILE);

        if (file.exists())
        {
            try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE)))
            {
                String line;
                while ((line = reader.readLine()) != null)
                {

                    String[] parts = line.split(",");
                    String className = parts[0];

                    if (className.equals("Flight"))
                    {
                        Flight flight = new Flight("", 0, 0, 0, "", 0);  // provide necessary arguments
                        flight.fromCSV(line);
                        data.add(flight);
                    }
                    else if (className.equals("Pilot"))
                    {
                        Pilot pilot = new Pilot("", 0, "", false, 0);
                        pilot.fromCSV(line);
                        data.add(pilot);
                    }
                    else if (className.equals("FlightAttendant"))
                    {
                        FlightAttendant attendant = new FlightAttendant("", 0, "", false, 0, new ArrayList<>());
                        attendant.fromCSV(line);
                        data.add(attendant);
                    }
                    else if (className.equals("Passenger"))
                    {
                        Passenger passenger = new Passenger("", 0, "", "");
                        passenger.fromCSV(line);
                        data.add(passenger);
                    }
                    else if (className.equals("Employee"))
                    {
                        Employee employee = new Employee("", 0, "", false);  // adjust constructor as needed
                        employee.fromCSV(line);
                        data.add(employee);
                    }
                    else if (className.equals("Ticket"))
                    {
                        Ticket ticket = new Ticket(0, "", "", 0.0, new Passenger("", 0, "", ""), "01/01/2025", "12:00", 0.0);
                        ticket.fromCSV(line);
                        data.add(ticket);
                    }

                }
                System.out.println("Data loaded from CSV.");
            } catch (IOException e) {
                System.out.println("Error reading from CSV file: " + e.getMessage());
            }
        } else {
            System.out.println("No data found. CSV file not found.");
        }

        return data;
    }
    private static List<CSVConvertible> data = new ArrayList<>();
    // csv code ends here
    static ArrayList<Pilot> pilots = new ArrayList<>();
    static ArrayList<FlightAttendant> FAs = new ArrayList<>();
    static ArrayList<Ticket> t = new ArrayList<>();
    static Flight[] flights = new Flight[100];
    static int flightCount = 0;
    static int passengerCount = 0;
    static Person[] passengers = new Passenger[100]; //Polymorphism
    public static void main(String[] args) throws InterruptedException // keep the throws InterruptedException cause it's for the cool exiting animation metel bel c++
    {
        // heda to load the data when application starts
        data = readDataFromCSV();

        // hedda to populate individual collections from loaded data
        for (CSVConvertible item : data)
        {
            if (item instanceof Flight)
            {
                if (flightCount < flights.length)
                {
                    flights[flightCount++] = (Flight) item;
                }
            }
            else if (item instanceof Pilot)
            {
                pilots.add((Pilot) item);
            }
            else if (item instanceof FlightAttendant)
            {
                FAs.add((FlightAttendant) item);
            }
            else if (item instanceof Passenger)
            {
                for (int i = 0; i < passengers.length; i++)
                {
                    if (passengers[i] == null)
                    {
                        passengers[i] = (Passenger) item;
                        passengerCount++;
                        break;
                    }
                }
            }
            else if (item instanceof Ticket)
            {
                t.add((Ticket) item);
            }
        }
        Scanner scan = new Scanner(System.in);
        int mainChoice, subChoice, selection;
        do {
            do {
                System.out.println("-----Airport System-----");
                System.out.println("1- Manage Flights");
                System.out.println("2- Manage Employees");
                System.out.println("3- Manage Passengers");
                System.out.println("4- Exit");
                System.out.print("Enter Choice: ");
                mainChoice = scan.nextInt();
            }while (mainChoice < 1 || mainChoice > 4);
            switch (mainChoice)
            {
                // heda l case 1 taba3 l manage flights li 3melto
                case 1:
                    do
                    {
                        System.out.println("-----Manage Flights-----");
                        System.out.println("1- Add Flight");
                        System.out.println("2- Edit Flight");
                        System.out.println("3- View All Flights");
                        System.out.println("4- Return");
                        System.out.print("Enter Choice: ");
                        subChoice = scan.nextInt();
                        scan.nextLine();

                        switch (subChoice)
                        {
                            case 1:
                                if (flightCount >= flights.length)
                                {
                                    System.out.println("Cannot add more flights. Flight storage is full.");
                                    break;
                                }


                                String date;
                                boolean validDate = false;

                                do
                                {
                                    System.out.print("Enter flight date (dd/MM/yyyy): ");
                                    date = scan.next();

                                    String[] parts = date.split("/");

                                    if (parts.length == 3)
                                    {
                                        try {
                                            int day = Integer.parseInt(parts[0]);
                                            int month = Integer.parseInt(parts[1]);
                                            int year = Integer.parseInt(parts[2]);

                                            if (day >= 1 && day <= 31 && month >= 1 && month <= 12 && parts[2].length() == 4)
                                            {
                                                // heda to add 0 if l user gave the date 7/5/2025 bet sir 07/05/2025
                                                date = String.format("%02d/%02d/%04d", day, month, year);
                                                validDate = true;
                                            }
                                            else
                                            {
                                                System.out.println("Error: invalid date values.");
                                            }
                                        }
                                        catch (NumberFormatException e)
                                        {
                                            System.out.println("Error: invalid input. plz enter numbers only.");
                                        }
                                    }
                                    else
                                    {
                                        System.out.println("Error: invalid format. plz use dd/MM/yyyy.");
                                    }
                                } while (!validDate);

                                int first;
                                do
                                {
                                    System.out.print("Enter number of first class seats: ");
                                    first = scan.nextInt();
                                    if (first < 0) System.out.println("Seat count cannot be negative.");
                                } while (first < 0);

                                int business;
                                do
                                {
                                    System.out.print("Enter number of business class seats: ");
                                    business = scan.nextInt();
                                    if (business < 0) System.out.println("Seat count cannot be negative.");
                                } while (business < 0);

                                int economy;
                                do
                                {
                                    System.out.print("Enter number of economy class seats: ");
                                    economy = scan.nextInt();
                                    if (economy < 0) System.out.println("Seat count cannot be negative.");
                                } while (economy < 0);

                                int hours, minutes;
                                String flightTime = "";
                                do
                                {
                                    System.out.print("Enter flight departure hour(Military time, 0-23): ");
                                    hours = scan.nextInt();
                                    if (hours < 0 || hours > 23) System.out.println("Flight hours must be equal or greater than zero, less than 24");
                                } while (hours < 0 || hours > 23);
                                do
                                {
                                    System.out.print("Enter flight departure minute (0-59): ");
                                    minutes = scan.nextInt();
                                    if (minutes < 0 || minutes > 59) System.out.println("Minutes must be equal or greater than 0, and less than 60");
                                } while (minutes < 0 || minutes > 59);
                                if (hours < 10){
                                    if (minutes < 10){
                                        flightTime = "0" + hours + ":" + "0" + minutes;
                                    }
                                    else{
                                        flightTime = "0" + hours + ":" + minutes;
                                    }
                                }
                                else {
                                    if (minutes < 10){
                                        flightTime = hours + ":" + "0" + minutes;
                                    }
                                    else {
                                        flightTime = hours + ":" + minutes;
                                    }
                                }
                                double duration;
                                do{
                                    System.out.print("Enter flight duration (0-24): ");
                                    duration = scan.nextDouble();
                                }while (duration <= 0 || duration > 24);
                                flights[flightCount] = new Flight(date, first, business, economy, flightTime, duration);
                                data.add(flights[flightCount]); // added the flight the array list csv file
                                System.out.println("Flight added successfully:\n" + flights[flightCount]);
                                flightCount++;
                                break;
                            // heda l case 2 aka to edit the flight
                            case 2:
                                if (flightCount == 0)
                                {
                                    System.out.println("No flights available to edit.");
                                    break;
                                }

                                System.out.print("Enter the flight ID to edit: ");
                                int flightId = scan.nextInt();
                                boolean foundFlight = false;

                                // hayde for loop to search for the flight by ID
                                for (int i = 0; i < flightCount; i++)
                                {
                                    if (flights[i].getID() == flightId)
                                    {
                                        foundFlight = true;
                                        Flight flightToEdit = flights[i];

                                        System.out.println("Editing flight: " + flightToEdit);

                                        // hone we will edit flight date akid with validation
                                        String newDate;
                                        boolean validDateEdit = false;
                                        do {
                                            System.out.print("Enter new flight date (dd/MM/yyyy) or press enter to keep current date: ");
                                            scan.nextLine();
                                            newDate = scan.nextLine();

                                            if (newDate.isEmpty())
                                            {
                                                newDate = flightToEdit.getFlightDate(); // hayde to keep the current date if the user presses enter
                                                validDateEdit = true;
                                            } else
                                            {
                                                String[] parts = newDate.split("/");
                                                if (parts.length == 3)
                                                {
                                                    try
                                                    {
                                                        int day = Integer.parseInt(parts[0]);
                                                        int month = Integer.parseInt(parts[1]);
                                                        int year = Integer.parseInt(parts[2]);

                                                        if (day >= 1 && day <= 31 && month >= 1 && month <= 12 && parts[2].length() == 4) {
                                                            newDate = String.format("%02d/%02d/%04d", day, month, year);
                                                            validDateEdit = true;
                                                        }
                                                        else
                                                        {
                                                            System.out.println("Error: invalid date values.");
                                                        }
                                                    } catch (NumberFormatException e)
                                                    {
                                                        System.out.println("Error: invalid input. plz enter numbers only.");
                                                    }
                                                }
                                                else
                                                {
                                                    System.out.println("Error: invalid format. plz use dd/MM/yyyy.");
                                                }
                                            }
                                        } while (!validDateEdit);
                                        int newFirstClass = flights[i].getSeats(1);
                                        int newBusinessClass = flights[i].getSeats(2);
                                        int newEconomyClass = flights[i].getSeats(3);
                                        do
                                        {
                                            System.out.print("Enter flight departure hour(Military time, 0-23): ");
                                            hours = scan.nextInt();
                                            if (hours < 0 || hours > 23) System.out.println("Flight hours must be equal or greater than zero, less than 24");
                                        } while (hours < 0 || hours > 23);
                                        do
                                        {
                                            System.out.print("Enter flight departure minute (0-59): ");
                                            minutes = scan.nextInt();
                                            if (minutes < 0 || minutes > 59) System.out.println("Minutes must be equal or greater than 0, and less than 60");
                                        } while (minutes < 0 || minutes > 59);
                                        if (hours < 10){
                                            if (minutes < 10){
                                                flightTime = "0" + hours + ":" + "0" + minutes;
                                            }
                                            else{
                                                flightTime = "0" + hours + ":" + minutes;
                                            }
                                        }
                                        else {
                                            if (minutes < 10) {
                                                flightTime = hours + ":" + "0" + minutes;
                                            } else {
                                                flightTime = hours + ":" + minutes;
                                            }
                                        }
                                        do{
                                            System.out.print("Enter flight duration: ");
                                            duration = scan.nextDouble();
                                        }while (duration < 0 || duration > 24);
                                        // hon we update the flight
                                        flightToEdit.setFlightDate(newDate);
                                        flightToEdit.setFlightHours(flightTime);
                                        flightToEdit.setDuration(duration);
                                        for (int j = 0; j < passengerCount; j++) {
                                            if (t.get(j).getID() == flightToEdit.getID()) {
                                                t.get(j).setDate(newDate);
                                                t.get(j).setTime(flightTime);
                                                t.get(j).setDuration(duration);
                                            }
                                        }

                                        System.out.println("Flight updated successfully:\n");
                                        break;
                                    }
                                }

                                // eza flight not found, it will say an error
                                if (!foundFlight)
                                {
                                    System.out.println("Flight with ID " + flightId + " not found.");
                                }
                                break;
                            // end of case 2
                            case 3:
                                if (flightCount == 0)
                                {
                                    System.out.println("No flights available.");
                                } else
                                {
                                    for (int i = 0; i < flightCount; i++)
                                    {
                                        System.out.println(flights[i]);
                                        System.out.println("-------------------------");
                                    }
                                }
                                break;

                            case 4:
                                System.out.println("Returning to menu...");
                                break;

                            default:
                                System.out.println("Error: invalid choice.");
                        }
                    } while (subChoice != 4);
                    break;
                case 2:
                    do {
                        do {
                            System.out.println("-----Employee Management-----");
                            System.out.println("1- Add Employee");
                            System.out.println("2- Remove Employee");
                            System.out.println("3- Edit Employee Info");
                            System.out.println("4- Display Employees");
                            System.out.println("5- Assign Employee");
                            System.out.println("6- Return");
                            System.out.print("Enter Choice: ");
                            subChoice = scan.nextInt();
                        } while (subChoice < 1 || subChoice > 6);
                        switch (subChoice){
                            case 1:
                                String name;
                                int age;
                                double hours;
                                scan.nextLine();
                                do {
                                    System.out.print("Enter Full Name:" );
                                    name = scan.nextLine();
                                } while(!name.contains(" ")); // when name doesn't contain a space, repeat
                                do{
                                    System.out.print("Enter Age: ");
                                    age = scan.nextInt();
                                }while(age < 18);
                                do {
                                    System.out.print("Enter number of work hours: ");
                                    hours = scan.nextDouble();
                                }while (hours < 0);
                                do{
                                    System.out.print("1- Pilot\n" +
                                            "2- Co-Pilot\n" +
                                            "3- Flight Attendant\n" +
                                            "Enter Role: ");
                                    selection = scan.nextInt();
                                } while (selection > 3 || selection < 1);
                                if (selection == 1){
                                    Pilot p = new Pilot(name, age, "Pilot",false, hours);
                                    if (pilots.size() > 0){ // checks for duplicate IDs
                                        while (Search(p.getID()) != -1){
                                            p = new Pilot(name, age, "Pilot", false, hours);
                                        }
                                    }
                                    pilots.add(p);
                                    data.add(p);
                                    System.out.println("Pilot Added Successfully!" + "\nID: " + p.getID());
                                }
                                else if (selection == 2){
                                    Pilot p = new Pilot(name, age, "Co-Pilot",false, hours);
                                    if (pilots.size() > 0){
                                        while (Search(p.getID()) != -1){
                                            p = new Pilot(name, age, "Co-Pilot", false, hours);
                                        }
                                    }
                                    pilots.add(p);
                                    data.add(p); // adding pilot to array list for csv
                                    System.out.println("Co-Pilot Added Successfully!" + "\nID: " + p.getID());


                                }
                                else {
                                    int langs;
                                    ArrayList<String> languages = new ArrayList<>();
                                    do {
                                        System.out.print("Enter number of languages spoken: ");
                                        langs = scan.nextInt();
                                    } while (langs <= 0);
                                    for (int i = 0; i < langs; i++)
                                    {
                                        System.out.print("Enter Language " + (i+1) + ": ");
                                        languages.add(scan.next());
                                    }
                                    FlightAttendant fa = new FlightAttendant(name, age, "Flight Attendant",false, hours, languages);
                                    if (FAs.size() > 0){
                                        while (Search(fa.getID()) != -1){
                                            fa = new FlightAttendant(name, age, "Flight Attendant", false, hours, languages);
                                        }
                                    }
                                    FAs.add(fa);
                                    data.add(fa); // adding fa  to array list for csv
                                    System.out.println("Flight Attendant Added Successfully!"  + "\nID: " + fa.getID());
                                }
                                break;
                            case 2:
                                if(pilots.isEmpty() || FAs.isEmpty()){
                                    System.out.println("Please add a Pilot and an Attendant first.\n");
                                    break;
                                }
                                do {
                                    System.out.print("Enter ID of Employee to remove: ");
                                    selection = scan.nextInt();
                                }while (selection < 100000 || selection > 999999);
                                int searchIndex = Search(selection);
                                if (searchIndex != -1) {
                                    if (selection < 500000)
                                    {
                                        Employee toRemove = FAs.get(searchIndex);
                                        FAs.remove(searchIndex);
                                        data.remove(toRemove);
                                    }

                                    else
                                    {
                                        Employee toRemove = pilots.get(searchIndex);
                                        pilots.remove(searchIndex);
                                        data.remove(toRemove);
                                    }

                                    System.out.println("Employee removed successfully!");
                                }
                                else {
                                    System.out.println("Employee not found.");
                                }
                                break;
                            case 3:
                                if(pilots.isEmpty() || FAs.isEmpty()){
                                    System.out.println("Please add a Pilot and an Attendant first.\n");
                                    break;
                                }
                                do {
                                    System.out.print("Enter ID of Employee to edit: ");
                                    selection = scan.nextInt();
                                } while (selection < 100000 || selection > 999999);

                                int edit = Search(selection);
                                if (edit != -1) {
                                    int editChoice;
                                    do {
                                        System.out.println("What would you like to edit?");
                                        System.out.println("1- Name");
                                        System.out.println("2- Age");
                                        System.out.println("3- Work Hours");
                                        if (selection < 500000) {
                                            System.out.println("4- Languages");
                                        }
                                        System.out.println("5- Exit");
                                        do {
                                            System.out.print("Enter choice: ");
                                            editChoice = scan.nextInt();
                                        }while (editChoice > 5 || editChoice <= 0);

                                        switch (editChoice) {
                                            case 1:
                                                String newName;
                                                do {
                                                    System.out.print("Enter new Full Name: ");
                                                    scan.nextLine();
                                                    newName = scan.nextLine();
                                                } while (!newName.contains(" "));

                                                if (selection < 500000) {
                                                    FAs.get(edit).setName(newName);
                                                } else {
                                                    pilots.get(edit).setName(newName);
                                                }
                                                System.out.println("Name updated successfully!");
                                                break;

                                            case 2:
                                                int newAge;
                                                do {
                                                    System.out.print("Enter new Age: ");
                                                    newAge = scan.nextInt();
                                                } while (newAge < 18);

                                                if (selection < 500000) {
                                                    FAs.get(edit).setAge(newAge);
                                                } else {
                                                    pilots.get(edit).setAge(newAge);
                                                }
                                                System.out.println("Age updated successfully!");
                                                break;

                                            case 3:
                                                double newHours;
                                                do {
                                                    System.out.print("Enter new number of work hours: ");
                                                    newHours = scan.nextDouble();
                                                } while (newHours < 0);

                                                if (selection < 500000) {
                                                    FAs.get(edit).setHours(newHours);
                                                } else {
                                                    pilots.get(edit).setHours(newHours);
                                                }
                                                System.out.println("Work hours updated successfully!");
                                                break;

                                            case 4:
                                                if (selection < 500000) {
                                                    int newLangs;
                                                    ArrayList<String> newLanguages = new ArrayList<>();
                                                    do {
                                                        System.out.print("Enter new number of languages spoken: ");
                                                        newLangs = scan.nextInt();
                                                    } while (newLangs <= 0);

                                                    for (int i = 0; i < newLangs; i++) {
                                                        System.out.print("Enter Language " + (i+1) + ": ");
                                                        newLanguages.add(scan.next());
                                                    }
                                                    FAs.get(edit).setLanguages(newLanguages);
                                                    System.out.println("Languages updated successfully!");
                                                } else {
                                                    System.out.println("Invalid choice for Pilots/Co-Pilots.");
                                                }
                                                break;

                                            case 5:
                                                System.out.println("Returning to menu...");
                                                break;

                                            default:
                                                System.out.println("Invalid choice! Please enter a valid option.");
                                        }
                                    } while (editChoice != 5);
                                } else {
                                    System.out.println("Employee not found.");
                                }
                                break;
                            case 4:
                                System.out.println("\n----- All Employees -----");

                                if (pilots.isEmpty() && FAs.isEmpty()) {
                                    System.out.println("No employees found.");
                                } else {
                                    System.out.println("\n--- Pilots & Co-Pilots ---");
                                    for (Pilot p : pilots) {
                                        System.out.println(p);
                                    }

                                    System.out.println("\n--- Flight Attendants ---");
                                    for (FlightAttendant fa : FAs) {
                                        System.out.println(FAs);
                                    }
                                }
                                break;
                            case 5:
                                if(pilots.isEmpty() || FAs.isEmpty()){
                                    System.out.print("Please add a Pilot and an Attendant first.\n");
                                    break;
                                }
                                if(flightCount == 0){
                                    System.out.print("No Flights.");
                                    break;
                                }
                                do {
                                    System.out.println("Enter ID of Employee you wish to assign: ");
                                    selection = scan.nextInt();
                                } while (selection < 100000 || selection > 999999);
                                edit = Search(selection);
                                int assign = selection;
                                if (edit != -1){
                                    if(pilots.get(edit).isAssigned()){
                                        System.out.println("Pilot is already assigned.");
                                        break;
                                    }
                                    int errors = 0;
                                    do{
                                        errors++;
                                        System.out.println("Enter ID of plane you wish to be assigned to: ");
                                        selection = scan.nextInt();
                                        if (errors == 3)
                                            break;
                                    }while (selection < 1 || selection > flightCount);
                                    if (assign > 500000){
                                        if (!flights[selection - 1].addPilot(pilots.get(edit)))
                                        {
                                            System.out.println("Max amount reached.");
                                            break;
                                        }
                                        pilots.get(edit).setAssigned(true);
                                    }
                                    else {
                                        if (!flights[selection - 1].addAttendant(FAs.get(edit)))
                                        {
                                            System.out.println("Max amount reached.");
                                            break;
                                        }
                                        FAs.get(edit).setAssigned(true);
                                    }
                                }
                                else{
                                    System.out.println("Employee not found.");
                                    break;
                                }

                            default:
                                break;
                        }
                    }while (subChoice != 6);
                    break;
                case 3: // hayde l manage passenger li 3melto
                    if (flightCount == 0){
                        System.out.println("Please add a flight before assigning passengers.");
                        break;
                    }
                    do
                    {
                        do
                        {
                            System.out.println("-----Passenger Management-----");
                            System.out.println("1- Add Passenger");
                            System.out.println("2- Remove Passenger");
                            System.out.println("3- Edit Passenger Info");
                            System.out.println("4- Display Passengers");
                            System.out.println("5- Return");
                            System.out.print("Enter Choice: ");
                            subChoice = scan.nextInt();
                        } while (subChoice < 1 || subChoice > 5);

                        switch (subChoice)
                        {
                            case 1:
                                int errorCounter = 0;
                                if(passengerCount == passengers.length){
                                    doubleArraySize(passengers);
                                }
                                String name;
                                do {
                                    System.out.print("Enter name: ");
                                    scan.nextLine();
                                    name = scan.nextLine();
                                }while(!name.contains(" "));
                                // hayde validation lal age to make sure l user ma ykoun akbar men 150 cause no way houwe akbar men 150 years old
                                int age = 0;
                                boolean validAge = false;
                                do
                                {
                                    System.out.print("Enter age: ");
                                    if (scan.hasNextInt())
                                    {
                                        age = scan.nextInt();
                                        if (age >= 1 && age <= 150) // age must be between 1 and 150
                                        {
                                            validAge = true;
                                        } else {
                                            System.out.println("Error: invalid age. value must be between 1 and 150.");
                                        }
                                    } else {
                                        System.out.println("Error: invalid input. value must be between 1 and 150.");
                                        scan.next();  // heda to take the invalid input so ma bi sir fi infinite loop
                                    }
                                } while (!validAge);
                                int tempF;
                                int tempC;
                                String seat, level;
                                do {
                                    System.out.print("Enter Flight ID (1 -> " + flightCount + "): ");
                                    tempF = scan.nextInt();
                                    if ((flights[tempF-1].getSeats(1) + flights[tempF-1].getSeats(2) + flights[tempF-1].getSeats(3)) == 0){
                                        System.out.println("Flight is full (do 3 times to exit this menu).");
                                        tempF = -1;
                                        errorCounter++;
                                        if (errorCounter == 3){
                                            errorCounter = 0;
                                            break;
                                        }
                                    }
                                }while (tempF < 1 || tempF > flightCount);
                                do {

                                    System.out.print("Enter Seating Class (1 for First, 2 for Business, 3 for Economy): ");
                                    tempC = scan.nextInt();
                                    if(flights[tempF - 1].getSeats(tempC) == 0){
                                        System.out.println("Class full, pick a different one (do 3 times to exit this menu)");
                                        errorCounter++;
                                        tempC = -1;
                                        if (errorCounter == 3) {
                                            errorCounter = 0;
                                            break;
                                        }
                                    }
                                } while (tempC < 1 || tempF > 3);

                                if(tempC == 1){
                                    seat = "F" + flights[tempF - 1].getSeats(1);
                                    level = "First";
                                }
                                else if (tempC == 2) {
                                    seat = "B" + flights[tempF - 1].getSeats(2);
                                    level = "Business";
                                }
                                else {
                                    seat = "E" + flights[tempF - 1].getSeats(3);
                                    level = "Economy";
                                }
                                String phone;
                                Passenger passenger;
                                if (age >= 18){
                                    scan.nextLine();
                                    do {
                                        System.out.println("Enter Phone Number(xx-xxxxxx): ");
                                        phone = scan.nextLine();
                                    } while ((phone.charAt(2) != '-' && phone.length() < 9) || phone.length() > 9);
                                    passenger = new Passenger(name,age,"", phone);
                                }
                                else {
                                    passenger = new Passenger(name, age, "");
                                }
                                data.add(passenger);
                                passenger.setPassNb();
                                if(passengerCount != 0) {
                                    String duplicate = passenger.getPassNb();
                                    if (SearchP(duplicate) == 1) {
                                        do {
                                            passenger.setPassNb();
                                            duplicate = passenger.getPassNb();
                                        } while (SearchP(duplicate) == 1);
                                    }
                                }
                                String date,time;
                                date = flights[tempF-1].getFlightDate();
                                time = flights[tempF-1].getHours();
                                double duration = flights[tempF-1].getDuration();
                                Ticket ticket = new Ticket(tempF, level, seat, 0, passenger,date, time, duration);
                                t.add(ticket);
                                data.add(ticket);
                                flights[tempF-1].addTicket(tempC);
                                for (int i = 0; i < passengers.length; i++)
                                {
                                    if (passengers[i] == null)
                                    {
                                        passengers[i] = passenger;
                                        passengerCount++;
                                        System.out.println("Passenger added.");
                                        break;
                                    }
                                }
                                break;
                            case 2:
                                System.out.print("Enter passport number to remove: ");
                                scan.nextLine();
                                String passToRemove = scan.nextLine();
                                boolean found = false;


                                for (int i = 0; i < data.size(); i++) {
                                    if (data.get(i) instanceof Passenger &&
                                            ((Passenger) data.get(i)).getPassNb().equals(passToRemove)) {


                                        Passenger toRemove = (Passenger) data.get(i);


                                        Ticket ticketToRemove = null;
                                        for (Ticket ticketss : t) {
                                            if (ticketss.getPassenger().getPassNb().equals(passToRemove)) {
                                                ticketToRemove = ticketss;
                                                break;
                                            }
                                        }


                                        if (ticketToRemove != null) {
                                            String nb = ticketToRemove.getSeatNb();
                                            int id = ticketToRemove.getID();

                                            flights[id - 1].removeTicket(nb);
                                            t.remove(ticketToRemove);
                                        } else {
                                            System.out.println("No corresponding ticket found for this passenger.");
                                        }


                                        data.remove(i);


                                        for (int j = i; j < passengers.length - 1; j++) {
                                            passengers[j] = passengers[j + 1];
                                        }
                                        passengers[passengers.length - 1] = null;
                                        passengerCount--;

                                        System.out.println("Passenger removed.");
                                        found = true;
                                        break;
                                    }
                                }

                                if (!found) {
                                    System.out.println("Passenger with the provided passport number was not found.");
                                }

                            case 3:
                                System.out.print("Enter passport number to edit: ");
                                scan.nextLine();
                                String passToEdit = scan.nextLine();
                                for (int i = 0; i < passengers.length; i++)
                                {
                                    if (passengers[i] != null && passengers[i] instanceof Passenger && ((Passenger) passengers[i]).getPassNb().equals(passToEdit))
                                    {
                                        System.out.print("Enter new name: ");
                                        String newName = scan.nextLine();
                                        System.out.print("Enter new age: ");
                                        int newAge = scan.nextInt();
                                        passengers[i].setName(newName);
                                        passengers[i].setAge(newAge);
                                        t.get(i).setPassenger((Passenger)passengers[i]);
                                        System.out.println("Passenger updated.");
                                        break;
                                    }
                                }
                                break;
                            case 4:
                                for (int i = 0; i < passengerCount; i++)
                                {
                                    if (passengers[i] != null)
                                        System.out.println(t.get(i));
                                }
                                break;
                            case 5:
                                System.out.println("Returning to menu...");
                                break;
                            default:
                                System.out.println("Error: invalid choice.");
                                break;
                        }
                    } while (subChoice != 5);
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Error: invalid choice.");
                    break;
            }
        } while (mainChoice != 4);
        scan.close();
        // exiting animation
        // exiting animation metel bel c++
        System.out.print("Exiting the program");
        Thread.sleep(1000);
        System.out.print(".");
        Thread.sleep(1000);
        System.out.print(".");
        Thread.sleep(1000);
        System.out.print(".");
        Thread.sleep(1000);

        System.out.println();

        System.out.println("Have a great day/night <3");
        Thread.sleep(1000);

        System.out.println("Thank you for using our program :)");
        Thread.sleep(1000);

        System.out.println("Program created by the legendary coding duo, the two most amazing humans to ever code: Joe & Paul!");
        Thread.sleep(1000);

        writeDataToCSV(data);
        System.out.println("Data saved and program exited.");
    }
    public static int Search(int ID){
        if (ID >= 100000 && ID < 500000){
            for(int i = 0; i < FAs.size(); i++){
                if (FAs.get(i).getID() == ID){
                    return i;
                }
            }
        }
        else if(ID >= 500000 && ID <= 999999){
            for(int i = 0; i < pilots.size(); i++){
                if (pilots.get(i).getID() == ID){
                    return i;
                }
            }
        }
        return -1;
    }
    public static int SearchP(String ID){
        for (int i = 0; i < passengerCount; i++){
            if (ID.equals(passengers[i].getPassNb())){
                return 1;
            }
        }
        return -1;
    }
    public static Person[] doubleArraySize(Person[] passengers) {
        Person[] newArray = new Passenger[passengers.length * 2];

        // Copy existing elements into the new array
        for (int i = 0; i < passengers.length; i++) {
            newArray[i] = passengers[i];
        }

        return newArray;
    }

}