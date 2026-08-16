package co.edu.sena.inventario.web;

import co.edu.sena.inventario.model.Producto;
import co.edu.sena.inventario.service.ProductoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@WebServlet("/productos")
public class ProductoListServlet extends HttpServlet {
    private ProductoService servicio;

    @Override
    public void init() {
        servicio = (ProductoService) getServletContext().getAttribute(AplicacionListener.SERVICIO_PRODUCTOS);
    }

    /** GET consulta y presenta los productos, con filtro opcional en la URL. */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String consulta = request.getParameter("q");
        List<Producto> productos = servicio.listar(consulta);
        request.setAttribute("titulo", "Inventario de productos");
        request.setAttribute("consulta", consulta == null ? "" : consulta.trim());
        request.setAttribute("productos", productos);
        request.setAttribute("totalInventario", servicio.calcularValorTotal(productos));

        Object mensaje = request.getSession().getAttribute("mensaje");
        if (mensaje != null) {
            request.setAttribute("mensaje", mensaje);
            request.getSession().removeAttribute("mensaje");
        }
        request.getRequestDispatcher("/WEB-INF/jsp/productos.jsp").forward(request, response);
    }

    /** POST procesa eliminaciones; cambiar datos nunca se realiza mediante GET. */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        if (!"eliminar".equals(request.getParameter("accion"))) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
            return;
        }
        try {
            Long id = Long.valueOf(request.getParameter("id"));
            servicio.eliminar(id);
            request.getSession().setAttribute("mensaje", "Producto eliminado correctamente.");
            response.sendRedirect(request.getContextPath() + "/productos");
        } catch (NumberFormatException | NoSuchElementException excepcion) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "El producto no existe");
        }
    }
}
