package com.hardbug.bibliotecatenango;


/*Clase que controla la alta de libros
  Vista a la que está asociada la clase: AltaLibrosView.fxml
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.IntegerStringConverter;

import javax.swing.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

/* Clase AltaLibros:
    Su funcionalidad es recibir la información de su vista ligada, tratar la información y mandarla a base de datos
 */

public class AltaLibrosController implements Initializable {

    public static ArrayList<Object> datosLibro = new ArrayList<>();

    private static boolean Clasificacion_bol, Anio_edicion_bol, Descripcion_libro_bol, Nombre_autor_bol,
            Titulo_libro_bol, Lugar_edicion_bol, Editorial_bol, Registro_clasificacion_bol, Estante_bol,
            Existencias_bol, Clave_registro_bol;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private Separator Separador_uno, Separador_dos, Separador_tres, Separador_cuatro, Separador_cinco;
    @FXML
    private Label Label_cabecera, Label_datos_del_libro, Label_datos_referencia;

    private ClaseLibro libro = new ClaseLibro();
    private IndexApp indexApp = new IndexApp();
    private BDController bdController = new BDController();

    @FXML
    private Button Boton_Modo, BotonGuardar;
    @FXML
    private TextField Campo_clasificacion, Campo_anio_edicion, Campo_registro_clasificacion, Campo_estante,
            Campo_existencias, Campo_editorial, Campo_lugar_edicion, Campo_titulo_libro, Campo_nombre_autor, Campo_descripcion_libro,
            Campo_clave_registro;
    public AnchorPane Fondo;

    /*
        Método que dependiendo el texto del botón, se realiza la acción
        Guardar: Invoca el método de guardar el libro
        Buscar, Editar, Eliminar: Invoca a desactivar gran parte de la interfaz para hacer su operación
        Buscar otro, Editar otro, Eliminar otro: Revierten la interfaz para visualizar la información
     */
    @FXML
    void AccionesBotonGuardar() throws SQLException {
        switch (BotonGuardar.getText()) {
            case "Guardar":
                GuardarLibro();
                break;
            case "Buscar":
                BuscarLibro(0);
                break;
            case "Buscar otro":
                crudLibro(2);
                break;
            case "Editar":
                BuscarLibro(1);
                break;
            case "Editar libro":
                EditarLibro();
                break;
            case "Eliminar":
                CrearAlerta(4);
                break;
            case "Eliminar otro":
                crudLibro(4);
                break;
        }
    }

    void CrearAlerta(int tipo) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        DialogPane dialogPane = alert.getDialogPane();
        alert.setHeaderText(null);
        if (IndexApp.TEMA == 0) {
            dialogPane.setStyle("-fx-background-color: white; -fx-text-fill: black");
        } else {
            dialogPane.setStyle("-fx-background-color: #2b2b2b; -fx-text-fill: white");
        }
        switch (tipo) {
            case 0:
                alert.setTitle("¡Ocurrió un error!");
                alert.setContentText("Error al guardar en base de datos");
                alert.showAndWait();
                break;
            case 1:
                alert.setTitle("¡Libro guardado con éxito!");
                alert.setContentText("El libro " + libro.getTitulo_libro() + " ha sido registrado :)");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("¡Libro no encontrado!");
                alert.setContentText("No se encontró el libro, prueba checando la ortografía :)");
                Campo_titulo_libro.setText("");
                alert.showAndWait();
                break;
            case 3:
                alert.setTitle("¡Campos inválidos!");
                alert.setContentText("Error en procesar la información, verifica que los campos estén llenados de forma correcta");
                alert.showAndWait();
                break;
            case 4:
                if (!Campo_titulo_libro.getText().isEmpty()) {
                    Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                    DialogPane dialogPane1 = alert.getDialogPane();
                    alert1.setHeaderText(null);
                    alert1.setTitle("¡Cuidado!");
                    alert1.setContentText("¿Estás seguro de eliminar el libro: " + Campo_titulo_libro.getText() + "?");
                    if (IndexApp.TEMA == 0) {
                        dialogPane1.setStyle("-fx-background-color: white; -fx-text-fill: white");
                    } else {
                        dialogPane1.setStyle("-fx-background-color: #2b2b2b; -fx-text-fill: white");
                    }
                    Optional<ButtonType> result = alert1.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        EliminarLibro();
                    } else {
                        alert1.close();
                    }
                }
                break;
            case 5:
                alert.setTitle("Eliminación realizada");
                alert.setContentText("Se ha eliminado el libro de manera correcta");
                Optional<ButtonType> result1 = alert.showAndWait();
                if (result1.isPresent() && result1.get() == ButtonType.OK) {
                    alert.close();
                    crudLibro(4);
                }
                break;
            case 6:
                alert.setTitle("¡Libro editado con éxito!");
                alert.setContentText("El libro " + libro.getTitulo_libro() + " ha sido editado :)");
                alert.showAndWait();
                break;
        }
    }

    @FXML
    void GuardarLibro() throws SQLException {
        if (camposValidos()) {
            boolean guardado = bdController.InsertarLibro(libro.getClave_registro(), libro.getEstante(), libro.getDescripcion_libro(), libro.getExistencias(),
                    libro.getTitulo_libro(), libro.getAnio_edicion(), libro.getNombre_autor(), libro.getClasificacion(),
                    libro.getRegistro_clasificacion(), libro.getEditorial(), libro.getLugar_edicion());
            if (guardado) {
                CrearAlerta(1);
                limpiarCampos();
            } else {
                CrearAlerta(0);
            }
        } else {
            CrearAlerta(3);
        }
    }

    void EditarLibro() throws SQLException {
        if (camposValidos()) {
            boolean editado = bdController.EditarLibro(libro.getClave_registro(), libro.getEstante(), libro.getDescripcion_libro(), libro.getExistencias(),
                    libro.getTitulo_libro(), libro.getAnio_edicion(), libro.getNombre_autor(), libro.getClasificacion(),
                    libro.getRegistro_clasificacion(), libro.getEditorial(), libro.getLugar_edicion());
            if (editado) {
                CrearAlerta(6);
                limpiarCampos();
            } else {
                CrearAlerta(0);
            }
        } else {
            CrearAlerta(3);
        }
    }

    void EliminarLibro() throws SQLException {
        Boolean eliminado = bdController.BorrarLibro(Campo_titulo_libro.getText());
        if (eliminado) {
            CrearAlerta(5);
        }
    }

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
            }else{
                crudEncontradosOriginal(3);
            }
        } else {
            CrearAlerta(2);
        }
    }


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

    // Activación del tema de las interfaces
    @FXML
    void ActivarModoOscuro(ActionEvent event) {
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
    public void validar_clasificacion(javafx.scene.input.KeyEvent keyEvent) {
        if (Campo_clasificacion.isEditable()) {
            if (Campo_clasificacion.getText().matches("^[A-Z.\s][A-Za-z\s\\á\\é\\í\\ó\\ú\\ñ]{4,15}") && !Campo_clasificacion.getText().isEmpty()) {
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
    public void validar_registro_clasificacion(KeyEvent keyEvent) {
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
    public void validar_clave_registro(javafx.scene.input.KeyEvent keyEvent) {
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
    public void validar_estante(KeyEvent keyEvent) {
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
    public void validar_existencias(KeyEvent keyEvent) {
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
    public void validar_editorial(KeyEvent keyEvent) {
        if (Campo_editorial.isEditable()) {
            if (Campo_editorial.getText().matches("^[A-Za-z\\s&.-\\á\\é\\í\\ó\\ú\\ñ]{1,30}$") && !Campo_editorial.getText().isEmpty()) {
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
    public void validar_lugar_edicion(KeyEvent keyEvent) {
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
    public void validar_nombre_autor(KeyEvent keyEvent) {
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
    public void validar_titulo_libro(KeyEvent keyEvent) {
        if (Campo_titulo_libro.isEditable()) {
            if (Campo_titulo_libro.getText().matches("^[A-Za-z\\s\\á\\é\\í\\ó\\ú\\ñ]{1,40}$") && !Campo_titulo_libro.getText().isEmpty()) {
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
    public void validar_anio_edicion(KeyEvent keyEvent) {
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
    public void validar_descripcion_libro(KeyEvent keyEvent) {
        if (Campo_descripcion_libro.isEditable()) {
            if (Campo_descripcion_libro.getText().matches("^[A-Za-z\\s.,\\á\\é\\í\\ó\\ú\\ñ]{1,80}$") && !Campo_descripcion_libro.getText().isEmpty()) {
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


    @FXML
    void ObtenerCombo() {
        switch (comboBox.getValue()) {
            case "Agregar":
                crudLibro(1);
                break;
            case "Buscar":
                crudLibro(2);
                break;
            case "Editar":
                crudLibro(3);
                break;
            case "Eliminar":
                crudLibro(4);
                break;
        }
    }


    private void crudLibro(int bandera) {
        switch (bandera) {
            case 1:
                Label_cabecera.setText("Registro de Libro");
                Label_cabecera.setLayoutX(254.0);
                BotonGuardar.setLayoutX(317.0);
                BotonGuardar.setLayoutY(334.0);
                BotonGuardar.setText("Guardar");
                ActivarCampos();
                break;
            case 2:
                Label_cabecera.setText("Búsqueda de Libros");
                Label_cabecera.setLayoutX(221.0);
                BotonGuardar.setLayoutX(317.0);
                BotonGuardar.setLayoutY(334.0);
                BotonGuardar.setText("Buscar");
                DesactivarCampos();
                break;
            case 3:
                Label_cabecera.setText("Editar Libro");
                Label_cabecera.setLayoutX(254.0);
                BotonGuardar.setLayoutX(317.0);
                BotonGuardar.setLayoutY(334.0);
                BotonGuardar.setText("Editar");
                DesactivarCampos();
                break;
            case 4:
                Label_cabecera.setText("Eliminar Libro");
                Label_cabecera.setLayoutX(254.0);
                BotonGuardar.setLayoutX(317.0);
                BotonGuardar.setLayoutY(334.0);
                BotonGuardar.setText("Eliminar");
                DesactivarCampos();
                break;
        }
    }

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
            case 1:
                Label_cabecera.setText("Registro de Libros");
                Label_cabecera.setLayoutX(221.0);
                BotonGuardar.setText("Guardar");
                activarEditable();
                break;
            case 2:
                Label_cabecera.setText("Búsqueda de Libros");
                Label_cabecera.setLayoutX(221.0);
                BotonGuardar.setText("Buscar otro");
                desactivarEditable();
                break;
            case 3:
                Label_cabecera.setText("Edición de Libro");
                Label_cabecera.setLayoutX(221.0);
                BotonGuardar.setText("Editar libro");
                activarEditable();
                break;
            case 4:
                Label_cabecera.setText("Eliminar Libro");
                Label_cabecera.setLayoutX(221.0);
                BotonGuardar.setText("Eliminar otro");
                desactivarEditable();
                break;
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
      Inicializa el combobox para que cargue los ítems
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


