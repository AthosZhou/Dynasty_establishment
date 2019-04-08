package com.zhr.athos.dynasty_establishment.Student.Exam;

import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zhr.athos.dynasty_establishment.R;
import com.zhr.athos.dynasty_establishment.Util.NormalLoadPictrue;

import java.util.ArrayList;
import java.util.List;

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<CardItem> mData;
    private float mBaseElevation;
    private addClickListerner listener;

    public CardPagerAdapter() {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
    }

    public void addCardItem(CardItem item) {
        mViews.add(null);
        mData.add(item);
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate( R.layout.adapter, container, false);
        container.addView(view);
        bind(mData.get(position), view);

        CardView cardView = (CardView) view.findViewById(R.id.cardView);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(final CardItem item, final View view) {
        TextView titleTextView = (TextView) view.findViewById(R.id.titleTextView);
        TextView contentTextView = (TextView) view.findViewById(R.id.contentTextView);
        final RadioGroup asw=(RadioGroup)view.findViewById(R.id.Asw);
        asw.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id1=asw.getCheckedRadioButtonId();
                RadioButton sk=view.findViewById(id1);
                int num= Integer.valueOf(item.getText().toString())-1;
                if(listener!= null) {
                    listener.addClick(sk.getText().toString(),num);
                }
            }
        });
        ImageView contentImg=(ImageView) view.findViewById(R.id.contentImg);
        new NormalLoadPictrue().getPicture(item.getMurl(),contentImg);
        titleTextView.setText(item.getTitle());
        contentTextView.setText(item.getText());
        if(item.getFlag().equals("1"))
        {
            asw.setVisibility(View.GONE);
            contentTextView.setVisibility(View.VISIBLE);
        }

    }


    public static interface addClickListerner{

        public void addClick(String ask, int postion);  //自行配置参数  需要传递到activity的值

    }

    public void setCusClickListener(addClickListerner cusClickListener) {
        this.listener = cusClickListener;
    }
}
