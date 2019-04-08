package com.zhr.athos.dynasty_establishment.Util;

public class dot {

    public String[] getindex(String ask)
    {
        String[] talk=new String[5];
        talk[0]=ask.substring(1,2);
        talk[1]=ask.substring(2,3);
        talk[2]=ask.substring(3,4);
        talk[3]=ask.substring(4,5);
        talk[4]=ask.substring(5,6);
        return talk;
    }

}
