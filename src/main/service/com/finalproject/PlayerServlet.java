package com.finalproject;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/players")
public class PlayerServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String gameID = request.getParameter("gameID");

        Game game = GameServer.games.get(gameID);
        if (game == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        ArrayList<JSONObject> jsonPlayers = new ArrayList<>();
        for (Player player : game.getPlayers().values())
            jsonPlayers.add(player.toJSON());
        JSONArray users = new JSONArray(jsonPlayers);
        out.print(users);
        out.flush();
    }
}
