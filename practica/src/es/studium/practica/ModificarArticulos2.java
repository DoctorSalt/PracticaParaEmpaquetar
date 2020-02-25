package es.studium.practica;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
/**
 * Esta es la clase donde se realizan modificaciones al articulo anteriormente seleccionado, 
 * aqui se cambian los registros
 * @author DoctorSalt/ José Antonio Muñoz Periáñez 
 */
public class ModificarArticulos2 extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtDescripcion;
	private JTextField txtPrecio;
	private JTextField txtCantidad;
	/**
	 * Esta es la sentencia que vamos a ejecutar
	 */
	String frase="";
	/**
	 * Este dialog confirma si se quiere hacer la modificación en caso afirmativo se realiza
	 */
	ConfirmacionModificarArticulos confirmacionModificarArticulos = new ConfirmacionModificarArticulos();
	public ModificarArticulos2() {
		setTitle("Modificar Articulo");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				setVisible(false);
			}
		});
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JLabel lblDesc = new JLabel("Descripci\u00F3n:");
		lblDesc.setBounds(88, 24, 78, 31);
		contentPane.add(lblDesc);		
		txtDescripcion = new JTextField();
		txtDescripcion.setColumns(10);
		txtDescripcion.setBounds(194, 28, 116, 22);
		contentPane.add(txtDescripcion);		
		txtPrecio = new JTextField();
		txtPrecio.setColumns(10);
		txtPrecio.setBounds(194, 65, 116, 22);
		contentPane.add(txtPrecio);
		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(88, 67, 56, 16);
		contentPane.add(lblPrecio);		
		JLabel lblCantidad = new JLabel("Cantidad:");
		lblCantidad.setBounds(86, 106, 56, 16);
		contentPane.add(lblCantidad);		
		txtCantidad = new JTextField();
		txtCantidad.setColumns(10);
		txtCantidad.setBounds(194, 102, 117, 22);
		contentPane.add(txtCantidad);		
		JButton btnGuardarCambios = new JButton("Guardar Cambios");
		btnGuardarCambios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Boolean primerCheck=false;
				Boolean segundoCheck=false;
				Boolean tercerCheck=false;
				Boolean errorTransformacion=false;
				double precio = 0;
				int cantidad = 0;
				String precioRecibido="";
				String precioMandado="";
				String descripcion = txtDescripcion.getText();
				try {
					precioRecibido=txtPrecio.getText();
					if(precioRecibido.contains(",")) {
						String [] partes =	precioRecibido.split(",");
						precioMandado=partes[0]+"."+partes[1];					
					}
					else {
						precioMandado=precioRecibido;
					}
					precio = Double.parseDouble(precioMandado);
				}
				catch(Exception exc) {
					problemas("precio");
					errorTransformacion=true;
					exc.getMessage();
				}
				try {
					cantidad = Integer.parseInt(txtCantidad.getText());
				}
				catch(Exception exc) {
					problemas("cantidad");
					errorTransformacion=true;
					exc.getMessage();
				}
				primerCheck = comprobantePrimero(primerCheck, descripcion);
				segundoCheck = segundoComprobante(segundoCheck, precio);
				tercerCheck = tercerComprobante(tercerCheck, cantidad);
				if((primerCheck==true)&&(segundoCheck==true)&&(tercerCheck==true)&&(errorTransformacion==false)) {
					String sentencia="UPDATE tiendecita.articulos set descArticulo = '"+descripcion+"', precioArticulo = "+precio+", cantidadArticulo = "+cantidad+" where idArticulo="+getFrase()+";";
					confirmacionModificarArticulos.setFrase(sentencia);
					confirmacionModificarArticulos.setVisible(true);
				}						
			}
		});
		btnGuardarCambios.setBounds(46, 182, 131, 25);
		contentPane.add(btnGuardarCambios);

		JButton btnBorrarDatos = new JButton("Borrar Datos");
		btnBorrarDatos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtDescripcion.setText("");
				txtCantidad.setText("");
				txtPrecio.setText("");
			}
		});
		btnBorrarDatos.setBounds(233, 182, 131, 25);
		contentPane.add(btnBorrarDatos);
	}
	/**
	 * Esta clase rellena los datos de los texfields con los datos 
	 * del registro anteriormente seleccionado
	 * @param selec
	 */
	private void DatosCompletos(String selec) {	
		String login = ConectameEsta.getUsuarioBD();
		String driver = "com.mysql.jdbc.Driver";
		String url = ConectameEsta.getConexionBD();
		String password = ConectameEsta.getPasswordDB();
		Connection connection = null;
		java.sql.Statement statement = null;
		ResultSet rs = null;		
		String sentencia;
		try
		{
			connection =Conectar(driver,connection,url,login,password);
			statement = connection.createStatement();
			sentencia ="Select * from tiendecita.articulos where idArticulo = "+selec+" ;";
			rs = statement.executeQuery(sentencia);
			while(rs.next())
			{
				String nombreRespuesta = rs.getString("descArticulo");
				double precioRespuesta=rs.getDouble("precioArticulo");
				int cantidadRespuesta = rs.getInt("cantidadArticulo");
				txtDescripcion.setText(nombreRespuesta);
				txtPrecio.setText(precioRespuesta+"");
				txtCantidad.setText(cantidadRespuesta+"");
			}
		}
		catch (SQLException sqle)
		{
			System.out.println("Error de SQL: "+sqle.getMessage());
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
		}
		catch(ClassNotFoundException cnfe) {
			System.out.println("Error 1: "+cnfe.getMessage());
		}
		catch (SQLException sqle)
		{
			System.out.println("Error 2: "+sqle.getMessage());
		}		
		return connection;
	}
	String getFrase(){
		return frase;
	}
	void setFrase(String fr) {
		frase = fr;
	}
	/**
	 * Incializa todos los componentes con sus datos llamando al metodo necesario
	 */
	public void inicializar() {
		System.out.println(getFrase());
		DatosCompletos(getFrase());
	}
	/**
	 * Este simplemente pasa el error que haya sucedido como string explicando el problema como dialog con
	 * showMessageDialog
	 * @param problema string que determina que problema ha sucedido
	 */
	private void problemas(String problema) {
		JOptionPane.showMessageDialog (null, "Escriba bien el registro "+problema+"", "Continuar", JOptionPane.INFORMATION_MESSAGE);				
	}
	/**
	 * Comprueba si la cantidad es 0
	 * @param tercerCheck el booleano que recoge el flag
	 * @param cantidad 
	 * @return el mismo booleano que puede o no haberse cambiado
	 */
	private Boolean tercerComprobante(Boolean tercerCheck, int cantidad) {
		if(cantidad!=0) {
			tercerCheck=true;
		}
		return tercerCheck;
	}
	/**
	 * Comprueba si el precio es 0 o 0.0
	 * @param segundoCheck el booleano que recoge el flag
	 * @param precio
	 * @return el mismo booleano que puede o no haberse cambiado
	 */
	private Boolean segundoComprobante(Boolean segundoCheck, double precio) {
		if((precio!=0)||(precio!=0.0)) {
			segundoCheck=true;
		}
		return segundoCheck;
	}
	/**
	 * Comprueba si la descripción está vacía
	 * @param primerCheck el booleano que recoge el flag
	 * @param Descripcion
	 * @return el mismo booleano que puede o no haberse cambiado
	 */
	private Boolean comprobantePrimero(Boolean primerCheck, String Descripcion) {
		switch(Descripcion) {
		case "":
		case " ":
			problemas("Descripción");
			break;
		default:
			primerCheck=true;
			break;
		}
		return primerCheck;
	}
}
