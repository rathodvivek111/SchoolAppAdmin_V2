package com.schoolappadmin_v2.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.schoolappadmin_v2.R;
import com.schoolappadmin_v2.GalleryPagingActivity;
import com.schoolappadmin_v2.Models.ModelPictures;

import java.util.ArrayList;

public class PicturesAdapter extends RecyclerView.Adapter<PicturesAdapter.MyViewHolder> {

    private ArrayList<ModelPictures> modelPicturesArrayList;
    private Activity activity;


    public PicturesAdapter(ArrayList<ModelPictures> moviesList, Activity activity) {
        this.modelPicturesArrayList = moviesList;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.raw_pitctures, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Glide
                .with(activity)
                .load(modelPicturesArrayList.get(position).getUrl())
                .placeholder(R.drawable.img_placeholder)
                .centerCrop()
                .into(holder.imgPic);
        holder.Albumcard.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, GalleryPagingActivity.class);
                        intent.putExtra("images_list", modelPicturesArrayList);
                        intent.putExtra("position", position);
                        activity.startActivity(intent);
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return modelPicturesArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgPic;
        public CardView Albumcard;

        public MyViewHolder(View view) {
            super(view);
            imgPic = (ImageView) view.findViewById(R.id.imageView2);
            Albumcard = (CardView) view.findViewById(R.id.Albumcard);
        }
    }
}