package com.hardbug.bibliotecatenango;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/* Clase que crea el stage y hace que se muestren todas las vistas
* Absolutamente todas las vistas y controladores se deben conectar a esta clase para poder crearse la vista*/

public class SceneController {
    //Instanciamos la variable que contendrá el Stage
    private Stage mainStage;
    //Borderpane que tendrá la vista
    private BorderPane borderPane;


    //Constructor de la clase, aquí se recibe el Stage a crear y se carga el stage principal(pantalla principal)
    public SceneController(Stage stage){
        this.mainStage = stage;
        CargarEscenaPrincipal();
    }

    /*Método para crear y mostrar la ventana principal, como es border pane, se le pueden agregar hasta 5 vistas al momento
    * (Top, center, left, right y bottom)*/
    public void CargarEscenaPrincipal(){
        try {
            //Obtenemos la vista con un FXMLLoader
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("EscenaPrincipalView.fxml"));
            //Al border pane le asignamos la vista anterior que cargamos
            borderPane = (BorderPane) loader.load();
            //Creamos la escena a la cuál le añadimos el border pane
            Scene s = new Scene(borderPane);
            //Al stage instanciado al principio de clase le asignamos la
            mainStage.setScene(s);
            mainStage.setTitle("Biblioteca Pública Municipal Lic. Abel C. Salazar");
            mainStage.setMinHeight(450);
            mainStage.setWidth(600);
            mainStage.getIcons().add(new Image(SceneController.class.getResourceAsStream("/assets/logotenango.png")));
            //Mostramos el Stage en pantalla
            mainStage.show();
            //Como cada Vista tiene su controlador, al controlador de escenas le indicamos el controlador de la vista
            SceneController sc = loader.getController();
            //Aquí directamente cargamos la vista de AltaLibros al borderpane
            CargarAltaLibros();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void CargarBuscadorLibros(){
        try {
            //Obtenemos la vista con un FXMLLoader
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("BuscadorLibrosView.fxml"));
            AnchorPane anchorPane = (AnchorPane) loader.load();
            //Seteamos la vista en el centro del Borderpane
            borderPane.setCenter(anchorPane);
            //Le asignamos el controlador a la vista
            BuscadorLibrosController blc = loader.getController();
            //Al controlador le asignamos el controlador de escena, ya que tenemos sus getters and setters en la clase
            blc.setSceneController(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void CargarAltaLibros(){
        try {
            //Obtenemos la vista con un FXMLLoader
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("AltaLibrosView.fxml"));
            AnchorPane anchorPane = (AnchorPane) loader.load();
            //Seteamos la vista en el centro del Borderpane
            borderPane.setCenter(anchorPane);
            //Le asignamos el controlador a la vista
            AltaLibrosController alc = loader.getController();
            //Al controlador le asignamos el controlador de escena, ya que tenemos sus getters and setters en la clase
            alc.setSceneController(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void CargarLogin(){

    }
}
