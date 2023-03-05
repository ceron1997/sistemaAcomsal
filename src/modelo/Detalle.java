/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author admin
 */
public class Detalle {
    private String serie, idproducto, unidadmedida ;
    private String nombreProd ; // esta variable sirve para el verDetalle en el frmDetalleFactura
     // el anulado y borrado los paso como 0 en DetalleDAO       
    private double precioU, totalProd,cantidad,cantidadimpresa; 
    int nlinea, noDocumento; 

    public Detalle() {
    }

    public Detalle(String serie, String idproducto, String unidadmedida, String nombreProd, double precioU, double totalProd, double cantidad, double cantidadimpresa, int nlinea, int noDocumento) {
        this.serie = serie;
        this.idproducto = idproducto;
        this.unidadmedida = unidadmedida;
        this.nombreProd = nombreProd;
        this.precioU = precioU;
        this.totalProd = totalProd;
        this.cantidad = cantidad;
        this.cantidadimpresa = cantidadimpresa;
        this.nlinea = nlinea;
        this.noDocumento = noDocumento;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(String idproducto) {
        this.idproducto = idproducto;
    }

    public String getUnidadmedida() {
        return unidadmedida;
    }

    public void setUnidadmedida(String unidadmedida) {
        this.unidadmedida = unidadmedida;
    }

    public String getNombreProd() {
        return nombreProd;
    }

    public void setNombreProd(String nombreProd) {
        this.nombreProd = nombreProd;
    }

    public double getPrecioU() {
        return precioU;
    }

    public void setPrecioU(double precioU) {
        this.precioU = precioU;
    }

    public double getTotalProd() {
        return totalProd;
    }

    public void setTotalProd(double totalProd) {
        this.totalProd = totalProd;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getCantidadimpresa() {
        return cantidadimpresa;
    }

    public void setCantidadimpresa(double cantidadimpresa) {
        this.cantidadimpresa = cantidadimpresa;
    }

    public int getNlinea() {
        return nlinea;
    }

    public void setNlinea(int nlinea) {
        this.nlinea = nlinea;
    }

    public int getNoDocumento() {
        return noDocumento;
    }

    public void setNoDocumento(int noDocumento) {
        this.noDocumento = noDocumento;
    }




    
    
    
    
    
}

