package com.hardbug.bibliotecatenango;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/* Clase ViewSwitcher:
    su funcionalidad es recibir la vista que va a mostrar en la ventana, a su vez, recibe el tema seleccionado
 */

public class ViewSwitcher {

private static Scene scene;

    public static int TEMA_CLARO = 0;
    public static int TEMA_OSCURO = 1;
    public static int BANDERA_TEMA = 0;

    public static void setScene(Scene scene) {
        ViewSwitcher.scene = scene;
    }

    public static Scene getScene() {
        return scene;
    }

    public static void switchTo(View view, int theme){
        if (scene == null){
            System.out.printf("No hay una escena configurada");
        }else{
            try {
                Parent root;
                root = FXMLLoader.load(ViewSwitcher.class.getResource(view.getFileName()));
                if (theme == TEMA_CLARO){
                    scene.getStylesheets().clear();
                    scene.getStylesheets().add(IndexApp.class.getResource("/styles/WhiteTheme.css").toExternalForm());
                }else if (theme == TEMA_OSCURO){
                    scene.getStylesheets().clear();
                    scene.getStylesheets().add(IndexApp.class.getResource("/styles/DarkTheme.css").toExternalForm());
                }
                scene.setRoot(root);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
