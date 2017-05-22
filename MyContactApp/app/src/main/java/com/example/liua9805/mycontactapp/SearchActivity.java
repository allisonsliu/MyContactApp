package com.example.liua9805.mycontactapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class SearchActivity extends MainActivity {
    DatabaseHelper myDb;
    EditText editSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent = getIntent();
        //String name = intent.getStringExtra("Name");
        //String age = intent.getStringExtra("Age");
        //String phone = intent.getStringExtra("Phone");
        myDb = super.myDb;
        editSearch = (EditText) findViewById(R.id.editText_search);

    }
    public void searchDataTwo(View v) {
        Cursor res = myDb.getAllData();
        StringBuffer bufferSearch = new StringBuffer();
        int count = 0;

        if (res != null) {
            res.moveToFirst();
            for (int i = 0; i < res.getCount(); i++) {
                for (int j = 0; j < res.getColumnCount(); j++) {
                    if (res.getString(j).equals(editSearch.getText().toString())) {
                        bufferSearch.append(res.getString(j) + "\n" + res.getString(j + 1) + "\n" + res.getString(j + 2) + "\n");
                        count++;
                    }
                }
                bufferSearch.append("\n");
                res.moveToNext();
            }
            res.close();
        }
        if (count != 0) {
            showMessage("SearchData", bufferSearch.toString());
        } else {
            showMessage("SearchData", "Not in database");
        }
    }

    private void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
