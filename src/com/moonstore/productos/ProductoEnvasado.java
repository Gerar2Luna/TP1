package com.moonstore.productos;

import java.time.LocalDate;

import com.moonstore.interfaces.Comestible;
import com.moonstore.productos.enums.TipoEnvase;

public class ProductoEnvasado extends Producto implements Comestible {
    private TipoEnvase tipoEnvase;
    private boolean esImportado;
    private LocalDate fechaDeVencimiento;
    private int calorias;

    public ProductoEnvasado(String identificador, String descripcion, int cantidadEnStock,
                            double costoPorUnidad, boolean disponibleParaVenta,
                            TipoEnvase tipoEnvase, boolean esImportado) {
        super(identificador, descripcion, cantidadEnStock, costoPorUnidad, disponibleParaVenta);
        if(this.validarIdentificador(identificador)){
            this.tipoEnvase = tipoEnvase;
            this.esImportado = esImportado;
        }
        else throw new IllegalArgumentException("El identificador ingresado no es valido para "+ this.getClass().getSimpleName());
    }

    // Getters y setters

    public String getTipoEnvase() {
        return tipoEnvase.toString();
    }
    public boolean isImportado() {
        return esImportado;
    }


    @Override
    public boolean aplicaDescuento(double descuento) {
        if(descuento<20.0 && this.validarGanancia(calcularPrecioVentaConDescuento())){
            return true;
        } else
            return false;
    }

    @Override
    public boolean validarIdentificador(String identificador){
        if(identificador.matches("AB\\d{3}"))
            return true;
        else
            return false;
    }
    
    // Interfaz Comestible

    @Override
    public LocalDate getFechaVencimiento() {
        return this.fechaDeVencimiento;
    }

    @Override
    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaDeVencimiento=fechaVencimiento;
    }
    @Override
    public boolean isDisponibleParaVenta(){
        if (this.getFechaVencimiento().equals(LocalDate.now()))
            return false;
        else
            return true;
    }
    @Override
    public int getCalorias() {
        return this.calorias;
    }

    @Override
    public void setCalorias(int calorias) {
        this.calorias=calorias;
    }

    // Interfaz AplicableDescuento

    @Override
    public double getPorcentajeDescuento() {
        return super.porcentajeDescuento;
    }

    @Override
    public void setPorcentajeDescuento(double porcentajeDescuento) {
        super.porcentajeDescuento=porcentajeDescuento;
    }

    @Override
    public double calcularPrecioVentaConDescuento() {
        double precioConDescuento; // 10
        precioConDescuento = (this.getPrecioPorUnidad()*(1-(getPorcentajeDescuento())/100));
        return precioConDescuento;
    }

    @Override
    public double calcularPrecioDeVenta() {
        if(this.isImportado())
            return getPrecioPorUnidad()*1.10;
        else
            return getPrecioPorUnidad();
    }

    @Override
    public boolean validarGanancia(double precio) {
        double ganancia = precio-getCostoPorUnidad();
        if(ganancia<=(getCostoPorUnidad()*0.20) && ganancia>=0)
            return true;
        else{
            return false;
            }      
    }


}

