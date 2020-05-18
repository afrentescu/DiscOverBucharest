package com.example.user.discoverbucharest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity {
    int setPassword = 1;
    EditText etNameLogin, etPasswordLogin;
    Button btnLogin,  btnSwap;
    private ProgressDialog pDialog;
    private FirebaseAuth firebaseAuth;
    TextView textview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textview = findViewById(R.id.textView5);
        textview.setText("You don't have an account? \n Create one here:");
        btnSwap = findViewById(R.id.btnSwapage);
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() !=  null){
           // finish();
            Toast.makeText(getApplicationContext(), "You are already logged in!", Toast.LENGTH_LONG).show();
           // Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            //startActivity(intent);
        }else{
            FirebaseUser user = firebaseAuth.getCurrentUser();
        }

        etNameLogin = findViewById(R.id.etNameLogin);
        etPasswordLogin = findViewById(R.id.etPaswordLogin);
        btnLogin = findViewById(R.id.btnLogin);

        pDialog = new ProgressDialog(this);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid()) {
                    userLogin();
                   // Intent intent = new Intent(getApplicationContext(), MainActivity.class); ///TODO: save into firebase
                    //startActivity(intent);
                    //Toast.makeText(getApplicationContext(), "Logged in succesfully!", Toast.LENGTH_LONG).show();
                   // btnLogin.setText("LogOut");
                }

            }
        });

        btnSwap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateAccountActivity.class);
                startActivity(intent);
            }
        });
    }
    private void userLogin(){
       final String email = etNameLogin.getText().toString();
       final String password = etPasswordLogin.getText().toString();
       pDialog.setMessage("Logging in...");
       pDialog.show();
       firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {
               pDialog.dismiss();
               if(task.isSuccessful()){
                   finish();
                   Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                   startActivity(intent);
                   Toast.makeText(getApplicationContext(), "Logged in succesfully!", Toast.LENGTH_LONG).show();

               }else{
                   Toast.makeText(getApplicationContext(), "Registration error", Toast.LENGTH_LONG).show();
               }
           }
       });
    }
    public void onClick(View v){
        if(setPassword == 1){
            setPassword = 0;
            etPasswordLogin.setTransformationMethod(null);
            if(etPasswordLogin.getText().length()>0)
                etPasswordLogin.setSelection(etPasswordLogin.getText().length());
            //  tvEye.setBackgroundResource(R.drawable.eye);
        }else{
            setPassword = 1;
            etPasswordLogin.setTransformationMethod(new PasswordTransformationMethod());
            if(etPasswordLogin.getText().length()>0)
                etPasswordLogin.setSelection(etPasswordLogin.getText().length());
        }
    }
    private boolean isValid(){
        boolean isValid = true;
        final String userName = etNameLogin.getText().toString();
        final String password = etPasswordLogin.getText().toString();
      //TODO: compare assword to the one in the database for a speficic user
        if (userName.isEmpty() || userName.length() > 30|| userName.length() <6) {
            etNameLogin.setError("Please enter a valid user name!");
            isValid = false;
        }

      /*  if (password.length() < 8 || password.isEmpty())  {
            etPasswordLogin.setError("the password should be at least 8 chars. long!");
            isValid = false;

        }
        String upperLetters = "(.*[A-Z].*)";
        if (!password.matches(upperLetters ))
        {
            etPasswordLogin.setError("Password should contain at least one upper letter");
            isValid = false;
        }
        String lowerLetters = "(.*[a-z].*)";
        if (!password.matches(lowerLetters ))
        {
            etPasswordLogin.setError("Password should contain atleast one lower case alphabet");
            isValid = false;
        }
        String numbers = "(.*[0-9].*)";
        if (!password.matches(numbers ))
        {
            etPasswordLogin.setError("Password should contain atleast one number.");
            isValid = false;
        }*/

        return isValid;
    }
}
