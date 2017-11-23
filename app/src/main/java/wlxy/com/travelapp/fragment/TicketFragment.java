package wlxy.com.travelapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import wlxy.com.travelapp.R;
import wlxy.com.travelapp.adapter.MerChantAdapter;
import wlxy.com.travelapp.adapter.TicketAdapter;
import wlxy.com.travelapp.model.MerChantModel;

/**
 *
 * @author dragon
 * @date 2017/11/22
 */

public class TicketFragment extends Fragment {
    private ListView ticketListView;
    private ListView merChantListView;
    private final int RIGHTSTATUS = 200;
    private ArrayList<MerChantModel> merChantModelList;
    private TicketAdapter ticketAdapter;

//    public static TicketFragment newInstance(String name, string passwd) {
//        TicketFragment newFragment = new TicketFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("name", name);
//        bundle.putString("passwd", passwd);
//        newFragment.setArguments(bundle);
//
//        return newFragment;
//
//    }


//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.home_layout, container, false);
//        ticketListView = (ListView) view.findViewById(R.id.merchant_listView);
//        ticketAdapter = new TicketAdapter();
//
//
//        return;
//
//
//    }
}
