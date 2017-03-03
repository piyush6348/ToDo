package todo.codepath.piyush6348.com.codepathtodo.activity;

import android.content.Context;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ListView;

import java.util.List;

import todo.codepath.piyush6348.com.codepathtodo.R;
import todo.codepath.piyush6348.com.codepathtodo.adapter.VideoListCursorAdapter;
import todo.codepath.piyush6348.com.codepathtodo.database.DatabseColumns;
import todo.codepath.piyush6348.com.codepathtodo.database.QuoteProvider;
import todo.codepath.piyush6348.com.codepathtodo.fragments.NotesFragment;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private VideoListCursorAdapter videoListCursorAdapter;
    private RecyclerView recyclerView;
    private FloatingActionButton newToDo;
    Context context;
    Cursor c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();

        newToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newToDoFunction();
            }
        });
        context=this;
        getSupportLoaderManager().initLoader(0, null,this);
        //recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setLayoutManager(new GridLayoutManager(context,3));
        recyclerView.setHasFixedSize(true);
        c = getContentResolver().query(QuoteProvider.Quotes.CONTENT_URI, null, null, null, null);
        videoListCursorAdapter = new VideoListCursorAdapter(MainActivity.this, c);
        videoListCursorAdapter.notifyDataSetChanged();

        recyclerView.setAdapter(videoListCursorAdapter);

        int ct=c.getCount();
        c.moveToFirst();
        for(int i=0;i<ct;i++)
        {
            c.moveToPosition(i);
            Log.e("onCreate: ",c.getString(c.getColumnIndex(DatabseColumns.ID)) );
        }
    }

    private void newToDoFunction() {
        FragmentManager fm = getSupportFragmentManager();
        NotesFragment editNameDialogFragment = NotesFragment.newInstance("Add a new ToDo",false,-1);
        editNameDialogFragment.show(fm,"fragment_edit_name");
    }

    private void initialize() {
        newToDo=(FloatingActionButton) findViewById(R.id.new_to_do);
        recyclerView=(RecyclerView)findViewById(R.id.list_of_notes);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader = new CursorLoader(MainActivity.this, QuoteProvider.Quotes.CONTENT_URI, null, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (videoListCursorAdapter != null)
            videoListCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        videoListCursorAdapter.swapCursor(null);
    }
}
