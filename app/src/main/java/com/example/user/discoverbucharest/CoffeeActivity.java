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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.HashMap;

import Classes.Attraction;

import static com.example.user.discoverbucharest.HistoricalBuildingsActivity.removeDuplicates;

public class CoffeeActivity extends AppCompatActivity {
    ListView lvCoffee;
    private FirebaseListAdapter<Attraction> adapter;
    ArrayList<String> list, newList, locations, programs, newListl, newListp;
    Button btnTravel, btnMaps, btnMenu;
    RatingBar ratingBar;
    HashMap<String, Object> map;

    int[] IMAGES = { R.drawable.manuc, R.drawable.car,R.drawable.linea2, R.drawable.bite,   R.drawable.acuarela ,R.drawable.origo, R.drawable.mm, R.drawable.mm};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layoutcoffee);
        lvCoffee = findViewById(R.id.lvCoffee);
        list  = new ArrayList<>();
        locations = new ArrayList<>();
        programs = new ArrayList<>();
        map = new HashMap<>();
        FirebaseApp.initializeApp(this);
        final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

        Query query = FirebaseDatabase.getInstance().getReference().child("attractions").child("coffees");
        FirebaseListOptions<Attraction> options = new FirebaseListOptions.Builder<Attraction>().setLayout(R.layout.attractionlayout2).setQuery(query, Attraction.class).build();
        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {
                ratingBar = v.findViewById(R.id.ratingBar3);
                TextView tvName, tvLocation, tvProgramme, tvDescription;
                tvName = v.findViewById(R.id.tvAttractionName);
                tvLocation = v.findViewById(R.id.tvLocation);
                tvDescription  =v.findViewById(R.id.tvDescription);
                tvProgramme = v.findViewById(R.id.tvProgramme);
                ImageView imgAtt = v.findViewById(R.id.imageViewattraction);
                btnTravel = v.findViewById(R.id.buttontravelplan);
                btnMaps = v.findViewById(R.id.btnMaps2);
                btnMenu = v.findViewById(R.id.btnMenu);
                // model = getItem(position);
               final  Attraction attraction = (Attraction)model;

                //  tvName.setText("Name:" + attraction.getAttractionName());
                tvName.setText( attraction.getName());

                tvDescription.setText("Description:\n" + attraction.getDescription());
                tvProgramme.setText("Programme:\n" + attraction.getProgramme());
                tvLocation.setText("Location:\n" + attraction.getLocation());
                imgAtt.setImageResource(IMAGES[position]);
                ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        attraction.setAttractionRate(rating);
                        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        if (currentUser != null) {
                            Toast.makeText(getApplicationContext(), "Thank you for your rate!", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(CoffeeActivity.this, "You need an account in order to give a rate!", Toast.LENGTH_LONG).show();

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

                        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        if (currentUser != null) {
                            dbRef.child("users").child(currentUser.getUid()).child("attractionToSee").push().setValue(attraction);
                            Toast.makeText(CoffeeActivity.this, "Added to your travel plan!", Toast.LENGTH_LONG).show();

                        }else{
                            Toast.makeText(CoffeeActivity.this, "You need an account in order to create a travel plan!", Toast.LENGTH_LONG).show();

                        }

                    }
                });

            }
        };
        lvCoffee.setAdapter(adapter);

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

}

