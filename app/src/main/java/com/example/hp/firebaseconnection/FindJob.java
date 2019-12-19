package com.example.hp.firebaseconnection;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FindJob extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private StorageTask storageTask;

    private static final int PICK_IMAGE_REQUEST=1;

    private List<String> fileNameList;
    private String fileName;
    private Task<Uri> result;
    private String imgUri;
    private Uri imgPathUri;

    private EditText unameET,uemailET,ujobTitleET,uDescET,uContactET,uFeesET;
    private Button savBtn, chooseBtn;
    private RecyclerView chooseImgList;
    private ImagesRecyclerAdapter imagesRecyclerAdapter;
    //    private ImageView imgChooserIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_job);

        unameET= findViewById(R.id.nameET);
        uemailET=findViewById(R.id.uemailET);
        ujobTitleET=findViewById(R.id.jobTitleET);
        uDescET=findViewById(R.id.jobDescET);
        uContactET=findViewById(R.id.uContactET);
        uFeesET=findViewById(R.id.uFeesET);
        savBtn=findViewById(R.id.saveBtn);
        chooseBtn=findViewById(R.id.chooseImgBtn);
        chooseImgList=findViewById(R.id.chooseImages);
        //imgChooserIV=findViewById(R.id.portpholioImg);

        fileNameList=new ArrayList<>();
        imagesRecyclerAdapter=new ImagesRecyclerAdapter(fileNameList);

        //RecyclerView
        chooseImgList.setLayoutManager(new LinearLayoutManager(this));
        chooseImgList.setHasFixedSize(true);
        chooseImgList.setAdapter(imagesRecyclerAdapter);

        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference=FirebaseDatabase.getInstance().getReference();
        storageReference= FirebaseStorage.getInstance().getReference();

        savBtn.setOnClickListener(this);
        chooseBtn.setOnClickListener(this);


    }

    public void saveUserJobInformation(){

        String name=unameET.getText().toString().trim();
        String email=uemailET.getText().toString().trim();
        String jobTitle=ujobTitleET.getText().toString().trim();
        String jobDesc=uDescET.getText().toString().trim();
        String contact=uContactET.getText().toString();
        String fees=uFeesET.getText().toString();

        UserJobInformation userJobInformation = new UserJobInformation(name, email,jobTitle,jobDesc,contact,fees,imgUri);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child("Architectures").child(user.getUid()).setValue(userJobInformation);

        Toast.makeText(getApplicationContext(),"Information Successfully Saved",Toast.LENGTH_LONG).show();

    }

    private void chooseUserPortfolio(){
        //Get Images from our mobile storage
        Intent chooseImg = new Intent();
        chooseImg.setType("image/*");
        chooseImg.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        chooseImg.setAction(chooseImg.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(chooseImg,"Select The Image"),PICK_IMAGE_REQUEST);
    }

   //for Get the image in imageview
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK){
            if(data.getClipData()!=null){

                int totalItemsSelected = data.getClipData().getItemCount();

                for(int i=0;i<totalItemsSelected;i++){

                    imgPathUri =data.getClipData().getItemAt(i).getUri();

                    fileName=getFileName(imgPathUri);

                    fileNameList.add(fileName);
                    imagesRecyclerAdapter.notifyDataSetChanged();

                    uploadImgInDatabase();

                    //condition, that image upload only once
//                    if(storageTask!=null && storageTask.isInProgress()){
//                        Toast.makeText(getApplicationContext(),"Upload is in Progress",Toast.LENGTH_SHORT).show();
//                    }
//                    else{
//                    uploadImgInDatabase(); }
                }
            }
            if(data.getData()!=null){
                //single image selected
            }
        }
    }

    private void uploadImgInDatabase(){

        if(imgPathUri!=null) {

            final ProgressDialog progressDialog=new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            final StorageReference portfolioRef = storageReference.child("Images").child(fileName);

           storageTask= portfolioRef.putFile(imgPathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Image Uploaded",Toast.LENGTH_LONG).show();
//                           imgUri=taskSnapshot.getMetadata().toString();
                            // imgUri=taskSnapshot.getUploadSessionUri().toString();
                            result= taskSnapshot.getMetadata().getReference().getDownloadUrl();
                              //imgUri=portfolioRef.getDownloadUrl().toString();
                            result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                 imgUri=uri.toString();
//                                 Intent selfIntent = new Intent(FindJob.this,FindJob.class);
//                                 selfIntent.putExtra("PORTFOLIO",imgUri);
//                                 startActivity(selfIntent);
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),exception.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress=(100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage((int)progress+"% Uploaded...");

                        }
                    });
        }else{
            //display a error toast
        }
    }

    @Override
    public void onClick(View v) {
       if(v==savBtn){
        saveUserJobInformation();
        }
       if(v==chooseBtn){
         chooseUserPortfolio();
       }
    }

    //Get the Images Name
    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

}
