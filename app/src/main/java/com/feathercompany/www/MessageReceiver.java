package com.feathercompany.www;



import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;



public class MessageReceiver extends FirebaseMessagingService {
    public MessageReceiver() {
    }
    @Override
    public void onNewToken(String token) {
        //Log.d("FireBase", "Refreshed token: " + token);
        System.out.println("TOKEN: "+ token);
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        //sendRegistrationToServer(token);
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
           Log.d("FireBase", "Message data payload: " + remoteMessage.getData());


        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
           Log.d("FireBase", "Message Notification Body: " + remoteMessage.getNotification().getBody());
            //Intent intent =
            Intent intent = new Intent(this, MainActivity.class);
            int id = (int) (System.currentTimeMillis() / 1000);
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    this,
                    id,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setSmallIcon( R.drawable.ic_launcher )
                    .setTicker( "Central do Aluno" )
                    .setContentTitle( "Notificação" )
                    .setContentText( remoteMessage.getNotification().getBody() )
                    .setAutoCancel( true )
                    .setContentIntent( pendingIntent );


            NotificationManager notifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notifyManager.notify( id, builder.build() );
            //showNotificationMessage(getApplication().getApplicationContext(), "Central do Aluno",remoteMessage.getNotification().getBody(), "", intent);
        }

    }


}
