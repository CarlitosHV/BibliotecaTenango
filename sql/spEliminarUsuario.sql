CREATE OR REPLACE PROCEDURE spEliminarUsuario(
    IN i_id_usuario INT
)
LANGUAGE plpgsql
AS $$
BEGIN

    DELETE FROM USUARIOS WHERE USUARIOS.id_usuario = i_id_usuario;

END;
$$;