package principal;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;

import java.io.FileWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;



import javax.xml.xquery.XQException;
import javax.xml.xquery.XQPreparedExpression;

import javax.xml.xquery.XQResultSequence;
import net.xqj.exist.ExistXQDataSource;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.*;

import javax.xml.xquery.XQExpression;
import javax.xml.xquery.XQSequence;


public class PruebasExist_XQJ {


	public static void main(String[] args) throws IOException, ParserConfigurationException {
		verproductos();
    //	cuentaproduc();
	//	 numporzona();
    //	ejecutarconsultadefichero();
    //	creaemple10();
	}

	public static void verproductos() {
		try {
			XQDataSource server = new ExistXQDataSource();
			server.setProperty("serverName", "localhost");
			server.setProperty("port", "8083");
			server.setProperty("user", "admin");
			server.setProperty("password", "admin");
			XQConnection conn = server.getConnection();
			
			XQPreparedExpression consulta;
			XQResultSequence resultado;
			System.out.println("------- Consulta documentos productos.xml ------");
			consulta = conn
					.prepareExpression("for $de in collection('/db/BDProductosXML')/productos/produc return $de");
			resultado = consulta.executeQuery();
			while (resultado.next()) {
				System.out.println("Elemento getItem " + resultado.getSequenceAsString(null));
				
			}
			conn.close();
		} catch (XQException ex) {
			System.out.println("Error en las propiedades del server.");
			System.out.println("Error al operar.");
			ex.printStackTrace();
		}
	}

	public static void cuentaproduc() {
		try {
			XQDataSource server = new ExistXQDataSource();
			server.setProperty("serverName", "localhost");
			server.setProperty("port", "8083");
			server.setProperty("user", "admin");
			server.setProperty("password", "admin");
			XQConnection conn = server.getConnection();
			XQPreparedExpression consulta = conn
					.prepareExpression(" count(collection('/db/BDProductosXML')/productos/produc[precio>50] )");
			XQResultSequence resultado = consulta.executeQuery();
			resultado.next();
			System.out.println("--------------------------------------------");
			System.out.println("Número de productos con precio > de 50: " + resultado.getInt());
			conn.close();
		} catch (XQException ex) {
			System.out.println("Error en las propiedades del server.");
			System.out.println("Error al operar.");
			Logger.getLogger(PruebasExist_XQJ.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static void ejecutarconsultadefichero() {
		try {
			XQDataSource server = new ExistXQDataSource();
			server.setProperty("serverName", "localhost");
			server.setProperty("port", "8083");
			server.setProperty("user", "admin");
			server.setProperty("password", "admin");
			XQConnection conn = server.getConnection();
			InputStream query;
			query = new FileInputStream("miconsulta.xq");
			XQExpression xqe = conn.createExpression();
			
			XQSequence resultado = xqe.executeQuery(query);
			System.out.println("----- Consulta desde fichero -------");
			while (resultado.next()) {
				System.out.println(resultado.getItem().toString());
			}
			conn.close();
		} catch (XQException ex) {
			System.out.println("Error en las propiedades del server.");
			System.out.println("Error al operar.");
			Logger.getLogger(PruebasExist_XQJ.class.getName()).log(Level.SEVERE, null, ex);
		} catch (FileNotFoundException ex) {
			Logger.getLogger(PruebasExist_XQJ.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static void creaemple10() {
		String nom_archivo = "NUEVO_EMPLE10.xml";
		File fichero = new File(nom_archivo);

		XQDataSource server = new ExistXQDataSource();
		try {
			server.setProperty("serverName", "localhost");
			server.setProperty("port", "8083");
			XQConnection conn = server.getConnection();
			XQPreparedExpression consulta = conn
					.prepareExpression("let $titulo:= collection('/db/ColeccionPruebas')/EMPLEADOS/TITULO  "
							+ " return  <EMPLEADOS>{$titulo} " + " {for $em in collection('/db/ColeccionPruebas')"
							+ "/EMPLEADOS/EMP_ROW[DEPT_NO=10] return $em} " + " </EMPLEADOS> ");
			XQResultSequence result = consulta.executeQuery();
			if (fichero.exists()) { // borramos el archivo si existe y se crea
									// de nuevo
				if (fichero.delete())
					System.out.println("Archivo borrado. Creo de nuevo.");
				else
					System.out.println("Error al borrar el archivo");
			}
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(nom_archivo));
				bw.write("<?xml version='1.0' encoding='ISO-8859-1'?>" + "\n");
				result.next();
				String cad = result.getItemAsString();;
				System.out.println(" output " + cad); // visualizamos
				bw.write(cad + "\n"); // grabamos en el fichero
				bw.close(); // Cerramos el fichero el fichero
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			conn.close();
		} catch (XQException e) {
			e.printStackTrace();
		}

	}

	public static void numporzona() {
		try {
			XQDataSource server = new ExistXQDataSource();
			server.setProperty("serverName", "localhost");
			server.setProperty("port", "8083");
			server.setProperty("user", "admin");
			server.setProperty("password", "admin");
			XQConnection conn = server.getConnection();
   		    XQPreparedExpression consulta = conn
					.prepareExpression(" for $zo in collection('/db/BDProductosXML')/zonas/zona "
							+ "let $cu:= count(collection('/db/BDProductosXML')/"
							+ "productos/produc[cod_zona=$zo/cod_zona]) "
							+ " return  concat($zo/nombre,', productos: ', $cu) ");
			XQResultSequence resultado = consulta.executeQuery();
			System.out.println("-------------------------------------");
			System.out.println("Número de productos por cada zona ");
			while (resultado.next()) {
				System.out.println(resultado.getSequenceAsString(null));
			}
			conn.close();
		} catch (XQException ex) {
			System.out.println("Error en las propiedades del server.");
			System.out.println("Error al operar.");
			Logger.getLogger(PruebasExist_XQJ.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
