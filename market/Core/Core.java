package market.Core;

import java.util.Iterator;
import java.util.Set;

import market.GoogleFinance.Share;

public class Core {

	public static void run() {
		
		// Step 1 : Recover data
		Set<Share> shares = DataRecoverer.recoverer();
		
		// Step 1.1 : Print
		Iterator<Share> iter = shares.iterator();
		while (iter.hasNext()) {
			String tempMarketName = iter.next().getMarketName();
			String tempShareName  = iter.next().getShareName();
			System.out.println(tempMarketName + ":" + tempShareName);
		}
		
		// Step 2 : Analyze data
		DataAnalyzer.analyzeData(shares);			
	}
	
	public static void main(String[] args) {
		run();
	}
}
