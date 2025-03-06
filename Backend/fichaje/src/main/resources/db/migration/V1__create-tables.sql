
CREATE TABLE usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255),
    email VARCHAR(255) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL
);

CREATE TABLE organizacion (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255),
    numero_registro INT,
    razon_social VARCHAR(255),
    rubro VARCHAR(255),
    usuario_id BIGINT NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

CREATE TABLE estado_enum (
    nombre VARCHAR(20) PRIMARY KEY
);

INSERT INTO estado_enum (nombre) VALUES
    ('ACTIVO'),
    ('VACACIONES'),
    ('SUSPENDIDO'),
    ('BAJA');

CREATE TABLE colaboradores (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    organizacion_id BIGINT NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    dni INT,
    direccion VARCHAR(255) NOT NULL,
    ccodigo_postal VARCHAR(255) NOT NULL,
    telefono VARCHAR(255) NOT NULL,
    correo_electronico VARCHAR(255) NOT NULL,
    fecha_alta DATE NOT NULL,
    estado VARCHAR(20) NOT NULL,
    fecha_baja DATE,
    razon_baja VARCHAR(255),
    cargo VARCHAR(255) NOT NULL,
    frente LONGTEXT,
    FOREIGN KEY (organizacion_id) REFERENCES organizacion(id),
    FOREIGN KEY (estado) REFERENCES estado_enum(nombre)
);

CREATE TABLE asistencias (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    organizacion_id BIGINT NOT NULL,
    colaborador_id BIGINT NOT NULL,
    fecha_registro TIMESTAMP,
    entrada TIMESTAMP,
    salida TIMESTAMP,
    justificacion VARCHAR(255),
    presente BOOLEAN DEFAULT FALSE,
    es_extra BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (organizacion_id) REFERENCES organizacion(id),
    FOREIGN KEY (colaborador_id) REFERENCES colaboradores(id)
);

CREATE INDEX idx_colaboradores_organizacion ON colaboradores (organizacion_id);
CREATE INDEX idx_asistencias_colaborador ON asistencias (colaborador_id);
CREATE INDEX idx_asistencias_organizacion ON asistencias (organizacion_id);
CREATE INDEX idx_organizacion_usuario ON organizacion (usuario_id);