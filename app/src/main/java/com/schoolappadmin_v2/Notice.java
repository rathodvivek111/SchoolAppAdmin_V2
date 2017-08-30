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
import com.schoolappadmin_v2.Adapters.NoticeAdapter;
import com.schoolappadmin_v2.Models.ModelNotice;
import com.schoolappadmin_v2.utility.RecyclerItemClickListener;
import com.schoolappadmin_v2.utility.WebConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Notice extends Fragment {

    RecyclerView rcvNotice;
    NoticeAdapter noticeAdapter;
    String TAG = getClass().getSimpleName();
    ProgressBar progressbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notice, container, false);
        rcvNotice = (RecyclerView) rootView.findViewById(R.id.rcvNotice);
        progressbar = (ProgressBar) rootView.findViewById(R.id.progressbar);
        rcvNotice.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcvNotice.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(getActivity(),AddNotice.class);
                intent.putExtra("noticeModel",noticeAdapter.getModelNoticeArrayList().get(position));
                startActivity(intent);
            }
        }));
        getNoticeList();
        return rootView;

    }

    @Override
    public void onResume() {
        getNoticeList();
        super.onResume();
    }

    private void getNoticeList() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebConfig.GETNOTICE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: " + response);
                        progressbar.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            JSONArray dataArray = jsonObject.getJSONArray("data");
                            ArrayList<ModelNotice> list = new ArrayList<>();
                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject mainObject = dataArray.getJSONObject(i);
                                ModelNotice modelNotice = new ModelNotice();
                                modelNotice.setTitle(mainObject.getString("notice_title"));
                                modelNotice.setDate(mainObject.getString("date"));
                                modelNotice.setDesc(mainObject.getString("notice_desc"));
                                modelNotice.setId(mainObject.getString("id"));
                                modelNotice.setActive(mainObject.getString("active"));
                                list.add(modelNotice);
                            }

                            noticeAdapter = new NoticeAdapter(list, getActivity());
                            rcvNotice.setAdapter(noticeAdapter);

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
