package controlador;

import java.io.IOException;
import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.scene.layout.Pane;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {// Stage es el contenedor padre y sobre el cual generaremos las escenas.

		try {
			 //Cargo la vista
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/vista/vistaLogin.fxml"));
			
			// Cargo la ventana
			Pane ventana = (Pane) loader.load();

			// Cargo el scene con un tamanio
			Scene scene = new Scene(ventana);
		
			// Seteo la scene y la muestro
			primaryStage.setTitle("Panel de Eventos");
			primaryStage.setScene(scene );
			primaryStage.show();
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {// Instancia o lanza la aplicación y llama al metodo start
		launch(args);
		
	}
}