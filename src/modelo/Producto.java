/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author admin
 */
public class Producto {

    private int idcategoria, idunidadMedida, cantCaja, cantUnidad, cantDocena, cantProm;
    private String idproducto, nombre;
    private Double precioCaja, precioUnidad, precioDocena, precioProm;
    private Boolean papelera;
    //variable existencia para seleccionar producto 
    private Double existencia ; 

    public Producto() {
    }

    public Double getExistencia() {
        return existencia;
    }

    public void setExistencia(Double existencia) {
        this.existencia = existencia;
    }

    public int getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(int idcategoria) {
        this.idcategoria = idcategoria;
    }

    public int getIdunidadMedida() {
        return idunidadMedida;
    }

    public void setIdunidadMedida(int idunidadMedida) {
        this.idunidadMedida = idunidadMedida;
    }

    public int getCantCaja() {
        return cantCaja;
    }

    public void setCantCaja(int cantCaja) {
        this.cantCaja = cantCaja;
    }

    public int getCantUnidad() {
        return cantUnidad;
    }

    public void setCantUnidad(int cantUnidad) {
        this.cantUnidad = cantUnidad;
    }

    public int getCantDocena() {
        return cantDocena;
    }

    public void setCantDocena(int cantDocena) {
        this.cantDocena = cantDocena;
    }

    public int getCantProm() {
        return cantProm;
    }

    public void setCantProm(int cantProm) {
        this.cantProm = cantProm;
    }

    public String getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(String idproducto) {
        this.idproducto = idproducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecioCaja() {
        return precioCaja;
    }

    public void setPrecioCaja(Double precioCaja) {
        this.precioCaja = precioCaja;
    }

    public Double getPrecioUnidad() {
        return precioUnidad;
    }

    public void setPrecioUnidad(Double precioUnidad) {
        this.precioUnidad = precioUnidad;
    }

    public Double getPrecioDocena() {
        return precioDocena;
    }

    public void setPrecioDocena(Double precioDocena) {
        this.precioDocena = precioDocena;
    }

    public Double getPrecioProm() {
        return precioProm;
    }

    public void setPrecioProm(Double precioProm) {
        this.precioProm = precioProm;
    }

    public Boolean getPapelera() {
        return papelera;
    }

    public void setPapelera(Boolean papelera) {
        this.papelera = papelera;
    }
    
    

}
