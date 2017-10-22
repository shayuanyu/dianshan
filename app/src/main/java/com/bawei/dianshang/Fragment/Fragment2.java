package com.bawei.dianshang.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bawei.dianshang.Aadapter.MyleftAdapter;
import com.bawei.dianshang.Aadapter.Myright1Adapter;
import com.bawei.dianshang.Activity.API;
import com.bawei.dianshang.Bean.beanerji;
import com.bawei.dianshang.Bean.benaleft;
import com.bawei.dianshang.R;
import com.bawei.dianshang.utils.GsonObjectCallback;
import com.bawei.dianshang.utils.OkHttp3Utils;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/10/8.
 */

public class Fragment2 extends Fragment {
    private RecyclerView rv_left;
    private RecyclerView rv_right;
    private LinearLayoutManager linearLayoutManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment2, container, false);
        initView(view);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        //设置布局管理器
        linearLayoutManager = new LinearLayoutManager(getActivity());
        rv_left.setLayoutManager(linearLayoutManager);
        LinearLayoutManager right = new LinearLayoutManager(getActivity());
        rv_right.setLayoutManager(right);

        getData();
    }

    public void getData() {

        OkHttp3Utils.getInstance().doGet(API.LEFTR, new GsonObjectCallback<benaleft>() {
            private MyleftAdapter adapter;
            private List<benaleft.DatasBean.ClassListBean> class_list;

            @Override
            public void onUi(benaleft bealeft) {
                class_list = bealeft.getDatas().getClass_list();
                //设置适配器
                //    Toast.makeText(getActivity(),"数据"+ class_list.toString(), Toast.LENGTH_SHORT).show();
                adapter = new MyleftAdapter(getActivity(), class_list);
                rv_left.setAdapter(adapter);
                //默认显示选中加载第一个
                getDataer("1");
                adapter.setOnItemClickListener(new MyleftAdapter.OnItemClickListener() {

                    private String gc_id;

                    @Override
                    public void onItemClick(int position) {
                     //   Toast.makeText(getActivity(), "您点击了" + position, Toast.LENGTH_SHORT).show();
                        gc_id = class_list.get(position).getGc_id();

                        getDataer(gc_id);


                    }
                });

            }

            @Override
            public void onFailed(Call call, IOException e) {

            }
        });


    }


    public void getDataer(String gc_id) {
        OkHttp3Utils.getInstance().doGet(API.ERJI + "&gc_id=" + gc_id, new GsonObjectCallback<beanerji>() {
            private List<beanerji.DatasBean.ClassListBean> classList1;

            private Myright1Adapter adapterji;

            @Override
            public void onUi(beanerji beaerji) {

                classList1 = beaerji.getDatas().getClass_list();
                //设置适配器
                //    Toast.makeText(getActivity(),"数据"+ class_list.toString(), Toast.LENGTH_SHORT).show();
                adapterji = new Myright1Adapter(getActivity(), classList1);

                rv_right.setAdapter(adapterji);
                adapterji.setOnItemClickListener(new Myright1Adapter.OnItemClickListener() {
                   @Override
                    public void onItemClick(int position) {
                    //    Toast.makeText(getActivity(), "点击了3" + position, Toast.LENGTH_SHORT).show();


                    }
                });

            }

            @Override
            public void onFailed(Call call, IOException e) {

            }
        });


    }



    private void initView(View view) {
        rv_left = (RecyclerView) view.findViewById(R.id.rv_left);
        rv_right = (RecyclerView) view.findViewById(R.id.rv_right);


    }
}
