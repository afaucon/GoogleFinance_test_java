package htmlunit_test;

import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.CollectingAlertHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableBody;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;

public class toto {
	public static void main(String[] args) throws Exception {
		
		final List<String> collectedAlerts = new ArrayList<String>();
		
		Boolean bDebug = true;
		
		final WebClient webClient = new WebClient(BrowserVersion.CHROME);
		webClient.setAlertHandler(new CollectingAlertHandler(collectedAlerts));
	    try {
	    	
	    	// turn off htmlunit warnings
	        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
	        java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);
	    	
	        // Charger la page
	    	HtmlPage page = (HtmlPage) webClient.getPage("https://www.google.com/finance/stockscreener");
	    	
	    	// Récupérer la table
//          HtmlTable table = page.getHtmlElementById("criteria_rows");
//	    	for (final HtmlTableRow row : table.getRows()) {
//	    		for (final HtmlTableCell cell : row.getCells()) {	    			
//	    			System.out.print(cell.asText() + "\t");
//	    		}
//	    		System.out.println();
//	    	}
	    		    	
//	    	// Récupérer les images qui permettent de suprimer les filtres dans une liste
//	    	ArrayList<HtmlImage> imgList = new ArrayList<HtmlImage>();
//	    	table = page.getHtmlElementById("criteria_rows");
//	    	for (final HtmlTableRow row : table.getRows()) {
//	    		for (final HtmlTableCell cell : row.getCells()) {	    			
//	    			if (cell.getHtmlElementsByTagName("img").size() == 1) {
//	    				imgList.add((HtmlImage) cell.getHtmlElementsByTagName("img").get(0));	    				
//	    			}
//	    		}
//	    	}
//	    	
//	    	// Parcourir la liste des images et cliquer sur chaque image pour suprimer le filtre
//	    	for (HtmlImage img : imgList) {
//	    		page = (HtmlPage) img.click();
//	    	}
//	    	
//	    	// Ajouter les critères intéressants comme filtres
//	    	ArrayList<HtmlAnchor> anchorList = new ArrayList<HtmlAnchor>();
//	    	HtmlTableCell tableCell = (HtmlTableCell) page.getByXPath("//*[@id='add_criteria_wizard']/table/tbody/tr/td[2]/table/tbody/tr/td").get(0);
//	    	ArrayList<HtmlElement> criteriaList = (ArrayList<HtmlElement>) tableCell.getHtmlElementsByTagName("div");
//	    	for (HtmlElement criteria : criteriaList) {
//	    		if (criteria.getAttribute("class").contains("criteria_list_div")) {
//	    			String criteriaName = criteria.getId();
//	    			
//	    			if (criteriaName.contentEquals("price")) {
//		    			ArrayList<HtmlElement> subCriteriaL = (ArrayList<HtmlElement>) criteria.getHtmlElementsByTagName("div");
//			    		for (HtmlElement subCriteria : subCriteriaL) {
//			    			HtmlAnchor anchor = (HtmlAnchor) subCriteria.getHtmlElementsByTagName("a").get(0);
//			    			String anchorTextContent = anchor.getTextContent(); 
//			    			if (anchorTextContent.contains("price change") || anchorTextContent.contains("Last price")) {
//			    				anchorList.add(anchor);
//			    			}
//			    		}	
//	    			}
//	    			
//	    			if (criteriaName.contentEquals("valuation")) {
//		    			ArrayList<HtmlElement> subCriteriaL = (ArrayList<HtmlElement>) criteria.getHtmlElementsByTagName("div");
//	    				for (HtmlElement subCriteria : subCriteriaL) {
//			    			HtmlAnchor anchor = (HtmlAnchor) subCriteria.getHtmlElementsByTagName("a").get(0);
//			    			String anchorTextContent = anchor.getTextContent(); 
//			    			if (anchorTextContent.contains("market cap")) {
//			    				anchorList.add(anchor);
//			    			}
//			    		}	
//	    			}
//	    		}
//	    	}
//	    	
//	    	// Parcourir la liste des anchor, cliquer dessus, puis cliquer sur le bouton "Add criteria"
//	    	for (HtmlAnchor anchor : anchorList) {
//	    		anchor.click();
//	    		
//	    		// HtmlElement selectedFilter = (HtmlElement) page.getByXPath("//*[@id='criteria_definition']").get(0);
//	    		// System.out.println(selectedFilter.getTextContent());
//	    		
//	    		HtmlButton button = (HtmlButton) page.getByXPath("//*[@id='criteria_button']/button").get(0);
//	    		page = button.click();
//	    	}
//	    	
//	    	// Mettre les bons {Min ; max} dans les critères
//	    	HtmlTableBody tableBody = (HtmlTableBody) page.getByXPath("//*[@id='criteria_rows']/tbody").get(0);
//	    	for (HtmlTableRow tableRow : tableBody.getRows()) {
//	    		if (tableRow.getId().startsWith("row_")) {
//	    			String ID = tableRow.getId().substring(4);
//	    			// System.out.println(ID);
//	    			
//	    			String xpath_start = "//*[@id='";
//	    			String xpath_end   = "']";
//	    			
//	    			HtmlInput input_left  = (HtmlInput) page.getByXPath(xpath_start + ID + "_left"  + xpath_end).get(0);
//	    			HtmlInput input_right = (HtmlInput) page.getByXPath(xpath_start + ID + "_right" + xpath_end).get(0);
//	    			
//	    			if (ID.contentEquals("QuoteLast")) {
//		    			input_left.setAttribute("value", "30");    // 10
//	    				input_right.setAttribute("value", "40");   // 60
//	    			}
//	    			else if (ID.contentEquals("Price13WeekPercChange")) {
//		    			input_left.setAttribute("value", "0");
//	    				input_right.setAttribute("value", "300");
//	    			}
//	    			else if (ID.contentEquals("Price26WeekPercChange")) {
//		    			input_left.setAttribute("value", "0");
//	    				input_right.setAttribute("value", "300");
//	    			}
//	    			else if (ID.contentEquals("Price52WeekPercChange")) {
//		    			input_left.setAttribute("value", "0");
//	    				input_right.setAttribute("value", "300");
//	    			}
//	    			else if (ID.contentEquals("marketCap")) {
//		    			input_left.setAttribute("value", "1.00M");
//	    				input_right.setAttribute("value", "1000.00B");
//	    			}
//	    			
//	    			// System.out.println("   left: " + input_left.getValueAttribute());
//	    			// System.out.println("  right: " + input_right.getValueAttribute());
//	    		}
//	    	}
	    	
	    	// Choisir le marché belge
	    	
	    	HtmlForm form = (HtmlForm) page.getByXPath("//*[@id='criteria']/form").get(0);
	    	form.click();
	    	HtmlSelect select = form.getSelectByName("regionselect");

	    	System.out.println(select.getSelectedIndex());
	    	
			for (HtmlOption option : select.getOptions()) {
				if (bDebug == true) {
					System.out.println(option.getText());
				}
			}
	    	
	    	HtmlOption optionBelgium = select.getOptionByText("Belgium");
	    	
	    	HtmlOption optionUS      = select.getOptionByText("United States");

	    	
	    	System.out.println(optionUS.asText());
	    	System.out.println(optionBelgium.asText());

	    	optionUS.setSelected(false);
	    	optionBelgium.setSelected(true);
	    	
			//page = select.setSelectedAttribute(optionBelgium, true);
	    	
	    	
	    	System.out.println(select.getSelectedIndex());
			System.out.println("Adrien");


	    	
	    	
	    	
	    	
			
			
//	    	HtmlSelect select = (HtmlSelect) page.getByXPath("//*[@id='criteria']/form/table[1]/tbody/tr/td[1]/select").get(0);
//
//			System.out.println(select.getSelectedIndex());
//			
//			select.setSelectedIndex(select.getSelectedIndex());
//			
//			System.out.println(select.getSelectedIndex());
//			
//			HtmlOption optionBelgium = select.getOptionByText("Belgium");
			//page = select.setSelectedAttribute(optionBelgium, true);
			
//			for (HtmlOption option : select.getOptions()) {
//    			if (bDebug == true) {
//	    			System.out.println(option.getText());
//	    		}
//    			
//    			if (option.getText().contentEquals("Belgium")) {
//    				page = select.setSelectedAttribute(option, false);
//    			}
//	    	}
	    	
			System.out.println(select.getSelectedIndex());
			
			
			
			
			
			
	    	
	    	
	    	
	    	
	    	// Récupérer les résultats
//	    	tableBody = (HtmlTableBody) page.getByXPath("//*[@id='gf-viewc']/div/div[2]/div[2]/div[3]/table/tbody").get(0);
//	    	for (HtmlTableRow tableRow : tableBody.getRows()) {
//	    		System.out.println(tableBody.getRows().size());
//	    	}
	    	
			

	    }
	    finally {
	        for (final String alert : collectedAlerts) {
	            System.out.println("ALERT: " + alert);
	        }
	    }
	}
}
