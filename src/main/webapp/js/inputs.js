/**
 * Returns a promise containing the prompt the player inputs.
 * @returns {Promise<string>} The chosen prompt.
 */
export async function getPrompt() {
    return new Promise(resolve => {
        const input = document.getElementById("prompt_field");
        input.onkeydown = function (event) {
            if (event.key !== "Enter") return;
            input.onkeydown = null;
            resolve(input.value.trim());
            input.value = '';
        }
    });
}

/**
 * Returns a promise containing the answer the player inputs.
 * @returns {Promise<string>} The chosen answer.
 */
export async function getAnswer() {
    return new Promise(resolve => {
        const input = document.getElementById("answer_field");
        input.onkeydown = function (event) {
            if (event.key !== "Enter" || !event.ctrlKey) return;
            input.onkeydown = null;
            console.log("answer:", input.value);
            resolve(input.value.trim());
            input.value = '';
        }
    });
}

/**
 * Counts down 'time' seconds before forcefully
 * submitting the answer
 * @param time amount of seconds to countdown
 * @returns {Promise<string>} The answer.
 */
export function getAnswerTimed(time) {
    return new Promise(resolve => {
        const timer = document.getElementById("timer");
        timer.textContent = time;
        const intervalId = setInterval(() => {
            time--;
            timer.textContent = time;
            if (time <= 0) {
                clearInterval(intervalId);
                timer.textContent = "Time's up!";
                const answer = document.getElementById("answer_field");
                resolve(answer.value.trim());
                answer.value = '';
            }
        }, 1000);
    });
}

/**
 * Prompts the user to enter a username for use on the backend.
 * @returns {Promise<String>} The entered username.
 */
export async function getUsername() {
    return new Promise(resolve => {
        const input = document.getElementById("username");
        input.onkeydown = function (event) {
            if (event.key !== "Enter") return;
            input.onkeydown = null;
            document.getElementById("login").style.display = "none";
            resolve(input.value.trim());
            input.value = '';
        }
        document.getElementById("login").style.display = "block";
    });
}