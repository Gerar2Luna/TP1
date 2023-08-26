package com.moonstore.productos;

import com.moonstore.productos.enums.TipoAplicacion;

public class ProductoLimpieza extends Producto {
    
    private TipoAplicacion tipoAplicacion;

    public ProductoLimpieza(String identificador, String descripcion, int cantidadEnStock,
                            double costoPorUnidad, boolean disponibleParaVenta,
                            TipoAplicacion tipoAplicacion) {
        super(identificador, descripcion, cantidadEnStock, costoPorUnidad, disponibleParaVenta);
        if (this.validarIdentificador(identificador)){
            this.tipoAplicacion = tipoAplicacion;
        }
        else throw new IllegalArgumentException("El identificador ingresado no es valido para "+ this.getClass().getSimpleName());
    }
    
    
    // Getters y setters
        public TipoAplicacion getTipoAplicacion() {
        return tipoAplicacion;
    }

    @Override
    public double calcularPrecioDeVenta() {
        return getPrecioPorUnidad();
    }
    
    @Override
    public boolean aplicaDescuento(double descuento) {
        if (descuento<25.0 && validarGanancia(calcularPrecioVentaConDescuento())){
            return true;
        } else 
            return false;
    }
    
    @Override
    public boolean validarIdentificador(String identificador){
        if(identificador.matches("AZ\\d{3}"))
            return true;
        else
            return false;
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
        double precioConDescuento;
        precioConDescuento = (this.getPrecioPorUnidad()*getPorcentajeDescuento())/100;
        return precioConDescuento;
    }
    // Otros métodos relevantes


    @Override
    public boolean validarGanancia(double precio) {
        double ganancia = precio - getCostoPorUnidad();
        if(ganancia > (getCostoPorUnidad()*0.25))
            return false;
            else if((this.tipoAplicacion== TipoAplicacion.COCINA || this.tipoAplicacion== TipoAplicacion.PISOS) && ganancia<(getCostoPorUnidad()*0.10)){
                return false;
            }   else{
                    if (ganancia>=0)
                        return true;
                    else
                        return false;
                }
    }
}
