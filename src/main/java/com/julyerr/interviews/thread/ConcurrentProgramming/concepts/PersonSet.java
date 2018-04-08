package com.julyerr.interviews.thread.ConcurrentProgramming.concepts;

import java.util.HashSet;
import java.util.Set;

public class PersonSet {
    private final Set<Person> people = new HashSet<>();

    public synchronized void addPerson(Person p) {
        people.add(p);
    }

    public synchronized boolean containsPerson(Person p) {
        return people.contains(p);
    }

    class Person {
        private String name;

        public Person() {
        }
    }
}
