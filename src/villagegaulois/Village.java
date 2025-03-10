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
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		private int trouverEtalLibre() {
			int indice = -1;
			for (int i = 0; i < etals.length; i++) {
				Etal etal = etals[i];
				if(!etal.isEtalOccupe()){
					indice = i;
					i = etals.length;
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
				System.out.println("Il reste " + nbEtalVide + " étals non utilis�s dans le marché.\n");
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

	public String afficherVillageois() throws VillageSansChefException {
		if(chef == null) {
			throw new VillageSansChefException("Il n'y a pas de chef a ce village.\n");
		}
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		int numEtal = marche.trouverEtalLibre();
		StringBuilder text = new StringBuilder().append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n");
		if (numEtal == -1) {
			text.append("Aucun étal libre.\n");
			return text.toString();
		}
		marche.utiliserEtal(numEtal, vendeur, produit, nbProduit);
		text.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " à l'étal n°" + (numEtal + 1) + ".\n");
		return text.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		Etal[] etals = marche.trouverEtals(produit);
		switch (etals.length) {
		case 0:
			return "Il n'y a pas de vendeur qui propose des " + produit + " au marché.\n";
		case 1:
			return "Seul le vendeur " + etals[0].getVendeur().getNom() + " propose des " + produit + " au marché.\n";
		default:
			StringBuilder nameList = new StringBuilder()
					.append("Les vendeur qui proposent des " + produit + " sont :\n");
			for (Etal etal : etals) {
				nameList.append("- " + etal.getVendeur().getNom() + "\n");
			}
			return nameList.toString();
		}
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		Etal etal = new Etal();
		for(int i = 0; i < marche.etals.length; i++) {
			etal = marche.etals[i];
			if(vendeur.equals(etal.getVendeur())) {
				i = marche.etals.length;
			}
		}
		return etal;
	}

	public String partirVendeur(Gaulois vendeur) {
		try {
			return rechercherEtal(vendeur).libererEtal();
		}catch(NullPointerException e) {
			StringBuilder text = new StringBuilder().append("Le vendeur " + vendeur.getNom() +" ne vend rien.\n");
			return text.toString(); 
		}
		
	}

	public String afficherMarche() {
		StringBuilder text = new StringBuilder()
				.append("Le marché du village " + this.getNom() + " possède plusieurs étals :\n");
		int etalTotal = marche.etals.length;
		int etalUtil = 0;
		for (Etal etal : marche.etals) {
			if (etal.isEtalOccupe()) {
				etalUtil++;
				text.append(etal.afficherEtal());
			}
		}
		text.append("Il reste " + (etalTotal - etalUtil) + " non utilisés dans le marché");
		return text.toString();
	}

	
}