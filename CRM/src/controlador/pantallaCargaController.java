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
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;

import javafx.scene.control.ProgressBar;

import javafx.scene.control.Label;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class pantallaCargaController implements Initializable {
	@FXML
	private ProgressBar idProgressBar;
	@FXML
	private Button idBotonIniciar;
	@FXML
	private Label idTxtInicio;
	@FXML
	private Label idTxtCarga;
	@FXML
	private ImageView idImgInit;
	
	ScheduledExecutorService executor = null;
	
	@FXML
	public void cargar(ActionEvent event) {
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//idTxtCarga.textProperty().bind(timeSeconds.divide(100).asString());
		
	      cargarPantallaLogin();  
	}
	
	private  void delaySegundo(){
        try{
            Thread.sleep(6000);
        }catch(InterruptedException e){
        	System.out.println(e.getMessage());
        }
    }
	
	public void cargarPantallaLogin() {
		
		try {
		
			// Cargo la vista
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/vistaLogin.fxml"));

	        // Cargo la ventana
	        Parent root = loader.load();
			 // Creo el Scene
	        Scene scene = new Scene(root);
	        Stage stage = new Stage();
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.setScene(scene);
	        stage.show(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public TimerTask tiempoEjecucion() {

        TimerTask timerTask;
        timerTask = new TimerTask() {
            int count = 1;
            @Override
            public void run() {
            	System.out.println(count );
                if (count >= 6) {
                    executor.shutdown();
                }
                count++;
            }
        };
        return timerTask;
    }
	
    public void iniciarTiempo() {
        executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(tiempoEjecucion(), 1 /*Retardo inicial*/, 1, TimeUnit.SECONDS); //Cada 1 segundo
    }
   
}
