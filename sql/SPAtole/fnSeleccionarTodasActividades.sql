CREATE OR REPLACE FUNCTION fnSeleccionarTodasActividades(
)
RETURNS TABLE(
	id_actividad int,
	nombre varchar
)
LANGUAGE plpgsql
AS $$
BEGIN
	RETURN QUERY SELECT
		ac.id_actividad,
		ac.nombre
	FROM ACTIVIDADES ac;
END;
$$;