package reportes;

import java.awt.Dimension;
import java.io.InputStream;
import java.sql.Connection;
import javax.swing.JFrame;
import modelo.ConexionBD;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.swing.JRViewer;

/**
 * Clase para responder crear un documento con jasperReport
 * @author FranJ
 * @version 1.0
 */
public class Reporte {
	private ConexionBD conBD;
	private Connection connection;
	private String reporte;
	
	/**
	 * Contructor con parametro
	 * @param report
	 */
	public Reporte(String report) {
		this.reporte=report;
	}
	
	/**
	 * Método para realizar una consulta a la base de datos
	 * Obtenemos información de la consulta y la incluimos en un documento
	 */
	public void generarReporte() {
		conBD = new ConexionBD();
		connection = conBD.conseguirConexion();
		
		try {
			//Ruta del fichero .jrxml
			InputStream dir = getClass().getResourceAsStream("/reportes/"+this.reporte+".jrxml");
			JasperReport jr = JasperCompileManager.compileReport(dir);
			JasperPrint jp = JasperFillManager.fillReport(jr,null, connection);	
			//Creamos una ventana para mostrar el docuemento
			JRViewer test = new JRViewer(jp);
			JFrame frame = new JFrame("Ordenes Actuales");
			frame.getContentPane().add(test);
			frame.setResizable(true);
			frame.setMinimumSize(new Dimension(1350, 1000));
			frame.getExtendedState();
			frame.pack();
			frame.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("!! Error -> "+e.getMessage());
		}
		//Cerramos conexión 
		conBD.cerrarConexion();
	}
}
