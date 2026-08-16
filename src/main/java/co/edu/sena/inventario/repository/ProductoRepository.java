package co.edu.sena.inventario.repository;

import co.edu.sena.inventario.model.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository {
    Producto guardar(Producto producto);

    Optional<Producto> buscarPorId(Long id);

    List<Producto> listar();

    boolean eliminar(Long id);

    boolean existeCodigo(String codigo, Long idExcluido);
}
