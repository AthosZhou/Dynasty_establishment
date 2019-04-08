package com.zhr.athos.dynasty_establishment.Teacher;

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
import com.zhr.athos.dynasty_establishment.Util.Time;

public class ChooseTargetActivity extends AppCompatActivity implements View.OnClickListener{
    private Button target_1,target_2,target_3,target_4,target_5,target_6;
    public MyApplication myApplication;
    private Time time;
    private ForHttp forHttp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_target);
        initview();
        myApplication=(MyApplication) getApplication();
        initListener();

    }

    private void initListener() {
        target_1.setOnClickListener(this);
        target_2.setOnClickListener(this);
        target_3.setOnClickListener(this);
        target_4.setOnClickListener(this);
        target_5.setOnClickListener(this);
        target_6.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.target_1:
                time=new Time();
                forHttp=new ForHttp();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String url="http://47.106.38.118:2333/api/tea/create_hk?TeaNo="+myApplication.getIMNum()+"&chapter=1&CreateTime="+time.GetDate();
                        if(Integer.parseInt(forHttp.ForHttp(url))==1)
                        {
                            Looper.prepare();
                            Toast.makeText(ChooseTargetActivity.this,"发布作业！",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                        else
                        {
                            Looper.prepare();
                            Toast.makeText(ChooseTargetActivity.this,"今天本章节已经发布过啦！",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }
                }).start();
                break;
            case R.id.target_2:
                time=new Time();
                forHttp=new ForHttp();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String url="http://47.106.38.118:2333/api/tea/create_hk?TeaNo="+myApplication.getIMNum()+"&chapter=2&CreateTime="+time.GetDate();
                        if(Integer.parseInt(forHttp.ForHttp(url))==1)
                        {
                            Looper.prepare();
                            Toast.makeText(ChooseTargetActivity.this,"发布作业！",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                        else
                        {
                            Looper.prepare();
                            Toast.makeText(ChooseTargetActivity.this,"今天本章节已经发布过啦！",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }
                }).start();
                break;
            case R.id.target_3:
                time=new Time();
                forHttp=new ForHttp();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String url="http://47.106.38.118:2333/api/tea/create_hk?TeaNo="+myApplication.getIMNum()+"&chapter=3&CreateTime="+time.GetDate();
                        if(Integer.parseInt(forHttp.ForHttp(url))==1)
                        {
                            Looper.prepare();
                            Toast.makeText(ChooseTargetActivity.this,"发布作业！",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                        else
                        {
                            Looper.prepare();
                            Toast.makeText(ChooseTargetActivity.this,"今天本章节已经发布过啦！",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }
                }).start();
                break;
            case R.id.target_4:
                time=new Time();
                forHttp=new ForHttp();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String url="http://47.106.38.118:2333/api/tea/create_hk?TeaNo="+myApplication.getIMNum()+"&chapter=4&CreateTime="+time.GetDate();
                        if(Integer.parseInt(forHttp.ForHttp(url))==1)
                        {
                            Looper.prepare();
                            Toast.makeText(ChooseTargetActivity.this,"发布作业！",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                        else
                        {
                            Looper.prepare();
                            Toast.makeText(ChooseTargetActivity.this,"今天本章节已经发布过啦！",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }
                }).start();
                break;
            case R.id.target_5:
                time=new Time();
                forHttp=new ForHttp();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String url="http://47.106.38.118:2333/api/tea/create_hk?TeaNo="+myApplication.getIMNum()+"&chapter=5&CreateTime="+time.GetDate();
                        if(Integer.parseInt(forHttp.ForHttp(url))==1)
                        {
                            Looper.prepare();
                            Toast.makeText(ChooseTargetActivity.this,"发布作业！",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                        else
                        {
                            Looper.prepare();
                            Toast.makeText(ChooseTargetActivity.this,"今天本章节已经发布过啦！",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }
                }).start();
                break;
            case R.id.target_6:
                time=new Time();
                forHttp=new ForHttp();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String url="http://47.106.38.118:2333/api/tea/create_hk?TeaNo="+myApplication.getIMNum()+"&chapter=6&CreateTime="+time.GetDate();
                        if(Integer.parseInt(forHttp.ForHttp(url))==1)
                        {
                            Looper.prepare();
                            Toast.makeText(ChooseTargetActivity.this,"发布作业！",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                        else
                        {
                            Looper.prepare();
                            Toast.makeText(ChooseTargetActivity.this,"今天本章节已经发布过啦！",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }
                }).start();
                break;
        }
    }

    private void initview() {
        target_1=(Button) findViewById(R.id.target_1);
        target_2=(Button) findViewById(R.id.target_2);
        target_3=(Button) findViewById(R.id.target_3);
        target_4=(Button) findViewById(R.id.target_4);
        target_5=(Button) findViewById(R.id.target_5);
        target_6=(Button) findViewById(R.id.target_6);
    }
}
