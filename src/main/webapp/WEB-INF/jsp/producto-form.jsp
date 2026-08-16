<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="common/header.jsp"/>

<section class="cabecera-pagina cabecera-formulario">
    <div>
        <a class="enlace-regreso" href="${pageContext.request.contextPath}/productos">← Volver al inventario</a>
        <p class="etiqueta"><c:out value="${editando ? 'Actualización' : 'Registro'}"/></p>
        <h1><c:out value="${titulo}"/></h1>
        <p class="texto-secundario">Los campos marcados con * son obligatorios.</p>
    </div>
</section>

<section class="panel panel-formulario">
    <c:if test="${not empty errores.general}">
        <div class="alerta alerta-error" role="alert"><c:out value="${errores.general}"/></div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/productos/formulario" novalidate>
        <input type="hidden" name="id" value="<c:out value='${formulario.id}'/>">

        <div class="cuadricula-formulario">
            <div class="grupo-campo">
                <label for="codigo">Código *</label>
                <input id="codigo" name="codigo" type="text" required minlength="3" maxlength="20"
                       value="<c:out value='${formulario.codigo}'/>" autocomplete="off"
                       aria-describedby="error-codigo" class="${not empty errores.codigo ? 'invalido' : ''}">
                <small id="error-codigo" class="mensaje-error"><c:out value="${errores.codigo}"/></small>
            </div>

            <div class="grupo-campo campo-amplio">
                <label for="nombre">Nombre del producto *</label>
                <input id="nombre" name="nombre" type="text" required minlength="3" maxlength="80"
                       value="<c:out value='${formulario.nombre}'/>" autocomplete="off"
                       aria-describedby="error-nombre" class="${not empty errores.nombre ? 'invalido' : ''}">
                <small id="error-nombre" class="mensaje-error"><c:out value="${errores.nombre}"/></small>
            </div>

            <div class="grupo-campo">
                <label for="categoria">Categoría *</label>
                <input id="categoria" name="categoria" type="text" required minlength="3" maxlength="50"
                       value="<c:out value='${formulario.categoria}'/>" autocomplete="off"
                       aria-describedby="error-categoria" class="${not empty errores.categoria ? 'invalido' : ''}">
                <small id="error-categoria" class="mensaje-error"><c:out value="${errores.categoria}"/></small>
            </div>

            <div class="grupo-campo">
                <label for="cantidad">Cantidad disponible *</label>
                <input id="cantidad" name="cantidad" type="number" required min="0" step="1"
                       value="<c:out value='${formulario.cantidad}'/>" inputmode="numeric"
                       aria-describedby="error-cantidad" class="${not empty errores.cantidad ? 'invalido' : ''}">
                <small id="error-cantidad" class="mensaje-error"><c:out value="${errores.cantidad}"/></small>
            </div>

            <div class="grupo-campo">
                <label for="precio">Precio unitario (COP) *</label>
                <input id="precio" name="precio" type="number" required min="0" step="0.01"
                       value="<c:out value='${formulario.precio}'/>" inputmode="decimal"
                       aria-describedby="error-precio" class="${not empty errores.precio ? 'invalido' : ''}">
                <small id="error-precio" class="mensaje-error"><c:out value="${errores.precio}"/></small>
            </div>
        </div>

        <div class="acciones-formulario">
            <a class="boton boton-terciario" href="${pageContext.request.contextPath}/productos">Cancelar</a>
            <button class="boton" type="submit"><c:out value="${editando ? 'Guardar cambios' : 'Crear producto'}"/></button>
        </div>
    </form>
</section>

<jsp:include page="common/footer.jsp"/>
