package com.nplix.wifidemoapp;


import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
private FragmentManager fm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        WifiFragment wifiFragment=WifiFragment.newInstance();
        ft.replace(R.id.main_container,wifiFragment);
        ft.commit();

    }

    public void Action(View v)
    {
        Intent i = new Intent(MainActivity.this,Main2Activity.class);
        startActivity(i);
    }
}
