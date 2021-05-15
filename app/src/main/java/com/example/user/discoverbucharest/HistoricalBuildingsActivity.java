package com.example.user.discoverbucharest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.Switch;
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
import com.google.gson.Gson;

import org.w3c.dom.Attr;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import Classes.Attraction;

public class HistoricalBuildingsActivity extends AppCompatActivity implements Serializable {
    private static final String APP_STATE_KEY = "APP_STATE_KEY";
    TextView tvName, tvLocation, tvProgramme, tvDescription, tvPret;
    ListView lvHist;
    CheckBox cbAdult, cbStudent, cbPensionar;

    int[] IMAGES = {R.drawable.ateneu, R.drawable.palatulparlamentului, R.drawable.manastire, R.drawable.cecpalace, R.drawable.mogosoaia, R.drawable.muzeulartabuc};

    private FirebaseListAdapter<Attraction> adapter;
    ArrayList <String> list, locations, programs;
    ArrayList<ImageView> images;
    ArrayList<Float> ratings;
    RatingBar ratingBar;
    ArrayList<Attraction> attractionList;
    float total;

    Button btnTravel;
    String state;
    HashMap<String, Object> map, mapLocation, mapProgram;


    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
        }

        setContentView(R.layout.listviewattractions);
        lvHist = findViewById(R.id.listVieAttractions);
       list  = new ArrayList<>();
       locations = new ArrayList<>();
       attractionList =new ArrayList<>();
      programs = new ArrayList<>();
       ratings = new ArrayList<Float>();
       images = new ArrayList<>();
       map = new HashMap<>();
        mapLocation = new HashMap<>();
        mapProgram = new HashMap<>();

        FirebaseApp.initializeApp(this);

      final   DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        Query query = FirebaseDatabase.getInstance().getReference().child("attractions").child("historical_buildings");
        FirebaseListOptions<Attraction> options = new FirebaseListOptions.Builder<Attraction>().setLayout(R.layout.attractionlayout2).setQuery(query, Attraction.class).build();
        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, final int position) {
                ratingBar = v.findViewById(R.id.ratingBar3);
                tvName = v.findViewById(R.id.tvAttractionName);
                tvLocation = v.findViewById(R.id.tvLocation);
                tvDescription = v.findViewById(R.id.tvDescription);
                tvProgramme = v.findViewById(R.id.tvProgramme);
                tvPret = v.findViewById(R.id.textView10);
                tvPret.setText("Pret:");

                ImageView imgAtt = v.findViewById(R.id.imageViewattraction);
                btnTravel = v.findViewById(R.id.buttontravelplan);
                cbAdult = v.findViewById(R.id.checkBoxAdult);
                cbStudent = v.findViewById(R.id.checkBoxStudent);
                cbPensionar = v.findViewById(R.id.checkBoxPensionar);

                final  Attraction attraction = (Attraction) model;
                tvName.setText(attraction.getName());

                tvDescription.setText("Description:\n" + attraction.getDescription());
                tvProgramme.setText("Programme:\n" + attraction.getProgramme());
                tvLocation.setText("Location:\n" + attraction.getLocation());
                imgAtt.setImageResource(IMAGES[position]);


                if( attraction.getPriceAdult() == 0 && attraction.getPriceStudent() == 0 && attraction.getPriceRetired() == 0 ) {
                    cbAdult.setText("Free entrance!");
                    cbPensionar.setVisibility(View.INVISIBLE);
                    cbStudent.setVisibility(View.INVISIBLE);
                }
                else

                if(attraction.getPriceAdult() == 0)
                    cbAdult.setText("Adult: FREE");
                else
                cbAdult.setText("Adult: " + attraction.getPriceAdult().toString() + " ron");

                if(attraction.getPriceStudent() == 0)
                    cbStudent.setText("Student: FREE");
                else
                cbStudent.setText("Student: " + attraction.getPriceStudent().toString() + " ron");

                if(attraction.getPriceRetired() == 0)
                    cbPensionar.setText("Retired: FREE");
                else
                cbPensionar.setText("Retired: " +attraction.getPriceRetired().toString() + " ron");


                ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        attraction.setAttractionRate(rating);
                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        if (currentUser != null) {
                        Toast.makeText(getApplicationContext(), "Thank you for your rate!", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(HistoricalBuildingsActivity.this, "You need an account in order to give a rate!", Toast.LENGTH_LONG).show();

                        }
                    }
                });

         btnTravel.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                attractionList.add(attraction); //!!!!!!!!!!!!



                 FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                 if (currentUser != null) {
                     Toast.makeText(HistoricalBuildingsActivity.this, "Added to your travel plan!", Toast.LENGTH_LONG).show();

                     dbRef.child("users").child(currentUser.getUid()).child("attractionToSee").push().setValue(attraction); //updateChildren(map);

                 }else{
                     Toast.makeText(HistoricalBuildingsActivity.this, "You need an account in order to create a travel plan!", Toast.LENGTH_LONG).show();

                 }

             }
         });
            }



        };
        lvHist.setAdapter(adapter);



    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
      outState.putString(APP_STATE_KEY, state);
      super.onSaveInstanceState(outState);

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
   @Override
   public void onResume() {

       super.onResume();
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

