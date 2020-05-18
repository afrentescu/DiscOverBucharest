package com.example.user.discoverbucharest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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

public class MallsActivity extends AppCompatActivity {
    ListView lvMalls;
    private FirebaseListAdapter<Attraction> adapter;
    ArrayList<String> list, newList, locations, programs, newListl, newListp;
    Button btnTravel;
    HashMap<String, Object> map;
     RatingBar ratingBar;

    int[] IMAGES = { R.drawable.unireajpg, R.drawable.afi,R.drawable.promenada, R.drawable.verand,   R.drawable.parklake ,R.drawable.baeasa, R.drawable.sunplaza};
    String[] NAMES = {"Unirea Shopping center", "AFI Palace Cotroceni", "Promenada Mall","Veranda Mall","ParkLake", "Baneasa Mall", "Sun Plaza"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layoutmalls);
        lvMalls= findViewById(R.id.lvMalls);
        list  = new ArrayList<>();
        locations = new ArrayList<>();
        programs = new ArrayList<>();
        map = new HashMap<>();
        FirebaseApp.initializeApp(this);
        final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

        Query query = FirebaseDatabase.getInstance().getReference().child("attractions").child("malls");
        FirebaseListOptions<Attraction> options = new FirebaseListOptions.Builder<Attraction>().setLayout(R.layout.attractionlayout).setQuery(query, Attraction.class).build();
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
                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        if (currentUser != null) {
                            Toast.makeText(getApplicationContext(), "Thank you for your rate!", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(MallsActivity.this, "You need an account in order to give a rate!", Toast.LENGTH_LONG).show();

                        }

                    }
                });
                btnTravel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        if (currentUser != null) {
                            dbRef.child("users").child(currentUser.getUid()).child("attractionToSee").push().setValue(attraction);
                            Toast.makeText(MallsActivity.this, "Added to your travel plan!", Toast.LENGTH_LONG).show();


                        }else{
                            Toast.makeText(MallsActivity.this, "You need an account in order to create a travel plan!", Toast.LENGTH_LONG).show();

                        }

                    }
                });
            }

        };
        lvMalls.setAdapter(adapter);

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

