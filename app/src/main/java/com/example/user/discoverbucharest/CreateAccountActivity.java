package com.example.user.discoverbucharest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import Classes.User;

public class CreateAccountActivity extends AppCompatActivity {
    int setPassword =1;
    EditText etName, etSurname, etUserName, etPassword, etEmail, etCountry;
    Button btnCreate;
    private ProgressDialog progress;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    ColorDrawable colorDrawable;
    ActionBar bar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bar = getSupportActionBar();

        colorDrawable = new ColorDrawable(Color.parseColor("#DDA0DD"));
        bar.setBackgroundDrawable(colorDrawable);
        FirebaseApp.initializeApp(getApplicationContext());
        setContentView(R.layout.activity_createaccount);
        progress = new ProgressDialog(this);
        etName = findViewById(R.id.etName);
        etCountry = findViewById(R.id.etCountry);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etSurname = findViewById(R.id.etSurname);
        etUserName = findViewById(R.id.etUserName);
        btnCreate =findViewById(R.id.btnCreate);



        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        firebaseAuth = FirebaseAuth.getInstance();

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isValid()) {
                    registerUser();

//                    DatabaseReference  currentUserReferece = FirebaseDatabase.getInstance().getReference();

                    // writeUserToFirebase(currentUserReferece, name, surname, username, country, secretPassword, email);

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Account created succesfully!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void registerUser(){
        final String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();
        final String name = etName.getText().toString();
        final String surname = etSurname.getText().toString();
        final String username = etUserName.getText().toString();
        final String country = etCountry.getText().toString();

       if(!CreateAccountActivity.this.isFinishing()){
            progress.setMessage("Registering in progress...");
            progress.show();
        }
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Registration failed!", Toast.LENGTH_LONG).show();

                }else{
                    Log.d("Authentication", "Startig user authentication");

                    String userId = firebaseAuth.getCurrentUser().getUid();
                    DatabaseReference  currentUserReferece = FirebaseDatabase.getInstance().getReference().child("users").child(userId);

                    User user = new User(name, surname, username, country, password, email);

                  //  databaseReference.child("users").child(userId).setValue(user);


                    Log.d("Saving data", "Saving the user into firebase");
                    Map hashMap  = new HashMap();
                    hashMap.put("name", name);
                    hashMap.put("surname", surname);
                    hashMap.put("username", username);
                    hashMap.put("country", country);
                    hashMap.put("email", email);
                    hashMap.put("password", password);
                    currentUserReferece.setValue(hashMap);
                    Log.d("After saving", "User saved");

                }
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private  void writeUserToFirebase(DatabaseReference dbRef, String name, String surname, String username, String email, String country, String password){
        User user = new User(name, surname, username, email, country, password);
        FirebaseUser fbUser = firebaseAuth.getInstance().getCurrentUser();
        dbRef.child("users").child(fbUser.getUid()).setValue(user);
    }
    public void onClick(View v){
        if(setPassword == 1){
            setPassword = 0;
            etPassword.setTransformationMethod(null);
            if(etPassword.getText().length()>0)
                etPassword.setSelection(etPassword.getText().length());
            //  tvEye.setBackgroundResource(R.drawable.eye);
        }else{
            setPassword = 1;
            etPassword.setTransformationMethod(new PasswordTransformationMethod());
            if(etPassword.getText().length()>0)
                etPassword.setSelection(etPassword.getText().length());
        }
    }
    private boolean isValid(){
        boolean isValid = true;
        final String userName = etUserName.getText().toString();
        final String password = etPassword.getText().toString();
        final String firstname = etName.getText().toString();
        final String surname = etSurname.getText().toString();
        final String coutry = etCountry.getText().toString();
        final String email = etEmail.getText().toString();
        if (userName.isEmpty() || userName.length() > 30|| userName.length() <6) {
            etUserName.setError("Please enter a valid user name!");
            isValid = false;
        }
        if (firstname.isEmpty() || firstname.length() > 30) {
            etName.setError("Please enter a valid  name!");
            isValid = false;
        }
        if (surname.isEmpty() || surname.length() > 30) {
            etSurname.setError("Please enter a valid  surname!");
            isValid = false;
        }
        if (coutry.isEmpty() || coutry.length() > 30) {
            etCountry.setError("Please enter the name of your country!");
            isValid = false;
        }
        if(email.isEmpty() || (!email.contains("@")) && (!email.contains("."))){
            etEmail.setError(("Please enter a valid email address"));
            isValid = false;
        }
        if (password.length() < 8 || password.isEmpty())  {
            etPassword.setError("the password should be at least 8 chars. long!");
            isValid = false;

        }
        String upperLetters = "(.*[A-Z].*)";
        if (!password.matches(upperLetters ))
        {
            etPassword.setError("Password should contain at least one upper letter");
            isValid = false;
        }
        String lowerLetters = "(.*[a-z].*)";
        if (!password.matches(lowerLetters ))
        {
            etPassword.setError("Password should contain atleast one lower case alphabet");
            isValid = false;
        }
        String numbers = "(.*[0-9].*)";
        if (!password.matches(numbers ))
        {
            etPassword.setError("Password should contain atleast one number.");
            isValid = false;
        }
        return isValid;
    }

}
