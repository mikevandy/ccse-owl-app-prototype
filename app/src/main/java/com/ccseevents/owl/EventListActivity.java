package com.ccseevents.owl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EventListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        Toolbar toolbar = (Toolbar)findViewById(R.id.alToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        MyAdapter adapter = new MyAdapter(generateSimpleList());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.myRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Toast.makeText(EventListActivity.this, "You clicked the Linear Layout " + position, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onLongItemClick(View view, int position) {
                    Toast.makeText(EventListActivity.this, "You clicked the Linear Layout LONG " + position, Toast.LENGTH_SHORT).show();
                }
            })
        );
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private List<MyViewModel> generateSimpleList() {
        List<MyViewModel> myViewModelList = new ArrayList<>();

        // Manually Create Event #1
        MyViewModel myViewModel1 = new MyViewModel();
        myViewModel1.setId(1);
        myViewModel1.setDayOfWeek("Mon");
        myViewModel1.setDay("02");
        myViewModel1.setMonth("Nov");
        myViewModel1.setYear("2020");
        myViewModel1.setFromTime("6:00PM");
        myViewModel1.setToTime("7:00PM");
        myViewModel1.setTitle("BlackRock Series");
        myViewModel1.setDescription("Once upon a midnight dreary, while I pondered, weak and weary, Over many a quaint and curious volume of forgotten lore - ");
        myViewModel1.setHost("The Raven by Edgar Allen Poe");
        myViewModelList.add(myViewModel1);

        // Manually Create Event #2
        MyViewModel myViewModel2 = new MyViewModel();
        myViewModel2.setId(2);
        myViewModel2.setDayOfWeek("Tue");
        myViewModel2.setDay("10");
        myViewModel2.setMonth("Nov");
        myViewModel2.setYear("2020");
        myViewModel2.setFromTime("6:30AM");
        myViewModel2.setToTime("7:30AM");
        myViewModel2.setTitle("BlackRock Series");
        myViewModel2.setDescription("While I nodded, nearly napping, suddenly there came a tapping As of some one gently rapping, rapping at my chamber door.");
        myViewModel2.setHost("The Raven by Edgar Allen Poe");
        myViewModelList.add(myViewModel2);

        // Manually Create Event #1
        MyViewModel myViewModel3 = new MyViewModel();
        myViewModel3.setId(3);
        myViewModel3.setDayOfWeek("Wed");
        myViewModel3.setDay("18");
        myViewModel3.setMonth("Nov");
        myViewModel3.setYear("2020");
        myViewModel3.setFromTime("6:00PM");
        myViewModel3.setToTime("7:00PM");
        myViewModel3.setTitle("CCSE Internship Networking Night");
        myViewModel3.setDescription("Tis some visitor, I muttered, tapping at my chamber door Only this and nothing more.");
        myViewModel3.setHost("The Raven by Edgar Allen Poe");
        myViewModelList.add(myViewModel3);

        // Manually Create Event #1
        MyViewModel myViewModel4 = new MyViewModel();
        myViewModel4.setId(4);
        myViewModel4.setDayOfWeek("Thu");
        myViewModel4.setDay("26");
        myViewModel4.setMonth("Nov");
        myViewModel4.setYear("2020");
        myViewModel4.setFromTime("6:30AM");
        myViewModel4.setToTime("7:45AM");
        myViewModel4.setTitle("CCSE Internship Networking Night");
        myViewModel4.setDescription("Ah, distinctly I remember it was in the bleak December; And each separate dying ember wrought its ghost upon the floor.");
        myViewModel4.setHost("The Raven by Edgar Allen Poe");
        myViewModelList.add(myViewModel4);

        // Manually Create Event #1
        MyViewModel myViewModel5 = new MyViewModel();
        myViewModel5.setId(5);
        myViewModel5.setDayOfWeek("Fri");
        myViewModel5.setDay("04");
        myViewModel5.setMonth("Dec");
        myViewModel5.setYear("2020");
        myViewModel5.setFromTime("6:00PM");
        myViewModel5.setToTime("7:00PM");
        myViewModel5.setTitle("CCSE Hackathon");
        myViewModel5.setDescription("Eagerly I wished the morrow;—vainly I had sought to borrow From my books surcease of sorrow—sorrow for the lost Lenore— For the rare and radiant maiden whom the angels name Lenore— Nameless here for evermore.");
        myViewModel5.setHost("The Raven by Edgar Allen Poe");
        myViewModelList.add(myViewModel5);

        return myViewModelList;
    }
}