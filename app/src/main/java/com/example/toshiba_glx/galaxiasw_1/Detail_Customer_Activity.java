package com.example.toshiba_glx.galaxiasw_1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Detail_Customer_Activity extends AppCompatActivity {
    String clienteID;
    String URL_base = "http://192.168.1.35/galaxiaSW/consulta.php";
    String URL;
    JSONArray pers;
    TextView txtCodigo,txtRuc,txtNombre,txtDireccion
            ,txtTelefono,txtFax,txtCelular,txtRpm,txtLocalidad
            ,txtCredito,txtPagWeb,txtGenero,txtFechaNacimiento;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__customer);

        Intent i = getIntent();
        clienteID = i.getStringExtra("clienteId");

        txtCodigo = (TextView) findViewById(R.id.txtCodigo);
        txtRuc = (TextView) findViewById(R.id.txtRuc);
        txtNombre = (TextView) findViewById(R.id.txtNombre);
        txtDireccion = (TextView) findViewById(R.id.txtDireccion);
        txtTelefono= (TextView) findViewById(R.id.txtTelefono);
        txtFax= (TextView) findViewById(R.id.txtFax);
        txtCelular= (TextView) findViewById(R.id.txtCelular);
        txtRpm= (TextView) findViewById(R.id.txtRpm);
        txtLocalidad= (TextView) findViewById(R.id.txtLocalidad);
        txtCredito= (TextView) findViewById(R.id.txtCredito);
        txtPagWeb= (TextView) findViewById(R.id.txtPagWeb);
        txtGenero= (TextView) findViewById(R.id.txtGenero);
        txtFechaNacimiento= (TextView) findViewById(R.id.txtFechaNacimiento);


        URL = String.format("%s?consulta=%s",URL_base,clienteID);
        new GetCostumer().execute();

    }
    private class GetCostumer extends AsyncTask<Void, Void, Void> {
        private ProgressDialog pDialog;
        public GetCostumer() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Detail_Customer_Activity.this);
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

                        txtCodigo.setText(String.format("Código: %s",c.getString("ccodclie")));
                        txtRuc.setText(String.format("RUC: %s",c.getString("crucclie")));
                        txtNombre.setText(String.format("Nombre: %s",c.getString("cnomclie")));
                        txtDireccion.setText(String.format("Dirección: %s",c.getString("cdirclie")));
                        txtTelefono.setText(String.format("Teléfono: %s",c.getString("cnumtele")));
                        txtFax.setText(String.format("Fax: %s",c.getString("cnumfax_")));
                        txtCelular.setText(String.format("Celular: %s",c.getString("cnumcelu")));
                        txtRpm.setText(String.format("RPM: %s",c.getString("cnumrpm_")));
                        txtLocalidad.setText(String.format("Localidad: %s",c.getString("clocalid")));
                        txtCredito.setText(String.format("Credito: %s",c.getString("nlimcred")));
                        txtPagWeb.setText(String.format("Página Web: %s",c.getString("cpagweb_")));
                        txtGenero.setText(String.format("Género: %s",((c.getString("csexo").equals("M"))?"Varón":"Mujer")));
                        txtFechaNacimiento.setText(String.format("Fecha Nacimiento: %s",c.getString("ffecnaci")));


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
        }
    }
}
