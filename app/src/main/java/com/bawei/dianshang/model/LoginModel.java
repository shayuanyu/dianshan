package com.bawei.dianshang.model;

import android.content.Context;

import com.bawei.dianshang.LoginFinishListener;

/**
 * 1. 类的用途
 * 2. @author forever
 * 3. @date 2017/9/26 16:17
 */

public interface LoginModel {


    void login(Context context, String name, String pwd,String querpwd,String email,LoginFinishListener listener);
}
