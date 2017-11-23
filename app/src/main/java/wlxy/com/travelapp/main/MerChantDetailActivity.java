package wlxy.com.travelapp.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;


import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import wlxy.com.travelapp.R;
import wlxy.com.travelapp.adapter.MerChantDetailAdapter;
import wlxy.com.travelapp.adapter.TicketAdapter;
import wlxy.com.travelapp.fragment.MineFragment;
import wlxy.com.travelapp.fragment.TicketFragment;
import wlxy.com.travelapp.model.MerChantModel;
import wlxy.com.travelapp.model.TicketModel;
import wlxy.com.travelapp.utils.AppController;
import wlxy.com.travelapp.utils.HttpUtils;
import wlxy.com.travelapp.utils.utils;

/**
 * @author Dragon
 * @date 2017/11/22
 */

public class MerChantDetailActivity extends AppCompatActivity {
    private TabLayout tabBarTitle;
    private ViewPager tabBarViewPager;
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


        HttpUtils request = new HttpUtils(utils.BASE + "/business/findById.action?bid=" + "2511150102", null, new Response.Listener<JSONObject>() {
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
