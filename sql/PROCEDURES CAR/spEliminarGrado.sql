CREATE OR REPLACE PROCEDURE spEliminarGrado(
    IN i_id_grado_escolar INT
)
LANGUAGE plpgsql
AS $$
DECLARE
    id_grado_escolar_ref INT;
BEGIN
    SELECT COUNT(*) INTO id_grado_escolar_ref FROM USUARIOS WHERE id_grado_escolar = i_id_grado_escolar;

    IF id_grado_escolar_ref = 0 THEN
        DELETE FROM GRADO_ESCOLAR WHERE id_grado_escolar = i_id_grado_escolar;
        RAISE NOTICE 'Grado escolar eliminado correctamente';
    ELSE
        RAISE EXCEPTION 'No se puede eliminar el grado escolar porque está referenciada en otra tabla.';
    END IF;
END;
$$;