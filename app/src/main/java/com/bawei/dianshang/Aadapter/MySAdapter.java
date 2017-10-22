package com.bawei.dianshang.Aadapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.List;

/**
 * Created by Administrator on 2017/10/22.
 */

public class MySAdapter extends PagerAdapter {
     private List<String> list;
     private DisplayImageOptions options;

     public MySAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
//                .showImageForEmptyUri(R.mipmap.ic_empty)
//                .showImageOnLoading(R.mipmap.loading)
//                .showImageOnFail(R.mipmap.ic_error)
                .displayer(new RoundedBitmapDisplayer(30))
                .build();
    }

    private Context context;

    public MySAdapter(Context context) {
        this.context = context;

    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView=new ImageView(context);
        ImageLoader.getInstance().displayImage(list.get(position%list.size()),imageView,options);

        container.addView(imageView);
        return imageView;
    }

    @Override

    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View) object);
    }







}
