CREATE OR REPLACE FUNCTION fnFinalizarPrestamo(
	IN i_id_prestamo int,
	IN i_clave_registro VARCHAR
)
RETURNS INTEGER
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
            UPDATE LIBROS 
			SET
				existencias = existencias + 1
			WHERE clave_registro = libro_clave;
        END IF;
    END LOOP;

	UPDATE PRESTAMOS
	SET 
		activo = false
        WHERE id_prestamo = i_id_prestamo;
	
	UPDATE DETALLE_PRESTAMO
	SET
		id_prestamo = NULL
		WHERE id_prestamo = i_id_prestamo;
	RETURN 1;
END;
$$;