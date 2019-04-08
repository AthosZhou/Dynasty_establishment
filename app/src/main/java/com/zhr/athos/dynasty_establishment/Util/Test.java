package com.zhr.athos.dynasty_establishment.Util;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/*
* 测试用的网易云注册api使用
* 在我们注册的过程中要将获取的token作为网易云密码存储 同时自己也要在登录处先验证私有密码再获取token密码登录
*
 */
public class Test {

    public String Verify(String No,String Name) throws Exception
    {
        DefaultHttpClient httpClient=new DefaultHttpClient();
        String url = "https://api.netease.im/nimserver/user/create.action";
        HttpPost httpPost = new HttpPost(url);

        String appKey = "b3fb3ead33e0625a1753d5c86a9497ec";
        String appSecret = "d8ba4445c194";
        String nonce = Integer.toString((int) (Math.random() * 999999999));
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);//参考 计算CheckSum的java代码

        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // 设置请求的参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("accid", No));
        nvps.add(new BasicNameValuePair("name", Name));
        httpPost.setEntity(new UrlEncodedFormEntity
                (nvps, "utf-8"));
        // 执行请求

        HttpResponse response = httpClient.execute(httpPost);
//        Log.i("服务器注册测试",EntityUtils.toString(response.getEntity(), "utf-8"));
        return EntityUtils.toString(response.getEntity(), "utf-8");
    }

    public String addFriend(String accid,String faccid) throws Exception
    {
        DefaultHttpClient httpClient=new DefaultHttpClient();
        String url = "https://api.netease.im/nimserver/friend/add.action";
        HttpPost httpPost = new HttpPost(url);

        String appKey = "b3fb3ead33e0625a1753d5c86a9497ec";
        String appSecret = "d8ba4445c194";
        String nonce = Integer.toString((int) (Math.random() * 999999999));
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);//参考 计算CheckSum的java代码

        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // 设置请求的参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("accid", accid));
        nvps.add(new BasicNameValuePair("faccid", faccid));
        nvps.add(new BasicNameValuePair("type", "1"));
        nvps.add(new BasicNameValuePair("msg", "addfriend"));
        httpPost.setEntity(new UrlEncodedFormEntity
                (nvps, "utf-8"));
        // 执行请求

        HttpResponse response = httpClient.execute(httpPost);
        return EntityUtils.toString(response.getEntity(), "utf-8");
    }
}
