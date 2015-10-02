package market.GoogleFinance;

import java.time.LocalDate;

public class SharePrice {

	private LocalDate date;
	private double    closePrice;
	private double    volume;
	
	public SharePrice(LocalDate date, double closePrice, double volume) {
		this.date       = date;
		this.closePrice = closePrice;
		this.volume     = volume;
	}
	
	public LocalDate getDate() {
		return this.date;
	}
	
	public double getClosePrice() {
		return this.closePrice;
	}
	
	public double getVolume() {
		return this.volume;
	}
}
