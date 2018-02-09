package com.example.toshiba_glx.galaxiasw_1;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button buscar,agregar;
    private ListView listaa;
    String searchTerm ;
    //SI OS FIJAIS LA URL EMPIEZA POR dl EN VEZ DE www
    String URL_base = "http://192.168.1.35/galaxiaSW/consulta.php";
    String URL;
    int contador=0;
    Activity a;
    Context context;
    static ArrayList<cliente_CLASS> lista;
    JSONArray pers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = new ArrayList<cliente_CLASS>();
        a=this;
        context=getApplicationContext();
        listaa = (ListView) findViewById(R.id.listViewLista);
        buscar = (Button) findViewById(R.id.btnBusqueda);
        agregar = (Button) findViewById(R.id.btnAgregar);
        buscar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                contador=0;
                listaa.setAdapter(null);
                URL= String.format("%s?contador=%d",URL_base,contador);
                new GetContacts(listaa).execute();
                agregar.setVisibility(View.VISIBLE);
            }
        });
        agregar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                URL= String.format("%s?contador=%d",URL_base,contador);
                new GetContacts(listaa).execute();
            }
        });
    }
    private class GetContacts extends AsyncTask<Void, Void, Void> {
        ListView list;
        private ProgressDialog pDialog;
        public GetContacts(ListView listaa) {
            this.list=listaa;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // CREAMOS LA INSTANCIA DE LA CLASE
            JSONdata sh = new JSONdata();

            String jsonStr = sh.makeServiceCall(URL, JSONdata.GET);


            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    pers = jsonObj.getJSONArray("gx_cliente");

                    // looping through All Equipos
                    for (int i = 0; i < pers.length(); i++) {
                        JSONObject c = pers.getJSONObject(i);

                        String cod = c.getString("ccodclie");
                        String ruc = c.getString("crucclie");
                        String nom = c.getString("cnomclie");
                        //SUBITEM CON LAS HABILIDADES
                        /*JSONObject habilidades = c.getJSONObject("Habilidades");
                        String fuerza = habilidades.getString("Fuerza");
                        String espiritu = habilidades.getString("Espiritu");
                        String fortaleza = habilidades.getString("Fortaleza");*/

                        cliente_CLASS e=new cliente_CLASS();
                        e.setCod(cod);
                        e.setRuc(ruc);
                        e.setNombre(nom);
                        // adding contact to contact list
                        lista.add(e);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Esta habiendo problemas para cargar el JSON");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing()){
                pDialog.dismiss();
            }
            new CargarListTask().execute();
        }
        class CargarListTask extends AsyncTask<Void,String,Adapter>{
            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
            }

            protected Adapter doInBackground(Void... arg0) {
                // TODO Auto-generated method stub

                try{

                }catch(Exception ex){
                    ex.printStackTrace();
                }

                Adapter adaptador = new Adapter(a,lista);
                return adaptador;
            }

            @Override
            protected void onPostExecute(Adapter result) {
                // TODO Auto-generated method stub
                super.onPostExecute(result);
                listaa.setAdapter(result);
                listaa.setSelection(contador*20);
                listaa.requestFocus();
                contador++;
            }
        }
    }
}
