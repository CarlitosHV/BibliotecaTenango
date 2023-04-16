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

    @FXML
    void GuardarLibro(ActionEvent event){
        /* Aquí se obtendrá la información de los campos de texto y se validarán,
        posteriormente se mandará a almacenar en la base de datos.
         */
        //Valida una palabra iniciando por mayúscula
        if ( Campo_clasificacion.getText().matches("\\b[A-Z][a-z]*\\b")) {
            //Valida tres números del 000 al 999 o S/C
            if ( Campo_registro_clasificacion.getText().matches("^\\d{3}") || Campo_registro_clasificacion.getText().matches("^S\\/C")) {
                //Valida de 1 a 6 letras A-Z seguida de un gion medio seguido de números n
                if ( Campo_estante.getText().matches("^[A-Z]{1,6}-\\d+")) {
                    //Valida números hasta una cantidad de 1 a 8
                    if ( Campo_existencias.getText().matches("^\\d{1,8}")) {
                        //Valida una o mas palaras con espacios y simbolos & -
                        if ( Campo_editorial.getText().matches("^[A-Za-z\\s&-]+")) {
                            //Valida una o mas palaras con espacios y simbolos , . ´- -
                            if ( Campo_lugar_edicion.getText().matches("^[A-Za-z]+[\\s,.'-]*")) {
                                //Valida de 1 a 4 palabras separadas por espacios iniciadas por mayúsculas
                                if ( Campo_nombre_autor.getText().matches("^[A-Z][a-z]+(\\s[A-Z][a-z]+){1,4}")) {
                                    //Valida de 1 a 15 palabras separadas por espacios iniciada solo la primera por mayúscula
                                    if ( Campo_titulo_libro.getText().matches("^[A-Z][a-z]+(\\s[a-z]+){1,15}")) {
                                        //Valida 4 números
                                        if ( Campo_anio_edicion.getText().matches("^\\d{4}")) {
                                            //Valida que no este vacío el campo
                                            if (!Campo_descripcion_libro.getText().isEmpty()) {

                                                JOptionPane.showMessageDialog(null, "Campos correctos y bien llenados.");
                                            } else {
                                                JOptionPane.showMessageDialog(null, "Corrija los datos ingresados en el campo Descripción del libro.\n Ejemplo: En este libro podemos encontra los temas...");
                                            }
                                        } else {
                                            JOptionPane.showMessageDialog(null, "Corrija los datos ingresados en el campo Registro de Año de edición.\n Ejemplo: 1943");
                                        }
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Corrija los datos ingresados en el campo Titulo del libro.\n Ejemplo: El principito");
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "Corrija los datos ingresados en el campo Nombre del autor.\n Ejemplo: Antoine De Saint Exupéry");
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Corrija los datos ingresados en el campo Lugar de edición. \n Ejemplo: Francia");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Corrija los datos ingresados en el campo Editorial. \n Ejemplo: Reynal & Hitchcock");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Corrija los datos ingresados en el campo Existencias.\n Ejemplo: 5");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Corrija los datos ingresados en el campo Estante.\n Ejemplo: AAAAAA-000000 ");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Corrija los datos ingresados en el campo Registro de clasificación.\n Ejemplo: 000 al 900 o S/C");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Corrija los datos ingresados en el campo Clasificación.\n Ejemplo: Historia, Ciencias, Álgebra, etc.");
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
