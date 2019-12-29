package principal;
import org.xmldb.api.*;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.*;
public class pruebaexcepcionesxmldb {

	protected static String driver = "org.exist.xmldb.DatabaseImpl";
	public static String URI = 
	           "xmldb:exist://localhost:8083/exist/xmlrpc";
	private static Database database;
	private static String usu="admin";
	private static String pwd="admin";
	private static Class cl=null;
	private static XPathQueryService service;
	private static ResourceSet result=null;
	private static Collection col = null; // Colección	
	
	public static void main(String[] args) {
	      try {
		  cl = Class.forName(driver);
		} catch (ClassNotFoundException e) {
		  System.out.println("No se encuentra la clase del driver: "
	          + e.getMessage());	}
		try {
		  database = (Database) cl.newInstance();
		  DatabaseManager.registerDatabase(database);
		} catch (InstantiationException e) {
		  System.out.println("Error instanciando el driver. ");
		}catch(NullPointerException e) {
		  System.out.println("Error al instanciar la clase del driver: "
	          + e.getMessage());
		} catch (IllegalAccessException e) {
		  System.out.println("Se ha producido una IllegalAccessException");
		} catch (XMLDBException e) {
		  System.out.println("Error XMLDB :" + e.getMessage());	} 
		try 	{
		 col = DatabaseManager.getCollection(URI+"/db/ColeccionPruebas",usu,pwd);
		} catch (XMLDBException e) {
		  System.out.println("ERROR XMLDBException en getCollection."+ 
	           e.getMessage());	} 
		try {
		  service =(XPathQueryService) 
	            col.getService("XPathQueryService", "1.0");
		}catch (NullPointerException n){
		  System.out.println("Error en getService, no se puede "+
	            " crear el servicio.");
		}catch (XMLDBException e) {
		  System.out.println("ERROR XMLDBException, en get service."
	          + e.getMessage());	} 
		//Consulta a la BD
		try 	{ result = service.query("forr $b in "+
	            " /EMPLEADOS/EMP_ROW[APELLIDO='TOVAR'] return $b");
		}catch (NullPointerException n){
		  System.out.println("Error en query, no se ha inicializado"+
	             "  la BD o el servicio.");
		}catch (XMLDBException e) {
		  System.out.println("Error XMLDBException en la query: "+
	              e.getMessage());	} 
		try 	{ 
	        ResourceIterator i;
		  i = result.getIterator();
	        if (!i.hasMoreResources()){
		   	   System.out.println("LA CONSULTA NO DEVUELVE NADA");	   }
	       while (i.hasMoreResources()) { //Procesamos el resultado
	           Resource r = i.nextResource();
	           System.out.println((String) r.getContent());    }
		}catch ( NullPointerException n){
		  System.out.println("Error getIterator. Problemas con el "+
	           "  servicio.");
		}catch (XMLDBException  e) {
		  System.out.println("Error XMLDBException, getIterator. : "+ 
	             e.getMessage());	} 
		try {
		 col.close();
		}catch ( NullPointerException n){
		  System.out.println("Error en el cierre de la colección.");
		} catch (XMLDBException  e) {
		System.out.println("Error XMLDBException, col.close. : "+ 
	           e.getMessage());	}
	 }//fin main
}// fin de la clase pruebaexcepciones

