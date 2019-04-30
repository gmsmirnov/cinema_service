package ru.job4j.cinema.dao.impl;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.cinema.dao.PlaceDao;
import ru.job4j.cinema.dao.exception.system.DaoSystemException;
import ru.job4j.cinema.model.Person;
import ru.job4j.cinema.model.Place;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * Implementation of data access object, working with the place-model for data base.
 *
 * @author Gregory Smirnov (artress@ngs.ru)
 * @version 1.2
 * @since 29/04/2019
 */
public class PlaceDaoDb implements PlaceDao {
    private final static String CONFIG_URL = "url";

    private final static String CONFIG_USER = "username";

    private final static String CONFIG_PASSWORD = "password";

    private final static String CONFIG_DRIVER = "driver-class-name";

    private final static int CONFIG_MIN_IDLE_CONNECTIONS = 5;

    private final static int CONFIG_MAX_IDLE_CONNECTIONS = 10;

    private final static int CONFIG_MAX_PREPARED_STATEMENTS = 100;

    private final static String COLUMN_ROW = "row";

    private final static String COLUMN_NUMBER = "number";

    private final static String COLUMN_ISEMPTY = "isEmpty";

    private final static String COLUMN_PRICE = "price";

    /**
     * The logger.
     */
    private static final Logger LOG = LogManager.getLogger(PlaceDaoDb.class.getName());

    /**
     * The connections' pool.
     */
    private static final BasicDataSource SOURCE = new BasicDataSource();

    private static final PlaceDaoDb INSTANCE = new PlaceDaoDb();

    private PlaceDaoDb() {
        Properties config = new Properties();
        try (InputStream in = PlaceDaoDb.class.getClassLoader().getResourceAsStream("db.properties")) {
            config.load(in);
        } catch (IOException e) {
            PlaceDaoDb.LOG.error(e.getMessage(), e);
        }
        PlaceDaoDb.SOURCE.setDriverClassName(config.getProperty(PlaceDaoDb.CONFIG_DRIVER));
        PlaceDaoDb.SOURCE.setUrl(config.getProperty(PlaceDaoDb.CONFIG_URL));
        PlaceDaoDb.SOURCE.setUsername(config.getProperty(PlaceDaoDb.CONFIG_USER));
        PlaceDaoDb.SOURCE.setPassword(config.getProperty(PlaceDaoDb.CONFIG_PASSWORD));
        PlaceDaoDb.SOURCE.setMinIdle(PlaceDaoDb.CONFIG_MIN_IDLE_CONNECTIONS);
        PlaceDaoDb.SOURCE.setMaxIdle(PlaceDaoDb.CONFIG_MAX_IDLE_CONNECTIONS);
        PlaceDaoDb.SOURCE.setMaxOpenPreparedStatements(PlaceDaoDb.CONFIG_MAX_PREPARED_STATEMENTS);
    }

    public static PlaceDaoDb getInstanceOf() {
        return PlaceDaoDb.INSTANCE;
    }

    /**
     * Finds all free places of the hall.
     *
     * @return all free places of the hall.
     * @throws DaoSystemException if SQLException occurs.
     */
    @Override
    public List<Place> findFreePlaces() throws DaoSystemException {
        return this.findPlaces(true);
    }

    /**
     * Finds all busy places of the hall.
     *
     * @return all busy places of the hall.
     * @throws DaoSystemException if SQLException occurs.
     */
    @Override
    public List<Place> findBusyPlaces() throws DaoSystemException {
        return this.findPlaces(false);
    }

    /**
     * Finds all places of the hall by the specified 'isEmpty' parameter.
     *
     * @param isEmpty - param of select, selects empty or busy places.
     * @return all suitable places of the hall.
     * @throws DaoSystemException if SQLException occurs.
     */
    private List<Place> findPlaces(boolean isEmpty) throws DaoSystemException {
        List<Place> result = new LinkedList<Place>();
        try (Connection connection = PlaceDaoDb.SOURCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "select * from hall where isempty = ? order by row, number;"
             )) {
            statement.setBoolean(1, isEmpty);
            try (ResultSet rslSet = statement.executeQuery()) {
                while (rslSet.next()) {
                    result.add(new Place(
                            rslSet.getInt(PlaceDaoDb.COLUMN_ROW),
                            rslSet.getInt(PlaceDaoDb.COLUMN_NUMBER),
                            rslSet.getBoolean(PlaceDaoDb.COLUMN_ISEMPTY),
                            rslSet.getInt(PlaceDaoDb.COLUMN_PRICE)
                    ));
                }
            }
        } catch (SQLException e) {
            throw new DaoSystemException(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Finds all places of the hall.
     *
     * @return all places of the hall.
     * @throws DaoSystemException if SQLException occurs.
     */
    @Override
    public List<Place> findAllPlaces() throws DaoSystemException {
        List<Place> result = new LinkedList<Place>();
        try (Connection connection = PlaceDaoDb.SOURCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "select * from hall order by row, number;"
             )) {
            try (ResultSet rslSet = statement.executeQuery()) {
                while (rslSet.next()) {
                    result.add(new Place(
                            rslSet.getInt(PlaceDaoDb.COLUMN_ROW),
                            rslSet.getInt(PlaceDaoDb.COLUMN_NUMBER),
                            rslSet.getBoolean(PlaceDaoDb.COLUMN_ISEMPTY),
                            rslSet.getInt(PlaceDaoDb.COLUMN_PRICE)
                    ));
                }
            }
        } catch (SQLException e) {
            throw new DaoSystemException(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Checks if the specified place is free.
     *
     * @param place - the specified place.
     * @return true if the specified place is free.
     * @throws DaoSystemException if SQLException occurs.
     */
    @Override
    public boolean isFree(Place place) throws DaoSystemException {
        boolean result = false;
        try (Connection connection = PlaceDaoDb.SOURCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "select * from hall where row = ? and number = ? order by row, number;"
             )) {
            statement.setInt(1, place.getRow());
            statement.setInt(2, place.getNumber());
            try (ResultSet rslSet = statement.executeQuery()) {
                if (rslSet.next()) {
                    if (rslSet.getBoolean(PlaceDaoDb.COLUMN_ISEMPTY)) {
                        result = true;
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoSystemException(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Checks if the specified place is busy.
     *
     * @param place - the specified place.
     * @return true if the specified place is busy.
     * @throws DaoSystemException if SQLException occurs.
     */
    @Override
    public boolean isBusy(Place place) throws DaoSystemException {
        boolean result = false;
        try (Connection connection = PlaceDaoDb.SOURCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "select * from hall where row = ? and number = ? order by row, number;"
             )) {
            statement.setInt(1, place.getRow());
            statement.setInt(2, place.getNumber());
            try (ResultSet rslSet = statement.executeQuery()) {
                if (rslSet.next()) {
                    if (!rslSet.getBoolean(PlaceDaoDb.COLUMN_ISEMPTY)) {
                        result = true;
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoSystemException(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Takes the specified place (sets "isEmpty" as false).
     *
     * @param place - the specified place.
     * @throws DaoSystemException if SQLException occurs.
     */
    @Override
    public void busyPlace(Place place) throws DaoSystemException {
        try (Connection connection = PlaceDaoDb.SOURCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "update hall set isempty = 'false' where row = ? and number = ?;"
             )) {
            statement.setInt(1, place.getRow());
            statement.setInt(2, place.getNumber());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoSystemException(e.getMessage(), e);
        }
    }

    /**
     * Frees the specified place (sets "isEmpty" as true).
     *
     * @param place - the specified place.
     * @throws DaoSystemException if SQLException occurs.
     */
    @Override
    public void freePlace(Place place) throws DaoSystemException {
        try (Connection connection = PlaceDaoDb.SOURCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "update hall set isempty = 'true' where row = ? and number = ?;"
             )) {
            statement.setInt(1, place.getRow());
            statement.setInt(2, place.getNumber());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoSystemException(e.getMessage(), e);
        }
    }

    /**
     * Checks if the specified place lies in the hall.
     *
     * @param place the specified place.
     * @return true if the specified place's row lies in the hall's rows range and number is in the hall's numbers range.
     * @throws DaoSystemException if SQLException occurs.
     */
    @Override
    public boolean inHall(Place place) throws DaoSystemException {
        boolean result = false;
        try (Connection connection = PlaceDaoDb.SOURCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "select * from hall where row = ? and number = ? order by row, number;"
             )) {
            statement.setInt(1, place.getRow());
            statement.setInt(2, place.getNumber());
            try (ResultSet rslSet = statement.executeQuery()) {
                if (rslSet.next()) {
                    result = true;
                }
            }
        } catch (SQLException e) {
            throw new DaoSystemException(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Gets the actual place's price from database.
     *
     * @param place - the specified place (row and number);
     * @return actual place's price.
     * @throws DaoSystemException if SQLException occurs.
     */
    @Override
    public int getActualPrice(Place place) throws DaoSystemException {
        int price = 0;
        try (Connection connection = PlaceDaoDb.SOURCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "select price from hall where row = ? and number = ?;"
             )) {
            statement.setInt(1, place.getRow());
            statement.setInt(2, place.getNumber());
            try (ResultSet rslSet = statement.executeQuery()) {
                if (rslSet.next()) {
                    price = rslSet.getInt(PlaceDaoDb.COLUMN_PRICE);
                }
            }
        } catch (SQLException e) {
            throw new DaoSystemException(e.getMessage(), e);
        }
        return price;
    }

    /**
     * Creates a ticket in database with the specified parameters: person and place.
     *
     * @param place - the specified place.
     * @param person - the specified person.
     * @throws DaoSystemException if SQL Exception occurs.
     */
    @Override
    public void createTicket(Place place, Person person) throws DaoSystemException {
        try (Connection connection = PlaceDaoDb.SOURCE.getConnection()) {
            this.updateTables(connection, person, place);
        } catch (SQLException e) {
            throw new DaoSystemException(e.getMessage(), e);
        }
    }

    /**
     * A help-method for "createTicket()"-method. The specified connection sets into not auto commit mode, prepares
     * all update table requests, then commits all request, or rollback, if an error occurs.
     *
     * @param connection - the specified connection.
     * @param person - the specified person.
     * @param place - the specified place.
     * @throws DaoSystemException if SQL Exception occurs.
     */
    private void updateTables(Connection connection, Person person, Place place) throws DaoSystemException {
        try {
            connection.setAutoCommit(false);
            this.updateHall(connection, place);
            if (this.checkAccount(person)) {
                this.updateAccountsTable(connection, person);
            } else {
                this.addAccount(person);
            }
            this.updateTicketsTable(connection, person, place);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DaoSystemException(e1.getMessage(), e1);
            }
            throw new DaoSystemException(e.getMessage(), e);
        }
    }

    /**
     * A help-method for "updateTables()"-method. Updates accounts table.
     *
     * @param connection - the specified not auto commit connection.
     * @param person - the specified person.
     * @throws DaoSystemException if SQL Exception occurs.
     */
    private void updateAccountsTable(Connection connection, Person person) throws DaoSystemException {
        try (PreparedStatement statement = connection.prepareStatement(
                     "update accounts set phone = ? where name = ?;")) {
            statement.setString(1, person.getPhone());
            statement.setString(2, person.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoSystemException(e.getMessage(), e);
        }
    }

    /**
     * A help-method for "updateTables()"-method. Updates hall, busy the specified place.
     *
     * @param connection - the specified not auto commit connection.
     * @param place - the specified place.
     * @throws DaoSystemException if SQL Exception occurs.
     */
    private void updateHall(Connection connection, Place place) throws DaoSystemException {
        try (PreparedStatement statement = connection.prepareStatement(
                "update hall set isempty = 'false' where row = ? and number = ?;"
        )) {
            statement.setInt(1, place.getRow());
            statement.setInt(2, place.getNumber());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoSystemException(e.getMessage(), e);
        }
    }

    /**
     * A help-method for "updateTables()"-method. Creates a new ticket in database.
     *
     * @param connection - the specified not auto commit connection.
     * @param person - the specified person.
     * @param place - the specified place.
     * @throws DaoSystemException if SQL Exception occurs.
     */
    private void updateTicketsTable(Connection connection, Person person, Place place) throws DaoSystemException {
        try (PreparedStatement statement = connection.prepareStatement(
                "insert into tickets (place_id, account_id) values ("
                        + "(select id from hall where row = ? and number = ?),  "
                        + "(select id from accounts where name = ?));"
        )) {
            statement.setInt(1, place.getRow());
            statement.setInt(2, place.getNumber());
            statement.setString(3, person.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoSystemException(e.getMessage(), e);
        }
    }

    /**
     * Checks existence of account for the specified person.
     *
     * @param person - the specified person.
     * @return true if account already exists.
     * @throws DaoSystemException if SQL Exception occurs.
     */
    @Override
    public boolean checkAccount(Person person) throws DaoSystemException {
        boolean result = false;
        try (Connection connection = PlaceDaoDb.SOURCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "select * from accounts where name = ?;"
             )) {
            statement.setString(1, person.getName());
            try (ResultSet rslSet = statement.executeQuery()) {
                if (rslSet.next()) {
                    result = true;
                }
            }
        } catch (SQLException e) {
            throw new DaoSystemException(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Adds new account for the specified person into database.
     *
     * @param person - the specified person.
     * @throws DaoSystemException if SQL Exception occurs.
     */
    @Override
    public void addAccount(Person person) throws DaoSystemException {
        try (Connection connection = PlaceDaoDb.SOURCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "insert into accounts(name, phone) values(?, ?);")) {
            statement.setString(1, person.getName());
            statement.setString(2, person.getPhone());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoSystemException(e.getMessage(), e);
        }
    }

    /**
     * Updates the existent account for the specified person in database.
     *
     * @param person - the specified person.
     * @throws DaoSystemException if SQL Exception occurs.
     */
    @Override
    public void updateAccount(Person person) throws DaoSystemException {
        try (Connection connection = PlaceDaoDb.SOURCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "update accounts set phone = ? where name = ?;")) {
            statement.setString(1, person.getPhone());
            statement.setString(2, person.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoSystemException(e.getMessage(), e);
        }
    }
}