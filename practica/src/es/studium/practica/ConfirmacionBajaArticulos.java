package es.studium.practica;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
/**
 * Esta clase contiene un dialog donde se hace la conexion a la base de datos al darle aceptar.
 * Esta clase acaba recibiendo la sentencia en frase.
 * @author DoctorSalt/ José Antonio Muñoz Periáñez 
 */
public class ConfirmacionBajaArticulos extends JDialog {
	private static final long serialVersionUID = 1L;
	String frase;
	private final JPanel contentPanel = new JPanel();
	public ConfirmacionBajaArticulos() {
		setTitle("\u00BFEst\u00E1 Seguro?");
		setBounds(100, 100, 317, 135);
		getContentPane().setLayout(new BorderLayout());
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				setVisible(false);
			}
		});
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JLabel lblNewLabel = new JLabel("\u00BFEst\u00E1 seguro de borrar este Art\u00EDculo?");
			contentPanel.add(lblNewLabel);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Si");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Ejecutar(getFrase());
						setVisible(false);
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
	 * @param frase2 es la sentencia que ejecutara
	 */
	public void Ejecutar(String frase2) {
		String login = "root";
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/tiendecita?autoReconnect=true&useSSL=false";
		String password = "Studium2019;";
		String sentencia=frase2;
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
					connection.close();
				}
			}
			catch (SQLException e)
			{
				System.out.println("Error al cerrar SQL: "+e.getMessage());
			}
		}
	}
	public void setFrase(String sentencia) {
		frase = sentencia;
	}
	public String getFrase(){
		return frase;
	}
}
