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
import modelo.Clientes;
import modelo.ClientesDao;
import javafx.scene.input.KeyEvent;

import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

/**
 * Clase para responder a eventos e invocar peticiones al modelo Clientes
 * @author FranJ
 * @version 1.0
 */
public class ClientesController implements Initializable{
	@FXML
	private Label lbIni;
	@FXML
	private Label lbNomCli;
	@FXML
	private Label lbNif;
	@FXML
	private TextField tfNomCli;
	@FXML
	private TextField tfNif;
	@FXML
	private Button btnAnadir;
	@FXML
	private Button btnEditar;
	@FXML
	private Button btnBorrar;
	@FXML
	private Button btnVolver;
	@FXML
	private TableView <Clientes>tbClientes;
	@FXML
	private TableColumn <Clientes, String>colNomCli;
	@FXML
	private TableColumn <Clientes, String>colNif;
	@FXML
	private Label lbListClien;
	@FXML
	private Label lbInfo;
	private boolean expresionRe;
	private ObservableList<Clientes> listaClientes;
	private ClientesDao clienteDao;
	
	/**
	 * Método para iniciar algunos controles de la interfaz
	 * @param arg0
	 * @param arg1
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		tfNomCli.setStyle("-fx-control-inner-background:#ef9a9a");
		tfNif.setStyle("-fx-control-inner-background:#ef9a9a");
		 //Asignamos las relación de las columnas con los atributos
		this.colNomCli.setCellValueFactory(new PropertyValueFactory<Clientes, String>("nombreCliente"));
		this.colNif.setCellValueFactory(new PropertyValueFactory<Clientes, String>("nif"));
		//Obtenemos la informacion de la BD y la enviamos a la tabla
		clienteDao = new ClientesDao();
		if(clienteDao.mostrarClientes().equals(null)){
			lbInfo.setText("No hay información en la base de datos");
		}
		else {
			listaClientes = FXCollections.observableArrayList(clienteDao.mostrarClientes());
			tbClientes.setItems(listaClientes);
			lbInfo.setText("Proceda con el Cliente");
		}
	}
	
	/**
	 * Método para controlar el evento del campo de texto Nombre cliente
	 * @param event
	 */
	@FXML
	public void keyNomCliente(KeyEvent event) {
		try {
			// Se obtiene un boolean para comprobar que el nombre del cliente tiene los caracteres correctos
			expresionRe = ComprobarNombreCliente(this.tfNomCli.getText());
			if (tfNomCli.getText().isEmpty() || expresionRe == false) {
				tfNomCli.setStyle("-fx-control-inner-background:#ef9a9a");
			} else if (!tfNomCli.getText().isEmpty() && expresionRe == true) {
				tfNomCli.setStyle("-fx-control-inner-background:#b8daba");
				lbInfo.setText("Proceda con el Cliente");
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método para controlar el evento del campo de texto Nif
	 * @param event
	 */
	@FXML
	public void KeyNif(KeyEvent event) {
		try {
			// Se obtiene un boolean para comprobar que el Nif tiene los caracteres correctos
			expresionRe = ComprobarNif(this.tfNif.getText());
			if (tfNif.getText().isEmpty() || expresionRe == false) {
				tfNif.setStyle("-fx-control-inner-background:#ef9a9a");
			} else if (!tfNif.getText().isEmpty() && expresionRe == true) {
				tfNif.setStyle("-fx-control-inner-background:#b8daba");
				lbInfo.setText("Proceda con el Cliente");
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método para añadir a un Cliente
	 * @param event
	 */
	@FXML
	public void anadir(ActionEvent event) {
		try {
			//Se comprueba que los campos de texto contienen valor
			if (!this.tfNomCli.getText().isEmpty() && !this.tfNif.getText().isEmpty()) {
				Clientes cliente = new Clientes (this.tfNomCli.getText(), this.tfNif.getText());
				boolean contiene = false;
				//Se comprueba que no exista un cliente con el mismo nombre
				for (int i = 0; i < listaClientes.size(); i++) {
					if (listaClientes.get(i).getNombreCliente().toLowerCase().equals(cliente.getNombreCliente().toLowerCase()) 
					|| listaClientes.get(i).getNif().toUpperCase().equals(cliente.getNif().toUpperCase())) {
						contiene = true;
					}
				}
				if (!contiene) {
					// Se obtiene un boolean para comprobar que los campos de texto tiene los caracteres correctos
					boolean comNomCli = ComprobarNombreCliente(this.tfNomCli.getText());
					boolean comNif = ComprobarNif(this.tfNif.getText());
					//Se comprueba que los campos de texto sean correctos
					if (tfNomCli.getText().isEmpty() || comNomCli == false) {
						tfNomCli.setStyle("-fx-control-inner-background:#ef9a9a");
						lbInfo.setText("Debes completar los campos correctamente");
					} else if (this.tfNif.getText().isEmpty() || comNif == false) {
						tfNif.setStyle("-fx-control-inner-background:#ef9a9a");
						lbInfo.setText("Debes completar los campos correctamente");
					} else if (!this.tfNomCli.getText().isEmpty() && comNomCli == true && !tfNif.getText().isEmpty() && comNif == true) {
						tfNomCli.setStyle("-fx-control-inner-background:#b8daba");
						//Incluimos el nuevo Cliente
						clienteDao = new ClientesDao();
						boolean insertando = clienteDao.insertarCliente(cliente);
						if(insertando) {
							this.listaClientes.add(cliente);
							this.tbClientes.setItems(listaClientes);
							lbInfo.setText("El Cliente fue incluido con éxito");
						}
						else {
							lbInfo.setText("¡Error!. El cliente no fue incluido");
						}
					}
				} else {
					//Ventana emergente de Alerta
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setHeaderText(null);
					alert.setTitle("Alerta");
					alert.setContentText("El Cliente ya existe revisa la tabla");
					alert.showAndWait();
				}
			} else {
				//Ventana emergente de información
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setTitle("Información");
				alert.setContentText("Introduce los datos del Cliente");
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
	 * Método para editar a un cliente
	 * @param event
	 */
	@FXML
	public void editar(ActionEvent event) {
		
		Clientes cliente = this.tbClientes.getSelectionModel().getSelectedItem();
		if(cliente == null) {
			//Ventana emergente de Alerta
			 Alert alert = new Alert(Alert.AlertType.WARNING);
	         alert.setHeaderText(null);
	         alert.setTitle("Alerta");
	         alert.setContentText("Debes seleccionar un Cliente");
	         alert.showAndWait();
		}else {
			try {
				//Creamos un cliente con los campos del texto
				Clientes clienteAux = new Clientes(this.tfNomCli.getText(),this.tfNif.getText());
				boolean contiene = false;
				//Comprobamos que no exixtan un cliente con el mismo nombre
				 for (int i = 0; i < listaClientes.size() ; i++) {
					 if(listaClientes.get(i).getNombreCliente().toLowerCase().equals(clienteAux.getNombreCliente().toLowerCase())) {
				    	 contiene = true;
				     }
				 }
				if(!contiene) {
					 //Comprobamos que los campos de texto tenga un valor correcto
					boolean comNomCli = ComprobarNombreCliente(this.tfNomCli.getText());
					boolean comNif = ComprobarNif(this.tfNif.getText());
					
					if (tfNomCli.getText().isEmpty() || comNomCli == false) {
						tfNomCli.setStyle("-fx-control-inner-background:#ef9a9a");
						lbInfo.setText("Debes completar los campos correctamente");
					}else if(tfNif.getText().isEmpty() || comNif == false){
						tfNif.setStyle("-fx-control-inner-background:#ef9a9a");
						lbInfo.setText("Debes completar los campos correctamente");
					} else if (!tfNomCli.getText().isEmpty() && comNomCli == true && !tfNif.getText().isEmpty() && comNif == true) {	
						tfNomCli.setStyle("-fx-control-inner-background:#b8daba");
						tfNif.setStyle("-fx-control-inner-background:#b8daba");
						//Modificamos el cliente seleccionado
						cliente.setNombreCliente(clienteAux.getNombreCliente());
						cliente.setNif(clienteAux.getNif());
						clienteDao = new ClientesDao();
						boolean editar = clienteDao.modificarClientes(cliente);
						if(editar) {
							this.tbClientes.refresh();
							lbInfo.setText("El Cliente fue modificado con éxito");
						}
						else {
							lbInfo.setText("¡Error!. El Cliente no fue modificado");
						}
					}
				}
				else {
					//Ventana emergente de Alerta
				     Alert alert = new Alert(Alert.AlertType.WARNING);
			         alert.setHeaderText(null);
			         alert.setTitle("Alerta");
			         alert.setContentText("El Cliente ya existe");
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
	 * Método para borrar la ciudad seleccionada
	 * @param event
	 */
	@FXML
	public void borrar(ActionEvent event) {
		//Se obtiene el cliente seleccionado del tabla
		Clientes cliente= this.tbClientes.getSelectionModel().getSelectedItem();
		//Revisamos si tiene valor
		if (cliente == null) {
			//Ventana emergente de Alerta
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setHeaderText(null);
			alert.setTitle("Alerta");
			alert.setContentText("Debes seleccionar un Cliente");
			alert.showAndWait();
		} else {
			clienteDao = new ClientesDao();
			boolean contieneOrden = clienteDao.consultaClienteTieneOrden(cliente.getIdcliente());
			if(!contieneOrden) {
				boolean borrado = clienteDao.borrarClientes(cliente);
				if(borrado) {
					this.listaClientes.remove(cliente);
					this.tbClientes.refresh();
					lbInfo.setText("El Cliente fue borrado con exito");
				}
				else {
					lbInfo.setText("¡Error!. El cliente no fue borrado");	
				}
			}
			else {
				lbInfo.setText("El cliente no se puede borrar tiene una Orden");
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
	public void selectTablaClien(MouseEvent event) {
		
		lbInfo.setText("Proceda con el Cliente");
		//Selecciona la ciudad de la tabla
		Clientes cliente = this.tbClientes.getSelectionModel().getSelectedItem();
		try {
			//Comprueba los valores de los campos
			if (cliente != null) {
				this.tfNomCli.setText(cliente.getNombreCliente());
				this.tfNif.setText(cliente.getNif());
			}
			// Se obtiene un boolean para comprobar que los campos tiene los caracteres correctos
			boolean nombrCorrect = ComprobarNombreCliente(this.tfNomCli.getText());
			boolean cPcorrect = ComprobarNif(this.tfNif.getText());

			if (tfNomCli.getText().isEmpty() || cPcorrect == false) {
				tfNomCli.setStyle("-fx-control-inner-background:#ef9a9a");
			} else if (!tfNomCli.getText().isEmpty() && cPcorrect == true) {
				tfNomCli.setStyle("-fx-control-inner-background:#b8daba");
				lbInfo.setText("Proceda con el Cliente");
			}
			if (tfNif.getText().isEmpty() || nombrCorrect == false) {
				tfNif.setStyle("-fx-control-inner-background:#ef9a9a");
			} else if (!tfNif.getText().isEmpty() && nombrCorrect == true) {
				tfNif.setStyle("-fx-control-inner-background:#b8daba");
				lbInfo.setText("Proceda con el Cliente");
			}
		} catch (NullPointerException n) {
			n.printStackTrace();
			System.out.println("!! Error -> "+n.getMessage());
		}
	}

	/**
	 * Método para controlar los caracteres del campo Nombre Cliente
	 * @param log
	 * @return
	 */
	public boolean ComprobarNombreCliente(String log) {

		Pattern pat = Pattern.compile("^[A-Za-zZáéíóúÁÉÍÓÚ\s._%+@-]{4,20}$");
		Matcher mat = pat.matcher(log);
		if (mat.find()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Método para controlar los caracteres del campo Nif
	 * @param log
	 * @return
	 */
	public boolean ComprobarNif(String log) {

		Pattern pat = Pattern.compile("^[0-9]{7,8}[T|R|W|A|G|M|Y|F|P|D|X|B|N|J|Z|S|Q|V|H|L|C|K|E]$");
		Matcher mat = pat.matcher(log);
		if (mat.find()) {
			return true;
		} else {
			return false;
		}
	}
	
}
