CREATE OR REPLACE FUNCTION fnBuscarLibroClave(
    i_clave_registro character varying
) RETURNS TABLE (
    clave_registro VARCHAR(10),
    estante VARCHAR(5),
    descripcion_libro VARCHAR(80),
    existencias int,
    titulo_libro VARCHAR(40),
    anio_edicion VARCHAR(4),
    nombre_autor VARCHAR(40),
    clasificacion VARCHAR(50),
    registro_clasificacion VARCHAR(50),
    editorial VARCHAR(50),
    lugar_edicion VARCHAR(50)
)
AS $$
BEGIN
    RETURN QUERY SELECT
        l.clave_registro,
        l.estante,
        l.descripcion_libro,
        l.existencias,
        l.titulo_libro,
        l.anio_edicion,
        ne.nombre,
        c.nombre as clasificacion,
        rc.nombre as registro_clasificacion,
        e.nombre as editorial,
        le.nombre as lugar_edicion
    FROM LIBROS l
    JOIN CLASIFICACION c ON l.id_clasificacion = c.id_clasificacion
    JOIN REGISTRO_CLASIFICACION rc ON l.id_registro_clasificacion = rc.id_registro_clasificacion
    JOIN EDITORIAL e ON l.id_editorial = e.id_editorial
    JOIN LUGAR_EDICION le ON l.id_lugar_edicion = le.id_lugar_edicion
    JOIN NOMBRES_AUTORES ne ON l.id_nombre_autor = ne.id_nombre_autor
    WHERE 
		upper(l.clave_registro) = upper(i_clave_registro);
END;
$$ LANGUAGE plpgsql;