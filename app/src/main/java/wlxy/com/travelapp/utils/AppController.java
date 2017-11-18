package wlxy.com.travelapp.utils;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by WLW on 2017/11/18.
 *
 * @author dragon
 * 获取Application的单例
 */

public class AppController extends Application {
    public static final String TAG = AppController.class.getSimpleName();
    private static RequestQueue mRequestQueue;
    private static ImageLoader mImageLoader;
    private static AppController mInstance;


    @Override
    public void onCreate() {
        mInstance = this;
        super.onCreate();

    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }


    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    /**
     * 图片加载器
     */
    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {

            //还初始化缓存
            mImageLoader = new ImageLoader(mRequestQueue, new LruBitmapCache());
        }
        return mImageLoader;
    }


    /**
     * 请求加入队列
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    /**
     * 取消请求
     */
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
