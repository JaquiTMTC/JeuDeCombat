package jeudecombat;

import java.util.Scanner;
public class JeuDeCombat {
    static Scanner scanner = new Scanner( System.in );
    public static void main(String[] args) {

        Joueur j1 = new Joueur(1, 8, 8, '#');
        Joueur j2 = new Joueur(2, 1, 5, '@');
//        int nbJoueurs = demanderEntier("Combien de joueurs êtes-vous ?",2,4);
//        Joueur[] joueurs = new Joueur[nbJoueurs];
//
//        int n = demanderEntier("Veuillez entre la taille de votre terrain (entrez un entier):", 7,15);
//        char[][] terrain = new char [n][n];
//        remplissageTerrain(terrain);
//
//        for(int i=0; i<joueurs.length; i++){
//
//            System.out.println("1) Guerrier : Détruit tous les buissons dans un rayon de 5 cases");
//            System.out.println("2) Magicien : Attaque à distance à n'importe quel endroit et peut se téléporter");
//            System.out.println("3) Voleur : Esquive les attaques et se rend invisible pendant un tour");
//            int perso = demanderEntier("Veuillez entrer le numéro de votre personnage :",1,3);
//            joueurs[i]=(perso,(int)(n*Math.random()),(int)(n*Math.random()); //à changer
//
//
//        }
//        while(!fini(joueurs)){
//            afficherJeu(terrain, joueurs);
//            afficherStat
//            for(int i=0; i<joueurs.length; i++){
//                tour(joueurs, joueurs[i]);
//            }
//        }
        Joueur[] joueurs = {j1, j2};
        char[][] terrain = new char[10][10];
        remplissageTerrain(terrain);
        afficherJeu(terrain, joueurs);
    }

    public static void tour(Joueur[] joueurs, Joueur principal,int n) {
        int nbActions = principal.getActions();
        while(nbActions!=0){
            System.out.println("1) Se déplacer");
            System.out.println("2) Attaquer");
            int action = demanderEntier("Veuillez entrer le numéro de l'action que vous souhaitez réaliser :",1,2);
            if(action==1){
                int x = demanderEntier("Veuillez entrer la colonne où vous souhaitez vous déplacer :",0, n-1);
                int y = demanderEntier("Veuillez entrer la ligne où vous souhaitez vous déplacer :", 0 ,n-1);
                if(principal.peutSeDeplacer(joueurs,x,y)){

                }

            }else if(action==2){
                int joueurAAttaquer = demanderEntier("Veuillez entrer le numéro du joueur que vous souhaitez attaquer :",0,joueurs.length);

                if(Joueur principal.peutAttaquer())

            }


            nbActions--;
        }
    }

    public static boolean fini(Joueur[] joueurs) {
        for(int i=0; i<joueurs.length; i++){
            int vivant = 0;
            if(!joueurs[i].mort){
                vivant++;
            }
            if(vivant>1){
                return false;
            }
        }
        return true;
    }

    private static void finPartie(Joueur joueur) {
        System.out.println(joueur + ", vous avez gagné !");
    }

    public static void afficherJeu(char terrain [][], Joueur joueurs[]) {
        boolean testPresence;
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
            if((joueurs[i].x == x) && (joueurs[i].y== y)){
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

    /**
     * @param question
     * @param min
     * @param max
     * @return
     */
    public static int demanderEntier(String question, int min, int max){
        System.out.println(question);
        int reponse = scanner.nextInt();
        while(min>reponse || reponse >= max) {
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
