package wlxy.com.travelapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

import wlxy.com.travelapp.R;
import wlxy.com.travelapp.model.MerChantModel;
import wlxy.com.travelapp.model.TicketModel;
import wlxy.com.travelapp.utils.AppController;
import wlxy.com.travelapp.utils.utils;

/**
 * Created by WLW on 2017/11/22.
 */

public class TicketAdapter extends ArrayAdapter<TicketModel> {
    private ArrayList<TicketModel> data;
    private int viewId;

    public TicketAdapter(Context context, int viewId, ArrayList<TicketModel> data) {
        super(context, viewId, data);
        this.data = data;
        this.viewId = viewId;
    }

    @Override
    public int getCount() {
        return data.size();
    }


    @Override
    public TicketModel getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TicketModel ticketModel = data.get(position);

        View view;
        TicketAdapter.ViewHolders viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(viewId, null);
            viewHolder = new TicketAdapter.ViewHolders();
            viewHolder.ticketPrice = (TextView) view.findViewById(R.id.tick_price);
            viewHolder.ticketName = (TextView) view.findViewById(R.id.ticket_name);
            viewHolder.ticketInfo = (TextView) view.findViewById(R.id.ticket_info);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (TicketAdapter.ViewHolders) view.getTag();
        }

        //给控件写入信息
        viewHolder.ticketPrice.setText(ticketModel.getPrice());
        viewHolder.ticketInfo.setText(ticketModel.getInfo());
        viewHolder.ticketName.setText(ticketModel.getTname());

        return view;
    }

    class ViewHolders {
        public NetworkImageView image;
        public TextView ticketPrice;
        public TextView ticketName;
        public TextView ticketInfo;
    }
}
