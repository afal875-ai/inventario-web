package co.edu.sena.inventario.web;

import co.edu.sena.inventario.model.ProductoFormulario;
import co.edu.sena.inventario.repository.InMemoryProductoRepository;
import co.edu.sena.inventario.repository.ProductoRepository;
import co.edu.sena.inventario.service.ProductoService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/** Configura las dependencias compartidas al iniciar la aplicación web. */
@WebListener
public class AplicacionListener implements ServletContextListener {
    public static final String SERVICIO_PRODUCTOS = "servicioProductos";

    @Override
    public void contextInitialized(ServletContextEvent evento) {
        ProductoRepository repository = new InMemoryProductoRepository();
        ProductoService servicio = new ProductoService(repository);
        evento.getServletContext().setAttribute(SERVICIO_PRODUCTOS, servicio);

        servicio.guardar(formulario("TEC-001", "Teclado mecánico", "Periféricos", "12", "189900"));
        servicio.guardar(formulario("MON-002", "Monitor 24 pulgadas", "Monitores", "7", "649900"));
        servicio.guardar(formulario("CAB-003", "Cable HDMI", "Accesorios", "30", "24900"));
    }

    private ProductoFormulario formulario(String codigo, String nombre, String categoria,
                                           String cantidad, String precio) {
        ProductoFormulario formulario = new ProductoFormulario();
        formulario.setCodigo(codigo);
        formulario.setNombre(nombre);
        formulario.setCategoria(categoria);
        formulario.setCantidad(cantidad);
        formulario.setPrecio(precio);
        return formulario;
    }
}
