package wlxy.com.travelapp.main;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import wlxy.com.travelapp.R;
import wlxy.com.travelapp.adapter.OrderInfoAdapter;
import wlxy.com.travelapp.model.UserTicketModel;
import wlxy.com.travelapp.utils.AppController;
import wlxy.com.travelapp.utils.HttpUtils;
import wlxy.com.travelapp.utils.utils;

import static wlxy.com.travelapp.utils.utils.getFilter;

/**
 * @author dragon
 * @date 2017/11/30
 */

public class MineOrderInfoActivity extends BaseActivity {


    private String oid;
    private OrderInfoAdapter orderInfoAdapter;
    private ListView orderInfoListView;
    private ArrayList<UserTicketModel> userTicketModelArrayList;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_info_layout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            //给状态栏设置颜色。我设置透明。
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }


        oid = getIntent().getStringExtra("oid");


        userTicketModelArrayList = new ArrayList<UserTicketModel>();
        orderInfoListView = (ListView) findViewById(R.id.order_info_list);
        orderInfoAdapter = new OrderInfoAdapter(MineOrderInfoActivity.this, R.layout.item_mine_ticket, userTicketModelArrayList);
        orderInfoListView.setAdapter(orderInfoAdapter);


        //orderInfoListView.addHeaderView(getLayoutInflater().inflate(R.layout.order_info_header, null));


        String par = getFilter(getSharedPreferences("info", MODE_PRIVATE));
        if ("error".equals(par)) {
            Toast.makeText(MineOrderInfoActivity.this, "登录过期，请重新登录", Toast.LENGTH_SHORT);
        }

        AppController.getInstance().addToRequestQueue(new HttpUtils(utils.BASE + "/userTicket/findbyOid.action?oid=" + oid + par, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int status = response.getInt("status");

                    if (status == utils.RIGHTSTATUS) {
                        JSONObject data = response.getJSONObject("data");
                        JSONArray list = data.getJSONArray("list");

                        for (int i = 0; i < list.length(); i++) {
                            JSONObject item = (JSONObject) list.get(i);
                            JSONObject ticket = item.getJSONObject("ticket");
                            JSONObject userTicket = item.getJSONObject("userTicket");

                            String tname = ticket.getString("tname");
                            String info = ticket.getString("info");
                            String code = userTicket.getString("code");
                            String num = userTicket.getString("num");


                            UserTicketModel utm = new UserTicketModel();
                            utm.setCode(code);
                            utm.setInfo(info);
                            utm.setTname(tname);
                            utm.setNum(num);

                            userTicketModelArrayList.add(utm);
                        }
                        orderInfoAdapter.notifyDataSetChanged();
                    } else {
                        String msg = response.getString("msg");
                        Toast.makeText(MineOrderInfoActivity.this, msg, Toast.LENGTH_SHORT);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));


    }
}
