package com.ankitgaurav.notes;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    public TodosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todos, container, false);
        final AppDBHandler appDBHandler;
        appDBHandler = new AppDBHandler(getActivity());
        ArrayList<Todo> arrayList = appDBHandler.getMultipleTodosArrayList("incomplete");

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

            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.todos_recyclerview);
            Todos_Adapter adapter = new Todos_Adapter(arrayList, getContext());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        return view;
    }
}
