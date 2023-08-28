CREATE OR REPLACE PROCEDURE spInsertarActualizarActividad(
	IN i_id_actividad INT,
	IN i_nombre VARCHAR(150)
)
LANGUAGE plpgsql
AS $$
DECLARE actividadexistente INT;
BEGIN
    SELECT id_actividad INTO actividadexistente FROM actividades WHERE nombre = i_nombre;

    IF i_id_actividad = 0 THEN
        IF actividadexistente IS NULL THEN
            INSERT INTO actividades (nombre) VALUES (i_nombre);
        END IF;
    ELSE
        UPDATE actividades SET nombre = i_nombre WHERE id_actividad = i_id_actividad;
    END IF;
END;
$$;