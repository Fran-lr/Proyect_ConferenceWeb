package controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Clase para responder a eventos de la interfaz Panel Principal
 * @author FranJ
 * @version 1.0
 */
public class PanelPrincipalController  implements Initializable{
	@FXML
	private Label lbIni;
	@FXML
	private Button btnSalas;
	@FXML
	private Button btnCiudades;
	@FXML
	private Button btnClientes;
	@FXML
	private Button btnOrden;
	@FXML
	private Button btnUsuarios;
	@FXML
	private Button btnCerrar;

	/**
	 * Método para iniciar algunos controles de la interfaz
	 * @param arg0
	 * @param arg1
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		colocarImagenBotones();
	}
	/**
	 * Método para controlar el evento del botón Salas
	 * Abre una nueva ventana
	 * Cierra la ventana actual
	 * @param event
	 */
	@FXML
	public void Salas(ActionEvent event) {
		try {
			// Cargo la vista
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VistaSalas.fxml"));
	       // Cargo la ventana
	        Parent root = loader.load();
	        // Creo el Scene
	        Scene scene = new Scene(root);
	        Stage stage = new Stage();
	        stage.setResizable(false);
	        stage.setTitle(" -  Registro Salas  - ");
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.setScene(scene);
	        stage.show(); 
	     // Cerrar ventana
            Stage stageClose = (Stage) this.btnSalas.getScene().getWindow();
            stageClose.close();
		} catch (IOException io) {
			io.printStackTrace();
			System.out.println("!! Error -> "+io.getMessage());
		}
	}
	
	/**
	 * Método para controlar el evento del botón Ciudades
	 * Abre una nueva ventana
	 * Cierra la ventana actual
	 * @param event
	 */
	@FXML
	public void ciudades(ActionEvent event) {
		try {
			// Cargo la vista
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VistaCiudades.fxml"));
	       // Cargo la ventana
	        Parent root = loader.load();
	        // Creo el Scene
	        Scene scene = new Scene(root);
	        Stage stage = new Stage();
	        stage.setResizable(false);
	        stage.setTitle(" -  Registro Ciudades  - ");
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.setScene(scene);
	        stage.show(); 
	       // Cerrar ventana
            Stage stageClose = (Stage) this.btnCiudades.getScene().getWindow();
            stageClose.close();
		} catch (IOException io) {
			io.printStackTrace();
			System.out.println("¡¡ Error -> "+io.getMessage());
		}
	}
	
	/**
	 * Método para controlar el evento del botón Clientes
	 * Abre una nueva ventana
	 * Cierra la ventana actual
	 * @param event
	 */
	@FXML
	public void clientes(ActionEvent event) {
		try {
			// Cargo la vista
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VistaClientes.fxml"));
	        // Cargo la ventana
	        Parent root = loader.load();
	        // Creo el Scene
	        Scene scene = new Scene(root);
	        Stage stage = new Stage();
	        stage.setResizable(false);
	        stage.setTitle(" -  Registro Clientes - ");
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.setScene(scene);
	        stage.show();
	        //Cerrar ventana
            Stage stageClose = (Stage) this.btnClientes.getScene().getWindow();
            stageClose.close();  
		} catch (IOException io) {
			io.printStackTrace();
			System.out.println("!! Error -> "+io.getMessage());;
		}
	}

	/**
	 * Método para controlar el evento del botón Orden
	 * Abre una nueva ventana
	 * Cierra la ventana actual
	 * @param event
	 */
	@FXML
	public void orden(ActionEvent event) {
		try {
			// Cargo la vista
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VistaOrden.fxml"));
	       // Cargo la ventana
	        Parent root = loader.load();
	        // Creo el Scene
	        Scene scene = new Scene(root);
	        Stage stage = new Stage();
	        stage.setResizable(false);
	        stage.setTitle(" -  Registro Orden  - ");
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.setScene(scene);
	        stage.show(); 
	     // Cerrar ventana
            Stage stageClose = (Stage) this.btnOrden.getScene().getWindow();
            stageClose.close();
		} catch (IOException io) {
			io.printStackTrace();
			System.out.println("!! Error -> "+io.getMessage());
		}
		
	}
	/**
	 * Método para controlar el evento del botón Usuarios
	 * Abre una nueva ventana
	 * Cierra la ventana actual
	 * @param event
	 */
	@FXML
	public void usuarios(ActionEvent event) {
		try {
			// Cargo la vista
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VistaUsuarioAdmin.fxml"));
	       // Cargo la ventana
	        Parent root = loader.load();
	        // Creo el Scene
	        Scene scene = new Scene(root);
	        Stage stage = new Stage();
	        stage.setResizable(false);
	        stage.setTitle(" -  Registro Usuarios  - ");
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.setScene(scene);
	        stage.show(); 
	     // Cerrar ventana
            Stage stageClose = (Stage) this.btnUsuarios.getScene().getWindow();
            stageClose.close();
		} catch (IOException io) {
			io.printStackTrace();
			System.out.println("!! Error -> "+io.getMessage());
		}
	}
	/**
	 * Método para controlar el evento del botón Cerrar
	 * Cierra la aplicación
	 * @param event
	 */
	@FXML
	public void cerrar(ActionEvent event) {
		 // Cerrar ventana y aplicación
        Stage stageClose = (Stage) this.btnUsuarios.getScene().getWindow();
        Platform.exit();
        System.exit(0);
        stageClose.close();
	}
	/**
	 * Método para cargar los valores de todos los botones
	 */
    private void colocarImagenBotones() {
		
    	//Indicamos la ubicación de la imagenes
		URL linkSala = getClass().getResource("/img/img_sala.png");
		URL linkClientes = getClass().getResource("/img/img_clientes.png");
		URL linkUsarios = getClass().getResource("/img/img_controluser.png");
		URL linkCiudades = getClass().getResource("/img/img_ubicacion.png");
		URL linkOrden = getClass().getResource("/img/img_orden.png");
		//Tamaño de las imagenes
		Image imagenSala = new Image(linkSala.toString(),60,60,false,true);
		Image imagenClientes = new Image(linkClientes.toString(),50,50,false,true);
		Image imagenUsarios = new Image(linkUsarios.toString(),50,50,false,true);
		Image imagenCiudades = new Image(linkCiudades.toString(),55,55,false,true);
		Image imagenOrden = new Image(linkOrden.toString(),50,50,false,true);
		//Asignar valores a las imagenes
		btnSalas.setGraphic((new ImageView(imagenSala)));
		btnCiudades.setGraphic((new ImageView(imagenCiudades)));
		btnClientes.setGraphic((new ImageView(imagenClientes)));
		btnOrden.setGraphic((new ImageView(imagenOrden)));
		btnUsuarios.setGraphic((new ImageView(imagenUsarios)));
	}
	
}
