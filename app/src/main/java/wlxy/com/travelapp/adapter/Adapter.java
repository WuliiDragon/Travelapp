package wlxy.com.travelapp.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.util.LruCache;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import wlxy.com.travelapp.fragment.HeaderFragment;
import wlxy.com.travelapp.model.Model;
import wlxy.com.travelapp.OnRecyclerItemClickListener;
import wlxy.com.travelapp.R;

/**
 * Created by guardian on 2017/10/15.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<Model> data;
    //LruCache相当于Map，key就是该图片的url，value就是该图片
    private LruCache<String, BitmapDrawable> mImageCache;
    private RecyclerView recyclerView;
    private OnRecyclerItemClickListener onRecyclerItemClickListener;

    public Adapter(List<Model> data, HeaderFragment context, OnRecyclerItemClickListener onRecyclerItemClickListener) {

        this.onRecyclerItemClickListener = onRecyclerItemClickListener;

        this.data = data;
        //在实例化LruCache的时候，我们需要传入一个参数，表明我们可以使用的最大缓存，这个缓存参数我们传入可用缓存的1/8
        int maxCache = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxCache / 8;
        mImageCache = new LruCache<String, BitmapDrawable>(cacheSize) {
            @Override
            //用sizeof()返回每个图片的大小
            protected int sizeOf(String key, BitmapDrawable value) {
                return value.getBitmap().getByteCount();
            }
        };
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (recyclerView == null) {
            recyclerView = (RecyclerView) parent;
        }
        final View view = (LayoutInflater.from(parent.getContext()).inflate(R.layout.scenic_list_item, parent, false));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRecyclerItemClickListener != null) {
                    onRecyclerItemClickListener.onItemClick(view, (int) view.getTag());
                }
            }
        });


        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
//
//    return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.scenic_list_item, parent, false));
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //获取Model(实例
        Model Model = data.get(position);
        holder.store_name.setText(Model.getStore_name());
        holder.itemView.setTag(position);

//        holder.ImageView.setTag(Model.getImgurl());
        // 如果本地已有缓存，就从本地读取，否则从网络请求数据
//        if (mImageCache.get(Model.getImgurl()) != null) {
//            holder.ImageView.setImageDrawable(mImageCache.get(Model.getImgurl()));
//        } else {
//            ImageTask it = new ImageTask();
//            it.execute(Model.getImgurl());
//        }

    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return data.size();
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView store_name;

        public ViewHolder(View itemView) {
            super(itemView);
            //获取实例
            store_name = (TextView) itemView.findViewById(R.id.store_name);

//            ImageView = (ImageView) itemView.findViewById(R.id.imageView1);


        }
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    class ImageTask extends AsyncTask<String, Void, BitmapDrawable> {
        private String imageUrl;


        @Override
        protected BitmapDrawable doInBackground(String... params) {
            imageUrl = params[0];
            //从网络上下载图片
            Bitmap bitmap = downloadImage();
            BitmapDrawable db = new BitmapDrawable(bitmap);
            // 如果本地还没缓存该图片，就缓存
            if (mImageCache.get(imageUrl) == null) {
                mImageCache.put(imageUrl, db);
            }
            return db;
        }

        //在从网络请求图片的时候，为了防止发生图片错位的情况，
        // 我们要给每一个item的每一个ImageView设置一个tag，
        // 这个tag就使用该ImageView要加载的图片的url（这样就可以确保每一个ImageView唯一），
        // 在给ImageView设置图片的时候我们就可以通过这个tag找到我们需要的ImageView，这样可以有效避免图片错位的问题
        @Override
        protected void onPostExecute(BitmapDrawable result) {
            // 通过Tag找到我们需要的ImageView，如果该ImageView所在的item已被移出页面，就会直接返回null
            ImageView iv = (ImageView) recyclerView.findViewWithTag(imageUrl);
            if (iv != null && result != null) {
                iv.setImageDrawable(result);
            }
        }

        //根据url从网络上下载图片
        private Bitmap downloadImage() {
            HttpURLConnection con = null;
            Bitmap bitmap = null;
            try {
                URL url = new URL(imageUrl);
                con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(5 * 1000);
                con.setReadTimeout(10 * 1000);
                bitmap = BitmapFactory.decodeStream(con.getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (con != null) {
                    con.disconnect();
                }
            }
            return bitmap;
        }
    }
}