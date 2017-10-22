package com.bawei.dianshang.Aadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.dianshang.Bean.beanlaolishi;
import com.bawei.dianshang.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017/10/10.
 */


    public class MylaolishiAdapter extends RecyclerView.Adapter<MylaolishiAdapter.MyViewHolder> {
       private Context context;
       private List<beanlaolishi.DatasBean.GoodsListBean> list;

        public MylaolishiAdapter(Context context, List<beanlaolishi.DatasBean.GoodsListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //无法自适应 获取屏幕的宽度
            final View view = View.inflate(context, R.layout.item, null);
            //屏幕适配中的代码适配 侧滑
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.width = width;
            view.setLayoutParams(layoutParams);
            //参考父布局
            //  View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);

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

                 holder.tv_title.setText(list.get(position).getGoods_name());
                 Picasso.with(holder.iamge.getContext()).load(list.get(position).getGoods_image_url()).placeholder(R.mipmap.ic_launcher).into(holder.iamge);
                 holder.tv_price.setText(list.get(position).getGoods_price());
                 holder.tv_yuanprice.setText(list.get(position).getGoods_marketprice());

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv_price;
            TextView tv_yuanprice;
            TextView tv_title;
            private  ImageView iamge;

            public MyViewHolder(View itemView) {
                super(itemView);
                tv_title = (TextView) itemView.findViewById(R.id.tv_title);
                tv_price = (TextView) itemView.findViewById(R.id.tv_price);
                iamge = (ImageView) itemView.findViewById(R.id.iamge);
                tv_yuanprice = (TextView) itemView.findViewById(R.id.tv_yuanprice);
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


