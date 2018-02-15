package com.example.toshiba_glx.galaxiasw_1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Detail_Customer_Activity extends AppCompatActivity {
    String clienteID;
    cliente_CLASS clienteClass;
    String URL;
    JSONArray pers;
    TextView txtCodigo,txtRuc,txtNombre,txtDireccion
            ,txtTelefono,txtFax,txtCelular,txtRpm,txtLocalidad
            ,txtCredito,txtPagWeb,txtPrecio,txtTipoVia,txtNombreVia,txtNumeroVia,
            txtIntVia,txtZonaVia,txtDistritoVia,txtProvinciaVia,txtDepartamentoVia,
            txtEmail,txtIdCliente,txtIdZona,txtComercial,txtSituacion,txtAcumulado,
            txtEdad,txtGenero,txtFechaNacimiento;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__customer);
//llamar la flecha de atras <-    no es necesario ya q en el AndroidManifest esta configurado
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
// If your minSdkVersion is 11 or higher, instead use:
        // getActionBar().setDisplayHomeAsUpEnabled(true);

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
        txtPrecio= (TextView) findViewById(R.id.txtPrecio);
        txtTipoVia= (TextView) findViewById(R.id.txtTipoVia);
        txtNombreVia=(TextView) findViewById(R.id.txtNombreVia);
        txtNumeroVia= (TextView) findViewById(R.id.txtNumeroVia);
        txtIntVia= (TextView) findViewById(R.id.txtIntVia);
        txtZonaVia= (TextView) findViewById(R.id.txtZonaVia);
        txtDistritoVia= (TextView) findViewById(R.id.txtDistritoVia);
        txtProvinciaVia= (TextView) findViewById(R.id.txtProvinciaVia);
        txtDepartamentoVia= (TextView) findViewById(R.id.txtDepartamentoVia);
        txtEmail= (TextView) findViewById(R.id.txtEmail);
        txtIdCliente= (TextView) findViewById(R.id.txtIdCliente);
        txtIdZona= (TextView) findViewById(R.id.txtIdZona);
        txtComercial= (TextView) findViewById(R.id.txtComercial);
        txtSituacion= (TextView) findViewById(R.id.txtSituacion);
        txtAcumulado= (TextView) findViewById(R.id.txtAcumulado);
        txtEdad= (TextView) findViewById(R.id.txtEdad);
        txtGenero= (TextView) findViewById(R.id.txtGenero);
        txtFechaNacimiento= (TextView) findViewById(R.id.txtFechaNacimiento);

        clienteClass= new cliente_CLASS(this);
        clienteClass.setCod(clienteID);

        URL = String.format("%s?consulta=%s",getString(R.string.url),clienteID);
        new GetCostumer().execute();

    }
    private void insertarDato(){
        txtCodigo.setText(String.format("Código: %s",clienteClass.getCod()));
        txtRuc.setText(String.format("RUC: %s",clienteClass.getRuc()));
        txtNombre.setText(String.format("Nombre: %s",clienteClass.getNombre()));
        txtDireccion.setText(String.format("Dirección: %s",clienteClass.getDireccion()));
        txtTelefono.setText(String.format("Teléfono: %s",clienteClass.getNumTele()));
        txtFax.setText(String.format("Fax: %s",clienteClass.getFax()));
        txtCelular.setText(String.format("Celular: %s",clienteClass.getCelular()));
        txtRpm.setText(String.format("RPM: %s",clienteClass.getRPM()));
        txtLocalidad.setText(String.format("Localidad: %s",clienteClass.getLocalidad()));
        txtCredito.setText(String.format("Credito: %s",clienteClass.getLimcred()));
        txtPagWeb.setText(String.format("Página Web: %s",clienteClass.getPagWeb()));
        txtPrecio.setText(String.format("Precio: %s",clienteClass.getLisprec()));
        txtTipoVia.setText(String.format("Tipo Via: %s",clienteClass.getTipvia()));
        txtNombreVia.setText(String.format("Nombre Via: %s",clienteClass.getNomvia()));
        txtNumeroVia.setText(String.format("Número Via: %s",clienteClass.getNumvia()));
        txtIntVia.setText(String.format("Int Via: %s",clienteClass.getIntvia()));
        txtZonaVia.setText(String.format("Zona Via: %s",clienteClass.getZonvia()));
        txtDistritoVia.setText(String.format("Distrito Via: %s",clienteClass.getDisvia()));
        txtProvinciaVia.setText(String.format("Provincia Via: %s",clienteClass.getProvia()));
        txtDepartamentoVia.setText(String.format("Departamento Via: %s",clienteClass.getDepvia()));
        txtEmail.setText(String.format("E-mail: %s",clienteClass.getEmail()));
        txtIdCliente.setText(String.format("ID zona: %s",clienteClass.getIDclient()));
        txtIdZona.setText(String.format("ID zona: %s",clienteClass.getIDzona()));
        txtComercial.setText(String.format("Comercial: %s",clienteClass.getComercial()));
        txtSituacion.setText(String.format("Situación: %s",clienteClass.getSituacion()));
        txtAcumulado.setText(String.format("Acumulados: %s",clienteClass.getAcumulado()));
        txtEdad.setText(String.format("Edad: %s",clienteClass.getEdad()));
        txtGenero.setText(String.format("Género: %s",((clienteClass.getgenero().equals("M"))?"Varón":"Mujer")));
        txtFechaNacimiento.setText(String.format("Fecha Nacimiento: %s",clienteClass.getNacimiento()));

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
            JSONdata sh = new JSONdata(Detail_Customer_Activity.this);

            String jsonStr = sh.makeServiceCall(URL, JSONdata.GET);


            if (jsonStr != null) {
                try {
                    clienteClass.getDataJSON(new JSONObject(jsonStr));
                    insertarDato();
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

    // parte de ACTION BAR
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ma_activity_detail_customer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_edit) {
            Toast.makeText(this, "editar", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Detail_Customer_Activity.this, Edit_Customer_Activity.class);
            intent.putExtra("clienteId", clienteID);
            startActivity(intent);
            return true;
        }else if(id == R.id.action_delete) {
            Toast.makeText(this, "eliminar", Toast.LENGTH_SHORT).show();
            return true;
        }else if(id == R.id.action_settings) {
            Toast.makeText(this, "ajustes", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
