package jeudecombat;

public class Joueur {
    private int vie;
    private int portee;
    private int defense;
    private int actions;
    private int force;
    private final int classe;
    private int dexterite;

    public int x;
    public int y;

    public final char caractere;

    //public boolean cache = false;
    private boolean mort = false;

    private String nomCapacite1;
    private String nomCapacite2;

    public Joueur(int classe, int x, int y, char caractere){
        this.classe = classe;
        switch (classe) {
            case 1 -> { // Magicien
                this.vie = 12;
                this.portee = 5;
                this.defense = 10;
                this.force = 8;
                this.dexterite = 12;
                this.nomCapacite1 = "Attaque à distance";
                this.nomCapacite2 = "Fuite tactique";
            }
            case 2 -> { // Guerrier
                this.vie = 15;
                this.portee = 4;
                this.defense = 8;
                this.force = 12;
                this.dexterite = 10;
                this.nomCapacite1 = "Souffle rageur";
                this.nomCapacite2 = "Vague de dégats";
            }
            case 3 -> { // Voleur
                this.vie = 10;
                this.portee = 6;
                this.defense = 12;
                this.force = 8;
                this.dexterite = 14;
                this.nomCapacite1 = "Vol de vie";
                this.nomCapacite2 = "Vol la position";
            }
        }
        this.caractere = caractere;
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

    public int getDexterite() {
        return dexterite;
    }

    public void setDexterite(int dexterite) {
        if (dexterite >= 0) {
            this.dexterite = dexterite;
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

    public int getClasse(){
        return this.classe;
    }

    public boolean getMort(){
        return mort;
    }

    // --- Methodes --- //

    public String nomClasse(){
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

    /**
     * Attaque le joueur passé en paramètres.
     * @param joueur Joueur à attaquer
     * @return Les dégâts effectués
     */
    public int attaquer(Joueur joueur){
        De deDegats = new De(5, this.force/4);
        De deAttaque = new De(20, this.dexterite/2);
        if(deAttaque.lancer()>joueur.defense){
            int degats = deDegats.lancer();
            joueur.prendreDegats(degats);
            return degats;
        }
        return 0;
    }

    /**
     * Donne la distance de manhattan entre le joueur donné et 'this'
     * @param joueur
     * @return La distance de manhattan entre le joueur donné et 'this'
     */
    public int distance(Joueur joueur){
        return distance(joueur.x, joueur.y);
    }

    /**
     * Donne la distance de manhattan entre la position donnée et le joueur
     * @param x Position x
     * @param y Position y
     * @return La distance de manhattan entre la position donnée et le joueur
     */
    public int distance(int x, int y){
        return Math.abs(x-this.x)+Math.abs(y-this.y);
    }

    /**
     * Vérifie si un joueur donné est attaquable
     * @param joueur le joueur à attaquer
     * @return le joueur est-il attaquable ou non
     */
    public boolean peutAttaquerJoueur(Joueur joueur){
        return this.distance(joueur)<=this.portee;
    }

    /**
     * Vérifie que le joueur peut attaquer au moins une autre personne
     * @param joueurs Liste des joeurs
     * @return Si le joueur peut attaquer ou non
     */
    public boolean peutAttaquer(Joueur[] joueurs){
        for(int i=0; i<joueurs.length; i++){
            if(this.peutAttaquerJoueur(joueurs[i]) && this != joueurs[i]){
                return true;
            }
        }
        return false;
    }

    /**
     * Methode à appeler pour que le personnage prenne des dégats
     * @param degats les dégats à appliquer
     */
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

    public boolean estCache(char[][] terrain){
        return terrain[y][x]=='O';
    }

    /**
     * Place aléatoirement le joueur sur une position valide (pas d'autre joueur, pas de buisson)
     * @param terrain
     * @param joueurs
     */
    public void placementAleatoire(char[][] terrain, Joueur[] joueurs){
        int n = terrain.length;
        int x, y;
        boolean joueurPos = false;
        do {
            x = (int)(Math.random()*n);
            y = (int)(Math.random()*n);
            for(int i=0; i< joueurs.length; i++){
                if(joueurs[i].x == x && joueurs[i].y == y){
                    joueurPos = true;
                    break;
                }
            }
        } while(terrain[y][x]=='O' || joueurPos);
        this.seDeplacer(x, y);
    }

    /**
     * Vérifie si le joueur peut se déplacer aux coordonnées données
     * @param joueurs liste des joueurs sur le terrain
     * @param terrain
     * @param x coordonnées x du point d'arrivée
     * @param y coordonnées y du point d'arrivée
     * @return le joueur peut ou non se deplacer
     */
    public boolean peutSeDeplacer(Joueur[] joueurs, char[][] terrain, int x, int y) {
        if (x < 0 || y < 0 || x >= terrain.length || y >= terrain.length) {
            return false;
        }
        for (int i = 0; i < joueurs.length; i++) {
            if (joueurs[i] != this && joueurs[i].x == x && joueurs[i].y == y) {
                return false;
            }
        }
        return this.distance(x, y) <= this.portee;
    }

    /**
     * Attaque un joueur se tenir compte de la portée
     * @param joueur Le joueur à attaquer
     * @return les dégats effectués
     */
    public int capacite1Magicien(Joueur joueur){
        return this.attaquer(joueur);
    }

    /**
     * Se déplacer sans tenir compte de la portée
     * @param x
     * @param y
     */
    public void capacite2Magicien(int x, int y){
        this.seDeplacer(x, y);
    }

    public void capacite1Guerrier(char[][] terrain){
        int distance;
        for(int i=0; i<terrain.length; i++){
            for(int j=0; j<terrain[i].length; j++){
                if(terrain[i][j]=='O' && this.distance(j, i)<=4){
                    terrain[i][j] = ' ';
                }
            }
        }
    }

    /**
     * Attaque tous les joueurs dans un rayon de 4
     * @param joueurs Liste des joueurs
     * @return Total des dégâts effectués
     */
    public int capacite2Guerrier(Joueur[] joueurs){
        int total=0;
        for(int i = 0; i<joueurs.length; i++){
            if(distance(joueurs[i])<4 && joueurs[i]!= this){
                total += attaquer(joueurs[i]);
            }
        }
        return total;
    }

    /**
     * Vol de vie à un joueur qu'on attaque
     * @param joueur
     * @return
     */
    public int capacite1Voleur(Joueur joueur){
        int degats = attaquer(joueur);
        this.vie += (int)Math.ceil((double)degats/2);
        return degats;
    }

    public void capacite2Voleur(Joueur joueur){
        int x = this.x;
        int y = this.y;
        this.seDeplacer(joueur.x, joueur.y);
        joueur.seDeplacer(x, y);
    }

    public String toString(){
        String string = this.nomClasse()+" de symbole " + this.caractere + "\n 1) Vie : " + this.vie+"\n 2) Force : "
                + this.force+"\n 3) Portée : " + this.portee+"\n 4) Défense : " + this.defense
                +"\n 5) Dexterité : " + this.dexterite;

        return string;
    }
}