import {API_URL, gameID} from "./main.js";

export function refreshPlayerList() {
    getPlayers(gameID).then(players => {
        listPlayers(players);
        const start_btn = document.getElementById("start");
        if (players.length >= 4 && players.length % 2 === 0) {
            start_btn.setAttribute("onclick", 'startGame()');
            start_btn.classList.remove("inactive");
        } else {
            start_btn.setAttribute("onclick", '');
            start_btn.classList.add("inactive");
        }
    });
}

function listPlayers(players) {
    const player_list = document.getElementById("player_list");
    player_list.innerHTML = '';
    for (const player of players) {
        const li = document.createElement("li");
        li.innerText = player.name;
        player_list.appendChild(li);
    }
}

export async function getPlayers(gameID) {
    const response = await fetch(`http://${API_URL}/players?gameID=${gameID}`, {
        method: 'GET',
        headers: {'Accept': 'application/json',},
    });
    return await response.json();
}