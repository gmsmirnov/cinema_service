package ru.job4j.cinema.model;

import java.util.Objects;

/**
 * Ticket's model description (place; person).
 *
 * @author Gregory Smirnov (artress@ngs.ru)
 * @version 1.0
 * @since 20/05/2019
 */
public class Ticket {
    /**
     * The ticket's place.
     */
    private final Place place;

    /**
     * The person whose bought this ticket.
     */
    private final Person person;

    /**
     * Ticket's constructor. Creates a new ticket with the specified params.
     *
     * @param place - the specified place.
     * @param person - the specified person.
     */
    public Ticket(Place place, Person person) {
        this.place = place;
        this.person = person;
    }

    /**
     * Gets the ticket's place (row and number).
     *
     * @return the ticket's place (row and number).
     */
    public Place getPlace() {
        return this.place;
    }

    /**
     * Gets the person whose bought the ticket.
     *
     * @return the person whose bought the ticket.
     */
    public Person getPerson() {
        return this.person;
    }

    /**
     * Checks this ticket with the other ticket for equivalence.
     *
     * @param o - the other ticket.
     * @return true if the tickets are equals.
     */
    @Override
    public boolean equals(Object o) {
        boolean result;
        if (this == o) {
            result = true;
        } else if (o == null || getClass() != o.getClass()) {
            result = false;
        } else {
            Ticket ticket = (Ticket) o;
            result = Objects.equals(this.place, ticket.place) && Objects.equals(this.person, ticket.person);
        }
        return result;
    }

    /**
     * Calculates the hash-code for this ticket.
     *
     * @return the hash-code for this ticket.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.place, this.person);
    }

    /**
     * Presents the ticket-model in a String-view.
     *
     * @return the String presentation of the ticket-model.
     */
    @Override
    public String toString() {
        return String.format("Ticket {place=%s, person=%s}", this.place, this.person);
    }
}