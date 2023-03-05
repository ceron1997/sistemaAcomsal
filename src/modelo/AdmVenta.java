/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author admin
 */
public class AdmVenta {

    String fecha ; 
    int anio , corr, cantidad; 
    String cliente, tipoDoc, serie,formaPago, usuario,nombreProducto ; 
    Double total,precioPorUnidad, totalProducto,costoprom,totalCosto ;
    

    public AdmVenta() {
    }

    public AdmVenta(String fecha, int anio, int corr, int cantidad, String cliente, String tipoDoc, String serie, String formaPago, String usuario, String nombreProducto, Double total, Double precioPorUnidad, Double totalProducto, Double costoprom, Double totalCosto) {
        this.fecha = fecha;
        this.anio = anio;
        this.corr = corr;
        this.cantidad = cantidad;
        this.cliente = cliente;
        this.tipoDoc = tipoDoc;
        this.serie = serie;
        this.formaPago = formaPago;
        this.usuario = usuario;
        this.nombreProducto = nombreProducto;
        this.total = total;
        this.precioPorUnidad = precioPorUnidad;
        this.totalProducto = totalProducto;
        this.costoprom = costoprom;
        this.totalCosto = totalCosto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getCorr() {
        return corr;
    }

    public void setCorr(int corr) {
        this.corr = corr;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getPrecioPorUnidad() {
        return precioPorUnidad;
    }

    public void setPrecioPorUnidad(Double precioPorUnidad) {
        this.precioPorUnidad = precioPorUnidad;
    }

    public Double getTotalProducto() {
        return totalProducto;
    }

    public void setTotalProducto(Double totalProducto) {
        this.totalProducto = totalProducto;
    }

    public Double getCostoprom() {
        return costoprom;
    }

    public void setCostoprom(Double costoprom) {
        this.costoprom = costoprom;
    }

    public Double getTotalCosto() {
        return totalCosto;
    }

    public void setTotalCosto(Double totalCosto) {
        this.totalCosto = totalCosto;
    }

   
}
