package com.example.hp.firebaseconnection;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddProperty extends AppCompatActivity {

    // declaration
    Button chooseImg_bt,addProp_bt;
    RadioGroup propType_rg;
    RecyclerView recyclerView;
    Add_Prop_Adapter adapter;
    GridLayout gridLayout;
    EditText propTitle_et,propDesc_et,propPrice_et,propArea_et,propEmail_et,propNmbr_et;
    private List<String> mlist;
    String imageEncoded;

    // firebase
    FirebaseStorage storage;
    StorageReference storageReference;
    private DatabaseReference fbase;
    FirebaseUser currentUser;

    List<String> imagesEncodedList;
    Uri[] newuri=null;
    int j;
    Uri uri;
    //Uri uri[]=null;


    //Instance variable for data
    String propType,propTitle,propDesc,propPrice,propArea,propEmail,userId;
    int propNmbr;


    StorageTask<UploadTask.TaskSnapshot> uploadTask;

    // request code for intent
    private static final int IMG_PICK_CODE = 400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);

        //define views
        chooseImg_bt = findViewById(R.id.pimagesadd_bt);
        //recyclerView = findViewById(R.id.pimages_rv);
        propType_rg = findViewById(R.id.ptype_rg);
        addProp_bt = findViewById(R.id.addProp_bt);
        propTitle_et = findViewById(R.id.ptitle_et);
        propDesc_et = findViewById(R.id.pdesc_et);
        propPrice_et = findViewById(R.id.pprice_et);
        propArea_et = findViewById(R.id.parea_et);
        propEmail_et = findViewById(R.id.p_email_et);
        propNmbr_et = findViewById(R.id.p_phone_et);
        gridLayout = findViewById(R.id.images_Glayout);


        // set layout of recycler view

//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(layoutManager);



        // Reference to storage
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        //Firebase current user
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // Reference to db
        fbase = FirebaseDatabase.getInstance().getReference("Properties");

        //View Listeners

        addProp_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getValues();


            }
        });

        chooseImg_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
          chooseImgResult(requestCode, resultCode, data);


    }

    /*
     *    Choose Images
     */

    public void chooseImage() {
        Intent in_chooseimg = new Intent();
        in_chooseimg.setType("image/*");
        in_chooseimg.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        in_chooseimg.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(in_chooseimg, "Select Image"), IMG_PICK_CODE);
    }

    /*
     *   Result Of Choose Image
     */

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void chooseImgResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMG_PICK_CODE && resultCode == RESULT_OK) {


            // Get the Image from data

            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            imagesEncodedList = new ArrayList<String>();
            if (data.getData() != null) {


                 uri = data.getData();

                //mArrayUri.add(uri);
                Log.v("LOG_TAG", "Selected Images are: " + uri);
                // Get the cursor
                Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imageEncoded  = cursor.getString(columnIndex);
                imagesEncodedList.add(imageEncoded);
                cursor.close();

                ImageView myimage = new ImageView(this);
                myimage.setLayoutParams(new GridLayout.LayoutParams());
                myimage.getLayoutParams().height=400;
                myimage.getLayoutParams().width=400;
                myimage.requestLayout();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    myimage.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                gridLayout.addView(myimage);


                // newuri = new Uri[1];
//                newuri[0]=uri;

                Toast.makeText(this, "uri "+uri, Toast.LENGTH_SHORT).show();

               //  adapter =  new Add_Prop_Adapter(this.mlist,"name");


//
//
//                try {
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
//                    myimage.setImageBitmap(bitmap);
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }



            } else {
                if (data.getClipData() != null) {

                    gridLayout.removeAllViews();

                    ClipData mClipData = data.getClipData();
                    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                    for (int i = 0; i < mClipData.getItemCount(); i++) {

                        ClipData.Item item = mClipData.getItemAt(i);
                        Uri uri = item.getUri();
                        mArrayUri.add(uri);
                        Log.v("LOG_TAG", "Selected Images are: " + uri);
                        // Get the cursor
                        Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                        // Move to first row
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        imageEncoded  = cursor.getString(columnIndex);
                        imagesEncodedList.add(imageEncoded);
                        cursor.close();

                        ImageView myimage = new ImageView(this);
                        myimage.setLayoutParams(new GridLayout.LayoutParams());
                        myimage.getLayoutParams().height=600;
                        myimage.getLayoutParams().width=600;
                        myimage.requestLayout();
                        try {
                            this.uri = mClipData.getItemAt(0).getUri();
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mClipData.getItemAt(i).getUri());
                            myimage.setImageBitmap(bitmap);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        gridLayout.addView(myimage);

                    }
//                    Log.v("LOG_TAG", "Selected Images" + newuri.length);

                }
            }

            //


//            adapter =  new Add_Prop_Adapter(newuri,"name");
//            recyclerView.setAdapter(adapter);
            //
        } else {
            Toast.makeText(this, "You haven't picked Image",
                    Toast.LENGTH_LONG).show();
        }


    }

    public void getValues()
    {

        //get id of selected Radio button
        int gr_id =  propType_rg.getCheckedRadioButtonId();
        // get Radio Button from id
        RadioButton rb = propType_rg.findViewById(gr_id);

       // Toast.makeText(getApplicationContext(), " rb "+rb.getText(), Toast.LENGTH_SHORT).show();

        propType = ""+rb.getText();
        propTitle = propTitle_et.getText().toString();
        propDesc = propDesc_et.getText().toString();
        propPrice = propPrice_et.getText().toString();
        propArea = propArea_et.getText().toString();
        propEmail = propEmail_et.getText().toString();
        propNmbr = Integer.parseInt(propNmbr_et.getText().toString());

        //get id or current user
        userId = currentUser.getUid();


//       Toast.makeText(this, " Image"+newuri.length,Toast.LENGTH_LONG).show();
//               uri = new Uri[newuri.length];
//        for(int i=0;i<newuri.length;i++){
           storeValues(uri);
           propTitle_et.setText("");
           propDesc_et.setText("");
           propPrice_et.setText("");
           propArea_et.setText("");
           propEmail_et.setText("");
           propNmbr_et.setText("");

        gridLayout.removeAllViews();


//        }
    }

    public void storeValues(Uri paramUri)
    {

        //for ( j=0;j<newuri.length;j++) {
            StorageReference storageRef = storageReference.child("housePlot_images"+System.currentTimeMillis()+paramUri+".png");
             uploadTask = storageRef.putFile(paramUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                 @Override
                 public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                     Task<Uri> result_uri = task.getResult().getMetadata().getReference().getDownloadUrl();

                     result_uri.addOnSuccessListener(new OnSuccessListener<Uri>() {
                         @Override
                         public void onSuccess(Uri uri) {

                             //Toast.makeText(getApplicationContext(), "onsuccess upload imges ", Toast.LENGTH_LONG).show();
                             String currentUri = uri.toString();
                             Toast.makeText(getApplicationContext(), "Successfully Added", Toast.LENGTH_LONG).show();
                             Prop_Model upload = new Prop_Model(propType,propTitle,propDesc,propPrice,propArea,propEmail,userId,propNmbr,currentUri);
                             String uploadId = fbase.push().getKey();
                             fbase.child(uploadId).setValue(upload);

                         }
                     });
                 }
             });

        //}



    }


}
