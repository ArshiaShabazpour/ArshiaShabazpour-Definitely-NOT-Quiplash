package com.finalproject;

public class PlayerAnswer {
    private final String name;
    private final String answer;

    PlayerAnswer(String name, String answer) {
        this.name = name;
        this.answer = answer;
    }

    String toJSON() {
        return "{\"name\": \"" + name + "\", \"answer\": \"" + answer + "\"}";
    }
}
