<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="es_CO"/>
<jsp:include page="common/header.jsp"/>

<section class="cabecera-pagina">
    <div>
        <p class="etiqueta">Gestión de inventario</p>
        <h1>Productos</h1>
        <p class="texto-secundario">Consulta y administra las existencias desde un solo lugar.</p>
    </div>
    <a class="boton" href="${pageContext.request.contextPath}/productos/formulario">+ Agregar producto</a>
</section>

<c:if test="${not empty mensaje}">
    <div class="alerta alerta-exito" role="status">
        <c:out value="${mensaje}"/>
    </div>
</c:if>

<section class="resumen" aria-label="Resumen del inventario">
    <article class="tarjeta-resumen">
        <span>Productos visibles</span>
        <strong><c:out value="${productos.size()}"/></strong>
    </article>
    <article class="tarjeta-resumen">
        <span>Valor del inventario visible</span>
        <strong><fmt:formatNumber value="${totalInventario}" type="currency" currencySymbol="$" maxFractionDigits="0"/></strong>
    </article>
</section>

<section class="panel">
    <form class="buscador" method="get" action="${pageContext.request.contextPath}/productos" role="search">
        <div class="campo-busqueda">
            <label for="q">Buscar productos</label>
            <input id="q" name="q" type="search" value="<c:out value='${consulta}'/>"
                   placeholder="Código, nombre o categoría" maxlength="80">
        </div>
        <button class="boton boton-secundario" type="submit">Buscar</button>
        <c:if test="${not empty consulta}">
            <a class="enlace-limpiar" href="${pageContext.request.contextPath}/productos">Limpiar filtro</a>
        </c:if>
    </form>

    <c:choose>
        <c:when test="${empty productos}">
            <div class="estado-vacio">
                <span aria-hidden="true">⌕</span>
                <h2>No se encontraron productos</h2>
                <p>Prueba con otra búsqueda o registra un producto nuevo.</p>
            </div>
        </c:when>
        <c:otherwise>
            <div class="tabla-contenedor">
                <table>
                    <caption class="solo-lectores">Listado de productos registrados</caption>
                    <thead>
                    <tr>
                        <th scope="col">Código</th>
                        <th scope="col">Producto</th>
                        <th scope="col">Categoría</th>
                        <th scope="col" class="numero">Cantidad</th>
                        <th scope="col" class="numero">Precio</th>
                        <th scope="col" class="numero">Subtotal</th>
                        <th scope="col">Acciones</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="producto" items="${productos}">
                        <tr>
                            <td><span class="codigo"><c:out value="${producto.codigo}"/></span></td>
                            <td class="nombre-producto"><c:out value="${producto.nombre}"/></td>
                            <td><span class="insignia"><c:out value="${producto.categoria}"/></span></td>
                            <td class="numero"><c:out value="${producto.cantidad}"/></td>
                            <td class="numero"><fmt:formatNumber value="${producto.precio}" type="currency" currencySymbol="$" maxFractionDigits="0"/></td>
                            <td class="numero"><strong><fmt:formatNumber value="${producto.valorInventario}" type="currency" currencySymbol="$" maxFractionDigits="0"/></strong></td>
                            <td>
                                <div class="acciones">
                                    <a class="enlace-accion" href="${pageContext.request.contextPath}/productos/formulario?id=${producto.id}">Editar</a>
                                    <form method="post" action="${pageContext.request.contextPath}/productos"
                                          onsubmit="return confirm('¿Desea eliminar este producto?');">
                                        <input type="hidden" name="accion" value="eliminar">
                                        <input type="hidden" name="id" value="${producto.id}">
                                        <button class="enlace-accion peligro" type="submit">Eliminar</button>
                                    </form>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:otherwise>
    </c:choose>
</section>

<jsp:include page="common/footer.jsp"/>
