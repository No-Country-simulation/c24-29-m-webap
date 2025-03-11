# Pruebas en Postman - Ficheck

Este documento describe las pruebas realizadas en Postman sobre la API de Ficheck, disponible en [ficheck.vercel.app](https://ficheck.vercel.app/).

## 📌 Descripción
Se realizaron pruebas para verificar el correcto funcionamiento de los endpoints de la API, asegurando que respondan correctamente a las solicitudes enviadas.

## 🚀 Endpoints Probados

### 1. Autenticación de Usuario
- **URL:** `https://ficheck.vercel.app/api/auth/login`
- **Método:** `POST`
- **Headers:**
  - `Content-Type: application/json`
- **Body (JSON Ejemplo):**
  ```json
  {
    "email": "usuario@example.com",
    "password": "123456"
  }
  ```
- **Respuesta Esperada (200 OK):**
  ```json
  {
    "token": "eyJhbGciOiJI...",
    "user": {
      "id": 1,
      "name": "Usuario Ejemplo",
      "email": "usuario@example.com"
    }
  }
  ```

### 2. Registro de Fichaje
- **URL:** `https://ficheck.vercel.app/api/check-in`
- **Método:** `POST`
- **Headers:**
  - `Authorization: Bearer <TOKEN>`
  - `Content-Type: application/json`
- **Body (JSON Ejemplo):**
  ```json
  {
    "userId": 1,
    "timestamp": "2025-03-11T08:00:00Z"
  }
  ```
- **Respuesta Esperada (201 Created):**
  ```json
  {
    "message": "Check-in registrado con éxito",
    "data": {
      "id": 123,
      "userId": 1,
      "timestamp": "2025-03-11T08:00:00Z"
    }
  }
  ```

## 📊 Resumen de la Prueba
- **Nombre de la Prueba:** FickeckPruebasdeIniciodeSesión
- **Estatus:** Completado
- **Total de pruebas exitosas:** 5
- **Total de pruebas fallidas:** 0
- **Tiempo total de ejecución:** 1200 ms

## 🔍 Detalle de los Endpoints Probados
- **Inicio de Sesión**
  - **Método:** POST
  - **URL:** `https://ficheck.vercel.app/api/auth/login`
  - **Código de respuesta:** 200
  - **Tiempo de respuesta:** 350 ms

- **Registro de Fichaje**
  - **Método:** POST
  - **URL:** `https://ficheck.vercel.app/api/check-in`
  - **Código de respuesta:** 201
  - **Tiempo de respuesta:** 450 ms

- **Consulta de Fichajes**
  - **Método:** GET
  - **URL:** `https://ficheck.vercel.app/api/check-in?userId=1`
  - **Código de respuesta:** 200
  - **Tiempo de respuesta:** 200 ms

## 📷 Capturas de Postman

![image](https://github.com/user-attachments/assets/3aec8ff4-b46c-42ea-871d-096857ddf61a)

