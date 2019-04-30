package ru.job4j.cinema.validate.impl;

import ru.job4j.cinema.dao.exception.business.NoSuchModelException;
import ru.job4j.cinema.dao.exception.business.NullArgumentException;
import ru.job4j.cinema.dao.exception.business.WrongArgumentException;
import ru.job4j.cinema.dao.exception.system.DaoSystemException;
import ru.job4j.cinema.dao.impl.PlaceDaoDb;
import ru.job4j.cinema.model.Person;
import ru.job4j.cinema.model.Place;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.validate.Validator;

import java.util.List;

/**
 * A validate layer implementation for data base. Verify params and returned values between view-layer and storage-layer.
 *
 * @author Gregory Smirnov (artress@ngs.ru)
 * @version 1.1
 * @since 29/04/2019
 */
public class ValidatorDB implements Validator {
    /**
     * The DB validator. Singleton.
     */
    private static final ValidatorDB VALIDATOR = new ValidatorDB();

    /**
     * The database singleton.
     */
    private static final PlaceDaoDb DB = PlaceDaoDb.getInstanceOf();

    /**
     * No params constructor.
     */
    private ValidatorDB() {
    }

    /**
     * Gets the singleton instance of DB-validator.
     *
     * @return
     */
    public static ValidatorDB getInstanceOf() {
        return ValidatorDB.VALIDATOR;
    }

    /**
     * Finds all places of the hall. Checks returned value.
     *
     * @return all places of the hall. Never returns null.
     * @throws DaoSystemException if SQLException occurs.
     * @throws NoSuchModelException if the list is empty.
     */
    @Override
    public List<Place> selectAllPlaces() throws NoSuchModelException, DaoSystemException {
        List<Place> result = ValidatorDB.DB.findAllPlaces();
        if (result.isEmpty()) {
            throw new NoSuchModelException("There is no free places.");
        }
        return result;
    }

    /**
     * Finds all free places of the hall. Checks returned value.
     *
     * @return all free places of the hall. Never returns null.
     * @throws DaoSystemException if SQLException occurs.
     * @throws NoSuchModelException if the list is empty.
     */
    @Override
    public List<Place> selectFreePlaces() throws NoSuchModelException, DaoSystemException {
        List<Place> result = ValidatorDB.DB.findFreePlaces();
        if (result.isEmpty()) {
            throw new NoSuchModelException("There is no free places.");
        }
        return result;
    }

    /**
     * Finds all busy places of the hall. Checks returned value.
     *
     * @return all busy places of the hall. Never returns null.
     * @throws DaoSystemException if SQLException occurs.
     * @throws NoSuchModelException if the list is empty.
     */
    @Override
    public List<Place> selectBusyPlaces() throws NoSuchModelException, DaoSystemException {
        List<Place> result = ValidatorDB.DB.findFreePlaces();
        if (result.isEmpty()) {
            throw new NoSuchModelException("There is no busy places.");
        }
        return result;
    }

    /**
     * Checks the specified place's params, is it null first, second:
     * row and number must lies in the cinema rows and numbers diapason.
     * Then checks is it free.
     *
     * @param place - the specified place.
     * @return true if the specified place is free.
     * @throws NullArgumentException if the specified param is null.
     * @throws WrongArgumentException if the place is out of the hall.
     * @throws DaoSystemException if SQLException occurs.
     */
    @Override
    public boolean isFree(Place place) throws DaoSystemException, WrongArgumentException, NullArgumentException {
        this.checkPlace(place);
        return ValidatorDB.DB.isFree(place);
    }

    /**
     * Checks the specified place's params, is it null first, second:
     * row and number must lies in the cinema rows and numbers diapason.
     * Then checks is it busy.
     *
     * @param place - the specified place.
     * @return true if the specified place is busy.
     * @throws NullArgumentException if the specified param is null.
     * @throws WrongArgumentException if the place is out of the hall.
     * @throws DaoSystemException if SQLException occurs.
     */
    @Override
    public boolean isBusy(Place place) throws DaoSystemException, WrongArgumentException, NullArgumentException {
        this.checkPlace(place);
        return ValidatorDB.DB.isBusy(place);
    }

    /**
     * Checks the specified place's params, is it null first, second:
     * row and number must lies in the cinema rows and numbers diapason.
     * Then takes it.
     *
     * @param place - the specified place.
     * @throws NullArgumentException if the specified param is null.
     * @throws WrongArgumentException if the place is out of the hall.
     * @throws DaoSystemException if SQLException occurs.
     */
    @Override
    public void busyPlace(Place place) throws NullArgumentException, WrongArgumentException, DaoSystemException {
        this.checkPlace(place);
        if (this.isFree(place)) {
            ValidatorDB.DB.busyPlace(place);
        }
    }

    /**
     * Checks the specified place's params, is it null first, second:
     * row and number must lies in the cinema rows and numbers diapason.
     * Then frees it.
     *
     * @param place - the specified place.
     * @throws NullArgumentException if the specified param is null.
     * @throws WrongArgumentException if the place is out of the hall.
     * @throws DaoSystemException if SQLException occurs.
     */
    @Override
    public void freePlace(Place place) throws NullArgumentException, WrongArgumentException, DaoSystemException {
        this.checkPlace(place);
        if (this.isBusy(place)) {
            ValidatorDB.DB.freePlace(place);
        }
    }

    /**
     * Checks the specified place's params, is it null first, second:
     * row and number must lies in the cinema rows and numbers diapason.
     *
     * @param place - the specified place.
     * @throws NullArgumentException if the specified param is null.
     * @throws WrongArgumentException if the place is out of the hall.
     * @throws DaoSystemException if SQLException occurs.
     */
    private void checkPlace(Place place) throws NullArgumentException, WrongArgumentException, DaoSystemException {
        if (place == null) {
            throw new NullArgumentException("Incorrect argument: place.");
        } else if (!ValidatorDB.DB.inHall(place)) {
            throw new WrongArgumentException("The place is out of the cinema places range.");
        }
    }

    /**
     * Gets actual price of the specified place from the database.
     *
     * @param place - the specified place.
     * @return actual price.
     * @throws NullArgumentException if the specified param is null.
     * @throws WrongArgumentException if the place is out of the hall.
     * @throws DaoSystemException if SQLException occurs.
     */
    @Override
    public int getActualPrice(Place place) throws DaoSystemException, WrongArgumentException, NullArgumentException {
        this.checkPlace(place);
        return ValidatorDB.DB.getActualPrice(place);
    }

    /**
     * Checks if there is account for the specified person.
     *
     * @param person - the specified person.
     * @return if person already exists in database.
     * @throws DaoSystemException if SQLException occurs.
     * @throws NullArgumentException if the specified param is null.
     */
    private boolean checkAccount(Person person) throws DaoSystemException, NullArgumentException {
        this.checkPerson(person);
        return ValidatorDB.DB.checkAccount(person);
    }

    /**
     * Puts new account in database.
     *
     * @param person - the specified person.
     * @throws DaoSystemException if SQLException occurs.
     * @throws NullArgumentException if the specified param is null.
     */
    @Override
    public void addAccount(Person person) throws DaoSystemException, NullArgumentException {
        if (!this.checkAccount(person)) {
            ValidatorDB.DB.addAccount(person);
        }
    }

    /**
     * Checks the specified person is not null.
     *
     * @param person - the specified place.
     * @throws NullArgumentException if the specified param is null.
     */
    private void checkPerson(Person person) throws NullArgumentException {
        if (person == null) {
            throw new NullArgumentException("Incorrect argument: person.");
        }
    }

    /**
     * Checks person and place params and send request for ticket creation into database.
     *
     * @param ticket - the specified ticket (place and person).
     * @throws NullArgumentException if the specified param is null.
     * @throws WrongArgumentException if the place is out of the hall.
     * @throws DaoSystemException if SQLException occurs.
     */
    public void createTicket(Ticket ticket) throws NullArgumentException, WrongArgumentException, DaoSystemException {
        this.checkPerson(ticket.getPerson());
        this.checkPlace(ticket.getPlace());
        ValidatorDB.DB.createTicket(ticket.getPlace(), ticket.getPerson());
    }
}