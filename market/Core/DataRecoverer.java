package market.Core;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import market.GoogleFinance.GoogleFinanceConnector;
import market.GoogleFinance.Share;
import market.Configuration.Configuration;

public class DataRecoverer {
	
	public static Set<Share> recoverer() {
		
		// Charger la configuration
		Configuration.loadConfiguration();
		int      numberOfShares = Configuration.getNumberOfShares();
		String[] marketNames    = Configuration.getMarketNames();
		String[] shareNames     = Configuration.getShareNames();
		
		// Création d'une map
		HashMap<Share, GoogleFinanceConnector> dataMap = new HashMap<Share, GoogleFinanceConnector>();
		for (int i = 0 ; i < numberOfShares ; i++) {
			Share share = new Share(marketNames[i], shareNames[i]);

			String googleShareName        = marketNames[i] + ":" + shareNames[i];
			LocalDate startDate           = LocalDate.of(2015, 5, 25);
			LocalDate endDate             = LocalDate.of(2015, 5, 29);
			int maxNumberOfShareToRecover = 100;
			int startPosition             = 0;
			
			GoogleFinanceConnector gFConnector;
			try {
				gFConnector = new GoogleFinanceConnector(googleShareName, startDate, endDate, maxNumberOfShareToRecover, startPosition);

				dataMap.put(share, gFConnector);
				
				// Démarrage du processus
				gFConnector.start();
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				// TODO: 
				// Que se passe-t-il si un gFConnector n'a pas pu être créé ?
				// On aura un pointeur null
			}
		}
		
		// Attendre que tous les threads se terminent
		// Affichage d'une barre de progression
		int numberOfSharesRemaining = numberOfShares;
		int numberOfSharesRemaining_prev = 0;
		while (true) {
			
			// Remettre au maximum
			numberOfSharesRemaining = numberOfShares;
			
			Iterator<Share> keySetIterator = dataMap.keySet().iterator();
			while (keySetIterator.hasNext()) {
				Share share = keySetIterator.next();
				GoogleFinanceConnector gFConnector = dataMap.get(share);
				
				if (gFConnector.getState() == Thread.State.TERMINATED) {
					numberOfSharesRemaining = numberOfSharesRemaining - 1; 
				}
			}
			
			// Progression bar
			if (numberOfSharesRemaining_prev != numberOfSharesRemaining) {
				System.out.print("|");
				for (int i = 0 ; i < numberOfShares ; i++) {
					if (i < numberOfShares - numberOfSharesRemaining) {
						System.out.print("=");
					}
					else {
						System.out.print(" ");
					}
				}
				System.out.println("| (" + Double.toString((double) 100 * (numberOfShares - numberOfSharesRemaining) / numberOfShares) + "%)");
			}
			numberOfSharesRemaining_prev = numberOfSharesRemaining;
			
			// Exit infinite loop
			if (numberOfSharesRemaining == 0) {
				System.out.println("Data recovered");
				break;
			}
		}
		
		
		// Sauvegarder les données récupérées de Google dans les objets
		Iterator<Share> keySetIterator = dataMap.keySet().iterator();
		while (keySetIterator.hasNext()) {
			Share share = keySetIterator.next();
			GoogleFinanceConnector gFConnector = dataMap.get(share);
			
			// TODO : Vérifier si le pointeur est null.
			// Il a pu être null à cause du try catch plus haut.
			share.SetPrices(gFConnector.getSharePrices());
		}
		
		return dataMap.keySet();
		
	}

	public static void main(String[] args) {
		
	}
}
