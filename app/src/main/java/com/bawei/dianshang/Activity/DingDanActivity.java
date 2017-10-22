package com.bawei.dianshang.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bawei.dianshang.Bean.Beanshouhuo;
import com.bawei.dianshang.Bean.Beanxiangqing;
import com.bawei.dianshang.R;
import com.bawei.dianshang.entity.PayResult;
import com.bawei.dianshang.utils.GsonObjectCallback;
import com.bawei.dianshang.utils.OkHttp3Utils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DingDanActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int SDK_PAY_FLAG = 1;
    private Beanxiangqing bean;
    private ImageView aff_back;
    private TextView aff_username;
    private TextView aff_userphone;
    private ImageView address_pic;
    private TextView aff_address;
    private ImageView aff_line;
    private TextView aff_shopname;
    private ImageView aff_goodspic;
    private TextView aff_goodsname;
    private TextView aff_goodsprice;
    private TextView aff_goodsnum;
    private Button aff_order;
    private TextView aff_count;
    private SharedPreferences sp;
    private String key;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ding_dan);
        sp = getSharedPreferences("userinfo", MODE_PRIVATE);
        key = sp.getString("key", "");
        phone = sp.getString("phone", "");


        initView();
        getData();
        Intent intent = getIntent();
      //  bean = (Beanxiangqing) intent.getSerializableExtra("datas");
//        intent.putExtra("sum",
        String shopname = intent.getStringExtra("shopname");
        String name = intent.getStringExtra("name");
        String price = intent.getStringExtra("price");
        String sum = intent.getStringExtra("sum");
        String image = intent.getStringExtra("image");

        aff_shopname.setText("店铺:"+shopname);
        aff_goodsname.setText(name);
        aff_goodsprice.setText("￥" +price);
        aff_goodsnum.setText(sum);
        Picasso.with(DingDanActivity.this).load(image).placeholder(R.mipmap.ic_launcher).into(aff_goodspic);
        aff_count.setText("总价格:"+sum+"*"+price);
        aff_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DingDanActivity.this,TianjiaActiivity.class);
                startActivity(intent);


            }
        });


    }

    private void getData() {
        HashMap<String,String> map=new HashMap<>();
        map.put("key",key);
        map.put("client", API.CLIENT);

        OkHttp3Utils.doPost(API.SHOUHUODIZHI, map, new GsonObjectCallback<Beanshouhuo>() {

            private List<Beanshouhuo.DatasBean.AddressListBean> address_list;

            @Override
            public void onUi(Beanshouhuo beanshouhuo) {
                    if (beanshouhuo.getCode()==200){

                        address_list = beanshouhuo.getDatas().getAddress_list();


                        Toast.makeText(DingDanActivity.this, "json串"+address_list, Toast.LENGTH_SHORT).show();
                        for (int i=0;i<beanshouhuo.getDatas().getAddress_list().size();i++){
                             if (sp.getString("address_id","").equals(address_list.get(i).getAddress_id())){
                                   if ((address_list.get(i).getTrue_name()).equals("")){
                                       Toast.makeText(DingDanActivity.this, "收货人不能为空", Toast.LENGTH_SHORT).show();

                                   }

                                    aff_username.setText(phone);
                                    aff_userphone.setText(address_list.get(i).getMob_phone());
                                    aff_address.setText(address_list.get(i).getAddress()+"  "+address_list.get(i).getArea_info());

                             }

                        }
                        Toast.makeText(DingDanActivity.this, "地址成功", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(DingDanActivity.this, "地址错误", Toast.LENGTH_SHORT).show();
                    }
            }

            @Override
            public void onFailed(Call call, IOException e) {

            }
        });





    }


    private void initView() {
        aff_back = (ImageView) findViewById(R.id.aff_back);
        aff_username = (TextView) findViewById(R.id.aff_username);
        aff_userphone = (TextView) findViewById(R.id.aff_userphone);
        address_pic = (ImageView) findViewById(R.id.address_pic);
        aff_address = (TextView) findViewById(R.id.aff_address);
        aff_line = (ImageView) findViewById(R.id.aff_line);
        aff_shopname = (TextView) findViewById(R.id.aff_shopname);
        aff_goodspic = (ImageView) findViewById(R.id.aff_goodspic);
        aff_goodsname = (TextView) findViewById(R.id.aff_goodsname);
        aff_goodsprice = (TextView) findViewById(R.id.aff_goodsprice);
        aff_goodsnum = (TextView) findViewById(R.id.aff_goodsnum);
        aff_order = (Button) findViewById(R.id.aff_order);
        aff_count = (TextView) findViewById(R.id.aff_count);
        aff_order.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //跳转到支付界面
            case R.id.aff_order:

             // Intent intent=new Intent(DingDanActivity.this,);
               //  startActivity(intent);
                postData();


                break;
        }
    }

    private void postData() {
        //添加参数，url中的ip可以换成我们自己的后台ip
        String url = "http://169.254.255.250:8080/PayServer/AlipayDemo";
        StringBuffer sb = new StringBuffer("?");
        sb.append("subject=");
        sb.append("来自Server测试的商品");
        sb.append("&");
        sb.append("body=");
        sb.append("该测试商品的详细描述");
        sb.append("&");
        sb.append("total_fee=");
        sb.append("0.01");
        String urll = url + sb.toString();

        OkHttp3Utils.doGet(urll, new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {

        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            final String string = response.body().string();
            Runnable payRunnable = new Runnable() {
                @Override
                public void run() {
                    // 构造PayTask 对象
                    PayTask alipay = new PayTask(DingDanActivity.this);
                    // 调用支付接口，获取支付结果
                    String result = alipay.pay(string, true);
                    Log.i("TAG", "走了pay支付方法.............");

                    Message msg = new Message();
                    msg.what = SDK_PAY_FLAG;
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                }
            };

            Thread payThread = new Thread(payRunnable);
            payThread.start();
        }


    });

    }
    //这里需要一个handler
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(DingDanActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(DingDanActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(DingDanActivity.this, "支付失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }


    };



}
