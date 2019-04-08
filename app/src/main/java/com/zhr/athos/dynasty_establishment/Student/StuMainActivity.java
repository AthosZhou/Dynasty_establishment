package com.zhr.athos.dynasty_establishment.Student;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.netease.nim.uikit.impl.NimUIKitImpl;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.AuthService;
import com.zhr.athos.dynasty_establishment.Adapter.StuhkAlAdapter;
import com.zhr.athos.dynasty_establishment.AddFriendActivity;
import com.zhr.athos.dynasty_establishment.MyApplication;
import com.zhr.athos.dynasty_establishment.R;
import com.zhr.athos.dynasty_establishment.Util.GetPath;
import com.zhr.athos.dynasty_establishment.Util.hideNavKey;
import com.zhr.athos.dynasty_establishment.fragment.ChatFragment;
import com.zhr.athos.dynasty_establishment.fragment.ClassroomFragment;
import com.zhr.athos.dynasty_establishment.fragment.ContactsFragment;
import com.zhr.athos.dynasty_establishment.fragment.VideoFragment;

import java.io.File;

public class StuMainActivity extends FragmentActivity {


    private RadioGroup stu_gp;
    private ChatFragment recentContactsFragment;
    private ContactsFragment contactsFragment;
    private VideoFragment videofragment;
    private ClassroomFragment classroomFragment;
    private Button stu_logout,stu_punch,stu_joinclass,stu_find_hk,stu_find_hk_al;
    private TextView stu_title,stu_add,stu_accid;
    private MyApplication myApplication;
    private Fragment currentFragment=null;
    //private FragmentManager manager;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        setContentView(R.layout.activity_stu_main);
        hideNavKey key=new hideNavKey();
        key.hideNavKey(StuMainActivity.this);
        initView();
        initFragment();
        initListener();
    }

    private void initFragment() {
        //manager=getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putInt("tag",1);
        recentContactsFragment=new ChatFragment();
        recentContactsFragment.setArguments(bundle);

        bundle=new Bundle();
        bundle.putInt("tag",2);
        contactsFragment=new ContactsFragment();
        contactsFragment.setArguments(bundle);

        bundle=new Bundle();
        bundle.putInt("tag",3);
        videofragment=new VideoFragment();
        videofragment.setArguments(bundle);
        classroomFragment=new ClassroomFragment();
        currentFragment=new Fragment();
        switchFragment(recentContactsFragment);


    }

    private void initView() {
        myApplication=(MyApplication) getApplication();
        stu_gp=findViewById(R.id.stu_gp);
        stu_logout=findViewById(R.id.stu_Logout);
        stu_add=findViewById(R.id.stu_add);
        stu_find_hk_al=findViewById(R.id.stu_find_hk_al);
        stu_find_hk=findViewById(R.id.stu_find_hk);
        stu_title=findViewById(R.id.stu_title);
        stu_accid=findViewById(R.id.stu_accid);
        stu_punch=findViewById(R.id.stu_punch);
        stu_joinclass=findViewById(R.id.stu_joinclass);
        stu_accid.setText(myApplication.getIMNum());

    }



    private void initListener() {
        stu_gp.check(R.id.stu_chat);
        stu_gp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.stu_chat:
                        switchFragment(recentContactsFragment);
                        stu_title.setText("消息");
                        stu_add.setVisibility(View.VISIBLE);
                        break;
                    case R.id.stu_contact:
                        switchFragment(contactsFragment);
                        stu_title.setText("联系人");
                        stu_add.setVisibility(View.GONE);
                        break;
                    case R.id.stu_video:
                        switchFragment(videofragment);
                        stu_title.setText("视频");
                        stu_add.setVisibility(View.GONE);
                        break;
                    case R.id.stu_classroom:
                        switchFragment(classroomFragment);
                        stu_title.setText("微课堂");
                        stu_add.setVisibility(View.GONE);
                }
            }
        });

        stu_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StuMainActivity.this, AddFriendActivity.class);
                startActivity(intent);
            }
        });

        stu_joinclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StuMainActivity.this,StuJoinClassActivity.class);
                startActivity(intent);
            }
        });

        stu_find_hk_al.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StuMainActivity.this,StuHkAlActivity.class);
                startActivity(intent);
            }
        });


        stu_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NIMClient.getService(AuthService.class).logout();
                NimUIKitImpl.logout();
                myApplication.setIMNum("");
                Intent intent=new Intent(StuMainActivity.this,StuLoginActivity.class);
                startActivity(intent);

            }
        });

        stu_find_hk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StuMainActivity.this,StuhkActivity.class);
                startActivity(intent);
            }
        });


        stu_punch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StuMainActivity.this,StuPunchActivity.class);
                startActivity(intent);
            }
        });
    }

    private void switchFragment(Fragment fragment) {
        if (fragment != currentFragment) {
            if (!fragment.isAdded()) {
                getSupportFragmentManager().beginTransaction().hide(currentFragment)
                        .add(R.id.stu_FL, fragment).commit();
            } else {
                getSupportFragmentManager().beginTransaction().hide(currentFragment)
                        .show(fragment).commit();
            }
            currentFragment = fragment;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideNavKey key=new hideNavKey();
        key.hideNavKey(StuMainActivity.this);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1024) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                if (uri != null) {
                    GetPath getPath=new GetPath();
                    String path = getPath.getPath(this, uri);
                    if (path != null) {
                        File file = new File(path);
                        if (file.exists()) {
                            final String upLoadFilePath = file.toString();
                            final String upLoadFileName = file.getName();
                            Log.i("上传路径",upLoadFilePath);
                            Log.i("上传文件名",upLoadFilePath);

                        }
                    }
                }
            }
        }

    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
