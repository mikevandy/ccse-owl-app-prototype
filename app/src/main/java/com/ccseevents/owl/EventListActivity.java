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
    private List<MyViewModel> viewModelList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        Toolbar toolbar = (Toolbar)findViewById(R.id.alToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        viewModelList.addAll(generateSimpleList());
        MyAdapter adapter = new MyAdapter(viewModelList);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.myRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(EventListActivity.this, EventDetailActivity.class);
                    intent.putExtra("TITLE",viewModelList.get(position).getTitle());
                    intent.putExtra("HOST",viewModelList.get(position).getHost());
                    intent.putExtra("MONTH",viewModelList.get(position).getMonth());
                    intent.putExtra("DAY",viewModelList.get(position).getDay());
                    intent.putExtra("YEAR",viewModelList.get(position).getYear());
                    intent.putExtra("TIMEFROM",viewModelList.get(position).getFromTime());
                    intent.putExtra("TIMETO",viewModelList.get(position).getToTime());
                    intent.putExtra("LOCATION","The Atrium Building: Room 201 (Place holder)"); // TODO: bundle.getString("LOCATION");
                    intent.putExtra("DESCRIPTION",viewModelList.get(position).getDescription());
                    intent.putExtra("EVENTID",viewModelList.get(position).getId());
                    startActivity(intent);
                }

                @Override
                public void onLongItemClick(View view, int position) {
                    Intent intent = new Intent(EventListActivity.this, EventDetailActivity.class);
                    intent.putExtra("TITLE",viewModelList.get(position).getTitle());
                    startActivity(intent);
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
        myViewModel1.setDescription("Once upon a midnight dreary, while I pondered, weak and weary, Over many a quaint and curious volume of forgotten lore- While I nodded, nearly napping, suddenly there came a tapping, As of some one gently rapping, rapping at my chamber door— Tis some visitor, I muttered, tapping at my chamber door— Only this and nothing more.");
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
        myViewModel2.setDescription("Ah, distinctly I remember it was in the bleak December; And each separate dying ember wrought its ghost upon the floor. Eagerly I wished the morrow;—vainly I had sought to borrow From my books surcease of sorrow—sorrow for the lost Lenore— For the rare and radiant maiden whom the angels name Lenore— Nameless here for evermore.");
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
        myViewModel3.setDescription("And the silken, sad, uncertain rustling of each purple curtain Thrilled me—filled me with fantastic terrors never felt before; So that now, to still the beating of my heart, I stood repeating, Tis some visitor entreating entrance at my chamber door— Some late visitor entreating entrance at my chamber door;— This it is and nothing more.");
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
        myViewModel4.setDescription("Presently my soul grew stronger; hesitating then no longer, Sir, said I, or Madam, truly your forgiveness I implore; But the fact is I was napping, and so gently you came rapping, And so faintly you came tapping, tapping at my chamber door, That I scarce was sure I heard you\"—here I opened wide the door;— Darkness there and nothing more.");
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
        myViewModel5.setDescription("Deep into that darkness peering, long I stood there wondering, fearing, Doubting, dreaming dreams no mortal ever dared to dream before; But the silence was unbroken, and the stillness gave no token, And the only word there spoken was the whispered word, Lenore? This I whispered, and an echo murmured back the word, Lenore!— Merely this and nothing more.");
        myViewModel5.setHost("The Raven by Edgar Allen Poe");
        myViewModelList.add(myViewModel5);

        return myViewModelList;
    }
}