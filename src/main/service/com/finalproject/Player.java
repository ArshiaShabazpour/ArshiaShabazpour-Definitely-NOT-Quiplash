package com.finalproject;

import org.json.JSONObject;

public class Player {
    public final String name;
    private int score;

    Player(String name) {
        this.name = name;
        this.score = 0;
    }
    public int getScore(){
        return this.score;
    }
    public void increaseScore() {
        this.score++;
    }
    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        object.put("name", name);
        object.put("score", score);
        return object;
    }
}
