package constants;

public class MySqlStatement {
	private static final String country_region = "country_region";
	
	public static String getRegionOfCountryCode = "SELECT * FROM " + country_region + " WHERE countrycode = '";
}
