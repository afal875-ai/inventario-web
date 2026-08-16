<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="No fue posible completar la solicitud" scope="request"/>
<jsp:include page="common/header.jsp"/>
<section class="panel estado-vacio pagina-error">
    <span aria-hidden="true">!</span>
    <h1>No fue posible completar la solicitud</h1>
    <p>El recurso solicitado no existe o se presentó un inconveniente.</p>
    <a class="boton" href="${pageContext.request.contextPath}/productos">Volver al inventario</a>
</section>
<jsp:include page="common/footer.jsp"/>
