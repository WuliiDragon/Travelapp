package wlxy.com.travelapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import wlxy.com.travelapp.R;
import wlxy.com.travelapp.model.MineOrderModel;

/**
 * @author dragon
 * @date 2017/11/29
 */

public class MineOrderAdapter extends ArrayAdapter<MineOrderModel> {

    private ArrayList<MineOrderModel> data;
    private int viewId;

    public MineOrderAdapter(Context context, int viewId, ArrayList<MineOrderModel> data) {
        super(context, viewId, data);
        this.data = data;
        this.viewId = viewId;
    }


    @Override
    public int getCount() {
        return data.size();
    }


    @Override
    public MineOrderModel getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MineOrderModel mineOrderModel = data.get(position);

        View view;
        MineOrderAdapter.MineOrderViewHolders viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(viewId, null);
            viewHolder = new MineOrderAdapter.MineOrderViewHolders();
            viewHolder.orderCreateTime = (TextView) view.findViewById(R.id.order_create_time);
            viewHolder.orderTotalPrice = (TextView) view.findViewById(R.id.order_total_price);
            viewHolder.orderStatus = (TextView) view.findViewById(R.id.order_status);
            viewHolder.orderName = (TextView) view.findViewById(R.id.order_name);


            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (MineOrderAdapter.MineOrderViewHolders) view.getTag();
        }

        //给控件写入信息
        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd HH:mm");
        String createTime = mineOrderModel.getCreateTime();
        viewHolder.orderCreateTime.setText(formatter.format(new Date(Long.parseLong(createTime))));

        viewHolder.orderTotalPrice.setText(mineOrderModel.getTotalprice());

        if (mineOrderModel.getStatus().equals("0")) {
            viewHolder.orderStatus.setTextColor(Color.RED);
            viewHolder.orderStatus.setText("未付款");

        }
        if (mineOrderModel.getStatus().equals("1")) {
            viewHolder.orderStatus.setTextColor(Color.GREEN);
            viewHolder.orderStatus.setText("已付款");

        }
        if (mineOrderModel.getStatus().equals("2")) {
            viewHolder.orderStatus.setText("交易结束");
        }

        viewHolder.orderName.setText(mineOrderModel.getBname());
        return view;
    }


    class MineOrderViewHolders {
        public TextView orderCreateTime;
        public TextView orderTotalPrice;
        public TextView orderStatus;
        public TextView orderName;
    }

}
