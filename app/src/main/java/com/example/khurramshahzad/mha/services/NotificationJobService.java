package com.example.khurramshahzad.mha.services;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.khurramshahzad.mha.DatabaseHandler;
import com.example.khurramshahzad.mha.Medication;
import com.example.khurramshahzad.mha.R;
import com.example.khurramshahzad.mha.common.CommonConstants;
import com.example.khurramshahzad.mha.receivers.NotificationPublisher;

import org.joda.time.LocalTime;

import java.util.List;

import static android.app.PendingIntent.FLAG_ONE_SHOT;


public class NotificationJobService extends IntentService {

    private DatabaseHandler dbHandler;

    public NotificationJobService() {
        super(NotificationJobService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {

        Log.d(getClass().getSimpleName(), getClass().getSimpleName() + " started");
        dbHandler = new DatabaseHandler(NotificationJobService.this);

        Long from = (long) LocalTime.now().getMillisOfDay();
        Long to = (long) LocalTime.now().plusMinutes(1).getMillisOfDay();

        List<Medication> medications = dbHandler.getMedicationsByTime(from.toString(), to.toString());
        for (Medication medication : medications) {

            Long milliSeconds = 0L;
            if (medication.getMorningTime() >= from && medication.getMorningTime() < to) {
                milliSeconds = LocalTime.fromMillisOfDay(medication.getMorningTime()).toDateTimeToday().getMillis();
                medication.setCurrentTime(CommonConstants.MORNING);
            } else if (medication.getAfternoonTime() >= from && medication.getAfternoonTime() < to) {
                milliSeconds = LocalTime.fromMillisOfDay(medication.getAfternoonTime()).toDateTimeToday().getMillis();
                medication.setCurrentTime(CommonConstants.AFTERNOON);

            } else if (medication.getNightTime() >= from && medication.getNightTime() < to) {
                milliSeconds = LocalTime.fromMillisOfDay(medication.getNightTime()).toDateTimeToday().getMillis();
                medication.setCurrentTime(CommonConstants.NIGHT);
            }

            Intent dismissIntent = new Intent(this, NotificationActionService.class);
            dismissIntent.setAction(CommonConstants.ACTION_DISMISS);
            dismissIntent.putExtra(Medication.class.getSimpleName(), medication);

            Intent takenIntent = new Intent(this, NotificationActionService.class);
            takenIntent.setAction(CommonConstants.ACTION_TAKEN);
            takenIntent.putExtra(Medication.class.getSimpleName(), medication);

            PendingIntent pendingIntentDismiss = PendingIntent.getService(this, 0, dismissIntent, FLAG_ONE_SHOT);
            PendingIntent pendingIntentTaken = PendingIntent.getService(this, 1, takenIntent, FLAG_ONE_SHOT);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel mChannel = new NotificationChannel(CommonConstants.MEDICATION_NOTIFICATION, "channel name", NotificationManager.IMPORTANCE_HIGH);
                mChannel.setDescription("Medication notification");
                mChannel.setLightColor(Color.CYAN);
                mChannel.canShowBadge();
                mChannel.setShowBadge(true);
                notificationManager.createNotificationChannel(mChannel);
            }
            NotificationCompat.Builder builder = new NotificationCompat.Builder(NotificationJobService.this, CommonConstants.MEDICATION_NOTIFICATION);
            builder.setStyle(new NotificationCompat.BigTextStyle()
                    .bigText(medication.getComment()))
                    .addAction(R.drawable.ic_stat_close,
                            getString(R.string.dismiss), pendingIntentDismiss)
                    .addAction(R.drawable.ic_stat_later,
                            getString(R.string.taken), pendingIntentTaken);
            builder.setContentTitle(medication.getMedicineName());
            builder.setSmallIcon(R.drawable.ic_medicine);
            Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            builder.setSound(notificationSound);
            Notification notification = builder.build();
            takenIntent.putExtra(Notification.class.getSimpleName(), notification);
            dismissIntent.putExtra(Notification.class.getSimpleName(), notification);

            scheduleNotification(notification, medication.getId().intValue(), milliSeconds);
            Log.d(this.getClass().getSimpleName(), medication.toString());
        }

        Log.d(getClass().getSimpleName(), getClass().getSimpleName() + " finished");
    }

    private void scheduleNotification(Notification notification, Integer notificationId, Long milliSeconds) {

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, notificationId);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, FLAG_ONE_SHOT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, milliSeconds, pendingIntent);

    }
}
