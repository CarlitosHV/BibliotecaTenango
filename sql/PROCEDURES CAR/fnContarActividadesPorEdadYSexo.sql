CREATE OR REPLACE FUNCTION fnContarActividadesPorEdadYSexo(
    fecha_inicio DATE,
    fecha_fin DATE
) 
RETURNS TABLE(
    ActividadHombres0a12 BIGINT,
    ActividadMujeres0a12 BIGINT,
    Actividades0a12 BIGINT,
    ActividadHombres13a17 BIGINT,
    ActividadMujeres13a17 BIGINT,
    Actividades13a17 BIGINT,
    ActividadHombres18a29 BIGINT,
    ActividadMujeres18a29 BIGINT,
    Actividades18a29 BIGINT,
    ActividadHombres30a59 BIGINT,
    ActividadMujeres30a59 BIGINT,
    Actividades30a59 BIGINT,
    ActividadHombres60 BIGINT,
    ActividadMujeres60 BIGINT,
    Actividades60 BIGINT,
    TotalActividades BIGINT,
    TotalHombres BIGINT,
    TotalMujeres BIGINT
) 
AS $$
BEGIN
    RETURN QUERY 
    SELECT 
        SUM(CASE WHEN v.edad_visitante BETWEEN 0 AND 12 AND v.sexo = 'Masculino' THEN 1 ELSE 0 END) AS ActividadHombres0a12,
        SUM(CASE WHEN v.edad_visitante BETWEEN 0 AND 12 AND v.sexo = 'Femenino' THEN 1 ELSE 0 END) AS ActividadMujeres0a12,
        SUM(CASE WHEN v.edad_visitante BETWEEN 0 AND 12 THEN 1 ELSE 0 END) AS Actividades0a12,
        SUM(CASE WHEN v.edad_visitante BETWEEN 13 AND 17 AND v.sexo = 'Masculino' THEN 1 ELSE 0 END) AS ActividadHombres13a17,
        SUM(CASE WHEN v.edad_visitante BETWEEN 13 AND 17 AND v.sexo = 'Femenino' THEN 1 ELSE 0 END) AS ActividadMujeres13a17,
        SUM(CASE WHEN v.edad_visitante BETWEEN 13 AND 17 THEN 1 ELSE 0 END) AS Actividades13a17,
        SUM(CASE WHEN v.edad_visitante BETWEEN 18 AND 29 AND v.sexo = 'Masculino' THEN 1 ELSE 0 END) AS ActividadHombres18a29,
        SUM(CASE WHEN v.edad_visitante BETWEEN 18 AND 29 AND v.sexo = 'Femenino' THEN 1 ELSE 0 END) AS ActividadMujeres18a29,
        SUM(CASE WHEN v.edad_visitante BETWEEN 18 AND 29 THEN 1 ELSE 0 END) AS Actividades18a29,
        SUM(CASE WHEN v.edad_visitante BETWEEN 30 AND 59 AND v.sexo = 'Masculino' THEN 1 ELSE 0 END) AS ActividadHombres30a59,
        SUM(CASE WHEN v.edad_visitante BETWEEN 30 AND 59 AND v.sexo = 'Femenino' THEN 1 ELSE 0 END) AS ActividadMujeres30a59,
        SUM(CASE WHEN v.edad_visitante BETWEEN 30 AND 59 THEN 1 ELSE 0 END) AS Actividades30a59,
        SUM(CASE WHEN v.edad_visitante >=60 AND v.sexo = 'Masculino' THEN 1 ELSE 0 END) AS ActividadHombres60,
        SUM(CASE WHEN v.edad_visitante >=60 AND v.sexo = 'Femenino' THEN 1 ELSE 0 END) AS ActividadMujeres60,
        SUM(CASE WHEN v.edad_visitante >=60 THEN 1 ELSE 0 END) AS Actividades60,
        COUNT(*) AS TotalActividades, 
        SUM(CASE WHEN v.sexo = 'Masculino' THEN 1 ELSE 0 END) AS TotalHombres, 
        SUM(CASE WHEN v.sexo = 'Femenino' THEN 1 ELSE 0 END) AS TotalMujeres
    FROM VISITANTES v
    JOIN ACTIVIDADES a ON v.id_actividad = a.id_actividad
    WHERE v.fecha_visita BETWEEN fecha_inicio AND fecha_fin
    AND a.nombre IN ('Tertulias', 'Lectura en voz alta', 'Cuenta Cuentos', 'Hora del cuento', 'Teatro en Atril');
END; $$
LANGUAGE plpgsql;
