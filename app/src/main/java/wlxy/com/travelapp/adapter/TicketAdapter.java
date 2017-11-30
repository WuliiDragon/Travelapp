package wlxy.com.travelapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import wlxy.com.travelapp.R;
import wlxy.com.travelapp.model.TicketModel;

/**
 *
 * @author dragon
 * @date 2017/11/22
 * @describe 票的ListView适配器
 */

public class TicketAdapter extends ArrayAdapter<TicketModel> implements View.OnClickListener {
    private ArrayList<TicketModel> data;
    private int viewId;
    private opCallBack mCallback;

    public interface opCallBack {
        public void click(View v);
    }

    public TicketAdapter(Context context, int viewId, ArrayList<TicketModel> data, opCallBack btnCallback) {
        super(context, viewId, data);
        this.data = data;
        this.viewId = viewId;
        this.mCallback = btnCallback;
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
            viewHolder.ticketCount = (TextView) view.findViewById(R.id.ticket_count);

            viewHolder.ticketPlus = (Button) view.findViewById(R.id.ticket_plus);
            viewHolder.ticketReduce = (Button) view.findViewById(R.id.ticket_reduce);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (TicketAdapter.ViewHolders) view.getTag();
        }

        viewHolder.ticketPlus.setOnClickListener(this);
        viewHolder.ticketReduce.setOnClickListener(this);
        viewHolder.ticketPlus.setTag(position + "+");
        viewHolder.ticketReduce.setTag(position + "-");

        //给控件写入信息
        viewHolder.ticketPrice.setText(ticketModel.getPrice());
        viewHolder.ticketInfo.setText(ticketModel.getInfo());
        viewHolder.ticketName.setText(ticketModel.getTname());
        viewHolder.ticketCount.setText(ticketModel.getCount() + "");


        return view;
    }

    @Override
    public void onClick(View v) {
        mCallback.click(v);
    }

    class ViewHolders {
        public NetworkImageView image;
        public TextView ticketPrice;
        public TextView ticketName;
        public TextView ticketInfo;
        public TextView ticketCount;
        public Button ticketPlus;
        public Button ticketReduce;
    }
}
