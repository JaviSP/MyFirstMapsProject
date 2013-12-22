package com.bsodcorp.android.myfirstmapsproject;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyPlacesAdapter extends BaseAdapter {

	private Activity activity;
	private List<String> list;
	
	public MyPlacesAdapter(Activity activity, List<String>list) {
		this.activity = activity;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = activity.getLayoutInflater();
		View view = inflater.inflate(R.layout.drawer_list_item, null, true);
		
		TextView text = (TextView) view.findViewById(R.id.list_item);
		
		text.setText(list.get(position));
		
		return view;
	}

}
