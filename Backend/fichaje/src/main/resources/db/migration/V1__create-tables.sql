CREATE TABLE organizacion (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    numero INT NOT NULL UNIQUE,
    siglas_org VARCHAR(255),
    responsable VARCHAR(255) NOT NULL,
    rubro VARCHAR(255),
    telefono VARCHAR(20),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    instagram VARCHAR(255),
    facebook VARCHAR(255),
    x VARCHAR(255)
);

CREATE TABLE sectores (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    tareas TEXT,
    organizacion_id BIGINT NOT NULL,
    FOREIGN KEY (organizacion_id) REFERENCES organizacion(id) ON DELETE CASCADE
);

CREATE TABLE sesion (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    organizacion_id BIGINT NOT NULL,
    sesion_key VARCHAR(255) NOT NULL UNIQUE,
    inico TIMESTAMP,
    fin TIMESTAMP,
    FOREIGN KEY (organizacion_id) REFERENCES organizacion(id) ON DELETE CASCADE
);

CREATE TABLE colaboradores (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    organizacion_id BIGINT NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    dni INT NOT NULL UNIQUE,
    direccion VARCHAR(255),
    codigo_postal VARCHAR(20),
    telefono VARCHAR(20),
    correo_electronico VARCHAR(255) UNIQUE,
    fecha_alta TIMESTAMP,
    estado ENUM('ACTIVO', 'VACACIONES', 'BAJA'),
    fecha_baja TIMESTAMP,
    razon_baja TEXT,
    sector_id BIGINT NOT NULL,
    cargo VARCHAR(255),
    frente LONGTEXT,
    FOREIGN KEY (organizacion_id) REFERENCES organizacion(id) ON DELETE CASCADE,
    FOREIGN KEY (sector_id) REFERENCES sectores(id) ON DELETE SET NULL
);

CREATE TABLE asistencias (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    organizacion_id BIGINT NOT NULL,
    colaborador_id BIGINT NOT NULL,
    sesion_id BIGINT,
    fecha_registro NOT NULL DEFAULT (CURRENT_DATE),
    entrada TIMESTAMP NULL DEFAULT NULL,
    salida TIMESTAMP NULL DEFAULT NULL,
    justificacion TEXT NULL,
    presente BOOLEAN NOT NULL DEFAULT FALSE,
    es_extra BOOLEAN NOT NULL DEFAULT (DAYWEEK(CURRENT_DATE) IN (1,7)),
    FOREIGN KEY (organizacion_id) REFERENCES organizacion(id) ON DELETE CASCADE,
    FOREIGN KEY (colaborador_id) REFERENCES colaboradores(id) ON DELETE CASCADE,
    FOREIGN KEY (sesion_id) REFERENCES sesion(id) ON DELETE SET NULL
);