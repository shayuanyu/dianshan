package com.bawei.dianshang.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bawei.dianshang.Aadapter.MyAdaptergouwu;
import com.bawei.dianshang.Activity.API;
import com.bawei.dianshang.Activity.CustomExpandableListView;
import com.bawei.dianshang.Activity.DingDanActivity;
import com.bawei.dianshang.Bean.GoodsBean;
import com.bawei.dianshang.Bean.ShopBean;
import com.bawei.dianshang.Bean.beanGoushu;
import com.bawei.dianshang.R;
import com.bawei.dianshang.utils.GsonObjectCallback;
import com.bawei.dianshang.utils.OkHttp3Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2017/10/8.
 */

public class Fragment3 extends Fragment  {

    private View view;
    private SharedPreferences sp;
    private String key;
    private TextView cartcount;
    private CustomExpandableListView elv;
    private CheckBox allcheck;
    private Button jiesuancount;
    private Button jiesuan;
    private TextView total_price;
    private List<ShopBean>  shoplist;
    private List<GoodsBean> goodslist;
    private MyAdaptergouwu adaptergouwu;
    private List<GoodsBean> list;
    private int carnum = 0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment3, container, false);

        initView(view);
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        elv.setGroupIndicator(null);
        initDatas();
        allcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                     //得到全选状态
                boolean checked = allcheck.isChecked();
                if (checked){
                   int num=0;
                    float price=0;
                    for (int i=0;i<shoplist.size();i++){
                              shoplist.get(i).setsIscheck(true);
                     for (int j=0;j<shoplist.get(i).getGoodsBeanList().size();j++){
                         shoplist.get(i).getGoodsBeanList().get(j).setgIscheck(true);
              num+=Integer.parseInt(shoplist.get(i).getGoodsBeanList().get(j).getGoodsName());
              price+= Float.parseFloat(shoplist.get(i).getGoodsBeanList().get(j).getGoodsNum());
                     }
                    }
                    total_price.setText("总计:"+price+"");
                    jiesuancount.setText("结算："+num+"");

                }else {
                    for (int i = 0; i < shoplist.size(); i++) {
                        shoplist.get(i).setsIscheck(false);
                        for (int j = 0; j < shoplist.get(i).getGoodsBeanList().size(); j++) {
                            shoplist.get(i).getGoodsBeanList().get(j).setgIscheck(false);
                        }
                    }
                    total_price.setText("总计:" + 0 + "");
                    jiesuancount.setText("结算:" + 0 + "");
                }
                 adaptergouwu.notifyDataSetChanged();
            }
        });
        jiesuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list = new ArrayList<GoodsBean>();
                //遍历所以子数据   把选中的添加到 集合
                for (int i = 0; i < shoplist.size(); i++) {
                    shoplist.get(i).setsIscheck(false);
                    for (int j = 0; j < shoplist.get(i).getGoodsBeanList().size(); j++) {
                        if (shoplist.get(i).getGoodsBeanList().get(j).isgIscheck()) {
                            list.add(shoplist.get(i).getGoodsBeanList().get(j));
                        }
                    }
                }
            }
        });
        //结算
        jiesuancount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  GoodsBean goodsBean = new GoodsBean();

//                if (goodsBean.getGoodsName().equals("")) {
//                    Toast.makeText(getActivity(), "请选择要购买的商品", Toast.LENGTH_SHORT).show();
//                }
                Intent intent=new Intent(getActivity(), DingDanActivity.class);
                  startActivity(intent);

            }
        });

        }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sp = getActivity().getSharedPreferences("userinfo", MODE_PRIVATE);
        key = sp.getString("key", "");


    }

    private void initView(View view) {
        cartcount = (TextView) view.findViewById(R.id.cartcount);
        elv = (CustomExpandableListView) view.findViewById(R.id.elv);
        allcheck = (CheckBox) view.findViewById(R.id.allcheck);
        jiesuancount = (Button) view.findViewById(R.id.jiesuancount);
        jiesuan = (Button) view.findViewById(R.id.jiesuancount);
        total_price = (TextView) view.findViewById(R.id.total_price);

    }
    private void initDatas() {
        HashMap<String,String> map=new HashMap<>();
        map.put("key",key);
        map.put("client", API.CLIENT);
        OkHttp3Utils.getInstance().doPost(API.GOUWUCHE, map, new GsonObjectCallback<beanGoushu>() {
            @Override
            public void onUi(beanGoushu beangoushu) {
                shoplist = new ArrayList<ShopBean>();

                for (int i = 0; i < beangoushu.getDatas().getCart_list().size(); i++) {
                    goodslist = new ArrayList<GoodsBean>();
                    for (int j = 0; j < beangoushu.getDatas().getCart_list().get(i).getGoods().size(); j++) {
                        goodslist.add(new GoodsBean(beangoushu.getDatas().getCart_list().get(i).getGoods().get(j).getGoods_name(), beangoushu.getDatas().getCart_list().get(i).getGoods().get(j).getGoods_price(),
                                beangoushu.getDatas().getCart_list().get(i).getGoods().get(j).getCart_id(), beangoushu.getDatas().getCart_list().get(i).getGoods().get(j).getGoods_image_url(),
                                beangoushu.getDatas().getCart_list().get(i).getGoods().get(j).getGoods_num(), beangoushu.getDatas().getCart_list().get(i).getGoods().get(j).getGoods_total(),
                                beangoushu.getDatas().getCart_list().get(i).getGoods().get(j).getStore_name(), false));
                            //统计购物车所有商品数量

                        carnum += Integer.parseInt(beangoushu.getDatas().getCart_list().get(i).getGoods().get(j).getGoods_num());
                    }
                    shoplist.add(new ShopBean(beangoushu.getDatas().getCart_list().get(i).getStore_name(), false, goodslist));
                }
                //赋值
                cartcount.setText("购物车(" + carnum + ")");
                adaptergouwu = new MyAdaptergouwu(getActivity(),shoplist);
                 elv.setAdapter(adaptergouwu);
                for (int i = 0; i < adaptergouwu.getGroupCount(); i++) {
                    elv.expandGroup(i);
                }
                //根据组和子的选择状态来控制全选
                adaptergouwu.setOnChecked(new MyAdaptergouwu.OnChecked() {
                    @Override
                    public void checked(boolean tag) {
                         allcheck.setChecked(tag);
                    }
                });
                //
                adaptergouwu.setOnPriceTotal(new MyAdaptergouwu.OnPriceTotal() {
                    @Override
                    public void priceTotal(float price) {
                        total_price.setText("总计:"+price);
                    }
                });
                adaptergouwu.setOnCountTotal(new MyAdaptergouwu.OnCountTotal() {
                    @Override
                    public void countTotal(int count) {
                      jiesuancount.setText("结算"+count+"");
                    }
                });







            }

            @Override
            public void onFailed(Call call, IOException e) {

            }
        });



    }


}
