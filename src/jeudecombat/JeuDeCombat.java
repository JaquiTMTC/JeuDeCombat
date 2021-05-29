package jeudecombat;

import java.util.Scanner;

public class JeuDeCombat {
    static Scanner scanner = new Scanner( System.in );
    public static void main(String[] args) {
        char[] caracteres = {'@', '#', '&', '$'};
        // --- Bienvenue --- //
        System.out.println(
                "_________ _______               ______   _______      _______  _______  _______  ______   _______ _________\n" +
                "\\__    _/(  ____ \\|\\     /|    (  __  \\ (  ____ \\    (  ____ \\(  ___  )(       )(  ___ \\ (  ___  )\\__   __/\n" +
                "   )  (  | (    \\/| )   ( |    | (  \\  )| (    \\/    | (    \\/| (   ) || () () || (   ) )| (   ) |   ) (   \n" +
                "   |  |  | (__    | |   | |    | |   ) || (__        | |      | |   | || || || || (__/ / | (___) |   | |   \n" +
                "   |  |  |  __)   | |   | |    | |   | ||  __)       | |      | |   | || |(_)| ||  __ (  |  ___  |   | |   \n" +
                "   |  |  | (      | |   | |    | |   ) || (          | |      | |   | || |   | || (  \\ \\ | (   ) |   | |   \n" +
                "|\\_)  )  | (____/\\| (___) |    | (__/  )| (____/\\    | (____/\\| (___) || )   ( || )___) )| )   ( |   | |   \n" +
                "(____/   (_______/(_______)    (______/ (_______/    (_______/(_______)|/     \\||/ \\___/ |/     \\|   )_(   \n"
        );

        // --- Initialisation du jeu --- //
        instruction();
        int nbJoueurs = demanderEntier("Combien de joueurs êtes-vous ?",2,4);
        Joueur[] joueurs = new Joueur[nbJoueurs];

        int n = demanderEntier("Veuillez entre la taille de votre terrain (entrez un entier):", 7,15);
        char[][] terrain = new char [n][n];
        remplissageTerrain(terrain);

        for(int i=0; i<joueurs.length; i++){
            joueurs[i] = creerJoueur(caracteres[i], i);
            modifStat(joueurs[i]);
        }
        for(int i=0; i<joueurs.length; i++){
            joueurs[i].placementAleatoire(terrain, joueurs);
        }

        // --- Jeu --- //
        while(!fini(joueurs)){
            for(int i=0; i<joueurs.length; i++){
                tour(joueurs, i, terrain);
            }
        }

        // --- Fin ---
        finPartie(trouverGagnant(joueurs));
    }

    // --- Methodes d'interaction avec les utilisateurs --- //

    public static Joueur creerJoueur(char caractere, int i) {
        System.out.println("Joueur " + (i+1) + ", c'est à vous de choisir votre personnage."); /// Changer l'affichage des joueurs
        System.out.println("1) Magicien : Attaque à distance à n'importe quel endroit et peut se téléporter.");
        System.out.println("2) Guerrier : Détruit tous les buissons dans un rayon de 5 cases et peut faire une attaque hyperpuissante.");
        System.out.println("3) Voleur : Vole des vies et la position de son adversaire.");
        int perso = demanderEntier("Veuillez entrer le numéro de votre personnage :", 1, 3);
        return new Joueur(perso, 0, 0, caractere);
    }

    public static void instruction() {
        int res = demanderEntier("Voulez vous les règles du jeu ? \n Entrez 0 pour \"oui\" et 1 pour \"non\".", 0,1);
        if(res == 0){
            System.out.println("Instructions du jeu de combat :\n - Début de partie : \n Vous entrez le nombre de joueurs (entre 2 et 4) ainsi que la taille du terrain. Chaque joueur peut choisir un personnage parmi : le Magicien, le Guerrier et le Voleur. Les joueurs apparaissent sur le terrain avec les symboles : @ (joueur 1), # (joueur 2), & (joueur 3), # (joueur 4). Chaque joueur possède un pouvoir particulier qui vous sera expliqué au moment de choisir votre personnage. Vous avez ensuite accès aux statistiques du joueur : nombres de vies, portée, défense, force, dextérité. Vous pouvez alors répartir 5 points supplémentaires dans ces statistiques. \n - Déroulement du tour : \n A chaque tour, le joueur peut soit réaliser deux actions entre \"se déplacer et attaquer\", soit utiliser une de ses capacités spéciales. \n - Fin du jeu :\n Le gagnant est le dernier joueur à rester en vie. ");
            System.out.println("/!\\ Petit plus : Vous pouvez vous cacher sous des buissons, posés aléatoirement sur le plateau. Ils sont représentés par des O.");
        }
    }

    public static void modifStat(Joueur joueur){
        int nbPoints = 5;
        int stat;
        int points;
        System.out.println("Vous pouvez maintenant répartir "+ nbPoints + " points dans vos statistiques.");
        while(nbPoints>0){
            System.out.println("Voici vos statistiques : \n"+joueur);
            stat = demanderEntier("Veuillez choisir le numéro de la statistique auquel vous voulez ajouter des points.", 1,5);
            points = demanderEntier("Combien de points voulez-vous ajouter à cette statistique ?",1,nbPoints);
            switch (stat) {
                case 1 -> {
                    joueur.setVie(joueur.getVie() + points);
                }
                case 2 -> {
                    joueur.setForce(joueur.getForce() + points);
                }
                case 3 -> {
                    joueur.setPortee(joueur.getPortee() + points);
                }
                case 4 -> {
                    joueur.setDefense(joueur.getDefense() + points);
                }
                case 5 -> {
                    joueur.setDexterite(joueur.getDexterite() + points);
                }
            }
            nbPoints -= points;
            if(nbPoints != 0){
                System.out.println("Vous pouvez encore répartir "+nbPoints+" points.");
            }
        }
        System.out.println("Vous avez maintenant fini de répartir vos points. Et maintenant, place au jeu !");
    }

    public static void tour(Joueur[] joueurs, int principal, char[][] terrain) {
        int nbActions = joueurs[principal].getActions();
        while(nbActions!=0 && !fini(joueurs)){
            afficherJeu(terrain, joueurs);
            afficherStats(joueurs);
            int actionMax = 1;
            int actionAttaque = 0;
            int actionSpe1 = 0;
            int actionSpe2 = 0;
            System.out.println("Joueur "+ (principal+1) +", veuillez choisir une action :");
            System.out.println("1) Se déplacer");
            if(joueurs[principal].peutAttaquer(joueurs)){
                actionMax++;
                actionAttaque = actionMax;
                System.out.println("2) Attaquer");
            }
            if(nbActions>=2){
                actionMax++;
                actionSpe1 = actionMax;
                System.out.println(actionMax+") Capacité spéciale : "+joueurs[principal].getNomCapacite1()+" (1 fois par tour)");
                actionMax++;
                actionSpe2 = actionMax;
                System.out.println(actionMax+") Capacité spéciale : "+joueurs[principal].getNomCapacite2()+" (1 fois par tour)");
            }
            int action = demanderEntier("Veuillez entrer le numéro de l'action que vous souhaitez réaliser :",1,actionMax);
            if(action==1){
                deplacement(joueurs, principal, terrain);
                nbActions--;

            }else if(action==actionAttaque){
                nbActions = attaque(joueurs, principal, nbActions);

            }else if(action==actionSpe1){
                nbActions = capaciteSpeciale1(joueurs, principal, terrain, nbActions);

            }else if(action==actionSpe2){
                capaciteSpeciale2(joueurs, principal, terrain);
                nbActions-=2;
            }
        }
    }

    // --- Méthodes correspondant à chaque action ---//

    public static void deplacement(Joueur[] joueurs, int principal, char[][] terrain){
        int x;
        int y;
        int n = terrain.length;
        x = demanderEntier("Veuillez entrer la colonne où vous souhaitez vous déplacer :", 0, n - 1);
        y = demanderEntier("Veuillez entrer la ligne où vous souhaitez vous déplacer :", 0, n - 1);
        while (!joueurs[principal].peutSeDeplacer(joueurs, x, y)) {
            System.out.println("Impossible de se déplacer à cet endroit. Veuillez choisir une autre location.");
            x = demanderEntier("Veuillez entrer la colonne où vous souhaitez vous déplacer :", 0, n - 1);
            y = demanderEntier("Veuillez entrer la ligne où vous souhaitez vous déplacer :", 0, n - 1);
        }
        joueurs[principal].seDeplacer(x, y);
    }

    public static int attaque(Joueur[] joueurs, int principal, int nbActions){
        if(!joueurs[principal].peutAttaquer(joueurs)){
            System.out.println("Vous ne pouvez attaquer personne.");
            return nbActions;
        }
        int joueurAAttaquer = demanderEntier("Veuillez entrer le numéro du joueur que vous souhaitez attaquer :",1,joueurs.length)-1;
        while(!joueurs[principal].peutAttaquerJoueur(joueurs[joueurAAttaquer])){
            System.out.println("Impossible d'attaquer ce joueur.");
            joueurAAttaquer = demanderEntier("Veuillez entrer le numéro du joueur que vous souhaitez attaquer :",1,joueurs.length)-1;
        }
        System.out.println("Vous avez effectué "+joueurs[principal].attaquer(joueurs[joueurAAttaquer])+" dégats.");
        return nbActions-1;
    }

    public static int capaciteSpeciale1(Joueur[] joueurs, int principal, char[][] terrain, int nbActions){
        switch(joueurs[principal].getClasse()){
            case 1 -> { // Magicien
                int joueurAAttaquer = demanderEntier("Veuillez entrer le numéro du joueur que vous souhaitez attaquer, sans tenir compte de votre portée :",1,joueurs.length)-1;
                System.out.println("Vous effectuez "+joueurs[principal].capacite1Magicien(joueurs[joueurAAttaquer])+" dégâts.");

            }
            case 2 -> { // Guerrier
                joueurs[principal].capacite1Guerrier(terrain);
                System.out.println("Tous les buissons aux alentours ont été détruits par votre souffle ravageur.");

            }
            case 3 -> { // Voleur
                if(!joueurs[principal].peutAttaquer(joueurs)){
                    System.out.println("Vous ne pouvez attaquer personne.");
                    return nbActions;
                }
                int joueurAAttaquer = demanderEntier("Veuillez entrer le numéro du joueur que vous souhaitez attaquer et prendre les vies :",1,joueurs.length)-1;
                while(!joueurs[principal].peutAttaquerJoueur(joueurs[joueurAAttaquer])){
                    System.out.println("Impossible d'attaquer ce joueur.");
                    joueurAAttaquer = demanderEntier("Veuillez entrer le numéro du joueur que vous souhaitez attaquer :",1,joueurs.length)-1;
                }
                System.out.println("Vous effectuez "+joueurs[principal].capacite1Voleur(joueurs[joueurAAttaquer])+" dégâts et gagnez autant en vies.");
            }
        }
        return nbActions-2;
    }

    public static void capaciteSpeciale2(Joueur[] joueurs, int principal, char[][] terrain){
        int n = terrain.length;
        switch (joueurs[principal].getClasse()){
            case 1 -> { // Magicien
                int x;
                int y;
                System.out.println("Vous pouvez maintenant vous téléporter !!!");
                x = demanderEntier("Veuillez entrer la colonne où vous souhaitez vous déplacer :", 0, n - 1);
                y = demanderEntier("Veuillez entrer la ligne où vous souhaitez vous déplacer :", 0, n - 1);
                while(trouverJoueur(joueurs, x, y)){
                    System.out.println("Vous ne pouvez pas vous déplacer ici");
                    x = demanderEntier("Veuillez entrer la colonne où vous souhaitez vous déplacer :", 0, n - 1);
                    y = demanderEntier("Veuillez entrer la ligne où vous souhaitez vous déplacer :", 0, n - 1);
                }
                joueurs[principal].capacite2Magicien(x, y);
            }
            case 2 -> { // Guerrier
                System.out.println("Vous effectuez un total de "+joueurs[principal].capacite2Guerrier(joueurs)+" dégats.");
            }
            case 3 -> { // Voleur
                int joueurAEchanger = demanderEntier("Veuillez entrer le numéro du joueur avec qui vous voulez permuter :",1,joueurs.length)-1;
                joueurs[principal].capacite2Voleur(joueurs[joueurAEchanger]);
            }
        }
    }

    // --- Méthodes d'affichage --- //

    public static void afficherStats(Joueur[] joueurs){
        for(int i=0; i<joueurs.length; i++) {
            if(joueurs[i].getVie()>0) {
                System.out.println("Stats --> Joueur " + (i + 1));
                System.out.println(joueurs[i]);
            }else{
                System.out.println("Joueur "+ (i+1) + " est mort.");
            }
        }
    }

    private static void finPartie(int gagnant) {
        System.out.println("Joueur "+ (gagnant+1) +", vous avez triomphé de tous vos adversaires les plus coriaces !");
    }

    public static void afficherJeu(char terrain [][], Joueur joueurs[]) {
        System.out.print("| |");
        for (int i = 0; i < terrain[0].length; i++) {
            System.out.print(i+"|");
        }
        System.out.println();
        for(int i=0; i<terrain.length; i ++){ // parcourt les lignes
            System.out.print("|"+i+"|");
            for(int j=0; j<terrain[0].length; j++){ // parcours les colonnes
                System.out.print(joueurCase(terrain, joueurs, j, i) + "|");
            }
            System.out.println();
        }
    }

    // --- Méthodes diverses --- //

    /**
     * Cherche si un joueur est présent à la position donnée
     * @param joueurs Liste des joueurs
     * @param x
     * @param y
     * @return Joueur présent ou non
     */
    private static boolean trouverJoueur(Joueur[] joueurs, int x, int y) {
        for(int i=0; i<joueurs.length; i++){
            if(joueurs[i].x == x && joueurs[i].y == y){
                return true;
            }
        }
        return false;
    }

    public static boolean fini(Joueur[] joueurs) {
        int vivant = 0;
        for(int i=0; i<joueurs.length; i++){
            if(!joueurs[i].mort){
                vivant++;
            }
            if(vivant>1){
                return false;
            }
        }
        return true;
    }

    public static int trouverGagnant(Joueur[] joueurs) {
        for(int i=0; i<joueurs.length; i++){
            if(!joueurs[i].mort){
                return i;
            }
        }
        return -1;
    }

    /**
     * Pour une case donnée, renvoie sa représentation pour les utilisateurs selon qu'un joueur y soit présent ou non
     * @param terrain
     * @param joueurs
     * @param x
     * @param y
     * @return la représentation de la case du derrain
     */
    public static char joueurCase(char[][] terrain, Joueur[] joueurs, int x, int y){
        for(int i=0; i<joueurs.length; i++){
            if((joueurs[i].x == x) && (joueurs[i].y== y) && !joueurs[i].mort && !joueurs[i].estCache(terrain)){
                return joueurs[i].caractere;
            }
        }
        return terrain[y][x];
    }

    public static void remplissageTerrain(char terrain [][]){
        for(int i = 0; i<terrain.length; i ++) {
            for (int j = 0; j < terrain[0].length; j++) {
                if(Math.random()<0.1) {
                    terrain[i][j]='O';
                } else {
                    terrain[i][j]=' ';
                }
            }
        }
    }

    /**
     * Demande à l'utilisateur un entier entre min et max
     * @param question Question à poser à l'utilisateur
     * @param min Minimum accepté (compris)
     * @param max Maximum accepté (compris)
     * @return La réponse de l'utilisateur
     */
    public static int demanderEntier(String question, int min, int max){
        System.out.println(question + " (nombre entre "+min+" et "+max+")");
        int reponse = scanner.nextInt();
        while(min>reponse || reponse > max) {
            System.out.println("Réponse invalide. "+question);
            reponse = scanner.nextInt();
        }
        return reponse;
    }
}