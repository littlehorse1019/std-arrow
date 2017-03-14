package test.others;

import java.util.Arrays;
import java.util.Comparator;

class Person {

    private final String name;
    private final int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public static void main(String[] args) {
        Person[] people = {new Person("luoxiao", 29), new Person("huanglu", 27)};
//		Comparator<Person> byName = Comparator.comparing(p -> p.getName());
        Comparator<Person> byName = Comparator.comparing(Person::getName);
        Arrays.sort(people, byName);

    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

}
