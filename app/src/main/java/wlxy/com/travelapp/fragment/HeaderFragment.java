package wlxy.com.travelapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import wlxy.com.travelapp.OnRecyclerItemClickListener;
import wlxy.com.travelapp.PurchaseActivity;
import wlxy.com.travelapp.R;
import wlxy.com.travelapp.adapter.Adapter;
import wlxy.com.travelapp.control.Requester;
import wlxy.com.travelapp.model.Model;
import wlxy.com.travelapp.utils.HttpUtils;
import wlxy.com.travelapp.utils.utils;

public class HeaderFragment extends Fragment {
    private ImageView mybuy;
    private ArrayList<Model> dayList;
    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_header, container, false);
//        mybuy = (ImageView) view.findViewById(R.id.mybuy);
        Banner banner = (Banner) view.findViewById(R.id.banner);
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        });
        //zyl加的
        //设置图片集合
        ArrayList images = new ArrayList();
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508064094593&di=2d88c2bfd7f04b3232e5b627f4584bc2&imgtype=0&src=http%3A%2F%2Fepaper.sxncb.com%2Fsxncb%2F20150415%2Fhtml%2Fimg_355_888_209_126.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508064197672&di=c154b806694b7365ca3a2b70fe93295d&imgtype=0&src=http%3A%2F%2Fwww.xinhuanet.com%2Fphoto%2Ftitlepic%2F111923%2F1119235380_1468810150527_title0h.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508064434024&di=45e0db6230dd86d2fe6b44584c364523&imgtype=0&src=http%3A%2F%2Fi3.sinaimg.cn%2Fent%2Fy%2Fp%2F2009-07-01%2FU1819P28T3D2590250F346DT20090701012140.jpg");
        banner.setImages(images);
        //banner设置方法全部调用完毕时最后调用
        banner.start();


        //recycleView实现
        mRecyclerView = (RecyclerView) view.findViewById(R.id.local_info);
        mRecyclerView.setHasFixedSize(true);
        //设置recycleView的布局方式
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        dayList = new ArrayList<Model>();


        getData();
        return view;
    }
    //获取数据
    public void getData() {

        HttpUtils js = new HttpUtils(utils.BASE + "Business/findAll.action?page=" + 1, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int status = response.getInt("status");
                    if (status == 200) {
                        JSONObject data = response.getJSONObject("data");
                        //从data中取list数组
                        JSONArray list = data.getJSONArray("list");
                        for (int i = 0; i < list.length(); i++) {
                            //取出对应i位置的对象
                            JSONObject js = (JSONObject) list.get(i);
                            //将bnam
                            String bname = js.getString("bname");
                            Model model = new Model();
                            model.setStore_name(bname);
                            dayList.add(model);
                        }
                        mRecyclerView.setAdapter(new Adapter(dayList, HeaderFragment.this, new OnRecyclerItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(getActivity(),PurchaseActivity.class);
//                                position代表的是recycleView中的某一行
                                Model model = dayList.get(position);
                                Log.v("position", position + "");
                                intent.putExtra("name", model.getStore_name());
                                startActivity(intent);

                            }
                        }));

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
        Requester.getInstance(getContext()).addToRequestQueue(js);
    }
}







