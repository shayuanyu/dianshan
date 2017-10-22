package com.bawei.dianshang.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bawei.dianshang.R;
import com.bawei.dianshang.presenter.LoginPresenter;
import com.bawei.dianshang.presenter.LoginPresenterImpl;
import com.bawei.dianshang.view.LoginView;

public class ZhuceActivity extends AppCompatActivity implements View.OnClickListener, LoginView {

    private ImageView reg_return;
    private EditText et_name;
    private EditText et_pwd;
    private EditText et_ispassword;
    private EditText et_email;
    private Button bt_zhuce;
    private ProgressBar pb;
    private LoginPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce);
        initView();
        presenter = new LoginPresenterImpl(this);

    }

    private void initView() {
        reg_return = (ImageView) findViewById(R.id.reg_return);
        et_name = (EditText) findViewById(R.id.reg_uname);
        et_pwd = (EditText) findViewById(R.id.reg_password);
        et_ispassword = (EditText) findViewById(R.id.reg_ispassword);
        et_email = (EditText) findViewById(R.id.reg_email);
        bt_zhuce = (Button) findViewById(R.id.bt_zhuce);
        pb = (ProgressBar) findViewById(R.id.pb);

        reg_return.setOnClickListener(this);
        bt_zhuce.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_zhuce:
                presenter.validatePass(this, et_name.getText().toString(), et_pwd.getText().toString(),et_ispassword.getText().toString(),et_email.getText().toString());

                break;
            case R.id.reg_return:
                 finish();
                break;
        }
    }


    @Override
    public void showProgressBar() {
        pb.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        pb.setVisibility(View.INVISIBLE);

    }

    @Override
    public void setNameError() {
        et_name.setError("name cannot be empty");
    }

    @Override
    public void setPwdError() {
        et_pwd.setError("pwd cannot be empty");

    }

    @Override
    public void setquerPwdError() {
        et_email.setError("qurepwd cannot be empty");
    }

    @Override
    public void setemailError() {
          et_email.setError("email cannot be empty");
    }

    @Override
    public void toHomeActivity() {

        Toast.makeText(ZhuceActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
        finish();
    }
    @Override
    public void toFill() {

        Toast.makeText(ZhuceActivity.this, "用户名已存在", Toast.LENGTH_SHORT).show();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestory();
    }



}
