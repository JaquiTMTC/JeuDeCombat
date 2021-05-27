package jeudecombat;

public class De {
    public int taille;
    public int ajout;

    public De(int taille, int ajout){
        this.taille = taille;
        this.ajout = ajout;
    }
    public int lancer(){
        return (int)(Math.random()*(this.taille+1))+ajout;
    }
}
