package com.bawei.dianshang.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bawei.dianshang.R;

public class SouSuoActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView Iv_return;
    private EditText ed_laolishi;
    private Button bt_sousuo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sou_suo);
        initView();



    }

    private void initView() {
        Iv_return = (ImageView) findViewById(R.id.Iv_return);
        ed_laolishi = (EditText) findViewById(R.id.ed_laolishi);
        bt_sousuo = (Button) findViewById(R.id.bt_sousuo);

        Iv_return.setOnClickListener(this);
        bt_sousuo.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Iv_return:

                finish();
                break;
            case R.id.bt_sousuo:
                  submit();

                break;
        }
    }

    private void submit() {
        // validate
        Intent intent=new Intent(SouSuoActivity.this, SoulaolishiActivity.class);
     String laolishi = ed_laolishi.getText().toString();
//        Log.i("请求数据",laolishi);
//        intent.putExtra("laolishi",laolishi);
        if (TextUtils.isEmpty(laolishi)) {
            Toast.makeText(this, "请输入要搜索的商品", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something

        startActivity(intent);



    }
}
