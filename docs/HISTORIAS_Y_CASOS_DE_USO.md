# Historias de usuario y casos de uso

## Actor principal

**Auxiliar de inventario:** persona responsable de consultar y mantener actualizado el catálogo de productos.

## Historias de usuario

### HU-01 — Consultar productos

Como auxiliar de inventario, quiero visualizar los productos registrados para conocer sus cantidades, precios y valor total.

**Criterios de aceptación:**

1. El sistema muestra código, nombre, categoría, cantidad, precio y subtotal.
2. Los productos aparecen ordenados alfabéticamente.
3. El sistema muestra la cantidad de productos y el valor acumulado.

### HU-02 — Buscar productos

Como auxiliar de inventario, quiero buscar por código, nombre o categoría para encontrar rápidamente un producto.

**Criterios de aceptación:**

1. La búsqueda se envía mediante GET.
2. No distingue mayúsculas de minúsculas.
3. Se informa cuando no existen coincidencias.

### HU-03 — Registrar producto

Como auxiliar de inventario, quiero registrar un producto para incorporarlo al catálogo.

**Criterios de aceptación:**

1. Código, nombre, categoría, cantidad y precio son obligatorios.
2. La cantidad y el precio no aceptan valores negativos.
3. El código no puede repetirse.
4. Si ocurre un error, los valores escritos permanecen en el formulario.

### HU-04 — Editar producto

Como auxiliar de inventario, quiero modificar un producto para corregir o actualizar sus datos.

**Criterios de aceptación:**

1. El formulario presenta los datos actuales.
2. Se aplican las mismas validaciones del registro.
3. Después de guardar, el listado muestra los cambios.

### HU-05 — Eliminar producto

Como auxiliar de inventario, quiero eliminar un producto que ya no se maneja para mantener limpio el catálogo.

**Criterios de aceptación:**

1. La interfaz solicita confirmación.
2. La operación se envía mediante POST.
3. El sistema informa que la eliminación fue correcta.

## Casos de uso

| Código | Caso de uso | Entrada | Flujo principal | Resultado |
|---|---|---|---|---|
| CU-01 | Listar productos | GET `/productos` | Consultar, ordenar y presentar | Tabla y resumen |
| CU-02 | Buscar productos | Texto `q` | Filtrar código, nombre o categoría | Coincidencias visibles |
| CU-03 | Crear producto | Formulario POST | Validar, crear y redirigir | Producto registrado |
| CU-04 | Editar producto | ID y formulario POST | Cargar, validar y actualizar | Producto actualizado |
| CU-05 | Eliminar producto | ID y acción POST | Verificar y eliminar | Producto retirado |

## Reglas de negocio

- RN-01: el código debe tener entre 3 y 20 caracteres y ser único.
- RN-02: nombre y categoría son obligatorios.
- RN-03: la cantidad debe ser un número entero mayor o igual que cero.
- RN-04: el precio debe ser mayor o igual que cero y tener máximo dos decimales.
- RN-05: el valor de inventario es `cantidad × precio`.
- RN-06: no se puede editar ni eliminar un identificador inexistente.
