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
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;

import static java.time.temporal.ChronoUnit.DAYS;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import modelo.Ciudades;
import modelo.CiudadesDao;
import modelo.Clientes;
import modelo.ClientesDao;
import modelo.Orden;
import modelo.OrdenDao;
import modelo.Salas;
import modelo.SalasDao;
import reportes.Reporte;
import javafx.scene.input.KeyEvent;

import javafx.scene.control.TableView;

import javafx.scene.control.DatePicker;

import javafx.scene.control.TableColumn.*;

/**
 * Clase para responder a eventos e invocar peticiones al modelo Orden
 * @author FranJ
 * @version 1.0
 */
public class OrdenController  implements Initializable{
	@FXML
	private Label lbIni;
	@FXML
	private Button btnAnadir;
	@FXML
	private Button btnEditar;
	@FXML
	private Button btnBorrar;
	@FXML
	private Button btnVolver;
	@FXML
	private Button btnReset;
	@FXML
	private TableView <Salas>tbSalas;
	@FXML
	private TableColumn <Salas,Integer> colIdSala;
	@FXML
	private TableColumn <Salas,String> colDirec;
	@FXML
	private TableColumn <Salas,String> colCentro;
	@FXML
	private TableColumn <Salas,String> colNsala;
	@FXML
	private TableColumn <Salas,String> colNAsist;
	@FXML
	private TableView <Clientes> tbClientes;
	@FXML
	private TableColumn <Clientes,Integer> colIdCliente;
	@FXML
	private TableColumn <Clientes,String> colNombreClient;
	@FXML
	private TableColumn <Clientes,String> colNif;
	@FXML
	private TableView <Orden> tablaOrdenes;
	@FXML
	private TableColumn <Orden,Integer> colIdOrden;
	@FXML
	private TableColumn <Orden,Integer> collOrdenIdCliente = new TableColumn<Orden, Integer>("clientes");
	@FXML
	private TableColumn <Orden,Integer> collOrdenIdSala = new TableColumn<Orden, Integer>("salas");
	@FXML
	private TableColumn <Orden,Integer> colOrdenIdCiudad = new TableColumn<Orden, Integer>("ciudades");
	@FXML
	private TableColumn <Orden,String>colPrecio;
	@FXML
	private TableColumn <Orden,Date>colFechaEntra;
	@FXML
	private TableColumn  <Orden,Date> colFechaSalida;
	@FXML
	private Label lbSalas;
	@FXML
	private Label lbClientes;
	@FXML
	private Label lbIdClien;
	@FXML
	private Label lbIdSala;
	@FXML
	private Label lbPrecio;
	@FXML
	private Button btnImprimir;
	@FXML
	private Label lbOrdenes;
	@FXML
	private Label lbInfo;
	@FXML
	private TextField tfPrecio;
	@FXML
	private TableView <Ciudades>tbCiudades;
	@FXML
	private TableColumn <Ciudades,Integer> colIdCiudad;
	@FXML
	private TableColumn <Ciudades,String>colNomCiud;
	@FXML
	private TableColumn <Ciudades,String> colCpCiud;
	@FXML
	private TextField tfIdCliente;
	@FXML
	private TextField tfIdSala;
	@FXML
	private Label lbFechaEntr;
	@FXML
	private Label lbFechaSal;
	@FXML
	private DatePicker dtFechaEntr;
	@FXML
	private DatePicker dtFechaSal;
	@FXML
	private Label lbCiudades;
	@FXML
	private TextField tfIdCiudad;
	@FXML
	private Label lbIdCiudad;
	private boolean expresionRe;
	private ObservableList<Ciudades> listaCiudades;
	private ObservableList<Salas> listaSalas;
	private ObservableList<Clientes> listaClientes;
	private ObservableList<Orden> listaOrdenes;
	private ClientesDao clienteDao;
	private SalasDao salaDao;
	private CiudadesDao ciudadDao;
	private OrdenDao ordenDao;
	private Clientes cliente;
	private Salas sala;
	private Ciudades ciudad;
	
	/**
	 * Método para iniciar algunos controles de la interfaz
	 * @param arg0
	 * @param arg1
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	    tfIdCliente.setStyle("-fx-control-inner-background:#ef9a9a");
		tfIdSala.setStyle("-fx-control-inner-background:#ef9a9a");
		tfIdCiudad.setStyle("-fx-control-inner-background:#ef9a9a");
		tfPrecio.setStyle("-fx-control-inner-background:#ef9a9a");
		dtFechaEntr.setStyle("-fx-control-inner-background:#ef9a9a");
		dtFechaSal.setStyle("-fx-control-inner-background:#ef9a9a");
		//Inicializamos las fechas
		dtFechaEntr.setValue(LocalDate.now());
		dtFechaSal.setValue(LocalDate.now());
		//Clientes -- Asignamos las relación de las columnas con los atributos 
		this.colIdCliente.setCellValueFactory(new PropertyValueFactory<Clientes, Integer>("idcliente"));
		this.colNombreClient.setCellValueFactory(new PropertyValueFactory<Clientes, String>("nombreCliente"));
		this.colNif.setCellValueFactory(new PropertyValueFactory<Clientes, String>("nif"));
		//Salas -- Asignamos las relación de las columnas con los atributos 
		this.colIdSala.setCellValueFactory(new PropertyValueFactory<Salas,Integer>("idsala"));
		this.colDirec.setCellValueFactory(new PropertyValueFactory<Salas,String>("direccion"));
		this.colCentro.setCellValueFactory(new PropertyValueFactory<Salas,String>("centro"));
		this.colNsala.setCellValueFactory(new PropertyValueFactory<Salas,String>("nsala"));
		this.colNAsist.setCellValueFactory(new PropertyValueFactory<Salas,String>("nasistentes"));
		//Ciudades -- Asignamos las relación de las columnas con los atributos 
		this.colIdCiudad.setCellValueFactory(new PropertyValueFactory<Ciudades,Integer>("idciudad"));
		this.colNomCiud.setCellValueFactory(new PropertyValueFactory<Ciudades,String>("nombreciudad"));
		this.colCpCiud.setCellValueFactory(new PropertyValueFactory<Ciudades,String>("codigopostal"));
		//Orden -- Asignamos las relación de las columnas con los atributos 
		this.colIdOrden.setCellValueFactory(new PropertyValueFactory<Orden,Integer>("idorden"));
		this.collOrdenIdCliente.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Orden , Integer>, ObservableValue<Integer>>() {
			@Override
			public ObservableValue<Integer> call(CellDataFeatures<Orden, Integer> arg0) {
				 return new SimpleObjectProperty<>(arg0.getValue().getClientes().getIdcliente());
			}
		});
		this.collOrdenIdSala.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Orden , Integer>, ObservableValue<Integer>>() {
			@Override
			public ObservableValue<Integer> call(CellDataFeatures<Orden, Integer> arg0) {
				 return new SimpleObjectProperty<>(arg0.getValue().getSalas().getIdsala());
			}
		});
		this.colOrdenIdCiudad.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Orden , Integer>, ObservableValue<Integer>>() {
			@Override
			public ObservableValue<Integer> call(CellDataFeatures<Orden, Integer> arg0) {
				 return new SimpleObjectProperty<>(arg0.getValue().getCiudades().getIdciudad());
			}
		});
		this.colPrecio.setCellValueFactory(new PropertyValueFactory<Orden,String>("precio"));
		this.colFechaEntra.setCellValueFactory(new PropertyValueFactory<Orden,Date>("fechaentrada"));
		this.colFechaSalida.setCellValueFactory(new PropertyValueFactory<Orden,Date>("fechasalida"));
		//Clientes - Obtenemos la información de la BD y la enviamos a la tabla
		clienteDao = new ClientesDao();
		if(clienteDao.mostrarClientes().equals(null)){
			lbClientes.setText("No hay información en la base de datos");
			lbClientes.setStyle("-fx-control-inner-background:#7a1b0c");
		}
		else {
			listaClientes = FXCollections.observableArrayList(clienteDao.mostrarClientes());
			tbClientes.setItems(listaClientes);
		}
		//Salas - Obtenemos la información de la BD y la enviamos a la tabla
		salaDao = new SalasDao();
		if(salaDao.mostrarSalas().equals(null)) {
			lbSalas.setText("No hay información en la base de datos");
			lbSalas.setStyle("-fx-control-inner-background:#7a1b0c");
		}
		else {
			listaSalas = FXCollections.observableArrayList(salaDao.mostrarSalas());
			tbSalas.setItems(listaSalas);
		}
		//Ciudades Obtenemos la información de la BD y la enviamos a la tabla
		ciudadDao = new CiudadesDao();
		if(ciudadDao.mostrarCiudades().equals(null)) {
				lbCiudades.setText("No hay información en la base de datos");
				lbCiudades.setStyle("-fx-control-inner-background:#7a1b0c");
		}
		else {
			listaCiudades = FXCollections.observableArrayList(ciudadDao.mostrarCiudades());
			tbCiudades.setItems(listaCiudades);
		}
		//Orden Obtenemos la información de la BD y la enviamos a la tabla
		ordenDao = new OrdenDao();
		if(ordenDao.obtenerTodasLasOrdenes().equals(null)) {
			lbInfo.setText("No hay información en la base de datos");	
		}
		else {
			listaOrdenes = FXCollections.observableArrayList(ordenDao.obtenerTodasLasOrdenes());
			tablaOrdenes.setItems(listaOrdenes);
		}
	}
	
	/**
	 * Método para controlar el evento del campo de texto Id cliente
	 * @param event
	 */
	@FXML
	public void keyCliente(KeyEvent event) {
		try {
			// Se obtiene un boolean para comprobar que el ID Cliente tiene los caracteres correctos
			expresionRe = ComprobarIDs(this.tfIdCliente.getText());
			if (tfIdCliente.getText().isEmpty() || expresionRe == false) {
				tfIdCliente.setStyle("-fx-control-inner-background:#ef9a9a");
			} else if (!tfIdCliente.getText().isEmpty() && expresionRe == true) {
				tfIdCliente.setStyle("-fx-control-inner-background:#b8daba");
				lbInfo.setText("Proceda con la Orden");
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método para controlar el evento del campo de texto Id Sala
	 * @param event
	 */
	@FXML
	public void keySala(KeyEvent event) {
		try {
			// Se obtiene un boolean para comprobar que el ID Sala tiene los caracteres correctos
			expresionRe = ComprobarIDs(this.tfIdSala.getText());
			if (tfIdSala.getText().isEmpty() || expresionRe == false) {
				tfIdSala.setStyle("-fx-control-inner-background:#ef9a9a");
			} else if (!tfIdSala.getText().isEmpty() && expresionRe == true) {
				tfIdSala.setStyle("-fx-control-inner-background:#b8daba");
				lbInfo.setText("Proceda con la Orden");
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método para controlar el evento del campo de texto Id Ciudad
	 * @param event
	 */
	@FXML
	public void keyCiudad(KeyEvent event) {
		try {
			// Se obtiene un boolean para comprobar que el ID Ciudad tiene los caracteres correctos
			expresionRe = ComprobarIDs(this.tfIdCiudad.getText());
			if (tfIdCiudad.getText().isEmpty() || expresionRe == false) {
				tfIdCiudad.setStyle("-fx-control-inner-background:#ef9a9a");
			} else if (!tfIdCiudad.getText().isEmpty() && expresionRe == true) {
				tfIdCiudad.setStyle("-fx-control-inner-background:#b8daba");
				lbInfo.setText("Proceda con la Orden");
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método para controlar el evento del campo de texto Precio
	 * @param event
	 */
	@FXML
	public void keyPrecio(KeyEvent event) {
		try {
			// Se obtiene un boolean para comprobar que el Precio tiene los caracteres correctos
			expresionRe = ComprobarPrecio(this.tfPrecio.getText());
			if (tfPrecio.getText().isEmpty() || expresionRe == false) {
				tfPrecio.setStyle("-fx-control-inner-background:#ef9a9a");
			} else if (!tfPrecio.getText().isEmpty() && expresionRe == true) {
				tfPrecio.setStyle("-fx-control-inner-background:#b8daba");
				lbInfo.setText("Proceda con la Orden");
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método para controlar el evento click  para la fecha entrada dataPicker
	 * @param event
	 */
	@FXML
	public void mouseFechaEntrada(MouseEvent event) {
		//Se comprueba que los campos deben de tener valor y una fecha correcta
		boolean revisarFecha = ComprobarFechaActual();
		if(dtFechaEntr.getValue().equals(null)) {
			dtFechaEntr.setStyle("-fx-control-inner-background:#ef9a9a");
			lbInfo.setText("Introcuce una fecha ");
		}else if(revisarFecha){
			lbInfo.setText("Introduce una fecha correcta");
			dtFechaEntr.setStyle("-fx-control-inner-background:#ef9a9a");	
		}else {
			dtFechaEntr.setStyle("-fx-control-inner-background:#b8daba");
			lbInfo.setText("Proceda con la Orden");
		}
	}
	
	/**
	 * Método para controlar el evento de entrada del mouse para la fecha entrada 
	 * @param event
	 */
	@FXML
	public void mouseEnterdedFechaEntrada(MouseEvent event) {
		//Se comprueba que los campos deben de tener valor y una fecha correcta
		boolean revisarFecha = ComprobarFechaActual();
		if(dtFechaEntr.getValue().equals(null)) {
			dtFechaEntr.setStyle("-fx-control-inner-background:#ef9a9a");
			lbInfo.setText("Introcuce una fecha ");
		}else if(revisarFecha){
			lbInfo.setText("Introduce una fecha correcta");
			dtFechaEntr.setStyle("-fx-control-inner-background:#ef9a9a");	
		}else {
			dtFechaEntr.setStyle("-fx-control-inner-background:#b8daba");
			lbInfo.setText("Proceda con la Orden");
		}
	}
	/**
	 * Método para controlar el evento de salida del mouse para la fecha entrada 
	 * @param event
	 */
	@FXML
	public void mouseExiteddFechaEntrada(MouseEvent event) {
		//Se comprueba que los campos deben de tener valor y una fecha correcta
		boolean revisarFecha = ComprobarFechaActual();
		if(dtFechaEntr.getValue().equals(null)) {
			dtFechaEntr.setStyle("-fx-control-inner-background:#ef9a9a");
			lbInfo.setText("Introcuce una fecha ");
		}else if(revisarFecha){
			lbInfo.setText("Introduce una fecha correcta");
			dtFechaEntr.setStyle("-fx-control-inner-background:#ef9a9a");	
		}else {
			dtFechaEntr.setStyle("-fx-control-inner-background:#b8daba");
			lbInfo.setText("Proceda con la Orden");
		}
	}
	
	/**
	 * Método para controlar el evento click para la fecha salida dataPicker
	 * @param event
	 */
	@FXML
	public void mouseFechaSalida(MouseEvent event) {
		//Se comprueba que los campos deben de tener valor y una fecha correcta
		boolean revisarFecha = ComprobarFechas();
		if(dtFechaSal.getValue().equals(null)) {
			dtFechaSal.setStyle("-fx-control-inner-background:#ef9a9a");
		}else if(revisarFecha) {
			lbInfo.setText("Introduce una fecha correcta");
			dtFechaSal.setStyle("-fx-control-inner-background:#ef9a9a");	
		}else {
			dtFechaSal.setStyle("-fx-control-inner-background:#b8daba");
			lbInfo.setText("Proceda con la Orden");
		}	
	}
	
	/**
	 * Método para controlar el evento de entrada del mouse para la fecha salida
	 * @param event
	 */
	@FXML
	public void mouseEnterdedFechaSalida(MouseEvent event) {
		//Se comprueba que los campos deben de tener valor y una fecha correcta
		boolean revisarFecha = ComprobarFechas();
		if(dtFechaSal.getValue().equals(null)) {
			dtFechaSal.setStyle("-fx-control-inner-background:#ef9a9a");
		}else if(revisarFecha) {
			lbInfo.setText("Introduce una fecha correcta");
			dtFechaSal.setStyle("-fx-control-inner-background:#ef9a9a");	
		}else {
			dtFechaSal.setStyle("-fx-control-inner-background:#b8daba");
			lbInfo.setText("Proceda con la Orden");
		}	
	}

	/**
	 * Método para controlar el evento de salida del mouse para la fecha salida
	 * @param event
	 */
	@FXML
	public void mouseExiteddFechaSalida(MouseEvent event) {
		//Se comprueba que los campos deben de tener valor y una fecha correcta
		boolean revisarFecha = ComprobarFechas();
		if(dtFechaSal.getValue().equals(null)) {
			dtFechaSal.setStyle("-fx-control-inner-background:#ef9a9a");
		}else if(revisarFecha) {
			lbInfo.setText("Introduce una fecha correcta");
			dtFechaSal.setStyle("-fx-control-inner-background:#ef9a9a");	
		}else {
			dtFechaSal.setStyle("-fx-control-inner-background:#b8daba");
			lbInfo.setText("Proceda con la Orden");
		}	
	}

	/**
	 * Método para añadir una Orden
	 * @param event
	 */
	@FXML
	public void anadir(ActionEvent event) {
		
		try {
			//Se comprueba que los campos de texto contienen valor
			if (!this.tfIdCliente.getText().isEmpty() && !this.tfIdSala.getText().isEmpty() && !this.tfIdCiudad.getText().isEmpty() && 
				!this.tfPrecio.getText().isEmpty()  && !this.dtFechaEntr.getValue().equals(null) && !this.dtFechaSal.getValue().equals(null)) {

				 String FechaEntrada = this.dtFechaEntr.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				 String FechaSalida = this.dtFechaSal.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				 
				 clienteDao = new ClientesDao();
				 boolean existecliente =  clienteDao.consultaExisteCliente(Integer.parseInt (this.tfIdCliente.getText()));
				 salaDao = new SalasDao();
				 boolean existeSala = salaDao.consultaExisteSala(Integer.parseInt (this.tfIdSala.getText()));
				 ciudadDao = new CiudadesDao();
				 boolean existeCiudad = ciudadDao.consultaExisteCiudad(Integer.parseInt (this.tfIdCiudad.getText()));
					 
				 if(existecliente && existeSala && existeCiudad ) {
					 
					cliente = clienteDao.ObtenerCliente(Integer.parseInt (this.tfIdCliente.getText()));
					sala = salaDao.ObtenerSala(Integer.parseInt (this.tfIdSala.getText()));
					ciudad = ciudadDao.ObtenerCiudad(Integer.parseInt (this.tfIdCiudad.getText()));
					
					Orden ordenNueva = new Orden (ciudad,  cliente, sala ,this.tfPrecio.getText(),FechaEntrada,FechaSalida );
					boolean contiene = false;
					//Se comprueba que no exista una orden repetida a partir de la sala ciudad y fechas 
					for (int i = 0; i < listaOrdenes.size(); i++) {
						if (listaOrdenes.get(i).getCiudades().getIdciudad() == ordenNueva.getCiudades().getIdciudad() &&
							listaOrdenes.get(i).getSalas().getIdsala() == ordenNueva.getSalas().getIdsala() &&
						    comprobarFechaAlquiler(listaOrdenes.get(i).getFechaentrada(),listaOrdenes.get(i).getFechasalida(),ordenNueva.getFechaentrada(),ordenNueva.getFechasalida())){
							contiene = true;
						}
					}
					if (!contiene) {
						// Se obtiene un boolean para comprobar que los campos de texto tiene los caracteres correctos
						boolean clientCorrect = ComprobarIDs(this.tfIdCliente.getText());
						boolean salaCorrect = ComprobarIDs(this.tfIdSala.getText());
						boolean ciudaCorrect = ComprobarIDs(this.tfIdCiudad.getText());
						boolean precioCorrect = ComprobarPrecio(this.tfPrecio.getText());
						boolean fechaEntrCorrect = ComprobarFechaActual();
						boolean fechaSalCorrect = ComprobarFechas();
						//Se comprueba que los campos de texto sean correctos
						if (tfIdCliente.getText().isEmpty() || clientCorrect == false) {
							tfIdCliente.setStyle("-fx-control-inner-background:#ef9a9a");
							lbInfo.setText("Debes completar los campos correctamente");
						} else if (this.tfIdSala.getText().isEmpty() || salaCorrect == false) {
							tfIdSala.setStyle("-fx-control-inner-background:#ef9a9a");
							lbInfo.setText("Debes completar los campos correctamente");
						}else if(this.tfIdCiudad.getText().isEmpty() || ciudaCorrect == false) {
							tfIdCiudad.setStyle("-fx-control-inner-background:#ef9a9a");
							lbInfo.setText("Debes completar los campos correctamente");
						}else if(this.tfPrecio.getText().isEmpty() || precioCorrect == false){
							tfPrecio.setStyle("-fx-control-inner-background:#ef9a9a");
							lbInfo.setText("Debes completar los campos correctamente");	
						}else if(dtFechaEntr.getValue().equals(null) || fechaEntrCorrect == true){
							dtFechaEntr.setStyle("-fx-control-inner-background:#ef9a9a");
							lbInfo.setText("Debes completar los campos correctamente");	
						}else if(dtFechaSal.getValue().equals(null) || fechaSalCorrect == true){
							dtFechaSal.setStyle("-fx-control-inner-background:#ef9a9a");
							lbInfo.setText("Debes completar los campos correctamente");	
						}else if (!this.tfIdCliente.getText().isEmpty() && clientCorrect == true && !tfIdSala.getText().isEmpty() && salaCorrect == true &&
							!this.tfIdCiudad.getText().isEmpty() && ciudaCorrect== true && !tfPrecio.getText().isEmpty() && precioCorrect == true &&
							!dtFechaEntr.getValue().equals(null) && fechaEntrCorrect == false && !dtFechaSal.getValue().equals(null) && fechaSalCorrect == false) {
							tfIdCliente.setStyle("-fx-control-inner-background:#b8daba");
							tfIdSala.setStyle("-fx-control-inner-background:#b8daba");
							tfIdCiudad.setStyle("-fx-control-inner-background:#b8daba");
							tfPrecio.setStyle("-fx-control-inner-background:#b8daba");
							dtFechaEntr.setStyle("-fx-control-inner-background:#b8daba");
							dtFechaSal.setStyle("-fx-control-inner-background:#b8daba");
							//Incluimos una nueva Orden
							
							boolean insertando = ordenDao.insertarOrden(ordenNueva);
							if(insertando) {
								this.listaOrdenes.add(ordenNueva);
								this.tablaOrdenes.setItems(listaOrdenes);
								lbInfo.setText("La Orden fue incluida con éxito");
							}
							else {
								lbInfo.setText("¡Error!. La Orden no fue incluida");
							}
						}
					} else {
						//Ventana emergente de Alerta
						Alert alert = new Alert(Alert.AlertType.WARNING);
						alert.setHeaderText(null);
						alert.setTitle("Alerta");
						alert.setContentText("La Sala que corresponde a la Ciudad ya están alquiladas en las fechas solicitadas");
						alert.showAndWait();
					}
				 }else {
					 lbInfo.setText("Revisa que el Cliente, Sala y la Ciudad existan");
				 }
			} else {
				//Ventana emergente de información
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setTitle("Información");
				alert.setContentText("Introduce la Orden");
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
	 * Metodo para editar a una Orden
	 * @param event
	 */
	@FXML
	public void editar(ActionEvent event) {
		//Obtenemos una orden selecionada de la tabla
		Orden ordene = this.tablaOrdenes.getSelectionModel().getSelectedItem();
		
		if(ordene == null) {
			//Ventana emergente de Alerta
			 Alert alert = new Alert(Alert.AlertType.WARNING);
	         alert.setHeaderText(null);
	         alert.setTitle("Alerta");
	         alert.setContentText("Debes seleccionar una Orden");
	         alert.showAndWait();
		}else {
			try {
				//Creamos una orden con los campos del texto
				 String FechaEntrada = this.dtFechaEntr.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				 String FechaSalida = this.dtFechaSal.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				 
				 clienteDao = new ClientesDao();
				 boolean existecliente =  clienteDao.consultaExisteCliente(Integer.parseInt (this.tfIdCliente.getText()));
				 salaDao = new SalasDao();
				 boolean existeSala = salaDao.consultaExisteSala(Integer.parseInt (this.tfIdSala.getText()));
				 ciudadDao = new CiudadesDao();
				 boolean existeCiudad = ciudadDao.consultaExisteCiudad(Integer.parseInt (this.tfIdCiudad.getText()));
					 
				 if(existecliente && existeSala && existeCiudad ) {
					 
					cliente = clienteDao.ObtenerCliente(Integer.parseInt (this.tfIdCliente.getText()));
					sala = salaDao.ObtenerSala(Integer.parseInt (this.tfIdSala.getText()));
					ciudad = ciudadDao.ObtenerCiudad(Integer.parseInt (this.tfIdCiudad.getText()));
					
					Orden ordenAux = new Orden (ciudad,  cliente, sala ,this.tfPrecio.getText(),FechaEntrada,FechaSalida );
					boolean contiene = false;
					//Se comprueba que no exista una orden repetida a partir de la sala ciudad y fechas 
					for (int i = 0; i < listaOrdenes.size(); i++) {
						if (listaOrdenes.get(i).getCiudades().getIdciudad() == ordenAux.getCiudades().getIdciudad() &&
							listaOrdenes.get(i).getSalas().getIdsala() == ordenAux.getSalas().getIdsala() &&
						    comprobarFechaAlquiler(listaOrdenes.get(i).getFechaentrada(),listaOrdenes.get(i).getFechasalida(),ordenAux.getFechaentrada(),ordenAux.getFechasalida())){
							contiene = true;
						}
					}
					if (!contiene) {
						// Se obtiene un boolean para comprobar que los campos de texto tiene los caracteres correctos
						boolean clientCorrect = ComprobarIDs(this.tfIdCliente.getText());
						boolean salaCorrect = ComprobarIDs(this.tfIdSala.getText());
						boolean ciudaCorrect = ComprobarIDs(this.tfIdCiudad.getText());
						boolean precioCorrect = ComprobarPrecio(this.tfPrecio.getText());
						boolean fechaEntrCorrect = ComprobarFechaActual();
						boolean fechaSalCorrect = ComprobarFechas();
						//Se comprueba que los campos de texto sean correctos
						if (tfIdCliente.getText().isEmpty() || clientCorrect == false) {
							tfIdCliente.setStyle("-fx-control-inner-background:#ef9a9a");
							lbInfo.setText("Debes completar los campos correctamente");
						} else if (this.tfIdSala.getText().isEmpty() || salaCorrect == false) {
							tfIdSala.setStyle("-fx-control-inner-background:#ef9a9a");
							lbInfo.setText("Debes completar los campos correctamente");
						}else if(this.tfIdCiudad.getText().isEmpty() || ciudaCorrect == false) {
							tfIdCiudad.setStyle("-fx-control-inner-background:#ef9a9a");
							lbInfo.setText("Debes completar los campos correctamente");
						}else if(this.tfPrecio.getText().isEmpty() || precioCorrect == false){
							tfPrecio.setStyle("-fx-control-inner-background:#ef9a9a");
							lbInfo.setText("Debes completar los campos correctamente");	
						}else if(dtFechaEntr.getValue().equals(null) || fechaEntrCorrect == true){
							dtFechaEntr.setStyle("-fx-control-inner-background:#ef9a9a");
							lbInfo.setText("Debes completar los campos correctamente");	
						}else if(dtFechaSal.getValue().equals(null) || fechaSalCorrect == true){
							dtFechaSal.setStyle("-fx-control-inner-background:#ef9a9a");
							lbInfo.setText("Debes completar los campos correctamente");	
						}else if (!this.tfIdCliente.getText().isEmpty() && clientCorrect == true && !tfIdSala.getText().isEmpty() && salaCorrect == true &&
							!this.tfIdCiudad.getText().isEmpty() && ciudaCorrect== true && !tfPrecio.getText().isEmpty() && precioCorrect == true &&
							!dtFechaEntr.getValue().equals(null) && fechaEntrCorrect == false && !dtFechaSal.getValue().equals(null) && fechaSalCorrect == false) {
							tfIdCliente.setStyle("-fx-control-inner-background:#b8daba");
							tfIdSala.setStyle("-fx-control-inner-background:#b8daba");
							tfIdCiudad.setStyle("-fx-control-inner-background:#b8daba");
							tfPrecio.setStyle("-fx-control-inner-background:#b8daba");
							dtFechaEntr.setStyle("-fx-control-inner-background:#b8daba");
							dtFechaSal.setStyle("-fx-control-inner-background:#b8daba");
							//Modificamos la orden seleccionada
							ordene.setClientes(ordenAux.getClientes());
							ordene.setSalas(ordenAux.getSalas());
							ordene.setCiudades(ordenAux.getCiudades());
							ordene.setPrecio(ordenAux.getPrecio());
							ordene.setFechaentrada(ordenAux.getFechaentrada());
							ordene.setFechasalida(ordenAux.getFechasalida());
							ordenDao = new OrdenDao();
							boolean editado = ordenDao.modificarOrden(ordene);
							if(editado) {
								lbInfo.setText("La Orden fue modificada con éxito");
								this.tablaOrdenes.refresh();
							}
							else {
								lbInfo.setText("¡Error!. La Orden no fue modificada");
							}
						}
					}
					else {
						//Ventana emergente de Alerta
						Alert alert = new Alert(Alert.AlertType.WARNING);
						alert.setHeaderText(null);
						alert.setTitle("Alerta");
						alert.setContentText("La Sala que corresponde a la Ciudad ya están alquiladas en las fechas solicitadas");
						alert.showAndWait();
					}
			}else {
				lbInfo.setText("Revisa que el Cliente, Sala y Ciudad existan");
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
	 * Método para imprimir toda las lista de las ordenes
	 * @param event
	 */
	@FXML
	public void imprimir(ActionEvent event) {
		Reporte reporte = new Reporte("Ordenes_CW");
		reporte.generarReporte();
	
	}
	
	/**
	 * Método para borrar la orden seleccionada
	 * @param event
	 */
	@FXML
	public void borrar(ActionEvent event) {
		//Se obtiene la sala seleccionada del tabla
		Orden orden = this.tablaOrdenes.getSelectionModel().getSelectedItem();
		//Revisamos si tiene valor
		if (orden == null) {
			//Ventana emergente de Alerta
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setHeaderText(null);
			alert.setTitle("Alerta");
			alert.setContentText("Debes seleccionar una Orden");
			alert.showAndWait();
		} else {
			//Borramos Orden
			ordenDao = new OrdenDao();
			boolean borrada = ordenDao.borrarOrden(orden);
			if (borrada) {
				this.listaOrdenes.remove(orden);
				this.tablaOrdenes.refresh();
				lbInfo.setText("La Orden fue borrada con éxito");
			}
			else {
				lbInfo.setText("¡Error!. La Orden no fue borrada");	
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
	 * Método para resetear los campos de texto y los campos datapicker
	 * @param event
	 */
	@FXML
	public void reset(ActionEvent event) {
		tfIdCliente.setText("");
		tfIdSala.setText("");
		tfIdCiudad.setText("");
		tfPrecio.setText("");
		dtFechaEntr.setValue(LocalDate.now());
		dtFechaSal.setValue(LocalDate.now());
		tfIdCliente.setStyle("-fx-control-inner-background:#ef9a9a");
		tfIdSala.setStyle("-fx-control-inner-background:#ef9a9a");
		tfIdCiudad.setStyle("-fx-control-inner-background:#ef9a9a");
		tfPrecio.setStyle("-fx-control-inner-background:#ef9a9a");
		dtFechaEntr.setStyle("-fx-control-inner-background:#ef9a9a");
		dtFechaSal.setStyle("-fx-control-inner-background:#ef9a9a");
		lbInfo.setText("Proceda con la Orden");
	}
	
	/**
	 * Método que controla el evento de la selección de la tabla
	 * @param event
	 */
	@FXML
	public void selectTablaOrden(MouseEvent event) {
		lbInfo.setText("Proceda con la Orden");
		//Selecciona la sala de la tabla
		Orden orden = this.tablaOrdenes.getSelectionModel().getSelectedItem();
		try {
			//Comprueba los valores de los campos
			if (orden != null) {
				this.tfIdCliente.setText(orden.getClientes().getIdcliente()+"");
				this.tfIdSala.setText(orden.getSalas().getIdsala()+"");
				this.tfIdCiudad.setText(orden.getCiudades().getIdciudad()+"");
				this.tfPrecio.setText(orden.getPrecio());
				LocalDate localDate1 = LocalDate.parse(orden.getFechaentrada(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				this.dtFechaEntr.setValue(localDate1);
				LocalDate localDate2 = LocalDate.parse(orden.getFechasalida(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				this.dtFechaSal.setValue(localDate2);
			}
			// Se obtiene un boolean para comprobar que los campos tiene los caracteres correctos
			boolean ClienCorrect = ComprobarIDs(this.tfIdCliente.getText());
			boolean salaCorrect = ComprobarIDs(this.tfIdSala.getText());
			boolean ciudadCorrect = ComprobarIDs(this.tfIdCiudad.getText());
			boolean precioCorrect = ComprobarPrecio(this.tfPrecio.getText());
			boolean revisarFecha = ComprobarFechas();

			if (tfIdCliente.getText().isEmpty() || ClienCorrect == false) {
				tfIdCliente.setStyle("-fx-control-inner-background:#ef9a9a");
			} else if (!tfIdCliente.getText().isEmpty() && ClienCorrect == true) {
				tfIdCliente.setStyle("-fx-control-inner-background:#b8daba");
				lbInfo.setText("Proceda con la Orden");
			}
			if (tfIdSala.getText().isEmpty() || salaCorrect == false) {
				tfIdSala.setStyle("-fx-control-inner-background:#ef9a9a");
			} else if (!tfIdSala.getText().isEmpty() && salaCorrect == true) {
				tfIdSala.setStyle("-fx-control-inner-background:#b8daba");
				lbInfo.setText("Proceda con la Orden");
			}
			if (tfIdCiudad.getText().isEmpty() || ciudadCorrect == false) {
				tfIdCiudad.setStyle("-fx-control-inner-background:#ef9a9a");
			} else if (!tfIdCiudad.getText().isEmpty() && ciudadCorrect == true) {
				tfIdCiudad.setStyle("-fx-control-inner-background:#b8daba");
				lbInfo.setText("Proceda con la Orden");
			}
			if (tfPrecio.getText().isEmpty() || precioCorrect == false) {
				tfPrecio.setStyle("-fx-control-inner-background:#ef9a9a");
			} else if (!tfPrecio.getText().isEmpty() && precioCorrect == true) {
				tfPrecio.setStyle("-fx-control-inner-background:#b8daba");
				lbInfo.setText("Proceda con la Orden");
			}
			if(dtFechaEntr.getValue().equals(null)) {
				dtFechaEntr.setStyle("-fx-control-inner-background:#ef9a9a");
			}else if (!dtFechaEntr.getValue().equals(null)){
				dtFechaEntr.setStyle("-fx-control-inner-background:#b8daba");
				lbInfo.setText("Proceda con la Orden");
			}	
			if(dtFechaSal.getValue().equals(null) || revisarFecha) {
				dtFechaSal.setStyle("-fx-control-inner-background:#ef9a9a");
			}else if(!dtFechaSal.getValue().equals(null) && !revisarFecha) {
				dtFechaSal.setStyle("-fx-control-inner-background:#b8daba");
				lbInfo.setText("Proceda con la Orden");
			}	
			
		} catch (NullPointerException n) {
			n.printStackTrace();
			System.out.println("!! Error -> "+n.getMessage());
		} 
	}

	/**
	 * Método para controlar los caracteres del campo Id
	 * @param log
	 * @return
	 */
	public boolean ComprobarIDs(String log) {
		//Solo debe contenner números
		Pattern pat = Pattern.compile("^[0-9]{1,4}$");
		Matcher mat = pat.matcher(log);
		if (mat.find()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Método para controlar los caracteres del campo Precio
	 * @param log
	 * @return
	 */
	public boolean ComprobarPrecio(String log) {
		//Solo debe contenner números y un punto
		Pattern pat = Pattern.compile("^[0-9]{1,8}+([.][0-9]{1,2}+)?$");
		Matcher mat = pat.matcher(log);
		if (mat.find()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Método para controlar que la fecha entrada no puede ser menor que la fecha salida
	 * @return
	 */
	public boolean ComprobarFechas() {
		//Obtiene las fechas para calcular la diferencia de días
		LocalDate fechaEntra = this.dtFechaEntr.getValue();
		LocalDate fechaSali = this.dtFechaSal.getValue();
		long dias = DAYS.between(fechaEntra , fechaSali);
		 if(dias < 0) {
			 return true;
		 }
		 else {
			 return false;
		 }	
	}
	
	/**
	 * Método para controlar que la fecha de hoy no sea menor que la fecha entrada
	 * @return
	 */
	public boolean ComprobarFechaActual() {
		//Obtiene las fechas para calcular la diferencia de días
		LocalDate fechaEntra = this.dtFechaEntr.getValue();
		LocalDate fechaHoy = LocalDate.now();
		long dias = DAYS.between(fechaHoy, fechaEntra );
		 if(dias < 0) {
			 return true;
		 }
		 else {
			 return false;
		 }	
	}
	
	/**
	 * Método para comprobar si la fechas pasadas por parametro se solapan
	 * @param fEntrada1
	 * @param fSalida1
	 * @param fEntrada2
	 * @param fSalida2
	 * @return
	 */
	public boolean comprobarFechaAlquiler(String fEntrada1 , String fSalida1,String fEntrada2 , String fSalida2) {
		
		org.joda.time.format.DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
		DateTime fechaInicio1 = formatter.parseDateTime(fEntrada1);
		DateTime fechaFin1 = formatter.parseDateTime(fSalida1);
	
		DateTime fechaInicio2 = formatter.parseDateTime(fEntrada2);
		DateTime fechaFin2 = formatter.parseDateTime(fSalida2);

		Interval intervalo1 = new Interval( fechaInicio1.minusDays(1), fechaFin1 );
		Interval intervalo2 = new Interval( fechaInicio2.minusDays(1), fechaFin2 );
		return intervalo1.overlaps( intervalo2 );
	}
}
