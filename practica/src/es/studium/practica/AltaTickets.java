package es.studium.practica;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
/**
 * Esta es la clase donde damos altas a los tickets, es un frame que
 * rellenamos con diferentes datos para poder crear un ticket.
 * Para la creación de los susodichos se confirma con ConfirmacionAltaTickets.
 * @author DoctorSalt/ José Antonio Muñoz Periáñez 
 *
 */
public class AltaTickets extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtcantidad;
	/**
	 * Es la clase de confirmacion es el dialog de confirmacion
	 */
	ConfirmacionAltaTickets confirmacionAltaTickets =new ConfirmacionAltaTickets();
	/**
	 * Este es el textfield donde acabará el total de la factua,
	 * este se autocalcula y no es modificable.
	 */
	private JTextField txttotal;
	JComboBox <String> articulosCombo =  new JComboBox <String>();
	private JTable table;
	String cabecera[]= {"IdArticulo","Descripcion","Precio U","Cantidad","Subtotal"};
	public AltaTickets() {
		setTitle("Alta Tickets");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				setVisible(false);
			}
			@Override
			public void windowActivated(WindowEvent arg0) {
				if(articulosCombo.getItemCount()==0) {
					MeterDatos();
				}else {
					articulosCombo.removeAll();
					MeterDatos();
				}

			}
		});
		setBounds(100, 100, 482, 298);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JLabel lblArticulo = new JLabel("Articulo:");
		lblArticulo.setBounds(12, 48, 56, 16);
		contentPane.add(lblArticulo);
		articulosCombo.setBounds(80, 45, 169, 22);
		contentPane.add(articulosCombo);
		txtcantidad = new JTextField();
		txtcantidad.setBounds(261, 45, 37, 22);
		contentPane.add(txtcantidad);
		txtcantidad.setColumns(10);
		table = new JTable();
		table.setEnabled(false);
		final DefaultTableModel modeloTabla = new DefaultTableModel();	
		table.setModel(modeloTabla);
		modeloTabla.setColumnIdentifiers(cabecera);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 88, 256, 104);
		contentPane.add(scrollPane);
		scrollPane.setViewportView(table);
		JButton btnAadir = new JButton("A\u00F1adir");
		btnAadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				if(articulosCombo.getSelectedIndex()==0) {
					JOptionPane.showMessageDialog (null, "Elija un registro valido, por favor.", "OK", JOptionPane.INFORMATION_MESSAGE);				
				}else {
					String sacadaSeleccion = articulosCombo.getSelectedItem().toString();
					int seleccion=SplitSeleccionado1(sacadaSeleccion);
					String descripcionArt =SplitSeleccionado2(sacadaSeleccion);
					double precio = SplitSeleccionado3(sacadaSeleccion);
					int cantidad=0;
					try {
						cantidad = Integer.parseInt(txtcantidad.getText());
					}
					catch(Exception exc) {
						JOptionPane.showMessageDialog (null, "Elija un numero valido, por favor.", "OK", JOptionPane.INFORMATION_MESSAGE);				
						exc.getMessage();
					}
					if(cantidad==0) {
						
					}
					else {
					meterInformacion(seleccion, descripcionArt, precio, cantidad,modeloTabla);
					Calcular(modeloTabla);
					}
				}
			}
		});
		btnAadir.setBounds(310, 44, 97, 25);
		contentPane.add(btnAadir);
		JButton btnBorrarTodos = new JButton("Borrar todos");
		btnBorrarTodos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int numeroFilasActuales = modeloTabla.getRowCount();
				if(numeroFilasActuales==0) {
					JOptionPane.showMessageDialog (null, "Añada algun articulo, por favor.", "OK", JOptionPane.INFORMATION_MESSAGE);				
				}
				else {
					for(int i=0; i<numeroFilasActuales; i++) {
						modeloTabla.removeRow(0);
					}
				}
			}
		});
		btnBorrarTodos.setBounds(186, 215, 112, 25);
		contentPane.add(btnBorrarTodos);		
		JButton btnBorrarSeleccionado = new JButton("Borrar \u00DAltimo");
		btnBorrarSeleccionado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(modeloTabla.getRowCount()==0) {
					
				}else {
				modeloTabla.removeRow(modeloTabla.getRowCount()-1);
				}
			}
		});
		btnBorrarSeleccionado.setBounds(290, 117, 162, 25);
		contentPane.add(btnBorrarSeleccionado);		
		JButton btnConfirmarTicket = new JButton("Confirmar Ticket");
		btnConfirmarTicket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int numeroTicket = 1;
				numeroTicket=recogerTicket();
				String totalFormateado=cambiarComas(txttotal.getText().toString());
				double totalResultante=Double.parseDouble(totalFormateado);
				ArrayList<String> listaSentenciaTicketsArticulo = new ArrayList<String>();				
				for(int i=0; i<modeloTabla.getRowCount(); i++) {
					int idArticuloCorrespondiente=Integer.parseInt(modeloTabla.getValueAt(i,0).toString());
					int cantidadCorrespondiente =Integer.parseInt(modeloTabla.getValueAt(i,3).toString());
					String sentenciaTicketArticulo = "insert into tiendecita.ticketsarticulos VALUES(null,"+numeroTicket+","+idArticuloCorrespondiente+","+cantidadCorrespondiente+");";
					listaSentenciaTicketsArticulo.add(sentenciaTicketArticulo);
				}		
				String sentenciaTicket="insert into tiendecita.tickets VALUES(null,'"+fechaActual()+"','"+totalResultante+"');";
				confirmacionAltaTickets.setSentencias(sentenciaTicket,listaSentenciaTicketsArticulo);
				confirmacionAltaTickets.setVisible(true);
			}			
		});
		btnConfirmarTicket.setBounds(12, 215, 127, 25);
		contentPane.add(btnConfirmarTicket);		
		txttotal = new JTextField();
		txttotal.setEnabled(false);
		txttotal.setEditable(false);
		txttotal.setColumns(10);
		txttotal.setBounds(367, 180, 85, 22);
		contentPane.add(txttotal);		
		JLabel lblTotal = new JLabel("Total:");
		lblTotal.setBounds(310, 183, 56, 16);
		contentPane.add(lblTotal);	
	}
	/**
	 * Rellena el articuloCombo con todos los articulos
	 */
	void MeterDatos(){
		articulosCombo.setModel(new DefaultComboBoxModel<String>(new String[] {"Seleccione un Art\u00EDculo"}));		
		String login = "root";
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/tiendecita?autoReconnect=true&useSSL=false";
		String password = "Patata01";
		String sentencia="Select * from tiendecita.articulos";
		Connection connection = null;
		java.sql.Statement statement = null;
		ResultSet rs = null;		
		int datosChoice;
		String nombreChoice;
		double precioChoice;
		try
		{
			connection=Conectar(driver,connection,url,login,password);
			statement = connection.createStatement();
			rs = statement.executeQuery(sentencia);
			while (rs.next())
			{
				datosChoice = rs.getInt("idArticulo");
				nombreChoice = rs.getString("descArticulo");
				precioChoice = rs.getDouble("precioArticulo");
				articulosCombo.addItem(datosChoice+" - "+nombreChoice+" - "+precioChoice);				
			}
		}
		catch (SQLException sqle)
		{
			System.out.println("Error 2: "+sqle.getMessage());
		}

		finally
		{
			Desconectar(connection);
		}			
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
	private Connection Conectar(String driver, Connection connection, String url, String login, String password) {
		try {
			//Cargar los controladores para el acceso a la BD
			Class.forName(driver);
			//Establecer la conexión con la BD Empresa
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
	/**
	 * Recoge el tercer dato con formato (x-y-a) y lo devuelve
	 * @param cosaSeleccionada el propio dato con formato (x-y-a)
	 * @return el tercer dato devuelto
	 */
	private int SplitSeleccionado1(String elegido) {
		String[] cosasElegidas = elegido.split(" - ");
		int numeroElegido = Integer.parseInt(cosasElegidas[0]);
		return numeroElegido;
	}
	/**
	 * Recoge el segundo dato con formato (x-y-a) y lo devuelve
	 * @param cosaSeleccionada el propio dato con formato (x-y-a)
	 * @return el segundo dato devuelto
	 */
	private String SplitSeleccionado2(String elegido) {
		String[] cosasElegidas = elegido.split(" - ");
		String cosaElegido = cosasElegidas[1];
		return cosaElegido;
	}
	/**
	 * Recoge el primer dato con formato (x-y-a) y lo devuelve
	 * @param cosaSeleccionada el propio dato con formato (x-y-a)
	 * @return el primer dato devuelto
	 */
	private double SplitSeleccionado3(String elegido) {
		String[] cosasElegidas = elegido.split(" - ");
		double numeroElegido = Double.parseDouble(cosasElegidas[2]);
		return numeroElegido;
	}
	/**
	 * Introduce en la tabla todos los datos necesarios
	 * @param seleccion Es un int con el id del articulo
	 * @param descripcion Es la descripcion del articulo
	 * @param precio es el precio del articulo
	 * @param cantidad es la cantidad del articulo
	 * @param modeloTabla es el modelo de la tabla
	 */
	private void meterInformacion(int seleccion, String descripcion , double precio,int cantidad, DefaultTableModel modeloTabla) {
		DecimalFormat df = new DecimalFormat("#.##");
		df.setMaximumFractionDigits(2);
		double subtotal=precio*cantidad;		
		String datos[]= {seleccion+"",descripcion,df.format(precio)+"",cantidad+"",subtotal+""};
		modeloTabla.addRow(datos);
	}
	/**
	 * En este metodo se calcula los totales.
	 * @param modeloTabla
	 */
	private void Calcular(DefaultTableModel modeloTabla) {
		int candtidadColumnas=modeloTabla.getRowCount();
		double total=0;
		for(int i=0; i<candtidadColumnas; i++) { 
			total=Double.parseDouble((String) modeloTabla.getValueAt(i,4))+total;
		}
		DecimalFormat df = new DecimalFormat("#.##");
		df.setMaximumFractionDigits(2);
		txttotal.setText(df.format(total)+"");
	}
	/**
	 * Recoge la fecha del sistema
	 * @return devuelve un string con la fecha actual formato americano
	 */
	private String fechaActual() {
		Calendar c2 = new GregorianCalendar();
		String fecha = Integer.toString(c2.get(Calendar.YEAR))+"-"+Integer.toString(c2.get(Calendar.MONTH))+"-"+Integer.toString(c2.get(Calendar.DATE));
		return fecha;
	}
	private int recogerTicket() {
		String login = "root";
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/tiendecita?autoReconnect=true&useSSL=false";
		String password = "Patata01";
		String sentencia="SELECT idTicket FROM tiendecita.tickets;";
		Connection connection = null;
		java.sql.Statement statement = null;
		ResultSet rs = null;
		int dichosoTicket=0;
		try
		{
			connection=Conectar(driver,connection,url,login,password);
			statement = connection.createStatement();
			rs = statement.executeQuery(sentencia);
			rs.last();
			dichosoTicket=rs.getInt("idTicket");
		}
		catch (SQLException sqle)
		{
			System.out.println("Error 2: "+sqle.getMessage());
		}
		finally
		{
			Desconectar(connection);
		}
		if(dichosoTicket==0) {
			
		}
		else {
			dichosoTicket++;
		}
		return dichosoTicket;
	}
	/**
	 * Cambia la coma por un punto
	 * @param numeroComas
	 * @return
	 */
	private String cambiarComas(String numeroComas) {
		String[] numeroCompleto = numeroComas.split(",");
		String numeroPunto = numeroCompleto[0]+"."+numeroCompleto[1];
		return numeroPunto;
	}	
}
