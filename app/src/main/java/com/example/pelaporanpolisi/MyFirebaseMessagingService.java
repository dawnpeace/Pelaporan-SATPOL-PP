package com.example.pelaporanpolisi;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.pelaporanpolisi.RetrofitInterface.Authentication;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d("FCMTOKEN", "onNewToken: "+s);
        SharedPrefHelper sharedPrefHelper = SharedPrefHelper.getInstance(this);
        sharedPrefHelper.storeFirebaseToken(s);
    }
}
