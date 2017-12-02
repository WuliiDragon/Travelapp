package wlxy.com.travelapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import wlxy.com.travelapp.R;
import wlxy.com.travelapp.model.UserTicketModel;
import wlxy.com.travelapp.utils.utils;

/**
 * @author dragon
 * @date 2017/12/1
 * 票列表的适配器
 */

public class OrderInfoAdapter extends ArrayAdapter<UserTicketModel> {


    private ArrayList<UserTicketModel> ticketModelArrayList;
    private int viewId;

    public OrderInfoAdapter(Context context, int viewId, ArrayList<UserTicketModel> data) {
        super(context, viewId, data);
        this.ticketModelArrayList = data;
        this.viewId = viewId;
    }

    @Override
    public int getCount() {
        return ticketModelArrayList.size();
    }

    @Nullable
    @Override
    public UserTicketModel getItem(int position) {
        return ticketModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        UserTicketModel utm = ticketModelArrayList.get(position);


        View view;
        MineTickerViewHolders viewHolder;

        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(viewId, null);
            viewHolder = new MineTickerViewHolders();
            viewHolder.QRCode = (ImageView) view.findViewById(R.id.mine_order_qr);
            viewHolder.tName = (TextView) view.findViewById(R.id.miner_ticket_tname);
            viewHolder.info = (TextView) view.findViewById(R.id.miner_ticket_info);
            viewHolder.num = (TextView) view.findViewById(R.id.miner_ticket_num);

            view.setTag(viewHolder);

        } else {
            view = convertView;
            viewHolder = (MineTickerViewHolders) view.getTag();
        }

        viewHolder.QRCode.setImageBitmap(utils.QRBitmap(utm.getCode(), 200, 200));
        viewHolder.tName.setText(utm.getTname());
        viewHolder.info.setText(utm.getInfo());
        viewHolder.num.setText(utm.getNum() + "张");

        return view;
    }


    class MineTickerViewHolders {
        public ImageView QRCode;
        public TextView tName;
        public TextView info;
        public TextView num;
    }
}
