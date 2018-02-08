package com.example.toshiba_glx.galaxiasw_1;

/**
 * Created by toshiba_glx on 8/02/2018.
 */

public class cliente_CLASS {
    private String ccodClie;
    private String crucClie;
    private String cnomClie;

    public cliente_CLASS() {
        super();
    }

    public String getCod() {
        return ccodClie;
    }
    public void setCod(String ccodClie) {
        this.ccodClie = ccodClie;
    }
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

}
