package com.hardbug.bibliotecatenango;

import com.hardbug.bibliotecatenango.Models.Catalogo;
import com.hardbug.bibliotecatenango.Models.Libro;
import com.hardbug.bibliotecatenango.Models.Prestamo;
import com.hardbug.bibliotecatenango.Models.Usuario;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class PrestamoController extends BDController implements Initializable {

    Alertas alertas = new Alertas();
    private BuscadorLibrosController buscadorLibrosController;

    private ArrayList<Libro> _libros = new ArrayList<>();

    public void setLibros(ArrayList<Libro> libros){
        this._libros = libros;
    }
    private Stage modalStage;

    public void setBuscadorLibrosController(BuscadorLibrosController buscadorLibrosController){
        this.buscadorLibrosController = buscadorLibrosController;
    }
    public void setModalStage(Stage modalStage) {
        this.modalStage = modalStage;
    }

    @FXML
    private ListView<Usuario> ListViewUser;
    @FXML
    private Label LabelCurp, LabelCorreo, LabelNombre, LabelHeader, LabelFechaInicio, LabelFechaFin, LabelHeaderTwo,
            LabelHeaderTres, LabelHInicio, LabelHFin, LabelDir;
    @FXML
    private TextField InputUser;
    @FXML
    private Button BotonConfirmUser, ButtonPrestamo;
    @FXML
    private ComboBox<Catalogo> ComboDocumento;

    private static ArrayList<Usuario> _usuarios = new ArrayList<>();
    private static Usuario usuario;
    private static FilteredList<Usuario> _usuariosfiltrados;
    private ArrayList<Catalogo> _documentos;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        _usuarios = MostrarTodosUsuarios();
        _documentos = ConsultarDocumentos(false);
        ComboDocumento.getItems().addAll(FXCollections.observableArrayList(_documentos));
        configurarLista();
        ListViewUser.setVisible(false);
        BotonConfirmUser.setVisible(false);
        LabelFechaInicio.setVisible(false);
        LabelFechaFin.setVisible(false);
        LabelHeaderTwo.setVisible(false);
        LabelHeaderTres.setVisible(false);
        ComboDocumento.setVisible(false);
        LabelHInicio.setVisible(false);
        LabelHFin.setVisible(false);
        ButtonPrestamo.setVisible(false);
        InputUser.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length() > 0){
                ListViewUser.setVisible(true);
                if(oldValue.length() > newValue.length()){
                    LabelCorreo.setText("");
                    LabelNombre.setText("");
                    LabelCurp.setText("");
                    LabelDir.setText("");
                    BotonConfirmUser.setVisible(false);
                }
            }else{
                ListViewUser.setVisible(false);
                BotonConfirmUser.setVisible(false);
                LabelCorreo.setText("");
                LabelNombre.setText("");
                LabelCurp.setText("");
                LabelDir.setText("");
            }
            Search(newValue);
        });

        ComboDocumento.setOnAction(evt -> {
            if(ComboDocumento.getValue() != null){
                ButtonPrestamo.setVisible(true);
            }
        });

        BotonConfirmUser.setOnAction(evt -> {
            Alert alert = alertas.CrearAlertaConfirmacion("Confirmar usuario", "Una vez seleccionado el usuario ya no se puede cambiar.\n ¿Estás seguro de confirmar al usuario con CURP: " + usuario.getCurp().trim() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                BotonConfirmUser.setVisible(false);
                InputUser.setText("");
                InputUser.setVisible(false);
                ListViewUser.setVisible(false);
                LabelNombre.setText("Nombre: " +usuario.nombre.GetNombreCompleto());
                LabelCurp.setText("CURP: " + usuario.Curp);
                LabelCorreo.setText("Correo: " + usuario.Correo);
                LabelDir.setText("Dirección: " + usuario.direccion.getDireccionCompleta());
                Animar(LabelNombre);
                Animar(LabelCorreo);
                Animar(LabelCurp);
                Animar(LabelHeader);
                Animar(LabelDir);
                LabelFechaInicio.setText(Fechas.ObtenerFechaInicio());
                LabelFechaFin.setText(Fechas.ObtenerFechaDevolucion());
                LabelFechaInicio.setVisible(true);
                LabelFechaFin.setVisible(true);
                LabelHeaderTwo.setVisible(true);
                LabelHeaderTres.setVisible(true);
                ComboDocumento.setVisible(true);
                LabelHInicio.setVisible(true);
                LabelHFin.setVisible(true);
            }
        });

        ButtonPrestamo.setOnAction(evt -> {
            Prestamo prestamo = new Prestamo();
            prestamo.FechaInicio = Fechas.obtenerFechaInicioSqlDate();
            prestamo.FechaFin = Fechas.obtenerFechaDevolucionSqlDate();
            prestamo.Usuario = usuario;
            prestamo.Documento = ComboDocumento.getValue();
            prestamo.libros = _libros;
            try {
                Alert al;
                int result = InsertarActualizarPrestamo(prestamo);
                if (result == 0){
                     al = alertas.CrearAlertaInformativa("¡Préstamo creado!", "El préstamo se ha creado de manera satisfactoria");
                     al.showAndWait();
                     cerrarModal();
                }else if(result == -2){
                    al = alertas.CrearAlertaError("Error", "El usuario ya cuenta con un préstamo activo");
                    al.showAndWait();
                }else{
                    al = alertas.CrearAlertaError("Error", "Ha ocurrido un error al generar el préstamo");
                    al.showAndWait();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void Animar(Node node){
        TranslateTransition traslacion = new TranslateTransition(Duration.millis(500));
        traslacion.setByX(-150);
        traslacion.setNode(node);
        traslacion.play();
    }

    void configurarLista(){
        _usuariosfiltrados = new FilteredList<>(FXCollections.observableArrayList(_usuarios));
        ListViewUser.setCellFactory(lv -> new ComboUser());
        ListViewUser.setItems(_usuariosfiltrados);

        ListViewUser.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Usuario usuarioSeleccionado = newValue;
                usuario = usuarioSeleccionado;
                LabelNombre.setText("Nombre: " +usuarioSeleccionado.nombre.GetNombreCompleto());
                LabelCurp.setText("CURP: " + usuarioSeleccionado.Curp);
                LabelCorreo.setText("Correo: " + usuarioSeleccionado.Correo);
                LabelDir.setText("Dirección: " + usuarioSeleccionado.direccion.getDireccionCompleta());
                ListViewUser.setVisible(false);
                BotonConfirmUser.setVisible(true);
            }
        });
    }

    private void Search(String searchText) {
        String searchTextLowerCase = searchText.toLowerCase();
        _usuariosfiltrados.setPredicate(usuario -> {
            boolean match = usuario.nombre.GetNombreCompleto().toLowerCase().contains(searchTextLowerCase)
                    || usuario.getCurp().toLowerCase().contains(searchTextLowerCase)
                    || usuario.getTelefono().toString().toLowerCase().contains(searchTextLowerCase)
                    || usuario.getCorreo().toLowerCase().contains(searchTextLowerCase);
            return match;
        });
    }

    private void cerrarModal(){
        if(modalStage != null){
            buscadorLibrosController.configurarLista();
            buscadorLibrosController._librosSeleccionados.clear();
            buscadorLibrosController.PilaLibros.setText("");
            buscadorLibrosController.ContadorLibros = 0;
            modalStage.close();
        }
    }
}
