package com.example.khurramshahzad.mha;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.example.khurramshahzad.mha.services.NotificationJobService;

import java.util.concurrent.TimeUnit;

import static android.app.PendingIntent.FLAG_ONE_SHOT;


public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ImageView imgC = (ImageView) findViewById(R.id.ivC);
        imgC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, CreateActivity.class);
                startActivity(intent);
            }
        });


        ImageView imgM = (ImageView) findViewById(R.id.ivM);
        imgM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, SessionManagementActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Launch Notification bob service every minute
        Intent intent = new Intent(this, NotificationJobService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 123456789, intent,FLAG_ONE_SHOT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC, 0, TimeUnit.MINUTES.toMillis(1), pendingIntent);

    }

}
