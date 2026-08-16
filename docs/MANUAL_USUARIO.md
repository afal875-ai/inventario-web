# Manual de usuario

## Ingreso

Después de desplegar el archivo WAR en Tomcat, abra `http://localhost:8080/inventario-web/`. El sistema redirige al listado de productos.

## Consultar y buscar

El listado muestra código, nombre, categoría, existencias, precio y subtotal. Escriba una palabra en **Buscar productos** y seleccione **Buscar**. Use **Limpiar filtro** para regresar al catálogo completo.

## Crear un producto

1. Seleccione **Nuevo producto** o **Agregar producto**.
2. Complete código, nombre, categoría, cantidad y precio.
3. Seleccione **Crear producto**.
4. Corrija los campos señalados si el sistema presenta validaciones.

## Editar un producto

1. Seleccione **Editar** en la fila correspondiente.
2. Modifique los datos necesarios.
3. Seleccione **Guardar cambios**.

## Eliminar un producto

1. Seleccione **Eliminar** en la fila correspondiente.
2. Confirme la operación en el mensaje del navegador.
3. El sistema regresa al listado e informa el resultado.

> Los datos de esta versión académica se conservan en memoria y regresan a su estado inicial cuando se reinicia el servidor.
