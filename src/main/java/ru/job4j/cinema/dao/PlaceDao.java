package ru.job4j.cinema.dao;

import ru.job4j.cinema.dao.exception.system.DaoSystemException;
import ru.job4j.cinema.model.Person;
import ru.job4j.cinema.model.Place;

import java.util.List;

/**
 * Data access object, working with the place-model.
 *
 * @author Gregory Smirnov (artress@ngs.ru)
 * @version 1.0
 * @since 29/04/2019
 */
public interface PlaceDao {
    /**
     * Finds all free places of the hall.
     *
     * @return all free places of the hall.
     * @throws DaoSystemException if SQLException occurs.
     */
    List<Place> findFreePlaces() throws DaoSystemException;

    /**
     * Finds all busy places of the hall.
     *
     * @return all busy places of the hall.
     * @throws DaoSystemException if SQLException occurs.
     */
    List<Place> findBusyPlaces() throws DaoSystemException;

    /**
     * Finds all places of the hall.
     *
     * @return all places of the hall.
     * @throws DaoSystemException if SQLException occurs.
     */
    List<Place> findAllPlaces() throws DaoSystemException;

    /**
     * Checks if the specified place is free.
     *
     * @param place - the specified place.
     * @return true if the specified place is free.
     * @throws DaoSystemException if SQLException occurs.
     */
    boolean isFree(Place place) throws DaoSystemException;

    /**
     * Checks if the specified place is busy.
     *
     * @param place - the specified place.
     * @return true if the specified place is busy.
     * @throws DaoSystemException if SQLException occurs.
     */
    boolean isBusy(Place place) throws DaoSystemException;

    /**
     * Takes the specified place (sets "isEmpty" as false).
     *
     * @param place - the specified place.
     * @throws DaoSystemException if SQLException occurs.
     */
    void busyPlace(Place place) throws DaoSystemException;

    /**
     * Frees the specified place (sets "isEmpty" as true).
     *
     * @param place - the specified place.
     * @throws DaoSystemException if SQLException occurs.
     */
    void freePlace(Place place) throws DaoSystemException;

    /**
     * Checks if the specified place lies in the hall.
     *
     * @param place the specified place.
     * @return true if the specified place's row lies in the hall's rows range and number is in the hall's numbers range.
     * @throws DaoSystemException if SQLException occurs.
     */
    boolean inHall(Place place) throws DaoSystemException;

    /**
     * Gets the actual place's price from database.
     *
     * @param place - the specified place (row and number);
     * @return actual place's price.
     * @throws DaoSystemException if SQLException occurs.
     */
    int getActualPrice(Place place) throws DaoSystemException;

    /**
     * Creates a ticket in database with the specified parameters: person and place.
     *
     * @param place - the specified place.
     * @param person - the specified person.
     * @throws DaoSystemException if SQL Exception occurs.
     */
    void createTicket(Place place, Person person) throws DaoSystemException;

    /**
     * Checks existence of account for the specified person.
     *
     * @param person - the specified person.
     * @return true if account already exists.
     * @throws DaoSystemException if SQL Exception occurs.
     */
    boolean checkAccount(Person person) throws DaoSystemException;

    /**
     * Adds new account for the specified person into database.
     *
     * @param person - the specified person.
     * @throws DaoSystemException if SQL Exception occurs.
     */
    void addAccount(Person person) throws DaoSystemException;

    /**
     * Updates the existent account for the specified person in database.
     *
     * @param person - the specified person.
     * @throws DaoSystemException if SQL Exception occurs.
     */
    void updateAccount(Person person) throws DaoSystemException;
}