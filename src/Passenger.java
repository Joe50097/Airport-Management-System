import java.util.Random;

public class Passenger extends Person implements CSVConvertible {
    private String passNb, phone; //passport number
    Random gen = new Random();

    public Passenger(String name, int age, String passNb, String phone) {
        super(name, age);
        if (passNb == null || passNb.isEmpty()) {
            setPassNb(); // generate new
        } else {
            this.passNb = passNb; // use provided
        }
        this.phone = phone;
    }

    public Passenger(String name, int age, String passNb) { // overload for underage
        super(name, age);
        if (passNb == null || passNb.isEmpty()) {
            setPassNb();
        } else {
            this.passNb = passNb;
        }
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPassNb() {

        String name = super.getName();
        if (name == null || name.isEmpty()) {
            this.passNb = "U" + (gen.nextInt(9000) + 1000); // Default prefix "U" for unknown name
        } else {
            this.passNb = name.charAt(0) + "" + (gen.nextInt(9000) + 1000);
        }
    }

    @Override
    public String getPassNb() {
        return passNb;
    }

    @Override
    public String toString() {
        if(super.getAge() >= 18){
            return super.toString() + "Passport Number: " + passNb + "\nPhone: " + phone + "\n";
        }
        else {
            return super.toString() + "Passport Number: " + passNb + "\n";
        }
    }

    @Override
    public String toCSV() {
        return "Passenger," + super.getName() + "," + super.getAge() + "," + passNb + "," + (phone != null ? phone : "Unknown");
    }

    @Override
    public void fromCSV(String csvData) {
        String[] data = csvData.split(",");

        super.setName(data[1]);
        super.setAge(Integer.parseInt(data[2]));
        passNb = data[3];


        if (data.length > 4 && data[4].matches("\\d{2}-\\d{6}")) {
            phone = data[4];
        } else if (data.length > 4 && data[4].matches("\\d{8}")) {
            phone = data[4].substring(0, 2) + "-" + data[4].substring(2); // Convert XXXXXXXX -> XX-XXXXXX
        } else {
            System.out.println("Warning: Invalid phone number format. Setting default.");
            phone = "00-000000";
        }
    }


}