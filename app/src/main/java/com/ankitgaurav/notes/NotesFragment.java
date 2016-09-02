package com.ankitgaurav.notes;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotesFragment extends Fragment {

    public NotesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        final AppDBHandler notesDBHandler;
        notesDBHandler = new AppDBHandler(container.getContext());
        ArrayList<Note> arrayList = notesDBHandler.getMultipleNotesArrayList();
        ArrayList<Note> unlockedArrayList =  new ArrayList<>();

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        boolean lockStateBool = sharedPref.getBoolean("lockStateBool", true);
        if(lockStateBool){
            for(int i=0; i<arrayList.size(); i++){
                if(arrayList.get(i).getIsLocked() == 0)
                    unlockedArrayList.add(arrayList.get(i));
            }
        }
        else{
            unlockedArrayList = arrayList;
        }
        NotesAdapter notesAdapter = new NotesAdapter(getContext(), unlockedArrayList);
        final ListView notesListView = (ListView) view.findViewById(R.id
                .NotesListView);
        notesListView.setAdapter(notesAdapter);
        notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Object o = notesListView.getItemAtPosition(position);
                Note note=(Note)o;
                Intent intent = new Intent(getContext(), DetailedNote.class);
                intent.putExtra("note_id", note.get_id());
                intent.putExtra("note_created_at", note.getCreatedAt());
                intent.putExtra("noteText", note.getNoteText());
                intent.putExtra("note_is_locked", note.getIsLocked());
                startActivity(intent);
            }
        });
        return view;
    }

}
