package com.zhr.athos.dynasty_establishment.Student;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zhr.athos.dynasty_establishment.MyApplication;
import com.zhr.athos.dynasty_establishment.R;
import com.zhr.athos.dynasty_establishment.Teacher.TeaCreateClassActivity;
import com.zhr.athos.dynasty_establishment.Util.ForHttp;

public class StuJoinClassActivity extends AppCompatActivity {

    private Button join;
    private EditText name;
    public MyApplication myApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_join_class);
        initview();
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ClassN=name.getText().toString().toLowerCase();
                final String StuNo=myApplication.getIMNum();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ForHttp http=new ForHttp();
                        String url="http://47.106.38.118:2333/api/stu/join_class?StuNo="+StuNo+"&ClassName="+ClassN;
                        final int ask=Integer.parseInt(http.ForHttp(url));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(ask==1)
                                {
                                    Toast.makeText(StuJoinClassActivity.this,"加入成功",Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(StuJoinClassActivity.this,StuMainActivity.class);
                                    startActivity(intent);
                                }
                                else
                                {
                                    Toast.makeText(StuJoinClassActivity.this,"加入失败，已经加入过或班级不存在",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                }).start();
            }
        });
    }

    private void initview() {
        join=findViewById(R.id.stu_join);
        name=findViewById(R.id.stu_join_name);
        myApplication=(MyApplication) getApplication();
    }
}
