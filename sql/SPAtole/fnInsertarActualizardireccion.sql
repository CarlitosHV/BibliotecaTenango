CREATE OR REPLACE FUNCTION fnInsertarActualizardireccion(
    IN i_id_direccion int,
    IN i_calle VARCHAR(30),
    IN i_codigo_postal VARCHAR(30),
    IN i_id_municipio int,
    IN i_id_estado int,
    IN i_id_localidad int
)
RETURNS INTEGER
LANGUAGE plpgsql
AS $$
DECLARE direccionexistente int;
DECLARE fnreturn int;
BEGIN
    SELECT id_direccion INTO direccionexistente FROM DIRECCION WHERE i_codigo_postal = codigo_postal AND i_calle = calle 
    AND i_id_municipio = id_municipio AND i_id_estado = id_estado AND i_id_localidad = id_localidad;
    
    IF i_id_direccion = 0 THEN
        IF direccionexistente IS NULL THEN
            INSERT INTO DIRECCION (
                calle,
                codigo_postal,
                id_municipio,
                id_estado,
                id_localidad
            ) VALUES (
                i_calle,
                i_codigo_postal,
                i_id_municipio,
                i_id_estado,
                i_id_localidad
            )
            RETURNING id_direccion INTO fnreturn;
		END IF;
	ELSE
		UPDATE DIRECCION
		SET
            calle = i_calle,
            codigo_postal = i_codigo_postal,
            id_municipio = i_id_municipio,
            id_estado = i_id_estado,
            id_localidad = i_id_localidad
        WHERE 
            id_direccion = i_id_direccion
        RETURNING id_direccion INTO fnreturn;
    END IF;
    RETURN fnreturn;
END;
$$;