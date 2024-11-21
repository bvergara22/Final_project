package com.ProyectoAula.Prueba.model;

public class ProductoCarrito {
    private String modelo;
    private int cantidad;
    private double precio;

    public ProductoCarrito(String modelo, int cantidad, double precio) {
        this.modelo = modelo;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    // Getters y Setters
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public double getTotal() { return precio * cantidad; }

}
