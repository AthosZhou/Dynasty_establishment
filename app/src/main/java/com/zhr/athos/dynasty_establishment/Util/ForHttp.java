package com.zhr.athos.dynasty_establishment.Util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class ForHttp {

    public String ForHttp(String Ink)
    {
        String con="";
        try
        {
            URL url = new URL(Ink);
            URLConnection connection = url.openConnection();
            InputStream in = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(in,"utf-8");
            BufferedReader br = new BufferedReader(isr);
            String line;
            StringBuilder sb = new StringBuilder();
            while((line = br.readLine()) != null)
            {
                sb.append(line);
            }
            br.close();
            isr.close();
            in.close();
            con=sb.toString();
        }
        catch (Exception e)
        { e.printStackTrace();}
        return con;

    }

}
