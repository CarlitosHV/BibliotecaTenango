package com.hardbug.bibliotecatenango;

public enum View {
    LOGIN("LoginView.fxml"),
    CRUD_LIBROS("AltaLibrosView.fxml"),
    MODO_OSCURO("/styles/DarkTheme.css"),
    MODO_BLANCO("/styles/WhiteTheme.css");

    private String fileName;

    View(String fileName){
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
