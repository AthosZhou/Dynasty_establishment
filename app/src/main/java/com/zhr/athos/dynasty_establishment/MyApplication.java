package com.zhr.athos.dynasty_establishment;

import android.app.Application;
import android.content.Context;

import com.mob.MobSDK;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.util.NIMUtil;

public class MyApplication extends Application {

    public static Context context;

    public String IMNum;
    public String IMName;


    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        MyApplication.context = context;
    }

    public String getIMName() {
        return IMName;
    }

    public void setIMName(String IMName) {
        this.IMName = IMName;
    }

    public String getIMNum() {
        return IMNum;
    }

    public void setIMNum(String IMNum) {
        this.IMNum = IMNum;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setIMNum("0000000");

        NIMClient.init(this,null,null);
        MobSDK.init(this);
        if (NIMUtil.isMainProcess(this)) {
            // 在主进程中初始化UI组件，判断所属进程方法请参见demo源码。
            NimUIKit.init(this);
        }
    }
}
