package com.example.hp.firebaseconnection;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FindArchiRecyclerAdaptor extends RecyclerView.Adapter<FindArchiRecyclerAdaptor.RecyclerViewHolder>{

    private Context mContext;
    private List<UserJobInformation> mUserJobInformationList;


    public FindArchiRecyclerAdaptor(Context context, List<UserJobInformation> userJobInformationList){
        mContext=context;
        mUserJobInformationList=userJobInformationList;
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{

         TextView imgTitleTV;
         ImageView portfolioIV;

        public RecyclerViewHolder(@NonNull View itemView) {

            super(itemView);

            imgTitleTV=itemView.findViewById(R.id.jTitleTV);
            portfolioIV=itemView.findViewById(R.id.architectsImages);
        }
    }


    @NonNull
    @Override
    public FindArchiRecyclerAdaptor.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.find_archi_images,viewGroup,false);
        return new FindArchiRecyclerAdaptor.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FindArchiRecyclerAdaptor.RecyclerViewHolder recyclerViewHolder, int i) {

        final UserJobInformation uploadInfo = mUserJobInformationList.get(i);
        recyclerViewHolder.imgTitleTV.setText(uploadInfo.getuJobTitle());
       // Glide.with(mContext).load(uploadInfo.getuImgUrl()).into(recyclerViewHolder.portfolioIV);
        Picasso.with(mContext)
                .load(uploadInfo.getuImgUrl())
                .fit()
                .centerCrop()
                .into(recyclerViewHolder.portfolioIV);

        recyclerViewHolder.portfolioIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent details_Intent=new Intent(mContext.getApplicationContext(),ArchitectDetails.class);
                details_Intent.putExtra("uname",uploadInfo.getUname());
                details_Intent.putExtra("uemail", uploadInfo.getuEmail());
                details_Intent.putExtra("jobTitle", uploadInfo.getuJobTitle());
                details_Intent.putExtra("jobDesc", uploadInfo.getuDesc());
                details_Intent.putExtra("uPno", uploadInfo.getuContact());
                details_Intent.putExtra("jobFee", uploadInfo.getuFees());
                details_Intent.putExtra("portfolio", uploadInfo.getuImgUrl());
                mContext.startActivity(details_Intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return mUserJobInformationList.size();
    }


}
