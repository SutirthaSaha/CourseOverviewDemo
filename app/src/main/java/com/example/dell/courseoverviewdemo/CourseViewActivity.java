package com.example.dell.courseoverviewdemo;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CourseViewActivity extends AppCompatActivity implements CourseOverviewFragment.OnFragmentInteractionListener,CourseAssessmentFragment.OnFragmentInteractionListener,CourseContentFragment.OnFragmentInteractionListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private String postKey;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_view);
        tabLayout=(TabLayout)findViewById(R.id.tablayout);
        viewPager=(ViewPager)findViewById(R.id.viewPager);
        viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());

        tv=findViewById(R.id.tv);

        postKey=getIntent().getStringExtra("postParam");
        Bundle bundle = new Bundle();
        bundle.putString("post", postKey );
        CourseOverviewFragment courseOverviewFragment = new CourseOverviewFragment();
        courseOverviewFragment.setArguments(bundle);

        viewPagerAdapter.addFragments(courseOverviewFragment,"Course OverView");
        viewPagerAdapter.addFragments(new CourseContentFragment(),"Course Content");
        viewPagerAdapter.addFragments(new CourseAssessmentFragment(),"Course Assessment");


        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
    @Override
    public void onFragmentInteraction(Uri uri) {


    }
}
