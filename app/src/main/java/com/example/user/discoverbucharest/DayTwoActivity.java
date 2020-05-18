package com.example.user.discoverbucharest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import Classes.Attraction;

public class DayTwoActivity extends AppCompatActivity {
    ListView lvDaily;
    FirebaseListAdapter<Attraction> adapter;
    TextView tvName, tvLocation, tvProgramme, tvDescription;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_specific_daily_plan);

        lvDaily = findViewById(R.id.lvDAILY);


        FirebaseApp.initializeApp(this);

        final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        Query query = FirebaseDatabase.getInstance().getReference().child("dayTwo");
        FirebaseListOptions<Attraction> options = new FirebaseListOptions.Builder<Attraction>().setLayout(R.layout.layout_custom_daily_plan).setQuery(query, Attraction.class).build();
        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, final int position) {

                tvName = v.findViewById(R.id.tvDailyName);
                tvLocation = v.findViewById(R.id.tvDailyLocation);
                tvDescription = v.findViewById(R.id.tvDescription);
                tvProgramme = v.findViewById(R.id.tvDailyProgramme);
                ImageView imgAtt = v.findViewById(R.id.imgCustomPlan);



                final  Attraction attraction = (Attraction) model;
                tvName.setText(attraction.getName());

                tvProgramme.setText("Programme:\n" + attraction.getProgramme());
                tvLocation.setText("Location:\n" + attraction.getLocation());


                if(tvName.getText().equals("Romanian Atheneum")){
                    imgAtt.setImageResource(R.drawable.ateneudoi);
                }
                if(tvName.getText().equals("Grigore Antipa Museum")){
                    imgAtt.setImageResource(R.drawable.antipa);
                }
                if(tvName.getText().equals("Herastrau Park")){
                    imgAtt.setImageResource(R.drawable.herastrau);
                }
                if(tvName.getText().equals("Dimitrie Gusti National Village Museum")){
                    imgAtt.setImageResource(R.drawable.sat);
                }
                if(tvName.getText().equals("The Royal Palace")){
                    imgAtt.setImageResource(R.drawable.muzeulartabuc);
                }
                if(tvName.getText().equals("Beraria H")){
                    imgAtt.setImageResource(R.drawable.berarie);
                }


            }



        };
        lvDaily.setAdapter(adapter);


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
