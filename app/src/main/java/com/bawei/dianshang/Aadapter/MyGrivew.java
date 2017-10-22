package com.bawei.dianshang.Aadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.dianshang.Bean.beanlaolishi;
import com.bawei.dianshang.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017/10/22.
 */

public class MyGrivew extends BaseAdapter {
    private Context context;
    private List<beanlaolishi.DatasBean.GoodsListBean> list;
    TextView tv_price;
    TextView tv_yuanprice;
    TextView tv_title;
    private  ImageView iamge;
    public MyGrivew(Context context, List<beanlaolishi.DatasBean.GoodsListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
             convertView=convertView.inflate(context, R.layout.item, null);
         tv_title = (TextView)convertView.findViewById(R.id.tv_title);
        tv_price = (TextView)convertView.findViewById(R.id.tv_price);
        iamge = (ImageView)convertView.findViewById(R.id.iamge);
        tv_yuanprice = (TextView)convertView.findViewById(R.id.tv_yuanprice);
        tv_title.setText(list.get(position).getGoods_name());
        Picasso.with(iamge.getContext()).load(list.get(position).getGoods_image_url()).placeholder(R.mipmap.ic_launcher).into(iamge);
       tv_price.setText(list.get(position).getGoods_price());
        tv_yuanprice.setText(list.get(position).getGoods_marketprice());




        return convertView;
    }


}
