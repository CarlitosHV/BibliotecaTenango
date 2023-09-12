CREATE OR REPLACE PROCEDURE spEliminarActividad(
    IN i_id_actividad INT
)
LANGUAGE plpgsql
AS $$
DECLARE
    id_actividad_ref INT;
BEGIN
    SELECT COUNT(*) INTO id_actividad_ref FROM VISITANTES WHERE id_actividad = i_id_actividad;

    IF id_actividad_ref = 0 THEN
        DELETE FROM ACTIVIDADES WHERE id_actividad = i_id_actividad;
        RAISE NOTICE 'Actividad eliminada correctamente';
    ELSE
        RAISE EXCEPTION 'No se puede eliminar la actividad porque está referenciada en otra tabla.';
    END IF;
END;
$$;