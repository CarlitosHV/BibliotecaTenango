CREATE OR REPLACE PROCEDURE spinsertarregistrovisitante(
	IN i_edad_visitante int,
	IN i_id_grado_escolar int,
	IN i_ocupacion VARCHAR(15),
	IN i_discapacidad BOOLEAN,
	IN i_id_nombre int)
	
LANGUAGE plpgsql
AS $$
	BEGIN
		INSERT INTO VISITANTES(
			edad_visitante,
			id_grado_escolar,
			ocupacion,
			discapacidad,
			id_nombre)
		VALUES(
			i_edad_visitante,
			i_id_grado_escolar,
			i_ocupacion,
			i_discapacidad,
			i_id_nombre);
	END;
$$;
