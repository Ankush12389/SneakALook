package com.ankush.sneakalook;

import android.provider.ContactsContract;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ankush on 28-06-2015.
 */
class ContainsInAddress extends IPredicate<SMS> {

    private String pred;

    public ContainsInAddress(String argPred) {
        pred = argPred.toLowerCase();
    }

    public boolean apply( SMS sms ) {
        return sms.getAddress().toLowerCase().contains(pred);
    }
}

class ExtractInfo implements IConverter<SMS, Option<SMSInfo>>{
    public Option<SMSInfo> apply( SMS sms ) {
        return SMSInboxUtil.extractInfo(sms);
    }
}
public class SMSFilter {
    public static IPredicate<SMS> onContainsInAddress(String pred) {
        return new ContainsInAddress(pred);
    }

    public static IPredicate<SMS> onDefaultErrorCode() {
        return new IPredicate<SMS>() {
            @Override
            public boolean apply(SMS instance) {
                return (instance.getErrorCode()==0);
            }
        };
    }

    public static IConverter<SMS,String>  onGetBody() {
        return new IConverter<SMS, String>() {
            @Override
            public String apply(SMS sms) {
                return sms.getBody();
            }
        };
    }

    public static DateFormat df = new SimpleDateFormat("dd-MMM-yy");

    public static IConverter<SMSInfo,String>  onToString() {
        return new IConverter<SMSInfo, String>() {
            @Override
            public String apply(SMSInfo smsInfo) {
                String debitInfo =
                smsInfo.isDebit() ? "-" : "+";
                return (debitInfo+smsInfo.getNumber() + " on " + df.format(smsInfo.getTransactionDate()) + " at " + smsInfo.getPlaceOfTransaction());
            }
        };
    }

}
