# Proyecto Final Martín Ferreyra

Este proyecto es un sistema de facturación. Permite gestionar clientes, productos, y la gestión de facturas con sus respectivos detalles.

## Características

- **Gestión de Clientes**: Crear, leer, actualizar y eliminar clientes.
- **Gestión de Productos**: Crear, leer, actualizar y eliminar productos.

  &#x20;
- **Facturas**:&#x20;
  - Obtener el detalle de todas las facturas o una factura específica por su ID.
  - Generación de facturas con detalles de productos y cálculo automático del monto total de la factura y cantidad total de productos además actualizacion de stock de productos y validación de stock existente para cobertura de la factura.
  - Eliminación de una Factura con actualización de stock.
  - Modificacion de detalles o cliente de una factura actualizando (reponiendo o extrayendo segun sea el caso) stock de producto asi como tambien verificando el cliente.\


## Estructura del Proyecto

- **Models**: Clases de entidad como Cliente, Producto, Factura y DetalleFactura.
- **DTOs**: Clases de transferencia de datos, como FacturaDTO y DetalleFacturaDTO.
- **Repositories**: Interfaces que extienden JpaRepository para la persistencia de datos.
- **Services**: Servicios que contienen la lógica de negocio.
- **Controllers**: Controladores que manejan las peticiones HTTP.

## &#x20;
## EndPoints
#### Disponibles por Postman importando Json ubicado en la carpeta Archivos del proyecto. O bien por Swagger

## Licencia

Este proyecto está licenciado bajo la Licencia MIT. Consulta el archivo `LICENSE` para obtener más detalles.
