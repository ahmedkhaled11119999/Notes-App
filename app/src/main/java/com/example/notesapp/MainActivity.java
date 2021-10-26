package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    static ArrayList<String> notes = new ArrayList<String>();
    static ArrayAdapter arrayAdapter;
    SharedPreferences sharedPreferences;
    ListView listView;
    Set<String> stringSet = new HashSet<String>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.notes_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            super.onOptionsItemSelected(item);
        switch(item.getItemId()){
            case R.id.addNote:
                Intent intent = new Intent(getApplicationContext(), EditNotesActivity.class);

                if(notes.isEmpty() || !notes.get(notes.size()-1).equals("Add your note..")){
                    intent.putExtra("noteID",notes.size() );
                    notes.add("");
                }
                else{
                    intent.putExtra("noteID",notes.size()-1);
                }
                startActivity(intent);
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("com.example.notesapp", Context.MODE_PRIVATE);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);
        listView = findViewById(R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), EditNotesActivity.class);
                intent.putExtra("noteID", position);
                startActivity(intent);
            }

        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setMessage("You sure u wanna delete note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                notes.remove(position);
                                arrayAdapter.notifyDataSetChanged();
                                HashSet<String> strings = new HashSet<String>(MainActivity.notes);
                                sharedPreferences.edit().putStringSet("notes",strings).apply();
                            }
                        })
                .setNegativeButton("No",null).show();
                return true;
            }
        });
        if (sharedPreferences.contains("notes")) {
            stringSet = sharedPreferences.getStringSet("notes", null);
            for (String str : stringSet)
                notes.add(str);

        }
        else
            {
                notes.add("Add your note..");
            }
            listView.setAdapter(arrayAdapter);

        }
    }
