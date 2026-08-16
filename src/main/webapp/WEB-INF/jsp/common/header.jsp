<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Módulo web para la gestión de productos del inventario">
    <title><c:out value="${titulo}"/> | StockSENA</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/estilos.css">
</head>
<body>
<header class="encabezado">
    <div class="contenedor barra-navegacion">
        <a class="marca" href="${pageContext.request.contextPath}/productos" aria-label="Ir al inventario">
            <span class="marca-icono" aria-hidden="true">S</span>
            <span>StockSENA</span>
        </a>
        <nav aria-label="Navegación principal">
            <a href="${pageContext.request.contextPath}/productos">Productos</a>
            <a class="boton boton-pequeno" href="${pageContext.request.contextPath}/productos/formulario">Nuevo producto</a>
        </nav>
    </div>
</header>
<main class="contenedor contenido-principal">
