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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.schoolappadmin_v2.Models.ModelEvents;
import com.schoolappadmin_v2.Models.ModelNotice;
import com.schoolappadmin_v2.utility.WebConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddEvent extends AppCompatActivity {


    String TAG = getClass().getSimpleName();
    EditText edtx_start_date, edtx_end_date, edtx_title, edtx_organized_by, edtx_venue, edtx_theme, edtx_url;
    Button btn_save,btn_delete;


    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    private ProgressDialog progressDialog;


    //EDIT
    private ModelEvents modelEvents;
    private boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        if (getIntent().getExtras() != null) {
            modelEvents = (ModelEvents) getIntent().getSerializableExtra("eventModel");
            isEditMode = true;
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (isEditMode)
            getSupportActionBar().setTitle("Update Event");
        else
            getSupportActionBar().setTitle("Add Event");

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        edtx_start_date = (EditText) findViewById(R.id.edtx_start_date);
        edtx_end_date = (EditText) findViewById(R.id.edtx_end_date);
        edtx_title = (EditText) findViewById(R.id.edtx_title);
        edtx_organized_by = (EditText) findViewById(R.id.edtx_organized_by);
        edtx_venue = (EditText) findViewById(R.id.edtx_venue);
        edtx_theme = (EditText) findViewById(R.id.edtx_theme);
        edtx_url = (EditText) findViewById(R.id.edtx_url);
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

        edtx_end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEndDate(v);
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidate()){
                    if (isValidate()){
                        if (isEditMode)
                            updateEvent();
                        else
                            saveEvent();
                    }

                }
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEvent();
            }
        });

        if (isEditMode) {
            edtx_start_date.setText(modelEvents.getSdate());
            edtx_end_date.setText(modelEvents.getEdate());
            edtx_title.setText(modelEvents.getTitle());
            edtx_organized_by.setText(modelEvents.getOrganized_by());
            edtx_venue.setText(modelEvents.getVenue());
            edtx_theme.setText(modelEvents.getTheme());
            edtx_url.setText(modelEvents.getUrl());



        }
    }

    @SuppressWarnings("deprecation")
    public void setStartDate(View view) {
        showDialog(888);
    }

    @SuppressWarnings("deprecation")
    public void setEndDate(View view) {
        showDialog(999);
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 888) {
            return new DatePickerDialog(this, myDateListenerStartDate, year, month, day);
        }
        if (id == 999) {
            return new DatePickerDialog(this, myDateListenerEndDate, year, month, day);
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

    private DatePickerDialog.OnDateSetListener myDateListenerEndDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // arg1 = year
            // arg2 = month
            // arg3 = day
            edtx_end_date.setText(new StringBuilder().append(arg3).append("/")
                    .append(arg2).append("/").append(arg1));
        }
    };


    private boolean isValidate() {
        if (edtx_start_date.getText().toString().trim().length() <= 0) {
            Toast.makeText(this, "Start date mandotory", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtx_end_date.getText().toString().trim().length() <= 0) {
            Toast.makeText(this, "End date mandotory", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtx_title.getText().toString().trim().length() <= 0) {
            Toast.makeText(this, "Title mandotory", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtx_organized_by.getText().toString().trim().length() <= 0) {
            Toast.makeText(this, "Organized by mandotory", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtx_venue.getText().toString().trim().length() <= 0) {
            Toast.makeText(this, "Venue mandotory", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtx_theme.getText().toString().trim().length() <= 0) {
            Toast.makeText(this, "Theme mandotory", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtx_url.getText().toString().trim().length() <= 0) {
            Toast.makeText(this, "URL mandotory", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void saveEvent() {
        progressDialog = ProgressDialog.show(this, "Loading", "Please wait for a moment...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebConfig.SAVE_EVENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.d(TAG, "onResponse: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            if (jsonObject.has("status") && jsonObject.getInt("status") == 200) {
                                Toast.makeText(AddEvent.this, "Event Inserted Successfully", Toast.LENGTH_SHORT).show();
                                finish();

                            } else {
                                Toast.makeText(AddEvent.this, "Fail", Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(AddEvent.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(WebConfig.PARAM_START_DATE, edtx_start_date.getText().toString().trim());
                params.put(WebConfig.PARAM_END_DATE, edtx_end_date.getText().toString().trim());
                params.put(WebConfig.PARAM_ORGANIZED_BY, edtx_organized_by.getText().toString().trim());
                params.put(WebConfig.PARAM_THEME, edtx_theme.getText().toString().trim());
                params.put(WebConfig.PARAM_URL, edtx_url.getText().toString().trim());
                params.put(WebConfig.PARAM_VENUE, edtx_venue.getText().toString().trim());
                params.put(WebConfig.PARAM_TITLE, edtx_title.getText().toString().trim());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void updateEvent() {
        progressDialog = ProgressDialog.show(this, "Loading", "Please wait for a moment...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebConfig.UPDATE_EVENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.d(TAG, "onResponse: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            if (jsonObject.has("status") && jsonObject.getInt("status") == 200) {
                                Toast.makeText(AddEvent.this, "Event Updated Successfully", Toast.LENGTH_SHORT).show();
                                finish();

                            } else {
                                Toast.makeText(AddEvent.this, "Fail", Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(AddEvent.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(WebConfig.PARAM_EVENT_ID, modelEvents.getId());
                params.put(WebConfig.PARAM_START_DATE, edtx_start_date.getText().toString().trim());
                params.put(WebConfig.PARAM_END_DATE, edtx_end_date.getText().toString().trim());
                params.put(WebConfig.PARAM_ORGANIZED_BY, edtx_organized_by.getText().toString().trim());
                params.put(WebConfig.PARAM_THEME, edtx_theme.getText().toString().trim());
                params.put(WebConfig.PARAM_URL, edtx_url.getText().toString().trim());
                params.put(WebConfig.PARAM_VENUE, edtx_venue.getText().toString().trim());
                params.put(WebConfig.PARAM_TITLE, edtx_title.getText().toString().trim());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void deleteEvent() {
        progressDialog = ProgressDialog.show(this, "Loading", "Please wait for a moment...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebConfig.DELETE_EVENT,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.d(TAG, "onResponse: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            if (jsonObject.has("status") && jsonObject.getInt("status") == 200) {
                                Toast.makeText(AddEvent.this, "Event Deleted Successfully", Toast.LENGTH_SHORT).show();
                                finish();

                            } else {
                                Toast.makeText(AddEvent.this, "Fail", Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(AddEvent.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(WebConfig.PARAM_EVENT_ID,modelEvents.getId());

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
