package com.bawei.dianshang.Activity;

/**
 * Created by Administrator on 2017/10/12.
 */

public class API {
    public static final String HOME_PAGE_PATH = "http://m.yunifang.com/yunifang/mobile/home";
  public static final String URL = "169.254.110.146";
    // public static final String URL = "169.254.165.38";
    public static final String CLIENT = "android";
    public static final String ET_POST = "http://"+URL+"/mobile/index.php?act=login&op=register";
    public static final String SOUSUO = "http://"+URL+"/mobile/index.php?act=goods&op=goods_list&page=100";
    public static final String DENGLU = "http://"+URL+"/mobile/index.php?act=login";
    public static final String LEFTR = "http://"+URL+"/mobile/index.php?act=goods_class";
    //二级列表
    public static final String ERJI = "http://"+URL+"/mobile/index.php?act=goods_class";
    //商品详情
    public static final String XINGQING = "http://"+URL+"/mobile/index.php?act=goods&op=goods_detail";
    //商品介绍
    public static final String JIESHAO = "http://"+URL+"/mobile/index.php?act=goods&op=goods_body";
    //购物车添加 POST
    public static final String GOUSHUJU = "http://"+URL+"/mobile/index.php?act=member_cart&op=cart_add";
   //购物车查询
    public static final String GOUWUCHE= "http://"+URL+"/mobile/index.php?act=member_cart&op=cart_list";
   //收货地址
   public static final String SHOUHUODIZHI= "http://"+URL+"/mobile/index.php?act=member_address&op=address_list";
    //添加地址
    public static final String TIANJIADIZHII= "http://"+URL+"/mobile/index.php?act=member_address&op=address_add";
    //删除地址
    public static final String SHANCHUDIZHI= "http://"+URL+"/mobile/index.php?act=member_address&op=address_del";
    //编辑地址
    public static final String BIANJIDIZHI= "http://"+URL+"/mobile/index.php?act=member_address&op=address_edit";


}
