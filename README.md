## Instalación

Esta app funciona con la API del repositorio:
https://github.com/MMAndresC/acceso-datos-AA1/tree/AA1  
El repositorio contiene todo lo necesario para hacer funcionar la base de datos con datos iniciales para realizar las pruebas.

Para que funcionen los mapas de la app es necesario registrarse en Mapbox:  
https://account.mapbox.com/auth/signup/

Una vez registrado generar un token privado, en la seccion Account.
Crear el archivo gradle.properties en la carpeta .gradle:  
`.gradle/gradle.properties`

El contenido del archivo gradle .properties sera:  
`MAPBOX_DOWNLOADS_TOKEN=TOKEN PRIVADO`

## Descripción de la app
La app permite consultar los torneos que existen de un e-sport (competicion de videojuego), en dos vistas, la primera muestra los torneos pendientes de celebrarse y en otra, lista todos los torneos guardados.  
Aparte permite insertar, modificar o borrar los torneos y consultar un torneo en detalle.

También se puede listar los equipos registrados, realizar las operaciones de insercción, modificación, borrado y consultar todos los datos de un equipo.

Permite registrar o loguearse con un usuario, es necesario estar logueado para realizar la operación de borrado tanto en torneos como en equipos, para poder marcar equipos como favoritos y para acceder a la zona personal de usuario.  
La zona de usuario permite la gestion de datos del usuario, referente a su perfil del juego y lista todos los usuarios que estan registrados en ese dispositivo, ya que un mismo jugador puede tener varios perfiles.  
Toda la información de usuario se guarda de forma local.

### Puntos extras

- _Utilizar diálogos siempre que sea necesario (al modificar o eliminar información, por ejemplo)_  
  Dialogos para guardar y borrar la información de usuario y para borrar torneo o equipo.


- _Añadir alguna función que interactúe con otras aplicaciones del dispositivo (cámara, contactos, galería, . . .)_  
  En la zona de usuario se da la opcion de guardar foto de perfil con imagen de galeria o bien usando la camara.


- _Utiliza la herramienta Git (y GitHub) durante todo el desarrollo de la aplicación. Utiliza el gestor de Issues para los problemas/fallos que vayan surgiendo_   
  Repositorio https://github.com/MMAndresC/PMDM-AA1


- _Utilizar imágenes como atributos de algún objeto (y almacenarlo en la base de datos)_  
  El usuario puede guardar una imagen en la base de datos local.


- _Emplear Fragments en el diseño de alguna de las Activities de la aplicación_  
  El mapa está dentro de un fragment que utilizan 3 vistas


- _Hacer uso de mapas en las pantalla donde se registre información. Para introducir información geográfica, por ejemplo_
  En el formulario para dar de entrada un nuevo torneo hay que marcar un punto en el mapa y se guarda el registro con las coordenadas


- _El usuario podrá hacer búsquedas para filtrar la información en los listados de datos_    
  En la vista que lista todos los torneos están las opciones para listar de más recientes a más antiguos y viceversa y a buscar un torneo por nombre


- _Utilizar las credenciales de usuario en la aplicación para comunicarse con la API (si ésta se encuentra securizada con token JWT)_  
  Se guarda el token cuando el usario se loguea en shared preferences y se manda con la petición de borrado a la API


- _El usuario podrá almacenar como “favorito” aquella información de la API que le resulte de interés_  
  Si el usuario está logueado puede marcar/desmarcar un equipo como favorito, cada usuario tiene sus favoritos y los marca en el listado de equipos




