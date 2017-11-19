package wlxy.com.travelapp.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import wlxy.com.travelapp.R;
import wlxy.com.travelapp.adapter.CarouselAdapter;
import wlxy.com.travelapp.adapter.MerChantAdapter;
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
    ViewPager viewPager;
    private Timer timer = new Timer();
    private int autoCurrIndex = 0;
    private Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.home_layout, container, false);
        merChantListView = (ListView) view.findViewById(R.id.merchant_listView);
        merChantModelList = new ArrayList<MerChantModel>();
        merChantAdapter = new MerChantAdapter(getActivity(), R.layout.merchant_tem, merChantModelList);
        merChantListView.setAdapter(merChantAdapter);
        View v = inflater.inflate(R.layout.view_page, container, false);
        viewPager = (ViewPager) v.findViewById(R.id.headviewpager);
        CarouselAdapter carouselAdapter = new CarouselAdapter();
        final List<ImageView> imageViewList = new ArrayList<ImageView>();
        //发送HTTP请求请求轮播图集合
        HttpUtils httpUtil = new HttpUtils(utils.BASE + "/businessCarousel/findAll.action", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int status = response.getInt("status");
                    if (status == RIGHTSTATUS) {
                        JSONArray list = response.getJSONArray("data");
                        for (int i = 0; i < list.length(); i++) {
                            JSONObject item = (JSONObject) list.get(i);
                            final String imgPath = item.getString("imgpath");
                            System.out.println(imgPath + "  imgPath");
                            Log.d("imgPath", imgPath);
                           /* ImageView nv = new ImageView(view.getContext());
                            Bitmap bm = new ImageHttp().getImageBitMap(imgPath);
                            nv.setImageBitmap(bm);
                            imageViewList.add(nv);*/
                        }
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
        AppController.getInstance().addToRequestQueue(httpUtil);

        //给viewPager中的Adapter设置轮播图集合
        carouselAdapter.images = imageViewList;
        //给viewPager中设置Adpapter
        viewPager.setAdapter(carouselAdapter);
        merChantListView.addHeaderView(viewPager);
        //轮播图数量
        final int num = imageViewList.size();
        System.out.println("num:" + num);
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

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                if (autoCurrIndex == num) {
                    autoCurrIndex = 0;
                } else {
                    autoCurrIndex++;
                }
                message.arg1 = autoCurrIndex;
                mHandler.sendMessage(message);
            }

            private Handler mHandler = new Handler() {
                public void handleMessage(Message msg) {
                    if (msg.arg1 != 0) {
                        viewPager.setCurrentItem(msg.arg1);
                    } else {
                        //false 当从末页调到首页是，不显示翻页动画效果，
                        viewPager.setCurrentItem(msg.arg1, true);
                    }
                }
            };
        }, 3000, 2000);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }


}
