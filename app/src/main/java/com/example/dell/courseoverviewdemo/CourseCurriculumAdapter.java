package com.example.dell.courseoverviewdemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by DELL on 04-Feb-18.
 */

class CourseCurriculumAdapter extends RecyclerView.Adapter<CourseCurriculumAdapter.CurriculumHolder>{

    private ArrayList<String> curriculumTitleDataSet;
    private ArrayList<String> curriculumBodyDataSet;

    public CourseCurriculumAdapter(ArrayList<String> curriculumTitleDataSet, ArrayList<String> curriculumBodyDataSet) {
        this.curriculumTitleDataSet = curriculumTitleDataSet;
        this.curriculumBodyDataSet = curriculumBodyDataSet;
    }

    @Override
    public CurriculumHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.course_curriculum_item_layout,parent,false);
        CurriculumHolder curriculumHolder=new CurriculumHolder(view);
        return curriculumHolder;
    }

    @Override
    public void onBindViewHolder(CurriculumHolder holder, int position) {

        holder.courseCurriculumTitle.setText(curriculumTitleDataSet.get(position));
        holder.courseCurriculumBody.setText(curriculumBodyDataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return curriculumBodyDataSet.size();
    }

    public class CurriculumHolder extends RecyclerView.ViewHolder {
        public TextView courseCurriculumTitle,courseCurriculumBody;
        public CurriculumHolder(View itemView) {
            super(itemView);
            courseCurriculumTitle=itemView.findViewById(R.id.courseCurriculumTitle);
            courseCurriculumBody=itemView.findViewById(R.id.courseCurriculumBody);


        }
    }
}