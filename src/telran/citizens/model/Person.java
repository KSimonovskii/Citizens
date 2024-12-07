package telran.citizens.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Person implements Comparable<Person>{
    private final int id;
    private String firstName;
    private String lastName;
    private final LocalDate birthDate;

    public Person(int id, String firstName, String lastName, LocalDate birthDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge(){
        LocalDate now = LocalDate.now();
        return (int) ChronoUnit.YEARS.between(birthDate, now);
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("Person -> ID: ").append(id)
                .append(", first name: ").append(firstName)
                .append(", last name: ").append(lastName)
                .append(", age: ").append(getAge());
        return res.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return getId() == person.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public int compareTo(Person o) {
        return Integer.compare(getId(), o.getId());
    }
}
