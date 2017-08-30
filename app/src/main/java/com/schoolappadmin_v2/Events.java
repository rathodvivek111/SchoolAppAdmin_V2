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
import com.schoolappadmin_v2.Adapters.EventsAdapter;
import com.schoolappadmin_v2.Models.ModelEvents;
import com.schoolappadmin_v2.utility.RecyclerItemClickListener;
import com.schoolappadmin_v2.utility.WebConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Events extends Fragment {

    RecyclerView rcvEvents;
    EventsAdapter eventsAdapter;
    String TAG = getClass().getSimpleName();
    ProgressBar progressbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_events, container, false);
        rcvEvents = (RecyclerView) rootView.findViewById(R.id.rcvEvents);
        progressbar = (ProgressBar) rootView.findViewById(R.id.progressbar);
        rcvEvents.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcvEvents.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(getActivity(),AddEvent.class);
                intent.putExtra("eventModel",eventsAdapter.getModelEventArrayList().get(position));
                startActivity(intent);
            }
        }));
        getEventList();
        return rootView;

    }

    @Override
    public void onResume() {
        getEventList();
        super.onResume();
    }

    private void getEventList() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebConfig.GETEVENTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: " + response);
                        progressbar.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            JSONArray dataArray = jsonObject.getJSONArray("data");
                            ArrayList<ModelEvents> list = new ArrayList<>();
                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject mainObject = dataArray.getJSONObject(i);
                                ModelEvents modelEvents = new ModelEvents();
                                modelEvents.setTitle(mainObject.getString("title"));
                                modelEvents.setSdate(mainObject.getString("sdate"));
                                modelEvents.setEdate(mainObject.getString("edate"));
                                modelEvents.setVenue(mainObject.getString("venue"));
                                modelEvents.setId(mainObject.getString("id"));
                                modelEvents.setTheme(mainObject.getString("theme"));
                                modelEvents.setUrl(mainObject.getString("url"));
                                modelEvents.setOrganized_by(mainObject.getString("organized_by"));

                                list.add(modelEvents);
                            }

                            eventsAdapter = new EventsAdapter(list, getActivity());
                            rcvEvents.setAdapter(eventsAdapter);

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
