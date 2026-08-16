package co.edu.sena.inventario.web;

import co.edu.sena.inventario.model.Producto;
import co.edu.sena.inventario.model.ProductoFormulario;
import co.edu.sena.inventario.service.ProductoService;
import co.edu.sena.inventario.service.ValidacionException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.NoSuchElementException;

@WebServlet("/productos/formulario")
public class ProductoFormServlet extends HttpServlet {
    private ProductoService servicio;

    @Override
    public void init() {
        servicio = (ProductoService) getServletContext().getAttribute(AplicacionListener.SERVICIO_PRODUCTOS);
    }

    /** GET abre el formulario vacío o carga el producto solicitado para editar. */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idTexto = request.getParameter("id");
        if (idTexto == null || idTexto.isBlank()) {
            prepararVista(request, new ProductoFormulario(), false);
        } else {
            try {
                Producto producto = servicio.buscar(Long.valueOf(idTexto));
                prepararVista(request, ProductoFormulario.desde(producto), true);
            } catch (NumberFormatException | NoSuchElementException excepcion) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "El producto no existe");
                return;
            }
        }
        request.getRequestDispatcher("/WEB-INF/jsp/producto-form.jsp").forward(request, response);
    }

    /** POST recibe, valida y guarda los datos enviados por el formulario HTML. */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductoFormulario formulario = leerFormulario(request);
        boolean editando = !formulario.getId().isBlank();
        try {
            Producto guardado = servicio.guardar(formulario);
            request.getSession().setAttribute("mensaje",
                    editando ? "Producto actualizado correctamente." : "Producto creado correctamente.");
            response.sendRedirect(request.getContextPath() + "/productos");
        } catch (ValidacionException excepcion) {
            prepararVista(request, formulario, editando);
            request.setAttribute("errores", excepcion.getErrores());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            request.getRequestDispatcher("/WEB-INF/jsp/producto-form.jsp").forward(request, response);
        }
    }

    private ProductoFormulario leerFormulario(HttpServletRequest request) {
        ProductoFormulario formulario = new ProductoFormulario();
        formulario.setId(request.getParameter("id"));
        formulario.setCodigo(request.getParameter("codigo"));
        formulario.setNombre(request.getParameter("nombre"));
        formulario.setCategoria(request.getParameter("categoria"));
        formulario.setCantidad(request.getParameter("cantidad"));
        formulario.setPrecio(request.getParameter("precio"));
        return formulario;
    }

    private void prepararVista(HttpServletRequest request, ProductoFormulario formulario, boolean editando) {
        request.setAttribute("formulario", formulario);
        request.setAttribute("editando", editando);
        request.setAttribute("titulo", editando ? "Editar producto" : "Nuevo producto");
    }
}
