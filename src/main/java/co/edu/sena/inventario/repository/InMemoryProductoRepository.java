package co.edu.sena.inventario.repository;

import co.edu.sena.inventario.model.Producto;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/** Repositorio seguro para múltiples peticiones concurrentes del servidor web. */
public class InMemoryProductoRepository implements ProductoRepository {
    private final Map<Long, Producto> productos = new LinkedHashMap<>();
    private final AtomicLong secuencia = new AtomicLong(0);

    @Override
    public synchronized Producto guardar(Producto producto) {
        if (producto.getId() == null) {
            producto.setId(secuencia.incrementAndGet());
        } else {
            secuencia.updateAndGet(actual -> Math.max(actual, producto.getId()));
        }
        productos.put(producto.getId(), producto);
        return producto;
    }

    @Override
    public synchronized Optional<Producto> buscarPorId(Long id) {
        return Optional.ofNullable(productos.get(id));
    }

    @Override
    public synchronized List<Producto> listar() {
        return new ArrayList<>(productos.values());
    }

    @Override
    public synchronized boolean eliminar(Long id) {
        return productos.remove(id) != null;
    }

    @Override
    public synchronized boolean existeCodigo(String codigo, Long idExcluido) {
        if (codigo == null) return false;
        String codigoNormalizado = codigo.trim().toLowerCase(Locale.ROOT);
        return productos.values().stream()
                .anyMatch(producto -> producto.getCodigo().toLowerCase(Locale.ROOT).equals(codigoNormalizado)
                        && (idExcluido == null || !producto.getId().equals(idExcluido)));
    }
}
