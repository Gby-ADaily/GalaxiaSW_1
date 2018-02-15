package com.example.toshiba_glx.galaxiasw_1;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by usuario on 6/10/2017.
 */

public class JSONdata   {
    static String response = null;
    public final static int GET = 1;
    public final static int POST = 2;
    Context ctx;
    //CONSTRUCTOR
    public JSONdata(Context ctx) {
        this.ctx=ctx;
    }
    public String getJSON(int contador){
       String URL= String.format("%s?contador=%d",ctx.getString(R.string.url),contador);
        return makeServiceCall(URL,GET);
    }
    public String makeServiceCall(String url, int method) {
        return this.makeServiceCall(url, method, null);
    }
    //METODO PARA ESTABLECER CONEXIÓN
    public String makeServiceCall(String url, int method,List<NameValuePair> params) {
        try {
            //HTTP CLIENT
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;

            // AÑADIMOS PARAMETROS AL METODO POST
            if (method == POST) {
                HttpPost httpPost = new HttpPost(url);
                // adding post params
                if (params != null) {
                    httpPost.setEntity(new UrlEncodedFormEntity(params));
                }
                httpResponse = httpClient.execute(httpPost);
            } else if (method == GET) {
                // AÑADIMOS PARAMETROS AL METODO GET
                if (params != null) {
                    String paramString = URLEncodedUtils.format(params, "utf-8");
                    url += "?" + paramString;
                }
                //METODO GET
                HttpGet httpGet = new HttpGet(url);
                httpResponse = httpClient.execute(httpGet);

            }
            httpEntity = httpResponse.getEntity();
            response = EntityUtils.toString(httpEntity);
            //EXCEPCIONES
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //DEVOLVEMOS RESPUESTA
        return response;
    }

    public JSONObject makeServiceCall(String myurl) throws IOException {
        Log.i("URL",""+myurl);
        myurl = myurl.replace(" ","%20");
        InputStream is = null;
        JSONObject jObj = null;
        String json = "";

        int len = 500;
        try{
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();

            int response = conn.getResponseCode();
            Log.d("Respuesta","The resopnse is: "+response);
            is = conn.getInputStream();

            /*String contentAsString = readIt(is,len);
            return contentAsString;*/

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                json = sb.toString();
            } catch (Exception e) {
                Log.e("Buffer Error", "Error converting result " + e.toString());
            }

            // try parse the string to a JSON object
            try {
                jObj = new JSONObject(json);
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }

            // return JSON String
            return jObj;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null){
                is.close();
            }
        }
        return jObj;
    }



}


