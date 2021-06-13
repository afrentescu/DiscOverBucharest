package com.example.user.discoverbucharest;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import Classes.Attraction;

public class MainActivity extends AppCompatActivity {
    ColorDrawable colorDrawable;
    ListView listViewCategories;
    ActionBar bar;
    int[] IMAGES = { R.drawable.ateneudoi, R.drawable.caleav,R.drawable.titan, R.drawable.muzeulartabuc,   R.drawable.linea2 ,R.drawable.parklake};
    String[] CATEGORIES = {"Historical Buildings", "Streets", "Parks&Squares", "Museums","Coffees&Restaurants", "Malls"};
    FirebaseDatabase database;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseAuth firebaseAuth;
    private  ArrayList<Attraction> attractionList;

    Attraction attraction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        attraction = new Attraction();
        listViewCategories = findViewById(R.id.listViewCategories);
        bar = getSupportActionBar();

        colorDrawable = new ColorDrawable(Color.parseColor("#DDA0DD"));
        bar.setBackgroundDrawable(colorDrawable);

        CustomAdapter customAdapter = new CustomAdapter();
        listViewCategories.setAdapter(customAdapter);

        listViewCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position ==0){
                    Intent intent = new Intent(getApplicationContext(), HistoricalBuildingsActivity.class);
                    startActivity(intent);
                }
                else if(position ==1){
                    Intent intent = new Intent(getApplicationContext(), StreetsActvity.class);
                    startActivity(intent);
                }else if(position ==2){
                    Intent intent = new Intent(getApplicationContext(), ParksActivity.class);
                    startActivity(intent);
                }else if(position == 3){
                    Intent intent = new Intent(getApplicationContext(), MuseumsActivity.class);
                    startActivity(intent);
                }else if(position ==4){
                    Intent intent = new Intent(getApplicationContext(), CoffeeActivity.class);
                    startActivity(intent);
                }else if(position == 5){
                    Intent intent = new Intent(getApplicationContext(), MallsActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.logout);
        MenuItem item2 = menu.findItem(R.id.login);
        MenuItem item3 = menu.findItem(R.id.createAccount);
        if (currentUser == null) {
            item.setVisible(false);
            invalidateOptionsMenu();
        }else{
            item2.setVisible(false);
            item3.setVisible(false);
            invalidateOptionsMenu();
        }


        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.createAccount:
                Intent intent = new Intent(getApplicationContext(), CreateAccountActivity.class);
                this.startActivity(intent);
                return true;

            case R.id.login:
                    Intent intent2 = new Intent(getApplicationContext(), LogInActivity.class);
                    this.startActivity(intent2);


                return true;
            case R.id.logout:

                firebaseAuth.signOut();
                Toast.makeText(getApplicationContext(), "You logged out successfully!", Toast.LENGTH_LONG).show();
                return true;


            case R.id.travelPlan:
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser == null){
                    Toast.makeText(getApplicationContext(), "Create account or log in to create your travel plan!", Toast.LENGTH_LONG).show();
                }else {
                    Intent intent3 = new Intent(getApplicationContext(), TravelPLanActivity.class);
                    this.startActivity(intent3);
                }

                return true;
            case R.id.budget:
                Intent intent4 = new Intent(getApplicationContext(), BugdetActivity.class);
                this.startActivity(intent4);
                return true;
            case R.id.trasportation:
                Intent intent5 = new Intent(getApplicationContext(), TransportationActivity.class);
                startActivity(intent5);
                return true;
            case R.id.impressions:
                 currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser == null){
                    Toast.makeText(getApplicationContext(), "Please log in!", Toast.LENGTH_LONG).show();
                }else {
                    Intent intent6 = new Intent(getApplicationContext(), ImpressionActivity.class);
                    this.startActivity(intent6);
                }
                return true;
            case R.id.daily:
                Intent intent7 = new Intent(getApplicationContext(), DailyPlanActivity.class);
                this.startActivity(intent7);
                return true;

            case R.id.maps:
                Intent intent8 = new Intent(getApplicationContext(), MapsActivity.class);
                this.startActivity(intent8);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }



    }

    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return IMAGES.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            view = getLayoutInflater().inflate(R.layout.customlayoutmain, null);
            ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
            TextView tvCategories = (TextView)view.findViewById(R.id.tvCategory);
            imageView.setImageResource(IMAGES[position]);
            tvCategories.setText(CATEGORIES[position]);
            return view;
        }
    }
}
