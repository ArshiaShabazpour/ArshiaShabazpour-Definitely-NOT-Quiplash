package com.finalproject;

/***
 * This class represents a message
 * it stores the contents and the type
 * of message
 */
public class Message {
    private final String type;
    private final String value;

    /**
     * Constructor
     *
     * @param type
     * @param value
     */
    public Message(String type, String value) {
        this.type = type;
        this.value = value;
    }

    /***
     * Converts the message to JSON string format
     * @return
     */
    public String toJSON() {
        String string_value = value;
        if (!value.startsWith("{")) string_value = '"' + value + '"';
        return ("{\"type\": \"" + type + "\", \"value\": " + string_value + "}");
    }
}
