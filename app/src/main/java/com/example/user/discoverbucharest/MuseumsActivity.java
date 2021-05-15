package com.example.user.discoverbucharest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
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

    int[] IMAGES = { R.drawable.sat, R.drawable.istorie, R.drawable.antipa,   R.drawable.muzeulartabuc ,R.drawable.simturi};
    String[] NAMES = {"Dimitrie Gusti National Village Museum",  "The National Museum of Romanian History","Grigore Antipa National Museum of Natural History","Museum of Art Collections", "Museum of Senses"};
    Button btnTravel;
    HashMap<String, Object> map;
    Spinner spinnerAtt;
    ArrayList <String> prices  = new ArrayList<>();
   // ArrayAdapter<String>  spinnerAdapter ;//= new ArrayAdapter<String>(MuseumsActivity.this, android.R.layout.simple_spinner_dropdown_item, prices);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layoutmuseums);
        lvMuseums =findViewById(R.id.lvMuseums);
        list  = new ArrayList<>();
        locations = new ArrayList<>();
        programs = new ArrayList<>();
        map = new HashMap<>();


        FirebaseApp.initializeApp(this);
        final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

        //final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
     //   arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Query query = FirebaseDatabase.getInstance().getReference().child("attractions").child("museums");
        FirebaseListOptions<Attraction> options = new FirebaseListOptions.Builder<Attraction>().setLayout(R.layout.attractionlayout2).setQuery(query, Attraction.class).build();
        adapter = new FirebaseListAdapter(options) {

               /* ArrayList <String> prices = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    String ticketPrice = (String) ds.child("pret").getValue();

                    prices.add(ticketPrice);
                }

            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(MuseumsActivity.this, android.R.layout.simple_spinner_dropdown_item);
*/

            @Override
            protected void populateView(View v, Object model, int position) {
               RatingBar ratingBar = v.findViewById(R.id.ratingBar3);
                TextView tvName, tvLocation, tvProgramme, tvDescription, tvPret;
              CheckBox cbAdult, cbStudent, cbPensionar;
                tvName = v.findViewById(R.id.tvAttractionName);
                tvLocation = v.findViewById(R.id.tvLocation);
                tvDescription  =v.findViewById(R.id.tvDescription);
                tvProgramme = v.findViewById(R.id.tvProgramme);
                ImageView imgAtt = v.findViewById(R.id.imageViewattraction);
                tvPret = v.findViewById(R.id.textView10);
                btnTravel = v.findViewById(R.id.buttontravelplan);
              cbAdult = v.findViewById(R.id.checkBoxAdult);
              cbStudent = v.findViewById(R.id.checkBoxStudent);
              cbPensionar = v.findViewById(R.id.checkBoxPensionar);
                // model = getItem(position);
              //  spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
             //   spinnerAtt.setAdapter(spinnerAdapter);
           //     ArrayAdapter<Integer> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.)
                tvPret.setText("Pret:");
                final Attraction attraction = (Attraction)model;

                //  tvName.setText("Name:" + attraction.getAttractionName());
                tvName.setText( attraction.getName());

                tvDescription.setText("Description:\n" + attraction.getDescription());
                tvProgramme.setText("Programme:\n" + attraction.getProgramme());
                tvLocation.setText("Location:\n" + attraction.getLocation());
                imgAtt.setImageResource(IMAGES[position]);
                cbAdult.setText("Adult: " + attraction.getPriceAdult().toString() + " ron");
                cbStudent.setText("Student: " + attraction.getPriceStudent().toString() + " ron");
                cbPensionar.setText("Retired: " +attraction.getPriceRetired().toString() + " ron");

                ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        attraction.setAttractionRate(rating);
                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        if (currentUser != null) {
                            Toast.makeText(getApplicationContext(), "Thank you for your rate!", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(MuseumsActivity.this, "You need an account in order to give a rate!", Toast.LENGTH_LONG).show();

                        }


                    }
                });

                btnTravel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        if (currentUser != null) {
                            dbRef.child("users").child(currentUser.getUid()).child("attractionToSee").push().setValue(attraction);
                            Toast.makeText(MuseumsActivity.this, "Added to your travel plan!", Toast.LENGTH_LONG).show();

                        }else{
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

}

