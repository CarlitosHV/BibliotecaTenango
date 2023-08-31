CREATE OR REPLACE FUNCTION fnSeleccionarTodosPrestamos(
)
RETURNS TABLE(
	id_prestamo int,
	id_usuario int,
	fecha_prestamo DATE,
	fecha_devolucion DATE,
	comentario_atraso VARCHAR,
	renovaciones int,
	id_tipo_documento int,
	activo boolean
)
LANGUAGE plpgsql
AS $$
BEGIN
	RETURN QUERY SELECT
		pr.*
	FROM PRESTAMOS pr
	WHERE pr.activo = true;
END;
$$;