package com.finalproject;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "lobbyServlet", value = "/lobbies")
public class LobbyServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        // Return a json array of room codes corresponding with the currently
        // active rooms. Empty list if no rooms have been created.
        if (GameServer.games.isEmpty()) {
            out.print("[]");
            return;
        }
        String message = "[ ";
        for (String code : GameServer.games.keySet()) {
            if (GameServer.games.get(code).playerCount() < 8)
                message += '"' + code + "\",";
        }
        message = message.substring(0, message.length() - 1) + " ]";
        out.print(message);
    }
}
