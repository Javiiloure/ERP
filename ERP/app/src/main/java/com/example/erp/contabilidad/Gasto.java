package com.example.erp.contabilidad;

public class Gasto {

    int id;
    String fecha;
    String concepto;
    double importe;

    public Gasto(int id, String fecha, String concepto, double importe) {
        this.id = id;
        this.fecha = fecha;
        this.concepto = concepto;
        this.importe = importe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }
}
