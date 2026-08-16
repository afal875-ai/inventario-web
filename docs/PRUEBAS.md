# Plan y reporte de pruebas

## Objetivo

Comprobar las reglas de negocio y las operaciones del repositorio antes de empaquetar el módulo web.

## Pruebas automatizadas diseñadas

| ID | Componente | Escenario | Resultado esperado |
|---|---|---|---|
| PU-01 | Repositorio | Guardar producto nuevo | Asigna ID y permite consultarlo |
| PU-02 | Repositorio | Actualizar producto | Conserva ID y no duplica registros |
| PU-03 | Repositorio | Comparar códigos | Detecta duplicados sin distinguir mayúsculas |
| PU-04 | Repositorio | Eliminar producto | Retira el registro existente |
| PU-05 | Servicio | Crear producto válido | Normaliza espacios, código y precio |
| PU-06 | Servicio | Enviar campos vacíos | Reporta cada campo obligatorio |
| PU-07 | Servicio | Cantidad/precio inválidos | Rechaza negativos y exceso de decimales |
| PU-08 | Servicio | Crear código duplicado | Rechaza el registro |
| PU-09 | Servicio | Buscar | Filtra por código, nombre y categoría |
| PU-10 | Servicio | Editar | Actualiza sin duplicar |
| PU-11 | Servicio | Calcular total | Suma cantidad por precio |
| PU-12 | Servicio | Eliminar dos veces | Reporta recurso inexistente |

## Ejecución

```bash
mvn clean test
```

Maven genera el detalle verificable en `target/surefire-reports`. El resultado final de la ejecución se registra también en `RESULTADO_PRUEBAS.txt` en la raíz del proyecto.

## Prueba funcional ejecutada

El WAR se desplegó en un contenedor temporal Apache Tomcat 10.1.42. Un cliente HTTP automatizado comprobó el siguiente flujo completo:

1. `GET /productos`: respuesta 200 y renderizado del JSP con datos iniciales.
2. `POST /productos/formulario`: creación válida y redirección 302.
3. `GET /productos?q=PRU-900`: búsqueda y visualización del producto creado.
4. `POST /productos`: eliminación válida y redirección 302.
5. Nueva búsqueda: confirmación de que el producto ya no aparece.

**Resultado:** `SMOKE TEST OK`.

## Pruebas manuales sugeridas

1. Abrir el listado y comprobar los tres productos iniciales.
2. Buscar `monitor` y limpiar el filtro.
3. Crear un producto con datos válidos.
4. Intentar crear el mismo código en minúsculas y confirmar el mensaje de validación.
5. Enviar cantidad negativa y precio con tres decimales.
6. Editar el producto recién creado.
7. Cancelar una edición y comprobar que no cambian los datos.
8. Eliminar el producto y verificar el mensaje de confirmación.
9. Reducir el ancho del navegador para comprobar el diseño adaptable.
