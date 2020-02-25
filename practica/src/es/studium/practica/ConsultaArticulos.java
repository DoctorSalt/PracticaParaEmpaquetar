package es.studium.practica;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
/**
 * Esta es la clase donde consultamos a los articulos, es un frame donde vemos 
 * en una tabla todos los articulos.
 * @author DoctorSalt/ José Antonio Muñoz Periáñez 
 */
public class ConsultaArticulos extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JScrollPane table;
	public ConsultaArticulos() {
		setTitle("Consulta Articulos");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				setVisible(false);
			}
		});
		setBounds(100, 100, 545, 356);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);		
		table = new JScrollPane();
		rellenarTabla();
		table.setBounds(42, 41, 401, 207);
		contentPane.add(table);
	}
	/**
	 * Este metodo se encarga de realizar la tabla llamando al metodo de TablaConsulta
	 * pasando dos arrays y un string, uno de titulos de las columnas y otro de busqueda,
	 * como último pasa el nombre de la tabla en este caso articulos.
	 */
	private void rellenarTabla() {
		ArrayList<String> listaArticulosTitulo = new ArrayList<>();
		ArrayList<String> listaArticulosBusqueda = new ArrayList<>();
		listaArticulosTitulo.add("idArticulo");
		listaArticulosBusqueda.add("idArticulo");
		listaArticulosTitulo.add("Descripción");
		listaArticulosBusqueda.add("descArticulo");
		listaArticulosTitulo.add("Precio");
		listaArticulosBusqueda.add("precioArticulo");
		listaArticulosTitulo.add("Cantidad");
		listaArticulosBusqueda.add("cantidadArticulo");
		String nombreTabla="articulos";
		TablaConsulta articulos =new TablaConsulta();
		JTable tablaRecogida =articulos.tablaConsultaAplicacion(listaArticulosTitulo, listaArticulosBusqueda,nombreTabla);
		table=new JScrollPane(tablaRecogida);
		table.setEnabled(false);
		table.setPreferredSize(new Dimension(550, 200));
	}
}
