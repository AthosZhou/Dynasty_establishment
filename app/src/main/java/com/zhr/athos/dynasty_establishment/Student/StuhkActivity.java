package com.zhr.athos.dynasty_establishment.Student;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;
import com.zhr.athos.dynasty_establishment.Adapter.StuhkAdapter;
import com.zhr.athos.dynasty_establishment.Bean.stuhk;
import com.zhr.athos.dynasty_establishment.MyApplication;
import com.zhr.athos.dynasty_establishment.R;
import com.zhr.athos.dynasty_establishment.Student.Exam.ExamActivity;
import com.zhr.athos.dynasty_establishment.Util.ForHttp;

import java.util.List;

public class StuhkActivity extends AppCompatActivity {
    private ListView stu_hk_gg;
    private Handler handler;
    private List<stuhk.NoBean> nolist;
    private MyApplication myApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stuhk);
        stu_hk_gg=findViewById(R.id.stu_hk_gg);
        myApplication=(MyApplication) getApplication();
        /*
        填充数据
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url="http://47.106.38.118:2333/api/stu/find_hk?StuNo="+myApplication.getIMNum();
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
                        StuhkAdapter stuhkAdapter=new StuhkAdapter(getApplicationContext(),R.layout.stuhk_item,gson.fromJson(ask,stuhk.class).getNo());
                        stuhkAdapter.setCusClickListener(new StuhkAdapter.addClickListerner1() {
                            @Override
                            public void addClick1(int TeaNo, String date, int chapter, int postion) {
                                Intent intent=new Intent(StuhkActivity.this, ExamActivity.class);
                                intent.putExtra("TeaNo",String.valueOf(TeaNo));
                                intent.putExtra("date",date);
                                intent.putExtra("chapter",String.valueOf(chapter));
                                intent.putExtra("point","0");
                                startActivity(intent);
                            }
                        });
                        stu_hk_gg.setAdapter(stuhkAdapter);
                    }
                });
            }
        }).start();

        handler=new Handler() {
            @Override
            public void handleMessage(android.os.Message msg) {
                if (msg.what == 2) {
                    Gson gson1=new Gson();
                    List<stuhk.NoBean> token=gson1.fromJson(msg.obj.toString(),stuhk.class).getNo();
                    nolist=token;
                } else {
                }
            }
        };
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(StuhkActivity.this,StuMainActivity.class);
        startActivity(intent);
    }
}
