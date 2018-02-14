package com.example.toshiba_glx.galaxiasw_1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.app.DatePickerDialog;;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Edit_Customer_Activity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener {
    String clienteID;
    String URL;
    JSONArray pers;
    EditText txtRuc,txtNombre,txtDireccion
            ,txtTelefono,txtFax,txtCelular,txtRpm,txtLocalidad
            ,txtCredito,txtPagWeb;
    TextView txtCodigo;

    private static final String CERO = "0";
    private static final String BARRA = "/";
    //Calendario para obtener fecha & hora
    public final Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    int mes = cal.get(Calendar.MONTH);
    int dia = cal.get(Calendar.DAY_OF_MONTH);
    int anio = cal.get(Calendar.YEAR);
    //Widgets

    TextView etFecha;
    ImageButton ibObtenerFecha;
    Spinner spnGenero;

    //cliente_CLASS cliente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__customer);
        // agrega la flecha de back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //cliente= new cliente_CLASS(this);
        Intent i = getIntent();
        clienteID = i.getStringExtra("clienteId");
        //cliente.setCod(clienteID);
        spnGenero=(Spinner) findViewById(R.id.spnGenero);
        etFecha = (TextView) findViewById(R.id.txtNacimiento);
        ibObtenerFecha = (ImageButton) findViewById(R.id.btnPickerDate);
        //--
        txtCodigo = (TextView) findViewById(R.id.txtCodigo);
        txtRuc = (EditText) findViewById(R.id.txtRuc);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtDireccion = (EditText) findViewById(R.id.txtDireccion);
        txtTelefono= (EditText) findViewById(R.id.txtTelefono);
        txtFax= (EditText) findViewById(R.id.txtFax);
        txtCelular= (EditText) findViewById(R.id.txtCelular);
        txtRpm= (EditText) findViewById(R.id.txtRpm);
        txtLocalidad= (EditText) findViewById(R.id.txtLocalidad);
        txtCredito= (EditText) findViewById(R.id.txtCredito);
        txtPagWeb= (EditText) findViewById(R.id.txtPagWeb);

        spnGenero.setOnItemSelectedListener(this);
        ibObtenerFecha.setOnClickListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.genero_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnGenero.setAdapter(adapter);

        URL = String.format("%s?consulta=%s",getString(R.string.url),clienteID);
        new GetCostumer(true).execute();
        //cliente.setURL(String.format("%s?consulta=%s",getString(R.string.url),clienteID));
        //cliente.setConsulta(true);
        //cliente.execute();

    }
    private class GetCostumer extends AsyncTask<Void, Void, Void> {
        private ProgressDialog pDialog;
        boolean consulta=false;
        public GetCostumer(boolean consulta) {this.consulta=consulta;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Edit_Customer_Activity.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // CREAMOS LA INSTANCIA DE LA CLASE
            JSONdata sh = new JSONdata(Edit_Customer_Activity.this);

            String jsonStr = sh.makeServiceCall(URL, JSONdata.GET);


            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                        if( consulta){
                    pers = jsonObj.getJSONArray("gx_cliente");
    for (int i = 0; i < pers.length(); i++) {
        JSONObject c = pers.getJSONObject(i);

        txtCodigo.setText(String.format("Código: %s",c.getString("ccodclie")));
        txtRuc.setText(c.getString("crucclie"));
        txtNombre.setText(c.getString("cnomclie"));
        txtDireccion.setText(c.getString("cdirclie"));
        txtTelefono.setText(c.getString("cnumtele"));
        txtFax.setText(c.getString("cnumfax_"));
        txtCelular.setText(c.getString("cnumcelu"));
        txtRpm.setText(c.getString("cnumrpm_"));
        txtLocalidad.setText(c.getString("clocalid"));
        txtCredito.setText(c.getString("nlimcred"));
        txtPagWeb.setText(c.getString("cpagweb_"));
        spnGenero.setSelection(((c.getString("csexo").equals("M"))?0:1));
        String fNacimiento = c.getString("ffecnaci").replace("-",BARRA); // dd-mm-yy
        cal.setTime(sdf.parse(fNacimiento));
        mes = cal.get(Calendar.MONTH);
        dia = cal.get(Calendar.DAY_OF_MONTH)+1 ;
        anio = cal.get(Calendar.YEAR);
        etFecha.setText(dia + BARRA + mes + BARRA + anio);
    }
}else if( !consulta){
                            pers = jsonObj.getJSONArray("estado");
                            for (int i = 0; i < pers.length(); i++) {
                                JSONObject c = pers.getJSONObject(i);
                                if(c.getString("actualizar").equals("exito")){
                                    //Toast.makeText(Edit_Customer_Activity.this,"Se guardaron los cambios",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Edit_Customer_Activity.this,  Detail_Customer_Activity.class);
                                    intent.putExtra("clienteId", clienteID);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(Edit_Customer_Activity.this,"No se guardaron los cambio",Toast.LENGTH_SHORT).show();
                                }

                                }
                        }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnPickerDate:
                obtenerFecha();
                break;
        }}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ma_activity_edit_customer, menu);
        return true;
    }
            @Override
    public boolean onOptionsItemSelected(MenuItem item) {

       switch(item.getItemId()){
           case R.id.action_done:
               URL = String.format("%s?actualizar=%s&ruc=%s&id=%s",getString(R.string.url),"A",txtRuc.getText().toString(),clienteID);
               new GetCostumer(false).execute();
               break;
           default:
               Toast.makeText(this, "atras:"+item.getItemId()
                       , Toast.LENGTH_SHORT).show();
               onBackPressed();
               break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
        switch(adapterView.getId()){
            case R.id.spnGenero:
                Toast.makeText(this, "Item: " + adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void obtenerFecha(){
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                final int mesActual = month + 1;
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                etFecha.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);
                anio = year;
                mes = month;
                dia=dayOfMonth;
            }
        },anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();
    }
}
