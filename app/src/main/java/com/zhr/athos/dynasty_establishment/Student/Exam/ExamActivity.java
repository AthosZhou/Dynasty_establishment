package com.zhr.athos.dynasty_establishment.Student.Exam;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhr.athos.dynasty_establishment.Adapter.StuhkAlAdapter;
import com.zhr.athos.dynasty_establishment.Bean.stuhk;
import com.zhr.athos.dynasty_establishment.Bean.subject;
import com.zhr.athos.dynasty_establishment.MyApplication;
import com.zhr.athos.dynasty_establishment.R;
import com.zhr.athos.dynasty_establishment.Student.StuHkAlActivity;
import com.zhr.athos.dynasty_establishment.Student.StuMainActivity;
import com.zhr.athos.dynasty_establishment.Student.StuhkActivity;
import com.zhr.athos.dynasty_establishment.Util.ForHttp;
import com.zhr.athos.dynasty_establishment.Util.dot;

import java.util.List;

public class ExamActivity extends AppCompatActivity implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener {

    private ViewPager mViewPager;
    private Button sub,see;
    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private CardFragmentPagerAdapter mFragmentCardAdapter;
    private ShadowTransformer mFragmentCardShadowTransformer;
    private boolean mShowingFragments = false;
    private String[] Answear;
    private Handler handler;
    private int[] sks=new int[5];
    private MyApplication myApplication;
    private String cp,TN,Time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        myApplication=(MyApplication) getApplication();
        final Intent intent=getIntent();
        final String TeaNo=intent.getStringExtra("TeaNo");
        final String date=intent.getStringExtra("date");
        final String chapter=intent.getStringExtra("chapter");
        final String point=intent.getStringExtra("point");
        cp=chapter;
        TN=TeaNo;
        Time=chapter;
        sub=(Button) findViewById(R.id.sub);
        see=(Button) findViewById(R.id.see);
        if(point.equals("1"))
        {
            sub.setVisibility(View.GONE);
            see.setVisibility(View.VISIBLE);
        }
        Log.i("获得的信息",TeaNo+date+chapter);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        new Thread(new Runnable() {
            @Override
            public void run() {
                String url="http://47.106.38.118:2333/api/stu/find_hk_detail?chapter="+chapter+"&TeaNo="+TeaNo+"&startdate="+date;
                ForHttp http=new ForHttp();
                String ask=http.ForHttp(url);
                Message message=new Message();
                Gson gson1=new Gson();
                sks[0]=gson1.fromJson(ask,subject.class).getSj().get(0).getNo_s_1();
                sks[1]=gson1.fromJson(ask,subject.class).getSj().get(0).getNo_s_2();
                sks[2]=gson1.fromJson(ask,subject.class).getSj().get(0).getNo_s_3();
                sks[3]=gson1.fromJson(ask,subject.class).getSj().get(0).getNo_s_4();
                sks[4]=gson1.fromJson(ask,subject.class).getSj().get(0).getNo_s_5();
                String url1="http://47.106.38.118:2333/api/stu/hk_answear?chapter="+cp+"&No_s_1="+String.valueOf(sks[0])+"&No_s_2="+String.valueOf(sks[1])+"&No_s_3="+String.valueOf(sks[2])+"&No_s_4="+String.valueOf(sks[3])+"&No_s_5="+String.valueOf(sks[4]);
                String answear1=http.ForHttp(url1);
                dot dot=new dot();
                final String[] realan=dot.getindex(answear1);
                message.what=2;
                message.obj=ask;
                handler.sendMessage(message);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("你拿到了吗？",String.valueOf(sks[0]));
                        Answear=new String[]{"E","E","E","E","E"};
                        mCardAdapter = new CardPagerAdapter();
                        for(int i=0;i<5;i++)
                        {
                            Log.i("你拿到了吗？",String.valueOf(sks[i]));
                            mCardAdapter.addCardItem(new CardItem(String.valueOf(i+1), realan[i],"http://mathic.oss-cn-shanghai.aliyuncs.com/"+chapter+"/"+String.valueOf(sks[i])+".png",point));
                            mCardAdapter.setCusClickListener(new CardPagerAdapter.addClickListerner() {
                                @Override
                                public void addClick(String ask,int postion) {
                                    Answear[postion]=ask;
                                }
                            });
                            mFragmentCardAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager(),
                                    dpToPixels(2, getApplicationContext()));

                            mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
                            mFragmentCardShadowTransformer = new ShadowTransformer(mViewPager, mFragmentCardAdapter);

                            mViewPager.setAdapter(mCardAdapter);
                            mViewPager.setPageTransformer(false, mCardShadowTransformer);
                            mViewPager.setOffscreenPageLimit(5);
                        }
                    }
                });
            }
        }).start();
        handler=new Handler() {
            @Override
            public void handleMessage(android.os.Message msg) {
                if (msg.what == 2) {
                    Gson gson1=new Gson();
                    sks[0]=gson1.fromJson(msg.obj.toString(),subject.class).getSj().get(0).getNo_s_1();
                    sks[1]=gson1.fromJson(msg.obj.toString(),subject.class).getSj().get(0).getNo_s_2();
                    sks[2]=gson1.fromJson(msg.obj.toString(),subject.class).getSj().get(0).getNo_s_3();
                    sks[3]=gson1.fromJson(msg.obj.toString(),subject.class).getSj().get(0).getNo_s_4();
                    sks[4]=gson1.fromJson(msg.obj.toString(),subject.class).getSj().get(0).getNo_s_5();
                } else {
                }
            }
        };

        sub.setOnClickListener(this);
        see.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.see)
        {
            Intent intent=new Intent(ExamActivity.this, StuHkAlActivity.class);
            startActivity(intent);
        }


        if (view.getId()==R.id.sub)
        {
            boolean flag=false;
            //获取答案并对比
            for(int q=0;q<5;q++)
            {
                if(Answear[q].equals("E"))
                flag=true;
            }
            if(flag)
            {
                Toast.makeText(ExamActivity.this,"还没答完题就想走？都是送分题！",Toast.LENGTH_LONG).show();
            }
            else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ForHttp http1=new ForHttp();
                    String url1="http://47.106.38.118:2333/api/stu/hk_answear?chapter="+cp+"&No_s_1="+String.valueOf(sks[0])+"&No_s_2="+String.valueOf(sks[1])+"&No_s_3="+String.valueOf(sks[2])+"&No_s_4="+String.valueOf(sks[3])+"&No_s_5="+String.valueOf(sks[4]);
                    String answear=http1.ForHttp(url1);
                    Log.i("答案是",answear);
                    dot dot=new dot();
                    String[] realan=dot.getindex(answear);
                    int j=0;//计分数
                    int k=11111;//记对错
                    for(int i=0;i<5;i++)
                    {
                        if(Answear[i].equals(realan[i]))
                        {
                            j+=20;
                        }
                        else
                        {
                            k-=Math.pow(10,4-i);
                        }
                    }
                    final int QwQ=j;
                    String url2="http://47.106.38.118:2333/api/stu/ins_grade?chapter="+cp+"&TeaNo="+TN+"&startdate="+Time+"&suit="+k+"&grade="+j+"&StuNo="+myApplication.getIMNum();//填分数
                    http1.ForHttp(url2);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ExamActivity.this,"你的分数是"+String.valueOf(QwQ),Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(ExamActivity.this, StuMainActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }).start();}
        }
    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        mCardShadowTransformer.enableScaling(b);
        mFragmentCardShadowTransformer.enableScaling(b);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(ExamActivity.this,"考试中不能退出！",Toast.LENGTH_LONG).show();
    }


}