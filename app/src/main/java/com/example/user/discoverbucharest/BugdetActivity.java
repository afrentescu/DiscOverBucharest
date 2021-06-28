package com.example.user.discoverbucharest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
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

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Map;

import Classes.Attraction;
import Classes.User;

public class BugdetActivity extends AppCompatActivity implements Serializable {
    TextView tvCurrentBudget;
    User user ;
    Button btnUpdate;
    float curentBudget;
    float newCurrentB ;
    String  maximmm;
    NotificationCompat.Builder notification;
    String ticketPrices;
    EditText etMAXIM, etMINIM, etDAILY;
   TextView tvMessage, tvBudget;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
   @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_budget);
       etMAXIM = findViewById(R.id.etMAXb);
       btnUpdate = findViewById(R.id.btnUpdate);
        tvMessage = findViewById(R.id.textView2);
        tvBudget = findViewById(R.id.tvBudget);
       // tvMessage.setText("We want to help you manage your budget during this trip1! /n If you are entering the application for the first time, enter the ammount you planned on spending while visiting touristical attractions. /n" +
             //   "When you choose to visit a specific place and add it to your travel plan, we will substract the cost of the ticket from the total amount for you, if there is any! " +
               // "Enjoy! :D");

      if(savedInstanceState != null){

}

        user = new User();


       FirebaseApp.initializeApp(this);
     final  FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

       final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
      // etMINIM = findViewById(R.id.etMINb);
    //   etDAILY = findViewById(R.id.etDAILYb);

    //   tvCurrentBudget = findViewById(R.id.textView3);

       dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                maximmm = dataSnapshot.child("users").child(currentUser.getUid()).child("maxBudget").getValue(String.class);
                if(maximmm == null){
                    tvBudget.setText(" ");
                    tvMessage.setText("We want to help you manage your budget during this trip! Please " +
                            "enter the amount you planned on spending while visiting touristical attractions. We will notify you when there are only 100Ron left!" );
                }else{
                    if(maximmm.length() < 3){
                        sendNotification();

                    }
                    tvBudget.setText(maximmm + " Ron");
                    tvMessage.setText("When you choose to visit a specific place and add it to your travel plan, we will substract the cost of the ticket from the total amount for you, if there is any! Enjoy! :D");
                    btnUpdate.setVisibility(View.INVISIBLE);
                    etMAXIM.setVisibility(View.INVISIBLE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



       notification = new NotificationCompat.Builder(this);


     btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser == null) {
                    Intent intent = new Intent (getApplicationContext(), LogInActivity.class);
                    startActivity(intent);
                }else{
                    String maxbudget = String.valueOf(etMAXIM.getText());
                    dbRef.child("users").child(currentUser.getUid()).child("maxBudget").setValue(maxbudget);
                    Toast.makeText(BugdetActivity.this, "Budget set succesfully!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent (getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                  //  tvBudget.setText(dbRef.child(currentUser.getUid()).child("maxBudget").toString());

                }

        }
        });

/*

       if(currentUser != null){
           //  String savedMaxBudget = savedInstanceState.getString("maximBudget");
//           etMaxB.setText(savedMaxBudget);
           // etMINIM.setText(dbRef.child("minBudget").toString());
           //etMAXIM.setText(dbRef.child("maxBudget").toString());

           ValueEventListener valueEventListener = new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   //   user.setMinBudget(dataSnapshot.child("users").child(currentUser.getUid()).child("minBudget").getValue(String.class));
                   //  etMINIM.setText(user.getMinBudget());
                    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("users");
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                   String maximmm = dataSnapshot.child("users").child(currentUser.getUid()).child("maxBudget").getValue(String.class);
                    String minimmmm = dataSnapshot.child("users").child(currentUser.getUid()).child("minBudget").getValue(String.class);

                   Float dailyBudget = dataSnapshot.child("users").child(currentUser.getUid()).child("dailyBudget").getValue(Float.class);
                   Float current = dataSnapshot.child("users").child(currentUser.getUid()).child("currentBudget").getValue(Float.class);
                //   if (currentUser != null && maximmm != null && minimmmm != null && current != null) {
                       etMAXIM.setText(dataSnapshot.child("users").child(currentUser.getUid()).child("maxBudget").getValue(String.class));
                //   tvCurrentBudget.setText(dailyBudget + "RON");

                   etMINIM.setText(dataSnapshot.child("users").child(currentUser.getUid()).child("minBudget").getValue(String.class));
                    //   tvCurrentBudget.setText(dataSnapshot.child("users").child(currentUser.getUid()).child("currentBudget").getValue(Float.class).toString() + "  RON");
                       // tvCurrentBudget.setText(String.valueOf(newBB));
                  // }



               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
           };
             dbRef.addListenerForSingleValueEvent(valueEventListener);


       }
*/

   }


    @Override
    protected void onStart() {
        super.onStart();





    }

    @Override
    protected void onStop() {
        super.onStop();

    }




    @Override
    public void onResume() {
        super.onResume();

    }



    private void updateUser( ){

        DatabaseReference dbRef;
        dbRef = FirebaseDatabase.getInstance().getReference("users");


        dbRef.addValueEventListener(new ValueEventListener() {
           @RequiresApi(api = Build.VERSION_CODES.O)
           @Override
           public void onDataChange(@NonNull DataSnapshot dbSnap) {
                FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference dbRef;
               dbRef = FirebaseDatabase.getInstance().getReference("users");
                String maxbudget = String.valueOf(etMAXIM.getText());
                String minbudget = etMINIM.getText().toString();
               // String dailyBudget = etDAILY.getText().toString();

          //  String dailyBudget = getIntent().getStringExtra("ticketPrices");
        //     Float dailyBudget = dbSnap.child("users").child(currentUser.getUid()).child("dailyBudget").getValue(Float.class);
              //  curentBudget  = Float.parseFloat(maxbudget);
               //  curentBudget = Long.valueOf(maxbudget) -  Long.valueOf(ticketPrices);
//             curentBudget = computeCurrentBalance();
//              if(curentBudget <= Float.parseFloat(minbudget ))
//                  sendNotification();
            //  if(Float.parseFloat(maxbudget) != 0) {
              //    maxbudget = maxbudget - dailyBudget;

             //     tvCurrentBudget.setText(dailyBudget + "RON");
              // - aici imi trebuie alt textbox etMAXIM.setText(maxbudget);
             // }


                   if (fbUser != null) {

                       dbRef.child(fbUser.getUid()).child("maxBudget").setValue(maxbudget);

                   }
            //   etMAXIM.setText(dbSnap.child("users").child(currentUser.getUid()).child("maxBudget").getValue(String.class));

               }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });


    }

    private float computeCurrentBalance(){

        //TODO: compute current budget by substractiong from the current amount the daily amount spent
        //TODO: make data persist in the view
         FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference dbRef;
        dbRef = FirebaseDatabase.getInstance().getReference("users");

        dbRef.child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, String> data = (Map<String, String>) dataSnapshot.getValue();

                String currentB;
                 FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                Float maximmm = dataSnapshot.child("users").child(currentUser.getUid()).child("maxBudget").getValue(Float.class);
                Float minimmmm = dataSnapshot.child("users").child(currentUser.getUid()).child("minBudget").getValue(Float.class);
                Float current = dataSnapshot.child("users").child(currentUser.getUid()).child("dailyBudget").getValue(Float.class);
                if (currentUser != null && maximmm != null && minimmmm != null ) {
                   // String maximB = String.valueOf(data.get("maxBudget"));
                    String minimB = String.valueOf(data.get("minBudget"));
                    String dailyBB = String.valueOf(data.get("dailyBudget"));

                    //currentB = maximB;
                    if (maximmm >= minimmmm) {
                        maximmm = maximmm - current;
                        newCurrentB = maximmm ;
                    } else {
                        sendNotification();
                    }
                    dbRef.child(currentUser.getUid()).child("maxBudget").setValue(newCurrentB);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

   return newCurrentB;

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendNotification(){



        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String chanel_id = "3000";
            CharSequence name = "Channel Name";
            String description = "Chanel Description";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel mChannel = new NotificationChannel(chanel_id, name, importance);
            mChannel.setDescription(description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.BLUE);
            nm.createNotificationChannel(mChannel);
            notification = new NotificationCompat.Builder(this, chanel_id);
        } else {
            notification = new NotificationCompat.Builder(this);
        }
        notification.setSmallIcon(R.drawable.used);
        notification.setTicker("You have reached the MININUN budget for your trip!");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("Trip budget");
        notification.setContentText("Minimum budget reached! Only 100 RON left from the allocated ammount!");

        Intent intent = new Intent(getApplicationContext(), BugdetActivity.class);
        PendingIntent pdi = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        notification.setContentIntent(pdi);
        int notid  =1;
        nm.notify(notid, notification.build());
    }



}

