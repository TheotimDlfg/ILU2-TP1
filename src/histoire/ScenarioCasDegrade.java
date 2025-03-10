package histoire;

import villagegaulois.Etal;


public class ScenarioCasDegrade {
	public static void main(String[] args) {
		Etal etal = new Etal();
        try { 
        	etal.libererEtal();
            etal.acheterProduit(-1, null);
		}catch(IllegalArgumentException e) {
			System.err.println("Exception capturée dans Scenario : " + e.getMessage());
		}catch(IllegalStateException e) {
			System.err.println(e.getMessage());
		}
		System.out.println("Fin du test");
	}
} 

