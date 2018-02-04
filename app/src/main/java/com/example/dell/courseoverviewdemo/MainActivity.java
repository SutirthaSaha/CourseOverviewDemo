package com.example.dell.courseoverviewdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    String[] courseName={"Android","Python","Web","Java"};
    String[] courseUrl={"mobile-app-development-using-android",
            "programming-101-in-python",
            "web-development-using-php-and-mysql",
            "object-oriented-programming-in-java"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv=findViewById(R.id.lv);

        ArrayAdapter adapter=new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,courseName);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent=new Intent(MainActivity.this,CourseViewActivity.class);
                intent.putExtra("postParam",courseUrl[position]);
                startActivity(intent);
            }
        });

    }
}
