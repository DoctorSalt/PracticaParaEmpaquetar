package es.studium.practica;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
/**
 * Esta clase contiene un dialog donde se hace la conexion a la base de datos al darle aceptar.
 * Esta clase acaba recibiendo la sentencia en frase.
 * @author DoctorSalt/ José Antonio Muñoz Periáñez 
 */
public class ConfirmacionModificarArticulos extends JDialog {
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	String frase;
	public ConfirmacionModificarArticulos() {
		setTitle("\u00BFEst\u00E1 Seguro?");
		setBounds(100, 100, 343, 133);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JLabel lblNewLabel = new JLabel("\u00BFEst\u00E1 seguro de modifciar este Art\u00EDculo?");
			contentPanel.add(lblNewLabel);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("SI");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Ejecutar();
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
	public void setFrase(String sentencia) {
		frase=sentencia;		
	}
	public String getFrase() {
		return frase;
	}
	/**
	 * Ejecuta la sentencia SQL que se le ha pasado
	 */
	private void Ejecutar() {
		String login = "root";
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/tiendecita?autoReconnect=true&useSSL=false";
		String password = "Studium2019;";
		Connection connection = null;
		java.sql.Statement statement = null;
		ResultSet rs = null;		
		String sentencia=getFrase();
		try
		{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);			
			statement.executeUpdate(sentencia);
		}
		catch (SQLException sqle)
		{
			System.out.println("Error de SQL: "+sqle.getMessage());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
}
