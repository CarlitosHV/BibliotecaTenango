CREATE OR REPLACE FUNCTION fnContarArtisticasPorEdadYSexo(
    fecha_inicio DATE,
    fecha_fin DATE
) 
RETURNS TABLE(
    ArtisticaHombres0a12 BIGINT,
    ArtisticaMujeres0a12 BIGINT,
    Artistica0a12 BIGINT,
    ArtisticaHombres13a17 BIGINT,
    ArtisticaMujeres13a17 BIGINT,
    Artistica13a17 BIGINT,
    ArtisticaHombres18a29 BIGINT,
    ArtisticaMujeres18a29 BIGINT,
    Artistica18a29 BIGINT,
    ArtisticaHombres30a59 BIGINT,
    ArtisticaMujeres30a59 BIGINT,
    Artistica30a59 BIGINT,
    ArtisticaHombres60 BIGINT,
    ArtisticaMujeres60 BIGINT,
    Artistica60 BIGINT,
    TotalArtistica BIGINT,
    TotalHombres BIGINT,
    TotalMujeres BIGINT
) 
AS $$
BEGIN
    RETURN QUERY 
    SELECT 
        SUM(CASE WHEN v.edad_visitante BETWEEN 0 AND 12 AND v.sexo = 'Hombre' THEN 1 ELSE 0 END) AS ArtisticaHombres0a12,
        SUM(CASE WHEN v.edad_visitante BETWEEN 0 AND 12 AND v.sexo = 'Mujer' THEN 1 ELSE 0 END) AS ArtisticaMujeres0a12,
        SUM(CASE WHEN v.edad_visitante BETWEEN 0 AND 12 THEN 1 ELSE 0 END) AS Artistica0a12,
        SUM(CASE WHEN v.edad_visitante BETWEEN 13 AND 17 AND v.sexo = 'Hombre' THEN 1 ELSE 0 END) AS ArtisticaHombres13a17,
        SUM(CASE WHEN v.edad_visitante BETWEEN 13 AND 17 AND v.sexo = 'Mujer' THEN 1 ELSE 0 END) AS ArtisticaMujeres13a17,
        SUM(CASE WHEN v.edad_visitante BETWEEN 13 AND 17 THEN 1 ELSE 0 END) AS Artistica13a17,
        SUM(CASE WHEN v.edad_visitante BETWEEN 18 AND 29 AND v.sexo = 'Hombre' THEN 1 ELSE 0 END) AS ArtisticaHombres18a29,
        SUM(CASE WHEN v.edad_visitante BETWEEN 18 AND 29 AND v.sexo = 'Mujer' THEN 1 ELSE 0 END) AS ArtisticaMujeres18a29,
        SUM(CASE WHEN v.edad_visitante BETWEEN 18 AND 29 THEN 1 ELSE 0 END) AS Artistica18a29,
        SUM(CASE WHEN v.edad_visitante BETWEEN 30 AND 59 AND v.sexo = 'Hombre' THEN 1 ELSE 0 END) AS ArtisticaHombres30a59,
        SUM(CASE WHEN v.edad_visitante BETWEEN 30 AND 59 AND v.sexo = 'Mujer' THEN 1 ELSE 0 END) AS ArtisticaMujeres30a59,
        SUM(CASE WHEN v.edad_visitante BETWEEN 30 AND 59 THEN 1 ELSE 0 END) AS Artistica30a59,
        SUM(CASE WHEN v.edad_visitante >=60 AND v.sexo = 'Hombre' THEN 1 ELSE 0 END) AS ArtisticaHombres60,
        SUM(CASE WHEN v.edad_visitante >=60 AND v.sexo = 'Mujer' THEN 1 ELSE 0 END) AS ArtisticaMujeres60,
        SUM(CASE WHEN v.edad_visitante >=60 THEN 1 ELSE 0 END) AS Artistica60,
        COUNT(*) AS TotalArtistica, 
        SUM(CASE WHEN v.sexo = 'Hombre' THEN 1 ELSE 0 END) AS TotalHombres, 
        SUM(CASE WHEN v.sexo = 'Mujer' THEN 1 ELSE 0 END) AS TotalMujeres
    FROM VISITANTES v
    JOIN ACTIVIDADES a ON v.id_actividad = a.id_actividad
    WHERE v.fecha_visita BETWEEN fecha_inicio AND fecha_fin
    AND a.nombre IN ('Conciertos', 'Teatro', 'Danza', 'Proyecciones de cine', 'Fotografía', 'Pintura', 'Exposiciones');
END; $$
LANGUAGE plpgsql;