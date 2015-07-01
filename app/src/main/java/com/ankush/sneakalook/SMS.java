package com.ankush.sneakalook;

import android.text.BoringLayout;

import java.util.Date;

/**
 * Created by Ankush on 27-06-2015.
 */
public class SMS {
    private int errorCode;
    private int threadID;
    private String address;
    private int person;
    private Date date;
    private int protocol;
    private boolean read;
    private String status;
    private int type;
    private boolean replyPathPresent;
    private String subject;
    private String body;
    private String serviceCenter;
    private int locked;

    public SMS(
            int argErrorCode,
            int argThreadID,
            String argAddress,
            int argPerson,
            Date argDate,
            int argProtocol,
            boolean argRead,
            String argStatus,
            int argType,
            boolean argReplyPathPresent,
            String argSubject,
            String argBody,
            String argServiceCenter,
            int argLocked
    ) {
        errorCode = argErrorCode;
        threadID = argThreadID;
        address = argAddress;
        person = argPerson;
        date = argDate;
        protocol = argProtocol;
        read = argRead;
        status = argStatus;
        type = argType;
        replyPathPresent = argReplyPathPresent;
        subject = argSubject;
        body = argBody;
        serviceCenter = argServiceCenter;
        locked = argLocked;
    }

    public SMS(
            String argErrorCode,
            String argThreadID,
            String argAddress,
            String argPerson,
            String argDate,
            String argProtocol,
            String  argRead,
            String argStatus,
            String argType,
            String  argReplyPathPresent,
            String argSubject,
            String argBody,
            String argServiceCenter,
            String argLocked
    ) {
        if( argErrorCode==null)
            errorCode = -1;
        else
            errorCode = Integer.parseInt(argErrorCode);

        if( argThreadID==null)
            threadID = -1;
        else
            threadID = Integer.parseInt(argThreadID);

        if(argAddress==null)
            address="";
        else
            address = argAddress;

        if(argPerson==null)
            person = -1;
        else
            person = Integer.parseInt(argPerson);
        if(argDate==null)
            date = null;
        else
            date = new Date( Long.parseLong(argDate) );

        if(argProtocol==null)
            protocol = -1;
        else
            protocol = Integer.parseInt(argProtocol);

        if(argRead == null)
            read = true;
        else
            read = Boolean.parseBoolean(argRead);

        if(argStatus == null )
            status = "";
        else
            status = argStatus;

        if(argType==null)
            type=-1;
        else
            type = Integer.parseInt(argType);

        if(argReplyPathPresent==null)
            replyPathPresent = false;
        else
            replyPathPresent = Boolean.parseBoolean(argReplyPathPresent);

        if(argSubject==null)
            subject = "";
        else
            subject = argSubject;

        if(argBody==null)
            body="";
        else
            body = argBody;

        if(argServiceCenter==null)
            serviceCenter="";
        else
            serviceCenter = argServiceCenter;

        if(argLocked==null)
            locked = -1;
        else
            locked = Integer.parseInt(argLocked);


    }

    public int getErrorCode() {
        return errorCode;
    }

    public int getThreadID() {
        return errorCode;
    }

    public String getAddress() {
        return address;
    }

    public int getPerson() {
        return person;
    }

    public Date getDate() {
        return date;
    }

    public int getProtocol() {
        return protocol;
    }

    public boolean getRead() {
        return read;
    }

    public String getStatus() {
        return status;
    }

    public int getType() {
        return type;
    }
    public boolean getReplyPathPresent() {
        return replyPathPresent;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public String getServiceCenter() {
        return serviceCenter;
    }

    public int getLocked() {
        return locked;
    }
}
