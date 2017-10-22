package com.bawei.dianshang.Aadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.bawei.dianshang.R;

/**
 * Created by Administrator on 2017/10/10.
 */


    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        Context context;

        public MyAdapter(Context context) {
            this.context = context;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //无法自适应 获取屏幕的宽度
            final View view = View.inflate(context, R.layout.itemleft, null);
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

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

         //   holder.tv_title.setText("标题");
        }

        @Override
        public int getItemCount() {
            return 20;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
          //  TextView tv_title;

            public MyViewHolder(View itemView) {
                super(itemView);
                //tv_title = (TextView) itemView.findViewById(R.id.tv_title);
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


