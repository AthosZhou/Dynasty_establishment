package com.zhr.athos.dynasty_establishment.Teacher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zhr.athos.dynasty_establishment.R;

public class TeaExamManageActivity extends AppCompatActivity {
    private Button add_hk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tea_exam_manage);
        add_hk=findViewById(R.id.add_hk);
        add_hk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TeaExamManageActivity.this,ChooseTargetActivity.class);
                startActivity(intent);
            }
        });
    }
}
