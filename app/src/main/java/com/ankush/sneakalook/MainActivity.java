package com.ankush.sneakalook;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.Telephony;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.util.Log;


public class MainActivity extends ActionBarActivity {
    TextView lblMsg,lblNumber;
    private static final String TAG = MainActivity.class.getSimpleName();
    SimpleCursorAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy old = StrictMode.getThreadPolicy();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder(old)
                .permitDiskWrites()
                .build());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            ListView lvSMSMsgs = (ListView) findViewById(R.id.SMSMsgs);
            Uri inboxURI = Uri.parse("content://sms/inbox");
            String[] reqCols =
                    new String[] {
                            Telephony.TextBasedSmsColumns.ERROR_CODE,
                            Telephony.TextBasedSmsColumns.THREAD_ID,
                            Telephony.TextBasedSmsColumns.ADDRESS,
                            Telephony.TextBasedSmsColumns.PERSON,
                            Telephony.TextBasedSmsColumns.DATE,
                            Telephony.TextBasedSmsColumns.PROTOCOL,
                            Telephony.TextBasedSmsColumns.READ,
                            Telephony.TextBasedSmsColumns.STATUS,
                            Telephony.TextBasedSmsColumns.TYPE,
                            Telephony.TextBasedSmsColumns.REPLY_PATH_PRESENT,
                            Telephony.TextBasedSmsColumns.SUBJECT,
                            Telephony.TextBasedSmsColumns.BODY,
                            Telephony.TextBasedSmsColumns.SERVICE_CENTER,
                            Telephony.TextBasedSmsColumns.TYPE
                    };
            ContentResolver cr = getContentResolver();
            Cursor c = cr.query(inboxURI, reqCols, null, null, null);

            if (c.moveToFirst()) {
                ArrayList<SMS> arrSMS = new ArrayList<SMS>();
                do {
                    SMS sms = new SMS(
                            c.getString(c.getColumnIndex(reqCols[0])),
                            c.getString(c.getColumnIndex(reqCols[1])),
                            c.getString(c.getColumnIndex(reqCols[2])),
                            c.getString(c.getColumnIndex(reqCols[3])),
                            c.getString(c.getColumnIndex(reqCols[4])),
                            c.getString(c.getColumnIndex(reqCols[5])),
                            c.getString(c.getColumnIndex(reqCols[6])),
                            c.getString(c.getColumnIndex(reqCols[7])),
                            c.getString(c.getColumnIndex(reqCols[8])),
                            c.getString(c.getColumnIndex(reqCols[9])),
                            c.getString(c.getColumnIndex(reqCols[10])),
                            c.getString(c.getColumnIndex(reqCols[11])),
                            c.getString(c.getColumnIndex(reqCols[12])),
                            c.getString(c.getColumnIndex(reqCols[13]))
                    );

                    arrSMS.add(sms);
                } while (c.moveToNext());

                FArrayList<SMS> fArrSMS = new FArrayList<>(arrSMS);
                FArrayList<String> fArrInfo = SMSInboxUtil.extractInfo( SMSInboxUtil.filterInvalidSMS(fArrSMS) ).map(SMSFilter.onToString());
                ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, fArrInfo.arrList);
                lvSMSMsgs.setAdapter(adapter);

            } else {
                // empty box, no SMS
            }
            c.close();
        } catch(Exception e) {
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}