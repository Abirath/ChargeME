package com.example.chargeme;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void loadOnlyOneTileForOneInput_RecyclerView()
    {
        MainActivity activity =new MainActivity();
        ArrayList<LocationResponse> locationList = new ArrayList<>();
        locationList.add(new LocationResponse(R.drawable.ic_ev_station, "Title 1", "Address 1"));
        activity.LoadRecyclerView(locationList);

        //assertThat()isEqualTo(1);
    }
}