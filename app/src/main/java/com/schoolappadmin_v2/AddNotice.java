package com.schoolappadmin_v2;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.schoolappadmin_v2.Models.ModelNews;
import com.schoolappadmin_v2.Models.ModelNotice;
import com.schoolappadmin_v2.utility.WebConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddNotice extends AppCompatActivity {


    String TAG = getClass().getSimpleName();
    EditText edtx_start_date, edtx_title, edtx_notice_desc;
    CheckBox active;
    Button btn_save,btn_delete;


    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    private ProgressDialog progressDialog;

    //EDIT
    private ModelNotice modelNotice;
    private boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notice);
        if (getIntent().getExtras() != null) {
            modelNotice = (ModelNotice) getIntent().getSerializableExtra("noticeModel");
            isEditMode = true;
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (isEditMode)
            getSupportActionBar().setTitle("Update Notice");
        else
            getSupportActionBar().setTitle("Add Notice");

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        edtx_start_date = (EditText) findViewById(R.id.edtx_start_date);

        edtx_title = (EditText) findViewById(R.id.edtx_title);
        edtx_notice_desc = (EditText) findViewById(R.id.edtx_notice_desc);
        active = (CheckBox) findViewById(R.id.active);

        btn_save = (Button) findViewById(R.id.btn_save);
        btn_delete = (Button) findViewById(R.id.btn_delete);

        if (isEditMode) {
            btn_save.setText("Update");
        }


        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        edtx_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStartDate(v);
            }
        });



        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidate()){
                    if (isValidate()){
                        if (isEditMode)
                            updateNotice();
                        else
                            saveNotice();
                    }

                }

            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNotice();
            }
        });


        if (isEditMode) {
            edtx_start_date.setText(modelNotice.getDate());
            edtx_title.setText(modelNotice.getTitle());
            edtx_notice_desc.setText(modelNotice.getDesc());


            if (modelNotice.getActive().equalsIgnoreCase("1")) {
                active.setChecked(true);
            } else {
                active.setChecked(false);
            }
        }
    }

    @SuppressWarnings("deprecation")
    public void setStartDate(View view) {
        showDialog(888);
    }




    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 888) {
            return new DatePickerDialog(this, myDateListenerStartDate, year, month, day);
        }

        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListenerStartDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // arg1 = year
            // arg2 = month
            // arg3 = day
            edtx_start_date.setText(new StringBuilder().append(arg3).append("/")
                    .append(arg2).append("/").append(arg1));
        }
    };




    private boolean isValidate() {
        if (edtx_start_date.getText().toString().trim().length() <= 0) {
            Toast.makeText(this, "Start date mandotory", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtx_title.getText().toString().trim().length() <= 0) {
            Toast.makeText(this, "Title mandotory", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtx_notice_desc.getText().toString().trim().length() <= 0) {
            Toast.makeText(this, "Organized by mandotory", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void saveNotice() {
        progressDialog = ProgressDialog.show(this, "Loading", "Please wait for a moment...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebConfig.SAVE_NOTICE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.d(TAG, "onResponse: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            if (jsonObject.has("status") && jsonObject.getInt("status") == 200) {
                                Toast.makeText(AddNotice.this, "Notice Inserted Successfully", Toast.LENGTH_SHORT).show();
                                finish();

                            } else {
                                Toast.makeText(AddNotice.this, "Fail", Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(AddNotice.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(WebConfig.PARAM_DATE, edtx_start_date.getText().toString().trim());

                params.put(WebConfig.PARAM_NOTICE_DESC, edtx_notice_desc.getText().toString().trim());

                params.put(WebConfig.PARAM_NOTICE_TITLE,edtx_title.getText().toString().trim());

                if(active.isChecked()){
                    params.put(WebConfig.PARAM_ACTIVE,"1");
                }else
                {
                    params.put(WebConfig.PARAM_ACTIVE,"0");
                }

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void updateNotice() {
        progressDialog = ProgressDialog.show(this, "Loading", "Please wait for a moment...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebConfig.UPDATE_NOTICE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.d(TAG, "onResponse: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            if (jsonObject.has("status") && jsonObject.getInt("status") == 200) {
                                Toast.makeText(AddNotice.this, "Notice Updated Successfully", Toast.LENGTH_SHORT).show();
                                finish();

                            } else {
                                Toast.makeText(AddNotice.this, "Fail", Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(AddNotice.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put(WebConfig.PARAM_NOTICE_ID, modelNotice.getId());

                params.put(WebConfig.PARAM_DATE, edtx_start_date.getText().toString().trim());

                params.put(WebConfig.PARAM_NOTICE_DESC, edtx_notice_desc.getText().toString().trim());

                params.put(WebConfig.PARAM_NOTICE_TITLE,edtx_title.getText().toString().trim());

                if(active.isChecked()){
                    params.put(WebConfig.PARAM_ACTIVE,"1");
                }else
                {
                    params.put(WebConfig.PARAM_ACTIVE,"0");
                }

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void deleteNotice() {
        progressDialog = ProgressDialog.show(this, "Loading", "Please wait for a moment...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebConfig.DELETE_NOTICE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.d(TAG, "onResponse: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            if (jsonObject.has("status") && jsonObject.getInt("status") == 200) {
                                Toast.makeText(AddNotice.this, "Notice Deleted Successfully", Toast.LENGTH_SHORT).show();
                                finish();

                            } else {
                                Toast.makeText(AddNotice.this, "Fail", Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(AddNotice.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(WebConfig.PARAM_NOTICE_ID,modelNotice.getId());

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

}
