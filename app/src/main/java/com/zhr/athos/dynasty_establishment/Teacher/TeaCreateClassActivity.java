package com.zhr.athos.dynasty_establishment.Teacher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zhr.athos.dynasty_establishment.MyApplication;
import com.zhr.athos.dynasty_establishment.R;
import com.zhr.athos.dynasty_establishment.Util.ForHttp;

public class TeaCreateClassActivity extends AppCompatActivity {

    private Button add;
    private EditText ClassName;
    public MyApplication myApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tea_create_class);
        initview();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                创建班级
                url=http://47.106.38.118:2333/api/tea/addclass?TeaNo=1111&ClassName=test
                 */
                final String ClassN=ClassName.getText().toString().toLowerCase();
                final String TeaNo=myApplication.getIMNum();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ForHttp http=new ForHttp();
                        String url="http://47.106.38.118:2333/api/tea/addclass?TeaNo="+TeaNo+"&ClassName="+ClassN;
                        final int ask=Integer.parseInt(http.ForHttp(url));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(ask==1)
                                {
                                    Toast.makeText(TeaCreateClassActivity.this,"创建成功",Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(TeaCreateClassActivity.this,"创建失败，班级名已存在",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                }).start();

            }
        });
    }

    private void initview() {
        add=findViewById(R.id.add_class);
        ClassName=findViewById(R.id.add_class_name);
        myApplication=(MyApplication) getApplication();
    }
}
