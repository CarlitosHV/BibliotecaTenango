CREATE OR REPLACE FUNCTION fnContarClasificaciones(
    fecha_inicio DATE,
    fecha_fin DATE
) 
RETURNS TABLE(
	ColGen BIGINT, 
	ColCon BIGINT, 
	ColInf BIGINT) 
AS $$
BEGIN
    RETURN QUERY 
    SELECT 
        SUM(CASE WHEN li.registro_clasificacion BETWEEN '000' AND '900' THEN 1 ELSE 0 END) AS ColGen,
        SUM(CASE WHEN li.registro_clasificacion BETWEEN 'C 000' AND 'C 900' THEN 1 ELSE 0 END) AS ColCon,
        SUM(CASE WHEN li.registro_clasificacion BETWEEN 'IC 000' AND 'IC 900' OR li.registro_clasificacion BETWEEN 'I 000' AND 'I 900' THEN 1 ELSE 0 END) AS ColInf
    FROM LIBROSINTERNOS li
    JOIN REGISTRO_CLASIFICACION rc ON li.registro_clasificacion = rc.nombre
    WHERE li.fecha_registro BETWEEN fecha_inicio AND fecha_fin;
END; $$
LANGUAGE plpgsql;

