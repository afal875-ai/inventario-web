package co.edu.sena.inventario.model;

/** JavaBean usado para conservar los valores escritos en el formulario JSP. */
public class ProductoFormulario {
    private String id = "";
    private String codigo = "";
    private String nombre = "";
    private String categoria = "";
    private String cantidad = "";
    private String precio = "";

    public static ProductoFormulario desde(Producto producto) {
        ProductoFormulario formulario = new ProductoFormulario();
        formulario.setId(String.valueOf(producto.getId()));
        formulario.setCodigo(producto.getCodigo());
        formulario.setNombre(producto.getNombre());
        formulario.setCategoria(producto.getCategoria());
        formulario.setCantidad(String.valueOf(producto.getCantidad()));
        formulario.setPrecio(producto.getPrecio().toPlainString());
        return formulario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? "" : id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo == null ? "" : codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre == null ? "" : nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria == null ? "" : categoria;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad == null ? "" : cantidad;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio == null ? "" : precio;
    }
}
