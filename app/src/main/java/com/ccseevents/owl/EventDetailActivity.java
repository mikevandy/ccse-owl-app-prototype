package com.ccseevents.owl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

public class EventDetailActivity extends AppCompatActivity {
    public MyEventsDatabaseHelper myeventDB = new MyEventsDatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        Toolbar toolbar = (Toolbar)findViewById(R.id.adToolbar);
        final Button btnCalendar = (Button)findViewById(R.id.btn_calendar);
        final ToggleButton FavoriteButton = findViewById(R.id.toggleFavorite);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final Bundle bundle = getIntent().getExtras();
        //Check whether event has already been added to My Events and adjust button text
        int eID = bundle.getInt("EVENTID");
        boolean btnfavorited = myeventDB.existsMyEvents(eID);
        if (btnfavorited) {
            FavoriteButton.setChecked(true);
        }

        // TITLE
        final String titleValue = bundle.getString("TITLE");
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
        timeTextView.setText(fromValue + " to " + toValue);

        String locationValue = bundle.getString("LOCATION");
        TextView locationTextView = (TextView)findViewById(R.id.locationText);
        locationTextView.setText(locationValue);

        // DESCRIPTION
        final String descriptionValue = bundle.getString("DESCRIPTION");
        TextView descriptionTextView = (TextView)findViewById(R.id.descriptionText);
        descriptionTextView.setText(descriptionValue);

        final int eventID = bundle.getInt("EVENTID");
        FavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean favorited = myeventDB.existsMyEvents(eventID);
                if (favorited) {
                    boolean isDeleted = myeventDB.deleteMyEvents(eventID);
                    if (isDeleted) {
                        Toast.makeText(EventDetailActivity.this, "Removed from My Events", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(EventDetailActivity.this, "Failed to Remove", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    boolean isInserted = myeventDB.insertMyEvents(eventID);
                    if (isInserted){
                        Toast.makeText(EventDetailActivity.this, "Added to My Events", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(EventDetailActivity.this, "Failed to Add", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = myeventDB.getEventDate(eventID);
                int calday = 1;
                int calmonth = 1;
                int calyear = 2020;
                int starthour = 12;
                int startmin = 0;
                int endhour = 1;
                int endmin = 0;
                if (res.getCount() == 0) {
                    return;
                }
                while (res.moveToNext()){
                    calday = res.getInt(0);
                    calmonth = res.getInt(1);
                    calyear = res.getInt(2);
                    starthour = res.getInt(3);
                    startmin = res.getInt(4);
                    endhour = res.getInt(5);
                    endmin = res.getInt(6);
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
                //intent.putExtra(CalendarContract.Events.EVENT_LOCATION, loc.getText().toString());
                //intent.putExtra(CalendarContract.Events.ALL_DAY, true);

                if(intent.resolveActivity(getPackageManager())!=null){
                    startActivity(intent);
                }else{
                    Toast.makeText(EventDetailActivity.this, "No compatible App for Calendar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

}