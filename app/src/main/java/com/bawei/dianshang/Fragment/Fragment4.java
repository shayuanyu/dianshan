package com.bawei.dianshang.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.dianshang.Activity.AddActivity;
import com.bawei.dianshang.Activity.DengluActivity;
import com.bawei.dianshang.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2017/10/8.
 */

public class Fragment4 extends Fragment implements View.OnClickListener {


    private ImageView iv_touxiang;
    private LinearLayout tv_denglu;
    private TextView tv_weidenglu;
    private SharedPreferences sp;
    private TextView tv_zhuxiao;
    private String phone;
    private String password;
    private boolean nihao;
    private TextView dingdan;
    private TextView mydizhi;
    private ImageView mainpage;
    private ImageView dengu;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment4, container, false);


        initView(view);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // EventBus.getDefault().register(this);
//        layout_1.setVisibility(View.VISIBLE);//这一句显示布局LinearLayout区域
//        layout_1.setVisibility(View.GONE)
        sp = getActivity().getSharedPreferences("userinfo", MODE_PRIVATE);
        phone = sp.getString("phone", "");
        password = sp.getString("password", "");

        dengu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), DengluActivity.class);
                startActivity(intent);
            }
        });
        mainpage.setScaleType(ImageView.ScaleType.FIT_XY);
        if (phone.equals("") && password.equals("")) {

            Intent intent = new Intent(getActivity(), DengluActivity.class);
            tv_zhuxiao.setVisibility(View.GONE);
            startActivity(intent);
        }
      else if (phone.equals(phone)&&password.equals(password)){
            tv_weidenglu.setText(phone);
            tv_zhuxiao.setVisibility(View.VISIBLE);
            tv_zhuxiao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        sp.edit().clear();
                    Toast.makeText(getActivity(), "注销成功", Toast.LENGTH_SHORT).show();

                }
            });

       }
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void helloEvent(beanEventBus beanEventBus) {
//        tv_weidenglu.setText(beanEventBus.getName());
//    }


    private void initView(View view) {


        tv_weidenglu = (TextView) view.findViewById(R.id.tv_weidenglu);
        tv_zhuxiao = (TextView) view.findViewById(R.id.tv_zhuxiao);
        mydizhi = (TextView) view.findViewById(R.id.mydizhi);
        mydizhi.setOnClickListener(this);
        mainpage = (ImageView) view.findViewById(R.id.mainpage);

        dengu = (ImageView) view.findViewById(R.id.dengu);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.mydizhi:
                //地址
                Intent intent = new Intent(getActivity(), AddActivity.class);
                startActivity(intent);
                break;
            case R.id.dengu:
                break;
        }
    }
    public void zhuxiao(){




    }
}
