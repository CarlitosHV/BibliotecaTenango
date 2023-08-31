CREATE OR REPLACE FUNCTION fnActualizarPrestamo(
	IN i_id_prestamo int,
	IN i_fecha_fin DATE,
	IN i_comentario_atraso VARCHAR
)
RETURNS INTEGER
LANGUAGE plpgsql
AS $$
BEGIN
	UPDATE PRESTAMOS
	SET 
		fecha_devolucion = i_fecha_fin,
		comentario_atraso = i_comentario_atraso,
		renovaciones = COALESCE(renovaciones, 0) + 1
        WHERE id_prestamo = i_id_prestamo;
	RETURN 1;
END;
$$;