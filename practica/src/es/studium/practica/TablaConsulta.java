package es.studium.practica;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * @author DoctorSalt/ José Antonio Muñoz Periáñez 
 *
 */
public class TablaConsulta {
		/**
		 * Este es el atributo de TablaConsulta que medira el tamaño de la cantidad de columnas que abrán
		 */
		int tamano;
		/**
		 * Un array consiste en los nombres titulo que pondremos en la tabla
		 */
		ArrayList<String> listaClientesTituloR;
		/**
		 * Este otro seran otros utilizados para buscar en la base de datos
		 */
		ArrayList<String> listaClientesBusquedaR;
		/**
		 * Otro atributo este sirve para identificar
		 */
		String nombreTablaR;
		/**
		 * Este es el default table model que se necesita para poder hacer la tabla
		 */
		DefaultTableModel modelo = new DefaultTableModel();	
		/**
		 * Este metodo genera una tabla pasando dos arrays y el nombre de la tabla
		 * @param listaCosasTitulo array que contiene los titulos que aparecerán en la tabla
		 * @param listaCosasBusqueda array que contiene los nombres de las columnas que se buscará en la tabla
		 * @param nombreTabla tabla donde se hará la busqueda
		 * @return devuelve el Jtable ya hecho
		 */
		public JTable tablaConsultaAplicacion(ArrayList<String> listaCosasTitulo, ArrayList<String> listaCosasBusqueda, String nombreTabla) {
			tamano=listaCosasTitulo.size();
			listaClientesTituloR=listaCosasTitulo;
			listaClientesBusquedaR=listaCosasBusqueda;
			nombreTablaR=nombreTabla;

			String login = "root";
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/tiendecita?autoReconnect=true&useSSL=false";
			String password = "Studium2019;";
			String sentencia;
			Connection connection = null;
			java.sql.Statement statement = null;
			ResultSet rs = null;
			try
			{
				String palabro="";
				Class.forName(driver);
				connection = DriverManager.getConnection(url, login, password);
				statement = connection.createStatement();
				/**
				 * Es el vector de columnas aqui se añaden las columnas con sus nombres
				 * Recibirá el array listaClientesTituloR
				 */
				Vector columnNames=new Vector();
				
				for(int i=0;i<tamano;i++) {
					modelo.addColumn(listaClientesTituloR.get(i));
					columnNames.addElement(listaClientesTituloR.get(i));				
				}	
				modelo.setColumnIdentifiers(columnNames);
				for(int i=0; i<tamano;i++) 
				{
					//Esto lo pongo para al escribir las columnas el primer registro
					//Sin coma y los siguientes con ella
					if(i==0) {
					palabro=listaClientesBusquedaR.get(i);	
					}else {
					palabro= palabro+", "+listaClientesBusquedaR.get(i);					
					}
				}
				sentencia = "Select "+palabro+" from tiendecita."+nombreTablaR+";";
				rs = statement.executeQuery (sentencia);
				while(rs.next()) {
					Object [] fila = new Object[tamano];
					for(int i=0; i<tamano;i++) {
						fila[i] = rs.getString(listaClientesBusquedaR.get(i));
					}
					modelo.addRow(fila);
				}
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
						rs.close();
						statement.close();
						connection.close();
					}
				}
				catch (SQLException e)
				{
					System.out.println("Error al cerrar SQL: "+e.getMessage());
				}
			}
			/**
			 * Tabla a devolver por el metodo se rellena con el DefaultTableModel modelo antes mencionado
			 */
			JTable tabla = new JTable(modelo);
			tabla.setDefaultEditor(Object.class, null);
			return tabla;
		}
}
