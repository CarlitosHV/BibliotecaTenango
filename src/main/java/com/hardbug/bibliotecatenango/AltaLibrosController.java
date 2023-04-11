package com.hardbug.bibliotecatenango;


/*Clase que controla la alta de libros
  Vista a la que está asociada la clase: AltaLibrosView.fxml
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/* Clase AltaLibros:
    Su funcionalidad es recibir la información de su vista ligada, tratar la información y mandarla a base de datos
 */

public class AltaLibrosController {


    @FXML
    private Button Boton_Modo;
    public TextField Campo_clasificacion;
    public AnchorPane Fondo;




    @FXML
    void ActivarModoOscuro(ActionEvent event){
        if (ViewSwitcher.BANDERA_TEMA == 1){
            ViewSwitcher.switchTo(View.CRUD_LIBROS, ViewSwitcher.TEMA_CLARO);
            ViewSwitcher.BANDERA_TEMA = 0;
        }else if(ViewSwitcher.BANDERA_TEMA == 0){
            ViewSwitcher.switchTo(View.CRUD_LIBROS, ViewSwitcher.TEMA_OSCURO);
            ViewSwitcher.BANDERA_TEMA = 1;
        }
    }
}
