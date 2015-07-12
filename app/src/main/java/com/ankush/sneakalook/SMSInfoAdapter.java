package com.ankush.sneakalook;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
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
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            TextView amt = (TextView) row.findViewById(R.id.amt);
            TextView  place = (TextView) row.findViewById(R.id.place);
            TextView date = (TextView) row.findViewById(R.id.date);



        SMSInfo smsinfo = data[position];
        amt.setText(smsinfo.getNumber() + "");
        place.setText(SMSInboxUtil.toCamelCase(smsinfo.getPlaceOfTransaction()));
        date.setText(SMSFilter.df.format(smsinfo.getDate()));
        if(position%2==0) {
            place.setTextColor(0xff666666);
            date.setTextColor(0xff6666ff);
            amt.setTextColor(0xffff6666);
        } else {
            row.setBackgroundColor(0xffeeeeff);
            place.setTextColor(0xff888888);
            date.setTextColor(0xff8888ff);
            amt.setTextColor(0xffff7777);
        }
            return row;
    }
}
