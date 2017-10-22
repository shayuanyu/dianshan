package com.bawei.dianshang.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bawei.dianshang.Bean.beanadd;
import com.bawei.dianshang.Bean.beantianjian;
import com.bawei.dianshang.R;
import com.bawei.dianshang.utils.GsonObjectCallback;
import com.bawei.dianshang.utils.OkHttp3Utils;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;

public class TianjiaActiivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView address_back;
    private EditText name;
    private EditText phone;
    private EditText youaddress;
    private EditText addressinfo;
    private Button commint;

    private String click;
    private Intent intent;
    private SharedPreferences sp;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tianjia_actiivity);
        sp = getSharedPreferences("userinfo", MODE_PRIVATE);
        key = sp.getString("key", "");
        initView();
        //得到传过来的值
        intent = getIntent();
        click = intent.getStringExtra("click");
        if (click.equals("bianji")) {
            String true_name = intent.getStringExtra("true_name");
            String mob_phone = intent.getStringExtra("mob_phone");
            String address1 = intent.getStringExtra("address");
            String areaInfo = intent.getStringExtra("addressInfo");
            name.setText(true_name);
            phone.setText(mob_phone);
            youaddress.setText(address1);
            addressinfo.setText(areaInfo);
        }

    }

    private void gettianData() {
        HashMap<String,String> map=new HashMap<>();
        map.put("key",key);
        map.put("true_name",name.getText().toString().trim());
        map.put("mob_phone",phone.getText().toString().trim());
        map.put("city_id","1");
        map.put("area_id","1");
        map.put("address",youaddress.getText().toString().trim());
         map.put("area_info",addressinfo.getText().toString().trim());
        OkHttp3Utils.doPost(API.TIANJIADIZHII, map, new GsonObjectCallback<beantianjian>() {
            @Override
            public void onUi(beantianjian beatianjian) {
                    if (beatianjian.getCode()==200){
                        Toast.makeText(TianjiaActiivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(TianjiaActiivity.this,AddActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(TianjiaActiivity.this, "添加失败喔", Toast.LENGTH_SHORT).show();

                    }


            }

            @Override
            public void onFailed(Call call, IOException e) {

            }
        });




    }

    private void initView() {
        address_back = (ImageView) findViewById(R.id.address_back);
        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);
        youaddress = (EditText) findViewById(R.id.youaddress);
        addressinfo = (EditText) findViewById(R.id.addressinfo);
        commint = (Button) findViewById(R.id.commint);

        commint.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commint:
                       submit();
                   if (click.equals("tianjia")) {
                  //  添加
                    // postServerData(API.ADD_ADDRESS_PATH);
                    gettianData();
                } else if (click.equals("bianji")) {
                    String address_id = intent.getStringExtra("address_id");
                    //    postServerData2(API.BIANJI_ADDRESS_PATH, address_id);
                     //修改
                      getupData(address_id);
                }
                break;
        }
    }

    private void getupData(String address_id) {
        HashMap<String,String> map=new HashMap<>();
        map.put("key",key);
        map.put("true_name",name.getText().toString().trim());
        map.put("mob_phone",phone.getText().toString().trim());
        map.put("city_id","1");
        map.put("area_id","1");
        map.put("address",youaddress.getText().toString().trim());
        map.put("area_info",addressinfo.getText().toString().trim());
        map.put("address_id",address_id);
        OkHttp3Utils.doPost(API.BIANJIDIZHI, map, new GsonObjectCallback<beanadd>() {
            @Override
            public void onUi(beanadd beadd) {
                   if (beadd.getCode()==200){
                       Toast.makeText(TianjiaActiivity.this, "编辑成功", Toast.LENGTH_SHORT).show();
                       finish();

                   }else{
                       Toast.makeText(TianjiaActiivity.this, "编辑失败", Toast.LENGTH_SHORT).show();

                   }
            }

            @Override
            public void onFailed(Call call, IOException e) {

            }
        });


    }

    private void submit() {
        // validate
        String nameString = name.getText().toString().trim();
        if (TextUtils.isEmpty(nameString)) {
            Toast.makeText(this, "请输入真实姓名", Toast.LENGTH_SHORT).show();
            return;
        }

        String phoneString = phone.getText().toString().trim();
        if (TextUtils.isEmpty(phoneString)) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        String youaddressString = youaddress.getText().toString().trim();
        if (TextUtils.isEmpty(youaddressString)) {
            Toast.makeText(this, "地址", Toast.LENGTH_SHORT).show();
            return;
        }

        String addressinfoString = addressinfo.getText().toString().trim();
        if (TextUtils.isEmpty(addressinfoString)) {
            Toast.makeText(this, "描述信息", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }
}
