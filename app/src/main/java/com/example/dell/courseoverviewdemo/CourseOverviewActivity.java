package com.example.dell.courseoverviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CourseOverviewActivity extends AppCompatActivity {

    String url="http://nfly.in/api/course";
    String postKey=null;
    TextView courseOverviewTitle,courseOverviewText,courseCurriculumText,courseLearnText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_overview);

        postKey=getIntent().getStringExtra("postParam");
        courseOverviewTitle=findViewById(R.id.courseOverviewTitle);
        courseCurriculumText=findViewById(R.id.courseCurriculumText);
        courseLearnText=findViewById(R.id.courseLearnText);
        courseOverviewText=findViewById(R.id.courseOverviewText);
        loadCourseOverview();
    }

    public void loadCourseOverview(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST,url,new Response.Listener<String>( ){

            @Override
            public void onResponse(String response) {
                Toast.makeText(CourseOverviewActivity.this, "Successful", Toast.LENGTH_LONG).show();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    courseOverviewTitle.setText(jsonObject.getString("course_name"));
                    courseOverviewText.setText(jsonObject.getString( "course_overview"));
                    courseLearnText.setText(jsonObject.getString("course_learnings"));
                    courseCurriculumText.setText(jsonObject.getString("course_curriculum"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CourseOverviewActivity.this, "Unsuccessful", Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("X-Api-Key", "59671596837f42d974c7e9dcf38d17e8");
                return headers;
            }


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("course", postKey);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(CourseOverviewActivity.this);
        requestQueue.add(stringRequest);
    }
}
