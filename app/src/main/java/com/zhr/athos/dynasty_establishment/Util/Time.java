package com.zhr.athos.dynasty_establishment.Util;

import java.util.Calendar;

public class Time {

    public String GetTime()
    {
        Calendar Cld = Calendar.getInstance();
        String YY =String.valueOf(Cld.get(Calendar.YEAR) );
        String MM =String.valueOf(Cld.get(Calendar.MONTH)+1);
        String DD =String.valueOf( Cld.get(Calendar.DATE));
        String HH =String.valueOf( Cld.get(Calendar.HOUR_OF_DAY));
        String mm =String.valueOf( Cld.get(Calendar.MINUTE));
        String ss =String.valueOf( Cld.get(Calendar.SECOND));

        return YY+"_"+MM+"_"+DD+"_"+HH+"_"+mm+"_"+ss;
    }

    public String GetDate()
    {
        Calendar Cld = Calendar.getInstance();
        String YY =String.valueOf(Cld.get(Calendar.YEAR) );
        String MM =String.valueOf(Cld.get(Calendar.MONTH)+1);
        String DD =String.valueOf( Cld.get(Calendar.DATE));
        return YY+"-"+MM+"-"+DD;
    }

    public String GetToday()
    {
        Calendar Cld = Calendar.getInstance();
        String YY =String.valueOf(Cld.get(Calendar.YEAR) );
        String MM =String.valueOf(Cld.get(Calendar.MONTH)+1);
        String DD =String.valueOf( Cld.get(Calendar.DATE));
        String HH =String.valueOf( Cld.get(Calendar.HOUR_OF_DAY));
        String mm =String.valueOf( Cld.get(Calendar.MINUTE));
        String ss =String.valueOf( Cld.get(Calendar.SECOND));
        return YY+"/"+MM+"/"+DD;
    }
}
