package com.example.todoapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.todoapplication.R;
import com.example.todoapplication.model.Todo;

import java.util.List;

public class TodoAdapter extends BaseAdapter {
    Context context;
    List<Todo> todoList;

    public TodoAdapter(Context context, List<Todo> todoList) {
        this.context = context;
        this.todoList = todoList;
    }

    @Override
    public int getCount() {
        return todoList.size();
    }

    @Override
    public Object getItem(int position) {
        return todoList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_view, parent, false);
        }
        TextView id = (TextView) convertView.findViewById(R.id.textViewId);
        TextView name = (TextView) convertView.findViewById(R.id.textViewName);
        TextView description = (TextView) convertView.findViewById(R.id.textViewDescription);
        TextView parent_id = (TextView) convertView.findViewById(R.id.textViewParentId);
        id.setText("    Id: " + todoList.get(position).getId());
        name.setText("   name: " + todoList.get(position).getName());
        description.setText("   description: " + todoList.get(position).getDescription());
        parent_id.setText("    parent_id: " + todoList.get(position).getParent_id());


        return convertView;
    }
}