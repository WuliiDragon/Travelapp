package wlxy.com.travelapp.fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
    private MerChantAdapter merChantAdapter;

    //统计下载了几张图片
    int n = 0;
    //统计当前viewpager轮播到第几页
    int p = 0;
    private ViewPager viewPager;
    //准备好三张网络图片的地址
    private String imageUrl[] = new String[]{"http://172.16.120.129:8080/img/2017-11/a.jpg",
            "http://172.16.120.129:8080/img/2017-11/b.jpg",
            "http://172.16.120.129:8080/img/2017-11/c.jpg"};
    //装载下载图片的集合
    private List<ImageView> data;
    //控制图片是否开始轮播的开关,默认关的
    private boolean isStart = false;
    //开始图片轮播的线程
    private MyThread t;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    n++;
                    Bitmap bitmap = (Bitmap) msg.obj;
                    ImageView iv = new ImageView(getActivity());
                    iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    iv.setImageBitmap(bitmap);
                    //把图片添加到集合里
                    data.add(iv);
                    //当接收到第三张图片的时候，设置适配器,
                    if (n == imageUrl.length) {
                        viewPager.setAdapter(new CarouselAdapter(data, getActivity()));
                        //把开关打开
                        isStart = true;
                        t = new MyThread();
                        //启动轮播图片线程
                        t.start();
                    }
                    break;
                case 1:
                    //接受到的线程发过来的p数字
                    int page = (Integer) msg.obj;
                    viewPager.setCurrentItem(page);
                    break;
            }
        }

        ;
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.home_layout, container, false);
        merChantListView = (ListView) view.findViewById(R.id.merchant_listView);
        merChantModelList = new ArrayList<MerChantModel>();
        merChantAdapter = new MerChantAdapter(getActivity(), R.layout.merchant_tem, merChantModelList);
        merChantListView.setAdapter(merChantAdapter);
        View v = inflater.inflate(R.layout.view_page, container, false);
        viewPager = (ViewPager) v.findViewById(R.id.vp);
        merChantListView.addHeaderView(v);
        //构造一个存储照片的集合
        data = new ArrayList<ImageView>();
        //从网络上把图片下载下来
        for (int i = 0; i < imageUrl.length; i++) {
            getImageFromNet(imageUrl[i]);
        }

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

    private void getImageFromNet(final String imagePath) {
        new Thread() {
            public void run() {
                try {
                    URL url = new URL(imagePath);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setConnectTimeout(10 * 1000);
                    InputStream is = con.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    Message message = new Message();
                    message.what = 0;
                    message.obj = bitmap;
                    mHandler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            ;
        }.start();

    }

    //控制图片轮播
    class MyThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (isStart) {
                Message message = new Message();
                message.what = 1;
                message.obj = p;
                mHandler.sendMessage(message);
                try {
                    //睡眠3秒,在isStart为真的情况下，一直每隔三秒循环
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                p++;
            }
        }
    }

}
