CREATE OR REPLACE FUNCTION fnSeleccionarTodosLibrosPrestamos(
	IN i_id_prestamo int
)
RETURNS TABLE(
	id_detalle int,
	id_prestamo int,
	clave_registro VARCHAR
)
LANGUAGE plpgsql
AS $$
BEGIN
	RETURN QUERY SELECT
		pr.*
	FROM DETALLE_PRESTAMO pr
	WHERE pr.id_prestamo = i_id_prestamo;
END;
$$;