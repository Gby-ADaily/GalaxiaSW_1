package com.example.toshiba_glx.galaxiasw_1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by usuario on 6/10/2017.
 */

public class JSONdata  extends AsyncTask<String, Void, String> {
    public AsyncResponse delegate = null;

        Context context;
        String value;
        String tablaName,idComercial;
        boolean  primerosDatos=false;
        private ProgressDialog pDialog;
        String imagenesDownload;

        public JSONdata(Context context) {
            this.context=context;
            this.value = null;
            this.imagenesDownload ="";
        }
        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Cargando datos. Por favor espere...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... strings) {
            try {
                value = downloadUrl();
                final JSONObject obj = new JSONObject(value);
                insertData(obj,tablaName);
                return "";
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            pDialog.dismiss();
            delegate.processFinish("molde");
        }
        private void insertData(JSONObject obj, String tablaName) throws JSONException, ParseException {

            final JSONArray geodata = obj.getJSONArray(tablaName);
            final int n = geodata.length();
            for (int i = 0; i < n; ++i) {
                String columna="",conjColumna="";
                String value ="",conjValue="";
                final JSONObject person = geodata.getJSONObject(i);
                for(int j=0; j < person.names().length() ; ++j){
                    columna = person.names().getString(j);
                    value = person.getString(columna).replace("tb_","TB");
                        conjColumna=conjColumna+columna+";";
                        conjValue=conjValue+value+";";
                    //context.lista_frutas.add(conjValue);
                }
            }
        }
        private static jsonObject downloadUrl() throws IOException{
            String url = String.format("http://192.168.1.35/galaxiaSW/consulta.php");
            InputStream is = null;
            String result = "";
            JSONObject jsonObject = null;

            // HTTP
            try {
                HttpClient httpclient = new DefaultHttpClient(); // for port 80 requests!
                HttpPost httppost = new HttpPost(url);
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
            } catch(Exception e) {
                return null;
            }

            // Read response to string
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"),8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();
            } catch(Exception e) {
                return null;
            }

            // Convert string to object
            try {
                jsonObject = new JSONObject(result);
            } catch(JSONException e) {
                return null;
            }

            return jsonObject;
        }
    }


