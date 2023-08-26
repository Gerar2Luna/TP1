package com.moonstore.tienda;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Comparator;
import java.util.stream.Collectors;

import com.moonstore.productos.Bebida;
import com.moonstore.productos.Producto;
import com.moonstore.productos.ProductoEnvasado;
import com.moonstore.productos.ProductoLimpieza;
import com.moonstore.productos.enums.TipoAplicacion;


public class Tienda {
    private String nombre;
    private int maxProductosEnStock=0;


    private double saldoCaja;


    private Map<String, Producto> productosEnStock;

    public Map<String, Producto> getProductosEnStock() {
        return productosEnStock;
    }

    public Tienda(String nombre, int maxProductosEnStock, double saldoCaja) {
        this.nombre = nombre;
        this.maxProductosEnStock = maxProductosEnStock;
        this.saldoCaja = saldoCaja;
        this.productosEnStock = new HashMap<>();
    }

    // Getters y Setters
     
    public int getMaxProductosEnStock() {
        return maxProductosEnStock;
    }

    public double getSaldoCaja(){
        return this.saldoCaja;
    }
    
    public void setSaldoCaja(double saldoCaja) {
        this.saldoCaja = saldoCaja;
    }

    public boolean validarSaldoEnCaja(double costo, int cantidad){
        if(this.getSaldoCaja()<(costo*cantidad))
            return false;
        else
            return true;
    }

    public boolean validarStock(int cantidad){
        if((this.calcularCantidadTotalEnStock()+cantidad)>getMaxProductosEnStock())
            return false;
        else
            return true;
    }

    // Si el producto es nuevo se modifica solamente el saldo en caja y se enlista el producto
    public void comprarProducto(Producto producto, int cantidad){
        getProductosEnStock().put(producto.getIdentificador(), producto);
        this.saldoCaja-=(cantidad*producto.getCostoPorUnidad());
    }
    
    // Si el producto ya existe se modifica el saldo en caja y la cantidad
    public void comprarProductoExistente(String identificador, int cantidad){
        int cantidadPorAumentar = buscarProductoPorId(identificador).getCantidadEnStock();
        buscarProductoPorId(identificador).setCantidadEnStock(cantidad+cantidadPorAumentar);
        this.saldoCaja-=(cantidad*buscarProductoPorId(identificador).getCostoPorUnidad());
    }

    // Este metodo se utiliza para agregar productos al inicio del ciclo sin reducir el saldo en caja
    public void agregarProducto(Producto producto){
        this.productosEnStock.put(producto.getIdentificador(), producto);
    }
    
    public boolean validarCantidadAVender(int cantidad){
        if(cantidad<=10)
            return true;
        else
            return false;
    }

    public Double venderProductoConDescuento(Producto producto, int cantidad){
        Double subtotal;
        producto.setCantidadEnStock((producto.getCantidadEnStock()-cantidad));
            subtotal = (producto.calcularPrecioVentaConDescuento()*cantidad);
            this.setSaldoCaja(this.getSaldoCaja()+subtotal);
            if(producto.getCantidadEnStock()==0)
            {
                producto.setDisponibleParaVenta(false);
            }
        return subtotal;
    }
    public Double venderProducto(Producto producto, int cantidad){
            Double subtotal;
            subtotal = producto.calcularPrecioDeVenta()*cantidad;
            this.setSaldoCaja(this.getSaldoCaja()+subtotal);
            if(producto.getCantidadEnStock()==0){
                producto.setDisponibleParaVenta(false);
            }
            return subtotal;
        }
        

    public boolean validarDisponibilidadProducto(Producto producto){
        if (producto.isDisponibleParaVenta())
            return true;
        else
            return false;
    }

    public Producto buscarProductoPorId(String identificador){
        return productosEnStock.get(identificador);
    }

    public int calcularCantidadTotalEnStock(){
        int cantidadDeProductos=0;
        for ( Map.Entry<String,Producto> entry : productosEnStock.entrySet()){
            cantidadDeProductos+=entry.getValue().getCantidadEnStock();
        }
        return cantidadDeProductos;
    }

    public String listarProductos(){
        String aux="";
        for ( Map.Entry<String,Producto> entry : productosEnStock.entrySet()){
            aux+= entry.getValue().getIdentificador()+" "+ entry.getValue().getDescripcion() + " $" + entry.getValue().getPrecioPorUnidad() +" Cantidad disponible: "+
                    entry.getValue().getCantidadEnStock()+" Costo de compra: "+entry.getValue().getCostoPorUnidad()+"\n";
        }
        return aux;
    }

    public List<Producto> obtenerComestiblesConMenorDescuento(double porcentajeDescuento) {
        List<Producto> comestiblesConMenorDesc = productosEnStock.values().stream()
            .filter(producto -> (producto instanceof Bebida && !((Bebida) producto).isImportado()) ||
                                (producto instanceof ProductoEnvasado && !((ProductoEnvasado) producto).isImportado()))
                                .filter(producto -> producto instanceof Bebida || producto instanceof ProductoEnvasado)
                                .filter(producto -> producto.getPorcentajeDescuento() < porcentajeDescuento)
                                .sorted((producto1, producto2) ->
                                Double.compare(producto1.getPrecioPorUnidad(), producto2.getPrecioPorUnidad()))
                                .collect(Collectors.toList());
        
        return comestiblesConMenorDesc;
    }


    public List<Producto> listarProductosConUtilidadesInferiores(double porcentajeUtilidad) {
        List<Producto> productosConUtilidadesInferiores = productosEnStock.values().stream()
            .filter(producto -> producto.calcularGanancia() < porcentajeUtilidad)
            .collect(Collectors.toList());
        return productosConUtilidadesInferiores;
    }
}
