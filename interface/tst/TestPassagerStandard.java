package tec;

class TestPassagerStandard extends TestPassagerAbstrait {

  public static void main(String[] args) {
    boolean estMisAssertion = false;
    assert estMisAssertion = true;

    if (!estMisAssertion) {
      System.out.println("Execution impossible sans l'option -ea");
      return;
    }

    int nbTest = 0;

    // ************ Verifier l'instanciation *************
    System.out.print('.');
    nbTest++;
    new TestPassagerStandard().testInstanciation();

    // ********* Verifier changement d'etat **************
    System.out.print('.');
    nbTest++;
    new TestPassagerStandard().testGestionEtat();

    // ********* Verifier les interactions *************
    System.out.print('.');
    nbTest++;
    new TestPassagerStandard().testInteractionMontee();

    System.out.print('.');
    nbTest++;
    new TestPassagerStandard().testInteractionArret();

    System.out.println("(" + nbTest + "):OK: " + "tec.TestPassagerStandard");
  }

  protected PassagerAbstrait creerPassager(String nom, int destination) {
    return new PassagerStandard(nom, destination);
  }

  /*
   * Interaction a la montee
   * Trois cas
   * - des places assises et debout
   * - pas de place assise
   * - aucune place.
   */
  public void testInteractionMontee() {
    PassagerStandard p = new PassagerStandard("yyy", 5);

    FauxVehicule faux2 = new FauxVehicule(FauxVehicule.VIDE);
    Transport faux=(Transport)faux2;
    p.monterDans(faux);

    assert "monteeDemanderAssis" == getLastLog(faux2) : "assis";

    faux2 = new FauxVehicule(FauxVehicule.DEBOUT);
    faux=(Transport)faux2;
    p.monterDans(faux);

    assert "monteeDemanderDebout" == getLastLog(faux2) : "debout";

    faux2 = new FauxVehicule(FauxVehicule.PLEIN);
    faux=(Transport)faux2;
    p.monterDans(faux);

    assert 0 == faux2.logs.size() : "pas de place";
  }

  /*
   * Interaction a un arret
   * Deux cas
   * - numero d'arret < a la destination
   * - numero d'arret >= a la destination
   */
  public void testInteractionArret() {
    PassagerStandard p = new PassagerStandard("yyy", 5);

    FauxVehicule faux = new FauxVehicule(FauxVehicule.VIDE);

    p.nouvelArret(faux, 1);
    assert 0 == faux.logs.size() : "pas a destination";

    p.nouvelArret(faux, 5);
    assert "arretDemanderSortie" == getLastLog(faux) : "destination";
  }
}
