package jeudecombat;

public class De {
    public int taille;
    public De(int taille){
        this.taille = taille;
    }
    public int lancer(){
        return (int)(Math.random()*(this.taille+1));
    }
}
