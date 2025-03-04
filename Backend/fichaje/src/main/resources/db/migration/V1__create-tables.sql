
CREATE TABLE organizacion (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255),
    numero INT,
    siglasOrg VARCHAR(100),
    responsable VARCHAR(255),
    rubro VARCHAR(255),
    telefono VARCHAR(50),
    email VARCHAR(255),
    password VARCHAR(255),

    instagram VARCHAR(255),
    facebook VARCHAR(255),
    twitter VARCHAR(255)
);

CREATE TABLE sectores (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255),
    tareas VARCHAR(255),
    organizacion_id BIGINT NOT NULL,
    CONSTRAINT fk_sectores_organizacion
        FOREIGN KEY (organizacion_id) REFERENCES organizacion(id)
);

CREATE TABLE colaboradores (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    organizacion_id BIGINT NOT NULL,
    nombre VARCHAR(255),
    dni INT,
    direccion VARCHAR(255),
    codigo_postal VARCHAR(20),
    telefono VARCHAR(50),
    correoElectronico VARCHAR(255),
    fechaAlta DATETIME,
    estado VARCHAR(50),
    fechaBaja DATETIME,
    razonBaja VARCHAR(255),
    sector_id BIGINT NOT NULL,
    cargo VARCHAR(255),
    frente TEXT,
    CONSTRAINT fk_colaboradores_organizacion
        FOREIGN KEY (organizacion_id) REFERENCES organizacion(id),
    CONSTRAINT fk_colaboradores_sectores
        FOREIGN KEY (sector_id) REFERENCES sectores(id)
);

CREATE TABLE sesion (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    organizacion_id BIGINT NOT NULL,
    sesionKey VARCHAR(255) NOT NULL,
    inico DATETIME,
    fin DATETIME,
    CONSTRAINT uq_sesionKey UNIQUE (sesionKey),
    CONSTRAINT fk_sesion_organizacion
        FOREIGN KEY (organizacion_id) REFERENCES organizacion(id)
);

CREATE TABLE asistencias (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    organizacion_id BIGINT NOT NULL,
    colaborador_id BIGINT NOT NULL,
    sesion_id BIGINT,
    fechaRegistro DATETIME,
    entrada DATETIME,
    salida DATETIME,
    justificacion VARCHAR(255),
    presente BOOLEAN,
    esExtra BOOLEAN,
    CONSTRAINT fk_asistencias_organizacion
        FOREIGN KEY (organizacion_id) REFERENCES organizacion(id),
    CONSTRAINT fk_asistencias_colaborador
        FOREIGN KEY (colaborador_id) REFERENCES colaboradores(id),
    CONSTRAINT fk_asistencias_sesion
        FOREIGN KEY (sesion_id) REFERENCES sesion(id)
);
