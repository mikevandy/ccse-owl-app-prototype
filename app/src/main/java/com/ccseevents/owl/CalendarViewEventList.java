package com.ccseevents.owl;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class CalendarViewEventList extends AppCompatActivity {

    private ImageButton ListView;
    private RecyclerView rv;
    private EventsDatabaseHelper myeventDB = new EventsDatabaseHelper(this);
    public String date;
    private Cursor res;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final List<MyViewModel> calModel = new ArrayList<>();

        setContentView(R.layout.activity_calendar_view_event_list);
        rv = (RecyclerView) findViewById(R.id.recyclerView);


        final CalendarView calendar = (CalendarView) findViewById(R.id.calendarView2);
        ListView = (ImageButton) findViewById(R.id.ListBttn);

        //sets default date to the current date
        LocalDateTime ldt = LocalDateTime.now();
        final DateTimeFormatter formmat1 = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        String formatter = formmat1.format(ldt);

        date = formatter;

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                month++;
                if (month < 10 && dayOfMonth < 10) {
                    date = (year + "-" + "0" + month + "-" + "0" + dayOfMonth);
                    calModel.clear();
                    calModel.addAll(generateSimpleList());
                } else if (month < 10 && dayOfMonth >= 10){
                    date = (year + "-" + "0" + month + "-" + dayOfMonth);
                    calModel.clear();
                    calModel.addAll(generateSimpleList());
                } else if (month >= 10 && dayOfMonth < 10){
                    date = (year + "-" + month + "-" + "0" + dayOfMonth);
                    calModel.clear();
                    calModel.addAll(generateSimpleList());
                } else {
                    date = (year + "-" + month + "-" + dayOfMonth);
                    calModel.clear();
                    calModel.addAll(generateSimpleList());
                }
                Toast.makeText(CalendarViewEventList.this, date, Toast.LENGTH_SHORT).show();

                final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                rv.setHasFixedSize(true);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                rv.setLayoutManager(layoutManager);

                final MyAdapter adapter = new MyAdapter(calModel);
                rv.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onItemClick(int position) {
                        Intent intent = new Intent(CalendarViewEventList.this, EventDetailActivity.class);
                        intent.putExtra("TITLE",calModel.get(position).getTitle());
                        intent.putExtra("HOST", calModel.get(position).getHost());
                        intent.putExtra("MONTH", calModel.get(position).getMonth());
                        intent.putExtra("DAY", calModel.get(position).getDay());
                        intent.putExtra("YEAR", calModel.get(position).getYear());
                        intent.putExtra("TIMEFROM", calModel.get(position).getFromTime());
                        intent.putExtra("TIMETO", calModel.get(position).getToTime());
                        intent.putExtra("LOCATION", calModel.get(position).getLocation());
                        intent.putExtra("DESCRIPTION", calModel.get(position).getDescription());
                        intent.putExtra("EVENTID", calModel.get(position).getId());
                        intent.putExtra("PHOTOURL", calModel.get(position).getPhotoURL());
                        startActivity(intent);
                    }

                    @Override
                    public void toggleFav(int position) {
                        int eID = calModel.get(position).getId();
                        boolean favorited = myeventDB.existsMyEvents(eID);
                        if (favorited) {
                            boolean isDeleted = myeventDB.deleteMyEvents(eID);
                            if (isDeleted) {
                                Toast.makeText(CalendarViewEventList.this, "Removed from My Events", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(CalendarViewEventList.this, "Failed to Remove", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            boolean isInserted = myeventDB.insertMyEvents(eID);
                            if (isInserted){
                                Toast.makeText(CalendarViewEventList.this, "Added to My Events", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(CalendarViewEventList.this, "Failed to Add", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
            }
        });

        Toast.makeText(CalendarViewEventList.this, date, Toast.LENGTH_SHORT).show();

        //navigation button to get back to the event list view
        ListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarViewEventList.this, EventListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

    }

    private List<MyViewModel> generateSimpleList() {
        List<MyViewModel> myViewModelList = new ArrayList<>();
        res = myeventDB.getEventsOnDate(date);
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
}
