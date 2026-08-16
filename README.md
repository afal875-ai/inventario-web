# StockSENA — módulo web de inventario

Proyecto de la evidencia **GA7-220501096-AA2-EV02: Módulos de software codificados y probados**.

**Aprendiz:** Andrés Avendaño López  
**Ficha:** 3235904

## Funcionalidades

- Listar productos y calcular el valor del inventario.
- Buscar por código, nombre o categoría mediante una petición `GET`.
- Crear y editar productos con formulario JSP y petición `POST`.
- Eliminar productos mediante `POST`, con confirmación previa.
- Validar campos obligatorios, cantidades, precios y códigos duplicados.
- Mostrar mensajes de confirmación y conservar los datos ante errores.
- Adaptar la interfaz a computadores y dispositivos móviles.

## Tecnologías

- Java 17.
- Jakarta Servlet 6.0 y Jakarta Server Pages (JSP/JSTL 3.0).
- Maven 3.9 o superior.
- Apache Tomcat 10.1 o superior.
- JUnit 5 para pruebas automatizadas.
- Git para control de versiones.

## Estructura

```text
src/main/java
├── model         Entidades y JavaBeans
├── repository    Acceso a datos en memoria
├── service       Reglas de negocio y validación
└── web           Servlets, filtro y listener
src/main/webapp
├── assets/css    Hoja de estilos
└── WEB-INF/jsp   Vistas JSP protegidas
src/test/java     Pruebas unitarias
docs              Artefactos técnicos de soporte
```

## Compilar y probar

Desde la raíz del proyecto:

```bash
mvn clean test
mvn clean package
```

El segundo comando genera `target/inventario-web.war`.

## Ejecutar con Tomcat

1. Instale Java 17 y Tomcat 10.1.
2. Ejecute `mvn clean package`.
3. Copie `target/inventario-web.war` en la carpeta `webapps` de Tomcat.
4. Inicie Tomcat.
5. Abra `http://localhost:8080/inventario-web/`.

La aplicación carga tres productos de demostración. La persistencia es temporal: los cambios se mantienen mientras el servidor está activo y se reinician al volver a desplegar la aplicación. Esta decisión permite evaluar el módulo sin configurar servicios externos.

## Rutas web y métodos HTTP

| Método | Ruta | Propósito |
|---|---|---|
| GET | `/productos` | Listar y buscar productos (`?q=texto`) |
| GET | `/productos/formulario` | Mostrar formulario de creación |
| GET | `/productos/formulario?id=1` | Mostrar formulario de edición |
| POST | `/productos/formulario` | Crear o actualizar un producto |
| POST | `/productos` | Eliminar un producto (`accion=eliminar`) |

## Documentación incluida

- [Informe técnico](docs/INFORME_TECNICO.md)
- [Historias de usuario y casos de uso](docs/HISTORIAS_Y_CASOS_DE_USO.md)
- [Plan y reporte de pruebas](docs/PRUEBAS.md)
- [Manual de usuario](docs/MANUAL_USUARIO.md)
