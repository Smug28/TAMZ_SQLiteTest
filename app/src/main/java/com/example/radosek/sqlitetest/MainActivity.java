package com.example.radosek.sqlitetest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {

    DBHelper mydb;
    private ListView obj;
    public static ArrayList<Long> arrayListId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb = new DBHelper(this);
        //naplnim pole polozek
        mydb.setAllContacs();
        //ziskam do jedno listu vsechny polozky
        ArrayList<Entry> arrayList = mydb.getAllContacsName();

        ArrayAdapter<Entry> arrayAdapter=new ArrayAdapter<Entry>(this,android.R.layout.simple_list_item_1, arrayList);

        obj = (ListView)findViewById(R.id.listView1);
        obj.setAdapter(arrayAdapter);
        obj.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //abych vedel jake id v poli mam hledat
                Log.d("Clicked item id", " "+ arg2);
                //TODO 1: zavolat aktivitu, ktera bude zobrazovat informace o zaznamu v db a predat ji hledane id zaznamu
                Intent intent = new Intent(getContext(), DisplayContact.class);
                intent.putExtra("id", ((Entry)arg0.getItemAtPosition(arg2)).id);
                startActivityForResult(intent, 666);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private Context getContext(){
        return this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item1)
        {
            //TODO 2: v menu (pokud je zvoleno vytvoreni noveho itemu) zavolat novou aktivity na pridani kontaktu
            Intent intent = new Intent(this, DisplayContact.class);
            intent.putExtra("id", 0);
            startActivity(intent);
        }

        if (id == R.id.Delete_All_Contact)
        {
            mydb.removeAll();
            Toast.makeText(getApplicationContext(), "Deleted All Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 666 && resultCode == RESULT_OK)
            recreate();
    }
}
