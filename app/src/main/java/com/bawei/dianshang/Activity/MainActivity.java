package com.bawei.dianshang.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.bawei.dianshang.R;

public class MainActivity extends AppCompatActivity {
   private int Count=3;
  private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

                 startActivity(new Intent(MainActivity.this, Main2Activity.class));


        }
    };
    private TextView tv_tiaoguo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        initView();
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(3000);
                    handler.sendEmptyMessage(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        tv_tiaoguo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
            }
        });


    }

    private void initView() {
        tv_tiaoguo = (TextView) findViewById(R.id.tv_tiaoguo);
    }
}
