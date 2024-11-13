package com.finalproject;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;
import java.util.Map.*;

@ServerEndpoint(value = "/game/{gameID}")
public class GameServer {
    public static Map<String, Game> games = new HashMap<>();
    public static Map<String, String> sessionGameIDs = new HashMap<>();

    @OnOpen
    public void open(@PathParam("gameID") String gameID, Session session) {
        sessionGameIDs.put(session.getId(), gameID);
    }

    @OnMessage
    public void handleMessage(String comm, Session session) throws IOException {
        JSONObject jsonMsg = new JSONObject(comm);
        String type = jsonMsg.getString("type");
        String value = jsonMsg.getString("value");

        Game game = games.get(sessionGameIDs.get(session.getId()));
        switch (type) {
            case "username":
                game.addPlayer(session, new Player(value));
                String packet = new Message("welcome", value).toJSON();
                session.getBasicRemote().sendText(packet);

                packet = new Message("join", value).toJSON();
                for (Session peer : game.getPlayers().keySet()) {
                    if (peer.getId().equals(session.getId())) continue;
                    peer.getBasicRemote().sendText(packet);
                }
                break;
            case "start":
                handleStartRound(game, session);
                break;
            case "prompt":
                game.currentPrompt = value;
                game.answers.clear();
                game.answerCount = 0;
                game.broadcast(new Message("answer", value));
                game.beginVoteCountdown();
                break;
            case "answer":
                Player player = game.getPlayer(session);
                PlayerAnswer answer = new PlayerAnswer(player.name, value);
                // addAnswer(game, answer);
                game.answers.add(answer);
                break;
            case "vote":
                Player votedPlayer = null;
                for (Player p : game.getPlayers().values()) {
                    if (p.name.equals(value)) {
                        votedPlayer = p;
                        break;
                    }
                }
                if (votedPlayer != null) {
                    votedPlayer.increaseScore();
                    game.voteCount++;
                    if(game.checkVotes()) {
                        handleStartRound(game, session);
                    };
                    game.broadcast(new Message("scoreUpdate", votedPlayer.toJSON().toString()));
                }
                break;
        }
    }

    @OnClose
    public void close(Session session) throws IOException {
        String sessionId = session.getId();
        String gameID = sessionGameIDs.get(sessionId);
        Game game = games.get(gameID);
        Player player = game.removePlayer(session);

        if (game.playerCount() != 0)
            game.broadcast(new Message("leave", player.name));
        else games.remove(gameID);
    }

    private void handleStartRound(Game game, Session session) throws IOException {
        Random rand = new Random();
        String playerName = "";
        int prompted = rand.nextInt(game.playerCount()), i = 0;
        for (Player player : game.getPlayers().values()) {
            if (i == prompted) playerName = player.name;
            ++i;
        }
        String packet;
        for (Entry<Session, Player> peer : game.getPlayers().entrySet()) {
            if (peer.getValue().name.equals(playerName))
                packet = new Message("prompt", playerName).toJSON();
            else packet = new Message("waiting", playerName).toJSON();
            peer.getKey().getBasicRemote().sendText(packet);
        }
    }

}

