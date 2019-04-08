package com.zhr.athos.dynasty_establishment.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhr.athos.dynasty_establishment.Bean.stuhk;
import com.zhr.athos.dynasty_establishment.R;

import java.util.List;

public class StuhkAlAdapter extends ArrayAdapter{
    private final int resourceId;
    private StuhkAdapter.addClickListerner1 listener;
    public StuhkAlAdapter(Context context, int textViewResourceId, List<stuhk.YesBean> object1) {
        super(context, textViewResourceId, object1);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final stuhk.YesBean co=(stuhk.YesBean)getItem(position);
        convertView= LayoutInflater.from(getContext()).inflate(resourceId, null);
        LinearLayout stu_hk_item;
        TextView stu_hk_target,stu_hk_info,stu_hk_state;
        stu_hk_target=convertView.findViewById(R.id.stu_hk_target);
        stu_hk_info=convertView.findViewById(R.id.stu_hk_info);
        stu_hk_state=convertView.findViewById(R.id.stu_hk_state);
        stu_hk_item=convertView.findViewById(R.id.stu_hk_item);
        if(co.getChapter()==1)
        {
            stu_hk_target.setText("函数、极限、连续");
        }
        if(co.getChapter()==2)
        {
            stu_hk_target.setText("一元函数微分学");
        }
        if(co.getChapter()==3)
        {
            stu_hk_target.setText("一元函数积分学");
        }
        if(co.getChapter()==4)
        {
            stu_hk_target.setText("常微分方程");
        }
        if(co.getChapter()==5)
        {
            stu_hk_target.setText("多元函数微分学");
        }
        if(co.getChapter()==6)
        {
            stu_hk_target.setText("多元函数积分学");
        }
        stu_hk_info.setText(co.getTeaNo()+"发布于"+co.getStartdate().substring(0,10));
        stu_hk_state.setText("已完成");
        stu_hk_state.setTextColor(Color.GREEN);

        stu_hk_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!= null) {
                    listener.addClick1(co.getTeaNo(),co.getStartdate().substring(0,10),co.getChapter(),position);
                }
            }
        });

        return convertView;
    }

    public static interface addClickListerner1{

        public void addClick1(int TeaNo,String date,int chapter, int postion);  //自行配置参数  需要传递到activity的值

    }

    public void setCusClickListener(StuhkAdapter.addClickListerner1 cusClickListener) {
        this.listener = cusClickListener;
    }
}
