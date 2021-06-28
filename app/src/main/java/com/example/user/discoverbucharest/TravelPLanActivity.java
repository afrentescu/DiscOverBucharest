package com.example.user.discoverbucharest;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import Classes.Attraction;
import Classes.User;

import static java.lang.String.CASE_INSENSITIVE_ORDER;

public  class TravelPLanActivity extends AppCompatActivity {
    ListView lvTravelPlan;
    TextView tvOrder;
    TextView tvReceiveName, tvRate;

     Button btnMaps;
     RatingBar ratingBar;
    Map<String, Object> map;

    private ArrayList<String> arrayList;
    private FirebaseListAdapter<Attraction> adapter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){
          //  tvReceineName.setText(NAME);
        }
        setContentView(R.layout.activity_plan);


       map = new HashMap<>();
        lvTravelPlan = findViewById(R.id.lvTravelPlan);
        tvOrder= findViewById(R.id.textView4);
        tvOrder.setText("MY TRAVEL PLAN");

        arrayList = new ArrayList();

        FirebaseApp.initializeApp(this);

        final   DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        Query query = dbRef.child("users").child(currentUser.getUid()).child("attractionToSee");
        FirebaseListOptions<Attraction> options = new FirebaseListOptions.Builder<Attraction>().setLayout(R.layout.layoutspecialplan).setQuery(query, Attraction.class).build();
        adapter =new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {
               TextView tvRate = v.findViewById(R.id.textView9);
                btnMaps = v.findViewById(R.id.btnMapsTP);
                ratingBar = v.findViewById(R.id.ratingBarTP);
                tvReceiveName = v.findViewById(R.id.tvReceiveName);
                ImageView imagev = v.findViewById(R.id.imageView2);

               TextView tvReceiveProgramme = v.findViewById(R.id.tvReceiveProgramme);
                final  Attraction attraction = (Attraction) model;
                tvReceiveName.setText(attraction.getName());

                tvReceiveProgramme.setText("Program: \n " + attraction.getProgramme());
                if(attraction.getAttractionRate() != 0) {
                    tvRate.setText(String.valueOf(attraction.getAttractionRate()) + "★");
                }



               if((attraction.getName()).equals("Romanian Atheneum")){
                    imagev.setImageResource(R.drawable.ateneu);
                }
                if(attraction.getName().equals("Palace of the Parliament")){
                    imagev.setImageResource(R.drawable.palatulparlamentului);
                }
                if(attraction.getName().equals("Stavropoleos Monastery")){
                    imagev.setImageResource(R.drawable.manastire);
                }
                if(attraction.getName().equals("CEC Palace")){
                    imagev.setImageResource(R.drawable.cecpalace);
                }
                if(attraction.getName().equals("Mogosoaia Palace")){
                    imagev.setImageResource(R.drawable.mogosoaia);
                }
                if(attraction.getName().equals("The Royal Palace")){
                    imagev.setImageResource(R.drawable.muzeulartabuc);
                }
                if(attraction.getName().equals("Calea Victoriei")){
                    imagev.setImageResource(R.drawable.caleav);
                }
                if(attraction.getName().equals("Dacia Boulevard")){
                    imagev.setImageResource(R.drawable.daciajpg);
                }
                if(attraction.getName().equals("Spring Boulevard")){
                    imagev.setImageResource(R.drawable.primaverii);
                }
                if(attraction.getName().equals("Xenophon Street")){
                    imagev.setImageResource(R.drawable.xen);
                }
                if(attraction.getName().equals("\"Old Town\"")){
                    imagev.setImageResource(R.drawable.oldt);
                }
                if(attraction.getName().equals("Arthur Verona Street")){
                    imagev.setImageResource(R.drawable.verona);
                }
                if(attraction.getName().equals("Manuc's Inn")){
                    imagev.setImageResource(R.drawable.manuc);
                }
                if(attraction.getName().equals("Caru' cu Bere")){
                    imagev.setImageResource(R.drawable.car);
                }
                if(attraction.getName().equals("Linea closer to the moon")){
                    imagev.setImageResource(R.drawable.linea2);
                }
                if(attraction.getName().equals("Bite")){
                    imagev.setImageResource(R.drawable.bite);
                }
                if(attraction.getName().equals("Acuarela")){
                    imagev.setImageResource(R.drawable.acuarela);
                }
                if(attraction.getName().equals("Origo")){
                    imagev.setImageResource(R.drawable.origo);
                }
                if(attraction.getName().equals("M60")){
                    imagev.setImageResource(R.drawable.mm);
                }
                if(attraction.getName().equals("Unirea Shopping Center")){
                    imagev.setImageResource(R.drawable.unireajpg);
                }
                if(attraction.getName().equals("AFI Cotroceni")){
                    imagev.setImageResource(R.drawable.afi);
                }
                if(attraction.getName().equals("Promenada Mall")){
                    imagev.setImageResource(R.drawable.promenada);
                }
                if(attraction.getName().equals("Veranda Mall")){
                    imagev.setImageResource(R.drawable.verand);
                } if(attraction.getName().equals("ParkLake Shopping ")){
                    imagev.setImageResource(R.drawable.parklake);
                }
                if(attraction.getName().equals("Băneasa Shopping City")){
                    imagev.setImageResource(R.drawable.baeasa);
                }
                if(attraction.getName().equals("Sun Plaza")){
                    imagev.setImageResource(R.drawable.sunplaza);
                }
                if(attraction.getName().equals("Dimitrie Gusti National Village Museum")){
                    imagev.setImageResource(R.drawable.sat);
                }
                if(attraction.getName().equals("Romanian Peasant Museum")){
                    imagev.setImageResource(R.drawable.peasant);
                }
                if(attraction.getName().equals("The National Museum of Romanian History")){
                    imagev.setImageResource(R.drawable.istorie);
                }
                if(attraction.getName().equals("Grigore Antipa National Museum of Natural History")){
                    imagev.setImageResource(R.drawable.antipa);
                }
                if(attraction.getName().equals("Museum of Art Collections")){
                    imagev.setImageResource(R.drawable.muzeulartabuc);
                }
                if(attraction.getName().equals("\"Museum of Senses\"")){
                    imagev.setImageResource(R.drawable.simturi);
                }
                if(attraction.getName().equals("\"Arch of Triumph")){
                    imagev.setImageResource(R.drawable.arc);
                }
                if(attraction.getName().equals("Cismigiu Gardens")){
                    imagev.setImageResource(R.drawable.cismigiu);
                }
                if(attraction.getName().equals("Herastrau Park")){
                    imagev.setImageResource(R.drawable.herastrau);
                }
                if(attraction.getName().equals("Carol I Park")){
                    imagev.setImageResource(R.drawable.carol1);
                }
                if(attraction.getName().equals("Tineretului Park")){
                    imagev.setImageResource(R.drawable.tineretului);
                }
                if(attraction.getName().equals("Botanical Garden")){
                    imagev.setImageResource(R.drawable.botanic);
                }
                if(attraction.getName().equals("Titan Park")){
                    imagev.setImageResource(R.drawable.titan);
                }
                if(attraction.getName().equals("University Square")){
                    imagev.setImageResource(R.drawable.univ);
                }
                if(attraction.getName().equals("Revolution Square")){
                    imagev.setImageResource(R.drawable.revolutiei);
                }
                if(attraction.getName().equals("Unirii Square")){
                    imagev.setImageResource(R.drawable.uniriip);
                }
                if(attraction.getName().equals("Unirea Shopping Center")){
                    imagev.setImageResource(R.drawable.unireajpg);
                }
                if(attraction.getName().equals("AFI Cotroceni")){
                    imagev.setImageResource(R.drawable.afi);
                }
                if(attraction.getName().equals("Promenada Mall")){
                    imagev.setImageResource(R.drawable.promenada);
                }
                if(attraction.getName().equals("Veranda Mall")){
                    imagev.setImageResource(R.drawable.verand);
                }
                if(attraction.getName().equals("ParkLake Shopping ")){
                    imagev.setImageResource(R.drawable.parklake);
                }
                if(attraction.getName().equals("Băneasa Shopping City")){
                    imagev.setImageResource(R.drawable.baeasa);
                }


                ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                      final String groupId = String.valueOf(attraction.getAttractionId());
                        attraction.setAttractionRate(rating);
                        rating = attraction.getAttractionRate();
                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        if (currentUser != null) {
                        //   dbRef.child("users").child(currentUser.getUid()).child("attractionToSee").child(groupId).child("rate").setValue(rating);
                            Toast.makeText(getApplicationContext(), "Thank you for your rate!", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(TravelPLanActivity.this, "You need an account in order to give a rate!", Toast.LENGTH_LONG).show();

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

            }
        };



        lvTravelPlan.setAdapter(adapter);

/*
        if(spinner_order.getSelectedItem().toString().equals("A->Z")){
            Collections.sort(arrayList);

        }else if(spinner_order.getSelectedItem().toString().equals("Z->A")){
            Collections.reverse(arrayList);

        }

*/


    }

    private void DisplayTrack(String source){
        try{
            Uri uri = Uri.parse("https://www.google.co.in/maps/search/" + source );
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

    @Override
    protected void onResume() {
        super.onResume();

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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putString("NAME_KEY", tvReceineName.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
      // tvReceineName.setText( savedInstanceState.getString("NAME_KEY"));
        }



}

