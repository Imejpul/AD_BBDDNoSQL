package com.imejpul;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import java.io.File;

public class xmldbOperations{

    private static String URI = "xmldb:exist://localhost:8083/exist/xmlrpc/db/";

    /**
     * collectionName Should be the name of the collection to access
     * resourceName Should be the name of the resource to read from the collection
     */
    public static void createCollectionResource(String collectionName, String resourceName) throws Exception {

        final String driver = "org.exist.xmldb.DatabaseImpl";

        // initialize database driver
        Class cl = Class.forName(driver);
        Database database = (Database) cl.newInstance();
        DatabaseManager.registerDatabase(database);

        Collection col = null;
        XMLResource res = null;
        try {
            // get the collection
            col = DatabaseManager.getCollection(URI + collectionName, "admin", "admin");

            res = (XMLResource) col.createResource(resourceName, "XMLResource");
            File f = new File(resourceName);
            if (!f.canRead()) {
                System.out.println("cannot read file " + resourceName);
                return;
            }

            res.setContent(f);
            System.out.print("storing document " + res.getId() + "...");
            col.storeResource(res);
            System.out.println("ok.");

        } finally {
            if (col != null) {
                try {
                    col.close();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
        }
    }

    /**
     * collectionName Should be the name of the collection to access
     * resourceName Should be the name of the resource to read from the collection
     */
    public static Object RetrieveResource(String collectionName, String resourceName) throws Exception {
        final String driver = "org.exist.xmldb.DatabaseImpl";

        // initialize database driver
        Class cl = Class.forName(driver);
        Database database = (Database) cl.newInstance();
        DatabaseManager.registerDatabase(database);

        Collection col = null;
        XMLResource res = null;
        try {
            // get the collection
            col = DatabaseManager.getCollection(URI + collectionName, "admin", "admin");
            res = (XMLResource) col.getResource(resourceName);

            if (res == null) {
                System.out.println("document not found!");
                return null;
            } else {
                System.out.println(res.getContent());
                return res.getContent();
            }
        } finally {
            //dont forget to clean up!

            if (col != null) {
                try {
                    col.close();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
        }
    }
}
