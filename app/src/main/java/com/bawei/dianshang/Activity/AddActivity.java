package com.bawei.dianshang.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.dianshang.Aadapter.MdizhiAdapter;
import com.bawei.dianshang.Bean.Beanshouhuo;
import com.bawei.dianshang.Bean.beanadd;
import com.bawei.dianshang.R;
import com.bawei.dianshang.utils.GsonObjectCallback;
import com.bawei.dianshang.utils.OkHttp3Utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView address_back;
    private ListView lv_address;
    private TextView addaddress;
    private SharedPreferences sp;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        sp = getSharedPreferences("userinfo", MODE_PRIVATE);
        key = sp.getString("key", "");
        initView();
        //进入到我的地址
        getAddData();


    }

    private void getAddData() {
        HashMap<String,String> map=new HashMap<>();
        map.put("key",key);
        map.put("client", API.CLIENT);
        OkHttp3Utils.doPost(API.SHOUHUODIZHI, map, new GsonObjectCallback<Beanshouhuo>() {


            private MdizhiAdapter adapterdizhi;
            private List<Beanshouhuo.DatasBean.AddressListBean> address_list;

            @Override
            public void onUi(Beanshouhuo beanshouhuo) {
                address_list = beanshouhuo.getDatas().getAddress_list();
                if (beanshouhuo.getCode()==200){
                    Toast.makeText(AddActivity.this, "进入我的地址成功", Toast.LENGTH_SHORT).show();
                    adapterdizhi = new MdizhiAdapter(AddActivity.this,address_list);
                   lv_address.setAdapter(adapterdizhi);
                   adapterdizhi.setOnChangeDefault(new MdizhiAdapter.onChangeDefault() {
                       @Override
                       public void changeDefault(boolean flag) {
                           if (flag){
                               adapterdizhi.notifyDataSetChanged();
                           }
                       }
                   });
                    adapterdizhi.setOnDeleteAddress(new MdizhiAdapter.onDeleteAddress() {
                        @Override
                        public void deleteAddress(String address_id) {
                            postDeleteaddress(address_id);
                        }


                    });





                    }else {
                        Toast.makeText(AddActivity.this, "地址错误", Toast.LENGTH_SHORT).show();
                    }
            }

            @Override
            public void onFailed(Call call, IOException e) {

            }
        });


}
  //删除地址
    private void postDeleteaddress(String address_id) {
        HashMap<String,String> map=new HashMap<>();
        map.put("key",key);
        map.put("address_id",address_id);
        map.put("client", API.CLIENT);
        OkHttp3Utils.doPost(API.SHANCHUDIZHI, map, new GsonObjectCallback<beanadd>() {
           @Override
           public void onUi(beanadd beaadd) {
                 if (beaadd.getCode()==200){

                                        Toast.makeText(AddActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                         getAddData();
                                    }




                 else{
                     Toast.makeText(AddActivity.this, "删除失败", Toast.LENGTH_SHORT).show();

                 }

           }

           @Override
           public void onFailed(Call call, IOException e) {

           }
       });

    }
            private void initView() {
        address_back = (ImageView) findViewById(R.id.address_back);
        lv_address = (ListView) findViewById(R.id.lv_address);
        addaddress = (TextView) findViewById(R.id.addaddress);

        addaddress.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //添加地址
            case R.id.addaddress:
                Intent intent = new Intent(AddActivity.this,TianjiaActiivity.class);
                intent.putExtra("click", "tianjia");
                startActivity(intent);
                break;
        }
    }
}
