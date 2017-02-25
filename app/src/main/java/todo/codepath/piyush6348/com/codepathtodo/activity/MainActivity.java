package todo.codepath.piyush6348.com.codepathtodo.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import todo.codepath.piyush6348.com.codepathtodo.R;
import todo.codepath.piyush6348.com.codepathtodo.fragments.NotesFragment;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton newToDo;
    private ListView listOfNotes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();

        newToDoFunction();
    }

    private void newToDoFunction() {
        FragmentManager fm = getSupportFragmentManager();
        NotesFragment editNameDialogFragment = NotesFragment.newInstance("Add a new ToDo");
        editNameDialogFragment.show(fm,"fragment_edit_name");
    }

    private void initialize() {
        newToDo=(FloatingActionButton) findViewById(R.id.new_to_do);
        listOfNotes=(ListView)findViewById(R.id.list_of_notes);
    }
}
