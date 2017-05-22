package com.example.liua9805.mycontactapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    DatabaseHelper myDb;
    EditText editName;
    EditText editAge;
    EditText editPhone;
    EditText editSearch;
    Button btnAddData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper (this);

        editName = (EditText) findViewById(R.id.editText_name);
        editAge =  (EditText) findViewById(R.id.editText_age);
        editPhone = (EditText) findViewById(R.id.editText_phone);
        editSearch = (EditText) findViewById(R.id.editText_search);
    }

    public void addData(View v){
        boolean isInsertedName = myDb.insertData(editName.getText().toString(),editAge.getText().toString(),editPhone.getText().toString());

        if(isInsertedName == true){
            Log.d("MyContact", "Data insertion successful");
            Toast.makeText(getApplicationContext(), "Data insertion successful", Toast.LENGTH_LONG).show();
            //Creat toast message to user indicating data inserted correctly
        }
        else{
            Log.d("MyContact", "Data insertion NOT successful");
            Toast.makeText(getApplicationContext(), "Data insertion NOT successful", Toast.LENGTH_LONG).show();
        }
    }

    public void viewData(View v){
        Cursor res = myDb.getAllData();
        if(res.getCount() == 0){
            showMessage("Error", "No data found in database");
            Log.d("My Contact", "No data found in database");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        if(res != null){
                res.moveToFirst();
                for(int i = 0; i<res.getCount(); i++){
                    for (int j = 0; j < res.getColumnCount(); j++) {
                        buffer.append(res.getString(j) + "\n");
                    }
                    buffer.append("\n");
                    res.moveToNext();
                }
                res.close();
        }
        //setup loop with cursor moveToNext method
            //      append each COL to buffer
        //      use getString method

        showMessage("Data", buffer.toString());
    }



    private void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void searchData(View v){
        Cursor res = myDb.getAllData();
        StringBuffer bufferSearch = new StringBuffer();
        int count = 0;

        if(res != null){
            res.moveToFirst();
            for(int i = 0; i<res.getCount(); i++){
                for(int j =0; j<res.getColumnCount(); j++){
                    if(res.getString(j).equals(editSearch.getText().toString())){
                        bufferSearch.append(res.getString(j) + "\n" + res.getString(j+1) + "\n" + res.getString(j+2)+ "\n");
                        count++;
                    }
                }
                bufferSearch.append("\n");
                res.moveToNext();
            }
            res.close();
        }
        if(count != 0){
            showMessage("SearchData", bufferSearch.toString());
        }
        else{
            showMessage("SearchData", "Not in database");
        }

    }

    public void openNewPage(View v){
        Intent i = new Intent(this, SearchActivity.class);
        //i.putExtra("Name",editName.getText().toString());
        //i.putExtra("Age",editAge.getText().toString());
        //i.putExtra("Phone",editPhone.getText().toString());
        startActivity(i);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
