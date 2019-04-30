package ru.job4j.cinema.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.cinema.Constants;
import ru.job4j.cinema.Utils;
import ru.job4j.cinema.dao.exception.business.NullArgumentException;
import ru.job4j.cinema.dao.exception.business.WrongArgumentException;
import ru.job4j.cinema.dao.exception.system.DaoSystemException;
import ru.job4j.cinema.model.Place;
import ru.job4j.cinema.validate.Validator;
import ru.job4j.cinema.validate.impl.ValidatorDB;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Booking place controller. Sets to the session place's params.
 *
 * @author Gregory Smirnov (artress@ngs.ru)
 * @version 1.0
 * @since 20/05/2019
 */
public class BookingController extends HttpServlet {
    /**
     * The logger.
     */
    private static final Logger LOG = LogManager.getLogger(BookingController.class.getName());
    
    /**
     * The logic singleton instance.
     */
    private final Validator logic = ValidatorDB.getInstanceOf();

    /**
     * Actualize price of the specified place and puts it into session's attribute.
     *
     * @param req - HTTP request.
     * @param resp - HTTP response.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        Place place = Utils.parsePlace(req.getParameter(Constants.ATTR_PLACE));
        try {
            place.setPrice(this.logic.getActualPrice(place));
        } catch (DaoSystemException e) {
            BookingController.LOG.error("SQL error occurs.", e);
        } catch (WrongArgumentException e) {
            BookingController.LOG.error("Wrong argument.", e);
        } catch (NullArgumentException e) {
            BookingController.LOG.error("Null pointer argument.", e);
        }
        session.setAttribute(Constants.ATTR_PLACE, place);
    }
}