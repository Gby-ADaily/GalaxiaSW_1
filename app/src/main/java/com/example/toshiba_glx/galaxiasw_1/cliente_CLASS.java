package com.example.toshiba_glx.galaxiasw_1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Calendar;

/**
 * Created by toshiba_glx on 8/02/2018.
 */

public class cliente_CLASS  {
    private String ccodClie, crucClie, cnomClie,cdirclie,cnumtele,cnumfax_,cnumcelu,
            cnumrpm_,clocalid,nlimcred,cpagweb_,nlisprec,ctipvia_,cnomvia_,cnumvia_,
            cintvia_,czonvia_,cdisvia_,cprovia_,cdepvia_,cemail__,idclient,idzona,
            comercial,situacion,acumulados,cedad,csexo,ffecnaci;

    private ProgressDialog pDialog;
    boolean consulta=false;
    Context ctx;
    String URL;
    JSONArray pers;
    Class clase;
    public cliente_CLASS(Context context) {
        this.ctx = context;
    }
    public cliente_CLASS(Context context,Class clase) {
        this.ctx = context;
        //this.clase= new clase();
    }
    public void setURL(String url) {
        this.URL = url;
    }
    public void setConsulta(boolean consulta) {
        this.consulta=consulta;
    }
    public String getCod() {return ccodClie;}
    public void setCod(String ccodClie) {this.ccodClie = ccodClie;}
    public String getRuc() {
        return crucClie;
    }
    public void setRuc(String crucClie) {
        this.crucClie = crucClie;
    }
    public String getNombre() {
        return cnomClie;
    }
    public void setNombre(String nombre) {
        this.cnomClie = nombre;
    }
    public String getDireccion() {
        return cdirclie;
    }
    public void setDireccion(String cdirclie) {
        this.cdirclie = cdirclie;
    }
    public String getNumTele() {
        return cnumtele;
    }
    public void setNumTele(String cnumtele) {
        this.cnumtele = cnumtele;
    }
    public String getFax() {
        return cnumfax_;
    }
    public void setFax(String cnumfax_) {
        this.cnumfax_ = cnumfax_;
    }
    public String getCelular() {
        return cnumcelu;
    }
    public void setCelular(String cnumcelu) {
        this.cnumcelu = cnumcelu;
    }
    public String getRPM() {
        return cnumrpm_;
    }
    public void setRPM(String cnumrpm_) {
        this.cnumrpm_ = cnumrpm_;
    }
    public String getLocalidad() {
        return clocalid;
    }
    public void setLocalidad(String clocalid) {
        this.clocalid = clocalid;
    }
    public String getLimcred() {
        return nlimcred;
    }
    public void setLimcred(String nlimcred) {
        this.nlimcred = nlimcred;
    }
    public String getPagWeb() {
        return cpagweb_;
    }
    public void setPagWeb(String cpagweb_) {
        this.cpagweb_ = cpagweb_;
    }
    public String getgenero() {
        return csexo;
    }
    public void setGenero(String csexo) {
        this.csexo = csexo;
    }
    public String getLisprec() {
        return nlisprec;
    }
    public void setLisprec(String nlisprec) {
        this.nlisprec = nlisprec;
    }
    public String getTipvia() {
        return ctipvia_;
    }
    public void setTipia(String ctipvia_) {
        this.ctipvia_ = ctipvia_;
    }
    public String getNomvia() {
        return cnomvia_;
    }
    public void setNomvia(String cnomvia_) {
        this.cnomvia_ = cnomvia_;
    }
    public String getNumvia() {
        return cnumvia_;
    }
    public void setNumvia(String cnumvia_) {
        this.cnumvia_ = cnumvia_;
    }
    public String getIntvia() {
        return cintvia_;
    }
    public void setIntvia(String cintvia_) {
        this.cintvia_ = cintvia_;
    }
    public String getZonvia() {
        return czonvia_;
    }
    public void setZonvia(String czonvia_) {
        this.czonvia_ = czonvia_;
    }
    public String getDisvia() {
        return cdisvia_;
    }
    public void setDisvia(String cdisvia_) {
        this.cdisvia_ = cdisvia_;
    }
    public String getProvia() {return cprovia_;}
    public void setProvia(String cprovia_) {
        this.cprovia_ = cprovia_;
    }
    public String getDepvia() {
        return cdepvia_;
    }
    public void setDepvia(String cdepvia_) {
        this.cdepvia_ = cdepvia_;
    }
    public String getEmail() {
        return cemail__;
    }
    public void setEmail(String cemail__) {
        this.cemail__ = cemail__;
    }
    public String getIDclient() {
        return idclient;
    }
    public void setIDclient(String idclient) {
        this.idclient = idclient;
    }
    public String getIDzona() {
        return idzona;
    }
    public void setIDzona(String idzona) {
        this.idzona = idzona;
    }
    public String getComercial() {
        return comercial;
    }
    public void setComercial(String comercial) {
        this.comercial = comercial;
    }
    public String getSituacion() {return situacion; }
    public void setSituacion(String situacion) {
        this.situacion = situacion;
    }
    public String getAcumulado() {
        return acumulados;
    }
    public void setAcumulado(String acumulados) {
        this.acumulados = acumulados;
    }
    public String getEdad() {
        return cedad;
    }
    public void setEdad(String cedad) {
        this.cedad = cedad;
    }
    public String getNacimiento() {
        return ffecnaci;
    }
    public void setNacimiento(String ffecnaci) {
        this.ffecnaci = ffecnaci;
    }
    public void getDataJSON(JSONObject jsonObj) throws JSONException {
            pers = jsonObj.getJSONArray("gx_cliente");
            for (int i = 0; i < pers.length(); i++) {
                JSONObject c = pers.getJSONObject(i);

                setCod(c.getString("ccodclie"));
                setRuc(c.getString("crucclie"));
                setNombre(c.getString("cnomclie"));
                setDireccion(c.getString("cdirclie"));
                setNumTele(c.getString("cnumtele"));
                setFax(c.getString("cnumfax_"));
                setCelular(c.getString("cnumcelu"));
                setRPM(c.getString("cnumrpm_"));
                setLocalidad(c.getString("clocalid"));
                setLimcred(c.getString("nlimcred"));
                setPagWeb(c.getString("cpagweb_"));
                setLisprec(c.getString("nlisprec"));
                setTipia(c.getString("ctipvia_"));
                setNomvia(c.getString("cnomvia_"));
                setNumvia(c.getString("cnumvia_"));
                setIntvia(c.getString("cintvia_"));
                setZonvia(c.getString("czonvia_"));
                setDisvia(c.getString("cdisvia_"));
                setProvia(c.getString("cprovia_"));
                setDepvia(c.getString("cdepvia_"));
                setEmail(c.getString("cemail__"));
                setIDclient(c.getString("idclient"));
                setIDzona(c.getString("idzona"));
                setComercial(c.getString("comercial"));
                setSituacion(c.getString("situacion"));
                setAcumulado(c.getString("acumulados"));
                setEdad(c.getString("cedad"));
                setGenero(c.getString("csexo"));
                setNacimiento(c.getString("ffecnaci").replace("-","/")); // dd-mm-yy

            }
        }

}
