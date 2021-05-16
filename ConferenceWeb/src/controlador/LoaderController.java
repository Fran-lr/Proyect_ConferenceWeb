package controlador;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.image.ImageView;

public class LoaderController implements Initializable {
	@FXML
	private Label lbCargando;
	@FXML
	private ImageView imgCohete;
	@FXML
	private AnchorPane cuerpoCarga;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		FadeTransition ft = new FadeTransition();
		ft.setNode(imgCohete);
		ft.setDuration(new Duration(600));
		ft.setFromValue(1.0);
		ft.setToValue(0.1);
		ft.setCycleCount(8);
		ft.setAutoReverse(true);
		ft.play();
		new preloader().start();
	}

	public class preloader extends Thread {
		@Override
		public void run() {
			try {
				Thread.sleep(4000);
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						Parent root = null;
						try {
							root = FXMLLoader.load(getClass().getResource("/vista/VistaLogin.fxml"));
							Scene scene = new Scene(root);
							Stage stage = new Stage();
							stage.setResizable(false);
							stage.setTitle(" -  Panel de acceso  - ");
							stage.setScene(scene);
							stage.show();
							cuerpoCarga.getScene().getWindow().hide();
						} catch (NullPointerException nu) {

							System.out.println("!! Error controlado-> " + nu.getMessage());
						} catch (Exception e) {
							e.printStackTrace();
							System.out.println("!! Error -> " + e.getMessage());
						}
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("!! Error -> " + e.getMessage());
			}
		}

	}

}
