package com.hardbug.bibliotecatenango;

/*Clase que controla el buscador de libros
  Vista a la que está asociada la clase: BuscadorLibrosView.fxml
 */
public class BuscadorLibrosController {
    //Instanciamos al controlador de escenas para controlar la escena
    SceneController sceneController;

    //Método para cambiar entre escenas, aún no tiene nada porque aún no se conecta el flujo
    void changescene(){

    }

    //Getters and setters del controlador de escenas
    public SceneController getSceneController() {
        return sceneController;
    }

    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }
}
