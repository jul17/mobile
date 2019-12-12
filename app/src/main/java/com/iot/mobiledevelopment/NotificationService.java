package com.iot.mobiledevelopment;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.List;

import static com.iot.mobiledevelopment.MoviesFragment.EXTRA_DESCRIPTION;
import static com.iot.mobiledevelopment.MoviesFragment.EXTRA_TITTLE;
import static com.iot.mobiledevelopment.MoviesFragment.EXTRA_URL;
import static com.iot.mobiledevelopment.MoviesFragment.EXTRA_YEAR;

public class NotificationService extends FirebaseMessagingService {


    public static final String EXTRA_COMPARE_TITLE = "compare_title";
    public static final String TAG = "Notification";
    private int count = 0;
    private List<Movie> movieList;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

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
        intent.putExtra(EXTRA_TITTLE, messageBody);

        intent.putExtra(EXTRA_URL, "https://images-na.ssl-images-amazon.com/images/I/717KH%2Bf1fkL._SY550_.jpg");
        intent.putExtra(EXTRA_TITTLE, "Titanic");
        intent.putExtra(EXTRA_YEAR, 2000);
        intent.putExtra(EXTRA_DESCRIPTION, "Great movie");

        NotificationManager mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel mChannel = new NotificationChannel("movie", "movie", importance);
            mChannel.setDescription(messageBody);

            mNotifyManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "movie");
        mBuilder.setContentTitle(title)
                .setContentText(messageBody)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setChannelId("movie")
                .setPriority(NotificationCompat.PRIORITY_LOW);

        mNotifyManager.notify(count, mBuilder.build());
        count++;
    }
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
    }
}



