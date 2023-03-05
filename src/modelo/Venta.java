/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author admin
 */
public class Venta {

    private String serie, dui, noRegistro, dui_no_reg, nombre_no_reg, fechamodificar ; 
    private int idusuario, idcondicionPago = 1;  //establezco pago de contado como predeterminado  
    private int noDocumento, idtipodoc;
    private Double total, iva, suma;

    public Venta() {
    }

    public Venta(String serie, String dui, String noRegistro, String dui_no_reg, String nombre_no_reg, String fechamodificar, int idusuario, int noDocumento, int idtipodoc, Double total, Double iva, Double suma) {
        this.serie = serie;
        this.dui = dui;
        this.noRegistro = noRegistro;
        this.dui_no_reg = dui_no_reg;
        this.nombre_no_reg = nombre_no_reg;
        this.fechamodificar = fechamodificar;
        this.idusuario = idusuario;
        this.noDocumento = noDocumento;
        this.idtipodoc = idtipodoc;
        this.total = total;
        this.iva = iva;
        this.suma = suma;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }

    public String getNoRegistro() {
        return noRegistro;
    }

    public void setNoRegistro(String noRegistro) {
        this.noRegistro = noRegistro;
    }

    public String getDui_no_reg() {
        return dui_no_reg;
    }

    public void setDui_no_reg(String dui_no_reg) {
        this.dui_no_reg = dui_no_reg;
    }

    public String getNombre_no_reg() {
        return nombre_no_reg;
    }

    public void setNombre_no_reg(String nombre_no_reg) {
        this.nombre_no_reg = nombre_no_reg;
    }

    public String getFechamodificar() {
        return fechamodificar;
    }

    public void setFechamodificar(String fechamodificar) {
        this.fechamodificar = fechamodificar;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public int getIdcondicionPago() {
        return idcondicionPago;
    }

    public void setIdcondicionPago(int idcondicionPago) {
        this.idcondicionPago = idcondicionPago;
    }

    public int getNoDocumento() {
        return noDocumento;
    }

    public void setNoDocumento(int noDocumento) {
        this.noDocumento = noDocumento;
    }

    public int getIdtipodoc() {
        return idtipodoc;
    }

    public void setIdtipodoc(int idtipodoc) {
        this.idtipodoc = idtipodoc;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getIva() {
        return iva;
    }

    public void setIva(Double iva) {
        this.iva = iva;
    }

    public Double getSuma() {
        return suma;
    }

    public void setSuma(Double suma) {
        this.suma = suma;
    }

    
    
}
