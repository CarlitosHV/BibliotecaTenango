CREATE OR REPLACE PROCEDURE spInsertarActualizarUsuario(
    IN i_id_usuario int,
    IN i_telefono BIGINT,
    IN i_edad INT,
    IN i_sexo VARCHAR(15),
    IN i_curp VARCHAR(25),
    IN i_contrasenia VARCHAR(50),
    IN i_id_grado_escolar int,
    IN i_correo VARCHAR(50),
    IN i_id_nombre int,
    IN i_id_tipo_usuario int,
    IN i_id_ocupacion int,
    IN i_id_direccion int
)
LANGUAGE plpgsql
AS $$
DECLARE usuarioexistente INT;
BEGIN   
    SELECT id_usuario INTO usuarioexistente FROM USUARIOS WHERE curp = i_curp;

    IF i_id_usuario = 0 THEN
        IF usuarioexistente IS NULL THEN
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
                i_id_grado_escolar,
                i_correo,
                i_id_nombre,
                i_id_ocupacion,
                i_id_tipo_usuario,
                i_id_direccion
            );
        END IF;
    ELSE
        UPDATE USUARIOS
        SET
            telefono = i_telefono,
            edad = i_edad,
            sexo = ,
            curp = i_sexo,
            contrasenia = i_contrasenia,
            id_grado_escolar = i_id_grado_escolar,
            correo = i_correo,
            id_nombre = i_id_nombre,
            id_ocupacion = i_id_ocupacion,
            id_tipo_usuario = i_id_tipo_usuario,
            id_direccion = i_id_direccion
        WHERE 
            id_usuario = i_id_usuario;
    END IF;
END;
$$;
