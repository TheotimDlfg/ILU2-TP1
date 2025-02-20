package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	private static class Marche {
		private Etal[] etals;
		
		public Marche(int nbEtals) {
			this.etals = new Etal[nbEtals];
			for(int i = 0; i < nbEtals; i++) {
				this.etals[i] = new Etal();
			}
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			// to do si l'étal est deja occupé
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		private int trouverEtalLibre() {
			int indice = -1;
			for (int i = 0; i < etals.length; i++) {
				Etal etal = etals[i];
				if(!etal.isEtalOccupe()){
					indice = i;
				}
			}
			return indice;
		}
		
		private Etal[] trouverEtals(String produit) {
			int nbEtal = 0;
			for (int i = 0; i < etals.length; i++) {
				if(etals[i].contientProduit(produit)) {
					nbEtal++;
				}
			}
			Etal[] etalResult = new Etal[nbEtal];
			for (int i = 0, j = 0; i < etals.length; i++) {
				if(etals[i].contientProduit(produit)) {
					etalResult[j] = etals[i];
					j++;
				}
			}
			return etalResult;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			Etal result = null;
			for (int i = 0; i < etals.length; i++) {
				if(etals[i].getVendeur() == gaulois) {
					// to do si ya plusieurs fois le même gaulois
					result = etals[i];
				}
			}
			return result;
		}
		
		public void afficherMarche() {
			int nbEtalUti = 0;
			for(int i = 0; i < etals.length; i++) {
				if(etals[i].isEtalOccupe()) {
					nbEtalUti++;
					etals[i].afficherEtal();
				}
			}
			int nbEtalVide = etals.length - nbEtalUti;
			if(nbEtalVide > 0) {
				System.out.println("Il reste " + nbEtalVide + " étals non utilisés dans le marché.\n");
			}
		}
		
	}
	
	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit,int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur + " cherche un endroit pour vendre "+ nbProduit + produit +".Le vendeur "+ vendeur +" vend des "+ produit);
		int iEtal = marche.trouverEtalLibre();
		if(iEtal < 0) {
			// to do si aucune etal libre
		}
		chaine.append(" à l'étal n°" + iEtal +".");
		return chaine.toString(); 
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		for(int i = 0; i < ; i++) {
			if(marche.trouverEtals(produit) ) {
				chaine.append("Il n'y a pas de vendeur qui propose des "+produit+" au marché.")
			}
		}
		return chaine.toString();
	}
	
}