package jeudecombat;

public class De {
    public final int taille;
    public final int modificateur;

    public De(int taille, int modificateur){
        this.taille = taille;
        this.modificateur = modificateur;
    }
    public int lancer(){
        return (int)(Math.random()*(this.taille+1))+ modificateur;
    }
}
