CREATE OR REPLACE PROCEDURE spInsertarRegistroLibro(
    IN i_clave_registro VARCHAR,
	IN i_registro_clasificacion VARCHAR
)
LANGUAGE plpgsql
AS $$
BEGIN   
    INSERT INTO LIBROSINTERNOS(
		fecha_registro,
		clave_registro,
		registro_clasificacion
	)VALUES(
		CURRENT_DATE,
		i_clave_registro,
		i_registro_clasificacion
	);
END;
$$;
