package es.studium.practica;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Este es el frame principal desde el que se mueve a cada funcionalidad
 * @author DoctorSalt/ José Antonio Muñoz Periáñez
 * @version JDK 1.8.0_211 Supongo que version 1.8
 */

public class MenuPrincipal extends JFrame {
	private static final long serialVersionUID = 1L;
	/**
	 * Este es el panel principal del menu
	 */
	private JPanel contentPane;
	/**
	 * Este es el menu bar de MenuPrincipal
	 */
	private JMenuBar menuBar;
	/**
	 * Estos son los apartados Jmenu que se encuentran en el menu bar
	 */
	private JMenu mnarticulos, mntickets, mnFormulario;
	/**
	 * Estos son los menuItem de cada apartado. Cada apartado hace visible el correspondiente java.
	 * Cada uno ya ha sido ejecutado pero es invisible
	 */
	private JMenuItem mniAltaArticulos, mniBajaArticulos,	
	mniConsultaArticulos, mniModificaArticulos,
	mniAltaTickets, mniConsultaTickets,
	mniFormularioTicket, mniFormularioArticulos;
	/**
	 * Este es el apartado de Altas de ticket aqui se dan de alta los susodichos
	 */
	AltaTickets altaTicket = new AltaTickets();
	/**
	 * Este es el apartado de Altas de articulos aqui se dan de alta los susodichos
	 */
	AltaArticulos altaArticulo= new AltaArticulos();
	/**
	 * Este es el apartado de bajas de articulos aqui se dan de baja los susodichos
	 */
	BajaArticulos bajaArticulo = new BajaArticulos();
	/**
	 * Esto es el apartado de consulta de articulos, aqui se pueden ver los articulos
	 */
	ConsultaArticulos consultaArticulos = new ConsultaArticulos();
	/**
	 * Esto es el apartado de consulta de tickets, aqui se pueden ver los tickets y su contenido
	 */
	ConsultaTickets consultaTickets = new ConsultaTickets();
	/**
	 * Esto es el apartado de modificar articulos, aqui se pueden modificar los articulos presentes
	 */
	ModificaArticulos modificaArticulos = new ModificaArticulos();
	/**
	 * Este es el apartado de los formularios hechos con jasper
	 */
	FormularioFechas formFech = new FormularioFechas(); 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuPrincipal frame = new MenuPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Constructor del frame de MenuPrincipal
	 */
	public MenuPrincipal() {
		setTitle("Programa");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 472, 126);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		mntickets=new JMenu();
		mntickets.setText("Tickets");
		mniAltaTickets=new JMenuItem();
		mniAltaTickets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				altaTicket.setVisible(true);
			}
		});
		mniAltaTickets.setText("Alta Tickets");
		mntickets.add(mniAltaTickets);
		mniConsultaTickets=new JMenuItem();
		mniConsultaTickets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consultaTickets.setVisible(true);
			}
		});
		mniConsultaTickets.setText("Consulta Tickets");
		mntickets.add(mniConsultaTickets);
		menuBar=new JMenuBar();
		mnarticulos=new JMenu();
		mnarticulos.setText("Articulos");		
		mniAltaArticulos=new JMenuItem();
		mniAltaArticulos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				altaArticulo.setVisible(true);
			}
		});
		mniAltaArticulos.setText("Alta Artículos");
		mnarticulos.add(mniAltaArticulos);
		mniBajaArticulos=new JMenuItem();
		mniBajaArticulos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bajaArticulo.setVisible(true);
			}
		});
		mniBajaArticulos.setText("Baja Artículos");
		mnarticulos.add(mniBajaArticulos);
		mniConsultaArticulos=new JMenuItem();
		mniConsultaArticulos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consultaArticulos.setVisible(true);
			}
		});
		mniConsultaArticulos.setText("Consulta Artículos");
		mnarticulos.add(mniConsultaArticulos);
		mniModificaArticulos=new JMenuItem();
		mniModificaArticulos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modificaArticulos.setVisible(true);
			}
		});		
		mnarticulos.add(mniModificaArticulos);		
		mnFormulario = new JMenu();
		mnFormulario.setText("Formularios");
		mniFormularioTicket = new JMenuItem();
		mniFormularioTicket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				formFech.setVisible(true);
			}
		});
		mniFormularioTicket.setText("Tickets");
		mniFormularioArticulos = new JMenuItem();
		mniFormularioArticulos.addActionListener(new ActionListener() {
			/**
			 *Ejecuta el actionPerformed todosArticulos de la clase guardaPdf
			 */
			public void actionPerformed(ActionEvent arg0) {
				GuardaPDF.todosArticulos();
			}
		});
		mniFormularioArticulos.setText("Todos Articulos");
		mnFormulario.add(mniFormularioArticulos);
		mnFormulario.add(mniFormularioTicket);			
		menuBar.add(mnarticulos);
		menuBar.add(mntickets);
		menuBar.add(mnFormulario);
		contentPane.add(menuBar);		
	}
}