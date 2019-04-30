package ru.job4j.cinema.dao.exception.business;

/**
 * Argument is wrong.
 *
 * @author Gregory Smirnov (artress@ngs.ru)
 * @version 1.0
 * @since 01/05/2019
 */
public class WrongArgumentException extends DaoBusinessException {
    /**
     * Creates checked exception, based on message.
     *
     * @param message - the specified message.
     */
    public WrongArgumentException(String message) {
        super(message);
    }

    /**
     * Creates checked exception, based on another checked exception and message.
     *
     * @param message - the specified message.
     * @param cause - another checked exception.
     */
    public WrongArgumentException(String message, Throwable cause) {
        super(message, cause);
    }
}
