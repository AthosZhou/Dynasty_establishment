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

import okhttp3.OkHttpClient;

import static android.app.Activity.RESULT_OK;

public class VideoFragment extends Fragment {

    private Button video_upload;
    private ProgressBar uploading;
    private EditText upload_name;
    private MyApplication myApplication;
    private SwipeRefreshLayout refreskk;
    private ListView video_list;
    private Handler handler;
    private VideoListAdapter videoListAdapter;
    private List<VideoInfo.VideoInfoBean> videoinfo=new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=View.inflate(getActivity(), R.layout.videofragment,null);
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
                String url="http://47.106.38.118:2333/api/Tools/VideoInfo_S";
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
        initListener();


    }

    private void initListener() {
        video_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String uploadname=upload_name.getText().toString().trim();
                if (uploadname.length()==0 || uploadname=="")
                {
                    Toast.makeText(getContext(),"上传文件名不能为空！",Toast.LENGTH_LONG).show();
                }
                else
                {
                    PermissionsUtil.requestPermission(getActivity(), new PermissionListener() {
                                @Override
                                public void permissionGranted(@NonNull String[] permission) {
                                    chooseVideo();
                                }

                                @Override
                                public void permissionDenied(@NonNull String[] permission) {
                                    Toast.makeText(getContext(),"权限未获取",Toast.LENGTH_SHORT).show();
                                }
                            },
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
                }

            }
        });
        refreskk.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String url="http://47.106.38.118:2333/api/Tools/VideoInfo_S";
                        ForHttp forHttp=new ForHttp();
                        final String ask=forHttp.ForHttp(url);
                        Log.i("刷新获得数据",ask);
                        Gson gson=new Gson();
                        videoinfo=gson.fromJson(ask,VideoInfo.class).getVideoInfo();
                    }
                }).start();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        videoListAdapter=new VideoListAdapter(getActivity().getApplicationContext(),R.layout.videoitem,videoinfo);
                        video_list.setAdapter(videoListAdapter);
                        refreskk.setRefreshing(false);
                    }
                }, 2000);
            }
        });
    }

    private void initView() {
        video_upload=getActivity().findViewById(R.id.video_upload);
        uploading=getActivity().findViewById(R.id.uploading);
        upload_name=getActivity().findViewById(R.id.upload_name);
        myApplication=(MyApplication)getActivity().getApplication();
        video_list=getActivity().findViewById(R.id.Video_list);
        refreskk=getActivity().findViewById(R.id.refreskk);

    }

    private void chooseVideo(){
//        ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS);

        Intent intent =new Intent();
        /* 开启Pictures画面Type设定为image */
        //intent.setType("image/*");  
        //  intent.setType("audio/*"); //选择音频  
        intent.setType("video/*");
        // 选择视频 （mp4 3gp 是android支持的视频格式）  
        //  intent.setType("video/*;image/*");//同时选择视频和图片          
        /* 使用Intent.ACTION_GET_CONTENT这个Action */
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent,1024);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1024) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                if (uri != null) {
                    GetPath getPath=new GetPath();
                    String path = getPath.getPath(getContext(), uri);
                    if (path != null) {
                        File file = new File(path);
                        if (file.exists()) {
                            final String upLoadFilePath = file.toString();
                            Log.i("上传路径",upLoadFilePath);
                            //初始化OssService类，参数分别是Content，accessKeyId，accessKeySecret，endpoint，bucketName（后4个参数是您自己阿里云Oss中参数）
                            OssService ossService = new OssService(getContext(), "LTAI4aHTfcdo88rD", "dIrPz30ClVdGvTme5es5EiSFhnivz7", "oss-cn-shanghai.aliyuncs.com", "mathic");
//初始化OSSClient
                            ossService.initOSSClient();
//开始上传，参数分别为content，上传的文件名filename，上传的文件路径filePath
                            final String name=myApplication.getIMNum()+"_"+upload_name.getText().toString().trim()+".mp4";
                            Log.i("上传文件名",name);
                            ossService.beginupload(getContext(), name, upLoadFilePath);
                            ossService.setProgressCallback(new OssService.ProgressCallback() {
                                @Override
                                public void onProgressCallback(final double progress) {
                                    Log.i("上传进度",String.valueOf(progress));
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if(progress==100)
                                            {
                                                Toast.makeText(getContext(),"上传成功啦！(*^_^*)",Toast.LENGTH_LONG).show();
                                                final String upname=upload_name.getText().toString().toLowerCase();
                                                new Thread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        /*
                                                        插入上传信息:上传人，视频名称
                                                         */

                                                        Time time=new Time();
                                                        String url="http://47.106.38.118:2333/api/Tools/UpReNo?" +
                                                                "UpNo=" + myApplication.getIMNum()+
                                                                "&VideoName=" + upname+
                                                                "&UpTime=" +time.GetDate();
                                                        Log.i("上传信息",url);
                                                        ForHttp forHttp=new ForHttp();
                                                        forHttp.ForHttp(url);

                                                    }
                                                }).start();
                                                upload_name.setText("");
                                                upload_name.setVisibility(View.VISIBLE);
                                                video_upload.setVisibility(View.VISIBLE);
                                                uploading.setVisibility(View.GONE);
                                            }
                                            else
                                            {
                                                if(String.valueOf(progress)==null||String.valueOf(progress)=="")
                                                {
                                                    Toast.makeText(getContext(),"上传失败！X﹏X",Toast.LENGTH_SHORT).show();
                                                    upload_name.setVisibility(View.VISIBLE);
                                                    video_upload.setVisibility(View.VISIBLE);
                                                    uploading.setVisibility(View.GONE);
                                                }
                                                else
                                                {
                                                    video_upload.setVisibility(View.GONE);
                                                    upload_name.setVisibility(View.GONE);
                                                    uploading.setVisibility(View.VISIBLE);
                                                    uploading.setProgress((int)progress);
                                                }

                                            }
                                        }
                                    });


                                }
                            });

                        }
                    }
                }
            }
        }

    }

}
