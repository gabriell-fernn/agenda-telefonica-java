package org.agenda_contatos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import org.agenda_contatos.utils.PathFXML;

import java.io.FileInputStream;
import java.io.IOException;

public class MainApp extends Application {
    @Getter
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(new FileInputStream(PathFXML.pathBase() + "MainView.fxml"));
        scene = new Scene(root, 800, 600);
        stage.setTitle("Agenda Telefônica");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}