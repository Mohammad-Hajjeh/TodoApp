package com.example.todoapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapplication.R;
import com.example.todoapplication.activity.MainActivity;
import com.example.todoapplication.activity.MainActivity2;
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
        ViewHolder viewHolder=new ViewHolder(convertView);
        TextView id = (TextView) convertView.findViewById(R.id.textViewId);
        TextView name = (TextView) convertView.findViewById(R.id.textViewName);
        TextView description = (TextView) convertView.findViewById(R.id.textViewDescription);
        TextView parent_id = (TextView) convertView.findViewById(R.id.textViewParentId);
        ImageButton imageButton=(ImageButton)convertView.findViewById(R.id.btn_send);
        id.setText("    Id: " + todoList.get(position).getId());
        name.setText("   name: " + todoList.get(position).getName());
        description.setText("   description: " + todoList.get(position).getDescription());
        parent_id.setText("    parent_id: " + todoList.get(position).getParent_id());

        viewHolder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity2.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id",todoList.get(position).getId());
                intent.putExtra("name",todoList.get(position).getName());
                intent.putExtra("description",todoList.get(position).getDescription());
                intent.putExtra("parent_id",todoList.get(position).getParent_id());
                context.startActivity(intent);
                Toast.makeText(context, "TEST", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageButton imageButton;
        public ViewHolder(View itemView) {
            super(itemView);
            imageButton = itemView.findViewById(R.id.btn_send);

        }
    }
}