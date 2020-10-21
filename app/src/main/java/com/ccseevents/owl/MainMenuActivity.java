package com.ccseevents.owl;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MainMenuActivity extends AppCompatActivity {
    public EventsDatabaseHelper myeventDB = new EventsDatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        ImageButton FeaturedEvent = (ImageButton)findViewById(R.id.featuredEventBtn);
        ImageButton EventList = (ImageButton)findViewById(R.id.eventListBtn);
        ImageButton MyEvents = (ImageButton)findViewById(R.id.myEventBtn);
        TextView FeaturedEventTitle = (TextView)findViewById(R.id.FeaturedEventTitle);

        final Cursor res = myeventDB.featuredEvent();
        final MyViewModel myViewModel = new MyViewModel();
        String title = "";
        String photourl = "";
        while (res.moveToNext()) {
            title = res.getString(7);
            photourl = res.getString(11);
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
            myViewModel.setLocation(res.getString(10));
            myViewModel.setPhotoURL(res.getString(11));
        }
        //Photo
        ImageButton eventImage = findViewById(R.id.featuredEventBtn);
        Picasso.get().load(photourl).into(eventImage);
        FeaturedEventTitle.setText(title);



        EventList.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, EventListActivity.class);
                intent.putExtra("LISTTYPE","ALL");
                startActivity(intent);
            }
        });

        MyEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, EventListActivity.class);
                intent.putExtra("LISTTYPE","MYEVENTS");
                startActivity(intent);
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
                intent.putExtra("PHOTOURL",myViewModel.getPhotoURL());
                startActivity(intent);
            }
        });
    }
}