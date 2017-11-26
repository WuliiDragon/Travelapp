package wlxy.com.travelapp.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wlxy.com.travelapp.R;

/**
 * Created by WLW on 2017/11/25.
 */

public class OrderActivity extends AppCompatActivity {

    private TextView orderOid;
    private TextView orderStatus;
    private TextView orderCreateTime;
    private TextView orderUid;
    private TextView orderBid;
    private TextView orderTotalprice;
    private Button orderPay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_layout);


        orderOid = (TextView) findViewById(R.id.order_oid);
        orderStatus = (TextView) findViewById(R.id.order_status);
        orderCreateTime = (TextView) findViewById(R.id.order_createTime);
        orderUid = (TextView) findViewById(R.id.order_uid);
        orderBid = (TextView) findViewById(R.id.order_bid);
        orderTotalprice = (TextView) findViewById(R.id.order_totalprice);
        orderPay = (Button) findViewById(R.id.order_pay);


        orderOid.setText(getIntent().getStringExtra("oid"));
        orderStatus.setText(getIntent().getStringExtra("status"));
        orderCreateTime.setText(getIntent().getStringExtra("createTime"));
        orderUid.setText(getIntent().getStringExtra("uid"));
        orderBid.setText(getIntent().getStringExtra("bid"));
        orderTotalprice.setText(getIntent().getStringExtra("totalprice"));

        orderPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


    }


}
