package com.zhr.athos.dynasty_establishment.Adapter;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dl7.player.media.IjkVideoView;
import com.zhr.athos.dynasty_establishment.Bean.VideoInfo;
import com.zhr.athos.dynasty_establishment.MyApplication;
import com.zhr.athos.dynasty_establishment.R;
import com.zhr.athos.dynasty_establishment.Util.ForHttp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class VideoListAdapter extends ArrayAdapter {

    private MyApplication myApplication;

    private final int resourceId;
    private Context mContext;
    public VideoListAdapter(Context context, int textViewResourceId, List<VideoInfo.VideoInfoBean> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        mContext=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        VideoInfo.VideoInfoBean vi=(VideoInfo.VideoInfoBean) getItem(position);
        convertView= LayoutInflater.from(getContext()).inflate(resourceId, null);
        myApplication=(MyApplication) mContext;
        final String upno=String.valueOf(vi.getTeaNo());
        final String videoname=vi.getVideoName();
        final String uptime=vi.getUpTime().substring(0,10);
        final String loveno=myApplication.getIMNum();
        final int numsk=vi.getVideoSK();

        TextView up_name,up_info;
        final TextView subnum;
        final IjkVideoView videoplayer_view;
        CheckBox video_status;
        final CheckBox up_up;
        up_name=convertView.findViewById(R.id.up_name);
        up_info=convertView.findViewById(R.id.up_info);
        subnum=convertView.findViewById(R.id.subnum);
        videoplayer_view=convertView.findViewById(R.id.videoplayer_view);
        video_status=convertView.findViewById(R.id.video_status);
        up_up=convertView.findViewById(R.id.up_up);
        up_name.setText(vi.getVideoName());
        up_info.setText(vi.getTeaName()+"发布于"+vi.getUpTime().substring(0,10));
        subnum.setText(String.valueOf(vi.getVideoSK()));
        Uri uri=Uri.parse("http://mathic.oss-cn-shanghai.aliyuncs.com/"+vi.getTeaNo()+"_"+vi.getVideoName()+".mp4");
        videoplayer_view.setVideoURI(uri);
        video_status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    videoplayer_view.start();
                }
                else
                {
                    videoplayer_view.pause();
                }
            }
        });

        final Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==0)
                {
                    if(Integer.parseInt(msg.obj.toString())==1)
                    {
                        up_up.setChecked(true);

                    }
                    else
                    {
                        up_up.setChecked(false);

                    }
                }


            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                String url="http://47.106.38.118:2333/api/Tools/IsLove?UpNo="+upno+"&VideoName="+videoname+"&UpTime="+uptime+"&LoveNo="+loveno;
                ForHttp forHttp=new ForHttp();
                Message msg=new Message();
                msg.what=1;
                msg.obj=forHttp.ForHttp(url);
                handler.sendMessage(msg);
            }
        }).start();

        up_up.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true)
                {
                    int k=Integer.valueOf(subnum.getText().toString());
                    subnum.setText(String.valueOf(k+1));
                    //选中点赞
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String url="http://47.106.38.118:2333/api/Tools/Love_video?UpNo="+upno+"&VideoName="+videoname+"&UpTime="+uptime+"&LoveNo="+loveno;
                            ForHttp forHttp=new ForHttp();
                            int k=Integer.parseInt(forHttp.ForHttp(url));
                            if (k==1)
                            {
                                Log.i("点赞","+1");
                            }
                            if (k==15)
                            {
                                Log.i("点赞","+15");
                            }
                        }
                    }).start();
                }
                if(isChecked==false)
                {
                    int k=Integer.valueOf(subnum.getText().toString());
                    subnum.setText(String.valueOf(k-1));
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String url="http://47.106.38.118:2333/api/Tools/CanLove_video?UpNo="+upno+"&VideoName="+videoname+"&UpTime="+uptime+"&LoveNo="+loveno;
                            ForHttp forHttp=new ForHttp();
                            int k=Integer.parseInt(forHttp.ForHttp(url));
                            if (k==1)
                            {
                                Log.i("点赞","-1");
                            }
                            if (k==15)
                            {
                                Log.i("点赞","-15");
                            }
                        }
                    }).start();
                }
            }
        });
        return convertView;
    }

}
