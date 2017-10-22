package com.bawei.dianshang.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.dianshang.Aadapter.MyxqingiAdapter;
import com.bawei.dianshang.Bean.Beanxiangqing;
import com.bawei.dianshang.Bean.beanGouWu;
import com.bawei.dianshang.R;
import com.bawei.dianshang.utils.GsonObjectCallback;
import com.bawei.dianshang.utils.OkHttp3Utils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;

public class XiangQingActivity extends AppCompatActivity implements View.OnClickListener {


    private  String gs_id;
    private ImageView img_return;
    private ImageView gwuche;
    private ImageView sangedian;
    private ImageView iamge_pop;
    private ImageView img_lunbo;
    private TextView p_goods_name;
    private TextView p_goods_price;
    private TextView area_name;
    private TextView content;
    private TextView if_store_cn;
    private String phone;
    private String password;
    private WebView webview;
    private RecyclerView tuijian;
    private Button addshop;
    private Button buy;
    private View view;
    private ImageView jia;
    private ImageView jian;
    private Button quer;
    private FlowLayout flowLayout;
    private int shu=1;
    private TextView count;
    private PopupWindow popupWindow;
    private Beanxiangqing.DatasBean datas;
    private SharedPreferences sp;
    private String key;

    private TextView tv_pricpop;
    private TextView tv_pop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiang_qing);
        initView();
        sp = getSharedPreferences("userinfo", MODE_PRIVATE);
         key = sp.getString("key", "");
        phone = sp.getString("phone", "");
        password = sp.getString("password", "");
        Intent intent = getIntent();
         gs_id = intent.getStringExtra("gs_id");
     //  LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        GridLayoutManager grid=new GridLayoutManager(this,3);
         tuijian.setLayoutManager(grid);
           getData();
        view = View.inflate(this, R.layout.gouwupopwindow, null);
        jia = (ImageView) view.findViewById(R.id.jia);
        jian = (ImageView) view.findViewById(R.id.jian);
        quer = (Button) view.findViewById(R.id.quer);
        count = (TextView) view.findViewById(R.id.count);
        tv_pop = (TextView) view.findViewById(R.id.tv_pop);
        tv_pricpop = (TextView) view.findViewById(R.id.tv_pricpop);
        iamge_pop = (ImageView) view.findViewById(R.id.iamge_pop);
        //流布局
    //    flowLayout = (FlowLayout) view.findViewById(R.id.flowlayout);
        //??????
        //加号
         jia.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
               shu++;
                count.setText(shu+"");  
             }
         });
        //减号
        jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shu==1) {
                    Toast.makeText(XiangQingActivity.this, "请至少选择一件商品", Toast.LENGTH_SHORT).show();
                }else {
                    shu--;
                    count.setText(shu+"");
                }
            }
        });
        //确认
               quer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    getGouWu();
                  //关闭窗口
                  popupWindow.dismiss();

            }

        });
        img_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //设置popupWindow
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题
        // ：无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
    }


    public void getData () {
        OkHttp3Utils.getInstance().doGet(API.XINGQING + "&goods_id=" + gs_id, new GsonObjectCallback<Beanxiangqing>() {

            private List<Beanxiangqing.DatasBean.GoodsCommendListBean> commend_list;
            private WebSettings settings;

            private MyxqingiAdapter adapter;

            @Override
            public void onUi(Beanxiangqing bxiangqing) {
                datas = bxiangqing.getDatas();

                  Picasso.with(XiangQingActivity.this).load( datas.getGoods_image()).placeholder(R.mipmap.ic_launcher).into(img_lunbo);
                Picasso.with(XiangQingActivity.this).load( datas.getGoods_image()).placeholder(R.mipmap.ic_launcher).into(iamge_pop);

                p_goods_name.setText(datas.getGoods_info().getGoods_name());
                tv_pop.setText(datas.getGoods_info().getGoods_name());

                p_goods_price.setText("￥"+datas.getGoods_info().getGoods_promotion_price()+"-"+datas.getGoods_info().getGoods_price());
                tv_pricpop.setText("￥"+datas.getGoods_info().getGoods_promotion_price());

                area_name.setText(datas.getGoods_hair_info().getArea_name());
                  content.setText(datas.getGoods_hair_info().getContent());
                  if_store_cn.setText(datas.getGoods_hair_info().getIf_store_cn());
                 //加载商品介绍
                  settings = webview.getSettings();
                 settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                  settings.setJavaScriptEnabled(true);
                 webview.setWebChromeClient(new WebChromeClient());
                   webview.loadUrl(API.JIESHAO+"&goods_id=" + gs_id);
                webview.setWebViewClient(new WebViewClient());

                  commend_list = datas.getGoods_commend_list();
                adapter = new MyxqingiAdapter(XiangQingActivity.this,commend_list);
                tuijian.setAdapter(adapter);
                adapter.setOnItemClickListener(new MyxqingiAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Toast.makeText(XiangQingActivity.this,datas.getGoods_info().getGoods_name()+ "已下架", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onFailed(Call call, IOException e) {

            }
        });
    }

    private void initView() {
        img_return = (ImageView) findViewById(R.id.img_return);
        gwuche = (ImageView) findViewById(R.id.gwuche);
        sangedian = (ImageView) findViewById(R.id.sangedian);
        img_lunbo = (ImageView) findViewById(R.id.img_lunbo);
        p_goods_name = (TextView) findViewById(R.id.p_goods_name);
        p_goods_price = (TextView) findViewById(R.id.p_goods_price);
        area_name = (TextView) findViewById(R.id.area_name);
        content = (TextView) findViewById(R.id.content);
        if_store_cn = (TextView) findViewById(R.id.if_store_cn);

        webview = (WebView) findViewById(R.id.webview);
        tuijian = (RecyclerView) findViewById(R.id.tuijian);

        addshop = (Button) findViewById(R.id.addshop);
        buy = (Button) findViewById(R.id.buy);

        addshop.setOnClickListener(this);
        buy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            //加入购物车
            case R.id.addshop:
                     emptiy();

                break;
            //立即购买
            case R.id.buy:
               Intent intent=new Intent(XiangQingActivity.this,DingDanActivity.class);
              // intent.putExtra("datas",datas);
                intent.putExtra("shopname",datas.getStore_info().getStore_name());
                intent.putExtra("image",datas.getGoods_image());
                intent.putExtra("name",datas.getGoods_info().getGoods_name());
                intent.putExtra("price",datas.getGoods_info().getGoods_promotion_price());
                intent.putExtra("sum",shu+"");


                startActivity(intent);
                break;
        }
    }
    //加载购物车
    private void getGouWu() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("key",key);
        map.put("goods_id",gs_id);
        map.put("quantity",shu+"");
        map.put("client", API.CLIENT);
     OkHttp3Utils.doPost(API.GOUSHUJU, map, new GsonObjectCallback<beanGouWu>() {

         @Override
         public void onUi(beanGouWu beanguwu) {
                if (beanguwu.getCode()==200){
                    Toast.makeText(XiangQingActivity.this, "添加成功", Toast.LENGTH_SHORT).show();


                    Log.e("取出的key值",key);
                }else {

                    Toast.makeText(XiangQingActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                }
         }

         @Override
         public void onFailed(Call call, IOException e) {

         }
     });










    }

    public void emptiy(){
        if (phone.equals("")&&password.equals("")){
            Intent intent = new Intent(XiangQingActivity.this, DengluActivity.class);
            startActivity(intent);
        }else {
            if (popupWindow.isShowing()){
                popupWindow.dismiss();
            }else {
                popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
            }
        }
    }
}
