package com.zhr.athos.dynasty_establishment.Student;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.zhr.athos.dynasty_establishment.Adapter.StuhkAdapter;
import com.zhr.athos.dynasty_establishment.Adapter.StuhkAlAdapter;
import com.zhr.athos.dynasty_establishment.Bean.stuhk;
import com.zhr.athos.dynasty_establishment.MyApplication;
import com.zhr.athos.dynasty_establishment.R;
import com.zhr.athos.dynasty_establishment.Student.Exam.ExamActivity;
import com.zhr.athos.dynasty_establishment.Util.ForHttp;

import java.util.List;

public class StuHkAlActivity extends AppCompatActivity {

    private ListView stu_hk_kk;
    private MyApplication myApplication;
    private Handler handler;
    private List<stuhk.YesBean> nolist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_hk_al);
        myApplication=(MyApplication) getApplication();
        stu_hk_kk=findViewById(R.id.stu_hk_kk);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url="http://47.106.38.118:2333/api/stu/find_hk_al?StuNo="+myApplication.getIMNum();
                ForHttp http=new ForHttp();
                Message msg = new Message();
                final String ask=http.ForHttp(url);
                Log.i("获得的作业情况",ask);
                msg.what = 2;
                msg.obj = ask;
                handler.sendMessage(msg);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson=new Gson();
                        StuhkAlAdapter stuhkAdapter=new StuhkAlAdapter(getApplicationContext(),R.layout.stuhk_item,gson.fromJson(ask,stuhk.class).getYes());
                        stuhkAdapter.setCusClickListener(new StuhkAdapter.addClickListerner1() {
                            @Override
                            public void addClick1(int TeaNo, String date, int chapter, int postion) {
                                Intent intent=new Intent(StuHkAlActivity.this, ExamActivity.class);
                                intent.putExtra("TeaNo",String.valueOf(TeaNo));
                                intent.putExtra("date",date);
                                intent.putExtra("chapter",String.valueOf(chapter));
                                intent.putExtra("point","1");
                                startActivity(intent);
                            }
                        });
                        stu_hk_kk.setAdapter(stuhkAdapter);
                    }
                });
            }
        }).start();

        handler=new Handler() {
            @Override
            public void handleMessage(android.os.Message msg) {
                if (msg.what == 2) {
                    Gson gson1=new Gson();
                    List<stuhk.YesBean> token=gson1.fromJson(msg.obj.toString(),stuhk.class).getYes();
                    nolist=token;
                } else {
                }
            }
        };
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(StuHkAlActivity.this,StuMainActivity.class);
        startActivity(intent);
    }
}
