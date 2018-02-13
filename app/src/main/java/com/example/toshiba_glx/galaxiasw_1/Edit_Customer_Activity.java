package com.example.toshiba_glx.galaxiasw_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class Edit_Customer_Activity extends AppCompatActivity {
    String clienteID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__customer);
        // agrega la flecha de back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        clienteID = i.getStringExtra("clienteId");

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ma_activity_edit_customer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

       switch(item.getItemId()){
           default:
               Toast.makeText(this, "atras:"+item.getItemId()
                       , Toast.LENGTH_SHORT).show();
               onBackPressed();
               break;
        }

        return super.onOptionsItemSelected(item);
    }
}
