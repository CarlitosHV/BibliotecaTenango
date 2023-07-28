CREATE OR REPLACE FUNCTION fnSeleccionarTodasOcupaciones(
)
RETURNS TABLE(
	id_ocupacion int,
	nombre varchar
)
LANGUAGE plpgsql
AS $$
BEGIN
	RETURN QUERY SELECT
		o.id_ocupacion,
		o.nombre
	FROM OCUPACION o;
END;
$$;