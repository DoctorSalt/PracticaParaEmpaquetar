package es.studium.practica;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class ConfirmacionAltaTickets extends JDialog {
	String ejecucionAltaTicket;
	ArrayList<String> listaEjecucionTicketsArticulo;
	private final JPanel contentPanel = new JPanel();
	public ConfirmacionAltaTickets() {
		setTitle("\u00BFEst\u00E1 Seguro?");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				setVisible(false);
			}
		});
		setBounds(100, 100, 375, 145);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JLabel lblestSeguroDe = new JLabel("\u00BFEst\u00E1 seguro de crear este Ticket?");
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
						Ejecutar(getEjecucionAltaTicket(),getListaEjecucionTicketsArticulo());
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
	public void setSentencias(String sentenciaTicket, ArrayList<String> listaSentenciaTicketsArticulo) {
		ejecucionAltaTicket=sentenciaTicket;
		listaEjecucionTicketsArticulo=listaSentenciaTicketsArticulo;
	}
	public String getEjecucionAltaTicket() {
		return ejecucionAltaTicket;
	}
	public ArrayList<String> getListaEjecucionTicketsArticulo(){
		return listaEjecucionTicketsArticulo;
	}
	private void Ejecutar(String ejecucionAltaTicket, ArrayList<String> listaEjecucionTicketsArticulo) {
		insertar(ejecucionAltaTicket);
		int cantidadEjecuciones = listaEjecucionTicketsArticulo.size();
		for(int i=0;i<cantidadEjecuciones;i++) {
			insertar(listaEjecucionTicketsArticulo.get(i));
		}		
	}
	private void insertar(String ejecucionSentencia) {
		String login = "root";
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/tiendecita?autoReconnect=true&useSSL=false";
		String password = "Patata01";
		String sentencia=ejecucionSentencia;
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
}
