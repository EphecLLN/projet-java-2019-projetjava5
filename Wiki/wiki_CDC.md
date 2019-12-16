


# CAHIER DES CHARGES


## Contexte du projet et objectifs :
Dans le cadre du cours de Développement informatique avancé orienté applications (Java), il nous est demandé de réaliser une application utilitaire ou bien un jeu qui se présente à la fois sous forme d’interface graphique et en ligne de commande. Ceux-ci doivent comporter une communication réseau ou une interaction avec une base de données ou éventuellement un service web.

Nous avons opté pour la réalisation d’un jeu vidéo de type 1 contre 1 en réseau se basant sur le jeu populaire « bataille navale ».

Dans notre version du jeu, le terrain de jeu n’est plus l’océan mais la terre ferme, les bateaux sont remplacés par des bâtiments et des véhicules.
Certains bâtiments et véhicules offrent des compétences supplémentaires au joueur tel qu’un raid aérien depuis un aéroport ou un tir de rockets depuis un véhicule lance-roquettes. Ces compétences spéciales ne sont évidemment pas accessibles à tout moment :

* Une fois utilisée, une compétence doit être « rechargée » (elle redeviendra disponible après un certain temps).
* Si le bâtiment/véhicule est détruit, la compétence liée à celui-ci n’est plus disponible.

## Contraintes techniques :
1. Le jeu doit pouvoir être joué de 2 manières :
    * Par le biais d’une interface en ligne de commande
    * Par le biais d’une interface graphique 

    Attention, les deux joueurs ne doivent pas spécialement être sur la même interface. 

2. Le jeu doit pouvoir être joué sur deux machines distinctes dans un réseau local.

3. Un joueur doit pouvoir choisir avec qui il joue. 

4. Les bonus sont utilisables uniquement si l’unité associée est toujours « vivante ».

### Déroulement d’une partie : 

1.	Les deux joueurs se connectent. 
2.	Les deux joueurs placent leurs unités sur leur terrain de jeu.
3.	Une fois les deux joueurs prêts, le joueur A débute son tour.
4.	Une fois que le joueur A a fini son attaque, le tour est donné au joueur B. 
5.	Le point 4 est répété jusqu’à ce qu’un des deux joueurs :
    * n’ait plus d’unités.
    * quitte la partie. 

## Description des besoins fonctionnels :
Nous décrivons ci-dessous les besoins fonctionnels du programme pour un joueur : 

### Spécificité générale :

#### Interface de jeu :
* 2 grilles de 13x13 cases 
* Une grille sur laquelle le joueur place ses unités et voit les attaques infligées par son adversaire.
* Une grille sur laquelle le joueur peut placer ses propres attaques et en voir le résultat. 

#### Unités :
Le joueur dispose de 6 unités (3 <span style="color: brown">bâtiments</span> et 3 <span style="color: blue">véhicules</span>) :

|                     Unité                     | Taille |      Bonus      |
|-----------------------------------------------|:------:|-----------------|
| <span style="color: brown">Airport</span>     |   2x4  | Air-strike      |
| <span style="color: brown">Radar Tower</span> |   2x3  | Radar Discovery |
| <span style="color: brown">Headquarter</span> |   2x2  | /               |
| <span style="color: blue">Railway Gun</span>  |   1x6  | Big-shot        |
| <span style="color: blue">MMRL</span>         |   2x2  | Rocket-strike   |
| <span style="color: blue">Tank</span>         |   1x2  | /               |


#### Bonus :
*Air-strike* : Un avion largue des bombes sur 7 cases en ligne. <br>

*Radar Discovery* : 4 cases adjacentes une à une peuvent être découverte.<br>

*Big-shot* : Un obus de 800mm explose sur une case et détruit toutes les cases adjacentes.<br>

*Rocket-strike* : 4 missiles tombent dans une zone de 7x7 de manière aléatoire. <br>


## Use Case :
![com_serv-pc](img/Communication_Server-PC.png) 

Le serveur doit pouvoir :

1.	Établir une connexion entre deux joueurs sur un même réseau.
2.	Donner le tour à chacun des joueurs. 
3.	Recevoir les informations  du joueur qui a le tour. 
4.	Traiter les informations reçues. 
5.	Renvoyer des informations aux deux joueurs. (La case où le tir a été effectué, l’état d’une unité, …)

Le joueur doit pouvoir : 

1.	Se connecter au serveur.
2.	Avant le commencement de la partie, placer ses unités sur la grille de jeu. 
3.	Visualiser les deux grilles de jeu :
a.	Sa grille contenant ses unités et leur état. (non touchée, touchée, détruite)
b.	La grille de l’adversaire avec la position de ses propres tires et l’état de ceux-ci. (touché, non touché) (cette grille est vierge au début de la partie)
4.	Lorsque c’est son tour, tirer sur une case de la grille de son adversaire ou utiliser une attaque spéciale. 
5.	Lorsque ce n’est pas son tour, le joueur ne peut rien faire hormis observe l’attaque de son adversaire. 
6.	Quitter le jeu sans avoir terminé la partie. (L’adversaire aura alors gagné par abandon) 


