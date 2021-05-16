package controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.UsuariosDao;

/**
 * Clase para responder a eventos e invocar peticiones al modelo Usuario
 * @author FranJ
 * @version 1.0
 */
public class LoginController  implements Initializable{
	@FXML
	private ImageView imgUsuario;
	@FXML
	private Label lbUsuario;
	@FXML
	private TextField tfUsuario;
	@FXML
	private Label lbContrasena;
	@FXML
	private PasswordField tfContrasena;
	@FXML
	private ImageView imgLogin;
	@FXML
	private ImageView imgOk;
	@FXML
	private Label lbAcceso;
	@FXML
	private Button btnAcceder;
	@FXML
	private Button btnLimpiar;
	boolean expresionRe;
	private UsuariosDao usuarioDao;
	
	/**
	 * Método para iniciar algunos controles de la interfaz
	 * @param arg0
	 * @param arg1
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Se inicializa el color de fondo
		lbAcceso.setText("Introduzca sus credenciales");
		tfUsuario.setStyle("-fx-control-inner-background:#ef9a9a");
		tfContrasena.setStyle("-fx-control-inner-background:#ef9a9a");
		imgOk.setVisible(false);//Ocultamos la imagen de verificación
	}
	
	/**
	 * Método para dar una funcionalidad al pulsar el botón de acceso
	 * @param event
	 */
	@FXML
	public void mousePressedAcceder(MouseEvent event) {
		
		if (!tfUsuario.getText().isEmpty() && !tfContrasena.getText().isEmpty()){
			
			boolean comLogUsu = ComprobarLogin(this.tfUsuario.getText());
			boolean comLogCon = ComprobarLogin(this.tfContrasena.getText());
			
			if (tfUsuario.getText().isEmpty() || comLogUsu == false) {
				tfUsuario.setStyle("-fx-control-inner-background:#ef9a9a");
				lbAcceso.setText("Completa los campos correctamente");
			} else if (this.tfContrasena.getText().isEmpty() || comLogCon  == false) {
				tfContrasena.setStyle("-fx-control-inner-background:#ef9a9a");
				lbAcceso.setText("Completa los campos correctamente");
			} else if (!this.tfUsuario.getText().isEmpty() && comLogUsu == true && !tfContrasena.getText().isEmpty() && comLogCon == true) {
				usuarioDao = new UsuariosDao();//Comprobamos si existe el usuario/contraseña en la BD
				boolean permitido = usuarioDao.comprobarAcceso(this.tfUsuario.getText(), this.tfContrasena.getText());
				if(permitido) {
					tfUsuario.setStyle("-fx-control-inner-background:#b8daba");
					tfContrasena.setStyle("-fx-control-inner-background:#b8daba");
					lbAcceso.setText("Acceso permitido");
					lbAcceso.setStyle("-fx-text-fill:#0000ff");
					imgOk.setVisible(true);//La imagen de verificación esta visible
				}
				else {
					lbAcceso.setText("Contraseña Incorrecta");
				}
			}
		}
		else {
			lbAcceso.setText("Introduzca sus credenciales");
			tfUsuario.setText("");
			tfContrasena.setText("");
		}
	}
	/**
	 * Método para controlar el evento del campo de texto usuario
	 * @param event
	 */
	@FXML
	public void KeyUser(KeyEvent event) {
		try {
			//Se obtiene un boolean para comprobar que el usuario tiene los caracteres correctos
			expresionRe = ComprobarLogin( this.tfUsuario.getText());

			if (tfUsuario.getText().isEmpty() || expresionRe == false) {
				tfUsuario.setStyle("-fx-control-inner-background:#ef9a9a");
				lbAcceso.setText("Introduzca su usuario");
			} else if (!tfUsuario.getText().isEmpty() && expresionRe == true) {
				tfUsuario.setStyle("-fx-control-inner-background:#b8daba");
				lbAcceso.setText("Introduzca su usuario");
			}
		} catch (NullPointerException io) {
			io.printStackTrace();
			System.out.println("!! Error -> "+io.getMessage());
			lbAcceso.setText("Introduzca su usuario");
		}
	}
	
	/**
	 * Método para controlar el evento del campo de texto contraseña
	 * @param event
	 */
	@FXML
	public void KeyPass(KeyEvent event) {
		try {
			//Se obtiene un boolean para comprobar que la constraseña tenga los caracteres correctos
			expresionRe = ComprobarLogin(this.tfContrasena.getText());
			if (tfContrasena.getText().isEmpty() || expresionRe == false) {
				tfContrasena.setStyle("-fx-control-inner-background:#ef9a9a");
				lbAcceso.setText("Introduzca su contraseña");
			} else if (!tfContrasena.getText().isEmpty() && expresionRe == true) {
				tfContrasena.setStyle("-fx-control-inner-background:#b8daba");
				lbAcceso.setText("Introduzca su contraseña");
			}
		} catch (NullPointerException io) {
			io.printStackTrace();
			System.out.println("!! Error -> "+io.getMessage());
			lbAcceso.setText("Introduzca su contraseña");
		}
	}
	
	/**
	 * Método para dar funcionalidad al botón de acceder
	 * Comprueba si existe las credenciales introducidas
	 * Si las credenciales son correctas accede al menú
	 * @param event
	 */
	@FXML
	public void acceder(ActionEvent event) {
		
		//Se revisa que los campos contienen valor
		if (!tfUsuario.getText().isEmpty() && !tfContrasena.getText().isEmpty()){
			
			boolean comLogUsu = ComprobarLogin(this.tfUsuario.getText());
			boolean comLogCon = ComprobarLogin(this.tfContrasena.getText());
			
			if (tfUsuario.getText().isEmpty() || comLogUsu == false) {
				tfUsuario.setStyle("-fx-control-inner-background:#ef9a9a");
				lbAcceso.setText("Completa los campos correctamente");
			} else if (this.tfContrasena.getText().isEmpty() || comLogCon  == false) {
				tfContrasena.setStyle("-fx-control-inner-background:#ef9a9a");
				lbAcceso.setText("Completa los campos correctamente");
			} else if (!this.tfUsuario.getText().isEmpty() && comLogUsu == true && !tfContrasena.getText().isEmpty() && comLogCon == true) {
			
				usuarioDao = new UsuariosDao();
				//Comprobamos si existe el usuario/contraseña en la BD
				boolean permitido = usuarioDao.comprobarAcceso(this.tfUsuario.getText(), this.tfContrasena.getText());
				if(permitido) {//Si las credenciales son correctas se accede a la aplicación
				tfUsuario.setStyle("-fx-control-inner-background:#b8daba");
				tfContrasena.setStyle("-fx-control-inner-background:#b8daba");
			    dormirProcedimiento();//Dormir el procedimiento unos segundos
					try {
						// Cargo la vista del menu
				        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/vistaPanelPrincipal.fxml"));
				       // Cargo la ventana
				        Parent root = loader.load();
				        // Creo el Scene
				        Scene scene = new Scene(root);
				       // scene.getStylesheets().addAll(this.getClass().getResource("/css/application.css").toExternalForm());
				        Stage stage = new Stage();
				        stage.setResizable(false);
				        stage.setTitle(" -  Menú Principal  - ");
				        stage.initModality(Modality.APPLICATION_MODAL);
				        stage.setScene(scene);
				        stage.show(); 
				     // Cerrar ventana
			            Stage stageClose = (Stage) this.btnAcceder.getScene().getWindow();
			            stageClose.close();
			
					} catch (IOException io) {
						io.printStackTrace();
						System.out.println("!! Error -> "+io.getMessage());
					}
				}else {
					lbAcceso.setText("Contraseña Incorrecta");
				}
			}
		}else {
			lbAcceso.setText("Introduzca sus credenciales");
			
		}
	}
	
	/**
	 * Método para limpiar los campos de texto
	 * @param event
	 */
	@FXML
	public void limpiar(ActionEvent event) {
		//Deja los valores de los campos sin contenido
		tfUsuario.setText("");
		tfContrasena.setText("");
		tfUsuario.setStyle("-fx-control-inner-background:#ef9a9a");
		tfContrasena.setStyle("-fx-control-inner-background:#ef9a9a");
		lbAcceso.setText("Introduzca sus credenciales");
	}
	
	/**
	 * Método para controlar los caracteres de los campos
	 * @param log
	 * @return
	 */
	public boolean ComprobarLogin(String log) {
		//Método que contiene una expresión regular para comprobar el usuario y contraseña
		Pattern pat = Pattern.compile("^[a-zA-Z0-9áéíóúÁÉÍÓÚ_-]{3,12}$");
		Matcher mat = pat.matcher(log);
		if (mat.find()) {
			return true;
		} else {
			return false;
		}

	}
	/**
	 * Método para dormir el proceso unos segundos
	 */
	public void dormirProcedimiento() {
		try {
			for (int i = 0; i < 3; i++) {
				TimeUnit.SECONDS.sleep(1);
			}
		}catch(Exception e) {
			System.out.println(e);
		}
	}
}
