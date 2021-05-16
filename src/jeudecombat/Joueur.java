package jeudecombat;

import java.util.Scanner;

public class Joueur {
    private int vie;
    private int portee;
    private int defense;
    private int actions;
    private int force;
    private int classe;

    public int x;
    public int y;

    public char caractere;

    public boolean cache = false;
    public boolean mort = false;

    private String nomCapacite1;
    private String nomCapacite2;

    public Joueur(int classe, int x, int y, char caractere){
        this.classe = classe;
        switch (classe) {
            case 1 -> { // Magicien
                this.vie = 12;
                this.portee = 3;
                this.defense = 12;
                this.force = 8;
                this.nomCapacite1 = "Attaque à distance";
                this.nomCapacite2 = "Fuite tactique";
            }
            case 2 -> { // Guerrier
                this.vie = 15;
                this.portee = 2;
                this.defense = 10;
                this.force = 12;
            }
            case 3 -> { // Voleur
                this.vie = 10;
                this.portee = 4;
                this.defense = 14;
                this.force = 8;
            }
        }
        this.caractere = caractere;
        this.cache = false;
        this.mort = false;
        this.actions = 2;
        this.x = x;
        this.y = y;
    }

    // --- Getters et Setters --- //

    public int getVie() {
        return vie;
    }

    public void setVie(int vie){
        if(vie>=0){
            this.vie = vie;
        }
    }

    public int getPortee() {
        return portee;
    }

    public void setPortee(int portee) {
        if (portee>=0){
            this.portee = portee;
        }
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        if (defense>=0){
            this.defense = portee;
        }
    }

    public int getForce() {
        return force;
    }

    public void setForce(int force) {
        if(force>=0) {
            this.force = force;
        }
    }

    public int getActions() {
        return actions;
    }

    public String getNomCapacite1() {
        return nomCapacite1;
    }

    public String getNomCapacite2() {
        return nomCapacite2;
    }

    public String getClasse(){
        switch (this.classe){
            case 1 -> {
                return "Magicien";
            }
            case 2 -> {
                return "Guerrier";
            }
            case 3 -> {
                return "Voleur";
            }
        }
        return "indéfini";
    }

    // --- Methodes --- //

    /**
     * Methode a appeler pour attaquer un joueur
     * @param joueur joueur à attaquer
     */
    public int attaquer(Joueur joueur){
        De deDegats = new De(5, 5); /// ajouter force
        De deAttaque = new De(20, 5); // modifier selon attributs joueur
        if(deAttaque.lancer()>joueur.defense){
            int degats = deDegats.lancer();
            joueur.prendreDegats(degats);
            return degats;
        }
        return 0;
    }

    /**
     * Vérifie si un joueur donné est attaquable
     * @param joueur le joueur à attaquer
     * @return le joueur est-il attaquable ou non
     */
    public boolean peutAttaquerJoueur(Joueur joueur){
        int distance = Math.abs(joueur.x-this.x)+Math.abs(joueur.y-this.y);
        return distance<=this.portee;
    }

    public boolean peutAttaquer(Joueur[] joueurs){
        for(int i=0; i<joueurs.length; i++){
            if(this.peutAttaquerJoueur(joueurs[i]) && this != joueurs[i]){
                return true;
            }
        }
        return false;
    }

    private void prendreDegats(int degats) {
        if(this.vie>degats){
            this.vie -= degats;
        } else {
            this.vie = 0;
            this.mort = true;
        }
    }

    public void seDeplacer(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Vérifie si le joueur peut se déplacer aux coordonnées données
     * @param joueurs liste des joueurs sur le terrain
     * @param x coordonnées x du point d'arrivée
     * @param y coordonnées y du point d'arrivée
     * @return le joueur peut ou non se deplacer
     */
    public boolean peutSeDeplacer(Joueur[] joueurs, int x, int y){
        for(int i=0; i<joueurs.length; i++){
            if(joueurs[i] != this && joueurs[i].x == x && joueurs[i].y == y){
                return false;
            }
        }
        int distance = Math.abs(x-this.x)+Math.abs(y-this.y);
        return distance<=this.portee;
    }

//    public void capacite1(){
//        Scanner sc = new Scanner(System.in);
//        switch (this.classe){
//            case 1 -> { // Magicien
//                System.out.println("Veuillez choisir un joueur à attaquer");
//
//            }
//            case 2 -> {
//
//            }
//            case 3 -> {
//
//            }
//        }
//    }

    public void capacite2(){

    }

    public String toString(){
        String string = this.getClasse()+" "+this.caractere+"\n 1) Vies : " + this.getVie()+"\n 2) Force : "
                + this.getForce()+"\n 3) Portée : " + this.getPortee()+"\n 4) Défense : " + this.getDefense()+"\n";
        return string;
    }
}