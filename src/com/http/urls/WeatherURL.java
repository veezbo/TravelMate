package com.http.urls;

import android.graphics.drawable.Drawable;

public class WeatherURL {
	static final String API_KEY = "64d9af9fcac9850a";
	
	public static String getWeatherUrl(String features, String query, String format) {
		return "http://api.wunderground.com/api/"+API_KEY+"/"+features+"/q/"+query+"."+format;
	}
	
	public static Drawable drawable_from_url(String url, String src_name) throws 
	   java.net.MalformedURLException, java.io.IOException 
	{
	   return Drawable.createFromStream(((java.io.InputStream)
	      new java.net.URL(url).getContent()), src_name);
	}
}
