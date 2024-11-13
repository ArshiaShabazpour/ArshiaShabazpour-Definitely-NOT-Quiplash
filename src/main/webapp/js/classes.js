export class Message {
    type;
    value;

    /**
     * Constructs a new Message instance.
     * @param {string} type
     * @param {string} value
     */
    constructor(type, value) {
        this.type = type;
        this.value = value;
    }

    toJSON() {
        return JSON.stringify({
            type: this.type,
            value: this.value
        });
    }
}

// Screen ids found in index.html should match these properties
export class GAMESTATES {
    static MENU = "menu";
    static LOBBY = "lobby";
    static PROMPT = "prompt";
    static WAITING = "waiting";
    static ANSWER = "answer";
    static VOTING = "voting";
    static END = "end";
}