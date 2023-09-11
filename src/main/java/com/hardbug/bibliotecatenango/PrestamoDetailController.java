package com.hardbug.bibliotecatenango;

import com.hardbug.bibliotecatenango.Models.Prestamo;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class PrestamoDetailController extends BDController implements Initializable {
    @FXML
    private Label LabelNombre, LabelCorreo, LabelRenovaciones, LabelLibros,
            LabelCurp, LabelFechaInicio, LabelFechaDevolucion;
    @FXML
    private Button ButtonExtender, ButtonCerrar, ButtonFinalizar, ButtonReminder;
    @FXML
    private TextArea TextComentario;
    Prestamo mprestamo = new Prestamo();

    private MenuPrestamosController menuPrestamosController;

    public void setMenuPrestamosController(MenuPrestamosController menuPrestamosController){
        this.menuPrestamosController = menuPrestamosController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ButtonCerrar.setOnAction(actionEvent -> CerrarVista());

        ButtonExtender.setOnAction(evt -> {
            menuPrestamosController.IconoCarga.setVisible(true);
            menuPrestamosController.rootPane.setOpacity(0.5);
            mprestamo.FechaFin = Fechas.extenderFechaDevolucion(mprestamo.FechaFin);
            mprestamo.ComentarioAtraso = TextComentario.getText();
            try {
                if(ExtenderPrestamo(mprestamo)){
                    menuPrestamosController.IconoCarga.setVisible(false);
                    menuPrestamosController.rootPane.setOpacity(1);
                    Alert alert = new Alertas().CrearAlertaInformativa("Préstamo extendido", "Se ha extendido la fecha límite del préstamo hasta "  + Fechas.obtenerFechaDevolucion(mprestamo.FechaFin) + " \n Ahora lo puedes visualizar en el apartado de préstamos");
                    alert.showAndWait();
                    CerrarVista();
                    menuPrestamosController.configurarLista();
                }else{
                    menuPrestamosController.IconoCarga.setVisible(false);
                    menuPrestamosController.rootPane.setOpacity(1);
                    Alert alert = new Alertas().CrearAlertaError("Ocurrió un error", "Hubo un error al extender el préstamo, inténtalo después");
                    alert.showAndWait();
                    CerrarVista();
                    menuPrestamosController.configurarLista();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        ButtonReminder.setOnAction(evt -> {
            try {
                EnviarRecordatorio(mprestamo);
                Alert alert = new Alertas().CrearAlertaInformativa("Recordatorio enviado", "Se ha enviado un recordatorio al correo del usuario");
                alert.showAndWait();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        ButtonFinalizar.setOnAction(evt -> {
            menuPrestamosController.IconoCarga.setVisible(true);
            menuPrestamosController.rootPane.setOpacity(0.5);
            try{
                if(FinalizarPrestamo(mprestamo)){
                    menuPrestamosController.IconoCarga.setVisible(false);
                    menuPrestamosController.rootPane.setOpacity(1);
                    Alert alert = new Alertas().CrearAlertaInformativa("Préstamo terminado", "El préstamo ha terminado de manera correcta\n El usuario puede volver a generar otro préstamo");
                    alert.showAndWait();
                    CerrarVista();
                    menuPrestamosController.configurarLista();
                }else{
                    menuPrestamosController.IconoCarga.setVisible(false);
                    menuPrestamosController.rootPane.setOpacity(1);
                    Alert alert = new Alertas().CrearAlertaError("Ocurrió un error", "Hubo un error al terminar el préstamo, inténtalo después");
                    alert.showAndWait();
                    CerrarVista();
                    menuPrestamosController.configurarLista();
                }
            }catch(Exception e){
                throw new RuntimeException(e);
            }
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

    public void initData(Prestamo prestamo) {
        LabelNombre.setText(prestamo.Usuario.nombre.GetNombreCompleto());
        LabelCorreo.setText("Correo: " +prestamo.Usuario.Correo);
        if(prestamo.Renovaciones != 0){
            LabelRenovaciones.setText("Renovaciones:" + prestamo.Renovaciones);
        }else{
            LabelRenovaciones.setText("Renovaciones:" + "Ninguna");
        }
        LabelLibros.setText("Libros solicitados: " + prestamo.libros.size());
        LabelCurp.setText("CURP: " +prestamo.Usuario.getCurp());
        LabelFechaInicio.setText("Fecha préstamo: " + Fechas.obtenerFechaInicio(prestamo.FechaInicio));
        LabelFechaDevolucion.setText("Fecha devolución: " + Fechas.obtenerFechaDevolucion(prestamo.FechaFin));
        if(!prestamo.ComentarioAtraso.isEmpty()){
            TextComentario.setText(prestamo.ComentarioAtraso);
        }
        mprestamo = prestamo;
    }

    private void limpiar(){
        LabelNombre.setText("");
        LabelCorreo.setText("");
        LabelRenovaciones.setText("");
        LabelLibros.setText("");
        LabelCurp.setText("");
        LabelFechaInicio.setText("");
        LabelFechaDevolucion.setText("");
        TextComentario.setText("");
    }
}
