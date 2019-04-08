package com.zhr.athos.dynasty_establishment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zhr.athos.dynasty_establishment.Student.StuLoginActivity;
import com.zhr.athos.dynasty_establishment.Teacher.TeaLoginActivity;
import com.zhr.athos.dynasty_establishment.Util.hideNavKey;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideNavKey key=new hideNavKey();
        key.hideNavKey(MainActivity.this);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.chos_tea)
    void teaLogin()
    {
        Intent intent1=new Intent(this,MyService.class);
        startService(intent1);
        Intent intent=new Intent(MainActivity.this, TeaLoginActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.chos_stu)
    void stuLogin()
    {
        Intent intent1=new Intent(this,MyService.class);
        startService(intent1);
        Intent intent=new Intent(MainActivity.this, StuLoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
