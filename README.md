Aplicación: Sistema de facturacion e inventario <br>
1-	Objetivos del software: <br>
Crear un software funcional y robusto que demuestre las habilidades y conocimientos técnicos del desarrollador en el desarrollo de aplicaciones Java para la gestión de negocios.<br>
2-	Análisis de mercado <br>
No aplica, la aplicación es para practica con el objetivo de mostrar habilidades y conocimiento técnico en el uso de herramientas y aplicación de técnicas de programación para la producción de software. <br>
3-	Definición de alcance <br>
a.	Generación de facturas y comprobantes de crédito fiscal.<br>
b.	Actualización automática del inventario en la base de datos al facturar.<br>
c.	Actualización del inventario: disminución de stock de productos vendidos.<br>
d.	Consulta de inventario: disponibilidad de productos y niveles de stock actualizados, al momento de seleccionar un item de la factura
e.	Acceso seguro al sistema mediante autenticación de usuarios.<br>
f.	Diseño intuitivo y fácil de usar para la captura de datos y la visualización de información.<br>
g.	Utilización de una base de datos relacional para el almacenamiento de datos (MYSQL)<br>
h.	Diseño de tablas para almacenar información sobre clientes, productos, facturas e inventario, incluye el uso de store procedures, triggers, views, etc.<br>
i.	Rendimiento: respuesta rápida del sistema durante la generación de facturas y consultas de inventario.<br>
j.	Escalabilidad: capacidad para manejar un volumen creciente de datos y usuarios.<br>
k.	Disponibilidad: garantizar que el sistema esté disponible en todo momento, con mínimos tiempos de inactividad.<br>
l.	Generación de reportes en pantalla y en Excel: total de caja resumido, detalle de ventas con costo. <br>
m.	Edición de facturas, actualización, anulación y eliminación (tomado en cuenta con el stock de los productos para su actualización constante trayendo la lógica del negocio desde la bd)<br>

4-	Interfaz de usuario <br>
 Login:
 <img src="imagenes_repositorio/login.png" alt="login.png" width="1000" height="600">
 Bienvenida:
 <img src="imagenes_repositorio/bienvenida.png" alt="bienvenida.png" width="1000" height="600">
 Facturador de factura de consumidor final:
 <img src="imagenes_repositorio/factura final .png" alt="factura final .png" width="1000" height="600">
 Facturador de factura de credito fiscal:
 <img src="imagenes_repositorio/facturador ccf .png" alt="factura ccf .png" width="1000" height="600">
 Selecccion del cliente: (Opcion similar para seleccionar productos)
 <img src="imagenes_repositorio/seleccionar cliente.png" alt="imagen" width="1000" height="600">
 Opciones de descarga:
 <img src="imagenes_repositorio/sistema de descarga multiple inidades de medida.png" alt="sistema de descarga .png" width="1000" height="600">
 <img src="imagenes_repositorio/no permite la descarga en negativo de productos .png" alt="sistema de descarga .png" width="1000" height="600">
 Edicion de Facturas: 
 <img src="imagenes_repositorio/edicion facturas.png" alt="ventas .png" width="1000" height="600">

 Administrador de ventas:
 <img src="imagenes_repositorio/adm ventas.png" alt="ventas .png" width="1000" height="600">
 Reportes:
 <img src="imagenes_repositorio/total de caja resumido 1.png" alt="reporte .png" width="1000" height="600">
 <img src="imagenes_repositorio/caja resumido 2 .png" alt="reporte .png" width="1000" height="600">
 <img src="imagenes_repositorio/excel generado.png" alt="reporte .png" width="1000" height="600">
 Pantalla de configuracion para especificar las series de los documentos y configurar el ancho de la impresion de la factura: 
 <img src="imagenes_repositorio/pantalla de configuracion .png" alt="conf" width="1000" height="600">
 Funcion de respaldo de bd: 
 <img src="imagenes_repositorio/funcion de respaldo de bd .png" alt="imagen" width="1000" height="600">

 Sobre la base de datos: 
 Resumen: 
 <img src="imagenes_repositorio/resumen de bd .png" alt="imagen" width="1000" height="600">
 Vistas: 
 <img src="imagenes_repositorio/vistas.png" alt="imagen" width="1000" height="600">

 Logica: 
 <img src="imagenes_repositorio/logica de negocio desde bd .png" alt="imagen" width="1000" height="600">

  Disparadores: 
 <img src="imagenes_repositorio/disparadores para actualizacion de kardex, existencias .png" alt="imagen" width="1000" height="600">

Diagrama ER: 
 <img src="imagenes_repositorio/diagrama ER from Workbech.png" alt="imagen" width="1000" height="600">






5-	Recopilación de requerimientos técnicos <br>
-	Entorno de Desarrollo: Netbeans, jdk 11<br>
-	Base de datos: MYSQL, MySQL Workbench, phpmyadmin.<br>
-	Lenguaje de programación: java, uso de la librería swing <br>

