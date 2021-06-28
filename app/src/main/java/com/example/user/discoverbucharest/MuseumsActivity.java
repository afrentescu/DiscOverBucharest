package com.example.user.discoverbucharest;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import Classes.Attraction;

import static com.example.user.discoverbucharest.HistoricalBuildingsActivity.removeDuplicates;

public class MuseumsActivity extends AppCompatActivity {
    ListView lvMuseums;
    private FirebaseListAdapter<Attraction> adapter;
    ArrayList<String> list, locations, programs;
    Long ticketPrices;
    int[] IMAGES = {R.drawable.sat, R.drawable.istorie, R.drawable.antipa, R.drawable.muzeulartabuc, R.drawable.simturi};
    String[] NAMES = {"Dimitrie Gusti National Village Museum", "The National Museum of Romanian History", "Grigore Antipa National Museum of Natural History", "Museum of Art Collections", "Museum of Senses"};
    Button btnTravel, btnMaps;
    HashMap<String, Object> map;
    NotificationCompat.Builder notification;
    String maximmm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layoutmuseums);
        lvMuseums = findViewById(R.id.lvMuseums);
        list = new ArrayList<>();
        locations = new ArrayList<>();
        programs = new ArrayList<>();
        map = new HashMap<>();
        notification = new NotificationCompat.Builder(this);
        FirebaseApp.initializeApp(this);
        final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();


        Query query = FirebaseDatabase.getInstance().getReference().child("attractions").child("museums");
        FirebaseListOptions<Attraction> options = new FirebaseListOptions.Builder<Attraction>().setLayout(R.layout.attractionlayout2).setQuery(query, Attraction.class).build();
        adapter = new FirebaseListAdapter(options) {

            @Override
            protected void populateView(View v, Object model, int position) {
                RatingBar ratingBar = v.findViewById(R.id.ratingBar3);
                TextView tvName, tvLocation, tvProgramme, tvDescription, tvPret, tvRate;
                final RadioButton rbAdult, rbStudent, rbPensionar;
                tvName = v.findViewById(R.id.tvAttractionName);
                tvLocation = v.findViewById(R.id.tvLocation);
                tvDescription = v.findViewById(R.id.tvDescription);
                tvProgramme = v.findViewById(R.id.tvProgramme);
                tvRate = v.findViewById(R.id.tvRate);
                ImageView imgAtt = v.findViewById(R.id.imageViewattraction);
                tvPret = v.findViewById(R.id.textView10);

                btnTravel = v.findViewById(R.id.buttontravelplan);
                rbAdult = v.findViewById(R.id.rbAdult);
                rbStudent = v.findViewById(R.id.rbStudent);
                rbPensionar = v.findViewById(R.id.rbPensionar);
                btnMaps = v.findViewById(R.id.btnMaps2);

                tvPret.setText("Pret:");
                final Attraction attraction = (Attraction) model;
                tvName.setText(attraction.getName());
                tvDescription.setText("Description:\n" + attraction.getDescription());
                tvProgramme.setText("Programme:\n" + attraction.getProgramme());
                tvLocation.setText("Location:\n" + attraction.getLocation());
                tvRate.setText(attraction.getAttractionRate() + "★");
                imgAtt.setImageResource(IMAGES[position]);
                rbAdult.setText("Adult: " + attraction.getPriceAdult().toString() + " ron");
                rbStudent.setText("Student: " + attraction.getPriceStudent().toString() + " ron");
                rbPensionar.setText("Retired: " + attraction.getPriceRetired().toString() + " ron");

                if (rbAdult.isChecked()) {
                    rbStudent.setChecked(false);
                    rbPensionar.setChecked(false);
                } else if (rbStudent.isChecked()) {
                    rbAdult.setChecked(false);
                    rbPensionar.setChecked(false);
                } else if (rbPensionar.isChecked()) {
                    rbAdult.setChecked(false);
                    rbStudent.setChecked(false);
                }
                ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        attraction.setAttractionRate(rating);
                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        if (currentUser != null) {
                            Toast.makeText(getApplicationContext(), "Thank you for your rate!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MuseumsActivity.this, "You need an account in order to give a rate!", Toast.LENGTH_LONG).show();

                        }


                    }
                });
                btnMaps.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String source = attraction.getName();

                        DisplayTrack(source);

                    }
                });

                btnTravel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ticketPrices = Long.valueOf(0);
                        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        if (currentUser != null) {
                            if (rbAdult.isChecked()) {
                                ticketPrices = ticketPrices + attraction.getPriceAdult();
                            }
                            if (rbStudent.isChecked()) {
                                ticketPrices = ticketPrices + attraction.getPriceStudent();
                            }
                            if (rbPensionar.isChecked()) {
                                ticketPrices = ticketPrices + attraction.getPriceRetired();
                            }/*
                            if (rbAdult.isChecked() && rbStudent.isChecked()){
                                ticketPrices = ticketPrices + attraction.getPriceAdult() + attraction.getPriceStudent();
                            }
                            if (rbAdult.isChecked() && rbPensionar.isChecked()){
                                ticketPrices = ticketPrices + attraction.getPriceAdult() + attraction.getPriceRetired();
                            }
                            if (rbStudent.isChecked() && rbPensionar.isChecked()){
                                ticketPrices = ticketPrices + attraction.getPriceStudent() + attraction.getPriceRetired();
                            }
                            if ( rbAdult.isChecked() && rbStudent.isChecked() && rbPensionar.isChecked()){
                                ticketPrices = ticketPrices + attraction.getPriceAdult() + attraction.getPriceStudent() + attraction.getPriceRetired();
                            }*/
                            //TODO: adu aici buget si scade din el ticketPrice, apoi il trimiti inapoi in baza!!!!

                            dbRef.child("users").child(currentUser.getUid()).child("attractionToSee").push().setValue(attraction);

                            dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @RequiresApi(api = Build.VERSION_CODES.O)
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
                                    maximmm = dataSnapshot.child("users").child(currentUser.getUid()).child("maxBudget").getValue(String.class);

                                    maximmm = String.valueOf(Integer.parseInt(maximmm) - ticketPrices);

                                    Toast.makeText(MuseumsActivity.this, "Added to your travel plan!", Toast.LENGTH_LONG).show();
                                    dbRef.child("users").child(currentUser.getUid()).child("maxBudget").setValue(maximmm);
                                    if (maximmm.length() < 3) {
                                        sendNotification();

                                    }
                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                            dbRef.child("users").child(currentUser.getUid()).child("dailyBudget").setValue(ticketPrices);
                        } else {
                            Toast.makeText(MuseumsActivity.this, "You need an account in order to create a travel plan!", Toast.LENGTH_LONG).show();

                        }

                    }
                });
            }
        };


        lvMuseums.setAdapter(adapter);

    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendNotification() {


        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String chanel_id = "3000";
            CharSequence name = "Channel Name";
            String description = "Chanel Description";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel mChannel = new NotificationChannel(chanel_id, name, importance);
            mChannel.setDescription(description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.BLUE);
            nm.createNotificationChannel(mChannel);
            notification = new NotificationCompat.Builder(this, chanel_id);
        } else {
            notification = new NotificationCompat.Builder(this);
        }
        notification.setSmallIcon(R.drawable.used);
        notification.setTicker("You have reached the MININUN budget for your trip!");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("Trip budget");
        notification.setContentText("Minimum budget reached! Only 100 RON left from the allocated ammount!");

        Intent intent = new Intent(getApplicationContext(), BugdetActivity.class);
        PendingIntent pdi = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        notification.setContentIntent(pdi);
        int notid = 1;
        nm.notify(notid, notification.build());
    }

    private void DisplayTrack(String source) {
        try {
            Uri uri = Uri.parse("https://www.google.co.in/maps/search/" + source);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.apps.maps");

            Intent intent2 = new Intent(Intent.ACTION_VIEW, uri);
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent2);
        }
    }
}

