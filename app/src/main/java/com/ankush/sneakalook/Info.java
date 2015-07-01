package com.ankush.sneakalook;

import java.util.Date;

/**
 * Created by Ankush on 29-06-2015.
 */
public interface Info {
    enum Type { Amount, Percent };
    enum Origin{ SMS, Mail };

    public Type getType();
    public Double getNumber();
    public Date getDate();
    public Origin getOrigin();
}
