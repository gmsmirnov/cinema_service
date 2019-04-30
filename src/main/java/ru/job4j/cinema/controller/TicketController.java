package ru.job4j.cinema.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.cinema.Constants;
import ru.job4j.cinema.dao.exception.business.NullArgumentException;
import ru.job4j.cinema.dao.exception.business.WrongArgumentException;
import ru.job4j.cinema.dao.exception.system.DaoSystemException;
import ru.job4j.cinema.model.Person;
import ru.job4j.cinema.model.Place;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.validate.Validator;
import ru.job4j.cinema.validate.impl.ValidatorDB;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Tickets controller. Actualize ticket's price and puts ticket into database.
 *
 * @author Gregory Smirnov (artress@ngs.ru)
 * @version 1.0
 * @since 20/05/2019
 */
public class TicketController extends HttpServlet {
    /**
     * The logger.
     */
    private static final Logger LOG = LogManager.getLogger(TicketController.class.getName());
    
    /**
     * The logic singleton instance.
     */
    private final Validator logic = ValidatorDB.getInstanceOf();

    /**
     * Prepares information about selected place for the "payment.html" page.
     *
     * @param req - HTTP request.
     * @param resp - HTTP response.
     * @throws IOException if occurs.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Place place = null;
        try {
            place = this.actualizePrice((Place) req.getSession().getAttribute(Constants.ATTR_PLACE));
        } catch (NullArgumentException e) {
            TicketController.LOG.error("Null pointer argument.", e);
        } catch (WrongArgumentException e) {
            TicketController.LOG.error("Wrong argument.", e);
        } catch (DaoSystemException e) {
            TicketController.LOG.error("SQL error occurs.", e);
        }
        this.prepareResponse(place, resp);
    }

    /**
     * Sends information about payment (person, place) into database to create a ticket.
     *
     * @param req - HTTP request.
     * @param resp - HTTP response.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Place place = this.actualizePrice((Place) req.getSession().getAttribute(Constants.ATTR_PLACE));
            Person person = new Person(req.getParameter(Constants.ATTR_NAME), req.getParameter(Constants.ATTR_PHONE));
            this.logic.createTicket(new Ticket(place, person));
        } catch (NullArgumentException e) {
            TicketController.LOG.error("Null pointer argument.", e);
        } catch (WrongArgumentException e) {
            TicketController.LOG.error("Wrong argument.", e);
        } catch (DaoSystemException e) {
            TicketController.LOG.error("SQL error occurs.", e);
        }
    }

    /**
     * Prepares response.
     *
     * @param place - the selected place.
     * @param resp - HTTP response.
     * @throws IOException if occurs.
     */
    private void prepareResponse(Place place, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/json");
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        StringBuilder result = new StringBuilder("[");
        result.append(mapper.writeValueAsString(place));
        result.append("]");
        writer.append(result.toString());
        writer.flush();
    }

    /**
     * Actualize price of the specified place.
     *
     * @param place - the specified place.
     * @return actual place's price.
     * @throws DaoSystemException if SQLException occurs.
     * @throws WrongArgumentException if argument is wrong (e.g. place located outside of the hall).
     * @throws NullArgumentException if argument pointer is null.
     */
    private Place actualizePrice(Place place) throws DaoSystemException, WrongArgumentException, NullArgumentException {
        place.setPrice(this.logic.getActualPrice(place));
        return place;
    }
}