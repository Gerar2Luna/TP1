package com.moonstore.productos;

import com.moonstore.interfaces.AplicableDescuento;

// TODO Resuelta la validacion de los identificadores

public abstract class Producto implements AplicableDescuento {
    private String identificador;
    private String descripcion;
    private int cantidadEnStock;
    private double precioPorUnidad;
    private double costoPorUnidad;
    private boolean disponibleParaVenta;
    protected double porcentajeDescuento;

    // Constructor
    public Producto(String identificador, String descripcion, int cantidadEnStock,
                    double costoPorUnidad, boolean disponibleParaVenta) {
        this.identificador = identificador;
        this.descripcion = descripcion;
        this.cantidadEnStock = cantidadEnStock;
        this.costoPorUnidad = costoPorUnidad;
        this.disponibleParaVenta = disponibleParaVenta;
        this.porcentajeDescuento = 0;
    }

    // Getters
    public String getIdentificador() {
        return identificador;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public int getCantidadEnStock() {
        return cantidadEnStock;
    }
    public double getPrecioPorUnidad() {
        return precioPorUnidad;
    }
    public double getCostoPorUnidad() {
        return costoPorUnidad;
    }
    public boolean isDisponibleParaVenta() {
        return disponibleParaVenta;
    }


    // Setters
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public void setCantidadEnStock(int cantidadEnStock) {
        this.cantidadEnStock = cantidadEnStock;
    }
    public void setPrecioPorUnidad(double precioPorUnidad) {
        this.precioPorUnidad = precioPorUnidad;
    }
    public void setCostoPorUnidad(double costoPorUnidad) {
        this.costoPorUnidad = costoPorUnidad;
    }
    public void setDisponibleParaVenta(boolean disponibleParaVenta) {
        this.disponibleParaVenta = disponibleParaVenta;
    }

    public double calcularGanancia(){
        return (this.getPrecioPorUnidad()-this.getCostoPorUnidad());
    }

    public abstract boolean validarGanancia(double precio);

    public abstract double calcularPrecioDeVenta();

    public abstract boolean aplicaDescuento(double descuento);

    public abstract boolean validarIdentificador(String identificador);
    
}

