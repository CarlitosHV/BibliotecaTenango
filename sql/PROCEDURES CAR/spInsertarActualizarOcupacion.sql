CREATE OR REPLACE PROCEDURE spInsertarActualizarOcupacion(
	IN i_id_ocupacion INT,
	IN i_nombre VARCHAR(50)
)
LANGUAGE plpgsql
AS $$
DECLARE ocupacionexistente INT;
BEGIN
    SELECT id_ocupacion INTO ocupacionexistente FROM ocupacion WHERE nombre = i_nombre;

    IF i_id_ocupacion = 0 THEN
        IF ocupacionexistente IS NULL THEN
            INSERT INTO ocupacion (nombre) VALUES (i_nombre);
        END IF;
    ELSE
        UPDATE ocupacion SET nombre = i_nombre WHERE id_ocupacion = i_id_ocupacion;
    END IF;
END;
$$;