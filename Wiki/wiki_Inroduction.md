
# Battleground 

Battleground is a 2 player arcade game based on the popular [Battleship](https://en.wikipedia.org/wiki/Battleship_(game)) board game. 

Instead of a sea with ships, you play on the ground with buildigns and vehicles.<br>
Each player has :
#### 3 buildings:
1. Airport
2. Radar tower
3. Headquarter

#### 3 vehicles:
1. Gustav railway gun
2. MMRL (Mobile Multiple Rocket Launcher)
3. Tank 

In addition to the normal rules where a player can only shoot and hit one cell of the grid at a time, we added some special actions such as:

* an airstrike (drops bombs on 7 cells horizontally or vertically)
* a radar (discovers 4 cells) //**TODO**
* a rocket strike (shoots **5** rockets randomly in a 7 by 7 cells area)
* ... 

Each one of these special actions can only be used if the linked building or vehicle isn't destroyed and they have a "reload-time" to keep the game dynamic and strategically. 

## Project status

* [x] Architectural design
* [x] Playable simple version in cmd-line
* [x] Playable advanced version in cmd-line
* [x] Playable advanced version in GUI
* [ ] Game finished 


## Built with

* [Eclipse](https://www.eclipse.org)
* [Visual Studio Code](https://code.visualstudio.com) 

## Authors

Group number: 5

* **Michotte Martin** - [MMichotte](https://github.com/MMichotte)
* **Perdaens Martin** - [Martinp-95](https://github.com/Martinp-95)
* **Valentin Morgan** - [momo007Dev](https://github.com/momo007Dev)

## License
This project is licensed under the GNU General Public License v3.0 - see the [LICENSE](LICENSE) file for details.