CREATE OR REPLACE FUNCTION fnContarVisitasGuiadas(
    fecha_inicio DATE,
    fecha_fin DATE
) 
RETURNS TABLE(
    GHombres0a12 BIGINT,
    GMujeres0a12 BIGINT,
    GHombres13a17 BIGINT,
    GMujeres13a17 BIGINT,
    GHombres18a29 BIGINT,
    GMujeres18a29 BIGINT,
    GHombres30a59 BIGINT,
    GMujeres30a59 BIGINT,
    GHombres60 BIGINT,
    GMujeres60 BIGINT,
    TotalG BIGINT,
    TotalHombres BIGINT,
    TotalMujeres BIGINT
) 
AS $$
BEGIN
    RETURN QUERY 
    SELECT 
        SUM(CASE WHEN v.edad_visitante BETWEEN 0 AND 12 AND v.sexo = 'Masculino' THEN 1 ELSE 0 END) AS GHombres0a12,
        SUM(CASE WHEN v.edad_visitante BETWEEN 0 AND 12 AND v.sexo = 'Femenino' THEN 1 ELSE 0 END) AS GMujeres0a12,
        SUM(CASE WHEN v.edad_visitante BETWEEN 13 AND 17 AND v.sexo = 'Masculino' THEN 1 ELSE 0 END) AS GHombres13a17,
        SUM(CASE WHEN v.edad_visitante BETWEEN 13 AND 17 AND v.sexo = 'Femenino' THEN 1 ELSE 0 END) AS GMujeres13a17,
        SUM(CASE WHEN v.edad_visitante BETWEEN 18 AND 29 AND v.sexo = 'Masculino' THEN 1 ELSE 0 END) AS GHombres18a29,
        SUM(CASE WHEN v.edad_visitante BETWEEN 18 AND 29 AND v.sexo = 'Femenino' THEN 1 ELSE 0 END) AS GMujeres18a29,
        SUM(CASE WHEN v.edad_visitante BETWEEN 30 AND 59 AND v.sexo = 'Masculino' THEN 1 ELSE 0 END) AS GHombres30a59,
        SUM(CASE WHEN v.edad_visitante BETWEEN 30 AND 59 AND v.sexo = 'Femenino' THEN 1 ELSE 0 END) AS GMujeres30a59,
        SUM(CASE WHEN v.edad_visitante >=60 AND v.sexo = 'Masculino' THEN 1 ELSE 0 END) AS GHombres60,
        SUM(CASE WHEN v.edad_visitante >=60 AND v.sexo = 'Femenino' THEN 1 ELSE 0 END) AS GMujeres60,
        COUNT(*) AS TotalG, 
        SUM(CASE WHEN v.sexo = 'Masculino' THEN 1 ELSE 0 END) AS TotalHombres, 
        SUM(CASE WHEN v.sexo = 'Femenino' THEN 1 ELSE 0 END) AS TotalMujeres
    FROM VISITANTES v
    JOIN ACTIVIDADES a ON v.id_actividad = a.id_actividad
    WHERE v.fecha_visita BETWEEN fecha_inicio AND fecha_fin
    AND a.nombre IN ('Visitas guiadas');
END; $$
LANGUAGE plpgsql;