package com.ankush.sneakalook;

import android.view.inputmethod.ExtractedTextRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;
/**
 * Created by Ankush on 29-06-2015.
 */

public class SMSInboxUtil {
    enum TokenType { Amount, IsDebit, Source, PlaceOfTransaction, TransactionDate };

    public static class ExtractorPattern {
        public String getRegex() {
            return regex;
        }
        public Pattern getPattern() {
            return p;
        }
        public Map<TokenType, Integer> getMapping() {
            return mapping;
        }

        public DateFormat getDateFormat() {
            return dateFormat;
        }
        public Option<SMSInfo> extractInfo(SMS sms) {
            Matcher matcher = p.matcher(sms.getBody().toLowerCase());
            while( matcher.find()) {
                double amt = Double.parseDouble(matcher.group(mapping.get(TokenType.Amount)).replaceAll(",", ""));
                Date smsDate = sms.getDate();
                Date txnDate;
                if( mapping.containsKey(TokenType.TransactionDate) ) {
                    String dateStr = matcher.group(mapping.get(TokenType.TransactionDate));
                    try {
                        txnDate = dateFormat.parse(dateStr);
                    } catch (ParseException pe) {
                        txnDate = sms.getDate();
                    }
                } else {
                    txnDate = sms.getDate();
                }

                boolean isDebit;
                if(mapping.containsKey(TokenType.IsDebit)) {
                    String matchStr = matcher.group(mapping.get(TokenType.IsDebit));
                    if(matchStr.contains("withdrawn") || matchStr.contains("debited") || matchStr.contains("dr"))
                        isDebit = true;
                    else
                        isDebit = false;
                } else {
                    isDebit = true;
                }

                String placeOfTxn="NA";
                if(mapping.containsKey(TokenType.PlaceOfTransaction)) {
                    placeOfTxn = matcher.group(mapping.get(TokenType.PlaceOfTransaction));
                }

                String source ="NA";
                if(mapping.containsKey(TokenType.Source)) {
                    source = matcher.group(mapping.get(TokenType.Source));
                }

                return new Option.Some( new SMSInfo(amt,smsDate,txnDate,isDebit,placeOfTxn,source));
            }
            return new Option.None<SMSInfo>();
        }

        private String regex;
        private Pattern p;
        private Map<TokenType, Integer> mapping;
        private DateFormat dateFormat;
        public ExtractorPattern( String argRegex, Map<TokenType, Integer> argTokenMap, DateFormat argDateFormat ) {
            regex = argRegex;
            mapping = argTokenMap;
            dateFormat = argDateFormat;
            p = Pattern.compile(regex);
        }
    }

    public static String x1 = "thank you for using (.*) ending (\\d+) for rs\\.(\\s?)(\\d+((,\\d+)*)\\.\\d+) (.*) at (.*) on (.*) avl (.*)";
    public static DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss");
    public static HashMap m1 = new HashMap(  );
    static {
        m1.put(TokenType.Amount, new Integer(4));
        m1.put(TokenType.TransactionDate, new Integer(9));
        m1.put(TokenType.PlaceOfTransaction, new Integer(8));
    }

    public static String x2 = "an amount of rs\\.(\\d+((,\\d+)*)\\.\\d+) has been (.*) from (.*) for (.*) done using (.*)";
    public static DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
    public static HashMap m2 = new HashMap(  );
    static {
        m2.put(TokenType.Amount, new Integer(1));
        m2.put(TokenType.PlaceOfTransaction, new Integer(6));
        m2.put(TokenType.Source, new Integer(7));
        m2.put(TokenType.IsDebit, new Integer(4));
    }

    public static String x3 = "rs\\.(\\d+((,\\d+)*)\\.\\d+) was (.*) using (.*) on (.*) at (.*)\\. avl (.*)";
    public static DateFormat df3 = new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss");
    public static HashMap m3 = new HashMap(  );
    static {
        m3.put(TokenType.Amount, new Integer(1));
        m3.put(TokenType.PlaceOfTransaction, new Integer(7));
        m3.put(TokenType.IsDebit, new Integer(4));
        m3.put(TokenType.TransactionDate, new Integer(6));
    }

    public static String x4 = "inr (\\d+((,\\d+)*)\\.\\d+) (.*) to (.*) towards (.*) paid-(.*) val (.*)\\. clr bal (.*)";
    public static DateFormat df4 = new SimpleDateFormat("dd-MMM-yy");
    public static HashMap m4 = new HashMap(  );
    static {
        m4.put(TokenType.Amount, new Integer(1));
        m4.put(TokenType.IsDebit, new Integer(4));
        m4.put(TokenType.PlaceOfTransaction, new Integer(7));
        m4.put(TokenType.Source, new Integer(6));
        m4.put(TokenType.TransactionDate, new Integer(8));
    }

    public static ArrayList<ExtractorPattern> listPatterns =  new ArrayList<ExtractorPattern>();
    static {
        ExtractorPattern p1 = new ExtractorPattern(x1, m1, df1);
        ExtractorPattern p2 = new ExtractorPattern(x2, m2, df2);
        ExtractorPattern p3 = new ExtractorPattern(x3, m3, df3);
        ExtractorPattern p4 = new ExtractorPattern(x4, m4, df4);
        listPatterns.add(p1);
        listPatterns.add(p2);
        listPatterns.add(p3);
        listPatterns.add(p4);
    }

    public static Option<SMSInfo> extractInfo ( SMS sms ){
        try {
            //An amount of Rs.233 has been debited from your account number xxxx8668 for an online payment txn done using HDFC net banking
            //An amount of Rs.233 has been debited from your account number xxxx8668 for NFT txn done using HDFC Bank Netbanking
            //Rs.10000  was withdrawn using your hdfc bank card ending 6249 on 2015-06-29:12:08:25 at +S V RD MALAD ATM. Avl Bal: Rs.234235
            //Thank you for using Debit card ending 6249 for Rs.433 in MUMBAI at 5 SPICE on 2015-06-29:12:08:25 Avl Bal: Rs.234235
            //INR 31,000 Dr to A/c No XX8668 towards Chq paid-MICR CTS-CHENNAI RK S Val 23-JUN-15. Cr Bal INR 1,34,345.09.
            //Salary of INR 2,234,534 credited to A/C XXXXXX8668 towards Salary Credit Morgan Stanley May2015 Value 26-May-2015 .Check A/c for balance
            //INR 6,00,000 deposited to A/c No xx3364 towards IB FUNDS TRANSFER CR-05421610028668 Val 19-May-15, Clr Bal is INR 6,01,234 subject to clearing.
            //Ur transaction on HDFC bank debit/atm card ending 6249 for Rs 375 has been credited/reversed by IRCTC12027 on 2015-06-29:12:08:25
            //IN 44,000 deposited to a/c No xx3364 towards credit Interest capitalized val 31-mar-15. clr bal is inr 22,86,897 subject to clearing.

            Option<SMSInfo> ret = new Option.None<SMSInfo>();
            for ( ExtractorPattern cp: listPatterns ) {
                if(!ret.isDefined())
                    ret = cp.extractInfo(sms);
            }

            return ret;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Option.None<SMSInfo>();
    }

    public static IPredicate<SMS> getFilters() {
        return SMSFilter.onContainsInAddress("HDFC").and(SMSFilter.onDefaultErrorCode());
    }

    public  static FArrayList<SMS> filterInvalidSMS( FArrayList<SMS> fArr ) {
        return fArr.filter(getFilters());
    }

    public static FArrayList<SMSInfo> extractInfo( FArrayList<SMS> fArr ) {
        return fArr.collect(new ExtractInfo());
    }


}
