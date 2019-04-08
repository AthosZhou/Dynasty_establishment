package com.zhr.athos.dynasty_establishment.Teacher;

import android.app.Application;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zhr.athos.dynasty_establishment.MyApplication;
import com.zhr.athos.dynasty_establishment.R;
import com.zhr.athos.dynasty_establishment.Util.ForHttp;
import com.zhr.athos.dynasty_establishment.Util.Time;


                /*
                设计思路：教师点击签到按钮 开启签到 并将页面置为签到中 按钮置为结束签到
                 */


public class TeaPunchActivity extends AppCompatActivity {

    private Button tea_status_start,tea_status_end;
    private TextView tea_punch_status;
    private MyApplication application;
    private Time time;
    private ForHttp http;
    private String alttime="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tea_punch);
        initView();
        initListener();

    }

    private void initListener() {

        tea_status_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                置状态
                插入表
                建立新表
                 */
                alttime=time.GetTime();
                final String url="http://47.106.38.118:2333/api/tea/punch?TeaNo="+application.getIMNum()+"&CreateTime="+alttime;
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        int ask=Integer.parseInt(http.ForHttp(url));
                        if(ask!=1)
                        {

                            Looper.prepare();
                            Toast.makeText(TeaPunchActivity.this,"操作过于频繁",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                        else
                        {
                            String url1="http://47.106.38.118:2333/api/tea/CreatePunch?punchinfo=p_"+application.getIMNum()+"_"+time.GetTime();
                            http.ForHttp(url1);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tea_punch_status.setText("签到中");
                                    tea_status_start.setVisibility(View.GONE);
                                    tea_status_end.setVisibility(View.VISIBLE);
                                }
                            });

                        }
                    }
                }).start();


            }
        });
        tea_status_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String url="http://47.106.38.118:2333/api/tea/AltPunch?Name=p_"+application.getIMNum()+"_"+alttime;
                        http.ForHttp(url);
                        String url1="http://47.106.38.118:2333/api/Tools/download?tablename=f_"+application.getIMNum()+"_"+alttime;
                        http.ForHttp(url1);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                String downloadurl = "http://47.106.38.118:7878/f_"+application.getIMNum()+"_"+alttime+".xls";
                                //创建下载任务,downloadUrl就是下载链接
                                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadurl));
                                //指定下载路径和下载文件名
                                request.setDestinationInExternalPublicDir("/download", "出席表"+alttime+".xls");
                                //获取下载管理器
                                DownloadManager downloadManager= (DownloadManager) TeaPunchActivity.this.getSystemService(Context.DOWNLOAD_SERVICE);
                                //将下载任务加入下载队列，否则不会进行下载
                                downloadManager.enqueue(request);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(TeaPunchActivity.this,"开始下载出席表请在",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).start();

                        Intent intent=new Intent(TeaPunchActivity.this,TeaMainActivity.class);
                        startActivity(intent);
                    }
                }).start();
            }
        });


    }

    private void initView() {
        tea_punch_status=findViewById(R.id.tea_punch_status);
        tea_status_start=findViewById(R.id.tea_status_start);
        tea_status_end=findViewById(R.id.tea_status_end);
        application=(MyApplication) getApplication();
        time=new Time();
        http=new ForHttp();
    }
}
