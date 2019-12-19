package com.example.hp.firebaseconnection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends AppCompatActivity implements View.OnClickListener{

    private TextView usernameTv;
    private Button logoutBtn, jobBtn, archiBtn;
    Button addProp,findProp;
    String userId;
    private FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseAuth.getCurrentUser()==null){
            userId = currentUser.getUid();
            //user in logged out
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        FirebaseUser user= firebaseAuth.getCurrentUser();

        usernameTv = findViewById(R.id.usernameTextView);
        logoutBtn=findViewById(R.id.logoutBtn);
        jobBtn=findViewById(R.id.findJob);
        archiBtn=findViewById(R.id.findArchi);
//        loginTv=findViewById(R.id.login);
//        signUpTv=findViewById(R.id.signup);

        addProp = findViewById(R.id.addProp);
        findProp = findViewById(R.id.findProp);

        usernameTv.setText(user.getEmail());


        logoutBtn.setOnClickListener(this);
        jobBtn.setOnClickListener(this);
        archiBtn.setOnClickListener(this);

        addProp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Home.this,AddProperty.class);
                in.putExtra("userId",userId);
                startActivity(in);
            }
        });

        findProp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Home.this,FindProperty.class);
                in.putExtra("userId",userId);
                startActivity(in);
            }
        });


    }

    @Override
    public void onClick(View v) {
       if(v==logoutBtn){
           firebaseAuth.signOut();
           finish();
           startActivity(new Intent(this, LoginActivity.class));
       }
       if(v==jobBtn){
        startActivity(new Intent(this, FindJob.class));
       }
       if(v==archiBtn){
           startActivity(new Intent(Home.this,FindArchitects.class));
       }
    }
}
