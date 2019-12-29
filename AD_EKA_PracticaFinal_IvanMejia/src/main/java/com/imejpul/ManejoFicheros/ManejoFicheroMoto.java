package com.imejpul.ManejoFicheros;

import com.imejpul.Auxiliares.ListaMotos;
import com.imejpul.Serializables.Moto;
import com.thoughtworks.xstream.XStream;

import java.io.*;

public class ManejoFicheroMoto {

    public void escribirFicheroMoto(Moto moto) {
        //objeto file
        File fichero = new File("./.datDocs/FichMotos.dat");
        //Flujo de salida
        try {
            FileOutputStream fileout = new FileOutputStream(fichero, true);
            //Conecta el flujo de bytes al flujo de datos
            ObjectOutputStream dataOS = new ObjectOutputStream(fileout);

            dataOS.writeObject(moto);
            dataOS.close();
        } catch (IOException e) {
            System.out.println(" - Fin fichero Motos - ");
            //e.printStackTrace();
        }
    }

    public void escribirFicheroMotoXML() {

        File fichero = new File("./.datDocs/FichMotos.dat");

        ObjectInputStream dataIS = null;
        FileInputStream filein = null;
        try {
            filein = new FileInputStream(fichero);

        } catch (IOException e) {
            System.out.println(" - Fin fichero Motos (XML) - ");
            //e.printStackTrace();
        }

        //Creamos un objeto Lista
        ListaMotos lr = new ListaMotos();
        try {
            while (true) {
                //lectura del fichero
                dataIS = new ObjectInputStream(filein);
                Moto moto = (Moto) dataIS.readObject();
                lr.addResultadoALista(moto);

            }
        } catch (IOException o) {
            System.out.println(" - Fin fichero Motos (XML)- ");
            //o.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            dataIS.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            XStream xstream = new XStream();
            //cambiar de nombre a las etiquetas XML
            xstream.alias("ListaMotos", ListaMotos.class);
            xstream.alias("Moto", Moto.class);
            //quitar etiqueta lista (atributo de la clase ListaPersonas)
            xstream.addImplicitCollection(ListaMotos.class, "Lista");
            //Insrtarlos objetos en el XML
            xstream.toXML(lr, new FileOutputStream("./XMLDocs/Motos.xml", true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
