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
    EditText txtRuc,txtNombre,txtDireccion,txtTelefono,txtFax,txtCelular,
            txtRpm,txtLocalidad,txtCredito,txtPagWeb,txtPrecio,txtTipoVia,
            txtNumeroVia,txtIntVia,txtZonaVia,txtDistritoVia,txtProvinciaVia,
            txtDepartamentoVia,txtEmail,txtIdCliente,txtIdZona,txtComercial,
            txtSituacion,txtAcumulado,txtEdad,txtNombreVia;
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

    TextView Nacimiento;
    ImageButton ibObtenerFecha;
    Spinner spnGenero;

    cliente_CLASS clienteClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__customer);
        // agrega la flecha de back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        clienteID = i.getStringExtra("clienteId");

        spnGenero=(Spinner) findViewById(R.id.spnGenero);
        Nacimiento = (TextView) findViewById(R.id.txtNacimiento);
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
        txtPrecio= (EditText) findViewById(R.id.txtPrecio);
        txtTipoVia= (EditText) findViewById(R.id.txtTipoVia);
        txtNumeroVia= (EditText) findViewById(R.id.txtNumeroVia);
        txtNombreVia= (EditText) findViewById(R.id.txtNombreVia);
        txtIntVia= (EditText) findViewById(R.id.txtIntVia);
        txtZonaVia= (EditText) findViewById(R.id.txtZonaVia);
        txtDistritoVia= (EditText) findViewById(R.id.txtDistritoVia);
        txtProvinciaVia= (EditText) findViewById(R.id.txtProvinciaVia);
        txtDepartamentoVia= (EditText) findViewById(R.id.txtDepartamentoVia);
        txtEmail= (EditText) findViewById(R.id.txtEmail);
        txtIdCliente= (EditText) findViewById(R.id.txtIdCliente);
        txtIdZona= (EditText) findViewById(R.id.txtIdZona);
        txtComercial= (EditText) findViewById(R.id.txtComercial);
        txtSituacion= (EditText) findViewById(R.id.txtSituacion);
        txtAcumulado= (EditText) findViewById(R.id.txtAcumulado);
        txtEdad= (EditText) findViewById(R.id.txtEdad);

        clienteClass= new cliente_CLASS(this);
        clienteClass.setCod(clienteID);

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
    private class GetCostumer extends AsyncTask<String, String, String> {
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
        protected String doInBackground(String... arg0) {
            // CREAMOS LA INSTANCIA DE LA CLASE
            JSONdata sh = new JSONdata(Edit_Customer_Activity.this);

            String jsonStr = sh.makeServiceCall(URL, JSONdata.GET);


            if (jsonStr != null) {
                try {
                        if( consulta){
                            clienteClass.getDataJSON(new JSONObject(jsonStr));
                            insertarDato();
                        }else if( !consulta){
                            JSONObject jsonObj = new JSONObject(jsonStr);
                            pers = jsonObj.getJSONArray("estado");
                            for (int i = 0; i < pers.length(); i++) {
                                JSONObject c = pers.getJSONObject(i);
                                return c.getString("actualizar");
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
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing()){
                pDialog.dismiss();
            }
            if(result != null){
                if(result.equals("exito")){
                    Toast.makeText(Edit_Customer_Activity.this,"Se actualizo con Exito",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Edit_Customer_Activity.this, Detail_Customer_Activity.class);
                    intent.putExtra("clienteId", clienteID);
                    startActivity(intent);
                }else if(result.equals("error")){
                    Toast.makeText(Edit_Customer_Activity.this,"No se actualizo, verifique los datos",Toast.LENGTH_SHORT).show();
                }
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
               guardarDato();
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
                //Toast.makeText(this, "Item: " + adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void insertarDato() throws ParseException {
        txtCodigo.setText(clienteClass.getCod());
        txtRuc.setText(clienteClass.getRuc());
        txtNombre.setText(clienteClass.getNombre());
        txtDireccion.setText(clienteClass.getDireccion());
        txtTelefono.setText(clienteClass.getNumTele());
        txtFax.setText(clienteClass.getFax());
        txtCelular.setText(clienteClass.getCelular());
        txtRpm.setText(clienteClass.getRPM());
        txtLocalidad.setText(clienteClass.getLocalidad());
        txtCredito.setText(clienteClass.getLimcred());
        txtPagWeb.setText(clienteClass.getPagWeb());
        txtPrecio.setText(clienteClass.getLisprec());
        txtTipoVia.setText(clienteClass.getTipvia());
        txtNombreVia.setText(clienteClass.getNomvia());
        txtNumeroVia.setText(clienteClass.getNumvia());
        txtIntVia.setText(clienteClass.getIntvia());
        txtZonaVia.setText(clienteClass.getZonvia());
        txtDistritoVia.setText(clienteClass.getDisvia());
        txtProvinciaVia.setText(clienteClass.getProvia());
        txtDepartamentoVia.setText(clienteClass.getDepvia());
        txtEmail.setText(clienteClass.getEmail());
        txtIdCliente.setText(clienteClass.getIDclient());
        txtIdZona.setText(clienteClass.getIDzona());
        txtComercial.setText(clienteClass.getComercial());
        txtSituacion.setText(clienteClass.getSituacion());
        txtAcumulado.setText(clienteClass.getAcumulado());
        txtEdad.setText(clienteClass.getEdad());
        spnGenero.setSelection(((clienteClass.getgenero().equals("M"))?0:1)); // M - F

        String fNacimiento = clienteClass.getNacimiento().replace("-",BARRA); // dd-mm-yy
        cal.setTime(sdf.parse(fNacimiento));
        mes = cal.get(Calendar.MONTH)+1;
        dia = cal.get(Calendar.DAY_OF_MONTH);
        anio = cal.get(Calendar.YEAR);
        String diaFormateado = (dia < 10)? CERO + String.valueOf(dia):String.valueOf(dia);
        String mesFormateado = (mes < 10)? CERO + String.valueOf(mes):String.valueOf(mes);

        Nacimiento.setText(anio + BARRA + mesFormateado + BARRA + diaFormateado);

    }
    private void guardarDato(){
        String ruc=txtRuc.getText().toString(),nombre=txtNombre.getText().toString(),
                direccion=txtDireccion.getText().toString(),telefono=txtTelefono.getText().toString(),
        fax=txtFax.getText().toString(),celular=txtCelular.getText().toString(),rpm=txtRpm.getText().toString(),
        localidad=txtLocalidad.getText().toString(),credito=txtCredito.getText().toString(),
        pagWeb=txtPagWeb.getText().toString(),precio=txtPrecio.getText().toString(),
        tipoVia=txtTipoVia.getText().toString(),nomVia=txtNombreVia.getText().toString(), 
        numVia=txtNumeroVia.getText().toString(), intVia=txtIntVia.getText().toString(),
                zonVia=txtZonaVia.getText().toString(),disVia=txtDistritoVia.getText().toString(),
                proVia=txtProvinciaVia.getText().toString(),depVia=txtDepartamentoVia.getText().toString(),
        email=txtEmail.getText().toString(),IDclient=txtIdCliente.getText().toString(),
        IDzona=txtIdZona.getText().toString(),comercial=txtComercial.getText().toString(),
        situacion=txtSituacion.getText().toString(),acumulado=txtAcumulado.getText().toString(),
        edad=txtEdad.getText().toString(),sexo=((spnGenero.getSelectedItemPosition())==0)?"M":"F",
        nacimiento=Nacimiento.getText().toString();
        /*http://localhost/galaxiaSW/consulta.php?
        actualizar=7&id=67431&ruc=00000007&nombre=RENE%20BARDALES%20AHUANAR&
        direccion=direccion&telefono=67431&fax=4444&celular=67431&rpm=00000007&
        localidad=localidad1&credito=0.10&pagWeb=www.google.com.pe&precio=2.00&
        tipoVia=1&nomVia=via1&numVia=56&intVia=807&zonVia=2&disVia=distrito1&
        proVia=provincia1&depVia=departamento1&email=email@hotmail.com&IDclient=07&
        IDzona=02&comercial=comercial1&situacion=N&acumulado=1&edad=45&sexo=M&
        nacimiento=1915-05-26*/
        nombre=nombre.replace(" ","%20").toUpperCase();
        nacimiento=nacimiento.replace(BARRA,"-");
        URL = String.format("%s?actualizar=%s&id=%s&ruc=%s&nombre=%s&" +
                "direccion=%s&telefono=%s&fax=%s&celular=%s&rpm=%s&" +
                "localidad=%s&credito=%s&pagWeb=%s&precio=%s&" +
                "tipoVia=%s&nomVia=%s&numVia=%s&intVia=%s&zonVia=%s&disVia=%s&" +
                "proVia=%s&depVia=%s&email=%s&IDclient=%s&" +
                "IDzona=%s&comercial=%s&situacion=%s&acumulado=%s&edad=%s&sexo=%s&" +
                "nacimiento=%s",
                getString(R.string.url),"A",clienteID,ruc,nombre,
                direccion,telefono,fax,celular,rpm,localidad,credito,pagWeb,precio,tipoVia,nomVia,
                numVia,intVia,zonVia,disVia,proVia,depVia,email,IDclient,IDzona,comercial,situacion,
                acumulado,edad,sexo,nacimiento);
        new GetCostumer(false).execute();
    }
    private void obtenerFecha(){
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                final int mesActual = month + 1;
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                Nacimiento.setText(year + BARRA + mesFormateado + BARRA + diaFormateado);
                anio = year;
                mes = month;
                dia=dayOfMonth;
            }
        },anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();
    }
}
