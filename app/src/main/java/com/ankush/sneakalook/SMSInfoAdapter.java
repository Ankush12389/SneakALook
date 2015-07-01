package com.ankush.sneakalook;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Ankush on 02-07-2015.
 */
public class SMSInfoAdapter extends ArrayAdapter<SMSInfo> {

    Context context;
    int layoutResourceId;

    SMSInfo[] data;

    public SMSInfoAdapter(Context context, int layoutResourceId, SMSInfo[] data) {
        super(context,layoutResourceId,data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;

    }

    static class SMSInfoHolder {
        TextView amt, place, date;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        SMSInfoHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new SMSInfoHolder();
            holder.amt = (TextView) row.findViewById(R.id.amt);
            holder.place = (TextView) row.findViewById(R.id.place);
            holder.date = (TextView) row.findViewById(R.id.date);
        } else {
            holder = (SMSInfoHolder) row.getTag();
        }

        SMSInfo smsinfo = data[position];
        holder.amt.setText(smsinfo.getNumber() + "");
        holder.place.setText(smsinfo.getPlaceOfTransaction());
        holder.date.setText(smsinfo.getDate().toString());
        return row;
    }
}
