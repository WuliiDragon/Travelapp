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
import wlxy.com.travelapp.model.TicketModel;

/**
 * @author dragon
 * @date 2017/11/22
 */

public class TicketFragment extends Fragment {
    private ListView ticketListView;
    private ListView merChantListView;
    private final int RIGHTSTATUS = 200;
    private ArrayList<TicketModel> ticketModelList;
    private TicketAdapter ticketAdapter;

    public void setTicketAdapter(TicketAdapter ticketAdapter) {
        this.ticketAdapter = ticketAdapter;
    }

    public TicketAdapter getTicketAdapter() {
        return ticketAdapter;
    }

    /**
     * 静态工厂方法需要一个int型的值来初始化fragment的参数，
     * 然后返回新的fragment到调用者
     */

    public static TicketFragment newInstance(ArrayList<TicketModel> ticketModelList) {
        TicketFragment f = new TicketFragment();
        Bundle args = new Bundle();
        args.putSerializable("ticketModelList", ticketModelList);
        f.setArguments(args);
        return f;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle args = new Bundle();
        ticketModelList = (ArrayList<TicketModel>) getArguments().getSerializable("ticketModelList");

        View view = inflater.inflate(R.layout.home_layout, container, false);
        ticketListView = (ListView) view.findViewById(R.id.merchant_listView);

        ticketAdapter = new TicketAdapter(getActivity(), R.layout.item_ticket, ticketModelList);
        ticketListView.setAdapter(ticketAdapter);
        return view;

    }
}
