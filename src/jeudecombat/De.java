package jeudecombat;

public class De {
    public final int taille;
    public final int modificateur;

    public De(int taille, int modificateur){
        this.taille = taille;
        this.modificateur = modificateur;
    }

    /**
     * Lance un dé de taille "taille"
     * @return (entier entre 1 et taille)+modificateur
     */
    public int lancer(){
        return (int)(Math.random()*this.taille+1)+ modificateur;
    }
}
