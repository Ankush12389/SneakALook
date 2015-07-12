package com.ankush.sneakalook;

import java.util.Date;

/**
 * Created by Ankush on 29-06-2015.
 */
public class SMSInfo implements Info {

    public SMSInfo(
            Double argAmt,
            Date argSmsDate,
            Date argTransactionDate,
            boolean argIsDebit,
            String argPlaceOfTransaction,
            String argSource
    ){
        amt = argAmt;
        smsDate = argSmsDate;
        transactionDate = argTransactionDate;
        isDebit = argIsDebit;
        placeOfTransaction = argPlaceOfTransaction;
        source = argSource;
    }

    private Double amt;
    private Date smsDate;
    private Date transactionDate;
    private boolean isDebit;
    private String placeOfTransaction;
    private String source;

    public Type getType() {
        return Type.Amount;
    }
    public Double getNumber() {
        return amt;
    };
    public Date getDate() {
        return smsDate;
    }
    public Origin getOrigin(){
        return Origin.SMS;
    };
    public Date getTransactionDate(){
        return transactionDate;
    }
    public boolean isDebit(){
        return isDebit;
    }
    public String getPlaceOfTransaction(){
        return placeOfTransaction;
    }
    public String getSource(){
        return source;
    }
    @Override
    public String toString(){
        return ( getNumber() + " at " + getPlaceOfTransaction() + " on " +  getDate() );
    }
}
