package com.example.hp.firebaseconnection;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ArchitectDetails extends AppCompatActivity {

    TextView jTitle,jName, jDesc, jEmail, jPno, jFee;
    ImageView portfolioImg;
    Button callButton, mailButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_architect_details);

        jTitle=findViewById(R.id.jtitle_tv);
        jName=findViewById(R.id.jname_tv);
        jDesc=findViewById(R.id.jdesc_tv);
        jEmail=findViewById(R.id.j_email_tv);
        jPno=findViewById(R.id.j_phone_tv);
        jFee=findViewById(R.id.jfees_tv);
        portfolioImg=findViewById(R.id.imageView);
        callButton=findViewById(R.id.call_Archi);
        mailButton=findViewById(R.id.mail_Archi);

        final Intent showDetails = getIntent();
        jTitle.setText(showDetails.getStringExtra("jobTitle"));
        jName.setText(showDetails.getStringExtra("uname"));
        jDesc.setText(showDetails.getStringExtra("jobDesc"));
        jEmail.setText(showDetails.getStringExtra("uemail"));
        jPno.setText(showDetails.getStringExtra("uPno"));
        jFee.setText(showDetails.getStringExtra("jobFee"));
        Picasso.with(this)
                .load(showDetails.getStringExtra("portfolio"))
                .fit()
                .centerCrop()
                .into(portfolioImg);

        final String pno = showDetails.getStringExtra("uPno");
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+pno));
                startActivity(callIntent);
            }
        });

        mailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{showDetails.getStringExtra("uemail")});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Subject of email");
                intent.putExtra(Intent.EXTRA_TEXT, "Mail Body");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(Intent.createChooser(intent, ""));
                }
            }
        });
    }
}
