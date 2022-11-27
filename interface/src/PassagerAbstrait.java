package tec;


abstract public class PassagerAbstrait implements Passager,Usager {

    private String nom;
    private int destination;
    private Position maPosition;

    final private ArretComportement comportement;
    
    public PassagerAbstrait(String nom, int destination, ArretComportement comportement) {
	//ici, on peut lever une exception sur la destination si elle est negative, comportement en réalitée non réèl.
	if(destination<0){
	    throw new IllegalArgumentException("La destination d'un passager est toujours plus grande ou égale à 0.");
	}
	//voilà.
        this.nom = nom;
        this.destination = destination;
        this.maPosition = Position.dehors();
	this.comportement=comportement;
    }

    public String nom() {
        return nom;
    }

    public boolean estDehors() {
        return maPosition.estDehors();
    }

    public boolean estAssis() {
        return maPosition.estAssis();
    }

    public boolean estDebout() {
        return maPosition.estDebout();
    }

    public void changerEnDehors() {
        maPosition = Position.dehors();
    }

    public void changerEnAssis() {
        maPosition = Position.assis();
    }

    public void changerEnDebout() {
        maPosition = Position.debout();
    }

    public void monterDans(Transport t) throws TecException {//l'interface Usager qui throws une
	//exception pas besoin mais ON va le faire.(obligee)
	Vehicule v=(Vehicule)t;
	if(!(v instanceof Vehicule)) //instanceof par instanceOf
	    throw new TecException("Le transport n'a pas être convertie au bon type pour son traitement ultèrieur");

	
	try{
        choixPlaceMontee(v);
	}catch(IllegalStateException e){//probleme deja resolu indirectement.
	    throw new TecException(e);
	}//normal de faire ceci dans un scenario de modelisation informatique
	//en monde reel pas virtuel.
    }
	

    //	choixPlaceMontee(v);
    
	
    //vrai message d'erreur.
    //voir la doc dans: java.lang.Throwable qui est une CLASSE !

    public void nouvelArret(Vehicule v, int numeroArret) {
        if (numeroArret == destination)
            v.arretDemanderSortie(this);
	comportement.choixPlaceArret(this,v,destination-numeroArret);
    }

    public String toString() {
        return nom + " " + maPosition;
    }

    abstract void choixPlaceMontee(Vehicule v);
}
