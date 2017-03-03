package todo.codepath.piyush6348.com.codepathtodo.fragments;


import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
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
    private Button done,cancel,delete;
    private DatePicker datePicker;
    private EditText todoTitle,todoDescription;
    private String date;
    private String title,desc;
    private int priority=1,priorityNumber;
    private long time=0;
    Cursor cursorToEdit=null;
    public NotesFragment()
    {

    }
    public static NotesFragment newInstance(String title,boolean edit,int position)
    {
        NotesFragment fragment=new NotesFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putBoolean("editable",edit);
        args.putInt("position",position);
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
                   // if(!getArguments().getBoolean("editable"))
                    insertInDatabase();
                    //else
                    //    update();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getArguments().getBoolean("editable"))
                {
                    getActivity().getContentResolver().delete(QuoteProvider.Quotes.CONTENT_URI,
                            DatabseColumns.ID+"=?",new String[]{String.valueOf(getArguments().getInt("position"))});
                }
                    getDialog().dismiss();
            }
        });

        Boolean edittable=getArguments().getBoolean("editable");
        if(edittable)
        {
            int position=getArguments().getInt("position");

            Log.e("onCreateView: ",""+String.valueOf(position));
            cursorToEdit=getActivity().getContentResolver().query(QuoteProvider.Quotes.CONTENT_URI,
                    null, DatabseColumns.ID+"=?",new String[]{String.valueOf(position)},null);
            cursorToEdit.moveToFirst();

            setAllItems(cursorToEdit);
            Log.e("onCreateView: ",cursorToEdit.getString(cursorToEdit.getColumnIndex(DatabseColumns.TITLE)));
        }
        return view;
    }

    private void setAllItems(Cursor cursorToEdit) {
        String title=cursorToEdit.getString(cursorToEdit.getColumnIndex(DatabseColumns.TITLE));
        String desc=cursorToEdit.getString(cursorToEdit.getColumnIndex(DatabseColumns.DESCRIPTION));
        String date=cursorToEdit.getString(cursorToEdit.getColumnIndex(DatabseColumns.DATE));
        long time=cursorToEdit.getLong(cursorToEdit.getColumnIndex(DatabseColumns.TIME));
        int prio=cursorToEdit.getInt(cursorToEdit.getColumnIndex(DatabseColumns.PRIORITY));

        todoTitle.setText(title);
        //todoTitle.findFocus();
        todoDescription.setText(desc);
        if(prio==1)
            spinner.setSelection(0);
        else
            spinner.setSelection(1);


        int day=Integer.parseInt(date.substring(0,date.indexOf('/')));
        int mon=Integer.parseInt(date.substring(date.indexOf('/')+1,date.lastIndexOf('/')));
        int yr=Integer.parseInt(date.substring(date.lastIndexOf('/')+1,date.length()));
        //datePicker.setMaxDate();
        datePicker.updateDate(yr,mon-1,day);
        Log.e( "setAllItems: ",""+day+" "+mon+" "+yr);

    }

    private void insertInDatabase() {
        //date=datePicker.getDayOfMonth()+"-"+(datePicker.getMonth()+1)+"-"+datePicker.getYear();
        date=datePicker.getDayOfMonth()+"/"+(datePicker.getMonth()+1)+"/"+datePicker.getYear();
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

        if(!getArguments().getBoolean("editable"))
        getActivity().getContentResolver().insert(QuoteProvider.Quotes.CONTENT_URI,values);
        else
        getActivity().getContentResolver().update(QuoteProvider.Quotes.CONTENT_URI,values,DatabseColumns.ID+"=?",
                new String[]{String.valueOf(getArguments().getInt("position"))});
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
        delete=(Button)view.findViewById(R.id.delete);
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
