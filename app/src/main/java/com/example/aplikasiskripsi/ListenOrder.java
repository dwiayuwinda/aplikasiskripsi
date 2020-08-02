package com.example.aplikasiskripsi;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class ListenOrder extends Service implements ChildEventListener {
    FirebaseDatabase firedb;
    DatabaseReference myRef;

    @Override
    public void onCreate() {
        super.onCreate();
        firedb = FirebaseDatabase.getInstance();
        myRef = firedb.getReference("Barang");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        myRef.addChildEventListener(this);
        return super.onStartCommand(intent, flags, startId);
    }

    public ListenOrder() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
        BarangDB barang = snapshot.getValue(BarangDB.class);
        if(barang.getStok().equals("5"))
            showNotification(snapshot.getKey(), barang);
    }

    private void showNotification(String key, BarangDB barang) {
        Intent intent = new Intent(getBaseContext(), BarangDB.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getBaseContext(), 0, intent,0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext());
        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setTicker("Toko Anto Berkah App")
                .setContentInfo("Stok Barang")
                .setContentText("Stok "+key+"segera habis, hubungi pemasok untuk memesan kembali")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(contentIntent);

        NotificationManager manager = (NotificationManager)getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        int randomInt = new Random().nextInt(9999-1)+1;
        manager.notify(randomInt, builder.build());
    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}
