package market.GoogleFinance;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;

public class Util {
	
	public static String getGoogleDate(LocalDate localDate) {
		String date = "";
		// Month
		switch (localDate.getMonth().getValue()) {
			case 1: date = date + "Jan"; break;
			case 2: date = date + "Jeb"; break;
			case 3: date = date + "Mar"; break;
			case 4: date = date + "Apr"; break;
			case 5: date = date + "May"; break;
			case 6: date = date + "Jun"; break;
			case 7: date = date + "Jul"; break;
			case 8: date = date + "Aug"; break;
			case 9: date = date + "Sep"; break;
			case 10: date = date + "Oct"; break;
			case 11: date = date + "Nov"; break;
			case 12: date = date + "Dec"; break;
		}
		
		// Seperator
		date = date + " ";
		
		// Day
		date = date + localDate.getDayOfMonth();
		
		// Seperator
		date = date + ", ";
		
		// Year
		date = date + localDate.getYear();
		
		// return
		return date;
	}
	
	public static LocalDate getLocalDate(String googleDate) {
		
		googleDate = googleDate.replace(',', ' ');
		String[] googleDateToken = googleDate.split(" ");
		
		// System.out.println("googleDateToken[0] = " + googleDateToken[0]);
		// System.out.println("googleDateToken[1] = " + googleDateToken[1]);
		// System.out.println("googleDateToken[2] = " + googleDateToken[2]);
		// System.out.println("googleDateToken[3] = " + googleDateToken[3]);
		
		// Month
		int month = 0;
		switch (googleDateToken[0]) {
			case "Jan" : month = 1; break;
			case "Feb" : month = 2; break;
			case "Mar" : month = 3; break;
			case "Apr" : month = 4; break;
			case "May" : month = 5; break;
			case "Jun" : month = 6; break;
			case "Jul" : month = 7; break;
			case "Aug" : month = 8; break;
			case "Sep" : month = 9; break;
			case "Oct" : month = 10; break;
			case "Nov" : month = 11; break;
			case "Dec" : month = 12; break;
			default:
				// TODO raise exception
		}
		
		// Day
		int day = Integer.parseInt(googleDateToken[1]);
		
		// Year
		int year = Integer.parseInt(googleDateToken[3]);
		
		// System.out.println("day   = " + day);
		// System.out.println("month = " + month);
		// System.out.println("year  = " + year);
		
		return LocalDate.of(year, month, day);
	}
	
	public static String getGoogleURL(String share, LocalDate startDate, LocalDate endDate, int maxNumberOfShareToRecover, int startPosition) throws UnsupportedEncodingException {

		// share		
    	String shareEncoded = URLEncoder.encode(share, "UTF-8");
    	
    	// startdate
    	String startDateEncoded = URLEncoder.encode(getGoogleDate(startDate), "UTF-8");
    	
    	// enddate
    	String endDateEncoded = URLEncoder.encode(getGoogleDate(endDate), "UTF-8");
    	
    	// number
    	String numberEncoded =  URLEncoder.encode("" + maxNumberOfShareToRecover, "UTF-8");
    	
    	// start position
    	String startPositionEncoded =  URLEncoder.encode("" + startPosition, "UTF-8");
    	
    	return  "https://www.google.com/finance/historical" + 
    			"?q="         + shareEncoded     +
    			"&startdate=" + startDateEncoded + 
    			"&enddate="   + endDateEncoded   + 
    			"&num="       + numberEncoded    + 
    			"&start="     + startPositionEncoded;
	}
	
	public static void main(String[] args) {
		
		String googleShareName        = "EBR:BREBT";
		LocalDate startDate           = LocalDate.of(2015, 5, 25);
		LocalDate endDate             = LocalDate.of(2015, 5, 29);
		int maxNumberOfShareToRecover = 100;
		int startPosition             = 0;
		
		try {
			String GoogleURL = getGoogleURL(googleShareName, startDate, endDate, maxNumberOfShareToRecover, startPosition);
			System.out.println(GoogleURL);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
