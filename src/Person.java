public class Person {
    private String name;
    private int age;
    public Person (String name, int age){
        setName(name);
        setAge(age);
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return "Name: " + name + "\nAge: " + age + "\n";
    }

    public String getPassNb() {
        return null;
    }
}