package todo.codepath.piyush6348.com.codepathtodo.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import todo.codepath.piyush6348.com.codepathtodo.R;
import todo.codepath.piyush6348.com.codepathtodo.database.DatabseColumns;
import todo.codepath.piyush6348.com.codepathtodo.database.QuoteProvider;
import todo.codepath.piyush6348.com.codepathtodo.fragments.NotesFragment;


public class VideoListCursorAdapter extends CursorRecyclerViewAdapter<VideoListCursorAdapter.ViewHolder> {

    static private Context context;

    public VideoListCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView headline;
        public TextView description,date;
        public ViewHolder(View view) {
            super(view);
            headline = (TextView) view.findViewById(R.id.heading);
            description=(TextView)view.findViewById(R.id.description);
            date=(TextView)view.findViewById(R.id.date);
            view.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            Cursor c = view.getContext().getContentResolver().query(QuoteProvider.Quotes.CONTENT_URI, null, null, null, null);
            c.moveToFirst();
            c.moveToPosition(getAdapterPosition());
            newToDoFunction(c.getInt(c.getColumnIndex(DatabseColumns.ID)));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_card, parent, false);

        ViewHolder vh = new ViewHolder(itemView);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        viewHolder.headline.setText(cursor.getString(cursor.getColumnIndex("title")));
        viewHolder.description.setText(cursor.getString(cursor.getColumnIndex("description")));
        viewHolder.date.setText(cursor.getString(cursor.getColumnIndex(DatabseColumns.DATE)));
    }

    private void newToDoFunction(int id) {
        FragmentManager fm = ((FragmentActivity)context).getSupportFragmentManager();
        NotesFragment editNameDialogFragment = NotesFragment.newInstance("Add a new ToDo",true,id);
        editNameDialogFragment.show(fm,"fragment_edit_name");
    }

}