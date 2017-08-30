package com.schoolappadmin_v2;

/**
 * Created by RATHOD'S on 6/7/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.schoolappadmin_v2.Adapters.NewsAdapter;
import com.schoolappadmin_v2.Models.ModelNews;
import com.schoolappadmin_v2.utility.RecyclerItemClickListener;
import com.schoolappadmin_v2.utility.WebConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class News extends Fragment {

    RecyclerView rcvNews;
    NewsAdapter newsAdapter;
    String TAG = getClass().getSimpleName();
    ProgressBar progressbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        rcvNews = (RecyclerView) rootView.findViewById(R.id.rcvNews);
        progressbar = (ProgressBar) rootView.findViewById(R.id.progressbar);
        rcvNews.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcvNews.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(getActivity(),AddNews.class);
                intent.putExtra("newsModel",newsAdapter.getModelNewsArrayList().get(position));
                startActivity(intent);
            }
        }));
        return rootView;

    }


    @Override
    public void onResume() {
        super.onResume();
        getNewsList();
    }

    private void getNewsList() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebConfig.GETNEWS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: " + response);
                        progressbar.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            JSONArray dataArray = jsonObject.getJSONArray("data");
                            ArrayList<ModelNews> list = new ArrayList<>();
                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject mainObject = dataArray.getJSONObject(i);
                                ModelNews modelNews = new ModelNews();
                                modelNews.setTitle(mainObject.getString("title"));
                                modelNews.setSdate(mainObject.getString("sdate"));
                                modelNews.setNews(mainObject.getString("news"));
                                modelNews.setId(mainObject.getString("id"));
                                modelNews.setReadmoreurl(mainObject.getString("readmore_url"));
                                modelNews.setHotnews(mainObject.getString("hot_news"));

                                list.add(modelNews);
                            }

                            newsAdapter = new NewsAdapter(list, getActivity());
                            rcvNews.setAdapter(newsAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressbar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}
