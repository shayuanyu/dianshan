package com.bawei.dianshang.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bawei.dianshang.R;
import com.uuzuche.lib_zxing.activity.CodeUtils;


public class Mainsaoyisao extends AppCompatActivity {

    private ImageView erweima;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_mainsaoyisao);
        initView();
        ImageView erweima = (ImageView) findViewById(R.id.erweima);
        Bitmap bitmap= CodeUtils.createImage("jiahongyin",400,400, BitmapFactory.decodeResource(getResources(),R.mipmap.ic_checked ));
        erweima.setImageBitmap(bitmap);


    }

    private void initView() {
        erweima = (ImageView) findViewById(R.id.erweima);
    }
}
