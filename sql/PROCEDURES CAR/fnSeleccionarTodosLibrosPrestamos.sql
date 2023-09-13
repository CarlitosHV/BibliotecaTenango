CREATE OR REPLACE FUNCTION fnSeleccionarTodosLibrosPrestamos(
	IN i_id_prestamo int
)
RETURNS TABLE(
	id_detalle int,
	id_prestamo int,
	clave_registro VARCHAR,
	titulo_libro VARCHAR
)
LANGUAGE plpgsql
AS $$
BEGIN
	RETURN QUERY SELECT
		pr.*,
		l.titulo_libro
	FROM DETALLE_PRESTAMO pr
	JOIN LIBROS l ON pr.clave_registro = l.clave_registro
	WHERE pr.id_prestamo = i_id_prestamo;
END;
$$;