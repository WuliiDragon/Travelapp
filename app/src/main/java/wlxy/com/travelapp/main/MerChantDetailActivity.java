package wlxy.com.travelapp.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import wlxy.com.travelapp.R;
import wlxy.com.travelapp.adapter.MerChantDetailAdapter;
import wlxy.com.travelapp.fragment.MineFragment;
import wlxy.com.travelapp.fragment.TicketFragment;
import wlxy.com.travelapp.model.TicketModel;
import wlxy.com.travelapp.model.TicketOrderModel;
import wlxy.com.travelapp.utils.AppController;
import wlxy.com.travelapp.utils.BusinessCarouselImg;
import wlxy.com.travelapp.utils.HttpUtils;
import wlxy.com.travelapp.utils.utils;

/**
 * @author Dragon
 * @date 2017/11/22
 */

public class MerChantDetailActivity extends BaseActivity {
    private TabLayout tabBarTitle;
    private ViewPager tabBarViewPager;
    private ViewPager CarouseVp;
    private String bid;
    private ArrayList<TicketModel> ticketModelList;

    private ArrayList<String> titleList = new ArrayList<String>() {{
        add("门票");
        add("详情须知");
        add("纪念品");
    }};

    private ArrayList<Fragment> fragmentList;
    private MerChantDetailAdapter merChantDetailAdapter;


    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        bid = intent.getStringExtra("bid");
        setContentView(R.layout.merchant_detail_layout);
        this.TAG = "MerChantDetailActivity";


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            //给状态栏设置颜色。我设置透明。
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }


        FloatingActionButton mButton = (FloatingActionButton) findViewById(R.id.fab);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<TicketOrderModel> orderModelArrayList = new ArrayList<>();
                float totalprice = 0;
                for (TicketModel tm : ticketModelList) {
                    if (tm.getCount() > 0) {
                        totalprice += Float.parseFloat(tm.getPrice()) * tm.getCount();
                        TicketOrderModel ticketOrderModel = new TicketOrderModel();
                        ticketOrderModel.setTid(tm.getTid());
                        ticketOrderModel.setNum(tm.getCount());
                        ticketOrderModel.setPrice(tm.getPrice());
                        orderModelArrayList.add(ticketOrderModel);
                    }
                }
                if (orderModelArrayList.size() == 0) {
                    Toast.makeText(MerChantDetailActivity.this, "您还未选择任何门票哦！", Toast.LENGTH_LONG).show();
                    return;
                }

                SharedPreferences sharedPreferences = getSharedPreferences("info", MODE_PRIVATE);
                if (sharedPreferences.getString("token", "").equals("null") || sharedPreferences.getString("token", "").equals("")) {
                    Intent intent = new Intent(MerChantDetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                    return;
                }

                int i = 0;
                StringBuffer sb = new StringBuffer();
                for (TicketOrderModel tm : orderModelArrayList) {
                    sb.append("&userTicket[" + i + "].num=" + tm.getNum());
                    sb.append("&userTicket[" + i + "].tid=" + tm.getTid());
                    sb.append("&userTicket[" + i + "].price=" + tm.getPrice());
                    i++;
                }
                sb.append("&totalprice=" + totalprice);
                sb.append("&uid=" + sharedPreferences.getString("uid", ""));
                sb.append("&phone=" + sharedPreferences.getString("phone", ""));
                sb.append("&token=" + sharedPreferences.getString("token", ""));
                sb.append("&bid=" + bid);

                HttpUtils request = new HttpUtils(Request.Method.POST, utils.BASE + "/order/createOrder.action?" + sb.toString(), null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            if (status.equals("200")) {
                                JSONObject data = response.getJSONObject("data");
                                JSONObject order = data.getJSONObject("order");


                                Intent intent = new Intent(MerChantDetailActivity.this, OrderActivity.class);

                                intent.putExtra("oid", order.getString("oid"));
                                intent.putExtra("status", order.getString("status"));
                                intent.putExtra("createTime", order.getString("createTime"));
                                intent.putExtra("payTime", order.getString("payTime"));
                                intent.putExtra("uid", order.getString("uid"));
                                intent.putExtra("bid", order.getString("bid"));
                                intent.putExtra("totalprice", order.getString("totalprice"));
                                startActivity(intent);

                            } else {
                                String msg = response.getString("msg");
                                Toast.makeText(MerChantDetailActivity.this, msg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MerChantDetailActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });

                AppController.getInstance().addToRequestQueue(request);
            }
        });


        tabBarTitle = (TabLayout) findViewById(R.id.tab_title);
        tabBarViewPager = (ViewPager) findViewById(R.id.tab_view_pager);
        ticketModelList = new ArrayList<TicketModel>();

        fragmentList = new ArrayList<Fragment>() {{
            add(TicketFragment.newInstance(ticketModelList));
            add(new MineFragment());
            add(new MineFragment());
        }};


        //viewpager的适配
        merChantDetailAdapter = new MerChantDetailAdapter(getSupportFragmentManager(), titleList, fragmentList);
        tabBarViewPager.setAdapter(merChantDetailAdapter);
        tabBarTitle.setupWithViewPager(tabBarViewPager, true);
        tabBarTitle.setTabMode(TabLayout.MODE_FIXED);
        CarouseVp = (ViewPager) findViewById(R.id.CarouseVp);
        new BusinessCarouselImg(CarouseVp, this, bid).init();

        HttpUtils request = new HttpUtils(utils.BASE + "/business/findById.action?bid=" + bid, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("status");
                    if (status.equals("200")) {
                        JSONObject data = response.getJSONObject("data");
                        JSONArray ticket = data.getJSONArray("ticket");

                        for (int i = 0; i < ticket.length(); i++) {
                            JSONObject item = (JSONObject) ticket.get(i);
                            TicketModel ticketModel = JSON.parseObject(item.toString(), TicketModel.class);
                            ticketModel.setCount(0);
                            ticketModelList.add(ticketModel);
                        }

                        TicketFragment tf = (TicketFragment) fragmentList.get(0);
                        tf.getTicketAdapter().notifyDataSetChanged();
                    } else {
                        String msg = response.getString("msg");
                        Toast.makeText(MerChantDetailActivity.this, msg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MerChantDetailActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        AppController.getInstance().addToRequestQueue(request);

    }
}