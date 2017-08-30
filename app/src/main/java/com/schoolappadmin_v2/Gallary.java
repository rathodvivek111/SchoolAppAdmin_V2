package com.schoolappadmin_v2;

/**
 * Created by RATHOD'S on 6/7/2017.
 */

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
import com.schoolappadmin_v2.Adapters.GalleryAdapter;
import com.schoolappadmin_v2.Models.ModelGallery;
import com.schoolappadmin_v2.utility.WebConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Gallary extends Fragment {

    RecyclerView rcvGallery;
    GalleryAdapter galleryAdapter;
    String TAG = getClass().getSimpleName();
    ProgressBar progressbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gallary, container, false);
        rcvGallery = (RecyclerView) rootView.findViewById(R.id.rcvGallery);
        progressbar = (ProgressBar) rootView.findViewById(R.id.progressbar);
        rcvGallery.setLayoutManager(new LinearLayoutManager(getActivity()));
        getAlbumList();
        return rootView;

    }

    @Override
    public void onResume() {
        getAlbumList();
        super.onResume();
    }

    public void getAlbumList() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebConfig.GETALBUMS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: " + response);
                        progressbar.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            JSONArray dataArray = jsonObject.getJSONArray("data");
                            ArrayList<ModelGallery> list = new ArrayList<>();
                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject mainObject = dataArray.getJSONObject(i);
                                ModelGallery modelGallery = new ModelGallery();
                                modelGallery.setUrl(mainObject.getString("thumbnail"));
                                modelGallery.setTitle(mainObject.getString("gallerytitle"));
                                modelGallery.setGallery_id(mainObject.getString("id"));
                                list.add(modelGallery);
                            }

                            galleryAdapter = new GalleryAdapter(list, getActivity());
                            rcvGallery.setAdapter(galleryAdapter);

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
