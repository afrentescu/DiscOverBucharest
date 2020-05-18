package com.example.user.discoverbucharest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import Classes.Attraction;
import Classes.User;

public class ImpressionActivity extends AppCompatActivity {
    TextView tvImp;
    private Spinner spinner_attraction;
    private ArrayAdapter<String> spinnerAdapter;
    private ArrayList<String> arrayList;
    private Button btnComment;
    private EditText etIMP;
    private String  text, value;
    private  Map<String, Object> map;
     ListView lvIMP;
    private FirebaseListAdapter<Object> adapter;


    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_attractionimpression);
        tvImp = findViewById(R.id.tvImpression);
        tvImp.setText("Choose attraction:");
        etIMP = findViewById(R.id.editTextIMP2);

        map = new HashMap<>();
       lvIMP = findViewById(R.id.lvIMP);
        spinner_attraction = findViewById(R.id.spinner2);
        final String[] array = new String[]{"Romanian Atheneum", "Palace of the Parliament", "Stavropoleos Monastery", "CEC Palace", "Mogosoaia Palace",
                "The Royal Palace", "Calea Victoriei", "Dacia Boulevard", "Spring Boulevard", "Xenophon Street", "Old Town", "Arthur Verona Street",
                "Manuc's Inn", "Caru' cu Bere", "Linea closer to the moon", "Bite", "Acuarela", "Origo", "M60", "Dimitrie Gusti National Village Museum", "Romanian Peasant Museum",
                "The National Museum of Romanian History", "Grigore Antipa National Museum of Natural History", "Museum of Art Collections", "Museum of Senses",
                "Arch of Triumph", "Cismigiu Gardens", "Herastrau Park", "Carol I Park", "Tineretului Park", "Botanical Garden", "Titan Park", "University Square",
                "Revolution Square", "Unirii Square"};
        arrayList = new ArrayList<String>(Arrays.asList(array));
        Collections.sort(arrayList);
        spinnerAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_attraction.setAdapter(spinnerAdapter);

        spinner_attraction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                value = parent.getItemAtPosition(position).toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("users").child(currentUser.getUid()).child("impressions");
        btnComment = findViewById(R.id.btnADDimp);
        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser != null) {
                    text = value + ": \n " + etIMP.getText().toString();

                    map.put(value, text );
                    dbRef.updateChildren(map);

                }
            }

        });



        Query query = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid()).child("impressions");
        FirebaseListOptions<Object> options = new FirebaseListOptions.Builder<Object>().setLayout(R.layout.simplelayoutimpresson).setQuery(query, Object.class).build();
        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {
                TextView tvName;
                tvName = v.findViewById(R.id.textView6);



                //  tvName.setText("Name:" + attraction.getAttractionName());
                tvName.setText( model.toString());



            }
        };

        lvIMP.setAdapter(adapter);

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





