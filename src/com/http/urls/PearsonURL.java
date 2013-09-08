package com.http.urls;

public class PearsonURL {
	
	static final String API_KEY = "D9O5GQEOC5RNPDHSiICpOxGDfulfFxbM";
	
	public static String getUrlTopTen(int limit, String search, int dist) {
		return "http://api.pearson.com/v2/travel/topten?"+"limit="+limit+"&search="+search.replace(" ", "+")+"&dist="+dist+"&apikey="+API_KEY;  
	}
	
	public static String getUrlTopTen(int limit, float lat, float lon, int dist) {
		return "http://api.pearson.com/v2/travel/topten?"+"limit="+limit+"&search="+"&lat="+lat+"&lon="+lon+"&dist="+dist+"&apikey="+API_KEY;
	}
	
	public static String getUrlPlaces(int limit, float lat, float lon, int dist) {
		return "http://api.pearson.com/v2/travel/places?"+"limit="+limit+"&lat="+lat+"&lon"+lon+"&dist"+dist+"&apikey="+API_KEY;
	}
	
	public static String getUrlPlaces(int limit, String category, float lat, float lon, int dist) {
		return "http://api.pearson.com/v2/travel/places?"+"limit="+limit+"&category="+category+"&lat="+lat+"&lon"+lon+"&dist"+dist+"&apikey="+API_KEY;
	}
}
