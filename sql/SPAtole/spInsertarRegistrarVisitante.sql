CREATE OR REPLACE PROCEDURE spInsertarRegistrarVisitante(
	IN i_edad_visitante int,
	IN i_id_grado_escolar int,
	IN i_discapacidad BOOLEAN,
	IN i_id_nombre int,
	IN i_fecha_visita DATE,
	IN i_id_ocupacion int,
	IN i_id_actividad int,
	IN i_sexo varchar(15))
	
LANGUAGE plpgsql
AS $$
	BEGIN
		INSERT INTO VISITANTES(
			edad_visitante,
			id_grado_escolar,
			discapacidad,
			id_nombre,
			fecha_visita,
			id_ocupacion,
			id_actividad,
			sexo)
		VALUES(
			i_edad_visitante,
			i_id_grado_escolar,
			i_discapacidad,
			i_id_nombre,
			i_fecha_visita,
			i_id_ocupacion,
			i_id_actividad,
			i_sexo);
	END;
$$;
