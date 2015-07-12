package com.ankush.sneakalook;

import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Highlight;

import org.w3c.dom.Text;

/**
 * Created by Ankush on 13-07-2015.
 */
public class SMSChartValueSelectedListener implements OnChartValueSelectedListener {
    ListView lv;
    TextView tv;
    public SMSChartValueSelectedListener(ListView argLV, TextView argTV){
        this.lv = argLV;
        tv = argTV;
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        lv.setSelection(e.getXIndex());
    }

    @Override
    public void onNothingSelected() {

    }
}
