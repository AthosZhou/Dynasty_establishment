package com.zhr.athos.dynasty_establishment.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhr.athos.dynasty_establishment.Adapter.ContactAdapter;
import com.zhr.athos.dynasty_establishment.Adapter.VideoListAdapter;
import com.zhr.athos.dynasty_establishment.Bean.Contact;
import com.zhr.athos.dynasty_establishment.Bean.VideoInfo;
import com.zhr.athos.dynasty_establishment.MyApplication;
import com.zhr.athos.dynasty_establishment.R;
import com.zhr.athos.dynasty_establishment.Util.ForHttp;

import java.util.List;
import java.util.regex.Pattern;

public class ContactsFragment extends Fragment {
    private ListView contacts_list;
    public MyApplication myApplication;
    private TextView idi;
    private Handler handler;
    private List<Contact.ContactBean> contactlist;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=View.inflate(getActivity(), R.layout.classcontactlist,null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        String num=myApplication.getIMNum();
        String pattern="920.*";
        final String url;
        boolean isMatch = Pattern.matches(pattern, num);
        if (isMatch)
        {
            idi.setText("您的身份是教师，您的学生如下：");
            url="http://47.106.38.118:2333/api/Tools/ListT?TeaNo="+num;
        }
        else
        {
            idi.setText("您的身份是学生，您的教师如下：");
            url="http://47.106.38.118:2333/api/Tools/ListS?StuNo="+num;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                /*
                获取信息列表填充数据；
                分为两种 一种是教师 一种是学生
                将教师的工号开辟分段规定在920....区间 0000-9999共计10000
                首先进行模糊判断借此确定查询的范围
                */
                ForHttp http=new ForHttp();
                Message msg = new Message();
                final String ask=http.ForHttp(url);
                Log.i("获得的数据源/联系人",ask);
                msg.what = 2;
                msg.obj = ask;
                handler.sendMessage(msg);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson=new Gson();
                        ContactAdapter contactAdapter=new ContactAdapter(getContext(),R.layout.stu_item,gson.fromJson(ask,Contact.class).getContact());
                        contacts_list.setAdapter(contactAdapter);
                    }
                });

            }
        }).start();
        handler=new Handler() {
            @Override
            public void handleMessage(android.os.Message msg) {
                if (msg.what == 2) {
                    Gson gson1=new Gson();
                    List<Contact.ContactBean> token=gson1.fromJson(msg.obj.toString(),Contact.class).getContact();
                    contactlist=token;
                } else {
                }
            }
        };
    }



    private void initView() {
        contacts_list=getActivity().findViewById(R.id.stu_list);
        myApplication=(MyApplication) getActivity().getApplication();
        idi=getActivity().findViewById(R.id.idit);
    }
}
