package wlxy.com.travelapp.model;

import java.io.Serializable;

/**
 * Created by WLW on 2017/11/25.
 */

public class TicketOrderModel implements Serializable {
    private int  num;
    private String tid;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }
}
