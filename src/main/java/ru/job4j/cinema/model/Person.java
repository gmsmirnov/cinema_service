package ru.job4j.cinema.model;

import java.util.Objects;

/**
 * Person's model description (name; phone).
 *
 * @author Gregory Smirnov (artress@ngs.ru)
 * @version 1.0
 * @since 20/05/2019
 */
public class Person {
    /**
     * A person's name.
     */
    private String name;

    /**
     * A person's phone.
     */
    private String phone;

    /**
     * Person's constructor. Creates a new person with the specified params.
     *
     * @param name - the specified name.
     * @param phone - the specified phone.
     */
    public Person(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    /**
     * Gets the name of this person.
     *
     * @return the name of this person.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the phone of this person.
     *
     * @return the phone of this person.
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * Checks this person with the other person for equivalence.
     *
     * @param o - the other person.
     * @return true if the persons are equals.
     */
    @Override
    public boolean equals(Object o) {
        boolean result;
        if (this == o) {
            result = true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        } else {
            Person person = (Person) o;
            result = Objects.equals(this.name, person.name) && Objects.equals(this.phone, person.phone);
        }
        return result;
    }

    /**
     * Calculates the hash-code for this person.
     *
     * @return the hash-code for this person.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.phone);
    }

    /**
     * Presents the person-model in a String-view.
     *
     * @return the String presentation of the person-model.
     */
    @Override
    public String toString() {
        return String.format("Person {name=%s, phone=%s}", this.name, this.phone);
    }
}
