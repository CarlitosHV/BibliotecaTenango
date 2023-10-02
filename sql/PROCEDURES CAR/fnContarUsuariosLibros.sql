CREATE OR REPLACE FUNCTION fnContarUsuariosLibros(
    fecha_inicio DATE,
    fecha_fin DATE
) 
RETURNS TABLE(
	TotalCredenciales BIGINT, 
	TotalLibros BIGINT) 
AS $$
BEGIN
    RETURN QUERY 
    SELECT 
        (SELECT COUNT(*) FROM USUARIOS u WHERE u.fecha_registro BETWEEN fecha_inicio AND fecha_fin) AS TotalCredenciales,
        (SELECT COUNT(*) FROM PRESTAMOS p JOIN DETALLE_PRESTAMO dp ON p.id_prestamo = dp.id_prestamo WHERE p.fecha_prestamo BETWEEN fecha_inicio AND fecha_fin) AS TotalLibros;
END; 
$$
LANGUAGE plpgsql;
