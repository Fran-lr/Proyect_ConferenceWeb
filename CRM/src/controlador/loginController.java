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
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.UsuarioDao;


public class loginController implements Initializable {
	@FXML
	private ImageView imgUsuario;
	@FXML
	private Label labelUsuario;
	@FXML
	private TextField textFieldUsuario;
	@FXML
	private Label labelContrasena;
	@FXML
	private PasswordField textFieldContrasena;
	@FXML
	private ImageView imgLogin;
	@FXML
	private Label labelAcceso;
	@FXML
	private Button btnAcceder;
	@FXML
	private Button btnLimpiar;
	
	boolean aux;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Se inicaliza el fondoe de los campos de tecto con un color rojo
		labelAcceso.setText("Introduzca sus credenciales");
		textFieldUsuario.setStyle("-fx-control-inner-background:#ef9a9a");
		textFieldContrasena.setStyle("-fx-control-inner-background:#ef9a9a");
	
	}

	@FXML
	public void KeyUser(KeyEvent event) {
	
		try {
			//Se obtiene un boolean para comprobar que el email tiene los caracteres correctos
			String acc = this.textFieldUsuario.getText();
			aux = ComprobarLogin(acc);

			if (textFieldUsuario.getText().isEmpty() || aux == false) {
				textFieldUsuario.setStyle("-fx-control-inner-background:#ef9a9a");
				labelAcceso.setText("Introduzca su usuario");

			} else if (!textFieldUsuario.getText().isEmpty() && aux == true) {
				textFieldUsuario.setStyle("-fx-control-inner-background:#b8daba");
				labelAcceso.setText("");
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			labelAcceso.setText("Introduzca su usuario");
		}
	}

	@FXML
	public void KeyPass(KeyEvent event) {
		try {
			//Se obtiene un boolean para comprobar que el email tiene los caracteres correctos
			String pass = this.textFieldContrasena.getText();
			aux = ComprobarLogin(pass);

			if (textFieldContrasena.getText().isEmpty() || aux == false) {
				textFieldContrasena.setStyle("-fx-control-inner-background:#ef9a9a");
				labelAcceso.setText("Introduzca su contraseña");

			} else if (!textFieldContrasena.getText().isEmpty() && aux == true) {
				textFieldContrasena.setStyle("-fx-control-inner-background:#b8daba");
				labelAcceso.setText("");
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			labelAcceso.setText("Introduzca su contraseña");
		}
	}

	@FXML
	public void acceder(ActionEvent event) {
		
		if (!textFieldUsuario.getText().isEmpty() && !textFieldContrasena.getText().isEmpty()){
			
			String per = this.textFieldUsuario.getText();
			String pass = this.textFieldContrasena.getText();
			UsuarioDao user = new UsuarioDao();
			boolean permitido = user.comprobarAcceso(per, pass);
		
			if(permitido) {
				labelAcceso.setStyle("-fx-text-fill:#0088cc");
				labelAcceso.setText("¡¡ Acceso permitido !!");
				dormirProcedimiento();
				
				try {
					// Cargo la vista
			        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/vistaPanelPrincipal.fxml"));

			       // Cargo la ventana
			        Parent root = loader.load();
			        
			        // Creo el Scene
			        Scene scene = new Scene(root);
			        Stage stage = new Stage();
			        stage.initModality(Modality.APPLICATION_MODAL);
			        stage.setScene(scene);
			        stage.show(); 
			        
			     // Cerrar ventana
		            Stage stageClose = (Stage) this.btnAcceder.getScene().getWindow();
		            stageClose.close();
			        
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				labelAcceso.setStyle("-fx-text-fill:#a51b0b");
				labelAcceso.setText("Contraseña Incorrecta");
			}
			
		}else {
			labelAcceso.setText("Introduzca sus credenciales");
		}
	}

	@FXML
	public void limpiar(ActionEvent event) {
		textFieldUsuario.setText(null);
		textFieldContrasena.setText(null);
		textFieldUsuario.setStyle("-fx-control-inner-background:#ef9a9a");
		textFieldContrasena.setStyle("-fx-control-inner-background:#ef9a9a");
		labelAcceso.setText("Introduzca sus credenciales");
	}
	
	//Metodo que contiene una expresion regular para comprobar el usuario y contraseña
	public boolean ComprobarLogin(String log) {
			//Empezamos diciendole al analizador sintáctico que encuentre el principio de la cadena (^), seguido de cualquier letra minúscula 
			//(a-z), número (0-9), un carácter de subrayado o un guión. A continuación, {3,16} asegura que sean al menos 3 de esos caracteres,
			//pero no más de 16. Por último, queremos el final de la cadena ($).

			Pattern pat = Pattern.compile("^[a-zA-Z0-9_-]{3,12}$");
			Matcher mat = pat.matcher(log);
			if (mat.find()) {
				return true;
			} else {
				return false;
			}

	}
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
