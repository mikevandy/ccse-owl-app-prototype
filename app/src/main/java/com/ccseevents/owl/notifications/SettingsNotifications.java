package com.ccseevents.owl.notifications;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.ccseevents.owl.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

public class SettingsNotifications extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);



        }
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Boolean value = sharedPreferences.getBoolean("notifications",true );
        getToken();
        if(value==true){ subscribeToTopic("ccse");}
        if(value==false){unsubscribeToTopic("ccse"); }

    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }

    private void getToken(){
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        Log.e("Token", instanceIdResult.getToken());
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
                        Toast.makeText(SettingsNotifications.this, "Subcribed to topic", Toast.LENGTH_SHORT).show();
                    }
                });
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
                        Toast.makeText(SettingsNotifications.this, "UnSubcribed to topic", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}