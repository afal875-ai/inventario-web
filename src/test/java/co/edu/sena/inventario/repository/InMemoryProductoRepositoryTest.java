package co.edu.sena.inventario.repository;

import co.edu.sena.inventario.model.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryProductoRepositoryTest {
    private InMemoryProductoRepository repository;

    @BeforeEach
    void configurar() {
        repository = new InMemoryProductoRepository();
    }

    @Test
    void debeAsignarIdentificadorAlGuardarUnProductoNuevo() {
        Producto guardado = repository.guardar(producto(null, "PRO-001"));

        assertEquals(1L, guardado.getId());
        assertEquals(guardado, repository.buscarPorId(1L).orElseThrow());
    }

    @Test
    void debeConservarElIdentificadorAlActualizar() {
        Producto producto = repository.guardar(producto(null, "PRO-001"));
        producto.setNombre("Nombre actualizado");

        repository.guardar(producto);

        assertEquals(1, repository.listar().size());
        assertEquals("Nombre actualizado", repository.buscarPorId(1L).orElseThrow().getNombre());
    }

    @Test
    void debeDetectarCodigosDuplicadosSinDistinguirMayusculas() {
        repository.guardar(producto(null, "PRO-001"));

        assertTrue(repository.existeCodigo("pro-001", null));
        assertFalse(repository.existeCodigo("PRO-001", 1L));
    }

    @Test
    void debeEliminarUnProductoExistente() {
        Producto producto = repository.guardar(producto(null, "PRO-001"));

        assertTrue(repository.eliminar(producto.getId()));
        assertFalse(repository.buscarPorId(producto.getId()).isPresent());
        assertFalse(repository.eliminar(producto.getId()));
    }

    private Producto producto(Long id, String codigo) {
        LocalDateTime ahora = LocalDateTime.now();
        return new Producto(id, codigo, "Producto de prueba", "Categoría",
                3, new BigDecimal("1000"), ahora, ahora);
    }
}
