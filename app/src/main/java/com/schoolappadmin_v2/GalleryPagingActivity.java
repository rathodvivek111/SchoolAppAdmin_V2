package com.schoolappadmin_v2;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.schoolappadmin_v2.Adapters.ImageAdapter;
import com.schoolappadmin_v2.Models.ModelPictures;
import com.schoolappadmin_v2.utility.WebConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GalleryPagingActivity extends AppCompatActivity {

    String TAG = getClass().getSimpleName();
    ViewPager viewPager;
    ImageAdapter imageAdapter;
    ArrayList<ModelPictures> list = new ArrayList<>();
    int POSITION = 0;
    ImageView imgCross;
    ImageView imgdelete;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallary_paging);
        if (getIntent().getExtras() != null) {
            list = (ArrayList<ModelPictures>) getIntent().getSerializableExtra("images_list");
            POSITION = getIntent().getIntExtra("position", 0);
        }
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        imgCross = (ImageView) findViewById(R.id.imgCross);
        imgdelete = (ImageView) findViewById(R.id.imgdelete);
        imgCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imageAdapter = new ImageAdapter(this, list);
        viewPager.setAdapter(imageAdapter);
        viewPager.setCurrentItem(POSITION);

        imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GalleryPagingActivity.this);
                builder.setTitle("Are you want to delete this image?");
                builder.setCancelable(false);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        deletPicture(list.get(POSITION).getId());
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

    private void deletPicture(final String id) {
        progressDialog = ProgressDialog.show(this, "Loading", "Please wait for a moment...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebConfig.DELETE_PHOTOS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.d(TAG, "onResponse: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            if (jsonObject.has("status") && jsonObject.getInt("status") == 200) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(GalleryPagingActivity.this);
                                builder.setTitle("Picture Deleted Successfully!");
                                builder.setCancelable(false);
                                builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int item) {
                                        finish();
                                    }
                                });
                                builder.show();
                            } else {
                                Toast.makeText(GalleryPagingActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(GalleryPagingActivity.this, error.toString(), Toast.LENGTH_LONG).show();
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
