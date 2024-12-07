package telran.citizens.test;

import org.junit.jupiter.api.BeforeEach;
import telran.citizens.dao.Citizens;
import telran.citizens.dao.CitizensImpl;
import telran.citizens.model.Person;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CitizensTest {

    private Citizens citizens;

    @BeforeEach
    void SetUp(){

        citizens = new CitizensImpl();
        citizens.add(new Person(1, "Ozzy", "Osborn", LocalDate.of(1986, 5, 5)));
        citizens.add(new Person(2, "Angus", "Young", LocalDate.of(1943, 2, 21)));
        citizens.add(new Person(3, "David", "Gilmour", LocalDate.of(1993, 11, 10)));
        citizens.add(new Person(4, "Jan", "Gillan", LocalDate.of(1972, 6, 30)));
        citizens.add(new Person(5, "Jimmy", "Hendrix", LocalDate.of(2010, 3, 25)));
    }

    @org.junit.jupiter.api.Test
    void testAdd() {

        Person newPerson1 = new Person(5, "Bob", "Marley", LocalDate.of(2015, 4, 1));
        Person newPerson2 = new Person(6, "Bob", "Marley", LocalDate.of(2015, 4, 1));

        assertFalse(citizens.add(newPerson1));
        assertFalse(citizens.add(null));
        assertEquals(5, citizens.size());

        assertTrue(citizens.add(newPerson2));
        assertEquals(6, citizens.size());
    }

    @org.junit.jupiter.api.Test
    void testRemove() {
        assertTrue(citizens.remove(3));
        assertEquals(4, citizens.size());

        assertFalse(citizens.remove(10));
    }

    @org.junit.jupiter.api.Test
    void testFindById() {

        assertNotNull(citizens.find(3));
        assertNull(citizens.find(10));

    }

    @org.junit.jupiter.api.Test
    void testFindByAge() {

        Iterable<Person> res = citizens.find(20, 50);
        List<Person> expected = List.of(citizens.find(3), citizens.find(1), citizens.find(4));

        assertIterableEquals(expected, res);

        res = citizens.find(10, 14);
        expected = List.of(citizens.find(5));
        assertIterableEquals(expected, res);

        res = citizens.find(60, 70);
        assertIterableEquals(new ArrayList<>(), res);
    }

    @org.junit.jupiter.api.Test
    void testFindByLastName() {

        Person newPerson = new Person(6, "Bob", "Gilmour", LocalDate.of(2015, 4, 1));
        citizens.add(newPerson);
        Iterable<Person> res = citizens.find("Gilmour");

        List<Person> expected = List.of(newPerson, citizens.find(3));
        assertIterableEquals(expected, res);

        res = citizens.find("Lennon");
        assertIterableEquals(new ArrayList<>(), res);
    }

    @org.junit.jupiter.api.Test
    void testGetAllPersonsSortedById() {

        Iterable<Person> res = citizens.getAllPersonsSortedById();

        List<Person> expected = new ArrayList<>();
        for (int id = 1; id < 6; id++) {
            expected.add(citizens.find(id));
        }

        assertIterableEquals(expected, res);

    }

    @org.junit.jupiter.api.Test
    void testGetAllPersonsSortedByAge() {

        Comparator<Person> comparator = (p1, p2) -> Integer.compare(p1.getAge(), p2.getAge());
        Iterable<Person> res = citizens.getAllPersonsSortedByAge();

        boolean succeed = true;
        int counter = 0;
        Person prevPerson = null;

        for (Person person : res) {
            counter++;
            if (prevPerson == null) {
                prevPerson = person;
                continue;
            }

            succeed = succeed && comparator.compare(person, prevPerson) > 0;
        }

        assertTrue(succeed);
        assertEquals(citizens.size(), counter);
    }

    @org.junit.jupiter.api.Test
    void testGetAllPersonsSortedByLastName() {

        Comparator<Person> comparator = (p1, p2) -> p1.getLastName().compareTo(p2.getLastName());
        Iterable<Person> res = citizens.getAllPersonsSortedByLastName();

        boolean succeed = true;
        int counter = 0;
        Person prevPerson = null;

        for (Person person : res) {
            counter++;
            if (prevPerson == null) {
                prevPerson = person;
                continue;
            }

            succeed = succeed && comparator.compare(person, prevPerson) > 0;
        }

        assertTrue(succeed);
        assertEquals(citizens.size(), counter);
    }

    @org.junit.jupiter.api.Test
    void testSize() {
        assertEquals(5, citizens.size());
    }
}