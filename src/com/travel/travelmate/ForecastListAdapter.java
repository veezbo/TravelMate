package com.travel.travelmate;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ForecastListAdapter extends ArrayAdapter<String>{
	private Context mContext;
    private List<String> values;

	public ForecastListAdapter(Context context, int textViewResourceId,
			List<String> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		mContext = context;
		values = objects;
	}
	
/*	@Override
	public View getView(int position, int convertView, ViewGroup parent) {
		super.getView(position, convertView, parent);
		return null;
	}*/
	

}
