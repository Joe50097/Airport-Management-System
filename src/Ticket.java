public class Ticket implements CSVConvertible {
    private int ID;
    private String level, seatNb, date, time; // level as in first, business, or eco class
    private double price;
    private Passenger passenger;
    private double duration;

    public Ticket(int ID, String level, String seatNb, double price, Passenger passenger, String date, String time, double duration) {
        setID(ID);
        setLevel(level);
        setPassenger(passenger);
        setDuration(duration);
        if (level.equalsIgnoreCase("Economy")) {
            this.price = 100 * duration;
        } else if (level.equalsIgnoreCase("Business")) {
            this.price = 400 * duration;
        } else {
            this.price = 800 * duration;
        }
        setSeatNb(seatNb);
        setDate(date);
        setTime(time);
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    public void setSeatNb(String seatNb) {
        this.seatNb = seatNb;
    }

    public String getSeatNb() {
        return seatNb;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getDuration() {
        return duration;
    }

    public int getID() {
        return this.ID;
    }

    public String toString() {
        return "Flight ID: " + ID + "\nClass: " + level + "\nSeat Number: " + seatNb + "\nPrice: " + price + "\nDate: " + date + "\nTime: " + time + "\nDuration: " + duration + "\nPassenger: " + passenger + "\n";
    }



    @Override
    public String toCSV() {

        return "Ticket,"
                + ID + ","
                + level + ","
                + seatNb + ","
                + price + ","
                + passenger.getName() + ","
                + passenger.getAge() + ","
                + passenger.getPassNb() + ","
                + passenger.getPhone() + ","  // Save phone number here
                + date + ","
                + time + ","
                + duration;
    }

    @Override
    public void fromCSV(String csvData) {
        String[] data = csvData.split(",");

        setID(Integer.parseInt(data[1]));
        setLevel(data[2]);
        setSeatNb(data[3]);

        // Read the duration from the CSV (last field)
        double loadedDuration = Double.parseDouble(data[11]);
        setDuration(loadedDuration);

        // Calculate price by level based on duration
        if (getLevel().equalsIgnoreCase("Economy")) {
            setPrice(100 * loadedDuration);
        } else if (getLevel().equalsIgnoreCase("Business")) {
            setPrice(400 * loadedDuration);
        } else {
            setPrice(800 * loadedDuration);
        }


        String passengerName = data[5];
        int passengerAge = Integer.parseInt(data[6]);
        String passportNumber = data[7];
        String passengerPhone = data[8];


        Passenger passenger = new Passenger(passengerName, passengerAge, passportNumber, passengerPhone);
        setPassenger(passenger);

        setDate(data[9]);
        setTime(data[10]);
    }
}