package com.schoolappadmin_v2.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.schoolappadmin_v2.R;
import com.schoolappadmin_v2.Models.ModelNews;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    private ArrayList<ModelNews> modelNewsArrayList;
    private Activity activity;


    public NewsAdapter(ArrayList<ModelNews> moviesList, Activity activity) {
        this.modelNewsArrayList = moviesList;
        this.activity = activity;
    }

    public ArrayList<ModelNews> getModelNewsArrayList() {
        return modelNewsArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.raw_news, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.News_title.setText(modelNewsArrayList.get(position).getTitle());
        holder.News_date.setText(modelNewsArrayList.get(position).getSdate());
        holder.News_content.setText(modelNewsArrayList.get(position).getNews());

    }

    @Override
    public int getItemCount() {
        return modelNewsArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView News_title,News_date,News_content;


        public MyViewHolder(View view) {
            super(view);
            News_title = (TextView) view.findViewById(R.id.News_title);
            News_date = (TextView) view.findViewById(R.id.News_date);
            News_content = (TextView) view.findViewById(R.id.News_content);
        }
    }
}