package com.iot.mobiledevelopment;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class NotificationService extends FirebaseMessagingService {


    public static final String EXTRA_COMPARE_TITLE = "compare_title";
    public static final String TAG = "Notification";
    public NotificationService() {
        // Empty constructor
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
//        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
//        intent.putExtra(EXTRA_COMPARE_TITLE, myCustomKey);

        // Check if message contains a notification payload.
//        if (remoteMessage.getNotification() != null) {
//            String title = remoteMessage.getNotification().getTitle(); //get title
//            String message = remoteMessage.getNotification().getBody(); //get message
//            String click_action = remoteMessage.getNotification().getClickAction(); //get click_action
//
//            Log.d(TAG, "Message Notification Title: " + title);
//            Log.d(TAG, "Message Notification Body: " + message);
//            Log.d(TAG, "Message Notification click_action: " + click_action);
//
//            sendNotification(title, message, click_action);
//        }

        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        try {
            sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDeletedMessages() {

    }

    private void sendNotification(String title, String messageBody){
        Intent intent = new Intent(this, DetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(EXTRA_COMPARE_TITLE, messageBody);


        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
    }
}



