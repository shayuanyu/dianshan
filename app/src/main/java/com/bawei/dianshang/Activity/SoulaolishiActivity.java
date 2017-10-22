package com.bawei.dianshang.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.bawei.dianshang.Aadapter.MylaolishiAdapter;
import com.bawei.dianshang.Bean.beanlaolishi;
import com.bawei.dianshang.R;
import com.bawei.dianshang.utils.GsonObjectCallback;
import com.bawei.dianshang.utils.OkHttp3Utils;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

public class SoulaolishiActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerview;
    private int list_grid=0;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soulaolishi);

//
//        Intent intent = getIntent();
//        String laolish = intent.getStringExtra("laolishi");
        getData();
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        //设置布局管理器
        linearLayoutManager = new LinearLayoutManager(this);

        //设置GridLayoutManager布局管理器
        gridLayoutManager = new GridLayoutManager(this, 2);

        //设置StaggeredGridLayoutManager布局管理器
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        //把布局管理器给RecyclerView
        recyclerview.setLayoutManager(linearLayoutManager);
        //添加华丽分割线
//        MyDecoration decoration = new MyDecoration(this, LinearLayoutManager.VERTICAL);
//        rv.addItemDecoration(decoration);
        //给表格添加华丽的分割线
               /* GridDivider gridDivider = new GridDivider(this, 2, Color.BLUE);
                rv.setLayoutManager(gridLayoutManager);
                rv.addItemDecoration(gridDivider);*/

    }

    public void getData() {
        OkHttp3Utils.getInstance().doGet(API.SOUSUO, new GsonObjectCallback<beanlaolishi>() {
            private List<beanlaolishi.DatasBean.GoodsListBean> list;
            private MylaolishiAdapter adapter;

            @Override
            public void onUi(beanlaolishi beanlaolishi) {
                list = beanlaolishi.getDatas().getGoods_list();
                //设置适配器
                System.out.println("请求数据" + list.toString());
//              MyokAdapter  adapter = new MyokAdapter(SoulaolishiActivity.this, list);
                 adapter = new MylaolishiAdapter(SoulaolishiActivity.this, list);
                recyclerview.setAdapter(adapter);
                adapter.setOnItemClickListener(new MylaolishiAdapter.OnItemClickListener() {

                    private String goods_id;

                    @Override
                    public void onItemClick(int position) {
                        goods_id = list.get(position).getGoods_id();
                        Intent intent=new Intent(SoulaolishiActivity.this,XiangQingActivity.class);
                        intent.putExtra("gs_id",goods_id);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onFailed(Call call, IOException e) {

            }
        });


    }


    @Override
    public void onClick(View v) {
        if(list_grid==0){
            recyclerview.setLayoutManager(linearLayoutManager);
            list_grid=1;


        }else if(list_grid==1){
            recyclerview.setLayoutManager(gridLayoutManager);
            list_grid=2;

        }else if(list_grid==2){
            recyclerview.setLayoutManager(staggeredGridLayoutManager);
            list_grid=0;
        }
    }
}
