package com.example.samson.miwork_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView numbers, colors, phrases, family;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // WORKING WITH IMPLICIT INTENT
/*
        Intent intend = new Intent(Intent.ACTION_SENDTO);
        intend.setData(Uri.parse(""))
                .putExtra(Intent.EXTRA_SUBJECT, "")
                .putExtra(Intent.EXTRA_TEXT, "");


        if(intend.resolveActivity(getPackageManager()) != null){
            startActivity(intend);
        }*/

numbers = (TextView)findViewById(R.id.numbers);
        colors = (TextView)findViewById(R.id.colors);
        phrases = (TextView) findViewById(R.id.phrases);
        family = (TextView)findViewById(R.id.family);

        numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NumbersActivity.class);
                startActivity(intent);
            }
        });

        colors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ColorsActivity.class);
                startActivity(intent);
            }
        });

        phrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PhrasesActivity.class);
                startActivity(intent);
            }
        });
        family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FamilyActivity.class);
                startActivity(intent);
            }
        });

    }

}
