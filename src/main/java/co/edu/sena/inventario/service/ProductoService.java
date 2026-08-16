package co.edu.sena.inventario.service;

import co.edu.sena.inventario.model.Producto;
import co.edu.sena.inventario.model.ProductoFormulario;
import co.edu.sena.inventario.repository.ProductoRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;

/** Aplica reglas de negocio y mantiene los servlets libres de lógica de dominio. */
public class ProductoService {
    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    public List<Producto> listar(String consulta) {
        String filtro = consulta == null ? "" : consulta.trim().toLowerCase(Locale.ROOT);
        return repository.listar().stream()
                .filter(producto -> filtro.isBlank()
                        || producto.getCodigo().toLowerCase(Locale.ROOT).contains(filtro)
                        || producto.getNombre().toLowerCase(Locale.ROOT).contains(filtro)
                        || producto.getCategoria().toLowerCase(Locale.ROOT).contains(filtro))
                .sorted(Comparator.comparing(Producto::getNombre, String.CASE_INSENSITIVE_ORDER))
                .toList();
    }

    public Producto buscar(Long id) {
        if (id == null) throw new NoSuchElementException("Producto no encontrado");
        return repository.buscarPorId(id)
                .orElseThrow(() -> new NoSuchElementException("Producto no encontrado"));
    }

    public Producto guardar(ProductoFormulario formulario) {
        Map<String, String> errores = new LinkedHashMap<>();
        Long id = convertirId(formulario.getId(), errores);
        String codigo = limpiar(formulario.getCodigo());
        String nombre = limpiar(formulario.getNombre());
        String categoria = limpiar(formulario.getCategoria());
        Integer cantidad = convertirCantidad(formulario.getCantidad(), errores);
        BigDecimal precio = convertirPrecio(formulario.getPrecio(), errores);

        validarTexto("codigo", codigo, 3, 20, "El código", errores);
        validarTexto("nombre", nombre, 3, 80, "El nombre", errores);
        validarTexto("categoria", categoria, 3, 50, "La categoría", errores);

        if (!codigo.isBlank() && repository.existeCodigo(codigo, id)) {
            errores.put("codigo", "Ya existe un producto con este código.");
        }
        if (id != null && repository.buscarPorId(id).isEmpty()) {
            errores.put("general", "El producto que intenta editar ya no existe.");
        }
        if (!errores.isEmpty()) throw new ValidacionException(errores);

        LocalDateTime ahora = LocalDateTime.now();
        Producto producto;
        if (id == null) {
            producto = new Producto(null, codigo.toUpperCase(Locale.ROOT), nombre, categoria,
                    cantidad, precio, ahora, ahora);
        } else {
            producto = repository.buscarPorId(id).orElseThrow();
            producto.setCodigo(codigo.toUpperCase(Locale.ROOT));
            producto.setNombre(nombre);
            producto.setCategoria(categoria);
            producto.setCantidad(cantidad);
            producto.setPrecio(precio);
            producto.setFechaActualizacion(ahora);
        }
        return repository.guardar(producto);
    }

    public void eliminar(Long id) {
        if (id == null || !repository.eliminar(id)) {
            throw new NoSuchElementException("Producto no encontrado");
        }
    }

    public BigDecimal calcularValorTotal(List<Producto> productos) {
        return productos.stream()
                .map(Producto::getValorInventario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Long convertirId(String valor, Map<String, String> errores) {
        if (valor == null || valor.isBlank()) return null;
        try {
            long id = Long.parseLong(valor);
            if (id <= 0) throw new NumberFormatException();
            return id;
        } catch (NumberFormatException excepcion) {
            errores.put("general", "El identificador del producto no es válido.");
            return null;
        }
    }

    private Integer convertirCantidad(String valor, Map<String, String> errores) {
        try {
            int cantidad = Integer.parseInt(valor == null ? "" : valor.trim());
            if (cantidad < 0) {
                errores.put("cantidad", "La cantidad no puede ser negativa.");
            }
            return cantidad;
        } catch (NumberFormatException excepcion) {
            errores.put("cantidad", "Ingrese una cantidad entera válida.");
            return null;
        }
    }

    private BigDecimal convertirPrecio(String valor, Map<String, String> errores) {
        try {
            BigDecimal precio = new BigDecimal(valor == null ? "" : valor.trim().replace(',', '.'));
            if (precio.compareTo(BigDecimal.ZERO) < 0) {
                errores.put("precio", "El precio no puede ser negativo.");
            }
            if (precio.scale() > 2) {
                errores.put("precio", "El precio puede tener máximo dos decimales.");
            }
            return precio;
        } catch (NumberFormatException excepcion) {
            errores.put("precio", "Ingrese un precio válido.");
            return null;
        }
    }

    private void validarTexto(String campo, String valor, int minimo, int maximo,
                              String etiqueta, Map<String, String> errores) {
        if (valor.isBlank()) {
            errores.put(campo, etiqueta + " es obligatorio.");
        } else if (valor.length() < minimo || valor.length() > maximo) {
            errores.put(campo, etiqueta + " debe tener entre " + minimo + " y " + maximo + " caracteres.");
        }
    }

    private String limpiar(String valor) {
        return valor == null ? "" : valor.trim().replaceAll("\\s+", " ");
    }
}
