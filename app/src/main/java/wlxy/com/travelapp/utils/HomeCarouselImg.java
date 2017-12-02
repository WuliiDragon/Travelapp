package wlxy.com.travelapp.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.ListView;

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
import wlxy.com.travelapp.adapter.HomeCarouselAdapter;

/**
 * Created by DT on 2017/11/19.
 */

public class HomeCarouselImg {
    int n = 0;
    int p = 0;
    private ViewPager viewPager;
    private ListView merChantListView;
    FragmentActivity activity;
    private String[] imageUrl;
    private List<String> bid;
    private LayoutInflater inflater;
    private List<ImageView> data;
    private boolean isStart = false;
    private MyThreads t;
    private final int RIGHTSTATUS = 200;

    public HomeCarouselImg(ViewPager viewPager, ListView merChantListView, FragmentActivity activity, LayoutInflater inflater) {
        this.inflater = inflater;
        this.viewPager = viewPager;
        this.merChantListView = merChantListView;
        this.activity = activity;
    }

    public void init() {
        data = new ArrayList<ImageView>();
        bid = new ArrayList<String>();
        //从网络上把图片下载下来
        HttpUtils httpUtil = new HttpUtils(utils.BASE + "/businessCarousel/findAll.action", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int status = response.getInt("status");
                    if (status == RIGHTSTATUS) {
                        JSONArray list = response.getJSONArray("data");
                        imageUrl = new String[list.length()];
                        for (int i = 0; i < list.length(); i++) {
                            JSONObject item = (JSONObject) list.get(i);
                            String imgPath = utils.BASE + item.getString("imgpath");
                            bid.add(item.getString("bid"));
                            imageUrl[i] = imgPath;
                        }
                        for (int i = 0; i < imageUrl.length; i++) {
                            getImageFromNet(imageUrl[i]);
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
    }


    class MyThreads extends Thread {
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

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    n++;
                    Bitmap bitmap = (Bitmap) msg.obj;
                    ImageView iv = new ImageView(activity);
                    iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    iv.setImageBitmap(bitmap);
                    data.add(iv);
                    if (n == imageUrl.length) {
                        HomeCarouselAdapter ca = new HomeCarouselAdapter(data, activity);
                        ca.bid = bid;
                        viewPager.setAdapter(ca);
                        merChantListView.addHeaderView(viewPager);
                        merChantListView.addHeaderView(inflater.inflate(R.layout.home_fragment_layout, null));
                        isStart = true;
                        t = new MyThreads();
                        t.start();
                    }
                    break;
                case 1:
                    int page = (Integer) msg.obj;
                    viewPager.setCurrentItem(page);
                    break;
            }
        }
    };

    private void getImageFromNet(final String imagePath) {
        new Thread() {
            @Override
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
        }.start();


    }
}
