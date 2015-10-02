package market.Configuration;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

public class Configuration {

	private static int      NumberOfShares;
	private static String[] MarketNameList;
	private static String[] ShareNameList;
	
	public static void loadConfiguration() {

		SAXBuilder saxBuilder = new SAXBuilder(); 
		Document   document   = null;
	    try {
	        document = saxBuilder.build(new File("src/configuration.xml"));
	    }
	    catch (Exception e) {
	    	// TODO
	    }
    	
	    Element racine = document.getRootElement();

		List<Element> SharesList = racine.getChildren("Share");

		Iterator<Element> i = SharesList.iterator();
		
		NumberOfShares = 0;
		MarketNameList = new String[SharesList.size()];
		ShareNameList  = new String[SharesList.size()];
		
		while (i.hasNext())
		{
			NumberOfShares = NumberOfShares + 1;
			Element courant = (Element) i.next();
			
			// System.out.println(courant.getChild("MarketName").getText() + '\t' + courant.getChild("ShareName").getText());
			
			MarketNameList[SharesList.indexOf(courant)] = courant.getChild("MarketName").getText();
			ShareNameList[SharesList.indexOf(courant)] = courant.getChild("ShareName").getText();
		}
	}
	
	public static int getNumberOfShares() {
		return NumberOfShares;
	}
	
	public static String[] getMarketNames() {
		return MarketNameList;
	}
	
	public static String[] getShareNames() {
		return ShareNameList;
	}
	
	public static void main(String[] args) {
		loadConfiguration();
	}
	
}
