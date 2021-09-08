package com.example.todoapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapplication.R;
import com.example.todoapplication.activity.MainActivity2;
import com.example.todoapplication.model.Todo;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {
    Context context;
    List<Todo> todoList;

    public RecyclerAdapter(Context context, List<Todo> todoList) {
        this.context = context;
        this.todoList = todoList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_view, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);

        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.id.setText("    Id: " + todoList.get(position).getId());
        holder.name.setText("   name: " + todoList.get(position).getName());
        holder.description.setText("   description: " + todoList.get(position).getDescription());
        holder.parent_id.setText("    parent_id: " + todoList.get(position).getParent_id());
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity2.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);;
                context.startActivity(intent);
                Toast.makeText(context, "TEST", Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView id;
        TextView name;
        TextView description;
        TextView parent_id;
        ImageButton imageButton;


        public RecyclerViewHolder(View view) {
            super(view);
             id = (TextView) view.findViewById(R.id.textViewId);
             name = (TextView) view.findViewById(R.id.textViewName);
             description = (TextView) view.findViewById(R.id.textViewDescription);
             parent_id = (TextView) view.findViewById(R.id.textViewParentId);
             imageButton=(ImageButton)view.findViewById(R.id.btn_send);
        }

    }
}
