
-- Crear tabla de usuarios
CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL
);

-- Crear tabla de organizaciones
CREATE TABLE organizaciones (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    numero_registro INT NOT NULL,
    razon_social VARCHAR(255) NOT NULL,
    rubro VARCHAR(255) NOT NULL,
    usuario_id BIGINT NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- Crear tabla de colaboradores
CREATE TABLE colaboradores (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    organizacion_id BIGINT NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    dni INT NOT NULL UNIQUE,
    direccion VARCHAR(255) NOT NULL,
    codigo_postal VARCHAR(20) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    correo_electronico VARCHAR(255) NOT NULL,
    fecha_alta DATE NOT NULL,
    estado ENUM('ACTIVO', 'VACACIONES', 'SUSPENDIDO', 'BAJA', 'LICENCIA') NOT NULL,
    fecha_baja DATE,
    razon_baja VARCHAR(255),
    cargo VARCHAR(255) NOT NULL,
    frente LONGTEXT,
    FOREIGN KEY (organizacion_id) REFERENCES organizaciones(id)
);

-- Crear tabla de asistencias
CREATE TABLE asistencias (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    organizacion_id BIGINT NOT NULL,
    colaborador_id BIGINT NOT NULL,
    fecha_registro TIMESTAMP NOT NULL,
    entrada TIMESTAMP,
    salida TIMESTAMP,
    justificacion VARCHAR(255),
    presente BOOLEAN DEFAULT FALSE,
    es_extra BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (organizacion_id) REFERENCES organizaciones(id),
    FOREIGN KEY (colaborador_id) REFERENCES colaboradores(id)
);
