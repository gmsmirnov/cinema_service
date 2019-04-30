package ru.job4j.cinema.validate;

import ru.job4j.cinema.dao.exception.business.NoSuchModelException;
import ru.job4j.cinema.dao.exception.business.NullArgumentException;
import ru.job4j.cinema.dao.exception.business.WrongArgumentException;
import ru.job4j.cinema.dao.exception.system.DaoSystemException;
import ru.job4j.cinema.model.Person;
import ru.job4j.cinema.model.Place;
import ru.job4j.cinema.model.Ticket;

import java.util.List;

/**
 * A validate layer. Verify params and returned values between view-layer and storage-layer.
 *
 * @author Gregory Smirnov (artress@ngs.ru)
 * @version 1.1
 * @since 29/04/2019
 */
public interface Validator {
    /**
     * Finds all places of the hall. Checks returned value.
     *
     * @return all places of the hall. Never returns null.
     * @throws DaoSystemException if SQLException occurs.
     * @throws NoSuchModelException if the list is empty.
     */
    List<Place> selectAllPlaces() throws NoSuchModelException, DaoSystemException;

    /**
     * Finds all free places of the hall. Checks returned value.
     *
     * @return all free places of the hall. Never returns null.
     * @throws DaoSystemException if there is some system problems.
     * @throws NoSuchModelException if the list is empty.
     */
    List<Place> selectFreePlaces() throws NoSuchModelException, DaoSystemException;

    /**
     * Finds all busy places of the hall. Checks returned value.
     *
     * @return all busy places of the hall. Never returns null.
     * @throws DaoSystemException if there is some system problems.
     * @throws NoSuchModelException if the list is empty.
     */
    List<Place> selectBusyPlaces() throws NoSuchModelException, DaoSystemException;

    /**
     * Checks the specified place's params, is it null first, second:
     * row and number must lies in the cinema rows and numbers diapason.
     * Then checks is it free.
     *
     * @param place - the specified place.
     * @return true if the specified place is free.
     * @throws NullArgumentException if the specified param is null.
     * @throws WrongArgumentException if the place is out of the hall.
     * @throws DaoSystemException if there is some system problems.
     */
    boolean isFree(Place place) throws DaoSystemException, WrongArgumentException, NullArgumentException;

    /**
     * Checks the specified place's params, is it null first, second:
     * row and number must lies in the cinema rows and numbers diapason.
     * Then checks is it busy.
     *
     * @param place - the specified place.
     * @return true if the specified place is busy.
     * @throws NullArgumentException if the specified param is null.
     * @throws WrongArgumentException if the place is out of the hall.
     * @throws DaoSystemException if there is some system problems.
     */
    boolean isBusy(Place place) throws DaoSystemException, WrongArgumentException, NullArgumentException;

    /**
     * Checks the specified place's params, is it null first, second:
     * row and number must lies in the cinema rows and numbers diapason.
     * Then takes it.
     *
     * @param place - the specified place.
     * @throws NullArgumentException if the specified param is null.
     * @throws WrongArgumentException if the place is out of the hall.
     * @throws DaoSystemException if there is some system problems.
     */
    void busyPlace(Place place) throws NullArgumentException, WrongArgumentException, DaoSystemException;

    /**
     * Checks the specified place's params, is it null first, second:
     * row and number must lies in the cinema rows and numbers diapason.
     * Then frees it.
     *
     * @param place - the specified place.
     * @throws NullArgumentException if the specified param is null.
     * @throws WrongArgumentException if the place is out of the hall.
     * @throws DaoSystemException if there is some system problems.
     */
    void freePlace(Place place) throws NullArgumentException, WrongArgumentException, DaoSystemException;

    /**
     * Gets actual price of the specified place from the database.
     *
     * @param place - the specified place.
     * @return actual price.
     * @throws NullArgumentException if the specified param is null.
     * @throws WrongArgumentException if the place is out of the hall.
     * @throws DaoSystemException if SQLException occurs.
     */
    int getActualPrice(Place place) throws DaoSystemException, WrongArgumentException, NullArgumentException;

    /**
     * Puts new account in database.
     *
     * @param person - the specified person.
     * @throws DaoSystemException if SQLException occurs.
     * @throws NullArgumentException if the specified param is null.
     */
    void addAccount(Person person) throws DaoSystemException, NullArgumentException;

    /**
     * Checks person and place params and send request for ticket creation into database.
     *
     * @param ticket - the specified ticket (place and person).
     * @throws NullArgumentException if the specified param is null.
     * @throws WrongArgumentException if the place is out of the hall.
     * @throws DaoSystemException if SQLException occurs.
     */
    void createTicket(Ticket ticket) throws NullArgumentException, WrongArgumentException, DaoSystemException;
}