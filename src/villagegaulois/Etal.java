package villagegaulois;

import personnages.Gaulois;

public class Etal {
	private Gaulois vendeur;
	private String produit;
	private int quantiteDebutMarche;
	private int quantite;
	private boolean etalOccupe = false;

	public boolean isEtalOccupe() {
		return etalOccupe;
	}

	public Gaulois getVendeur() {
		return vendeur;
	}

	public void occuperEtal(Gaulois vendeur, String produit, int quantite) {
		this.vendeur = vendeur;
		this.produit = produit;
		this.quantite = quantite;
		quantiteDebutMarche = quantite;
		etalOccupe = true;
	}

	public String libererEtal() {
		etalOccupe = false;
		StringBuilder chaine = new StringBuilder();
		try {
			chaine.append("Le vendeur " + vendeur.getNom() + " quitte son Ã©tal, ");
			int produitVendu = quantiteDebutMarche - quantite;
			if (produitVendu > 0) {
				chaine.append("il a vendu " + produitVendu + " " + produit + " parmi  les "+ quantiteDebutMarche + " qu'il voulait vendre.\n");
			} else {
				chaine.append("il n'a malheureusement rien vendu.\n");
			}
		}catch(NullPointerException e){
			System.out.println("L'étal est déjà  vide.");
		}

		return chaine.toString();
	}

	public String afficherEtal() {
		if (etalOccupe) {
			return "L'Etal de " + vendeur.getNom() + " est garni de " + quantite
					+ " " + produit + "\n";
		}
		return "L'Ã©tal est libre";
	}

	public String acheterProduit(int quantiteAcheter, Gaulois acheteur) {
	    if (quantiteAcheter < 1) {
	        throw new IllegalArgumentException("La quantitÃ© achetÃ©e doit Ãªtre d'au moins 1.");
	    }
	    if (!etalOccupe) {
	        throw new IllegalStateException("L'étal est vide.");
	    }
		StringBuilder chaine = new StringBuilder();
		try {
			chaine.append(acheteur.getNom() + " veut acheter " + quantiteAcheter+ " " + produit + " Ã  " + vendeur.getNom());
			if (quantite == 0) {
				chaine.append(", malheureusement il n'y en a plus !");
				quantiteAcheter = 0;
			}
			else if (quantiteAcheter > quantite) {
				chaine.append(", comme il n'y en a plus que " + quantite + ", "+ acheteur.getNom() + " vide l'Ã©tal de "+ vendeur.getNom() + ".\n");
				quantiteAcheter = quantite;
				quantite = 0;
			}
			else if (quantite != 0) {
				quantite -= quantiteAcheter;
				chaine.append(". " + acheteur.getNom()+ ", est ravi de tout trouver sur l'Ã©tal de "+ vendeur.getNom() + "\n");
			}
			return chaine.toString();
		}catch(Error e) {
			e.printStackTrace(System.err);
			return "";
		}
	}

	public boolean contientProduit(String produit) {
		return produit.equals(this.produit);
	}

}


/*ligne 78 je dois préciser le nom de l'erreur sinon problemes*/