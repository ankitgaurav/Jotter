package com.ankitgaurav.notes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
    public void onBindViewHolder(final View_Holder holder, final int position) {
        final String todoText = todosList.get(position).getTodoText();
        final int todo_id = todosList.get(position).get_id();
        final String isFlagged = todosList.get(position).getIs_flagged();
        final String todo_created_at = todosList.get(position).getCreatedAt();
        holder.title.setText(getSnippet(todoText, 40));

        if(isFlagged.equals("true")){
            holder.starButton.setButtonDrawable(android.R.drawable.btn_star_big_on);
            //holder.rl1.setBackgroundColor(Color.argb(100,255,0,0));
        }

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                alertDialogBuilder.setTitle(todo_created_at).setMessage(todoText);
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remove(todo_id);
            }
        });
        holder.starButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appDBHandler.flagTodo(todo_id, isFlagged);
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
    private String getSnippet(String text, int snippetLength){
        if(text.length()>snippetLength){
            text = text.substring(0, snippetLength-3) + "...";
        }
        return text;
    }
}