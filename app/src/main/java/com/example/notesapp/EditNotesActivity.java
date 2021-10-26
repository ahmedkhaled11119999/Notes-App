package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.util.HashSet;
import java.util.Set;

public class EditNotesActivity extends AppCompatActivity {
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.notesapp", Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_notes);
        EditText editText = findViewById(R.id.editText);
        Intent intent = getIntent();
        position = intent.getIntExtra("noteID",-1);
        if(position == -1){
            MainActivity.notes.add("");
            position = MainActivity.notes.size() - 1;
        }
        editText.setText(MainActivity.notes.get(position));
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.notes.set(position,String.valueOf(s));
                MainActivity.arrayAdapter.notifyDataSetChanged();
                HashSet<String> strings = new HashSet<String>(MainActivity.notes);
                sharedPreferences.edit().putStringSet("notes",strings).apply();
            }

            @Override
            public void afterTextChanged(Editable s) {



            }
        });
    }
}