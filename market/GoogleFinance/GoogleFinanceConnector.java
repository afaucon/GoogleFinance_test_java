package market.GoogleFinance;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableBody;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlOption;

public class GoogleFinanceConnector extends Thread {
	
	// Internal data
	private String googleURL;	
	private SharePrice[] sharePrices;
	
	// Constructeur
	public GoogleFinanceConnector(String googleShareName, LocalDate startDate, LocalDate endDate, int maxNumberOfShareToRecover, int startPosition) throws UnsupportedEncodingException {
		this.googleURL = market.GoogleFinance.Util.getGoogleURL(googleShareName, startDate, endDate, maxNumberOfShareToRecover, startPosition);
	}	
	
	public void run() {
		final WebClient webClient = new WebClient(BrowserVersion.CHROME);
	    
    	// turn off htmlunit warnings
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);
        
        // Charger la page
		try {
			HtmlPage page;
			page = webClient.getPage(this.googleURL);
			getShareData(page);
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public SharePrice[] getSharePrices() {
		return this.sharePrices;
	}
	
	
	
	
	
	private void getShareData(HtmlPage page) {
		
		List<?> tbodyList = page.getByXPath("//*[@id='prices']/table/tbody");
		if (tbodyList.size() != 0) {
			
			// Récupérer la table qui contient les données intéressantes
			HtmlTableBody tableBody = (HtmlTableBody) page.getByXPath("//*[@id='prices']/table/tbody").get(0);
	    	
	    	// Vérifier le nombre de ligne de la table récupérée
	    	// Il doit y avoir au minimum 2 lignes :
	    	//     1) Celle tout en haut
	    	//     2) Celle tout en bas
	    	if (tableBody.getRows().size() < 2) {
	    		// raise program exception
	    	}
	    	
	    	// Traitement de la toute première ligne pour récupérer l'index des 3 cellules interessantes
	    	//     1) la colonne de la date
	    	//     2) la colonne de la valeur de l'action à la fermeture
	    	//     3) la colonne du volume échangé
	    	HtmlTableRow firstRow = tableBody.getRows().get(0);
	    	int index_of_the_column_for_date   = -1;
	    	int index_of_the_column_for_close  = -1;
	    	int index_of_the_column_for_volume = -1;
	    	for (int i = 0 ; i < firstRow.getCells().size() ; i++) {
	    		
	    		if (firstRow.getCell(i).getTextContent().contains("Date")) {
	    			// Recovering the column index of the date
	    			index_of_the_column_for_date = i;
	    		}
	    		else if (firstRow.getCell(i).getTextContent().contains("Close")) {
	    			// Recovering the column index of the close
	    			index_of_the_column_for_close = i;
	    		}
	    		else if (firstRow.getCell(i).getTextContent().contains("Volume")) {
	    			// Recovering the column index of the volume
	    			index_of_the_column_for_volume = i;
	    		}
	    		else {
	    			// Nothing to do
	    		}
	    	}
	    	
	    	// Vérifier que les 3 colonnes interesstantes ont été trouvées
	    	if (index_of_the_column_for_date   == -1 ||
	            index_of_the_column_for_close  == -1 ||
	            index_of_the_column_for_volume == -1 ) 
	    	{
	    		// raise program exception
	    	}
	    	
	    	// Récupérer les données intéressantes
	    	// Si tableBody.getRows().size() vaut 100
	    	// ligne 0  :   1st ligne : pas intéressant
	    	// ligne 1  :   2nd ligne :  1st valeur intéressante |
	    	// ligne .. : ..... ligne : .... valeur intéressante | => 98 valeurs intéressantes d'index [1 ; 99[
	    	// ligne 98 :  99th ligne : 98th valeur intéressante |
	    	// ligne 99 : 100th ligne : pas intéressant
	    	
	    	int numberOfResults = tableBody.getRows().size() - 2;
	    	
	    	this.sharePrices = new SharePrice[numberOfResults];
	    	
			for (int i = 0 ; i < numberOfResults ; i++) {
				HtmlTableRow row = tableBody.getRows().get(i+1);
				
				String sDate   = row.getCell(index_of_the_column_for_date).getTextContent();
				String sClose  = row.getCell(index_of_the_column_for_close).getTextContent();
				String sVolume = row.getCell(index_of_the_column_for_volume).getTextContent();

				// System.out.println(sDate + '\t' + sClose + '\t' + sVolume);
				
				// Post processing on recovered date string
				// 1: Removing the last character if it is carriage return
				if (sDate.charAt(sDate.length()-1) == '\n') {
					sDate = sDate.substring(0, sDate.length() - 1);
				}
				
				// Post processing on recovered close string
				// 1: Removing the last character if it is carriage return
				if (sClose.charAt(sClose.length()-1) == '\n') {
					sClose = sClose.substring(0, sClose.length() - 1);
				}
				
				// Post processing on recovered volument string
				// 1: Removing the last character if it is carriage return
				// 2: Replacing "," by ""
				if (sVolume.charAt(sVolume.length()-1) == '\n') {
					sVolume = sVolume.substring(0, sVolume.length() - 1);
				}
				sVolume = sVolume.replace(",", "");

				// Finally, fill the sharePrices table
		    	LocalDate date   = market.GoogleFinance.Util.getLocalDate(sDate);
		    	double    close  = Double.parseDouble(sClose);
		    	double    volume = Double.parseDouble(sVolume);
				
				this.sharePrices[i] = new SharePrice(date, close, volume);
			}
		}
		else {
			// Nothing to recover from the URL
			this.sharePrices = new SharePrice[0];
		}
	}
}
