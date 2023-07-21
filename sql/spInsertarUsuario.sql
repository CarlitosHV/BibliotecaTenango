CREATE OR REPLACE PROCEDURE spInsertarUsuario(
    IN i_nombre VARCHAR,
    IN i_apellido_paterno VARCHAR,
	IN i_apellido_materno VARCHAR,
	IN i_correo VARCHAR,
	IN i_contrasenia VARCHAR,
	IN i_telefono BIGINT,
	IN i_grado_escolar VARCHAR,
	IN i_curp VARCHAR,
	IN i_edad INT,
	IN i_ocupacion VARCHAR,
    IN i_calle VARCHAR,
	IN i_estado VARCHAR,
	IN i_municipio VARCHAR,
    IN i_codigo_postal INT,
	
	
)
LANGUAGE plpgsql
AS $$
DECLARE
    v_id_clasificacion INT;
    v_id_registro_clasificacion INT;
    v_id_editorial INT;
    v_id_lugar_edicion INT;
    v_id_nombre_autor INT;
BEGIN
    IF NOT EXISTS (SELECT 1 FROM CLASIFICACION WHERE nombre = i_clasificacion) THEN
    INSERT INTO CLASIFICACION (nombre)
    VALUES (i_clasificacion);
    END IF;

    SELECT id_clasificacion INTO v_id_clasificacion
    FROM CLASIFICACION
    WHERE nombre = i_clasificacion;


	IF NOT EXISTS (SELECT 1 FROM REGISTRO_CLASIFICACION WHERE nombre = i_registro_clasificacion) THEN
    INSERT INTO REGISTRO_CLASIFICACION (nombre)
    VALUES (i_registro_clasificacion);
    END IF;

    SELECT id_registro_clasificacion INTO v_id_registro_clasificacion
    FROM REGISTRO_CLASIFICACION
    WHERE nombre = i_registro_clasificacion;


    IF NOT EXISTS (SELECT 1 FROM EDITORIAL WHERE nombre = i_editorial) THEN
    INSERT INTO EDITORIAL (nombre)
    VALUES (i_editorial);
    END IF;

    SELECT id_editorial INTO v_id_editorial
    FROM EDITORIAL
    WHERE nombre = i_editorial;


    IF NOT EXISTS (SELECT 1 FROM LUGAR_EDICION WHERE nombre = i_lugar_edicion) THEN
    INSERT INTO LUGAR_EDICION (nombre)
    VALUES (i_lugar_edicion);
    END IF;

    SELECT id_lugar_edicion INTO v_id_lugar_edicion
    FROM LUGAR_EDICION
    WHERE nombre = i_lugar_edicion;


    IF NOT EXISTS (SELECT 1 FROM NOMBRES_AUTORES WHERE nombre = i_nombre_autor) THEN
    INSERT INTO NOMBRES_AUTORES (nombre)
    VALUES (i_nombre_autor);
    END IF;

    SELECT id_nombre_autor INTO v_id_nombre_autor
    FROM NOMBRES_AUTORES
    WHERE nombre = i_nombre_autor;

    
    INSERT INTO LIBROS (
        clave_registro,
        estante,
        descripcion_libro,
        existencias,
        titulo_libro,
        anio_edicion,
        id_nombre_autor,
        id_clasificacion,
        id_registro_clasificacion,
        id_editorial,
        id_lugar_edicion
    ) VALUES (
        i_clave_registro,
        i_estante,
        i_descripcion_libro,
        i_existencias,
        i_titulo_libro,
        i_anio_edicion,
        v_id_nombre_autor,
        v_id_clasificacion,
        v_id_registro_clasificacion,
        v_id_editorial,
        v_id_lugar_edicion
    );
END;
$$;