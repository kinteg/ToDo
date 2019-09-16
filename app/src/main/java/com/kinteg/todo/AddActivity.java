package com.kinteg.todo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    private EditText name, desc;
    private int tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        name = this.findViewById(R.id.name);
        desc = this.findViewById(R.id.desc);

        if (getIntent().getExtras() != null) {
            tag = getIntent().getIntExtra("tag", 0);
            name.setText(Store.getStore().get(tag).getName());
            desc.setText(Store.getStore().get(tag).getName());
        }
    }

    public void save(View view) {
        if (getIntent().getExtras() == null) {
            Store.getStore().add(new Item(name.getText().toString(), desc.getText().toString(), Calendar.getInstance(), Store.getStore().size()));
        } else {
            Item item = Store.getStore().get(tag);
            item.setName(name.getText().toString());
            item.setDescription(desc.getText().toString());
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
