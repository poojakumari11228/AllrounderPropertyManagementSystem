package com.example.hp.firebaseconnection;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText emailET, passEt;
    private Button signUpBtn;
    private TextView signInTv;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null) {
            //user Already logged in
            finish();
            //start the home activity
            Intent homeIntent = new Intent(this, Home.class);
            startActivity(homeIntent);
        }

        progressDialog = new ProgressDialog(this);
        emailET = findViewById(R.id.emailField);
        passEt = findViewById(R.id.passwordField);
        signUpBtn=findViewById(R.id.signupBtn);
        signInTv=findViewById(R.id.signInTextView);

        signUpBtn.setOnClickListener(this);
        signInTv.setOnClickListener(this);
        }

    @Override
    public void onClick(View v) {
      if(v == signUpBtn){
          registerUser();
      }
      if(v==signInTv){
          //Login Activity start here.
          Intent loginActivity = new Intent(SignupActivity.this, LoginActivity.class);
          startActivity(loginActivity);
      }

    }

    //registerUser Method
    public void registerUser(){
        email=emailET.getText().toString().trim();
        password=passEt.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(getApplicationContext(),"Please Enter The Email",Toast.LENGTH_SHORT).show();
            //stopping the function to execute furture
            return;
        }
        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(getApplicationContext(),"Please Enter The Password",Toast.LENGTH_SHORT).show();
            //stopping the function to execute furture
            return;
        }

        //after validation
        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //user successfully registered
                            progressDialog.dismiss();
                            finish();

                            Toast.makeText(getApplicationContext(),"Successfully Registered!",Toast.LENGTH_SHORT).show();

                            //start the home activity
                            Intent homeIntent = new Intent(SignupActivity.this, Home.class);
                            startActivity(homeIntent);


                        }
                        else {
                            //user not registered
                            Toast.makeText(getApplicationContext(),"Could Not Register, Please Try Again!"+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}


