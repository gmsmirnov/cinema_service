package ru.job4j.cinema.model;

import java.util.Objects;

/**
 * Place description (row, number in row; is the place free or busy).
 *
 * @author Gregory Smirnov (artress@ngs.ru)
 * @version 1.1
 * @since 29/04/2019
 */
public class Place {
    /**
     * Constant for default price.
     */
    private static final int DEFAULT_PRICE = 500;

    /**
     * The constant for constructor, when creating place is free.
     */
    public static final boolean FREE = true;

    /**
     * The constant for constructor, when creating place is busy.
     */
    public static final boolean BUSY = false;

    /**
     * The specified row position.
     */
    private final int row;

    /**
     * The specified number position.
     */
    private final int number;

    /**
     * True if place is free, false if the place is busy.
     */
    private boolean isEmpty;

    /**
     * This place's price.
     */
    private int price;

    /**
     * Constructor with default 'isEmpty' param (free by default).
     *
     * @param row - the specified row position.
     * @param number - the specified number position.
     */
    public Place(int row, int number) {
        this.row = row;
        this.number = number;
        this.isEmpty = Place.FREE;
        this.price = Place.DEFAULT_PRICE;
    }

    /**
     * Place constructor.
     *
     * @param row - the specified row position.
     * @param number - the specified number position.
     * @param isEmpty - true if place is free, false either.
     */
    public Place(int row, int number, boolean isEmpty) {
        this.row = row;
        this.number = number;
        this.isEmpty = isEmpty;
        this.price = Place.DEFAULT_PRICE;
    }

    /**
     * Place constructor.
     *
     * @param row - the specified row position.
     * @param number - the specified number position.
     * @param isEmpty - true if place is free, false either.
     * @param price - the specified place's price.
     */
    public Place(int row, int number, boolean isEmpty, int price) {
        this.row = row;
        this.number = number;
        this.isEmpty = isEmpty;
        this.price = price;
    }

    /**
     * Gets the place's row position.
     *
     * @return the place's row position.
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Gets the place's number position.
     *
     * @return the place's number position.
     */
    public int getNumber() {
        return this.number;
    }

    /**
     * Gets the 'isEmpty' param.
     *
     * @return true if the place has free state, false either.
     */
    public boolean isEmpty() {
        return this.isEmpty;
    }

    /**
     * Sets the specified value 'isEmpty'.
     *
     * @param isEmpty is the place free (true) or busy (false).
     */
    public void setEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    /**
     * Gets the current place's price.
     *
     * @return the current place's price.
     */
    public int getPrice() {
        return this.price;
    }

    /**
     * Changes the place's price.
     *
     * @param price - the new specified price.
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Checks this place with the other place for equivalence.
     *
     * @param o - the other place.
     * @return true if the places are equals.
     */
    @Override
    public boolean equals(Object o) {
        boolean result;
        if (this == o) {
            result = true;
        } else if (o == null || getClass() != o.getClass()) {
            result = false;
        } else {
            Place place = (Place) o;
            result = this.row == place.row && this.number == place.number && this.isEmpty == place.isEmpty;
        }
        return result;
    }

    /**
     * Calculates the hash-code for this place.
     *
     * @return the hash-code for this place.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.row, this.number, this.isEmpty);
    }

    /**
     * Presents the place-model in a String-view.
     *
     * @return the String presentation of the place-model.
     */
    @Override
    public String toString() {
        return String.format("Place {row=%d, number=%d, isEmpty=%b, price=%d}%n", this.row, this.number, this.isEmpty, this.price);
    }
}