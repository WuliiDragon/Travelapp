package wlxy.com.travelapp;

import com.alibaba.fastjson.JSON;

import org.junit.Test;

import wlxy.com.travelapp.fragment.MerChantModel;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        MerChantModel m = new MerChantModel();
        m.setAddress("12");
        m.setBname("bnam3");
        m.setImage("32");

        MerChantModel merChantModel = JSON.parseObject(JSON.toJSONString(m), MerChantModel.class);
        System.out.print(JSON.toJSONString(m));

        assertEquals(4, 2 + 2);
    }
}