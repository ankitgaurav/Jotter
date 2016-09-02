package com.ankitgaurav.notes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Todos_Adapter extends RecyclerView.Adapter<View_Holder> {

    ArrayList<Todo> todosList = new ArrayList<>();
    Context context;
    AppDBHandler appDBHandler;

    public Todos_Adapter(ArrayList<Todo> list, Context context) {
        this.todosList = list;
        this.context = context;
    }

    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.todos_row_item, parent, false);
        View_Holder holder = new View_Holder(v);
        appDBHandler = new AppDBHandler(context);
        return holder;
    }

    @Override
    public void onBindViewHolder(View_Holder holder, int position) {
        final String todoText = todosList.get(position).getTodoText();
        final int todo_id = todosList.get(position).get_id();
        holder.title.setText(todoText);

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remove(todo_id);
            }

        });
    }

    @Override
    public int getItemCount() {
        return todosList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void insert(int position, Todo data) {
        todosList.add(position, data);
        notifyItemInserted(position);
    }

    public void remove(int todo_id) {
        int position = 0;
        for(int i=0; i<todosList.size(); i++){
            if(todosList.get(i).get_id() == todo_id){
                position = i;
                break;
            }
        }
        appDBHandler.updateTodo(todosList.get(position).get_id(), todosList.get(position));
        todosList.remove(position);
        notifyItemRemoved(position);
    }
}