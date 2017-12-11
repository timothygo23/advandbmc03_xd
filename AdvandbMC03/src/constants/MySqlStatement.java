package constants;

public class MySqlStatement {
	private static final String country_region = "country_region";
	
	public static String getAll = "SELECT * FROM data_by_year";
	
	public static String getRegionOfCountryCode = "SELECT * FROM " + country_region + " WHERE countrycode = '";
	
	public static String getUnemploymentFrequencyMale = "SELECT C.CountryCode, C.CountryName, CI.Income, SUM(DY.Data) FROM country C, country_income CI, data_by_year DY WHERE C.CountryCode = CI.CountryCode && C.CountryCode = DY.CountryCode && DY.SeriesCode = \"SL.UEM.TERT.MA.ZS\" GROUP BY DY.CountryCode";
	
	public static String getUnemploymentFrequencyFemale = "SELECT C.CountryCode, C.CountryName, CI.Income, SUM(DY.Data) FROM country C, country_income CI, data_by_year DY WHERE C.CountryCode = CI.CountryCode && C.CountryCode = DY.CountryCode && DY.SeriesCode = \"SL.UEM.TERT.FE.ZS\" GROUP BY DY.CountryCode";
	
	public static String insertCountryUnemploymentZimbabweMale = "INSERT INTO data_by_year (CountryCode, SeriesCode, YearC, Data)VALUES (\"ZWE\", \"SL.UEM.TERT.MA.ZS\", \"2012\", \"12345\");";
	
	public static String insertCountryUnemploymentZimbabweFemale = "INSERT INTO data_by_year (CountryCode, SeriesCode, YearC, Data)VALUES (\"ZWE\", \"SL.UEM.TERT.FE.ZS\", \"2012\", \"12345\");";
	
	public static String updateUnemploymentFreqPHL2012Male = "UPDATE data_by_year SET Data = 12345 WHERE CountryCode = \"PHL\" && YearC = \"2012\" && SeriesCode = \"SL.UEM.TERT.MA.ZS\"";
	
	public static String updateUnemploymentFreqPHL2012Female = "UPDATE data_by_year SET Data = 12345 WHERE CountryCode = \"PHL\" && YearC = \"2012\" && SeriesCode = \"SL.UEM.TERT.FA.ZS\"";
	
	public static String deleteUnemploymentSGP2012Male = "DELETE FROM data_by_year WHERE CountryCode = \"SGP\" && TimeCode = \"2012\" && SeriesCode = \"SL.UEM.TERT.MA.ZS\"";
	
	public static String deleteUnemploymentSGP2012Female = "DELETE FROM data_by_year WHERE CountryCode = \"SGP\" && TimeCode = \"2012\" && SeriesCode = \"SL.UEM.TERT.FE.ZS\"";
	
	//local queries
	public static String localCase1_Transaction1(){
		/*
		 * gets the avg unemployment of female in 2004 in this respective node
		 */
		String sql = "SELECT AVG(Data)"
				+ " FROM data_by_year"
				+ " WHERE YearC = '2004 [YR2004]' and SeriesCode = 'SL.UEM.TERT.FE.ZS';";
		return sql;
	}
	
	public static String localCase1_Transaction2(){
		/*
		 * gets the sum unemployment of female in 2004 in this respective node
		 */
		String sql = "SELECT SUM(Data)"
				+ " FROM data_by_year"
				+ " WHERE YearC = '2004 [YR2004]' and SeriesCode = 'SL.UEM.TERT.FE.ZS';";
		return sql;
	}
	
	public static String localCase2_Transaction1(){
		/*
		 * gets the count of country that has data in the year 2001 in this respective node
		 */
		String sql = "SELECT count(CountryCode)"
				+ " FROM data_by_year"
				+ " WHERE YearC = '2001 [YR2001]'"
				+ " GROUP BY SeriesCode = 'SL.UEM.TERT.MA.ZS';";
		return sql;
	}
	
	public static String localCase2_Transaction2(String countryCode){
		/*
		 * inserts a data in the year 2001, in thi respective node
		 */
		String sql = "INSERT INTO data_by_ye"
				+ "ar (CountryCode, SeriesCode, YearC, Data)VALUES (\""+countryCode+"\", \"SL.UEM.TERT.MA.ZS\", \"2001 [YR2001]\", \"1234\");";
		return sql;
	}
	
	public static String localCase3_Transaction1(String countryCode){
		String sql = "UPDATE data_by_year SET Data = Data + 100 WHERE CountryCode = \""+countryCode+"\" && YearC = \"2001 [YR2001]\" && SeriesCode = \"SL.UEM.TERT.MA.ZS\";";
		return sql;
	}
	
	public static String localCase3_Transaction2(String countryCode){
		String sql = "UPDATE data_by_year SET Data = Data * 2 WHERE CountryCode = \""+countryCode+"\" && YearC = \"2001 [YR2001]\" && SeriesCode = \"SL.UEM.TERT.MA.ZS\";";
		return sql;
	}
	
	//global queries
	public static String globalCase1_Transaction1(){
		String sql = "SELECT AVG(Data)"
				+ " FROM data_by_year"
				+ " WHERE YearC = '2004 [YR2004]' and SeriesCode = 'SL.UEM.TERT.FE.ZS';";
		return sql;
	}
	
	public static String globalCase1_Transaction2(){
		String sql = "SELECT SUM(Data)"
				+ " FROM data_by_year"
				+ " WHERE YearC = '2004 [YR2004]' and SeriesCode = 'SL.UEM.TERT.FE.ZS';";
		return sql;
	}
	
	public static String globalCase2_Transaction1(){
		String sql = "UPDATE data_by_year SET Data = 12345 WHERE CountryCode = \"PHL\" AND YearC = \"2012\" AND SeriesCode = \"SL.UEM.TERT.MA.ZS\"";
		return sql;
	}
	
	public static String globalCase2_Transaction2(){
		String sql = "INSERT INTO data_by_year (CountryCode, SeriesCode, YearC, Data)VALUES (\"ZWE\", \"SL.UEM.TERT.FE.ZS\", \"2012\", \"12345\");";
		return sql;
	}
	
	public static String globalCase3_Transaction1(){
		String sql = "DELETE FROM data_by_year WHERE CountryCode = \"SGP\" AND TimeCode = \"2012\" AND SeriesCode = \"SL.UEM.TERT.MA.ZS\"";
		return sql;
	}
	
	public static String globalCase3_Transaction2(){
		String sql = "INSERT INTO data_by_year (CountryCode, SeriesCode, YearC, Data) VALUES (\"ZWE\", \"SL.UEM.TERT.MA.ZS\", \"2012\", \"12345\");";
		return sql;
	}
	
	
}
