package controlador;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.input.MouseEvent;
import modelo.Usuarios;
import javafx.scene.input.KeyEvent;

import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class usuarioAdminController implements Initializable{
	@FXML
	private Label labelPrincipalAdmin;
	@FXML
	private Label labelNombre;
	@FXML
	private Label labelContrasena;
	@FXML
	private TextField textFieldNombre;
	@FXML
	private TextField textFieldContrasena;
	@FXML
	private Button btnAnadir;
	@FXML
	private Button btnEditar;
	@FXML
	private Button btnBorrar;
	@FXML
	private Button btnVolver;
	@FXML
	private TableView<Usuarios> tablaUsuarioAdmin;
	@FXML
	private TableColumn collUsario;
	@FXML
	private TableColumn colContrasena;
	@FXML
	private Label labelListaUserAdmin;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
	}
	
	@FXML
	public void keyNombre(KeyEvent event) {
	
	}
	
	@FXML
	public void keyPass(KeyEvent event) {
		
	}
	
	@FXML
	public void anadir(ActionEvent event) {
		
	}
	
	@FXML
	public void editar(ActionEvent event) {
		
	}
	
	@FXML
	public void volver(ActionEvent event) {
		
	}
	
	@FXML
	public void seleccionarTablaAdmin(MouseEvent event) {
		
	}


}
