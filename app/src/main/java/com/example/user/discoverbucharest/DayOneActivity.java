package com.example.user.discoverbucharest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

import Classes.Attraction;

public class DayOneActivity extends AppCompatActivity {
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
        Query query = FirebaseDatabase.getInstance().getReference().child("dayOne");
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
                    imgAtt.setImageResource(R.drawable.ateneu);

                 if(attraction.getName().equals("Palace of the Parliament")){
                     imgAtt.setImageResource(R.drawable.palatnou);
                 }
                if(attraction.getName().equals("Calea Victoriei")){
                    imgAtt.setImageResource(R.drawable.vicdoi);
                }
                if(attraction.getName().equals("Caru' cu bere")){
                    imgAtt.setImageResource(R.drawable.cardoi);
                }
                if(attraction.getName().equals("CEC Palace")){
                    imgAtt.setImageResource(R.drawable.cecdoi);
                }

                if(attraction.getName().equals("Stavropoleos Monastery")){
                    imgAtt.setImageResource(R.drawable.manastire);
                }
                if(attraction.getName().equals("Old Town")){
                    imgAtt.setImageResource(R.drawable.oldt);
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
