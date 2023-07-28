CREATE OR REPLACE PROCEDURE spEliminarOcupacion(
    IN i_id_ocupacion INT
)
LANGUAGE plpgsql
AS $$
DECLARE
    id_ocupacion_ref INT;
BEGIN
    SELECT COUNT(*) INTO id_ocupacion_ref FROM USUARIOS WHERE id_ocupacion = i_id_ocupacion;

    IF id_ocupacion_ref = 0 THEN
        DELETE FROM OCUPACION WHERE id_ocupacion = i_id_ocupacion;
        RAISE NOTICE 'Ocupación eliminada correctamente';
    ELSE
        RAISE EXCEPTION 'No se puede eliminar la ocupación porque está referenciada en otra tabla.';
    END IF;
END;
$$;
