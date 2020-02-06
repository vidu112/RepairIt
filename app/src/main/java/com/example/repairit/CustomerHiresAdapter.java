package com.example.repairit;


import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class CustomerHiresAdapter extends ArrayAdapter<HiresClass> {
    private Context mContext;
    private int mResource;

    public CustomerHiresAdapter(Context context, int resource, ArrayList<HiresClass> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final View result;
        final String RepairmanType = getItem(position).getRepairmanType();
        final String RepairmanName = getItem(position).getRepairmanName();
        final String Price = getItem(position).getCustomerPaidPrice();
        final String Date = getItem(position).getDate();
        final String Status = getItem(position).getStatus();
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.repairmen_repairType = convertView.findViewById(R.id.cus_repairmen_repairType);
            holder.repairmen_name = convertView.findViewById(R.id.cus_repairmen_name);
            holder.repairmen_costPerDay = convertView.findViewById(R.id.cus_price);
            holder.repairmen_date = convertView.findViewById(R.id.cus_date);
            holder.hire_linear_layout = convertView.findViewById(R.id.customer_repairmen_linear_layout);
            result = convertView;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        holder.repairmen_repairType.setText(RepairmanType);
        holder.repairmen_name.setText(RepairmanName);
        holder.repairmen_costPerDay.setText(Price);
        holder.repairmen_date.setText(Date);
        if (Status.equals("Accepted")) {
            holder.hire_linear_layout.setBackgroundColor(Color.GREEN);
        } else if (Status.equals("NotAccepted")) {
            holder.hire_linear_layout.setBackgroundColor(Color.YELLOW);
        } else {
            holder.hire_linear_layout.setBackgroundColor(Color.RED);
        }
        //holder.hire_linear_layout.setBackgroundColor(Color.RED);
        holder.hire_linear_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("HireList", RepairmanName);
            }
        });


        return convertView;
    }

    private static class ViewHolder {
        TextView repairmen_repairType;
        TextView repairmen_name;
        TextView repairmen_costPerDay;
        TextView repairmen_date;
        LinearLayout hire_linear_layout;

    }

}