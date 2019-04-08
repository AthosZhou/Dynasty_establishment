package com.zhr.athos.dynasty_establishment;

import android.app.IntentService;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;

import java.io.IOException;

public class MyIntentService extends IntentService {
    public MyIntentService(String name) throws IOException {
        super(name);
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource("http://athos.oss-cn-shanghai.aliyuncs.com/%E9%9F%A9%E5%9B%BD%E5%8E%9F%E5%A3%B0%E5%B8%A6-%EC%9A%B0%20%EA%B2%80%EC%82%AC.mp3");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        /*

        */
    }
}
