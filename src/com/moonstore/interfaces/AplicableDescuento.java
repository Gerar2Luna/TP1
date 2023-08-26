package com.moonstore.interfaces;

public interface AplicableDescuento {
    double getPorcentajeDescuento();
    void setPorcentajeDescuento(double porcentajeDescuento);
    double calcularPrecioVentaConDescuento();
}