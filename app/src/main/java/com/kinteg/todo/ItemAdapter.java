package com.kinteg.todo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ConstraintLayout> layouts = new ArrayList<>();
    private Context context;
    private Store store = Store.getStore();

    public ItemAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerView.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false)) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ConstraintLayout constraintLayout = holder.itemView.findViewById(R.id.itemLayout);
        constraintLayout.setOnLongClickListener(ItemAdapter.this::delete);
        constraintLayout.setOnClickListener(ItemAdapter.this::open);
        layouts.add(constraintLayout);
        TextView name = holder.itemView.findViewById(R.id.name);
        TextView created = holder.itemView.findViewById(R.id.created);
        TextView shortDesk = holder.itemView.findViewById(R.id.shortDesc);
        Item item = store.get(position);
        constraintLayout.setTag(item.getTag());
        name.setText(String.format("%s. %s", position, item.getName()));
        created.setText(format(item.getCreated()));
        shortDesk.setText(item.getShortDesc());
        CheckBox done = holder.itemView.findViewById(R.id.done);
        done.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            item.setDone(isChecked);
            TextView closed = holder.itemView.findViewById(R.id.closed);
            if (isChecked) {
                item.setClosed(Calendar.getInstance());
                closed.setText(format(item.getClosed()));
            } else {
                closed.setText("");
                item.setClosed(null);
            }
        }));
    }

    public boolean delete(View view) {
        ConstraintLayout constraintLayout = (ConstraintLayout) view;
        int itemId = layouts.indexOf(constraintLayout);
        if (itemId != -1) {
            store.getAll().remove(itemId);
            layouts.remove(itemId);
            this.notifyItemRemoved(itemId);
            updateItems(itemId);
            return true;
        }
        return false;
    }

    private void updateItems(int itemId) {
        for (int i = itemId; i < store.size(); i++) {
            store.get(i).setTag(i);
            this.layouts.get(i).setTag(i);
            TextView name = this.layouts.get(i).findViewById(R.id.name);
            name.setText(String.format("%d. %s", i, store.get(i).getName()));
        }
    }

    public void open(View view) {
        ConstraintLayout constraintLayout = (ConstraintLayout) view;
        Intent intent = new Intent(context, AddActivity.class);
        intent.putExtra("tag", Integer.parseInt(constraintLayout.getTag().toString()));
        context.startActivity(intent);
    }

    private String format(Calendar calendar) {
        return String.format(
                Locale.getDefault(), "%02d.%02d.%d",
                calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR)
        );
    }

    @Override
    public int getItemCount() {
        return store.size();
    }

}