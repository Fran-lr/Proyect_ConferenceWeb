package controlador;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Clase para iniciar la aplicación
 * @author FranJ
 * @version 1.0
 */
public class MainIni extends Application {
	
    /**
     * Método para cargar el la ventana
     * @param primaryStage
     */
	@Override
	public void start(Stage stage) {// Stage es el contenedor padre y sobre el cual generaremos las escenas.

		try {
			 //Cargo la vista
			Parent root = FXMLLoader.load(getClass().getResource("/vista/VistaLoader.fxml"));
			// Cargo el scene con un tamaño
			Scene scene = new Scene(root);
			// Seteo la scene y la muestro
			stage.setTitle("  -  Cargando...  -  ");
			stage.setResizable(false);
			stage.setScene(scene );
			stage.show();
			
		} catch (IOException io) {
			io.printStackTrace();
			System.out.println("!! Error -> "+io.getMessage());
		}
	}
	/**
	 * Método que lanza la aplicación
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);//Lanza la aplicación
		
	}

}
