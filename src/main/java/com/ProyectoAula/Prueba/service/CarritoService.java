package com.ProyectoAula.Prueba.service;

import com.ProyectoAula.Prueba.model.ProductoCarrito;
import com.ProyectoAula.Prueba.model.Pedido;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarritoService {
    private List<ProductoCarrito> carrito = new ArrayList<>();

    public void agregarProducto(ProductoCarrito producto) {
        carrito.add(producto);
    }

    public List<ProductoCarrito> obtenerCarrito() {
        return carrito;
    }

    public void eliminarProducto(int indice) {
        if (indice >= 0 && indice < carrito.size()) {
            carrito.remove(indice);
        }
    }

    public Pedido finalizarCompra(String cliente) {
        Pedido pedido = new Pedido(cliente, new ArrayList<>(carrito));
        carrito.clear(); // Vaciar el carrito después de la compra
        return pedido;
    }
}

