package com.crays.gmagro.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class tools {

    public static String sumTps(String tps1, String tps2) throws ParseException {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        Date date1 = timeFormat.parse(tps1);
        Date date2 = timeFormat.parse(tps2);

        long sum = date1.getTime() + date2.getTime();
        String date3 = timeFormat.format(new Date(sum));
        return date3;
    }
}
