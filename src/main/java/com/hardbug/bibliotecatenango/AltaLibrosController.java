package com.hardbug.bibliotecatenango;


/*Clase que controla la alta de libros
  Vista a la que está asociada la clase: AltaLibrosView.fxml
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.util.Date;

/* Clase AltaLibros:
    Su funcionalidad es recibir la información de su vista ligada, tratar la información y mandarla a base de datos
 */

public class AltaLibrosController {

    private IndexApp indexApp = new IndexApp();

    @FXML
    private Button Boton_Modo, BotonGuardar;
    @FXML
    private TextField Campo_clasificacion, Campo_anio_edicion, Campo_registro_clasificacion, Campo_estante, Campo_existencias
            , Campo_editorial, Campo_lugar_edicion, Campo_titulo_libro, Campo_nombre_autor, Campo_descripcion_libro;
    public AnchorPane Fondo;



    //El método causará errores al correrse actualmente, se debe validar
    @FXML
    void GuardarLibro(ActionEvent event){
        /* Aquí se obtendrá la información de los campos de texto y se validarán, posteriormente se mandará
            a almacenar en la base de datos
         */
        if (camposValidos()){
            System.out.println("Campos correctos y bien llenados");
        }else{
            System.out.println("Campos con errores o no llenados");
        }
    }

    boolean camposValidos(){
        String clasificacion = Campo_clasificacion.getText();
        String anio_edicion = Campo_anio_edicion.getText();
        String registro_clasificacion = Campo_registro_clasificacion.getText();
        String estante = Campo_estante.getText();
        int existencias = Integer.parseInt(Campo_existencias.getText());
        String editorial = Campo_editorial.getText();
        String lugar_edicion = Campo_lugar_edicion.getText();
        String titulo_libro = Campo_titulo_libro.getText();
        String nombre_autor = Campo_nombre_autor.getText();
        String descripcion_libro = Campo_descripcion_libro.getText();
        if (true){
            return true;
        }else{
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


}
