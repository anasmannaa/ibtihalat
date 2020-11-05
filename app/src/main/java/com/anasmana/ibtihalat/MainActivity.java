package com.anasmana.ibtihalat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tobar = (TextView) findViewById(R.id.sh_tobar);
        TextView fashni = (TextView) findViewById(R.id.sh_fashni);
        TextView bahtimi = (TextView) findViewById(R.id.sh_bahtimi);
        TextView imran = (TextView) findViewById(R.id.sh_imran);
        TextView tokhi = (TextView) findViewById(R.id.sh_tokhi);
        TextView nakshbandi = (TextView) findViewById(R.id.sh_nakshbandi);
        TextView old = (TextView) findViewById(R.id.sh_old);

        tobar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tobarIntent = new Intent(MainActivity.this, TobarActivity.class);
                startActivity(tobarIntent);
            }
        });

        fashni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fashniIntent = new Intent(MainActivity.this, FashniActivity.class);
                startActivity(fashniIntent);
            }
        });

        bahtimi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bahtimiIntent = new Intent(MainActivity.this, BahtimiActivity.class);
                startActivity(bahtimiIntent);
            }
        });

        imran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imranIntent = new Intent(MainActivity.this, ImranActivity.class);
                startActivity(imranIntent);
            }
        });

        tokhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tokhiIntent = new Intent(MainActivity.this, TokhiActivity.class);
                startActivity(tokhiIntent);
            }
        });

        nakshbandi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nakshbandiIntent = new Intent(MainActivity.this, NakshbandiActivity.class);
                startActivity(nakshbandiIntent);
            }
        });

        old.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent oldIntent = new Intent(MainActivity.this, OldActivity.class);
                startActivity(oldIntent);
            }
        });
    }
}