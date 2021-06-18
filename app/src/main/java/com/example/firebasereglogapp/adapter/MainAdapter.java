package com.example.firebasereglogapp.adapter;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebasereglogapp.R;

import java.util.ArrayList;
import java.util.HashMap;

public class MainAdapter extends BaseExpandableListAdapter {
    ArrayList<String> mainArrayList=new ArrayList<>();
    HashMap<String,ArrayList<String>> childList=new HashMap<>();

    public MainAdapter(ArrayList<String> mainArrayList, HashMap<String, ArrayList<String>> childList) {
        this.mainArrayList = mainArrayList;
        this.childList = childList;
    }

    @Override
    public int getGroupCount() {
        return mainArrayList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childList.get(mainArrayList.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mainArrayList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childList.get(mainArrayList.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_list,parent,false);
        ImageView img=convertView.findViewById(R.id.img_group);
        TextView textView2=convertView.findViewById(R.id.text1);
        String sGroups=String.valueOf(getGroup(groupPosition));
        textView2.setText(sGroups);
        textView2.setTypeface(null, Typeface.BOLD);
        textView2.setTextColor(Color.BLUE);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_layout,parent,false);
        ImageView img=convertView.findViewById(R.id.img_child);
        TextView textView=convertView.findViewById(R.id.text1);
        String sChilds=String.valueOf(getChild(groupPosition,childPosition));
        textView.setText(sChilds);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setTextColor(Color.DKGRAY);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

     Toast.makeText(parent.getContext(), sChilds, Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
