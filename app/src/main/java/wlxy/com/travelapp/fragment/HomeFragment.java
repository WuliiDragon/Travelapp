package wlxy.com.travelapp.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import wlxy.com.travelapp.R;
import wlxy.com.travelapp.adapter.MerChantAdapter;
import wlxy.com.travelapp.main.MerChantDetailActivity;
import wlxy.com.travelapp.model.MerChantModel;
import wlxy.com.travelapp.utils.AppController;
import wlxy.com.travelapp.utils.HomeCarouselImg;
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
    private MerChantAdapter merChantAdapter;
    private ViewPager viewPager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("RightFragment", "onAttach");
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_layout, container, false);

        merChantListView = (ListView) view.findViewById(R.id.merchant_listView);
        merChantModelList = new ArrayList<MerChantModel>();
        merChantAdapter = new MerChantAdapter(getActivity(), R.layout.item_merchant, merChantModelList);
        merChantListView.setAdapter(merChantAdapter);
        View view_page = inflater.inflate(R.layout.view_page, container, false);
        viewPager = (ViewPager) view_page.findViewById(R.id.vp);
        //初始化轮播图
        new HomeCarouselImg(viewPager, merChantListView, getActivity(),inflater).init();
        //listView设置点击事件
        merChantListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MerChantModel info = merChantModelList.get(position - 2);
                Bundle bundle = new Bundle();
                bundle.putString("bid", info.getBid());
                Intent intent = new Intent(getActivity(), MerChantDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        //获取listView数据
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
        Log.d("RightFragment", "onCreateView");
        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("RightFragment", "onCreate");
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("RightFragment", "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("RightFragment", "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("RightFragment", "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("RightFragment", "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("RightFragment", "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("RightFragment", "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("RightFragment", "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("RightFragment", "onDetach");
    }


}