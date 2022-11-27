package tec;
//import java.util.List;(l'interface de ArrayList)

import java.util.ArrayList;
import java.util.ListIterator;//classe qui étend Iterator.
import java.util.List;

//ou bien import java.util.* mais ca va importer des choses
//dont on a pas besoin. 

class Autobus implements Vehicule, Transport {
    //ajout de transport (division en deux)
	private Jauge jaugeAssis;
	private Jauge jaugeDebout;

    //private Passager[] passagers;(remplacement)
    //est une liste parametree par le type Passager de classe, soit:
    private List<Passager> passagers;//ajouter le nom à la fin(important!!)

    /*(*)*/
    private ListIterator<Passager> it;
    
	// private int nbPassagers;

	private int arretCourant;

	public Autobus(int nbPlaceAssise, int nbPlaceDebout) {
	    //traitement sur les integers triviaux comme sur destination
	    //de passager_abstrait qui comporte apres tout
	    //tous les fichiers manquants~(heritage multiple)
	    //voir le script et l'executer pour s'apercevoir et se rappeler
	    //des liens.


	    /*
Suivant la modification de private Passager[] passagers en private Liste<Passager> passagers, on doit modifier des trucs ici notamment ceux là (avant le seul commentaire de début existant)
	    */
	    
	    if(nbPlaceAssise < 0 || nbPlaceDebout<0){
		throw new IllegalArgumentException("L'autobus n'est pas un ensemble vide !");
	    }
		jaugeAssis = new Jauge(nbPlaceAssise, 0);
		jaugeDebout = new Jauge(nbPlaceDebout, 0);
		arretCourant = 0;
		//passagers = new Passager[nbPlaceAssise + nbPlaceDebout];

		/*On va pas créer un tableau d'instances ### via
new Passager[nbPlaceAssise+nbPlaceDebout] mais via l'instanciation
d'une liste. C'est pratiquement la même chose après l'importation de ce
C_FRAMEWORK via java.util.*:
(Rq: List est une interface, ArrayList est une implémentation de cet interface
+le fait que ArrayList est une sorte de tableau avec taille pas fixe et d'autres
méthodes dessus interessantes notamment size get qu'on va utiliser après
pour les exceptions) ; pour parcourir on passe par ListIterator.(*)
             passagers=new ArrayList<>();
		*/
		    passagers=new ArrayList<>();
		    it=passagers.listIterator();
		    //public interface ListIterator
		    //extends Iterator(*)(*).
		    //et voilà.


	}

    //toutes les méthodes vont s'adpater à ceci maintenant.
    //a enlever ces 2 methodes(nemarchent plus des maintenant).

    //du facile vers le difficile.
    private void ajouterPassager(Passager p){
	passagers.add(p);
    }
    //la généricité fait une croix sur la validité des tests malgré le changement de la structure(principe de généricité).
    public void allerArretSuivant(){
	arretCourant++;
	it=passagers.listIterator();
	while(it.hasNext()){
	    Passager p=it.next();
	    p.nouvelArret(this,arretCourant);
	}
    }

    private void enleverPassager(Passager p){
	it=passagers.listIterator();
	ListIterator<Passager> it2=it;
	while(it2.hasNext()){
	    Passager q=it2.next();
	    if(q.equals(p)){
		it2.remove();//le p s'envole de it2
		//it et it2 sont des itérateurs de la même collection
		//on re-précise avec it=passagers.listIterator..
		it=passagers.listIterator();
	    }
	}
    }
	
    
    /*private void enleverPassager(Passager p) {
		int i = 0;
		while (passagers[i] == null || !passagers[i].equals(p)) {
			i++;
		}
		passagers[i] = null;
	}

	private void ajouterPassager(Passager p) {
		int i = 0;
		while (passagers[i] != null) {
			i++;
		}
		passagers[i] = p;
	}
    */

    /*public void allerArretSuivant() {
		arretCourant++;
		for (Passager p : passagers) {
			if (p != null)
				p.nouvelArret(this, arretCourant);
		}
		}*/
    //exception controlee.


    //méthodes inchangeables.
	public boolean aPlaceAssise() {
		return jaugeAssis.estVert();
	}

	public boolean aPlaceDebout() {
		return jaugeDebout.estVert();
	}

    //méthodes à exceptions non controlees.(Les 2 avant-dernieres)
	public void arretDemanderAssis(Passager p) {
		jaugeDebout.decrementer();
		jaugeAssis.incrementer();
		p.changerEnAssis();
	}

	public void arretDemanderDebout(Passager p) {
		jaugeAssis.decrementer();
		jaugeDebout.incrementer();
		p.changerEnDebout();
	}

	public void arretDemanderSortie(Passager p) {
		if (p.estAssis()) {
			jaugeAssis.decrementer();
		} else {
			jaugeDebout.decrementer();
		}
		p.changerEnDehors();
		this.enleverPassager(p);
	}
    //faite:celle-ci   .
	public void monteeDemanderAssis(Passager p) {
	    //Selon l'énoncé, on doit lever une exception si le passager s'y trouve déjà(l'usager(client)) dans l'autobus.
	    //parcours de la liste des passagers
	    //identifiee par private List<Passager> passagers
	    //puis par (dans le contructeur): passagers=new ArrayList<>();

	    int i=0;
	    while(i<passagers.size()){
		if(p==passagers.get(i)){//Le passager p en argument s'y trouve déjà
		    throw new IllegalStateException("L'usager est déjà dans le transport.");
		}
		i++;
	    }
	    
		jaugeAssis.incrementer();
		p.changerEnAssis();
		this.ajouterPassager(p);
	}

    public void monteeDemanderDebout(Passager p) {//faite.
	    int i=0;
	    while(i<passagers.size()){
		if(p==passagers.get(i)){
		    throw new IllegalStateException("L'usager est déjà dans le transport.");
		}
		i++;
	    }
		jaugeDebout.incrementer();
		p.changerEnDebout();
		this.ajouterPassager(p);
	}

	@Override
	public String toString() {
		String res = "[arret " + arretCourant + "] " + "assis" + jaugeAssis + " debout" + jaugeDebout;
		return res;
	}
}
