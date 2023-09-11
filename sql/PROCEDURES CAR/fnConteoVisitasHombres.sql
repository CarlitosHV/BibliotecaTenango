CREATE OR REPLACE FUNCTION fnConteoVisitas(
    i_fecha_inicio date,
    i_fecha_fin date
) RETURNS TABLE (
    Masculinos60 bigint,
    Masculinos3059 bigint,
    Masculinos1829 bigint,
    Masculinos1317 bigint,
    Masculinos012 bigint,
	MasculinosDis60 bigint,
    MasculinosDis3059 bigint,
    MasculinosDis1829 bigint,
    MasculinosDis1317 bigint,
    MasculinosDis012 bigint,
	MasculinosTotales bigint,
	MasculinosDiscTotales bigint,
	FEM60 bigint,
    FEM3059 bigint,
    FEM1829 bigint,
    FEM1317 bigint,
    FEM012 bigint,
	FEMDis60 bigint,
    FEMDis3059 bigint,
    FEMDis1829 bigint,
    FEMDis1317 bigint,
    FEMDis012 bigint,
	FEMTotales bigint,
	FEMDisTotales bigint,
	TotalUsers bigint
) AS $$
BEGIN
    RETURN QUERY (
        SELECT
            COUNT(*) FILTER (WHERE edad_visitante > 59 AND discapacidad = false AND sexo = 'Masculino') AS Masculinos60,
            COUNT(*) FILTER (WHERE edad_visitante BETWEEN 30 AND 59 AND discapacidad = false AND sexo = 'Masculino') AS Masculinos3059,
            COUNT(*) FILTER (WHERE edad_visitante BETWEEN 18 AND 29 AND discapacidad = false AND sexo = 'Masculino') AS Masculinos1829,
            COUNT(*) FILTER (WHERE edad_visitante BETWEEN 13 AND 17 AND discapacidad = false AND sexo = 'Masculino') AS Masculinos1317,
            COUNT(*) FILTER (WHERE edad_visitante BETWEEN 0 AND 12 AND discapacidad = false AND sexo = 'Masculino') AS Masculinos012,
			COUNT(*) FILTER (WHERE edad_visitante > 59 AND discapacidad = true AND sexo = 'Masculino') AS MasculinosDis60,
            COUNT(*) FILTER (WHERE edad_visitante BETWEEN 30 AND 59 AND discapacidad = true AND sexo = 'Masculino') AS MasculinosDis3059,
            COUNT(*) FILTER (WHERE edad_visitante BETWEEN 18 AND 29 AND discapacidad = true AND sexo = 'Masculino') AS MasculinosDis1829,
            COUNT(*) FILTER (WHERE edad_visitante BETWEEN 13 AND 17 AND discapacidad = true AND sexo = 'Masculino') AS MasculinosDis1317,
            COUNT(*) FILTER (WHERE edad_visitante BETWEEN 0 AND 12 AND discapacidad = true AND sexo = 'Masculino') AS MasculinosDis012,
			COUNT(*) FILTER (WHERE discapacidad = false AND sexo = 'Masculino') AS MasculinosTotales,
			COUNT(*) FILTER (WHERE discapacidad = true AND sexo = 'Masculino') AS MasculinosDiscTotales,
		
			COUNT(*) FILTER (WHERE edad_visitante > 59 AND discapacidad = false AND sexo = 'Femenino') AS FEM60,
            COUNT(*) FILTER (WHERE edad_visitante BETWEEN 30 AND 59 AND discapacidad = false AND sexo = 'Femenino') AS FEM3059,
            COUNT(*) FILTER (WHERE edad_visitante BETWEEN 18 AND 29 AND discapacidad = false AND sexo = 'Femenino') AS FEM1829,
            COUNT(*) FILTER (WHERE edad_visitante BETWEEN 13 AND 17 AND discapacidad = false AND sexo = 'Femenino') AS FEM1317,
            COUNT(*) FILTER (WHERE edad_visitante BETWEEN 0 AND 12 AND discapacidad = false AND sexo = 'Femenino') AS FEM012,
			COUNT(*) FILTER (WHERE edad_visitante > 59 AND discapacidad = true AND sexo = 'Femenino') AS FEMDis60,
            COUNT(*) FILTER (WHERE edad_visitante BETWEEN 30 AND 59 AND discapacidad = true AND sexo = 'Femenino') AS FEMDis3059,
            COUNT(*) FILTER (WHERE edad_visitante BETWEEN 18 AND 29 AND discapacidad = true AND sexo = 'Femenino') AS FEMDis1829,
            COUNT(*) FILTER (WHERE edad_visitante BETWEEN 13 AND 17 AND discapacidad = true AND sexo = 'Femenino') AS FEMDis1317,
            COUNT(*) FILTER (WHERE edad_visitante BETWEEN 0 AND 12 AND discapacidad = true AND sexo = 'Femenino') AS FEMDis012,
			COUNT(*) FILTER (WHERE discapacidad = false AND sexo = 'Femenino') AS FEMTotales,
			COUNT(*) FILTER (WHERE discapacidad = true AND sexo = 'Femenino') AS FEMDiscTotales,
			COUNT(*) AS TotalUsers
        FROM VISITANTES
		WHERE VISITANTES.fecha_visita >= i_fecha_inicio
		AND VISITANTES.fecha_visita <= i_fecha_fin
    );
END;
$$ LANGUAGE plpgsql;

