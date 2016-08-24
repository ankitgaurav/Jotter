package layout;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ankitgaurav.notes.DetailedNote;
import com.ankitgaurav.notes.MyDBHandler;
import com.ankitgaurav.notes.Note;
import com.ankitgaurav.notes.NotesAdapter;
import com.ankitgaurav.notes.R;

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
        final MyDBHandler notesDBHandler;
        notesDBHandler = new MyDBHandler(container.getContext());
        ArrayList<Note> arrayList = notesDBHandler.dbToNoteObjectArrayList();

        final NotesAdapter notesAdapter = new NotesAdapter(getContext(), arrayList);
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
                startActivity(intent);
            }
        });
        return view;

    }

}
