package com.example.hp.firebaseconnection;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class FindProperty extends AppCompatActivity {

    RecyclerView recyclerView;
    Find_Prop_Adapter propAdapter;
    FirebaseStorage storage;
    StorageReference storageReference;
    private DatabaseReference fbase;
    FirebaseUser currentUser;


    private List<Prop_Model> mlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_property);

        recyclerView = findViewById(R.id.recyclerImgs);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mlist = new ArrayList<>();

        // Reference to storage
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        //Firebase current user
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // Reference to db
        fbase = FirebaseDatabase.getInstance().getReference("Properties");

        fbase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){

                Prop_Model prop = snapshot.getValue(Prop_Model.class);

                Log.d("", "Upload: data " + prop.getUri());
                mlist.add(prop);
            }

                propAdapter = new Find_Prop_Adapter(FindProperty.this,mlist);
                recyclerView.setAdapter(propAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
