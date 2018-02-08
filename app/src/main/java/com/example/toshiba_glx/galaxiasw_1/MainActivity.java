package com.example.toshiba_glx.galaxiasw_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AsyncResponse{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> lista_frutas = new ArrayList<>();

        JSONdata json1 = new JSONdata(this);
        json1.execute ("");

    }
    @Override
    public void processFinish(String output){
        Toast.makeText(this,"m-"+output,Toast.LENGTH_LONG);
    }
}
