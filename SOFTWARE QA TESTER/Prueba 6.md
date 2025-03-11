# Pruebas en JMeter - Ficheck

Este documento describe las pruebas realizadas en JMeter sobre la API de Ficheck, disponible en [ficheck.vercel.app](https://ficheck.vercel.app/).

## 📌 Descripción

Se realizaron pruebas para verificar el correcto funcionamiento de los endpoints de la API, asegurando que respondan correctamente a las solicitudes enviadas.

## 🚀 Pruebas en JMeter

### Configuración de la Prueba

- **Herramienta utilizada:** Apache JMeter
- **Usuarios concurrentes simulados:** 50
- **Duración total de la prueba:** 5 minutos

### Resultados de la Prueba

- **Número total de solicitudes:** 1000
- **Tasa de éxito:** 98%
- **Tiempo de respuesta promedio:** 480 ms
- **Errores detectados:** 2%

### Detalle de Endpoints Probados en JMeter

- **Inicio de Sesión**

  - **Método:** POST
  - **URL:** `https://ficheck.vercel.app/api/auth/login`
  - **Tiempo de respuesta promedio:** 320 ms
  - **Éxito:** 99%

- **Registro de Fichaje**

  - **Método:** POST
  - **URL:** `https://ficheck.vercel.app/api/check-in`
  - **Tiempo de respuesta promedio:** 520 ms
  - **Éxito:** 97%

- **Consulta de Fichajes**

  - **Método:** GET
  - **URL:** `https://ficheck.vercel.app/api/check-in?userId=1`
  - **Tiempo de respuesta promedio:** 600 ms
  - **Éxito:** 96%

## 📷 Capturas de JMeter

![image](https://github.com/user-attachments/assets/bbc9c682-5a32-4db6-af32-63ad0c714df8)


## ⚠️ Errores Encontrados

- En JMeter, se observaron algunas respuestas con código `500 Internal Server Error` cuando la carga de usuarios concurrentes superaba los 45 simultáneos.

