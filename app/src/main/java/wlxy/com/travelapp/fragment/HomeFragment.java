package wlxy.com.travelapp.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import wlxy.com.travelapp.R;
import wlxy.com.travelapp.model.MerChantModel;
import wlxy.com.travelapp.utils.AppController;
import wlxy.com.travelapp.utils.HttpUtils;
import wlxy.com.travelapp.utils.utils;

/**
 * Created by WLW on 2017/11/17.
 * 首页碎片
 *
 * @author dragon
 */

public class HomeFragment extends Fragment {
    private ListView merChantListView;
    private final int RIGHTSTATUS = 200;
    private ArrayList<MerChantModel> merChantModelList;
    private int pageNum = 0;
    private MerChantAdapter merChantAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.home_layout, container, false);
        merChantListView = (ListView) view.findViewById(R.id.merchant_listView);
        merChantModelList = new ArrayList<MerChantModel>();
        merChantAdapter = new MerChantAdapter(getActivity(), R.layout.merchant_tem, merChantModelList);
        merChantListView.setAdapter(merChantAdapter);


        //获取ListView数据
        HttpUtils httpUtils = new HttpUtils(utils.BASE + "/business/findAll.action", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int status = response.getInt("status");
                    if (status == RIGHTSTATUS) {
                        JSONObject data = response.getJSONObject("data");
                        JSONArray list = data.getJSONArray("list");
                        for (int i = 0; i < list.length(); i++) {
                            JSONObject item = (JSONObject) list.get(i);
                            MerChantModel merChantModel = JSON.parseObject(item.toString(), MerChantModel.class);
                            merChantModelList.add(merChantModel);
                        }
                        merChantAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(httpUtils);


        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
    }


    /**
     * @author dragon
     *         listView的适配器
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

            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag(position);
            }


            //给控件写入信息
            viewHolder.image.setImageUrl(utils.BASE + merChantModel.getImage(), AppController.getInstance().getImageLoader());
            viewHolder.adress.setText(merChantModel.getAddress());
            viewHolder.bname.setText(merChantModel.getBname());
            viewHolder.level.setText(merChantModel.getLevel());

            return view;
        }

    }

    /**
     * @author dragon
     * 承载listView控件的类
     */
    class ViewHolder {
        public NetworkImageView image;
        public TextView bname;
        public TextView adress;
        public TextView level;
    }
}
