package com.hardbug.bibliotecatenango;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Objects;

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

    public static void showTo(View view, int theme, BorderPane rootPane){
        if (scene == null){
            System.out.print("No hay una escena configurada");
        }else{
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(ViewSwitcher.class.getResource(view.getFileName())));
                applyCSS(theme);
                if (view == View.MENU_LATERAL){
                    rootPane.setLeft(root);
                }else{
                    rootPane.setCenter(root);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void switchTo(View view, int theme){
        if (scene == null){
            System.out.print("No hay una escena configurada");
        }else{
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(ViewSwitcher.class.getResource(view.getFileName())));
                applyCSS(theme);
                scene.setRoot(root);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void applyCSS(int theme){
        if (theme == TEMA_CLARO){
            scene.getStylesheets().clear();
            scene.getStylesheets().add(Objects.requireNonNull(ViewSwitcher.class.getResource("/styles/WhiteTheme.css")).toExternalForm());
        }else if (theme == TEMA_OSCURO){
            scene.getStylesheets().clear();
            scene.getStylesheets().add(Objects.requireNonNull(ViewSwitcher.class.getResource("/styles/DarkTheme.css")).toExternalForm());
        }
    }
}
