CREATE OR REPLACE FUNCTION fnConteoMujeres(
    fecha_inicio date,
    fecha_fin date
) RETURNS TABLE (
    genero text,
    edad_rango text,
    con_discapacidad bigint,
    sin_discapacidad bigint
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        'Femenino' AS genero,
        CASE
            WHEN edad_visitante > 60 THEN 'Mayor de 60 años'
            WHEN edad_visitante BETWEEN 30 AND 59 THEN '30-59 años'
            WHEN edad_visitante BETWEEN 18 AND 29 THEN '18-29 años'
            WHEN edad_visitante BETWEEN 13 AND 17 THEN '13-17 años'
			WHEN edad_visitante BETWEEN 0 AND 12 THEN '0-12 años'
        END AS edad_rango,
        COUNT(*) FILTER (WHERE discapacidad) AS con_discapacidad,
        COUNT(*) FILTER (WHERE NOT discapacidad) AS sin_discapacidad
    FROM
        visitantes
    WHERE
        sexo = 'Femenino'
        AND fecha_visita BETWEEN fecha_inicio AND fecha_fin
    GROUP BY
        genero, edad_rango
    ORDER BY
        genero, edad_rango;

    RETURN;
END;
$$ LANGUAGE plpgsql;