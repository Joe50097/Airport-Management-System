import java.text.NumberFormat;
import java.util.Locale;
import java.util.*;

public class FlightAttendant extends Employee implements CSVConvertible {
    Random generator = new Random();
    NumberFormat dollarSign = NumberFormat.getCurrencyInstance(Locale.US);
    private double hours, salary = 0;
    private int ID;
    private ArrayList<String> languages = new ArrayList<>();

    public FlightAttendant(String name, int age, String role, boolean assigned, double hours, ArrayList<String> languages){
        super(name, age, role, assigned);
        setHours(hours);
        this.ID = (generator.nextInt(400000) + 100000);
        setLanguages(languages);
    }

    public ArrayList<String> getLanguages() {
        return languages;
    }

    public void setLanguages(ArrayList<String> languages) {
        this.languages = languages;
    }

    public String printLanguages() {
        String spoken = "";
        for (int i = 0; i < languages.size(); i++) {
            spoken += (i + 1) + "- " + languages.get(i);

            // Adds comma if not last element
            if (i < languages.size() - 1) {
                spoken += ", ";
            }
        }
        return spoken;
    }

    public boolean addLanguage(String Add) {
        if (!languages.contains(Add)) { //check if language available
            return languages.add(Add);
        }
        return false;
    }

    public boolean removeLanguage(String Remove) {
        return languages.remove(Remove);
    }

    public void calculateSalary(double hours){
        if(getRole().equalsIgnoreCase("Flight Attendant")) {
            salary += (languages.size() * 100);
            if (hours <= 75)
                salary += (hours * 30);
            if (hours > 75 && hours < 125)
                salary += ((hours * 30) + ((hours - 75) * 60));
            if (hours > 125)
                salary += ((hours * 30) + ((hours - 75) * 60) + ((hours - 125) * 100));
        }
    }

    public String getSalary(){ // Here it's a string since number-format turns the number into a string
        return dollarSign.format(salary);
    }

    public void setHours(double hours) {
        this.hours = hours;
        calculateSalary(this.hours);
    }

    public double getHours() {
        return hours;
    }

    public void setID(int id) {
        this.ID = id;
    }

    public int getID() {
        return ID;
    }

    @Override
    public String toString() {
        String spoken = "";
        for (int i = 0; i < languages.size(); i++){
            spoken += (i+1) + "- " + languages.get(i) + "\n";
        }
        return super.toString() + "ID: " + ID + "\nSalary: " + getSalary() + "\nLanguages Spoken: " + spoken;
    }

    @Override
    public String toCSV() {
        String languagesCSV = String.join(";", languages); // Join languages with semicolon for CSV
        return "FlightAttendant," + getName() + "," + getAge() + "," + getRole() + "," + (isAssigned() ? "Yes" : "No") + "," + getID() + "," + getHours() + "," + languagesCSV;
    }

    @Override
    public void fromCSV(String csvData) {
        String[] data = csvData.split(",");


        setName(data[1]);
        setAge(Integer.parseInt(data[2]));
        setRole(data[3]);
        setAssigned(data[4].equals("Yes"));
        setID(Integer.parseInt(data[5]));
        setHours(Double.parseDouble(data[6]));

        if (data.length > 7) {
            String[] langs = data[7].split(";");
            languages = new ArrayList<>(Arrays.asList(langs));
        } else {
            languages = new ArrayList<>();
        }
    }


}