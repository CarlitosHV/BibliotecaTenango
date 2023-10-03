CREATE OR REPLACE FUNCTION fnContarOcupaciones(
    fecha_inicio DATE,
    fecha_fin DATE
) 
RETURNS TABLE(
	hogar BIGINT, 
	estudiante BIGINT, 
	empleado BIGINT, 
	desocupado BIGINT
) 
AS $$
BEGIN
    RETURN QUERY 
    SELECT 
        SUM(CASE WHEN o.nombre = 'Hogar' THEN 1 ELSE 0 END) AS hogar,
        SUM(CASE WHEN o.nombre = 'Estudiante' THEN 1 ELSE 0 END) AS estudiante,
        SUM(CASE WHEN o.nombre NOT IN ('Hogar', 'Estudiante', 'Desocupado') THEN 1 ELSE 0 END) AS empleado,
        SUM(CASE WHEN o.nombre = 'Desocupado' THEN 1 ELSE 0 END) AS desocupado
    FROM VISITANTES v
    JOIN OCUPACION o ON v.id_ocupacion = o.id_ocupacion
    WHERE v.fecha_visita BETWEEN fecha_inicio AND fecha_fin;
END; $$
LANGUAGE plpgsql;
