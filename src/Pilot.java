import java.util.*;
import java.text.NumberFormat;

public class Pilot extends Employee implements CSVConvertible {
    Random generator = new Random();
    NumberFormat dollarSign = NumberFormat.getCurrencyInstance(Locale.US);
    private double hours, salary = 0;
    private int ID;

    public Pilot(String name, int age, String role, boolean assigned, double hours) {
        super(name, age, role, assigned);
        this.ID = (generator.nextInt(500000) + 500000);
        setHours(hours);
    }

    public void calculateSalary(double hours) {
        salary = 0;
        if (getRole().equalsIgnoreCase("pilot")) {
            if (hours <= 75)
                salary += (hours * 90);
            if (hours > 75 && hours < 125)
                salary += ((75 * 90) + ((hours - 75) * 120));
            if (hours > 125)
                salary += ((75 * 90) + ((hours - 75) * 120) + ((hours - 125) * 200));
        } else if (getRole().equalsIgnoreCase("co-pilot")) {
            if (hours <= 75)
                salary += (hours * 60);
            if (hours > 75 && hours < 125)
                salary += ((75 * 60) + ((hours - 75) * 90));
            if (hours > 125)
                salary += ((75 * 60) + ((hours - 75) * 90) + ((hours - 125) * 150));
        }
    }

    public String getSalary() { // Here it's a string since number-format turns the number into a string
        return dollarSign.format(salary);
    }

    public void setHours(double hours) {
        this.hours = hours;
        calculateSalary(this.hours);
    }

    public double getHours() {
        return hours;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    @Override
    public String toString()
    {
        return super.toString() + "ID: " + ID + "\nSalary: " + getSalary() + "\n";
    }

    // heda l CSVConvertible interface methods
    @Override
    public String toCSV()
    {
        return "Pilot," + getName() + "," + getAge() + "," + getRole() + "," + isAssigned() + "," + getID() + "," + getHours();
    }

    @Override
    public void fromCSV(String csvData) {
        String[] data = csvData.split(",");
        // skip index 0 (class name)
        setName(data[1]);
        setAge(Integer.parseInt(data[2]));
        setRole(data[3]);
        setAssigned(Boolean.parseBoolean(data[4]));
        setID(Integer.parseInt(data[5])); // Set the ID from CSV
        setHours(Double.parseDouble(data[6]));
    }

}