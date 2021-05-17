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
import java.util.Map;

import Classes.Attraction;
import Classes.User;

public class BugdetActivity extends AppCompatActivity implements Serializable {
    TextView tvCurrentBudget;
    User user ;
    Button btnUpdate;
    float curentBudget;
    float newCurrentB ;
    NotificationCompat.Builder notification;
    String ticketPrices;
    EditText etMAXIM, etMINIM, etDAILY;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
   @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      if(savedInstanceState != null){

}
        setContentView(R.layout.activity_budget);
        user = new User();

         ticketPrices = getIntent().getStringExtra(Intent.EXTRA_TEXT);

       FirebaseApp.initializeApp(this);
       FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

       final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
       etMAXIM = findViewById(R.id.etMAXb);
       etMINIM = findViewById(R.id.etMINb);
       etDAILY = findViewById(R.id.etDAILYb);

       tvCurrentBudget = findViewById(R.id.textView3);

        btnUpdate = findViewById(R.id.btnUpdate);
       Toast.makeText(BugdetActivity.this, ticketPrices, Toast.LENGTH_LONG).show();

        notification = new NotificationCompat.Builder(this);

   /*    btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser == null) {
                    Intent intent = new Intent (getApplicationContext(), LogInActivity.class);
                    startActivity(intent);
                }else{
                    etMinB.setText(dbRef.child(currentUser.getUid()).child("minBudget").toString());
                    etMaxB.setText(dbRef.child(currentUser.getUid()).child("maxBudget").toString());

                }

        }
        });*/
       btnUpdate = findViewById(R.id.btnUpdate);
       btnUpdate.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
               if (currentUser == null) {
                   Intent intent = new Intent (getApplicationContext(), LogInActivity.class);
                   startActivity(intent);
               } else {

                   updateUser();

               }


           }
       });


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
                Float maxbudget = dbSnap.child("users").child(currentUser.getUid()).child("maxBudget").getValue(Float.class);
                String minbudget = etMINIM.getText().toString();
               // String dailyBudget = etDAILY.getText().toString();

          //  String dailyBudget = getIntent().getStringExtra("ticketPrices");
             Float dailyBudget = dbSnap.child("users").child(currentUser.getUid()).child("dailyBudget").getValue(Float.class);
              //  curentBudget  = Float.parseFloat(maxbudget);
               //  curentBudget = Long.valueOf(maxbudget) -  Long.valueOf(ticketPrices);
             curentBudget = computeCurrentBalance();
              if(curentBudget <= Float.parseFloat(minbudget ))
                  sendNotification();
            //  if(Float.parseFloat(maxbudget) != 0) {
                  maxbudget = maxbudget - dailyBudget;

                  tvCurrentBudget.setText(dailyBudget + "RON");
              // - aici imi trebuie alt textbox etMAXIM.setText(maxbudget);
             // }
                   if (fbUser != null) {

                       dbRef.child(fbUser.getUid()).child("maxBudget").setValue(maxbudget);
                       dbRef.child(fbUser.getUid()).child("minBudget").setValue(minbudget);
                       dbRef.child(fbUser.getUid()).child("dailyBudget").setValue(dailyBudget);

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
    private void sendNotification(){



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
        notification.setContentText("Minimum budget reached");

        Intent intent = new Intent(getApplicationContext(), BugdetActivity.class);
        PendingIntent pdi = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        notification.setContentIntent(pdi);
        int notid  =1;
        nm.notify(notid, notification.build());
    }



}

