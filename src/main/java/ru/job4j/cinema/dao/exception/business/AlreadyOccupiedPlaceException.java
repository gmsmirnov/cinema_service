package ru.job4j.cinema.dao.exception.business;

/**
 * The place is already occupied.
 *
 * @author Gregory Smirnov (artress@ngs.ru)
 * @version 1.0
 * @since 06/06/2019
 */
public class AlreadyOccupiedPlaceException extends DaoBusinessException {
    /**
     * Creates checked exception, based on message.
     *
     * @param message - the specified message.
     */
    public AlreadyOccupiedPlaceException(String message) {
        super(message);
    }

    /**
     * Creates checked exception, based on another checked exception and message.
     *
     * @param message - the specified message.
     * @param cause - another checked exception.
     */
    public AlreadyOccupiedPlaceException(String message, Throwable cause) {
        super(message, cause);
    }
}
