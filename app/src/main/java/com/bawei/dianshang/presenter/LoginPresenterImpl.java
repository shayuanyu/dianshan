package com.bawei.dianshang.presenter;

import android.content.Context;

import com.bawei.dianshang.LoginFinishListener;
import com.bawei.dianshang.model.LoginModel;
import com.bawei.dianshang.model.LoginModelImpl;
import com.bawei.dianshang.view.LoginView;

/**
 * 1. 类的用途
 * 2. @author forever
 * 3. @date 2017/9/26 16:22
 * 问题 造成内存泄漏
 * 一：页面销毁的时候view=null
 * 二：用弱引用
 */

public class LoginPresenterImpl implements LoginPresenter, LoginFinishListener {
    LoginView loginView;
    LoginModel loginModel;

    //构造传参进行初始化
    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        //多态
        loginModel = new LoginModelImpl();
    }
    @Override
    public void validatePass(Context context, String name, String pwd,String querpwd,String email) {
        if (loginView != null) {
            loginView.showProgressBar();
        }
        //p关联m进行数据逻辑
        loginModel.login(context,name,pwd,querpwd,email,this);
    }
    //销毁view
    @Override
    public void onDestory() {
        if (loginView != null) {
            loginView = null;
        }
    }
    @Override
    public void onNameError() {
        if (loginView != null) {
            loginView.setNameError();
            loginView.hideProgressBar();
        }
    }


     @Override
    public void onPwdError() {
        if (loginView != null) {
            loginView.setPwdError();
            loginView.hideProgressBar();

        }
    }
    @Override
    public void onquerPwdError() {
        if (loginView != null) {
            loginView.setquerPwdError();
            loginView.hideProgressBar();

        }
    }
    @Override
    public void onemailError() {
        if (loginView != null) {
            loginView.setemailError();
            loginView.hideProgressBar();
        }
    }
    @Override
    public void onSuccess() {
        if (loginView != null) {
            loginView.toHomeActivity();
            loginView.hideProgressBar();

        }
    }
    @Override
    public void onFill() {
        if (loginView != null) {
            loginView.toFill();
            loginView.hideProgressBar();
        }
    }
}
