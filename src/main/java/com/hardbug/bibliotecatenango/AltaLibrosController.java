package com.hardbug.bibliotecatenango;


/*Clase que controla la alta de libros
  Vista a la que está asociada la clase: AltaLibrosView.fxml
 */

import com.hardbug.bibliotecatenango.Models.Libro;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

/* Clase AltaLibros:
    Su funcionalidad es recibir la información de su vista ligada, tratar la información y mandarla a base de datos
 */

public class AltaLibrosController implements Initializable {

    public AltaLibrosController(int OPERACION) {
        this.OPERACION = OPERACION;
    }

    public AltaLibrosController(){

    }

    private int OPERACION;

    private static final int INSERTAR = 0, EDITAR = 1, ELIMINAR = 2;


    /* Este arraylist almacena la información del libro consultado */
    public static ArrayList<Object> datosLibro = new ArrayList<>();

    /* Variables booleanas que marcan si los campos están correctos */
    private static boolean Clasificacion_bol, Anio_edicion_bol, Descripcion_libro_bol, Nombre_autor_bol,
            Titulo_libro_bol, Lugar_edicion_bol, Editorial_bol, Registro_clasificacion_bol, Estante_bol,
            Existencias_bol, Clave_registro_bol;

    /* Variables traidas desde le FXML y que se necesitan*/
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
    private final Libro libro = new Libro();
    private final IndexApp indexApp = new IndexApp();
    private final BDController bdController = new BDController();

    private BuscadorLibrosController buscadorLibrosController;
    private MenuLibrosController menuLibrosController;
    private Stage modalStage;

    public void setBuscadorLibrosController(BuscadorLibrosController buscadorLibrosController){
        this.buscadorLibrosController = buscadorLibrosController;
    }

    public void setMenuLibrosController(MenuLibrosController menuLibrosController){
        this.menuLibrosController = menuLibrosController;
    }
    public void setModalStage(Stage modalStage) {
        this.modalStage = modalStage;
    }


    /*
        Método que dependiendo el texto del botón, se realiza la acción
        Guardar: Invoca el método de guardar el libro
        Buscar, Editar, Eliminar: Invoca a desactivar gran parte de la interfaz para hacer su operación
        Buscar otro, Editar libro, Eliminar otro: Revierten la interfaz para visualizar la información
     */
    @FXML
    void AccionesBotonGuardar() throws SQLException {
        GuardarLibro();
    }

    /*
        Método que crea la alerta, dependiendo el tipo de alerta que recibe es la información que muestra
     */
    void CrearAlerta(int TIPO_ALERTA) throws SQLException {
        switch (TIPO_ALERTA) {
            case 0 -> aplicarTemaAlerta("¡Ocurrió un error!", "Error al guardar en base de datos", 0);
            case 1 ->
                    aplicarTemaAlerta("¡Libro guardado con éxito!", "El libro " + libro.getTitulo_libro() + " ha sido registrado :)", 0);
            case 2 ->
                    aplicarTemaAlerta("¡Libro no encontrado!", "No se encontró el libro, prueba checando la ortografía :)", 0);
            case 3 ->
                    aplicarTemaAlerta("¡Campos inválidos!", "Error en procesar la información, verifica que los campos estén llenados de forma correcta", 0);
        }
    }


    /*
        Método que aplica el tema dependiendo el seleccionado y también aplica el texto y la cabecera
     */
    static void aplicarTemaAlerta(String titulo, String contenido, int tipo) throws SQLException {
        Alert alerta;
        if (tipo == 2) {
            alerta = new Alert(Alert.AlertType.CONFIRMATION);
        } else {
            alerta = new Alert(Alert.AlertType.INFORMATION);
        }
        DialogPane dialogPane = alerta.getDialogPane();
        Stage stage = (Stage) dialogPane.getScene().getWindow();
        stage.getIcons().add(new Image(Objects.requireNonNull(BookDetailsController.class.getResourceAsStream("/assets/logotenangoNR.png"))));
        Label content = new Label(alerta.getContentText());
        alerta.setHeaderText(null);
        alerta.setTitle(titulo);
        content.setText(contenido);
        if (IndexApp.TEMA == 0) {
            dialogPane.setStyle("-fx-background-color: white; -fx-text-fill: white");
            content.setTextFill(Color.BLACK);
            alerta.getDialogPane().setContent(content);
        } else {
            dialogPane.setStyle("-fx-background-color: #2b2b2b; -fx-text-fill: white");
            content.setTextFill(Color.WHITESMOKE);
            alerta.getDialogPane().setContent(content);
        }
        alerta.showAndWait();
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
                cerrarModalMenuLibros();
                limpiarCampos();
            } else {
                CrearAlerta(ALERTA_ERROR);
            }
        } else {
            CrearAlerta(ALERTA_CAMPOS_INVALIDOS);
        }
    }
    /*
        Método que valida los campos y manda a escribir en el objeto libro
     */
    boolean camposValidos() {
        if (Clasificacion_bol && Anio_edicion_bol && Descripcion_libro_bol && Nombre_autor_bol &&
                Titulo_libro_bol && Lugar_edicion_bol && Editorial_bol && Registro_clasificacion_bol && Estante_bol &&
                Existencias_bol && Clave_registro_bol) {
            llenarLibro();
            return true;
        } else {
            return false;
        }
    }

    /*
    Función para llenar el libro
     */

    private void llenarLibro() {
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
            if (Campo_estante.getText().matches("^[A-Z]{1,2}-[1-5]$") && !Campo_estante.getText().isEmpty()) {
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
            if (Campo_lugar_edicion.getText().matches("^[A-Z][a-záéíóú]+([,.]?\\s[A-Z][a-záéíóú,.]+){0,3}$") && !Campo_lugar_edicion.getText().isEmpty()) {

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
      Inicializa los elementos de la interfaz
      ComboBox: Inicializa los ítems del ComboBox
      Campos de Texto: Inicializa los TextFormatter para limitar los caracteres
   */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
        //Caja de campo Estante rango de 4 caracteres
        Campo_estante.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() > 4) {
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

    private void cerrarModalMenuLibros() {
        if (modalStage != null) {
            menuLibrosController.configurarLista();
            modalStage.close();
        }
    }

    public static class RegistroEntradaController {
    }
}


