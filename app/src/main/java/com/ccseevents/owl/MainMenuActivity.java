package com.ccseevents.owl;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ccseevents.owl.navigation.facebookActivity;
import com.ccseevents.owl.navigation.feedbackActivity;
import com.ccseevents.owl.notifications.NotificationsActivity;
import com.ccseevents.owl.notifications.ReminderNotificationActivity;
import com.ccseevents.owl.notifications.SettingsNotifications;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceManager;

public class MainMenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public EventsDatabaseHelper eventDB = new EventsDatabaseHelper(this);
    public static final String  CHANNEL_ID = "101"; //Notifications Channel
    Toolbar toolbar;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        createNotificationChannel();

        //toolbar
        toolbar = findViewById(R.id.toolBarHome);
        setSupportActionBar(toolbar);
        mDrawerLayout = findViewById(R.id.drawerLayout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ImageButton FeaturedEvent = (ImageButton) findViewById(R.id.featuredEventBtn);
        ImageButton EventList = (ImageButton) findViewById(R.id.eventListBtn);
        ImageButton MyEvents = (ImageButton) findViewById(R.id.myEventBtn);
        TextView FeaturedEventTitle = (TextView) findViewById(R.id.FeaturedEventTitle);

        final Cursor res = eventDB.featuredEvent();
        final MyViewModel myViewModel = new MyViewModel();
        String title = "";
        String photourl = "";
        if (res.getCount() != 0) {
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
        }else{
            FeaturedEventTitle.setText("No Events Available");
        }


        EventList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, EventListActivity.class);
                intent.putExtra("LISTTYPE", "ALL");
                startActivity(intent);
            }
        });

        MyEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, EventListActivity.class);
                intent.putExtra("LISTTYPE", "MYEVENTS");
                startActivity(intent);
            }
        });

        FeaturedEvent.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if (res.getCount() != 0) {
                    Intent intent = new Intent(MainMenuActivity.this, EventDetailActivity.class);
                    intent.putExtra("TITLE", myViewModel.getTitle());
                    intent.putExtra("HOST", myViewModel.getHost());
                    intent.putExtra("MONTH", myViewModel.getMonth());
                    intent.putExtra("DAY", myViewModel.getDay());
                    intent.putExtra("YEAR", myViewModel.getYear());
                    intent.putExtra("TIMEFROM", myViewModel.getFromTime());
                    intent.putExtra("TIMETO", myViewModel.getToTime());
                    intent.putExtra("LOCATION", myViewModel.getLocation());
                    intent.putExtra("DESCRIPTION", myViewModel.getDescription());
                    intent.putExtra("EVENTID", myViewModel.getId());
                    intent.putExtra("PHOTOURL", myViewModel.getPhotoURL());
                    intent.putExtra("LISTTYPE", "FEATURED");
                    startActivity(intent);
                }
            }
        });
        //gets Tokens from Firebase
       /* getToken();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Boolean value = sharedPreferences.getBoolean("notifications",true );
        getToken();
        if(value==true){ subscribeToTopic("ccse");}
        if(value==false){unsubscribeToTopic("ccse"); }
*/

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_events) {
            Intent intent = new Intent(MainMenuActivity.this, EventListActivity.class);
            intent.putExtra("LISTTYPE", "ALL");
            startActivity(intent);
        }
        if (id == R.id.nav_my_events) {

            Intent intent = new Intent(MainMenuActivity.this, EventListActivity.class);
            intent.putExtra("LISTTYPE", "MYEVENTS");
            startActivity(intent);
        }


        if (id == R.id.nav_calendar) {
            startActivity(new Intent(MainMenuActivity.this, CalendarViewEventList.class));
        }

        if (id == R.id.nav_feedback) {
            startActivity(new Intent(MainMenuActivity.this, feedbackActivity.class));
        }
        if (id == R.id.nav_notifications) {
            startActivity(new Intent(MainMenuActivity.this, SettingsNotifications.class));
        }
        if (id == R.id.nav_reminders) {
            startActivity(new Intent(MainMenuActivity.this, ReminderNotificationActivity.class));
        }
        if (id == R.id.nav_facebook) {
            startActivity(new Intent(MainMenuActivity.this, facebookActivity.class));
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public  void  onBackPressed(){
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)){ mDrawerLayout.closeDrawer(GravityCompat.START);} else { super.onBackPressed();}
    }

    //get the applications token
    private void getToken(){
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        Log.e("Token", instanceIdResult.getToken());
                    }
                });
    }

    //create a notification channel
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Firebase_Notification_Channel";
            String description = "This the channel to receive firebase notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void unsubscribeToTopic(String topic2){

        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic2)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //   String msg = getString(R.string.msg_subscribed);
                        // if (!task.isSuccessful()) {
                        //   msg = getString(R.string.msg_subscribe_failed);
                        //      }
                        //    Log.d(TAG, msg);
                        Toast.makeText(MainMenuActivity.this, "UnSubcribed to topic", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void subscribeToTopic(String topic1){

        FirebaseMessaging.getInstance().subscribeToTopic(topic1)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //   String msg = getString(R.string.msg_subscribed);
                        // if (!task.isSuccessful()) {
                        //   msg = getString(R.string.msg_subscribe_failed);
                        //      }
                        //    Log.d(TAG, msg);
                        Toast.makeText(MainMenuActivity.this, "Subcribed to topic", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}