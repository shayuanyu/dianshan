package com.bawei.dianshang.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bawei.dianshang.Aadapter.MyGrivew;
import com.bawei.dianshang.Activity.API;
import com.bawei.dianshang.Activity.Mainsaoyisao;
import com.bawei.dianshang.Activity.NNihao;
import com.bawei.dianshang.Activity.SouSuoActivity;
import com.bawei.dianshang.Bean.Homebean;
import com.bawei.dianshang.Bean.beanlaolishi;
import com.bawei.dianshang.R;
import com.bawei.dianshang.banner.BannerImageLoader;
import com.bawei.dianshang.utils.GsonObjectCallback;
import com.bawei.dianshang.utils.OkHttp3Utils;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.youth.banner.Banner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/10/8.
 */

public class Fragment1 extends Fragment implements View.OnClickListener {
    private View view;
    String url = "http://www.93.gov.cn/93app/data.do?channelId=0&startNum=0";
    private ImageView saoyisao;
    private ImageView sousuo;
    private EditText et_laolishi;
    private ImageView xiangji;
    private RelativeLayout search;
    private RecyclerView recyclerView;

    private GridLayoutManager gridLayoutManager;
    private GridView gride;
    private ImageView huiyuanma;
    int REQUEST_CODE = 1;
    private Banner mybanner;
    private Homebean.DataBean dataBean;
    private ImageView iamgeview;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment1, container, false);
        initView(view);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //获取摄像头权限
        getCameraPermission();
        //ZXingLibrary初始化
        ZXingLibrary.initDisplayOpinion(getActivity());
        recq();
        Budier();
  iamgeview.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
      //    Toast.makeText(getActivity(), "请检查您的网络", Toast.LENGTH_SHORT).show();
           startActivity(new Intent(getActivity(),NNihao.class));

      }
  });

    }

    private void initView(View view) {
        saoyisao = (ImageView) view.findViewById(R.id.saoyisao);
        et_laolishi = (EditText) view.findViewById(R.id.et_laolishi);
        search = (RelativeLayout) view.findViewById(R.id.search);
        search.setOnClickListener(this);

        gride = (GridView) view.findViewById(R.id.gride);
        huiyuanma = (ImageView) view.findViewById(R.id.huiyuanma);
        huiyuanma.setOnClickListener(this);
        mybanner = (Banner) view.findViewById(R.id.mybanner);

        iamgeview = (ImageView) view.findViewById(R.id.iamgeview);
        iamgeview.setOnClickListener(this);
    }

    public void recq() {


        OkHttp3Utils.doGet(API.SOUSUO, new GsonObjectCallback<beanlaolishi>() {
            private MyGrivew adapter1;
            private List<beanlaolishi.DatasBean.GoodsListBean> list;

            @Override
            public void onUi(beanlaolishi beanlaolishi) {
                list = beanlaolishi.getDatas().getGoods_list();
                //设置适配器
                adapter1 = new MyGrivew(getActivity(), list);
                gride.setAdapter(adapter1);
            }

            @Override
            public void onFailed(Call call, IOException e) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saoyisao:
                Intent intent2 = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent2, REQUEST_CODE);

                break;

            //跳转搜索界面
            case R.id.search:
                Intent intent = new Intent(getActivity(), SouSuoActivity.class);
                startActivity(intent);
                break;
            //跳转搜索界面
            case R.id.huiyuanma:
                Intent intener = new Intent(getActivity(), Mainsaoyisao.class);
                startActivity(intener);
                break;


        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(getActivity(), "解析结果:" + result, Toast.LENGTH_LONG).show();
                    //  Log.e(TAG,"解析结果:" + result);
                    // textView.setText(result);//解析结果显示在TextView
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(getActivity(), "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void Budier() {
        OkHttp3Utils.doGet(API.HOME_PAGE_PATH, new GsonObjectCallback<Homebean>() {
            @Override
            public void onUi(Homebean homebean) {
                dataBean = homebean.getData();
                List<String> bannerList = new ArrayList<>();
                for (int i = 0; i < dataBean.getAd1().size(); i++) {
                    bannerList.add(dataBean.getAd1().get(i).getImage());
                }
                mybanner.setImageLoader(new BannerImageLoader());
                mybanner.setImages(bannerList);
                //bannerView.myBanner.setBannerAnimation(CubeInTransformer.class);
                mybanner.start();
            }

            @Override
            public void onFailed(Call call, IOException e) {

            }
        });


    }

    public void getCameraPermission() {
        if (Build.VERSION.SDK_INT > 22) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                //先判断有没有权限 ，没有就在这里进行权限的申请
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.CAMERA}, 2);
            } else {
                //说明已经获取到摄像头权限了 想干嘛干嘛
            }
        } else {
            //这个说明系统版本在6.0之下，不需要动态获取权限。
        }
    }
}

