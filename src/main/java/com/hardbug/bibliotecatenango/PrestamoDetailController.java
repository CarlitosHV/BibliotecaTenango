package com.hardbug.bibliotecatenango;

import com.hardbug.bibliotecatenango.Models.Prestamo;
import com.hardbug.bibliotecatenango.Models.Usuario;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
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
    private Button ButtonExtender, ButtonCerrar, ButtonFinalizar;
    @FXML
    private TextArea TextComentario;
    Prestamo mprestamo = new Prestamo();

    private MenuPrestamosController menuPrestamosController;

    public void setMenuPrestamosController(MenuPrestamosController menuPrestamosController){
        this.menuPrestamosController = menuPrestamosController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ButtonCerrar.setOnAction(actionEvent -> {
            CerrarVista();
        });

        ButtonExtender.setOnAction(evt -> {
            mprestamo.FechaFin = Fechas.obtenerFechaDevolucionSqlDate(mprestamo.FechaFin);
            mprestamo.ComentarioAtraso = TextComentario.getText();
            try {
                if(ExtenderPrestamo(mprestamo)){
                    //alerta de si
                }else{
                    //alerta de nel
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        ButtonFinalizar.setOnAction(evt -> {
            try{
                if(FinalizarPrestamo(mprestamo)){
                    //Se hizo
                }else{
                    //Nel we xd
                }
            }catch(Exception e){
                throw new RuntimeException(e);
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

    public void initData(Prestamo prestamo) {
        LabelNombre.setText(prestamo.Usuario.nombre.GetNombreCompleto());
        LabelCorreo.setText("Correo: " +prestamo.Usuario.Correo);
        if(prestamo.Renovaciones != 0){
            LabelRenovaciones.setText("Renovaciones:" + prestamo.Renovaciones.toString());
        }else{
            LabelRenovaciones.setText("Renovaciones:" + "Ninguna");
        }
        LabelLibros.setText("Libros solicitados: " + String.valueOf(prestamo.libros.size()));
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
