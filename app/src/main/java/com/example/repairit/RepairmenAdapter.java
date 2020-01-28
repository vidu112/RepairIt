package com.example.repairit;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.opencensus.common.Function;


public class RepairmenAdapter extends ArrayAdapter<Repairmen> {
    private Context mContext;
    private int mResource;

    private static class ViewHolder {
        TextView repairmen_repairType;
        TextView repairmen_name;
        TextView repairmen_costPerDay;
        TextView repairmen_rating;
        Button hire_button;

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

        final String First = getItem(position).getRepairType();
        final String Second = getItem(position).getFullName();
        final String Thrid = getItem(position).getCostPerDay();
        final String Forth = getItem(position).getRating();
        final String Five = getItem(position).getDescription();
        //final Function Six = getItem(position).getHire();
        //Repairmen repairmen = new Repairmen(First,Second,Thrid,Forth,Five,Six);
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.repairmen_repairType = (TextView) convertView.findViewById(R.id.repairmen_repairType);
            holder.repairmen_name = (TextView) convertView.findViewById(R.id.repairmen_name);
            holder.repairmen_costPerDay = (TextView) convertView.findViewById(R.id.repairmen_costPerDay);
            holder.repairmen_rating = (TextView) convertView.findViewById(R.id.repairmen_rating);
            holder.hire_button = (Button) convertView.findViewById(R.id.hire_me);


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
        holder.hire_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(mContext, DashBoard.class);
                mContext.startActivity(intent);
                Log.i(Second,"Working");
            }
        });




        return convertView;
    }

}