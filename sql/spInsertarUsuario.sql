CREATE OR REPLACE PROCEDURE spInsertarUsuario(
    IN i_telefono BIGINT,
    IN i_edad INT,
    IN i_sexo VARCHAR(15),
    IN i_curp VARCHAR(25),
    IN i_contrasenia VARCHAR(50),
    IN i_grado_escolar VARCHAR(50),
    IN i_correo VARCHAR(50),
    IN i_nombre VARCHAR(50),
    IN i_apellido_paterno VARCHAR(50),
    IN i_apellido_materno VARCHAR(50),
    IN i_tipo_usuario VARCHAR(50),
    IN i_ocupacion VARCHAR(50),
    IN i_calle VARCHAR(150),
    IN i_codigo_postal INT,
    IN i_municipio VARCHAR(150),
    IN i_estado VARCHAR(150),
    IN i_localidad VARCHAR(150)
)
LANGUAGE plpgsql
AS $$
DECLARE
    v_id_grado_escolar INT;
    v_id_nombre INT;
    v_id_tipo_usuario INT;
    v_id_ocupacion INT;
    v_id_direccion INT;
    v_id_municipio INT;
    v_id_estado INT;
    v_id_localidad INT;
BEGIN
    IF NOT EXISTS (SELECT 1 FROM GRADO_ESCOLAR WHERE nombre = i_grado_escolar) THEN
    INSERT INTO GRADO_ESCOLAR (nombre)
    VALUES (i_grado_escolar);
    END IF;

    SELECT id_grado_escolar INTO v_id_grado_escolar
    FROM GRADO_ESCOLAR
    WHERE nombre = i_grado_escolar;


	IF NOT EXISTS (SELECT 1 FROM NOMBRES WHERE nombre = i_nombre AND apellido_paterno = i_apellido_paterno 
    AND apellido_materno = i_apellido_materno) THEN
    INSERT INTO NOMBRES (nombre, apellido_paterno, apellido_materno)
    VALUES (i_nombre, apellido_paterno, i_apellido_materno);
    END IF;

    SELECT id_nombre INTO v_id_nombre
    FROM NOMBRES
    WHERE nombre = i_nombre AND apellido_paterno = i_apellido_paterno 
    AND apellido_materno = i_apellido_materno;


    IF NOT EXISTS (SELECT 1 FROM TIPO_USUARIO WHERE nombre = i_tipo_usuario) THEN
    INSERT INTO TIPO_USUARIO (nombre)
    VALUES (i_tipo_usuario);
    END IF;

    SELECT id_tipo_usuario INTO v_id_tipo_usuario
    FROM TIPO_USUARIO
    WHERE nombre = i_tipo_usuario;


    IF NOT EXISTS (SELECT 1 FROM OCUPACION WHERE nombre = i_ocupacion) THEN
    INSERT INTO OCUPACION (nombre)
    VALUES (i_ocupacion);
    END IF;

    SELECT id_ocupacion INTO v_id_ocupacion
    FROM OCUPACION
    WHERE nombre = i_ocupacion;


    SELECT id INTO v_id_municipio
    FROM MUNICIPIOS
    WHERE nombre = i_municipio;

    SELECT id INTO v_id_estado
    FROM ESTADOS
    WHERE nombre = i_estado;

    SELECT id INTO v_id_localidad
    FROM LOCALIDADES
    WHERE descripcion = i_localidad AND cp = i_codigo_postal;


    IF NOT EXISTS (SELECT 1 FROM DIRECCION WHERE calle = i_calle AND codigo_postal = i_codigo_postal
    AND id_municipio = v_id_municipio AND id_estado = v_id_estado AND id_localidad = v_id_localidad) THEN
    INSERT INTO DIRECCION (calle, codigo_postal, id_municipio, id_estado, id_localidad)
    VALUES (i_calle, i_codigo_postal, v_id_municipio, v_id_estado, v_id_localidad);
    END IF;

    SELECT id_direccion INTO v_id_direccion
    FROM DIRECCION
    WHERE calle = i_calle AND codigo_postal = i_codigo_postal
    AND id_municipio = v_id_municipio AND id_estado = v_id_estado AND id_localidad = v_id_localidad;

    
    INSERT INTO USUARIOS (
        telefono,
        edad,
        sexo,
        curp,
        contrasenia,
        id_grado_escolar,
        correo,
        id_nombre,
        id_ocupacion,
        id_tipo_usuario,
        id_direccion
    ) VALUES (
        i_telefono,
        i_edad,
        i_sexo,
        i_curp,
        i_contrasenia,
        v_id_grado_escolar,
        i_correo,
        v_id_nombre,
        v_id_ocupacion,
        v_id_tipo_usuario,
        v_id_direccion
    );
END;
$$;
