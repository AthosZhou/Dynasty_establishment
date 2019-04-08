package com.zhr.athos.dynasty_establishment.fragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.google.gson.Gson;
import com.zhr.athos.dynasty_establishment.Adapter.VideoListAdapter;
import com.zhr.athos.dynasty_establishment.Bean.VideoInfo;
import com.zhr.athos.dynasty_establishment.MyApplication;
import com.zhr.athos.dynasty_establishment.R;
import com.zhr.athos.dynasty_establishment.Util.ForHttp;
import com.zhr.athos.dynasty_establishment.Util.GetPath;
import com.zhr.athos.dynasty_establishment.Util.OssService;
import com.zhr.athos.dynasty_establishment.Util.Time;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class ClassroomFragment extends Fragment{


    private MyApplication myApplication;
    private ListView video_list;
    private Handler handler;
    private SwipeRefreshLayout smksm;
    private VideoListAdapter videoListAdapter;
    private List<VideoInfo.VideoInfoBean> videoinfo=new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=View.inflate(getActivity(), R.layout.classroomfragment,null);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
                            /*
        获得数据进行捆绑
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url="http://47.106.38.118:2333/api/Tools/VideoInfo_T";
                ForHttp forHttp=new ForHttp();
                Message msg = new Message();
                final String ask=forHttp.ForHttp(url);
                Log.i("获得的数据源",ask);
                msg.what = 1;
                msg.obj = ask;
                handler.sendMessage(msg);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson=new Gson();
                        videoListAdapter=new VideoListAdapter(getActivity().getApplicationContext(),R.layout.videoitem,gson.fromJson(ask,VideoInfo.class).getVideoInfo());
                        video_list.setAdapter(videoListAdapter);
                    }
                });
            }
        }).start();
        handler=new Handler() {
            @Override
            public void handleMessage(android.os.Message msg) {
                if (msg.what == 1) {
                    Gson gson1=new Gson();
                    List<VideoInfo.VideoInfoBean> token=gson1.fromJson(msg.obj.toString(),VideoInfo.class).getVideoInfo();
                    videoinfo=token;
                } else {
                }
            }
        };

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        smksm.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String url="http://47.106.38.118:2333/api/Tools/VideoInfo_T";
                        ForHttp forHttp=new ForHttp();
                        final String ask=forHttp.ForHttp(url);
                        Gson gson=new Gson();
                        videoinfo=gson.fromJson(ask,VideoInfo.class).getVideoInfo();
                    }
                }).start();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        videoListAdapter=new VideoListAdapter(getActivity().getApplicationContext(),R.layout.videoitem,videoinfo);
                        video_list.setAdapter(videoListAdapter);
                        smksm.setRefreshing(false);
                    }
                }, 2000);
            }
        });

    }


    private void initView() {
        myApplication=(MyApplication)getActivity().getApplication();
        video_list=getActivity().findViewById(R.id.classvideolist);
        smksm=getActivity().findViewById(R.id.smksm);
    }



}
