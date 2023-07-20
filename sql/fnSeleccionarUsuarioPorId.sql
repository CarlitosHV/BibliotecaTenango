CREATE OR REPLACE FUNCTION fnSeleccionarTodosUsuarios(
    IN i_id_usuario INT
) RETURNS TABLE (
    id_usuario INT,
    telefono INT,
    edad INT,
    sexo VARCHAR(20),
    curp VARCHAR(20),
    contrasenia VARCHAR(50),
    grado_escolar VARCHAR(50),
    correo VARCHAR(50),
    nombre VARCHAR(50),
    apellido_paterno VARCHAR(50),
    apellido_materno VARCHAR(50),
    ocupacion VARCHAR(100),
    tipo_usuario VARCHAR(20),
    calle VARCHAR(100),
    cp INT,
    municipio VARCHAR(100),
    estado VARCHAR(100),
    localidad VARCHAR(100)
)
AS $$
BEGIN
    RETURN QUERY SELECT
        u.id_usuario,
        u.telefono,
        u.edad,
        u.sexo,
        u.curp,
        u.contrasenia,
        ge.nombre as grado_escolar,
        u.correo,
        n.nombre,
        n.apellido_paterno,
        n.apellido_materno,
        o.nombre as ocupacion,
        tu.nombre as tipo_usuario,
        d.calle,
        d.codigo_postal,
        m.nombre as municipio,
        e.nombre as estado,
        l.descripcion as localidad
    FROM USUARIOS u
    JOIN GRADO_ESCOLAR ge ON ge.id_grado_escolar = u.id_grado_escolar
    JOIN NOMBRES n ON n.id_nombre = u.id_nombre
    JOIN OCUPACION o ON o.id_ocupacion = u.id_ocupacion
    JOIN TIPO_USUARIO tu ON tu.id_tipo_usuario = u.id_tipo_usuario
    JOIN DIRECCION d ON d.id_direccion = u.id_direccion
    JOIN MUNICIPIOS m ON m.id_municipio = d.id_municipio
    JOIN ESTADOS e ON e.id_estado = d.id_estado
    JOIN LOCALIDADES l ON l.id = d.id_localidad
    WHERE
        u.id_usuario = i_id_usuario;
END;
$$ LANGUAGE plpgsql;