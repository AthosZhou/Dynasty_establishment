package com.zhr.athos.dynasty_establishment.Teacher;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.netease.nim.uikit.impl.NimUIKitImpl;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.AuthService;
import com.zhr.athos.dynasty_establishment.AddFriendActivity;
import com.zhr.athos.dynasty_establishment.MyApplication;
import com.zhr.athos.dynasty_establishment.R;
import com.zhr.athos.dynasty_establishment.Student.StuLoginActivity;
import com.zhr.athos.dynasty_establishment.Student.StuMainActivity;
import com.zhr.athos.dynasty_establishment.Util.hideNavKey;
import com.zhr.athos.dynasty_establishment.fragment.ChatFragment;
import com.zhr.athos.dynasty_establishment.fragment.ClassroomFragment;
import com.zhr.athos.dynasty_establishment.fragment.ContactsFragment;
import com.zhr.athos.dynasty_establishment.fragment.VideoFragment;

public class TeaMainActivity extends AppCompatActivity {

    private RadioGroup tea_gp;
    private ChatFragment recentContactsFragment;
    private ContactsFragment contactsFragment;
    private VideoFragment videofragment;
    private ClassroomFragment classroomFragment;
    private Button tea_logout,tea_punch,tea_createclass,tea_exammanage;
    private TextView tea_title,tea_add,tea_accid;
    private MyApplication myApplication;
    private Fragment currentFragment=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        setContentView(R.layout.activity_tea_main);
        hideNavKey key=new hideNavKey();
        key.hideNavKey(TeaMainActivity.this);
        initView();
        initFragment();
        initListener();
    }

    private void initView() {
        myApplication=(MyApplication) getApplication();
        tea_gp=findViewById(R.id.tea_gp);
        tea_exammanage=findViewById(R.id.tea_exammanage);
        tea_logout=findViewById(R.id.tea_Logout);
        tea_add=findViewById(R.id.tea_add);
        tea_punch=findViewById(R.id.tea_punch);
        tea_title=findViewById(R.id.tea_title);
        tea_accid=findViewById(R.id.tea_accid);
        tea_createclass=findViewById(R.id.tea_createclass);
        recentContactsFragment=new ChatFragment();
        contactsFragment=new ContactsFragment();
        videofragment=new VideoFragment();
        classroomFragment=new ClassroomFragment();
        tea_accid.setText(myApplication.getIMNum());
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

        currentFragment=new Fragment();
        switchFragment(recentContactsFragment);


    }

    private void initListener() {
        tea_gp.check(R.id.tea_chat);
        tea_gp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.tea_chat:
                        switchFragment(recentContactsFragment);
                        tea_title.setText("消息");
                        tea_add.setVisibility(View.VISIBLE);
                        break;
                    case R.id.tea_contact:
                        switchFragment(contactsFragment);
                        tea_title.setText("联系人");
                        tea_add.setVisibility(View.GONE);
                        break;
                    case R.id.tea_video:
                        switchFragment(videofragment);
                        tea_title.setText("视频");
                        tea_add.setVisibility(View.GONE);
                        break;
                    case R.id.tea_classroom:
                        switchFragment(classroomFragment);
                        tea_title.setText("微课堂");
                        tea_add.setVisibility(View.GONE);
                        break;
                }
            }
        });

        tea_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TeaMainActivity.this, AddFriendActivity.class);
                startActivity(intent);
            }
        });

        tea_createclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TeaMainActivity.this,TeaCreateClassActivity.class);
                startActivity(intent);
            }
        });

        tea_exammanage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TeaMainActivity.this,ChooseTargetActivity.class);
                startActivity(intent);
            }
        });



        tea_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NIMClient.getService(AuthService.class).logout();
                NimUIKitImpl.logout();
                myApplication.setIMNum("");
                Intent intent=new Intent(TeaMainActivity.this,TeaLoginActivity.class);
                startActivity(intent);
            }
        });

        tea_punch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TeaMainActivity.this,TeaPunchActivity.class);
                startActivity(intent);
            }
        });
    }


    private void switchFragment(Fragment fragment) {
        if (fragment != currentFragment) {
            if (!fragment.isAdded()) {
                getSupportFragmentManager().beginTransaction().hide(currentFragment)
                        .add(R.id.tea_FL, fragment).commit();
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
        key.hideNavKey(TeaMainActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
