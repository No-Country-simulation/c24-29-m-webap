# Fichaje - Sistema de Control de Asistencias

La aplicación **Fichaje** es un sistema de control de asistencias y gestión de colaboradores desarrollado en Java con Spring Boot. Permite el registro de entradas y salidas mediante reconocimiento facial, la administración de colaboradores y organizaciones, y la generación de códigos QR para facilitar el fichaje en diferentes dispositivos.

## Características Principales

- **Registro de Asistencia:**  
  - Se registra la entrada y salida de colaboradores mediante la validación de imágenes.
  - Se determina el tipo de registro (entrada o salida) basándose en el estado actual del colaborador.

- **Autenticación y Seguridad:**  
  - Uso de JWT para la autenticación y autorización en los endpoints.
  - Validación del token en cada petición sensible mediante un servicio dedicado.

- **Gestión de Colaboradores y Usuarios:**  
  - Registro, actualización y eliminación de colaboradores.
  - Actualización de datos personales, imagen y cambio de estado (activo, inactivo, baja).
  - Registro y administración de usuarios que pueden gestionar organizaciones.

- **Gestión de Organizaciones:**  
  - Registro de nuevas organizaciones.
  - Consulta de colaboradores asociados y generación de reportes estadísticos.
  - Eliminación y búsqueda de organizaciones mediante números de registro.

- **Generación de Códigos QR:**  
  - Generación de imágenes PNG con códigos QR que contienen URL con parámetros para el registro rápido de asistencias.

## Tecnologías Utilizadas

- **Java 17**
- **Spring Boot:** Creación de endpoints REST y manejo de la lógica de negocio.
- **Spring Security y JWT:** Para la autenticación y autorización.
- **Hibernate y JPA:** Acceso a la base de datos.
- **ZXing:** Generación de códigos QR.
- **Validaciones:** Uso de anotaciones como `@NotBlank`, `@Email`, `@NotNull`, etc., para garantizar la integridad de los datos.

## Estructura del Proyecto

La aplicación se organiza en varios controladores y servicios que exponen la funcionalidad a través de endpoints REST. A continuación, se describen los principales controladores, la funcionalidad que ofrecen y la estructura de JSON esperada en cada caso.

## 1. AsistenciaController

- **POST `/asistencia/login`**  
  Registra la asistencia de un colaborador.  
  **Estructura de JSON esperada (AsistenciaDTO):**
  
  ```json
  {
    "organizacionId": 1,
    "imagenCapturada": "base64EncodedImage..."
  }
  ```

### Valida el token de autenticación

Identifica al colaborador mediante la imagen capturada y el ID de la organización.

Registra la entrada o salida según el estado actual del colaborador.

**GET '/asistencia/reporte'**
Obtiene reportes estadísticos de asistencia de una organización en un período determinado.
**Estructura de JSON esperada (ReporteAsistenciaDTO):**

```json
    {
      "organizacionId": 1,
      "periodo": "mes"
    }
```

**GET '/asistencia/{colaboradorId}/estadisticas'**
   Proporciona estadísticas de asistencia específicas para un colaborador.
    Parámetro opcional: periodo (por defecto "mes").

### 2. AutenticacionController

**POST '/usuario/login'**
    Realiza la autenticación del usuario utilizando correo y contraseña.
    Estructura de JSON esperada (LoginUsuarioDTO):

```json
{
  "email": "[http://usuario@example.com](usuario@example.com)",
  "contrasena": "password"
}
```

Se genera y retorna un token JWT (respuesta tipo LoginResponse):

```json
    {
      "token": "jwtTokenString..."
    }
```

### 3. Controladores de Colaboradores y Usuarios

## Registro de Colaborador

Endpoint:
**POST '(por ejemplo, /usuario/regColab)'**

Estructura de JSON esperada (DtoRegColab):

```json
{
  "nombre": "Ana García",
  "dni": 12345678,
  "direccion": "Avenida Siempre Viva 742",
  "codigoPostal": "54321",
  "telefono": "5559876543",
  "correoElectronico": "[http://ana.garcia@example.com](ana.garcia@example.com)",
  "fechaAlta": "2025-01-15",
  "estado": "ACTIVO",
  "organizacionId": 1,
  "cargo": "Desarrolladora",
  "frente": "base64EncodedImage..."
}
```

## Actualizar Imagen del Colaborador

Endpoint:
**PUT /usuario/{id}/imagen**
Estructura de JSON esperada (ImagenDto):

```json
{
  "imagen": "base64EncodedImage..."
}
```

## Actualizar Datos Personales del Colaborador

Endpoint:
**PUT (por ejemplo, /usuario/{id})**
Estructura de JSON esperada (ActualizarColaboradorDTO):

```json
{
  "nombre": "Juan Pérez",
  "direccion": "Calle 123",
  "codigoPostal": "12345",
  "telefono": "5551234567",
  "correoElectronico": "[http://juan.perez@example.com](juan.perez@example.com)"
}
```

## Actualizar Datos del Usuario

Endpoint:
**PUT /usuario/{id}**
Estructura de JSON esperada (ActualizarUsuarioDTO):

```json
{
  "nombre": "Usuario Actualizado",
  "contrasena": "nuevaContrasena123"
}
```

## Cambio de Estado del Colaborador

Endpoint:
**PUT /usuario/{id}/estado**
Estructura de JSON esperada (CambioEstadoDTO):

```json
{
  "estado": "INACTIVO",
  "razonBaja": "Renuncia voluntaria",
  "fechaBaja": "2025-03-01"
}
```

## Justificación de Inasistencia

Endpoint:
**POST /usuario/{id}/justificar/{asistenciaId}**
Estructura de JSON esperada (JustificacionDTO):

```json
{
  "justificacion": "Motivo justificación: Ausencia por enfermedad"
}
```

## Registro de Usuario

Endpoint:
**POST /usuario**
Estructura de JSON esperada (RegistroUsuarioDTO):

```json
    {
      "nombre": "Usuario Nuevo",
      "email": "[http://nuevo@ejemplo.com](nuevo@ejemplo.com)",
      "contrasena": "contraseñaSegura123"
    }
```

### 4. OrganizacionController

## Registro de Organización

Endpoint:
**POST /organizacion**
Estructura de JSON esperada (DtoRegOrg):

```json
    {
      "nombre": "Organización XYZ",
      "numeroRegistro": 101,
      "razonSocial": "Organización XYZ S.A.",
      "rubro": "Tecnología",
      "usuarioId": 1
    }
```

## Listado de Colaboradores de una Organización

Endpoint:
**GET /organizacion/{id}/colaboradores**

## Obtener Estadísticas de Asistencia de la Organización

Endpoint:
**GET /organizacion/{id}/estadisticas**
Se puede enviar el parámetro opcional periodo (por ejemplo, "mes").

## Obtener ID de Organización por Número de Registro

Endpoint:
**GET /organizacion/organizaciones/buscar**
Parámetro de query: numeroRegistro

## Eliminar Organización

Endpoint:
**DELETE /organizacion/organizaciones/{id}**

### 5. QrController

## Generación de Código QR

Endpoint:
**GET /qr/generate**
Parámetro requerido: organizacionId (en la URL)
Retorna una imagen en formato PNG que contiene una URL con el parámetro organizacionId para el registro de asistencia.

Estructura de JSON Esperada (Resumen de DTOs)

**AsistenciaDTO:**

```json
{
  "organizacionId": 1,
  "imagenCapturada": "base64EncodedImage..."
}
```

**ActualizarColaboradorDTO:**

```json
{
  "nombre": "Juan Pérez",
  "direccion": "Calle 123",
  "codigoPostal": "12345",
  "telefono": "5551234567",
  "correoElectronico": "[http://juan.perez@example.com](juan.perez@example.com)"
}
```

**ActualizarUsuarioDTO:**

```json
{
  "nombre": "Usuario Actualizado",
  "contrasena": "nuevaContrasena123"
}
```

**CambioEstadoDTO:**

```json
{
  "estado": "INACTIVO",
  "razonBaja": "Renuncia voluntaria",
  "fechaBaja": "2025-03-01"
}
```

DtoRegColab:

```json
{
  "nombre": "Ana García",
  "dni": 12345678,
  "direccion": "Avenida Siempre Viva 742",
  "codigoPostal": "54321",
  "telefono": "5559876543",
  "correoElectronico": "[http://ana.garcia@example.com](ana.garcia@example.com)",
  "fechaAlta": "2025-01-15",
  "estado": "ACTIVO",
  "organizacionId": 1,
  "cargo": "Desarrolladora",
  "frente": "base64EncodedImage..."
}
```

**DtoRegOrg:**

```json
{
  "nombre": "Organización XYZ",
  "numeroRegistro": 101,
  "razonSocial": "Organización XYZ S.A.",
  "rubro": "Tecnología",
  "usuarioId": 1
}
```

**EstadisticasDTO:**

```json
{
  "periodo": "mes",
  "organizacionId": 1
}
```

**ImagenDto:**

```json
{
  "imagen": "base64EncodedImage..."
}
```

**JustificacionDTO:**

```json
{
  "justificacion": "Motivo justificación: Ausencia por enfermedad"
}
```

**LoginUsuarioDTO:**

```json
{
  "email": "[http://usuario@example.com](usuario@example.com)",
  "contrasena": "password"
}
```

**RegistroUsuarioDTO:**

```json
{
  "nombre": "Usuario Nuevo",
  "email": "[http://nuevo@ejemplo.com](nuevo@ejemplo.com)",
  "contrasena": "contraseñaSegura123"
}
```

**ReporteAsistenciaDTO:**

```json
{
  "organizacionId": 1,
  "periodo": "mes"
}
```

**LoginResponse (Respuesta del login):**

```json
    {
      "token": "jwtTokenString..."
    }
```

### Seguridad

La aplicación utiliza JWT para asegurar los endpoints. Cada petición que modifique o consulte datos sensibles requiere incluir el token en el header "Authorization". El TokenService se encarga de validar el token y extraer la información del usuario.
Cómo Ejecutar la Aplicación
Prerrequisitos

- Java 17 o superior
- Maven o Gradle (según la configuración del proyecto)
- Base de datos: Configurada en el archivo de propiedades de Spring Boot (por ejemplo, MySQL o PostgreSQL).

### Pasos para Ejecutar

**Clonar el repositorio:**

```bash
git clone https://github.com/tu-usuario/fichaje.git
cd fichaje
```

**Construir el proyecto:**

```bash
mvn clean install
```

**Ejecutar la aplicación:**

```bash
    mvn spring-boot:run
```

**Acceso a los Endpoints:**
    Utiliza herramientas como Postman o cURL para realizar peticiones a los distintos endpoints por ejemplo, [http://localhost:8080/asistencia/login](http://localhost:8080/asistencia/login).

Ejemplos de Uso

## Registro de Asistencia

```bash
curl -X POST http://localhost:8080/asistencia/login \
-H "Content-Type: application/json" \
-H "Authorization: Bearer (TOKEN)" \
-d '{
    "organizationId": 1,
    "imagenCapturada": "base64EncodedImage..."
}'
```

## Autenticación de Usuario

```bash
curl -X POST "http://localhost:8080/usuario/login" \
-H "Content-Type: application/json" \
-d '{
  "email": "usuario@example.com",
  "contrasena": "password"
}'
```

***Generación de Código QR***

```bash
curl -X GET "http://localhost:8080/qr/generate?organizacionId=1" \
-H "Authorization: Bearer <TOKEN>" \
--output qr.png
```

### Consideraciones Adicionales

## Manejo de Excepciones y Validaciones

Los controladores implementan un manejo de excepciones robusto para proporcionar mensajes de error claros en casos de datos inválidos, token expirado o errores internos.

**Transaccionalidad:**

Se utiliza '@Transactional' en los métodos que realizan operaciones de escritura para garantizar la integridad de los datos.

## Documentación y Pruebas

Se recomienda integrar herramientas como Swagger para documentar y probar de forma interactiva los endpoints

## Contribuciones

Las contribuciones son bienvenidas. Si deseas mejorar la aplicación o corregir errores, realiza un fork del repositorio y envía un pull request.
Licencia

### Este proyecto se distribuye bajo la 'MIT' License

## API desarrollada por Emanuel Peracchia 
