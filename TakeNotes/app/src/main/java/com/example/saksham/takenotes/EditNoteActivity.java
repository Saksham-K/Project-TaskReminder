package com.example.saksham.takenotes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.HashSet;

public class EditNoteActivity extends AppCompatActivity {

    EditText editnote;
    String noteData;
    Intent intent;
    int position;



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addnote:

                Intent gotoSetRemainder = new Intent(getApplicationContext(), SetRemainder.class);
                startActivity(gotoSetRemainder);

                return true;
            default:
                return false;
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        editnote =(EditText) findViewById(R.id.editNote);
        intent= getIntent();
        position=intent.getIntExtra("position",-1);

        if(position!=-1 && MainActivity.arrayList.get(position)!="+ Add New Note")
        {
            editnote.setText(MainActivity.arrayList.get(position));
        }

        else if (position==-1)
        {
            editnote.setText("");
        }


    }

  /*  @Override
    public void onBackPressed(){
        super.onBackPressed();
        noteData = editnote.getText().toString();

        if(position!=-1 ) {

//        saveNoteSp = this.getSharedPreferences("com.example.saksham.takenotes", Context.MODE_PRIVATE);
//        saveNoteSp.edit().putString(String.valueOf(position),noteData).apply();

            MainActivity.arrayList.set(position, noteData);
            MainActivity.arrayAdapter.notifyDataSetChanged();
            saveNoteperm();


//        Intent gotoMain = new Intent(getApplicationContext(),MainActivity.class);
//        gotoMain.putExtra("saveNoteSp",saveNoteSp);

        }

        else if (position==-1)
        {
            MainActivity.arrayList.add(editnote.getText().toString());
            MainActivity.arrayAdapter.notifyDataSetChanged();
            saveNoteperm();
        }

    }*/
    @Override
    public void onStop() {
        super.onStop();
        noteData = editnote.getText().toString();

        if(position!=-1 ) {

            MainActivity.arrayList.set(position, noteData);
            MainActivity.arrayAdapter.notifyDataSetChanged();
            saveNoteperm();

        }

        else if (position==-1)
        {
            MainActivity.arrayList.add(noteData);
            MainActivity.arrayAdapter.notifyDataSetChanged();
            saveNoteperm();
        }
    }


    public void  saveNoteperm(){
        SharedPreferences sharedpref = this.getSharedPreferences("com.example.saksham.takenotes", Context.MODE_PRIVATE);
        HashSet hs = new HashSet(MainActivity.arrayList);
        sharedpref.edit().putStringSet("notes",hs).apply();
    }
}
