package com.ccseevents.owl;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainMenuActivity extends AppCompatActivity {
    public MyEventsDatabaseHelper myeventDB = new MyEventsDatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        ImageButton FeaturedEvent = (ImageButton)findViewById(R.id.featuredEventBtn);
        ImageButton EventList = (ImageButton)findViewById(R.id.eventListBtn);
        ImageButton MyEvents = (ImageButton)findViewById(R.id.myEventBtn);
        myeventDB.insertEvents(1,"2020-11-02 18:00" ,"2020-11-02 19:00" ,"BlackRock Series","Once upon a midnight dreary, while I pondered, weak and weary, Over many a quaint and curious volume of forgotten lore- While I nodded, nearly napping, suddenly there came a tapping, As of some one gently rapping, rapping at my chamber door— Tis some visitor, I muttered, tapping at my chamber door— Only this and nothing more.","The Raven by Edgar Allen Poe","","");
        myeventDB.insertEvents(2,"2020-11-10 18:30","2020-11-10 19:45","BlackRock Series","Ah, distinctly I remember it was in the bleak December; And each separate dying ember wrought its ghost upon the floor. Eagerly I wished the morrow;—vainly I had sought to borrow From my books surcease of sorrow—sorrow for the lost Lenore— For the rare and radiant maiden whom the angels name Lenore— Nameless here for evermore.","The Raven by Edgar Allen Poe","","");
        myeventDB.insertEvents(3,"2020-11-18 18:00","2020-11-18 19:00","CCSE Internship Networking Night","And the silken, sad, uncertain rustling of each purple curtain Thrilled me—filled me with fantastic terrors never felt before; So that now, to still the beating of my heart, I stood repeating, Tis some visitor entreating entrance at my chamber door— Some late visitor entreating entrance at my chamber door;— This it is and nothing more.","The Raven by Edgar Allen Poe","","");
        myeventDB.insertEvents(4,"2020-11-26 18:30","2020-11-26 20:00","CCSE Internship Networking Night","Presently my soul grew stronger; hesitating then no longer, Sir, said I, or Madam, truly your forgiveness I implore; But the fact is I was napping, and so gently you came rapping, And so faintly you came tapping, tapping at my chamber door, That I scarce was sure I heard you\"—here I opened wide the door;— Darkness there and nothing more.","The Raven by Edgar Allen Poe","","");
        myeventDB.insertEvents(5,"2020-12-04 18:00","2020-12-04 19:00","CCSE Hackathon","Deep into that darkness peering, long I stood there wondering, fearing, Doubting, dreaming dreams no mortal ever dared to dream before; But the silence was unbroken, and the stillness gave no token, And the only word there spoken was the whispered word, Lenore? This I whispered, and an echo murmured back the word, Lenore!— Merely this and nothing more.","The Raven by Edgar Allen Poe","","");
        myeventDB.insertEvents(6,"2021-01-04 18:00","2021-01-04 19:00","CCSE Hackathon","Deep into that darkness peering, long I stood there wondering, fearing, Doubting, dreaming dreams no mortal ever dared to dream before; But the silence was unbroken, and the stillness gave no token, And the only word there spoken was the whispered word, Lenore? This I whispered, and an echo murmured back the word, Lenore!— Merely this and nothing more.","The Raven by Edgar Allen Poe","","");
        myeventDB.insertEvents(7,"2020-12-04 17:00","2020-12-04 18:00","CCSE Hackathon","Deep into that darkness peering, long I stood there wondering, fearing, Doubting, dreaming dreams no mortal ever dared to dream before; But the silence was unbroken, and the stillness gave no token, And the only word there spoken was the whispered word, Lenore? This I whispered, and an echo murmured back the word, Lenore!— Merely this and nothing more.","The Raven by Edgar Allen Poe","","");
        myeventDB.insertEvents(8,"2020-01-04 17:00","2020-01-04 18:00","Old Event","Deep into that darkness peering, long I stood there wondering, fearing, Doubting, dreaming dreams no mortal ever dared to dream before; But the silence was unbroken, and the stillness gave no token, And the only word there spoken was the whispered word, Lenore? This I whispered, and an echo murmured back the word, Lenore!— Merely this and nothing more.","The Raven by Edgar Allen Poe","","");
        myeventDB.insertEvents(9,"2020-09-04 17:00","2020-09-04 18:00","First Event","Deep into that darkness peering, long I stood there wondering, fearing, Doubting, dreaming dreams no mortal ever dared to dream before; But the silence was unbroken, and the stillness gave no token, And the only word there spoken was the whispered word, Lenore? This I whispered, and an echo murmured back the word, Lenore!— Merely this and nothing more.","The Raven by Edgar Allen Poe","","");
        myeventDB.insertEvents(10,"2020-10-10 17:00","2020-10-10 18:15","First Event","Deep into that darkness peering, long I stood there wondering, fearing, Doubting, dreaming dreams no mortal ever dared to dream before; But the silence was unbroken, and the stillness gave no token, And the only word there spoken was the whispered word, Lenore? This I whispered, and an echo murmured back the word, Lenore!— Merely this and nothing more.","The Raven by Edgar Allen Poe","","");

        EventList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this, EventListActivity.class));
            }
        });

        MyEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this, MyEventsActivity.class));
            }
        });

        FeaturedEvent.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                Cursor res = myeventDB.featuredEvent();
                if (res.getCount() == 0) {
                    showMessage("Error","No Data: ");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("ID :"+res.getString(0)+"\n");
                    buffer.append("date :"+res.getString(1)+"\n");
                }
                //Show all Data
                showMessage("Data",buffer.toString());
            }
        });

    }
    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}