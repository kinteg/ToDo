package com.kinteg.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private final List<Item> items = new ArrayList<>();
    private final RecyclerView.Adapter adapter = new ItemAdapter(this.items);
    private List<ConstraintLayout> layouts = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    public void add(View view) {
        EditText editText = this.findViewById(R.id.editText);
        if (!editText.getText().toString().equals("")) {
            this.items.add(new Item(editText.getText().toString(), Calendar.getInstance(), this.items.size()));
            editText.setText("");
            adapter.notifyItemInserted(this.items.size() - 1);
        }
    }

    public void delete(View view) {
        ConstraintLayout constraintLayout = (ConstraintLayout) view;
        int itemId = this.items.indexOf(new Item(null, null, Integer.parseInt(constraintLayout.getTag().toString())));
        if (itemId != -1) {
            this.items.remove(itemId);
            this.layouts.remove(itemId);
            adapter.notifyItemRemoved(itemId);
            updateItems(itemId);
        }
    }

    private void updateItems(int itemId) {
        for (int i = itemId; i < items.size(); i++) {
            this.layouts.get(i).setTag(this.items.get(i).getId());
            TextView name = this.layouts.get(i).findViewById(R.id.name);
            name.setText(String.format("%d. %s", i, this.items.get(i).getName()));
        }
    }

    private class ItemAdapter extends RecyclerView.Adapter {

        private final List<Item> items;

        public ItemAdapter(List<Item> items) {
            this.items = items;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RecyclerView.ViewHolder (LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false)) {};
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ConstraintLayout constraintLayout = holder.itemView.findViewById(R.id.itemLayout);
            layouts.add(constraintLayout);
            TextView name = holder.itemView.findViewById(R.id.name);
            TextView created = holder.itemView.findViewById(R.id.created);
            Item item = this.items.get(position);
            constraintLayout.setTag(item.getId());
            name.setText(String.format("%s. %s", position, item.getName()));
            created.setText(format(item.getCreated()));
            CheckBox done = holder.itemView.findViewById(R.id.done);
            done.setOnCheckedChangeListener((view, checked) -> {
                item.setDone(checked);
                TextView closed = holder.itemView.findViewById(R.id.closed);
                if (checked) {
                    item.setClosed(Calendar.getInstance());
                    closed.setText(format(item.getClosed()));
                } else {
                    closed.setText("");
                    item.setClosed(null);
                }
            });
        }

        private String format(Calendar calendar) {
            return String.format(
                    Locale.getDefault(), "%02d.%02d.%d",
                    calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR)
            );
        }

        @Override
        public int getItemCount() {
            return this.items.size();
        }



    }
}
