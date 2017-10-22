package com.bawei.dianshang.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.widget.Toast;

import com.bawei.dianshang.Activity.API;
import com.bawei.dianshang.Bean.beanzhuce;
import com.bawei.dianshang.LoginFinishListener;
import com.bawei.dianshang.utils.GsonObjectCallback;
import com.bawei.dianshang.utils.OkHttp3Utils;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;

import static android.content.Context.MODE_PRIVATE;

/**
 * 1. 类的用途 登录的业务逻辑
 * 2. @author forever
 * 3. @date 2017/9/26 16:19
 */

public class LoginModelImpl implements LoginModel {

    private SharedPreferences sp;

    @Override
    public void login(final Context context, final String name, final String pwd,final String querpwd,final String email, final LoginFinishListener listener) {

                if(TextUtils.isEmpty(name))

            {
                listener.onNameError();
                Toast.makeText(context, "用户名不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
                if(TextUtils.isEmpty(pwd))

            {
                listener.onPwdError();
                Toast.makeText(context, "密码不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
                if(TextUtils.isEmpty(querpwd))

            {
                listener.onquerPwdError();
                Toast.makeText(context, "确认密码不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
                if(TextUtils.isEmpty(email))

            {
                listener.onemailError();
                Toast.makeText(context, "邮箱不能为空", Toast.LENGTH_SHORT).show();
                return;
            }

            //    listener.onSuccess();
            getsuccess(context, name, pwd, querpwd, email, listener);
    }

    private void getsuccess(final Context context, final String name, final String pwd, final String querpwd, final String email, final LoginFinishListener listener) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("username", name);
        map.put("password", pwd);
        map.put("password_confirm", querpwd);
        map.put("email", email);
        map.put("client", API.CLIENT);

        sp = context.getSharedPreferences("userinfo", MODE_PRIVATE);

        OkHttp3Utils.doPost(API.ET_POST, map, new GsonObjectCallback<beanzhuce>() {
            @Override
            public void onUi(beanzhuce beanzhuce) {
                if (beanzhuce.getCode() == 200) {
                    listener.onSuccess();
                  sp.getBoolean("nihao",true);
                } else if (beanzhuce.getCode() == 400) {
                      listener.onFill();
                }
            }

            @Override
            public void onFailed(Call call, IOException e) {

            }
        });







    }








        }
