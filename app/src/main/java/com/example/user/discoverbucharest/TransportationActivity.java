package com.example.user.discoverbucharest;

import android.content.Intent;
import android.net.Uri;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Collections;

import Classes.Attraction;

public class TransportationActivity extends AppCompatActivity {
    ListView lvTrans;
    private FirebaseListAdapter<Attraction> adapter;
    URL url;
    URI uri;

    {
        try {
            url = new URL("http://www.metrorex.ro/first_page_p1352-2");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    URL url2;

    {
        try {
            url2 = new URL("http://stbsa.ro/eng/portofel_electronic_eng.php");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
  URL[] urls  = { url, url2};
    String[] NAMES = {"http://stbsa.ro/eng/index.php","http://www.metrorex.ro/first_page_p1352-2" , "http://stbsa.ro/eng/portofel_electronic_eng.php", " "};
    int[] IMAGES = { R.drawable.stb, R.drawable.hartamet,R.drawable.ticketts, R.drawable.uber};
    // <a href ="http://www.metrorex.ro/first_page_p1352-2"></a>
    //        <a href = "http://stbsa.ro/eng/portofel_electronic_eng.php"></a>
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layouttransportation);
        lvTrans = findViewById(R.id.lvTrans);

        FirebaseApp.initializeApp(this);

        Query query = FirebaseDatabase.getInstance().getReference().child("attractions").child("transportation");
        final FirebaseListOptions<Attraction> options = new FirebaseListOptions.Builder<Attraction>().setLayout(R.layout.customlayouttransportation).setQuery(query, Attraction.class).build();
        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {
              //  Collections.sort();
                TextView tvName,  tvProgramme, tvDescription, tvReference;
                tvName = v.findViewById(R.id.tvCustonTransName);
                tvDescription  =v.findViewById(R.id.tvCustomTransInformationn);
                tvProgramme = v.findViewById(R.id.tvCustonTransProgramme);
                tvReference = v.findViewById(R.id.tvReference);
                ImageView imgAtt = v.findViewById(R.id.imageViewCustom);
                Attraction attraction = (Attraction)model;

                tvName.setText( attraction.getName());

                tvDescription.setText("Description:\n" + attraction.getDescription());
                tvProgramme.setText("Programme:\n" + attraction.getProgramme());
                imgAtt.setImageResource(IMAGES[position]);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                //intent.toURI(urls[position]);
               tvReference.setText(NAMES[position]);
            }
        };
        lvTrans.setAdapter(adapter);

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

