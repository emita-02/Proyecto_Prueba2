Proyecto Prueba 2

Sistema de Gestión de Licencias de Conducir en Ecuador 

Realizado por:
* Emily Pazmiño
* Ariana Villota

El presente proyecto consiste en desarrollar un sistema con Java, implementando Swing para las interfaces y siguiendo una estructura MVC. Simulando los procesos realizados por la Agencia Nacional de Tránsito,
para crear licencias de conducir para las personas que cumplan con los requisitos establecidos.

Se tomó en cuenta el acceso de Administradores y de Analistas al sistema y sus diferentes funciones por realizar, por lo que se implementó un login con opciones de roles para el ingreso y validaciones de
usuario y contraseña.

Se implementó un generador de reportes para visualizar los usuarios creados desde el acceso de los administradores, teniendo como base el código para generar documento PDF de los conductores que 
deseen emitir una licencia.

Tecnologías utilizadas:
* Java con JDK 17: para la codificación.
* Java Swing: para la creación de interfaces.
* PostgreSQL: para la creación de la base de datos, con sus respectivas tablas, declarando las columnas con los datos necesarios.
* Supabase: para desplegar la BD creada.
* JDBC: para la conexión de la BD con el código en Java.

Credenciales de prueba con literales (valores fijos/quemados):
* Rol Administrador:
  * Usuario: Admin
  * Contraseña: admin123

*Rol Analista:
  * Usuario: Analista
  * Contraseña: analista123

 Ejecución del sistema:
 * Clonar el repositorio.
 * Abrir en un entorno de desarrollo que tolere las tecnologías antes mencionadas.
 * Ejecutar la clase Main.java, ubicada en src/main/java/ec.edu.sistemalicencias.



