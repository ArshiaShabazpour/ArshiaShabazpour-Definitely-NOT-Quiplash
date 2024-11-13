package com.finalproject;

public class PromptAnswer {
    private final String prompt;
    private final PlayerAnswer player1;
    private final PlayerAnswer player2;
    PromptAnswer(String prompt, PlayerAnswer player1, PlayerAnswer player2) {
        this.prompt = prompt;
        this.player1 = player1;
        this.player2 = player2;
    }

    String toJSON() {
        return "{\"prompt\": \"" + prompt + "\", \"player1\": " + player1.toJSON() + ", \"player2\": " + player2.toJSON() + "}";
    }
}

