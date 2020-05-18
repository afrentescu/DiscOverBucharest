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
import java.util.List;

import Classes.Attraction;

public class StreetsActvity extends AppCompatActivity {
    ListView lvStreets;
    private FirebaseListAdapter<Attraction> adapter;
    ArrayList<String> list, newList, locations, programs, newListl, newListp;
    RatingBar ratingBar;
    float total, average;
    Button btnTravel;
    HashMap<String, Object> map;

    int[] IMAGES = { R.drawable.caleav, R.drawable.daciajpg,R.drawable.primaverii, R.drawable.xen,   R.drawable.oldt ,R.drawable.verona};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layoutstreets);
        lvStreets = findViewById(R.id.lvStreets);
        list  = new ArrayList<>();
        locations = new ArrayList<>();
        programs = new ArrayList<>();
        map = new HashMap<>();
        FirebaseApp.initializeApp(this);
        final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

        Query query = FirebaseDatabase.getInstance().getReference().child("attractions").child("streets");
        FirebaseListOptions<Attraction> options = new FirebaseListOptions.Builder<Attraction>().setLayout(R.layout.attractionlayout).setQuery(query, Attraction.class).build();
        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {
                TextView tvName, tvLocation, tvProgramme, tvDescription;
                tvName = v.findViewById(R.id.tvAttractionName);
                tvLocation = v.findViewById(R.id.tvLocation);
                tvDescription  =v.findViewById(R.id.tvDescription);
                tvProgramme = v.findViewById(R.id.tvProgramme);
                ImageView imgAtt = v.findViewById(R.id.imageViewattraction);
                btnTravel = v.findViewById(R.id.buttontravelplan);

                final Attraction attraction = (Attraction)model;
                tvName.setText( attraction.getName());

                tvDescription.setText("Description:\n" + attraction.getDescription());
                tvProgramme.setText("Programme:\n" + attraction.getProgramme());
                tvLocation.setText("Location:\n" + attraction.getLocation());
                imgAtt.setImageResource(IMAGES[position]);
                ratingBar = v.findViewById(R.id.ratingBar3);

                ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        attraction.setAttractionRate(rating);
                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        if (currentUser != null) {
                            Toast.makeText(getApplicationContext(), "Thank you for your rate!", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(StreetsActvity.this, "You need an account in order to give a rate!", Toast.LENGTH_LONG).show();

                        }

                    }
                });
                btnTravel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        if (currentUser != null) {
                            dbRef.child("users").child(currentUser.getUid()).child("attractionToSee").push().setValue(attraction);//updateChildren(map);
                            Toast.makeText(StreetsActvity.this, "Added to your travel plan!", Toast.LENGTH_LONG).show();

                        }else{
                            Toast.makeText(StreetsActvity.this, "You need an account in order to create a travel plan!", Toast.LENGTH_LONG).show();

                        }

                    }
                });
            }
        };
        lvStreets.setAdapter(adapter);

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
    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list)
    {


        ArrayList<T> newList = new ArrayList<T>();


        for (T element : list) {


            if (!newList.contains(element)) {

                newList.add(element);
            }
        }

        // return the new list
        return newList;
    }

}
