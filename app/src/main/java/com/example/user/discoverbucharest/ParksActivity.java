package com.example.user.discoverbucharest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Attr;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Classes.Attraction;

import static com.example.user.discoverbucharest.HistoricalBuildingsActivity.removeDuplicates;

public class ParksActivity extends AppCompatActivity implements Serializable {
    ListView lvParks;
    private FirebaseListAdapter<Attraction> adapter;
    ArrayList <String> list, newList, locations, programs, newListl, newListp;
    int[] IMAGES = {R.drawable.arc, R.drawable.cismigiu, R.drawable.herastrau, R.drawable.carol1, R.drawable.tineretului, R.drawable.botanic, R.drawable.titan, R.drawable.univ, R.drawable.revolutiei, R.drawable.uniriip};
    ArrayAdapter<Attraction> arrayAdapter;
    Button btnTravel;
    HashMap<String, Object> map;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layoutparks);
        lvParks = findViewById(R.id.lvParks);
        list  = new ArrayList<>();
        locations = new ArrayList<>();
        programs = new ArrayList<>();
        map = new HashMap<>();



      //  final CustomAdapter2 customAdapter = new CustomAdapter2();
       // lvParks.setAdapter(customAdapter);
     //    ArrayAdapter<Attraction> arrayAdapter = new ArrayAdapter<Attraction>(this, R.layout.attractionlayout, arraylist);
      /*  DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("attractions");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.attractionlayout, R.id.tvAttractionName, arraylist);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               takeData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

*/
        FirebaseApp.initializeApp(this);
        final   DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

        Query query = FirebaseDatabase.getInstance().getReference().child("attractions").child("parks&squares");
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
                RatingBar ratingBar = v.findViewById(R.id.ratingBar3);

                ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        attraction.setAttractionRate(rating);
                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        if (currentUser != null) {
                            Toast.makeText(getApplicationContext(), "Thank you for your rate!", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(ParksActivity.this, "You need an account in order to give a rate!", Toast.LENGTH_LONG).show();

                        }

                    }
                });
                btnTravel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        if (currentUser != null) {
                            dbRef.child("users").child(currentUser.getUid()).child("attractionToSee").push().setValue(attraction);
                            Toast.makeText(ParksActivity.this, "Added to your travel plan!", Toast.LENGTH_LONG).show();

                        }else{
                            Toast.makeText(ParksActivity.this, "You need an account in order to create a travel plan!", Toast.LENGTH_LONG).show();

                        }

                    }
                });
            }
        };
        lvParks.setAdapter(adapter);

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


/*

    private void takeData(DataSnapshot dataSnapshot) {

        for (DataSnapshot ds : dataSnapshot.getChildren())
            for (DataSnapshot ds2 : ds.getChildren()) {
                //  attraction = ds2.getValue(Attraction.class);
                //String data = ds2.getValue(String.class);
              Attraction attraction =  ds2.getValue(Attraction.class);
                //arraylist.add(attraction);

                TextView tvName, tvLocation, tvProgramme, tvDescription;
                tvName = findViewById(R.id.tvAttractionName);
                tvLocation = findViewById(R.id.tvLocation);
                tvDescription = findViewById(R.id.tvDescription);
                tvProgramme = findViewById(R.id.tvProgramme);
                ImageView imgAtt = findViewById(R.id.imageViewattraction);
                // model = getItem(position);

                tvName.setText("Name:" + attraction.getAttractionName());
                tvDescription.setText("" + attraction.getAttractionDescription());
                tvProgramme.setText(attraction.getAttractionProgramme());
                tvLocation.setText(attraction.getAttractionLocation());



            }
        lvParks.setAdapter(arrayAdapter);
    }

*/
                }










