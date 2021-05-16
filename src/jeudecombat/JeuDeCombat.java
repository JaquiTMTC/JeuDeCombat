package jeudecombat;

import java.util.Scanner;
public class JeuDeCombat {
    static Scanner scanner = new Scanner( System.in );
    public static void main(String[] args) {
        char[] caracteres = {'@', '#', '&', '$'};
//        Joueur j1 = new Joueur(1, 8, 8, '#');
//        Joueur j2 = new Joueur(2, 1, 5, '@');
        // --- Initialisation du jeu ---
        int nbJoueurs = demanderEntier("Combien de joueurs êtes-vous ?",2,4);
        Joueur[] joueurs = new Joueur[nbJoueurs];

        int n = demanderEntier("Veuillez entre la taille de votre terrain (entrez un entier):", 7,15);
        char[][] terrain = new char [n][n];
        remplissageTerrain(terrain);

        for(int i=0; i<joueurs.length; i++){
            System.out.println("Joueur "+i+", c'est à vous de choisir votre classe."); /// Changer l'affichage des joueurs
            System.out.println("1) Guerrier : Détruit tous les buissons dans un rayon de 5 cases");
            System.out.println("2) Magicien : Attaque à distance à n'importe quel endroit et peut se téléporter");
            System.out.println("3) Voleur : Esquive les attaques et se rend invisible pendant un tour");
            int perso = demanderEntier("Veuillez entrer le numéro de votre classe :",1,3);
            joueurs[i]= new Joueur(perso,(int)(n*Math.random()),(int)(n*Math.random()), caracteres[i]); // changer la position
            modifStat(joueurs[i]);
        }
        while(!fini(joueurs)){
            for(int i=0; i<joueurs.length; i++){
                tour(joueurs, i, terrain);
            }
        }
        finPartie(trouverGagnant(joueurs));
    }

    public static void tour(Joueur[] joueurs, int principal, char[][] terrain) {
        int nbActions = joueurs[principal].getActions();
        int x;
        int y;
        int n = terrain.length;
        afficherJeu(terrain, joueurs);
        afficherStats(joueurs);
        while(nbActions!=0 && !fini(joueurs)){
            System.out.println("Joueur "+principal+", veuillez choisir une action :");
            System.out.println("1) Se déplacer");
            if(joueurs[principal].peutAttaquer(joueurs)){
                System.out.println("2) Attaquer");
            }
            if(nbActions == joueurs[principal].getActions()){ /// Séparer en plusieurs méthode le main est trop lourd
                System.out.println("3) Capacité spéciale 1 : "+joueurs[principal].getNomCapacite1()+" (1 fois par tour)");
                System.out.println("4) Capacité spéciale 2 : "+joueurs[principal].getNomCapacite2()+" (1 fois par tour)");
            }
            int action = demanderEntier("Veuillez entrer le numéro de l'action que vous souhaitez réaliser :",1,4);
            if(action==1){ // Déplacement
                x = demanderEntier("Veuillez entrer la colonne où vous souhaitez vous déplacer :", 0, n - 1);
                y = demanderEntier("Veuillez entrer la ligne où vous souhaitez vous déplacer :", 0, n - 1);
                while (!joueurs[principal].peutSeDeplacer(joueurs, x, y)) {
                    System.out.println("Impossible de se déplacer à cet endroit. Veuillez choisir une autre location.");
                    x = demanderEntier("Veuillez entrer la colonne où vous souhaitez vous déplacer :", 0, n - 1);
                    y = demanderEntier("Veuillez entrer la ligne où vous souhaitez vous déplacer :", 0, n - 1);
                }
                joueurs[principal].seDeplacer(x, y);
                afficherJeu(terrain, joueurs);

            }else if(action==2){ // Attaque
                if(!joueurs[principal].peutAttaquer(joueurs)){
                    System.out.println("Vous ne pouvez attaquer personne.");
                    break;
                }
                int joueurAAttaquer = demanderEntier("Veuillez entrer le numéro du joueur que vous souhaitez attaquer :",0,joueurs.length-1);
                while(!joueurs[principal].peutAttaquerJoueur(joueurs[joueurAAttaquer])){
                    System.out.println("Impossible d'attaquer ce joueur.");
                    joueurAAttaquer = demanderEntier("Veuillez entrer le numéro du joueur que vous souhaitez attaquer :",0,joueurs.length-1);
                }
                System.out.println("Vous effectuez "+joueurs[principal].attaquer(joueurs[joueurAAttaquer])+" degats.");

            }else if(action==3){

            }
            nbActions--;
        }
    }

    public static void afficherStats(Joueur[] joueurs){
        for(int i=0; i<joueurs.length; i++) {
            System.out.println("Stats --> Joueur "+i);
            System.out.println(joueurs[i]);
        }

    }

    public static void modifStat(Joueur joueur){
        int nbPoints = 5;
        int stat;
        int points;
        System.out.println("Vous pouvez maintenant répartir "+ nbPoints + " points dans vos statistiques.");
        while(nbPoints>0){
            System.out.println("Voici vos statistiques : \n"+joueur);
            stat = demanderEntier("Veuillez choisir le numéro de la statistique auquel vous voulez ajouter des points.", 1,4);
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
            }
            nbPoints -= points;
            if(nbPoints != 0){
                System.out.println("Vous pouvez encore répartir "+nbPoints+" points.");
            }
        }
        System.out.println("Vous avez maintenant fini de répartir vos points.");
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

    private static void finPartie(int gagnant) {
        System.out.println("Joueur "+gagnant+", vous avez gagné !");
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

    public static char joueurCase(char[][] terrain, Joueur[] joueurs, int x, int y){
        for(int i=0; i<joueurs.length; i++){
            if((joueurs[i].x == x) && (joueurs[i].y== y) && !joueurs[i].mort){
                return joueurs[i].caractere;
            }
        }
        return terrain[y][x];
    }

    public static void remplissageTerrain(char terrain [][]){
        for(int i = 0; i<terrain.length; i ++) {
            for (int j = 0; j < terrain[0].length; j++) {
                terrain[i][j]=' ';
            }
        }
    }

    /**Demander à l'interlocuteur un entier entre min et max (compris)
     * @param question
     * @param min
     * @param max
     * @return
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


//import java.awt.*;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
//
//public class JeuDeCombat extends Frame{
//
//    public JeuDeCombat(){
//        super();
//        prepareGUI();
//    }
//
//    private void prepareGUI() {
//        setSize(400, 400);
//        addWindowListener(new WindowAdapter() {
//            public void windowClosing(WindowEvent windowEvent){
//                System.exit(0);
//            }
//        });
//    }
//
//    public static void main(String[] args){
//        JeuDeCombat jeuDeCombat = new JeuDeCombat();
//        jeuDeCombat.setVisible(true);
//    }
//
//    //@Override
//    public void paint(Graphics g){
//        g.drawString("YOOOOO", 50, 150);
//    }
//}
