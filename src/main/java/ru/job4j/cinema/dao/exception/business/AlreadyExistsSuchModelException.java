package ru.job4j.cinema.dao.exception.business;

/**
 * There is another model with the specified login in the storage.
 *
 * @author Gregory Smirnov (artress@ngs.ru)
 * @version 1.0
 * @since 30/04/2019
 */
public class AlreadyExistsSuchModelException extends DaoBusinessException {
    /**
     * Creates checked exception, based on message.
     *
     * @param message - the specified message.
     */
    public AlreadyExistsSuchModelException(String message) {
        super(message);
    }

    /**
     * Creates checked exception, based on another checked exception and message.
     *
     * @param message - the specified message.
     * @param cause - another checked exception.
     */
    public AlreadyExistsSuchModelException(String message, Throwable cause) {
        super(message, cause);
    }
}