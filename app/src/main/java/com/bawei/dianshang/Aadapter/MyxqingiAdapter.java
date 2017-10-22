package com.bawei.dianshang.Aadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.dianshang.Bean.Beanxiangqing;
import com.bawei.dianshang.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017/10/10.
 */


    public class MyxqingiAdapter extends RecyclerView.Adapter<MyxqingiAdapter.MyViewHolder> {
       private Context context;

    private List<Beanxiangqing.DatasBean.GoodsCommendListBean> commend_list;

    public MyxqingiAdapter(Context context, List<Beanxiangqing.DatasBean.GoodsCommendListBean> commend_list) {
        this.context = context;
        this.commend_list = commend_list;
    }

    @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            //参考父布局
             View view = LayoutInflater.from(context).inflate(R.layout.itemtuijian, parent, false);

            final MyViewHolder holder = new MyViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    if (listener != null) {
                        listener.onItemClick(position);
                    }
                }
            });
            return holder;
        }
        //绑定数据
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {



         Picasso.with(context).load(commend_list.get(position).getGoods_image_url()).placeholder(R.mipmap.ic_launcher).into(holder.iamge_tuijian);
            holder.tv__tituijian.setText(commend_list.get(position).getGoods_name());
            holder.tv_pricetuijian.setText(commend_list.get(position).getGoods_promotion_price());

        }

        @Override
        public int getItemCount() {
            return commend_list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {


            private final ImageView iamge_tuijian;
            private final TextView tv__tituijian;
            private final TextView tv_pricetuijian;

            public MyViewHolder(View itemView) {
                super(itemView);
                iamge_tuijian = (ImageView) itemView.findViewById(R.id.iamge_tuijian);
                tv__tituijian = (TextView) itemView.findViewById(R.id.tv__tituijian);
                tv_pricetuijian = (TextView) itemView.findViewById(R.id.tv_pricetuijian);




            }
        }
    //接口回调
        private OnItemClickListener listener;
        public interface OnItemClickListener {
            void onItemClick(int position);
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }
    }


