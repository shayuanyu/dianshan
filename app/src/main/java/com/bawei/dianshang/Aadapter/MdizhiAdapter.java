package com.bawei.dianshang.Aadapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bawei.dianshang.Activity.TianjiaActiivity;
import com.bawei.dianshang.Bean.Beanshouhuo;
import com.bawei.dianshang.R;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2017/10/20.
 */

public class MdizhiAdapter extends BaseAdapter {

   private Context context;
   private List<Beanshouhuo.DatasBean.AddressListBean> list;
    private SharedPreferences sp;

    public MdizhiAdapter(Context context, List<Beanshouhuo.DatasBean.AddressListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            //得到布局
            convertView = View.inflate(context, R.layout.address_item, null);
            //实例ViewHolder
            holder = new ViewHolder();
            //获取控件
            holder.address = (TextView) convertView.findViewById(R.id.address);
            holder.address1 = (TextView) convertView.findViewById(R.id.address1);
            holder.defult = (CheckBox) convertView.findViewById(R.id.setdefult);
            holder.name = (TextView) convertView.findViewById(R.id.username);
            holder.phone = (TextView) convertView.findViewById(R.id.userphone);
            holder.update = (TextView) convertView.findViewById(R.id.update);
            holder.delete = (TextView) convertView.findViewById(R.id.delete);
            convertView.setTag(holder);
        } else {
            //复用
            holder = (ViewHolder) convertView.getTag();
        }
        //适配
        holder.name.setText(list.get(position).getTrue_name());
        holder.phone.setText(list.get(position).getMob_phone());
        holder.address.setText(list.get(position).getAddress());
        holder.address1.setText(list.get(position).getArea_info());
        sp = context.getSharedPreferences("userinfo", MODE_PRIVATE);
        if (list.get(position).getAddress_id().equals(sp.getString("address_id",""))) {
            holder.defult.setChecked(true);
        } else if (position == 0) {
            holder.defult.setChecked(true);
            SharedPreferences.Editor edit = sp.edit();
            edit.putString("address_id", list.get(position).getAddress_id());
            edit.commit();
        }
        //check点击
        holder.defult.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor edit = sp.edit();
                edit.putString("address_id", list.get(position).getAddress_id());
                edit.commit();
                onChangeDefault.changeDefault(true);
                notifyDataSetChanged();
            }
        });
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TianjiaActiivity.class);
                intent.putExtra("true_name", list.get(position).getTrue_name());
                intent.putExtra("mob_phone", list.get(position).getMob_phone());
                intent.putExtra("address", list.get(position).getAddress());
                intent.putExtra("addressInfo", list.get(position).getArea_info());
                intent.putExtra("address_id", list.get(position).getAddress_id());
                intent.putExtra("click", "bianji");
                context.startActivity(intent);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                         AlertDialog.Builder builder = new AlertDialog.Builder(context);
                         builder.setMessage("确定要删除吗");
                         builder.setNegativeButton("考虑", null);
                         builder.setPositiveButton("狠心删除", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                                 onDeleteAddress.deleteAddress(list.get(position).getAddress_id());


                             }
                         });
                         builder.create().show();

            }
        });
        return convertView;
    }

    class ViewHolder {
        private TextView name;
        private TextView phone;
        private TextView address;
        private TextView address1;
        private TextView update;
        private TextView delete;
        private CheckBox defult;
    }

    //声明成员变量
    private onChangeDefault onChangeDefault;
    private onDeleteAddress onDeleteAddress;

    //接口传值
    public interface onChangeDefault {
        void changeDefault(boolean flag);
    }

    public interface onDeleteAddress {
        void deleteAddress(String address_id);
    }

    //提供set方法
    public void setOnChangeDefault(MdizhiAdapter.onChangeDefault onChangeDefault) {
        this.onChangeDefault = onChangeDefault;
    }

    public void setOnDeleteAddress(MdizhiAdapter.onDeleteAddress onDeleteAddress) {
        this.onDeleteAddress = onDeleteAddress;
    }

}
