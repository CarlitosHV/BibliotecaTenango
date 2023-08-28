package com.hardbug.bibliotecatenango;

import com.hardbug.bibliotecatenango.Models.Libro;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class BookDetailsController extends BuscadorLibrosController implements Initializable{
    @FXML
    private Button ButtonCerrar, ButtonEliminar, ButtonEditar, ButtonSolicitar;
    @FXML
    private Label LabelTitulo, LabelAutor, LabelEditorial, LabelClaveRegistro, LabelEstante, LabelClasificacion, LabelDisponibilidad;
    @FXML
    private TextArea TextAreaDescripcion;
    BDController bd = new BDController();
    Alertas alerta = new Alertas();
    String Clave = "";
    int ALERTA_CONFIRMACION = 1, ALERTA_PRECAUCION = 2;

    public static int SOLICITAR = 0, OPERACION_CRUD = 1;

    private BuscadorLibrosController buscadorLibrosController;
    private MenuLibrosController menuLibrosController;
    private Libro libroseleccionado = null;

    public void setBuscadorLibrosController(BuscadorLibrosController buscadorLibrosController){
        this.buscadorLibrosController = buscadorLibrosController;
    }

    public void setmenuLibrosController(MenuLibrosController menuLibrosController){
        this.menuLibrosController = menuLibrosController;
    }

    public void initData(Libro libro, int operacion) {
        Clave = libro.getClave_registro();
        LabelTitulo.setText(libro.getTitulo_libro());
        LabelAutor.setText("Autor: " + libro.getNombre_autor());
        LabelEditorial.setText("Editorial: " + libro.getEditorial());
        LabelClaveRegistro.setText("Clave registro: " + libro.getClave_registro());
        LabelEstante.setText("Estante: " + libro.getEstante());
        LabelClasificacion.setText("Clasificación: " + libro.getClasificacion());
        LabelDisponibilidad.setText("Disponibles: " + String.valueOf(libro.getExistencias()));
        TextAreaDescripcion.setText(libro.getDescripcion_libro());
        libroseleccionado = libro;
        if (SOLICITAR == operacion){
            ButtonEditar.setVisible(false);
            ButtonEliminar.setVisible(false);
            ButtonSolicitar.setVisible(true);
        }else{
            ButtonEditar.setVisible(true);
            ButtonEliminar.setVisible(true);
            ButtonSolicitar.setVisible(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ButtonCerrar.setOnAction(actionEvent -> {
            CerrarVista();
        });

        ButtonEliminar.setOnAction(actionEvent -> {
            CerrarVista();
            Alert alert = alerta.CrearAlertaInformativa("Precaución", "¿Estás seguro de eliminar el libro? " + LabelTitulo.getText());
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                alert.close();
                boolean eliminado = bd.BorrarLibro(Clave);
                if (eliminado){
                    Alert al = alerta.CrearAlertaInformativa("Eliminación correcta", "¡Se ha eliminado el libro el libro " + LabelTitulo.getText() + "!");
                    Optional<ButtonType> result1 = al.showAndWait();
                    if (result1.isPresent() && result1.get() == ButtonType.OK) {
                        menuLibrosController.configurarLista();
                        CerrarVista();
                    }
                }else{
                    Alert alertaR = alerta.CrearAlertaError("Error", "Ocurrió un error al eliminar el libro \n" + LabelTitulo.getText());
                    alertaR.showAndWait();
                }
            }
        });

        ButtonEditar.setOnAction(actionEvent -> {
            CerrarVista();
            EditBooksController.clave_registro = Clave;
            Stage stage = (Stage) ViewSwitcher.getScene().getWindow();
            mostrarVentanaModal(stage);
        });

        ButtonSolicitar.setOnAction(event -> {
            if(libroseleccionado.getExistencias() > 0){
                if(_librosSeleccionados.size() < 5){
                    if(!_librosSeleccionados.contains(libroseleccionado)){
                        ContadorLibros += 1;
                        buscadorLibrosController.PilaLibros.setText(String.valueOf(ContadorLibros));
                        _librosSeleccionados.add(libroseleccionado);
                        ScaleTransition scaleIn = new ScaleTransition(Duration.seconds(0.6), buscadorLibrosController.PilaLibros);
                        scaleIn.setToX(1.5);
                        scaleIn.setToY(1.5);
                        ScaleTransition scaleOut = new ScaleTransition(Duration.seconds(0.6), buscadorLibrosController.PilaLibros);
                        scaleOut.setToX(1.0);
                        scaleOut.setToY(1.0);
                        scaleIn.setOnFinished(e -> {
                            CerrarVista();
                            scaleOut.play();
                        });
                        scaleIn.play();
                    }else{
                        Alert al = alerta.CrearAlertaError("Libro duplicado", "No puedes seleccionar el mismo libro más de una vez");
                        al.showAndWait();
                    }
                }else{
                    Alert alert = alerta.CrearAlertaError("Límite alcanzado", "Has alcanzado el límite de 5 libros por préstamo");
                    alert.showAndWait();
                }
            }else{
                Alert a = alerta.CrearAlertaError("Libro sin existencias", "El libro que estás intentando solicitar no cuenta con inventario actualmente.");
                a.showAndWait();
            }
        });
    }

    private void CerrarVista() {
        Parent bp = ViewSwitcher.getScene().getRoot();
        BorderPane pb = (BorderPane) ViewSwitcher.getScene().getRoot();
        Node right = pb.getRight();
        TranslateTransition menuTransition = new TranslateTransition(Duration.seconds(0.3), right);
        menuTransition.setToX(400);
        menuTransition.setOnFinished(actionEvent1 -> {
            limpiar();
        });
        menuTransition.play();
    }

    public void limpiar(){
        LabelTitulo.setText("");
        LabelAutor.setText("");
        LabelEditorial.setText("");
        LabelClaveRegistro.setText("");
        LabelEstante.setText("");
        LabelClasificacion.setText("");
        LabelDisponibilidad.setText("");
        TextAreaDescripcion.setText("");
    }

    private void mostrarVentanaModal(Stage ownerStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditBooksView.fxml"));
            Parent root = fxmlLoader.load();

            Stage modalStage = new Stage();
            modalStage.initOwner(ownerStage);
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setTitle("Datos del libro");
            modalStage.setResizable(false);
            modalStage.getIcons().add(new Image(Objects.requireNonNull(Objects.requireNonNull(IndexApp.class.getResourceAsStream("/assets/logotenangoNR.png")))));
            EditBooksController modalcontroller = fxmlLoader.getController();
            modalcontroller.setModalStage(modalStage);
            modalcontroller.setBuscadorLibrosController(buscadorLibrosController);
            modalcontroller.setMenuLibrosController(menuLibrosController);
            Scene modalScene = new Scene(root);
            if (IndexApp.TEMA == 0){
                modalScene.getStylesheets().clear();
                modalScene.getStylesheets().add(MenuLibrosController.class.getResource("/styles/WhiteTheme.css").toExternalForm());
            }else if (IndexApp.TEMA == 1){
                modalScene.getStylesheets().clear();
                modalScene.getStylesheets().add(MenuLibrosController.class.getResource("/styles/DarkTheme.css").toExternalForm());
            }
            modalStage.setScene(modalScene);

            // Crear la animación de escala para iniciar el modal
            ScaleTransition scaleIn = new ScaleTransition(Duration.seconds(0.3), root);
            scaleIn.setFromX(0);
            scaleIn.setFromY(0);
            scaleIn.setToX(1);
            scaleIn.setToY(1);
            // Crear la animación de escala para cerrar el modal
            ScaleTransition scaleOut = new ScaleTransition(Duration.seconds(0.3), root);
            scaleOut.setFromX(1);
            scaleOut.setFromY(1);
            scaleOut.setToX(0);
            scaleOut.setToY(0);
            scaleOut.setOnFinished(e -> modalStage.close());

            modalStage.setOnShowing(e -> scaleIn.play());
            modalStage.setOnCloseRequest(e -> {
                EditBooksController.clave_registro = "";
                e.consume();
                scaleOut.play();
            });

            modalStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
