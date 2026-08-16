package co.edu.sena.inventario.service;

import co.edu.sena.inventario.model.Producto;
import co.edu.sena.inventario.model.ProductoFormulario;
import co.edu.sena.inventario.repository.InMemoryProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ProductoServiceTest {
    private ProductoService servicio;

    @BeforeEach
    void configurar() {
        servicio = new ProductoService(new InMemoryProductoRepository());
    }

    @Test
    void debeCrearProductoYNormalizarSusDatos() {
        Producto producto = servicio.guardar(formulario("  tec-010 ", "  Teclado   inalámbrico  ",
                " Periféricos ", "5", "129900,50"));

        assertNotNull(producto.getId());
        assertEquals("TEC-010", producto.getCodigo());
        assertEquals("Teclado inalámbrico", producto.getNombre());
        assertEquals(new BigDecimal("129900.50"), producto.getPrecio());
        assertNotNull(producto.getFechaCreacion());
    }

    @Test
    void debeRechazarCamposObligatoriosVacios() {
        ValidacionException excepcion = assertThrows(ValidacionException.class,
                () -> servicio.guardar(formulario("", "", "", "1", "100")));

        assertAll(
                () -> assertTrue(excepcion.getErrores().containsKey("codigo")),
                () -> assertTrue(excepcion.getErrores().containsKey("nombre")),
                () -> assertTrue(excepcion.getErrores().containsKey("categoria"))
        );
    }

    @Test
    void debeRechazarCantidadYPrecioInvalidos() {
        ValidacionException excepcion = assertThrows(ValidacionException.class,
                () -> servicio.guardar(formulario("PRO-001", "Producto", "General", "-2", "10.999")));

        assertEquals("La cantidad no puede ser negativa.", excepcion.getErrores().get("cantidad"));
        assertEquals("El precio puede tener máximo dos decimales.", excepcion.getErrores().get("precio"));
    }

    @Test
    void debeRechazarCodigoDuplicado() {
        servicio.guardar(formulario("PRO-001", "Producto uno", "General", "2", "1000"));

        ValidacionException excepcion = assertThrows(ValidacionException.class,
                () -> servicio.guardar(formulario("pro-001", "Producto dos", "General", "3", "2000")));

        assertTrue(excepcion.getErrores().containsKey("codigo"));
    }

    @Test
    void debeFiltrarPorCodigoNombreOCategoria() {
        servicio.guardar(formulario("TEC-001", "Teclado", "Periféricos", "2", "1000"));
        servicio.guardar(formulario("MON-001", "Monitor", "Pantallas", "3", "2000"));

        assertEquals(1, servicio.listar("tec").size());
        assertEquals(1, servicio.listar("monitor").size());
        assertEquals(1, servicio.listar("pantallas").size());
        assertEquals(2, servicio.listar("").size());
    }

    @Test
    void debeActualizarUnProductoSinDuplicarlo() {
        Producto creado = servicio.guardar(formulario("PRO-001", "Producto inicial", "General", "2", "1000"));
        ProductoFormulario edicion = formulario("PRO-001", "Producto actualizado", "General", "7", "1500");
        edicion.setId(String.valueOf(creado.getId()));

        Producto actualizado = servicio.guardar(edicion);

        assertEquals(creado.getId(), actualizado.getId());
        assertEquals(creado.getFechaCreacion(), actualizado.getFechaCreacion());
        assertEquals("Producto actualizado", actualizado.getNombre());
        assertEquals(1, servicio.listar(null).size());
    }

    @Test
    void debeCalcularValorTotalDelInventario() {
        Producto primero = servicio.guardar(formulario("PRO-001", "Producto uno", "General", "2", "1000"));
        Producto segundo = servicio.guardar(formulario("PRO-002", "Producto dos", "General", "3", "500"));

        assertEquals(new BigDecimal("3500"), servicio.calcularValorTotal(List.of(primero, segundo)));
    }

    @Test
    void debeEliminarYReportarUnProductoInexistente() {
        Producto producto = servicio.guardar(formulario("PRO-001", "Producto uno", "General", "2", "1000"));
        servicio.eliminar(producto.getId());

        assertTrue(servicio.listar(null).isEmpty());
        assertThrows(NoSuchElementException.class, () -> servicio.eliminar(producto.getId()));
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
