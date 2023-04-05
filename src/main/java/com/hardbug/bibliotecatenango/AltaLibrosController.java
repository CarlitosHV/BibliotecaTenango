package com.hardbug.bibliotecatenango;


/*Clase que controla la alta de libros
  Vista a la que está asociada la clase: AltaLibrosView.fxml
 */

import io.github.palexdev.mfxcomponents.controls.buttons.MFXButton;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class AltaLibrosController {


    @FXML
    public MFXButton BotonToggle;
    public TextField Campo_clasificacion;
    public AnchorPane Fondo;




    public void ActivarModoOscuro(){
        if (ViewSwitcher.BANDERA_TEMA == 1){
            ViewSwitcher.switchTo(View.CRUD_LIBROS, ViewSwitcher.TEMA_CLARO);
            ViewSwitcher.BANDERA_TEMA = 0;
        }else if(ViewSwitcher.BANDERA_TEMA == 0){
            ViewSwitcher.switchTo(View.CRUD_LIBROS, ViewSwitcher.TEMA_OSCURO);
            ViewSwitcher.BANDERA_TEMA = 1;
        }
    }
}
