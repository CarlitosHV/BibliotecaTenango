CREATE OR REPLACE FUNCTION fnValidarPrestamo(
	IN i_id_usuario int
)
RETURNS BOOLEAN
LANGUAGE plpgsql
AS $$
DECLARE prestamoexistente INT;
BEGIN
	SELECT 
	COUNT(id_usuario) 
	INTO prestamoexistente 
	FROM prestamos 
	WHERE id_usuario = i_id_usuario 
	AND activo = true;
	
	IF prestamoexistente = 0 THEN
		RETURN TRUE;
	ELSE
		RETURN FALSE;
	END IF;
END;
$$;