package wlxy.com.travelapp.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;

import wlxy.com.travelapp.R;
import wlxy.com.travelapp.adapter.MerChantDetailAdapter;
import wlxy.com.travelapp.adapter.TicketAdapter;
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

public class MerChantDetailActivity extends AppCompatActivity {
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        bid = intent.getStringExtra("bid");
        setContentView(R.layout.merchant_detail_layout);


        FloatingActionButton mButton = (FloatingActionButton) findViewById(R.id.fab);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MerChantDetailActivity.this, "" + ticketModelList.toString(), Toast.LENGTH_LONG).show();
                ArrayList<TicketOrderModel> orderModelArrayList = new ArrayList<>();
                for (TicketModel tm : ticketModelList) {
                    TicketOrderModel ticketOrderModel = new TicketOrderModel();
                    ticketOrderModel.setTid(tm.getTid());
                    ticketOrderModel.setNum(tm.getCount());
                    orderModelArrayList.add(ticketOrderModel);
                }


                HashMap<String, String> par = new HashMap<>(2);


                par.put("bid", bid);
//                par.put("userTicket");


                HttpUtils request = new HttpUtils(Request.Method.POST, utils.BASE + "/order/createOrder.action", par, new Response.Listener<JSONObject>() {
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


//                usicket[0].num=2&userTicket[0].tid=2511150103erT&userTicket[1].num=2&userTicket[1].tid=2511150104
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