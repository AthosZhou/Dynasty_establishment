package com.zhr.athos.dynasty_establishment.Teacher;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.netease.nim.uikit.impl.NimUIKitImpl;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.zhr.athos.dynasty_establishment.MyApplication;
import com.zhr.athos.dynasty_establishment.R;
import com.zhr.athos.dynasty_establishment.Student.StuLoginActivity;
import com.zhr.athos.dynasty_establishment.Student.StuMainActivity;
import com.zhr.athos.dynasty_establishment.Student.StuRegisterActivity;
import com.zhr.athos.dynasty_establishment.Util.ForHttp;
import com.zhr.athos.dynasty_establishment.Util.hideNavKey;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TeaLoginActivity extends AppCompatActivity {

    private static Handler handler;
    public MyApplication myApplication;

    @BindView(R.id.tea_no)
    public EditText TeaNo;

    @BindView(R.id.tea_pwd)
    public EditText TeaPwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tea_login);
        hideNavKey key=new hideNavKey();
        key.hideNavKey(TeaLoginActivity.this);
        myApplication=(MyApplication)getApplication();
        ButterKnife.bind(this);
    }


    @OnClick(R.id.tea_go_reg)
    void go()
    {
        Intent intent=new Intent(TeaLoginActivity.this,TeaRegisterActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.tea_login)
    void hello()
    {
        TeaLoginActivity.MyThread myThread=new TeaLoginActivity.MyThread();
        myThread.start();

    }

    private void doLogin(String no, String token) {
        LoginInfo info = new LoginInfo(no,token); // config...
        RequestCallback<LoginInfo> callback =
                new RequestCallback<LoginInfo>() {
                    // 可以在此保存LoginInfo到本地，下次启动APP做自动登录用

                    @Override
                    public void onSuccess(LoginInfo loginInfo) {
                        Log.i("登陆成功","o(*￣▽￣*)ブ");
                        NimUIKitImpl.setAccount(loginInfo.getAccount());
                        myApplication.setIMNum(loginInfo.getAccount());
                        Intent intent=new Intent(TeaLoginActivity.this,TeaMainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailed(int i) {
                        Log.i("登陆失败","awsl");
                        Toast.makeText(TeaLoginActivity.this,"登录失败！≧ ﹏ ≦",Toast.LENGTH_SHORT).show();
                        TeaNo.setText("");
                        TeaPwd.setText("");
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        Log.d("报出的异常",throwable.toString());
                        TeaNo.setText("");
                    }
                };
        NIMClient.getService(AuthService.class).login(info)
                .setCallback(callback);
    }

    public class MyThread extends Thread
    {
        @Override
        public void run() {
            final String No=TeaNo.getText().toString().trim();
            final String Pwd=TeaPwd.getText().toString().trim();
            String Ink="http://47.106.38.118:2333/api/tea/Login?TeaNo="+No+"&TeaPwd="+Pwd;
            ForHttp forHttp=new ForHttp();
            final String token=forHttp.ForHttp(Ink).replace("\"", "");
            doLogin(No,token);
        }
    }
}
