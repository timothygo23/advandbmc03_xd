package constants;

public class MySqlStatement {
	private static final String country_region = "country_region";
	
	public static String getRegionOfCountryCode = "SELECT * FROM " + country_region + " WHERE countrycode = '";
	
	public static String getUnemploymentFrequencyMale = "SELECT C.CountryCode, C.CountryName, CI.Income, SUM(DY.Data) FROM country C, country_income CI, data_by_year DY WHERE C.CountryCode = CI.CountryCode && C.CountryCode = DY.CountryCode && DY.SeriesCode = \"SL.UEM.TERT.MA.ZS\" GROUP BY DY.CountryCode";
	
	public static String getUnemploymentFrequencyFemale = "SELECT C.CountryCode, C.CountryName, CI.Income, SUM(DY.Data) FROM country C, country_income CI, data_by_year DY WHERE C.CountryCode = CI.CountryCode && C.CountryCode = DY.CountryCode && DY.SeriesCode = \"SL.UEM.TERT.FE.ZS\" GROUP BY DY.CountryCode";
	
	public static String insertCountryUnemploymentZimbabweMale = "INSERT INTO data_by_year (CountryCode, SeriesCode, YearC, Data)VALUES (\"ZWE\", \"SL.UEM.TERT.MA.ZS\", \"2012\", \"12345\");";
	
	public static String insertCountryUnemploymentZimbabweFemale = "INSERT INTO data_by_year (CountryCode, SeriesCode, YearC, Data)VALUES (\"ZWE\", \"SL.UEM.TERT.FE.ZS\", \"2012\", \"12345\");";
	
	public static String updateUnemploymentFreqPHL2012Male = "UPDATE data_by_year SET Data = 12345 WHERE CountryCode = \"PHL\" && YearC = \"2012\" && SeriesCode = \"SL.UEM.TERT.MA.ZS\"";
	
	public static String updateUnemploymentFreqPHL2012Female = "UPDATE data_by_year SET Data = 12345 WHERE CountryCode = \"PHL\" && YearC = \"2012\" && SeriesCode = \"SL.UEM.TERT.FA.ZS\"";
	
	public static String deleteUnemploymentSGP2012Male = "DELETE FROM data_by_year WHERE CountryCode = \"SGP\" && TimeCode = \"2012\" && SeriesCode = \"SL.UEM.TERT.MA.ZS\"";
	
	public static String deleteUnemploymentSGP2012Female = "DELETE FROM data_by_year WHERE CountryCode = \"SGP\" && TimeCode = \"2012\" && SeriesCode = \"SL.UEM.TERT.FE.ZS\"";
	
}
