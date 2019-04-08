package com.zhr.athos.dynasty_establishment.Teacher;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhr.athos.dynasty_establishment.Bean.Msg;
import com.zhr.athos.dynasty_establishment.R;
import com.zhr.athos.dynasty_establishment.Util.ForHttp;
import com.zhr.athos.dynasty_establishment.Util.Test;
import com.zhr.athos.dynasty_establishment.Util.hideNavKey;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class TeaRegisterActivity extends AppCompatActivity {

    private EventHandler eventHandler;

    @BindView(R.id.Register_TeaNo)
    public EditText TeaNo;
    @BindView(R.id.Register_TeaName)
    public EditText TeaName;
    @BindView(R.id.Register_TeaTel)
    public EditText TeaTel;
    @BindView(R.id.Register_TeaPwd)
    public EditText TeaPwd;
    @BindView(R.id.Register_TeaCode)
    public EditText TeaCode;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tea_register);
        ButterKnife.bind(this);

        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                // afterEvent会在子线程被调用，因此如果后续有UI相关操作，需要将数据发送到UI线程
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                new Handler(Looper.getMainLooper(), new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        int event = msg.arg1;
                        int result = msg.arg2;
                        Object data = msg.obj;
                        if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                            if (result == SMSSDK.RESULT_COMPLETE) {
                                // TODO 处理成功得到验证码的结果
                                // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                                Log.i("SMSFS","成功发送");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(TeaRegisterActivity.this,"成功发送",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                // TODO 处理错误的结果
                                ((Throwable) data).printStackTrace();
                                Log.i("SMSFS","发送失败");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(TeaRegisterActivity.this,"发送失败",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                            if (result == SMSSDK.RESULT_COMPLETE) {
                                // TODO 处理验证码验证通过的结果
                                Log.i("SMSYZ","成功验证");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(TeaRegisterActivity.this,"成功验证",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                // TODO 处理错误的结果
                                ((Throwable) data).printStackTrace();
                                Log.i("SMSYZ","验证失败");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(TeaRegisterActivity.this,"验证失败",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                        // TODO 其他接口的返回结果也类似，根据event判断当前数据属于哪个接口
                        return false;
                    }
                }).sendMessage(msg);
            }
        };

        SMSSDK.registerEventHandler(eventHandler);
    }

    @OnClick(R.id.Register_TeaVerify)
    void GetVerifyCode()
    {
        //获取验证码
        String Tel=TeaTel.getText().toString().trim();
        SMSSDK.getVerificationCode("86",Tel);
    }

    @OnClick(R.id.Register_TeaSubmit)
    void Register()
    {
        final String No=TeaNo.getText().toString().trim();
        final String Name=TeaName.getText().toString().trim();
        final String Tel=TeaTel.getText().toString().trim();
        final String Pwd=TeaPwd.getText().toString().trim();
        final String Code=TeaCode.getText().toString().trim();
        //验证验证码、注册信息的准确性可靠性
        //手机号验证
        SMSSDK.submitVerificationCode("86",Tel,Code);
        //注册信息验证
        new Thread(new Runnable() {
            @Override
            public void run() {
                String Ink="http://47.106.38.118:2333/api/tea/Verify?TeaNo="+No+"&TeaName="+Name;
                try {
                    ForHttp forHttp=new ForHttp();

                    if(Integer.parseInt(forHttp.ForHttp(Ink))==0)
                    {
                        /*未注册过的正确信息 进行注册并更新原来学生表的信息 分为以下操作：
                        1、请求网易云信的ID并获得token
                        2、添加数据库中的表的信息
                        3、更新注册所依据的信息表（置1操作）
                         */
                        Test test=new Test();
                        try {
                            Gson gson=new Gson();
                            Msg msg=gson.fromJson(test.Verify(No,Name),Msg.class);
                            //这就是将要添加入个人表中的token
                            String token=msg.getInfo().getToken();
                            String Ink_Re="http://47.106.38.118:2333/api/tea/Register?" +
                                    "TeaNo=" +No+
                                    "&TeaPwd=" +Pwd+
                                    "&TeaTel=" +Tel+
                                    "&Token="+token;
                            if(Integer.parseInt(forHttp.ForHttp(Ink_Re))==1)
                            {
                                //成功插入用户数据
                                String Ink_Up="http://47.106.38.118:2333/api/tea/UpReNo?TeaNo="+No;

                                if(Integer.parseInt(forHttp.ForHttp(Ink_Up))==1)
                                {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            Toast.makeText(TeaRegisterActivity.this,"注册成功！(*^_^*)",Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(TeaRegisterActivity.this,TeaLoginActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                                }
                            }


                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                    }
                    else
                    {
                        //有可能注册过或信息不正确
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(TeaRegisterActivity.this,"可能注册过或注册信息不正确!",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                finally {
                    finish();
                }
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(TeaRegisterActivity.this,TeaLoginActivity.class);
        startActivity(intent);
    }





}
