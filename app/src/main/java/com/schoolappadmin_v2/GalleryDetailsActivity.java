package com.schoolappadmin_v2;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.schoolappadmin_v2.Adapters.PicturesAdapter;
import com.schoolappadmin_v2.Models.ModelPictures;
import com.schoolappadmin_v2.utility.WebConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GalleryDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    String TAG = getClass().getSimpleName();
    RecyclerView rcvGallery;
    PicturesAdapter picturesAdapter;
    String GALLERY_ID;
    ProgressBar progressBar;
    ProgressDialog progressDialog;
    ImageView imgdelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallary_details);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        if (getIntent().getExtras() != null) {
            GALLERY_ID = getIntent().getStringExtra("gallery_id");
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageView imgdelete = (ImageView) toolbar.findViewById(R.id.imgdelete);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Gallery");
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        rcvGallery = (RecyclerView) findViewById(R.id.rcvPictures);
        rcvGallery.setLayoutManager(new GridLayoutManager(this, 2));


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GalleryDetailsActivity.this, AddPhoto.class);
                intent.putExtra("GALLERY_ID", GALLERY_ID);
                startActivity(intent);


            }
        });

        imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GalleryDetailsActivity.this);
                builder.setTitle("Are you want to delete this gallery?");
                builder.setCancelable(false);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        deleteGallery(GALLERY_ID);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

    }

    @Override
    protected void onResume() {
        getPictures();
        super.onResume();
    }

    @Override
    public void onClick(View v) {

    }


    private void getPictures() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebConfig.GETPICTURES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: " + response);
                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            JSONArray dataArray = jsonObject.getJSONArray("data");
                            ArrayList<ModelPictures> list = new ArrayList<>();
                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject mainObject = dataArray.getJSONObject(i);
                                ModelPictures modelPictures = new ModelPictures();
                                modelPictures.setUrl(mainObject.getString("filename"));
                                modelPictures.setId(mainObject.getString("id"));
                                list.add(modelPictures);
                            }
                            picturesAdapter = new PicturesAdapter(list, GalleryDetailsActivity.this);
                            rcvGallery.setAdapter(picturesAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(GalleryDetailsActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(WebConfig.PARAM_GALLERY_ID, GALLERY_ID);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }


    private void deleteGallery(final String id) {
        progressDialog = ProgressDialog.show(this, "Loading", "Please wait for a moment...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebConfig.DELETE_GALLERY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.d(TAG, "onResponse: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            if (jsonObject.has("status") && jsonObject.getInt("status") == 200) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(GalleryDetailsActivity.this);
                                builder.setTitle("Gallery Deleted Successfully!");
                                builder.setCancelable(false);
                                builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int item) {
                                        finish();
                                    }
                                });
                                builder.show();
                            } else {
                                Toast.makeText(GalleryDetailsActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(GalleryDetailsActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(WebConfig.PARAM_ID, id);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(stringRequest);
    }
}
