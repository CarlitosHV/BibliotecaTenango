package com.hardbug.bibliotecatenango;

/*Clase ENUM la cuál su funcionalidad es crear variables globales, las cuáles cada una contendrá el nombre del archivo de la
 vista que le pertenece, a su vez tiene su Getter para poder llamarlo desde otras clases
 */

public enum View {
    LOGIN("LoginView.fxml"),
    CRUD_LIBROS("AltaLibrosView.fxml"),
    MODO_OSCURO("/styles/DarkTheme.css"),
    MODO_BLANCO("/styles/WhiteTheme.css"),
    MENU_LATERAL("MenuLateralView.fxml"),
    MENU_PRINCIPAL("IndexView.fxml"),
    BUSCADOR_LIBROS("BuscadorLibrosView.fxml"),
    MENU_LIBROS("MenuLibros.fxml"),
    MENU_CONFIGURACION("ViewConfig.fxml"),
    DETALLES_LIBROS("ViewBookDetails.fxml"),
    MENU_USUARIOS("MenuUsuarios.fxml");

    private String fileName;

    View(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
