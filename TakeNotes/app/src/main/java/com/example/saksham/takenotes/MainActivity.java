package com.example.saksham.takenotes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    ListView listNotes ;
   static ArrayList<String> arrayList;
   static ArrayAdapter<String> arrayAdapter;
    SharedPreferences saveNoteSp;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.addnote:

                Intent gotoEditIntent = new Intent(getApplicationContext(),EditNoteActivity.class);
                gotoEditIntent.putExtra("position",-1);
                startActivity(gotoEditIntent);

                return true;
            default:
                return false;
        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        saveNoteSp = this.getSharedPreferences("com.example.saksham.takenotes", Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrayList = new ArrayList<String>();
        listNotes= (ListView) findViewById(R.id.listNotes);

        SharedPreferences sharedpref = this.getSharedPreferences("com.example.saksham.takenotes", Context.MODE_PRIVATE);
        HashSet<String> hashSet = (HashSet<String>) sharedpref.getStringSet("notes",null);

        if(hashSet==null) {
                arrayList.add("+ Add New Note");
        }
        else {
            arrayList= new ArrayList(hashSet);
        }

         arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        listNotes.setAdapter(arrayAdapter);

        listNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemposition, long l) {

                Intent gotoEditIntent = new Intent(getApplicationContext(),EditNoteActivity.class);
                gotoEditIntent.putExtra("position",itemposition);
                startActivity(gotoEditIntent);

            }
        });

        listNotes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final int itemToDelete=i;

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                arrayList.remove(itemToDelete);
                                arrayAdapter.notifyDataSetChanged();

                                SharedPreferences sharedpref = MainActivity.this.getSharedPreferences("com.example.saksham.takenotes", Context.MODE_PRIVATE);
                                HashSet hs = new HashSet(MainActivity.arrayList);
                                sharedpref.edit().putStringSet("notes",hs).apply();
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();
                return true;
            }
        });
    }


}
