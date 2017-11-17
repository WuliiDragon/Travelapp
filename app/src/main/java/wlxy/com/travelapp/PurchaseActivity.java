package wlxy.com.travelapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by guardian on 2017/10/16.
 */

public class PurchaseActivity extends AppCompatActivity {
    private Button purchase;
    private ImageView liftback;
    private TextView sname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_purchase);
        Intent intent=getIntent();
        String name=intent.getStringExtra("name");
        sname= (TextView) findViewById(R.id.sname);
        sname.setText(name);
        purchase= (Button) findViewById(R.id.purchase);
        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PurchaseActivity.this,"购买成功",Toast.LENGTH_SHORT).show();
            }
        });
//        liftback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

    }
}
