package jeudecombat;

import javax.management.InvalidAttributeValueException;

public class Joueur {
    private int vie;
    private int portee;
    private int defense;
    private int actions;
    private int force;

    public int x;
    public int y;

    public char caractere;

    public boolean cache;
    public boolean mort;

    public Joueur(int classe, int x, int y, char caractere){
        switch (classe) {
            case 1 -> { // Magicien
                this.vie = 12;
                this.portee = 3;
                this.defense = 12;
                this.force = 8;
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

    // --- Methodes --- //

    /**
     * Methode a appeler pour attaquer un joueur
     * @param joueur joueur à attaquer
     */
    public void attaquer(Joueur joueur){
        De deDegats = new De(5);
        joueur.recevoirAttaque(deDegats.lancer());
    }

    /**
     * Vérifie si un joueur donné est attaquable
     * @param joueur le joueur à attaquer
     * @return le joueur est-il attaquable ou non
     */
    public boolean peutAttaquer(Joueur joueur){
        int distance = Math.abs(joueur.x-this.x)+Math.abs(joueur.y-this.y);
        return distance<=this.portee;
    }

    private void recevoirAttaque(int degats){
        De deAttaque = new De(20);
        if(deAttaque.lancer()>this.defense){
            this.prendreDegats(degats);
        }
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
}