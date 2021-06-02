# JeuDeCombat

## Description du jeu:

Ce que fait le joueur (1) et (2):

1. Afficher différents personnages et compétences / Choix du personnages
2. Répartition des points (ex : 5 points de vie) parmis les attributs :
    * Vie
    * Attaque
    * Défense
    * Portée d'attaque
3. Génération du terrain

À chaque tour 2 actions:

1. Se déplacer (1 action)
2. Attaquer (ou casser buissons)(1 action)
3. Pouvoir (2 actions)

Types de terrain /obstacle :

* 7*7 cases ?
* Cailloux (#)
* Potions (@)
* Buissons (O)

Types de personnages :

1. Le guerrier

    * Détruit tous les buissons dans un rayon de 5 cases
    * Attaque tous les personnages dans un rayon de 5 cases

2. Le magicien

    * Attaque à distance de n'importe quel endroit
    * Fuite tactique (tp)

3. Le voleur

    * Vole des vies
    * Vole une position

Jauge/Capacités des personnages :

* Vie
* Puissance
* Défence
* Portée (jusqu'à 3 max)
* Déplacement

## Organisation du projet :
Classe :

* les personnages
* le terrain

Méthode :

* afficher jeu : montre le terrain avec les personnages, potions, obstacles
* afficher personnages
* choix personnage et placement des points
* se déplacer : Donne une position et si pas atteignable, le jeu signale et redemande
* attaquer : Dans un certain carré (portée), on enlève un certain nombre de point de vie à sa cible en fonction de la puissance du personnage
* utilisation des pouvoirs
* utilisation des potions : dès qu'on la touche --> + 2 pts de vie

Organisation de la colaboration :

* travail par Code With Me sur IntelIJ
* utilisation de Git

Séparation des tâches :

* Une personne s'occupe de la classe Joueur
* Les autres s'occupent de toutes les méthodes de la classe principale en lien avec l'affichage du jeu, l'interaction avec l'utilisateur et le déroulement global
d'une partie
