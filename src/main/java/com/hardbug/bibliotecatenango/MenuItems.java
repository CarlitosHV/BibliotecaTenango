package com.hardbug.bibliotecatenango;

public class MenuItems {
    private String ImagePath;
    private String Description;

    public MenuItems(String imagePath, String description) {
        ImagePath = imagePath;
        Description = description;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
