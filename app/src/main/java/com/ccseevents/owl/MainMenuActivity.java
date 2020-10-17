package com.ccseevents.owl;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import static android.text.TextUtils.substring;

public class MainMenuActivity extends AppCompatActivity {
    public EventsDatabaseHelper myeventDB = new EventsDatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        //Pull Events from API - CCSE events only for the next year
        //Instructions for pulling events - https://developer.localist.com/doc/api#filter-json
        //   in case in future more departments are to get pulled (changing url)
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, 1);
        String enddt = new SimpleDateFormat( "yyyy-MM-dd" ).format(c.getTime() );
        String url = "https://calendar.kennesaw.edu/api/2/events?group_id=31678879585047&end="+enddt;

        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                parseJsonData(string);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Some error occurred.", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(MainMenuActivity.this);
        rQueue.add(request);

        ImageButton FeaturedEvent = (ImageButton)findViewById(R.id.featuredEventBtn);
        ImageButton EventList = (ImageButton)findViewById(R.id.eventListBtn);
        ImageButton MyEvents = (ImageButton)findViewById(R.id.myEventBtn);
        TextView FeaturedEventTitle = (TextView)findViewById(R.id.FeaturedEventTitle);

        final Cursor res = myeventDB.featuredEvent();
        final MyViewModel myViewModel = new MyViewModel();
        String title = "";
        while (res.moveToNext()) {
            title = res.getString(7);
            myViewModel.setId(res.getInt(0));
            myViewModel.setDayOfWeek(res.getInt(1));
            myViewModel.setDay(res.getString(2));
            myViewModel.setMonth(res.getInt(3));
            myViewModel.setYear(res.getString(4));
            myViewModel.setFromTime(res.getString(5));
            myViewModel.setToTime(res.getString(6));
            myViewModel.setTitle(res.getString(7));
            myViewModel.setDescription(res.getString(8));
            myViewModel.setHost(res.getString(9));
        }
        FeaturedEventTitle.setText(title);



        EventList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this, EventListActivity.class));
            }
        });

        MyEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this, MyEventsActivity.class));
            }
        });

        FeaturedEvent.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, EventDetailActivity.class);
                intent.putExtra("TITLE",myViewModel.getTitle());
                intent.putExtra("HOST",myViewModel.getHost());
                intent.putExtra("MONTH",myViewModel.getMonth());
                intent.putExtra("DAY",myViewModel.getDay());
                intent.putExtra("YEAR",myViewModel.getYear());
                intent.putExtra("TIMEFROM",myViewModel.getFromTime());
                intent.putExtra("TIMETO",myViewModel.getToTime());
                intent.putExtra("LOCATION",myViewModel.getLocation());
                intent.putExtra("DESCRIPTION",myViewModel.getDescription());
                intent.putExtra("EVENTID",myViewModel.getId());
                startActivity(intent);
            }
        });

    }
    void parseJsonData(String jsonString) {
        try {
            myeventDB.deleteAllData();
            JSONObject object = new JSONObject(jsonString);
            JSONArray eventsArray = object.getJSONArray("events");
            for(int i = 0; i < eventsArray.length(); ++i) {
                JSONObject e = eventsArray.getJSONObject(i);
                JSONObject event = e.getJSONObject("event");
                String title = event.getString("title");
                String descr = event.getString("description_text");
                String location = event.getString("location_name");
                JSONArray instanceArray = event.getJSONArray("event_instances");
                for(int z = 0; z < instanceArray.length(); ++z) {
                    JSONObject inst = instanceArray.getJSONObject(z);
                    JSONObject eventinst = inst.getJSONObject("event_instance");
                    Integer id = eventinst.getInt("id");
                    String start = substring(eventinst.getString("start"),0,19);
                    String end = substring(eventinst.getString("end"),0,19);
                    myeventDB.insertEvents(id,start,end,title,descr,"",location,"");
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}