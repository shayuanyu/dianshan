package com.bawei.dianshang.Aadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.dianshang.Bean.benaleft;
import com.bawei.dianshang.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017/10/10.
 */


    public class MyleftAdapter extends RecyclerView.Adapter<MyleftAdapter.MyViewHolder> {
       private Context context;
    private List<benaleft.DatasBean.ClassListBean> class_list;

        public MyleftAdapter(Context context, List<benaleft.DatasBean.ClassListBean> class_list) {
        this.context = context;
        this.class_list = class_list;
    }

    @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //无法自适应 获取屏幕的宽度
//            final View view = View.inflate(context, R.layout.itemleft, null);
//            //屏幕适配中的代码适配 侧滑
//            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//            int width = wm.getDefaultDisplay().getWidth();
//            RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            layoutParams.width = width;
//            view.setLayoutParams(layoutParams);
            //参考父布局
              View view = LayoutInflater.from(context).inflate(R.layout.itemleft, parent, false);

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

            holder.tv_leftname.setText(class_list.get(position).getGc_name());
                if (class_list.get(position).getImage().isEmpty()) {
                    return;
                }
            Picasso.with(context).load(class_list.get(position).getImage()).placeholder(R.mipmap.ic_launcher).into(holder.iamgeleft);
        }

        @Override
        public int getItemCount() {
            return class_list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv_leftname;
            private  ImageView iamgeleft;

            public MyViewHolder(View itemView) {
                super(itemView);
                tv_leftname = (TextView) itemView.findViewById(R.id.tv_leftname);

                iamgeleft = (ImageView) itemView.findViewById(R.id.iamgeleft);
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


