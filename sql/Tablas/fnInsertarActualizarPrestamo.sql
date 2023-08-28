CREATE OR REPLACE FUNCTION fnInsertarActualizarPrestamo(
    IN i_id_prestamo INT = 0,
    IN i_id_usuario INT = 0,
    IN i_fecha_prestamo DATE = NULL,
    IN i_fecha_devolucion DATE = NULL,
    IN i_renovaciones INT = 0,
    IN i_comentario_atraso VARCHAR(50) = NULL,
    IN i_id_tipo_documento INT = 0
)
RETURNS INTEGER
LANGUAGE plpgsql
AS $$
DECLARE prestamoexistente INT;
DECLARE id_retorno INT;
BEGIN
    SELECT COUNT(id_usuario) INTO prestamoexistente FROM prestamos WHERE id_usuario = i_id_usuario;

    IF i_id_prestamo = 0 THEN
        IF prestamoexistente = 0 THEN
            INSERT INTO prestamos (
                id_usuario,
                fecha_prestamo,
                fecha_devolucion,
                id_tipo_documento,
				activo
            ) VALUES (
                i_id_usuario,
                i_fecha_prestamo,
                i_fecha_devolucion,
                i_id_tipo_documento,
				TRUE
            ) RETURNING id_prestamo INTO id_retorno;
        ELSE
            id_retorno := -2;
        END IF;
    ELSE
        UPDATE prestamos 
        SET 
            fecha_devolucion = i_fecha_devolucion,
            renovaciones = COALESCE(renovaciones, 0) + 1
        WHERE id_prestamo = i_id_prestamo RETURNING id_prestamo INTO id_retorno;
    END IF;

    RETURN id_retorno;
END;
$$;
