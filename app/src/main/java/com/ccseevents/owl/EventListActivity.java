package com.ccseevents.owl;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class EventListActivity extends AppCompatActivity {
    private List<MyViewModel> viewModelList = new ArrayList<>();
    public EventsDatabaseHelper myeventDB = new EventsDatabaseHelper(this);
    String listType;
    Cursor res;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        Toolbar toolbar = (Toolbar)findViewById(R.id.alToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        final Bundle bundle = getIntent().getExtras();
        listType = bundle.getString("LISTTYPE");
        if (listType.equals("MYEVENTS")){
            toolbar.setTitle("My Events");
        }
        else if (listType.equals("HIDEEVENTS")){
            toolbar.setTitle("Hidden Events");
        }

        viewModelList.addAll(generateSimpleList());
        MyAdapter adapter = new MyAdapter(viewModelList);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.myRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);



        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
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
                    intent.putExtra("LOCATION",viewModelList.get(position).getLocation());
                    intent.putExtra("DESCRIPTION",viewModelList.get(position).getDescription());
                    intent.putExtra("EVENTID",viewModelList.get(position).getId());
                    intent.putExtra("PHOTOURL",viewModelList.get(position).getPhotoURL());
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
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return super.onOptionsItemSelected(item);
            case R.id.option_show:
                Intent intent = new Intent(EventListActivity.this, EventListActivity.class);
                intent.putExtra("LISTTYPE","HIDEEVENTS");
                startActivity(intent);
                return true;
            case R.id.option_calendarwidget:
                startActivity(new Intent(EventListActivity.this, CalendarViewEventList.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private List<MyViewModel> generateSimpleList() {
        List<MyViewModel> myViewModelList = new ArrayList<>();
        res = myeventDB.getAllData();
        if (listType.equals("MYEVENTS")){
            res = myeventDB.getMyEvents();
        }
        else if (listType.equals("HIDEEVENTS")){
            res = myeventDB.getHideEvents();
        }
        if (res.getCount() != 0) {
            MyViewModel myViewModel[] = new MyViewModel[res.getCount()];
            int i = 0;
            while (res.moveToNext()){
                myViewModel[i] = new MyViewModel();
                int eID = res.getInt(0);
                myViewModel[i].setId(eID);
                myViewModel[i].setDayOfWeek(res.getInt(1));
                myViewModel[i].setDay(res.getString(2));
                myViewModel[i].setMonth(res.getInt(3));
                myViewModel[i].setYear(res.getString(4));
                myViewModel[i].setFromTime(res.getString(5));
                myViewModel[i].setToTime(res.getString(6));
                myViewModel[i].setTitle(res.getString(7));
                myViewModel[i].setDescription(res.getString(8));
                myViewModel[i].setHost(res.getString(9));
                myViewModel[i].setLocation(res.getString(10));
                myViewModel[i].setPhotoURL(res.getString(11));
                myViewModel[i].setFavorite(myeventDB.existsMyEvents(eID));
                myViewModelList.add(myViewModel[i]);
                i++;
            }
        }
        return myViewModelList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_eventlist,menu);
        boolean hideEvents = myeventDB.existsHideEvents();
        MenuItem register = menu.findItem(R.id.option_show);
        register.setVisible(hideEvents);
        return true;
    }


}