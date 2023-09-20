package com.hardbug.bibliotecatenango;


/*Clase que controla la alta de libros
  Vista a la que está asociada la clase: AltaLibrosView.fxml
 */

import com.hardbug.bibliotecatenango.Models.Libro;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
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


    /* Este arraylist almacena la información del libro consultado */
    public static ArrayList<Object> datosLibro = new ArrayList<>();

    /* Variables booleanas que marcan si los campos están correctos */
    private static boolean Clasificacion_bol, Anio_edicion_bol, Descripcion_libro_bol, Nombre_autor_bol,
            Titulo_libro_bol, Lugar_edicion_bol, Editorial_bol, Registro_clasificacion_bol, Estante_bol,
            Existencias_bol, Clave_registro_bol;
    @FXML
    private Button BotonGuardar;
    @FXML
    private TextField Campo_clasificacion, Campo_anio_edicion, Campo_registro_clasificacion, Campo_estante,
            Campo_existencias, Campo_editorial, Campo_lugar_edicion, Campo_titulo_libro, Campo_nombre_autor, Campo_descripcion_libro,
            Campo_clave_registro;
    @FXML
    private ImageView Clasificacion_info, Estante_info, Clave_registro_info, Existencias_info, Editorial_info,
            Lugar_edicion_info, Anio_edicion_info, Nombre_autor_info, Titulo_info, Descripcion_info,
            Registro_clasificacion_info;


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
        validar_todo();
        GuardarLibro();
    }


    /*
        Método que manda guardar a Base de Datos el libro, y valida los campos
     */
    @FXML
    void GuardarLibro() {
        Alert alert;
        if (camposValidos()) {
            boolean guardado = bdController.InsertarLibro(libro.getClave_registro(), libro.getEstante(), libro.getDescripcion_libro(), libro.getExistencias(),
                    libro.getTitulo_libro(), libro.getAnio_edicion(), libro.getNombre_autor(), libro.getClasificacion(),
                    libro.getRegistro_clasificacion(), libro.getEditorial(), libro.getLugar_edicion());
            if (guardado) {
                 alert  = new Alertas().CrearAlertaInformativa("Libro guardado", "El libro <" + libro.getTitulo_libro() + "> se ha guardado con éxito en el sistema");
                 alert.showAndWait();
                cerrarModalMenuLibros();
                limpiarCampos();
            } else {
                alert = new Alertas().CrearAlertaError("Error al guardar", "Ha ocurrido un error al guardar el libro");
                alert.showAndWait();
            }
        } else {
            alert = new Alertas().CrearAlertaError("Campos inválidos", "Campos inválidos, revisa que estés generando la información correctamente");
            alert.showAndWait();
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
                Clasificacion_info.setVisible(false);
                if (IndexApp.TEMA == 1) {
                    Campo_clasificacion.setStyle("-fx-border-color: #595b5d");
                } else {
                    Campo_clasificacion.setStyle("-fx-border-color: black");
                }
            } else {
                Campo_clasificacion.setStyle("-fx-border-color: red");
                Clasificacion_bol = false;
                Tooltip.install(Clasificacion_info,crearTooltip("La clasificación debe empezar con mayuscula y no debe de contener números"+" \n"+"Ejemplos: Historia, Ciencias, Matemáticas, etc."));
                Clasificacion_info.setVisible(true);
                Clasificacion_info.setPickOnBounds(true);

            }
        }
    }

    public Tooltip crearTooltip(String mensaje){
        Tooltip tooltip = new Tooltip(mensaje);
        tooltip.setShowDuration(Duration.INDEFINITE);
        tooltip.setShowDelay(Duration.millis(500));
        tooltip.setFont(new Font("Roboto Light", 14));
        return tooltip;
    }

    public void Tooltipfalse(){
        Nombre_autor_info.setVisible(false);
        Clasificacion_info.setVisible(false);
        Anio_edicion_info.setVisible(false);
        Clave_registro_info.setVisible(false);
        Descripcion_info.setVisible(false);
        Editorial_info.setVisible(false);
        Existencias_info.setVisible(false);
        Estante_info.setVisible(false);
        Lugar_edicion_info.setVisible(false);
        Titulo_info.setVisible(false);
        Registro_clasificacion_info.setVisible(false);
    }

    public void validar_todo(){
        validar_clasificacion();
        validar_anio_edicion();
        validar_clave_registro();
        validar_descripcion_libro();
        validar_editorial();
        validar_existencias();
        validar_estante();
        validar_lugar_edicion();
        validar_nombre_autor();
        validar_registro_clasificacion();
        validar_titulo_libro();
    }

    //Campo Registro de clasificación: Del 000 al 900 o S/C
    //Debe de contener 3 digitos obligatorios o S/C =Sin Clasificación
    public void validar_registro_clasificacion() {
        if (Campo_registro_clasificacion.isEditable()) {
            if (Campo_registro_clasificacion.getText().matches("S/C") || Campo_registro_clasificacion.getText().matches("^\\d{3}$") && !Campo_registro_clasificacion.getText().isEmpty()) {
                Registro_clasificacion_bol = true;
                Registro_clasificacion_info.setVisible(false);
                if (IndexApp.TEMA == 1) {
                    Campo_registro_clasificacion.setStyle("-fx-border-color: #595b5d");
                } else {
                    Campo_registro_clasificacion.setStyle("-fx-border-color: black");
                }
            } else {
                Campo_registro_clasificacion.setStyle("-fx-border-color: red");
                Registro_clasificacion_bol = false;
                Tooltip.install(Registro_clasificacion_info,crearTooltip("El registro de clasificación debe de ser de 3 digitos"+" \n"+"Ejemplos: 000, 100, 200, 300, 400, 500, 600, 700, 800, 900"));
                Registro_clasificacion_info.setVisible(true);
                Registro_clasificacion_info.setPickOnBounds(true);
            }
        }
    }

    //Campo Clave registro: Clave dada por la biblioteca a libros “etiqueta”
    public void validar_clave_registro() {
        if (Campo_clave_registro.isEditable()) {
            if (Campo_clave_registro.getText().matches("^[a-zA-Z0-9.-]{2,10}$") && !Campo_clave_registro.getText().isEmpty()) {
                Clave_registro_bol = true;
                Clave_registro_info.setVisible(false);
                if (IndexApp.TEMA == 1) {
                    Campo_clave_registro.setStyle("-fx-border-color: #595b5d");
                } else {
                    Campo_clave_registro.setStyle("-fx-border-color: black");
                }
            } else {
                Campo_clave_registro.setStyle("-fx-border-color: red");
                Clave_registro_bol = false;
                Tooltip.install(Clave_registro_info,crearTooltip("La clave de registro puede contener una letra (mayúscula o minúscula) puntos,guiones medios y numeros ademas de que debe de ser de 2 a 10 caracteres"+" \n"+"Ejemplos: A-30, AZ-2, aX-01"));
                Clave_registro_info.setVisible(true);
                Clave_registro_info.setPickOnBounds(true);
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
                Estante_info.setVisible(false);
                if (IndexApp.TEMA == 1) {
                    Campo_estante.setStyle("-fx-border-color: #595b5d");
                } else {
                    Campo_estante.setStyle("-fx-border-color: black");
                }
            } else {
                Campo_estante.setStyle("-fx-border-color: red");
                Estante_bol = false;
                Tooltip.install(Estante_info,crearTooltip("El estante debe de ser de 1 a 2 letras mayúsculas seguidas de un guión medio y un numero del 1 al 5"+" \n"+"Ejemplos: A-1, B-2, AA-3, ZZ-5"));
                Estante_info.setVisible(true);
                Estante_info.setPickOnBounds(true);
            }
        }
    }

    //Campo Existencias solo digitos enteros de hasta una cantidad de 4 cifras mayor a 0
    public void validar_existencias() {
        if (Campo_existencias.isEditable()) {
            if (Campo_existencias.getText().matches("^[1-9][0-9]{0,4}$") && !Campo_existencias.getText().isEmpty()) {
                Existencias_bol = true;
                Existencias_info.setVisible(false);
                if (IndexApp.TEMA == 1) {
                    Campo_existencias.setStyle("-fx-border-color: #595b5d");
                } else {
                    Campo_existencias.setStyle("-fx-border-color: black");
                }
            } else {
                Campo_existencias.setStyle("-fx-border-color: red");
                Existencias_bol = false;
                Tooltip.install(Existencias_info,crearTooltip("Las existencias deben de ser de 1 a 4 digitos y no puede empezar con 0"+" \n"+"Ejemplos: 1, 10, 100, 1000, 9999"));
                Existencias_info.setVisible(true);
                Existencias_info.setPickOnBounds(true);
            }
        }
    }

    //Campo Editorial Valida de 1 a n palabras con un margnen de 30 caracteres incluiodos los simbolos &.- y espacios con vocales con acentos
    public void validar_editorial() {
        if (Campo_editorial.isEditable()) {
            if (Campo_editorial.getText().matches("^[A-Za-z\\s&.-áéíóúñ]{1,30}$") && !Campo_editorial.getText().isEmpty()) {
                Editorial_bol = true;
                Editorial_info.setVisible(false);
                if (IndexApp.TEMA == 1) {
                    Campo_editorial.setStyle("-fx-border-color: #595b5d");
                } else {
                    Campo_editorial.setStyle("-fx-border-color: black");
                }
            } else {
                Campo_editorial.setStyle("-fx-border-color: red");
                Editorial_bol = false;
                Tooltip.install(Editorial_info,crearTooltip("La editorial debe de ser de 1 a n palabras con un margnen de 30 caracteres incluiodos los simbolos &.- y espacios con vocales con acentos"+" \n"+"Ejemplos: Editorial & Co, Editorial & Co. S.A., Editorial & Co. S.A. de C.V."));
                Editorial_info.setVisible(true);
                Editorial_info.setPickOnBounds(true);
            }
        }
    }

    //Campo Lugar de edición Valida de 1 a 4 palabras con un margnen de 40 caracteres incluiodos el simolo - espacios, vocales con acentos
    public void validar_lugar_edicion() {
        if (Campo_lugar_edicion.isEditable()) {
            if (Campo_lugar_edicion.getText().matches("^[A-Z][a-záéíóú]+([,.]?\\s[A-Z][a-záéíóú,.]+){0,3}$") && !Campo_lugar_edicion.getText().isEmpty()) {
                Lugar_edicion_bol = true;
                Lugar_edicion_info.setVisible(false);
                if (IndexApp.TEMA == 1) {
                    Campo_lugar_edicion.setStyle("-fx-border-color: #595b5d");
                } else {
                    Campo_lugar_edicion.setStyle("-fx-border-color: black");
                }
            } else {
                Campo_lugar_edicion.setStyle("-fx-border-color: red");
                Lugar_edicion_bol = false;
                Tooltip.install(Lugar_edicion_info,crearTooltip("El lugar de edición debe de ser de 1 a 4 palabras que empiecen con mayúscula con un margen de 40 caracteres incluidos el simolo - espacios, vocales con acentos"+" \n"+"Ejemplos: Ciudad de México, México"));
                Lugar_edicion_info.setVisible(true);
                Lugar_edicion_info.setPickOnBounds(true);
            }
        }
    }

    //Campo Nombre del autor Valida de 1 a 4 palabras con un margnen de 40 caracteres incluiodos espacios, vocales con acentos
    public void validar_nombre_autor() {
        if (Campo_nombre_autor.isEditable()) {
            if (Campo_nombre_autor.getText().matches("^[A-Z][a-záéíóú]+(\\s[A-Z][a-záéíóú]+){1,3}$") && !Campo_nombre_autor.getText().isEmpty()) {
                Nombre_autor_bol = true;
                Nombre_autor_info.setVisible(false);
                if (IndexApp.TEMA == 1) {
                    Campo_nombre_autor.setStyle("-fx-border-color: #595b5d");
                } else {
                    Campo_nombre_autor.setStyle("-fx-border-color: black");
                }
            } else {
                Campo_nombre_autor.setStyle("-fx-border-color: red");
                Nombre_autor_bol = false;
                Tooltip.install(Nombre_autor_info,crearTooltip("El nombre del autor debe de ser de 1 a 4 palabras que empiecen con mayúscula con un margen de 40 caracteres incluidos espacios, vocales con acentos"+" \n"+"Ejemplos: Juan Pérez, Juan Pérez Sánchez Hernández"));
                Nombre_autor_info.setVisible(true);
                Nombre_autor_info.setPickOnBounds(true);
            }
        }
    }

    //Campo Título del libro Valida de 1 a n palabras con un margnen de 40 caracteres incluidos espacios, vocales con acentos
    public void validar_titulo_libro() {
        if (Campo_titulo_libro.isEditable()) {
            if (Campo_titulo_libro.getText().matches("^[A-Za-z\\sáéíóúñ]{1,40}$") && !Campo_titulo_libro.getText().isEmpty()) {
                Titulo_libro_bol = true;
                Titulo_info.setVisible(false);
                if (IndexApp.TEMA == 1) {
                    Campo_titulo_libro.setStyle("-fx-border-color: #595b5d");
                } else {
                    Campo_titulo_libro.setStyle("-fx-border-color: black");
                }
            } else {
                Campo_titulo_libro.setStyle("-fx-border-color: red");
                Titulo_libro_bol = false;
                Tooltip.install(Titulo_info,crearTooltip("El título debe empezar con mayúsculas puede contener caracteres como: espacios, vocales con acentos"+" \n"+"Ejemplos: El principito, El principito y el zorro"));
                Titulo_info.setVisible(true);
                Titulo_info.setPickOnBounds(true);
            }
        }
    }

    //Campo Año de edición valida que se escriba un año mayor a 1000 y menor a 2029
    public void validar_anio_edicion() {
        if (Campo_anio_edicion.isEditable()) {
            if (Campo_anio_edicion.getText().matches("^(1\\d{3}|20[0-9]\\d)$") && !Campo_anio_edicion.getText().isEmpty()) {
                Anio_edicion_bol = true;
                Anio_edicion_info.setVisible(false);
                if (IndexApp.TEMA == 1) {
                    Campo_anio_edicion.setStyle("-fx-border-color: #595b5d");
                } else {
                    Campo_anio_edicion.setStyle("-fx-border-color: black");
                }
            } else {
                Campo_anio_edicion.setStyle("-fx-border-color: red");
                Anio_edicion_bol = false;
                Tooltip.install(Anio_edicion_info,crearTooltip("El año de edición debe de ser de 4 digitos y debe de ser mayor a 1000 y menor a 2099"+" \n"+"Ejemplos: 1000, 2029, 2019"));
                Anio_edicion_info.setVisible(true);
                Anio_edicion_info.setPickOnBounds(true);
            }
        }
    }

    //Campo Descripción del libro Valida de 1 a n palabras con un margnen de 80 caracteres incliodo los simolos ., espacios, vocales con acentos
    public void validar_descripcion_libro() {
        if (Campo_descripcion_libro.isEditable()) {
            if (Campo_descripcion_libro.getText().matches("^[A-Za-z\\s.,áéíóúñ]{1,80}$") && !Campo_descripcion_libro.getText().isEmpty()) {
                Descripcion_libro_bol = true;
                Descripcion_info.setVisible(false);
                if (IndexApp.TEMA == 1) {
                    Campo_descripcion_libro.setStyle("-fx-border-color: #595b5d");
                } else {
                    Campo_descripcion_libro.setStyle("-fx-border-color: black");
                }
            } else {
                Campo_descripcion_libro.setStyle("-fx-border-color: red");
                Descripcion_libro_bol = false;
                Tooltip.install(Descripcion_info,crearTooltip("La descripción debe de ser de 1 a  80 puede incluir caracteres como: los simolos ., espacios, vocales con acentos"+" \n"+"Ejemplos: El principito es un libro de literatura infantil, El principito es un libro de literatura infantil escrito por Antoine de Saint-Exupéry"));
                Descripcion_info.setVisible(true);
                Descripcion_info.setPickOnBounds(true);
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
        Tooltipfalse();

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
}


