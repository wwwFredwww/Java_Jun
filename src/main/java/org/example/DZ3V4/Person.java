package org.example.DZ3V4;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Person {

    private String personName;
    private int personAge;
    private boolean personActive;
    private long departmentId;

    List<String> names = List.of("Vasya", "Petya", "Sasha", "Dasha", "Lena", "Olya", "Vlad");

    public Person(long departmentId) {
        this.departmentId = departmentId;
        setPersonName(names);
        setPersonAge();
        setPersonActive();
    }

    public Person(int personId, String personName, int personAge, boolean personAction, long departmentId) {
        this.personName = personName;
        this.personAge = personAge;
        this.personActive = personAction;
        this.departmentId = departmentId;
    }

    private static <T> T getRandom(List<? extends T> items) {
        int index = ThreadLocalRandom.current().nextInt(0, items.size());
        return items.get(index);
    }

    public void setPersonName(List<String> names) {
        this.personName = getRandom(names);
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonAge() {
        this.personAge = ThreadLocalRandom.current().nextInt(18, 75);
    }

    public int getPersonAge() {
        return personAge;
    }

    public void setPersonActive() {
        this.personActive = ThreadLocalRandom.current().nextBoolean();
    }

    public boolean getPersonActive() {
        return personActive;
    }

    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public String toString() {
        return "Person{" +
                "personName='" + personName + '\'' +
                ", personAge=" + personAge +
                ", personActive=" + personActive +
                ", departmentId=" + departmentId +
                '}';
    }
}


