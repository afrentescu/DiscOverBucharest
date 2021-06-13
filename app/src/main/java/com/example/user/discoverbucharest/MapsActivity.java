package com.example.user.discoverbucharest;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MapsActivity extends AppCompatActivity {

    EditText etSource, etDestination;
    Button btnMaps;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        etSource = findViewById(R.id.etSource);
        etDestination = findViewById(R.id.etDestination);
        btnMaps = findViewById(R.id.btnMaps);

        btnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String source = etSource.getText().toString().trim();
                String destination = etDestination.getText().toString().trim();

                if(source.equals("") && destination.equals("")){
                    Toast.makeText(getApplicationContext(), "Please enter values for source and destination locations!", Toast.LENGTH_LONG).show();

                }else{
                    DisplayTrack(source, destination);
                }
            }
        });
    }

    private void DisplayTrack(String source, String destination){
        try{
            Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" + source + "/" + destination);
            Intent intent = new Intent (Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }catch (ActivityNotFoundException e){
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.apps.maps");

            Intent intent2 = new Intent(Intent.ACTION_VIEW, uri);
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent2);
        }
    }
}
