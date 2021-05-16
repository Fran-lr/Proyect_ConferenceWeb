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
import modelo.Ciudades;
import modelo.CiudadesDao;
import javafx.scene.input.KeyEvent;

import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

/**
 * Clase para responder a eventos e invocar peticiones al modelo Ciudades
 * @author FranJ
 * @version 1.0
 */
public class CiudadesController implements Initializable {
	@FXML
	private Label lbIni;
	@FXML
	private Label lbCodP;
	@FXML
	private Label lbNomCiud;
	@FXML
	private TextField tfNomCiud;
	@FXML
	private Button btnAnadir;
	@FXML
	private Button btnEditar;
	@FXML
	private Button btnBorrar;
	@FXML
	private Button btnVolver;
	@FXML
	private TableView<Ciudades> tbCiudades;
	@FXML
	private TableColumn<Ciudades, String> colNomCiu;
	@FXML
	private TableColumn<Ciudades, String> colCpCiu;
	@FXML
	private Label lbLisCiudad;
	@FXML
	private Label lbInfo;
	@FXML
	private TextField tfCodP;
	private boolean expresionRe;
	private ObservableList<Ciudades> listaCiudades;
	private CiudadesDao ciudadDao;

	/**
	 * Método para iniciar algunos controles de la interfaz
	 * @param arg0
	 * @param arg1
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		tfNomCiud.setStyle("-fx-control-inner-background:#ef9a9a");
		tfCodP.setStyle("-fx-control-inner-background:#ef9a9a");
		 //Asignamos las relación de las columnas con los atributos 
		this.colNomCiu.setCellValueFactory(new PropertyValueFactory<Ciudades, String>("nombreciudad"));
		this.colCpCiu.setCellValueFactory(new PropertyValueFactory<Ciudades, String>("codigopostal"));
		//Obtenemos la información de la BD y la enviamos a la tabla
		ciudadDao = new CiudadesDao();
		if(ciudadDao.mostrarCiudades().equals(null)) {
			lbInfo.setText("No hay información en la base de datos");
		}
		else {
			listaCiudades = FXCollections.observableArrayList(ciudadDao.mostrarCiudades());
			tbCiudades.setItems(listaCiudades);
			lbInfo.setText("Proceda con la Ciudad");
		}
	}

	/**
	 * Método para controlar el evento del campo de texto Nombre ciudad
	 * @param event
	 */
	@FXML
	public void keyCiudad(KeyEvent event) {
		try {
			// Se obtiene un boolean para comprobar que el nombre Ciudad tiene los caracteres correctos
			expresionRe = ComprobarNombreCiudad(this.tfNomCiud.getText());
			if (tfNomCiud.getText().isEmpty() || expresionRe == false) {
				tfNomCiud.setStyle("-fx-control-inner-background:#ef9a9a");
			} else if (!tfNomCiud.getText().isEmpty() && expresionRe == true) {
				tfNomCiud.setStyle("-fx-control-inner-background:#b8daba");
				lbInfo.setText("Proceda con la Ciudad");
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método para controlar el evento del campo de texto CP 
	 * @param event
	 */
	@FXML
	public void keyCP(KeyEvent event) {
		try {
			// Se obtiene un boolean para comprobar que el CP tiene los caracteres correctos
			expresionRe = ComprobarCP(this.tfCodP.getText());
			if (tfCodP.getText().isEmpty() || expresionRe == false) {
				tfCodP.setStyle("-fx-control-inner-background:#ef9a9a");
			} else if (!tfCodP.getText().isEmpty() && expresionRe == true) {
				tfCodP.setStyle("-fx-control-inner-background:#b8daba");
				lbInfo.setText("Proceda con la Ciudad");
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Mètodo para añadir una Ciudad
	 * @param event
	 */
	@FXML
	public void anadir(ActionEvent event) {
		try {
			//Se comprueba que los campos de texto contienen valor
			if (!this.tfNomCiud.getText().isEmpty() && !this.tfCodP.getText().isEmpty()) {

				Ciudades ciu = new Ciudades(this.tfNomCiud.getText(), this.tfCodP.getText());
				boolean contiene = false;
				//Se comprueba que no exista una ciudad con el mismo nombre
				for (int i = 0; i < listaCiudades.size(); i++) {
					if (listaCiudades.get(i).getNombreciudad().toLowerCase().equals(ciu.getNombreciudad().toLowerCase())) {
						contiene = true;
					}
				}
				if (!contiene) {
					// Se obtiene un boolean para comprobar que los campos de texto tiene los caracteres correctos
					boolean comNomCiu = ComprobarNombreCiudad(this.tfNomCiud.getText());
					boolean comCP = ComprobarCP(this.tfCodP.getText());
					/// Se obtiene un boolean para comprobar que los campos de texto tiene los caracteres correctos
					if (tfNomCiud.getText().isEmpty() || comNomCiu == false) {
						tfNomCiud.setStyle("-fx-control-inner-background:#ef9a9a");
						lbInfo.setText("Debes completar los campos correctamente");
					} else if (this.tfCodP.getText().isEmpty() || comCP == false) {
						tfCodP.setStyle("-fx-control-inner-background:#ef9a9a");
						lbInfo.setText("Debes completar los campos correctamente");
					} else if (!this.tfNomCiud.getText().isEmpty() && comNomCiu == true && !tfCodP.getText().isEmpty() && comCP == true) {
						tfNomCiud.setStyle("-fx-control-inner-background:#b8daba");
						//Incluimos la Ciudad
						ciudadDao = new CiudadesDao();
						boolean insertando = ciudadDao.insertarCiudad(ciu);
						if(insertando) {
							this.listaCiudades.add(ciu);
							this.tbCiudades.setItems(listaCiudades);
							lbInfo.setText("La Ciudad fue incluida con éxito");
						}
						else {
							lbInfo.setText("¡Error!. La ciudad no fue incluida");
						}
					}
				} else {
					//Ventana emergente de Alerta
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setHeaderText(null);
					alert.setTitle("Alerta");
					alert.setContentText("la Ciudad ya exixte");
					alert.showAndWait();
				}
			} else {
				//Ventana emergente de información
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setTitle("Información");
				alert.setContentText("Introduce los datos de la Ciudad");
				alert.showAndWait();
			}

		} catch (NumberFormatException nu) {
			//Ventana emergente de Error
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Error");
			alert.setContentText("Formato incorrecto");
			alert.showAndWait();
		}
	}

	/**
	 * Metodo para editar a una ciudad
	 * @param event
	 */
	@FXML
	public void editar(ActionEvent event) {
		
		 Ciudades ciudad = this.tbCiudades.getSelectionModel().getSelectedItem();
			
			if(ciudad== null) {
				//Ventana emergente de Alerta
				 Alert alert = new Alert(Alert.AlertType.WARNING);
		         alert.setHeaderText(null);
		         alert.setTitle("Alerta");
		         alert.setContentText("Debes seleccionar una Ciudad");
		         alert.showAndWait();
			}else {
				try {
					//Creamos una ciudad con los campos de texto
					Ciudades ciudadAux = new Ciudades(this.tfNomCiud.getText(),this.tfCodP.getText());
					boolean contiene = false;
					//Comprobamos que no existan dos usuario con el mismo nombre
					 for (int i = 0; i < listaCiudades.size() ; i++) {
						 if(listaCiudades.get(i).getNombreciudad().toLowerCase().equals(ciudadAux.getNombreciudad().toLowerCase())) {
					    	 contiene = true;
					     }
					 }
					if(!contiene) {
						 //Comprobamos que los campos de texto tenga un valor correcto
						boolean nombrCiuCorrect = ComprobarNombreCiudad(this.tfNomCiud.getText());
						boolean cPcorrect  = ComprobarCP(this.tfCodP.getText());
						
						if (tfNomCiud.getText().isEmpty() || cPcorrect == false) {
							tfNomCiud.setStyle("-fx-control-inner-background:#ef9a9a");
							lbInfo.setText("Debes completar los campos correctamente");
						}else if(tfCodP.getText().isEmpty() || nombrCiuCorrect == false){
							tfCodP.setStyle("-fx-control-inner-background:#ef9a9a");
							lbInfo.setText("Debes completar los campos correctamente");
						} else if (!tfNomCiud.getText().isEmpty() && cPcorrect == true && !tfCodP.getText().isEmpty() && nombrCiuCorrect == true) {
							tfNomCiud.setStyle("-fx-control-inner-background:#b8daba");
							tfCodP.setStyle("-fx-control-inner-background:#b8daba");
							//Modificamos la sala seleccionada
							ciudad.setNombreciudad(ciudadAux.getNombreciudad());
							ciudad.setCodigopostal(ciudadAux.getCodigopostal());
							ciudadDao = new CiudadesDao();
							boolean editar = ciudadDao.modificarCiudad(ciudad);
							if(editar) {
								this.tbCiudades.refresh();
								lbInfo.setText("La Ciudad fue modificada con éxito");
							}
							else {
								lbInfo.setText("¡Error!. La Ciudad no fue modificado");
							}
						}
					}
					else {
						//Ventana emergente de Alerta
					     Alert alert = new Alert(Alert.AlertType.WARNING);
				         alert.setHeaderText(null);
				         alert.setTitle("Alerta");
				         alert.setContentText("La ciudad ya exixte");
				         alert.showAndWait();
					}
				} catch (NumberFormatException  nu) {
					//Ventana emergente de Error
					 Alert alert = new Alert(Alert.AlertType.ERROR);
			         alert.setHeaderText(null);
			         alert.setTitle("Error");
			         alert.setContentText("Formato incorrecto");
			         alert.showAndWait();
				}
			}
	}

	/**
	 * Metodo para borrar la ciudad seleccionada
	 * @param event
	 */
	@FXML
	public void borrar(ActionEvent event) {
		//Se obtiene la ciudad seleccionada de la tabla
		Ciudades ciudad = this.tbCiudades.getSelectionModel().getSelectedItem();
		//Revisamos si tiene valor
		if (ciudad == null) {
			//Ventana emergente de Alerta
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setHeaderText(null);
			alert.setTitle("Alerta");
			alert.setContentText("Debes seleccionar una Ciudad");
			alert.showAndWait();
		} else {
			//Borramos Ciudad
			ciudadDao = new CiudadesDao();
			boolean contieneOrden = ciudadDao.consultaCiudadTieneOrden(ciudad.getIdciudad());
			if(!contieneOrden) {
				boolean borrada = ciudadDao.borrarCiudad(ciudad);
				if(borrada) {
					this.listaCiudades.remove(ciudad);
					this.tbCiudades.refresh();
					lbInfo.setText("La Ciudad fue borrada con éxito");
				}
				else {
					lbInfo.setText("¡Error!. La Ciudad no fue borrada");	
				}
			}
			else {
				lbInfo.setText("La ciudad no se puede borrar tiene una Orden");	
			}
		}
		
	}
	/**
	 * Método para volver al menú principal
	 * Cierra la ventana actual
	 * @param event
	 */
	@FXML
	public void volver(ActionEvent event) {
		try {
			// Cargo la vista
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VistaPanelPrincipal.fxml"));
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
	public void selecTablaCiud(MouseEvent event) {

		lbInfo.setText("Proceda con la Ciudad");
		//Selecciona la ciudad de la tabla
		Ciudades ciudad = this.tbCiudades.getSelectionModel().getSelectedItem();
		try {
			//Comprueba los valores de los campos
			if (ciudad != null) {
				this.tfNomCiud.setText(ciudad.getNombreciudad());
				this.tfCodP.setText(ciudad.getCodigopostal());
			}
			// Se obtiene un boolean para comprobar que los campos tiene los caracteres correctos
			boolean nombrCorrect = ComprobarNombreCiudad(this.tfNomCiud.getText());
			boolean cPcorrect = ComprobarCP(this.tfCodP.getText());

			if (tfNomCiud.getText().isEmpty() || cPcorrect == false) {
				tfNomCiud.setStyle("-fx-control-inner-background:#ef9a9a");
			} else if (!tfNomCiud.getText().isEmpty() && cPcorrect == true) {
				tfNomCiud.setStyle("-fx-control-inner-background:#b8daba");
				lbInfo.setText("Proceda con la Ciudad");
			}
			if (tfCodP.getText().isEmpty() || nombrCorrect == false) {
				tfCodP.setStyle("-fx-control-inner-background:#ef9a9a");
			} else if (!tfCodP.getText().isEmpty() && nombrCorrect == true) {
				tfCodP.setStyle("-fx-control-inner-background:#b8daba");
				lbInfo.setText("Proceda con la Ciudad");
			}
		} catch (NullPointerException n) {
			n.printStackTrace();
			System.out.println("!! Error -> "+n.getMessage());
		}
	}

	/**
	 * Método para controlar los caracteres del campo Nombre de la Ciudad
	 * @param log
	 * @return
	 */
	public boolean ComprobarNombreCiudad(String log) {
		Pattern pat = Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚ\s]{2,18}$");
		Matcher mat = pat.matcher(log);
		if (mat.find()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Método para controlar los caracteres del campo CP
	 * @param log
	 * @return
	 */
	public boolean ComprobarCP(String log) {
		Pattern pat = Pattern.compile("^[0-9]{5}$");
		Matcher mat = pat.matcher(log);
		if (mat.find()) {
			return true;
		} else {
			return false;
		}
	}

}
