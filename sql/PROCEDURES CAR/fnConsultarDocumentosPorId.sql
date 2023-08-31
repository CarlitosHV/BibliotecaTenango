CREATE OR REPLACE FUNCTION fnConsultarDocumentosPorId(
	IN i_id_documento int
) RETURNS TABLE (
    id_documento INT,
    nombre VARCHAR(50)
)
AS $$
BEGIN
    RETURN QUERY SELECT
        td.* 
		FROM 
		TIPO_DOCUMENTO td
		WHERE td.id_documento = i_id_documento;
END;
$$ LANGUAGE plpgsql;