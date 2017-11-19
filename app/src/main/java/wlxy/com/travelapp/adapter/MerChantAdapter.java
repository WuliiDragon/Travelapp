package wlxy.com.travelapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import wlxy.com.travelapp.R;
import wlxy.com.travelapp.model.MerChantModel;
import wlxy.com.travelapp.utils.AppController;
import wlxy.com.travelapp.utils.utils;

/**
 * Created by DT on 2017/11/18.
 */

public class MerChantAdapter extends ArrayAdapter<MerChantModel> {
    private List<MerChantModel> data;
    private int viewId;


    public MerChantAdapter(Context context, int viewId, List<MerChantModel> data) {
        super(context, viewId, data);
        this.data = data;
        this.viewId = viewId;
    }

    @Override
    public int getCount() {
        return data.size();
    }


    @Override
    public MerChantModel getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MerChantModel merChantModel = data.get(position);

        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(viewId, null);
            viewHolder = new ViewHolder();
            viewHolder.bname = (TextView) view.findViewById(R.id.merchant_name);
            viewHolder.level = (TextView) view.findViewById(R.id.merchant_level);
            viewHolder.adress = (TextView) view.findViewById(R.id.merchant_adress);
            viewHolder.image = (NetworkImageView) view.findViewById(R.id.merchant_img);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        //给控件写入信息
        System.out.println(viewHolder.image);
        viewHolder.image.setImageUrl(utils.BASE + merChantModel.getImage(), AppController.getInstance().getImageLoader());
        viewHolder.adress.setText(merChantModel.getAddress());
        viewHolder.bname.setText(merChantModel.getBname());
        viewHolder.level.setText(merChantModel.getLevel());

        return view;
    }

    class ViewHolder {
        public NetworkImageView image;
        public TextView bname;
        public TextView adress;
        public TextView level;
    }

}