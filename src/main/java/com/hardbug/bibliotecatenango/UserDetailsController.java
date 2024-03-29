package com.hardbug.bibliotecatenango;

import com.hardbug.bibliotecatenango.Models.Usuario;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserDetailsController implements Initializable {
    @FXML
    private Button ButtonCerrar, ButtonEliminar, ButtonEditar;
    @FXML
    private Label LabelNombre, LabelCorreo, LabelDireccion, LabelOcupacion, LabelGradoEscolar, LabelCurp, LabelEdad, LabelSexo;
    BDController bd = new BDController();
    Usuario usuario = new Usuario();
    int ALERTA_CONFIRMACION = 1, ALERTA_PRECAUCION = 2;

    private MenuUsuariosController menuUsuariosController;

    public void setMenuUsuariosController(MenuUsuariosController menuUsuariosController){
        this.menuUsuariosController = menuUsuariosController;
    }

    public void initData(Usuario mUsuario) {
        LabelNombre.setText(mUsuario.nombre.GetNombreCompleto());
        LabelCorreo.setText("Correo: " + mUsuario.getCorreo());
        LabelDireccion.setText(mUsuario.direccion.getDireccionCompleta());
        LabelOcupacion.setText("Ocupación: " + mUsuario.getOcupacion().getNombre());
        LabelGradoEscolar.setText("Grado escolar: " + mUsuario.getGradoEscolar().getNombre());
        LabelCurp.setText("CURP: " + mUsuario.getCurp());
        LabelEdad.setText("Edad: " + mUsuario.getEdad());
        LabelSexo.setText("Sexo: " + mUsuario.getSexo());
        ButtonEditar.setVisible(true);
        ButtonEliminar.setVisible(true);
        usuario = mUsuario;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ButtonCerrar.setOnAction(actionEvent -> CerrarVista());

        ButtonEliminar.setOnAction(actionEvent -> {
            CerrarVista();
            Alert alerta = new Alertas().CrearAlertaConfirmacion("Precaución", "¿Estás seguro de eliminar a " + usuario.nombre.Nombre + "? \n Esta acción es irreversible a menos que lo vuelvas a registrar");
            Optional<ButtonType> result = alerta.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                alerta.close();
                if (bd.EliminarUsuario(usuario, usuario.getCorreo())){
                    Alert al = new Alertas().CrearAlertaInformativa("Eliminación correcta", "¡Haz eliminado a " + usuario.nombre.Nombre + " del sistema!");
                    Optional<ButtonType> result1 = al.showAndWait();
                    if (result1.isPresent() && result1.get() == ButtonType.OK) {
                        menuUsuariosController.configurarLista();
                        CerrarVista();
                    }
                }else{
                    Alert alertaR = new Alertas().CrearAlertaError("Error", "El usuario " +  usuario.nombre.Nombre + " no ha podido eliminarse. \n Verifica que no tenga préstamos activos antes de eliminar");
                    alertaR.showAndWait();
                }
            }
        });

        ButtonEditar.setOnAction(actionEvent -> {
            AltasUsersController.editUsuario = usuario;
            AltasUsersController.OPERACION = 2;
            Stage stage = (Stage) ViewSwitcher.getScene().getWindow();
            mostrarVentanaModal(stage);
        });
    }

    private void CerrarVista() {
        BorderPane pb = (BorderPane) ViewSwitcher.getScene().getRoot();
        Node right = pb.getRight();
        TranslateTransition menuTransition = new TranslateTransition(Duration.seconds(0.3), right);
        menuTransition.setToX(400);
        menuTransition.setOnFinished(actionEvent1 -> limpiar());
        menuTransition.play();
    }

    public void limpiar(){
        LabelNombre.setText("");
        LabelCorreo.setText("");
        LabelDireccion.setText("");
        LabelOcupacion.setText("");
        LabelGradoEscolar.setText("");
        LabelCurp.setText("");
        LabelEdad.setText("");
        LabelSexo.setText("");
    }

    private void mostrarVentanaModal(Stage ownerStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AltaUsersView.fxml"));
            Parent root = fxmlLoader.load();
            AltasUsersController controller = fxmlLoader.getController();

            Stage modalStage = new Stage();
            modalStage.initOwner(ownerStage);
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setTitle("Datos a editar del usuario");
            modalStage.setResizable(false);
            modalStage.getIcons().add(new Image(Objects.requireNonNull(Objects.requireNonNull(IndexApp.class.getResourceAsStream("/assets/logotenangoNR.png")))));
            Scene modalScene = new Scene(root);
            if (IndexApp.TEMA == 0){
                modalScene.getStylesheets().clear();
                modalScene.getStylesheets().add(Objects.requireNonNull(MenuUsuariosController.class.getResource("/styles/WhiteTheme.css")).toExternalForm());
            }else if (IndexApp.TEMA == 1){
                modalScene.getStylesheets().clear();
                modalScene.getStylesheets().add(Objects.requireNonNull(MenuUsuariosController.class.getResource("/styles/DarkTheme.css")).toExternalForm());
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
                e.consume();
                scaleOut.play();
            });

            controller.setModalStage(modalStage);
            controller.setUserDetailsController(this);
            controller.setMenuUsuariosController(menuUsuariosController);
            CerrarVista();

            modalStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
