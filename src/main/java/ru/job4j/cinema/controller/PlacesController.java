package ru.job4j.cinema.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.cinema.dao.exception.business.NoSuchModelException;
import ru.job4j.cinema.dao.exception.system.DaoSystemException;
import ru.job4j.cinema.model.Place;
import ru.job4j.cinema.validate.Validator;
import ru.job4j.cinema.validate.impl.ValidatorDB;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Places controller. Prepare places states for the cinema hall.
 *
 * @author Gregory Smirnov (artress@ngs.ru)
 * @version 1.0
 * @since 29/04/2019
 */
public class PlacesController extends HttpServlet {
    /**
     * The logger.
     */
    private static final Logger LOG = LogManager.getLogger(PlacesController.class.getName());
    
    /**
     * The logic singleton instance.
     */
    private final Validator logic = ValidatorDB.getInstanceOf();

    /**
     * Prepare places states for the cinema hall.
     *
     * @param req - HTTP request.
     * @param resp - HTTP response.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            this.prepareResponse(this.logic.selectAllPlaces(), resp);
        } catch (DaoSystemException e) {
            PlacesController.LOG.error("SQL error occurs.", e);
        } catch (NoSuchModelException e) {
            PlacesController.LOG.error("No such model in database.", e);
        } catch (IOException e) {
            PlacesController.LOG.error("IO error occurs.", e);
        }
    }

    /**
     * Prepares HTTP-response, puts into it json-array of places.
     *
     * @param resp - HTTP response.
     * @throws IOException when PrintWriter error occurs.
     */
    private void prepareResponse(List<Place> places, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/json");
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        StringBuilder result = new StringBuilder("[");
        int index = 0;
        for (Place place : places) {
            result.append(mapper.writeValueAsString(place));
            if (places.size() - index > 1) {
                result.append(",");
            }
            index++;
        }
        result.append("]");
        writer.append(result.toString());
        writer.flush();
    }
}