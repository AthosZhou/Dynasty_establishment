package com.zhr.athos.dynasty_establishment.Student;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zhr.athos.dynasty_establishment.MyApplication;
import com.zhr.athos.dynasty_establishment.R;
import com.zhr.athos.dynasty_establishment.Util.ForHttp;

public class StuPunchActivity extends AppCompatActivity {

    private Button stu_start_punch;
    private MyApplication myApplication;
    private ForHttp forHttp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_punch);
        initView();
        initListener();
    }

    private void initListener() {
        stu_start_punch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                点击后先查找自己老师的时间
                拼接出对应的表开始签到
                成功直接退出 失败提示
                 */new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String url="http://47.106.38.118:2333/api/stu/ph_search?TeaNo="+"9201375";
                        String item=forHttp.ForHttp(url).replace("\"","");
                        String url1="http://47.106.38.118:2333/api/stu/ph_stu?StuNo="+myApplication.getIMNum()+"&Name=p_"+"9201375"+"_"+item;//后期班级系统加入后可以直接替换成老师的id
                        int end=Integer.parseInt(forHttp.ForHttp(url1));
                        if(end==1)
                        {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(StuPunchActivity.this,"签到成功",Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(StuPunchActivity.this,StuMainActivity.class);
                                    startActivity(intent);
                                }
                            });

                        }
                        else
                        {
                            Looper.prepare();
                            Toast.makeText(StuPunchActivity.this,"签到失败，请稍后再试！",Toast.LENGTH_LONG).show();
                            Looper.loop();
                        }
                    }
                }).start();

            }
        });
    }

    private void initView() {
        stu_start_punch=findViewById(R.id.stu_start_punch);
        myApplication=(MyApplication) getApplication();
        forHttp=new ForHttp();
    }
}
