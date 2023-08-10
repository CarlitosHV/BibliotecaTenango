package com.hardbug.bibliotecatenango;

import com.hardbug.bibliotecatenango.Models.Usuario;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class MenuUsuariosController implements Initializable {

    @FXML
    private Label LabelCrearUsuario, LabelSinUsuarios;
    @FXML
    private ListView<Usuario> UsuariosListView;
    @FXML
    private ProgressIndicator IconoCarga;
    @FXML
    private Button BotonBuscar;
    @FXML
    private TextField BuscadorUsuarios;
    @FXML
    private AnchorPane rootPane;
    private static ArrayList<Usuario> _usuarios = new ArrayList<>();
    private static FilteredList<Usuario> _usuariosfiltrados;
    private MenuUsuariosController menuUsuariosController;

    public MenuUsuariosController MenuUsuariosController(){
        return menuUsuariosController;
    }
    BDController bd = new BDController();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menuUsuariosController = this;
        Parent bp = ViewSwitcher.getScene().getRoot();
        BorderPane pb = (BorderPane) ViewSwitcher.getScene().getRoot();
        ViewSwitcher.showTo(View.DETALLES_USUARIOS, IndexApp.TEMA, pb);
        Node contentNodeRight = pb.getRight();
        contentNodeRight.setTranslateX(400);
        configurarLista();
        BotonBuscar.setOnAction(actionEvent -> {
            Search();
        });
        BuscadorUsuarios.textProperty().addListener((observable, oldValue, newValue) -> {
            Search();
        });
        LabelCrearUsuario.setOnMouseClicked(event -> {
            AltasUsersController.OPERACION = 1;
            Stage stage = (Stage) rootPane.getScene().getWindow();
            mostrarVentanaModal(stage);
        });
    }

    private void Search() {
        IconoCarga.setVisible(true);
        String searchText = BuscadorUsuarios.getText().toLowerCase();
        _usuariosfiltrados.setPredicate(usuario -> {
            boolean match = usuario.nombre.GetNombreCompleto().toLowerCase().contains(searchText)
                    || usuario.getCorreo().toLowerCase().contains(searchText)
                    || usuario.getCurp().toLowerCase().contains(searchText)
                    || usuario.getCalle().toLowerCase().contains(searchText)
                    || usuario.getOcupacion().getNombre().contains(searchText)
                    || usuario.getTelefono().toString().toLowerCase().contains(searchText);
            Platform.runLater(() -> IconoCarga.setVisible(false));
            return match;
        });
    }
    private void mostrarVentanaModal(Stage ownerStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AltaUsersView.fxml"));
            Parent root = fxmlLoader.load();
            AltasUsersController controller = fxmlLoader.getController();

            Stage modalStage = new Stage();
            modalStage.initOwner(ownerStage);
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setTitle("Datos del usuario");
            modalStage.setResizable(false);
            modalStage.getIcons().add(new Image(Objects.requireNonNull(Objects.requireNonNull(IndexApp.class.getResourceAsStream("/assets/logotenangoNR.png")))));
            Scene modalScene = new Scene(root);
            if (IndexApp.TEMA == 0){
                modalScene.getStylesheets().clear();
                modalScene.getStylesheets().add(MenuUsuariosController.class.getResource("/styles/WhiteTheme.css").toExternalForm());
            }else if (IndexApp.TEMA == 1){
                modalScene.getStylesheets().clear();
                modalScene.getStylesheets().add(MenuUsuariosController.class.getResource("/styles/DarkTheme.css").toExternalForm());
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
                configurarLista();
                e.consume();
                scaleOut.play();
            });
            controller.setModalStage(modalStage);
            controller.setMenuUsuariosController(this);
            CerrarVista();
            modalStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void configurarLista(){
        _usuarios = bd.MostrarTodosUsuarios();
        IconoCarga.setVisible(false);
        if (!_usuarios.isEmpty()){
            LabelSinUsuarios.setVisible(false);
            UsuariosListView.setVisible(true);
            _usuariosfiltrados = new FilteredList<>(FXCollections.observableArrayList(_usuarios));;
            UsuariosListView.setCellFactory(lv -> new UserItemController(this));
            UsuariosListView.setItems(_usuariosfiltrados);
        }else{
            LabelSinUsuarios.setVisible(true);
            UsuariosListView.setVisible(false);
        }
    }

    private void CerrarVista() {
        Parent bp = ViewSwitcher.getScene().getRoot();
        BorderPane pb = (BorderPane) ViewSwitcher.getScene().getRoot();
        Node right = pb.getRight();
        TranslateTransition menuTransition = new TranslateTransition(Duration.seconds(0.3), right);
        menuTransition.setToX(400);
        menuTransition.play();
    }
}
