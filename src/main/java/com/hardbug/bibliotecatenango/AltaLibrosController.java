package com.hardbug.bibliotecatenango;


/*Clase que controla la alta de libros
  Vista a la que está asociada la clase: AltaLibrosView.fxml
 */

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

/* Clase AltaLibros:
    Su funcionalidad es recibir la información de su vista ligada, tratar la información y mandarla a base de datos
 */

public class AltaLibrosController implements Initializable {

    /* Este arraylist almacena la información del libro consultado */
    public static ArrayList<Object> datosLibro = new ArrayList<>();

    /* Variables booleanas que marcan si los campos están correctos */
    private static boolean Clasificacion_bol, Anio_edicion_bol, Descripcion_libro_bol, Nombre_autor_bol,
            Titulo_libro_bol, Lugar_edicion_bol, Editorial_bol, Registro_clasificacion_bol, Estante_bol,
            Existencias_bol, Clave_registro_bol;

    /* Variables traidas desde le FXML y que se necesitan*/
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private Separator Separador_dos, Separador_tres, Separador_cuatro;
    @FXML
    private Label Label_cabecera, Label_datos_del_libro, Label_datos_referencia;
    @FXML
    private Button BotonGuardar;
    @FXML
    private TextField Campo_clasificacion, Campo_anio_edicion, Campo_registro_clasificacion, Campo_estante,
            Campo_existencias, Campo_editorial, Campo_lugar_edicion, Campo_titulo_libro, Campo_nombre_autor, Campo_descripcion_libro,
            Campo_clave_registro;


    /* Variables de tipo integer para manejar los tipos de alertas */
    private static final int ALERTA_ERROR = 0, ALERTA_LIBRO_GUARDADO = 1, ALERTA_LIBRO_NO_ENCONTRADO = 2,
            ALERTA_CAMPOS_INVALIDOS = 3, ALERTA_ELIMINAR_LIBRO = 4, ALERTA_LIBRO_ELIMINADO = 5,
            ALERTA_LIBRO_EDITADO = 6;


    /* Instancias de las clases necesarias para funcionar el código */
    private final ClaseLibro libro = new ClaseLibro();
    private final IndexApp indexApp = new IndexApp();
    private final BDController bdController = new BDController();


    /*
        Método que dependiendo el texto del botón, se realiza la acción
        Guardar: Invoca el método de guardar el libro
        Buscar, Editar, Eliminar: Invoca a desactivar gran parte de la interfaz para hacer su operación
        Buscar otro, Editar libro, Eliminar otro: Revierten la interfaz para visualizar la información
     */
    @FXML
    void AccionesBotonGuardar() throws SQLException {
        switch (BotonGuardar.getText()) {
            case "Guardar" -> GuardarLibro();
            case "Buscar" -> BuscarLibro(0);
            case "Buscar otro" -> crudLibro(2);
            case "Editar" -> BuscarLibro(1);
            case "Editar libro" -> EditarLibro();
            case "Eliminar" -> {
                if (!Campo_titulo_libro.getText().isEmpty()){
                    CrearAlerta(ALERTA_ELIMINAR_LIBRO);
                }else{
                    CrearAlerta(ALERTA_CAMPOS_INVALIDOS);
                }
            }
            case "Eliminar otro" -> crudLibro(4);
        }
    }


    /*
        Método que crea la alerta, dependiendo el tipo de alerta que recibe es la información que muestra
     */
    void CrearAlerta(int TIPO_ALERTA) throws SQLException {
        switch (TIPO_ALERTA) {
            case 0 -> aplicarTemaAlerta("¡Ocurrió un error!", "Error al guardar en base de datos", 0);
            case 1 -> aplicarTemaAlerta("¡Libro guardado con éxito!", "El libro " + libro.getTitulo_libro() + " ha sido registrado :)", 0);
            case 2 -> aplicarTemaAlerta("¡Libro no encontrado!", "No se encontró el libro, prueba checando la ortografía :)", 0);
            case 3 -> aplicarTemaAlerta("¡Campos inválidos!", "Error en procesar la información, verifica que los campos estén llenados de forma correcta", 0);
            case 4 -> aplicarTemaAlerta("¡Cuidado!", "¿Estás seguro de eliminar el libro: " + Campo_titulo_libro.getText() + "?", 2);
            case 5 -> aplicarTemaAlerta("Eliminación realizada", "Se ha eliminado el libro de manera correcta", 1);
            case 6 -> aplicarTemaAlerta("¡Libro editado con éxito!", "El libro " + libro.getTitulo_libro() + " ha sido editado :)", 0);
        }
    }


    /*
        Método que aplica el tema dependiendo el seleccionado y también aplica el texto y la cabecera
     */
    void aplicarTemaAlerta(String titulo, String contenido, int tipo) throws SQLException {
        Alert alert;
        if (tipo == 2){
            alert = new Alert(Alert.AlertType.CONFIRMATION);
        }else{
            alert = new Alert(Alert.AlertType.INFORMATION);
        }
        DialogPane dialogPane = alert.getDialogPane();
        Label content = new Label(alert.getContentText());
        alert.setHeaderText(null);
        alert.setTitle(titulo);
        alert.setContentText(contenido);
        content.setText(contenido);
        if (IndexApp.TEMA == 0) {
            dialogPane.setStyle("-fx-background-color: white; -fx-text-fill: white");
            content.setTextFill(Color.BLACK);
            alert.getDialogPane().setContent(content);
        } else {
            dialogPane.setStyle("-fx-background-color: #2b2b2b; -fx-text-fill: white");
            content.setTextFill(Color.WHITESMOKE);
            alert.getDialogPane().setContent(content);
        }
        if (tipo == 1){
            Optional<ButtonType> result1 = alert.showAndWait();
            if (result1.isPresent() && result1.get() == ButtonType.OK) {
                alert.close();
                crudLibro(4);
            }
        }else if (tipo == 2){
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                EliminarLibro();
            } else {
                alert.close();
            }
        }else{
            alert.showAndWait();
        }

    }


    /*
        Método que manda guardar a Base de Datos el libro, y valida los campos
     */
    @FXML
    void GuardarLibro() throws SQLException {
        if (camposValidos()) {
            boolean guardado = bdController.InsertarLibro(libro.getClave_registro(), libro.getEstante(), libro.getDescripcion_libro(), libro.getExistencias(),
                    libro.getTitulo_libro(), libro.getAnio_edicion(), libro.getNombre_autor(), libro.getClasificacion(),
                    libro.getRegistro_clasificacion(), libro.getEditorial(), libro.getLugar_edicion());
            if (guardado) {
                CrearAlerta(ALERTA_LIBRO_GUARDADO);
                limpiarCampos();
            } else {
                CrearAlerta(ALERTA_ERROR);
            }
        } else {
            CrearAlerta(ALERTA_CAMPOS_INVALIDOS);
        }
    }


    /*
        Método que manda el libro editado a Base de Datos el libro, y valida los campos
     */
    void EditarLibro() throws SQLException {
        if (camposValidos()) {
            boolean editado = bdController.EditarLibro(libro.getClave_registro(), libro.getEstante(), libro.getDescripcion_libro(), libro.getExistencias(),
                    libro.getTitulo_libro(), libro.getAnio_edicion(), libro.getNombre_autor(), libro.getClasificacion(),
                    libro.getRegistro_clasificacion(), libro.getEditorial(), libro.getLugar_edicion());
            if (editado) {
                CrearAlerta(ALERTA_LIBRO_EDITADO);
                limpiarCampos();
            } else {
                CrearAlerta(ALERTA_ERROR);
            }
        } else {
            CrearAlerta(ALERTA_CAMPOS_INVALIDOS);
        }
    }

    /*
        Método que manda a eliminar a Base de Datos el libro
     */
    void EliminarLibro() throws SQLException {
        boolean eliminado = bdController.BorrarLibro(Campo_titulo_libro.getText());
        if (eliminado) {
            CrearAlerta(ALERTA_LIBRO_ELIMINADO);
        }
    }


    /*
        Método que manda a buscar en Base de Datos el libro
     */
    void BuscarLibro(int tipo) throws SQLException {
        boolean encontrado = bdController.BuscarLibro(Campo_titulo_libro.getText());
        if (encontrado) {
            Campo_clave_registro.setText(datosLibro.get(0).toString());
            Campo_clasificacion.setText(datosLibro.get(1).toString());
            Campo_anio_edicion.setText(datosLibro.get(2).toString());
            Campo_registro_clasificacion.setText(datosLibro.get(3).toString());
            Campo_estante.setText(datosLibro.get(4).toString());
            Campo_existencias.setText(datosLibro.get(5).toString());
            Campo_editorial.setText(datosLibro.get(6).toString());
            Campo_lugar_edicion.setText(datosLibro.get(7).toString());
            Campo_titulo_libro.setText(datosLibro.get(8).toString());
            Campo_nombre_autor.setText(datosLibro.get(9).toString());
            Campo_descripcion_libro.setText(datosLibro.get(10).toString());
            if (tipo == 0) {
                crudEncontradosOriginal(2);
            } else {
                crudEncontradosOriginal(3);
            }
        } else {
            CrearAlerta(ALERTA_LIBRO_NO_ENCONTRADO);
        }
    }

    /*
        Método que valida los campos y manda a escribir en el objeto libro
     */
    boolean camposValidos() {
        if (Clasificacion_bol && Anio_edicion_bol && Descripcion_libro_bol && Nombre_autor_bol &&
                Titulo_libro_bol && Lugar_edicion_bol && Editorial_bol && Registro_clasificacion_bol && Estante_bol &&
                Existencias_bol && Clave_registro_bol) {
            libro.setClave_registro(Campo_clave_registro.getText());
            libro.setClasificacion(Campo_clasificacion.getText());
            libro.setAnio_edicion(Campo_anio_edicion.getText());
            libro.setRegistro_clasificacion(Campo_registro_clasificacion.getText());
            libro.setEstante(Campo_estante.getText());
            libro.setExistencias(Integer.parseInt(Campo_existencias.getText()));
            libro.setEditorial(Campo_editorial.getText());
            libro.setLugar_edicion(Campo_lugar_edicion.getText());
            libro.setTitulo_libro(Campo_titulo_libro.getText());
            libro.setNombre_autor(Campo_nombre_autor.getText());
            libro.setDescripcion_libro(Campo_descripcion_libro.getText());
            return true;
        } else {
            return false;
        }
    }

    /*
        Método que trae el tema seleccionado de la app y lo aplica a la interfaz, a su vez reescribe las propiedades para que no se pierdan
     */
    @FXML
    void ActivarModoOscuro() {
        if (IndexApp.TEMA == 1) {
            ViewSwitcher.switchTo(View.CRUD_LIBROS, ViewSwitcher.TEMA_CLARO);
            ViewSwitcher.BANDERA_TEMA = 0;
            IndexApp.TEMA = 0;
            indexApp.EscribirPropiedades("theme", String.valueOf(ViewSwitcher.BANDERA_TEMA));
        } else if (IndexApp.TEMA == 0) {
            ViewSwitcher.switchTo(View.CRUD_LIBROS, ViewSwitcher.TEMA_OSCURO);
            ViewSwitcher.BANDERA_TEMA = 1;
            IndexApp.TEMA = 1;
            indexApp.EscribirPropiedades("theme", String.valueOf(ViewSwitcher.BANDERA_TEMA));
        }
    }

    // Validaciones de los campos
    // Campo Clasificación: Literatura, Historia, Ciencias,Matemáticas, etc.
    public void validar_clasificacion() {
        if (Campo_clasificacion.isEditable()) {
            if (Campo_clasificacion.getText().matches("^[A-Z. ][A-Za-z áéíóúñ]{4,15}") && !Campo_clasificacion.getText().isEmpty()) {
                Clasificacion_bol = true;
                if (IndexApp.TEMA == 1) {
                    Campo_clasificacion.setStyle("-fx-border-color: #595b5d");
                } else {
                    Campo_clasificacion.setStyle("-fx-border-color: black");
                }
            } else {
                Campo_clasificacion.setStyle("-fx-border-color: red");
                Clasificacion_bol = false;
            }
        }
    }

    //Campo Registro de clasificación: Del 000 al 900 o S/C
    //Debe de contener 3 digitos obligatorios o S/C =Sin Clasificación
    public void validar_registro_clasificacion() {
        if (Campo_registro_clasificacion.isEditable()) {
            if (Campo_registro_clasificacion.getText().matches("S/C") || Campo_registro_clasificacion.getText().matches("^\\d{3}$") && !Campo_registro_clasificacion.getText().isEmpty()) {
                Registro_clasificacion_bol = true;
                if (IndexApp.TEMA == 1) {
                    Campo_registro_clasificacion.setStyle("-fx-border-color: #595b5d");
                } else {
                    Campo_registro_clasificacion.setStyle("-fx-border-color: black");
                }
            } else {
                Campo_registro_clasificacion.setStyle("-fx-border-color: red");
                Registro_clasificacion_bol = false;
            }
        }
    }

    //Campo Clave registro: Clave dada por la biblioteca a libros “etiqueta”
    public void validar_clave_registro() {
        if (Campo_clave_registro.isEditable()) {
            if (Campo_clave_registro.getText().matches("^[a-zA-Z0-9.-]{2,10}$") && !Campo_clave_registro.getText().isEmpty()) {
                Clave_registro_bol = true;
                if (IndexApp.TEMA == 1) {
                    Campo_clave_registro.setStyle("-fx-border-color: #595b5d");
                } else {
                    Campo_clave_registro.setStyle("-fx-border-color: black");
                }
            } else {
                Campo_clave_registro.setStyle("-fx-border-color: red");
                Clave_registro_bol = false;
            }
        }
    }

    //Caja de campo Estante: Letra mayuscúla = estante, numero = filas, pueden haber más de 26 estantes
    // en algun punto, por eso pueden haber de 1 a 3 letras separadas del guion y seguidas de dos digitos máximo
    // El formato minimo es A-1 formato máximo ZZ-99
    public void validar_estante() {
        if (Campo_estante.isEditable()) {
            if (Campo_estante.getText().matches("^[A-Z Ñ]{1,2}-[0-9]{1,2}$") && !Campo_estante.getText().isEmpty()) {
                Estante_bol = true;
                if (IndexApp.TEMA == 1) {
                    Campo_estante.setStyle("-fx-border-color: #595b5d");
                } else {
                    Campo_estante.setStyle("-fx-border-color: black");
                }
            } else {
                Campo_estante.setStyle("-fx-border-color: red");
                Estante_bol = false;
            }
        }
    }

    //Campo Existencias solo digitos enteros de hasta una cantidad de 4 cifras mayor a 0
    public void validar_existencias() {
        if (Campo_existencias.isEditable()) {
            if (Campo_existencias.getText().matches("^[1-9][0-9]{0,4}$") && !Campo_existencias.getText().isEmpty()) {
                Existencias_bol = true;
                if (IndexApp.TEMA == 1) {
                    Campo_existencias.setStyle("-fx-border-color: #595b5d");
                } else {
                    Campo_existencias.setStyle("-fx-border-color: black");
                }
            } else {
                Campo_existencias.setStyle("-fx-border-color: red");
                Existencias_bol = false;
            }
        }
    }

    //Campo Editorial Valida de 1 a n palabras con un margnen de 30 caracteres incluiodos los simbolos &.- y espacios con vocales con acentos
    public void validar_editorial() {
        if (Campo_editorial.isEditable()) {
            if (Campo_editorial.getText().matches("^[A-Za-z\\s&.-áéíóúñ]{1,30}$") && !Campo_editorial.getText().isEmpty()) {
                Editorial_bol = true;
                if (IndexApp.TEMA == 1) {
                    Campo_editorial.setStyle("-fx-border-color: #595b5d");
                } else {
                    Campo_editorial.setStyle("-fx-border-color: black");
                }
            } else {
                Campo_editorial.setStyle("-fx-border-color: red");
                Editorial_bol = false;
            }
        }
    }

    //Campo Lugar de edición Valida de 1 a 4 palabras con un margnen de 40 caracteres incluiodos el simolo - espacios, vocales con acentos
    public void validar_lugar_edicion() {
        if (Campo_lugar_edicion.isEditable()) {
            if (Campo_lugar_edicion.getText().matches("^[A-Z][a-záéíóú]+(\\s[A-Z][a-záéíóú]+){1,3}$") && !Campo_lugar_edicion.getText().isEmpty()) {
                Lugar_edicion_bol = true;
                if (IndexApp.TEMA == 1) {
                    Campo_lugar_edicion.setStyle("-fx-border-color: #595b5d");
                } else {
                    Campo_lugar_edicion.setStyle("-fx-border-color: black");
                }
            } else {
                Campo_lugar_edicion.setStyle("-fx-border-color: red");
                Lugar_edicion_bol = false;
            }
        }
    }

    //Campo Nombre del autor Valida de 1 a 4 palabras con un margnen de 40 caracteres incluiodos espacios, vocales con acentos
    public void validar_nombre_autor() {
        if (Campo_nombre_autor.isEditable()) {
            if (Campo_nombre_autor.getText().matches("^[A-Z][a-záéíóú]+(\\s[A-Z][a-záéíóú]+){1,3}$") && !Campo_nombre_autor.getText().isEmpty()) {
                Nombre_autor_bol = true;
                if (IndexApp.TEMA == 1) {
                    Campo_nombre_autor.setStyle("-fx-border-color: #595b5d");
                } else {
                    Campo_nombre_autor.setStyle("-fx-border-color: black");
                }
            } else {
                Campo_nombre_autor.setStyle("-fx-border-color: red");
                Nombre_autor_bol = false;
            }
        }
    }

    //Campo Título del libro Valida de 1 a n palabras con un margnen de 40 caracteres incluidos espacios, vocales con acentos
    public void validar_titulo_libro() {
        if (Campo_titulo_libro.isEditable()) {
            if (Campo_titulo_libro.getText().matches("^[A-Za-z\\sáéíóúñ]{1,40}$") && !Campo_titulo_libro.getText().isEmpty()) {
                Titulo_libro_bol = true;
                if (IndexApp.TEMA == 1) {
                    Campo_titulo_libro.setStyle("-fx-border-color: #595b5d");
                } else {
                    Campo_titulo_libro.setStyle("-fx-border-color: black");
                }
            } else {
                Campo_titulo_libro.setStyle("-fx-border-color: red");
                Titulo_libro_bol = false;
            }
        }
    }

    //Campo Año de edición valida que se escriba un año mayor a 1000 y menor a 2029
    public void validar_anio_edicion() {
        if (Campo_anio_edicion.isEditable()) {
            if (Campo_anio_edicion.getText().matches("^(1\\d{3}|20[0-2]\\d)$") && !Campo_anio_edicion.getText().isEmpty()) {
                Anio_edicion_bol = true;
                if (IndexApp.TEMA == 1) {
                    Campo_anio_edicion.setStyle("-fx-border-color: #595b5d");
                } else {
                    Campo_anio_edicion.setStyle("-fx-border-color: black");
                }
            } else {
                Campo_anio_edicion.setStyle("-fx-border-color: red");
                Anio_edicion_bol = false;
            }
        }
    }

    //Campo Descripción del libro Valida de 1 a n palabras con un margnen de 80 caracteres incliodo los simolos ., espacios, vocales con acentos
    public void validar_descripcion_libro() {
        if (Campo_descripcion_libro.isEditable()) {
            if (Campo_descripcion_libro.getText().matches("^[A-Za-z\\s.,áéíóúñ]{1,80}$") && !Campo_descripcion_libro.getText().isEmpty()) {
                Descripcion_libro_bol = true;
                if (IndexApp.TEMA == 1) {
                    Campo_descripcion_libro.setStyle("-fx-border-color: #595b5d");
                } else {
                    Campo_descripcion_libro.setStyle("-fx-border-color: black");
                }
            } else {
                Campo_descripcion_libro.setStyle("-fx-border-color: red");
                Descripcion_libro_bol = false;
            }
        }
    }


    /*
        Método que trae el valor seleccionado en el ComboBox para mostrar esa parte de la interfaz
     */
    @FXML
    void ObtenerCombo() {
        switch (comboBox.getValue()) {
            case "Agregar" -> crudLibro(1);
            case "Buscar" -> crudLibro(2);
            case "Editar" -> crudLibro(3);
            case "Eliminar" -> crudLibro(4);
        }
    }


    /*
        Método que actualiza la interfaz para mostrar la información de la operación seleccionada
     */
    private void crudLibro(int bandera) {
        switch (bandera) {
            case 1 -> {
                Label_cabecera.setText("Registro de Libro");
                Label_cabecera.setLayoutX(254.0);
                BotonGuardar.setLayoutX(317.0);
                BotonGuardar.setLayoutY(334.0);
                BotonGuardar.setText("Guardar");
                ActivarCampos();
            }
            case 2 -> {
                Label_cabecera.setText("Búsqueda de Libros");
                Label_cabecera.setLayoutX(221.0);
                BotonGuardar.setLayoutX(317.0);
                BotonGuardar.setLayoutY(334.0);
                BotonGuardar.setText("Buscar");
                DesactivarCampos();
            }
            case 3 -> {
                Label_cabecera.setText("Editar Libro");
                Label_cabecera.setLayoutX(254.0);
                BotonGuardar.setLayoutX(317.0);
                BotonGuardar.setLayoutY(334.0);
                BotonGuardar.setText("Editar");
                DesactivarCampos();
            }
            case 4 -> {
                Label_cabecera.setText("Eliminar Libro");
                Label_cabecera.setLayoutX(254.0);
                BotonGuardar.setLayoutX(317.0);
                BotonGuardar.setLayoutY(334.0);
                BotonGuardar.setText("Eliminar");
                DesactivarCampos();
            }
        }
    }

    /*
        Método que activa los campos previamente desactivados, dependiendo de la operación
     */
    private void ActivarCampos() {
        activarVisible();
        limpiarCampos();
    }

    /*
        Limpia el texto de los campos
     */
    private void limpiarCampos() {
        Campo_clasificacion.setText("");
        Campo_estante.setText("");
        Campo_registro_clasificacion.setText("");
        Campo_existencias.setText("");
        Campo_editorial.setText("");
        Campo_lugar_edicion.setText("");
        Campo_anio_edicion.setText("");
        Campo_nombre_autor.setText("");
        Campo_descripcion_libro.setText("");
        Campo_titulo_libro.setText("");
        Campo_clave_registro.setText("");
    }

    /*
        Método que desactiva los campos que no se utilizarán en la operación seleccionada en la interfaz
     */
    private void DesactivarCampos() {
        desactivarVisible();
        limpiarCampos();
        Label_cabecera.setLayoutX(221.0);
        Campo_titulo_libro.setEditable(true);
        Campo_titulo_libro.setLayoutX(160.0);
        Campo_titulo_libro.setLayoutY(262.0);
        Campo_titulo_libro.setPrefWidth(371);
    }


    /*
        Activa la visibilidad de los elementos en pantalla
     */
    private void activarVisible() {
        Separador_dos.setVisible(true);
        Separador_tres.setVisible(true);
        Separador_cuatro.setVisible(true);
        Label_datos_referencia.setVisible(true);
        Label_datos_del_libro.setVisible(true);
        Campo_clave_registro.setVisible(true);
        Campo_clasificacion.setVisible(true);
        Campo_estante.setVisible(true);
        Campo_registro_clasificacion.setVisible(true);
        Campo_existencias.setVisible(true);
        Campo_editorial.setVisible(true);
        Campo_lugar_edicion.setVisible(true);
        Campo_anio_edicion.setVisible(true);
        Campo_nombre_autor.setVisible(true);
        Campo_descripcion_libro.setVisible(true);
        BotonGuardar.setLayoutX(561.0);
        BotonGuardar.setLayoutY(519.0);
        Campo_titulo_libro.setLayoutX(386.0);
        Campo_titulo_libro.setLayoutY(307.0);
        Campo_titulo_libro.setPrefWidth(145.0);
    }

    /*
        Desactiva la visibilidad de los elementos en pantalla
     */
    private void desactivarVisible() {
        Separador_dos.setVisible(false);
        Separador_tres.setVisible(false);
        Separador_cuatro.setVisible(false);
        Label_datos_referencia.setVisible(false);
        Label_datos_del_libro.setVisible(false);
        Campo_clave_registro.setVisible(false);
        Campo_clasificacion.setVisible(false);
        Campo_estante.setVisible(false);
        Campo_registro_clasificacion.setVisible(false);
        Campo_existencias.setVisible(false);
        Campo_editorial.setVisible(false);
        Campo_lugar_edicion.setVisible(false);
        Campo_anio_edicion.setVisible(false);
        Campo_nombre_autor.setVisible(false);
        Campo_descripcion_libro.setVisible(false);
    }

    /*
        Dependiendo la bandera, revierte la interfaz a la principal y cambia las cabeceras, textos y la posibilidad de editar los
        campos de texto
        Habilitados los campos: Agregar(1) y Editar(3)
        Inhabilitados los campos: Buscar(2) y Eliminar(4)
     */
    private void crudEncontradosOriginal(int bandera) {
        activarVisible();
        switch (bandera) {
            case 1 -> {
                Label_cabecera.setText("Registro de Libros");
                Label_cabecera.setLayoutX(221.0);
                BotonGuardar.setText("Guardar");
                activarEditable();
            }
            case 2 -> {
                Label_cabecera.setText("Búsqueda de Libros");
                Label_cabecera.setLayoutX(221.0);
                BotonGuardar.setText("Buscar otro");
                desactivarEditable();
            }
            case 3 -> {
                Label_cabecera.setText("Edición de Libro");
                Label_cabecera.setLayoutX(221.0);
                BotonGuardar.setText("Editar libro");
                activarEditable();
            }
            case 4 -> {
                Label_cabecera.setText("Eliminar Libro");
                Label_cabecera.setLayoutX(221.0);
                BotonGuardar.setText("Eliminar otro");
                desactivarEditable();
            }
        }
    }


    /*
        Activa la edición de los campos de texto
     */
    void activarEditable() {
        Campo_clave_registro.setEditable(true);
        Campo_clasificacion.setEditable(true);
        Campo_estante.setEditable(true);
        Campo_registro_clasificacion.setEditable(true);
        Campo_existencias.setEditable(true);
        Campo_editorial.setEditable(true);
        Campo_anio_edicion.setEditable(true);
        Campo_nombre_autor.setEditable(true);
        Campo_descripcion_libro.setEditable(true);
        Campo_titulo_libro.setEditable(true);
    }

    /*
       Desactiva la edición de los campos de texto
    */
    void desactivarEditable() {
        Campo_clave_registro.setEditable(false);
        Campo_clasificacion.setEditable(false);
        Campo_estante.setEditable(false);
        Campo_registro_clasificacion.setEditable(false);
        Campo_existencias.setEditable(false);
        Campo_editorial.setEditable(false);
        Campo_anio_edicion.setEditable(false);
        Campo_nombre_autor.setEditable(false);
        Campo_descripcion_libro.setEditable(false);
        Campo_titulo_libro.setEditable(false);
    }


    /*
      Inicializa los elementos de la interfaz
      ComboBox: Inicializa los ítems del ComboBox
      Campos de Texto: Inicializa los TextFormatter para limitar los caracteres
   */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        comboBox.getItems().addAll("Agregar", "Buscar", "Editar", "Eliminar");

        //Caja de campo Clasificación rango de 15 caracteres
        Campo_clasificacion.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() > 15) {
                return null;
            }
            return change;
        }));
        //Campo Registro de clasificación rango de 3 caracteres
        Campo_registro_clasificacion.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() > 3) {
                return null;
            }
            return change;
        }));
        //Caja de campo Clave de registro rango de 10 caracteres
        Campo_clave_registro.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() > 10) {
                return null;
            }
            return change;
        }));
        //Caja de campo Estante rango de 5 caracteres
        Campo_estante.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() > 5) {
                return null;
            }
            return change;
        }));
        //Caja de campo Existecias rango de 4 caracteres
        Campo_existencias.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() > 4) {
                return null;
            }
            return change;
        }));
        //Caja de campo Editorial rango de 30 caracteres
        Campo_editorial.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() > 30) {
                return null;
            }
            return change;
        }));
        //Caja de campo Lugar de edición rango de 40 caracteres
        Campo_lugar_edicion.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() > 40) {
                return null;
            }
            return change;
        }));
        //Caja de campo Nombre del autor rango de 40 caracteres
        Campo_nombre_autor.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() > 40) {
                return null;
            }
            return change;
        }));
        //Caja de campo Título del libro rango de 40 caracteres
        Campo_titulo_libro.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() > 40) {
                return null;
            }
            return change;
        }));
        //Caja de campo Año de edición  rango de 4 caracteres
        Campo_anio_edicion.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() > 4) {
                return null;
            }
            return change;
        }));
        //Caja de campo Descripción del libro rango de 80 caracteres
        Campo_descripcion_libro.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() > 80) {
                return null;
            }
            return change;
        }));
    }
}


