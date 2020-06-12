package com.enseval.samuelseptiano.hcservice.Firebase;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.enseval.samuelseptiano.hcservice.Activity.LoginActivity;
import com.enseval.samuelseptiano.hcservice.Activity.MainActivity;
import com.enseval.samuelseptiano.hcservice.Application.MyApplication;
import com.enseval.samuelseptiano.hcservice.MyService;
import com.enseval.samuelseptiano.hcservice.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
       //Toast.makeText(MyApplication.getContext(),"Pushed",Toast.LENGTH_LONG).show();
       // Log.d(TAG, "From: " + remoteMessage.getFrom());
        Map<String, String> data = remoteMessage.getData();
        String myCustomKey = data.get("action");
         Log.d(TAG, "ACTION: " + myCustomKey);

//                stopService(new Intent(this, MyService.class));
//                startService(new Intent(this, MyService.class));


        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        builder.setSmallIcon(android.R.drawable.ic_dialog_alert);
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.journaldev.com/"));
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("ISFROMNOTIFICATION", "Y");
        intent.putExtra("action", data.get("action"));

        builder.setOngoing(true);
        builder.setAutoCancel(false);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logohckiosk));
        builder.setContentTitle(remoteMessage.getNotification().getTitle());
        builder.setContentText(remoteMessage.getNotification().getBody());
       // builder.setSubText("Tap to update data");

        NotificationManager notificationManager = (NotificationManager) getApplication().getSystemService(getApplicationContext().NOTIFICATION_SERVICE);

        notificationManager.notify(1, builder.build());

    }



}
