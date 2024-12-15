package telran.citizens.dao;

import telran.citizens.model.Person;

import java.time.LocalDate;
import java.util.*;

public class CitizensImpl implements Citizens{

    private TreeSet<Person> idList;
    private TreeSet<Person> lastNameList;
    private TreeSet<Person> ageList;
    public static Comparator<Person> lastNameComparator;
    public static Comparator<Person> ageComparator;
    private static Comparator<Person> idComparator = (p1, p2) -> Integer.compare(p1.getId(), p2.getId());

    static {
        lastNameComparator = (p1, p2) -> {
            int res = p1.getLastName().compareTo(p2.getLastName());
            return res != 0 ? res : Integer.compare(p1.getId(), p2.getId());
        };
        ageComparator = (p1, p2) -> {
            int res = Integer.compare(p1.getAge(), p2.getAge());
            return res != 0 ? res : Integer.compare(p1.getId(), p2.getId());
        };
    }
    public CitizensImpl() {
        idList = new TreeSet<>((p1, p2) -> Integer.compare(p1.getId(), p2.getId()));
        lastNameList = new TreeSet<>(lastNameComparator);
        ageList = new TreeSet<>(ageComparator);
    }

    //O(1)
    public CitizensImpl(List<Person> citizens){

        this();

        HashSet<Person> tmpHash = new HashSet<>(citizens);
        tmpHash.removeIf((p) -> p == null);

        idList.addAll(tmpHash);
        lastNameList.addAll(tmpHash);
        ageList.addAll(tmpHash);
    }

    //O(1)
    @Override
    public boolean add(Person person) {

        if (person == null
                || idList.contains(person)) {
            return false;
        }

        idList.add(person);
        ageList.add(person);
        lastNameList.add(person);

        return true;
    }

    //O(1)
    @Override
    public boolean remove(int id) {

        Person victim = find(id);
        if (victim == null){
            return false;
        }

        idList.remove(victim);
        lastNameList.remove(victim);
        ageList.remove(victim);
        return true;
    }

    //O(1)
    @Override
    public Person find(int id) {

        Person pattern = new Person(id, "", "", null);

        Person res = idList.ceiling(pattern);

        if (pattern.equals(res)){
            return res;
        }

        return null;
    }

    //O(1)
    @Override
    public Iterable<Person> find(int minAge, int maxAge) {

        if (minAge > maxAge) {
            return new ArrayList<>();
        }

        LocalDate now = LocalDate.now();

        Person patternFrom = new Person(Integer.MIN_VALUE, "", "", now.minusYears(minAge));
        Person patternTo = new Person(Integer.MAX_VALUE, "", "", now.minusYears(maxAge));

        return ageList.subSet(patternFrom, true, patternTo, true);
    }

    //O(1)
    @Override
    public Iterable<Person> find(String lastName) {

        Person pattern1 = new Person(Integer.MIN_VALUE, "", lastName, null);
        Person pattern2 = new Person(Integer.MAX_VALUE, "", lastName, null);
        return lastNameList.subSet(pattern1, true, pattern2, false);
    }

    //O(1)
    @Override
    public Iterable<Person> getAllPersonsSortedById() {
        return idList;
    }

    //O(1)
    @Override
    public Iterable<Person> getAllPersonsSortedByAge() {
        return ageList;
    }

    //O(1)
    @Override
    public Iterable<Person> getAllPersonsSortedByLastName() {
        return lastNameList;
    }

    @Override
    public int size() {
        return idList.size();
    }
}
