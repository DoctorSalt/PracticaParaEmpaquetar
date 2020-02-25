package es.studium.practica;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import javax.swing.JTable;

/**
 * Este es el frame que devuelve una tabla con los datos de cada articulo y
 * la fecha y el coste total del ticket
 * @author DoctorSalt/ José Antonio Muñoz Periáñez 
 */

public class ConsultaTickets2 extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtTotal;
	private JTextField txtfecha;
	/**
	 * Propiedad que refleja el idTicket
	 */
	String idTicketMandado;
	/**
	 * Propiedad que refleja la fecha del Ticket
	 */
	String fechaTicketMandado;
	/**
	 * Propiedad que refleja el total del ticket
	 */
	String totalTickeMandado;
	/**
	 * Tabla que se mostrará en pantalla con todos los articulos
	 */
	private JTable table;
	String cabecera[]= {"IdArticulo","Descripcion","Precio U","Cantidad","Subtotal"};
	public ConsultaTickets2() {		
		setTitle("ConsultaTicket");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 510, 288);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		txtTotal = new JTextField();
		txtTotal.setEnabled(false);
		txtTotal.setEditable(false);
		txtTotal.setColumns(10);
		txtTotal.setBounds(387, 118, 85, 22);
		contentPane.add(txtTotal);		
		JLabel lblTotal = new JLabel("Total:");
		lblTotal.setBounds(345, 121, 56, 16);
		contentPane.add(lblTotal);
		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 59, 306, 132);
		contentPane.add(scrollPane);
		JLabel lblFecha = new JLabel("Fecha:");
		lblFecha.setBounds(23, 30, 56, 16);
		contentPane.add(lblFecha);
		txtfecha = new JTextField();
		txtfecha.setEnabled(false);
		txtfecha.setEditable(false);
		txtfecha.setColumns(10);
		txtfecha.setBounds(91, 27, 127, 22);
		contentPane.add(txtfecha);
		/**
		 * Esta parte se ejecutará al visualizarse y estar en primer plano
		 */
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent arg0) {
				meterDatosTicket();
				meterDatosTicketArticulo(scrollPane);
			}		
		});
	}
	/**
	 * Este metodo crea y rellena la tabla con lo necesario
	 * @param scrollPane
	 */
	private void meterDatosTicketArticulo(JScrollPane scrollPane) {
		table = new JTable();
		scrollPane.setViewportView(table);
		DefaultTableModel modeloTabla = new DefaultTableModel();	
		table.setModel(modeloTabla);
		modeloTabla.setColumnIdentifiers(cabecera);
		MeteEsasFilas(modeloTabla);
	}			
	/**
	 * Este metodo rellena la tabla con todo lo necesario, utilizado todos los datos que se hayan mandado
	 * @param modeloTabla es el modelo tabla donde se harán los cambios
	 */
	private void MeteEsasFilas(DefaultTableModel modeloTabla) {
		String login = "root";
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/tiendecita?autoReconnect=true&useSSL=false";
		String password = "Patata01";
		String sentencia="select idArticuloFK, descArticulo, precioArticulo, cantidadTA  from articulos, ticketsarticuloS where idTicketFK="+getidTicketMandado()+" and idArticulo=idArticuloFK;";
		Connection connection = null;
		java.sql.Statement statement = null;
		ResultSet rs = null;		
		int idArticuloRecibido;
		String descArticuloRecibido;
		double precioArticuloRecibido;
		int cantidad;
		DecimalFormat df = new DecimalFormat("#.##");
		df.setMaximumFractionDigits(2);
		try {
			connection=Conectar(driver,connection,url,login,password);
			statement = connection.createStatement();
			rs = statement.executeQuery(sentencia);
			while (rs.next())
			{
				idArticuloRecibido = rs.getInt("idArticuloFK");
				descArticuloRecibido = rs.getString("descArticulo");
				precioArticuloRecibido = rs.getDouble("precioArticulo");
				cantidad=rs.getInt("cantidadTA");
				String[]fila= {idArticuloRecibido+"", descArticuloRecibido, df.format(precioArticuloRecibido), cantidad+"", df.format(cantidad*precioArticuloRecibido)};
				modeloTabla.addRow(fila);
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		Desconectar(connection);
	}
	private Connection Conectar(String driver, Connection connection, String url, String login, String password) {
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, login, password);	
		}catch(ClassNotFoundException cnfe) {
			System.out.println("Error 1: "+cnfe.getMessage());
		}
		catch (SQLException sqle)
		{
			System.out.println("Error 2: "+sqle.getMessage());
		}	
		return connection;
	}
	private void Desconectar(Connection connection) {
		try
		{
			if(connection!=null)
			{
				connection.close();
			}
		}
		catch (SQLException e)
		{
			System.out.println("Error 3: "+e.getMessage());
		}	
	}
	
	void setIdTicketMandado(String idRecibido){
		idTicketMandado=idRecibido;
	}
	String getidTicketMandado() {
		return idTicketMandado;
	}
	void setFechaTicketMandado(String fechaRecibida) {
		fechaTicketMandado=fechaRecibida;
	}
	String getFechaTicketMandado() {
		return fechaTicketMandado;
	}
	void setTotalTickeMandado(String totalRecibido) {
		totalTickeMandado=totalRecibido;
	}
	String getTotalTicketMandado() {
		return totalTickeMandado;
	}
	/**
	 * Este metodo da a la clase todos los detalles que les son enviados de la otra
	 * @param idTicketEnviar idTicket
	 * @param fechaEnviar Fecha enviada
	 * @param totalEnviar cantidad total
	 */
	public void seteandoloTodo(String idTicketEnviar, String fechaEnviar, String totalEnviar) {
		setIdTicketMandado(idTicketEnviar);
		setFechaTicketMandado(cambiarFormatoEuropeo(fechaEnviar));
		setTotalTickeMandado(totalEnviar);
	}
	/**
	 * Cambia el formato de la fecha a formato europeo
	 * @param fechaEnviar Fecha enviada
	 * @return fecha modificada
	 */
	private String cambiarFormatoEuropeo(String fechaEnviar) {
		String[] fechas = fechaEnviar.split("-");
		String cosaElegido = fechas[2]+"/"+fechas[1]+"/"+fechas[0];
		return cosaElegido;
	}
	/**
	 * Introduce en el frame los datos que se muestran
	 * la fecha y el total.
	 */
	private void meterDatosTicket() {
		txtfecha.setText(getFechaTicketMandado());
		txtTotal.setText(getTotalTicketMandado());
	}
}
