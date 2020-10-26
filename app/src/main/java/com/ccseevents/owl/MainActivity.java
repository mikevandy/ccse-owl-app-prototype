package com.ccseevents.owl;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

import androidx.appcompat.app.AppCompatActivity;

import static android.text.TextUtils.substring;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 500;
    public EventsDatabaseHelper myeventDB = new EventsDatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = (new Intent(MainActivity.this, MainMenuActivity.class));
                startActivity(homeIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);
        //Pull Events from API - CCSE events only for the next year
        //Instructions for pulling events - https://developer.localist.com/doc/api#filter-json
        //   in case in future more departments are to get pulled (changing url)
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, 1);
        String enddt = new SimpleDateFormat( "yyyy-MM-dd" ).format(c.getTime() );
        String url = "https://calendar.kennesaw.edu/api/2/events?group_id=31678879585047&pp=100&end="+enddt;

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

        RequestQueue rQueue = Volley.newRequestQueue(MainActivity.this);
        rQueue.add(request);
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
                String photourl = event.getString("photo_url");
                JSONArray instanceArray = event.getJSONArray("event_instances");
                for(int z = 0; z < instanceArray.length(); ++z) {
                    String start = "";
                    String end = "";
                    JSONObject inst = instanceArray.getJSONObject(z);
                    JSONObject eventinst = inst.getJSONObject("event_instance");
                    Integer id = eventinst.getInt("id");
                    start = substring(eventinst.getString("start"), 0, 19);
                    boolean isnull = eventinst.isNull("end");
                    if(isnull){
                        end = start; //End Date is not required, so we have to deal with nulls
                    }else{
                        end = substring(eventinst.getString("end"), 0, 19);
                    }
                    myeventDB.insertEvents(id,start,end,title,descr,"",location,"",photourl);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
