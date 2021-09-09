package com.example.todoapplication.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.todoapplication.R;

public class MainActivity2 extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textView=findViewById(R.id.textView);
        Intent intent =getIntent();
        Integer id = intent.getIntExtra("id",0);
        String name = intent.getStringExtra("name");
        String description = intent.getStringExtra("description");
        Integer parent_id = intent.getIntExtra("parent_id",0);
        textView.setText("id: "+id+"\nname: "+name+"\ndescription: "+description+"\nparent_id: "+parent_id);

    }
}