package com.example.toshiba_glx.galaxiasw_1;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Adapter extends BaseAdapter{

    protected Activity activity;
    //ARRAYLIST CON TODOS LOS ITEMS
    protected ArrayList<cliente_CLASS> items;

    //CONSTRUCTOR
    public Adapter(Activity activity, ArrayList<cliente_CLASS> items) {
        this.activity = activity;
        this.items = items;
    }
    //CUENTA LOS ELEMENTOS
    @Override
    public int getCount() {
        return items.size();
    }
    //DEVUELVE UN OBJETO DE UNA DETERMINADA POSICION
    @Override
    public Object getItem(int arg0) {
        return items.get(arg0);
    }

    //METODO PRINCIPAL, AQUI SE LLENAN LOS DATOS
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // SE GENERA UN CONVERTVIEW POR MOTIVOS DE EFICIENCIA DE MEMORIA
        //ES UN NIVEL MAS BAJO DE VISTA, PARA QUE OCUPEN MENOS MEMORIA LAS

        View v = convertView;
        //ASOCIAMOS LA VISTA AL LAYOUT DEL RECURSO XML DONDE ESTA LA BASE DE

        if(convertView == null){
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.list_item, null);
        }

        cliente_CLASS dir = items.get(position);
        //RELLENAMOS LA IMAGEN Y EL TEXTO
        //IMAGEN
        /*ImageView img = (ImageView) v.findViewById(R.id.imageView1);
        if(img != null) {
            new LoadImage(img).execute(dir.getURLimagen());
        }*/
        //CAMPOS
        TextView nombre = (TextView) v.findViewById(R.id.username);
        nombre.setText("NOMBRE : "+dir.getNombre());
        TextView clase = (TextView) v.findViewById(R.id.clase);
        clase.setText("CODIGO : "+dir.getCod());
        TextView equipo = (TextView) v.findViewById(R.id.equipo);
        equipo.setText("RUC : "+dir.getRuc());
        //SUBCAMPOS
        /*fuerza.setText("FUERZA : "+dir.getFuerza());
        TextView fortaleza = (TextView) v.findViewById(R.id.fortaleza);
        fortaleza.setText("FORTALEZA : "+dir.getFortaleza());
        TextView espiritu = (TextView) v.findViewById(R.id.espiritu);
        espiritu.setText("ESPIRITU : "+dir.getEspiritu());*/

        // DEVOLVEMOS VISTA
        return v;
    }
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    class LoadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public LoadImage(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                mIcon11 = BitmapFactory.decodeStream((InputStream)new URL(urldisplay).getContent());

            } catch (Exception e) {
                //Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
