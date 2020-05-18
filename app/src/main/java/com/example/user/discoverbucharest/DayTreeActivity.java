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

public class DayTreeActivity extends AppCompatActivity {
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
        Query query = FirebaseDatabase.getInstance().getReference().child("dayTree");
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


                if(tvName.getText().equals("Manuc's Inn")){
                    imgAtt.setImageResource(R.drawable.manuc);
                }
                if(tvName.getText().equals("Unirii Square")){
                    imgAtt.setImageResource(R.drawable.fantani);
                }
                if(tvName.getText().equals("Xenophon Street")){
                    imgAtt.setImageResource(R.drawable.xen);
                }
                if(tvName.getText().equals("Tineretului Park")){
                    imgAtt.setImageResource(R.drawable.copiilor);
                }
                if(tvName.getText().equals("Carol I Park")){
                    imgAtt.setImageResource(R.drawable.carol1);
                }
                if(tvName.getText().equals("Sun Plaza")){
                    imgAtt.setImageResource(R.drawable.sunplaza);
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
