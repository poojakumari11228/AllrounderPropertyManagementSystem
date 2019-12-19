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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText loginEmailET, loginPassEt;
    private Button loginBtn;
    private TextView signUpTv;
    String loginEmail, loginPass;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null){
            //user Already logged in
            finish();
            //start the home activity
            Intent homeIntent=new Intent(LoginActivity.this, Home.class);
            startActivity(homeIntent);

        }
        progressDialog = new ProgressDialog(this);

        loginEmailET = findViewById(R.id.loginEmailField);
        loginPassEt = findViewById(R.id.loginPasswordField);
        loginBtn=findViewById(R.id.loginBtn);
        signUpTv=findViewById(R.id.signUpTextView);

        loginBtn.setOnClickListener(this);
        signUpTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == loginBtn){
            loginUser();
        }
        if(v==signUpTv){
            //stop the login Activity
            finish();
            //SignUP Activity start here.
            Intent signUpActivity = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(signUpActivity);
        }

    }

    public void loginUser(){

        loginEmail=loginEmailET.getText().toString().trim();
       loginPass=loginPassEt.getText().toString().trim();
        if(TextUtils.isEmpty(loginEmail)){
            //email is empty
            Toast.makeText(getApplicationContext(),"Please Enter The Email",Toast.LENGTH_SHORT).show();
            //stopping the function to execute furture
            return;
        }
        if(TextUtils.isEmpty(loginPass)){
            //password is empty
            Toast.makeText(getApplicationContext(),"Please Enter The Password",Toast.LENGTH_SHORT).show();
            //stopping the function to execute furture
            return;
        }

        //after validation
        progressDialog.setMessage("loading...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(loginEmail,loginPass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //user successfully login
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Successfully Login!",Toast.LENGTH_SHORT).show();
                            finish();
                            //start the home activity
                            Intent homeIntent=new Intent(LoginActivity.this, Home.class);
                            startActivity(homeIntent);

                        }
                        else {
                            //user not registered
                            Toast.makeText(getApplicationContext(),"Wrong Username OR Password, Please Try Again!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

}
