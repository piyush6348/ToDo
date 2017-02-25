package todo.codepath.piyush6348.com.codepathtodo.fragments;


import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;

import todo.codepath.piyush6348.com.codepathtodo.R;
import todo.codepath.piyush6348.com.codepathtodo.database.DatabseColumns;
import todo.codepath.piyush6348.com.codepathtodo.database.QuoteProvider;
import todo.codepath.piyush6348.com.codepathtodo.model.ToDoItem;

/**
 * Created by dell on 2/25/2017.
 */

public class NotesFragment extends DialogFragment implements AdapterView.OnItemSelectedListener {
    private Spinner spinner;
    private static final String[]paths = {"High", "Low"};
    private Button done,cancel;
    private DatePicker datePicker;
    private EditText todoTitle,todoDescription;
    private String date;
    private String title,desc;
    private int priority=1,priorityNumber;
    private long time=0;
    public NotesFragment()
    {

    }
    public static NotesFragment newInstance(String title)
    {
        NotesFragment fragment=new NotesFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_notes_details, container);

        initializer(view);

        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);

        ArrayAdapter<String>adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate())
                {
                    insertInDatabase();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        return view;
    }

    private void insertInDatabase() {
        date=datePicker.getDayOfMonth()+"-"+(datePicker.getMonth()+1)+"-"+datePicker.getYear();
        Calendar calendar=Calendar.getInstance();
        time=calendar.getTimeInMillis();

        ToDoItem object=new ToDoItem(title
                ,desc,priority,time,date);

        ContentValues values=new ContentValues();
        values.put(DatabseColumns.TITLE,object.getTitle());
        values.put(DatabseColumns.DESCRIPTION,object.getDescription());
        values.put(DatabseColumns.DATE,object.getDate());
        values.put(DatabseColumns.PRIORITY,object.getPriority());
        values.put(DatabseColumns.TIME,object.getTime());

        getActivity().getContentResolver().insert(QuoteProvider.Quotes.CONTENT_URI,values);
        getDialog().dismiss();
    }

    private boolean validate() {
        if(todoTitle.getText().length()<=0)
            return false;
        else
            title=todoTitle.getText().toString();

        if(todoDescription.getText().length()<=0)
            return false;
        else
            desc=todoDescription.getText().toString();
        return true;
    }

    private void initializer(View view) {
        spinner = (Spinner)view.findViewById(R.id.spinner);
        done=(Button)view.findViewById(R.id.done_button);
        cancel=(Button)view.findViewById(R.id.cancel_button);
        datePicker=(DatePicker) view.findViewById(R.id.date_picker);
        todoTitle=(EditText)view.findViewById(R.id.todo_title);
        todoDescription=(EditText)view.findViewById(R.id.todo_description);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0:
                // Whatever you want to happen when the first item gets selected
                priority=1;
                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
                priority=0;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
