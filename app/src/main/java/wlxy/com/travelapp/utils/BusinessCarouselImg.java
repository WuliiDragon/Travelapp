package wlxy.com.travelapp.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

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

import wlxy.com.travelapp.adapter.BusinessCarouselAdapter;

/**
 * Created by DT on 2017/11/19.
 */

public class BusinessCarouselImg {
    int n = 0;
    int p = 0;
    private ViewPager viewPager;
    FragmentActivity activity;
    private String[] imageUrl;
    private List<ImageView> data;
    private boolean isStart = false;
    private MyThreads t;
    private final int RIGHTSTATUS = 200;
    private String bid;
    private boolean hasImg = false;

    public BusinessCarouselImg(ViewPager viewPager, FragmentActivity activity, String bid) {
        this.viewPager = viewPager;
        this.activity = activity;
        this.bid = bid;
    }

    public void init() {
        data = new ArrayList<ImageView>();
        //从网络上把图片下载下来
        HttpUtils httpUtil = new HttpUtils(utils.BASE + "/businessImg/findAll.action" + "?bid=" + bid, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int status = response.getInt("status");
                    if (status == RIGHTSTATUS) {
                        JSONArray list = response.getJSONArray("data");
                        imageUrl = new String[list.length()];
                        for (int i = 0; i < list.length(); i++) {
                            JSONObject item = (JSONObject) list.get(i);
                            String imgPath = utils.BASE + item.getString("imgPath");
                            imageUrl[i] = imgPath;
                        }
                        for (int i = 0; i < imageUrl.length; i++) {
                            getImageFromNet(imageUrl[i]);
                        }
                        if (list.length() == 0) {
                            getImageFromNet(utils.BASE + utils.DefImg);
                        } else {
                            hasImg = true;
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
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    n++;
                    Bitmap bitmap = (Bitmap) msg.obj;
                    ImageView iv = new ImageView(activity);
                    iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    iv.setImageBitmap(bitmap);
                    data.add(iv);
                    if (!hasImg) {
                        BusinessCarouselAdapter bca = new BusinessCarouselAdapter(data, activity);
                        viewPager.setAdapter(bca);
                    }
                    if (n == imageUrl.length) {
                        BusinessCarouselAdapter bca = new BusinessCarouselAdapter(data, activity);
                        viewPager.setAdapter(bca);
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
