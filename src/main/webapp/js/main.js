import {createLobby, startGame} from "./onclick.js";
import {Message} from "./classes.js";
import {getUsername} from "./inputs.js";
import {changeGameState, handleWSMessage} from "./game.js";

/* Window Functions */
window.createLobby = createLobby;
window.startGame = startGame;

// Included for debugging
window.changeGameState = changeGameState;

/* Global Variables */
export const API_URL = "localhost:8080/FinalProject-1.0-SNAPSHOT";
export let ws;
export let gameID;

window.onload = function () {
    const lobby_list = document.getElementById("lobby_list");
    fetch(`http://${API_URL}/lobbies`, {
        method: 'GET',
        headers: {'Accept': 'application/json'},
    })
        .then(response => response.json())
        .then(lobbies => {
            for (const gameID of lobbies) {
                const li = document.createElement("li");
                li.innerText = gameID;
                li.onclick = clickLobbyId;
                lobby_list.appendChild(li);
            }
        });
}

/**
 * Handles onclick events on lobby list elements. This
 * will automatically navigate the client to the lobby
 * with the corresponding name from the clicked element.
 * @param {PointerEvent} event The onclick event.
 */
function clickLobbyId(event) {
    enterGame(event.target.innerText);
}

/**
 * Enters a game lobby given a code and initializes
 * websocket intercommunication.
 * @param {string} code The lobby code to enter.
 * @returns {void}
 */
export async function enterGame(code) {
    ws?.close();
    ws = new WebSocket(`ws://${API_URL}/game/${code}`);
    gameID = code;
    document.getElementById("lobby_label").innerHTML = `Lobby ${code} Players:`;
    ws.send(new Message("username", await getUsername()).toJSON());
    ws.onmessage = handleWSMessage;
}
