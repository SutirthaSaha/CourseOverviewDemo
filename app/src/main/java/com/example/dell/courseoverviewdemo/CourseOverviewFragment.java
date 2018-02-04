package com.example.dell.courseoverviewdemo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CourseOverviewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CourseOverviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CourseOverviewFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private String postKey;
    private String url="http://nfly.in/api/course";

    private TextView courseOverviewFragmentTitle,courseOverviewFrgamentText,courseLearnFragmentText,courseCurriculumFragmentText;
    private ListView preReqList;
    private OnFragmentInteractionListener mListener;

    private RecyclerView courseCurriculumRecyclerView;
    private RecyclerView.LayoutManager courseCurriculumLayoutManager;
    private RecyclerView.Adapter courseCurriculumAdapter;

    String[] courseCurriculumStringArray=new String[]{};
    String[] courseLearningsStringArray=new String[]{};

    public CourseOverviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment CourseOverviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CourseOverviewFragment newInstance(String param1) {
        CourseOverviewFragment fragment = new CourseOverviewFragment();
        Bundle args = new Bundle();
        args.putString("post", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            postKey = getArguments().getString("post");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V= inflater.inflate(R.layout.fragment_course_overview, container, false);

        courseOverviewFragmentTitle=V.findViewById(R.id.courseOverviewFragmentTitle);
        courseOverviewFrgamentText=V.findViewById(R.id.courseOverviewFragmentText);
        courseLearnFragmentText=V.findViewById(R.id.courseLearnFragmentText);
        courseCurriculumRecyclerView=V.findViewById(R.id.courseCurriculumRecyclerView);
        preReqList=V.findViewById(R.id.preReqList);
        ArrayAdapter adapter=new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.PreRequisites));
        preReqList.setAdapter(adapter);
        loadCourseOverviewFragment();
        return V;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public void loadCourseOverviewFragment(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST,url,new Response.Listener<String>( ){

            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(), "Successful", Toast.LENGTH_LONG).show();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    courseCurriculumStringArray=jsonObject.getString("course_curriculum").split("/");
                    courseOverviewFragmentTitle.setText(jsonObject.getString("course_name"));
                    courseOverviewFrgamentText.setText(jsonObject.getString( "course_overview").replace("<br>","\n"));
                    courseLearningsStringArray=jsonObject.getString("course_learnings").split("/");
                    setCourseCurriculumFragmentText();
                    setCourseLearnFragmentText();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Unsuccessful", Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    private void setCourseLearnFragmentText() {
        courseLearnFragmentText.setText(" ✔ "+courseLearningsStringArray[0]+"\n\n");
        for(int i=1;i<courseLearningsStringArray.length;i++){
            courseLearnFragmentText.setText(courseLearnFragmentText.getText()+" ✔ "+courseLearningsStringArray[i]+"\n\n");
        }

    }

    private void setCourseCurriculumFragmentText() {

        ArrayList<String> curriculumTitleDataSet=new ArrayList<String>();
        ArrayList<String> curriculumBodyDataSet=new ArrayList<String>();

        int length=courseCurriculumStringArray.length;
        for(int i=0;i<length;i+=2){
            curriculumTitleDataSet.add(courseCurriculumStringArray[i]);
        }
        for(int j=1;j<length;j+=2){
            curriculumBodyDataSet.add(courseCurriculumStringArray[j]);

        }

        courseCurriculumRecyclerView.setHasFixedSize(true);

        courseCurriculumLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        courseCurriculumRecyclerView.setLayoutManager(courseCurriculumLayoutManager);

        courseCurriculumAdapter=new CourseCurriculumAdapter(curriculumTitleDataSet,curriculumBodyDataSet);
        courseCurriculumRecyclerView.setAdapter(courseCurriculumAdapter);
    }
}

