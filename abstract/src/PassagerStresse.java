package tec;

public class PassagerStresse extends PassagerStandard
{
     public PassagerStresse(String nom, int destination)
    {
	super(nom,destination);
    }

    /*void nouvelArret(Vehicule v, int numeroArret)
    {
	if(this.getDest()-numeroArret==0 && v.aPlaceDebout()){
	    v.arretDemanderDebout(this);
	}
	super.nouvelArret(v,numeroArret);
	}*/

    void choixPlaceMontee(Vehicule v)
    {
	 if (v.aPlaceAssise()) {
            v.monteeDemanderAssis(this);
        } else if (v.aPlaceDebout()) {
            v.monteeDemanderDebout(this);
        }
    }

    void choixPlaceArret(Vehicule v, int arret)
    {
	if(destination-arret==3 && v.aPlaceDebout()){
	    v.arretDemanderDebout(this);
	}
    }
}
