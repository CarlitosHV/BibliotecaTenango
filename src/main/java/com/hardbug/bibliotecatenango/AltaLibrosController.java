package com.hardbug.bibliotecatenango;


/*Clase que controla la alta de libros
  Vista a la que está asociada la clase: AltaLibrosView.fxml
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.util.Date;

/* Clase AltaLibros:
    Su funcionalidad es recibir la información de su vista ligada, tratar la información y mandarla a base de datos
 */

public class AltaLibrosController {

    private IndexApp indexApp = new IndexApp();

    @FXML
    private Button Boton_Modo, BotonGuardar;
    @FXML
    private TextField  Campo_clasificacion, Campo_anio_edicion, Campo_registro_clasificacion, Campo_estante, Campo_existencias
            , Campo_editorial, Campo_lugar_edicion, Campo_titulo_libro, Campo_nombre_autor, Campo_descripcion_libro;
    public AnchorPane Fondo;


                                //El método causará errores al correrse actualmente, se debe validar
    @FXML
    void GuardarLibro(ActionEvent event){
        /* Aquí se obtendrá la información de los campos de texto y se validarán, posteriormente se mandará
            a almacenar en la base de datos
         */
        if (Campo_clasificacion.getText().matches("\\b[A-Z][a-z]*\\b")) {

            if ( Campo_registro_clasificacion.getText().matches("^\\d{3}") || Campo_registro_clasificacion.getText().matches("^S\\/C")) {

                if ( Campo_estante.getText().matches("^[A-Z]{1,6}-\\d+")) {

                    if (Campo_existencias.getText().matches("^\\d{1,8}")) {

                        if ( Campo_editorial.getText().matches("^[A-Za-z\\s&-]+")) {

                            if ( Campo_lugar_edicion.getText().matches("^[A-Za-z]+[\\s,.'-]*")) {

                                if ( Campo_nombre_autor.getText().matches("^[A-Z][a-z]+(\\s[A-Z][a-z]+){1,3}")) {

                                    if ( Campo_titulo_libro.getText().matches("^[A-Z][a-z]+(\\s[a-z]+){1,15}")) {

                                        if ( Campo_anio_edicion.getText().matches("^\\d{4}")) {

                                            if (!Campo_descripcion_libro.getText().isEmpty()) {

                                                JOptionPane.showMessageDialog(null, "Campos correctos y bien llenados");

                                            } else {
                                               // System.out.println("Corrija los datos ingresados en el campo Descripción del libro ");
                                                JOptionPane.showMessageDialog(null, "Corrija los datos ingresados en el campo Descripción del libro\n Ejemplo: En este libro podemos encontra los temas...");
                                            }
                                        } else {
                                            //System.out.println("Corrija los datos ingresados en el campo Registro de Año de edición");
                                            JOptionPane.showMessageDialog(null, "Corrija los datos ingresados en el campo Registro de Año de edición\n Ejemplo: 1943");
                                        }
                                    } else {
                                        //System.out.println("corrija los datos ingresados en el campo Titulo del libro");
                                        JOptionPane.showMessageDialog(null, "Corrija los datos ingresados en el campo Titulo del libro\n Ejemplo: El principito");
                                    }
                                } else {
                                    //System.out.println("corrija los datos ingresados en el campo Nombre del autor");
                                    JOptionPane.showMessageDialog(null, "Corrija los datos ingresados en el campo Nombre del autor\n Ejemplo: Antoine De Saint Exupéry");
                                }
                            } else {
                                //System.out.println("corrija los datos ingresados en el campo Lugar de edición");
                                JOptionPane.showMessageDialog(null, "Corrija los datos ingresados en el campo Lugar de edición \n Ejemplo: Francia");
                            }
                        } else {
                            //System.out.println("corrija los datos ingresados en el campo Editorial");
                            JOptionPane.showMessageDialog(null, "Corrija los datos ingresados en el campo Editorial \n Ejemplo: Reynal & Hitchcock");
                        }
                    } else {
                       //System.out.println("corrija los datos ingresados en el campo Existencias");
                        JOptionPane.showMessageDialog(null, "Corrija los datos ingresados en el campo Existencias\n Ejemplo: 5");
                    }
                } else {
                    //System.out.println("corrija los datos ingresados en el campo Estante");
                    JOptionPane.showMessageDialog(null, "Corrija los datos ingresados en el campo Estante\n Ejemplo: AAAAAA-000000 ");
                }
            } else {
               //System.out.println("corrija los datos ingresados en el campo Registro de clasificación");
                JOptionPane.showMessageDialog(null, "Corrija los datos ingresados en el campo Registro de clasificación\n Ejemplo: 000 al 900 o S/C");
            }
        } else {
           //System.out.println("corrija los datos ingresados en el campo Clasificación");
            JOptionPane.showMessageDialog(null, "Corrija los datos ingresados en el campo Clasificación\n Ejemplo: Historia, Ciencias, Álgebra, etc.");
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
