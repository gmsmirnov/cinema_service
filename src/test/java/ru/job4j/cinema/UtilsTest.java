package ru.job4j.cinema;

import org.junit.Test;
import ru.job4j.cinema.model.Place;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Utils test for Cinema Servlets.
 *
 * @author Gregory Smirnov (artress@ngs.ru)
 * @version 1.0
 * @since 25/05/2019
 */
public class UtilsTest {
    @Test
    public void whenGetNumberThenParseIt() {
        assertThat(Utils.parsePlace("22"), is(new Place(2, 2)));
        assertThat(Utils.parsePlace("13"), is(new Place(1, 3)));
        assertThat(Utils.parsePlace("31"), is(new Place(3, 1)));
    }
}