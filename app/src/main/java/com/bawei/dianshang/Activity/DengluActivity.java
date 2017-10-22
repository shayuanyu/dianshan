package com.bawei.dianshang.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.dianshang.Bean.beandenglu;
import com.bawei.dianshang.R;
import com.bawei.dianshang.utils.GsonObjectCallback;
import com.bawei.dianshang.utils.OkHttp3Utils;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;

public class DengluActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView image_return;
    private EditText et_name;
    private EditText et_pwd;
    private TextView xinzhuce;
    private Button loging_but;
    private CheckBox myckeck;
    private SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denglu);
        sp = getSharedPreferences("userinfo", MODE_PRIVATE);

        initView();
    }

    private void initView() {
        image_return = (ImageView) findViewById(R.id.image_return);
        et_name = (EditText) findViewById(R.id.et_name);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        xinzhuce = (TextView) findViewById(R.id.xinzhuce);
        loging_but = (Button) findViewById(R.id.loging_but);

        image_return.setOnClickListener(this);
        xinzhuce.setOnClickListener(this);
        loging_but.setOnClickListener(this);
        myckeck = (CheckBox) findViewById(R.id.myckeck);
        //选中密码和用户名,并保存下来
        myckeck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    if (getEdittext(et_name)!=null&&getEdittext(et_pwd)!=null) {

                        sp.edit().putString("user",getEdittext(et_name)).commit();
                        sp.edit().putString("password",getEdittext(et_pwd)).commit();
                         finish();
                    }
                }
            }
        });
        getData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_return:
                finish();
                break;
            case R.id.xinzhuce:
                Intent intent = new Intent(DengluActivity.this, ZhuceActivity.class);

                startActivity(intent);
                break;
            case R.id.loging_but:
                  submit();

                break;

        }
    }

    //获取edit的值
    private String  getEdittext(EditText edit){

        String text = edit.getText().toString().trim();
        if (isNotnull(text)) {
            return text;
        }
        return null;

    }

    //登陆
    private void submit() {
        // validate

        if (TextUtils.isEmpty(getEdittext(et_name))) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(getEdittext(et_pwd))) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        // TODO valide success, do something
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("username",getEdittext(et_name));
        map.put("password", getEdittext(et_pwd));

       // map.put("password_confirm", querpwd);
      //  map.put("email", email);
        map.put("client", API.CLIENT);

        OkHttp3Utils.doPost(API.DENGLU,map, new GsonObjectCallback<beandenglu>() {

            private String key;

            @Override
            public void onUi(beandenglu beandenglu) {
                    if (beandenglu.getCode()==200){
                  //      if (NNihao) {
                            Toast.makeText(DengluActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                            key =  beandenglu.getDatas().getKey();
                            sp.edit().putString("phone", getEdittext(et_name)).commit();
                            sp.edit().putString("password", getEdittext(et_pwd)).commit();
                            sp.edit().putString("key",key).commit();
//                             NNihao = sp.getBoolean("NNihao", true);
                        //关闭页面
                        //    EventBus.getDefault().post(new beanEventBus(getEdittext(et_name)));
                            DengluActivity.this.finish();

                    //    }
                    }else if (beandenglu.getCode()==400){
                       Toast.makeText(DengluActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
                    }
            }

            @Override
            public void onFailed(Call call, IOException e) {

            }
        });





    }

    //获取用户名和密码
    public void getData(){

        String phone = sp.getString("phone","");
        String password = sp.getString("password","");
        //如果用户名和密码不为空，显示到edit
        if (isNotnull(phone)&&isNotnull(password)) {
            et_name.setText(phone);
            et_pwd.setText(password);
            myckeck.setChecked(true);
        }

    }
    //不为空
    public boolean isNotnull(String text){

        if (text!=null&&!text.equals("")) {
            return true;
        }
        return false;

    }



}
