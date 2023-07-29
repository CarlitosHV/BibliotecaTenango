CREATE OR REPLACE PROCEDURE spInsertarActualizarGrado(
	IN i_id_grado_escolar INT,
	IN i_nombre VARCHAR(50)
)
LANGUAGE plpgsql
AS $$
DECLARE gradoexistente INT;
BEGIN
    SELECT id_grado_escolar INTO gradoexistente FROM GRADO_ESCOLAR WHERE nombre = i_nombre;

    IF i_id_grado_escolar = 0 THEN
        IF gradoexistente IS NULL THEN
            INSERT INTO GRADO_ESCOLAR (nombre) VALUES (i_nombre);
        END IF;
    ELSE
        UPDATE GRADO_ESCOLAR SET nombre = i_nombre WHERE id_grado_escolar = i_id_grado_escolar;
    END IF;
END;
$$;