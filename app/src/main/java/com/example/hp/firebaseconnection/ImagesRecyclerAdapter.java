package com.example.hp.firebaseconnection;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class ImagesRecyclerAdapter extends RecyclerView.Adapter<ImagesRecyclerAdapter.RecyclerViewHolder> {

    public List<String> fileNameList;

    public ImagesRecyclerAdapter(List<String> fileNameList){

        this.fileNameList = fileNameList;
        }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        View mView;
        TextView fileNameTV;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            fileNameTV=mView.findViewById(R.id.filename);
        }
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view=inflater.inflate(R.layout.choosed_images,viewGroup,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
        String fileName=fileNameList.get(i);
        recyclerViewHolder.fileNameTV.setText(fileName);
    }

    @Override
    public int getItemCount() {

        return fileNameList.size();
    }




}
