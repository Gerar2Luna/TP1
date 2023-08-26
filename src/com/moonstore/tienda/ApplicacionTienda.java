package com.moonstore.tienda;
import com.moonstore.interfaces.Comestible;
import com.moonstore.interfaces.AplicableDescuento;

import java.time.LocalDate;
import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;
import java.util.List;

import com.moonstore.productos.*;
import com.moonstore.productos.enums.TipoAplicacion;
import com.moonstore.productos.enums.TipoEnvase;


public class ApplicacionTienda {
    
    public static void main(String[] args) {
        // Variables de valores iniciales

        double saldoInicial=100000;
        int maximoStockInicial=100;
        boolean control = true;

        // Variables auxiliares para la creacion y validacion de productos

        String identificador;
        String descripcion;
        int cantidadEnStock;
        double costoPorUnidad;
        double precioDeVenta;
        boolean disponibleParaVenta;
        double graduacionAlcoholica;
        boolean esImportado;
        Scanner scanner = new Scanner(System.in);
        LocalDate fechaDeVencimiento;
        int calorias;
        TipoEnvase tipoEnvase;
        TipoAplicacion tipoAplicacion;
        int cantidadAComprar;
        double precioAComprar;
        boolean esAlcoholica= true;
        int cantidadAVender;
        double porcentajeDescuento;
        boolean stockMenorAlSolicitado = false;


        // Ejecucion del programa por consola

        System.out.println("Inicializando tienda de prueba...");
        System.out.println("Saldo inicial de prueba en caja a cargar: "+ saldoInicial);
        System.out.println("Maximo stock inicial de prueba a cargar: " + maximoStockInicial);
        System.out.println("Se cargaran productos iniciales de prueba con valores correctos: ");
        System.out.println("Presione enter para continuar...");
        scanner.nextLine();
        Tienda moonstore = new Tienda("moonstore", maximoStockInicial, saldoInicial);

        System.out.println("Tipo de producto elegido a cargar: Bebida");

        // Datos para ingresar bebida

        identificador="AC465";
        descripcion= "Coca-Cola";
        cantidadEnStock=25;
        costoPorUnidad=10.0;
        disponibleParaVenta=true; // Este dato a la hora de la creacion del producto seria true por default sin preguntar al usuario debido a los requerimientos del sistema
        graduacionAlcoholica= 0.0;
        esImportado=true;
        calorias = 1000;
        int controlswitch;
            
        Producto bebida = new Bebida(identificador, descripcion, cantidadEnStock, costoPorUnidad, disponibleParaVenta, disponibleParaVenta, graduacionAlcoholica, esImportado);
        
        // Se utilizaria un bucle de control verificando si bebida!=null, mientras sea null, quiere decir que la validacion no permitio crear el producto con un codigo invalido
        // Se le avisaria al usuario de esto a travez de una excepcion y se le pediria que vuelva a cargarlo
        // Por ultimo antes de cargarlo en el stock de la tienda se valida la ganancia esperada

        precioDeVenta = 11.0;
        System.out.println("Ingrese el precio de venta: " + precioDeVenta);
        if(bebida.validarGanancia(precioDeVenta)){
            bebida.setPrecioPorUnidad(precioDeVenta);
            ((Bebida)bebida).setCalorias(calorias);
            ((Bebida)bebida).setFechaVencimiento(LocalDate.of(2024, 12, 25));;
            moonstore.agregarProducto(bebida);
            System.out.println("El producto " +  bebida.getIdentificador() + " fue agregado correctamente!");
        }
        else
            System.out.println("Por favor vuelva a ingresar el precio de venta"); // Ejemplo de como controlar la entrada
        
        // Datos para ingresar Producto Envasado
        System.out.println("Tipo de producto elegido a cargar: Producto Envasado");
        identificador = "AB420";
        descripcion = "Papitas Lays";
        cantidadEnStock = 25;
        costoPorUnidad = 15.0;
        disponibleParaVenta=true;
        tipoEnvase = TipoEnvase.LATA;

        Producto envasado = new ProductoEnvasado(identificador,descripcion,cantidadEnStock,costoPorUnidad,disponibleParaVenta,tipoEnvase,esImportado);

        precioDeVenta= 16;
        System.out.println("Ingrese el precio de venta: " + precioDeVenta);
        System.out.println();
        if(envasado.validarGanancia(precioDeVenta)){
            envasado.setPrecioPorUnidad(precioDeVenta);
            ((ProductoEnvasado)envasado).setCalorias(calorias);
            ((ProductoEnvasado)envasado).setFechaVencimiento(LocalDate.of(2024, 12, 25));
            moonstore.agregarProducto(envasado);
            System.out.println("El producto " +  envasado.getIdentificador() + " fue agregado correctamente!");
        }
        else
            System.out.println("Por favor vuelva a ingresar el precio de venta"); 

        // Datos para ingresar Producto de Limpieza invalidos
        System.out.println("Se cargaran incorrectamente los datos de este producto: ");
        System.out.println("Tipo de producto elegido a cargar: Producto de Limpieza");
        identificador = "AZ420";
        descripcion = "Ayudin";
        cantidadEnStock = 25;
        costoPorUnidad = 20.0;
        disponibleParaVenta=true;
        tipoAplicacion = TipoAplicacion.MULTIUSO;
        
        Producto productoLimpieza = new ProductoLimpieza(identificador, descripcion, cantidadEnStock, costoPorUnidad, disponibleParaVenta, tipoAplicacion);
        
        precioDeVenta= 16;
        System.out.println("Ingrese el precio de venta: " + precioDeVenta);
        System.out.println();
        if(productoLimpieza.validarGanancia(precioDeVenta)){
            productoLimpieza.setPrecioPorUnidad(precioDeVenta);
            moonstore.agregarProducto(productoLimpieza);
            System.out.println("El producto " +  productoLimpieza.getIdentificador() + " fue agregado correctamente!");
        }
        else
            System.out.println("Por favor vuelva a ingresar el precio de venta");
        
        System.out.println("Prueba de que los elementos se cargaron en stock: ");
        
        for ( Map.Entry<String,Producto> entry : moonstore.getProductosEnStock().entrySet()){
            System.out.println(entry.getValue().getIdentificador()+" Nombre: "+ entry.getValue().getDescripcion()+" Cantidad: "+entry.getValue().getCantidadEnStock());
        }

        // Loop de compra, en la version de release iria con un menu y un loop que controle multiples
        // ciclos de compra, para realizar pruebas se puede modificar los valores de las variables locales

        System.out.println("Por default se intentara comprar un producto ya existente en stock, sobrepasando el limite de stock de la tienda");
        System.out.println("Presione enter para continuar... ");
        scanner.nextLine();
        // Para realizar pruebas modificar los valores a validar:
        
        identificador ="AZ420";
        cantidadAComprar = 50;
        precioAComprar = 1.0;



        controlswitch=1;  // NOTA: 

        // Al usuario le dariamos a elegir para que nos ingrese que tipo de producto esta comprando
       // Forzando asi que nos ingrese un codigo valido en los primeros 2 caracteres para alguno de los 3 productos
      // Los numeros si puede ingresarlos a mano


        // -------------------------------------------------------------- INICIO LOOP DE COMPRA-----------------------------------------------------------------------------
        if(moonstore.validarStock(cantidadAComprar)){
            if(moonstore.validarSaldoEnCaja(precioAComprar, cantidadAComprar)){
                if(moonstore.buscarProductoPorId(identificador)!=null){
                    System.out.println("Saldo en caja previo: "+ moonstore.getSaldoCaja());
                    System.out.println("Stock del producto previo: "+ moonstore.buscarProductoPorId(identificador).getCantidadEnStock());
                    moonstore.comprarProductoExistente(identificador,cantidadAComprar);
                    System.out.print("Se compro exitosamente el producto: "+ moonstore.buscarProductoPorId(identificador).getIdentificador());
                    System.out.println(" por: "+cantidadAComprar+" unidades");
                    System.out.println("Nuevo saldo en caja: "+moonstore.getSaldoCaja());
                    System.out.println("Nuevo stock del producto "+identificador+" "+moonstore.buscarProductoPorId(identificador).getCantidadEnStock());
                } else{
                    Producto productoAcomprar;
                    switch (identificador) {
                        case String aux1 when aux1.matches("AB\\d{3}"):{
                            productoAcomprar = new ProductoEnvasado(identificador,descripcion,cantidadAComprar,precioAComprar,disponibleParaVenta,tipoEnvase,esImportado);
                            ((ProductoEnvasado)productoAcomprar).setCalorias(calorias);
                            ((ProductoEnvasado)productoAcomprar).setFechaVencimiento(LocalDate.of(2025, 12, 25));
                            do{
                            System.out.println("Ingrese precio de venta: ");
                            precioDeVenta=scanner.nextDouble();
                            scanner.nextLine();
                            if(!(productoAcomprar.validarGanancia(precioDeVenta))){
                                    System.out.println("El margen de ganancia no es un valor valido, por favor ingrese otro precio de venta");
                            } else{
                                    productoAcomprar.setPrecioPorUnidad(precioDeVenta);
                                }
                                } while(!(productoAcomprar.validarGanancia(precioDeVenta)));
                            moonstore.comprarProducto(productoAcomprar, cantidadAComprar);
                            System.out.print("Se compro exitosamente el producto: "+ moonstore.buscarProductoPorId(identificador).getIdentificador());
                            System.out.println(" por: "+cantidadAComprar+" unidades");
                            System.out.println("Nuevo saldo en caja: "+moonstore.getSaldoCaja());
                            System.out.println("Nuevo stock del producto "+identificador+" "+moonstore.buscarProductoPorId(identificador).getCantidadEnStock());
                            break;
                        }
                        case String aux2 when aux2.matches("AC\\d{3}"):
                            productoAcomprar = new Bebida(identificador, descripcion, cantidadAComprar, precioAComprar, disponibleParaVenta, esAlcoholica, graduacionAlcoholica, esImportado);
                            ((Bebida)productoAcomprar).setCalorias(calorias);
                            ((Bebida)productoAcomprar).setFechaVencimiento(LocalDate.of(2025,12,25));
                            do{
                                System.out.println("Ingrese precio de venta: ");
                                precioDeVenta=scanner.nextDouble();
                                scanner.nextLine();
                                if(!(productoAcomprar.validarGanancia(precioDeVenta))){
                                    System.out.println("El margen de ganancia no es un valor valido, por favor ingrese otro precio de venta");
                                } else{
                                    productoAcomprar.setPrecioPorUnidad(precioDeVenta);
                                }
                            } while(!(productoAcomprar.validarGanancia(precioDeVenta)));
                            
                            moonstore.comprarProducto(productoAcomprar, cantidadAComprar);
                            System.out.print("Se compro exitosamente el producto: "+ moonstore.buscarProductoPorId(identificador).getIdentificador());
                            System.out.println(" por: "+cantidadAComprar+" unidades");
                            System.out.println("Nuevo saldo en caja: "+moonstore.getSaldoCaja());
                            System.out.println("Nuevo stock del producto "+identificador+" "+moonstore.buscarProductoPorId(identificador).getCantidadEnStock());
                            break;

                        case String aux3 when aux3.matches("AZ\\d{3}"):
                            productoAcomprar=new ProductoLimpieza(identificador, descripcion, cantidadAComprar, precioAComprar, disponibleParaVenta, tipoAplicacion);
                            do{
                                System.out.println("Ingrese precio de venta: ");
                                precioDeVenta=scanner.nextDouble();
                                scanner.nextLine();
                                if(!(productoAcomprar.validarGanancia(precioDeVenta))){
                                    System.out.println("El margen de ganancia no es un valor valido, por favor ingrese otro precio de venta");
                                } else{
                                    productoAcomprar.setPrecioPorUnidad(precioDeVenta);
                                }
                            } while(!(productoAcomprar.validarGanancia(precioDeVenta)));
                            moonstore.comprarProducto(productoAcomprar, cantidadAComprar);
                            System.out.print("Se compro exitosamente el producto: "+ moonstore.buscarProductoPorId(identificador).getIdentificador());
                            System.out.println(" por: "+cantidadAComprar+" unidades");
                            System.out.println("Nuevo saldo en caja: "+moonstore.getSaldoCaja());
                            System.out.println("Nuevo stock del producto "+identificador+" "+moonstore.buscarProductoPorId(identificador).getCantidadEnStock());
                            break;
                        default:       
                    }
                }
            } else{
                System.out.println("El saldo en caja es insuficiente para la realizar la compra!");
            }
        } else {
            System.out.println("La cantidad a comprar excede el stock maximo! ");
        }
        // ------------------------------------------------------------------- FIN LOOP DE COMPRA ---------------------------------------------------------------------------------
        scanner.nextLine();
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println("Inicio de la prueba de venta de los articulos cargados en la inicializacion de la tienda: ");
        boolean controlventa=true;
        identificador= "AZ420"; // Estos datos se le pediria al usuario que lo ingrese
        cantidadAVender =5; // Validando que solo se pueden vender productos que ya existen en el stock utilizando el metodo moonstore.buscarProductoPorID(identificador)
        System.out.println(moonstore.listarProductos());
        String cargarMasProductos;
        Map <String,Integer> carrito = new HashMap<>();
        while (carrito.size()<3 && controlventa==true)
        {
            System.out.println("Ingrese el ID del producto a vender: ");
            identificador=scanner.nextLine();
            if(carrito.containsKey(identificador)){
                System.out.println("El producto ingresado ya se encuentra en el carrito, por favor reingrese la cantidad: ");
                cantidadAVender=scanner.nextInt();
                scanner.nextLine();
                carrito.put(identificador,cantidadAVender);
            }
                else{
                        if(moonstore.buscarProductoPorId(identificador)==null)
                            System.out.println("Por favor ingrese un ID Valido");
                        else    if (moonstore.buscarProductoPorId(identificador).isDisponibleParaVenta()==false)
                                    System.out.println("El producto no se encuentra disponible para la venta!");
                                else {
                                    System.out.println("Ingrese la cantidad a vender: ");
                                    cantidadAVender=scanner.nextInt();
                                    scanner.nextLine();
                                    carrito.put(identificador,cantidadAVender);
                                    System.out.println("Ingrese SI para cargar mas productos o NO para finalizar la carga");
                                    cargarMasProductos=scanner.nextLine();
                                    cargarMasProductos=cargarMasProductos.toUpperCase();
                                    if(cargarMasProductos.equals("NO"))
                                    {
                                        controlventa=false;
                                    }
                                }
                }   
            
        }
        String resumenVenta="";
        double totalVenta=0;
        String auxiliar;
        boolean controlDescuento=false;
        for ( Map.Entry<String,Integer> entrada : carrito.entrySet())
        {
            System.out.println("Ingrese SI para aplicar descuento: ");
            System.out.println(moonstore.buscarProductoPorId(entrada.getKey()).getIdentificador()+" "+ moonstore.buscarProductoPorId(entrada.getKey()).getDescripcion());
            auxiliar = scanner.nextLine();
            auxiliar.toUpperCase();
            if(entrada.getValue()>=10){ // Si se excede de la cantidad maxima a vender por producto le forzamos el valor maximo
                cantidadAVender=10;
            }
            if (entrada.getValue()>(moonstore.buscarProductoPorId(entrada.getKey()).getCantidadEnStock()))
            {
                cantidadAVender=(moonstore.buscarProductoPorId(entrada.getKey()).getCantidadEnStock()); // Si cantidad a vender es mayor al stock le asignamos la cantidad en stock
                stockMenorAlSolicitado=true;
            }
            if(auxiliar.equals("SI"))
            {
                do{
                    System.out.println("Ingrese un porcentaje de descuento valido");
                    porcentajeDescuento=scanner.nextDouble();
                    scanner.nextLine();
                    moonstore.buscarProductoPorId(entrada.getKey()).setPorcentajeDescuento(porcentajeDescuento);
                    controlDescuento=(moonstore.buscarProductoPorId(entrada.getKey()).aplicaDescuento(porcentajeDescuento));
                    if(controlDescuento==false || porcentajeDescuento<=0){
                        System.out.println("El descuento ingresado no es valido!");
                    }
                } while(controlDescuento==false);
                moonstore.buscarProductoPorId(entrada.getKey()).setPorcentajeDescuento(porcentajeDescuento);
                totalVenta+=moonstore.venderProductoConDescuento(moonstore.buscarProductoPorId(entrada.getKey()), cantidadAVender);
                resumenVenta+=moonstore.buscarProductoPorId(entrada.getKey()).getIdentificador()+" "+moonstore.buscarProductoPorId(entrada.getKey()).getDescripcion()+" "+
                                cantidadAVender+" x $"+String.format("%.2f", moonstore.buscarProductoPorId(entrada.getKey()).calcularPrecioDeVenta())+"\n";
            } else
                {
                    totalVenta+=moonstore.venderProducto(moonstore.buscarProductoPorId(entrada.getKey()),cantidadAVender);
                    resumenVenta+=moonstore.buscarProductoPorId(entrada.getKey()).getIdentificador()+" "+moonstore.buscarProductoPorId(entrada.getKey()).getDescripcion()+" "+
                                cantidadAVender+" x $"+String.format("%.2f", moonstore.buscarProductoPorId(entrada.getKey()).calcularPrecioDeVenta())+"\n";
                }
                
                
            
        }
        if(stockMenorAlSolicitado==true)
        {
            resumenVenta+="Hay productos con stock disponible menor al solicitado\n";
        }
        resumenVenta+="TOTAL VENTA: $"+ totalVenta+"\n";
        System.out.println(resumenVenta);
    //-------------------------------------------------------------- Fin de Venta ------------------------------------------------------------------------------------------------
    
    double descuento = 10.0; // Modificar para realizar pruebas
    System.out.println("Se imprimira la lista de productos comestibles no importados cuyo descuento es menor al ingresado: "+ descuento);
    
    List<Producto> descuentosMenores = moonstore.obtenerComestiblesConMenorDescuento(descuento);
    if(descuentosMenores.size()==0){
        System.out.println("No existen productos que cumplan el requisito");
    }else{
                for ( Producto entry : descuentosMenores){
                    System.out.println(entry.getDescripcion().toUpperCase()); // Se elige enlistarlos en vertical en vez de horizontal para evitar que se vaya del rango de la consola
                }
        }
    System.out.println("Presione una tecla para continuar...");
    scanner.nextLine();
    double gananciaPorValidar = 10.0; // Modificar para realizar pruebas
    System.out.println("Se imprimira la lista de productos cuya ganancia es menor a la ingresada: " + gananciaPorValidar);
    List<Producto> gananciasMenores = moonstore.listarProductosConUtilidadesInferiores(gananciaPorValidar);
    if (gananciasMenores.size()==0){
        System.out.println("No existen productos que cumplan el requisito");
    }
     for ( Producto entry : gananciasMenores){
            System.out.println(entry.getIdentificador()+" "+entry.getDescripcion()+" Cantidad: "+entry.getCantidadEnStock());
    }
}


}
