package es.studium.practica;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/**
 * Esta es la clase donde damos bajas a los articulos, es un frame donde seleccionamos 
 * el articulo en un JComboBox para poder seleccionar el articulo a dar de baja.
 * @author DoctorSalt/ José Antonio Muñoz Periáñez 
 */
public class BajaArticulos extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JComboBox<Object> comboBox = new JComboBox<Object>();
	/**
	 * Es la clase de confirmacion es el dialog de confirmacion
	 */
	ConfirmacionBajaArticulos confirmacionBajaArticulos = new ConfirmacionBajaArticulos();
	public BajaArticulos() {
		setTitle("Baja Articulos");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				setVisible(false);
			}
			@Override
			public void windowActivated(WindowEvent arg0) {
				if(comboBox.getItemCount()==0) {
					MeterDatos(comboBox);
				}else {
					comboBox.removeAll();
					MeterDatos(comboBox);
				}				
			}
		});
		setBounds(100, 100, 365, 285);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JLabel lblNewLabel = new JLabel("Articulo:");
		lblNewLabel.setBounds(32, 43, 56, 16);
		contentPane.add(lblNewLabel);		
		comboBox.setBounds(125, 40, 169, 22);
		contentPane.add(comboBox);
		JButton btnNewButton = new JButton("Dar de Baja");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getSelectedItem().equals("Seleccione un Art\\u00EDculo")) {
					JOptionPane.showMessageDialog (null, "Elija un registro valido, por favor.", "OK", JOptionPane.INFORMATION_MESSAGE);				
				}
				else {
					String seleccionado = SplitElegido(comboBox.getSelectedItem().toString());
					String sentencia = "DELETE from tiendecita.articulos where idArticulo = "+seleccionado+";";
					confirmacionBajaArticulos.setFrase(sentencia);
					confirmacionBajaArticulos.setVisible(true);
				}
			}			
		});
		btnNewButton.setBounds(86, 162, 150, 25);
		contentPane.add(btnNewButton);
	}
	/**
	 * Este es el metodo donde se añaden los articulos en el comboBox mediante una conexion a la base
	 * de datos.
	 * @param comboBox Este es el comboBox que recibe los datos
	 */
	private void MeterDatos(JComboBox<Object> comboBox) {
		comboBox.setModel(new DefaultComboBoxModel<Object>(new String[] {"Seleccione un Art\u00EDculo"}));		
		String login = ConectameEsta.getUsuarioBD();
		String driver = "com.mysql.jdbc.Driver";
		String url = ConectameEsta.getConexionBD();
		String password = ConectameEsta.getPasswordDB();
		String sentencia="Select * from tiendecita.articulos";
		Connection connection = null;
		java.sql.Statement statement = null;
		ResultSet rs = null;		
		int datosChoice;
		String nombreChoice;
		try
		{
			connection=Conectar(driver,connection,url,login,password);
			statement = connection.createStatement();
			rs = statement.executeQuery(sentencia);
			while (rs.next())
			{
				datosChoice = rs.getInt("idArticulo");
				nombreChoice = rs.getString("descArticulo");
				comboBox.addItem(datosChoice+" - "+nombreChoice);				
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
	/**
	 * Este es un metodo para realizar una conexion
	 * @param driver
	 * @param connection
	 * @param url
	 * @param login
	 * @param password
	 * @return devuelve una variable Connection conctar ya inicializada
	 */
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
	/**
	 * Recoge de datos con formato x-y-z el primer dato
	 * @param elegido
	 * @return Este dato es el id que separamos
	 */
	private String SplitElegido(String elegido) {
		String[] cosasElegidas = elegido.split(" - ");
		String numeroElegido = cosasElegidas[0];
		return numeroElegido;
	}
}
