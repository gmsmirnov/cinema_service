package ru.job4j.cinema;

import ru.job4j.cinema.model.Place;

/**
 * Utils for Cinema Servlets.
 *
 * @author Gregory Smirnov (artress@ngs.ru)
 * @version 1.0
 * @since 25/05/2019
 */
public class Utils {
    /**
     * Parses the number param for place definition. The firs number is a row, the second is a place number in a row.
     *
     * @param number - the string, containing place number as a param.
     * @return the place with defined row and number.
     */
    public static Place parsePlace(String number) {
        return new Place(Character.getNumericValue(number.charAt(0)), Character.getNumericValue(number.charAt(1)));
    }
}