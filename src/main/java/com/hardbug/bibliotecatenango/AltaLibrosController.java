package com.hardbug.bibliotecatenango;


/*Clase que controla la alta de libros
  Vista a la que está asociada la clase: AltaLibrosView.fxml
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.util.Date;

/* Clase AltaLibros:
    Su funcionalidad es recibir la información de su vista ligada, tratar la información y mandarla a base de datos
 */

public class AltaLibrosController {

    private static boolean Clasificacion_bol, Anio_edicion_bol, Descripcion_libro_bol, Nombre_autor_bol,
            Titulo_libro_bol, Lugar_edicion_bol, Editorial_bol, Registro_clasificacion_bol, Estante_bol,
            Existencias_bol;

    private ClaseLibro libro = new ClaseLibro();
    private IndexApp indexApp = new IndexApp();

    @FXML
    private Button Boton_Modo, BotonGuardar;
    @FXML
    private TextField Campo_clasificacion, Campo_anio_edicion, Campo_registro_clasificacion, Campo_estante,
            Campo_existencias, Campo_editorial, Campo_lugar_edicion, Campo_titulo_libro, Campo_nombre_autor, Campo_descripcion_libro;
    public AnchorPane Fondo;


    @FXML
    void GuardarLibro(ActionEvent event) {
        if (camposValidos()) {
            System.out.println("Listo para mandar a BD");
        } else {
            System.out.println("Error en el llenado de los campos");
        }
    }


    boolean camposValidos() {
        if (Clasificacion_bol && Anio_edicion_bol && Descripcion_libro_bol && Nombre_autor_bol &&
                Titulo_libro_bol && Lugar_edicion_bol && Editorial_bol && Registro_clasificacion_bol && Estante_bol &&
                Existencias_bol) {
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


    public void validar_clasificacion(javafx.scene.input.KeyEvent keyEvent) {
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

    public void validar_anio_edicion(KeyEvent keyEvent) {
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

    public void validar_registro_clasificacion(KeyEvent keyEvent) {
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

    public void validar_estante(KeyEvent keyEvent) {
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

    public void validar_existencias(KeyEvent keyEvent) {
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

    public void validar_editorial(KeyEvent keyEvent) {
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

    public void validar_lugar_edicion(KeyEvent keyEvent) {
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

    public void validar_titulo_libro(KeyEvent keyEvent) {
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

    public void validar_nombre_autor(KeyEvent keyEvent) {
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

    public void validar_descripcion_libro(KeyEvent keyEvent) {
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


