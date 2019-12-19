package com.example.hp.firebaseconnection;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class Prop_Details extends AppCompatActivity {

    TextView propTitle_et,propDesc_et,propPrice_et,propArea_et,propEmail_et,propNmbr_et;
    Button bt_call,bt_mail;
    Intent in;
    ImageView propImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prop__details);


        propTitle_et = findViewById(R.id.ptitle_et);
        propDesc_et = findViewById(R.id.pdesc_et);
        propPrice_et = findViewById(R.id.pprice_et);
        propArea_et = findViewById(R.id.parea_et);
        propEmail_et = findViewById(R.id.p_email_et);
        propNmbr_et = findViewById(R.id.p_phone_et);
        propImage = findViewById(R.id.imageView);
        bt_call = findViewById(R.id.call_Prop_own);
        bt_mail = findViewById(R.id.mail_Prop_own);

        in = getIntent();
        Toast.makeText(this, "data from prev activity "+in.getStringExtra("title"), Toast.LENGTH_SHORT).show();

        propTitle_et.setText(in.getStringExtra("title"));
        propDesc_et.setText(in.getStringExtra("desc"));
        propPrice_et.setText(in.getStringExtra("price"));
        propArea_et.setText(in.getStringExtra("area"));
        propEmail_et.setText(in.getStringExtra("email"));
        propNmbr_et.setText(in.getStringExtra("no"));
        String uri = in.getStringExtra("uri");

        Picasso.with(getApplicationContext())
                .load(uri)
                .placeholder(R.drawable.ic_launcher_background)
                .into(propImage);



        bt_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+in.getStringExtra("no").toString()));
                startActivity(intent);

            }
        });
        bt_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //EMAIL INTENT

                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL,new String[]{""+in.getStringExtra("email")});
                intent.putExtra(Intent.EXTRA_SUBJECT,"");
                intent.putExtra(Intent.EXTRA_TEXT,"");
                if(intent.resolveActivity(getPackageManager())!=null){
                  //  startActivity(Intent.createChooser(intent,""));
                 //   startActivity(Intent.createChooser(intent,getResources().getText(in.getStringExtra("email").toString())));
                    startActivity(intent);
                }
            }
        });

    }
}
