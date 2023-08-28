CREATE OR REPLACE FUNCTION fnSeleccionarTodosDocumentos(
) RETURNS TABLE (
    id_documento INT,
    nombre VARCHAR(50)
)
AS $$
BEGIN
    RETURN QUERY SELECT
        * FROM 
		TIPO_DOCUMENTO;
END;
$$ LANGUAGE plpgsql;