package com.example.hp.firebaseconnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Add_Prop_Adapter extends RecyclerView.Adapter<Add_Prop_Adapter.Add_Prop_ViewHolder>{

    public List<Prop_Model> mUpload;
    String name;
    public Add_Prop_Adapter(List<Prop_Model> mUpload, String name)
    {

        this.mUpload = mUpload;
        this.name = name;
    }

    @NonNull
    @Override
    public Add_Prop_ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_prop,viewGroup,false);
        return new Add_Prop_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Add_Prop_ViewHolder add_prop_viewHolder, int i) {
        Prop_Model currentupload = mUpload.get(i);
       // String currentupload = mUpload.get(i);
        add_prop_viewHolder.addPropName.setText(name);
       // DownloadImage dm = new DownloadImage(imageViewHolder.imageView);
       // dm.execute(currentupload.getImageUrl());


        String urldisplay = currentupload.getUri();
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        add_prop_viewHolder.addPropImg.setImageBitmap(mIcon11);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class Add_Prop_ViewHolder extends RecyclerView.ViewHolder{

        ImageView addPropImg;
        TextView addPropName;

        public Add_Prop_ViewHolder(@NonNull View itemView) {
            super(itemView);

            addPropImg = itemView.findViewById(R.id.list_add_mv);
            addPropName = itemView.findViewById(R.id.list_add_tv);

        }
    }
}
