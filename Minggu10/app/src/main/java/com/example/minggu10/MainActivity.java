package com.example.minggu10;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    ImageButton btn_light, btn_proximity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar bar = getSupportActionBar();
        bar.hide();
        setContentView(R.layout.activity_main);
        btn_light = (ImageButton) findViewById(R.id.btn_light_sensor);
        btn_light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToLightActivity();
            }
        });

        btn_proximity = (ImageButton) findViewById(R.id.btn_proximity_sensor);
        btn_proximity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToProximityActivity();
            }
        });
    }

    private void moveToLightActivity() {
        try {
            Intent intent = new Intent(MainActivity.this, LightSensorActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "an error occured!", Toast.LENGTH_SHORT).show();
        }
    }
    private void moveToProximityActivity(){
        try {
            Intent intent = new Intent(MainActivity.this, ProximitySensorActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "an error occured!", Toast.LENGTH_SHORT).show();
        }

    }
}