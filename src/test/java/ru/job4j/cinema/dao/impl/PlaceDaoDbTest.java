package ru.job4j.cinema.dao.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.cinema.dao.exception.system.DaoSystemException;
import ru.job4j.cinema.model.Place;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

/**
 * Place DAO test.
 *
 * @author Gregory Smirnov (artress@ngs.ru)
 * @version 1.0
 * @since 25/05/2019
 */
public class PlaceDaoDbTest {
    private static final PlaceDaoDb DB = PlaceDaoDb.getInstanceOf();

    @Before
    public void init() {
        this.freePlaces();
    }

    @After
    public void clear() {
        this.freePlaces();
    }

    private void freePlaces() {
        try {
            List<Place> allPlaces = PlaceDaoDbTest.DB.findAllPlaces();
            for (Place place : allPlaces) {
                PlaceDaoDbTest.DB.freePlace(place);
            }
        } catch (DaoSystemException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenPlaceIsFreeThenTakeIt() throws DaoSystemException {
        assertThat(PlaceDaoDbTest.DB.isFree(new Place(1, 1)), is(true));
        assertThat(PlaceDaoDbTest.DB.isBusy(new Place(1, 1)), is(false));
        PlaceDaoDbTest.DB.busyPlace(new Place(1, 1));
        assertThat(PlaceDaoDbTest.DB.isFree(new Place(1, 1)), is(false));
        assertThat(PlaceDaoDbTest.DB.isBusy(new Place(1, 1)), is(true));
    }

    @Test
    public void whenPlaceIsBusyThenFreeIt() throws DaoSystemException {
        PlaceDaoDbTest.DB.busyPlace(new Place(1, 1));
        assertThat(PlaceDaoDbTest.DB.isFree(new Place(1, 1)), is(false));
        assertThat(PlaceDaoDbTest.DB.isBusy(new Place(1, 1)), is(true));
        PlaceDaoDbTest.DB.freePlace(new Place(1, 1));
        assertThat(PlaceDaoDbTest.DB.isFree(new Place(1, 1)), is(true));
        assertThat(PlaceDaoDbTest.DB.isBusy(new Place(1, 1)), is(false));
    }

    @Test
    public void whenInitThenAllPlacesAreFree() {
        try {
            assertThat(PlaceDaoDbTest.DB.findFreePlaces(), containsInAnyOrder(PlaceDaoDbTest.DB.findAllPlaces().toArray()));
        } catch (DaoSystemException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenThereIsBusyPlacesThenGetThem() {
        try {
            Place[] busyPlaces = new Place[3];
            busyPlaces[0] = new Place(1, 1, Place.BUSY);
            busyPlaces[1] = new Place(1, 2, Place.BUSY);
            busyPlaces[2] = new Place(1, 3, Place.BUSY);
            PlaceDaoDbTest.DB.busyPlace(busyPlaces[0]);
            PlaceDaoDbTest.DB.busyPlace(busyPlaces[1]);
            PlaceDaoDbTest.DB.busyPlace(busyPlaces[2]);
            assertThat(PlaceDaoDbTest.DB.findBusyPlaces(), containsInAnyOrder(busyPlaces));
            assertThat(PlaceDaoDbTest.DB.findBusyPlaces().size(), is(busyPlaces.length));
        } catch (DaoSystemException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenPlaceIsInHallThenTrue() throws DaoSystemException {
        assertThat(PlaceDaoDbTest.DB.inHall(new Place(1, 1)), is(true));
    }

    @Test
    public void whenPlaceIsOutOfHallThenFalse() throws DaoSystemException {
        assertThat(PlaceDaoDbTest.DB.inHall(new Place(4, 1)), is(false));
    }
}