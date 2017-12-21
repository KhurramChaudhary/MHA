package com.example.khurramshahzad.mha.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

import com.example.khurramshahzad.mha.DatabaseHandler;
import com.example.khurramshahzad.mha.Medication;
import com.example.khurramshahzad.mha.common.CommonConstants;

public class NotificationActionService extends IntentService {

    public NotificationActionService() {
        super("NotificationActionService");
    }

    private DatabaseHandler databaseHandler = new DatabaseHandler(this);
    @Override
    protected void onHandleIntent(Intent intent) {

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Medication medication = intent.getParcelableExtra(Medication.class.getSimpleName());

        if (intent.getAction().equals(CommonConstants.ACTION_TAKEN)) {
            notificationManager.cancel(medication.getId().intValue());
            if(medication.getCurrentTime().equals(CommonConstants.MORNING)){
                medication.setMorningCounter(medication.getMorningCounter() - 1 );
            }
            else if(medication.getCurrentTime().equals(CommonConstants.AFTERNOON)){
                medication.setAfternoonCounter(medication.getAfternoonCounter() - 1);
            }
            else if(medication.getCurrentTime().equals(CommonConstants.NIGHT)){
                medication.setNightCounter(medication.getNightCounter() - 1);
            }
            databaseHandler.updateMedication(medication);
        } else if (intent.getAction().equals(CommonConstants.ACTION_DISMISS)) {

            notificationManager.cancel(medication.getId().intValue());
            medication.setMissed(medication.getMissed() - 1);
            databaseHandler.updateMedication(medication);
        }
    }
}
