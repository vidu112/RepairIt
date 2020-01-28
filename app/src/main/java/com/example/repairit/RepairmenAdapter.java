package com.example.repairit;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;



public class RepairmenAdapter extends ArrayAdapter<Repairmen> {
    private Context mContext;
    private int mResource;

    private static class ViewHolder {
        TextView repairmen_repairType;
        TextView repairmen_name;
        TextView repairmen_costPerDay;
        TextView repairmen_rating;

    }

    public RepairmenAdapter(Context context, int resource, ArrayList<Repairmen> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder;
        final View result;

        String First = getItem(position).getRepairType();
        String Second = getItem(position).getFullName();
        String Thrid = getItem(position).getCostPerDay();
        String Forth = getItem(position).getRating();
        String Five = getItem(position).getDescription();

        Repairmen repairmen = new Repairmen(First,Second,Thrid,Forth,Five);

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.repairmen_repairType = (TextView) convertView.findViewById(R.id.repairmen_repairType);
            holder.repairmen_name = (TextView) convertView.findViewById(R.id.repairmen_name);
            holder.repairmen_costPerDay = (TextView) convertView.findViewById(R.id.repairmen_costPerDay);
            holder.repairmen_rating = (TextView) convertView.findViewById(R.id.repairmen_rating);


            result = convertView;
            convertView.setTag(holder);
        } else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        holder.repairmen_repairType.setText(First);
        holder.repairmen_name.setText(Second);
        holder.repairmen_costPerDay.setText(Thrid);
        holder.repairmen_rating.setText(Forth);




        return convertView;
    }

}