package es.studium.practica;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
/**
 * Esta es la clase donde se realizan modificaciones a los articulos, 
 * aqui se selecciona el articulo que se modificará. En
 * ModificaArticulos2 se modifican los registros.
 * @author DoctorSalt/ José Antonio Muñoz Periáñez 
 */
public class ModificaArticulos extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	ModificarArticulos2 modificarArticulos2 =new ModificarArticulos2();
	public ModificaArticulos() {
		setTitle("Modifica Articulos");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				setVisible(false);
			}
		});
		setBounds(100, 100, 380, 256);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);		
		final JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Selecciona un articulo"}));
		comboBox.setBounds(130, 51, 169, 22);
		MeterDatos(comboBox);
		contentPane.add(comboBox);		
		JLabel label = new JLabel("Articulo:");
		label.setBounds(31, 54, 56, 16);
		contentPane.add(label);		
		JButton btnRealizarModificacion = new JButton("Modificar Articulo");
		btnRealizarModificacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(comboBox.getSelectedIndex()==0) {
					JOptionPane.showMessageDialog (null, "Elija una opcion distinta, please."+"", "Continuar", JOptionPane.INFORMATION_MESSAGE);
				}else {
					String seleccionado = SplitElegido(comboBox.getSelectedItem().toString());
					modificarArticulos2.setFrase(seleccionado);
					modificarArticulos2.inicializar();
					modificarArticulos2.setVisible(true);
				}
			}		
		});
		btnRealizarModificacion.setBounds(87, 154, 169, 25);
		contentPane.add(btnRealizarModificacion);
	}
	/**
	 * Este es el metodo donde se añaden los articulos en el comboBox mediante una conexion a la base
	 * de datos.
	 * @param comboBox Este es el comboBox que recibe los datos
	 */
	private void MeterDatos(JComboBox comboBox) {
		String login = "root";
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/tiendecita?autoReconnect=true&useSSL=false";
		String password = "Studium2019;";
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
	 * Recoge el string con formato (x-a-y) y devuelve el primer dato
	 * @param elegido
	 * @return
	 */
	private String SplitElegido(String elegido) {
		String[] cosasElegidas = elegido.split(" - ");
		String numeroElegido = cosasElegidas[0];
		return numeroElegido;
	}
}
