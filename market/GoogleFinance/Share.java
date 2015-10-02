package market.GoogleFinance;

public class Share {

	String       marketName = "";
	String       shareName  = "";
	SharePrice[] historic;
	
	
	public Share(String marketName, String shareName) {
		this.marketName = marketName;
		this.shareName  = shareName;
	}
	
	public String getMarketName() {
		return this.marketName;
	}
	
	public String getShareName() {
		return this.shareName;
	}

	public void SetPrices(SharePrice[] sharePrices) {
		this.historic = new SharePrice[sharePrices.length];
		this.historic = sharePrices.clone();
	}	
}
