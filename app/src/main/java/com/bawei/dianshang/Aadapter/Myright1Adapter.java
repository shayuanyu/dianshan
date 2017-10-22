package com.bawei.dianshang.Aadapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bawei.dianshang.Activity.API;
import com.bawei.dianshang.Bean.beanerji;
import com.bawei.dianshang.Bean.beansanji;
import com.bawei.dianshang.R;
import com.bawei.dianshang.utils.GsonObjectCallback;
import com.bawei.dianshang.utils.OkHttp3Utils;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

import static com.bawei.dianshang.R.id.rv_right2;

/**
 * Created by Administrator on 2017/10/10.
 */


    public class Myright1Adapter extends RecyclerView.Adapter<Myright1Adapter.MyViewHolder> {
    private Context context;
    private List<beanerji.DatasBean.ClassListBean> classList1;
    private String gc_id3;

    public Myright1Adapter(Context context, List<beanerji.DatasBean.ClassListBean> classList1) {
        this.context = context;
        this.classList1 = classList1;
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
        View view = LayoutInflater.from(context).inflate(R.layout.itemrith1, parent, false);

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
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.tv_leftname.setText(classList1.get(position).getGc_name());

        gc_id3 = classList1.get(position).getGc_id();

        OkHttp3Utils.getInstance().doGet(API.ERJI + "&gc_id=" + gc_id3, new GsonObjectCallback<beansanji>() {
            private List<beansanji.DatasBean.ClassListBean> class_list2;


            private Myright2Adapter adaptsanji;

            @Override
            public void onUi(beansanji beasanji) {

                class_list2 = beasanji.getDatas().getClass_list();
                //设置适配器
                //    Toast.makeText(getActivity(),"数据"+ class_list.toString(), Toast.LENGTH_SHORT).show();
                adaptsanji = new Myright2Adapter(context, class_list2);
                holder.recyclerView.setAdapter(adaptsanji);

            }

            @Override
            public void onFailed(Call call, IOException e) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return classList1.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_leftname;
        private final RecyclerView recyclerView;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_leftname = (TextView) itemView.findViewById(R.id.tv_leftname);
            recyclerView = (RecyclerView) itemView.findViewById(rv_right2);
            GridLayoutManager grid=new GridLayoutManager(context,3);
            recyclerView.setLayoutManager(grid);
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
