package telran.citizens.dao;

import telran.citizens.model.Person;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
public class CitizensImpl implements Citizens{

    private List<Person> idList;
    private List<Person> lastNameList;
    private List<Person> ageList;
    public static Comparator<Person> lastNameComparator;
    public static Comparator<Person> ageComparator;
    private static Comparator<Person> idComparator;

    public CitizensImpl() {
        idList = new ArrayList<>();
        lastNameList = new ArrayList<>();
        ageList = new ArrayList<>();

        idComparator = (p1, p2) -> Integer.compare(p1.getId(), p2.getId());
        lastNameComparator = (p1, p2) -> p1.getLastName().compareTo(p2.getLastName());
        ageComparator = (p1, p2) -> Integer.compare(p1.getAge(), p2.getAge());
    }

    public CitizensImpl(List<Person> citizens){

        this();

        for (Person person : citizens) {
            add(person);
        }
    }


    @Override
    public boolean add(Person person) {

        if (person == null
                || find(person.getId()) != null) {
            return false;
        }

        insertToList(person, idList, idComparator);
        insertToList(person, lastNameList, lastNameComparator);
        insertToList(person, ageList, ageComparator);

        return true;
    }

    private void insertToList(Person person, List<Person> list, Comparator<Person> comparator){
        int index = Collections.binarySearch(list, person, comparator);
        index = index >= 0? index: -index - 1;
        list.add(index, person);
    }
    @Override
    public boolean remove(int id) {

        Person person = find(id);
        if (person == null){
            return false;
        }

        return idList.remove(person)
                && lastNameList.remove(person)
                && ageList.remove(person);
    }

    @Override
    public Person find(int id) {
        Person pattern = new Person(id, "", "", LocalDate.now());

        int index = Collections.binarySearch(idList, pattern, idComparator);
        return index < 0? null: idList.get(index);
    }

    @Override
    public Iterable<Person> find(int minAge, int maxAge) {

        if (minAge > maxAge) {
            return new ArrayList<>();
        }

        LocalDate now = LocalDate.now();

        Person patternFrom = new Person(Integer.MIN_VALUE, "", "", now.minusYears(minAge));
        Person patternTo = new Person(Integer.MIN_VALUE, "", "", now.minusYears(maxAge));

        int indexFrom = Collections.binarySearch(ageList, patternFrom, ageComparator);
        int indexTo = Collections.binarySearch(ageList, patternTo, ageComparator);

        if (indexFrom == indexTo && indexFrom < 0){
            return new ArrayList<>();
        }

        indexFrom = indexFrom >= 0 ? indexFrom : -indexFrom - 1;
        indexTo = indexTo >= 0 ? indexTo : -indexTo - 1;

       List<Person> res = ageList.subList(indexFrom, Math.min(indexTo + 1, ageList.size()));

       return new CitizensImpl(res).ageList;
    }

    @Override
    public Iterable<Person> find(String lastName) {

        Person pattern = new Person(Integer.MIN_VALUE, "", lastName, LocalDate.now());

        List<Person> res = new ArrayList<>();
        for (Person person : lastNameList) {
            if (lastNameComparator.compare(person, pattern) > 0){
                break;
            }

            if (lastName.equals(person.getLastName())){
                res.add(person);
            }
        }

        return res;
    }

    @Override
    public Iterable<Person> getAllPersonsSortedById() {
        return idList;
    }

    @Override
    public Iterable<Person> getAllPersonsSortedByAge() {
        return ageList;
    }

    @Override
    public Iterable<Person> getAllPersonsSortedByLastName() {
        return lastNameList;
    }

    @Override
    public int size() {
        return idList.size();
    }
}
