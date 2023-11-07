package com.example.swipenotes;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;

import io.realm.Realm;
import io.realm.RealmResults;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    Context context;
    RealmResults<Note> noteList;
    public Adapter(Context context, RealmResults<Note> noteList) {

        this.context = context;
        this.noteList = noteList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Note note = noteList.get(position);

        holder.title.setText(note.getTitle());
        holder.description.setText(note.getDescription());

        String formattedTime = DateFormat.getDateTimeInstance().format(note.timeCreated);
        holder.time.setText(formattedTime);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu menu = new PopupMenu(context, view);
                menu.getMenu().add("Delete");
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getTitle().equals("Delete")) {
                            // Deletes the note
                            Realm realm = Realm.getDefaultInstance();
                            realm.beginTransaction();
                            note.deleteFromRealm();
                            realm.commitTransaction();
                            Toast.makeText(context, "Note Deleted", Toast.LENGTH_SHORT).show();

                        }
                        return true;
                    }
                });

                menu.show();

                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }
    // This is the adapter for the RecyclerView

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView description;
        TextView time;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleofnote);
            description = itemView.findViewById(R.id.descriptionofnote);
            time = itemView.findViewById(R.id.timeofnote);

        }
    }
}
