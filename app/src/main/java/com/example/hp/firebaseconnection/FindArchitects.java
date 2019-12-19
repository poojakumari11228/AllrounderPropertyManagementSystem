package com.example.hp.firebaseconnection;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FindArchitects extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FindArchiRecyclerAdaptor findArchiRecyclerAdaptor;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private List<UserJobInformation> userJobInformationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_architects);

        recyclerView=findViewById(R.id.findArchi_RecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userJobInformationList=new ArrayList<>();

        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference=FirebaseDatabase.getInstance().getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.child("Architectures").getChildren()){
                    UserJobInformation userJobInformation=postSnapshot.getValue(UserJobInformation.class);
                    userJobInformationList.add(userJobInformation);
                }
                findArchiRecyclerAdaptor=new FindArchiRecyclerAdaptor(FindArchitects.this,userJobInformationList);
                recyclerView.setAdapter(findArchiRecyclerAdaptor);
                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
