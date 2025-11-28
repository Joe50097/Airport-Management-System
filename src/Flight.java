import java.text.NumberFormat;
import java.util.Locale;
import java.util.*;

public class Flight implements CSVConvertible {
    private static int flightCounter = 1; // to generate flight IDs for each flight
    private int ID;
    private double duration;
    private String flightDate;
    private int firstClassSeats, businessClassSeats, economyClassSeats;
    private Pilot[] pilots = new Pilot[2];
    private FlightAttendant[] attendants = new FlightAttendant[5];
    private String flightHours;
    private int orgFCS, orgBCS, orgECS, pilotCounter = 0, attendantCounter = 0;


    public Flight(String flightDate, int firstClassSeats, int businessClassSeats, int economyClassSeats, String flightHours, double duration) {
        this.ID = flightCounter++;

        if (flightDate != null &&
                flightDate.length() == 10 &&
                flightDate.charAt(2) == '/' &&
                flightDate.charAt(5) == '/' &&
                Character.isDigit(flightDate.charAt(0)) &&
                Character.isDigit(flightDate.charAt(1)) &&
                Character.isDigit(flightDate.charAt(3)) &&
                Character.isDigit(flightDate.charAt(4)) &&
                Character.isDigit(flightDate.charAt(6)) &&
                Character.isDigit(flightDate.charAt(7)) &&
                Character.isDigit(flightDate.charAt(8)) &&
                Character.isDigit(flightDate.charAt(9))) {
            this.flightDate = flightDate;
        } else {
            this.flightDate = "14/07/2006"; // default date (my birthday)
        }
        this.firstClassSeats = firstClassSeats;
        this.businessClassSeats = businessClassSeats;
        this.economyClassSeats = economyClassSeats;
        this.flightHours = flightHours;
        this.duration = duration;
        orgFCS = firstClassSeats;
        orgBCS = businessClassSeats;
        orgECS = economyClassSeats;
    }


    public boolean addPilot(Pilot pilot) {
        for (int i = 0; i < pilots.length; i++) {
            if (pilots[i] == null) {
                pilots[i] = pilot;
                pilotCounter++;
                return true;
            }
        }
        return false;
    }


    public boolean addFlight(Flight flight) {
        if (flight.getSeats(1) > 0 || flight.getSeats(2) > 0 || flight.getSeats(3) > 0) {
            System.out.println("Flight " + flight.getID() + " added successfully.");
            return true;
        } else {
            System.out.println("Flight " + flight.getID() + " is full.");
            return false;
        }
    }


    public boolean addTicket(int flightClassType) {
        switch (flightClassType) {
            case 1: // first class
                if (firstClassSeats > 0) {
                    firstClassSeats--;
                    return true;
                }
                break;
            case 2: // business class
                if (businessClassSeats > 0) {
                    businessClassSeats--;
                    return true;
                }
                break;
            case 3: // economy class
                if (economyClassSeats > 0) {
                    economyClassSeats--;
                    return true;
                }
                break;
            default:
                return false;
        }
        return false;
    }


    public void removeTicket(String seatNb) {
        if (seatNb.contains("F")) {
            firstClassSeats++;
        } else if (seatNb.contains("B")) {
            businessClassSeats++;
        } else {
            economyClassSeats++;
        }
    }


    public boolean addAttendant(FlightAttendant attendant) {
        for (int i = 0; i < attendants.length; i++) {
            if (attendants[i] == null) {
                attendants[i] = attendant;
                attendantCounter++;
                return true;
            }
        }
        return false;
    }


    public String getHours() {
        return flightHours;
    }


    public int getID() {
        return ID;
    }

    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }

    public void setFlightHours(String flightHours) {
        this.flightHours = flightHours;
    }


    public String getFlightDate() {
        return flightDate;
    }


    public void setDuration(double duration) {
        this.duration = duration;
    }


    public double getDuration() {
        return duration;
    }


    public int getSeats(int flightClassType) {
        switch (flightClassType) {
            case 1:
                return firstClassSeats;
            case 2:
                return businessClassSeats;
            case 3:
                return economyClassSeats;
            default:
                return 0;
        }
    }

    // toString method
    @Override
    public String toString() {
        String pilot = "", attendant = "";
        for (int i = 0; i < pilotCounter; i++) {
            pilot += (i + 1) + "- " + pilots[i].getName() + "\n";
        }
        for (int i = 0; i < attendantCounter; i++) {
            attendant += (i + 1) + "- " + attendants[i].getName() + "\n";
        }

        String result = "Flight ID: " + ID + "\nDate: " + flightDate + "\nFlight Time: " + flightHours + "\nDuration: " + duration + "\nAvailable First Class Seats (Total: " + orgFCS + "): " + firstClassSeats +
                "\nAvailable Business Class Seats (Total: " + orgBCS + "): " + businessClassSeats + "\nAvailable Economy Class Seats (Total: " + orgECS + "): " + economyClassSeats +
                "\nPilots: " + pilot + "\nFlight Attendants: " + attendant + "\n";
        return result;
    }


    @Override
    public String toCSV() {
        String pilotsCSV = "";
        for (int i = 0; i < pilotCounter; i++) {
            pilotsCSV += pilots[i].getName() + ";";
        }

        String attendantsCSV = "";
        for (int i = 0; i < attendantCounter; i++) {
            attendantsCSV += attendants[i].getName() + ";";
        }
        return "Flight," + ID + "," + flightDate + "," + flightHours + "," + duration + "," +
                firstClassSeats + "," + businessClassSeats + "," + economyClassSeats + "," +
                orgFCS + "," + orgBCS + "," + orgECS + "," +
                pilotsCSV + "," + attendantsCSV;
    }


    @Override
    public void fromCSV(String csvData) {
        String[] data = csvData.split(",");

        this.ID = Integer.parseInt(data[1]);
        this.flightDate = data[2];
        this.flightHours = data[3];
        this.duration = Double.parseDouble(data[4]);
        this.firstClassSeats = Integer.parseInt(data[5]);
        this.businessClassSeats = Integer.parseInt(data[6]);
        this.economyClassSeats = Integer.parseInt(data[7]);


        this.orgFCS = Integer.parseInt(data[8]);
        this.orgBCS = Integer.parseInt(data[9]);
        this.orgECS = Integer.parseInt(data[10]);


        if (data.length > 11 && !data[11].isEmpty()) {
            String[] pilotsData = data[11].split(";");
            for (String pilotName : pilotsData) {
                if (!pilotName.isEmpty()) {
                    Pilot pilot = new Pilot(pilotName, 30, "Pilot", true, 80); // placeholder values
                    addPilot(pilot);
                }
            }
        }


        if (data.length > 12 && !data[12].isEmpty()) {
            String[] attendantsData = data[12].split(";");
            for (String attendantName : attendantsData) {
                if (!attendantName.isEmpty()) {
                    FlightAttendant attendant = new FlightAttendant(attendantName, 30, "Flight Attendant", true, 80, new ArrayList<>());
                    addAttendant(attendant);
                }
            }
        }
    }
}