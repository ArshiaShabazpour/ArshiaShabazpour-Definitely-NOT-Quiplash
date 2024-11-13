
# Assignment Final: Quiplash
Our project is an online multiplayer game based off of Quiplash, ranging between 4 and 8 players. Each round a single random player is chosen to come up with a prompt that all players must answer. Once all the answers are received, the backend matches random answers up against each other, allowing players to vote for which of the answers is the funnier response. After three prompt rounds, the player who accumulated the most votes by the end is declared the winner!
[![Project Demo Video](https://i3.ytimg.com/vi/42yq1zxlRxA/maxresdefault.jpg)](http://www.youtube.com/watch?v=gSICJB0c5ZQ)
### Authors:
 - **Jack Ma**
 - **Alex Lowe**
 - **Jack Scheuring**
 - **Arshiya Shahbazpourtazehkand**

## Running the Project:
We use IntelliJ IDEA as our IDE, so it'd be best to use the same IDE to follow along with this tutorial. If you clone this repository from IDEA you can open it as a project and start using it yourself. To build the project, you'll need to install a plugin for running the server. We recommend using [GlassFish](https://glassfish.org/download) for this. Once you have it downloaded, you can install the GlassFish plugin on IDEA by heading to `Main Menu > File > Settings > Plugins` and searching for the plugin. Once you have all of that set up, you can create a build task by heading to `Main Menu > Run > Edit Configurations > Add New Configuration`. From there select the local suboption under the GlassFish section. Set your domain to the default domain, then go to the `Deployment` section and add `FinalProject:war exploded` as your artifact. With all those changes applied, you should be able to start the backend service through simply running the GlassFish build task you just created. Doing so will open the main application window in your defualt browser as well. From there everything should be working!

## Resources:
We didnt use any.

## Contributions:
### Jack Scheuring:
* Constructed initial client interface and styling.
* Created basic Game room listing, creation, switching and gameplay functionality.
* Improved readme file.
### Alex Lowe:
* Worked on completing the basic readme file requirements.
* Implemented User page.
* Adjusted client styling.
* Fixed various bugs.
### Jack Ma:
* Implemented End screen.
* Worked on additional backend logic.
### Arshiya Shahbazpourtazehkand:
* Restructured backend functionality.
* Implemented voting and scoring.
* Added the looping functionality for game logic  
