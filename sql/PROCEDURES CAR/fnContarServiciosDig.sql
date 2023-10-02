CREATE OR REPLACE FUNCTION fnContarServiciosDig(
    fecha_inicio DATE,
    fecha_fin DATE
) 
RETURNS TABLE(
    SDHombres0a12 BIGINT,
    SDMujeres0a12 BIGINT,
    SDHombres13a17 BIGINT,
    SDMujeres13a17 BIGINT,
    SDHombres18a29 BIGINT,
    SDMujeres18a29 BIGINT,
    SDHombres30a59 BIGINT,
    SDMujeres30a59 BIGINT,
    SDHombres60 BIGINT,
    SDMujeres60 BIGINT,
	SDCurso60 BIGINT,
	SDCurso30a59 BIGINT,
	SDCurso18a29 BIGINT,
	SDCurso13a17 BIGINT,
	SDCurso0a12 BIGINT,
    TotalHombres BIGINT,
    TotalMujeres BIGINT,
	TotalCurso BIGINT
) 
AS $$
BEGIN
    RETURN QUERY 
    SELECT 
        SUM(CASE WHEN v.edad_visitante BETWEEN 0 AND 12 AND v.sexo = 'Hombre' THEN 1 ELSE 0 END) SDHombres0a12,
        SUM(CASE WHEN v.edad_visitante BETWEEN 0 AND 12 AND v.sexo = 'Mujer' THEN 1 ELSE 0 END) AS SDMujeres0a12,
		SUM(CASE WHEN v.edad_visitante BETWEEN 0 AND 12 THEN 1 ELSE 0 END) AS SDCurso0a12,
        SUM(CASE WHEN v.edad_visitante BETWEEN 13 AND 17 AND v.sexo = 'Hombre' THEN 1 ELSE 0 END) AS SDHombres13a17,
        SUM(CASE WHEN v.edad_visitante BETWEEN 13 AND 17 AND v.sexo = 'Mujer' THEN 1 ELSE 0 END) AS SDMujeres13a17,
		SUM(CASE WHEN v.edad_visitante BETWEEN 13 AND 17 THEN 1 ELSE 0 END) AS SDCurso13a17,
        SUM(CASE WHEN v.edad_visitante BETWEEN 18 AND 29 AND v.sexo = 'Hombre' THEN 1 ELSE 0 END) AS SDHombres18a29,
        SUM(CASE WHEN v.edad_visitante BETWEEN 18 AND 29 AND v.sexo = 'Mujer' THEN 1 ELSE 0 END) AS SDMujeres18a29,
		SUM(CASE WHEN v.edad_visitante BETWEEN 18 AND 29 THEN 1 ELSE 0 END) AS SDCurso18a29,
        SUM(CASE WHEN v.edad_visitante BETWEEN 30 AND 59 AND v.sexo = 'Hombre' THEN 1 ELSE 0 END) AS SDHombres30a59,
        SUM(CASE WHEN v.edad_visitante BETWEEN 30 AND 59 AND v.sexo = 'Mujer' THEN 1 ELSE 0 END) AS SDMujeres30a59,
		SUM(CASE WHEN v.edad_visitante BETWEEN 30 AND 59 THEN 1 ELSE 0 END) AS SDCurso30a59,
        SUM(CASE WHEN v.edad_visitante >=60 AND v.sexo = 'Hombre' THEN 1 ELSE 0 END) AS SDHombres60,
        SUM(CASE WHEN v.edad_visitante >=60 AND v.sexo = 'Mujer' THEN 1 ELSE 0 END) AS SDMujeres60,
		SUM(CASE WHEN v.edad_visitante >=60 THEN 1 ELSE 0 END) AS SDCurso60,
        SUM(CASE WHEN v.sexo = 'Hombre' THEN 1 ELSE 0 END) AS TotalHombres, 
        SUM(CASE WHEN v.sexo = 'Mujer' THEN 1 ELSE 0 END) AS TotalMujeres,
		COUNT(*) AS TotalCurso
    FROM VISITANTES v
    JOIN ACTIVIDADES a ON v.id_actividad = a.id_actividad
    WHERE v.fecha_visita BETWEEN fecha_inicio AND fecha_fin
    AND a.nombre IN ('Servicios digitales');
END; $$
LANGUAGE plpgsql;