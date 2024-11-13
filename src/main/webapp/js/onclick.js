import {ws, API_URL, enterGame} from "./main.js";
import {Message} from "./classes.js";

export function createLobby() {
    console.log("Game Created!");
    fetch(`http://${API_URL}/new-game`, {
        method: 'GET',
        headers: {'Accept': 'text/plain'},
    })
        .then(response => response.text())
        .then(code => enterGame(code));
}

export function startGame() {
    console.log("Starting Game!");
    ws.send(new Message("start", "").toJSON());
}
