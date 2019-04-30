package ru.job4j.cinema.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Hall controller. Checks places of the cinema hall.
 *
 * @author Gregory Smirnov (artress@ngs.ru)
 * @version 1.0
 * @since 29/04/2019
 */
public class HallController extends HttpServlet {
    /**
     * Forwards request to the "cinema.html" page. 
     * 
     * @param req - HTTP request.
     * @param resp - HTTP response.
     * @throws ServletException if occurs.
     * @throws IOException if occurs.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("cinema.html").forward(req, resp);
    }
}