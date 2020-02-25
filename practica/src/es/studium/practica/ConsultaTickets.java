package es.studium.practica;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
/**
 * Este frame selecciona un ticket para poder visualizarlo visualizandolo en ConsultaTicket2
 * @author DoctorSalt/ José Antonio Muñoz Periáñez 
 */
public class ConsultaTickets extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;	
	JComboBox <String> comboBox;
	/**
	 * Este frame mostrará los datos del ticket seleccionado
	 */
	ConsultaTickets2 consultaTickets2= new ConsultaTickets2();
	public ConsultaTickets() {
		setTitle("Consulta Tickets");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				setVisible(false);
			}
		});
		
		setBounds(100, 100, 410, 209);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JLabel label = new JLabel("Tickets:");
		label.setBounds(61, 37, 56, 16);
		contentPane.add(label);		
		comboBox= new JComboBox();
		comboBox.setBounds(139, 36, 169, 22);
		comboBox=MeterDatos(comboBox);
		contentPane.add(comboBox);		
		JButton button = new JButton("Consultar Ticket");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				if(comboBox.getSelectedIndex()==0) {
					JOptionPane.showMessageDialog (null, "Elija una opcion distinta, please."+"", "Continuar", JOptionPane.INFORMATION_MESSAGE);
				}else {
					String cosaSeleccionada = comboBox.getSelectedItem().toString();
					String idTicketEnviar=Splitea1(cosaSeleccionada);
					String fechaEnviar=Splitea2(cosaSeleccionada);
					String totalEnviar=Splitea3(cosaSeleccionada);
					consultaTickets2.seteandoloTodo(idTicketEnviar,fechaEnviar,totalEnviar);
					consultaTickets2.setVisible(true);
				}				
			}			
		});
		button.setBounds(99, 86, 150, 25);
		contentPane.add(button);
	}
	/**
	 * Este es el metodo donde se añaden los articulos en el comboBox mediante una conexion a la base
	 * de datos.
	 * @param comboRecogido Este es el comboBox que recibe los datos
	 * @return devuelve el comboBox resultante
	 */
	private JComboBox MeterDatos(JComboBox comboRecogido) {
		comboRecogido.setModel(new DefaultComboBoxModel(new String[] {"Seleccione un Ticket"}));		
		String login = "root";
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/tiendecita?autoReconnect=true&useSSL=false";
		String password = "Patata01";
		String sentencia="Select * from tiendecita.tickets";
		Connection connection = null;
		java.sql.Statement statement = null;
		ResultSet rs = null;		
		int datosChoice;
		String dinerosResult;
		String fechaResult;
		try
		{
			connection=Conectar(driver,connection,url,login,password);
			statement = connection.createStatement();
			rs = statement.executeQuery(sentencia);
			while (rs.next())
			{
				datosChoice = rs.getInt("idTicket");
				dinerosResult = rs.getString("totalTicket");
				fechaResult = rs.getString("fechaTicket");
				comboRecogido.addItem(datosChoice+" - "+fechaResult+" - "+dinerosResult);				
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
		return comboRecogido;
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
	 * Recoge el primer dato con formato (x-y-a) y lo devuelve
	 * @param cosaSeleccionada el propio dato con formato (x-y-a)
	 * @return el primer dato devuelto
	 */
	private String Splitea1(String cosaSeleccionada) {
		String[] cosasElegidas = cosaSeleccionada.split(" - ");
		String cosaElegido = cosasElegidas[0];
		return cosaElegido;
	}
	/**
	 * Recoge el segundo dato con formato (x-y-a) y lo devuelve
	 * @param cosaSeleccionada el propio dato con formato (x-y-a)
	 * @return el segundo dato devuelto
	 */
	private String Splitea2(String cosaSeleccionada) {
		String[] cosasElegidas = cosaSeleccionada.split(" - ");
		String cosaElegido = cosasElegidas[1];
		return cosaElegido;
	}
	/**
	 * Recoge el tercer dato con formato (x-y-a) y lo devuelve
	 * @param cosaSeleccionada el propio dato con formato (x-y-a)
	 * @return el tercer dato devuelto
	 */
	private String Splitea3(String cosaSeleccionada) {
		String[] cosasElegidas = cosaSeleccionada.split(" - ");
		String cosaElegido = cosasElegidas[2];
		return cosaElegido;
	}
}
