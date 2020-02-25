package es.studium.practica;
import java.awt.Desktop;
import java.io.File;
import java.sql.DriverManager;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Esta clase se dedica a crear informes en formato pdf con jasperreport
 * @author DoctorSalt/ José Antonio Muñoz Periáñez
 */
public class GuardaPDF {
	/**
	 * Este metodo ejecuta JasperReport donde compila el archivo reportPrincipal.jrxml
	 * Despues de conectarse, se genera el jasper y el pdf.
	 */
	public static void todosArticulos() {
		try
		{
			JasperCompileManager.compileReportToFile("reportPrincipal.jrxml");
			HashMap<String,Object> parametros = new HashMap<String,Object>();
			JasperReport report = (JasperReport) JRLoader.loadObjectFromFile("reportPrincipal.jasper");
			Class.forName("com.mysql.jdbc.Driver");
			String servidor = "jdbc:mysql://localhost:3306/tiendecita?useSSL=false";
			String usuarioDB = "root";
			String passwordDB = "Studium2019;";
			java.sql.Connection conexion = DriverManager.getConnection(servidor, usuarioDB, passwordDB);
			JasperPrint print = JasperFillManager.fillReport(report, parametros, conexion);
			JasperExportManager.exportReportToPdfFile(print, "EjemploInforme.pdf");
			File path = new File ("EjemploInforme.pdf");
			Desktop.getDesktop().open(path);
		}
		catch (Exception e)
		{			
			JFrame error=new JFrame();
			JPanel panel = new JPanel();			
			error.setTitle("Error");
			JTextField cuadro = new JTextField(e+"");
			cuadro.setEnabled(true);
			cuadro.setEditable(true);
			panel.add(cuadro);
			error.setVisible(true);
			error.setContentPane(panel);
			System.out.println("Error: " + e.toString());
		}
	}
	
	/**
	 * Este metodo recibe una fecha y la transforma 
	 * para que la base de datos la entienda
	 * @param x fecha con un formato europeo dividido por /
	 * @return fecha resultante que devuelve este metodo
	 */
	public static String americanizarFecha(String x) {
		String[] fechaRecibida = x.split("/");
		String fechaAmericana = fechaRecibida[2]+"-"+fechaRecibida[1]+"-"+fechaRecibida[0];
		return fechaAmericana;
	}
	/**
	 * Este metodo recibe el rango de dos fechas como string y
	 * pasa estas dos con una transformacion como parametros a la busqueda
	 * en jasperrepor. Los parametros se llaman fechaDesde, fechaHasta,
	 * fechaDesde1(la fecha con un formato tradicional)
	 * fechaHasta1(la fecha con un formato tradicional)
	 * @param fechaDesde fecha en la que empieza el rango de busqueda
	 * @param fechaHasta fecha en la que finaliza el rango de busqueda
	 */
	public static void ejecutadorTickets(String fechaDesde, String fechaHasta) {
		String fechaDesdeR=americanizarFecha(fechaDesde);
		String fechaHastaR=americanizarFecha(fechaHasta);
		try
		{
			JasperCompileManager.compileReportToFile("reportSecundario.jrxml");
			HashMap<String,Object> parametros = new HashMap<String,Object>();
			parametros.put("fechaDesde", fechaDesdeR);
			parametros.put("fechaHasta", fechaHastaR);
			parametros.put("fechaDesde1", fechaDesde);
			parametros.put("fechaHasta1", fechaHasta);
			JasperReport report = (JasperReport) JRLoader.loadObjectFromFile("reportSecundario.jasper");
			Class.forName("com.mysql.jdbc.Driver");
			String servidor = "jdbc:mysql://localhost:3306/tiendecita?useSSL=false";
			String usuarioDB = "root";
			String passwordDB = "Patata01";
			java.sql.Connection conexion = DriverManager.getConnection(servidor, usuarioDB, passwordDB);
			JasperPrint print = JasperFillManager.fillReport(report, parametros, conexion);
			JasperExportManager.exportReportToPdfFile(print, "reportSecundario.pdf");
			File path = new File ("reportSecundario.pdf");
			Desktop.getDesktop().open(path);
		}
		catch (Exception e)
		{
			System.out.println("Error: " + e.toString());
		}	
	}
}