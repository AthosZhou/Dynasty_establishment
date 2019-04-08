package com.zhr.athos.dynasty_establishment;

import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhr.athos.dynasty_establishment.Bean.Msg;
import com.zhr.athos.dynasty_establishment.Util.Test;

public class AddFriendActivity extends AppCompatActivity {

    private EditText add_num;
    private Button add_friend;
    private MyApplication myApplication;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        myApplication=(MyApplication)getApplication();
        Log.i("获得的accid",myApplication.getIMNum());
        initView();
        initListener();
    }

    private void initListener() {
        add_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Test test=new Test();
                        String num=add_num.getText().toString();
                        Log.i("获得的accid",myApplication.getIMNum());
                        String accid=myApplication.getIMNum();
                        Gson gson=new Gson();
                        try {
                            Msg msg=gson.fromJson(test.addFriend(accid,num),Msg.class);
                            Log.i("获得的提示",msg.getDesc());
                            if(msg.getCode()==200)
                            {
                                Looper.prepare();
                                Toast.makeText(AddFriendActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }
                            if(msg.getCode()==414)
                            {
                                Looper.prepare();
                                Toast.makeText(AddFriendActivity.this,"没有该对象",Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }
                            if(msg.getCode()==416)
                            {
                                Looper.prepare();
                                Toast.makeText(AddFriendActivity.this,"输入过于频繁",Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }
                            if(msg.getCode()==431)
                            {
                                Looper.prepare();
                                Toast.makeText(AddFriendActivity.this,"已是好友",Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }
                            if(msg.getCode()==500)
                            {
                                Looper.prepare();
                                Toast.makeText(AddFriendActivity.this,"服务器错误",Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }

                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });
    }




    private void initView() {
        add_num=findViewById(R.id.add_num);
        add_friend=findViewById(R.id.add_friend);

    }
}
