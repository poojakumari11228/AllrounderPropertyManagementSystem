package com.example.hp.firebaseconnection;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Find_Prop_Adapter extends RecyclerView.Adapter<Find_Prop_Adapter.Find_Prop_ViewHolder> {

    ViewGroup myViewGroup;
    public Context mcontext;
    public List<Prop_Model> myProp;
    Find_Prop_Adapter(Context mcontext, List<Prop_Model> myProp){
        this.mcontext = mcontext;
        this.myProp = myProp;

    }

    @NonNull
    @Override
    public Find_Prop_ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v =LayoutInflater.from(mcontext).inflate(R.layout.list_prop,viewGroup,false);
        myViewGroup = viewGroup;
        return new Find_Prop_ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull Find_Prop_ViewHolder find_prop_viewHolder, int i) {
        final Prop_Model currentupload = myProp.get(i);
        find_prop_viewHolder.textView.setText(currentupload.getPropTitle());

//        DownloadImage dm = new DownloadImage(imageViewHolder.imageView);
//        dm.execute(currentupload.getImageUrl());
        Picasso.with(mcontext)
                .load(currentupload.getUri())
                .fit()
                .centerCrop()
                .fit()
                .placeholder(R.drawable.ic_launcher_background)
                .into(find_prop_viewHolder.imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d("", "onSuccess: picasso ");
                    }

                    @Override
                    public void onError() {
                        Log.d("", "onFail: picasso ");
                    }
                });
        find_prop_viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = currentupload.getUserId();
                Log.d("", "*************************onClick: "+id+"  name "+currentupload.getPropTitle());
              //  Toast.makeText(mcontext.getApplicationContext(),"*************************onClick: "+id+"  name "+currentupload.getPropTitle(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(myViewGroup.getContext(),Prop_Details.class);

                //send data
                intent.putExtra("propId",""+id);
                intent.putExtra("area",""+currentupload.getPropArea());
                intent.putExtra("desc",""+currentupload.getPropDesc());
                intent.putExtra("email",""+currentupload.getPropEmail());
                intent.putExtra("no",""+currentupload.getPropNmbr());
                intent.putExtra("price",""+currentupload.getPropPrice());
                intent.putExtra("title",""+currentupload.getPropTitle());
                intent.putExtra("type",""+currentupload.getPropType());
                intent.putExtra("uri",""+currentupload.getUri());
                // start intent
                mcontext.getApplicationContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return myProp.size();
    }

    public class Find_Prop_ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;

        public Find_Prop_ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.list_add_mv);
            this.textView = itemView.findViewById(R.id.list_add_tv);
        }
    }

}
