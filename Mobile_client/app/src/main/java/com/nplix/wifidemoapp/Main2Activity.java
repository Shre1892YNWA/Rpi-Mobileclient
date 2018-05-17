package com.nplix.wifidemoapp;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

import static android.provider.Contacts.SettingsColumns.KEY;

public class Main2Activity extends AppCompatActivity {

    TextView txtWifiInfo;
    private Button mForward;
    private Button mReverse;
    private Button mLeft;
    private Button mRight;
    Handler mHandler;

    private DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference databaseReference3 = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference databaseReference4 = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference databaseReference5 = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference databaseReference6 = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference databaseReference7 = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference databaseReference8 = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference databaseReference9 = FirebaseDatabase.getInstance().getReference().child("dis");
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtWifiInfo = (TextView) findViewById(R.id.txtWifi);
        this.mHandler = new Handler();
        m_Runnable.run();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button mForward = (Button) findViewById(R.id.aForward);
        Button mReverse = (Button) findViewById(R.id.aReverse);
        Button mLeft = (Button) findViewById(R.id.aLeft);
        Button mRight = (Button) findViewById(R.id.aRight);

        firebaseDatabase.goOnline();
        databaseReference1.child("M11").setValue("True");
        databaseReference2.child("M12").setValue("True");
        databaseReference3.child("M21").setValue("True");
        databaseReference4.child("M22").setValue("True");
        databaseReference5.child("M31").setValue("True");
        databaseReference6.child("M32").setValue("True");
        databaseReference7.child("M41").setValue("True");
        databaseReference8.child("M42").setValue("True");

        mForward.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        databaseReference1.child("M11").setValue("True");
                        databaseReference2.child("M12").setValue("False");
                        databaseReference3.child("M21").setValue("False");
                        databaseReference4.child("M22").setValue("True");
                        databaseReference5.child("M31").setValue("True");
                        databaseReference6.child("M32").setValue("False");
                        databaseReference7.child("M41").setValue("False");
                        databaseReference8.child("M42").setValue("True");
                        break;
                    case MotionEvent.ACTION_UP:
                        databaseReference1.child("M11").setValue("False");
                        databaseReference2.child("M12").setValue("False");
                        databaseReference3.child("M21").setValue("False");
                        databaseReference4.child("M22").setValue("False");
                        databaseReference5.child("M31").setValue("False");
                        databaseReference6.child("M32").setValue("False");
                        databaseReference7.child("M41").setValue("False");
                        databaseReference8.child("M42").setValue("False");
                        break;
                }
                return false;
            }
        });
        mReverse.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        databaseReference1.child("M11").setValue("False");
                        databaseReference2.child("M12").setValue("True");
                        databaseReference3.child("M21").setValue("True");
                        databaseReference4.child("M22").setValue("False");
                        databaseReference5.child("M31").setValue("False");
                        databaseReference6.child("M32").setValue("True");
                        databaseReference7.child("M41").setValue("True");
                        databaseReference8.child("M42").setValue("False");
                        break;
                    case MotionEvent.ACTION_UP:
                        databaseReference1.child("M11").setValue("False");
                        databaseReference2.child("M12").setValue("False");
                        databaseReference3.child("M21").setValue("False");
                        databaseReference4.child("M22").setValue("False");
                        databaseReference5.child("M31").setValue("False");
                        databaseReference6.child("M32").setValue("False");
                        databaseReference7.child("M41").setValue("False");
                        databaseReference8.child("M42").setValue("False");
                        break;
                }
                return false;
            }
        });
        mRight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        databaseReference1.child("M11").setValue("True");
                        databaseReference2.child("M12").setValue("False");
                        databaseReference3.child("M21").setValue("False");
                        databaseReference4.child("M22").setValue("True");
                        databaseReference5.child("M31").setValue("False");
                        databaseReference6.child("M32").setValue("True");
                        databaseReference7.child("M41").setValue("True");
                        databaseReference8.child("M42").setValue("False");
                        break;
                    case MotionEvent.ACTION_UP:
                        databaseReference1.child("M11").setValue("False");
                        databaseReference2.child("M12").setValue("False");
                        databaseReference3.child("M21").setValue("False");
                        databaseReference4.child("M22").setValue("False");
                        databaseReference5.child("M31").setValue("False");
                        databaseReference6.child("M32").setValue("False");
                        databaseReference7.child("M41").setValue("False");
                        databaseReference8.child("M42").setValue("False");
                        break;
                }
                return false;
            }
        });

        mLeft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        databaseReference1.child("M11").setValue("False");
                        databaseReference2.child("M12").setValue("True");
                        databaseReference3.child("M21").setValue("True");
                        databaseReference4.child("M22").setValue("False");
                        databaseReference5.child("M31").setValue("True");
                        databaseReference6.child("M32").setValue("False");
                        databaseReference7.child("M41").setValue("False");
                        databaseReference8.child("M42").setValue("True");
                        break;
                    case MotionEvent.ACTION_UP:
                        databaseReference1.child("M11").setValue("False");
                        databaseReference2.child("M12").setValue("False");
                        databaseReference3.child("M21").setValue("False");
                        databaseReference4.child("M22").setValue("False");
                        databaseReference5.child("M31").setValue("False");
                        databaseReference6.child("M32").setValue("False");
                        databaseReference7.child("M41").setValue("False");
                        databaseReference8.child("M42").setValue("False");
                        break;
                }
                return false;
            }
        });
    }
    private final Runnable m_Runnable = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void run() {
            getApplicationContext();
            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            String bssid = wifiInfo.getBSSID();
            double rssi = wifiInfo.getRssi();
            double freq = wifiInfo.getFrequency();
            String ssid = wifiInfo.getSSID();
            double fspl = Math.abs(rssi)-4;
            double distance = (fspl+ 27.55 - 20*Math.log10(freq))/20;
            double Distance = Math.pow(10.0 , distance);
            String info =  "Rssi : " +rssi +
                    "\n" + "BSSID :" +bssid +
                    "\n" + "SSID :" +ssid +
                    "\n" + "Frequency : "+freq +
                    "\n" + "Distance :"+Distance;
            txtWifiInfo.setText(info);
            Main2Activity.this.mHandler.postDelayed(m_Runnable,100);

        }
    };



}
