package com.zhr.athos.dynasty_establishment.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.netease.nim.uikit.api.NimUIKit;
import com.zhr.athos.dynasty_establishment.Bean.Contact;
import com.zhr.athos.dynasty_establishment.Bean.VideoInfo;
import com.zhr.athos.dynasty_establishment.R;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends ArrayAdapter{

    private final int resourceId;
    public ContactAdapter(Context context, int textViewResourceId, List<Contact.ContactBean> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Contact.ContactBean co=(Contact.ContactBean)getItem(position);
        convertView= LayoutInflater.from(getContext()).inflate(resourceId, null);
        LinearLayout talk;
        TextView stu_item_name;
        talk=convertView.findViewById(R.id.stu_item_talk);
        stu_item_name=convertView.findViewById(R.id.stu_item_name);
        stu_item_name.setText(co.getNick());
        talk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NimUIKit.startP2PSession(getContext(), String.valueOf(co.getNum()));
            }
        });
        return convertView;
    }
}
