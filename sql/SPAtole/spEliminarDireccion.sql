CREATE OR REPLACE PROCEDURE spEliminarDireccion(in i_id_direccion int)
LANGUAGE plpgsql 
AS $$
DECLARE referencia_direccion int;
BEGIN
    SELECT count(*)into referencia_direccion FROM USUARIOS where id_direccion = i_id_direccion;
    IF referencia_direccion = 0 THEN
        DELETE FROM DIRECCION where id_direccion = i_id_direccion;
        RAISE NOTICE 'Dirección Eliminada';
    ELSE
        RAISE Exception 'Error al eliminar';
    END IF;
END;
$$;
