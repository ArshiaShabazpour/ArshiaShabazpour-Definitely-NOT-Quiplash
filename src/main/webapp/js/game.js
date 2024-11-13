import {GAMESTATES, Message} from "./classes.js";
import {getPlayers, refreshPlayerList} from "./util.js";
import {getAnswerTimed, getPrompt} from "./inputs.js";
import {gameID, ws} from "./main.js";

/**
 * Executes the appropriate corresponding actions with
 * the websocket message type/value.
 * @param {MessageEvent} event The websocket message event.
 */
export function handleWSMessage(event) {
    console.log("e.data:", event.data);
    const data = JSON.parse(event.data);
    switch (data.type) {
        case "welcome":
            changeGameState(GAMESTATES.LOBBY);
        case "join":
            console.log(`Player ${data.value} has joined the game.`);
            refreshPlayerList();
            break;
        case "leave":
            console.log(`Player ${data.value} has left the game.`);
            refreshPlayerList();
            break;
        case "prompt":
            console.log("Please enter a prompt.");
            changeGameState(GAMESTATES.PROMPT);
            getPrompt().then(prompt =>
                ws.send(new Message("prompt", prompt).toJSON()));
            break;
        case "waiting":
            console.log(`Please wait for ${data.value} to enter a prompt.`);
            changeGameState(GAMESTATES.WAITING);
            const label = document.getElementById("waiting_message");
            label.innerText = `Waiting for ${data.value} to enter a prompt.`;
            break;
        case "answer":
            console.log(`Answer the prompt: ${data.value}.`);
            changeGameState(GAMESTATES.ANSWER);
            const prompt_label = document.getElementById("prompt_display");
            prompt_label.innerText = `${data.value}:`;

            getAnswerTimed(60).then(answer =>
                ws.send(new Message("answer", answer).toJSON()));
            break;
        case "voting":
            console.log(`Voting for prompt ${data.value.prompt}:`);
            console.log(`${data.value.player1.name}: ${data.value.player1.answer}`);
            console.log(`${data.value.player2.name}: ${data.value.player2.answer}`);
            updateVotingLabels(data.value);
            changeGameState(GAMESTATES.VOTING);
            break;
        case "end":
            console.log("Scoreboard Screen");
            changeGameState(GAMESTATES.END);
            getPlayers(gameID).then(players => {
                updateScoreBoard(players);
            });
            break;
    }
}

/**
 * Populates the scoreboard at the end of the game
 * @param players array of player objects
 */
function updateScoreBoard(players) {
    // sorts the array by property score
    players.sort((a, b) => b.score - a.score);

    const leaderBoard = document.getElementById("scoreboard");

    players.forEach((player) => {
        const playerEntry = document.createElement("li");
        playerEntry.textContent = `${player.name} - ${player.score}`;
        leaderBoard.appendChild(playerEntry);
    });
}

/**
 * Updates the displayed labels for the voting matchup.
 * @param matchup
 */
function updateVotingLabels(matchup) {
    const prompt_title = document.getElementById("prompt_label");
    prompt_title.innerText = matchup.prompt + ":";
    setPlayerAnswer("player1", matchup.player1);
    setPlayerAnswer("player2", matchup.player2);
    setVoteBtn("vote1", matchup.player1.name);
    setVoteBtn("vote2", matchup.player2.name);
}

/**
 * sets the player answers
 * @param playerId id of the player
 * @param player player obj to set
 */
function setPlayerAnswer(playerId, player) {
    const playerName = document.getElementById(`${playerId}_name`);
    const playerAnswer = document.getElementById(`${playerId}_answer`);
    playerName.innerText = player.name;
    playerAnswer.innerText = player.answer;
}

/**
 * sets the vote buttons
 * @param btnId id of the button
 * @param name name of player
 */
function setVoteBtn(btnId, name) {
    const vote = document.getElementById(btnId);
    vote.classList.remove("inactive");
    vote.onclick = function () {
        console.log(`Voted for ${name}.`);
        ws.send(new Message("vote", name).toJSON());
        deactivateBtns(["vote1", "vote2"]);

    }
}

/**
 * makes the buttons inactive
 * @param btnIds array of buttons to make inactive
 */
function deactivateBtns(btnIds) {
    btnIds.forEach((buttonId) => {
        const voteButton = document.getElementById(buttonId);
        voteButton.onclick = null;
        voteButton.classList.add("inactive");
    });
}

/**
 * Changes the active game state.
 * @param {String} state The desired game state.
 */
export function changeGameState(state) {
    if (!Object.values(GAMESTATES).includes(state)) {
        console.error(`Invalid game state {${state}} requested.`);
        return;
    }
    console.log(`Switching to ${state} game state.`);
    const screen = document.getElementById("screen");
    for (const screen_div of screen.children) {
        screen_div.style.display = "none";
    }
    document.getElementById(state).style.display = "block";
}