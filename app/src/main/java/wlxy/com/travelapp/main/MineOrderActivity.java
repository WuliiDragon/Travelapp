package wlxy.com.travelapp.main;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import wlxy.com.travelapp.R;
import wlxy.com.travelapp.adapter.MineOrderAdapter;
import wlxy.com.travelapp.model.MineOrderModel;
import wlxy.com.travelapp.utils.AppController;
import wlxy.com.travelapp.utils.HttpUtils;
import wlxy.com.travelapp.utils.utils;

/**
 * @author dragon
 * @date 2017/11/28
 */

public class MineOrderActivity extends BaseActivity {


    private ListView orderListView;
    private SwipeRefreshLayout orderListViewRefresh;
    private SharedPreferences sharedPreferences;
    private ArrayList<MineOrderModel> mineOrderModelArrayList;
    private MineOrderAdapter mineOrderAdapter;
    private int currPageNum;
    private StringBuffer parSb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mine_order_layout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            //给状态栏设置颜色。我设置透明。
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }

        orderListViewRefresh = (SwipeRefreshLayout) findViewById(R.id.order_list_view_refresh);
        orderListView = (ListView) findViewById(R.id.order_list_view);


        sharedPreferences = getSharedPreferences("info", MODE_PRIVATE);
        String uid = sharedPreferences.getString("uid", "");
        String token = sharedPreferences.getString("token", "");
        String phone = sharedPreferences.getString("phone", "");
        parSb = new StringBuffer();
        parSb.append("?uid=" + uid);
        parSb.append("&token=" + token);
        parSb.append("&phone=" + phone);


        mineOrderModelArrayList = new ArrayList<>();
        mineOrderAdapter = new MineOrderAdapter(MineOrderActivity.this, R.layout.item_order, mineOrderModelArrayList);
        orderListView.setAdapter(mineOrderAdapter);


        HttpUtils httpUtils = new HttpUtils(utils.BASE + "/order/findOrder.action" + parSb.toString(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int status = response.getInt("status");
                    if (status == utils.RIGHTSTATUS) {
                        JSONObject data = response.getJSONObject("data");
                        currPageNum = data.getInt("pageNum");
                        JSONArray list = data.getJSONArray("list");
                        for (int i = 0; i < list.length(); i++) {
                            JSONObject item = (JSONObject) list.get(i);
                            MineOrderModel mineOrderModel = JSON.parseObject(item.toString(), MineOrderModel.class);
                            mineOrderModelArrayList.add(mineOrderModel);
                        }
                        mineOrderAdapter.notifyDataSetChanged();
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


        orderListViewRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!orderListViewRefresh.isRefreshing()) {
                    return;
                }

                orderListViewRefresh.setRefreshing(true);
                currPageNum++;
                parSb.append("&page=" + currPageNum);
                HttpUtils httpUtils = new HttpUtils(utils.BASE + "/order/findOrder.action" + parSb.toString(), null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int status = response.getInt("status");
                            if (status == utils.RIGHTSTATUS) {
                                JSONObject data = response.getJSONObject("data");
                                JSONArray list = data.getJSONArray("list");
                                for (int i = 0; i < list.length(); i++) {
                                    JSONObject item = (JSONObject) list.get(i);
                                    MineOrderModel mineOrderModel = JSON.parseObject(item.toString(), MineOrderModel.class);
                                    mineOrderModelArrayList.add(mineOrderModel);
                                }
                                mineOrderAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        orderListViewRefresh.setRefreshing(false);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        orderListViewRefresh.setRefreshing(false);
                    }
                });
                AppController.getInstance().addToRequestQueue(httpUtils);
            }
        });
    }
}
//http://172.16.120.129:8080/userTicket/findbyOid.action?oid=0be59dadc6184500ad6a40a5a9776a07