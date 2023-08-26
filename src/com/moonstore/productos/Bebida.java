package com.moonstore.productos;
import java.time.LocalDate;
import com.moonstore.interfaces.*;

public class Bebida extends Producto implements Comestible {
    private boolean esAlcoholica;
    private double graduacionAlcoholica;
    private boolean esImportado;
    private LocalDate fechaDeVencimiento;
    private int calorias;

    public Bebida(String identificador, String descripcion, int cantidadEnStock,
                  double costoPorUnidad, boolean disponibleParaVenta,
                  boolean esAlcoholica, double graduacionAlcoholica, boolean esImportado) {
        super(identificador, descripcion, cantidadEnStock, costoPorUnidad, disponibleParaVenta);
        if(this.validarIdentificador(identificador)){
            this.esAlcoholica = esAlcoholica;
            this.graduacionAlcoholica = graduacionAlcoholica;
            this.esImportado = esImportado;
        }
        else throw new IllegalArgumentException("El identificador ingresado no es valido para "+ this.getClass().getSimpleName());
    }

    // Getters y setters
    @Override
    public boolean isDisponibleParaVenta(){
        if (this.getFechaVencimiento().equals(LocalDate.now()))
            return false;
        else
            return true;
    }
    public boolean isAlcoholica() {
        return esAlcoholica;
    }
    public double getGraduacionAlcoholica() {
        return graduacionAlcoholica;
    }
    public boolean isImportado() {
        return esImportado;
    }
    @Override
    public double calcularPrecioDeVenta() {
        if(this.isImportado())
            return getPrecioPorUnidad()*1.10;
        else
            return getPrecioPorUnidad();
    }

    @Override
    public boolean aplicaDescuento(double descuento) {
        if(descuento<25.0 && this.validarGanancia(calcularPrecioVentaConDescuento())) {
            return true;
        } else{
            return false;
        }
    }

    @Override
    public boolean validarIdentificador(String identificador){
        if(identificador.matches("AC\\d{3}"))
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
    public int getCalorias() {
        return this.calorias;
    }

    @Override
    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaDeVencimiento=fechaVencimiento;
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
        double precioConDescuento;
        if (!this.isImportado()) // Se verifica si la bebida es importada
        precioConDescuento = (this.getPrecioPorUnidad()*getPorcentajeDescuento())/100;
        else
        precioConDescuento = ((this.getPrecioPorUnidad()*1.10)*getPorcentajeDescuento())/100; // Se le agrega el 10% de impuesto
        return precioConDescuento;
    }

    @Override
    public boolean validarGanancia(double precio) {
        double ganancia = precio-getCostoPorUnidad();
        if(ganancia<=(getCostoPorUnidad()*0.20) && ganancia>=0)
            return true;
        else
            return false;
    }

}