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
import java.util.Date;
import java.util.ResourceBundle;

/* Clase AltaLibros:
    Su funcionalidad es recibir la información de su vista ligada, tratar la información y mandarla a base de datos
 */

public class AltaLibrosController implements Initializable {

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
                crudEncontradosOriginal(2);
                break;
            case "Buscar otro":
                crudLibro(2);
                break;
            case "Editar":
                crudEncontradosOriginal(3);
                break;
            case "Editar otro":
                crudLibro(3);
                break;
            case "Eliminar":
                crudEncontradosOriginal(4);
                break;
            case "Eliminar otro":
                crudLibro(4);
                break;
        }
    }


    @FXML
    void GuardarLibro() throws SQLException {
        if (camposValidos()) {
            bdController.InsertarLibro(libro.getClave_registro(), libro.getEstante(), libro.getDescripcion_libro(), libro.getExistencias(),
                        libro.getTitulo_libro(), libro.getAnio_edicion(), libro.getNombre_autor(), libro.getClasificacion(),
                        libro.getRegistro_clasificacion(), libro.getEditorial(), libro.getLugar_edicion());
        } else {
            System.out.println("Error en el llenado de los campos");
        }
    }


    boolean camposValidos() {
        if (Clasificacion_bol && Anio_edicion_bol && Descripcion_libro_bol && Nombre_autor_bol &&
                Titulo_libro_bol && Lugar_edicion_bol && Editorial_bol && Registro_clasificacion_bol && Estante_bol &&
                Existencias_bol && Clave_registro_bol) {
            libro.setClave_registro(Integer.parseInt(Campo_clave_registro.getText()));
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


    public void validar_clave_registro(javafx.scene.input.KeyEvent keyEvent){
        if (Campo_clave_registro.isEditable()) {
            if (Campo_clave_registro.getText().matches("^[a-zA-Z0-9]{1,15}$") && !Campo_clave_registro.getText().isEmpty()) {
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

    public void validar_clasificacion(javafx.scene.input.KeyEvent keyEvent) {
        if (Campo_clasificacion.isEditable()) {
            if (Campo_clasificacion.getText().matches("^[A-Z.\s][A-Za-z.\sñ]{1,12}") && !Campo_clasificacion.getText().isEmpty()) {
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

    public void validar_anio_edicion(KeyEvent keyEvent) {
        if (Campo_anio_edicion.isEditable()) {
            if (Campo_anio_edicion.getText().matches("^\\d{1,4}$") && !Campo_anio_edicion.getText().isEmpty()) {
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

    public void validar_registro_clasificacion(KeyEvent keyEvent) {
        if (Campo_registro_clasificacion.isEditable()) {
            if (Campo_registro_clasificacion.getText().matches("^\\d{1,3}$") && !Campo_registro_clasificacion.getText().isEmpty()) {
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

    public void validar_estante(KeyEvent keyEvent) {
        if (Campo_estante.isEditable()) {
            if (Campo_estante.getText().matches("^[A-Z]-\\d{1,2}") && !Campo_estante.getText().isEmpty()) {
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

    public void validar_existencias(KeyEvent keyEvent) {
        if (Campo_existencias.isEditable()) {
            if (Campo_existencias.getText().matches("^\\d{1,3}") && !Campo_existencias.getText().isEmpty()) {
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

    public void validar_editorial(KeyEvent keyEvent) {
        if (Campo_editorial.isEditable()) {
            if (Campo_editorial.getText().matches("^[A-Z.\s][A-Za-z.\sñ]{1,15}") && !Campo_editorial.getText().isEmpty()) {
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

    public void validar_lugar_edicion(KeyEvent keyEvent) {
        if (Campo_lugar_edicion.isEditable()) {
            if (Campo_lugar_edicion.getText().matches("^[A-Z.\s][A-Za-z.\sñ]{1,30}") && !Campo_lugar_edicion.getText().isEmpty()) {
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

    public void validar_titulo_libro(KeyEvent keyEvent) {
        if (Campo_titulo_libro.isEditable()) {
            if (Campo_titulo_libro.getText().matches("^[A-Z.\s][A-Za-z.\sñ]{1,30}") && !Campo_titulo_libro.getText().isEmpty()) {
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

    public void validar_nombre_autor(KeyEvent keyEvent) {
        if (Campo_nombre_autor.isEditable()) {
            if (Campo_nombre_autor.getText().matches("^[A-Z.\s][A-Za-z.\sñ]{1,30}") && !Campo_nombre_autor.getText().isEmpty()) {
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

    public void validar_descripcion_libro(KeyEvent keyEvent) {
        if (Campo_descripcion_libro.isEditable()) {
            if (Campo_descripcion_libro.getText().matches("^[A-Z.\sñ][A-Za-z.\sñ]{1,70}") && !Campo_descripcion_libro.getText().isEmpty()) {
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
        Campo_titulo_libro.setLayoutY(262.0);
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
                BotonGuardar.setText("Editar otro");
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

        Campo_clave_registro.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() > 15) {
                return null;
            }
            return change;
        }));

        Campo_clasificacion.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() > 10) {
                return null;
            }
            return change;
        }));

        Campo_estante.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() > 4) {
                return null;
            }
            return change;
        }));

        Campo_registro_clasificacion.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() > 30) {
                return null;
            }
            return change;
        }));

        Campo_existencias.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() > 4) {
                return null;
            }
            return change;
        }));

        Campo_editorial.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() > 30) {
                return null;
            }
            return change;
        }));

        Campo_titulo_libro.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() > 40) {
                return null;
            }
            return change;
        }));

        Campo_lugar_edicion.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() > 40) {
                return null;
            }
            return change;
        }));

        Campo_anio_edicion.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() > 4) {
                return null;
            }
            return change;
        }));

        Campo_nombre_autor.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() > 40) {
                return null;
            }
            return change;
        }));

        Campo_descripcion_libro.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() > 70) {
                return null;
            }
            return change;
        }));
    }
}


