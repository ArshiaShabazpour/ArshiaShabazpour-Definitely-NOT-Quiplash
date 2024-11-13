package com.finalproject;

import jakarta.websocket.Session;

import java.io.IOException;
import java.util.*;

/**
 * This class represents the data you may need to store about a Chat room
 * You may add more method or attributes as needed
 **/
public class Game {
    public static class States {
        static String MENU = "menu";
        static String LOBBY = "lobby";
        static String PROMPT = "prompt";
        static String ANSWERS = "answers";
        static String VOTING = "voting";
        static String END = "end";
    }

    private Map<Session, Player> players = new HashMap<>();
    private String state;
    public ArrayList<PlayerAnswer> answers = new ArrayList<>();
    public String currentPrompt;
    public int answerCount;
    public int voteCount = 0;
    int currentRound = 0;
    static final int NUMBER_OF_ROUNDS = 3;


    public Game() {
        this.state = States.LOBBY;
    }
    public boolean checkVotes() throws IOException {
        if (voteCount >= players.size()) {
            currentRound++;
            if (currentRound < NUMBER_OF_ROUNDS) {
                voteCount = 0;
                answers.clear();
                return true;
            } else {
                endGame();
            }
        }
        return false;
    }
    private void endGame() throws IOException {
        state = States.END;
        broadcast(new Message("end", "Game over! Thanks for playing."));
    }

    public Map<Session, Player> getPlayers() {
        return players;
    }

    String getState() {
        return state;
    }

    void setState(String state) {
        this.state = state;
    }

    Player getPlayer(Session session) {
        return players.get(session);
    }

    void addPlayer(Session session, Player player) {
        players.put(session, player);
    }

    Player removePlayer(Session session) {
        return players.remove(session);
    }

    int playerCount() {
        return this.players.size();
    }

    void broadcast(Message message) throws IOException {
        String packet = message.toJSON();
        for (Session session : players.keySet())
            session.getBasicRemote().sendText(packet);
    }

    public void beginVoteCountdown() throws IOException {
        long startTime = System.currentTimeMillis();
        long elapsed = 0L;
        // This should be whatever delay is on the
        // frontend plus two seconds.
        while (elapsed < 62 * 1000)
            elapsed = (new Date()).getTime() - startTime;
        Collections.shuffle(answers);
        showVote();
    }

    void showVote() throws IOException {
        PromptAnswer promptData = new PromptAnswer(currentPrompt, answers.get(0), answers.get(1));
        answers.remove(0);
        answers.remove(0);
        broadcast(new Message("voting", promptData.toJSON()));
    }
}
