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

import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class panelPrincipalController implements Initializable{
	@FXML
	private Label labePrincipal;
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


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		colocarImagenBotones();
		
	}
	
	@FXML
	public void Salas(ActionEvent event) {
		try {
			// Cargo la vista
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/vistaSalas.fxml"));

	       // Cargo la ventana
	        Parent root = loader.load();
	        
	        // Creo el Scene
	        Scene scene = new Scene(root);
	        Stage stage = new Stage();
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.setScene(scene);
	        stage.show(); 
	        
	     // Cerrar ventana
            Stage stageClose = (Stage) this.btnSalas.getScene().getWindow();
            stageClose.close();
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	public void ciiudades(ActionEvent event) {
		// TODO Autogenerated
	}

	@FXML
	public void clientes(ActionEvent event) {
		// TODO Autogenerated
	}

	@FXML
	public void orden(ActionEvent event) {
		// TODO Autogenerated
	}
	
	@FXML
	public void usarios(ActionEvent event) {
		try {
			// Cargo la vista
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/vistaUsuarioAdmin.fxml"));

	       // Cargo la ventana
	        Parent root = loader.load();
	        
	        // Creo el Scene
	        Scene scene = new Scene(root);
	        Stage stage = new Stage();
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.setScene(scene);
	        stage.show(); 
	        
	     // Cerrar ventana
            Stage stageClose = (Stage) this.btnSalas.getScene().getWindow();
            stageClose.close();
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	public void cerrar(ActionEvent event) {
		// TODO Autogenerated
	}
	private void colocarImagenBotones() {
		
		URL linkSala = getClass().getResource("/img/img_sala.png");
		URL linkClientes = getClass().getResource("/img/img_clientes.png");
		URL linkUsarios = getClass().getResource("/img/img_controluser.png");
		URL linkCiudades = getClass().getResource("/img/img_ubicacion.png");
		URL linkOrden = getClass().getResource("/img/img_orden.png");
		
		Image imagenSala = new Image(linkSala.toString(),45,45,false,true);
		Image imagenClientes = new Image(linkClientes.toString(),40,40,false,true);
		Image imagenUsarios = new Image(linkUsarios.toString(),40,40,false,true);
		Image imagenCiudades = new Image(linkCiudades.toString(),40,40,false,true);
		Image imagenOrden = new Image(linkOrden.toString(),40,40,false,true);
		
		btnSalas.setGraphic((new ImageView(imagenSala)));
		btnCiudades.setGraphic((new ImageView(imagenCiudades)));
		btnClientes.setGraphic((new ImageView(imagenClientes)));
		btnOrden.setGraphic((new ImageView(imagenOrden)));
		btnUsuarios.setGraphic((new ImageView(imagenUsarios)));
		
	}
	
}