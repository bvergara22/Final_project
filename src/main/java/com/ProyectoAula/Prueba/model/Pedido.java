package com.ProyectoAula.Prueba.model;


import java.util.List;

public class Pedido {
    private String cliente;
    private List<ProductoCarrito> productos;
    private double total;

    public Pedido(String cliente, List<ProductoCarrito> productos) {
        this.cliente = cliente;
        this.productos = productos;
        this.total = productos.stream().mapToDouble(ProductoCarrito::getTotal).sum();
    }

    // Getters y Setters
    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }

    public List<ProductoCarrito> getProductos() { return productos; }
    public void setProductos(List<ProductoCarrito> productos) { this.productos = productos; }

    public double getTotal() { return total; }
}

