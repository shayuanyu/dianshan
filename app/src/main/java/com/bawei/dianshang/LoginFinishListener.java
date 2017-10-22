package com.bawei.dianshang;

/**
 * 1. 类的用途 登录回调
 * 2. @author forever
 * 3. @date 2017/9/26 16:48
 */

public interface LoginFinishListener {
    void onNameError();

    void onPwdError();
    void onSuccess();
    void onFill();
     void onquerPwdError();
    void onemailError();

}
