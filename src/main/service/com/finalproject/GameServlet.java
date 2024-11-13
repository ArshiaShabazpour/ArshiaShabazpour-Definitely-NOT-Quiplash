package com.finalproject;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * This is a class that has services
 * In our case, we are using this to generate unique room IDs
 **/
@WebServlet(name = "gameServlet", value = "/new-game")
public class GameServlet extends HttpServlet {
    /**
     * Method generates unique room codes
     */
    public String generatingRandomUpperAlphanumericString(int length) {
        String generatedString = RandomStringUtils.randomAlphanumeric(length).toUpperCase();
        boolean flag = true;
        while (flag) {
            flag = false;
            for (String code : GameServer.games.keySet()) {
                if (code.equals(generatedString)) {
                    flag = true;
                    generatedString = RandomStringUtils.randomAlphanumeric(length).toUpperCase();
                    break;
                }
            }
        }
        GameServer.games.put(generatedString, new Game());
        return generatedString;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");

        // send the random code as the response's content
        PrintWriter out = response.getWriter();
        out.println(generatingRandomUpperAlphanumericString(5));

    }

    public void destroy() {
    }
}