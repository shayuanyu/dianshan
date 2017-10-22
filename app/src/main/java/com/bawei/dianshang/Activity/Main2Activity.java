package com.bawei.dianshang.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bawei.dianshang.Fragment.Fragment1;
import com.bawei.dianshang.Fragment.Fragment2;
import com.bawei.dianshang.Fragment.Fragment3;
import com.bawei.dianshang.Fragment.Fragment4;
import com.bawei.dianshang.R;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    private ViewPager viewpager;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private RadioGroup radiogroup;
    private SharedPreferences sp;
    private String phone;
    private String password;

    private List<Fragment> list = new ArrayList<>();
    private List<RadioButton> list2 = new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         getSupportActionBar().hide();
        setContentView(R.layout.activitin2);
        initView();
        sp = getSharedPreferences("userinfo", MODE_PRIVATE);
        phone = sp.getString("phone", "");
        password = sp.getString("password", "");
        Fragment1 fragment1 = new Fragment1();
        Fragment2 fragment2 = new Fragment2();
        Fragment3 fragment3 = new Fragment3();
        Fragment4 fragment4 = new Fragment4();

        list.add(fragment1);
        list.add(fragment2);
        list.add(fragment3);
        list.add(fragment4);

        list2.add(rb1);
        list2.add(rb2);
        list2.add(rb3);
        list2.add(rb4);

        viewpager.setCurrentItem(2);
        viewpager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });


        viewpager.setAdapter(new MFAdapter(getSupportFragmentManager(), list));
        viewpager.setCurrentItem(0);
        rb1.setChecked(true);

        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                for (int i = 0; i < list2.size(); i++) {
                    RadioButton button = list2.get(i);
                    if (button.isChecked()) {
                        viewpager.setCurrentItem(i);
                    }
                }


            }
        });

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                RadioButton radioButton = list2.get(position);


                for (int i = 0; i < list.size(); i++) {

                    if (i == position) {
                        radioButton.setChecked(true);
                    }

                }
                if (position==2){
                    emptiy();
                }else if (position==3){
                    emptiy();

                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initView() {
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        rb1 = (RadioButton) findViewById(R.id.rb1);
        rb2 = (RadioButton) findViewById(R.id.rb2);
        rb3 = (RadioButton) findViewById(R.id.rb3);
        rb4 = (RadioButton) findViewById(R.id.rb4);
        radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
    }
    public void emptiy(){
        if (phone.equals("")&&password.equals("")){
            Intent intent = new Intent(Main2Activity.this, DengluActivity.class);
            startActivity(intent);
        }
    }
}
