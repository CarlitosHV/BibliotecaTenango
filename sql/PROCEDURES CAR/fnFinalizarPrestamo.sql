CREATE OR REPLACE FUNCTION fnFinalizarPrestamo(
	IN i_id_prestamo int
)
RETURNS INTEGER
LANGUAGE plpgsql
AS $$
BEGIN
	UPDATE PRESTAMOS
	SET 
		activo = false
        WHERE id_prestamo = i_id_prestamo;
	RETURN 1;
END;
$$;