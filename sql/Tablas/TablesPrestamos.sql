CREATE TABLE IF NOT EXISTS prestamos
(
    id_prestamo serial PRIMARY KEY,
    id_usuario integer,
    fecha_prestamo date NOT NULL,
    fecha_devolucion date NOT NULL,
    comentario_atraso character varying(150)
);

CREATE TABLE IF NOT EXISTS detalle_prestamo
(
    id_detalle serial PRIMARY KEY,
    id_prestamo integer REFERENCES prestamos (id_prestamo) ON DELETE CASCADE,
    clave_registro character varying REFERENCES libros (clave_registro)
);