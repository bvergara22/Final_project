package com.ProyectoAula.Prueba.controller;

import com.ProyectoAula.Prueba.model.ProductoCarrito;
import com.ProyectoAula.Prueba.model.Pedido;
import com.ProyectoAula.Prueba.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import com.ProyectoAula.Prueba.model.Usuario;
import com.ProyectoAula.Prueba.repository.UsuarioRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.List;

@Controller
public class MainController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CarritoService carritoService;


    // Método para registrar un usuario
    @PostMapping("/registrarse")
    public String registrarUsuario(@RequestParam String nombre, @RequestParam String correo, @RequestParam String password, RedirectAttributes redirectAttributes) {
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setCorreo(correo);
        usuario.setPassword(password);

        usuarioRepository.save(usuario);
        // Usar RedirectAttributes para pasar mensajes
        redirectAttributes.addFlashAttribute("mensaje", "Registro exitoso. Por favor, inicie sesión.");
        return "redirect:/homepage?mostrarLogin=true";
    }

    // Método para iniciar sesión
    @PostMapping("/login")
    public String loginUsuario(@RequestParam String correo, @RequestParam String password, HttpSession session, RedirectAttributes redirectAttributes) {
        Usuario usuario = usuarioRepository.findByCorreo(correo).orElse(null);

        if (usuario == null) {
            redirectAttributes.addFlashAttribute("error", "Error al ingresar sesión, usuario inexistente.");
            return "redirect:/homepage?mostrarLogin=true";
        }

        if (!usuario.getPassword().equals(password)) {
            redirectAttributes.addFlashAttribute("error", "Error, correo o contraseña incorrecta.");
            return "redirect:/homepage?mostrarLogin=true";
        }

        // Guardar el usuario en la sesión después de iniciar sesión exitosamente
        session.setAttribute("usuario", usuario);

        // Mensaje de éxito
        redirectAttributes.addFlashAttribute("mensaje", "Inicio de sesión exitoso.");
        return "redirect:/comprar";
    }

    // Método para mostrar la página de compra
    @GetMapping("/comprar")
    public String comprar(HttpSession session, Model model) {
        // Obtener el usuario de la sesión
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario != null) {
            model.addAttribute("usuario", usuario);
        } else {
            // Redirigir a la página de inicio de sesión si no hay usuario en la sesión
            return "redirect:/homepage?mostrarLogin=true";
        }

        return "comprar"; // Renderizar la vista comprar.html
    }

    @PostMapping("/agregarCarrito")
    public String agregarAlCarrito(@RequestParam String modelo,
                                   @RequestParam int cantidad,
                                   @RequestParam double precio) {
        ProductoCarrito producto = new ProductoCarrito(modelo, cantidad, precio);
        carritoService.agregarProducto(producto);
        return "redirect:/carrito"; // Redirige a la vista del carrito después de añadir el producto
    }


    @GetMapping("/carrito")
    public String verCarrito(Model model) {
        model.addAttribute("carrito", carritoService.obtenerCarrito());
        return "carrito";
    }

    @PostMapping("/eliminarProducto")
    public String eliminarProducto(@RequestParam int indice) {
        carritoService.eliminarProducto(indice);
        return "redirect:/carrito";
    }

    @PostMapping("/finalizarCompra")
    public String finalizarCompra(@RequestParam String cliente, Model model) {
        Pedido pedido = carritoService.finalizarCompra(cliente);
        model.addAttribute("pedido", pedido);
        return "confirmacionCompra"; // Página de confirmación de compra
    }

    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        model.addAttribute("usuarios", usuarios);
        return "listarUsuarios";  // Redirige a una vista llamada "listarUsuarios.html"
    }

    @GetMapping("/homepage")
    public String homepage(){
        return "homepage";
    }

    @GetMapping("/acerca")
    public String acercaDeNosotros(){
        return"acerca";
    }

    @GetMapping("/terminos")
    public String terminos(){
        return"terminos";
    }

    @GetMapping("/soporte")
    public String soporte(){
        return "soporte";
    }
}
