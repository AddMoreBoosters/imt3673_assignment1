package com.example.herma.imt3673_assignment1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.common.api.GoogleApi;

public class MainActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void CloseApp(View view)
    {
        System.exit(0);
    }

    public void GoToSettings(View view)
    {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void StartGame(View view)
    {
        
    }
}
