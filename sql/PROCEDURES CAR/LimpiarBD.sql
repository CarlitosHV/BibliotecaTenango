DELETE FROM USUARIOS;
SELECT setval('usuarios_id_usuario_seq', 1, false);

DELETE FROM VISITANTES;
SELECT setval('visitantes_id_visitante_seq', 1, false);

DELETE FROM DIRECCION;
SELECT setval('direccion_id_direccion_seq', 1, false);

DELETE FROM NOMBRES;
SELECT setval('nombres_id_nombre_seq', 1, false);

DELETE FROM DETALLE_PRESTAMO;
SELECT setval('detalle_prestamo_id_detalle_seq', 1, false);

DELETE FROM PRESTAMOS;
SELECT setval('prestamos_id_prestamo_seq', 1, false);

DELETE FROM LIBROSINTERNOS;

DELETE FROM LIBROS;

DELETE FROM NOMBRES_AUTORES;
SELECT setval('nombres_autores_id_nombre_autor_seq', 1, false);

DELETE FROM LUGAR_EDICION;
SELECT setval('lugar_edicion_id_lugar_edicion_seq', 1, false);

DELETE FROM EDITORIAL;
SELECT setval('editorial_id_editorial_seq', 1, false);

DELETE FROM OCUPACION;
SELECT setval('ocupacion_id_ocupacion_seq', 1, false);

DELETE FROM ACTIVIDADES;
SELECT setval('actividades_id_actividad_seq', 1, false);

DELETE FROM REGISTRO_CLASIFICACION;
SELECT setval('registro_clasificacion_id_registro_clasificacion_seq', 1, false);


