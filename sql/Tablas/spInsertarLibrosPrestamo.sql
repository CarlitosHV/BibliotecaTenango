CREATE OR REPLACE PROCEDURE spInsertarLibrosPrestamo(
    IN i_id_prestamo INT,
    IN i_clave_registro VARCHAR
)
LANGUAGE plpgsql
AS $$
DECLARE
    libros_array TEXT[];
    libro_clave VARCHAR;
BEGIN
    libros_array := STRING_TO_ARRAY(i_clave_registro, ',');

    FOR i IN 1..ARRAY_LENGTH(libros_array, 1) LOOP
        libro_clave := TRIM(libros_array[i]);

        IF LENGTH(libro_clave) > 0 THEN
            INSERT INTO DETALLE_PRESTAMO (
                id_prestamo,
                clave_registro
            ) VALUES (
                i_id_prestamo,
                libro_clave
            );
			
			UPDATE libros
            SET existencias = existencias - 1
            WHERE clave_registro = clave_registro;
        END IF;
    END LOOP;
END;
$$;
