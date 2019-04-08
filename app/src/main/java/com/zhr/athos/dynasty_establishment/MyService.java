package com.zhr.athos.dynasty_establishment;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;

public class MyService extends android.app.Service {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("service","service create");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("service","service start");
        try {
            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource("http://athos.oss-cn-shanghai.aliyuncs.com/%E6%8A%BC%E5%B0%BE%E3%82%B3%E3%83%BC%E3%82%BF%E3%83%AD%E3%83%BC%2CDEPAPEPE%20-%20START.mp3");
            mediaPlayer.prepare();
            mediaPlayer.start();
        }
        catch (IOException e)
        {}

        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("service","service destroy");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("service","service bind");
        return null;
    }
}
