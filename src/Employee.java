public class Employee extends Person implements CSVConvertible {
    private String role;
    private boolean assigned;

    public Employee(String name, int age, String role, boolean assigned) {
        super(name, age);
        setRole(role);
        setAssigned(assigned);
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

    public boolean isAssigned() {
        return assigned;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        String assign = "";
        if (isAssigned())
            assign = "Yes";
        else
            assign = "No";
        return super.toString() + "Role: " + role + "\nAssigned: " + assign + "\n";
    }

    // hayde to implement the toCSV method from CSVConvertible
    @Override
    public String toCSV()
    {
        return "Employee," + getName() + "," + getAge() + "," + role + "," + assigned;
    }

    // hayde to implement the fromCSV method from CSVConvertible
    @Override
    public void fromCSV(String csvData)
    {
        String[] data = csvData.split(",");
        setName(data[0]);
        setAge(Integer.parseInt(data[1]));
        setRole(data[2]);
        setAssigned(Boolean.parseBoolean(data[3]));
    }
}