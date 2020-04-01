package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "DESCRIERE";
    public TextView textView;
    public MenuView.ItemView item;
    public Button senzori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ListView l = (ListView) findViewById(R.id.listView);

        textView = (TextView) findViewById(R.id.textView);
        String[] elements = new String[]{"mere", "suc"};
        final String[] val = new String[]{"1 leu", "7 lei"};

        ArrayList<String> ElementsArrayList = new ArrayList<String>(Arrays.asList(elements));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ElementsArrayList);

        l.setAdapter(adapter);

        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textView.setText(val[position]);
                Intent nextActivity = new Intent(MainActivity.this, DisplayMessageActivity.class);
                nextActivity.putExtra("pret",val[position]);

                //salvez datele
                FileOutputStream outputStream = null;
                try {
                    outputStream = getApplicationContext().openFileOutput("fisier.txt", Context.MODE_PRIVATE);
                    outputStream.write(val[position].getBytes());
                    outputStream.close();
                    Log.i("!!!!!!",getFilesDir().toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                startActivity(nextActivity);

            }
        });

        senzori = (Button)findViewById(R.id.senzori);
        senzori.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent nextActivity = new Intent(MainActivity.this, SenzoriActivity.class);
                startActivity(nextActivity);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.blue) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Informatii despre user");
            builder.setMessage("Username");
            builder.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                  dialog.cancel();
                }
            });
            builder.create();
            builder.show();
        }

        if(item.getItemId() == R.id.settings)
        {
            Intent nextActivity = new Intent(MainActivity.this, PreferencesSharedActivity.class);
            nextActivity.putExtra("pret","7lei");
            startActivity(nextActivity);
        }

        return true;
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Log.d("LifeCicle", "In the onStart() event");
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.d("LifeCicle", "In the onResume() event");
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Log.d("LifeCicle", "In the onPause() event");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.d("LifeCicle", "In the onDestroy() event");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("MyString", textView.getText().toString());

        textView.getText().toString();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        textView.setText(savedInstanceState.getString("MyString"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return true;
    }
}
