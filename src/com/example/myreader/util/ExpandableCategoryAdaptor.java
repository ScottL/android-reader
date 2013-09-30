package com.example.myreader.util;

import java.util.HashMap;
import java.util.List;

import com.example.myreader.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ExpandableCategoryAdaptor extends BaseExpandableListAdapter {
	
	final static String[] COLORS = {"#33B5E5", "#AA66CC", "#99CC00", "#FFBB33", "#FF4444", "#0099CC", "#9933CC", "#669900", "#FF8800" , "#CC0000"};
	private Context mContext;
	private List<String> mGroupData;
    private HashMap<String, List<String>> mChildData;
	 
    public ExpandableCategoryAdaptor(Context context, List<String> listDataHeader,
            HashMap<String, List<String>> listChildData) {
        this.mContext = context;
        this.mGroupData = listDataHeader;
        this.mChildData = listChildData;
    }
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return this.mChildData.get(this.mGroupData.get(groupPosition)).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.subcategory_row, null);
		}
		String childText = (String) getChild(groupPosition, childPosition);
        TextView childName = (TextView) convertView.findViewById(R.id.subcategory_name);
        childName.setText(childText);
        return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return this.mChildData.get(this.mGroupData.get(groupPosition)).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this.mGroupData.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return this.mGroupData.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.category_row, null);
		}
		TextView groupName = (TextView) convertView.findViewById(R.id.category_name);
		groupName.setText(mGroupData.get(groupPosition));
		groupName.setTextColor(Color.parseColor(COLORS[groupPosition % 10]));
		groupName.getBackground().setAlpha(190);
        return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
