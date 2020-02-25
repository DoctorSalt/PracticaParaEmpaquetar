package es.studium.practica;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Esta clase contiene un dialog donde se hace la conexion a la base de datos al darle aceptar.
 * Esta clase acaba recibiendo la sentencia en frase
 * @author DoctorSalt/ José Antonio Muñoz Periáñez 
 */
public class ConfirmacionAltaArticulos extends JDialog {
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	/**
	 * Variable que luego ejecuta la conexion a la base de datos
	 */
	String frase;
	public ConfirmacionAltaArticulos() {
		String frase = null;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				setVisible(false);
			}
		});
		setTitle("\u00BFEst\u00E1 Seguro?");
		setBounds(100, 100, 375, 145);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JLabel lblestSeguroDe = new JLabel("\u00BFEst\u00E1 seguro de crear este Art\u00EDculo?");
			contentPanel.add(lblestSeguroDe);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Si");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						Ejecutar(getFrase());
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("No");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	/**
	 * Esta seria la conexion a base de datos
	 * @param frase es la sentencia que ejecutara
	 */
	protected void Ejecutar(String frase) {
		String login = "root";
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/tiendecita?autoReconnect=true&useSSL=false";
		String password = "Patata01";
		String sentencia=frase;
		Connection connection = null;
		java.sql.Statement statement = null;
		ResultSet rs = null;
		try
		{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();
			statement.executeUpdate(sentencia);
		}
		catch (ClassNotFoundException cnfe)
		{
			System.out.println("Error de Clase: "+cnfe.getMessage());
		}
		catch (SQLException sqle)
		{
			System.out.println("Error de SQL: "+sqle.getMessage());
		}
		finally
		{
			try
			{
				if(connection!=null)
				{
					statement.close();
					connection.close();
				}
			}
			catch (SQLException e)
			{
				System.out.println("Error al cerrar SQL: "+e.getMessage());
			}
		}
	}
	/**
	 * Constructor para añadir al atributo frase
	 * @param fras dato que se añade
	 */
	public void setFrase(String fras) {
		frase=fras;
	}
	public String getFrase() {
		return frase;
	}
}
