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
import modelo.Salas;
import modelo.SalasDao;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

/**
 * Clase para responder a eventos e invocar peticiones al modelo Salas
 * @author FranJ
 * @version 1.0
 */
public class SalasController implements Initializable{
	@FXML
	private Label lbIni;
	@FXML
	private Label lbCentro;
	@FXML
	private Label lbDirec;
	@FXML
	private TextField tfCentro;
	@FXML
	private Label lbSala;
	@FXML
	private TextField tfNSala;
	@FXML
	private Label lbAsistentes;
	@FXML
	private Button btnAnadir;
	@FXML
	private Button btnEditar;
	@FXML
	private Button btnBorrar;
	@FXML
	private Button btnVolver;
	@FXML
	private TableView<Salas>tbSalas;
	@FXML
	private TableColumn <Salas,String> colDirec;
	@FXML
	private TableColumn <Salas,String> colCentro;
	@FXML
	private TableColumn <Salas,String> colNSala;
	@FXML
	private TableColumn <Salas,String> colAsistentes;
	@FXML
	private Label lbListSalas;
	@FXML
	private TextField tfAsistentes;
	@FXML
	private Label lbInfo;
	@FXML
	private TextField tftDirec;
	private boolean expresionRe;
	private ObservableList<Salas> listaSalas;
	private SalasDao salaDao;

	/**
	 * Método para iniciar algunos controles de la interfaz
	 * @param arg0
	 * @param arg1
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		tftDirec.setStyle("-fx-control-inner-background:#ef9a9a");
		tfCentro.setStyle("-fx-control-inner-background:#ef9a9a");
		tfNSala.setStyle("-fx-control-inner-background:#ef9a9a");
		tfAsistentes.setStyle("-fx-control-inner-background:#ef9a9a");
		 //Asignamos las relación de las columnas con los atributos 
		this.colDirec.setCellValueFactory(new PropertyValueFactory<Salas, String>("direccion"));
		this.colCentro.setCellValueFactory(new PropertyValueFactory<Salas, String>("centro"));
		this.colNSala.setCellValueFactory(new PropertyValueFactory<Salas, String>("nsala"));
		this.colAsistentes.setCellValueFactory(new PropertyValueFactory<Salas, String>("nasistentes"));
		//Obtenemos la información de la BD y la enviamos a la tabla
		salaDao = new SalasDao();
		if(salaDao.mostrarSalas().equals(null)) {
			lbInfo.setText("No hay información en la base de datos");
		}
		else {
			listaSalas = FXCollections.observableArrayList(salaDao.mostrarSalas());
			tbSalas.setItems(listaSalas);
			lbInfo.setText("Proceda con la Sala");
		}
	}
	/**
	 * Metodo para controlar el evento del campo de texto Dirección
	 * @param event
	 */
	@FXML
	public void KeyDireccion(KeyEvent event) {
		try {
			// Se obtiene un boolean para comprobar que la dirección tiene los caracteres correctos
			expresionRe = ComprobarCampos(this.tftDirec.getText());
			if (tftDirec.getText().isEmpty() || expresionRe == false) {
				tftDirec.setStyle("-fx-control-inner-background:#ef9a9a");
			} else if (!tftDirec.getText().isEmpty() && expresionRe == true) {
				tftDirec.setStyle("-fx-control-inner-background:#b8daba");
				lbInfo.setText("Proceda con la Sala");
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Método para controlar el evento del campo de texto Centro
	 * @param event
	 */
	@FXML
	public void keCentro(KeyEvent event) {
		try {
			// Se obtiene un boolean para comprobar que el centro tiene los caracteres correctos
			expresionRe = ComprobarCampos(this.tfCentro.getText());
			if (tfCentro.getText().isEmpty() || expresionRe == false) {
				tfCentro.setStyle("-fx-control-inner-background:#ef9a9a");
			} else if (!tfCentro.getText().isEmpty() && expresionRe == true) {
				tfCentro.setStyle("-fx-control-inner-background:#b8daba");
				lbInfo.setText("Proceda con la Sala");
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Método para controlar el evento del campo de texto N Sala
	 * @param event
	 */
	@FXML
	public void keyNsala(KeyEvent event) {
		try {
			// Se obtiene un boolean para comprobar que nº sala tiene los caracteres correctos
			expresionRe = ComprobarCampos(this.tfNSala.getText());
			if (tfNSala.getText().isEmpty() || expresionRe == false) {
				tfNSala.setStyle("-fx-control-inner-background:#ef9a9a");
			} else if (!tfNSala.getText().isEmpty() && expresionRe == true) {
				tfNSala.setStyle("-fx-control-inner-background:#b8daba");
				lbInfo.setText("Proceda con la Sala");
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método para controlar el evento del campo de texto nº Asistentes
	 * @param event
	 */
	@FXML
	public void keyNasistentes(KeyEvent event) {
		try {
			// Se obtiene un boolean para comprobar que los asistentes tiene los caracteres correctos
			expresionRe = ComprobarAsistentes(this.tfAsistentes.getText());
			if (tfAsistentes.getText().isEmpty() || expresionRe == false) {
				tfAsistentes.setStyle("-fx-control-inner-background:#ef9a9a");
			} else if (!tfAsistentes.getText().isEmpty() && expresionRe == true) {
				tfAsistentes.setStyle("-fx-control-inner-background:#b8daba");
				lbInfo.setText("Proceda con la Sala");
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método para añadir a una Sala
	 * @param event
	 */
	@FXML
	public void anadir(ActionEvent event) {
		try {
			//Se comprueba que los campos de texto contienen valor
			if (!this.tftDirec.getText().isEmpty() && !this.tfCentro.getText().isEmpty() && !this.tfNSala.getText().isEmpty() && !this.tfAsistentes.getText().isEmpty()) {

				Salas sala= new Salas (this.tftDirec.getText(), this.tfCentro.getText(),this.tfNSala.getText(),this.tfAsistentes.getText());
				boolean contiene = false;
				//Se comprueba que no exista una sala con este repetida
				for (int i = 0; i < listaSalas.size(); i++) {
					if (listaSalas.get(i).getDireccion().toLowerCase().equals(sala.getDireccion().toLowerCase()) &&
						listaSalas.get(i).getCentro().toLowerCase().equals(sala.getCentro().toLowerCase()) &&
						listaSalas.get(i).getNsala().toLowerCase().equals(sala.getNsala().toLowerCase())) {
						contiene = true;
					}
				}
				if (!contiene) {
					// Se obtiene un boolean para comprobar que los campos de texto tiene los caracteres correctos
					boolean direcCorrect = ComprobarCampos(this.tftDirec.getText());
					boolean centroCorrect = ComprobarCampos(this.tfCentro.getText());
					boolean nsalaCorrect = ComprobarCampos(this.tfNSala.getText());
					boolean nasistCorrect = ComprobarAsistentes(this.tfAsistentes.getText());
					//Se comprueba que los campos de texto sean correctos
					if (tftDirec.getText().isEmpty() || direcCorrect == false) {
						tftDirec.setStyle("-fx-control-inner-background:#ef9a9a");
						lbInfo.setText("Debes completar los campos correctamente");
					} else if (this.tfCentro.getText().isEmpty() || centroCorrect == false) {
						tfCentro.setStyle("-fx-control-inner-background:#ef9a9a");
						lbInfo.setText("Debes completar los campos correctamente");
					}else if(this.tfNSala.getText().isEmpty() || nsalaCorrect == false) {
						tfNSala.setStyle("-fx-control-inner-background:#ef9a9a");
						lbInfo.setText("Debes completar los campos correctamente");
					}else if(this.tfAsistentes.getText().isEmpty() || nasistCorrect == false){
						tfAsistentes.setStyle("-fx-control-inner-background:#ef9a9a");
						lbInfo.setText("Debes completar los campos correctamente");	
					}
					else if (!this.tftDirec.getText().isEmpty() && direcCorrect == true && !tfCentro.getText().isEmpty() && centroCorrect == true &&
							!this.tfNSala.getText().isEmpty() && nsalaCorrect == true && !tfAsistentes.getText().isEmpty() && nasistCorrect == true) {
						tftDirec.setStyle("-fx-control-inner-background:#b8daba");
						tfCentro.setStyle("-fx-control-inner-background:#b8daba");
						tfNSala.setStyle("-fx-control-inner-background:#b8daba");
						tfAsistentes.setStyle("-fx-control-inner-background:#b8daba");
						//Incluimos la nueva sala
						salaDao = new SalasDao();
						boolean insertando = salaDao.insertarSala(sala);
						if(insertando) {
							this.listaSalas.add(sala);
							this.tbSalas.setItems(listaSalas);
							lbInfo.setText("La sala fue incluida con éxito");
						}
						else {
							lbInfo.setText("¡Error!. La sala no fue incluida");
						}
					}
				} else {
					//Ventana emergente de Alerta
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setHeaderText(null);
					alert.setTitle("Alerta");
					alert.setContentText("La Sala ya exixte revisa la tabla");
					alert.showAndWait();
				}
			} else {
				//Ventana emergente de información
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setTitle("Información");
				alert.setContentText("Introduce la Sala");
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
	 * Método para editar a una sala
	 * @param event
	 */
	@FXML
	public void editar(ActionEvent event) {
		Salas sala = this.tbSalas.getSelectionModel().getSelectedItem();
			
			if(sala == null) {
				//Ventana emergente de Alerta
				 Alert alert = new Alert(Alert.AlertType.WARNING);
		         alert.setHeaderText(null);
		         alert.setTitle("Alerta");
		         alert.setContentText("Debes seleccionar una Sala");
		         alert.showAndWait();
			}else {
				try {
					//Creamos una sala con los campos del texto
					Salas salaAux= new Salas (this.tftDirec.getText(), this.tfCentro.getText(),this.tfNSala.getText(),this.tfAsistentes.getText());
					boolean contiene = false;
					//Comprobamos que no exixtan una sala repetida
					for (int i = 0; i < listaSalas.size(); i++) {
						if (listaSalas.get(i).getDireccion().toLowerCase().equals(salaAux.getDireccion().toLowerCase()) &&
							listaSalas.get(i).getCentro().toLowerCase().equals(salaAux.getCentro().toLowerCase()) &&
							listaSalas.get(i).getNsala().toLowerCase().equals(salaAux.getNsala().toLowerCase())) {
							contiene = true;
						}
					}
					if(!contiene) {
						// Se obtiene un boolean para comprobar que la sala tiene los caracteres correcto
						boolean direcCorrect = ComprobarCampos(this.tftDirec.getText());
						boolean centroCorrect = ComprobarCampos(this.tfCentro.getText());
						boolean nsalaCorrect = ComprobarCampos(this.tfNSala.getText());
						boolean nasistCorrect = ComprobarAsistentes(this.tfAsistentes.getText());

						if (tftDirec.getText().isEmpty() || direcCorrect == false) {
							tftDirec.setStyle("-fx-control-inner-background:#ef9a9a");
							lbInfo.setText("Debes completar los campos correctamente");
						} else if (this.tfCentro.getText().isEmpty() || centroCorrect == false) {
							tfCentro.setStyle("-fx-control-inner-background:#ef9a9a");
							lbInfo.setText("Debes completar los campos correctamente");
						}else if(this.tfNSala.getText().isEmpty() || nsalaCorrect == false) {
							tfNSala.setStyle("-fx-control-inner-background:#ef9a9a");
							lbInfo.setText("Debes completar los campos correctamente");
						}else if(this.tfAsistentes.getText().isEmpty() || nasistCorrect == false){
							tfAsistentes.setStyle("-fx-control-inner-background:#ef9a9a");
							lbInfo.setText("Debes completar los campos correctamente");
						}
						else if (!this.tftDirec.getText().isEmpty() && direcCorrect == true && !tfCentro.getText().isEmpty() && centroCorrect == true &&
								!this.tfNSala.getText().isEmpty() && nsalaCorrect == true && !tfAsistentes.getText().isEmpty() && nasistCorrect == true) {
							tftDirec.setStyle("-fx-control-inner-background:#b8daba");
							tfCentro.setStyle("-fx-control-inner-background:#b8daba");
							tfNSala.setStyle("-fx-control-inner-background:#b8daba");
							tfAsistentes.setStyle("-fx-control-inner-background:#b8daba");
							//Modificamos la sala seleccionada
							sala.setDireccion(salaAux.getDireccion());
							sala.setCentro(salaAux.getCentro());
							sala.setNsala(salaAux.getNsala());
							sala.setNasistentes(salaAux.getNasistentes());
							salaDao = new SalasDao();
							boolean editado = salaDao.modificarSala(sala);
							if(editado) {
								lbInfo.setText("La Sala fue modificada con éxito");
								this.tbSalas.refresh();
							}
							else {
								lbInfo.setText("¡Error!. La sala no fue modificada");
							}
						}
					}
					else {
						//Ventana emergente de Alerta
					     Alert alert = new Alert(Alert.AlertType.WARNING);
				         alert.setHeaderText(null);
				         alert.setTitle("Alerta");
				         alert.setContentText("La Sala ya exixte revisa la tabla");
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
	 * Método para borrar la sala seleccionada
	 * @param event
	 */
	@FXML
	public void borrar(ActionEvent event) {
		//Se obtiene la sala seleccionada del tabla
		Salas sala = this.tbSalas.getSelectionModel().getSelectedItem();
		//Revisamos si tiene valor
		if (sala == null) {
			//Ventana emergente de Alerta
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setHeaderText(null);
			alert.setTitle("Alerta");
			alert.setContentText("Debes seleccionar una Sala");
			alert.showAndWait();
		} else {
			//Borramos Sala
			salaDao = new SalasDao();
			boolean contieneOrden = salaDao.consultaSalaTieneOrden(sala.getIdsala());
			if (!contieneOrden) {
				boolean borrada = salaDao.borrarSala(sala);
				if(borrada) {
				this.listaSalas.remove(sala);
				this.tbSalas.refresh();
				lbInfo.setText("La Sala fue borrada con éxito");
				}
				else {
					lbInfo.setText("¡Error!. La Sala no fue borrada");
				}
			}
			else {
				lbInfo.setText("La sala no se puede borrar tiene una Orden");
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
	 * Método que controlar el evento de la selección de la tabla
	 * @param event
	 */
	@FXML
	public void selectTablaSalas(MouseEvent event) {
		lbInfo.setText("Proceda con la Sala");
		//Selecciona la sala de la tabla
		Salas sala = this.tbSalas.getSelectionModel().getSelectedItem();
		try {
			//Comprueba los valores de los campos
			if (sala != null) {
				this.tftDirec.setText(sala.getDireccion());
				this.tfCentro.setText(sala.getCentro());
				this.tfNSala.setText(sala.getNsala());
				this.tfAsistentes.setText(sala.getNasistentes());
			}
			// Se obtiene un boolean para comprobar que los campos tiene los caracteres correctos
			boolean direcCorrect = ComprobarCampos(this.tftDirec.getText());
			boolean centroCorrect = ComprobarCampos(this.tfCentro.getText());
			boolean nsalaCorrect = ComprobarCampos(this.tfNSala.getText());
			boolean nasistCorrect = ComprobarAsistentes(this.tfAsistentes.getText());

			if (tftDirec.getText().isEmpty() || direcCorrect == false) {
				tftDirec.setStyle("-fx-control-inner-background:#ef9a9a");
			} else if (!tftDirec.getText().isEmpty() && direcCorrect == true) {
				tftDirec.setStyle("-fx-control-inner-background:#b8daba");
				lbInfo.setText("Proceda con la Sala");
			}
			if (tfCentro.getText().isEmpty() || centroCorrect == false) {
				tfCentro.setStyle("-fx-control-inner-background:#ef9a9a");
			} else if (!tfCentro.getText().isEmpty() && centroCorrect== true) {
				tfCentro.setStyle("-fx-control-inner-background:#b8daba");
				lbInfo.setText("Proceda con la Sala");
			}
			if (tfNSala.getText().isEmpty() || nsalaCorrect == false) {
				tfNSala.setStyle("-fx-control-inner-background:#ef9a9a");
			} else if (!tfNSala.getText().isEmpty() && nsalaCorrect == true) {
				tfNSala.setStyle("-fx-control-inner-background:#b8daba");
				lbInfo.setText("Proceda con la Sala");
			}
			if (tfAsistentes.getText().isEmpty() || nasistCorrect == false) {
				tfAsistentes.setStyle("-fx-control-inner-background:#ef9a9a");
			} else if (!tfAsistentes.getText().isEmpty() && nasistCorrect == true) {
				tfAsistentes.setStyle("-fx-control-inner-background:#b8daba");
				lbInfo.setText("Proceda con la Sala");
			}
		} catch (NullPointerException n) {
			n.printStackTrace();
			System.out.println("!! Error -> "+n.getMessage());
		}
	}
	/**
	 * Método para controlar los caracteres de los campos Dirección, Centro y N Sala
	 * @param log
	 * @return
	 */
	public boolean ComprobarCampos(String log) {
		Pattern pat = Pattern.compile("^[a-zA-Z0-9áéíóúÁÉÍÓÚ\s._-]{2,20}$");
		Matcher mat = pat.matcher(log);
		if (mat.find()) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Metodo para controlar los caracteres del campo N Asistente
	 * @param log
	 * @return
	 */
	public boolean ComprobarAsistentes(String log) {
		//Solo debe contenner números
		Pattern pat = Pattern.compile("^[0-9]{1,5}$");
		Matcher mat = pat.matcher(log);
		if (mat.find()) {
			return true;
		} else {
			return false;
		}
	}
}
