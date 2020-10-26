package com.ccseevents.owl;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class EventDetailActivity extends AppCompatActivity {
    public EventsDatabaseHelper eventDB = new EventsDatabaseHelper(this);
    public int eID;
    public String titleValue;
    public String descriptionValue;
    public String listType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        Toolbar toolbar = (Toolbar)findViewById(R.id.adToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final Bundle bundle = getIntent().getExtras();
        //Check whether event has already been added to My Events and adjust button text
        eID = bundle.getInt("EVENTID");

        // TITLE
        titleValue = bundle.getString("TITLE");
        TextView titleTextView = (TextView)findViewById(R.id.titleText);
        titleTextView.setText(titleValue);

        // HOST
        String hostValue = bundle.getString("HOST");
        TextView hostTextView = (TextView)findViewById(R.id.hostedByText);
        hostTextView.setText(hostValue);

        // DATE
        String dayValue = bundle.getString("DAY");
        String monthValue = bundle.getString("MONTH");
        String yearValue = bundle.getString("YEAR");
        TextView dateTextView = (TextView)findViewById(R.id.dateText);
        dateTextView.setText(monthValue + " " + dayValue + ", " + yearValue);

        // TIME
        String fromValue = bundle.getString("TIMEFROM");
        String toValue = bundle.getString("TIMETO");
        TextView timeTextView = (TextView)findViewById(R.id.timeText);
        if(fromValue.equals(toValue)){
            timeTextView.setText(fromValue);
            if(fromValue.equals("12:00AM")){
                timeTextView.setText("All Day");
            }
        }else{
            timeTextView.setText(fromValue + " to " + toValue);
        }

        // LOCATION
        String locationValue = bundle.getString("LOCATION");
        TextView locationTextView = (TextView)findViewById(R.id.locationText);
        locationTextView.setText(locationValue);

        // DESCRIPTION
        descriptionValue = bundle.getString("DESCRIPTION");
        TextView descriptionTextView = (TextView)findViewById(R.id.descriptionText);
        descriptionTextView.setText(descriptionValue);

        //Photo
        String photoURL = bundle.getString("PHOTOURL");
        ImageView eventImage = findViewById(R.id.eventImage);
        Picasso.get().load(photoURL).into(eventImage);

        //List Type for Back Button
        listType = bundle.getString("LISTTYPE");
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                if (listType.equals("FEATURED")){
                    Intent backIntent = new Intent(EventDetailActivity.this, MainMenuActivity.class);
                    startActivity(backIntent);
                }else {
                    Intent backIntent = new Intent(EventDetailActivity.this, EventListActivity.class);
                    backIntent.putExtra("LISTTYPE", listType);
                    startActivity(backIntent);
                }
                return true;
            case R.id.option_favorite:
                boolean favorited = eventDB.existsMyEvents(eID);
                if (favorited) {
                    boolean isDeleted = eventDB.deleteMyEvents(eID);
                    if (isDeleted) {
                        Toast.makeText(EventDetailActivity.this, "Removed from My Events", Toast.LENGTH_SHORT).show();
                        item.setIcon(getResources().getDrawable(R.drawable.ic_favorite_unselected));
                    }
                    else{
                        Toast.makeText(EventDetailActivity.this, "Failed to Remove", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    boolean isInserted = eventDB.insertMyEvents(eID);
                    if (isInserted){
                        Toast.makeText(EventDetailActivity.this, "Added to My Events", Toast.LENGTH_SHORT).show();
                        item.setIcon(getResources().getDrawable(R.drawable.ic_favorite_selected));
                    }
                    else{
                        Toast.makeText(EventDetailActivity.this, "Failed to Add", Toast.LENGTH_SHORT).show();
                    }

                }
                return true;
            case R.id.option_hide:
                boolean itemHide = eventDB.eventisHidden(eID);
                if (itemHide){
                    Toast.makeText(this, "Event Shown",Toast.LENGTH_SHORT).show();
                    eventDB.deleteHideEvents(eID);
                    item.setTitle("Hide Event");
                }else{
                    Toast.makeText(this, "Event Hidden",Toast.LENGTH_SHORT).show();
                    eventDB.insertHideEvents(eID);
                    item.setTitle("Unhide Event");
                }
                return true;
            case R.id.option_calendar:
                Cursor res = eventDB.getEventDate(eID);
                int calday = 1;
                int calmonth = 0;
                int calyear = 2020;
                int starthour = 12;
                int startmin = 0;
                int endhour = 1;
                int endmin = 0;
                String location = "";
                while (res.moveToNext()){
                    calday = res.getInt(0);
                    calmonth = res.getInt(1)-1; //0 is Jan for Calendar, 1 is for Jan in DB so make adjustment here
                    calyear = res.getInt(2)-1;
                    starthour = res.getInt(3);
                    startmin = res.getInt(4);
                    endhour = res.getInt(5);
                    endmin = res.getInt(6);
                    location = res.getString(7);
                }

                Calendar start = Calendar.getInstance();
                start.set(calyear, calmonth, calday, starthour, startmin);
                Calendar end = Calendar.getInstance();
                end.set(calyear, calmonth, calday, endhour, endmin);

                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setData(CalendarContract.Events.CONTENT_URI);
                intent.putExtra(CalendarContract.Events.TITLE, titleValue);
                intent.putExtra(CalendarContract.Events.DESCRIPTION, descriptionValue);
                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, start.getTimeInMillis());
                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end.getTimeInMillis());
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION, location);
                //intent.putExtra(CalendarContract.Events.ALL_DAY, true);

                if(intent.resolveActivity(getPackageManager())!=null){
                    startActivity(intent);
                }else{
                    Toast.makeText(EventDetailActivity.this, "No compatible App for Calendar", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_eventdetail,menu);
        boolean btnfavorited = eventDB.existsMyEvents(eID);
        if (btnfavorited) {
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_favorite_selected));
        }
        boolean itemHide = eventDB.eventisHidden(eID);
        if (itemHide){
            menu.getItem(1).setTitle("Unhide Event");
        }
        return true;
    }
}