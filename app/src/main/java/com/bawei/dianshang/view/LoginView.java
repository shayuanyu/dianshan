package com.bawei.dianshang.view;

/**
 * 1. 类的用途
 * 2. @author forever
 * 3. @date 2017/9/26 16:02
 */

public interface LoginView {
    //显示进度条
    void showProgressBar();

    //隐藏进度条
    void hideProgressBar();

    //用户名输入错误
    void setNameError();

    //密码输入错误
    void setPwdError();
    void setquerPwdError();
    void setemailError();

    //登录跳转
    void toHomeActivity();
    void toFill();

}
