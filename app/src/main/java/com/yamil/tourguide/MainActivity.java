package com.yamil.tourguide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btn_blueTooth;
    private Button btn_mapa;
    private Button btn_regreso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_blueTooth = (Button) findViewById(R.id.btn_bluetooth);
        btn_mapa = (Button) findViewById(R.id.btn_mapa);
        btn_regreso = (Button) findViewById(R.id.btn_regreso);
        btn_blueTooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent blueTooth = new Intent(MainActivity.this,BluetoothActivity.class);
                startActivity(blueTooth);
            }
        });
        btn_mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapa = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(mapa);
            }
        });
        btn_regreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regreso = new Intent(MainActivity.this, RegresoExcurActivity.class);
                startActivity(regreso);
            }
        });
    }
}
