package es.studium.practica;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/**
 * Esta es la clase donde damos altas a los articulos, es un frame que
 * rellenamos con diferentes datos para poder crear un articulo.
 * Para la creación de los susodichos se confirma con ConfirmacionAltaArticulos.
 * @author DoctorSalt/ José Antonio Muñoz Periáñez 
 */
public class AltaArticulos extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txfDescripcion;
	private JTextField txfPrecio;
	private JTextField txfCantidad;
	/**
	 * Es la clase de confirmacion es el dialog de confirmacion
	 */
	ConfirmacionAltaArticulos confirmacionAltaArticulos =new ConfirmacionAltaArticulos();
	public AltaArticulos() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				setVisible(false);
			}
		});
		setTitle("Alta Articulos");
		setBounds(100, 100, 385, 289);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);		
		txfDescripcion = new JTextField();
		txfDescripcion.setBounds(173, 33, 116, 22);
		contentPane.add(txfDescripcion);
		txfDescripcion.setColumns(10);		
		txfPrecio = new JTextField();
		txfPrecio.setColumns(10);
		txfPrecio.setBounds(173, 70, 116, 22);
		contentPane.add(txfPrecio);		
		txfCantidad = new JTextField();
		txfCantidad.setColumns(10);
		txfCantidad.setBounds(173, 107, 117, 22);
		contentPane.add(txfCantidad);		
		JLabel lblDescripcion = new JLabel("Descripci\u00F3n:");
		lblDescripcion.setBounds(67, 29, 78, 31);
		contentPane.add(lblDescripcion);		
		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(67, 72, 56, 16);
		contentPane.add(lblPrecio);		
		JLabel lblCantidad = new JLabel("Cantidad:");
		lblCantidad.setBounds(65, 111, 56, 16);
		contentPane.add(lblCantidad);		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Boolean primerCheck=false;
				Boolean segundoCheck=false;
				Boolean tercerCheck=false;
				/** 
				 * Booleanoo que chequea si se han sucedido errores en la transformacion a numero
				 */
				Boolean errorTransformacion=false;
				double precio = 0;
				int cantidad = 0;
				String precioRecibido="";
				String precioMandado="";
				String Descripcion = txfDescripcion.getText();
				try {
					precioRecibido=txfPrecio.getText();
					if(precioRecibido.contains(",")) {
						String [] partes =	precioRecibido.split(",");
						precioMandado=partes[0]+"."+partes[1];					
					}
					else {
						precioMandado=precioRecibido;
					}
					precio = Double.parseDouble(precioMandado);
				}
				catch(Exception e) {
					problemas("precio");
					errorTransformacion=true;
					e.getMessage();
				}
				try {
					cantidad = Integer.parseInt(txfCantidad.getText());
				}
				catch(Exception e) {
					problemas("cantidad");
					errorTransformacion=true;
					e.getMessage();
				}
				primerCheck = comprobantePrimero(primerCheck, Descripcion);
				segundoCheck = segundoComprobante(segundoCheck, precio);
				tercerCheck = tercerComprobante(tercerCheck, cantidad);
				if((primerCheck==true)&&(segundoCheck==true)&&(tercerCheck==true)&&(errorTransformacion==false)) {
					String sentencia = "INSERT INTO tiendecita.articulos VALUES (null,"+"'"+Descripcion+"',"+precio+","+cantidad+");";
					confirmacionAltaArticulos.setFrase(sentencia);
					confirmacionAltaArticulos.setVisible(true);
				}			
			}		
		});
		btnAgregar.setBounds(60, 161, 97, 25);
		contentPane.add(btnAgregar);		
		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txfDescripcion.setText("");
				txfCantidad.setText("");
				txfPrecio.setText("");
			}
		});
		btnBorrar.setBounds(206, 161, 97, 25);
		contentPane.add(btnBorrar);
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
