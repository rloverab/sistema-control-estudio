/**************************************************
Script de creación de la base de datos para el 
Sistema de Control de Estudios del CUR May Hamilton

Autor: Roger Lovera <rloverab@yahoo.es>

Creado: 09/02/2021
Actualizado: 25/11/2021
**************************************************/

# Borrar base de datos "scedb" (sólo si existe)
/*
    NOTA: Esta sentencia borra la base de datos existente.
    
    Solo para fines de pruebas y desarrollo.

    Usar con precaución.

    Está sentencia está comentada por defecto(#)
*/
#DROP DATABASE IF EXISTS scedb;

# Crear base de datos "scedb"
CREATE SCHEMA IF NOT EXISTS scedb;

# Seleccionar base de datos "scedb"
USE scedb;

# Crear el usuario de acceso de la base de datos
CREATE USER IF NOT EXISTS 'sceusuario'@'%';
CREATE USER IF NOT EXISTS 'sceusuario'@'localhost';
ALTER USER 'sceusuario'@'%'
IDENTIFIED BY 'cambiame' ;
ALTER USER 'sceusuario'@'localhost'
IDENTIFIED BY 'cambiame' ;

# Establecer permisos para usuario creado
GRANT Execute ON scedb.* TO 'sceusuario'@'%';
GRANT Execute ON scedb.* TO 'sceusuario'@'localhost';

/*------------------
------ TABLAS ------
------------------*/

/*************************
Tablas sin claves foraneas
*************************/

# Crear tabla "sexos"
CREATE TABLE IF NOT EXISTS sexos (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    sexo VARCHAR(9) NOT NULL,
    creado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    INDEX (sexo)
) ENGINE=InnoDB;

# Crear tabla "estados_civiles"
CREATE TABLE IF NOT EXISTS estados_civiles (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    estado_civil VARCHAR(15) NOT NULL,
    creado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    INDEX (estado_civil)
) ENGINE=InnoDB;

# Crear tabla "estados"
CREATE TABLE IF NOT EXISTS estados (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    estado VARCHAR(20) NOT NULL,
    creado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    INDEX (estado)
) ENGINE=InnoDB;

# Crear tabla "municipios"
CREATE TABLE IF NOT EXISTS municipios (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    municipio VARCHAR(30) NOT NULL,
    creado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    INDEX (municipio)
) ENGINE=InnoDB;

# Crear tabla "parroquias"
CREATE TABLE IF NOT EXISTS parroquias (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    parroquia VARCHAR(60) NOT NULL,
    creado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    INDEX (parroquia)
) ENGINE=InnoDB;
    
# Crear tabla "carreras"
CREATE TABLE IF NOT EXISTS carreras (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    carrera VARCHAR(45) NOT NULL,    
    creado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    INDEX (carrera)
) ENGINE=InnoDB;

# Crear tabla "niveles"
CREATE TABLE IF NOT EXISTS niveles (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    nivel VARCHAR(50) NOT NULL,
    orden INT UNSIGNED NOT NULL,
    creado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    INDEX (nivel)
) ENGINE=InnoDB;

# Crear tabla "grados"
CREATE TABLE IF NOT EXISTS grados (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    grado VARCHAR(50) NOT NULL,
    creado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    INDEX (grado)
) ENGINE=InnoDB;

# Crear tabla "unidades"
CREATE TABLE IF NOT EXISTS unidades (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    codigo VARCHAR(16) COLLATE utf8mb4_bin NOT NULL, # Case Sensitive
    unidad VARCHAR(120) NOT NULL,
    creado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    INDEX (codigo,unidad)
) ENGINE=InnoDB;

# Crear tabla "modulos"
CREATE TABLE IF NOT EXISTS modulos (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,    
    modulo VARCHAR(200) NOT NULL,
    creado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    INDEX (modulo)
) ENGINE=InnoDB;

# Crear tabla "condiciones"
CREATE TABLE IF NOT EXISTS condiciones (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    condicion VARCHAR(18) NOT NULL,
    creado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    INDEX (condicion)
) ENGINE=InnoDB;

# Crear tabla "detalles"
CREATE TABLE IF NOT EXISTS detalles (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    detalle VARCHAR(15) NOT NULL,
    creado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    INDEX (detalle)
) ENGINE=InnoDB;

# Crear tabla "periodos"
CREATE TABLE IF NOT EXISTS periodos (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    periodo VARCHAR(12) NOT NULL,
    fecha_inicial DATE NOT NULL,
    fecha_final DATE NOT NULL,    
    creado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    INDEX (periodo)
) ENGINE=InnoDB;

# Crear tabla "etnias"
CREATE TABLE IF NOT EXISTS etnias (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    etnia VARCHAR(37) NOT NULL,
    creado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,    
    PRIMARY KEY (id),
    INDEX (etnia)
) ENGINE=InnoDB;

# Crear tabla "documentos"
CREATE TABLE IF NOT EXISTS documentos (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    documento VARCHAR(60) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    creado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    INDEX (documento)
) ENGINE=InnoDB;

# Crear tabla "resoluciones"
CREATE TABLE IF NOT EXISTS resoluciones (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    resolucion SMALLINT UNSIGNED NOT NULL,
    acta SMALLINT UNSIGNED NOT NULL,
    fecha DATE NOT NULL,
    creado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    INDEX (resolucion, acta, fecha)
) ENGINE=InnoDB;

/***************************
Tablas con claves foraneas
***************************/

# Crear tabla "ubicaciones_geograficas"
CREATE TABLE IF NOT EXISTS ubicaciones_geograficas (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    estado_id INT UNSIGNED NOT NULL,
    municipio_id INT UNSIGNED NOT NULL,
    parroquia_id INT UNSIGNED NOT NULL,    
    creado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),        
    FOREIGN KEY (estado_id) REFERENCES estados(id),
    FOREIGN KEY (municipio_id) REFERENCES municipios (id),
    FOREIGN KEY (parroquia_id) REFERENCES parroquias (id)
) ENGINE=InnoDB;

# Crear tabla "personas"
CREATE TABLE IF NOT EXISTS personas (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    cedula VARCHAR(9) NOT NULL,
    nombre1 VARCHAR(15) NOT NULL,
    nombre2 VARCHAR(15) NULL DEFAULT NULL,
    apellido1 VARCHAR(15) NOT NULL,
    apellido2 VARCHAR(15) NULL DEFAULT NULL,
    sexo_id INT UNSIGNED NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    lugar_nacimiento VARCHAR(40) NOT NULL,
    estado_civil_id INT UNSIGNED NOT NULL,
    etnia_id INT UNSIGNED NOT NULL,
    ubicacion_geografica_id INT UNSIGNED NOT NULL,
    direccion VARCHAR(60) NULL DEFAULT NULL,
    telefono_local VARCHAR(16) NULL DEFAULT NULL,
    telefono_movil VARCHAR(16) NULL DEFAULT NULL,
    correo_electronico VARCHAR(320) NULL DEFAULT NULL,
    creado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    INDEX (cedula),
    FOREIGN KEY (sexo_id) REFERENCES sexos (id),
    FOREIGN KEY (estado_civil_id) REFERENCES estados_civiles (id),
    FOREIGN KEY (etnia_id) REFERENCES etnias (id),
    FOREIGN KEY (ubicacion_geografica_id) REFERENCES ubicaciones_geograficas (id)
) ENGINE=InnoDB;

# Crear tabla "docentes"
CREATE TABLE IF NOT EXISTS docentes (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    persona_id INT UNSIGNED NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT FALSE,
    creado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (persona_id) REFERENCES personas (id)
) ENGINE=InnoDB;

# Crear tabla "planes_estudio"
CREATE TABLE IF NOT EXISTS planes_estudio (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    carrera_id INT UNSIGNED NOT NULL,
    nivel_id INT UNSIGNED NOT NULL,
    grado_id INT UNSIGNED NULL DEFAULT NULL,
    unidad_id INT UNSIGNED NOT NULL,
    resolucion_id INT UNSIGNED NOT NULL,
    uc SMALLINT UNSIGNED NOT NULL,
    creado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (carrera_id) REFERENCES carreras (id),
    FOREIGN KEY (nivel_id) REFERENCES niveles (id),
    FOREIGN KEY (grado_id) REFERENCES grados (id),
    FOREIGN KEY (unidad_id) REFERENCES unidades (id),
    FOREIGN KEY (resolucion_id) REFERENCES resoluciones (id)
) ENGINE=InnoDB;

# Crear tabla "planes_estudio_modulos"
CREATE TABLE IF NOT EXISTS planes_estudio_modulos (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    plan_estudio_id INT UNSIGNED NOT NULL,
    modulo_id INT UNSIGNED NULL DEFAULT NULL,
    resolucion_id INT UNSIGNED NOT NULL,
    hta SMALLINT UNSIGNED NOT NULL,
    htas SMALLINT UNSIGNED NOT NULL,
    creado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),    
    FOREIGN KEY (plan_estudio_id) REFERENCES planes_estudio (id),
    FOREIGN KEY (modulo_id) REFERENCES modulos (id),
    FOREIGN KEY (resolucion_id) REFERENCES resoluciones (id)
) ENGINE=InnoDB;

# Crear tabla "prelaciones"
CREATE TABLE IF NOT EXISTS prelaciones (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    plan_estudio_id_1 INT UNSIGNED NOT NULL,
    plan_estudio_id_2 INT UNSIGNED NULL DEFAULT NULL,    
    creado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    INDEX (plan_estudio_id_1,plan_estudio_id_2),
    FOREIGN KEY (plan_estudio_id_1) REFERENCES planes_estudio (id),
    FOREIGN KEY (plan_estudio_id_2) REFERENCES planes_estudio (id)
) ENGINE=InnoDB;
    
# Crear tabla "ofertas_academicas"
CREATE TABLE IF NOT EXISTS ofertas_academicas (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    plan_estudio_modulo_id INT UNSIGNED NOT NULL,   
    docente_id INT UNSIGNED NULL DEFAULT NULL,
    periodo_id INT UNSIGNED NOT NULL,         
    cupos SMALLINT UNSIGNED NOT NULL,
    numero_seccion SMALLINT UNSIGNED NOT NULL,
    nomenclatura SMALLINT UNSIGNED NOT NULL,
    creado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),    
    FOREIGN KEY (docente_id) REFERENCES docentes (id),
    FOREIGN KEY (periodo_id) REFERENCES periodos (id),
    FOREIGN KEY (plan_estudio_modulo_id) REFERENCES planes_estudio_modulos (id)
) ENGINE=InnoDB;

# Crear tabla "condiciones_detalles"    
CREATE TABLE IF NOT EXISTS condiciones_detalles (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    condicion_id INT UNSIGNED NOT NULL,
    detalle_id INT UNSIGNED NULL DEFAULT NULL,    
    creado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (condicion_id) REFERENCES condiciones (id),
    FOREIGN KEY (detalle_id) REFERENCES detalles (id)
) ENGINE=InnoDB;

# Crear tabla "estudiantes"
CREATE TABLE IF NOT EXISTS estudiantes (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    persona_id INT UNSIGNED NOT NULL,
    carrera_id INT UNSIGNED NOT NULL,
    condicion_detalle_id INT UNSIGNED NOT NULL,    
    creado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (persona_id) REFERENCES personas (id),
    FOREIGN KEY (carrera_id) REFERENCES carreras (id),
    FOREIGN KEY (condicion_detalle_id) REFERENCES condiciones_detalles (id)
) ENGINE=InnoDB;

# Crear tabla "historiales_academicos"
CREATE TABLE IF NOT EXISTS historiales_academicos (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    estudiante_id INT UNSIGNED NOT NULL,
    oferta_academica_id INT UNSIGNED NOT NULL,
    nota DOUBLE NOT NULL,
    observacion VARCHAR(150) NULL,
    creado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (estudiante_id) REFERENCES estudiantes (id),
    FOREIGN KEY (oferta_academica_id) REFERENCES ofertas_academicas (id)
) ENGINE=InnoDB;

# Crear tabla "estudiantes_documentos"
CREATE TABLE IF NOT EXISTS estudiantes_documentos (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    estudiante_id INT UNSIGNED NOT NULL,
    documento_id INT UNSIGNED NOT NULL,    
    consignado BOOLEAN NOT NULL DEFAULT FALSE,
    creado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (estudiante_id) REFERENCES estudiantes (id),
    FOREIGN KEY (documento_id) REFERENCES documentos (id)
) ENGINE=InnoDB;

/*--------------
---- VISTAS ----
--------------*/

# Crear vista "view_condiciones_detalles"
CREATE OR REPLACE VIEW view_condiciones_detalles AS
SELECT  condiciones_detalles.id, 
        condiciones.condicion, 
        detalles.detalle
FROM    condiciones_detalles,
        condiciones,
        detalles
WHERE   condiciones_detalles.condicion_id = condiciones.id
AND     condiciones_detalles.detalle_id = detalles.id
UNION
SELECT  condiciones_detalles.id, 
        condiciones.condicion, 
        NULL AS detalle
FROM    condiciones_detalles,
        condiciones
WHERE   condiciones_detalles.condicion_id = condiciones.id
AND     condiciones_detalles.detalle_id IS NULL;

# Crear vista "view_modulos_ultimas_resoluciones"
CREATE OR REPLACE VIEW view_modulos_ultimas_resoluciones AS
SELECT      tabla.carrera_id,
            tabla.nivel_id,
            tabla.unidad_id,
            COUNT(*) AS modulos,
            tabla.resolucion,
            tabla.acta,
            tabla.fecha
FROM        (
            SELECT      planes_estudio.carrera_id,			
                        planes_estudio.nivel_id,			
                        planes_estudio.unidad_id,
                        resoluciones.resolucion,
                        resoluciones.acta,
                        resoluciones.fecha
            FROM        planes_estudio,
                        planes_estudio_modulos,
                        modulos,
                        resoluciones
            WHERE       planes_estudio_modulos.plan_estudio_id = planes_estudio.id
            AND         planes_estudio_modulos.modulo_id = modulos.id
            AND         planes_estudio_modulos.resolucion_id = resoluciones.id
            ORDER BY	resoluciones.fecha,
                        resoluciones.acta,
                        resoluciones.resolucion DESC) AS tabla
GROUP BY    tabla.carrera_id, 
            tabla.nivel_id, 
            tabla.unidad_id;

/*--------------------------------
--- PROCEDIMIENTOS ALMACENADOS ---
--------------------------------*/

# Crear procedimiento almacenado "insert_ubicacion_geografica"
DROP procedure IF EXISTS insert_ubicacion_geografica;
DELIMITER $$
CREATE PROCEDURE insert_ubicacion_geografica (
                    estado VARCHAR(20), 
                    municipio VARCHAR(30), 
                    parroquia VARCHAR(60))
BEGIN
    DECLARE estado_id INT UNSIGNED DEFAULT NULL;
    DECLARE municipio_id INT UNSIGNED DEFAULT NULL;
    DECLARE parroquia_id INT UNSIGNED DEFAULT NULL;
    DECLARE ubicacion_geografica_id INT UNSIGNED DEFAULT NULL;
        
    REPEAT				
        SET estado_id = get_estado_id(estado);
        
        IF estado_id IS NULL THEN
            INSERT INTO estados (estado) 
            VALUES (estado);
        END IF;
    UNTIL estado_id IS NOT NULL
    END REPEAT;
    
    REPEAT		        
        SET municipio_id = get_municipio_id(municipio);
        
        IF municipio_id IS NULL THEN
            INSERT INTO municipios (municipio) 
            VALUES (municipio);
        END IF;
    UNTIL municipio_id IS NOT NULL
    END REPEAT;
    
    REPEAT		
        SET parroquia_id = get_parroquia_id(parroquia);
        
        IF parroquia_id IS NULL THEN
    		INSERT INTO parroquias (parroquia) 
            VALUES (parroquia);
        END IF;
    UNTIL parroquia_id IS NOT NULL
    END REPEAT;
    
    REPEAT
        SET ubicacion_geografica_id = get_ubicacion_geografica_id(
                                        estado_id, 
                                        municipio_id, 
                                        parroquia_id);
        
        IF ubicacion_geografica_id IS NULL THEN
            INSERT INTO ubicaciones_geograficas (
                            estado_id, 
                            municipio_id, 
                            parroquia_id) 
            VALUES (
                            estado_id, 
                            municipio_id, 
                            parroquia_id);
        END IF;
    UNTIL ubicacion_geografica_id IS NOT NULL
    END REPEAT;    
END$$
DELIMITER ;

# Crear procedimiento almacenado "insert_carrera"
DROP procedure IF EXISTS insert_carrera;
DELIMITER $$
CREATE PROCEDURE insert_carrera (carrera VARCHAR(45))
BEGIN
    DECLARE carrera_id INT UNSIGNED DEFAULT NULL;
        
    REPEAT
        SET carrera_id = get_carrera_id(carrera);
        
        IF carrera_id IS NULL THEN
            INSERT INTO carreras (carrera)
            VALUES (carrera);
        END IF;
    UNTIL carrera_id IS NOT NULL
    END REPEAT;    
END$$
DELIMITER ;

# Crear procedimiento almacenado "insert_nivel"
DROP procedure IF EXISTS insert_nivel;
DELIMITER $$
CREATE PROCEDURE insert_nivel (
                    nivel VARCHAR(50), 
                    orden SMALLINT)
BEGIN
    DECLARE nivel_id INT UNSIGNED DEFAULT NULL;
        
    REPEAT
        SET nivel_id = get_nivel_id(nivel);
        
        IF nivel_id IS NULL THEN
            INSERT INTO niveles (nivel, orden)
            VALUES(nivel,orden);
        END IF;
    UNTIL nivel_id IS NOT NULL
    END REPEAT;    
END$$
DELIMITER ;

# Crear procedimiento almacenado "insert_grado"
DROP procedure IF EXISTS insert_grado;
DELIMITER $$
CREATE PROCEDURE insert_grado (grado VARCHAR(50))
BEGIN
    DECLARE grado_id INT UNSIGNED DEFAULT NULL;
        
    REPEAT
        SET grado_id = get_grado_id(grado);
        
        IF grado_id IS NULL THEN
            INSERT INTO grados (grado)
            VALUES(grado);
        END IF;
    UNTIL grado_id IS NOT NULL
    END REPEAT;    
END$$
DELIMITER ;

# Crear procedimiento almacenado "insert_plan_estudio"
DROP procedure IF EXISTS insert_plan_estudio;
DELIMITER $$
CREATE PROCEDURE insert_plan_estudio (
                    carrera VARCHAR(45), 
                    codigo VARCHAR(16), 
                    unidad VARCHAR(120), 
                    nivel VARCHAR(50),
                    grado VARCHAR(50),
                    uc SMALLINT, 
                    codigo_prelacion VARCHAR(45),
                    resolucion SMALLINT,
                    acta SMALLINT,
                    fecha DATE)
BEGIN
    DECLARE carrera_id INT UNSIGNED DEFAULT NULL;
    DECLARE unidad_id INT UNSIGNED DEFAULT NULL;
    DECLARE modulo_id INT UNSIGNED DEFAULT NULL;
    DECLARE nivel_id INT UNSIGNED DEFAULT NULL;
    DECLARE grado_id INT UNSIGNED DEFAULT NULL;
    DECLARE resolucion_id INT UNSIGNED DEFAULT NULL;
    DECLARE plan_estudio_id INT UNSIGNED DEFAULT NULL;
    DECLARE plan_estudio_id_1 INT UNSIGNED DEFAULT NULL;
    DECLARE plan_estudio_id_2 INT UNSIGNED DEFAULT NULL;
    DECLARE prelacion_id INT UNSIGNED DEFAULT NULL;
    
    REPEAT
        SET carrera_id = get_carrera_id(carrera);
        
        IF carrera_id IS NULL THEN
            CALL insert_carrera(carrera);
        END IF;
    UNTIL carrera_id IS NOT NULL
    END REPEAT;
   	
    REPEAT
        SET unidad_id = get_unidad_id(codigo, unidad);
        
        IF unidad_id IS NULL THEN
            INSERT INTO unidades(codigo, unidad)
            VALUES(codigo, unidad);
        END IF;
    UNTIL unidad_id IS NOT NULL
    END REPEAT;     
  
    SET nivel_id = get_nivel_id(nivel);
    SET grado_id = get_grado_id(grado); 

    REPEAT
    	SET resolucion_id = get_resolucion_id(resolucion, acta, fecha);
    	
    	IF resolucion_id IS NULL THEN
    		CALL insert_resolucion(resolucion, acta, fecha);			
    	END IF;
    UNTIL resolucion_id IS NOT NULL
    END REPEAT;
    
    REPEAT
        SET plan_estudio_id = get_plan_estudio_id(
                                carrera_id, 
                                unidad_id, 
                                nivel_id,
                                resolucion_id);
        
        IF plan_estudio_id IS NULL THEN
            INSERT INTO planes_estudio(
                            carrera_id, 
                            unidad_id, 
                            nivel_id,  
                            grado_id,
                            resolucion_id,
                            uc)
            VALUES (
                            carrera_id, 
                            unidad_id, 
                            nivel_id,  
                            grado_id,
                            resolucion_id,
                            uc);
        END IF;
    UNTIL plan_estudio_id IS NOT NULL
    END REPEAT;
   
    SET plan_estudio_id_1 = plan_estudio_id;
    
    SELECT  planes_estudio.id INTO plan_estudio_id_2
    FROM    planes_estudio,
            unidades
    WHERE   unidades.codigo = codigo_prelacion
    AND     planes_estudio.unidad_id = unidades.id  
    AND     planes_estudio.carrera_id = carrera_id
    AND     planes_estudio.resolucion_id = resolucion_id	
    LIMIT   1;

    REPEAT
        SELECT  prelaciones.id INTO prelacion_id    		
        FROM    prelaciones
        WHERE   prelaciones.plan_estudio_id_1 = plan_estudio_id_1
        AND     (
                prelaciones.plan_estudio_id_2 = plan_estudio_id_2 OR 
                prelaciones.plan_estudio_id_2 IS NULL )
        LIMIT   1;
    
        IF prelacion_id IS NULL THEN
            INSERT INTO prelaciones(
                            plan_estudio_id_1, 
                            plan_estudio_id_2)
            VALUES(
                            plan_estudio_id_1, 
                            plan_estudio_id_2);
        END IF;
    UNTIL prelacion_id IS NOT NULL
    END REPEAT;
END$$
DELIMITER ;

# Crear procedimiento almacenado "insert_plan_estudio_modulo"
DROP procedure IF EXISTS insert_plan_estudio_modulo;
DELIMITER $$
CREATE PROCEDURE insert_plan_estudio_modulo (
                    carrera VARCHAR(45),
                    codigo VARCHAR(16),
                    unidad VARCHAR(120), 
                    nivel VARCHAR(50),
                    modulo VARCHAR(200),
                    hta SMALLINT,
                    htas SMALLINT,
                    resolucion_id_u INT UNSIGNED,
                    resolucion_m SMALLINT UNSIGNED,
                    acta_m SMALLINT UNSIGNED,
                    fecha_m DATE)
BEGIN
    DECLARE plan_estudio_id INT UNSIGNED DEFAULT NULL;
    DECLARE plan_estudio_modulo_id INT UNSIGNED DEFAULT NULL;
    DECLARE modulo_id INT UNSIGNED DEFAULT NULL;
    DECLARE resolucion_id_m INT UNSIGNED DEFAULT NULL;

    SET plan_estudio_id = get_plan_estudio_id(
                            get_carrera_id(carrera), 
                            get_unidad_id(codigo, unidad), 
                            get_nivel_id(nivel), 
                            resolucion_id_u);

    REPEAT
        SET resolucion_id_m = get_resolucion_id(resolucion_m, acta_m, fecha_m);
    
        IF resolucion_id_m IS NULL THEN
            CALL insert_resolucion(resolucion_m, acta_m, fecha_m);
        END IF;
    UNTIL resolucion_id_m IS NOT NULL
    END REPEAT;

    IF plan_estudio_id IS NOT NULL THEN
        REPEAT
            SET modulo_id = get_modulo_id(modulo);
    
            IF modulo_id IS NULL THEN
                    INSERT INTO modulos(modulo)
                    VALUES (modulo);
            END IF;
        UNTIL modulo_id IS NOT NULL
        END REPEAT;
    
        REPEAT
            SELECT  planes_estudio_modulos.id INTO plan_estudio_modulo_id
            FROM    planes_estudio_modulos,
                    modulos
            WHERE   planes_estudio_modulos.plan_estudio_id = plan_estudio_id
            AND     planes_estudio_modulos.modulo_id = modulos.id
            AND     modulos.modulo = modulo
            AND     planes_estudio_modulos.resolucion_id = resolucion_id_m;
    
            IF plan_estudio_modulo_id IS NULL THEN
                    INSERT INTO planes_estudio_modulos(
                                    plan_estudio_id, 
                                    modulo_id, 
                                    hta, 
                                    htas, 
                                    resolucion_id)
                    VALUES (
                                    plan_estudio_id,
                                    modulo_id,
                                    hta,
                                    htas,
                                    resolucion_id_m);
            END IF;
        UNTIL plan_estudio_modulo_id IS NOT NULL
        END REPEAT;
    END IF;
END$$    
DELIMITER ;

# Crear procedimiento almacenado "insert_etnia"
DROP procedure IF EXISTS insert_etnia;
DELIMITER $$
CREATE PROCEDURE insert_etnia (etnia VARCHAR(37))
BEGIN
    DECLARE etnia_id INT UNSIGNED DEFAULT NULL;
    
    SET etnia_id = get_etnia_id(etnia);
    
    IF etnia_id IS NULL THEN
        INSERT INTO etnias(etnia)
        VALUES(etnia);	
    END IF;
END$$
DELIMITER ;

# Crear procedimiento almacenado "insert_periodo"
DROP procedure IF EXISTS insert_periodo;
DELIMITER $$
CREATE PROCEDURE insert_periodo (
                    periodo VARCHAR(12), 
                    fecha_inicial DATE, 
                    fecha_final DATE)
BEGIN
    DECLARE periodo_id INT UNSIGNED DEFAULT NULL;
    
    SET periodo_id = get_periodo_id(periodo);    
    
    IF periodo_id IS NULL THEN
        INSERT INTO periodos(
                        periodo, 
                        fecha_inicial, 
                        fecha_final)
        VALUES (
                        periodo, 
                        fecha_inicial, 
                        fecha_final);
    ELSE
        SELECT "Periodo existente";
    END IF;
END$$
DELIMITER ;

# Crear procedimiento almacenado "update_periodo"
DROP procedure IF EXISTS update_periodo;
DELIMITER $$
CREATE PROCEDURE update_periodo (
                    id INT UNSIGNED, 
                    periodo VARCHAR(12), 
                    fecha_inicial DATE, 
                    fecha_final DATE)
BEGIN
    DECLARE periodo_id INT UNSIGNED DEFAULT NULL;
    
    SELECT  periodos.id INTO periodo_id
    FROM    periodos
    WHERE   periodos.id <> id
    AND     periodos.periodo = periodo    
    LIMIT   1;
    
    IF periodo_id IS NULL THEN
        UPDATE  periodos 
        SET     periodos.periodo = periodo, 
                periodos.fecha_inicial = fecha_inicial, 
                periodos.fecha_final = fecha_final
        WHERE   periodos.id = id;
    ELSE
        SELECT "Ya existe otro periodo con esta misma denominación";
    END IF;
END$$
DELIMITER ;

# Crear procedimiento almacenado "select_plan_estudio"
DROP procedure IF EXISTS select_plan_estudio;
DELIMITER $$
CREATE PROCEDURE select_plan_estudio (
                    carrera_id INT UNSIGNED, 
                    nivel_id INT UNSIGNED,
                    resolucion_id INT UNSIGNED)
BEGIN	
    SELECT      planes_estudio.id, 
                carreras.carrera, 
                unidades.codigo, 
                unidades.unidad, 
                niveles.nivel,
                IF( planes_estudio.grado_id IS NOT NULL, 
                    get_grado(planes_estudio.grado_id),
                    NULL) AS grado,
                SUM(planes_estudio_modulos.htas) AS htas,
                SUM(planes_estudio_modulos.hta) AS hta,
                planes_estudio.uc,
                get_prelaciones(planes_estudio.id) AS prelaciones,
                resoluciones.resolucion AS resolucion_uc,
                resoluciones.acta AS acta_uc,
                resoluciones.fecha AS fecha_uc,
                view_modulos_ultimas_resoluciones.resolucion AS resolucion_m,
                view_modulos_ultimas_resoluciones.acta AS acta_m,
                view_modulos_ultimas_resoluciones.fecha AS fecha_m
    FROM        carreras,
                unidades, 
                niveles, 
                planes_estudio,
                planes_estudio_modulos,
                view_modulos_ultimas_resoluciones,
                resoluciones
    WHERE       planes_estudio.carrera_id = carreras.id
    AND         planes_estudio.nivel_id = niveles.id
    AND         planes_estudio.unidad_id = unidades.id
    AND         planes_estudio.id = planes_estudio_modulos.plan_estudio_id
    AND         planes_estudio.carrera_id = view_modulos_ultimas_resoluciones.carrera_id 
    AND         planes_estudio.nivel_id = view_modulos_ultimas_resoluciones.nivel_id 
    AND         planes_estudio.unidad_id = view_modulos_ultimas_resoluciones.unidad_id
    AND         planes_estudio.resolucion_id = resoluciones.id
    AND         CASE WHEN carrera_id IS NOT NULL THEN 
    				planes_estudio.carrera_id = carrera_id
	            ELSE 
	            	planes_estudio.carrera_id IS NOT NULL
                END
    AND         CASE WHEN 
    				nivel_id IS NOT NULL THEN planes_estudio.nivel_id = nivel_id
            	ELSE 
            		planes_estudio.nivel_id IS NOT NULL
                END
    AND         CASE WHEN resolucion_id IS NOT NULL THEN 
    				planes_estudio.resolucion_id = resolucion_id	                
	            ELSE 
	            	planes_estudio.resolucion_id IS NOT NULL
            	END    			
    GROUP BY    carreras.carrera, 
            	niveles.orden, 
            	unidades.codigo;	
END$$
DELIMITER ;

# Crear procedimiento almacenado "select_plan_estudio_modulo"
DROP procedure IF EXISTS select_plan_estudio_modulo;
DELIMITER $$
CREATE PROCEDURE select_plan_estudio_modulo (plan_estudio_id INT UNSIGNED)
BEGIN
    DECLARE resolucion_id INT UNSIGNED DEFAULT NULL;
    
    SELECT      resoluciones.id INTO resolucion_id
    FROM        planes_estudio_modulos,
                resoluciones
    WHERE       planes_estudio_modulos.resolucion_id = resoluciones.id
    ORDER BY    resoluciones.fecha DESC
    LIMIT 1;

    SELECT      planes_estudio_modulos.id,
    			modulos.id AS modulo_id,
                modulos.modulo,
                planes_estudio_modulos.hta / planes_estudio_modulos.htas AS semanas,
                planes_estudio_modulos.htas,
                planes_estudio_modulos.hta,         
                resoluciones.fecha 
    FROM        planes_estudio_modulos,
                modulos,
                resoluciones
    WHERE       planes_estudio_modulos.plan_estudio_id = plan_estudio_id 
    AND         planes_estudio_modulos.modulo_id = modulos.id
    AND         planes_estudio_modulos.resolucion_id = resoluciones.id
    AND         resoluciones.id = resolucion_id 
    ORDER BY    modulos.modulo;
END$$
DELIMITER ;

# Crear procedimiento almacenado "select_prelaciones"
DROP procedure IF EXISTS select_prelaciones;
DELIMITER $$
CREATE PROCEDURE select_prelaciones (plan_estudio_id INT UNSIGNED)
BEGIN
    SELECT  vp.plan_estudio_id_1,
            vp.codigo,
            vp.materia
    FROM    view_prelaciones vp 
    WHERE   vp.plan_estudio_id_1 = plan_estudio_id;
END$$
DELIMITER ;

# Crear procedimiento almacenado "select_carreras"
DROP procedure IF EXISTS select_carreras;
DELIMITER $$
CREATE PROCEDURE select_carreras ()
BEGIN
    SELECT  id, 
            carrera
    FROM    carreras;
END$$
DELIMITER ;

# Crear procedimiento almacenado "select_planes_estudio_resoluciones"
DROP procedure IF EXISTS select_planes_estudio_resoluciones;
DELIMITER $$
CREATE PROCEDURE select_planes_estudio_resoluciones (carrera VARCHAR(45))
BEGIN
    SELECT      *
    FROM        (
                SELECT      resoluciones.id,
                			resoluciones.resolucion,
                            resoluciones.acta,
                            resoluciones.fecha 
                FROM        planes_estudio, 
                            carreras,
                            resoluciones
                WHERE       planes_estudio.carrera_id = carreras.id
                AND         planes_estudio.resolucion_id = resoluciones.id
                AND         carreras.carrera = carrera
                GROUP BY    resoluciones.fecha,
                            resoluciones.resolucion,
                            resoluciones.acta) AS tabla
    ORDER BY	tabla.fecha,
                tabla.resolucion,
                tabla.acta DESC;
END$$
DELIMITER ;

# Crear procedimiento almacenado "select_planes_estudio_niveles"
DROP procedure IF EXISTS select_planes_estudio_niveles;
DELIMITER $$
CREATE PROCEDURE select_planes_estudio_niveles (
                    carrera_id INT UNSIGNED, 
                    resolucion_id INT UNSIGNED)
BEGIN	
	SELECT      niveles.id,
				niveles.nivel,
				niveles.orden 
	FROM        niveles
	INNER JOIN	(
	            SELECT  DISTINCT(niveles.nivel),                 		
	                    niveles.orden
	            FROM    planes_estudio,
	                    niveles
	            WHERE   planes_estudio.carrera_id = carrera_id
	            AND     planes_estudio.resolucion_id = resolucion_id
	            AND     planes_estudio.nivel_id = niveles.id            
	            ) AS tabla ON tabla.nivel = niveles.nivel
	
	ORDER BY    tabla.orden ASC;
END$$
DELIMITER ;

# Crear procedimiento almacenado "select_periodos"
DROP procedure IF EXISTS select_periodos;
DELIMITER $$
CREATE PROCEDURE select_periodos ()
BEGIN
    DECLARE hoy DATE DEFAULT CURDATE();
    
    SELECT      id, 
                periodo, 
                fecha_inicial, 
                fecha_final, 
                hoy>=fecha_inicial AND hoy<=fecha_final AS vigente
    FROM        periodos
    ORDER BY    fecha_inicial DESC;
END$$
DELIMITER ;

# Crear procedimiento almacenado "select_estudiante"
DROP procedure IF EXISTS select_estudiante;
DELIMITER $$
CREATE PROCEDURE select_estudiante (cedula VARCHAR(9))
BEGIN
    SELECT      estudiantes.id AS estudiante_id,
                personas.id AS persona_id,
                personas.cedula,
                personas.nombre1,
                personas.nombre2,
                personas.apellido1,
                personas.apellido2,
                sexos.sexo,
                personas.fecha_nacimiento,
                personas.lugar_nacimiento,
                get_edad(personas.fecha_nacimiento) as edad,
                estados_civiles.estado_civil,
                etnias.etnia,
                estados.estado,
                municipios.municipio,            
                parroquias.parroquia,
                personas.direccion,
                personas.telefono_local,
                personas.telefono_movil,
                personas.correo_electronico,
                carreras.carrera,
                view_condiciones_detalles.condicion,
                view_condiciones_detalles.detalle
    FROM        personas,
                sexos,
                estados_civiles,
                etnias,
                estudiantes,
                ubicaciones_geograficas,
                estados,
                municipios,
                parroquias,
                carreras,
                view_condiciones_detalles            
    WHERE       personas.cedula = cedula
    AND         personas.sexo_id = sexos.id
    AND         personas.estado_civil_id = estados_civiles.id
    AND         personas.etnia_id = etnias.id    
    AND         personas.ubicacion_geografica_id = ubicaciones_geograficas.id
    AND         personas.id = estudiantes.persona_id
    AND         estudiantes.carrera_id = carreras.id
    AND         estudiantes.condicion_detalle_id = view_condiciones_detalles.id
    AND         ubicaciones_geograficas.estado_id = estados.id
    AND         ubicaciones_geograficas.municipio_id = municipios.id
    AND         ubicaciones_geograficas.parroquia_id = parroquias.id    
    ORDER BY    view_condiciones_detalles.condicion ASC;
END$$
DELIMITER ;

# Crear procedimiento almacenado "select_docente"
DROP procedure IF EXISTS select_docente;
DELIMITER $$
CREATE PROCEDURE select_docente (cedula VARCHAR(9))
BEGIN
	SELECT      docentes.id AS docente_id,
                personas.id AS persona_id,
                personas.cedula,
                personas.nombre1,
                personas.nombre2,
                personas.apellido1,
                personas.apellido2,
                sexos.sexo,
                personas.fecha_nacimiento,
                personas.lugar_nacimiento,
                get_edad(personas.fecha_nacimiento) as edad,
                estados_civiles.estado_civil,
                etnias.etnia,
                estados.estado,
                municipios.municipio,            
                parroquias.parroquia,
                personas.direccion,
                personas.telefono_local,
                personas.telefono_movil,
                personas.correo_electronico,
                docentes.activo
    FROM        personas,
                sexos,
                estados_civiles,
                etnias,
                docentes,
                ubicaciones_geograficas,
                estados,
                municipios,
                parroquias
    WHERE       personas.cedula = cedula
    AND         personas.sexo_id = sexos.id
    AND         personas.estado_civil_id = estados_civiles.id
    AND         personas.etnia_id = etnias.id    
    AND         personas.ubicacion_geografica_id = ubicaciones_geograficas.id
    AND         personas.id = docentes.persona_id        
    AND         ubicaciones_geograficas.estado_id = estados.id
    AND         ubicaciones_geograficas.municipio_id = municipios.id
    AND         ubicaciones_geograficas.parroquia_id = parroquias.id;
END$$
DELIMITER ;

# Crear procedimiento almacenado "select_docentes"
DROP procedure IF EXISTS select_docentes;
DELIMITER $$
CREATE PROCEDURE select_docentes (activo BOOLEAN)
BEGIN
    SELECT      docentes.id AS docente_id,
                personas.id AS persona_id,
                personas.cedula,
                personas.nombre1,
                personas.nombre2,
                personas.apellido1,
                personas.apellido2,
                sexos.sexo,
                personas.fecha_nacimiento,
                personas.lugar_nacimiento,
                get_edad(personas.fecha_nacimiento) as edad,
                estados_civiles.estado_civil,
                etnias.etnia,
                estados.estado,
                municipios.municipio,            
                parroquias.parroquia,
                personas.direccion,
                personas.telefono_local,
                personas.telefono_movil,
                personas.correo_electronico,
                docentes.activo
    FROM        personas,
                sexos,
                estados_civiles,
                etnias,
                docentes,
                ubicaciones_geograficas,
                estados,
                municipios,
                parroquias
    WHERE       CASE 
                    WHEN activo IS NOT NULL THEN docentes.activo = activo
                    ELSE docentes.activo IS NOT NULL
                END
    AND         personas.sexo_id = sexos.id
    AND         personas.estado_civil_id = estados_civiles.id
    AND         personas.etnia_id = etnias.id    
    AND         personas.ubicacion_geografica_id = ubicaciones_geograficas.id
    AND         personas.id = docentes.persona_id        
    AND         ubicaciones_geograficas.estado_id = estados.id
    AND         ubicaciones_geograficas.municipio_id = municipios.id
    AND         ubicaciones_geograficas.parroquia_id = parroquias.id;
END$$
DELIMITER ;

# Crear procedimiento almacenado "select_persona"
DROP procedure IF EXISTS select_persona;
DELIMITER $$
CREATE PROCEDURE select_persona (cedula VARCHAR(9))
BEGIN
    SELECT  personas.id AS persona_id,
            personas.cedula,
            personas.nombre1,
            personas.nombre2,
            personas.apellido1,
            personas.apellido2,
            sexos.sexo,
            personas.fecha_nacimiento,
            personas.lugar_nacimiento,
            get_edad(personas.fecha_nacimiento) as edad,
            estados_civiles.estado_civil,
            etnias.etnia,
            estados.estado,
            municipios.municipio,            
            parroquias.parroquia,
            personas.direccion,
            personas.telefono_local,
            personas.telefono_movil,
            personas.correo_electronico                
    FROM    personas,
            sexos,
            estados_civiles,
            etnias,
            ubicaciones_geograficas,
            estados,
            municipios,
            parroquias                            
    WHERE   personas.cedula = cedula
    AND     personas.sexo_id = sexos.id
    AND     personas.estado_civil_id = estados_civiles.id
    AND     personas.etnia_id = etnias.id    
    AND     personas.ubicacion_geografica_id = ubicaciones_geograficas.id    
    AND     ubicaciones_geograficas.estado_id = estados.id
    AND     ubicaciones_geograficas.municipio_id = municipios.id
    AND     ubicaciones_geograficas.parroquia_id = parroquias.id
    LIMIT   1;
END$$
DELIMITER ;

# Crear procedimiento almacenado "select_sexos"
DROP procedure IF EXISTS select_sexos;
DELIMITER $$
CREATE PROCEDURE select_sexos ()
BEGIN
    SELECT  sexo
    FROM    sexos;
END$$
DELIMITER ;

# Crear procedimiento almacenado "select_estados_civiles"
DROP procedure IF EXISTS select_estados_civiles;
DELIMITER $$
CREATE PROCEDURE select_estados_civiles ()
BEGIN
    SELECT  estado_civil
    FROM    estados_civiles;
END$$
DELIMITER ;

# Crear procedimiento almacenado "select_etnias"
DROP procedure IF EXISTS select_etnias;
DELIMITER $$
CREATE PROCEDURE select_etnias ()
BEGIN
    SELECT      etnia
    FROM        etnias
    ORDER BY    id;
END$$
DELIMITER ;

# Crear procedimiento almacenado "select_estados"
DROP procedure IF EXISTS select_estados;
DELIMITER $$
CREATE PROCEDURE select_estados ()
BEGIN
    SELECT  estado
    FROM    estados;
END$$
DELIMITER ;

# Crear procedimiento almacenado "select_municipios"
DROP procedure IF EXISTS select_municipios;
DELIMITER $$
CREATE PROCEDURE select_municipios (estado VARCHAR(20))
BEGIN
    DECLARE estado_id INT UNSIGNED DEFAULT NULL;
    
    SET estado_id = get_estado_id(estado);    
    
    IF estado_id IS NOT NULL THEN
        SELECT      municipios.municipio 
        FROM        ubicaciones_geograficas,
                    municipios
        WHERE       ubicaciones_geograficas.estado_id = estado_id
        AND         ubicaciones_geograficas.municipio_id = municipios.id
        GROUP BY    municipios.municipio;
    END IF;
END$$
DELIMITER ;

# Crear procedimiento almacenado "select_parroquias"
DROP procedure IF EXISTS select_parroquias;
DELIMITER $$
CREATE PROCEDURE select_parroquias (
                    estado VARCHAR(20), 
                    municipio VARCHAR(30))
BEGIN
    DECLARE estado_id INT UNSIGNED DEFAULT NULL;
    DECLARE municipio_id INT UNSIGNED DEFAULT NULL;
    
    SET estado_id = get_estado_id(estado);
    SET municipio_id = get_municipio_id(municipio);
    
    IF estado_id IS NOT NULL AND municipio_id IS NOT NULL THEN
        SELECT      parroquias.parroquia
        FROM        ubicaciones_geograficas,
                    parroquias
        WHERE       ubicaciones_geograficas.estado_id = estado_id
        AND         ubicaciones_geograficas.municipio_id = municipio_id
        AND         ubicaciones_geograficas.parroquia_id = parroquias.id
        GROUP BY    parroquias.parroquia;
    END IF;
END$$
DELIMITER ;

# Crear procedimiento almacenada "insert_persona"
DROP procedure IF EXISTS insert_persona;
DELIMITER $$
CREATE PROCEDURE insert_persona (
                    cedula VARCHAR(9), 
                    nombre1 VARCHAR(15),
                    nombre2 VARCHAR(15),
                    apellido1 VARCHAR(15),
                    apellido2 VARCHAR(15),
                    sexo VARCHAR(9),
                    fecha_nacimiento DATE,
                    lugar_nacimiento VARCHAR(40),
                    estado_civil VARCHAR(15),
                    etnia VARCHAR(37),
                    estado VARCHAR(20),
                    municipio VARCHAR(30),
                    parroquia VARCHAR(60),
                    direccion VARCHAR(60),
                    telefono_local VARCHAR(16),
                    telefono_movil VARCHAR(16),
                    correo_electronico VARCHAR(320))
BEGIN
    DECLARE persona_id INT UNSIGNED DEFAULT NULL;    
            
    REPEAT
        SET persona_id = get_persona_id(cedula);
    
        IF persona_id IS NULL THEN
            INSERT INTO personas (
                            cedula, 
                            nombre1, 
                            nombre2, 
                            apellido1, 
                            apellido2, 
                            sexo_id, 
                            fecha_nacimiento, 
                            lugar_nacimiento,
                            estado_civil_id, 
                            etnia_id,
                            ubicacion_geografica_id,
                            direccion,
                            telefono_local,
                            telefono_movil,
                            correo_electronico)
            VALUES (
                            cedula, 
                            nombre1, 
                            get_string_or_null(nombre2), 
                            apellido1, 
                            get_string_or_null(apellido2), 
                            get_sexo_id(sexo), 
                            fecha_nacimiento,
                            lugar_nacimiento,
                            get_estado_civil_id(estado_civil), 
                            get_etnia_id(etnia),
                            get_ubicacion_geografica_id(
                            get_estado_id(estado), 
                            get_municipio_id(municipio), 
                            get_parroquia_id(parroquia)),
                            get_string_or_null(direccion),
                            get_string_or_null(telefono_local),
                            get_string_or_null(telefono_movil),
                            get_string_or_null(correo_electronico));				  
        END IF;
    UNTIL persona_id IS NOT NULL
    END REPEAT;
END$$
DELIMITER ;

# Crear procedimiento almacenada "update_persona"
DROP procedure IF EXISTS update_persona;
DELIMITER $$
CREATE PROCEDURE update_persona (
					persona_id INT UNSIGNED,
                    cedula VARCHAR(9), 
                    nombre1 VARCHAR(15),
                    nombre2 VARCHAR(15),
                    apellido1 VARCHAR(15),
                    apellido2 VARCHAR(15),
                    sexo VARCHAR(9),
                    fecha_nacimiento DATE,
                    lugar_nacimiento VARCHAR(40),
                    estado_civil VARCHAR(15),
                    etnia VARCHAR(37),
                    estado VARCHAR(20),
                    municipio VARCHAR(30),
                    parroquia VARCHAR(60),
                    direccion VARCHAR(60),
                    telefono_local VARCHAR(16),
                    telefono_movil VARCHAR(16),
                    correo_electronico VARCHAR(320))
BEGIN
	UPDATE  personas
    SET     personas.cedula = cedula,
            personas.nombre1 = nombre1,
            personas.nombre2 = get_string_or_null(nombre2),
            personas.apellido1 = apellido1,
            personas.apellido2 = get_string_or_null(apellido2),
            personas.sexo_id = get_sexo_id(sexo),
            personas.fecha_nacimiento = fecha_nacimiento,
            personas.lugar_nacimiento = lugar_nacimiento,
            personas.estado_civil_id = get_estado_civil_id(estado_civil),
            personas.etnia_id = get_etnia_id(etnia),
            personas.ubicacion_geografica_id = get_ubicacion_geografica_id(
            get_estado_id(estado),
            get_municipio_id(municipio),
            get_parroquia_id(parroquia)),
            personas.direccion = get_string_or_null(direccion),
            personas.telefono_local = get_string_or_null(telefono_local),
            personas.telefono_movil = get_string_or_null(telefono_movil),
            personas.correo_electronico = get_string_or_null(correo_electronico)
    WHERE   personas.id = persona_id;
END$$
DELIMITER 

# Crear procedimiento almacenada "insert_estudiante"
/* 	
    NOTA: Usar con transacciones (opcional pero recomendado). 
    
    No se ha incluido las trasacciones dentro del la propia función
    debido a que este procedimiento está pensado para utilizarse
    en conjunto a otros procedimientos.
    
    La transacción debe invocarse de tal manera que abarque esta
    y las demás funciones que deban ejecutarse junto a él.
*/
DROP procedure IF EXISTS insert_estudiante;
DELIMITER $$
CREATE PROCEDURE insert_estudiante (
                    cedula VARCHAR(9), 
                    nombre1 VARCHAR(15),
                    nombre2 VARCHAR(15),
                    apellido1 VARCHAR(15),
                    apellido2 VARCHAR(15),
                    sexo VARCHAR(9),
                    fecha_nacimiento DATE,
                    lugar_nacimiento VARCHAR(40),
                    estado_civil VARCHAR(15),
                    etnia VARCHAR(37),
                    estado VARCHAR(20),
                    municipio VARCHAR(30),
                    parroquia VARCHAR(60),
                    direccion VARCHAR(60),
                    telefono_local VARCHAR(16),
                    telefono_movil VARCHAR(16),
                    correo_electronico VARCHAR(320),
                    carrera VARCHAR(45),
                    condicion VARCHAR(10),
                    detalle VARCHAR(10))
BEGIN
    DECLARE persona_id INT UNSIGNED DEFAULT NULL;    
    DECLARE carrera_id INT UNSIGNED DEFAULT NULL;    
    DECLARE condicion_detalle_id INT UNSIGNED DEFAULT NULL;    
    DECLARE estudiante_id INT UNSIGNED DEFAULT NULL;    
    DECLARE estudiante_documento_id INT UNSIGNED DEFAULT NULL;    
    DECLARE documento_id INT UNSIGNED DEFAULT NULL;    

   /*
    CALL insert_persona(
            cedula, 
            nombre1, 
            nombre2, 
            apellido1, 
            apellido2, 
            sexo, 
            fecha_nacimiento, 
            lugar_nacimiento,
            estado_civil, 
            etnia,
            estado,
            municipio,
            parroquia,
            direccion,
            telefono_local,
            telefono_movil,
            correo_electronico);
    */
    #SET persona_id = get_persona_id(cedula);    
    SET carrera_id = get_carrera_id(carrera);
    SET condicion_detalle_id = get_condicion_detalle_id(
                                get_condicion_id(condicion), 
                                get_detalle_id(detalle));
                               
    REPEAT
    	SET persona_id = get_persona_id(cedula);    
    	
    	IF persona_id IS NULL THEN
    		CALL insert_persona(
		            cedula, 
		            nombre1, 
		            nombre2, 
		            apellido1, 
		            apellido2, 
		            sexo, 
		            fecha_nacimiento, 
		            lugar_nacimiento,
		            estado_civil, 
		            etnia,
		            estado,
		            municipio,
		            parroquia,
		            direccion,
		            telefono_local,
		            telefono_movil,
		            correo_electronico);
		ELSE
			CALL update_persona(
					persona_id,
		            cedula, 
		            nombre1, 
		            nombre2, 
		            apellido1, 
		            apellido2, 
		            sexo, 
		            fecha_nacimiento, 
		            lugar_nacimiento,
		            estado_civil, 
		            etnia,
		            estado,
		            municipio,
		            parroquia,
		            direccion,
		            telefono_local,
		            telefono_movil,
		            correo_electronico);
    	END IF;
    UNTIL persona_id IS NOT NULL
    END REPEAT;
    
    REPEAT
        SET estudiante_id = get_estudiante_id(
                            persona_id, 
                            carrera_id, 
                            condicion_detalle_id);
        
        IF estudiante_id IS NULL THEN
            INSERT INTO estudiantes (
                            persona_id, 
                            carrera_id, 
                            condicion_detalle_id)
            VALUES (
                            persona_id, 
                            carrera_id, 
                            condicion_detalle_id);
        END IF;
    UNTIL estudiante_id IS NOT NULL
    END REPEAT;
    
    REPEAT
        SET documento_id = NULL;
        
        SELECT  documentos.id INTO documento_id                
        FROM    documentos
        WHERE   documentos.id NOT IN (
                    SELECT  estudiantes_documentos.documento_id 
                    FROM    estudiantes_documentos
                    WHERE   estudiantes_documentos.estudiante_id = estudiante_id)
        AND     documentos.activo = TRUE
        LIMIT   1;                
    
        IF documento_id IS NOT NULL THEN
            INSERT INTO estudiantes_documentos(
                            estudiante_id, 
                            documento_id,
                            consignado)
            VALUES (
                            estudiante_id, 
                            documento_id,
                            FALSE);
        END IF;
    UNTIL documento_id IS NULL
    END REPEAT;
END$$
DELIMITER ;

# Crear procedimiento almacenada "insert_condicion_detalle"
DROP procedure IF EXISTS insert_condicion_detalle;
DELIMITER $$
CREATE PROCEDURE insert_condicion_detalle (
                    condicion VARCHAR(18),
                    detalle VARCHAR(15))
BEGIN
    DECLARE condicion_id INT UNSIGNED DEFAULT NULL;
    DECLARE detalle_id INT UNSIGNED DEFAULT NULL;
    DECLARE condicion_detalle_id INT UNSIGNED DEFAULT NULL;    
    
    REPEAT
        SET condicion_id = get_condicion_id(condicion);
        
        IF condicion_id IS NULL THEN
            INSERT INTO condiciones (condicion)
            VALUES (condicion);
        END IF;
    UNTIL condicion_id IS NOT NULL
    END REPEAT;
    
    IF detalle IS NOT NULL THEN
        REPEAT
            SET detalle_id = get_detalle_id(detalle);
            
            IF detalle_id IS NULL THEN
                INSERT INTO detalles (detalle)
                VALUES (detalle);
            END IF;
        UNTIL detalle_id IS NOT NULL
        END REPEAT;
    END IF;
    
    REPEAT	
        SET condicion_detalle_id = get_condicion_detalle_id(condicion_id, detalle_id);
        
        IF condicion_detalle_id IS NULL THEN
            INSERT INTO condiciones_detalles (condicion_id, detalle_id)
            VALUES (condicion_id, detalle_id);
        END IF;
    UNTIL condicion_detalle_id IS NOT NULL
    END REPEAT;
END$$
DELIMITER ;

# Crear procedimiento almacenado "select_condiciones"
DROP procedure IF EXISTS select_condiciones;
DELIMITER $$
CREATE PROCEDURE select_condiciones ()
BEGIN
    SELECT      condiciones.condicion
    FROM        condiciones,
                condiciones_detalles
    WHERE       condiciones_detalles.condicion_id = condiciones.id
    GROUP BY    condiciones.condicion;
END$$
DELIMITER ;

# Crear procedimiento almacenado "select_detalles"
DROP procedure IF EXISTS select_detalles;
DELIMITER $$
CREATE PROCEDURE select_detalles (condicion VARCHAR(18))
BEGIN
    DECLARE condicion_id INT UNSIGNED DEFAULT NULL;
    
    SET condicion_id = get_condicion_id(condicion);
    
    SELECT  detalles.detalle
    FROM    detalles,
            condiciones_detalles
    WHERE   condiciones_detalles.detalle_id = detalles.id
    AND     condiciones_detalles.condicion_id = condicion_id;    
END$$
DELIMITER ;

# Crear procedimiento almacenado "insert_documento"
DROP procedure IF EXISTS insert_documento;
DELIMITER $$
CREATE PROCEDURE insert_documento (
                    documento VARCHAR(60),
                    activo BOOLEAN)
BEGIN
    DECLARE documento_id INT UNSIGNED DEFAULT NULL;
    
    SET documento_id = get_documento_id(documento);
    
    IF documento_id IS NULL THEN
        INSERT INTO documentos(documento, activo)
        VALUES(documento, activo);
    ELSE
        UPDATE  documentos
        SET     documentos.activo = activo
        WHERE   documentos.id = documento_id;
    END IF;
END$$
DELIMITER ;

# Crear procedimiento almacenado "insert_resolucion"
DROP procedure IF EXISTS insert_resolucion;
DELIMITER $$
CREATE PROCEDURE insert_resolucion (
                    resolucion SMALLINT,
                    acta SMALLINT,
                    fecha DATE)
BEGIN
    DECLARE resolucion_id INT UNSIGNED DEFAULT NULL;
    
    SET resolucion_id = get_resolucion_id(resolucion,acta,fecha);
    
    IF resolucion_id IS NULL THEN
        INSERT INTO resoluciones(resolucion, acta, fecha)
        VALUES(resolucion, acta, fecha);    
    END IF;
END$$
DELIMITER ;

# Crear procedimiento almacenado "select_estudiante_documentos"
DROP procedure IF EXISTS select_estudiante_documentos;
DELIMITER $$
CREATE PROCEDURE select_estudiante_documentos (
					estudiante_id INT UNSIGNED,
					consignado BOOLEAN)
BEGIN
    SELECT  documentos.documento,
            estudiantes_documentos.consignado
    FROM    documentos,
            estudiantes_documentos
    WHERE   estudiantes_documentos.estudiante_id = estudiante_id
    AND     estudiantes_documentos.documento_id = documentos.id
    AND		CASE WHEN consignado IS NOT NULL THEN
    			estudiantes_documentos.consignado = consignado
			END;
END$$
DELIMITER ;

# Crear procedimiento almacenado "update_estudiante"
/* 	
    NOTA: Usar con transacciones (opcional pero recomendado). 
    
    No se ha incluido las trasacciones dentro del la propia función
    debido a que este procedimiento está pensado para utilizarse
    en conjunto a otros procedimientos.
    
    La transacción debe invocarse de tal manera que abarque esta
    y las demás funciones que deban ejecutarse junto a él.
*/
DROP procedure IF EXISTS update_estudiante;
DELIMITER $$
CREATE PROCEDURE update_estudiante (
                    estudiante_id INT UNSIGNED,
                    cedula VARCHAR(9), 
                    nombre1 VARCHAR(15),
                    nombre2 VARCHAR(15),
                    apellido1 VARCHAR(15),
                    apellido2 VARCHAR(15),
                    sexo VARCHAR(9),
                    fecha_nacimiento DATE,
                    lugar_nacimiento VARCHAR(40),
                    estado_civil VARCHAR(15),
                    etnia VARCHAR(37),
                    estado VARCHAR(20),
                    municipio VARCHAR(30),
                    parroquia VARCHAR(60),
                    direccion VARCHAR(60),
                    telefono_local VARCHAR(16),
                    telefono_movil VARCHAR(16),
                    correo_electronico VARCHAR(320),
                    carrera VARCHAR(45),
                    condicion VARCHAR(10),
                    detalle VARCHAR(10))
BEGIN
    DECLARE persona_id INT UNSIGNED DEFAULT NULL;
    
    SELECT  estudiantes.persona_id INTO persona_id
    FROM    estudiantes
    WHERE   estudiantes.id = estudiante_id;
   
    CALL update_persona(
			persona_id,
		    cedula, 
		    nombre1, 
		    nombre2, 
		    apellido1, 
		    apellido2, 
		    sexo, 
		    fecha_nacimiento, 
		    lugar_nacimiento,
		    estado_civil, 
		    etnia,
		    estado,
		    municipio,
		    parroquia,
		    direccion,
		    telefono_local,
		    telefono_movil,
		    correo_electronico);
		   
    UPDATE  estudiantes
    SET     estudiantes.carrera_id = get_carrera_id(carrera),
            estudiantes.condicion_detalle_id = get_condicion_detalle_id(
            get_condicion_id(condicion),
            get_detalle_id(detalle))
    WHERE   estudiantes.id = estudiante_id;
END$$
DELIMITER ;

# Crear procedimiento almacenado "update_estudiante_documento"
DROP procedure IF EXISTS update_estudiante_documento;
DELIMITER $$
CREATE PROCEDURE update_estudiante_documento (
                    estudiante_id INT UNSIGNED,
                    documento VARCHAR(60),
                    consignado BOOLEAN)
BEGIN
    DECLARE documento_id INT UNSIGNED DEFAULT NULL;
    
    SET documento_id = get_documento_id(documento);
    
    UPDATE  estudiantes_documentos
    SET     estudiantes_documentos.consignado = consignado
    WHERE   estudiantes_documentos.estudiante_id = estudiante_id
    AND     estudiantes_documentos.documento_id = documento_id;		
END$$
DELIMITER ;

# Crear procedimiento almacenado "select_documentos"
DROP procedure IF EXISTS select_documentos;
DELIMITER $$
CREATE PROCEDURE select_documentos (activo BOOLEAN)
BEGIN
    SELECT  documentos.documento,
            documentos.activo
    FROM    documentos
    WHERE   documentos.activo = activo;
END$$
DELIMITER ;

# Crear procedimiento almacenado "select_ofertas_academicas_modulos"
DROP procedure IF EXISTS select_ofertas_academicas_modulos;
DELIMITER $$
CREATE PROCEDURE select_ofertas_academicas_modulos (
					periodo_id INT UNSIGNED,
					carrera_id INT UNSIGNED,
					nivel_id INT UNSIGNED,
					unidad_id INT UNSIGNED)
BEGIN
    SELECT 	ofertas_academicas.id,
			ofertas_academicas.docente_id,
			ofertas_academicas.periodo_id,
			ofertas_academicas.plan_estudio_modulo_id,			
			get_seccion(ofertas_academicas.numero_seccion, ofertas_academicas.nomenclatura) AS seccion,
			ofertas_academicas.cupos,
			ofertas_academicas.numero_seccion,
			ofertas_academicas.nomenclatura,
			modulos.id AS modulo_id,
			modulos.modulo,
			planes_estudio_modulos.hta,
			planes_estudio_modulos.htas
	FROM 	ofertas_academicas,
			planes_estudio_modulos,
			planes_estudio,
			modulos
	WHERE	ofertas_academicas.plan_estudio_modulo_id = planes_estudio_modulos.id 
	AND 	planes_estudio_modulos.plan_estudio_id = planes_estudio.id
	AND		ofertas_academicas.periodo_id = periodo_id 
	AND		planes_estudio.carrera_id = carrera_id
	AND		planes_estudio.nivel_id = nivel_id
	AND		planes_estudio.unidad_id = unidad_id
	AND		plan_estudio_modulo_id = modulos.id
	ORDER BY 	ofertas_academicas.numero_seccion ASC,
				modulos.modulo ASC;
END$$
DELIMITER ;

# Crear procedimiento almacenado "update_ofertas_academicas_modulos"
DROP procedure IF EXISTS update_ofertas_academicas_modulos;
DELIMITER $$
CREATE PROCEDURE update_ofertas_academicas_modulos (
					id INT UNSIGNED,
					docente_id INT,
					cupos INT UNSIGNED,
					numero_seccion INT UNSIGNED,
					nomenclatura INT UNSIGNED)
BEGIN
    UPDATE 	ofertas_academicas 
    SET		ofertas_academicas.docente_id = IF(docente_id > 0, docente_id, NULL),
    		ofertas_academicas.cupos = cupos,
    		ofertas_academicas.numero_seccion = numero_seccion,
    		ofertas_academicas.nomenclatura = nomenclatura 
	WHERE	ofertas_academicas.id = id;
END$$
DELIMITER ;

# Crear procedimiento almacenado "insert_oferta_academica"
DROP procedure IF EXISTS insert_oferta_academica;
DELIMITER $$
CREATE PROCEDURE insert_oferta_academica (
					plan_estudio_modulo_id INT UNSIGNED, 
					docente_id INT,
					periodo_id INT UNSIGNED,
					cupos SMALLINT UNSIGNED,
					numero_seccion SMALLINT UNSIGNED,
					nomenclatura SMALLINT UNSIGNED)
BEGIN
	DECLARE id INT UNSIGNED DEFAULT NULL;	

	SELECT 	ofertas_academicas.id INTO id
	FROM	ofertas_academicas
	WHERE 	ofertas_academicas.plan_estudio_modulo_id = plan_estudio_modulo_id
	AND 	ofertas_academicas.periodo_id = periodo_id
	AND 	ofertas_academicas.cupos = cupos
	AND 	ofertas_academicas.numero_seccion = numero_seccion;	

	IF id IS NULL THEN
		INSERT INTO ofertas_academicas(
	    					plan_estudio_modulo_id,
							docente_id, 
	    					periodo_id, 
	    					cupos, 
	    					numero_seccion, 
	    					nomenclatura)
	    VALUES(
	    					plan_estudio_modulo_id,
					    	IF(docente_id > 0, docente_id, null),
					    	periodo_id,
					    	cupos, 
					    	numero_seccion, 
					    	nomenclatura);
	END IF;        
END$$
DELIMITER ;

# Crear procedimiento almacenada "insert_docente"
/* 	
    NOTA: Usar con transacciones (opcional pero recomendado). 
    
    No se ha incluido las trasacciones dentro del la propia función
    debido a que este procedimiento está pensado para utilizarse
    en conjunto a otros procedimientos.
    
    La transacción debe invocarse de tal manera que abarque esta
    y las demás funciones que deban ejecutarse junto a él.
*/
DROP procedure IF EXISTS insert_docente;
DELIMITER $$
CREATE PROCEDURE insert_docente (
                    cedula VARCHAR(9), 
                    nombre1 VARCHAR(15),
                    nombre2 VARCHAR(15),
                    apellido1 VARCHAR(15),
                    apellido2 VARCHAR(15),
                    sexo VARCHAR(9),
                    fecha_nacimiento DATE,
                    lugar_nacimiento VARCHAR(40),
                    estado_civil VARCHAR(15),
                    etnia VARCHAR(37),
                    estado VARCHAR(20),
                    municipio VARCHAR(30),
                    parroquia VARCHAR(60),
                    direccion VARCHAR(60),
                    telefono_local VARCHAR(16),
                    telefono_movil VARCHAR(16),
                    correo_electronico VARCHAR(320),
                    activo BOOLEAN)
BEGIN
    DECLARE persona_id INT UNSIGNED DEFAULT NULL;
    DECLARE docente_id INT UNSIGNED DEFAULT NULL;    
                               
    REPEAT
    	SET persona_id = get_persona_id(cedula);    
    	
    	IF persona_id IS NULL THEN
    		CALL insert_persona(
		            cedula, 
		            nombre1, 
		            nombre2, 
		            apellido1, 
		            apellido2, 
		            sexo, 
		            fecha_nacimiento, 
		            lugar_nacimiento,
		            estado_civil, 
		            etnia,
		            estado,
		            municipio,
		            parroquia,
		            direccion,
		            telefono_local,
		            telefono_movil,
		            correo_electronico);
		ELSE
			CALL update_persona(
					persona_id,
		            cedula, 
		            nombre1, 
		            nombre2, 
		            apellido1, 
		            apellido2, 
		            sexo, 
		            fecha_nacimiento, 
		            lugar_nacimiento,
		            estado_civil, 
		            etnia,
		            estado,
		            municipio,
		            parroquia,
		            direccion,
		            telefono_local,
		            telefono_movil,
		            correo_electronico);
    	END IF;
    UNTIL persona_id IS NOT NULL
    END REPEAT;
    
    REPEAT
        SET docente_id = get_docente_id(persona_id);
        
        IF docente_id IS NULL THEN
            INSERT INTO docentes (
                            persona_id, 
                            activo)
            VALUES (
                            persona_id, 
                            activo);
        END IF;
    UNTIL docente_id IS NOT NULL
    END REPEAT;    
END$$
DELIMITER ;

# Crear procedimiento almacenado "update_docente"
/* 	
    NOTA: Usar con transacciones (opcional pero recomendado). 
    
    No se ha incluido las trasacciones dentro del la propia función
    debido a que este procedimiento está pensado para utilizarse
    en conjunto a otros procedimientos.
    
    La transacción debe invocarse de tal manera que abarque esta
    y las demás funciones que deban ejecutarse junto a él.
*/
DROP procedure IF EXISTS update_docente;
DELIMITER $$
CREATE PROCEDURE update_docente (
                    docente_id INT UNSIGNED,
                    cedula VARCHAR(9), 
                    nombre1 VARCHAR(15),
                    nombre2 VARCHAR(15),
                    apellido1 VARCHAR(15),
                    apellido2 VARCHAR(15),
                    sexo VARCHAR(9),
                    fecha_nacimiento DATE,
                    lugar_nacimiento VARCHAR(40),
                    estado_civil VARCHAR(15),
                    etnia VARCHAR(37),
                    estado VARCHAR(20),
                    municipio VARCHAR(30),
                    parroquia VARCHAR(60),
                    direccion VARCHAR(60),
                    telefono_local VARCHAR(16),
                    telefono_movil VARCHAR(16),
                    correo_electronico VARCHAR(320),
					activo BOOLEAN)
BEGIN
    DECLARE persona_id INT UNSIGNED DEFAULT NULL;
    
    SELECT  docentes.persona_id INTO persona_id
    FROM    docentes
    WHERE   docentes.id = docente_id;
   
    CALL update_persona(
			persona_id,
		    cedula, 
		    nombre1, 
		    nombre2, 
		    apellido1, 
		    apellido2, 
		    sexo, 
		    fecha_nacimiento, 
		    lugar_nacimiento,
		    estado_civil, 
		    etnia,
		    estado,
		    municipio,
		    parroquia,
		    direccion,
		    telefono_local,
		    telefono_movil,
		    correo_electronico);
		   
    UPDATE  docentes
    SET     docentes.activo = activo
    WHERE   docentes.id = docente_id;
END$$
DELIMITER ;

# Crear procedimiento almacenado "insert_sexo"
DROP procedure IF EXISTS insert_sexo;
DELIMITER $$
CREATE PROCEDURE insert_sexo (sexo VARCHAR(9))
BEGIN
    DECLARE sexo_id INT UNSIGNED DEFAULT NULL;
        
    REPEAT
        SET sexo_id = get_sexo_id(sexo);
        
        IF sexo_id IS NULL THEN
            INSERT INTO sexos (sexo)
            VALUES (sexo);
        END IF;
    UNTIL sexo_id IS NOT NULL
    END REPEAT;    
END$$
DELIMITER ;

# Crear procedimiento almacenado "insert_estado_civil"
DROP procedure IF EXISTS insert_estado_civil;
DELIMITER $$
CREATE PROCEDURE insert_estado_civil (estado_civil VARCHAR(15))
BEGIN
    DECLARE estado_civil_id INT UNSIGNED DEFAULT NULL;
        
    REPEAT
        SET estado_civil_id = get_estado_civil_id(estado_civil);
        
        IF estado_civil_id IS NULL THEN
            INSERT INTO estados_civiles (estado_civil)
            VALUES (estado_civil);
        END IF;
    UNTIL estado_civil_id IS NOT NULL
    END REPEAT;    
END$$
DELIMITER ;

/*-----------------
---- FUNCIONES ----
-----------------*/

# Crear función "get_sexo_id"
DROP function IF EXISTS get_sexo_id;
DELIMITER $$
CREATE FUNCTION get_sexo_id (sexo VARCHAR(9))
RETURNS INTEGER
READS SQL DATA
DETERMINISTIC
BEGIN
    DECLARE sexo_id INT UNSIGNED DEFAULT NULL;
    
    SELECT  id INTO sexo_id
    FROM    sexos
    WHERE   sexos.sexo = sexo;    
RETURN sexo_id;
END$$
DELIMITER ;

# Crear función "get_estado_id"
DROP function IF EXISTS get_estado_id;
DELIMITER $$
CREATE FUNCTION get_estado_id (estado VARCHAR(20))
RETURNS INTEGER
READS SQL DATA
DETERMINISTIC
BEGIN
    DECLARE estado_id INT UNSIGNED DEFAULT NULL;
    
    SELECT  id INTO estado_id
    FROM    estados
    WHERE   estados.estado = estado;    
RETURN estado_id;
END$$
DELIMITER ;

# Crear función "get_municipio_id"
DROP function IF EXISTS get_municipio_id;
DELIMITER $$
CREATE FUNCTION get_municipio_id (municipio VARCHAR(30))
RETURNS INTEGER
READS SQL DATA
DETERMINISTIC
BEGIN
    DECLARE municipio_id INT UNSIGNED DEFAULT NULL;
    
    SELECT  id INTO municipio_id
    FROM    municipios
    WHERE   municipios.municipio = municipio;    
RETURN municipio_id;
END$$
DELIMITER ;

# Crear función "get_parroquia_id"
DROP function IF EXISTS get_parroquia_id;
DELIMITER $$
CREATE FUNCTION get_parroquia_id (parroquia VARCHAR(60))
RETURNS INTEGER
READS SQL DATA
DETERMINISTIC
BEGIN
    DECLARE parroquia_id INT UNSIGNED DEFAULT NULL;
    
    SELECT  id INTO parroquia_id
    FROM    parroquias
    WHERE   parroquias.parroquia = parroquia;    
RETURN parroquia_id;
END$$
DELIMITER ;

# Crear función "get_ubicacion_geografica_id"
DROP function IF EXISTS get_ubicacion_geografica_id;
DELIMITER $$
CREATE FUNCTION get_ubicacion_geografica_id (
                    estado_id INT UNSIGNED,
                    municipio_id INT UNSIGNED,
                    parroquia_id INT UNSIGNED)
RETURNS INTEGER
READS SQL DATA
DETERMINISTIC
BEGIN
    DECLARE ubicacion_geografica_id INT UNSIGNED DEFAULT NULL;
    
    SELECT  id INTO ubicacion_geografica_id
    FROM    ubicaciones_geograficas
    WHERE   ubicaciones_geograficas.estado_id = estado_id
    AND     ubicaciones_geograficas.municipio_id = municipio_id
    AND     ubicaciones_geograficas.parroquia_id = parroquia_id
    LIMIT   1;
RETURN ubicacion_geografica_id;
END$$
DELIMITER ;

# Crear función "get_estado_civil_id"
DROP function IF EXISTS get_estado_civil_id;
DELIMITER $$
CREATE FUNCTION get_estado_civil_id (estado_civil VARCHAR(15))
RETURNS INTEGER
READS SQL DATA
DETERMINISTIC
BEGIN
    DECLARE estado_civil_id INT UNSIGNED DEFAULT NULL;
    
    SELECT  id INTO estado_civil_id
    FROM    estados_civiles
    WHERE   estados_civiles.estado_civil = estado_civil;    
RETURN estado_civil_id;
END$$
DELIMITER ;

# Crear función "get_persona_id"
DROP function IF EXISTS get_persona_id;
DELIMITER $$
CREATE FUNCTION get_persona_id (cedula VARCHAR(9))
RETURNS INTEGER
READS SQL DATA
DETERMINISTIC
BEGIN
    DECLARE persona_id INT UNSIGNED DEFAULT NULL;
    
    SELECT  id INTO persona_id
    FROM    personas
    WHERE   personas.cedula = cedula
    LIMIT   1;
RETURN persona_id;
END$$
DELIMITER ;

# Crear función "get_etnia_id"
DROP function IF EXISTS get_etnia_id;
DELIMITER $$
CREATE FUNCTION get_etnia_id (etnia VARCHAR(37))
RETURNS INTEGER
READS SQL DATA
DETERMINISTIC
BEGIN
    DECLARE etnia_id INT UNSIGNED DEFAULT NULL;
    
    SELECT  id INTO etnia_id
    FROM    etnias
    WHERE   etnias.etnia = etnia;    
RETURN etnia_id;
END$$
DELIMITER ;

# Crear función "get_carrera_id"
DROP function IF EXISTS get_carrera_id;
DELIMITER $$
CREATE FUNCTION get_carrera_id (carrera VARCHAR(45))
RETURNS INTEGER
READS SQL DATA
DETERMINISTIC
BEGIN
    DECLARE carrera_id INT UNSIGNED DEFAULT NULL;
    
    SELECT  id INTO carrera_id
    FROM    carreras
    WHERE   carreras.carrera = carrera;    
RETURN carrera_id;
END$$
DELIMITER ;

# Crear función "get_unidad_id"
DROP function IF EXISTS get_unidad_id;
DELIMITER $$
CREATE FUNCTION get_unidad_id (
                    codigo VARCHAR(16), 
                    unidad VARCHAR(120))
RETURNS INTEGER
READS SQL DATA
DETERMINISTIC
BEGIN
    DECLARE unidad_id INT UNSIGNED DEFAULT NULL;
    
    SELECT  unidades.id INTO unidad_id
    FROM    unidades
    WHERE   unidades.codigo LIKE BINARY codigo
    AND     unidades.unidad LIKE BINARY unidad;
RETURN unidad_id;
END$$
DELIMITER ;

# Crear función "get_modulo_id"
DROP function IF EXISTS get_modulo_id;
DELIMITER $$
CREATE FUNCTION get_modulo_id (modulo VARCHAR(200))
RETURNS INTEGER
READS SQL DATA
DETERMINISTIC
BEGIN
    DECLARE modulo_id INT UNSIGNED DEFAULT NULL;
    
    SELECT  modulos.id INTO modulo_id
    FROM    modulos
    WHERE   modulos.modulo = modulo;    
RETURN modulo_id;
END$$
DELIMITER ;

# Crear función "get_unidad_modulo_id"
DROP function IF EXISTS get_unidad_modulo_id;
DELIMITER $$
CREATE FUNCTION get_unidad_modulo_id (
                    unidad_id INT UNSIGNED,
                    modulo_id INT UNSIGNED)
RETURNS INTEGER
READS SQL DATA
DETERMINISTIC
BEGIN
    DECLARE unidad_modulo_id INT UNSIGNED DEFAULT NULL;
    
    SELECT  unidades_modulos.id INTO unidad_modulo_id
    FROM    unidades_modulos
    WHERE   unidades_modulos.unidad_id = unidad_id
    AND     (unidades_modulos.modulo_id = modulo_id OR
            unidades_modulos.modulo_id IS NULL);    
RETURN unidad_modulo_id;
END$$
DELIMITER ;

# Crear función "get_nivel_id"
DROP function IF EXISTS get_nivel_id;
DELIMITER $$
CREATE FUNCTION get_nivel_id (nivel VARCHAR(50))
RETURNS INTEGER
READS SQL DATA
DETERMINISTIC
BEGIN
    DECLARE nivel_id INT UNSIGNED DEFAULT NULL;
    
    SELECT  niveles.id INTO nivel_id
    FROM    niveles
    WHERE   niveles.nivel = nivel;   
RETURN nivel_id;
END$$
DELIMITER ;

# Crear función "get_grado"
DROP function IF EXISTS get_grado;
DELIMITER $$
CREATE FUNCTION get_grado (id INT UNSIGNED)
RETURNS VARCHAR(50)
READS SQL DATA
DETERMINISTIC
BEGIN
    DECLARE grado VARCHAR(50) DEFAULT NULL;
    
    SELECT  grados.grado INTO grado
    FROM    grados
    WHERE   grados.id = id;   
RETURN grado;
END$$
DELIMITER ;

# Crear función "get_grado_id"
DROP function IF EXISTS get_grado_id;
DELIMITER $$
CREATE FUNCTION get_grado_id (grado VARCHAR(50))
RETURNS INTEGER
READS SQL DATA
DETERMINISTIC
BEGIN
    DECLARE grado_id INT UNSIGNED DEFAULT NULL;
    
    SELECT  grados.id INTO grado_id
    FROM    grados
    WHERE   grados.grado = grado;   
RETURN grado_id;
END$$
DELIMITER ;

# Crear función "get_periodo_id"
DROP function IF EXISTS get_periodo_id;
DELIMITER $$
CREATE FUNCTION get_periodo_id (periodo VARCHAR(12))
RETURNS INTEGER
READS SQL DATA
DETERMINISTIC
BEGIN
    DECLARE periodo_id INT UNSIGNED DEFAULT NULL;
    
    SELECT  periodos.id INTO periodo_id
    FROM    periodos
    WHERE   periodos.periodo = periodo;   
RETURN periodo_id;
END$$
DELIMITER ;

# Crear función "get_plan_estudio_id"
DROP function IF EXISTS get_plan_estudio_id;
DELIMITER $$
CREATE FUNCTION get_plan_estudio_id (
                    carrera_id INT UNSIGNED,
                    unidad_id INT UNSIGNED,
                    nivel_id INT UNSIGNED,
                    resolucion_id INT UNSIGNED)
RETURNS INTEGER
READS SQL DATA
DETERMINISTIC
BEGIN
    DECLARE plan_estudio_id INT UNSIGNED DEFAULT NULL;
    
    SELECT  planes_estudio.id INTO plan_estudio_id
    FROM    planes_estudio
    WHERE   planes_estudio.carrera_id = carrera_id
    AND     planes_estudio.unidad_id = unidad_id
    AND     planes_estudio.nivel_id = nivel_id
    AND     planes_estudio.resolucion_id = resolucion_id;   
RETURN plan_estudio_id;
END$$
DELIMITER ;

# Crear función "get_condicion_id"
DROP function IF EXISTS get_condicion_id;
DELIMITER $$
CREATE FUNCTION get_condicion_id (condicion VARCHAR(18))
RETURNS INTEGER
READS SQL DATA
DETERMINISTIC
BEGIN
    DECLARE condicion_id INT UNSIGNED DEFAULT NULL;
    
    SELECT  condiciones.id INTO condicion_id
    FROM    condiciones
    WHERE   condiciones.condicion = condicion;
RETURN condicion_id;
END$$
DELIMITER ;

# Crear función "get_detalle_id"
DROP function IF EXISTS get_detalle_id;
DELIMITER $$
CREATE FUNCTION get_detalle_id (detalle VARCHAR(15))
RETURNS INTEGER
READS SQL DATA
DETERMINISTIC
BEGIN
    DECLARE detalle_id INT UNSIGNED DEFAULT NULL;
    
    SELECT  detalles.id INTO detalle_id
    FROM    detalles
    WHERE   detalles.detalle = detalle;
RETURN detalle_id;
END$$
DELIMITER ;

# Crear función "get_condicion_detalle_id"
DROP function IF EXISTS get_condicion_detalle_id;
DELIMITER $$
CREATE FUNCTION get_condicion_detalle_id (
                    condicion_id INT UNSIGNED, 
                    detalle_id INT UNSIGNED)
RETURNS INTEGER
READS SQL DATA
DETERMINISTIC
BEGIN
    DECLARE condicion_detalle_id INT UNSIGNED DEFAULT NULL;
    
    SELECT  condiciones_detalles.id INTO condicion_detalle_id
    FROM    condiciones_detalles
    WHERE   condiciones_detalles.condicion_id = condicion_id
    AND    (condiciones_detalles.detalle_id = detalle_id OR
            condiciones_detalles.detalle_id IS NULL);
RETURN condicion_detalle_id;
END$$
DELIMITER ;

# Crear función "get_string_or_null"
DROP function IF EXISTS get_string_or_null;
DELIMITER $$
CREATE FUNCTION get_string_or_null (cadena VARCHAR(16383))
RETURNS VARCHAR(16383)
DETERMINISTIC
BEGIN
    DECLARE resultado VARCHAR(16383) DEFAULT NULL;
    
    IF LENGTH(TRIM(cadena)) > 0 THEN
        SELECT TRIM(cadena) INTO resultado;	
    END IF;
RETURN resultado;
END$$
DELIMITER ;

# Crear función "get_edad"
DROP function IF EXISTS get_edad;
DELIMITER $$
CREATE FUNCTION get_edad (fecha_nacimiento DATE)
RETURNS INTEGER
DETERMINISTIC
BEGIN
    DECLARE resultado INT UNSIGNED DEFAULT NULL;
    
    SET resultado = TIMESTAMPDIFF(YEAR, fecha_nacimiento, CURDATE());
RETURN resultado;
END$$
DELIMITER ;

# Crear función "get_estudiante_id"
DROP function IF EXISTS get_estudiante_id;
DELIMITER $$
CREATE FUNCTION get_estudiante_id (
                    persona_id INT UNSIGNED,
                    carrera_id INT UNSIGNED,
                    condicion_detalle_id INT UNSIGNED)
RETURNS INTEGER
DETERMINISTIC
BEGIN
    DECLARE estudiante_id INT UNSIGNED DEFAULT NULL;

    SELECT  estudiantes.id INTO estudiante_id
    FROM    estudiantes
    WHERE   estudiantes.persona_id = persona_id
    AND     estudiantes.carrera_id = carrera_id
    AND     estudiantes.condicion_detalle_id = condicion_detalle_id
    LIMIT   1;
RETURN estudiante_id;
END$$
DELIMITER ;

# Crear función "get_docente_id"
DROP function IF EXISTS get_docente_id;
DELIMITER $$
CREATE FUNCTION get_docente_id (persona_id INT UNSIGNED)
RETURNS INTEGER
DETERMINISTIC
BEGIN
    DECLARE docente_id INT UNSIGNED DEFAULT NULL;

    SELECT  docentes.id INTO docente_id
    FROM    docentes
    WHERE   docentes.persona_id = persona_id    
    LIMIT   1;
RETURN docente_id;
END$$
DELIMITER ;

# Crear función "get_documento_id"
DROP function IF EXISTS get_documento_id;
DELIMITER $$
CREATE FUNCTION get_documento_id (documento VARCHAR(60))
RETURNS INTEGER
READS SQL DATA
DETERMINISTIC
BEGIN
    DECLARE documento_id INT UNSIGNED DEFAULT NULL;
    
    SELECT  id INTO documento_id
    FROM    documentos
    WHERE   documentos.documento = documento
    LIMIT   1;
RETURN documento_id;
END$$
DELIMITER ;

# Crear función "get_resolucion_id"
DROP function IF EXISTS get_resolucion_id;
DELIMITER $$
CREATE FUNCTION get_resolucion_id (
                    resolucion SMALLINT UNSIGNED,
                    acta SMALLINT UNSIGNED,
                    fecha DATE)
RETURNS INTEGER
READS SQL DATA
DETERMINISTIC
BEGIN
    DECLARE resolucion_id INT UNSIGNED DEFAULT NULL;
    
    SELECT  id INTO resolucion_id
    FROM    resoluciones
    WHERE   resoluciones.resolucion = resolucion
    AND     resoluciones.acta = acta
    AND     resoluciones.fecha = fecha
    LIMIT   1;
RETURN resolucion_id;
END$$
DELIMITER ;

# Crear función "get_prelaciones"
DROP function IF EXISTS get_prelaciones;
DELIMITER $$
CREATE FUNCTION get_prelaciones (plan_estudio_id INT UNSIGNED)
RETURNS VARCHAR(16383)
READS SQL DATA
DETERMINISTIC
BEGIN
    DECLARE prelaciones VARCHAR(16383) DEFAULT NULL;

    SELECT  GROUP_CONCAT(unidades.codigo SEPARATOR ', ') INTO prelaciones
    FROM    prelaciones,
            planes_estudio AS tabla_materias,
            planes_estudio AS tabla_prelaciones,
            unidades
    WHERE   prelaciones.plan_estudio_id_1 = tabla_materias.id
    AND     prelaciones.plan_estudio_id_2 = tabla_prelaciones.id
    AND     tabla_prelaciones.unidad_id = unidades.id
    AND     tabla_materias.id = plan_estudio_id;
RETURN prelaciones;
END$$
DELIMITER ;

## Crear función "get_seccion"
DROP function IF EXISTS get_seccion;
DELIMITER $$
CREATE FUNCTION get_seccion (	
					id_seccion SMALLINT UNSIGNED,
					id_nomenclatura SMALLINT UNSIGNED)
RETURNS VARCHAR(16383)
DETERMINISTIC
BEGIN
	DECLARE seccion VARCHAR(16383) DEFAULT "";
	DECLARE firstChar INT UNSIGNED DEFAULT NULL;
	DECLARE numChars INT UNSIGNED DEFAULT NULL;
	DECLARE id INt DEFAULT 0;

	SET id = id_seccion;

	IF id_nomenclatura = 0 THEN
		SET firstChar = 65;
		SET numChars = 26;
	ELSE
		SET firstChar = 48;
		SET numChars = 10;
	END IF;

	SET seccion = CONCAT(CHAR((id MOD numChars) + firstChar), seccion);
	
	WHILE id >= numChars DO
		SET id = (id DIV numChars) - 1;
	
		SET seccion = CONCAT(CHAR((id MOD numChars) + firstChar), seccion);
	END WHILE;
	
RETURN seccion;
END$$
DELIMITER ;

/****************************************************************************************************************************
*****************************************************************************************************************************
****************************************************************************************************************************/

/*-------------------------
---- PRECARGA DE DATOS ----
-------------------------*/
START TRANSACTION;
# Sexos
CALL insert_sexo('Femenino');
CALL insert_sexo('Masculino');
# INSERT INTO sexos (sexo) VALUES ('Femenino');
# INSERT INTO sexos (sexo) VALUES ('Masculino');
# Estados civiles
CALL insert_estado_civil('Casada(o)');
CALL insert_estado_civil('Soltera(o)');
CALL insert_estado_civil('Divorciada(o)');
CALL insert_estado_civil('Viuda(o)');
CALL insert_estado_civil('Unión libre');
# INSERT INTO estados_civiles (estado_civil) VALUES ('Casada(o)');
# INSERT INTO estados_civiles (estado_civil) VALUES ('Soltera(o)');
# INSERT INTO estados_civiles (estado_civil) VALUES ('Divorciada(o)');
# INSERT INTO estados_civiles (estado_civil) VALUES ('Viuda(o)');
# INSERT INTO estados_civiles (estado_civil) VALUES ('Unión libre');
# Condiciones  detalles
CALL insert_condicion_detalle('Activo','Regular');
CALL insert_condicion_detalle('Activo','Reingreso');
CALL insert_condicion_detalle('Activo','Reincorporación');
CALL insert_condicion_detalle('Retirado',NULL);
CALL insert_condicion_detalle('Suspendido',NULL);
CALL insert_condicion_detalle('Egresado',NULL);
CALL insert_condicion_detalle('Fin de Escolaridad',NULL);
# Documentos para consignar
CALL insert_documento('Copia de cédula de identidad legible',TRUE);
CALL insert_documento('Copia de partida de nacimiento',TRUE);
CALL insert_documento('Copia de calificaciones de bachillerato',TRUE);
CALL insert_documento('Copia simple del título de bachiller',TRUE);
CALL insert_documento('Fondo negro autenticado del título de bachiller',TRUE);
# Niveles
CALL insert_nivel('Trayecto Inicial',0);
CALL insert_nivel('Trayecto de Transición',0);
CALL insert_nivel('Trayecto I',1);
CALL insert_nivel('Trayecto II',2);
CALL insert_nivel('Trayecto III',3);
CALL insert_nivel('Trayecto IV',4);
# Grado (Titularidad)
CALL insert_grado('Técnico Superior Universitario');
CALL insert_grado('Licenciatura');
# Ubicación geográfica
CALL insert_ubicacion_geografica('Distrito Capital','Libertador','23 de Enero');
CALL insert_ubicacion_geografica('Distrito Capital','Libertador','Altagracia');
CALL insert_ubicacion_geografica('Distrito Capital','Libertador','Antímano');
CALL insert_ubicacion_geografica('Distrito Capital','Libertador','Caricuao');
CALL insert_ubicacion_geografica('Distrito Capital','Libertador','Catedral');
CALL insert_ubicacion_geografica('Distrito Capital','Libertador','Coche');
CALL insert_ubicacion_geografica('Distrito Capital','Libertador','El Junquito');
CALL insert_ubicacion_geografica('Distrito Capital','Libertador','El Paraíso');
CALL insert_ubicacion_geografica('Distrito Capital','Libertador','El Recreo');
CALL insert_ubicacion_geografica('Distrito Capital','Libertador','El Valle');
CALL insert_ubicacion_geografica('Distrito Capital','Libertador','Candelaria');
CALL insert_ubicacion_geografica('Distrito Capital','Libertador','La Pastora');
CALL insert_ubicacion_geografica('Distrito Capital','Libertador','La Vega');
CALL insert_ubicacion_geografica('Distrito Capital','Libertador','Macarao');
CALL insert_ubicacion_geografica('Distrito Capital','Libertador','San Agustín');
CALL insert_ubicacion_geografica('Distrito Capital','Libertador','San Bernardino');
CALL insert_ubicacion_geografica('Distrito Capital','Libertador','San José');
CALL insert_ubicacion_geografica('Distrito Capital','Libertador','San Juan');
CALL insert_ubicacion_geografica('Distrito Capital','Libertador','San Pedro');
CALL insert_ubicacion_geografica('Distrito Capital','Libertador','Santa Rosalia');
CALL insert_ubicacion_geografica('Distrito Capital','Libertador','Santa Teresa');
CALL insert_ubicacion_geografica('Distrito Capital','Libertador','Sucre');
CALL insert_ubicacion_geografica('Amazonas','Alto Orinoco','Alto Orinoco');
CALL insert_ubicacion_geografica('Amazonas','Alto Orinoco','Huachamacare');
CALL insert_ubicacion_geografica('Amazonas','Alto Orinoco','Marawaka');
CALL insert_ubicacion_geografica('Amazonas','Alto Orinoco','Mavaka');
CALL insert_ubicacion_geografica('Amazonas','Alto Orinoco','Sierra Parima');
CALL insert_ubicacion_geografica('Amazonas','Atabapo','Atabapo');
CALL insert_ubicacion_geografica('Amazonas','Atabapo','Ucata');
CALL insert_ubicacion_geografica('Amazonas','Atabapo','Yapacana');
CALL insert_ubicacion_geografica('Amazonas','Atabapo','Caname');
CALL insert_ubicacion_geografica('Amazonas','Atures','Fernando Girón Tovar');
CALL insert_ubicacion_geografica('Amazonas','Atures','Luis Alberto Gómez');
CALL insert_ubicacion_geografica('Amazonas','Atures','Pahueña');
CALL insert_ubicacion_geografica('Amazonas','Atures','Platanillal');
CALL insert_ubicacion_geografica('Amazonas','Autana','Samariapo');
CALL insert_ubicacion_geografica('Amazonas','Autana','Sipapo');
CALL insert_ubicacion_geografica('Amazonas','Autana','Munduapo');
CALL insert_ubicacion_geografica('Amazonas','Autana','Guayapo');
CALL insert_ubicacion_geografica('Amazonas','Autana','Isla Ratón');
CALL insert_ubicacion_geografica('Amazonas','Manapiare','Alto Ventuari');
CALL insert_ubicacion_geografica('Amazonas','Manapiare','Medio Ventuari');
CALL insert_ubicacion_geografica('Amazonas','Manapiare','Bajo Ventuari');
CALL insert_ubicacion_geografica('Amazonas','Manapiare','Manapiare');
CALL insert_ubicacion_geografica('Amazonas','Maroa','Maroa');
CALL insert_ubicacion_geografica('Amazonas','Maroa','Victorino');
CALL insert_ubicacion_geografica('Amazonas','Maroa','Comunidad');
CALL insert_ubicacion_geografica('Amazonas','Río Negro','Casiquiare');
CALL insert_ubicacion_geografica('Amazonas','Río Negro','Cocuy');
CALL insert_ubicacion_geografica('Amazonas','Río Negro','San Carlos de Río Negro');
CALL insert_ubicacion_geografica('Amazonas','Río Negro','Solano');
CALL insert_ubicacion_geografica('Anzoátegui','Anaco','Anaco');
CALL insert_ubicacion_geografica('Anzoátegui','Anaco','San Joaquín');
CALL insert_ubicacion_geografica('Anzoátegui','Anaco','Buena Vista');
CALL insert_ubicacion_geografica('Anzoátegui','Aragua','Cachipo');
CALL insert_ubicacion_geografica('Anzoátegui','Aragua','Aragua de Barcelona');
CALL insert_ubicacion_geografica('Anzoátegui','Diego Bautista Urbaneja','Lechería');
CALL insert_ubicacion_geografica('Anzoátegui','Diego Bautista Urbaneja','El Morro');
CALL insert_ubicacion_geografica('Anzoátegui','Fernando Peñalver','Puerto Píritu');
CALL insert_ubicacion_geografica('Anzoátegui','Fernando Peñalver','San Miguel');
CALL insert_ubicacion_geografica('Anzoátegui','Fernando Peñalver','Sucre');
CALL insert_ubicacion_geografica('Anzoátegui','Francisco del Carmen Carvajal','Valle de Guanape');
CALL insert_ubicacion_geografica('Anzoátegui','Francisco del Carmen Carvajal','Santa Bárbara');
CALL insert_ubicacion_geografica('Anzoátegui','Francisco de Miranda','Atapirire');
CALL insert_ubicacion_geografica('Anzoátegui','Francisco de Miranda','Boca del Pao');
CALL insert_ubicacion_geografica('Anzoátegui','Francisco de Miranda','El Pao');
CALL insert_ubicacion_geografica('Anzoátegui','Francisco de Miranda','Pariaguán');
CALL insert_ubicacion_geografica('Anzoátegui','Guanta','Guanta');
CALL insert_ubicacion_geografica('Anzoátegui','Guanta','Chorrerón');
CALL insert_ubicacion_geografica('Anzoátegui','Independencia','Mamo');
CALL insert_ubicacion_geografica('Anzoátegui','Independencia','Soledad');
CALL insert_ubicacion_geografica('Anzoátegui','José Gregorio Monagas','Mapire');
CALL insert_ubicacion_geografica('Anzoátegui','José Gregorio Monagas','Piar');
CALL insert_ubicacion_geografica('Anzoátegui','José Gregorio Monagas','Santa Clara');
CALL insert_ubicacion_geografica('Anzoátegui','José Gregorio Monagas','San Diego de Cabrutica');
CALL insert_ubicacion_geografica('Anzoátegui','José Gregorio Monagas','Uverito');
CALL insert_ubicacion_geografica('Anzoátegui','José Gregorio Monagas','Zuata');
CALL insert_ubicacion_geografica('Anzoátegui','Juan Antonio Sotillo','Puerto La Cruz');
CALL insert_ubicacion_geografica('Anzoátegui','Juan Antonio Sotillo','Pozuelos');
CALL insert_ubicacion_geografica('Anzoátegui','Juan Manuel Cajigal','Onoto');
CALL insert_ubicacion_geografica('Anzoátegui','Juan Manuel Cajigal','San Pablo');
CALL insert_ubicacion_geografica('Anzoátegui','Libertad','San Mateo');
CALL insert_ubicacion_geografica('Anzoátegui','Libertad','El Carito');
CALL insert_ubicacion_geografica('Anzoátegui','Libertad','Santa Inés');
CALL insert_ubicacion_geografica('Anzoátegui','Libertad','La Romereña');
CALL insert_ubicacion_geografica('Anzoátegui','Manuel Ezequiel Bruzual','Clarines');
CALL insert_ubicacion_geografica('Anzoátegui','Manuel Ezequiel Bruzual','Guanape');
CALL insert_ubicacion_geografica('Anzoátegui','Manuel Ezequiel Bruzual','Sabana de Uchire');
CALL insert_ubicacion_geografica('Anzoátegui','Pedro María Freites','Cantaura');
CALL insert_ubicacion_geografica('Anzoátegui','Pedro María Freites','Libertador');
CALL insert_ubicacion_geografica('Anzoátegui','Pedro María Freites','Santa Rosa');
CALL insert_ubicacion_geografica('Anzoátegui','Pedro María Freites','Urica');
CALL insert_ubicacion_geografica('Anzoátegui','Píritu','Píritu');
CALL insert_ubicacion_geografica('Anzoátegui','Píritu','San Francisco');
CALL insert_ubicacion_geografica('Anzoátegui','San José de Guanipa','San José de Guanipa');
CALL insert_ubicacion_geografica('Anzoátegui','San Juan de Capistrano','Boca de Uchire');
CALL insert_ubicacion_geografica('Anzoátegui','San Juan de Capistrano','Boca de Chávez');
CALL insert_ubicacion_geografica('Anzoátegui','Santa Ana','Pueblo Nuevo');
CALL insert_ubicacion_geografica('Anzoátegui','Santa Ana','Santa Ana');
CALL insert_ubicacion_geografica('Anzoátegui','Simón Bolívar','Bergantín');
CALL insert_ubicacion_geografica('Anzoátegui','Simón Bolívar','Caigua');
CALL insert_ubicacion_geografica('Anzoátegui','Simón Bolívar','El Carmen.');
CALL insert_ubicacion_geografica('Anzoátegui','Simón Bolívar','El Pilar');
CALL insert_ubicacion_geografica('Anzoátegui','Simón Bolívar','Naricual.');
CALL insert_ubicacion_geografica('Anzoátegui','Simón Bolívar','San Cristóbal');
CALL insert_ubicacion_geografica('Anzoátegui','Simón Rodríguez','Edmundo Barrios');
CALL insert_ubicacion_geografica('Anzoátegui','Simón Rodríguez','Miguel Otero Silva');
CALL insert_ubicacion_geografica('Anzoátegui','General Sir Arthur McGregor','El Chaparro');
CALL insert_ubicacion_geografica('Anzoátegui','General Sir Arthur McGregor','Tomás Alfaro');
CALL insert_ubicacion_geografica('Anzoátegui','General Sir Arthur McGregor','Calatrava');
CALL insert_ubicacion_geografica('Apure','Achaguas','Achaguas');
CALL insert_ubicacion_geografica('Apure','Achaguas','Apurito');
CALL insert_ubicacion_geografica('Apure','Achaguas','El Yagual');
CALL insert_ubicacion_geografica('Apure','Achaguas','Guachara');
CALL insert_ubicacion_geografica('Apure','Achaguas','Mucuritas');
CALL insert_ubicacion_geografica('Apure','Achaguas','Queseras del medio');
CALL insert_ubicacion_geografica('Apure','Biruaca','Biruaca');
CALL insert_ubicacion_geografica('Apure','Muñoz','Bruzual');
CALL insert_ubicacion_geografica('Apure','Muñoz','Mantecal');
CALL insert_ubicacion_geografica('Apure','Muñoz','Quintero');
CALL insert_ubicacion_geografica('Apure','Muñoz','Rincón Hondo');
CALL insert_ubicacion_geografica('Apure','Muñoz','San Vicente');
CALL insert_ubicacion_geografica('Apure','Pedro Camejo','San Juan de Payara');
CALL insert_ubicacion_geografica('Apure','Pedro Camejo','Codazzi');
CALL insert_ubicacion_geografica('Apure','Pedro Camejo','Cunaviche');
CALL insert_ubicacion_geografica('Apure','San Fernando','San Fernando');
CALL insert_ubicacion_geografica('Apure','San Fernando','El Recreo');
CALL insert_ubicacion_geografica('Apure','San Fernando','Peñalver');
CALL insert_ubicacion_geografica('Apure','San Fernando','San Rafael de Atamaica');
CALL insert_ubicacion_geografica('Apure','San Fernando','Distrito Alto Apure');
CALL insert_ubicacion_geografica('Apure','Páez','Guasdualito');
CALL insert_ubicacion_geografica('Apure','Páez','Aramendi');
CALL insert_ubicacion_geografica('Apure','Páez','El Amparo');
CALL insert_ubicacion_geografica('Apure','Páez','San Camilo');
CALL insert_ubicacion_geografica('Apure','Páez','Urdaneta');
CALL insert_ubicacion_geografica('Apure','Rómulo Gallegos','Elorza');
CALL insert_ubicacion_geografica('Apure','Rómulo Gallegos','La Trinidad');
CALL insert_ubicacion_geografica('Aragua','Bolívar','Bolívar');
CALL insert_ubicacion_geografica('Aragua','Camatagua','Camatagua');
CALL insert_ubicacion_geografica('Aragua','Camatagua','Carmen de Cura, (es una tribu)');
CALL insert_ubicacion_geografica('Aragua','Francisco Linares Alcántara','Santa Rita');
CALL insert_ubicacion_geografica('Aragua','Francisco Linares Alcántara','Francisco de Miranda');
CALL insert_ubicacion_geografica('Aragua','Francisco Linares Alcántara','Moseñor Feliciano González');
CALL insert_ubicacion_geografica('Aragua','Girardot','Pedro José Ovalles');
CALL insert_ubicacion_geografica('Aragua','Girardot','Joaquín Crespo');
CALL insert_ubicacion_geografica('Aragua','Girardot','José Casanova Godoy');
CALL insert_ubicacion_geografica('Aragua','Girardot','Madre María de San José');
CALL insert_ubicacion_geografica('Aragua','Girardot','Andrés Eloy Blanco');
CALL insert_ubicacion_geografica('Aragua','Girardot','Los Tacarigua');
CALL insert_ubicacion_geografica('Aragua','Girardot','Las Delicias');
CALL insert_ubicacion_geografica('Aragua','Girardot','Choroní');
CALL insert_ubicacion_geografica('Aragua','José Ángel Lamas','Santa Cruz');
CALL insert_ubicacion_geografica('Aragua','José Félix Ribas','José Félix Ribas');
CALL insert_ubicacion_geografica('Aragua','José Félix Ribas','Castor Nieves Ríos');
CALL insert_ubicacion_geografica('Aragua','José Félix Ribas','Las Guacamayas');
CALL insert_ubicacion_geografica('Aragua','José Félix Ribas','Pao de Zárate');
CALL insert_ubicacion_geografica('Aragua','José Félix Ribas','Zuata');
CALL insert_ubicacion_geografica('Aragua','José Rafael Revenga','José Rafael Revenga');
CALL insert_ubicacion_geografica('Aragua','José Rafael Revenga','El Consejo');
CALL insert_ubicacion_geografica('Aragua','Libertador','Palo Negro');
CALL insert_ubicacion_geografica('Aragua','Libertador','San Martín de Porres');
CALL insert_ubicacion_geografica('Aragua','Mario Briceño Iragorry','El Limón');
CALL insert_ubicacion_geografica('Aragua','Mario Briceño Iragorry','Caña de Azúcar');
CALL insert_ubicacion_geografica('Aragua','Ocumare de la Costa de Oro','Ocumare de la Costa');
CALL insert_ubicacion_geografica('Aragua','San Casimiro','San Casimiro');
CALL insert_ubicacion_geografica('Aragua','San Sebastián','San Sebastían');
CALL insert_ubicacion_geografica('Aragua','Santiago Mariño','Turmero');
CALL insert_ubicacion_geografica('Aragua','Santiago Mariño','Arévalo Aponte');
CALL insert_ubicacion_geografica('Aragua','Santiago Mariño','Chuao');
CALL insert_ubicacion_geografica('Aragua','Santiago Mariño','Samán de Güere');
CALL insert_ubicacion_geografica('Aragua','Santiago Mariño','Alfredo Pacheco Miranda');
CALL insert_ubicacion_geografica('Aragua','Santos Michelena','Santos Michelena');
CALL insert_ubicacion_geografica('Aragua','Santos Michelena','Tiara');
CALL insert_ubicacion_geografica('Aragua','Sucre','Cagua');
CALL insert_ubicacion_geografica('Aragua','Sucre','Bella Vista');
CALL insert_ubicacion_geografica('Aragua','Tovar','Tovar');
CALL insert_ubicacion_geografica('Aragua','Urdaneta','Urdaneta');
CALL insert_ubicacion_geografica('Aragua','Urdaneta','Las Peñitas');
CALL insert_ubicacion_geografica('Aragua','Urdaneta','San Francisco de Cara');
CALL insert_ubicacion_geografica('Aragua','Urdaneta','Taguay');
CALL insert_ubicacion_geografica('Aragua','Zamora','Villa de Cura');
CALL insert_ubicacion_geografica('Aragua','Zamora','Magdaleno');
CALL insert_ubicacion_geografica('Aragua','Zamora','San Francisco de Asís');
CALL insert_ubicacion_geografica('Aragua','Zamora','Valles de Tucutunemo');
CALL insert_ubicacion_geografica('Aragua','Zamora','Augusto Mijares');
CALL insert_ubicacion_geografica('Barinas','Alberto Arvelo Torrealba','Sabaneta');
CALL insert_ubicacion_geografica('Barinas','Alberto Arvelo Torrealba','Juan Antonio Rodríguez Domínguez');
CALL insert_ubicacion_geografica('Barinas','Andrés Eloy Blanco','El Cantón');
CALL insert_ubicacion_geografica('Barinas','Andrés Eloy Blanco','Santa Cruz de Guacas');
CALL insert_ubicacion_geografica('Barinas','Andrés Eloy Blanco','Puerto Vivas');
CALL insert_ubicacion_geografica('Barinas','Antonio José de Sucre','Socopo');
CALL insert_ubicacion_geografica('Barinas','Antonio José de Sucre','Nicolás Pulido');
CALL insert_ubicacion_geografica('Barinas','Antonio José de Sucre','Andrés Bello');
CALL insert_ubicacion_geografica('Barinas','Arismendi','Arismendi');
CALL insert_ubicacion_geografica('Barinas','Arismendi','Guadarrama');
CALL insert_ubicacion_geografica('Barinas','Arismendi','La Unión');
CALL insert_ubicacion_geografica('Barinas','Arismendi','San Antonio');
CALL insert_ubicacion_geografica('Barinas','Barinas','Barinas');
CALL insert_ubicacion_geografica('Barinas','Bolívar','Barinitas');
CALL insert_ubicacion_geografica('Barinas','Cruz Paredes','Barrancas');
CALL insert_ubicacion_geografica('Barinas','Cruz Paredes','El Socorro');
CALL insert_ubicacion_geografica('Barinas','Cruz Paredes','Mazparrito');
CALL insert_ubicacion_geografica('Barinas','Ezequiel Zamora','Santa Bárbara');
CALL insert_ubicacion_geografica('Barinas','Ezequiel Zamora','Pedro Briceño Méndez');
CALL insert_ubicacion_geografica('Barinas','Ezequiel Zamora','Ramón Ignacio Méndez');
CALL insert_ubicacion_geografica('Barinas','Ezequiel Zamora','José Ignacio del Pumar');
CALL insert_ubicacion_geografica('Barinas','Obispos','Obispos');
CALL insert_ubicacion_geografica('Barinas','Pedraza','Ciudad Bolivia');
CALL insert_ubicacion_geografica('Barinas','Rojas','Libertad');
CALL insert_ubicacion_geografica('Barinas','Rojas','Dolores');
CALL insert_ubicacion_geografica('Barinas','Rojas','Santa Rosa');
CALL insert_ubicacion_geografica('Barinas','Rojas','Palacio Fajardo');
CALL insert_ubicacion_geografica('Barinas','Rojas','Simón Rodríguez');
CALL insert_ubicacion_geografica('Barinas','Sosa','Ciudad de Nutrias');
CALL insert_ubicacion_geografica('Barinas','Sosa','El Regalo');
CALL insert_ubicacion_geografica('Barinas','Sosa','Puerto Nutrias');
CALL insert_ubicacion_geografica('Barinas','Sosa','Santa Catalina');
CALL insert_ubicacion_geografica('Barinas','Sosa','Simón Bolívar');
CALL insert_ubicacion_geografica('Bolívar','Caroní','Cachamay');
CALL insert_ubicacion_geografica('Bolívar','Caroní','Chirica');
CALL insert_ubicacion_geografica('Bolívar','Caroní','Dalla Costa');
CALL insert_ubicacion_geografica('Bolívar','Caroní','Once de Abril');
CALL insert_ubicacion_geografica('Bolívar','Caroní','Simón Bolívar');
CALL insert_ubicacion_geografica('Bolívar','Caroní','Unare');
CALL insert_ubicacion_geografica('Bolívar','Caroní','Universidad');
CALL insert_ubicacion_geografica('Bolívar','Caroní','Vista al Sol');
CALL insert_ubicacion_geografica('Bolívar','Caroní','Pozo Verde');
CALL insert_ubicacion_geografica('Bolívar','Caroní','Yocoima');
CALL insert_ubicacion_geografica('Bolívar','Caroní','5 de Julio');
CALL insert_ubicacion_geografica('Bolívar','Caroní','Pto. Ordaz');
CALL insert_ubicacion_geografica('Bolívar','Cedeño','Cedeño');
CALL insert_ubicacion_geografica('Bolívar','Cedeño','Altagracia');
CALL insert_ubicacion_geografica('Bolívar','Cedeño','Ascensión Farreras');
CALL insert_ubicacion_geografica('Bolívar','Cedeño','Guaniamo');
CALL insert_ubicacion_geografica('Bolívar','Cedeño','La Urbana');
CALL insert_ubicacion_geografica('Bolívar','Cedeño','Pijiguaos');
CALL insert_ubicacion_geografica('Bolívar','El Callao','El Callao');
CALL insert_ubicacion_geografica('Bolívar','Gran Sabana','Gran Sabana');
CALL insert_ubicacion_geografica('Bolívar','Gran Sabana','IkabaRú');
CALL insert_ubicacion_geografica('Bolívar','Heres','Catedral');
CALL insert_ubicacion_geografica('Bolívar','Heres','Zea');
CALL insert_ubicacion_geografica('Bolívar','Heres','Orinoco');
CALL insert_ubicacion_geografica('Bolívar','Heres','José Antonio Páez');
CALL insert_ubicacion_geografica('Bolívar','Heres','Marhuanta');
CALL insert_ubicacion_geografica('Bolívar','Heres','Agua Salada');
CALL insert_ubicacion_geografica('Bolívar','Heres','Vista Hermosa');
CALL insert_ubicacion_geografica('Bolívar','Heres','La Sabanita');
CALL insert_ubicacion_geografica('Bolívar','Heres','Panapana');
CALL insert_ubicacion_geografica('Bolívar','Padre Pedro Chien','Padre Pedro Chien');
CALL insert_ubicacion_geografica('Bolívar','Piar','Andrés Eloy Blanco');
CALL insert_ubicacion_geografica('Bolívar','Piar','Pedro Cova');
CALL insert_ubicacion_geografica('Bolívar','Piar','Upata');
CALL insert_ubicacion_geografica('Bolívar','Angostura (Raúl Leoni)','Raúl Leoni');
CALL insert_ubicacion_geografica('Bolívar','Angostura (Raúl Leoni)','Barceloneta');
CALL insert_ubicacion_geografica('Bolívar','Angostura (Raúl Leoni)','Santa Bárbara');
CALL insert_ubicacion_geografica('Bolívar','Angostura (Raúl Leoni)','San Francisco');
CALL insert_ubicacion_geografica('Bolívar','Roscio','Roscio');
CALL insert_ubicacion_geografica('Bolívar','Roscio','Salóm');
CALL insert_ubicacion_geografica('Bolívar','Sifontes','Tumeremo');
CALL insert_ubicacion_geografica('Bolívar','Sifontes','Dalla Costa');
CALL insert_ubicacion_geografica('Bolívar','Sifontes','San Isidro');
CALL insert_ubicacion_geografica('Bolívar','Sucre','Sucre');
CALL insert_ubicacion_geografica('Bolívar','Sucre','Aripao');
CALL insert_ubicacion_geografica('Bolívar','Sucre','Guarataro');
CALL insert_ubicacion_geografica('Bolívar','Sucre','Las Majadas');
CALL insert_ubicacion_geografica('Bolívar','Sucre','Moitaco');
CALL insert_ubicacion_geografica('Carabobo','Bejuma','Canoabo');
CALL insert_ubicacion_geografica('Carabobo','Bejuma','Simon Bolivar');
CALL insert_ubicacion_geografica('Carabobo','Carlos Arvelo','Güigüe');
CALL insert_ubicacion_geografica('Carabobo','Carlos Arvelo','Belén');
CALL insert_ubicacion_geografica('Carabobo','Carlos Arvelo','Tacarigua');
CALL insert_ubicacion_geografica('Carabobo','Diego Ibarra','Mariara');
CALL insert_ubicacion_geografica('Carabobo','Diego Ibarra','Aguas Calientes');
CALL insert_ubicacion_geografica('Carabobo','Guacara','Ciudad Alianza');
CALL insert_ubicacion_geografica('Carabobo','Guacara','Guacara');
CALL insert_ubicacion_geografica('Carabobo','Guacara','Yagua');
CALL insert_ubicacion_geografica('Carabobo','Juan José Mora','Morón');
CALL insert_ubicacion_geografica('Carabobo','Juan José Mora','Urama');
CALL insert_ubicacion_geografica('Carabobo','Libertador','Tocuyito Valencia');
CALL insert_ubicacion_geografica('Carabobo','Libertador','Independencia Campo Carabobo');
CALL insert_ubicacion_geografica('Carabobo','Los Guayos','Los Guayos Valencia');
CALL insert_ubicacion_geografica('Carabobo','Miranda','Miranda');
CALL insert_ubicacion_geografica('Carabobo','Montalbán','Montalbán');
CALL insert_ubicacion_geografica('Carabobo','Naguanagua','Urbana Naguanagua Valencia');
CALL insert_ubicacion_geografica('Carabobo','Puerto Cabello','Bartolomé Salóm');
CALL insert_ubicacion_geografica('Carabobo','Puerto Cabello','Democracia');
CALL insert_ubicacion_geografica('Carabobo','Puerto Cabello','Fraternidad');
CALL insert_ubicacion_geografica('Carabobo','Puerto Cabello','Goaigoaza');
CALL insert_ubicacion_geografica('Carabobo','Puerto Cabello','Juan José Flores');
CALL insert_ubicacion_geografica('Carabobo','Puerto Cabello','Unión');
CALL insert_ubicacion_geografica('Carabobo','Puerto Cabello','Borburata');
CALL insert_ubicacion_geografica('Carabobo','Puerto Cabello','Patanemo');
CALL insert_ubicacion_geografica('Carabobo','San Diego','San Diego Valencia');
CALL insert_ubicacion_geografica('Carabobo','San Joaquín','San Joaquín');
CALL insert_ubicacion_geografica('Carabobo','Valencia','Urbana Candelaria');
CALL insert_ubicacion_geografica('Carabobo','Valencia','Urbana Catedral');
CALL insert_ubicacion_geografica('Carabobo','Valencia','Urbana El Socorro');
CALL insert_ubicacion_geografica('Carabobo','Valencia','Urbana Miguel Peña');
CALL insert_ubicacion_geografica('Carabobo','Valencia','Urbana Rafael Urdaneta');
CALL insert_ubicacion_geografica('Carabobo','Valencia','Urbana San Blas');
CALL insert_ubicacion_geografica('Carabobo','Valencia','Urbana San José');
CALL insert_ubicacion_geografica('Carabobo','Valencia','Urbana Santa Rosa');
CALL insert_ubicacion_geografica('Carabobo','Valencia','No Urbana Negro Primero');
CALL insert_ubicacion_geografica('Cojedes','Anzoátegui','Cojedes');
CALL insert_ubicacion_geografica('Cojedes','Anzoátegui','Juan de Mata Suárez');
CALL insert_ubicacion_geografica('Cojedes','Pao de San Juan Bautista','El Pao');
CALL insert_ubicacion_geografica('Cojedes','Tinaquillo','Tinaquillo');
CALL insert_ubicacion_geografica('Cojedes','Girardot','El Baúl');
CALL insert_ubicacion_geografica('Cojedes','Girardot','Sucre');
CALL insert_ubicacion_geografica('Cojedes','Lima Blanco','La Aguadita');
CALL insert_ubicacion_geografica('Cojedes','Lima Blanco','Macapo');
CALL insert_ubicacion_geografica('Cojedes','Ricaurte','El Amparo');
CALL insert_ubicacion_geografica('Cojedes','Ricaurte','Libertad de Cojedes');
CALL insert_ubicacion_geografica('Cojedes','Rómulo Gallegos','Rómulo Gallegos');
CALL insert_ubicacion_geografica('Cojedes','Ezequiel Zamora (Cojedes)','San Carlos de Austria');
CALL insert_ubicacion_geografica('Cojedes','Ezequiel Zamora (Cojedes)','Juan Ángel Bravo');
CALL insert_ubicacion_geografica('Cojedes','Ezequiel Zamora (Cojedes)','Manuel Manrique');
CALL insert_ubicacion_geografica('Cojedes','Tinaco','General en Jefe José Laurencio Silva');
CALL insert_ubicacion_geografica('Delta Amacuro','Antonio Díaz','Curiapo');
CALL insert_ubicacion_geografica('Delta Amacuro','Antonio Díaz','Almirante Luis Brión');
CALL insert_ubicacion_geografica('Delta Amacuro','Antonio Díaz','Francisco Aniceto Lugo');
CALL insert_ubicacion_geografica('Delta Amacuro','Antonio Díaz','Manuel Renaud');
CALL insert_ubicacion_geografica('Delta Amacuro','Antonio Díaz','Padre Barral');
CALL insert_ubicacion_geografica('Delta Amacuro','Antonio Díaz','Santos de Abelgas');
CALL insert_ubicacion_geografica('Delta Amacuro','Casacoima','Imataca');
CALL insert_ubicacion_geografica('Delta Amacuro','Casacoima','Juan Bautista Arismendi');
CALL insert_ubicacion_geografica('Delta Amacuro','Casacoima','Manuel Piar');
CALL insert_ubicacion_geografica('Delta Amacuro','Casacoima','Rómulo Gallegos');
CALL insert_ubicacion_geografica('Delta Amacuro','Pedernales','Pedernales');
CALL insert_ubicacion_geografica('Delta Amacuro','Pedernales','Luis Beltrán Prieto Figueroa');
CALL insert_ubicacion_geografica('Delta Amacuro','Tucupita','San José');
CALL insert_ubicacion_geografica('Delta Amacuro','Tucupita','José Vidal Marcano');
CALL insert_ubicacion_geografica('Delta Amacuro','Tucupita','Leonardo Ruíz Pineda');
CALL insert_ubicacion_geografica('Delta Amacuro','Tucupita','Mariscal Antonio José de Sucre');
CALL insert_ubicacion_geografica('Delta Amacuro','Tucupita','Monseñor Argimiro García');
CALL insert_ubicacion_geografica('Delta Amacuro','Tucupita','Virgen del Valle');
CALL insert_ubicacion_geografica('Delta Amacuro','Tucupita','San Rafael');
CALL insert_ubicacion_geografica('Delta Amacuro','Tucupita','Juan Millan');
CALL insert_ubicacion_geografica('Falcón','Acosta','Capadare');
CALL insert_ubicacion_geografica('Falcón','Acosta','La Pastora');
CALL insert_ubicacion_geografica('Falcón','Acosta','Libertador');
CALL insert_ubicacion_geografica('Falcón','Acosta','San Juan de los Cayos');
CALL insert_ubicacion_geografica('Falcón','Bolívar','Aracua');
CALL insert_ubicacion_geografica('Falcón','Bolívar','La Peña');
CALL insert_ubicacion_geografica('Falcón','Bolívar','San Luis');
CALL insert_ubicacion_geografica('Falcón','Buchivacoa','Bariro');
CALL insert_ubicacion_geografica('Falcón','Buchivacoa','Borojó');
CALL insert_ubicacion_geografica('Falcón','Buchivacoa','Capatárida');
CALL insert_ubicacion_geografica('Falcón','Buchivacoa','Guajiro');
CALL insert_ubicacion_geografica('Falcón','Buchivacoa','Seque');
CALL insert_ubicacion_geografica('Falcón','Buchivacoa','Valle de Eroa');
CALL insert_ubicacion_geografica('Falcón','Buchivacoa','Zazárida');
CALL insert_ubicacion_geografica('Falcón','Cacique Manaure','Cacique Manaure');
CALL insert_ubicacion_geografica('Falcón','Carirubana','Norte');
CALL insert_ubicacion_geografica('Falcón','Carirubana','Carirubana');
CALL insert_ubicacion_geografica('Falcón','Carirubana','Santa Ana');
CALL insert_ubicacion_geografica('Falcón','Carirubana','Urbana Punta Cardón');
CALL insert_ubicacion_geografica('Falcón','Colina','La Vela de Coro');
CALL insert_ubicacion_geografica('Falcón','Colina','Acurigua');
CALL insert_ubicacion_geografica('Falcón','Colina','Guaibacoa');
CALL insert_ubicacion_geografica('Falcón','Colina','Las Calderas');
CALL insert_ubicacion_geografica('Falcón','Colina','Mataruca');
CALL insert_ubicacion_geografica('Falcón','Dabajuro','Dabajuro');
CALL insert_ubicacion_geografica('Falcón','Democracia','Agua Clara');
CALL insert_ubicacion_geografica('Falcón','Democracia','Avaria');
CALL insert_ubicacion_geografica('Falcón','Democracia','Pedregal');
CALL insert_ubicacion_geografica('Falcón','Democracia','Piedra Grande');
CALL insert_ubicacion_geografica('Falcón','Democracia','Purureche');
CALL insert_ubicacion_geografica('Falcón','Falcón','Adaure');
CALL insert_ubicacion_geografica('Falcón','Falcón','Adícora');
CALL insert_ubicacion_geografica('Falcón','Falcón','Baraived');
CALL insert_ubicacion_geografica('Falcón','Falcón','Buena Vista');
CALL insert_ubicacion_geografica('Falcón','Falcón','Jadacaquiva');
CALL insert_ubicacion_geografica('Falcón','Falcón','El Vínculo');
CALL insert_ubicacion_geografica('Falcón','Falcón','El Hato');
CALL insert_ubicacion_geografica('Falcón','Falcón','Moruy');
CALL insert_ubicacion_geografica('Falcón','Falcón','Pueblo Nuevo');
CALL insert_ubicacion_geografica('Falcón','Federación','Agua Larga');
CALL insert_ubicacion_geografica('Falcón','Federación','Churuguara');
CALL insert_ubicacion_geografica('Falcón','Federación','El Paují');
CALL insert_ubicacion_geografica('Falcón','Federación','Independencia');
CALL insert_ubicacion_geografica('Falcón','Federación','Mapararí');
CALL insert_ubicacion_geografica('Falcón','Jacura','Agua Linda');
CALL insert_ubicacion_geografica('Falcón','Jacura','Araurima');
CALL insert_ubicacion_geografica('Falcón','Jacura','Jacura');
CALL insert_ubicacion_geografica('Falcón','Los Taques','Los Taques');
CALL insert_ubicacion_geografica('Falcón','Los Taques','Judibana');
CALL insert_ubicacion_geografica('Falcón','Mauroa','Mene de Mauroa');
CALL insert_ubicacion_geografica('Falcón','Mauroa','San Félix');
CALL insert_ubicacion_geografica('Falcón','Mauroa','Casigua');
CALL insert_ubicacion_geografica('Falcón','Miranda','Guzmán Guillermo');
CALL insert_ubicacion_geografica('Falcón','Miranda','Mitare');
CALL insert_ubicacion_geografica('Falcón','Miranda','Río Seco');
CALL insert_ubicacion_geografica('Falcón','Miranda','Sabaneta');
CALL insert_ubicacion_geografica('Falcón','Miranda','San Antonio');
CALL insert_ubicacion_geografica('Falcón','Miranda','San Gabriel');
CALL insert_ubicacion_geografica('Falcón','Miranda','Santa Ana');
CALL insert_ubicacion_geografica('Falcón','Monseñor Iturriza','Boca del Tocuyo');
CALL insert_ubicacion_geografica('Falcón','Monseñor Iturriza','Chichiriviche');
CALL insert_ubicacion_geografica('Falcón','Monseñor Iturriza','Tocuyo de la Costa');
CALL insert_ubicacion_geografica('Falcón','Palmasola','Palmasola');
CALL insert_ubicacion_geografica('Falcón','Petit','Cabure');
CALL insert_ubicacion_geografica('Falcón','Petit','Colina');
CALL insert_ubicacion_geografica('Falcón','Petit','Curimagua');
CALL insert_ubicacion_geografica('Falcón','Píritu','San José de la Costa');
CALL insert_ubicacion_geografica('Falcón','Píritu','Píritu');
CALL insert_ubicacion_geografica('Falcón','San Francisco','Capital San Francisco Mirimire');
CALL insert_ubicacion_geografica('Falcón','José Laurencio Silva','Tucacas');
CALL insert_ubicacion_geografica('Falcón','José Laurencio Silva','Boca de Aroa');
CALL insert_ubicacion_geografica('Falcón','Sucre','Sucre');
CALL insert_ubicacion_geografica('Falcón','Sucre','Pecaya');
CALL insert_ubicacion_geografica('Falcón','Tocópero','Tocópero');
CALL insert_ubicacion_geografica('Falcón','Unión','El Charal');
CALL insert_ubicacion_geografica('Falcón','Unión','Las Vegas del Tuy');
CALL insert_ubicacion_geografica('Falcón','Unión','Santa Cruz de Bucaral');
CALL insert_ubicacion_geografica('Falcón','Urumaco','Bruzual');
CALL insert_ubicacion_geografica('Falcón','Urumaco','Urumaco');
CALL insert_ubicacion_geografica('Falcón','Zamora','Puerto Cumarebo');
CALL insert_ubicacion_geografica('Falcón','Zamora','La Ciénaga');
CALL insert_ubicacion_geografica('Falcón','Zamora','La Soledad');
CALL insert_ubicacion_geografica('Falcón','Zamora','Pueblo Cumarebo');
CALL insert_ubicacion_geografica('Falcón','Zamora','Zazárida');
CALL insert_ubicacion_geografica('Guárico','Camaguán','Camaguán');
CALL insert_ubicacion_geografica('Guárico','Camaguán','Puerto Miranda');
CALL insert_ubicacion_geografica('Guárico','Camaguán','Uverito');
CALL insert_ubicacion_geografica('Guárico','Chaguaramas','Chaguaramas');
CALL insert_ubicacion_geografica('Guárico','El Socorro','El Socorro');
CALL insert_ubicacion_geografica('Guárico','Francisco de Miranda','El Calvario');
CALL insert_ubicacion_geografica('Guárico','Francisco de Miranda','El Rastro');
CALL insert_ubicacion_geografica('Guárico','Francisco de Miranda','Guardatinajas');
CALL insert_ubicacion_geografica('Guárico','Francisco de Miranda','Capital Urbana Calabozo');
CALL insert_ubicacion_geografica('Guárico','José Félix Ribas','Tucupido');
CALL insert_ubicacion_geografica('Guárico','José Félix Ribas','San Rafael de Laya');
CALL insert_ubicacion_geografica('Guárico','José Tadeo Monagas','Altagracia de Orituco.');
CALL insert_ubicacion_geografica('Guárico','José Tadeo Monagas','San Rafael de Orituco.');
CALL insert_ubicacion_geografica('Guárico','José Tadeo Monagas','San Francisco Javier de Lezama.');
CALL insert_ubicacion_geografica('Guárico','José Tadeo Monagas','Paso Real de Macaira.');
CALL insert_ubicacion_geografica('Guárico','José Tadeo Monagas','Carlos Soublette.');
CALL insert_ubicacion_geografica('Guárico','José Tadeo Monagas','San Francisco de Macaira.');
CALL insert_ubicacion_geografica('Guárico','José Tadeo Monagas','Libertad de Orituco.');
CALL insert_ubicacion_geografica('Guárico','Juan Germán Roscio','Cantagallo');
CALL insert_ubicacion_geografica('Guárico','Juan Germán Roscio','San Juan de Los Morros');
CALL insert_ubicacion_geografica('Guárico','Juan Germán Roscio','Parapara');
CALL insert_ubicacion_geografica('Guárico','Julián Mellado','El Sombrero');
CALL insert_ubicacion_geografica('Guárico','Julián Mellado','Sosa');
CALL insert_ubicacion_geografica('Guárico','Las Mercedes','Las Mercedes');
CALL insert_ubicacion_geografica('Guárico','Las Mercedes','Cabruta');
CALL insert_ubicacion_geografica('Guárico','Las Mercedes','Santa Rita de Manapire');
CALL insert_ubicacion_geografica('Guárico','Leonardo Infante','Valle de la Pascua');
CALL insert_ubicacion_geografica('Guárico','Leonardo Infante','Espino');
CALL insert_ubicacion_geografica('Guárico','Ortiz','San José de Tiznados');
CALL insert_ubicacion_geografica('Guárico','Ortiz','San Francisco de Tiznados');
CALL insert_ubicacion_geografica('Guárico','Ortiz','San Lorenzo de Tiznados');
CALL insert_ubicacion_geografica('Guárico','Ortiz','Ortiz');
CALL insert_ubicacion_geografica('Guárico','Pedro Zaraza','San José de Unare');
CALL insert_ubicacion_geografica('Guárico','Pedro Zaraza','Zaraza');
CALL insert_ubicacion_geografica('Guárico','San Jerónimo de Guayabal','Guayabal');
CALL insert_ubicacion_geografica('Guárico','San Jerónimo de Guayabal','Cazorla');
CALL insert_ubicacion_geografica('Guárico','San José de Guaribe','San José de Guaribe');
CALL insert_ubicacion_geografica('Guárico','Santa María de Ipire','Santa María de Ipire');
CALL insert_ubicacion_geografica('Guárico','Santa María de Ipire','Altamira');
CALL insert_ubicacion_geografica('Lara','Andrés Eloy Blanco','Quebrada Honda de Guache');
CALL insert_ubicacion_geografica('Lara','Andrés Eloy Blanco','Pio Tamayo');
CALL insert_ubicacion_geografica('Lara','Andrés Eloy Blanco','Yacambú');
CALL insert_ubicacion_geografica('Lara','Crespo','Freitez');
CALL insert_ubicacion_geografica('Lara','Crespo','José María Blanco');
CALL insert_ubicacion_geografica('Lara','Iribarren','Aguedo Felipe Alvarado');
CALL insert_ubicacion_geografica('Lara','Iribarren','Buena Vista');
CALL insert_ubicacion_geografica('Lara','Iribarren','Catedral');
CALL insert_ubicacion_geografica('Lara','Iribarren','Concepción');
CALL insert_ubicacion_geografica('Lara','Iribarren','El Cují');
CALL insert_ubicacion_geografica('Lara','Iribarren','Juárez');
CALL insert_ubicacion_geografica('Lara','Iribarren','Ana Soto (Antigua Juan de Villegas)');
CALL insert_ubicacion_geografica('Lara','Iribarren','Santa Rosa');
CALL insert_ubicacion_geografica('Lara','Iribarren','Tamaca');
CALL insert_ubicacion_geografica('Lara','Iribarren','Unión');
CALL insert_ubicacion_geografica('Lara','Jiménez','Juan Bautista Rodríguez');
CALL insert_ubicacion_geografica('Lara','Jiménez','Cuara');
CALL insert_ubicacion_geografica('Lara','Jiménez','Diego de Lozada');
CALL insert_ubicacion_geografica('Lara','Jiménez','Paraíso de San José');
CALL insert_ubicacion_geografica('Lara','Jiménez','San Miguel');
CALL insert_ubicacion_geografica('Lara','Jiménez','Tintorero');
CALL insert_ubicacion_geografica('Lara','Jiménez','José Bernardo Dorante');
CALL insert_ubicacion_geografica('Lara','Jiménez','Coronel Mariano Peraza');
CALL insert_ubicacion_geografica('Lara','Morán','Anzoátegui');
CALL insert_ubicacion_geografica('Lara','Morán','Bolívar');
CALL insert_ubicacion_geografica('Lara','Morán','Guárico');
CALL insert_ubicacion_geografica('Lara','Morán','Hilario Luna y Luna');
CALL insert_ubicacion_geografica('Lara','Morán','Humocaro Bajo');
CALL insert_ubicacion_geografica('Lara','Morán','Humocaro Alto');
CALL insert_ubicacion_geografica('Lara','Morán','La Candelaria');
CALL insert_ubicacion_geografica('Lara','Morán','Morán');
CALL insert_ubicacion_geografica('Lara','Palavecino','Cabudare');
CALL insert_ubicacion_geografica('Lara','Palavecino','José Gregorio Bastidas');
CALL insert_ubicacion_geografica('Lara','Palavecino','Agua Viva');
CALL insert_ubicacion_geografica('Lara','Simón Planas','Buría');
CALL insert_ubicacion_geografica('Lara','Simón Planas','Gustavo Vega');
CALL insert_ubicacion_geografica('Lara','Simón Planas','Sarare');
CALL insert_ubicacion_geografica('Lara','Torres','Altagracia');
CALL insert_ubicacion_geografica('Lara','Torres','Antonio Díaz');
CALL insert_ubicacion_geografica('Lara','Torres','Camacaro');
CALL insert_ubicacion_geografica('Lara','Torres','Castañeda');
CALL insert_ubicacion_geografica('Lara','Torres','Cecilio Zubillaga');
CALL insert_ubicacion_geografica('Lara','Torres','Chiquinquira');
CALL insert_ubicacion_geografica('Lara','Torres','El Blanco');
CALL insert_ubicacion_geografica('Lara','Torres','Espinoza de los Monteros');
CALL insert_ubicacion_geografica('Lara','Torres','Heriberto Arrollo');
CALL insert_ubicacion_geografica('Lara','Torres','Lara');
CALL insert_ubicacion_geografica('Lara','Torres','Las Mercedes');
CALL insert_ubicacion_geografica('Lara','Torres','Manuel Morillo');
CALL insert_ubicacion_geografica('Lara','Torres','Montaña Verde');
CALL insert_ubicacion_geografica('Lara','Torres','Montes de Oca');
CALL insert_ubicacion_geografica('Lara','Torres','Reyes de Vargas');
CALL insert_ubicacion_geografica('Lara','Torres','Torres');
CALL insert_ubicacion_geografica('Lara','Torres','Trinidad Samuel');
CALL insert_ubicacion_geografica('Lara','Urdaneta','Xaguas');
CALL insert_ubicacion_geografica('Lara','Urdaneta','Siquisique');
CALL insert_ubicacion_geografica('Lara','Urdaneta','San Miguel');
CALL insert_ubicacion_geografica('Lara','Urdaneta','Moroturo');
CALL insert_ubicacion_geografica('Mérida','Alberto Adriani','Presidente Betancourt');
CALL insert_ubicacion_geografica('Mérida','Alberto Adriani','Presidente Páez');
CALL insert_ubicacion_geografica('Mérida','Alberto Adriani','Presidente Rómulo Gallegos');
CALL insert_ubicacion_geografica('Mérida','Alberto Adriani','Gabriel Picón González');
CALL insert_ubicacion_geografica('Mérida','Alberto Adriani','Héctor Amable Mora');
CALL insert_ubicacion_geografica('Mérida','Alberto Adriani','José Nucete Sardi');
CALL insert_ubicacion_geografica('Mérida','Alberto Adriani','Pulido Méndez');
CALL insert_ubicacion_geografica('Mérida','Andrés Bello','La Azulita');
CALL insert_ubicacion_geografica('Mérida','Antonio Pinto Salinas','Santa Cruz de Mora');
CALL insert_ubicacion_geografica('Mérida','Antonio Pinto Salinas','Mesa Bolívar');
CALL insert_ubicacion_geografica('Mérida','Antonio Pinto Salinas','Mesa de Las Palmas');
CALL insert_ubicacion_geografica('Mérida','Aricagua','Aricagua');
CALL insert_ubicacion_geografica('Mérida','Aricagua','San Antonio');
CALL insert_ubicacion_geografica('Mérida','Arzobispo Chacón','Canagua');
CALL insert_ubicacion_geografica('Mérida','Arzobispo Chacón','Capurí');
CALL insert_ubicacion_geografica('Mérida','Arzobispo Chacón','Chacantá');
CALL insert_ubicacion_geografica('Mérida','Arzobispo Chacón','El Molino');
CALL insert_ubicacion_geografica('Mérida','Arzobispo Chacón','Guaimaral');
CALL insert_ubicacion_geografica('Mérida','Arzobispo Chacón','Mucutuy');
CALL insert_ubicacion_geografica('Mérida','Arzobispo Chacón','Mucuchachí');
CALL insert_ubicacion_geografica('Mérida','Campo Elías','Fernández Peña');
CALL insert_ubicacion_geografica('Mérida','Campo Elías','Matriz');
CALL insert_ubicacion_geografica('Mérida','Campo Elías','Montalbán');
CALL insert_ubicacion_geografica('Mérida','Campo Elías','Acequias');
CALL insert_ubicacion_geografica('Mérida','Campo Elías','Jají');
CALL insert_ubicacion_geografica('Mérida','Campo Elías','La Mesa');
CALL insert_ubicacion_geografica('Mérida','Campo Elías','San José del Sur');
CALL insert_ubicacion_geografica('Mérida','Caracciolo Parra Olmedo','Tucaní');
CALL insert_ubicacion_geografica('Mérida','Caracciolo Parra Olmedo','Florencio Ramírez');
CALL insert_ubicacion_geografica('Mérida','Cardenal Quintero','Santo Domingo');
CALL insert_ubicacion_geografica('Mérida','Cardenal Quintero','Las Piedras');
CALL insert_ubicacion_geografica('Mérida','Guaraque','Guaraque');
CALL insert_ubicacion_geografica('Mérida','Guaraque','Mesa de Quintero');
CALL insert_ubicacion_geografica('Mérida','Guaraque','Río Negro');
CALL insert_ubicacion_geografica('Mérida','Julio César Salas','Arapuey');
CALL insert_ubicacion_geografica('Mérida','Julio César Salas','Palmira');
CALL insert_ubicacion_geografica('Mérida','Justo Briceño','San Cristóbal de Torondoy');
CALL insert_ubicacion_geografica('Mérida','Justo Briceño','Torondoy');
CALL insert_ubicacion_geografica('Mérida','Libertador','Antonio Spinetti Dini');
CALL insert_ubicacion_geografica('Mérida','Libertador','Arias');
CALL insert_ubicacion_geografica('Mérida','Libertador','Caracciolo Parra Pérez');
CALL insert_ubicacion_geografica('Mérida','Libertador','Domingo Peña');
CALL insert_ubicacion_geografica('Mérida','Libertador','El Llano');
CALL insert_ubicacion_geografica('Mérida','Libertador','Gonzalo Picón Febres');
CALL insert_ubicacion_geografica('Mérida','Libertador','Jacinto Plaza');
CALL insert_ubicacion_geografica('Mérida','Libertador','Juan Rodríguez Suárez');
CALL insert_ubicacion_geografica('Mérida','Libertador','Lasso de la Vega');
CALL insert_ubicacion_geografica('Mérida','Libertador','Mariano Picón Salas');
CALL insert_ubicacion_geografica('Mérida','Libertador','Milla');
CALL insert_ubicacion_geografica('Mérida','Libertador','Osuna Rodríguez');
CALL insert_ubicacion_geografica('Mérida','Libertador','Sagrario');
CALL insert_ubicacion_geografica('Mérida','Libertador','El Morro');
CALL insert_ubicacion_geografica('Mérida','Libertador','Los Nevados');
CALL insert_ubicacion_geografica('Mérida','Miranda','Andrés Eloy Blanco');
CALL insert_ubicacion_geografica('Mérida','Miranda','La Venta');
CALL insert_ubicacion_geografica('Mérida','Miranda','Piñango');
CALL insert_ubicacion_geografica('Mérida','Miranda','Timotes');
CALL insert_ubicacion_geografica('Mérida','Obispo Ramos de Lora','Eloy Paredes');
CALL insert_ubicacion_geografica('Mérida','Obispo Ramos de Lora','San Rafael de Alcázar');
CALL insert_ubicacion_geografica('Mérida','Obispo Ramos de Lora','Santa Elena de Arenales');
CALL insert_ubicacion_geografica('Mérida','Padre Noguera','Santa María de Caparo');
CALL insert_ubicacion_geografica('Mérida','Pueblo Llano','Pueblo Llano');
CALL insert_ubicacion_geografica('Mérida','Rangel','Cacute');
CALL insert_ubicacion_geografica('Mérida','Rangel','La Toma');
CALL insert_ubicacion_geografica('Mérida','Rangel','Mucuchíes');
CALL insert_ubicacion_geografica('Mérida','Rangel','Mucurubá');
CALL insert_ubicacion_geografica('Mérida','Rangel','San Rafael');
CALL insert_ubicacion_geografica('Mérida','Rivas Dávila','Gerónimo Maldonado');
CALL insert_ubicacion_geografica('Mérida','Rivas Dávila','Bailadores');
CALL insert_ubicacion_geografica('Mérida','Santos Marquina','Tabay');
CALL insert_ubicacion_geografica('Mérida','Sucre','Chiguará');
CALL insert_ubicacion_geografica('Mérida','Sucre','Estánques');
CALL insert_ubicacion_geografica('Mérida','Sucre','Lagunillas');
CALL insert_ubicacion_geografica('Mérida','Sucre','La Trampa');
CALL insert_ubicacion_geografica('Mérida','Sucre','Pueblo Nuevo del Sur');
CALL insert_ubicacion_geografica('Mérida','Sucre','San Juan');
CALL insert_ubicacion_geografica('Mérida','Tovar','El Amparo');
CALL insert_ubicacion_geografica('Mérida','Tovar','El Llano');
CALL insert_ubicacion_geografica('Mérida','Tovar','San Francisco');
CALL insert_ubicacion_geografica('Mérida','Tovar','Tovar');
CALL insert_ubicacion_geografica('Mérida','Tulio Febres Cordero','Independencia');
CALL insert_ubicacion_geografica('Mérida','Tulio Febres Cordero','María de la Concepción Palacios Blanco');
CALL insert_ubicacion_geografica('Mérida','Tulio Febres Cordero','Nueva Bolivia');
CALL insert_ubicacion_geografica('Mérida','Tulio Febres Cordero','Santa Apolonia');
CALL insert_ubicacion_geografica('Mérida','Zea','Caño El Tigre');
CALL insert_ubicacion_geografica('Mérida','Zea','Zea');
CALL insert_ubicacion_geografica('Miranda','Acevedo','Aragüita');
CALL insert_ubicacion_geografica('Miranda','Acevedo','Arévalo González,');
CALL insert_ubicacion_geografica('Miranda','Acevedo','Capaya');
CALL insert_ubicacion_geografica('Miranda','Acevedo','Caucagua');
CALL insert_ubicacion_geografica('Miranda','Acevedo','Panaquire');
CALL insert_ubicacion_geografica('Miranda','Acevedo','Ribas');
CALL insert_ubicacion_geografica('Miranda','Acevedo','El Café');
CALL insert_ubicacion_geografica('Miranda','Acevedo','Marizapa');
CALL insert_ubicacion_geografica('Miranda','Andrés Bello','Cumbo');
CALL insert_ubicacion_geografica('Miranda','Andrés Bello','San José de Barlovento');
CALL insert_ubicacion_geografica('Miranda','Baruta','El Cafetal');
CALL insert_ubicacion_geografica('Miranda','Baruta','Las Minas');
CALL insert_ubicacion_geografica('Miranda','Brión','Higuerote');
CALL insert_ubicacion_geografica('Miranda','Brión','Curiepe');
CALL insert_ubicacion_geografica('Miranda','Brión','Tacarigua de Brión');
CALL insert_ubicacion_geografica('Miranda','Buroz','Mamporal');
CALL insert_ubicacion_geografica('Miranda','Carrizal','Carrizal');
CALL insert_ubicacion_geografica('Miranda','Chacao','Chacao');
CALL insert_ubicacion_geografica('Miranda','Cristóbal Rojas','Charallave');
CALL insert_ubicacion_geografica('Miranda','Cristóbal Rojas','Las Brisas');
CALL insert_ubicacion_geografica('Miranda','El Hatillo','Santa Rosalía de Palermo de El Hatillo');
CALL insert_ubicacion_geografica('Miranda','Guaicaipuro','Altagracia de la Montaña');
CALL insert_ubicacion_geografica('Miranda','Guaicaipuro','Cecilio Acosta');
CALL insert_ubicacion_geografica('Miranda','Guaicaipuro','Los Teques');
CALL insert_ubicacion_geografica('Miranda','Guaicaipuro','El Jarillo.');
CALL insert_ubicacion_geografica('Miranda','Guaicaipuro','San Pedro.');
CALL insert_ubicacion_geografica('Miranda','Guaicaipuro','Tácata.');
CALL insert_ubicacion_geografica('Miranda','Guaicaipuro','Paracotos.');
CALL insert_ubicacion_geografica('Miranda','Independencia','Cartanal');
CALL insert_ubicacion_geografica('Miranda','Independencia','Santa Teresa del Tuy');
CALL insert_ubicacion_geografica('Miranda','Lander','La Democracia');
CALL insert_ubicacion_geografica('Miranda','Lander','Ocumare del Tuy');
CALL insert_ubicacion_geografica('Miranda','Lander','Santa Bárbara');
CALL insert_ubicacion_geografica('Miranda','Los Salias','San Antonio de los Altos');
CALL insert_ubicacion_geografica('Miranda','Páez','Río Chico');
CALL insert_ubicacion_geografica('Miranda','Páez','El Guapo');
CALL insert_ubicacion_geografica('Miranda','Páez','Tacarigua de la Laguna');
CALL insert_ubicacion_geografica('Miranda','Páez','Paparo');
CALL insert_ubicacion_geografica('Miranda','Páez','San Fernando del Guapo');
CALL insert_ubicacion_geografica('Miranda','Paz Castillo','Santa Lucía del Tuy');
CALL insert_ubicacion_geografica('Miranda','Pedro Gual','Cúpira');
CALL insert_ubicacion_geografica('Miranda','Pedro Gual','Machurucuto');
CALL insert_ubicacion_geografica('Miranda','Plaza','Guarenas');
CALL insert_ubicacion_geografica('Miranda','Simón Bolívar','San Antonio de Yare');
CALL insert_ubicacion_geografica('Miranda','Simón Bolívar','San Francisco de Yare');
CALL insert_ubicacion_geografica('Miranda','Sucre','Leoncio Martínez');
CALL insert_ubicacion_geografica('Miranda','Sucre','Caucagüita');
CALL insert_ubicacion_geografica('Miranda','Sucre','Filas de Mariche');
CALL insert_ubicacion_geografica('Miranda','Sucre','La Dolorita');
CALL insert_ubicacion_geografica('Miranda','Sucre','Petare');
CALL insert_ubicacion_geografica('Miranda','Urdaneta','Cúa');
CALL insert_ubicacion_geografica('Miranda','Urdaneta','Nueva Cúa');
CALL insert_ubicacion_geografica('Miranda','Zamora','Guatire');
CALL insert_ubicacion_geografica('Miranda','Zamora','Bolívar');
CALL insert_ubicacion_geografica('Monagas','Acosta','San Antonio de Maturín');
CALL insert_ubicacion_geografica('Monagas','Acosta','San Francisco de Maturín');
CALL insert_ubicacion_geografica('Monagas','Aguasay','Aguasay');
CALL insert_ubicacion_geografica('Monagas','Bolívar','Caripito');
CALL insert_ubicacion_geografica('Monagas','Caripe','El Guácharo');
CALL insert_ubicacion_geografica('Monagas','Caripe','La Guanota');
CALL insert_ubicacion_geografica('Monagas','Caripe','Sabana de Piedra');
CALL insert_ubicacion_geografica('Monagas','Caripe','San Agustín');
CALL insert_ubicacion_geografica('Monagas','Caripe','Teresen');
CALL insert_ubicacion_geografica('Monagas','Caripe','Caripe');
CALL insert_ubicacion_geografica('Monagas','Cedeño','Areo');
CALL insert_ubicacion_geografica('Monagas','Cedeño','Capital Cedeño');
CALL insert_ubicacion_geografica('Monagas','Cedeño','San Félix de Cantalicio');
CALL insert_ubicacion_geografica('Monagas','Cedeño','Viento Fresco');
CALL insert_ubicacion_geografica('Monagas','Ezequiel Zamora','El Tejero');
CALL insert_ubicacion_geografica('Monagas','Ezequiel Zamora','Punta de Mata');
CALL insert_ubicacion_geografica('Monagas','Libertador','Chaguaramas');
CALL insert_ubicacion_geografica('Monagas','Libertador','Las Alhuacas');
CALL insert_ubicacion_geografica('Monagas','Libertador','Tabasca');
CALL insert_ubicacion_geografica('Monagas','Libertador','Temblador');
CALL insert_ubicacion_geografica('Monagas','Maturín','Alto de los Godos');
CALL insert_ubicacion_geografica('Monagas','Maturín','Boquerón');
CALL insert_ubicacion_geografica('Monagas','Maturín','Las Cocuizas');
CALL insert_ubicacion_geografica('Monagas','Maturín','La Cruz');
CALL insert_ubicacion_geografica('Monagas','Maturín','San Simón');
CALL insert_ubicacion_geografica('Monagas','Maturín','El Corozo');
CALL insert_ubicacion_geografica('Monagas','Maturín','El Furrial');
CALL insert_ubicacion_geografica('Monagas','Maturín','Jusepín');
CALL insert_ubicacion_geografica('Monagas','Maturín','La Pica');
CALL insert_ubicacion_geografica('Monagas','Maturín','San Vicente');
CALL insert_ubicacion_geografica('Monagas','Piar','Aparicio');
CALL insert_ubicacion_geografica('Monagas','Piar','Aragua de Maturín');
CALL insert_ubicacion_geografica('Monagas','Piar','Chaguaramal');
CALL insert_ubicacion_geografica('Monagas','Piar','El Pinto');
CALL insert_ubicacion_geografica('Monagas','Piar','Guanaguana');
CALL insert_ubicacion_geografica('Monagas','Piar','La Toscana');
CALL insert_ubicacion_geografica('Monagas','Piar','Taguaya');
CALL insert_ubicacion_geografica('Monagas','Punceres','Cachipo');
CALL insert_ubicacion_geografica('Monagas','Punceres','Quiriquire');
CALL insert_ubicacion_geografica('Monagas','Santa Bárbara','Santa Bárbara');
CALL insert_ubicacion_geografica('Monagas','Santa Bárbara','Morón');
CALL insert_ubicacion_geografica('Monagas','Sotillo','Barrancas');
CALL insert_ubicacion_geografica('Monagas','Sotillo','Los Barrancos de Fajardo');
CALL insert_ubicacion_geografica('Monagas','Uracoa','Uracoa');
CALL insert_ubicacion_geografica('Nueva Esparta','Antolín del Campo','Antolín del Campo');
CALL insert_ubicacion_geografica('Nueva Esparta','Arismendi','Arismendi');
CALL insert_ubicacion_geografica('Nueva Esparta','Díaz','San Juan Bautista');
CALL insert_ubicacion_geografica('Nueva Esparta','Díaz','Zabala');
CALL insert_ubicacion_geografica('Nueva Esparta','García','García');
CALL insert_ubicacion_geografica('Nueva Esparta','García','Francisco Fajardo');
CALL insert_ubicacion_geografica('Nueva Esparta','Gómez','Bolívar');
CALL insert_ubicacion_geografica('Nueva Esparta','Gómez','Guevara');
CALL insert_ubicacion_geografica('Nueva Esparta','Gómez','Cerro de Matasiete');
CALL insert_ubicacion_geografica('Nueva Esparta','Gómez','Santa Ana');
CALL insert_ubicacion_geografica('Nueva Esparta','Gómez','Sucre');
CALL insert_ubicacion_geografica('Nueva Esparta','Macanao','San Francisco de Macanao');
CALL insert_ubicacion_geografica('Nueva Esparta','Macanao','Boca de Río');
CALL insert_ubicacion_geografica('Nueva Esparta','Maneiro','Aguirre');
CALL insert_ubicacion_geografica('Nueva Esparta','Maneiro','Maneiro');
CALL insert_ubicacion_geografica('Nueva Esparta','Marcano','Adrián');
CALL insert_ubicacion_geografica('Nueva Esparta','Marcano','Juan Griego');
CALL insert_ubicacion_geografica('Nueva Esparta','Mariño','Mariño');
CALL insert_ubicacion_geografica('Nueva Esparta','Tubores','Tubores');
CALL insert_ubicacion_geografica('Nueva Esparta','Tubores','Los Barales');
CALL insert_ubicacion_geografica('Nueva Esparta','Villalba','Vicente Fuentes');
CALL insert_ubicacion_geografica('Nueva Esparta','Villalba','Villalba');
CALL insert_ubicacion_geografica('Portuguesa','Agua Blanca','Agua Blanca');
CALL insert_ubicacion_geografica('Portuguesa','Araure','Araure');
CALL insert_ubicacion_geografica('Portuguesa','Araure','Río Acarigua');
CALL insert_ubicacion_geografica('Portuguesa','Esteller','Píritu');
CALL insert_ubicacion_geografica('Portuguesa','Esteller','Uveral');
CALL insert_ubicacion_geografica('Portuguesa','Guanare','Cordova');
CALL insert_ubicacion_geografica('Portuguesa','Guanare','Guanare');
CALL insert_ubicacion_geografica('Portuguesa','Guanare','San José de la Montaña');
CALL insert_ubicacion_geografica('Portuguesa','Guanare','San Juan de Guanaguanare');
CALL insert_ubicacion_geografica('Portuguesa','Guanare','Virgen de Coromoto');
CALL insert_ubicacion_geografica('Portuguesa','Guanarito','Guanarito');
CALL insert_ubicacion_geografica('Portuguesa','Guanarito','Trinidad de la Capilla');
CALL insert_ubicacion_geografica('Portuguesa','Guanarito','Divina Pastora');
CALL insert_ubicacion_geografica('Portuguesa','Monseñor José Vicente de Unda','Chabasquén');
CALL insert_ubicacion_geografica('Portuguesa','Monseñor José Vicente de Unda','Peña Blanca');
CALL insert_ubicacion_geografica('Portuguesa','Ospino','Aparición');
CALL insert_ubicacion_geografica('Portuguesa','Ospino','La Estación');
CALL insert_ubicacion_geografica('Portuguesa','Ospino','Ospino');
CALL insert_ubicacion_geografica('Portuguesa','Páez','Acarigua');
CALL insert_ubicacion_geografica('Portuguesa','Páez','Payara');
CALL insert_ubicacion_geografica('Portuguesa','Páez','Pimpinela');
CALL insert_ubicacion_geografica('Portuguesa','Páez','Ramón Peraza');
CALL insert_ubicacion_geografica('Portuguesa','Papelón','Caño Delgadito');
CALL insert_ubicacion_geografica('Portuguesa','Papelón','Papelón');
CALL insert_ubicacion_geografica('Portuguesa','San Genaro de Boconoíto','Antolín Tovar Anquino');
CALL insert_ubicacion_geografica('Portuguesa','San Genaro de Boconoíto','Boconoíto');
CALL insert_ubicacion_geografica('Portuguesa','San Rafael de Onoto','Santa Fé');
CALL insert_ubicacion_geografica('Portuguesa','San Rafael de Onoto','San Rafael de Onoto');
CALL insert_ubicacion_geografica('Portuguesa','San Rafael de Onoto','Thelmo Morles');
CALL insert_ubicacion_geografica('Portuguesa','Santa Rosalía','Florida');
CALL insert_ubicacion_geografica('Portuguesa','Santa Rosalía','El Playón');
CALL insert_ubicacion_geografica('Portuguesa','Sucre','Biscucuy');
CALL insert_ubicacion_geografica('Portuguesa','Sucre','Concepción');
CALL insert_ubicacion_geografica('Portuguesa','Sucre','San José de Saguaz');
CALL insert_ubicacion_geografica('Portuguesa','Sucre','San Rafael de Palo Alzado');
CALL insert_ubicacion_geografica('Portuguesa','Sucre','Uvencio Antonio Velásquez');
CALL insert_ubicacion_geografica('Portuguesa','Turén','Villa Bruzual');
CALL insert_ubicacion_geografica('Portuguesa','Turén','Canelones');
CALL insert_ubicacion_geografica('Portuguesa','Turén','Santa Cruz');
CALL insert_ubicacion_geografica('Portuguesa','Turén','San Isidro Labrador');
CALL insert_ubicacion_geografica('Sucre','Andrés Eloy Blanco','Mariño');
CALL insert_ubicacion_geografica('Sucre','Andrés Eloy Blanco','Rómulo Gallegos');
CALL insert_ubicacion_geografica('Sucre','Andrés Mata','San José de Areocuar');
CALL insert_ubicacion_geografica('Sucre','Andrés Mata','Tavera Acosta');
CALL insert_ubicacion_geografica('Sucre','Arismendi','Río Caribe');
CALL insert_ubicacion_geografica('Sucre','Arismendi','Antonio José de Sucre');
CALL insert_ubicacion_geografica('Sucre','Arismendi','El Morro de Puerto Santo');
CALL insert_ubicacion_geografica('Sucre','Arismendi','Puerto Santo');
CALL insert_ubicacion_geografica('Sucre','Arismendi','San Juan de las Galdonas');
CALL insert_ubicacion_geografica('Sucre','Benítez','El Pilar');
CALL insert_ubicacion_geografica('Sucre','Benítez','El Rincón');
CALL insert_ubicacion_geografica('Sucre','Benítez','General Francisco Antonio Vázquez');
CALL insert_ubicacion_geografica('Sucre','Benítez','Guaraúnos');
CALL insert_ubicacion_geografica('Sucre','Benítez','Tunapuicito');
CALL insert_ubicacion_geografica('Sucre','Benítez','Unión');
CALL insert_ubicacion_geografica('Sucre','Bermúdez','Santa Catalina');
CALL insert_ubicacion_geografica('Sucre','Bermúdez','Santa Rosa');
CALL insert_ubicacion_geografica('Sucre','Bermúdez','Santa Teresa');
CALL insert_ubicacion_geografica('Sucre','Bermúdez','Bolívar');
CALL insert_ubicacion_geografica('Sucre','Bermúdez','Maracapana');
CALL insert_ubicacion_geografica('Sucre','Bolívar','Marigüitar');
CALL insert_ubicacion_geografica('Sucre','Cajigal','Libertad');
CALL insert_ubicacion_geografica('Sucre','Cajigal','El Paujil');
CALL insert_ubicacion_geografica('Sucre','Cajigal','Yaguaraparo');
CALL insert_ubicacion_geografica('Sucre','Cruz Salmerón Acosta','Araya');
CALL insert_ubicacion_geografica('Sucre','Cruz Salmerón Acosta','Chacopata');
CALL insert_ubicacion_geografica('Sucre','Cruz Salmerón Acosta','Manicuare');
CALL insert_ubicacion_geografica('Sucre','Libertador','Tunapuy');
CALL insert_ubicacion_geografica('Sucre','Libertador','Campo Elías');
CALL insert_ubicacion_geografica('Sucre','Mariño','Irapa');
CALL insert_ubicacion_geografica('Sucre','Mariño','Campo Claro');
CALL insert_ubicacion_geografica('Sucre','Mariño','Marabal');
CALL insert_ubicacion_geografica('Sucre','Mariño','San Antonio de Irapa');
CALL insert_ubicacion_geografica('Sucre','Mariño','Soro');
CALL insert_ubicacion_geografica('Sucre','Mejía','San Antonio del Golfo');
CALL insert_ubicacion_geografica('Sucre','Montes','Cumanacoa');
CALL insert_ubicacion_geografica('Sucre','Montes','Arenas');
CALL insert_ubicacion_geografica('Sucre','Montes','Aricagua');
CALL insert_ubicacion_geografica('Sucre','Montes','Cocollar');
CALL insert_ubicacion_geografica('Sucre','Montes','San Fernando');
CALL insert_ubicacion_geografica('Sucre','Montes','San Lorenzo');
CALL insert_ubicacion_geografica('Sucre','Ribero','Cariaco');
CALL insert_ubicacion_geografica('Sucre','Ribero','Catuaro');
CALL insert_ubicacion_geografica('Sucre','Ribero','Rendón');
CALL insert_ubicacion_geografica('Sucre','Ribero','Santa Cruz');
CALL insert_ubicacion_geografica('Sucre','Ribero','Santa María');
CALL insert_ubicacion_geografica('Sucre','Sucre','Altagracia Cumaná');
CALL insert_ubicacion_geografica('Sucre','Sucre','Santa Inés Cumaná');
CALL insert_ubicacion_geografica('Sucre','Sucre','Valentín Valiente Cumaná');
CALL insert_ubicacion_geografica('Sucre','Sucre','Ayacucho Cumaná');
CALL insert_ubicacion_geografica('Sucre','Sucre','San Juan');
CALL insert_ubicacion_geografica('Sucre','Sucre','Raúl Leoni');
CALL insert_ubicacion_geografica('Sucre','Sucre','Gran Mariscal');
CALL insert_ubicacion_geografica('Sucre','Valdez','Cristóbal Colón');
CALL insert_ubicacion_geografica('Sucre','Valdez','Bideau');
CALL insert_ubicacion_geografica('Sucre','Valdez','Punta de Piedras');
CALL insert_ubicacion_geografica('Sucre','Valdez','Güiria');
CALL insert_ubicacion_geografica('Táchira','Andrés Bello','Cordero');
CALL insert_ubicacion_geografica('Táchira','Antonio Rómulo Costa','Las Mesas');
CALL insert_ubicacion_geografica('Táchira','Ayacucho','Rivas Berti');
CALL insert_ubicacion_geografica('Táchira','Ayacucho','San Juan de Colón');
CALL insert_ubicacion_geografica('Táchira','Ayacucho','San Pedro del Río');
CALL insert_ubicacion_geografica('Táchira','Bolívar','Isaías Medina Angarita');
CALL insert_ubicacion_geografica('Táchira','Bolívar','Juan Vicente Gómez');
CALL insert_ubicacion_geografica('Táchira','Bolívar','Palotal');
CALL insert_ubicacion_geografica('Táchira','Bolívar','San Antonio del Táchira');
CALL insert_ubicacion_geografica('Táchira','Cárdenas','Amenodoro Rangel Lamús');
CALL insert_ubicacion_geografica('Táchira','Cárdenas','La Florida');
CALL insert_ubicacion_geografica('Táchira','Cárdenas','Táriba');
CALL insert_ubicacion_geografica('Táchira','Córdoba','Santa Ana del Táchira');
CALL insert_ubicacion_geografica('Táchira','Fernández Feo','Alberto Adriani');
CALL insert_ubicacion_geografica('Táchira','Fernández Feo','San Rafael del Piñal');
CALL insert_ubicacion_geografica('Táchira','Fernández Feo','Santo Domingo');
CALL insert_ubicacion_geografica('Táchira','Francisco de Miranda','San José de Bolívar');
CALL insert_ubicacion_geografica('Táchira','García de Hevia','Boca de Grita');
CALL insert_ubicacion_geografica('Táchira','García de Hevia','José Antonio Páez');
CALL insert_ubicacion_geografica('Táchira','García de Hevia','La Fría');
CALL insert_ubicacion_geografica('Táchira','Guásimos','Palmira');
CALL insert_ubicacion_geografica('Táchira','Independencia','Capacho Nuevo');
CALL insert_ubicacion_geografica('Táchira','Independencia','Juan Germán Roscio');
CALL insert_ubicacion_geografica('Táchira','Independencia','Román Cárdenas');
CALL insert_ubicacion_geografica('Táchira','Jáuregui','Emilio Constantino Guerrero');
CALL insert_ubicacion_geografica('Táchira','Jáuregui','La Grita');
CALL insert_ubicacion_geografica('Táchira','Jáuregui','Monseñor Miguel Antonio Salas');
CALL insert_ubicacion_geografica('Táchira','José María Vargas','El Cobre');
CALL insert_ubicacion_geografica('Táchira','Junín','Bramón');
CALL insert_ubicacion_geografica('Táchira','Junín','La Petrólea');
CALL insert_ubicacion_geografica('Táchira','Junín','Quinimarí');
CALL insert_ubicacion_geografica('Táchira','Junín','Rubio');
CALL insert_ubicacion_geografica('Táchira','Libertad','Capacho Viejo');
CALL insert_ubicacion_geografica('Táchira','Libertad','Cipriano Castro');
CALL insert_ubicacion_geografica('Táchira','Libertad','Manuel Felipe Rugeles');
CALL insert_ubicacion_geografica('Táchira','Libertador','Abejales');
CALL insert_ubicacion_geografica('Táchira','Libertador','Doradas');
CALL insert_ubicacion_geografica('Táchira','Libertador','Emeterio Ochoa');
CALL insert_ubicacion_geografica('Táchira','Libertador','San Joaquín de Navay');
CALL insert_ubicacion_geografica('Táchira','Lobatera','Lobatera');
CALL insert_ubicacion_geografica('Táchira','Lobatera','Constitución');
CALL insert_ubicacion_geografica('Táchira','Michelena','Michelena');
CALL insert_ubicacion_geografica('Táchira','Panamericano','Coloncito');
CALL insert_ubicacion_geografica('Táchira','Panamericano','La Palmita');
CALL insert_ubicacion_geografica('Táchira','Pedro María Ureña','Ureña');
CALL insert_ubicacion_geografica('Táchira','Pedro María Ureña','Nueva Arcadia');
CALL insert_ubicacion_geografica('Táchira','Rafael Urdaneta','Delicias');
CALL insert_ubicacion_geografica('Táchira','Samuel Dario Maldonado','Boconó');
CALL insert_ubicacion_geografica('Táchira','Samuel Dario Maldonado','Hernández');
CALL insert_ubicacion_geografica('Táchira','Samuel Dario Maldonado','La Tendida');
CALL insert_ubicacion_geografica('Táchira','San Cristóbal','Francisco Romero Lobo');
CALL insert_ubicacion_geografica('Táchira','San Cristóbal','La Concordia');
CALL insert_ubicacion_geografica('Táchira','San Cristóbal','Pedro María Morantes');
CALL insert_ubicacion_geografica('Táchira','San Cristóbal','San Juan Bautista');
CALL insert_ubicacion_geografica('Táchira','San Cristóbal','San Sebastián');
CALL insert_ubicacion_geografica('Táchira','San Judas Tadeo','Umuquena');
CALL insert_ubicacion_geografica('Táchira','Seboruco','Seboruco');
CALL insert_ubicacion_geografica('Táchira','Simón Rodríguez','San Simón');
CALL insert_ubicacion_geografica('Táchira','Sucre','Eleazar López Contreras');
CALL insert_ubicacion_geografica('Táchira','Sucre','Capital Sucre');
CALL insert_ubicacion_geografica('Táchira','Sucre','San Pablo');
CALL insert_ubicacion_geografica('Táchira','Torbes','San Josecito');
CALL insert_ubicacion_geografica('Táchira','Uribante','Cárdenas');
CALL insert_ubicacion_geografica('Táchira','Uribante','Juan Pablo Peñaloza');
CALL insert_ubicacion_geografica('Táchira','Uribante','Potosí');
CALL insert_ubicacion_geografica('Táchira','Uribante','Pregonero');
CALL insert_ubicacion_geografica('Trujillo','Andrés Bello','Araguaney');
CALL insert_ubicacion_geografica('Trujillo','Andrés Bello','El Jaguito');
CALL insert_ubicacion_geografica('Trujillo','Andrés Bello','La Esperanza');
CALL insert_ubicacion_geografica('Trujillo','Andrés Bello','Santa Isabel');
CALL insert_ubicacion_geografica('Trujillo','Boconó','Boconó');
CALL insert_ubicacion_geografica('Trujillo','Boconó','El Carmen');
CALL insert_ubicacion_geografica('Trujillo','Boconó','Mosquey');
CALL insert_ubicacion_geografica('Trujillo','Boconó','Ayacucho');
CALL insert_ubicacion_geografica('Trujillo','Boconó','Burbusay');
CALL insert_ubicacion_geografica('Trujillo','Boconó','General Ribas');
CALL insert_ubicacion_geografica('Trujillo','Boconó','Guaramacal');
CALL insert_ubicacion_geografica('Trujillo','Boconó','Vega De Guaramacal');
CALL insert_ubicacion_geografica('Trujillo','Boconó','Monseñor Jáuregui');
CALL insert_ubicacion_geografica('Trujillo','Boconó','Rafael Rangel');
CALL insert_ubicacion_geografica('Trujillo','Boconó','San Miguel');
CALL insert_ubicacion_geografica('Trujillo','Boconó','San José');
CALL insert_ubicacion_geografica('Trujillo','Bolívar','Sabana Grande');
CALL insert_ubicacion_geografica('Trujillo','Bolívar','Cheregüé');
CALL insert_ubicacion_geografica('Trujillo','Bolívar','Granados');
CALL insert_ubicacion_geografica('Trujillo','Candelaria','Arnoldo Gabaldón');
CALL insert_ubicacion_geografica('Trujillo','Candelaria','Bolivia');
CALL insert_ubicacion_geografica('Trujillo','Candelaria','Carrillo');
CALL insert_ubicacion_geografica('Trujillo','Candelaria','Cegarra');
CALL insert_ubicacion_geografica('Trujillo','Candelaria','Chejendé');
CALL insert_ubicacion_geografica('Trujillo','Candelaria','Manuel Salvador Ulloa');
CALL insert_ubicacion_geografica('Trujillo','Candelaria','San José');
CALL insert_ubicacion_geografica('Trujillo','Carache','Carache');
CALL insert_ubicacion_geografica('Trujillo','Carache','La Concepción');
CALL insert_ubicacion_geografica('Trujillo','Carache','Cuicas');
CALL insert_ubicacion_geografica('Trujillo','Carache','Panamericana');
CALL insert_ubicacion_geografica('Trujillo','Carache','Santa Cruz');
CALL insert_ubicacion_geografica('Trujillo','Escuque','Escuque');
CALL insert_ubicacion_geografica('Trujillo','Escuque','La Unión');
CALL insert_ubicacion_geografica('Trujillo','Escuque','Santa Rita');
CALL insert_ubicacion_geografica('Trujillo','Escuque','Sabana Libre');
CALL insert_ubicacion_geografica('Trujillo','José Felipe Márquez Cañizales','El Socorro');
CALL insert_ubicacion_geografica('Trujillo','José Felipe Márquez Cañizales','Los Caprichos');
CALL insert_ubicacion_geografica('Trujillo','José Felipe Márquez Cañizales','Antonio José De Sucre');
CALL insert_ubicacion_geografica('Trujillo','Juan Vicente Campos Elías','Campo Elías');
CALL insert_ubicacion_geografica('Trujillo','Juan Vicente Campos Elías','Arnoldo Gabaldón');
CALL insert_ubicacion_geografica('Trujillo','La Ceiba','Santa Apolonia');
CALL insert_ubicacion_geografica('Trujillo','La Ceiba','El Progreso');
CALL insert_ubicacion_geografica('Trujillo','La Ceiba','La Ceiba');
CALL insert_ubicacion_geografica('Trujillo','La Ceiba','Tres De Febrero');
CALL insert_ubicacion_geografica('Trujillo','Miranda','El Dividive');
CALL insert_ubicacion_geografica('Trujillo','Miranda','Agua Santa');
CALL insert_ubicacion_geografica('Trujillo','Miranda','Agua Caliente');
CALL insert_ubicacion_geografica('Trujillo','Miranda','El Cenizo');
CALL insert_ubicacion_geografica('Trujillo','Miranda','Valerita');
CALL insert_ubicacion_geografica('Trujillo','Monte Carmelo','Monte Carmelo');
CALL insert_ubicacion_geografica('Trujillo','Monte Carmelo','Buena Vista');
CALL insert_ubicacion_geografica('Trujillo','Monte Carmelo','Santa María Del Horcón');
CALL insert_ubicacion_geografica('Trujillo','Motatán','Motatán');
CALL insert_ubicacion_geografica('Trujillo','Motatán','El Baño');
CALL insert_ubicacion_geografica('Trujillo','Motatán','Jalisco');
CALL insert_ubicacion_geografica('Trujillo','Pampán','Pampán');
CALL insert_ubicacion_geografica('Trujillo','Pampán','Flor De Patria');
CALL insert_ubicacion_geografica('Trujillo','Pampán','La Paz');
CALL insert_ubicacion_geografica('Trujillo','Pampán','Santa Ana');
CALL insert_ubicacion_geografica('Trujillo','Pampanito','Pampanito');
CALL insert_ubicacion_geografica('Trujillo','Pampanito','La Concepción');
CALL insert_ubicacion_geografica('Trujillo','Pampanito','Pampanito II');
CALL insert_ubicacion_geografica('Trujillo','Rafael Rangel','Betijoque');
CALL insert_ubicacion_geografica('Trujillo','Rafael Rangel','José Gregorio Hernández');
CALL insert_ubicacion_geografica('Trujillo','Rafael Rangel','La Pueblita');
CALL insert_ubicacion_geografica('Trujillo','Rafael Rangel','Los Cedros');
CALL insert_ubicacion_geografica('Trujillo','San Rafael De Carvajal','Carvajal');
CALL insert_ubicacion_geografica('Trujillo','San Rafael De Carvajal','Campo Alegre');
CALL insert_ubicacion_geografica('Trujillo','San Rafael De Carvajal','Antonio Nicolás Briceño');
CALL insert_ubicacion_geografica('Trujillo','San Rafael De Carvajal','José Leonardo Suárez');
CALL insert_ubicacion_geografica('Trujillo','Sucre','Sabana De Mendoza');
CALL insert_ubicacion_geografica('Trujillo','Sucre','Junín');
CALL insert_ubicacion_geografica('Trujillo','Sucre','Valmore Rodríguez');
CALL insert_ubicacion_geografica('Trujillo','Sucre','El Paraíso');
CALL insert_ubicacion_geografica('Trujillo','Trujillo','Andrés Linares');
CALL insert_ubicacion_geografica('Trujillo','Trujillo','Chiquinquirá');
CALL insert_ubicacion_geografica('Trujillo','Trujillo','Cristóbal Mendoza');
CALL insert_ubicacion_geografica('Trujillo','Trujillo','Cruz Carrillo');
CALL insert_ubicacion_geografica('Trujillo','Trujillo','Matriz');
CALL insert_ubicacion_geografica('Trujillo','Trujillo','Monseñor Carrillo');
CALL insert_ubicacion_geografica('Trujillo','Trujillo','Tres Esquinas');
CALL insert_ubicacion_geografica('Trujillo','Urdaneta','Cabimbú');
CALL insert_ubicacion_geografica('Trujillo','Urdaneta','Jajó');
CALL insert_ubicacion_geografica('Trujillo','Urdaneta','La Mesa De Esnujaque');
CALL insert_ubicacion_geografica('Trujillo','Urdaneta','Santiago');
CALL insert_ubicacion_geografica('Trujillo','Urdaneta','Tuñame');
CALL insert_ubicacion_geografica('Trujillo','Urdaneta','La Quebrada');
CALL insert_ubicacion_geografica('Trujillo','Valera','Juan Ignacio Montilla');
CALL insert_ubicacion_geografica('Trujillo','Valera','La Beatriz');
CALL insert_ubicacion_geografica('Trujillo','Valera','La Puerta');
CALL insert_ubicacion_geografica('Trujillo','Valera','Mendoza Del Valle De Momboy');
CALL insert_ubicacion_geografica('Trujillo','Valera','Mercedes Díaz');
CALL insert_ubicacion_geografica('Trujillo','Valera','San Luis');
CALL insert_ubicacion_geografica('Vargas','Vargas','Caraballeda');
CALL insert_ubicacion_geografica('Vargas','Vargas','Carayaca');
CALL insert_ubicacion_geografica('Vargas','Vargas','Carlos Soublette');
CALL insert_ubicacion_geografica('Vargas','Vargas','Caruao');
CALL insert_ubicacion_geografica('Vargas','Vargas','Catia La Mar');
CALL insert_ubicacion_geografica('Vargas','Vargas','El Junko');
CALL insert_ubicacion_geografica('Vargas','Vargas','La Guaira');
CALL insert_ubicacion_geografica('Vargas','Vargas','Macuto');
CALL insert_ubicacion_geografica('Vargas','Vargas','Maiquetía');
CALL insert_ubicacion_geografica('Vargas','Vargas','Naiguatá');
CALL insert_ubicacion_geografica('Vargas','Vargas','Urimare');
CALL insert_ubicacion_geografica('Yaracuy','Arístides Bastidas','Arístides Bastidas');
CALL insert_ubicacion_geografica('Yaracuy','Bolívar','Bolívar');
CALL insert_ubicacion_geografica('Yaracuy','Bruzual','Chivacoa');
CALL insert_ubicacion_geografica('Yaracuy','Bruzual','Campo Elías');
CALL insert_ubicacion_geografica('Yaracuy','Cocorote','Cocorote');
CALL insert_ubicacion_geografica('Yaracuy','Independencia','Independencia');
CALL insert_ubicacion_geografica('Yaracuy','José Antonio Páez','José Antonio Páez');
CALL insert_ubicacion_geografica('Yaracuy','José Joaquín Veroes','El Guayabo');
CALL insert_ubicacion_geografica('Yaracuy','José Joaquín Veroes','Farriar');
CALL insert_ubicacion_geografica('Yaracuy','La Trinidad','La Trinidad');
CALL insert_ubicacion_geografica('Yaracuy','Manuel Monge','Manuel Monge');
CALL insert_ubicacion_geografica('Yaracuy','Nirgua','Salóm');
CALL insert_ubicacion_geografica('Yaracuy','Nirgua','Temerla');
CALL insert_ubicacion_geografica('Yaracuy','Nirgua','Nirgua');
CALL insert_ubicacion_geografica('Yaracuy','Peña','San Andrés');
CALL insert_ubicacion_geografica('Yaracuy','Peña','Yaritagua');
CALL insert_ubicacion_geografica('Yaracuy','San Felipe','San Javier');
CALL insert_ubicacion_geografica('Yaracuy','San Felipe','Albarico');
CALL insert_ubicacion_geografica('Yaracuy','San Felipe','San Felipe');
CALL insert_ubicacion_geografica('Yaracuy','Sucre','Sucre');
CALL insert_ubicacion_geografica('Yaracuy','Urachiche','Urachiche');
CALL insert_ubicacion_geografica('Zulia','Almirante Padilla','Isla De Toas');
CALL insert_ubicacion_geografica('Zulia','Almirante Padilla','Monagas');
CALL insert_ubicacion_geografica('Zulia','Almirante Padilla','San Fernando');
CALL insert_ubicacion_geografica('Zulia','Baralt','San Timoteo');
CALL insert_ubicacion_geografica('Zulia','Baralt','General Urdaneta');
CALL insert_ubicacion_geografica('Zulia','Baralt','Libertador');
CALL insert_ubicacion_geografica('Zulia','Baralt','Marcelino Briceño');
CALL insert_ubicacion_geografica('Zulia','Baralt','Nuevo');
CALL insert_ubicacion_geografica('Zulia','Baralt','Manuel Guanipa Matos');
CALL insert_ubicacion_geografica('Zulia','Cabimas','Ambrosio');
CALL insert_ubicacion_geografica('Zulia','Cabimas','Carmen Herrera');
CALL insert_ubicacion_geografica('Zulia','Cabimas','La Rosa');
CALL insert_ubicacion_geografica('Zulia','Cabimas','Germán Ríos Linares');
CALL insert_ubicacion_geografica('Zulia','Cabimas','San Benito');
CALL insert_ubicacion_geografica('Zulia','Cabimas','Rómulo Betancourt');
CALL insert_ubicacion_geografica('Zulia','Cabimas','Jorge Hernández');
CALL insert_ubicacion_geografica('Zulia','Cabimas','Punta Gorda');
CALL insert_ubicacion_geografica('Zulia','Cabimas','Arístides Calvani');
CALL insert_ubicacion_geografica('Zulia','Catatumbo','Encontrados');
CALL insert_ubicacion_geografica('Zulia','Catatumbo','Udón Pérez');
CALL insert_ubicacion_geografica('Zulia','Colón','Moralito');
CALL insert_ubicacion_geografica('Zulia','Colón','Capital San Carlos Del Zulia');
CALL insert_ubicacion_geografica('Zulia','Colón','Santa Cruz Del Zulia');
CALL insert_ubicacion_geografica('Zulia','Colón','Santa Bárbara');
CALL insert_ubicacion_geografica('Zulia','Colón','Urribarrí');
CALL insert_ubicacion_geografica('Zulia','Francisco Javier Pulgar','Carlos Quevedo');
CALL insert_ubicacion_geografica('Zulia','Francisco Javier Pulgar','Francisco Javier Pulgar');
CALL insert_ubicacion_geografica('Zulia','Francisco Javier Pulgar','Simón Rodríguez');
CALL insert_ubicacion_geografica('Zulia','Francisco Javier Pulgar','Guamo-Gavilanes');
CALL insert_ubicacion_geografica('Zulia','Jesús Enrique Lossada','La Concepción');
CALL insert_ubicacion_geografica('Zulia','Jesús Enrique Lossada','San José');
CALL insert_ubicacion_geografica('Zulia','Jesús Enrique Lossada','Mariano Parra León');
CALL insert_ubicacion_geografica('Zulia','Jesús Enrique Lossada','José Ramón Yépez');
CALL insert_ubicacion_geografica('Zulia','Jesús María Semprún','Jesús María Semprún');
CALL insert_ubicacion_geografica('Zulia','Jesús María Semprún','Barí');
CALL insert_ubicacion_geografica('Zulia','La Cañada De Urdaneta','Concepción');
CALL insert_ubicacion_geografica('Zulia','La Cañada De Urdaneta','Andrés Bello');
CALL insert_ubicacion_geografica('Zulia','La Cañada De Urdaneta','Chiquinquirá');
CALL insert_ubicacion_geografica('Zulia','La Cañada De Urdaneta','El Carmelo');
CALL insert_ubicacion_geografica('Zulia','La Cañada De Urdaneta','Potreritos');
CALL insert_ubicacion_geografica('Zulia','Lagunillas','Libertad');
CALL insert_ubicacion_geografica('Zulia','Lagunillas','Alonso De Ojeda');
CALL insert_ubicacion_geografica('Zulia','Lagunillas','Venezuela');
CALL insert_ubicacion_geografica('Zulia','Lagunillas','Eleazar López Contreras');
CALL insert_ubicacion_geografica('Zulia','Lagunillas','Campo Lara');
CALL insert_ubicacion_geografica('Zulia','Machiques De Perijá','Bartolomé De Las Casas');
CALL insert_ubicacion_geografica('Zulia','Machiques De Perijá','Libertad');
CALL insert_ubicacion_geografica('Zulia','Machiques De Perijá','Río Negro');
CALL insert_ubicacion_geografica('Zulia','Machiques De Perijá','San José De Perijá');
CALL insert_ubicacion_geografica('Zulia','Mara','San Rafael');
CALL insert_ubicacion_geografica('Zulia','Mara','La Sierrita');
CALL insert_ubicacion_geografica('Zulia','Mara','Las Parcelas');
CALL insert_ubicacion_geografica('Zulia','Mara','Luis De Vicente');
CALL insert_ubicacion_geografica('Zulia','Mara','Monseñor Marcos Sergio Godoy');
CALL insert_ubicacion_geografica('Zulia','Mara','Ricaurte');
CALL insert_ubicacion_geografica('Zulia','Mara','Tamare');
CALL insert_ubicacion_geografica('Zulia','Maracaibo','Antonio Borjas Romero');
CALL insert_ubicacion_geografica('Zulia','Maracaibo','Bolívar');
CALL insert_ubicacion_geografica('Zulia','Maracaibo','Cacique Mara');
CALL insert_ubicacion_geografica('Zulia','Maracaibo','Carracciolo Parra Pérez');
CALL insert_ubicacion_geografica('Zulia','Maracaibo','Cecilio Acosta');
CALL insert_ubicacion_geografica('Zulia','Maracaibo','Cristo De Aranza');
CALL insert_ubicacion_geografica('Zulia','Maracaibo','Coquivacoa');
CALL insert_ubicacion_geografica('Zulia','Maracaibo','Chiquinquirá');
CALL insert_ubicacion_geografica('Zulia','Maracaibo','Francisco Eugenio Bustamante');
CALL insert_ubicacion_geografica('Zulia','Maracaibo','Idelfonzo Vásquez');
CALL insert_ubicacion_geografica('Zulia','Maracaibo','Juana De Ávila');
CALL insert_ubicacion_geografica('Zulia','Maracaibo','Luis Hurtado Higuera');
CALL insert_ubicacion_geografica('Zulia','Maracaibo','Manuel Dagnino');
CALL insert_ubicacion_geografica('Zulia','Maracaibo','Olegario Villalobos');
CALL insert_ubicacion_geografica('Zulia','Maracaibo','Raúl Leoni');
CALL insert_ubicacion_geografica('Zulia','Maracaibo','Santa Lucía');
CALL insert_ubicacion_geografica('Zulia','Maracaibo','San Isidro');
CALL insert_ubicacion_geografica('Zulia','Maracaibo','Venancio Pulgar');
CALL insert_ubicacion_geografica('Zulia','Miranda','Altagracia');
CALL insert_ubicacion_geografica('Zulia','Miranda','Faría');
CALL insert_ubicacion_geografica('Zulia','Miranda','Ana María Campos');
CALL insert_ubicacion_geografica('Zulia','Miranda','San Antonio');
CALL insert_ubicacion_geografica('Zulia','Miranda','San José');
CALL insert_ubicacion_geografica('Zulia','Guajira','Sinamaica');
CALL insert_ubicacion_geografica('Zulia','Guajira','Alta Guajira');
CALL insert_ubicacion_geografica('Zulia','Guajira','Elías Sánchez Rubio');
CALL insert_ubicacion_geografica('Zulia','Guajira','Guajira');
CALL insert_ubicacion_geografica('Zulia','Rosario De Perijá','Donaldo García');
CALL insert_ubicacion_geografica('Zulia','Rosario De Perijá','El Rosario');
CALL insert_ubicacion_geografica('Zulia','Rosario De Perijá','Sixto Zambrano');
CALL insert_ubicacion_geografica('Zulia','San Francisco','San Francisco');
CALL insert_ubicacion_geografica('Zulia','San Francisco','El Bajo');
CALL insert_ubicacion_geografica('Zulia','San Francisco','Domitila Flores');
CALL insert_ubicacion_geografica('Zulia','San Francisco','Francisco Ochoa');
CALL insert_ubicacion_geografica('Zulia','San Francisco','Los Cortijos');
CALL insert_ubicacion_geografica('Zulia','San Francisco','Marcial Hernández');
CALL insert_ubicacion_geografica('Zulia','San Francisco','José Domingo Rus');
CALL insert_ubicacion_geografica('Zulia','Santa Rita','Santa Rita');
CALL insert_ubicacion_geografica('Zulia','Santa Rita','El Mene');
CALL insert_ubicacion_geografica('Zulia','Santa Rita','Pedro Lucas Urribarrí');
CALL insert_ubicacion_geografica('Zulia','Santa Rita','José Cenobio Urribarrí');
CALL insert_ubicacion_geografica('Zulia','Simón Bolívar','Rafael Maria Baralt');
CALL insert_ubicacion_geografica('Zulia','Simón Bolívar','Manuel Manrique');
CALL insert_ubicacion_geografica('Zulia','Simón Bolívar','Rafael Urdaneta');
CALL insert_ubicacion_geografica('Zulia','Sucre','Bobures');
CALL insert_ubicacion_geografica('Zulia','Sucre','Gibraltar');
CALL insert_ubicacion_geografica('Zulia','Sucre','Heras');
CALL insert_ubicacion_geografica('Zulia','Sucre','Monseñor Arturo Álvarez');
CALL insert_ubicacion_geografica('Zulia','Sucre','Rómulo Gallegos');
CALL insert_ubicacion_geografica('Zulia','Sucre','El Batey');
CALL insert_ubicacion_geografica('Zulia','Valmore Rodríguez','Rafael Urdaneta');
CALL insert_ubicacion_geografica('Zulia','Valmore Rodríguez','La Victoria');
CALL insert_ubicacion_geografica('Zulia','Valmore Rodríguez','Raúl Cuenca');
# Planes de estudio
CALL insert_plan_estudio('Fisioterapia','FISIAPLI-00300-A','Física Aplicada','Trayecto Inicial','',0,'',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','QUIMAPLI-00300-A','Química Aplicada','Trayecto Inicial','',0,'',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','MATEINST-00300-A','Matemática Instrumental','Trayecto Inicial','',0,'',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','SALPUBFI-00500-A','Salud Pública y Fisioterapia','Trayecto Inicial','',0,'',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','PRNANUCI-00500-A','Proyecto Nacional y Nueva Ciudadanía','Trayecto Inicial','',0,'',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','LECTCOMP-00500-A','Lectura y Comprensión','Trayecto Inicial','',0,'',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ESFUCUHU-10607-A','Estructuras y Funciones de Cuerpo Humano','Trayecto I','',7,'FISIAPLI-00300-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ESFUCUHU-10607-A','Estructuras y Funciones de Cuerpo Humano','Trayecto I','',7,'QUIMAPLI-00300-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ESFUCUHU-10607-A','Estructuras y Funciones de Cuerpo Humano','Trayecto I','',7,'MATEINST-00300-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ESFUCUHU-10607-A','Estructuras y Funciones de Cuerpo Humano','Trayecto I','',7,'SALPUBFI-00500-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ESFUCUHU-10607-A','Estructuras y Funciones de Cuerpo Humano','Trayecto I','',7,'PRNANUCI-00500-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ESFUCUHU-10607-A','Estructuras y Funciones de Cuerpo Humano','Trayecto I','',7,'LECTCOMP-00500-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','INTETEFI-10607-A','Introducción a las Técnicas y Tecnologías en Fisioterapia','Trayecto I','',7,'FISIAPLI-00300-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','INTETEFI-10607-A','Introducción a las Técnicas y Tecnologías en Fisioterapia','Trayecto I','',7,'QUIMAPLI-00300-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','INTETEFI-10607-A','Introducción a las Técnicas y Tecnologías en Fisioterapia','Trayecto I','',7,'MATEINST-00300-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','INTETEFI-10607-A','Introducción a las Técnicas y Tecnologías en Fisioterapia','Trayecto I','',7,'SALPUBFI-00500-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','INTETEFI-10607-A','Introducción a las Técnicas y Tecnologías en Fisioterapia','Trayecto I','',7,'PRNANUCI-00500-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','INTETEFI-10607-A','Introducción a las Técnicas y Tecnologías en Fisioterapia','Trayecto I','',7,'LECTCOMP-00500-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','CIUACCSO-10506-A','Ciudadanía y Acción Social','Trayecto I','',6,'FISIAPLI-00300-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','CIUACCSO-10506-A','Ciudadanía y Acción Social','Trayecto I','',6,'QUIMAPLI-00300-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','CIUACCSO-10506-A','Ciudadanía y Acción Social','Trayecto I','',6,'MATEINST-00300-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','CIUACCSO-10506-A','Ciudadanía y Acción Social','Trayecto I','',6,'SALPUBFI-00500-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','CIUACCSO-10506-A','Ciudadanía y Acción Social','Trayecto I','',6,'PRNANUCI-00500-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','CIUACCSO-10506-A','Ciudadanía y Acción Social','Trayecto I','',6,'LECTCOMP-00500-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','BASCONCI-10506-A','Bases del Conocimiento Científico','Trayecto I','',6,'FISIAPLI-00300-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','BASCONCI-10506-A','Bases del Conocimiento Científico','Trayecto I','',6,'QUIMAPLI-00300-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','BASCONCI-10506-A','Bases del Conocimiento Científico','Trayecto I','',6,'MATEINST-00300-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','BASCONCI-10506-A','Bases del Conocimiento Científico','Trayecto I','',6,'SALPUBFI-00500-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','BASCONCI-10506-A','Bases del Conocimiento Científico','Trayecto I','',6,'PRNANUCI-00500-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','BASCONCI-10506-A','Bases del Conocimiento Científico','Trayecto I','',6,'LECTCOMP-00500-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','PRINABCF-10506-A','Proyecto: Introducción al Abordaje Comunitario en Fisioterapia','Trayecto I','',6,'FISIAPLI-00300-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','PRINABCF-10506-A','Proyecto: Introducción al Abordaje Comunitario en Fisioterapia','Trayecto I','',6,'QUIMAPLI-00300-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','PRINABCF-10506-A','Proyecto: Introducción al Abordaje Comunitario en Fisioterapia','Trayecto I','',6,'MATEINST-00300-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','PRINABCF-10506-A','Proyecto: Introducción al Abordaje Comunitario en Fisioterapia','Trayecto I','',6,'SALPUBFI-00500-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','PRINABCF-10506-A','Proyecto: Introducción al Abordaje Comunitario en Fisioterapia','Trayecto I','',6,'PRNANUCI-00500-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','PRINABCF-10506-A','Proyecto: Introducción al Abordaje Comunitario en Fisioterapia','Trayecto I','',6,'LECTCOMP-00500-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','CAESDICH-20607-A','Cambios en las Estructuras y Disfunciones del Cuerpo Humano','Trayecto II','',7,'ESFUCUHU-10607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','CAESDICH-20607-A','Cambios en las Estructuras y Disfunciones del Cuerpo Humano','Trayecto II','',7,'INTETEFI-10607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','CAESDICH-20607-A','Cambios en las Estructuras y Disfunciones del Cuerpo Humano','Trayecto II','',7,'CIUACCSO-10506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','CAESDICH-20607-A','Cambios en las Estructuras y Disfunciones del Cuerpo Humano','Trayecto II','',7,'BASCONCI-10506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','CAESDICH-20607-A','Cambios en las Estructuras y Disfunciones del Cuerpo Humano','Trayecto II','',7,'PRINABCF-10506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','TECTECFI-20607-A','Técnicas y Tecnologías de Fisioterapia','Trayecto II','',7,'ESFUCUHU-10607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','TECTECFI-20607-A','Técnicas y Tecnologías de Fisioterapia','Trayecto II','',7,'INTETEFI-10607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','TECTECFI-20607-A','Técnicas y Tecnologías de Fisioterapia','Trayecto II','',7,'CIUACCSO-10506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','TECTECFI-20607-A','Técnicas y Tecnologías de Fisioterapia','Trayecto II','',7,'BASCONCI-10506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','TECTECFI-20607-A','Técnicas y Tecnologías de Fisioterapia','Trayecto II','',7,'PRINABCF-10506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ACSOSADI-20607-A','Acción Social, Salud y Discapacidad','Trayecto II','',7,'ESFUCUHU-10607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ACSOSADI-20607-A','Acción Social, Salud y Discapacidad','Trayecto II','',7,'INTETEFI-10607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ACSOSADI-20607-A','Acción Social, Salud y Discapacidad','Trayecto II','',7,'CIUACCSO-10506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ACSOSADI-20607-A','Acción Social, Salud y Discapacidad','Trayecto II','',7,'BASCONCI-10506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ACSOSADI-20607-A','Acción Social, Salud y Discapacidad','Trayecto II','',7,'PRINABCF-10506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','FUESEPSA-20607-A','Fundamentos Estadísticos y Epidemiológicos en Salud','Trayecto II','',7,'ESFUCUHU-10607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','FUESEPSA-20607-A','Fundamentos Estadísticos y Epidemiológicos en Salud','Trayecto II','',7,'INTETEFI-10607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','FUESEPSA-20607-A','Fundamentos Estadísticos y Epidemiológicos en Salud','Trayecto II','',7,'CIUACCSO-10506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','FUESEPSA-20607-A','Fundamentos Estadísticos y Epidemiológicos en Salud','Trayecto II','',7,'BASCONCI-10506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','FUESEPSA-20607-A','Fundamentos Estadísticos y Epidemiológicos en Salud','Trayecto II','',7,'PRINABCF-10506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','PRACPROF-23006-A','Prácticas Profesionales I','Trayecto II','',6,'ESFUCUHU-10607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','PRACPROF-23006-A','Prácticas Profesionales I','Trayecto II','',6,'INTETEFI-10607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','PRACPROF-23006-A','Prácticas Profesionales I','Trayecto II','',6,'CIUACCSO-10506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','PRACPROF-23006-A','Prácticas Profesionales I','Trayecto II','',6,'BASCONCI-10506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','PRACPROF-23006-A','Prácticas Profesionales I','Trayecto II','',6,'PRINABCF-10506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','PRPRFHPD-20506-A','Proyecto: Promoción del Funcionamiento Humano y Prevención de la Discapacidad','Trayecto II','',6,'ESFUCUHU-10607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','PRPRFHPD-20506-A','Proyecto: Promoción del Funcionamiento Humano y Prevención de la Discapacidad','Trayecto II','',6,'INTETEFI-10607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','PRPRFHPD-20506-A','Proyecto: Promoción del Funcionamiento Humano y Prevención de la Discapacidad','Trayecto II','',6,'CIUACCSO-10506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','PRPRFHPD-20506-A','Proyecto: Promoción del Funcionamiento Humano y Prevención de la Discapacidad','Trayecto II','',6,'BASCONCI-10506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','PRPRFHPD-20506-A','Proyecto: Promoción del Funcionamiento Humano y Prevención de la Discapacidad','Trayecto II','',6,'PRINABCF-10506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ESFUALCH-30607-A','Estructuras, Funciones y Alteraciones del Cuerpo Humano','Trayecto III','Técnico Superior Universitario',7,'CAESDICH-20607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ESFUALCH-30607-A','Estructuras, Funciones y Alteraciones del Cuerpo Humano','Trayecto III','Técnico Superior Universitario',7,'TECTECFI-20607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ESFUALCH-30607-A','Estructuras, Funciones y Alteraciones del Cuerpo Humano','Trayecto III','Técnico Superior Universitario',7,'ACSOSADI-20607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ESFUALCH-30607-A','Estructuras, Funciones y Alteraciones del Cuerpo Humano','Trayecto III','Técnico Superior Universitario',7,'FUESEPSA-20607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ESFUALCH-30607-A','Estructuras, Funciones y Alteraciones del Cuerpo Humano','Trayecto III','Técnico Superior Universitario',7,'PRACPROF-23006-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ESFUALCH-30607-A','Estructuras, Funciones y Alteraciones del Cuerpo Humano','Trayecto III','Técnico Superior Universitario',7,'PRPRFHPD-20506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','TETEINFI-30607-A','Técnicas y Tecnologías Integrales en Fisioterapia','Trayecto III','Técnico Superior Universitario',7,'CAESDICH-20607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','TETEINFI-30607-A','Técnicas y Tecnologías Integrales en Fisioterapia','Trayecto III','Técnico Superior Universitario',7,'TECTECFI-20607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','TETEINFI-30607-A','Técnicas y Tecnologías Integrales en Fisioterapia','Trayecto III','Técnico Superior Universitario',7,'ACSOSADI-20607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','TETEINFI-30607-A','Técnicas y Tecnologías Integrales en Fisioterapia','Trayecto III','Técnico Superior Universitario',7,'FUESEPSA-20607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','TETEINFI-30607-A','Técnicas y Tecnologías Integrales en Fisioterapia','Trayecto III','Técnico Superior Universitario',7,'PRACPROF-23006-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','TETEINFI-30607-A','Técnicas y Tecnologías Integrales en Fisioterapia','Trayecto III','Técnico Superior Universitario',7,'PRPRFHPD-20506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','SEMCLIFI-30506-A','Semiología Clínica para Fisioterapeutas','Trayecto III','Técnico Superior Universitario',6,'CAESDICH-20607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','SEMCLIFI-30506-A','Semiología Clínica para Fisioterapeutas','Trayecto III','Técnico Superior Universitario',6,'TECTECFI-20607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','SEMCLIFI-30506-A','Semiología Clínica para Fisioterapeutas','Trayecto III','Técnico Superior Universitario',6,'ACSOSADI-20607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','SEMCLIFI-30506-A','Semiología Clínica para Fisioterapeutas','Trayecto III','Técnico Superior Universitario',6,'FUESEPSA-20607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','SEMCLIFI-30506-A','Semiología Clínica para Fisioterapeutas','Trayecto III','Técnico Superior Universitario',6,'PRACPROF-23006-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','SEMCLIFI-30506-A','Semiología Clínica para Fisioterapeutas','Trayecto III','Técnico Superior Universitario',6,'PRPRFHPD-20506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ORPOINPS-30506-A','Orientación Política e Integración Psicosocial','Trayecto III','Técnico Superior Universitario',6,'CAESDICH-20607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ORPOINPS-30506-A','Orientación Política e Integración Psicosocial','Trayecto III','Técnico Superior Universitario',6,'TECTECFI-20607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ORPOINPS-30506-A','Orientación Política e Integración Psicosocial','Trayecto III','Técnico Superior Universitario',6,'ACSOSADI-20607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ORPOINPS-30506-A','Orientación Política e Integración Psicosocial','Trayecto III','Técnico Superior Universitario',6,'FUESEPSA-20607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ORPOINPS-30506-A','Orientación Política e Integración Psicosocial','Trayecto III','Técnico Superior Universitario',6,'PRACPROF-23006-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ORPOINPS-30506-A','Orientación Política e Integración Psicosocial','Trayecto III','Técnico Superior Universitario',6,'PRPRFHPD-20506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','PRACPROF-33006-A','Prácticas Profesionales II','Trayecto III','Técnico Superior Universitario',6,'CAESDICH-20607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','PRACPROF-33006-A','Prácticas Profesionales II','Trayecto III','Técnico Superior Universitario',6,'TECTECFI-20607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','PRACPROF-33006-A','Prácticas Profesionales II','Trayecto III','Técnico Superior Universitario',6,'ACSOSADI-20607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','PRACPROF-33006-A','Prácticas Profesionales II','Trayecto III','Técnico Superior Universitario',6,'FUESEPSA-20607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','PRACPROF-33006-A','Prácticas Profesionales II','Trayecto III','Técnico Superior Universitario',6,'PRACPROF-23006-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','PRACPROF-33006-A','Prácticas Profesionales II','Trayecto III','Técnico Superior Universitario',6,'PRPRFHPD-20506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','PRATINPD-30506-A','Proyecto: Atención Integral a Personas con Discapacidad','Trayecto III','Técnico Superior Universitario',6,'CAESDICH-20607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','PRATINPD-30506-A','Proyecto: Atención Integral a Personas con Discapacidad','Trayecto III','Técnico Superior Universitario',6,'TECTECFI-20607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','PRATINPD-30506-A','Proyecto: Atención Integral a Personas con Discapacidad','Trayecto III','Técnico Superior Universitario',6,'ACSOSADI-20607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','PRATINPD-30506-A','Proyecto: Atención Integral a Personas con Discapacidad','Trayecto III','Técnico Superior Universitario',6,'FUESEPSA-20607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','PRATINPD-30506-A','Proyecto: Atención Integral a Personas con Discapacidad','Trayecto III','Técnico Superior Universitario',6,'PRACPROF-23006-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','PRATINPD-30506-A','Proyecto: Atención Integral a Personas con Discapacidad','Trayecto III','Técnico Superior Universitario',6,'PRPRFHPD-20506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ESFUDACH-40607-A','Estructuras, Funciones y Diagnóstico de las Alteraciones del Cuerpo Humano','Trayecto IV','Licenciatura',7,'ESFUALCH-30607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ESFUDACH-40607-A','Estructuras, Funciones y Diagnóstico de las Alteraciones del Cuerpo Humano','Trayecto IV','Licenciatura',7,'TETEINFI-30607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ESFUDACH-40607-A','Estructuras, Funciones y Diagnóstico de las Alteraciones del Cuerpo Humano','Trayecto IV','Licenciatura',7,'SEMCLIFI-30506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ESFUDACH-40607-A','Estructuras, Funciones y Diagnóstico de las Alteraciones del Cuerpo Humano','Trayecto IV','Licenciatura',7,'ORPOINPS-30506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ESFUDACH-40607-A','Estructuras, Funciones y Diagnóstico de las Alteraciones del Cuerpo Humano','Trayecto IV','Licenciatura',7,'PRACPROF-33006-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ESFUDACH-40607-A','Estructuras, Funciones y Diagnóstico de las Alteraciones del Cuerpo Humano','Trayecto IV','Licenciatura',7,'PRATINPD-30506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','TETEESFI-40607-A','Técnicas y Tecnologías Especializadas de Fisioterapia','Trayecto IV','Licenciatura',7,'ESFUALCH-30607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','TETEESFI-40607-A','Técnicas y Tecnologías Especializadas de Fisioterapia','Trayecto IV','Licenciatura',7,'TETEINFI-30607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','TETEESFI-40607-A','Técnicas y Tecnologías Especializadas de Fisioterapia','Trayecto IV','Licenciatura',7,'SEMCLIFI-30506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','TETEESFI-40607-A','Técnicas y Tecnologías Especializadas de Fisioterapia','Trayecto IV','Licenciatura',7,'ORPOINPS-30506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','TETEESFI-40607-A','Técnicas y Tecnologías Especializadas de Fisioterapia','Trayecto IV','Licenciatura',7,'PRACPROF-33006-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','TETEESFI-40607-A','Técnicas y Tecnologías Especializadas de Fisioterapia','Trayecto IV','Licenciatura',7,'PRATINPD-30506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','INTESPFI-40607-A','Intervención Especializada en Fisioterapia','Trayecto IV','Licenciatura',6,'ESFUALCH-30607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','INTESPFI-40607-A','Intervención Especializada en Fisioterapia','Trayecto IV','Licenciatura',6,'TETEINFI-30607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','INTESPFI-40607-A','Intervención Especializada en Fisioterapia','Trayecto IV','Licenciatura',6,'SEMCLIFI-30506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','INTESPFI-40607-A','Intervención Especializada en Fisioterapia','Trayecto IV','Licenciatura',6,'ORPOINPS-30506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','INTESPFI-40607-A','Intervención Especializada en Fisioterapia','Trayecto IV','Licenciatura',6,'PRACPROF-33006-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','INTESPFI-40607-A','Intervención Especializada en Fisioterapia','Trayecto IV','Licenciatura',6,'PRATINPD-30506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','FARMFISI-40506-A','Farmacología en Fisioterapia','Trayecto IV','Licenciatura',6,'ESFUALCH-30607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','FARMFISI-40506-A','Farmacología en Fisioterapia','Trayecto IV','Licenciatura',6,'TETEINFI-30607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','FARMFISI-40506-A','Farmacología en Fisioterapia','Trayecto IV','Licenciatura',6,'SEMCLIFI-30506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','FARMFISI-40506-A','Farmacología en Fisioterapia','Trayecto IV','Licenciatura',6,'ORPOINPS-30506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','FARMFISI-40506-A','Farmacología en Fisioterapia','Trayecto IV','Licenciatura',6,'PRACPROF-33006-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','FARMFISI-40506-A','Farmacología en Fisioterapia','Trayecto IV','Licenciatura',6,'PRATINPD-30506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','SALUOCUP-40506-A','Salud Ocupacional','Trayecto IV','Licenciatura',6,'ESFUALCH-30607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','SALUOCUP-40506-A','Salud Ocupacional','Trayecto IV','Licenciatura',6,'TETEINFI-30607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','SALUOCUP-40506-A','Salud Ocupacional','Trayecto IV','Licenciatura',6,'SEMCLIFI-30506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','SALUOCUP-40506-A','Salud Ocupacional','Trayecto IV','Licenciatura',6,'ORPOINPS-30506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','SALUOCUP-40506-A','Salud Ocupacional','Trayecto IV','Licenciatura',6,'PRACPROF-33006-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','SALUOCUP-40506-A','Salud Ocupacional','Trayecto IV','Licenciatura',6,'PRATINPD-30506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ADTAHURL-40506-A','Administración del Talento Humano y Relaciones Laborales','Trayecto IV','Licenciatura',6,'ESFUALCH-30607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ADTAHURL-40506-A','Administración del Talento Humano y Relaciones Laborales','Trayecto IV','Licenciatura',6,'TETEINFI-30607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ADTAHURL-40506-A','Administración del Talento Humano y Relaciones Laborales','Trayecto IV','Licenciatura',6,'SEMCLIFI-30506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ADTAHURL-40506-A','Administración del Talento Humano y Relaciones Laborales','Trayecto IV','Licenciatura',6,'ORPOINPS-30506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ADTAHURL-40506-A','Administración del Talento Humano y Relaciones Laborales','Trayecto IV','Licenciatura',6,'PRACPROF-33006-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ADTAHURL-40506-A','Administración del Talento Humano y Relaciones Laborales','Trayecto IV','Licenciatura',6,'PRATINPD-30506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ECONSALU-40506-A','Economía de la Salud','Trayecto IV','Licenciatura',6,'ESFUALCH-30607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ECONSALU-40506-A','Economía de la Salud','Trayecto IV','Licenciatura',6,'TETEINFI-30607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ECONSALU-40506-A','Economía de la Salud','Trayecto IV','Licenciatura',6,'SEMCLIFI-30506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ECONSALU-40506-A','Economía de la Salud','Trayecto IV','Licenciatura',6,'ORPOINPS-30506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ECONSALU-40506-A','Economía de la Salud','Trayecto IV','Licenciatura',6,'PRACPROF-33006-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ECONSALU-40506-A','Economía de la Salud','Trayecto IV','Licenciatura',6,'PRATINPD-30506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ORIINTSO-40506-A','Orientación e Integración Socioambiental','Trayecto IV','Licenciatura',6,'ESFUALCH-30607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ORIINTSO-40506-A','Orientación e Integración Socioambiental','Trayecto IV','Licenciatura',6,'TETEINFI-30607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ORIINTSO-40506-A','Orientación e Integración Socioambiental','Trayecto IV','Licenciatura',6,'SEMCLIFI-30506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ORIINTSO-40506-A','Orientación e Integración Socioambiental','Trayecto IV','Licenciatura',6,'ORPOINPS-30506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ORIINTSO-40506-A','Orientación e Integración Socioambiental','Trayecto IV','Licenciatura',6,'PRACPROF-33006-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','ORIINTSO-40506-A','Orientación e Integración Socioambiental','Trayecto IV','Licenciatura',6,'PRATINPD-30506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','GEINSSAD-40607-A','Gestión Integral de los Sistemas de Salud y Atención a la Diversidad','Trayecto IV','Licenciatura',7,'ESFUALCH-30607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','GEINSSAD-40607-A','Gestión Integral de los Sistemas de Salud y Atención a la Diversidad','Trayecto IV','Licenciatura',7,'TETEINFI-30607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','GEINSSAD-40607-A','Gestión Integral de los Sistemas de Salud y Atención a la Diversidad','Trayecto IV','Licenciatura',7,'SEMCLIFI-30506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','GEINSSAD-40607-A','Gestión Integral de los Sistemas de Salud y Atención a la Diversidad','Trayecto IV','Licenciatura',7,'ORPOINPS-30506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','GEINSSAD-40607-A','Gestión Integral de los Sistemas de Salud y Atención a la Diversidad','Trayecto IV','Licenciatura',7,'PRACPROF-33006-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','GEINSSAD-40607-A','Gestión Integral de los Sistemas de Salud y Atención a la Diversidad','Trayecto IV','Licenciatura',7,'PRATINPD-30506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','PRACPROF-43006-A','Prácticas Profesionales III','Trayecto IV','Licenciatura',6,'ESFUALCH-30607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','PRACPROF-43006-A','Prácticas Profesionales III','Trayecto IV','Licenciatura',6,'TETEINFI-30607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','PRACPROF-43006-A','Prácticas Profesionales III','Trayecto IV','Licenciatura',6,'SEMCLIFI-30506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','PRACPROF-43006-A','Prácticas Profesionales III','Trayecto IV','Licenciatura',6,'ORPOINPS-30506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','PRACPROF-43006-A','Prácticas Profesionales III','Trayecto IV','Licenciatura',6,'PRACPROF-33006-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','PRACPROF-43006-A','Prácticas Profesionales III','Trayecto IV','Licenciatura',6,'PRATINPD-30506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','PRGEESFI-40506-A','Proyecto: Gestión Especializada en Fisioterapia','Trayecto IV','Licenciatura',6,'ESFUALCH-30607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','PRGEESFI-40506-A','Proyecto: Gestión Especializada en Fisioterapia','Trayecto IV','Licenciatura',6,'TETEINFI-30607-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','PRGEESFI-40506-A','Proyecto: Gestión Especializada en Fisioterapia','Trayecto IV','Licenciatura',6,'SEMCLIFI-30506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','PRGEESFI-40506-A','Proyecto: Gestión Especializada en Fisioterapia','Trayecto IV','Licenciatura',6,'ORPOINPS-30506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','PRGEESFI-40506-A','Proyecto: Gestión Especializada en Fisioterapia','Trayecto IV','Licenciatura',6,'PRACPROF-33006-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fisioterapia','PRGEESFI-40506-A','Proyecto: Gestión Especializada en Fisioterapia','Trayecto IV','Licenciatura',6,'PRATINPD-30506-A',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','FISIAPLI-00300-B','Física Aplicada','Trayecto Inicial','',0,'',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','HERRTECN-00300-B','Herramientas Tecnológicas','Trayecto Inicial','',0,'',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PRNANUCI-00300-B','Proyecto Nacional y Nueva Ciudadanía','Trayecto Inicial','',0,'',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','SALUPUBL-00400-B','Salud Pública','Trayecto Inicial','',0,'',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','INTPNFFO-00500-B','Introducción al PNF en Fonoaudiología','Trayecto Inicial','',0,'',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','LECTCOMP-00500-B','Lectura y Compresión','Trayecto Inicial','',0,'',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ESTFUNCO-10506-B','Estructura y Función de la Comunicación','Trayecto I','',6,'FISIAPLI-00300-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ESTFUNCO-10506-B','Estructura y Función de la Comunicación','Trayecto I','',6,'HERRTECN-00300-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ESTFUNCO-10506-B','Estructura y Función de la Comunicación','Trayecto I','',6,'PRNANUCI-00300-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ESTFUNCO-10506-B','Estructura y Función de la Comunicación','Trayecto I','',6,'SALUPUBL-00400-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ESTFUNCO-10506-B','Estructura y Función de la Comunicación','Trayecto I','',6,'INTPNFFO-00500-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ESTFUNCO-10506-B','Estructura y Función de la Comunicación','Trayecto I','',6,'LECTCOMP-00500-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ORGPARCO-10304-B','Organización y Participación Comunitaria','Trayecto I','',4,'FISIAPLI-00300-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ORGPARCO-10304-B','Organización y Participación Comunitaria','Trayecto I','',4,'HERRTECN-00300-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ORGPARCO-10304-B','Organización y Participación Comunitaria','Trayecto I','',4,'PRNANUCI-00300-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ORGPARCO-10304-B','Organización y Participación Comunitaria','Trayecto I','',4,'SALUPUBL-00400-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ORGPARCO-10304-B','Organización y Participación Comunitaria','Trayecto I','',4,'INTPNFFO-00500-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ORGPARCO-10304-B','Organización y Participación Comunitaria','Trayecto I','',4,'LECTCOMP-00500-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','FONEFONO-10304-B','Fonética y Fonología','Trayecto I','',4,'FISIAPLI-00300-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','FONEFONO-10304-B','Fonética y Fonología','Trayecto I','',4,'HERRTECN-00300-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','FONEFONO-10304-B','Fonética y Fonología','Trayecto I','',4,'PRNANUCI-00300-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','FONEFONO-10304-B','Fonética y Fonología','Trayecto I','',4,'SALUPUBL-00400-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','FONEFONO-10304-B','Fonética y Fonología','Trayecto I','',4,'INTPNFFO-00500-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','FONEFONO-10304-B','Fonética y Fonología','Trayecto I','',4,'LECTCOMP-00500-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PSICOLOG-10304-B','Psicología','Trayecto I','',4,'FISIAPLI-00300-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PSICOLOG-10304-B','Psicología','Trayecto I','',4,'HERRTECN-00300-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PSICOLOG-10304-B','Psicología','Trayecto I','',4,'PRNANUCI-00300-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PSICOLOG-10304-B','Psicología','Trayecto I','',4,'SALUPUBL-00400-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PSICOLOG-10304-B','Psicología','Trayecto I','',4,'INTPNFFO-00500-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PSICOLOG-10304-B','Psicología','Trayecto I','',4,'LECTCOMP-00500-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','BASPRAFO-10506-B','Bases de la Práctica Fonoaudiológica','Trayecto I','',6,'FISIAPLI-00300-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','BASPRAFO-10506-B','Bases de la Práctica Fonoaudiológica','Trayecto I','',6,'HERRTECN-00300-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','BASPRAFO-10506-B','Bases de la Práctica Fonoaudiológica','Trayecto I','',6,'PRNANUCI-00300-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','BASPRAFO-10506-B','Bases de la Práctica Fonoaudiológica','Trayecto I','',6,'SALUPUBL-00400-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','BASPRAFO-10506-B','Bases de la Práctica Fonoaudiológica','Trayecto I','',6,'INTPNFFO-00500-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','BASPRAFO-10506-B','Bases de la Práctica Fonoaudiológica','Trayecto I','',6,'LECTCOMP-00500-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','BASACUCO-10506-B','Bases Acústicas de la Comunicación','Trayecto I','',6,'FISIAPLI-00300-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','BASACUCO-10506-B','Bases Acústicas de la Comunicación','Trayecto I','',6,'HERRTECN-00300-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','BASACUCO-10506-B','Bases Acústicas de la Comunicación','Trayecto I','',6,'PRNANUCI-00300-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','BASACUCO-10506-B','Bases Acústicas de la Comunicación','Trayecto I','',6,'SALUPUBL-00400-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','BASACUCO-10506-B','Bases Acústicas de la Comunicación','Trayecto I','',6,'INTPNFFO-00500-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','BASACUCO-10506-B','Bases Acústicas de la Comunicación','Trayecto I','',6,'LECTCOMP-00500-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','INGLINST-10304-B','Inglés Instrumental','Trayecto I','',4,'FISIAPLI-00300-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','INGLINST-10304-B','Inglés Instrumental','Trayecto I','',4,'HERRTECN-00300-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','INGLINST-10304-B','Inglés Instrumental','Trayecto I','',4,'PRNANUCI-00300-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','INGLINST-10304-B','Inglés Instrumental','Trayecto I','',4,'SALUPUBL-00400-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','INGLINST-10304-B','Inglés Instrumental','Trayecto I','',4,'INTPNFFO-00500-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','INGLINST-10304-B','Inglés Instrumental','Trayecto I','',4,'LECTCOMP-00500-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ELECTIVA-10202-B','Electiva','Trayecto I','',2,'FISIAPLI-00300-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ELECTIVA-10202-B','Electiva','Trayecto I','',2,'HERRTECN-00300-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ELECTIVA-10202-B','Electiva','Trayecto I','',2,'PRNANUCI-00300-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ELECTIVA-10202-B','Electiva','Trayecto I','',2,'SALUPUBL-00400-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ELECTIVA-10202-B','Electiva','Trayecto I','',2,'INTPNFFO-00500-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ELECTIVA-10202-B','Electiva','Trayecto I','',2,'LECTCOMP-00500-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PROYSOCI-10405-B','Proyecto Sociointegrador I','Trayecto I','',5,'FISIAPLI-00300-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PROYSOCI-10405-B','Proyecto Sociointegrador I','Trayecto I','',5,'HERRTECN-00300-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PROYSOCI-10405-B','Proyecto Sociointegrador I','Trayecto I','',5,'PRNANUCI-00300-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PROYSOCI-10405-B','Proyecto Sociointegrador I','Trayecto I','',5,'SALUPUBL-00400-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PROYSOCI-10405-B','Proyecto Sociointegrador I','Trayecto I','',5,'INTPNFFO-00500-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PROYSOCI-10405-B','Proyecto Sociointegrador I','Trayecto I','',5,'LECTCOMP-00500-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','NEUROLIN-20405-B','Neurolingüistica','Trayecto II','',5,'ESTFUNCO-10506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','NEUROLIN-20405-B','Neurolingüistica','Trayecto II','',5,'ORGPARCO-10304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','NEUROLIN-20405-B','Neurolingüistica','Trayecto II','',5,'FONEFONO-10304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','NEUROLIN-20405-B','Neurolingüistica','Trayecto II','',5,'PSICOLOG-10304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','NEUROLIN-20405-B','Neurolingüistica','Trayecto II','',5,'BASPRAFO-10506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','NEUROLIN-20405-B','Neurolingüistica','Trayecto II','',5,'BASACUCO-10506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','NEUROLIN-20405-B','Neurolingüistica','Trayecto II','',5,'INGLINST-10304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','NEUROLIN-20405-B','Neurolingüistica','Trayecto II','',5,'ELECTIVA-10202-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','NEUROLIN-20405-B','Neurolingüistica','Trayecto II','',5,'PROYSOCI-10405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','RECPSIFO-20405-B','Recursos Psicoterapéuticos en Fonoaudiología','Trayecto II','',5,'ESTFUNCO-10506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','RECPSIFO-20405-B','Recursos Psicoterapéuticos en Fonoaudiología','Trayecto II','',5,'ORGPARCO-10304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','RECPSIFO-20405-B','Recursos Psicoterapéuticos en Fonoaudiología','Trayecto II','',5,'FONEFONO-10304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','RECPSIFO-20405-B','Recursos Psicoterapéuticos en Fonoaudiología','Trayecto II','',5,'PSICOLOG-10304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','RECPSIFO-20405-B','Recursos Psicoterapéuticos en Fonoaudiología','Trayecto II','',5,'BASPRAFO-10506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','RECPSIFO-20405-B','Recursos Psicoterapéuticos en Fonoaudiología','Trayecto II','',5,'BASACUCO-10506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','RECPSIFO-20405-B','Recursos Psicoterapéuticos en Fonoaudiología','Trayecto II','',5,'INGLINST-10304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','RECPSIFO-20405-B','Recursos Psicoterapéuticos en Fonoaudiología','Trayecto II','',5,'ELECTIVA-10202-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','RECPSIFO-20405-B','Recursos Psicoterapéuticos en Fonoaudiología','Trayecto II','',5,'PROYSOCI-10405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PATCOMHU-20506-B','Patología de la Comunicación Humana','Trayecto II','',6,'ESTFUNCO-10506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PATCOMHU-20506-B','Patología de la Comunicación Humana','Trayecto II','',6,'ORGPARCO-10304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PATCOMHU-20506-B','Patología de la Comunicación Humana','Trayecto II','',6,'FONEFONO-10304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PATCOMHU-20506-B','Patología de la Comunicación Humana','Trayecto II','',6,'PSICOLOG-10304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PATCOMHU-20506-B','Patología de la Comunicación Humana','Trayecto II','',6,'BASPRAFO-10506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PATCOMHU-20506-B','Patología de la Comunicación Humana','Trayecto II','',6,'BASACUCO-10506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PATCOMHU-20506-B','Patología de la Comunicación Humana','Trayecto II','',6,'INGLINST-10304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PATCOMHU-20506-B','Patología de la Comunicación Humana','Trayecto II','',6,'ELECTIVA-10202-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PATCOMHU-20506-B','Patología de la Comunicación Humana','Trayecto II','',6,'PROYSOCI-10405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTLE-20506-B','Diagnóstico e Intervención del Lenguaje I','Trayecto II','',6,'ESTFUNCO-10506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTLE-20506-B','Diagnóstico e Intervención del Lenguaje I','Trayecto II','',6,'ORGPARCO-10304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTLE-20506-B','Diagnóstico e Intervención del Lenguaje I','Trayecto II','',6,'FONEFONO-10304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTLE-20506-B','Diagnóstico e Intervención del Lenguaje I','Trayecto II','',6,'PSICOLOG-10304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTLE-20506-B','Diagnóstico e Intervención del Lenguaje I','Trayecto II','',6,'BASPRAFO-10506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTLE-20506-B','Diagnóstico e Intervención del Lenguaje I','Trayecto II','',6,'BASACUCO-10506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTLE-20506-B','Diagnóstico e Intervención del Lenguaje I','Trayecto II','',6,'INGLINST-10304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTLE-20506-B','Diagnóstico e Intervención del Lenguaje I','Trayecto II','',6,'ELECTIVA-10202-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTLE-20506-B','Diagnóstico e Intervención del Lenguaje I','Trayecto II','',6,'PROYSOCI-10405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTHA-20506-B','Diagnóstico e Intervención del Habla','Trayecto II','',6,'ESTFUNCO-10506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTHA-20506-B','Diagnóstico e Intervención del Habla','Trayecto II','',6,'ORGPARCO-10304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTHA-20506-B','Diagnóstico e Intervención del Habla','Trayecto II','',6,'FONEFONO-10304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTHA-20506-B','Diagnóstico e Intervención del Habla','Trayecto II','',6,'PSICOLOG-10304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTHA-20506-B','Diagnóstico e Intervención del Habla','Trayecto II','',6,'BASPRAFO-10506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTHA-20506-B','Diagnóstico e Intervención del Habla','Trayecto II','',6,'BASACUCO-10506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTHA-20506-B','Diagnóstico e Intervención del Habla','Trayecto II','',6,'INGLINST-10304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTHA-20506-B','Diagnóstico e Intervención del Habla','Trayecto II','',6,'ELECTIVA-10202-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTHA-20506-B','Diagnóstico e Intervención del Habla','Trayecto II','',6,'PROYSOCI-10405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTAU-20405-B','Diagnóstico e Intervención Auditiva I','Trayecto II','',5,'ESTFUNCO-10506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTAU-20405-B','Diagnóstico e Intervención Auditiva I','Trayecto II','',5,'ORGPARCO-10304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTAU-20405-B','Diagnóstico e Intervención Auditiva I','Trayecto II','',5,'FONEFONO-10304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTAU-20405-B','Diagnóstico e Intervención Auditiva I','Trayecto II','',5,'PSICOLOG-10304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTAU-20405-B','Diagnóstico e Intervención Auditiva I','Trayecto II','',5,'BASPRAFO-10506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTAU-20405-B','Diagnóstico e Intervención Auditiva I','Trayecto II','',5,'BASACUCO-10506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTAU-20405-B','Diagnóstico e Intervención Auditiva I','Trayecto II','',5,'INGLINST-10304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTAU-20405-B','Diagnóstico e Intervención Auditiva I','Trayecto II','',5,'ELECTIVA-10202-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTAU-20405-B','Diagnóstico e Intervención Auditiva I','Trayecto II','',5,'PROYSOCI-10405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ELECTIVA-20202-B','Electiva','Trayecto II','',2,'ESTFUNCO-10506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ELECTIVA-20202-B','Electiva','Trayecto II','',2,'ORGPARCO-10304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ELECTIVA-20202-B','Electiva','Trayecto II','',2,'FONEFONO-10304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ELECTIVA-20202-B','Electiva','Trayecto II','',2,'PSICOLOG-10304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ELECTIVA-20202-B','Electiva','Trayecto II','',2,'BASPRAFO-10506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ELECTIVA-20202-B','Electiva','Trayecto II','',2,'BASACUCO-10506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ELECTIVA-20202-B','Electiva','Trayecto II','',2,'INGLINST-10304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ELECTIVA-20202-B','Electiva','Trayecto II','',2,'ELECTIVA-10202-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ELECTIVA-20202-B','Electiva','Trayecto II','',2,'PROYSOCI-10405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PRACPROF-21200-B','Prácticas Profesionales I','Trayecto II','',0,'ESTFUNCO-10506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PRACPROF-21200-B','Prácticas Profesionales I','Trayecto II','',0,'ORGPARCO-10304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PRACPROF-21200-B','Prácticas Profesionales I','Trayecto II','',0,'FONEFONO-10304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PRACPROF-21200-B','Prácticas Profesionales I','Trayecto II','',0,'PSICOLOG-10304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PRACPROF-21200-B','Prácticas Profesionales I','Trayecto II','',0,'BASPRAFO-10506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PRACPROF-21200-B','Prácticas Profesionales I','Trayecto II','',0,'BASACUCO-10506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PRACPROF-21200-B','Prácticas Profesionales I','Trayecto II','',0,'INGLINST-10304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PRACPROF-21200-B','Prácticas Profesionales I','Trayecto II','',0,'ELECTIVA-10202-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PRACPROF-21200-B','Prácticas Profesionales I','Trayecto II','',0,'PROYSOCI-10405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PROYSICO-20304-B','Proyecto Sociointegrador II','Trayecto II','',4,'ESTFUNCO-10506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PROYSICO-20304-B','Proyecto Sociointegrador II','Trayecto II','',4,'ORGPARCO-10304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PROYSICO-20304-B','Proyecto Sociointegrador II','Trayecto II','',4,'FONEFONO-10304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PROYSICO-20304-B','Proyecto Sociointegrador II','Trayecto II','',4,'PSICOLOG-10304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PROYSICO-20304-B','Proyecto Sociointegrador II','Trayecto II','',4,'BASPRAFO-10506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PROYSICO-20304-B','Proyecto Sociointegrador II','Trayecto II','',4,'BASACUCO-10506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PROYSICO-20304-B','Proyecto Sociointegrador II','Trayecto II','',4,'INGLINST-10304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PROYSICO-20304-B','Proyecto Sociointegrador II','Trayecto II','',4,'ELECTIVA-10202-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PROYSICO-20304-B','Proyecto Sociointegrador II','Trayecto II','',4,'PROYSOCI-10405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PSICOMOT-30202-B','Psicomotricidad','Trayecto III','Técnico Superior Universitario',2,'NEUROLIN-20405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PSICOMOT-30202-B','Psicomotricidad','Trayecto III','Técnico Superior Universitario',2,'RECPSIFO-20405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PSICOMOT-30202-B','Psicomotricidad','Trayecto III','Técnico Superior Universitario',2,'PATCOMHU-20506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PSICOMOT-30202-B','Psicomotricidad','Trayecto III','Técnico Superior Universitario',2,'DIAINTLE-20506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PSICOMOT-30202-B','Psicomotricidad','Trayecto III','Técnico Superior Universitario',2,'DIAINTHA-20506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PSICOMOT-30202-B','Psicomotricidad','Trayecto III','Técnico Superior Universitario',2,'DIAINTAU-20405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PSICOMOT-30202-B','Psicomotricidad','Trayecto III','Técnico Superior Universitario',2,'ELECTIVA-20202-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PSICOMOT-30202-B','Psicomotricidad','Trayecto III','Técnico Superior Universitario',2,'PRACPROF-21200-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PSICOMOT-30202-B','Psicomotricidad','Trayecto III','Técnico Superior Universitario',2,'PROYSICO-20304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTLE-30405-B','Diagnóstico e Intervención del Lenguaje II','Trayecto III','Técnico Superior Universitario',5,'NEUROLIN-20405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTLE-30405-B','Diagnóstico e Intervención del Lenguaje II','Trayecto III','Técnico Superior Universitario',5,'RECPSIFO-20405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTLE-30405-B','Diagnóstico e Intervención del Lenguaje II','Trayecto III','Técnico Superior Universitario',5,'PATCOMHU-20506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTLE-30405-B','Diagnóstico e Intervención del Lenguaje II','Trayecto III','Técnico Superior Universitario',5,'DIAINTLE-20506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTLE-30405-B','Diagnóstico e Intervención del Lenguaje II','Trayecto III','Técnico Superior Universitario',5,'DIAINTHA-20506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTLE-30405-B','Diagnóstico e Intervención del Lenguaje II','Trayecto III','Técnico Superior Universitario',5,'DIAINTAU-20405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTLE-30405-B','Diagnóstico e Intervención del Lenguaje II','Trayecto III','Técnico Superior Universitario',5,'ELECTIVA-20202-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTLE-30405-B','Diagnóstico e Intervención del Lenguaje II','Trayecto III','Técnico Superior Universitario',5,'PRACPROF-21200-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTLE-30405-B','Diagnóstico e Intervención del Lenguaje II','Trayecto III','Técnico Superior Universitario',5,'PROYSICO-20304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTVO-30405-B','Diagnóstico e Intervención de la Voz I','Trayecto III','Técnico Superior Universitario',5,'NEUROLIN-20405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTVO-30405-B','Diagnóstico e Intervención de la Voz I','Trayecto III','Técnico Superior Universitario',5,'RECPSIFO-20405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTVO-30405-B','Diagnóstico e Intervención de la Voz I','Trayecto III','Técnico Superior Universitario',5,'PATCOMHU-20506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTVO-30405-B','Diagnóstico e Intervención de la Voz I','Trayecto III','Técnico Superior Universitario',5,'DIAINTLE-20506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTVO-30405-B','Diagnóstico e Intervención de la Voz I','Trayecto III','Técnico Superior Universitario',5,'DIAINTHA-20506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTVO-30405-B','Diagnóstico e Intervención de la Voz I','Trayecto III','Técnico Superior Universitario',5,'DIAINTAU-20405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTVO-30405-B','Diagnóstico e Intervención de la Voz I','Trayecto III','Técnico Superior Universitario',5,'ELECTIVA-20202-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTVO-30405-B','Diagnóstico e Intervención de la Voz I','Trayecto III','Técnico Superior Universitario',5,'PRACPROF-21200-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTVO-30405-B','Diagnóstico e Intervención de la Voz I','Trayecto III','Técnico Superior Universitario',5,'PROYSICO-20304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTAU-30405-B','Diagnóstico e Intervención Auditiva II','Trayecto III','Técnico Superior Universitario',5,'NEUROLIN-20405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTAU-30405-B','Diagnóstico e Intervención Auditiva II','Trayecto III','Técnico Superior Universitario',5,'RECPSIFO-20405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTAU-30405-B','Diagnóstico e Intervención Auditiva II','Trayecto III','Técnico Superior Universitario',5,'PATCOMHU-20506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTAU-30405-B','Diagnóstico e Intervención Auditiva II','Trayecto III','Técnico Superior Universitario',5,'DIAINTLE-20506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTAU-30405-B','Diagnóstico e Intervención Auditiva II','Trayecto III','Técnico Superior Universitario',5,'DIAINTHA-20506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTAU-30405-B','Diagnóstico e Intervención Auditiva II','Trayecto III','Técnico Superior Universitario',5,'DIAINTAU-20405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTAU-30405-B','Diagnóstico e Intervención Auditiva II','Trayecto III','Técnico Superior Universitario',5,'ELECTIVA-20202-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTAU-30405-B','Diagnóstico e Intervención Auditiva II','Trayecto III','Técnico Superior Universitario',5,'PRACPROF-21200-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTAU-30405-B','Diagnóstico e Intervención Auditiva II','Trayecto III','Técnico Superior Universitario',5,'PROYSICO-20304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','MOTRORAL-30304-B','Motricidad Oral','Trayecto III','Técnico Superior Universitario',4,'NEUROLIN-20405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','MOTRORAL-30304-B','Motricidad Oral','Trayecto III','Técnico Superior Universitario',4,'RECPSIFO-20405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','MOTRORAL-30304-B','Motricidad Oral','Trayecto III','Técnico Superior Universitario',4,'PATCOMHU-20506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','MOTRORAL-30304-B','Motricidad Oral','Trayecto III','Técnico Superior Universitario',4,'DIAINTLE-20506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','MOTRORAL-30304-B','Motricidad Oral','Trayecto III','Técnico Superior Universitario',4,'DIAINTHA-20506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','MOTRORAL-30304-B','Motricidad Oral','Trayecto III','Técnico Superior Universitario',4,'DIAINTAU-20405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','MOTRORAL-30304-B','Motricidad Oral','Trayecto III','Técnico Superior Universitario',4,'ELECTIVA-20202-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','MOTRORAL-30304-B','Motricidad Oral','Trayecto III','Técnico Superior Universitario',4,'PRACPROF-21200-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','MOTRORAL-30304-B','Motricidad Oral','Trayecto III','Técnico Superior Universitario',4,'PROYSICO-20304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DEPORECR-30202-B','Deporte y Recreación','Trayecto III','Técnico Superior Universitario',2,'NEUROLIN-20405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DEPORECR-30202-B','Deporte y Recreación','Trayecto III','Técnico Superior Universitario',2,'RECPSIFO-20405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DEPORECR-30202-B','Deporte y Recreación','Trayecto III','Técnico Superior Universitario',2,'PATCOMHU-20506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DEPORECR-30202-B','Deporte y Recreación','Trayecto III','Técnico Superior Universitario',2,'DIAINTLE-20506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DEPORECR-30202-B','Deporte y Recreación','Trayecto III','Técnico Superior Universitario',2,'DIAINTHA-20506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DEPORECR-30202-B','Deporte y Recreación','Trayecto III','Técnico Superior Universitario',2,'DIAINTAU-20405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DEPORECR-30202-B','Deporte y Recreación','Trayecto III','Técnico Superior Universitario',2,'ELECTIVA-20202-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DEPORECR-30202-B','Deporte y Recreación','Trayecto III','Técnico Superior Universitario',2,'PRACPROF-21200-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DEPORECR-30202-B','Deporte y Recreación','Trayecto III','Técnico Superior Universitario',2,'PROYSICO-20304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ELECTIVA-30202-B','Electiva','Trayecto III','Técnico Superior Universitario',2,'NEUROLIN-20405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ELECTIVA-30202-B','Electiva','Trayecto III','Técnico Superior Universitario',2,'RECPSIFO-20405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ELECTIVA-30202-B','Electiva','Trayecto III','Técnico Superior Universitario',2,'PATCOMHU-20506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ELECTIVA-30202-B','Electiva','Trayecto III','Técnico Superior Universitario',2,'DIAINTLE-20506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ELECTIVA-30202-B','Electiva','Trayecto III','Técnico Superior Universitario',2,'DIAINTHA-20506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ELECTIVA-30202-B','Electiva','Trayecto III','Técnico Superior Universitario',2,'DIAINTAU-20405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ELECTIVA-30202-B','Electiva','Trayecto III','Técnico Superior Universitario',2,'ELECTIVA-20202-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ELECTIVA-30202-B','Electiva','Trayecto III','Técnico Superior Universitario',2,'PRACPROF-21200-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ELECTIVA-30202-B','Electiva','Trayecto III','Técnico Superior Universitario',2,'PROYSICO-20304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PRACPROF-3100-B','Prácticas Profesionales II','Trayecto III','Técnico Superior Universitario',0,'NEUROLIN-20405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PRACPROF-3100-B','Prácticas Profesionales II','Trayecto III','Técnico Superior Universitario',0,'RECPSIFO-20405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PRACPROF-3100-B','Prácticas Profesionales II','Trayecto III','Técnico Superior Universitario',0,'PATCOMHU-20506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PRACPROF-3100-B','Prácticas Profesionales II','Trayecto III','Técnico Superior Universitario',0,'DIAINTLE-20506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PRACPROF-3100-B','Prácticas Profesionales II','Trayecto III','Técnico Superior Universitario',0,'DIAINTHA-20506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PRACPROF-3100-B','Prácticas Profesionales II','Trayecto III','Técnico Superior Universitario',0,'DIAINTAU-20405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PRACPROF-3100-B','Prácticas Profesionales II','Trayecto III','Técnico Superior Universitario',0,'ELECTIVA-20202-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PRACPROF-3100-B','Prácticas Profesionales II','Trayecto III','Técnico Superior Universitario',0,'PRACPROF-21200-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PRACPROF-3100-B','Prácticas Profesionales II','Trayecto III','Técnico Superior Universitario',0,'PROYSICO-20304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PROYSOCI-30304-B','Proyecto Sociointegrador III','Trayecto III','Técnico Superior Universitario',4,'NEUROLIN-20405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PROYSOCI-30304-B','Proyecto Sociointegrador III','Trayecto III','Técnico Superior Universitario',4,'RECPSIFO-20405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PROYSOCI-30304-B','Proyecto Sociointegrador III','Trayecto III','Técnico Superior Universitario',4,'PATCOMHU-20506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PROYSOCI-30304-B','Proyecto Sociointegrador III','Trayecto III','Técnico Superior Universitario',4,'DIAINTLE-20506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PROYSOCI-30304-B','Proyecto Sociointegrador III','Trayecto III','Técnico Superior Universitario',4,'DIAINTHA-20506-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PROYSOCI-30304-B','Proyecto Sociointegrador III','Trayecto III','Técnico Superior Universitario',4,'DIAINTAU-20405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PROYSOCI-30304-B','Proyecto Sociointegrador III','Trayecto III','Técnico Superior Universitario',4,'ELECTIVA-20202-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PROYSOCI-30304-B','Proyecto Sociointegrador III','Trayecto III','Técnico Superior Universitario',4,'PRACPROF-21200-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PROYSOCI-30304-B','Proyecto Sociointegrador III','Trayecto III','Técnico Superior Universitario',4,'PROYSICO-20304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','GESADMFO-40506-B','Gestión Administrativa en Fonoaudiología','Trayecto IV','Licenciatura',6,'DIAINTLE-30405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','GESADMFO-40506-B','Gestión Administrativa en Fonoaudiología','Trayecto IV','Licenciatura',6,'DIAINTVO-30405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','GESADMFO-40506-B','Gestión Administrativa en Fonoaudiología','Trayecto IV','Licenciatura',6,'DIAINTAU-30405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','GESADMFO-40506-B','Gestión Administrativa en Fonoaudiología','Trayecto IV','Licenciatura',6,'MOTRORAL-30304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','GESADMFO-40506-B','Gestión Administrativa en Fonoaudiología','Trayecto IV','Licenciatura',6,'DEPORECR-30202-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','GESADMFO-40506-B','Gestión Administrativa en Fonoaudiología','Trayecto IV','Licenciatura',6,'ELECTIVA-30202-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','GESADMFO-40506-B','Gestión Administrativa en Fonoaudiología','Trayecto IV','Licenciatura',6,'PRACPROF-3100-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','GESADMFO-40506-B','Gestión Administrativa en Fonoaudiología','Trayecto IV','Licenciatura',6,'PROYSOCI-30304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTVO-40506-B','Diagnóstico e Intervención de la Voz II','Trayecto IV','Licenciatura',6,'DIAINTLE-30405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTVO-40506-B','Diagnóstico e Intervención de la Voz II','Trayecto IV','Licenciatura',6,'DIAINTVO-30405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTVO-40506-B','Diagnóstico e Intervención de la Voz II','Trayecto IV','Licenciatura',6,'DIAINTAU-30405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTVO-40506-B','Diagnóstico e Intervención de la Voz II','Trayecto IV','Licenciatura',6,'MOTRORAL-30304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTVO-40506-B','Diagnóstico e Intervención de la Voz II','Trayecto IV','Licenciatura',6,'DEPORECR-30202-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTVO-40506-B','Diagnóstico e Intervención de la Voz II','Trayecto IV','Licenciatura',6,'ELECTIVA-30202-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTVO-40506-B','Diagnóstico e Intervención de la Voz II','Trayecto IV','Licenciatura',6,'PRACPROF-3100-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTVO-40506-B','Diagnóstico e Intervención de la Voz II','Trayecto IV','Licenciatura',6,'PROYSOCI-30304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTAU-40506-B','Diagnóstico e Intervención Auditiva III','Trayecto IV','Licenciatura',6,'DIAINTLE-30405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTAU-40506-B','Diagnóstico e Intervención Auditiva III','Trayecto IV','Licenciatura',6,'DIAINTVO-30405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTAU-40506-B','Diagnóstico e Intervención Auditiva III','Trayecto IV','Licenciatura',6,'DIAINTAU-30405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTAU-40506-B','Diagnóstico e Intervención Auditiva III','Trayecto IV','Licenciatura',6,'MOTRORAL-30304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTAU-40506-B','Diagnóstico e Intervención Auditiva III','Trayecto IV','Licenciatura',6,'DEPORECR-30202-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTAU-40506-B','Diagnóstico e Intervención Auditiva III','Trayecto IV','Licenciatura',6,'ELECTIVA-30202-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTAU-40506-B','Diagnóstico e Intervención Auditiva III','Trayecto IV','Licenciatura',6,'PRACPROF-3100-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','DIAINTAU-40506-B','Diagnóstico e Intervención Auditiva III','Trayecto IV','Licenciatura',6,'PROYSOCI-30304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','SALOCUFO-40506-B','Salud Ocupacional en Fonoaudiología','Trayecto IV','Licenciatura',6,'DIAINTLE-30405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','SALOCUFO-40506-B','Salud Ocupacional en Fonoaudiología','Trayecto IV','Licenciatura',6,'DIAINTVO-30405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','SALOCUFO-40506-B','Salud Ocupacional en Fonoaudiología','Trayecto IV','Licenciatura',6,'DIAINTAU-30405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','SALOCUFO-40506-B','Salud Ocupacional en Fonoaudiología','Trayecto IV','Licenciatura',6,'MOTRORAL-30304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','SALOCUFO-40506-B','Salud Ocupacional en Fonoaudiología','Trayecto IV','Licenciatura',6,'DEPORECR-30202-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','SALOCUFO-40506-B','Salud Ocupacional en Fonoaudiología','Trayecto IV','Licenciatura',6,'ELECTIVA-30202-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','SALOCUFO-40506-B','Salud Ocupacional en Fonoaudiología','Trayecto IV','Licenciatura',6,'PRACPROF-3100-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','SALOCUFO-40506-B','Salud Ocupacional en Fonoaudiología','Trayecto IV','Licenciatura',6,'PROYSOCI-30304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','TECTECFO-40506-B','Técnicas y Tecnologías en Fonoaudiología','Trayecto IV','Licenciatura',6,'DIAINTLE-30405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','TECTECFO-40506-B','Técnicas y Tecnologías en Fonoaudiología','Trayecto IV','Licenciatura',6,'DIAINTVO-30405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','TECTECFO-40506-B','Técnicas y Tecnologías en Fonoaudiología','Trayecto IV','Licenciatura',6,'DIAINTAU-30405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','TECTECFO-40506-B','Técnicas y Tecnologías en Fonoaudiología','Trayecto IV','Licenciatura',6,'MOTRORAL-30304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','TECTECFO-40506-B','Técnicas y Tecnologías en Fonoaudiología','Trayecto IV','Licenciatura',6,'DEPORECR-30202-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','TECTECFO-40506-B','Técnicas y Tecnologías en Fonoaudiología','Trayecto IV','Licenciatura',6,'ELECTIVA-30202-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','TECTECFO-40506-B','Técnicas y Tecnologías en Fonoaudiología','Trayecto IV','Licenciatura',6,'PRACPROF-3100-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','TECTECFO-40506-B','Técnicas y Tecnologías en Fonoaudiología','Trayecto IV','Licenciatura',6,'PROYSOCI-30304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PORTINST-40304-B','Portugués Instrumental','Trayecto IV','Licenciatura',4,'DIAINTLE-30405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PORTINST-40304-B','Portugués Instrumental','Trayecto IV','Licenciatura',4,'DIAINTVO-30405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PORTINST-40304-B','Portugués Instrumental','Trayecto IV','Licenciatura',4,'DIAINTAU-30405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PORTINST-40304-B','Portugués Instrumental','Trayecto IV','Licenciatura',4,'MOTRORAL-30304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PORTINST-40304-B','Portugués Instrumental','Trayecto IV','Licenciatura',4,'DEPORECR-30202-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PORTINST-40304-B','Portugués Instrumental','Trayecto IV','Licenciatura',4,'ELECTIVA-30202-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PORTINST-40304-B','Portugués Instrumental','Trayecto IV','Licenciatura',4,'PRACPROF-3100-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PORTINST-40304-B','Portugués Instrumental','Trayecto IV','Licenciatura',4,'PROYSOCI-30304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','EDUCAMBI-40304-B','Educación Ambiental','Trayecto IV','Licenciatura',4,'DIAINTLE-30405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','EDUCAMBI-40304-B','Educación Ambiental','Trayecto IV','Licenciatura',4,'DIAINTVO-30405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','EDUCAMBI-40304-B','Educación Ambiental','Trayecto IV','Licenciatura',4,'DIAINTAU-30405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','EDUCAMBI-40304-B','Educación Ambiental','Trayecto IV','Licenciatura',4,'MOTRORAL-30304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','EDUCAMBI-40304-B','Educación Ambiental','Trayecto IV','Licenciatura',4,'DEPORECR-30202-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','EDUCAMBI-40304-B','Educación Ambiental','Trayecto IV','Licenciatura',4,'ELECTIVA-30202-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','EDUCAMBI-40304-B','Educación Ambiental','Trayecto IV','Licenciatura',4,'PRACPROF-3100-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','EDUCAMBI-40304-B','Educación Ambiental','Trayecto IV','Licenciatura',4,'PROYSOCI-30304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ELECTIVA-40202-B','ElectivA','Trayecto IV','Licenciatura',2,'DIAINTLE-30405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ELECTIVA-40202-B','ElectivA','Trayecto IV','Licenciatura',2,'DIAINTVO-30405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ELECTIVA-40202-B','ElectivA','Trayecto IV','Licenciatura',2,'DIAINTAU-30405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ELECTIVA-40202-B','ElectivA','Trayecto IV','Licenciatura',2,'MOTRORAL-30304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ELECTIVA-40202-B','ElectivA','Trayecto IV','Licenciatura',2,'DEPORECR-30202-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ELECTIVA-40202-B','ElectivA','Trayecto IV','Licenciatura',2,'ELECTIVA-30202-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ELECTIVA-40202-B','ElectivA','Trayecto IV','Licenciatura',2,'PRACPROF-3100-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ELECTIVA-40202-B','ElectivA','Trayecto IV','Licenciatura',2,'PROYSOCI-30304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ELECTIVa-40202-B','Electiva','Trayecto IV','Licenciatura',2,'DIAINTLE-30405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ELECTIVa-40202-B','Electiva','Trayecto IV','Licenciatura',2,'DIAINTVO-30405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ELECTIVa-40202-B','Electiva','Trayecto IV','Licenciatura',2,'DIAINTAU-30405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ELECTIVa-40202-B','Electiva','Trayecto IV','Licenciatura',2,'MOTRORAL-30304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ELECTIVa-40202-B','Electiva','Trayecto IV','Licenciatura',2,'DEPORECR-30202-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ELECTIVa-40202-B','Electiva','Trayecto IV','Licenciatura',2,'ELECTIVA-30202-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ELECTIVa-40202-B','Electiva','Trayecto IV','Licenciatura',2,'PRACPROF-3100-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','ELECTIVa-40202-B','Electiva','Trayecto IV','Licenciatura',2,'PROYSOCI-30304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PRACPROF-41221-B','Prácticas Profesionales III','Trayecto IV','Licenciatura',21,'PROYSOCI-30304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PROYSOCI-40708-B','Proyecto Sociointegrador IV','Trayecto IV','Licenciatura',8,'DIAINTLE-30405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PROYSOCI-40708-B','Proyecto Sociointegrador IV','Trayecto IV','Licenciatura',8,'DIAINTVO-30405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PROYSOCI-40708-B','Proyecto Sociointegrador IV','Trayecto IV','Licenciatura',8,'DIAINTAU-30405-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PROYSOCI-40708-B','Proyecto Sociointegrador IV','Trayecto IV','Licenciatura',8,'MOTRORAL-30304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PROYSOCI-40708-B','Proyecto Sociointegrador IV','Trayecto IV','Licenciatura',8,'DEPORECR-30202-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PROYSOCI-40708-B','Proyecto Sociointegrador IV','Trayecto IV','Licenciatura',8,'ELECTIVA-30202-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PROYSOCI-40708-B','Proyecto Sociointegrador IV','Trayecto IV','Licenciatura',8,'PRACPROF-3100-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Fonoaudiología','PROYSOCI-40708-B','Proyecto Sociointegrador IV','Trayecto IV','Licenciatura',8,'PROYSOCI-30304-B',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','FISIAPLI-00300-C','Física Aplicada','Trayecto Inicial','',0,'',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','QUIMAPLI-00300-C','Química Aplicada','Trayecto Inicial','',0,'',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','MATEINST-00300-C','Matemática Instrumental','Trayecto Inicial','',0,'',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','SALPUBTO-00300-C','Salud Pública','Trayecto Inicial','',0,'',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','INPNORPR-00400-C','Introducción al PNF en Órtesis y Prótesis','Trayecto Inicial','',0,'',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PRNANUCI-00300-C','Proyecto Nacional y Nueva Ciudadanía','Trayecto Inicial','',0,'',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','LECTCOMP-00500-C','Lectura y Compresión','Trayecto Inicial','',0,'',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','HERRTECN-00500-C','Herramientas Tecnológicas','Trayecto Inicial','',0,'',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ANATOMOF-10304-C','Anatomofisiología','Trayecto I','',4,'FISIAPLI-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ANATOMOF-10304-C','Anatomofisiología','Trayecto I','',4,'QUIMAPLI-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ANATOMOF-10304-C','Anatomofisiología','Trayecto I','',4,'MATEINST-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ANATOMOF-10304-C','Anatomofisiología','Trayecto I','',4,'SALPUBTO-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ANATOMOF-10304-C','Anatomofisiología','Trayecto I','',4,'INPNORPR-00400-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ANATOMOF-10304-C','Anatomofisiología','Trayecto I','',4,'PRNANUCI-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ANATOMOF-10304-C','Anatomofisiología','Trayecto I','',4,'LECTCOMP-00500-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ANATOMOF-10304-C','Anatomofisiología','Trayecto I','',4,'HERRTECN-00500-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','BIOMECAN-10304-C','Biomecánica','Trayecto I','',4,'FISIAPLI-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','BIOMECAN-10304-C','Biomecánica','Trayecto I','',4,'QUIMAPLI-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','BIOMECAN-10304-C','Biomecánica','Trayecto I','',4,'MATEINST-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','BIOMECAN-10304-C','Biomecánica','Trayecto I','',4,'SALPUBTO-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','BIOMECAN-10304-C','Biomecánica','Trayecto I','',4,'INPNORPR-00400-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','BIOMECAN-10304-C','Biomecánica','Trayecto I','',4,'PRNANUCI-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','BIOMECAN-10304-C','Biomecánica','Trayecto I','',4,'LECTCOMP-00500-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','BIOMECAN-10304-C','Biomecánica','Trayecto I','',4,'HERRTECN-00500-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ORGPARCO-10304-C','Organización y Participación Comunitaria','Trayecto I','',4,'FISIAPLI-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ORGPARCO-10304-C','Organización y Participación Comunitaria','Trayecto I','',4,'QUIMAPLI-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ORGPARCO-10304-C','Organización y Participación Comunitaria','Trayecto I','',4,'MATEINST-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ORGPARCO-10304-C','Organización y Participación Comunitaria','Trayecto I','',4,'SALPUBTO-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ORGPARCO-10304-C','Organización y Participación Comunitaria','Trayecto I','',4,'INPNORPR-00400-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ORGPARCO-10304-C','Organización y Participación Comunitaria','Trayecto I','',4,'PRNANUCI-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ORGPARCO-10304-C','Organización y Participación Comunitaria','Trayecto I','',4,'LECTCOMP-00500-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ORGPARCO-10304-C','Organización y Participación Comunitaria','Trayecto I','',4,'HERRTECN-00500-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','BIOESTAD-10304-C','Bioestadística','Trayecto I','',4,'FISIAPLI-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','BIOESTAD-10304-C','Bioestadística','Trayecto I','',4,'QUIMAPLI-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','BIOESTAD-10304-C','Bioestadística','Trayecto I','',4,'MATEINST-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','BIOESTAD-10304-C','Bioestadística','Trayecto I','',4,'SALPUBTO-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','BIOESTAD-10304-C','Bioestadística','Trayecto I','',4,'INPNORPR-00400-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','BIOESTAD-10304-C','Bioestadística','Trayecto I','',4,'PRNANUCI-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','BIOESTAD-10304-C','Bioestadística','Trayecto I','',4,'LECTCOMP-00500-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','BIOESTAD-10304-C','Bioestadística','Trayecto I','',4,'HERRTECN-00500-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','FUINSAPU-10304-C','Fundamentos de la Investigación en Salud Pública','Trayecto I','',4,'FISIAPLI-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','FUINSAPU-10304-C','Fundamentos de la Investigación en Salud Pública','Trayecto I','',4,'QUIMAPLI-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','FUINSAPU-10304-C','Fundamentos de la Investigación en Salud Pública','Trayecto I','',4,'MATEINST-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','FUINSAPU-10304-C','Fundamentos de la Investigación en Salud Pública','Trayecto I','',4,'SALPUBTO-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','FUINSAPU-10304-C','Fundamentos de la Investigación en Salud Pública','Trayecto I','',4,'INPNORPR-00400-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','FUINSAPU-10304-C','Fundamentos de la Investigación en Salud Pública','Trayecto I','',4,'PRNANUCI-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','FUINSAPU-10304-C','Fundamentos de la Investigación en Salud Pública','Trayecto I','',4,'LECTCOMP-00500-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','FUINSAPU-10304-C','Fundamentos de la Investigación en Salud Pública','Trayecto I','',4,'HERRTECN-00500-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TECLABOR-10304-C','Tecnología de Laboratorio Ortopédico','Trayecto I','',5,'FISIAPLI-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TECLABOR-10304-C','Tecnología de Laboratorio Ortopédico','Trayecto I','',5,'QUIMAPLI-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TECLABOR-10304-C','Tecnología de Laboratorio Ortopédico','Trayecto I','',5,'MATEINST-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TECLABOR-10304-C','Tecnología de Laboratorio Ortopédico','Trayecto I','',5,'SALPUBTO-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TECLABOR-10304-C','Tecnología de Laboratorio Ortopédico','Trayecto I','',5,'INPNORPR-00400-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TECLABOR-10304-C','Tecnología de Laboratorio Ortopédico','Trayecto I','',5,'PRNANUCI-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TECLABOR-10304-C','Tecnología de Laboratorio Ortopédico','Trayecto I','',5,'LECTCOMP-00500-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TECLABOR-10304-C','Tecnología de Laboratorio Ortopédico','Trayecto I','',5,'HERRTECN-00500-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PRMAMMOP-10304-C','Procesos de Manufactura y Manejo de Materiales para Órtesis y Prótesis','Trayecto I','',4,'FISIAPLI-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PRMAMMOP-10304-C','Procesos de Manufactura y Manejo de Materiales para Órtesis y Prótesis','Trayecto I','',4,'QUIMAPLI-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PRMAMMOP-10304-C','Procesos de Manufactura y Manejo de Materiales para Órtesis y Prótesis','Trayecto I','',4,'MATEINST-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PRMAMMOP-10304-C','Procesos de Manufactura y Manejo de Materiales para Órtesis y Prótesis','Trayecto I','',4,'SALPUBTO-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PRMAMMOP-10304-C','Procesos de Manufactura y Manejo de Materiales para Órtesis y Prótesis','Trayecto I','',4,'INPNORPR-00400-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PRMAMMOP-10304-C','Procesos de Manufactura y Manejo de Materiales para Órtesis y Prótesis','Trayecto I','',4,'PRNANUCI-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PRMAMMOP-10304-C','Procesos de Manufactura y Manejo de Materiales para Órtesis y Prótesis','Trayecto I','',4,'LECTCOMP-00500-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PRMAMMOP-10304-C','Procesos de Manufactura y Manejo de Materiales para Órtesis y Prótesis','Trayecto I','',4,'HERRTECN-00500-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','EPIDEMIO-10304-C','Epidemiología','Trayecto I','',4,'FISIAPLI-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','EPIDEMIO-10304-C','Epidemiología','Trayecto I','',4,'QUIMAPLI-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','EPIDEMIO-10304-C','Epidemiología','Trayecto I','',4,'MATEINST-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','EPIDEMIO-10304-C','Epidemiología','Trayecto I','',4,'SALPUBTO-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','EPIDEMIO-10304-C','Epidemiología','Trayecto I','',4,'INPNORPR-00400-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','EPIDEMIO-10304-C','Epidemiología','Trayecto I','',4,'PRNANUCI-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','EPIDEMIO-10304-C','Epidemiología','Trayecto I','',4,'LECTCOMP-00500-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','EPIDEMIO-10304-C','Epidemiología','Trayecto I','',4,'HERRTECN-00500-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','INGLINST-10304-C','Inglés Instrumental I','Trayecto I','',4,'FISIAPLI-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','INGLINST-10304-C','Inglés Instrumental I','Trayecto I','',4,'QUIMAPLI-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','INGLINST-10304-C','Inglés Instrumental I','Trayecto I','',4,'MATEINST-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','INGLINST-10304-C','Inglés Instrumental I','Trayecto I','',4,'SALPUBTO-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','INGLINST-10304-C','Inglés Instrumental I','Trayecto I','',4,'INPNORPR-00400-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','INGLINST-10304-C','Inglés Instrumental I','Trayecto I','',4,'PRNANUCI-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','INGLINST-10304-C','Inglés Instrumental I','Trayecto I','',4,'LECTCOMP-00500-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','INGLINST-10304-C','Inglés Instrumental I','Trayecto I','',4,'HERRTECN-00500-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','DEPORECR-10202-C','Deporte y Recreación','Trayecto I','',2,'FISIAPLI-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','DEPORECR-10202-C','Deporte y Recreación','Trayecto I','',2,'QUIMAPLI-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','DEPORECR-10202-C','Deporte y Recreación','Trayecto I','',2,'MATEINST-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','DEPORECR-10202-C','Deporte y Recreación','Trayecto I','',2,'SALPUBTO-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','DEPORECR-10202-C','Deporte y Recreación','Trayecto I','',2,'INPNORPR-00400-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','DEPORECR-10202-C','Deporte y Recreación','Trayecto I','',2,'PRNANUCI-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','DEPORECR-10202-C','Deporte y Recreación','Trayecto I','',2,'LECTCOMP-00500-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','DEPORECR-10202-C','Deporte y Recreación','Trayecto I','',2,'HERRTECN-00500-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ELECTIVA-10202-C','Electiva I','Trayecto I','',2,'FISIAPLI-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ELECTIVA-10202-C','Electiva I','Trayecto I','',2,'QUIMAPLI-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ELECTIVA-10202-C','Electiva I','Trayecto I','',2,'MATEINST-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ELECTIVA-10202-C','Electiva I','Trayecto I','',2,'SALPUBTO-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ELECTIVA-10202-C','Electiva I','Trayecto I','',2,'INPNORPR-00400-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ELECTIVA-10202-C','Electiva I','Trayecto I','',2,'PRNANUCI-00300-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ELECTIVA-10202-C','Electiva I','Trayecto I','',2,'LECTCOMP-00500-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ELECTIVA-10202-C','Electiva I','Trayecto I','',2,'HERRTECN-00500-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PAENINSU-20405-C','Patologías en Extremidades Inferiores y Superiores','Trayecto II','',5,'BIOMECAN-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PAENINSU-20405-C','Patologías en Extremidades Inferiores y Superiores','Trayecto II','',5,'ORGPARCO-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PAENINSU-20405-C','Patologías en Extremidades Inferiores y Superiores','Trayecto II','',5,'BIOESTAD-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PAENINSU-20405-C','Patologías en Extremidades Inferiores y Superiores','Trayecto II','',5,'FUINSAPU-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PAENINSU-20405-C','Patologías en Extremidades Inferiores y Superiores','Trayecto II','',5,'TECLABOR-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PAENINSU-20405-C','Patologías en Extremidades Inferiores y Superiores','Trayecto II','',5,'PRMAMMOP-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PAENINSU-20405-C','Patologías en Extremidades Inferiores y Superiores','Trayecto II','',5,'EPIDEMIO-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PAENINSU-20405-C','Patologías en Extremidades Inferiores y Superiores','Trayecto II','',5,'INGLINST-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PAENINSU-20405-C','Patologías en Extremidades Inferiores y Superiores','Trayecto II','',5,'DEPORECR-10202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PAENINSU-20405-C','Patologías en Extremidades Inferiores y Superiores','Trayecto II','',5,'ELECTIVA-10202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORPREI-20405-C','Técnicas en Órtesis y Prótesis para Extremidades Inferiores I','Trayecto II','',5,'BIOMECAN-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORPREI-20405-C','Técnicas en Órtesis y Prótesis para Extremidades Inferiores I','Trayecto II','',5,'ORGPARCO-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORPREI-20405-C','Técnicas en Órtesis y Prótesis para Extremidades Inferiores I','Trayecto II','',5,'BIOESTAD-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORPREI-20405-C','Técnicas en Órtesis y Prótesis para Extremidades Inferiores I','Trayecto II','',5,'FUINSAPU-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORPREI-20405-C','Técnicas en Órtesis y Prótesis para Extremidades Inferiores I','Trayecto II','',5,'TECLABOR-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORPREI-20405-C','Técnicas en Órtesis y Prótesis para Extremidades Inferiores I','Trayecto II','',5,'PRMAMMOP-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORPREI-20405-C','Técnicas en Órtesis y Prótesis para Extremidades Inferiores I','Trayecto II','',5,'EPIDEMIO-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORPREI-20405-C','Técnicas en Órtesis y Prótesis para Extremidades Inferiores I','Trayecto II','',5,'INGLINST-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORPREI-20405-C','Técnicas en Órtesis y Prótesis para Extremidades Inferiores I','Trayecto II','',5,'DEPORECR-10202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORPREI-20405-C','Técnicas en Órtesis y Prótesis para Extremidades Inferiores I','Trayecto II','',5,'ELECTIVA-10202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORPRES-20405-C','Técnicas en Órtesis y Prótesis para Extremidades Superiores I','Trayecto II','',5,'BIOMECAN-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORPRES-20405-C','Técnicas en Órtesis y Prótesis para Extremidades Superiores I','Trayecto II','',5,'ORGPARCO-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORPRES-20405-C','Técnicas en Órtesis y Prótesis para Extremidades Superiores I','Trayecto II','',5,'BIOESTAD-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORPRES-20405-C','Técnicas en Órtesis y Prótesis para Extremidades Superiores I','Trayecto II','',5,'FUINSAPU-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORPRES-20405-C','Técnicas en Órtesis y Prótesis para Extremidades Superiores I','Trayecto II','',5,'TECLABOR-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORPRES-20405-C','Técnicas en Órtesis y Prótesis para Extremidades Superiores I','Trayecto II','',5,'PRMAMMOP-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORPRES-20405-C','Técnicas en Órtesis y Prótesis para Extremidades Superiores I','Trayecto II','',5,'EPIDEMIO-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORPRES-20405-C','Técnicas en Órtesis y Prótesis para Extremidades Superiores I','Trayecto II','',5,'INGLINST-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORPRES-20405-C','Técnicas en Órtesis y Prótesis para Extremidades Superiores I','Trayecto II','',5,'DEPORECR-10202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORPRES-20405-C','Técnicas en Órtesis y Prótesis para Extremidades Superiores I','Trayecto II','',5,'ELECTIVA-10202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PSICDESA-20304-C','Psicología del Desarrollo','Trayecto II','',4,'BIOMECAN-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PSICDESA-20304-C','Psicología del Desarrollo','Trayecto II','',4,'ORGPARCO-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PSICDESA-20304-C','Psicología del Desarrollo','Trayecto II','',4,'BIOESTAD-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PSICDESA-20304-C','Psicología del Desarrollo','Trayecto II','',4,'FUINSAPU-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PSICDESA-20304-C','Psicología del Desarrollo','Trayecto II','',4,'TECLABOR-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PSICDESA-20304-C','Psicología del Desarrollo','Trayecto II','',4,'PRMAMMOP-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PSICDESA-20304-C','Psicología del Desarrollo','Trayecto II','',4,'EPIDEMIO-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PSICDESA-20304-C','Psicología del Desarrollo','Trayecto II','',4,'INGLINST-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PSICDESA-20304-C','Psicología del Desarrollo','Trayecto II','',4,'DEPORECR-10202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PSICDESA-20304-C','Psicología del Desarrollo','Trayecto II','',4,'ELECTIVA-10202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORCACT-20304-C','Tecnología en Órtesis para Cabeza, Cuello y Tronco','Trayecto II','',5,'BIOMECAN-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORCACT-20304-C','Tecnología en Órtesis para Cabeza, Cuello y Tronco','Trayecto II','',5,'ORGPARCO-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORCACT-20304-C','Tecnología en Órtesis para Cabeza, Cuello y Tronco','Trayecto II','',5,'BIOESTAD-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORCACT-20304-C','Tecnología en Órtesis para Cabeza, Cuello y Tronco','Trayecto II','',5,'FUINSAPU-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORCACT-20304-C','Tecnología en Órtesis para Cabeza, Cuello y Tronco','Trayecto II','',5,'TECLABOR-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORCACT-20304-C','Tecnología en Órtesis para Cabeza, Cuello y Tronco','Trayecto II','',5,'PRMAMMOP-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORCACT-20304-C','Tecnología en Órtesis para Cabeza, Cuello y Tronco','Trayecto II','',5,'EPIDEMIO-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORCACT-20304-C','Tecnología en Órtesis para Cabeza, Cuello y Tronco','Trayecto II','',5,'INGLINST-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORCACT-20304-C','Tecnología en Órtesis para Cabeza, Cuello y Tronco','Trayecto II','',5,'DEPORECR-10202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORCACT-20304-C','Tecnología en Órtesis para Cabeza, Cuello y Tronco','Trayecto II','',5,'ELECTIVA-10202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','DISCREHA-20202-C','Discapacidad y Rehabilitación','Trayecto II','',2,'BIOMECAN-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','DISCREHA-20202-C','Discapacidad y Rehabilitación','Trayecto II','',2,'ORGPARCO-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','DISCREHA-20202-C','Discapacidad y Rehabilitación','Trayecto II','',2,'BIOESTAD-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','DISCREHA-20202-C','Discapacidad y Rehabilitación','Trayecto II','',2,'FUINSAPU-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','DISCREHA-20202-C','Discapacidad y Rehabilitación','Trayecto II','',2,'TECLABOR-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','DISCREHA-20202-C','Discapacidad y Rehabilitación','Trayecto II','',2,'PRMAMMOP-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','DISCREHA-20202-C','Discapacidad y Rehabilitación','Trayecto II','',2,'EPIDEMIO-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','DISCREHA-20202-C','Discapacidad y Rehabilitación','Trayecto II','',2,'INGLINST-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','DISCREHA-20202-C','Discapacidad y Rehabilitación','Trayecto II','',2,'DEPORECR-10202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','DISCREHA-20202-C','Discapacidad y Rehabilitación','Trayecto II','',2,'ELECTIVA-10202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PACACUTR-20304-C','Patología de Cabeza, Cuello y Tronco','Trayecto II','',4,'BIOMECAN-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PACACUTR-20304-C','Patología de Cabeza, Cuello y Tronco','Trayecto II','',4,'ORGPARCO-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PACACUTR-20304-C','Patología de Cabeza, Cuello y Tronco','Trayecto II','',4,'BIOESTAD-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PACACUTR-20304-C','Patología de Cabeza, Cuello y Tronco','Trayecto II','',4,'FUINSAPU-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PACACUTR-20304-C','Patología de Cabeza, Cuello y Tronco','Trayecto II','',4,'TECLABOR-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PACACUTR-20304-C','Patología de Cabeza, Cuello y Tronco','Trayecto II','',4,'PRMAMMOP-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PACACUTR-20304-C','Patología de Cabeza, Cuello y Tronco','Trayecto II','',4,'EPIDEMIO-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PACACUTR-20304-C','Patología de Cabeza, Cuello y Tronco','Trayecto II','',4,'INGLINST-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PACACUTR-20304-C','Patología de Cabeza, Cuello y Tronco','Trayecto II','',4,'DEPORECR-10202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PACACUTR-20304-C','Patología de Cabeza, Cuello y Tronco','Trayecto II','',4,'ELECTIVA-10202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','DITEASCO-20304-C','Dibujo Técnico y Asistido por Computadora','Trayecto II','',4,'BIOMECAN-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','DITEASCO-20304-C','Dibujo Técnico y Asistido por Computadora','Trayecto II','',4,'ORGPARCO-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','DITEASCO-20304-C','Dibujo Técnico y Asistido por Computadora','Trayecto II','',4,'BIOESTAD-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','DITEASCO-20304-C','Dibujo Técnico y Asistido por Computadora','Trayecto II','',4,'FUINSAPU-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','DITEASCO-20304-C','Dibujo Técnico y Asistido por Computadora','Trayecto II','',4,'TECLABOR-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','DITEASCO-20304-C','Dibujo Técnico y Asistido por Computadora','Trayecto II','',4,'PRMAMMOP-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','DITEASCO-20304-C','Dibujo Técnico y Asistido por Computadora','Trayecto II','',4,'EPIDEMIO-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','DITEASCO-20304-C','Dibujo Técnico y Asistido por Computadora','Trayecto II','',4,'INGLINST-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','DITEASCO-20304-C','Dibujo Técnico y Asistido por Computadora','Trayecto II','',4,'DEPORECR-10202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','DITEASCO-20304-C','Dibujo Técnico y Asistido por Computadora','Trayecto II','',4,'ELECTIVA-10202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','INGLINST-20304-C','Inglés Instrumental II','Trayecto II','',4,'BIOMECAN-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','INGLINST-20304-C','Inglés Instrumental II','Trayecto II','',4,'ORGPARCO-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','INGLINST-20304-C','Inglés Instrumental II','Trayecto II','',4,'BIOESTAD-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','INGLINST-20304-C','Inglés Instrumental II','Trayecto II','',4,'FUINSAPU-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','INGLINST-20304-C','Inglés Instrumental II','Trayecto II','',4,'TECLABOR-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','INGLINST-20304-C','Inglés Instrumental II','Trayecto II','',4,'PRMAMMOP-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','INGLINST-20304-C','Inglés Instrumental II','Trayecto II','',4,'EPIDEMIO-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','INGLINST-20304-C','Inglés Instrumental II','Trayecto II','',4,'INGLINST-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','INGLINST-20304-C','Inglés Instrumental II','Trayecto II','',4,'DEPORECR-10202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','INGLINST-20304-C','Inglés Instrumental II','Trayecto II','',4,'ELECTIVA-10202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PRACPROF-21200-C','Práctica Profesional I','Trayecto II','',0,'BIOMECAN-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PRACPROF-21200-C','Práctica Profesional I','Trayecto II','',0,'ORGPARCO-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PRACPROF-21200-C','Práctica Profesional I','Trayecto II','',0,'BIOESTAD-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PRACPROF-21200-C','Práctica Profesional I','Trayecto II','',0,'FUINSAPU-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PRACPROF-21200-C','Práctica Profesional I','Trayecto II','',0,'TECLABOR-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PRACPROF-21200-C','Práctica Profesional I','Trayecto II','',0,'PRMAMMOP-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PRACPROF-21200-C','Práctica Profesional I','Trayecto II','',0,'EPIDEMIO-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PRACPROF-21200-C','Práctica Profesional I','Trayecto II','',0,'INGLINST-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PRACPROF-21200-C','Práctica Profesional I','Trayecto II','',0,'DEPORECR-10202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PRACPROF-21200-C','Práctica Profesional I','Trayecto II','',0,'ELECTIVA-10202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PROYSOCI-20304-C','Proyecto Sociointegrador I','Trayecto II','',4,'BIOMECAN-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PROYSOCI-20304-C','Proyecto Sociointegrador I','Trayecto II','',4,'ORGPARCO-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PROYSOCI-20304-C','Proyecto Sociointegrador I','Trayecto II','',4,'BIOESTAD-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PROYSOCI-20304-C','Proyecto Sociointegrador I','Trayecto II','',4,'FUINSAPU-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PROYSOCI-20304-C','Proyecto Sociointegrador I','Trayecto II','',4,'TECLABOR-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PROYSOCI-20304-C','Proyecto Sociointegrador I','Trayecto II','',4,'PRMAMMOP-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PROYSOCI-20304-C','Proyecto Sociointegrador I','Trayecto II','',4,'EPIDEMIO-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PROYSOCI-20304-C','Proyecto Sociointegrador I','Trayecto II','',4,'INGLINST-10304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PROYSOCI-20304-C','Proyecto Sociointegrador I','Trayecto II','',4,'DEPORECR-10202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PROYSOCI-20304-C','Proyecto Sociointegrador I','Trayecto II','',4,'ELECTIVA-10202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORPREI-30405-C','Técnicas en Órtesis y Prótesis para Extremidades Inferiores II','Trayecto III','Técnico Superior Universitario',5,'TEORPREI-20405-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORPREI-30405-C','Técnicas en Órtesis y Prótesis para Extremidades Inferiores II','Trayecto III','Técnico Superior Universitario',5,'TEORPRES-20405-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORPREI-30405-C','Técnicas en Órtesis y Prótesis para Extremidades Inferiores II','Trayecto III','Técnico Superior Universitario',5,'PSICDESA-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORPREI-30405-C','Técnicas en Órtesis y Prótesis para Extremidades Inferiores II','Trayecto III','Técnico Superior Universitario',5,'TEORCACT-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORPREI-30405-C','Técnicas en Órtesis y Prótesis para Extremidades Inferiores II','Trayecto III','Técnico Superior Universitario',5,'DISCREHA-20202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORPREI-30405-C','Técnicas en Órtesis y Prótesis para Extremidades Inferiores II','Trayecto III','Técnico Superior Universitario',5,'PACACUTR-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORPREI-30405-C','Técnicas en Órtesis y Prótesis para Extremidades Inferiores II','Trayecto III','Técnico Superior Universitario',5,'DITEASCO-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORPREI-30405-C','Técnicas en Órtesis y Prótesis para Extremidades Inferiores II','Trayecto III','Técnico Superior Universitario',5,'INGLINST-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORPREI-30405-C','Técnicas en Órtesis y Prótesis para Extremidades Inferiores II','Trayecto III','Técnico Superior Universitario',5,'PRACPROF-21200-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORPREI-30405-C','Técnicas en Órtesis y Prótesis para Extremidades Inferiores II','Trayecto III','Técnico Superior Universitario',5,'PROYSOCI-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORPRES-30405-C','Técnicas en Órtesis y Prótesis para Extremidades Superiores II','Trayecto III','Técnico Superior Universitario',5,'TEORPREI-20405-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORPRES-30405-C','Técnicas en Órtesis y Prótesis para Extremidades Superiores II','Trayecto III','Técnico Superior Universitario',5,'TEORPRES-20405-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORPRES-30405-C','Técnicas en Órtesis y Prótesis para Extremidades Superiores II','Trayecto III','Técnico Superior Universitario',5,'PSICDESA-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORPRES-30405-C','Técnicas en Órtesis y Prótesis para Extremidades Superiores II','Trayecto III','Técnico Superior Universitario',5,'TEORCACT-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORPRES-30405-C','Técnicas en Órtesis y Prótesis para Extremidades Superiores II','Trayecto III','Técnico Superior Universitario',5,'DISCREHA-20202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORPRES-30405-C','Técnicas en Órtesis y Prótesis para Extremidades Superiores II','Trayecto III','Técnico Superior Universitario',5,'PACACUTR-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORPRES-30405-C','Técnicas en Órtesis y Prótesis para Extremidades Superiores II','Trayecto III','Técnico Superior Universitario',5,'DITEASCO-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORPRES-30405-C','Técnicas en Órtesis y Prótesis para Extremidades Superiores II','Trayecto III','Técnico Superior Universitario',5,'INGLINST-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORPRES-30405-C','Técnicas en Órtesis y Prótesis para Extremidades Superiores II','Trayecto III','Técnico Superior Universitario',5,'PRACPROF-21200-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','TEORPRES-30405-C','Técnicas en Órtesis y Prótesis para Extremidades Superiores II','Trayecto III','Técnico Superior Universitario',5,'PROYSOCI-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PROCADMI-30202-C','Proceso Administrativo','Trayecto III','Técnico Superior Universitario',2,'TEORPREI-20405-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PROCADMI-30202-C','Proceso Administrativo','Trayecto III','Técnico Superior Universitario',2,'TEORPRES-20405-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PROCADMI-30202-C','Proceso Administrativo','Trayecto III','Técnico Superior Universitario',2,'PSICDESA-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PROCADMI-30202-C','Proceso Administrativo','Trayecto III','Técnico Superior Universitario',2,'TEORCACT-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PROCADMI-30202-C','Proceso Administrativo','Trayecto III','Técnico Superior Universitario',2,'DISCREHA-20202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PROCADMI-30202-C','Proceso Administrativo','Trayecto III','Técnico Superior Universitario',2,'PACACUTR-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PROCADMI-30202-C','Proceso Administrativo','Trayecto III','Técnico Superior Universitario',2,'DITEASCO-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PROCADMI-30202-C','Proceso Administrativo','Trayecto III','Técnico Superior Universitario',2,'INGLINST-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PROCADMI-30202-C','Proceso Administrativo','Trayecto III','Técnico Superior Universitario',2,'PRACPROF-21200-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PROCADMI-30202-C','Proceso Administrativo','Trayecto III','Técnico Superior Universitario',2,'PROYSOCI-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PSAPAPDE-30202-C','Psicología Aplicada al Abordaje de la Persona con Discapacidad y su Entorno Familiar','Trayecto III','Técnico Superior Universitario',2,'TEORPREI-20405-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PSAPAPDE-30202-C','Psicología Aplicada al Abordaje de la Persona con Discapacidad y su Entorno Familiar','Trayecto III','Técnico Superior Universitario',2,'TEORPRES-20405-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PSAPAPDE-30202-C','Psicología Aplicada al Abordaje de la Persona con Discapacidad y su Entorno Familiar','Trayecto III','Técnico Superior Universitario',2,'PSICDESA-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PSAPAPDE-30202-C','Psicología Aplicada al Abordaje de la Persona con Discapacidad y su Entorno Familiar','Trayecto III','Técnico Superior Universitario',2,'TEORCACT-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PSAPAPDE-30202-C','Psicología Aplicada al Abordaje de la Persona con Discapacidad y su Entorno Familiar','Trayecto III','Técnico Superior Universitario',2,'DISCREHA-20202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PSAPAPDE-30202-C','Psicología Aplicada al Abordaje de la Persona con Discapacidad y su Entorno Familiar','Trayecto III','Técnico Superior Universitario',2,'PACACUTR-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PSAPAPDE-30202-C','Psicología Aplicada al Abordaje de la Persona con Discapacidad y su Entorno Familiar','Trayecto III','Técnico Superior Universitario',2,'DITEASCO-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PSAPAPDE-30202-C','Psicología Aplicada al Abordaje de la Persona con Discapacidad y su Entorno Familiar','Trayecto III','Técnico Superior Universitario',2,'INGLINST-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PSAPAPDE-30202-C','Psicología Aplicada al Abordaje de la Persona con Discapacidad y su Entorno Familiar','Trayecto III','Técnico Superior Universitario',2,'PRACPROF-21200-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PSAPAPDE-30202-C','Psicología Aplicada al Abordaje de la Persona con Discapacidad y su Entorno Familiar','Trayecto III','Técnico Superior Universitario',2,'PROYSOCI-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ELEBASAP-30302-C','Electrónica Básica y Aplicada','Trayecto III','Técnico Superior Universitario',2,'TEORPREI-20405-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ELEBASAP-30302-C','Electrónica Básica y Aplicada','Trayecto III','Técnico Superior Universitario',2,'TEORPRES-20405-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ELEBASAP-30302-C','Electrónica Básica y Aplicada','Trayecto III','Técnico Superior Universitario',2,'PSICDESA-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ELEBASAP-30302-C','Electrónica Básica y Aplicada','Trayecto III','Técnico Superior Universitario',2,'TEORCACT-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ELEBASAP-30302-C','Electrónica Básica y Aplicada','Trayecto III','Técnico Superior Universitario',2,'DISCREHA-20202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ELEBASAP-30302-C','Electrónica Básica y Aplicada','Trayecto III','Técnico Superior Universitario',2,'PACACUTR-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ELEBASAP-30302-C','Electrónica Básica y Aplicada','Trayecto III','Técnico Superior Universitario',2,'DITEASCO-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ELEBASAP-30302-C','Electrónica Básica y Aplicada','Trayecto III','Técnico Superior Universitario',2,'INGLINST-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ELEBASAP-30302-C','Electrónica Básica y Aplicada','Trayecto III','Técnico Superior Universitario',2,'PRACPROF-21200-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ELEBASAP-30302-C','Electrónica Básica y Aplicada','Trayecto III','Técnico Superior Universitario',2,'PROYSOCI-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','INGLINST-30202-C','Inglés Instrumental III','Trayecto III','Técnico Superior Universitario',2,'TEORPREI-20405-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','INGLINST-30202-C','Inglés Instrumental III','Trayecto III','Técnico Superior Universitario',2,'TEORPRES-20405-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','INGLINST-30202-C','Inglés Instrumental III','Trayecto III','Técnico Superior Universitario',2,'PSICDESA-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','INGLINST-30202-C','Inglés Instrumental III','Trayecto III','Técnico Superior Universitario',2,'TEORCACT-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','INGLINST-30202-C','Inglés Instrumental III','Trayecto III','Técnico Superior Universitario',2,'DISCREHA-20202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','INGLINST-30202-C','Inglés Instrumental III','Trayecto III','Técnico Superior Universitario',2,'PACACUTR-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','INGLINST-30202-C','Inglés Instrumental III','Trayecto III','Técnico Superior Universitario',2,'DITEASCO-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','INGLINST-30202-C','Inglés Instrumental III','Trayecto III','Técnico Superior Universitario',2,'INGLINST-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','INGLINST-30202-C','Inglés Instrumental III','Trayecto III','Técnico Superior Universitario',2,'PRACPROF-21200-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','INGLINST-30202-C','Inglés Instrumental III','Trayecto III','Técnico Superior Universitario',2,'PROYSOCI-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ORGINTSO-30202-C','Organización e Integración Sociopolítica','Trayecto III','Técnico Superior Universitario',2,'TEORPREI-20405-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ORGINTSO-30202-C','Organización e Integración Sociopolítica','Trayecto III','Técnico Superior Universitario',2,'TEORPRES-20405-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ORGINTSO-30202-C','Organización e Integración Sociopolítica','Trayecto III','Técnico Superior Universitario',2,'PSICDESA-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ORGINTSO-30202-C','Organización e Integración Sociopolítica','Trayecto III','Técnico Superior Universitario',2,'TEORCACT-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ORGINTSO-30202-C','Organización e Integración Sociopolítica','Trayecto III','Técnico Superior Universitario',2,'DISCREHA-20202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ORGINTSO-30202-C','Organización e Integración Sociopolítica','Trayecto III','Técnico Superior Universitario',2,'PACACUTR-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ORGINTSO-30202-C','Organización e Integración Sociopolítica','Trayecto III','Técnico Superior Universitario',2,'DITEASCO-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ORGINTSO-30202-C','Organización e Integración Sociopolítica','Trayecto III','Técnico Superior Universitario',2,'INGLINST-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ORGINTSO-30202-C','Organización e Integración Sociopolítica','Trayecto III','Técnico Superior Universitario',2,'PRACPROF-21200-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ORGINTSO-30202-C','Organización e Integración Sociopolítica','Trayecto III','Técnico Superior Universitario',2,'PROYSOCI-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ELECTIVA-30202-C','Electiva II','Trayecto III','Técnico Superior Universitario',2,'TEORPREI-20405-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ELECTIVA-30202-C','Electiva II','Trayecto III','Técnico Superior Universitario',2,'TEORPRES-20405-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ELECTIVA-30202-C','Electiva II','Trayecto III','Técnico Superior Universitario',2,'PSICDESA-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ELECTIVA-30202-C','Electiva II','Trayecto III','Técnico Superior Universitario',2,'TEORCACT-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ELECTIVA-30202-C','Electiva II','Trayecto III','Técnico Superior Universitario',2,'DISCREHA-20202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ELECTIVA-30202-C','Electiva II','Trayecto III','Técnico Superior Universitario',2,'PACACUTR-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ELECTIVA-30202-C','Electiva II','Trayecto III','Técnico Superior Universitario',2,'DITEASCO-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ELECTIVA-30202-C','Electiva II','Trayecto III','Técnico Superior Universitario',2,'INGLINST-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ELECTIVA-30202-C','Electiva II','Trayecto III','Técnico Superior Universitario',2,'PRACPROF-21200-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ELECTIVA-30202-C','Electiva II','Trayecto III','Técnico Superior Universitario',2,'PROYSOCI-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PRACPROF-31200-C','Práctica Profesional II','Trayecto III','Técnico Superior Universitario',0,'TEORPREI-20405-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PRACPROF-31200-C','Práctica Profesional II','Trayecto III','Técnico Superior Universitario',0,'TEORPRES-20405-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PRACPROF-31200-C','Práctica Profesional II','Trayecto III','Técnico Superior Universitario',0,'PSICDESA-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PRACPROF-31200-C','Práctica Profesional II','Trayecto III','Técnico Superior Universitario',0,'TEORCACT-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PRACPROF-31200-C','Práctica Profesional II','Trayecto III','Técnico Superior Universitario',0,'DISCREHA-20202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PRACPROF-31200-C','Práctica Profesional II','Trayecto III','Técnico Superior Universitario',0,'PACACUTR-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PRACPROF-31200-C','Práctica Profesional II','Trayecto III','Técnico Superior Universitario',0,'DITEASCO-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PRACPROF-31200-C','Práctica Profesional II','Trayecto III','Técnico Superior Universitario',0,'INGLINST-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PRACPROF-31200-C','Práctica Profesional II','Trayecto III','Técnico Superior Universitario',0,'PRACPROF-21200-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PRACPROF-31200-C','Práctica Profesional II','Trayecto III','Técnico Superior Universitario',0,'PROYSOCI-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','CASOCLIN-30304-C','Casos Clínicos I','Trayecto III','Técnico Superior Universitario',4,'TEORPREI-20405-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','CASOCLIN-30304-C','Casos Clínicos I','Trayecto III','Técnico Superior Universitario',4,'TEORPRES-20405-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','CASOCLIN-30304-C','Casos Clínicos I','Trayecto III','Técnico Superior Universitario',4,'PSICDESA-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','CASOCLIN-30304-C','Casos Clínicos I','Trayecto III','Técnico Superior Universitario',4,'TEORCACT-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','CASOCLIN-30304-C','Casos Clínicos I','Trayecto III','Técnico Superior Universitario',4,'DISCREHA-20202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','CASOCLIN-30304-C','Casos Clínicos I','Trayecto III','Técnico Superior Universitario',4,'PACACUTR-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','CASOCLIN-30304-C','Casos Clínicos I','Trayecto III','Técnico Superior Universitario',4,'DITEASCO-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','CASOCLIN-30304-C','Casos Clínicos I','Trayecto III','Técnico Superior Universitario',4,'INGLINST-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','CASOCLIN-30304-C','Casos Clínicos I','Trayecto III','Técnico Superior Universitario',4,'PRACPROF-21200-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','CASOCLIN-30304-C','Casos Clínicos I','Trayecto III','Técnico Superior Universitario',4,'PROYSOCI-20304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ADLAORPR-40506-C','Administración de Laboratorios de Órtesis y Prótesis','Trayecto IV','Licenciatura',6,'TEORPRES-30405-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ADLAORPR-40506-C','Administración de Laboratorios de Órtesis y Prótesis','Trayecto IV','Licenciatura',6,'PROCADMI-30202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ADLAORPR-40506-C','Administración de Laboratorios de Órtesis y Prótesis','Trayecto IV','Licenciatura',6,'PSAPAPDE-30202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ADLAORPR-40506-C','Administración de Laboratorios de Órtesis y Prótesis','Trayecto IV','Licenciatura',6,'ELEBASAP-30302-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ADLAORPR-40506-C','Administración de Laboratorios de Órtesis y Prótesis','Trayecto IV','Licenciatura',6,'INGLINST-30202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ADLAORPR-40506-C','Administración de Laboratorios de Órtesis y Prótesis','Trayecto IV','Licenciatura',6,'ORGINTSO-30202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ADLAORPR-40506-C','Administración de Laboratorios de Órtesis y Prótesis','Trayecto IV','Licenciatura',6,'ELECTIVA-30202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ADLAORPR-40506-C','Administración de Laboratorios de Órtesis y Prótesis','Trayecto IV','Licenciatura',6,'PRACPROF-31200-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ADLAORPR-40506-C','Administración de Laboratorios de Órtesis y Prótesis','Trayecto IV','Licenciatura',6,'CASOCLIN-30304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ELECTIVA-40405-C','Electiva III','Trayecto IV','Licenciatura',5,'TEORPRES-30405-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ELECTIVA-40405-C','Electiva III','Trayecto IV','Licenciatura',5,'PROCADMI-30202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ELECTIVA-40405-C','Electiva III','Trayecto IV','Licenciatura',5,'PSAPAPDE-30202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ELECTIVA-40405-C','Electiva III','Trayecto IV','Licenciatura',5,'ELEBASAP-30302-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ELECTIVA-40405-C','Electiva III','Trayecto IV','Licenciatura',5,'INGLINST-30202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ELECTIVA-40405-C','Electiva III','Trayecto IV','Licenciatura',5,'ORGINTSO-30202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ELECTIVA-40405-C','Electiva III','Trayecto IV','Licenciatura',5,'ELECTIVA-30202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ELECTIVA-40405-C','Electiva III','Trayecto IV','Licenciatura',5,'PRACPROF-31200-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ELECTIVA-40405-C','Electiva III','Trayecto IV','Licenciatura',5,'CASOCLIN-30304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','INTEAVOP-40708-C','Introducción a las Tecnologías Avanzadas en Órtesis y Prótesis','Trayecto IV','Licenciatura',8,'TEORPRES-30405-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','INTEAVOP-40708-C','Introducción a las Tecnologías Avanzadas en Órtesis y Prótesis','Trayecto IV','Licenciatura',8,'PROCADMI-30202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','INTEAVOP-40708-C','Introducción a las Tecnologías Avanzadas en Órtesis y Prótesis','Trayecto IV','Licenciatura',8,'PSAPAPDE-30202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','INTEAVOP-40708-C','Introducción a las Tecnologías Avanzadas en Órtesis y Prótesis','Trayecto IV','Licenciatura',8,'ELEBASAP-30302-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','INTEAVOP-40708-C','Introducción a las Tecnologías Avanzadas en Órtesis y Prótesis','Trayecto IV','Licenciatura',8,'INGLINST-30202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','INTEAVOP-40708-C','Introducción a las Tecnologías Avanzadas en Órtesis y Prótesis','Trayecto IV','Licenciatura',8,'ORGINTSO-30202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','INTEAVOP-40708-C','Introducción a las Tecnologías Avanzadas en Órtesis y Prótesis','Trayecto IV','Licenciatura',8,'ELECTIVA-30202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','INTEAVOP-40708-C','Introducción a las Tecnologías Avanzadas en Órtesis y Prótesis','Trayecto IV','Licenciatura',8,'PRACPROF-31200-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','INTEAVOP-40708-C','Introducción a las Tecnologías Avanzadas en Órtesis y Prótesis','Trayecto IV','Licenciatura',8,'CASOCLIN-30304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ORINSOAM-40607-C','Orientación e Integración Socio Ambiental','Trayecto IV','Licenciatura',7,'TEORPRES-30405-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ORINSOAM-40607-C','Orientación e Integración Socio Ambiental','Trayecto IV','Licenciatura',7,'PROCADMI-30202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ORINSOAM-40607-C','Orientación e Integración Socio Ambiental','Trayecto IV','Licenciatura',7,'PSAPAPDE-30202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ORINSOAM-40607-C','Orientación e Integración Socio Ambiental','Trayecto IV','Licenciatura',7,'ELEBASAP-30302-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ORINSOAM-40607-C','Orientación e Integración Socio Ambiental','Trayecto IV','Licenciatura',7,'INGLINST-30202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ORINSOAM-40607-C','Orientación e Integración Socio Ambiental','Trayecto IV','Licenciatura',7,'ORGINTSO-30202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ORINSOAM-40607-C','Orientación e Integración Socio Ambiental','Trayecto IV','Licenciatura',7,'ELECTIVA-30202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ORINSOAM-40607-C','Orientación e Integración Socio Ambiental','Trayecto IV','Licenciatura',7,'PRACPROF-31200-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ORINSOAM-40607-C','Orientación e Integración Socio Ambiental','Trayecto IV','Licenciatura',7,'CASOCLIN-30304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ORJUDPDM-40708-C','Organización de Juegos Deportivos para Personas con Discapacidad Musculoesquelética','Trayecto IV','Licenciatura',8,'TEORPRES-30405-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ORJUDPDM-40708-C','Organización de Juegos Deportivos para Personas con Discapacidad Musculoesquelética','Trayecto IV','Licenciatura',8,'PROCADMI-30202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ORJUDPDM-40708-C','Organización de Juegos Deportivos para Personas con Discapacidad Musculoesquelética','Trayecto IV','Licenciatura',8,'PSAPAPDE-30202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ORJUDPDM-40708-C','Organización de Juegos Deportivos para Personas con Discapacidad Musculoesquelética','Trayecto IV','Licenciatura',8,'ELEBASAP-30302-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ORJUDPDM-40708-C','Organización de Juegos Deportivos para Personas con Discapacidad Musculoesquelética','Trayecto IV','Licenciatura',8,'INGLINST-30202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ORJUDPDM-40708-C','Organización de Juegos Deportivos para Personas con Discapacidad Musculoesquelética','Trayecto IV','Licenciatura',8,'ORGINTSO-30202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ORJUDPDM-40708-C','Organización de Juegos Deportivos para Personas con Discapacidad Musculoesquelética','Trayecto IV','Licenciatura',8,'ELECTIVA-30202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ORJUDPDM-40708-C','Organización de Juegos Deportivos para Personas con Discapacidad Musculoesquelética','Trayecto IV','Licenciatura',8,'PRACPROF-31200-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ORJUDPDM-40708-C','Organización de Juegos Deportivos para Personas con Discapacidad Musculoesquelética','Trayecto IV','Licenciatura',8,'CASOCLIN-30304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ELECTIVA-40405-C','Electiva IV','Trayecto IV','Licenciatura',5,'TEORPRES-30405-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ELECTIVA-40405-C','Electiva IV','Trayecto IV','Licenciatura',5,'PROCADMI-30202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ELECTIVA-40405-C','Electiva IV','Trayecto IV','Licenciatura',5,'PSAPAPDE-30202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ELECTIVA-40405-C','Electiva IV','Trayecto IV','Licenciatura',5,'ELEBASAP-30302-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ELECTIVA-40405-C','Electiva IV','Trayecto IV','Licenciatura',5,'INGLINST-30202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ELECTIVA-40405-C','Electiva IV','Trayecto IV','Licenciatura',5,'ORGINTSO-30202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ELECTIVA-40405-C','Electiva IV','Trayecto IV','Licenciatura',5,'ELECTIVA-30202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ELECTIVA-40405-C','Electiva IV','Trayecto IV','Licenciatura',5,'PRACPROF-31200-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','ELECTIVA-40405-C','Electiva IV','Trayecto IV','Licenciatura',5,'CASOCLIN-30304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','CASOCLIN-40708-C','Casos Clínicos II','Trayecto IV','Licenciatura',8,'TEORPRES-30405-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','CASOCLIN-40708-C','Casos Clínicos II','Trayecto IV','Licenciatura',8,'PROCADMI-30202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','CASOCLIN-40708-C','Casos Clínicos II','Trayecto IV','Licenciatura',8,'PSAPAPDE-30202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','CASOCLIN-40708-C','Casos Clínicos II','Trayecto IV','Licenciatura',8,'ELEBASAP-30302-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','CASOCLIN-40708-C','Casos Clínicos II','Trayecto IV','Licenciatura',8,'INGLINST-30202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','CASOCLIN-40708-C','Casos Clínicos II','Trayecto IV','Licenciatura',8,'ORGINTSO-30202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','CASOCLIN-40708-C','Casos Clínicos II','Trayecto IV','Licenciatura',8,'ELECTIVA-30202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','CASOCLIN-40708-C','Casos Clínicos II','Trayecto IV','Licenciatura',8,'PRACPROF-31200-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','CASOCLIN-40708-C','Casos Clínicos II','Trayecto IV','Licenciatura',8,'CASOCLIN-30304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PRACPROF-41214-C','Práctica Profesional III','Trayecto IV','Licenciatura',14,'TEORPRES-30405-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PRACPROF-41214-C','Práctica Profesional III','Trayecto IV','Licenciatura',14,'PROCADMI-30202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PRACPROF-41214-C','Práctica Profesional III','Trayecto IV','Licenciatura',14,'PSAPAPDE-30202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PRACPROF-41214-C','Práctica Profesional III','Trayecto IV','Licenciatura',14,'ELEBASAP-30302-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PRACPROF-41214-C','Práctica Profesional III','Trayecto IV','Licenciatura',14,'INGLINST-30202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PRACPROF-41214-C','Práctica Profesional III','Trayecto IV','Licenciatura',14,'ORGINTSO-30202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PRACPROF-41214-C','Práctica Profesional III','Trayecto IV','Licenciatura',14,'ELECTIVA-30202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PRACPROF-41214-C','Práctica Profesional III','Trayecto IV','Licenciatura',14,'PRACPROF-31200-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PRACPROF-41214-C','Práctica Profesional III','Trayecto IV','Licenciatura',14,'CASOCLIN-30304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PROYSOCI-40910-C','Proyecto Sociointegrador II','Trayecto IV','Licenciatura',10,'TEORPRES-30405-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PROYSOCI-40910-C','Proyecto Sociointegrador II','Trayecto IV','Licenciatura',10,'PROCADMI-30202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PROYSOCI-40910-C','Proyecto Sociointegrador II','Trayecto IV','Licenciatura',10,'PSAPAPDE-30202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PROYSOCI-40910-C','Proyecto Sociointegrador II','Trayecto IV','Licenciatura',10,'ELEBASAP-30302-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PROYSOCI-40910-C','Proyecto Sociointegrador II','Trayecto IV','Licenciatura',10,'INGLINST-30202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PROYSOCI-40910-C','Proyecto Sociointegrador II','Trayecto IV','Licenciatura',10,'ORGINTSO-30202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PROYSOCI-40910-C','Proyecto Sociointegrador II','Trayecto IV','Licenciatura',10,'ELECTIVA-30202-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PROYSOCI-40910-C','Proyecto Sociointegrador II','Trayecto IV','Licenciatura',10,'PRACPROF-31200-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Órtesis y Prótesis','PROYSOCI-40910-C','Proyecto Sociointegrador II','Trayecto IV','Licenciatura',10,'CASOCLIN-30304-C',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','FISIAPLI-00300-D','Física Aplicada','Trayecto Inicial','',0,'',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','QUIMAPLI-00300-D','Química Aplicada','Trayecto Inicial','',0,'',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','MATEINST-00300-D','Matemática Instrumental','Trayecto Inicial','',0,'',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','SALPUBTO-00500-D','Salud Pública y Terapia Ocupacional','Trayecto Inicial','',0,'',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRNANUCI-00500-D','Proyecto Nacional y Nueva Ciudadanía','Trayecto Inicial','',0,'',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','LECTCOMP-00500-D','Lectura y Comprensión','Trayecto Inicial','',0,'',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','ESFUCUHU-10607-D','Estructuras y Funciones del Cuerpo Humano','Trayecto I','',7,'FISIAPLI-00300-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','ESFUCUHU-10607-D','Estructuras y Funciones del Cuerpo Humano','Trayecto I','',7,'QUIMAPLI-00300-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','ESFUCUHU-10607-D','Estructuras y Funciones del Cuerpo Humano','Trayecto I','',7,'MATEINST-00300-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','ESFUCUHU-10607-D','Estructuras y Funciones del Cuerpo Humano','Trayecto I','',7,'SALPUBTO-00500-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','ESFUCUHU-10607-D','Estructuras y Funciones del Cuerpo Humano','Trayecto I','',7,'PRNANUCI-00500-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','ESFUCUHU-10607-D','Estructuras y Funciones del Cuerpo Humano','Trayecto I','',7,'LECTCOMP-00500-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','FUTETETO-10607-D','Fundamentos Teóricos y Tecnológicos de Terapia Ocupacional','Trayecto I','',7,'FISIAPLI-00300-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','FUTETETO-10607-D','Fundamentos Teóricos y Tecnológicos de Terapia Ocupacional','Trayecto I','',7,'QUIMAPLI-00300-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','FUTETETO-10607-D','Fundamentos Teóricos y Tecnológicos de Terapia Ocupacional','Trayecto I','',7,'MATEINST-00300-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','FUTETETO-10607-D','Fundamentos Teóricos y Tecnológicos de Terapia Ocupacional','Trayecto I','',7,'SALPUBTO-00500-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','FUTETETO-10607-D','Fundamentos Teóricos y Tecnológicos de Terapia Ocupacional','Trayecto I','',7,'PRNANUCI-00500-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','FUTETETO-10607-D','Fundamentos Teóricos y Tecnológicos de Terapia Ocupacional','Trayecto I','',7,'LECTCOMP-00500-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','ORIINTSO-10506-D','Orientación e Integración Sociopolítica','Trayecto I','',6,'FISIAPLI-00300-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','ORIINTSO-10506-D','Orientación e Integración Sociopolítica','Trayecto I','',6,'QUIMAPLI-00300-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','ORIINTSO-10506-D','Orientación e Integración Sociopolítica','Trayecto I','',6,'MATEINST-00300-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','ORIINTSO-10506-D','Orientación e Integración Sociopolítica','Trayecto I','',6,'SALPUBTO-00500-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','ORIINTSO-10506-D','Orientación e Integración Sociopolítica','Trayecto I','',6,'PRNANUCI-00500-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','ORIINTSO-10506-D','Orientación e Integración Sociopolítica','Trayecto I','',6,'LECTCOMP-00500-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','SAPUEPDI-10506-D','Salud Pública, Epidemiología y Discapacidad','Trayecto I','',6,'FISIAPLI-00300-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','SAPUEPDI-10506-D','Salud Pública, Epidemiología y Discapacidad','Trayecto I','',6,'QUIMAPLI-00300-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','SAPUEPDI-10506-D','Salud Pública, Epidemiología y Discapacidad','Trayecto I','',6,'MATEINST-00300-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','SAPUEPDI-10506-D','Salud Pública, Epidemiología y Discapacidad','Trayecto I','',6,'SALPUBTO-00500-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','SAPUEPDI-10506-D','Salud Pública, Epidemiología y Discapacidad','Trayecto I','',6,'PRNANUCI-00500-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','SAPUEPDI-10506-D','Salud Pública, Epidemiología y Discapacidad','Trayecto I','',6,'LECTCOMP-00500-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRABCPOH-10506-D','Proyecto: Abordaje Comunitario para la Promoción de la Ocupación Humana','Trayecto I','',6,'FISIAPLI-00300-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRABCPOH-10506-D','Proyecto: Abordaje Comunitario para la Promoción de la Ocupación Humana','Trayecto I','',6,'QUIMAPLI-00300-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRABCPOH-10506-D','Proyecto: Abordaje Comunitario para la Promoción de la Ocupación Humana','Trayecto I','',6,'MATEINST-00300-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRABCPOH-10506-D','Proyecto: Abordaje Comunitario para la Promoción de la Ocupación Humana','Trayecto I','',6,'SALPUBTO-00500-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRABCPOH-10506-D','Proyecto: Abordaje Comunitario para la Promoción de la Ocupación Humana','Trayecto I','',6,'PRNANUCI-00500-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRABCPOH-10506-D','Proyecto: Abordaje Comunitario para la Promoción de la Ocupación Humana','Trayecto I','',6,'LECTCOMP-00500-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','ALESFCHI-20607-D','Alteraciones en las Estructuras y Funciones del Cuerpo Humano y su Impacto en la Ocupación','Trayecto II','',7,'ESFUCUHU-10607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','ALESFCHI-20607-D','Alteraciones en las Estructuras y Funciones del Cuerpo Humano y su Impacto en la Ocupación','Trayecto II','',7,'FUTETETO-10607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','ALESFCHI-20607-D','Alteraciones en las Estructuras y Funciones del Cuerpo Humano y su Impacto en la Ocupación','Trayecto II','',7,'ORIINTSO-10506-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','ALESFCHI-20607-D','Alteraciones en las Estructuras y Funciones del Cuerpo Humano y su Impacto en la Ocupación','Trayecto II','',7,'SAPUEPDI-10506-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','ALESFCHI-20607-D','Alteraciones en las Estructuras y Funciones del Cuerpo Humano y su Impacto en la Ocupación','Trayecto II','',7,'PRABCPOH-10506-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','TEINTEOC-20607-D','Tecnologías de Intervención en Terapia Ocupacional ','Trayecto II','',7,'ESFUCUHU-10607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','TEINTEOC-20607-D','Tecnologías de Intervención en Terapia Ocupacional ','Trayecto II','',7,'FUTETETO-10607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','TEINTEOC-20607-D','Tecnologías de Intervención en Terapia Ocupacional ','Trayecto II','',7,'ORIINTSO-10506-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','TEINTEOC-20607-D','Tecnologías de Intervención en Terapia Ocupacional ','Trayecto II','',7,'SAPUEPDI-10506-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','TEINTEOC-20607-D','Tecnologías de Intervención en Terapia Ocupacional ','Trayecto II','',7,'PRABCPOH-10506-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','ORIINTPS-20607-D','Orientación e Integración Psicosocial','Trayecto II','',7,'ESFUCUHU-10607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','ORIINTPS-20607-D','Orientación e Integración Psicosocial','Trayecto II','',7,'FUTETETO-10607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','ORIINTPS-20607-D','Orientación e Integración Psicosocial','Trayecto II','',7,'ORIINTSO-10506-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','ORIINTPS-20607-D','Orientación e Integración Psicosocial','Trayecto II','',7,'SAPUEPDI-10506-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','ORIINTPS-20607-D','Orientación e Integración Psicosocial','Trayecto II','',7,'PRABCPOH-10506-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','SAPUEPDI-20506-D','Salud Públicas, Epidemiología y Discapacidad','Trayecto II','',6,'ESFUCUHU-10607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','SAPUEPDI-20506-D','Salud Públicas, Epidemiología y Discapacidad','Trayecto II','',6,'FUTETETO-10607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','SAPUEPDI-20506-D','Salud Públicas, Epidemiología y Discapacidad','Trayecto II','',6,'ORIINTSO-10506-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','SAPUEPDI-20506-D','Salud Públicas, Epidemiología y Discapacidad','Trayecto II','',6,'SAPUEPDI-10506-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','SAPUEPDI-20506-D','Salud Públicas, Epidemiología y Discapacidad','Trayecto II','',6,'PRABCPOH-10506-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRACPROF-23006-D','Prácticas Profesionales I','Trayecto II','',6,'ESFUCUHU-10607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRACPROF-23006-D','Prácticas Profesionales I','Trayecto II','',6,'FUTETETO-10607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRACPROF-23006-D','Prácticas Profesionales I','Trayecto II','',6,'ORIINTSO-10506-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRACPROF-23006-D','Prácticas Profesionales I','Trayecto II','',6,'SAPUEPDI-10506-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRACPROF-23006-D','Prácticas Profesionales I','Trayecto II','',6,'PRABCPOH-10506-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRINDIMM-20506-D','Proyecto: Intervención en las Disfunciones Motoras y Mentales','Trayecto II','',6,'ESFUCUHU-10607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRINDIMM-20506-D','Proyecto: Intervención en las Disfunciones Motoras y Mentales','Trayecto II','',6,'FUTETETO-10607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRINDIMM-20506-D','Proyecto: Intervención en las Disfunciones Motoras y Mentales','Trayecto II','',6,'ORIINTSO-10506-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRINDIMM-20506-D','Proyecto: Intervención en las Disfunciones Motoras y Mentales','Trayecto II','',6,'SAPUEPDI-10506-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRINDIMM-20506-D','Proyecto: Intervención en las Disfunciones Motoras y Mentales','Trayecto II','',6,'PRABCPOH-10506-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','ALESFCHI-30607-D','Alteraciones en las Estructuras y Funciones del Cuerpo Humano y su Impacto en la Ocupación','Trayecto III','Técnico Superior Universitario',7,'ALESFCHI-20607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','ALESFCHI-30607-D','Alteraciones en las Estructuras y Funciones del Cuerpo Humano y su Impacto en la Ocupación','Trayecto III','Técnico Superior Universitario',7,'TEINTEOC-20607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','ALESFCHI-30607-D','Alteraciones en las Estructuras y Funciones del Cuerpo Humano y su Impacto en la Ocupación','Trayecto III','Técnico Superior Universitario',7,'ORIINTPS-20607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','ALESFCHI-30607-D','Alteraciones en las Estructuras y Funciones del Cuerpo Humano y su Impacto en la Ocupación','Trayecto III','Técnico Superior Universitario',7,'SAPUEPDI-20506-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','ALESFCHI-30607-D','Alteraciones en las Estructuras y Funciones del Cuerpo Humano y su Impacto en la Ocupación','Trayecto III','Técnico Superior Universitario',7,'PRACPROF-23006-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','ALESFCHI-30607-D','Alteraciones en las Estructuras y Funciones del Cuerpo Humano y su Impacto en la Ocupación','Trayecto III','Técnico Superior Universitario',7,'PRINDIMM-20506-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','TEINESTO-30607-D','Técnicas de Intervención Especializada en Terapia Ocupacional','Trayecto III','Técnico Superior Universitario',7,'ALESFCHI-20607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','TEINESTO-30607-D','Técnicas de Intervención Especializada en Terapia Ocupacional','Trayecto III','Técnico Superior Universitario',7,'TEINTEOC-20607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','TEINESTO-30607-D','Técnicas de Intervención Especializada en Terapia Ocupacional','Trayecto III','Técnico Superior Universitario',7,'ORIINTPS-20607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','TEINESTO-30607-D','Técnicas de Intervención Especializada en Terapia Ocupacional','Trayecto III','Técnico Superior Universitario',7,'SAPUEPDI-20506-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','TEINESTO-30607-D','Técnicas de Intervención Especializada en Terapia Ocupacional','Trayecto III','Técnico Superior Universitario',7,'PRACPROF-23006-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','TEINESTO-30607-D','Técnicas de Intervención Especializada en Terapia Ocupacional','Trayecto III','Técnico Superior Universitario',7,'PRINDIMM-20506-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','ORIINTSO-30607-D','Orientación e Integración Psicosocial','Trayecto III','Técnico Superior Universitario',7,'ALESFCHI-20607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','ORIINTSO-30607-D','Orientación e Integración Psicosocial','Trayecto III','Técnico Superior Universitario',7,'TEINTEOC-20607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','ORIINTSO-30607-D','Orientación e Integración Psicosocial','Trayecto III','Técnico Superior Universitario',7,'ORIINTPS-20607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','ORIINTSO-30607-D','Orientación e Integración Psicosocial','Trayecto III','Técnico Superior Universitario',7,'SAPUEPDI-20506-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','ORIINTSO-30607-D','Orientación e Integración Psicosocial','Trayecto III','Técnico Superior Universitario',7,'PRACPROF-23006-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','ORIINTSO-30607-D','Orientación e Integración Psicosocial','Trayecto III','Técnico Superior Universitario',7,'PRINDIMM-20506-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRINTEOC-30506-D','Procedimientos de Intervención de Terapia Ocupacional','Trayecto III','Técnico Superior Universitario',6,'ALESFCHI-20607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRINTEOC-30506-D','Procedimientos de Intervención de Terapia Ocupacional','Trayecto III','Técnico Superior Universitario',6,'TEINTEOC-20607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRINTEOC-30506-D','Procedimientos de Intervención de Terapia Ocupacional','Trayecto III','Técnico Superior Universitario',6,'ORIINTPS-20607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRINTEOC-30506-D','Procedimientos de Intervención de Terapia Ocupacional','Trayecto III','Técnico Superior Universitario',6,'SAPUEPDI-20506-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRINTEOC-30506-D','Procedimientos de Intervención de Terapia Ocupacional','Trayecto III','Técnico Superior Universitario',6,'PRACPROF-23006-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRINTEOC-30506-D','Procedimientos de Intervención de Terapia Ocupacional','Trayecto III','Técnico Superior Universitario',6,'PRINDIMM-20506-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRACPROF-33006-D','Prácticas Profesionales II','Trayecto III','Técnico Superior Universitario',6,'ALESFCHI-20607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRACPROF-33006-D','Prácticas Profesionales II','Trayecto III','Técnico Superior Universitario',6,'TEINTEOC-20607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRACPROF-33006-D','Prácticas Profesionales II','Trayecto III','Técnico Superior Universitario',6,'ORIINTPS-20607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRACPROF-33006-D','Prácticas Profesionales II','Trayecto III','Técnico Superior Universitario',6,'SAPUEPDI-20506-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRACPROF-33006-D','Prácticas Profesionales II','Trayecto III','Técnico Superior Universitario',6,'PRACPROF-23006-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRACPROF-33006-D','Prácticas Profesionales II','Trayecto III','Técnico Superior Universitario',6,'PRINDIMM-20506-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRATINDO-30506-D','Proyecto: Atención Integral al Desempeño Ocupacional','Trayecto III','Técnico Superior Universitario',6,'ALESFCHI-20607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRATINDO-30506-D','Proyecto: Atención Integral al Desempeño Ocupacional','Trayecto III','Técnico Superior Universitario',6,'TEINTEOC-20607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRATINDO-30506-D','Proyecto: Atención Integral al Desempeño Ocupacional','Trayecto III','Técnico Superior Universitario',6,'ORIINTPS-20607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRATINDO-30506-D','Proyecto: Atención Integral al Desempeño Ocupacional','Trayecto III','Técnico Superior Universitario',6,'SAPUEPDI-20506-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRATINDO-30506-D','Proyecto: Atención Integral al Desempeño Ocupacional','Trayecto III','Técnico Superior Universitario',6,'PRACPROF-23006-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRATINDO-30506-D','Proyecto: Atención Integral al Desempeño Ocupacional','Trayecto III','Técnico Superior Universitario',6,'PRINDIMM-20506-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','DICUHUIO-40809-D','Disfunciones del Cuerpo Humano y su Impacto en la Ocupación','Trayecto IV','Licenciatura',9,'ALESFCHI-30607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','DICUHUIO-40809-D','Disfunciones del Cuerpo Humano y su Impacto en la Ocupación','Trayecto IV','Licenciatura',9,'TEINESTO-30607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','DICUHUIO-40809-D','Disfunciones del Cuerpo Humano y su Impacto en la Ocupación','Trayecto IV','Licenciatura',9,'ORIINTSO-30607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','DICUHUIO-40809-D','Disfunciones del Cuerpo Humano y su Impacto en la Ocupación','Trayecto IV','Licenciatura',9,'PRINTEOC-30506-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','DICUHUIO-40809-D','Disfunciones del Cuerpo Humano y su Impacto en la Ocupación','Trayecto IV','Licenciatura',9,'PRACPROF-33006-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','DICUHUIO-40809-D','Disfunciones del Cuerpo Humano y su Impacto en la Ocupación','Trayecto IV','Licenciatura',9,'PRATINDO-30506-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','TETEESTO-40809-D','Técnicas y Tecnologías Especializadas en Terapia Ocupacional','Trayecto IV','Licenciatura',9,'ALESFCHI-30607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','TETEESTO-40809-D','Técnicas y Tecnologías Especializadas en Terapia Ocupacional','Trayecto IV','Licenciatura',9,'TEINESTO-30607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','TETEESTO-40809-D','Técnicas y Tecnologías Especializadas en Terapia Ocupacional','Trayecto IV','Licenciatura',9,'ORIINTSO-30607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','TETEESTO-40809-D','Técnicas y Tecnologías Especializadas en Terapia Ocupacional','Trayecto IV','Licenciatura',9,'PRINTEOC-30506-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','TETEESTO-40809-D','Técnicas y Tecnologías Especializadas en Terapia Ocupacional','Trayecto IV','Licenciatura',9,'PRACPROF-33006-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','TETEESTO-40809-D','Técnicas y Tecnologías Especializadas en Terapia Ocupacional','Trayecto IV','Licenciatura',9,'PRATINDO-30506-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','ORINSOAM-40809-D','Orientación e Intergración Sociopolítica y Ambiental','Trayecto IV','Licenciatura',9,'ALESFCHI-30607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','ORINSOAM-40809-D','Orientación e Intergración Sociopolítica y Ambiental','Trayecto IV','Licenciatura',9,'TEINESTO-30607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','ORINSOAM-40809-D','Orientación e Intergración Sociopolítica y Ambiental','Trayecto IV','Licenciatura',9,'ORIINTSO-30607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','ORINSOAM-40809-D','Orientación e Intergración Sociopolítica y Ambiental','Trayecto IV','Licenciatura',9,'PRINTEOC-30506-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','ORINSOAM-40809-D','Orientación e Intergración Sociopolítica y Ambiental','Trayecto IV','Licenciatura',9,'PRACPROF-33006-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','ORINSOAM-40809-D','Orientación e Intergración Sociopolítica y Ambiental','Trayecto IV','Licenciatura',9,'PRATINDO-30506-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','SAPUEPDI-40809-D','Salud Pública, Epidemiología y Discapacidad','Trayecto IV','Licenciatura',9,'ALESFCHI-30607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','SAPUEPDI-40809-D','Salud Pública, Epidemiología y Discapacidad','Trayecto IV','Licenciatura',9,'TEINESTO-30607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','SAPUEPDI-40809-D','Salud Pública, Epidemiología y Discapacidad','Trayecto IV','Licenciatura',9,'ORIINTSO-30607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','SAPUEPDI-40809-D','Salud Pública, Epidemiología y Discapacidad','Trayecto IV','Licenciatura',9,'PRINTEOC-30506-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','SAPUEPDI-40809-D','Salud Pública, Epidemiología y Discapacidad','Trayecto IV','Licenciatura',9,'PRACPROF-33006-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','SAPUEPDI-40809-D','Salud Pública, Epidemiología y Discapacidad','Trayecto IV','Licenciatura',9,'PRATINDO-30506-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','ELECTIVA-40304-D','Electiva','Trayecto IV','Licenciatura',4,'ALESFCHI-30607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','ELECTIVA-40304-D','Electiva','Trayecto IV','Licenciatura',4,'TEINESTO-30607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','ELECTIVA-40304-D','Electiva','Trayecto IV','Licenciatura',4,'ORIINTSO-30607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','ELECTIVA-40304-D','Electiva','Trayecto IV','Licenciatura',4,'PRINTEOC-30506-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','ELECTIVA-40304-D','Electiva','Trayecto IV','Licenciatura',4,'PRACPROF-33006-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','ELECTIVA-40304-D','Electiva','Trayecto IV','Licenciatura',4,'PRATINDO-30506-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRACPROF-43021-D','Prácticas Profesionales III','Trayecto IV','Licenciatura',21,'ALESFCHI-30607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRACPROF-43021-D','Prácticas Profesionales III','Trayecto IV','Licenciatura',21,'TEINESTO-30607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRACPROF-43021-D','Prácticas Profesionales III','Trayecto IV','Licenciatura',21,'ORIINTSO-30607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRACPROF-43021-D','Prácticas Profesionales III','Trayecto IV','Licenciatura',21,'PRINTEOC-30506-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRACPROF-43021-D','Prácticas Profesionales III','Trayecto IV','Licenciatura',21,'PRACPROF-33006-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRACPROF-43021-D','Prácticas Profesionales III','Trayecto IV','Licenciatura',21,'PRATINDO-30506-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRGEESTO-40809-D','Proyecto: Gestión Especializada en Terapia Ocupacional','Trayecto IV','Licenciatura',9,'ALESFCHI-30607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRGEESTO-40809-D','Proyecto: Gestión Especializada en Terapia Ocupacional','Trayecto IV','Licenciatura',9,'TEINESTO-30607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRGEESTO-40809-D','Proyecto: Gestión Especializada en Terapia Ocupacional','Trayecto IV','Licenciatura',9,'ORIINTSO-30607-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRGEESTO-40809-D','Proyecto: Gestión Especializada en Terapia Ocupacional','Trayecto IV','Licenciatura',9,'PRINTEOC-30506-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRGEESTO-40809-D','Proyecto: Gestión Especializada en Terapia Ocupacional','Trayecto IV','Licenciatura',9,'PRACPROF-33006-D',51,22,'2016-11-24');
CALL insert_plan_estudio('Terapia Ocupacional','PRGEESTO-40809-D','Proyecto: Gestión Especializada en Terapia Ocupacional','Trayecto IV','Licenciatura',9,'PRATINDO-30506-D',51,22,'2016-11-24');
# Módulos por materia
CALL insert_plan_estudio_modulo('Fisioterapia','FISIAPLI-00300-A','Física Aplicada','Trayecto Inicial','Física Aplicada',3,36,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','QUIMAPLI-00300-A','Química Aplicada','Trayecto Inicial','Química Aplicada',3,36,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','MATEINST-00300-A','Matemática Instrumental','Trayecto Inicial','Matemática Instrumental',3,36,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','SALPUBFI-00500-A','Salud Pública y Fisioterapia','Trayecto Inicial','Salud Pública y Fisioterapia',5,60,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','PRNANUCI-00500-A','Proyecto Nacional y Nueva Ciudadanía','Trayecto Inicial','Proyecto Nacional y Nueva Ciudadanía',5,60,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','LECTCOMP-00500-A','Lectura y Comprensión','Trayecto Inicial','Lectura y Comprensión',5,60,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','ESFUCUHU-10607-A','Estructuras y Funciones de Cuerpo Humano','Trayecto I','Fisiología',72,2,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','ESFUCUHU-10607-A','Estructuras y Funciones de Cuerpo Humano','Trayecto I','Sistemas Óseo, Articular y Sistema Muscular',54,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','ESFUCUHU-10607-A','Estructuras y Funciones de Cuerpo Humano','Trayecto I','Sistema Nervioso',54,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','ESFUCUHU-10607-A','Estructuras y Funciones de Cuerpo Humano','Trayecto I','Análisis del Movimiento Humano',75,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','INTETEFI-10607-A','Introducción a las Técnicas y Tecnologías en Fisioterapia','Trayecto I','Fundamentos de Fisioterapia',21,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','INTETEFI-10607-A','Introducción a las Técnicas y Tecnologías en Fisioterapia','Trayecto I','Principios Físicos Aplicados a la Fisioterapia',24,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','INTETEFI-10607-A','Introducción a las Técnicas y Tecnologías en Fisioterapia','Trayecto I','Medios Físicos I',63,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','INTETEFI-10607-A','Introducción a las Técnicas y Tecnologías en Fisioterapia','Trayecto I','Valoración y Diagnóstico en Fisioterapia I',108,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','CIUACCSO-10506-A','Ciudadanía y Acción Social','Trayecto I','Diversidad Cultural y Nueva Ciudadanía',54,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','CIUACCSO-10506-A','Ciudadanía y Acción Social','Trayecto I','Herramientas Tecnológicas I',36,2,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','CIUACCSO-10506-A','Ciudadanía y Acción Social','Trayecto I','Atención Comunitaria para Personas con Discapacidad I',90,5,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','BASCONCI-10506-A','Bases del Conocimiento Científico','Trayecto I','Bases del Conocimiento Científico I',46,2,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','BASCONCI-10506-A','Bases del Conocimiento Científico','Trayecto I','Bases del Conocimiento Científico II',26,2,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','BASCONCI-10506-A','Bases del Conocimiento Científico','Trayecto I','Valoración de la Salud Pública',108,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','PRINABCF-10506-A','Proyecto: Introducción al Abordaje Comunitario en Fisioterapia','Trayecto I','Proyecto: Introducción al Abordaje Comunitario en Fisioterapia',180,5,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','CAESDICH-20607-A','Cambios en las Estructuras y Disfunciones del Cuerpo Humano','Trayecto II','Alteraciones de los Sistemas Óseo, Articular y Muscular',120,5,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','CAESDICH-20607-A','Cambios en las Estructuras y Disfunciones del Cuerpo Humano','Trayecto II','Alteraciones del Sistema Nervioso I',96,4,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','TECTECFI-20607-A','Técnicas y Tecnologías de Fisioterapia','Trayecto II','Valoración y Diagnóstico en Fisioterapia II',72,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','TECTECFI-20607-A','Técnicas y Tecnologías de Fisioterapia','Trayecto II','Técnicas de Intervención en Fisioterapia I',42,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','TECTECFI-20607-A','Técnicas y Tecnologías de Fisioterapia','Trayecto II','Técnicas de Intervención en Fisioterapia II',60,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','TECTECFI-20607-A','Técnicas y Tecnologías de Fisioterapia','Trayecto II','Medios Físicos II',42,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','ACSOSADI-20607-A','Acción Social, Salud y Discapacidad','Trayecto II','Atención Comunitaria para Personas con Discapacidad II',72,6,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','ACSOSADI-20607-A','Acción Social, Salud y Discapacidad','Trayecto II','Herramientas Tecnológicas II',36,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','ACSOSADI-20607-A','Acción Social, Salud y Discapacidad','Trayecto II','Inglés Instrumental',36,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','ACSOSADI-20607-A','Acción Social, Salud y Discapacidad','Trayecto II','Recreación, Educación Física y Deporte en Fisioterapia',72,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','FUESEPSA-20607-A','Fundamentos Estadísticos y Epidemiológicos en Salud','Trayecto II','Fundamentos Estadísticos y Epidemiológicos en Salud',216,6,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','PRACPROF-23006-A','Prácticas Profesionales I','Trayecto II','Prácticas Profesionales I',180,5,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','PRPRFHPD-20506-A','Proyecto: Promoción del Funcionamiento Humano y Prevención de la Discapacidad','Trayecto II','Período I',180,30,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','PRPRFHPD-20506-A','Proyecto: Promoción del Funcionamiento Humano y Prevención de la Discapacidad','Trayecto II','Período II',180,30,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','ESFUALCH-30607-A','Estructuras, Funciones y Alteraciones del Cuerpo Humano','Trayecto III','Alteraciones del Sistema Nervioso II',72,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','ESFUALCH-30607-A','Estructuras, Funciones y Alteraciones del Cuerpo Humano','Trayecto III','Sistema Cardiovascular y Respiratorio',54,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','ESFUALCH-30607-A','Estructuras, Funciones y Alteraciones del Cuerpo Humano','Trayecto III','Alteraciones Cardiorespiratorias',48,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','ESFUALCH-30607-A','Estructuras, Funciones y Alteraciones del Cuerpo Humano','Trayecto III','Fisiología del Ejercicio',42,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','TETEINFI-30607-A','Técnicas y Tecnologías Integrales en Fisioterapia','Trayecto III','Técnicas de Intervención en Fisioterapia III',56,4,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','TETEINFI-30607-A','Técnicas y Tecnologías Integrales en Fisioterapia','Trayecto III','Técnicas de Intervención en Fisioterapia IV',56,4,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','TETEINFI-30607-A','Técnicas y Tecnologías Integrales en Fisioterapia','Trayecto III','Valoración y Diagnóstico en Fisioterapia III',56,4,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','TETEINFI-30607-A','Técnicas y Tecnologías Integrales en Fisioterapia','Trayecto III','Técnicas de Intervención en Fisioterapia V',48,4,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','SEMCLIFI-30506-A','Semiología Clínica para Fisioterapeutas','Trayecto III','Semiología Clínica para Fisioterapeutas',180,9,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','ORPOINPS-30506-A','Orientación Política e Integración Psicosocial','Trayecto III','Políticas Públicas en Salud y Marco Normativo en el Área de la Discapacidad',36,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','ORPOINPS-30506-A','Orientación Política e Integración Psicosocial','Trayecto III','Psicología del Desarrollo Humano',108,6,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','ORPOINPS-30506-A','Orientación Política e Integración Psicosocial','Trayecto III','Psicología Social',36,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','PRACPROF-33006-A','Prácticas Profesionales II','Trayecto III','Período I',180,30,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','PRACPROF-33006-A','Prácticas Profesionales II','Trayecto III','Período II',180,30,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','PRATINPD-30506-A','Proyecto: Atención Integral a Personas con Discapacidad','Trayecto III','Proyecto: Atención Integral a Personas con Discapacidad',180,5,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','ESFUDACH-40607-A','Estructuras, Funciones y Diagnóstico de las Alteraciones del Cuerpo Humano','Trayecto IV','Diagnóstico en Fisioterapia',108,6,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','ESFUDACH-40607-A','Estructuras, Funciones y Diagnóstico de las Alteraciones del Cuerpo Humano','Trayecto IV','Sistema Tegumentario y Linfático',36,6,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','ESFUDACH-40607-A','Estructuras, Funciones y Diagnóstico de las Alteraciones del Cuerpo Humano','Trayecto IV','Esfera Urogenital',36,6,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','ESFUDACH-40607-A','Estructuras, Funciones y Diagnóstico de las Alteraciones del Cuerpo Humano','Trayecto IV','Sistema Estomatognático',36,6,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','TETEESFI-40607-A','Técnicas y Tecnologías Especializadas de Fisioterapia','Trayecto IV','Introducción a la Terapia Manual',24,6,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','TETEESFI-40607-A','Técnicas y Tecnologías Especializadas de Fisioterapia','Trayecto IV','Kaltenborn',42,6,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','TETEESFI-40607-A','Técnicas y Tecnologías Especializadas de Fisioterapia','Trayecto IV','Cyriax',18,6,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','TETEESFI-40607-A','Técnicas y Tecnologías Especializadas de Fisioterapia','Trayecto IV','Relajación Miofascial',42,6,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','TETEESFI-40607-A','Técnicas y Tecnologías Especializadas de Fisioterapia','Trayecto IV','Movilización Neuromeníngea',24,6,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','TETEESFI-40607-A','Técnicas y Tecnologías Especializadas de Fisioterapia','Trayecto IV','Mc Kenzie',36,6,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','TETEESFI-40607-A','Técnicas y Tecnologías Especializadas de Fisioterapia','Trayecto IV','Técnica de Jones y Músculo Energía',30,6,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','INTESPFI-40607-A','Intervención Especializada en Fisioterapia','Trayecto IV','Fisioterapia Aplicada al Deporte',54,6,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','INTESPFI-40607-A','Intervención Especializada en Fisioterapia','Trayecto IV','Fisioterapia en Oncología',54,6,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','INTESPFI-40607-A','Intervención Especializada en Fisioterapia','Trayecto IV','Dermatofuncional',54,6,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','INTESPFI-40607-A','Intervención Especializada en Fisioterapia','Trayecto IV','Piscina Terapéutica',54,6,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','FARMFISI-40506-A','Farmacología en Fisioterapia','Trayecto IV','Farmacología en Fisioterapia',180,5,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','SALUOCUP-40506-A','Salud Ocupacional','Trayecto IV','Salud Ocupacional',180,10,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','ADTAHURL-40506-A','Administración del Talento Humano y Relaciones Laborales','Trayecto IV','Administración del Talento Humano y Relaciones Laborales',180,10,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','ECONSALU-40506-A','Economía de la Salud','Trayecto IV','Economía de la Salud',180,10,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','ORIINTSO-40506-A','Orientación e Integración Socioambiental','Trayecto IV','Ambiente y salud',90,5,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','ORIINTSO-40506-A','Orientación e Integración Socioambiental','Trayecto IV','Accesibilidad',45,5,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','ORIINTSO-40506-A','Orientación e Integración Socioambiental','Trayecto IV','Protección, Bioseguridad y Seguridad Social',45,5,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','GEINSSAD-40607-A','Gestión Integral de los Sistemas de Salud y Atención a la Diversidad','Trayecto IV','Gestión Integral de los Sistemas de Salud y Atención a la Diversidad',216,12,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','PRACPROF-43006-A','Prácticas Profesionales III','Trayecto IV','Período I',180,30,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','PRACPROF-43006-A','Prácticas Profesionales III','Trayecto IV','Período II',180,30,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','PRACPROF-43006-A','Prácticas Profesionales III','Trayecto IV','Período III (**)',180,30,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fisioterapia','PRGEESFI-40506-A','Proyecto: Gestión Especializada en Fisioterapia','Trayecto IV','Proyecto: Gestión Especializada en Fisioterapia',180,5,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fonoaudiología','FISIAPLI-00300-B','Física Aplicada','Trayecto Inicial','Física Aplicada',36,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fonoaudiología','HERRTECN-00300-B','Herramientas Tecnológicas','Trayecto Inicial','Herramientas Tecnológicas',36,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fonoaudiología','PRNANUCI-00300-B','Proyecto Nacional y Nueva Ciudadanía','Trayecto Inicial','Proyecto Nacional y Nueva Ciudadanía',36,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fonoaudiología','SALUPUBL-00400-B','Salud Pública','Trayecto Inicial','Salud Pública',48,4,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fonoaudiología','INTPNFFO-00500-B','Introducción al PNF en Fonoaudiología','Trayecto Inicial','Introducción al PNF en Fonoaudiología',60,5,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fonoaudiología','LECTCOMP-00500-B','Lectura y Compresión','Trayecto Inicial','Lectura y Compresión',60,5,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fonoaudiología','ESTFUNCO-10506-B','Estructura y Función de la Comunicación','Trayecto I','Estructura y Función de la Comunicación',180,5,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fonoaudiología','ORGPARCO-10304-B','Organización y Participación Comunitaria','Trayecto I','Organización y Participación Comunitaria',108,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fonoaudiología','FONEFONO-10304-B','Fonética y Fonología','Trayecto I','Fonética y Fonología',108,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fonoaudiología','PSICOLOG-10304-B','Psicología','Trayecto I','Psicología',108,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fonoaudiología','BASPRAFO-10506-B','Bases de la Práctica Fonoaudiológica','Trayecto I','Bases de la Práctica Fonoaudiológica',180,5,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fonoaudiología','BASACUCO-10506-B','Bases Acústicas de la Comunicación','Trayecto I','Bases Acústicas de la Comunicación',180,5,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fonoaudiología','INGLINST-10304-B','Inglés Instrumental','Trayecto I','Inglés Instrumental',108,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fonoaudiología','ELECTIVA-10202-B','Electiva','Trayecto I','Electiva',72,2,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fonoaudiología','PROYSOCI-10405-B','Proyecto Sociointegrador I','Trayecto I','Proyecto Sociointegrador I',144,4,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fonoaudiología','NEUROLIN-20405-B','Neurolingüistica','Trayecto II','Neurolingüistica',144,4,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fonoaudiología','RECPSIFO-20405-B','Recursos Psicoterapéuticos en Fonoaudiología','Trayecto II','Recursos Psicoterapéuticos en Fonoaudiología',144,4,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fonoaudiología','PATCOMHU-20506-B','Patología de la Comunicación Humana','Trayecto II','Patología de la Comunicación Humana',180,5,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fonoaudiología','DIAINTLE-20506-B','Diagnóstico e Intervención del Lenguaje I','Trayecto II','Diagnóstico e Intervención del Lenguaje I',180,5,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fonoaudiología','DIAINTHA-20506-B','Diagnóstico e Intervención del Habla','Trayecto II','Diagnóstico e Intervención del Habla',180,5,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fonoaudiología','DIAINTAU-20405-B','Diagnóstico e Intervención Auditiva I','Trayecto II','Diagnóstico e Intervención Auditiva I',144,4,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fonoaudiología','ELECTIVA-20202-B','Electiva','Trayecto II','Electiva',72,2,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fonoaudiología','PRACPROF-21200-B','Prácticas Profesionales I','Trayecto II','Prácticas Profesionales I',432,12,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fonoaudiología','PROYSICO-20304-B','Proyecto Sociointegrador II','Trayecto II','Proyecto Sociointegrador II',108,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fonoaudiología','PSICOMOT-30202-B','Psicomotricidad','Trayecto III','Psicomotricidad',72,2,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fonoaudiología','DIAINTLE-30405-B','Diagnóstico e Intervención del Lenguaje II','Trayecto III','Diagnóstico e Intervención del Lenguaje II',144,4,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fonoaudiología','DIAINTVO-30405-B','Diagnóstico e Intervención de la Voz I','Trayecto III','Diagnóstico e Intervención de la Voz I',144,4,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fonoaudiología','DIAINTAU-30405-B','Diagnóstico e Intervención Auditiva II','Trayecto III','Diagnóstico e Intervención Auditiva II',144,4,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fonoaudiología','MOTRORAL-30304-B','Motricidad Oral','Trayecto III','Motricidad Oral',108,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fonoaudiología','DEPORECR-30202-B','Deporte y Recreación','Trayecto III','Deporte y Recreación',72,2,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fonoaudiología','ELECTIVA-30202-B','Electiva','Trayecto III','Electiva',72,2,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fonoaudiología','PRACPROF-3100-B','Prácticas Profesionales II','Trayecto III','Prácticas Profesionales II',432,12,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fonoaudiología','PROYSOCI-30304-B','Proyecto Sociointegrador III','Trayecto III','Proyecto Sociointegrador III',108,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fonoaudiología','GESADMFO-40506-B','Gestión Administrativa en Fonoaudiología','Trayecto IV','Gestión Administrativa en Fonoaudiología',180,5,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fonoaudiología','DIAINTVO-40506-B','Diagnóstico e Intervención de la Voz II','Trayecto IV','Diagnóstico e Intervención de la Voz II',180,5,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fonoaudiología','DIAINTAU-40506-B','Diagnóstico e Intervención Auditiva III','Trayecto IV','Diagnóstico e Intervención Auditiva III',180,5,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fonoaudiología','SALOCUFO-40506-B','Salud Ocupacional en Fonoaudiología','Trayecto IV','Salud Ocupacional en Fonoaudiología',180,5,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fonoaudiología','TECTECFO-40506-B','Técnicas y Tecnologías en Fonoaudiología','Trayecto IV','Técnicas y Tecnologías en Fonoaudiología',180,5,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fonoaudiología','PORTINST-40304-B','Portugués Instrumental','Trayecto IV','Portugués Instrumental',108,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fonoaudiología','EDUCAMBI-40304-B','Educación Ambiental','Trayecto IV','Educación Ambiental',108,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fonoaudiología','ELECTIVA-40202-B','ElectivA','Trayecto IV','ElectivA',72,2,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fonoaudiología','PRACPROF-41221-B','Prácticas Profesionales III','Trayecto IV','Prácticas Profesionales III',432,12,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Fonoaudiología','PROYSOCI-40708-B','Proyecto Sociointegrador IV','Trayecto IV','Proyecto Sociointegrador IV',252,7,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','FISIAPLI-00300-C','Física Aplicada','Trayecto Inicial','Física Aplicada',36,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','QUIMAPLI-00300-C','Química Aplicada','Trayecto Inicial','Química Aplicada',36,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','MATEINST-00300-C','Matemática Instrumental','Trayecto Inicial','Matemática Instrumental',36,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','SALPUBTO-00300-C','Salud Pública','Trayecto Inicial','Salud Pública',36,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','INPNORPR-00400-C','Introducción al PNF en Órtesis y Prótesis','Trayecto Inicial','Introducción al PNF en Órtesis y Prótesis',48,4,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','PRNANUCI-00300-C','Proyecto Nacional y Nueva Ciudadanía','Trayecto Inicial','Proyecto Nacional y Nueva Ciudadanía',36,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','LECTCOMP-00500-C','Lectura y Compresión','Trayecto Inicial','Lectura y Compresión',60,5,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','HERRTECN-00500-C','Herramientas Tecnológicas','Trayecto Inicial','Herramientas Tecnológicas',60,5,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','ANATOMOF-10304-C','Anatomofisiología','Trayecto I','Anatomofisiología',108,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','BIOMECAN-10304-C','Biomecánica','Trayecto I','Biomecánica',108,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','ORGPARCO-10304-C','Organización y Participación Comunitaria','Trayecto I','Organización y Participación Comunitaria',108,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','BIOESTAD-10304-C','Bioestadística','Trayecto I','Bioestadística',108,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','FUINSAPU-10304-C','Fundamentos de la Investigación en Salud Pública','Trayecto I','Fundamentos de la Investigación en Salud Pública',108,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','TECLABOR-10304-C','Tecnología de Laboratorio Ortopédico','Trayecto I','Tecnología de Laboratorio Ortopédico',144,4,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','PRMAMMOP-10304-C','Procesos de Manufactura y Manejo de Materiales para Órtesis y Prótesis','Trayecto I','Procesos de Manufactura y Manejo de Materiales para Órtesis y Prótesis',108,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','EPIDEMIO-10304-C','Epidemiología','Trayecto I','Epidemiología',108,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','INGLINST-10304-C','Inglés Instrumental I','Trayecto I','Inglés Instrumental I',108,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','DEPORECR-10202-C','Deporte y Recreación','Trayecto I','Deporte y Recreación',72,2,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','ELECTIVA-10202-C','Electiva I','Trayecto I','Electiva I',72,2,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','PAENINSU-20405-C','Patologías en Extremidades Inferiores y Superiores','Trayecto II','Patologías en Extremidades Inferiores y Superiores',144,4,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','TEORPREI-20405-C','Técnicas en Órtesis y Prótesis para Extremidades Inferiores I','Trayecto II','Técnicas en Órtesis y Prótesis para Extremidades Inferiores I',144,4,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','TEORPRES-20405-C','Técnicas en Órtesis y Prótesis para Extremidades Superiores I','Trayecto II','Técnicas en Órtesis y Prótesis para Extremidades Superiores I',144,4,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','PSICDESA-20304-C','Psicología del Desarrollo','Trayecto II','Psicología del Desarrollo',108,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','TEORCACT-20304-C','Tecnología en Órtesis para Cabeza, Cuello y Tronco','Trayecto II','Tecnología en Órtesis para Cabeza, Cuello y Tronco',144,4,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','DISCREHA-20202-C','Discapacidad y Rehabilitación','Trayecto II','Discapacidad y Rehabilitación',72,2,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','PACACUTR-20304-C','Patología de Cabeza, Cuello y Tronco','Trayecto II','Patología de Cabeza, Cuello y Tronco',108,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','DITEASCO-20304-C','Dibujo Técnico y Asistido por Computadora','Trayecto II','Dibujo Técnico y Asistido por Computadora',108,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','INGLINST-20304-C','Inglés Instrumental II','Trayecto II','Inglés Instrumental II',108,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','PRACPROF-21200-C','Práctica Profesional I','Trayecto II','Práctica Profesional I',432,12,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','PROYSOCI-20304-C','Proyecto Sociointegrador I','Trayecto II','Proyecto Sociointegrador I',108,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','TEORPREI-30405-C','Técnicas en Órtesis y Prótesis para Extremidades Inferiores II','Trayecto III','Técnicas en Órtesis y Prótesis para Extremidades Inferiores II',144,4,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','TEORPRES-30405-C','Técnicas en Órtesis y Prótesis para Extremidades Superiores II','Trayecto III','Técnicas en Órtesis y Prótesis para Extremidades Superiores II',144,4,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','PROCADMI-30202-C','Proceso Administrativo','Trayecto III','Proceso Administrativo',72,2,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','PSAPAPDE-30202-C','Psicología Aplicada al Abordaje de la Persona con Discapacidad y su Entorno Familiar','Trayecto III','Psicología Aplicada al Abordaje de la Persona con Discapacidad y su Entorno Familiar',72,2,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','ELEBASAP-30302-C','Electrónica Básica y Aplicada','Trayecto III','Electrónica Básica y Aplicada',72,2,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','INGLINST-30202-C','Inglés Instrumental III','Trayecto III','Inglés Instrumental III',72,2,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','ORGINTSO-30202-C','Organización e Integración Sociopolítica','Trayecto III','Organización e Integración Sociopolítica',72,2,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','ELECTIVA-30202-C','Electiva II','Trayecto III','Electiva II',72,2,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','PRACPROF-31200-C','Práctica Profesional II','Trayecto III','Práctica Profesional II',432,12,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','CASOCLIN-30304-C','Casos Clínicos I','Trayecto III','Casos Clínicos I',108,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','ADLAORPR-40506-C','Administración de Laboratorios de Órtesis y Prótesis','Trayecto IV','Administración de Laboratorios de Órtesis y Prótesis',180,5,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','ELECTIVA-40405-C','Electiva III','Trayecto IV','Electiva III',144,4,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','INTEAVOP-40708-C','Introducción a las Tecnologías Avanzadas en Órtesis y Prótesis','Trayecto IV','Introducción a las Tecnologías Avanzadas en Órtesis y Prótesis',252,7,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','ORINSOAM-40607-C','Orientación e Integración Socio Ambiental','Trayecto IV','Orientación e Integración Socio Ambiental',216,6,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','ORJUDPDM-40708-C','Organización de Juegos Deportivos para Personas con Discapacidad Musculoesquelética','Trayecto IV','Organización de Juegos Deportivos para Personas con Discapacidad Musculoesquelética',252,7,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','ELECTIVA-40405-C','Electiva IV','Trayecto IV','Electiva IV',144,4,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','CASOCLIN-40708-C','Casos Clínicos II','Trayecto IV','Casos Clínicos II',252,7,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','PRACPROF-41214-C','Práctica Profesional III','Trayecto IV','Práctica Profesional III',432,12,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Órtesis y Prótesis','PROYSOCI-40910-C','Proyecto Sociointegrador II','Trayecto IV','Proyecto Sociointegrador II',324,9,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','FISIAPLI-00300-D','Física Aplicada','Trayecto Inicial','Física Aplicada',3,36,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','QUIMAPLI-00300-D','Química Aplicada','Trayecto Inicial','Química Aplicada',3,36,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','MATEINST-00300-D','Matemática Instrumental','Trayecto Inicial','Matemática Instrumental',3,36,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','SALPUBTO-00500-D','Salud Pública y Terapia Ocupacional','Trayecto Inicial','Salud Pública y Terapia Ocupacional',3,60,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','PRNANUCI-00500-D','Proyecto Nacional y Nueva Ciudadanía','Trayecto Inicial','Proyecto Nacional y Nueva Ciudadanía',3,60,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','LECTCOMP-00500-D','Lectura y Comprensión','Trayecto Inicial','Lectura y Comprensión',3,60,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','ESFUCUHU-10607-D','Estructuras y Funciones del Cuerpo Humano','Trayecto I','Estructuras del Sistema Osteomioarticular y Sistema Nervioso, Cardio Respiratorio, Metabólico e Inmunológico',144,4,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','ESFUCUHU-10607-D','Estructuras y Funciones del Cuerpo Humano','Trayecto I','Funciones del Sistema Musculoesquelético y Sistema Nervioso, Cardio Respiratorio, Metabólico e Inmunológico',144,4,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','FUTETETO-10607-D','Fundamentos Teóricos y Tecnológicos de Terapia Ocupacional','Trayecto I','Fundamentos y Análisis del Desempeño Ocupacional',72,2,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','FUTETETO-10607-D','Fundamentos Teóricos y Tecnológicos de Terapia Ocupacional','Trayecto I','Análisis del Movimiento Humano',144,4,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','ORIINTSO-10506-D','Orientación e Integración Sociopolítica','Trayecto I','Ética y Deontología en Terapia Ocupacional',18,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','ORIINTSO-10506-D','Orientación e Integración Sociopolítica','Trayecto I','Métodos de Organización y Participación Comunitaria',90,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','ORIINTSO-10506-D','Orientación e Integración Sociopolítica','Trayecto I','Aspectos Teóricos Tecnológicos de la Comunicación',36,2,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','ORIINTSO-10506-D','Orientación e Integración Sociopolítica','Trayecto I','Inglés Instrumental',36,2,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','SAPUEPDI-10506-D','Salud Pública, Epidemiología y Discapacidad','Trayecto I','Bioestadística',72,2,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','SAPUEPDI-10506-D','Salud Pública, Epidemiología y Discapacidad','Trayecto I','Salud Pública',108,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','PRABCPOH-10506-D','Proyecto: Abordaje Comunitario para la Promoción de la Ocupación Humana','Trayecto I','Proyecto: Abordaje Comunitario para la Promoción de la Ocupación Humana',180,5,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','ALESFCHI-20607-D','Alteraciones en las Estructuras y Funciones del Cuerpo Humano y su Impacto en la Ocupación','Trayecto II','Alteraciones en las Estructuras Neuromúsculo Esqueléticas de Origen Periférico y su Impacto en la Ocupación',90,5,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','ALESFCHI-20607-D','Alteraciones en las Estructuras y Funciones del Cuerpo Humano y su Impacto en la Ocupación','Trayecto II','Alteraciones de las Funciones Mentales y su Impacto en la Ocupación',72,4,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','ALESFCHI-20607-D','Alteraciones en las Estructuras y Funciones del Cuerpo Humano y su Impacto en la Ocupación','Trayecto II','Alteraciones de la Función del Sistema Cardiorespiratorio, Sistema Metabólico e Inmunológico y su Impacto en la Ocupación',54,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','TEINTEOC-20607-D','Tecnologías de Intervención en Terapia Ocupacional','Trayecto II','Intervención en las Funciones por Alteraciones Neuromúsculoesqueléticas de Origen Periférico',90,5,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','TEINTEOC-20607-D','Tecnologías de Intervención en Terapia Ocupacional','Trayecto II','Intervenciones en las Disfunciones por Alteraciones de las Funciones Mentales',72,4,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','TEINTEOC-20607-D','Tecnologías de Intervención en Terapia Ocupacional','Trayecto II','Intervenciones en las Disfunciones por Alteraciones de las Funciones de los Sistemas Cardiorespiratorio, Metabólico e Inmunológico',54,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','ORIINTPS-20607-D','Orientación e Integración Psicosocial','Trayecto II','Psicología General',72,4,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','ORIINTPS-20607-D','Orientación e Integración Psicosocial','Trayecto II','Comunicación y Discapacidad',36,2,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','ORIINTPS-20607-D','Orientación e Integración Psicosocial','Trayecto II','Atención Comunitaria para Personas con y sin Discapacidad I',54,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','ORIINTPS-20607-D','Orientación e Integración Psicosocial','Trayecto II','Atención Comunitaria para Personas con y sin Discapacidad II',54,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','SAPUEPDI-20506-D','Salud Públicas, Epidemiología y Discapacidad','Trayecto II','Salud Públicas, Epidemiología y Discapacidad',180,5,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','PRACPROF-23006-D','Prácticas Profesionales I','Trayecto II','Período I',180,30,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','PRACPROF-23006-D','Prácticas Profesionales I','Trayecto II','Período II',180,30,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','PRINDIMM-20506-D','Proyecto: Intervención en las Disfunciones Motoras y Mentales','Trayecto II','Proyecto: Intervención en las Disfunciones Motoras y Mentales',180,5,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','ALESFCHI-30607-D','Alteraciones en las Estructuras y Funciones del Cuerpo Humano y su Impacto en la Ocupación','Trayecto III','Alteraciones de la Función Sensorial y del Dolor y su Impacto en la Ocupación',54,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','ALESFCHI-30607-D','Alteraciones en las Estructuras y Funciones del Cuerpo Humano y su Impacto en la Ocupación','Trayecto III','Alteraciones de las Funciones Neuromusculoesqueléticas de Origen Central',90,5,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','ALESFCHI-30607-D','Alteraciones en las Estructuras y Funciones del Cuerpo Humano y su Impacto en la Ocupación','Trayecto III','Alteraciones de las Funciones Mentales en el Niño y el Adolescente',72,4,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','TEINESTO-30607-D','Técnicas de Intervención Especializada en Terapia Ocupacional','Trayecto III','Análisis e Intervención de la Ocupación Humana',54,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','TEINESTO-30607-D','Técnicas de Intervención Especializada en Terapia Ocupacional','Trayecto III','Atención Integral de las Disfunciones Neuromusculoesqueléticas de Origen Central',90,5,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','TEINESTO-30607-D','Técnicas de Intervención Especializada en Terapia Ocupacional','Trayecto III','Atención de las Disfunciones Psicosociales de la Niñez y Adolescencia',72,4,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','ORIINTSO-30607-D','Orientación e Integración Psicosocial','Trayecto III','Psicología Evolutiva',54,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','ORIINTSO-30607-D','Orientación e Integración Psicosocial','Trayecto III','Férulas, Aparatos y Prótesis',54,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','ORIINTSO-30607-D','Orientación e Integración Psicosocial','Trayecto III','Deporte, Recreación y Salud',54,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','ORIINTSO-30607-D','Orientación e Integración Psicosocial','Trayecto III','Diversidad Cultural y Ciudadanía. Apartheid y Justicia Ocupacional, Evolucion del Trabajo',18,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','ORIINTSO-30607-D','Orientación e Integración Psicosocial','Trayecto III','Normativa en el Área de la Discapacidad',36,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','PRINTEOC-30506-D','Procedimientos de Intervención de Terapia Ocupacional','Trayecto III','Técnicas Terapéuticas para las Difunciones Neuromusculoesqueleticas de Origen Central',90,5,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','PRINTEOC-30506-D','Procedimientos de Intervención de Terapia Ocupacional','Trayecto III','Alteraciones de la Funciones de la Mano y su Intervencion',36,2,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','PRINTEOC-30506-D','Procedimientos de Intervención de Terapia Ocupacional','Trayecto III','Alteraciones del Adulto Mayor con o sin Discapacidad',54,3,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','PRACPROF-33006-D','Prácticas Profesionales II','Trayecto III','Período I',180,30,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','PRACPROF-33006-D','Prácticas Profesionales II','Trayecto III','Período II',180,30,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','PRATINDO-30506-D','Proyecto: Atención Integral al Desempeño Ocupacional','Trayecto III','Proyecto: Atención Integral al Desempeño Ocupacional',180,5,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','DICUHUIO-40809-D','Disfunciones del Cuerpo Humano y su Impacto en la Ocupación','Trayecto IV','Disfunciones de las Habilidades Motoras y Psicomotricidad en Niño y Adolescente y su Impacto en la Ocupación',108,6,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','DICUHUIO-40809-D','Disfunciones del Cuerpo Humano y su Impacto en la Ocupación','Trayecto IV','Disfunciones de las Habilidades Motoras y la Psicomotricidad en el Escolar y su Impacto en la Ocupacional',72,4,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','DICUHUIO-40809-D','Disfunciones del Cuerpo Humano y su Impacto en la Ocupación','Trayecto IV','Disfunciones del Sistema Locomotor en el Trabajador y Riesgo Disergonómico y su Impacto en la Ocupacional',108,6,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','TETEESTO-40809-D','Técnicas y Tecnologías Especializadas en Terapia Ocupacional','Trayecto IV','Diagnóstico e Intervención Ocupacional en la Niñez y la Adolescencia',108,6,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','TETEESTO-40809-D','Técnicas y Tecnologías Especializadas en Terapia Ocupacional','Trayecto IV','Diagnóstico e Intervención Ocupacional en el Escolar',72,4,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','TETEESTO-40809-D','Técnicas y Tecnologías Especializadas en Terapia Ocupacional','Trayecto IV','Diagnóstico e Intervención Ocupacional en el Trabajador',108,6,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','ORINSOAM-40809-D','Orientación e Intergración Sociopolítica y Ambiental','Trayecto IV','Protección y Seguridad Social',72,4,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','ORINSOAM-40809-D','Orientación e Intergración Sociopolítica y Ambiental','Trayecto IV','Protección y Seguridad Socio-Laboral (Legislación)',72,4,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','ORINSOAM-40809-D','Orientación e Intergración Sociopolítica y Ambiental','Trayecto IV','Accesibilidad al Medio Físico',72,4,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','ORINSOAM-40809-D','Orientación e Intergración Sociopolítica y Ambiental','Trayecto IV','Administración de Servicios de Salud de Terapia Ocupacional',72,4,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','SAPUEPDI-40809-D','Salud Pública, Epidemiología y Discapacidad','Trayecto IV','Salud Ocupacional',144,8,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','SAPUEPDI-40809-D','Salud Pública, Epidemiología y Discapacidad','Trayecto IV','Bioseguridad',144,8,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','ELECTIVA-40304-D','Electiva','Trayecto IV','Farmacología',108,6,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','ELECTIVA-40304-D','Electiva','Trayecto IV','Modelo de Ocupación Humana',108,6,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','PRACPROF-43021-D','Prácticas Profesionales III','Trayecto IV','Período I',180,30,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','PRACPROF-43021-D','Prácticas Profesionales III','Trayecto IV','Período II',180,30,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
CALL insert_plan_estudio_modulo('Terapia Ocupacional','PRGEESTO-40809-D','Proyecto: Gestión Especializada en Terapia Ocupacional','Trayecto IV','Proyecto: Gestión Especializada en Terapia Ocupacional',180,5,get_resolucion_id(51,22,'2016-11-24'),9,9,'2019-12-03');
# Etnias
CALL insert_etnia('Ninguna');
CALL insert_etnia('Akawayo');
CALL insert_etnia('Amorúa');
CALL insert_etnia('Añú/Paraujano');
CALL insert_etnia('Arawak');
CALL insert_etnia('Arutani/Uruak');
CALL insert_etnia('Ayaman');
CALL insert_etnia('Baniva');
CALL insert_etnia('Baré');
CALL insert_etnia('Barí');
CALL insert_etnia('Chaima');
CALL insert_etnia('Gayón');
CALL insert_etnia('Guanano');
CALL insert_etnia('Inga');
CALL insert_etnia('Japreria');
CALL insert_etnia('Jirajara');
CALL insert_etnia('Jivi/Guajibo/Sikwani');
CALL insert_etnia('Jodi');
CALL insert_etnia('Kaketío');
CALL insert_etnia('Kariña');
CALL insert_etnia('Kechwa');
CALL insert_etnia('Kubeo');
CALL insert_etnia('Kuiva');
CALL insert_etnia('Kumanagoto');
CALL insert_etnia('Kurripako');
CALL insert_etnia('L´ñepa/Panare');
CALL insert_etnia('Mako');
CALL insert_etnia('Makushi');
CALL insert_etnia('Mapoyo/Wanal');
CALL insert_etnia('Matako');
CALL insert_etnia('Pemón (Arekuna, Kamarakoto, Taurepán)');
CALL insert_etnia('Piapoko/Chase');
CALL insert_etnia('Piaroa');
CALL insert_etnia('Píritu');
CALL insert_etnia('Puinave');
CALL insert_etnia('Sáliva/Timote');
CALL insert_etnia('Sanemá');
CALL insert_etnia('Sapé');
CALL insert_etnia('Shiriana');
CALL insert_etnia('Timotocuica');
CALL insert_etnia('Tukano');
CALL insert_etnia('Tunebo');
CALL insert_etnia('Waikerí');
CALL insert_etnia('Wapishana');
CALL insert_etnia('Warao');
CALL insert_etnia('Warekena');
CALL insert_etnia('Wayuu/Guajiro');
CALL insert_etnia('Yanomami');
CALL insert_etnia('Yaruro/Pumé');
CALL insert_etnia('Yavarana');
CALL insert_etnia('Yekwana');
CALL insert_etnia('Yeral/Ñengatú');
CALL insert_etnia('Yukpa');

COMMIT;