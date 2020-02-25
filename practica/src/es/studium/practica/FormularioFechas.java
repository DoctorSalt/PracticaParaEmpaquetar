package es.studium.practica;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Es el frame que pide por dos fechas y busca en la base de datos entre ese rango
 * creando un pdf y lo muestra
 * @author DoctorSalt/ José Antonio Muñoz Periáñez 
 */
public class FormularioFechas extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lbDesde;
	private JLabel lbHasta;
	private JTextField txfDesde;
	private JTextField txfHasta;
	private JButton botonAceptar; 
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_4;

	public FormularioFechas() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
			}
		});
		setTitle("Consulta de Tickets");
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(2, 2, 0, 0));		
		panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignOnBaseline(true);
		panel.add(panel_1);
		lbDesde = new JLabel("Fecha Desde:");
		lbDesde.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lbDesde);		
		panel_2 = new JPanel();
		panel.add(panel_2);
		txfDesde = new JTextField();
		txfDesde.setColumns(16);
		txfDesde.setHorizontalAlignment(SwingConstants.CENTER);
		txfDesde.setToolTipText("19/10/2019");
		panel_2.add(txfDesde);		
		panel_3 = new JPanel();
		panel.add(panel_3);
		lbHasta = new JLabel("Fecha Hasta:");
		panel_3.add(lbHasta);		
		panel_4 = new JPanel();
		panel.add(panel_4);
		txfHasta = new JTextField();
		txfHasta.setColumns(16);
		txfHasta.setHorizontalAlignment(SwingConstants.CENTER);
		txfHasta.setToolTipText("19/10/2019");
		panel_4.add(txfHasta);
		txfHasta.setBounds(100,100,100,100);
		botonAceptar = new JButton("Aceptar");
		botonAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fechaDesde = txfDesde.getText();
				String fechaHasta =txfHasta.getText();
				GuardaPDF.ejecutadorTickets(fechaDesde,fechaHasta);
			}
		});
		contentPane.add(botonAceptar, BorderLayout.SOUTH);
	}
}