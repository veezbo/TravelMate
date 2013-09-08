package com.helper.json;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;

import org.json.JSONObject;
import com.eclipsesource.json.*;

import android.util.Log;

public class JsonHelper {
	
	private static final String TAG = "JsonHelper";
	
	public static JSONObject readJSONFromUrl(String url) throws IOException {
	    
		try {
	    	Log.d(TAG, "Starting readJsonFromUrl");
	        BufferedReader rd = new BufferedReader(new InputStreamReader(new URL(url).openStream(), Charset.forName("UTF-8")));
	        Log.d(TAG, "Got BufferedReader of URL request");
	        
	        StringBuilder sb = new StringBuilder();
	        String inputStr;
	        while ((inputStr = rd.readLine()) != null) {
	        	sb.append(inputStr);
	        }
	        	        
	        JSONObject jo = new JSONObject(sb.toString());
	        Log.d(TAG, "Got JSONObject by reading from bufferedreader");
	        rd.close();
	        return jo;
	    }
	    catch (Exception e) {
	    	Log.d(TAG, "errored at reading json from url");
	    }
	    return null;
	}
	
	public static JsonObject readJsonFromUrl(String url) throws IOException {
	    
		try {
			
	    	Log.d(TAG, "Starting readJsonFromUrl");
	        BufferedReader rd = new BufferedReader(new InputStreamReader(new URL(url).openStream(), Charset.forName("UTF-8")));
	        Log.d(TAG, "Got BufferedReader of URL request");
	        
	        JsonObject jo = JsonObject.readFrom(rd);
	        Log.d(TAG, "Got JsonObject by reading from bufferedreader");
	        rd.close();
	        return jo;
	    	
	    }
	    catch (Exception e) {
	    	Log.d(TAG, "darn errored at reading json from url");
	    }
	    return null;
	    
	}
}
