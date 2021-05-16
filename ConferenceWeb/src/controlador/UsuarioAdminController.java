package controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.UsuariosDao;
import modelo.Usuarios;
import javafx.scene.input.KeyEvent;

import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

/**
 * Clase para responder a eventos e invocar peticiones al modelo Usuario
 * @author FranJ
 * @version 1.0
 */
public class UsuarioAdminController implements Initializable {
	@FXML
	private Label lbIni;
	@FXML
	private Label lbNomUsu;
	@FXML
	private Label lbContrasena;
	@FXML
	private TextField tfNomUsu;
	@FXML
	private TextField tfContrasena;
	@FXML
	private Button btnAnadir;
	@FXML
	private Button btnEditar;
	@FXML
	private Button btnBorrar;
	@FXML
	private Button btnVolver;
	@FXML
	private TableView<Usuarios> tbUsuario;
	@FXML
	private TableColumn<Usuarios, String> colUsuario;
	@FXML
	private TableColumn<Usuarios, String> colContrasena;
	@FXML
	private Label lbListUsu;
	@FXML
	private Label lbInfo;
	boolean expresionRe;
	private ObservableList<Usuarios> listaUsuarios;
	private UsuariosDao userDao;

	/**
	 * Método para iniciar algunos controles de la interfaz
	 * @param arg0
	 * @param arg1
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		tfNomUsu.setStyle("-fx-control-inner-background:#ef9a9a");
		tfContrasena.setStyle("-fx-control-inner-background:#ef9a9a");
        //Asignamos las relación de las columnas con los atributos 
		this.colUsuario.setCellValueFactory(new PropertyValueFactory<Usuarios, String>("nombre"));
		this.colContrasena.setCellValueFactory(new PropertyValueFactory<Usuarios, String>("contrasena"));

		//Obtenemos la información de la BD y la enviamos a la tabla
		userDao = new UsuariosDao();
		if (userDao.mostrarUsuarios().equals(null)){
			lbInfo.setText("No hay información en la base de datos");
		}
		else {
			listaUsuarios = FXCollections.observableArrayList(userDao.mostrarUsuarios());
			tbUsuario.setItems(listaUsuarios);
			lbInfo.setText("Proceda con el usuario");
		}
	}

	/**
	 * Método para controlar el evento del campo de texto Nombre
	 * @param event
	 */
	@FXML
	public void keyNombre(KeyEvent event) {
		try {
			// Se obtiene un boolean para comprobar que el nombre tiene los caracteres correctos
			expresionRe = ComprobarLogin(this.tfNomUsu.getText());
			if (tfNomUsu.getText().isEmpty() || expresionRe == false) {
				tfNomUsu.setStyle("-fx-control-inner-background:#ef9a9a");
			} else if (!tfNomUsu.getText().isEmpty() && expresionRe == true) {
				tfNomUsu.setStyle("-fx-control-inner-background:#b8daba");
				lbInfo.setText("Proceda con el usuario");
			}
		} catch (NullPointerException io) {
			io.printStackTrace();
			System.out.println("!! Error -> "+io.getMessage());
		}

	}
	/**
	 * Método para controlar el evento del campo de texto Contraseña
	 * @param event
	 */
	@FXML
	public void keyPass(KeyEvent event) {
		try {
			// Se obtiene un boolean para comprobar que la contraseña tiene los caracteres correctos
			expresionRe = ComprobarLogin(this.tfContrasena.getText());

			if (tfContrasena.getText().isEmpty() || expresionRe == false) {
				tfContrasena.setStyle("-fx-control-inner-background:#ef9a9a");
			} else if (!tfContrasena.getText().isEmpty() && expresionRe == true) {
				tfContrasena.setStyle("-fx-control-inner-background:#b8daba");
				lbInfo.setText("Proceda con el usuario");
			}
		} catch (NullPointerException io) {
			io.printStackTrace();
			System.out.println("!! Error -> "+io.getMessage());
		}

	}

	/**
	 * Método para añadir a un usuario/administrador
	 * @param event
	 */
	@FXML
	public void anadir(ActionEvent event) {
		try {
			//Se comprueba que los campos de texto contienen valor
			if (!this.tfNomUsu.getText().isEmpty() && !this.tfContrasena.getText().isEmpty()) {

				Usuarios user = new Usuarios(this.tfNomUsu.getText(), this.tfContrasena.getText());
				boolean contiene = false;
				//Se comprueba que no exista un usuario con el mismo nombre
				for (int i = 0; i < listaUsuarios.size(); i++) {
					if (listaUsuarios.get(i).getNombre().equals(user.getNombre())) {
						contiene = true;
					}
				}

				if (!contiene) {
					// Se obtiene un boolean para comprobar que los tiene los caracteres correctos
					boolean comLogNom = ComprobarLogin(this.tfNomUsu.getText());
					boolean comLogCon = ComprobarLogin(this.tfContrasena.getText());
					//Se comprueba que los campos de texto sean correctos
					if (tfContrasena.getText().isEmpty() || comLogCon == false) {
						tfContrasena.setStyle("-fx-control-inner-background:#ef9a9a");
						lbInfo.setText("Debes completar los campos correctamente");
					} else if (this.tfNomUsu.getText().isEmpty() || comLogNom == false) {
						tfNomUsu.setStyle("-fx-control-inner-background:#ef9a9a");
						lbInfo.setText("Debes completar los campos correctamente");
					} else if (!this.tfNomUsu.getText().isEmpty() && comLogNom == true && !tfContrasena.getText().isEmpty() && comLogCon == true) {
						tfContrasena.setStyle("-fx-control-inner-background:#b8daba");
						//Se añade el usuario
						userDao = new UsuariosDao();
						boolean insertado = userDao.insertarUsuario(user);
						if(insertado) {
							this.listaUsuarios.add(user);
							this.tbUsuario.setItems(listaUsuarios);
							lbInfo.setText("El usuario fue incluido con éxito");
						}
						else {
							lbInfo.setText("¡Error!. El usuario no fue incluido");
						}
					}
				} else {
					//Ventana emergente de Alerta
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setHeaderText(null);
					alert.setTitle("Alerta");
					alert.setContentText("El usuario ya exixte");
					alert.showAndWait();
				}
			} else {
				//Ventana emergente de información
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setTitle("Información");
				alert.setContentText("Introduce el Usuario");
				alert.showAndWait();
			}
		} catch (NumberFormatException nu) {
			//Ventana emergente de error
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Error");
			alert.setContentText("Formato incorrecto");
			alert.showAndWait();
		}
	}

	/**
	 * Método para editar a un usuario/administrador
	 * @param event
	 */
	@FXML
	public void editar(ActionEvent event) {
		//Se obtiene el usuario seleccionado del tabla
		Usuarios user = this.tbUsuario.getSelectionModel().getSelectedItem();
        
		if (user == null) {//Revisamos si tiene valor
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setHeaderText(null);
			alert.setTitle("Alerta");
			alert.setContentText("Debes seleccionar un Usuario");
			alert.showAndWait();
		} else {
			try {
				//Creamos un usuario con los campos del texto
				Usuarios userAux = new Usuarios(this.tfNomUsu.getText(), tfContrasena.getText());
				boolean contiene = false;
				//Comprobamos que no existan dos usuario con el mismo nombre
				for (int i = 0; i < listaUsuarios.size(); i++) {
					if (listaUsuarios.get(i).getNombre().equals(userAux.getNombre())) {
						contiene = true;
					}
				}
				if (!contiene) {
                    //Comprobamos que los campos de texto tenga un valor correcto
					boolean comLogNom = ComprobarLogin(tfNomUsu.getText());
					boolean comLogCon = ComprobarLogin(this.tfContrasena.getText());

					if (tfContrasena.getText().isEmpty() || comLogCon == false) {
						tfContrasena.setStyle("-fx-control-inner-background:#ef9a9a");
						lbInfo.setText("Debes completar los campos correctamente");
					} else if (this.tfNomUsu.getText().isEmpty() || comLogNom == false) {
						tfNomUsu.setStyle("-fx-control-inner-background:#ef9a9a");
						lbInfo.setText("Debes completar los campos correctamente");
					} else if (!this.tfNomUsu.getText().isEmpty() && comLogNom == true && !tfContrasena.getText().isEmpty() && comLogCon == true) {
						tfContrasena.setStyle("-fx-control-inner-background:#b8daba");
						//Modificamos el usuario seleccionado
						user.setNombre(userAux.getNombre());
						user.setContrasena(userAux.getContrasena());
						userDao = new UsuariosDao();
						boolean editado = userDao.modificarUsuario(user);
						if(editado) {
							lbInfo.setText("El usuario fue modificado con éxito");
							this.tbUsuario.refresh();
						}
						else {
							lbInfo.setText("¡Error!. El usuario no fue modificado");
						}
					}
				} else {
					//Ventana emergente de alerta
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setHeaderText(null);
					alert.setTitle("Alerta");
					alert.setContentText("El Usuario ya exixte");
					alert.showAndWait();
				}
			} catch (NumberFormatException nu) {
				//Ventana emergente de error
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setTitle("Error");
				alert.setContentText("Formato incorrecto");
				alert.showAndWait();
			}
		}
	}

	/**
	 * Método para borrar el usuario seleccionado
	 * @param event
	 */
	@FXML
	public void borrar(ActionEvent event) {
		
		//Se obtiene el usuario seleccionado del tabla
		Usuarios user = this.tbUsuario.getSelectionModel().getSelectedItem();
		
		if(user== null) {//Revisamos si tiene valor
			//Ventana emergente
			 Alert alert = new Alert(Alert.AlertType.WARNING);
	         alert.setHeaderText(null);
	         alert.setTitle("Alerta");
	         alert.setContentText("Debes seleccionar un usuario");
	         alert.showAndWait();
		}else {
			//Borramos usuario
			userDao = new UsuariosDao();
			boolean borrado = userDao.borrarUsuario(user);
			if(borrado) {
				lbInfo.setText("El usuario fue borrado con éxito");
				this.listaUsuarios.remove(user);
				this.tbUsuario.refresh();
			}
			else {
				lbInfo.setText("¡Error!. El usuario no fue borrado");	
			}
		}
	}

	/**
	 * Metodo para volver al menú principal
	 * Cierra la ventana actual
	 * @param event
	 */
	@FXML
	public void volver(ActionEvent event) {
		try {
			// Cargo la vista
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/vistaPanelPrincipal.fxml"));
			// Cargo la ventana
			Parent root;
			root = loader.load();
			// Creo el Scene
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setResizable(false);
			stage.setTitle(" -  Menú Principal  - ");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.show();

			// Cerrar ventana
			Stage stageClose = (Stage) this.btnVolver.getScene().getWindow();
			stageClose.close();
		} catch (IOException io) {
			io.printStackTrace();
			System.out.println("!! Error -> "+io.getMessage());
		}
	}

	/**
	 * Método que controla el evento de la selección de la tabla
	 * @param event
	 */
	@FXML
	public void selectTablaUsuario(MouseEvent event) {
		lbInfo.setText("Proceda con el usuario");
		//Selecciona el usuario de la tabla
		Usuarios user = this.tbUsuario.getSelectionModel().getSelectedItem();

		try {
			//Comprueba los valores de los campos
			if (user != null) {
				this.tfNomUsu.setText(user.getNombre());
				this.tfContrasena.setText(user.getContrasena());
			}
			// Se obtiene un boolean para comprobar que el usuario tiene los caracteres correctos
			boolean nombreCorrec = ComprobarLogin(this.tfNomUsu.getText());
			boolean contrasenaCorrect= ComprobarLogin(this.tfContrasena.getText());

			if (tfNomUsu.getText().isEmpty() || nombreCorrec == false) {
				tfNomUsu.setStyle("-fx-control-inner-background:#ef9a9a");
			} else if (!tfNomUsu.getText().isEmpty() && nombreCorrec == true) {
				tfNomUsu.setStyle("-fx-control-inner-background:#b8daba");
			}
			if (tfContrasena.getText().isEmpty() || contrasenaCorrect == false) {
				tfContrasena.setStyle("-fx-control-inner-background:#ef9a9a");
			} else if (!tfContrasena.getText().isEmpty() && contrasenaCorrect == true) {
				tfContrasena.setStyle("-fx-control-inner-background:#b8daba");
			}
		} catch (NullPointerException n) {
			n.printStackTrace();
			System.out.println("!! Error -> "+n.getMessage());
		}

	}

	/**
	 * Método para controlar los caracteres de los campos
	 * @param log
	 * @return
	 */
	public boolean ComprobarLogin(String log) {
		//Metodo que contiene una expresión regular para comprobar el usuario y contraseña
		Pattern pat = Pattern.compile("^[a-zA-Z0-9áéíóúÁÉÍÓÚ_-]{3,15}$");
		Matcher mat = pat.matcher(log);
		if (mat.find()) {
			return true;
		} else {
			return false;
		}
	}

}
