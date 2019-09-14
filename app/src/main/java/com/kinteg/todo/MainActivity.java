package com.kinteg.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final List<Item> items = new ArrayList<>();
    private final RecyclerView.Adapter adapter = new ItemAdapter(this.items);
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
            this.items.add(new Item(editText.getText().toString()));
            editText.setText("");
            adapter.notifyItemInserted(this.items.size() - 1);
        }
    }

    public void delete(View view) {
        TextView textView = (TextView) view;
        String itemName = textView.getText().toString();
        int itemId = this.items.indexOf(new Item(itemName.split(" ")[1]));
        this.items.remove(itemId);
        adapter.notifyItemRemoved(itemId);
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
            TextView name = holder.itemView.findViewById(R.id.name);
            name.setText(String.format("%s. %s", position, this.items.get(position).getName()));
        }

        @Override
        public int getItemCount() {
            return this.items.size();
        }
    }
}
