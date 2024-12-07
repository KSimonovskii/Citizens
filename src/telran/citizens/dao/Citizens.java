package telran.citizens.dao;

import telran.citizens.model.Person;

public interface Citizens {

    boolean add(Person person);
    boolean remove(int id);
    Person find(int id);

    // minAge include, maxAge include
    Iterable<Person> find(int minAge, int maxAge);
    Iterable<Person> find(String lastName);
    Iterable<Person> getAllPersonsSortedById();
    Iterable<Person> getAllPersonsSortedByAge();
    Iterable<Person> getAllPersonsSortedByLastName();
    int size();

}
