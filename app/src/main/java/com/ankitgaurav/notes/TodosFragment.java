package com.ankitgaurav.notes;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TodosFragment extends Fragment {

    private RecyclerView  recyclerView;
    private Todos_Adapter adapter;
    ArrayList<Todo> arrayList;
    AppDBHandler appDBHandler;

    public TodosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todos, container, false);
        appDBHandler = new AppDBHandler(getActivity());
        arrayList = appDBHandler.getMultipleTodosArrayList("incomplete");

        /* The following if-else block toggles the visibility of the placeholder
        text that should be displayed if there is no todos items to be displayed
         */
        if(arrayList.size()==0){
            TextView view1 = (TextView) view.findViewById(R.id.todos_placeholder_text1);
            TextView view2 = (TextView) view.findViewById(R.id.todos_placeholder_text2);
            view1.setVisibility(View.VISIBLE);
            view2.setVisibility(View.VISIBLE);
        }
        else{
            TextView view1 = (TextView) view.findViewById(R.id.todos_placeholder_text1);
            TextView view2 = (TextView) view.findViewById(R.id.todos_placeholder_text2);
            view1.setVisibility(View.GONE);
            view2.setVisibility(View.GONE);

            recyclerView = (RecyclerView) view.findViewById(R.id.todos_recyclerview);
            adapter = new Todos_Adapter(arrayList, getContext());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        return view;
    }

    //Todo: make the todo editor to be revealed from the bottom
    @Override
    public void onResume() {
        /* The code below is simply refreshes the adapter for the todos
        so that newly added todos  can be reflected upon re-entering the
        fragment from the todos-editor activity

        However, this code doesn't seem to be the right way to refresh the todos list
        and should be replaced with a better technique
         */
        arrayList = appDBHandler.getMultipleTodosArrayList("incomplete");;
        adapter = new Todos_Adapter(arrayList, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        super.onResume();
    }
}
//Todo: Modify the UI of the detailed todo view and todo editor